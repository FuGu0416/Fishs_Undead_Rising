package com.Fishmod.fur.entities.flying;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.entities.ParasiteEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
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

public class BeelzebubEntity extends RidableFlyingMobEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE      = RawAnimation.begin().thenLoop("beelzebub.model.idle");
    private static final RawAnimation WALK      = RawAnimation.begin().thenLoop("beelzebub.model.walk");
    private static final RawAnimation FLY       = RawAnimation.begin().thenLoop("beelzebub.model.fly");
    private static final RawAnimation FLY_AGGRO = RawAnimation.begin().thenLoop("beelzebub.model.fly_aggro");
    private static final RawAnimation ATTACK    = RawAnimation.begin().thenPlay("beelzebub.model.attack_blend");

    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(BeelzebubEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_HARVEST =
            SynchedEntityData.defineId(BeelzebubEntity.class, EntityDataSerializers.BOOLEAN);

    private int pheromoneTick;

    public BeelzebubEntity(EntityType<? extends BeelzebubEntity> type, Level world) {
        super(type, world);
        this.pheromoneTick = 0;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new AIUseSpell());

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ParasiteEntity.class));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ParasiteEntity.class, 0, true, true,
                e -> this.getHealth() < this.getMaxHealth() * 0.2F));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false,
                p -> !(p.isPassenger() && p.getVehicle() instanceof BeelzebubEntity)).setUnseenMemoryTicks(160));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, LivingEntity.class, false,
                e -> e.attackable() && e.getType().is(FUREntityTypeTagsProvider.BEELZEBUB_TARGETS)).setUnseenMemoryTicks(160));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.08D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FLYING_SPEED, 0.08D);
    }

    public static boolean checkBeelzebubSpawnRules(EntityType<? extends BeelzebubEntity> type,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return FlyingMobEntity.checkFlyerSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, 0);
        this.getEntityData().define(CAN_HARVEST, false);
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Beelzebub_Health.get() * 2.0D);
            this.setHealth(this.getHealth() * 2.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Beelzebub_Health.get());
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isOwnedBy(player) && itemstack.getItem() == Items.GLASS_BOTTLE && this.isAlive() && this.canHarvest() && !player.isCrouching()) {
            this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);

            if (!player.level().isClientSide) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                ItemStack catalyst = new ItemStack(FURItemRegistry.CHARMING_CATALYST.get());
                if (!player.getInventory().add(catalyst)) {
                    player.drop(catalyst, false);
                }
            }

            this.setcanHarvest(false);
            this.pheromoneTick = 0;

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.45F;
    }

    @Override
    public int abilityCooldown() {
        return FURConfig.Beelzebub_Ability_Cooldown_Mount.get() * 20;
    }

    private int canHarvestLimit() {
        return 300;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.onGround() && this.tickCount % 20 == 0 && !this.level().isClientSide()) {
            this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
        }

        if (this.isTame() && this.pheromoneTick < this.canHarvestLimit() && this.tickCount % 20 == 0 && this.getRandom().nextFloat() <= 0.25F) {
            this.pheromoneTick++;
        }

        if (this.pheromoneTick >= this.canHarvestLimit()) {
            this.level().broadcastEntityEvent(this, (byte) 11);

            if (!this.canHarvest()) {
                this.setcanHarvest(true);
                this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);

                for (int j = 0; j < 24; ++j) {
                    double d0 = this.getX() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth();
                    double d1 = this.getY() + (this.getRandom().nextFloat() * this.getBbHeight());
                    double d2 = this.getZ() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth();
                    this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        // When badly wounded, prioritise eating nearby Parasites to heal.
        if (this.getHealth() <= this.getMaxHealth() * 0.2F && this.getTarget() != null && !this.getTarget().getType().equals(FUREntityRegistry.PARASITE.get())) {
            List<ParasiteEntity> list = this.level().getEntitiesOfClass(ParasiteEntity.class, this.getBoundingBox().inflate(16.0D));
            if (!list.isEmpty()) {
                this.setTarget(list.get(0));
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
                    larva.setSkin(3);
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
        // Double damage against BEELZEBUB_TARGETS.
        if (target.getType().is(FUREntityTypeTagsProvider.BEELZEBUB_TARGETS)) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Beelzebub_Attack.get() * 2.0D);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Beelzebub_Attack.get());
        }

        if (super.doHurtTarget(target)) {
            if (target instanceof ParasiteEntity) {
                target.discard();
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.heal(this.getMaxHealth() * 0.05F);
            }

            if (target instanceof LivingEntity living) {
                int difficulty = (int) this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.addEffect(new MobEffectInstance(FUREffectRegistry.SOILED.get(), 6 * 20 * difficulty, 2));

                for (ParasiteEntity parasite : this.level().getEntitiesOfClass(ParasiteEntity.class, this.getBoundingBox().inflate(16.0D))) {
                    parasite.setTarget(living);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker
                && attacker.getType().is(FUREntityTypeTagsProvider.BEELZEBUB_TARGETS)) {
            return super.hurt(source, amount * 0.5F);
        }
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Beelzebub_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Beelzebub_Attack.get());
        this.setHealth(this.getMaxHealth());

        return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE);
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, skinType);
    }

    public boolean canHarvest() {
        return this.getEntityData().get(CAN_HARVEST);
    }

    public void setcanHarvest(boolean i) {
        this.getEntityData().set(CAN_HARVEST, i);
    }

    @Override
    protected double VehicleSpeedMod() {
        return (this.isInLava() || this.isInWater()) ? 0.2D : 1.8D;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
        this.setcanHarvest(compound.getBoolean("CanHarvest"));
        this.pheromoneTick = compound.getInt("PheromoneTick");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getSkin());
        compound.putBoolean("CanHarvest", this.canHarvest());
        compound.putInt("PheromoneTick", this.pheromoneTick);
    }

    public void castSpell(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            if (this.level() instanceof ServerLevel server) {
                BlockPos blockpos = this.blockPosition().offset(-2 + this.getRandom().nextInt(5), 1, -2 + this.getRandom().nextInt(5));
                ParasiteEntity entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.PARASITE.get(), server, blockpos);

                if (entity != null) {
                    entity.setSkin(3);
                    entity.setSummoned(true);
                    entity.setTarget(this.getTarget());

                    for (int j = 0; j < 4; ++j) {
                        double d0 = entity.getX() + (this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - entity.getBbWidth();
                        double d1 = entity.getY() + (this.getRandom().nextFloat() * entity.getBbHeight());
                        double d2 = entity.getZ() + (this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - entity.getBbWidth();
                        server.sendParticles(ParticleTypes.POOF, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (!this.isTame()) {
            this.castSpell(1 + this.getRandom().nextInt(1));
        }
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
        return FURSoundRegistry.BEELZEBUB_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.BEELZEBUB_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BEELZEBUB_DEATH.get();
    }

    public SoundEvent getSpellSound() {
        return FURSoundRegistry.BEELZEBUB_SPELL.get();
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
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 10:
                this.spellTicks = 30;
                this.triggerAnim("trigger_controller", "attack");
                break;
            case 11:
                this.pheromoneTick = this.canHarvestLimit();
                break;
            default:
                super.handleEntityEvent(id);
                break;
        }
    }

    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        // Keep the goal-side warmup in real ticks so it stays aligned with the entity-side
        // spellTicks telegraph (1.18+ otherwise only ticks goals every other game tick).
        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            if (BeelzebubEntity.this.getTarget() == null) {
                return false;
            } else if (BeelzebubEntity.this.isSpellcasting()
                    || BeelzebubEntity.this.getHealth() < BeelzebubEntity.this.getMaxHealth() * 0.4F
                    // "airborne, 4+ blocks above the ground below me" — the old surface-heightmap
                    // getHeight made this always fail underground (it never cast in caves).
                    || BeelzebubEntity.this.getY() < SpawnUtil.getLocalGround(BeelzebubEntity.this.level(), BeelzebubEntity.this.blockPosition()).getY() + 4.0D) {
                return false;
            } else {
                int i = BeelzebubEntity.this.level().getEntitiesOfClass(ParasiteEntity.class, BeelzebubEntity.this.getBoundingBox().inflate(16.0D)).size();
                return BeelzebubEntity.this.tickCount >= this.spellCooldown && i < FURConfig.Beelzebub_Ability_Max.get();
            }
        }

        public boolean canContinueToUse() {
            return BeelzebubEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            BeelzebubEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = BeelzebubEntity.this.tickCount + this.getCastingInterval();
            BeelzebubEntity.this.level().broadcastEntityEvent(BeelzebubEntity.this, (byte) 10);
        }

        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                BeelzebubEntity.this.castSpell(FURConfig.Beelzebub_Ability_Num.get());
                BeelzebubEntity.this.playSound(BeelzebubEntity.this.getSpellSound(), 0.175F, 1.0F);
            }
        }

        protected int getCastWarmupTime() {
            return 10;
        }

        protected int getCastingTime() {
            return 10;
        }

        protected int getCastingInterval() {
            return FURConfig.Beelzebub_Ability_Cooldown.get() * 20;
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
