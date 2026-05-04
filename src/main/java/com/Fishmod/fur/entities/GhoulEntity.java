package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityAIPickupMeat;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GhoulEntity extends Monster implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("ghoul.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("ghoul.model.walk");
    private static final RawAnimation RUN = RawAnimation.begin().thenPlay("ghoul.model.run");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("ghoul.model.attack");
    
    private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(GhoulEntity.class, EntityDataSerializers.INT);
    public static final int ATTACK_TIMER = 15;

    public GhoulEntity(EntityType<? extends GhoulEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new AttackGoal(this));

        if (!FURConfig.SunScreen_Mode.get()) {
            this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        }

        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, true, (entity) -> entity.attackable() 
        		&& entity.getHealth() <= entity.getMaxHealth() * ((float) FURConfig.Ghoul_targetHPThreshold.get() / 100.0F)
        ));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.266D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 2.0D);
    }

    public static boolean checkGhoulSpawnRules(EntityType<? extends GhoulEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, 0);
    }

    @Override
    public double getMyRidingOffset() {
        return -0.25D;
    }

    @Override
    public void tick() {
        if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
            this.setSecondsOnFire(8);
        }

        super.tick();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        if (flag) {
            float difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

            if (this.getMainHandItem().isEmpty()
                    && this.isOnFire()
                    && this.random.nextFloat() < difficulty * 0.3F) {
                entity.setSecondsOnFire(2 * (int) difficulty);
            }

            if (entity instanceof LivingEntity living
                    && living.attackable()
                    && living.getHealth() <= living.getMaxHealth()
                    * ((float) FURConfig.Ghoul_targetHPThreshold.get() / 100.0F)) {

                this.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DAMAGE_BOOST, 4 * 20, 2));
            }

            if (entity instanceof LivingEntity living && living.deathTime <= 0) {
                this.heal(2.0F);
            }
        }

        return flag;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Ghoul_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ghoul_Attack.get());
        this.setHealth(this.getMaxHealth());
        
		if (reason == MobSpawnType.COMMAND || reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.SPAWNER || reason == MobSpawnType.DISPENSER) {
			this.setSkin(this.getRandom().nextInt(5));
		} else if (level.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_COLD_OVERWORLD)) {
    		// variant 2, 4
    		this.setSkin(2 + this.getRandom().nextInt(1) * 2);	
        } else if (level.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_DENSE_OVERWORLD)) {
    		// variant 1, 3
    		this.setSkin(1 + (this.getRandom().nextInt(1) * 2));
        } else {
    		// variant 0, 1
            this.setSkin(this.getRandom().nextInt(2));
        }

        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE);
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, skinType);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 5:
				this.triggerAnim("trigger_controller", "attack");
				break;		
			default:
				super.handleEntityEvent(id);
				break;
		}
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.8F;
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
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.GHOUL_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.GHOUL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.GHOUL_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.3D, true);
        }

        protected int atkTimerMax() {
            return ATTACK_TIMER;
        }

        protected int atkTimerHit() {
            return 6;
        }

        protected byte atkTimerEvent() {
            return (byte) 5;
        }
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (state.isMoving() && !this.isInWater()) {
            if (this.isAggressive()) {
            	state.getController().setAnimation(RUN);
            } else {
            	state.getController().setAnimation(WALK);
            }            
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attack", ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}