package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VespaEntity extends RidableFlyingMobEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE   	= RawAnimation.begin().thenLoop("vespa.model.idle");
    private static final RawAnimation WALK   	= RawAnimation.begin().thenLoop("vespa.model.walk");
    private static final RawAnimation FLY    	= RawAnimation.begin().thenLoop("vespa.model.fly");
    private static final RawAnimation FLY_AGGRO = RawAnimation.begin().thenLoop("vespa.model.fly_aggro");
    private static final RawAnimation ATTACK 	= RawAnimation.begin().thenPlay("vespa.model.attack_blend");
    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(VespaEntity.class, EntityDataSerializers.INT);

	public static final int ATTACK_TIMER = 30;
	public static final int ATTACK_HIT = 9;

    public VespaEntity(EntityType<? extends VespaEntity> type, Level world) {
        super(type, world);
    }

    public static boolean checkVespaSpawnRules(EntityType<? extends VespaEntity> type,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return FlyingMobEntity.checkFlyerSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AttackGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false,
                p -> !(p.isPassenger() && p.getVehicle() instanceof VespaEntity)).setUnseenMemoryTicks(160));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, LivingEntity.class, false,
                e -> e.attackable() && e.getType().is(FUREntityTypeTagsProvider.VESPA_TARGETS)).setUnseenMemoryTicks(160));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FLYING_SPEED, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, 0);
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get() * 2.0D);
            this.setHealth(this.getHealth() * 2.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get());
            this.setHealth(this.getHealth() * 0.5F);
        }
    }
    
    @Override
    protected Goal wanderGoal() {
        return new FlyingMobEntity.AIRandomFly(this, 1.0D);
    }

    @Override
    protected Goal followGoal() {
        return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, false, 24.0D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return this.isTame() && (stack.getItem().equals(FURItemRegistry.GLOWSHROOM_STEW.get()));
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == FUREffectRegistry.INFESTED.get() || effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public boolean canMate(Animal target) {
        return this.isTame() && super.canMate(target);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal partner) {
        ItemEntity ovum = new ItemEntity(level, this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ(),
                new ItemStack(FURItemRegistry.VESPA_OVUM.get()));
        ovum.setDefaultPickUpDelay();
        level.addFreshEntity(ovum);
        this.finalizeSpawnChildFromBreeding(level, partner, null);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        
        if (this.isOwnedBy(player) && itemstack.getItem() == Items.HONEY_BOTTLE && this.isAlive() && this.getSkin() == 0) {
        	if (!player.isCreative()) {
        		itemstack.shrink(1);
        	}
        	this.setSkin(1);      	
        	this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
        	for (int i = 0; i < 16; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), this.getY() + (this.random.nextFloat() * this.getBbHeight()), this.getZ() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), d0, d1, d2);
            }
        	
        	return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        
		return super.mobInteract(player, hand);             
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.45F;
    }

    @Override
    public int abilityCooldown() {
        return 30;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.onGround() && this.tickCount % 20 == 0 && !this.level().isClientSide()) {
            this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
        }

        if (this.abilityCooldown == (this.abilityCooldown() - (ATTACK_TIMER - ATTACK_HIT)) && this.deathTime <= 0) {
            double dx = 1.75D * this.getLookAngle().normalize().x;
            double dz = 1.75D * this.getLookAngle().normalize().z;
            double cx = this.getX() + dx;
            double cy = this.getY();
            double cz = this.getZ() + dz;

            for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class,
                    new AABB(cx, cy, cz, cx, cy, cz).inflate(1.0D))) {
                if (!this.equals(target) && !this.isAlliedTo(target)) {
                    this.doHurtTarget(target);
                }
            }

            if (!this.level().isClientSide()) {
                this.playSound(SoundEvents.TRIDENT_THROW, 0.6F, 2.0F);
            }
        }
    }

    @Override
    protected void ageBoundaryReached() {
        if (this.isBaby()) {
            if (this.isSaddled()) {
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE, 1);
            }

            if (this.level() instanceof ServerLevel server) {
                ParasiteEntity larva = FUREntityRegistry.PARASITE.get().create(server);
                if (larva != null) {
                    larva.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    larva.setSkin(2);
                    if (this.isTame() && this.getOwner() instanceof Player player) {
                        larva.tame(player);
                        larva.setCustomName(this.getCustomName());
                    }
                    this.level().addFreshEntity(larva);
                }
            }

            this.discard();
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target.getType().is(FUREntityTypeTagsProvider.VESPA_TARGETS)) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get() * 2.0D);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get());
        }

        if (super.doHurtTarget(target)) {
            if (target instanceof LivingEntity living) {
                int duration = 6 * 20 * (int) this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.addEffect(new MobEffectInstance(MobEffects.POISON, duration, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker
                && attacker.getType().is(FUREntityTypeTagsProvider.VESPA_TARGETS)) {
            return super.hurt(source, amount * 0.5F);
        }
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get());
       	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get());
       	this.setHealth(this.getMaxHealth());
       	
    	return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE);
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, skinType);
    }

    @Override
    protected double VehicleSpeedMod() {
        return (this.isInLava() || this.isInWater()) ? 0.2D : 2.0D;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getSkin());
    }

    @Override
    public int getAmbientSoundInterval() {
        return 1000;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.VESPA_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.VESPA_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.VESPA_DEATH.get();
    }

    protected SoundEvent getFlyingSound() {
        return FURSoundRegistry.VESPA_FLYING.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (this.getLandTimer() > 10) {
            this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.7F;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.triggerAnim("trigger_controller", "fly_aggro");
            this.triggerAnim("trigger_controller", "attack");
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
           super(mob, 1.0D, false, 32);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return ATTACK_HIT;
    	}
    	
    	protected byte atkTimerEvent() {
    		return (byte) 4;
    	}
	}

    private <E extends GeoAnimatable> PlayState animPredicate(AnimationState<E> state) {
    	if (!this.onGround()) {
        	if (this.isAggressive()) {
        		state.getController().setAnimation(FLY_AGGRO);
        	} else {
        		state.getController().setAnimation(FLY);
        	}
        } else {
        	if (state.isMoving() || !this.getNavigation().isDone()) {
        		state.getController().setAnimation(WALK);
        	} else {
        		state.getController().setAnimation(IDLE);
        	}
        }
    	
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::animPredicate));
        controllers.add(new AnimationController<>(this, "trigger_controller", 5,
                state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("fly_aggro", FLY_AGGRO));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
