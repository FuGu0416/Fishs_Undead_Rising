package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.ai.FURRangeAttackGoal;
import com.Fishmod.fur.entities.projectiles.SludgeJetEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

/**
 * Shroomlord — a hulking, plant-rotted undead. Wades through swamps spitting gravity-affected
 * {@link SludgeJetEntity} globs (Slowness + Blindness) from range, and stomps the ground for a
 * block-shattering AoE up close. Doubly hurt by fire, but sets melee victims alight while burning.
 *
 * <p>Ported from 1.16.5. Per request the desert (skin 1 / Sap Jet) variant and the Lil'Sludge
 * summon spell were intentionally dropped, so this is the swamp skin-0 monster only.
 */
public class ShroomLordEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE         = RawAnimation.begin().thenLoop("shroomlord.model.idle");
    private static final RawAnimation WALK         = RawAnimation.begin().thenLoop("shroomlord.model.walk");
    private static final RawAnimation ATTACK_MELEE = RawAnimation.begin().thenPlay("shroomlord.model.attack_melee");
    private static final RawAnimation ATTACK_RANGE = RawAnimation.begin().thenPlay("shroomlord.model.attack_range");

    public static final int ATTACK_TIMER = 30;

    public ShroomLordEntity(EntityType<? extends ShroomLordEntity> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FURRangeAttackGoal<SludgeJetEntity>(this,
                FUREntityRegistry.SLUDGEJET.get(),
                FURSoundRegistry.SHROOMLORD_ATTACK.get(), 1, 2, 0.0D, 8.0D, 1.2D, 0.6D, 1.2D));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        if (!FURConfig.SunScreen_Mode.get())
            this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Static defaults (must match the FURConfig defaults): config values cannot be read here,
        // since createAttributes() runs at registration before configs are loaded. The real
        // values are applied from config in finalizeSpawn().
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.19D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    public static boolean checkShroomLordSpawnRules(EntityType<? extends ShroomLordEntity> type,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, rand);
    }

    @Override
    public void tick() {
        super.tick();

        if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
            this.setSecondsOnFire(40);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        if (flag) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.getRandom().nextFloat() < f * 0.3F) {
                entity.setSecondsOnFire(2 * (int) f);
            }
        }

        return flag;
    }

    /** Fire deals double damage to the sludge-soaked flesh. */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE))
            return super.hurt(source, 2.0F * amount);
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.ShroomLord_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.ShroomLord_Attack.get());
        this.setHealth(this.getMaxHealth());
        return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.triggerAnim("trigger_controller", "attack_melee");
        } else if (id == 70) {
            this.triggerAnim("trigger_controller", "attack_range");
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 320;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SHROOMLORD_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.SHROOMLORD_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SHROOMLORD_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    /** Burning Shroomlords don't drop their (now-smelted) loot unless a player landed the kill. */
    @Override
    protected boolean shouldDropLoot() {
        return !this.isOnFire() || this.lastHurtByPlayer != null;
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    /**
     * Melee stomp: on the hit frame, the Shroomlord shatters the block in front of it (block-break
     * particles + sound) and damages every non-allied living thing within 1.5 blocks, rather than a
     * single target.
     */
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.0D, false);
        }

        @Override
        protected int atkTimerMax() {
            return ATTACK_TIMER;
        }

        @Override
        protected int atkTimerHit() {
            return 18;
        }

        @Override
        protected byte atkTimerEvent() {
            return (byte) 4;
        }

        @Override
        protected void dmgEvent(LivingEntity target) {
            Vec3 look = this.mob.getLookAngle().normalize();
            double d0 = this.mob.getX() + 2.5D * look.x;
            double d1 = this.mob.getY();
            double d2 = this.mob.getZ() + 2.5D * look.z;
            BlockPos below = BlockPos.containing(d0, d1, d2).below();
            BlockState state = this.mob.level().getBlockState(below);

            if (state.isSolid()) {
                this.mob.playSound(state.getSoundType(this.mob.level(), below, this.mob).getBreakSound(), 1.0F, 0.5F);

                if (this.mob.level() instanceof ServerLevel server) {
                    for (int i = 0; i < 64; i++) {
                        server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(below),
                                d0 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                d1 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                d2 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                15, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, 0.15D);
                    }
                }
            }

            this.mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            for (LivingEntity victim : this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(1.5D))) {
                if (!this.mob.equals(victim) && !this.mob.isAlliedTo(victim)
                        && !(victim instanceof TamableAnimal tamed && tamed.isOwnedBy(this.mob))) {
                    this.mob.doHurtTarget(victim);
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // GeckoLib
    // -------------------------------------------------------------------------

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        if ((state.isMoving() || !this.getNavigation().isDone()) && !this.isInWater()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
        controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
                .triggerableAnim("attack_melee", ATTACK_MELEE)
                .triggerableAnim("attack_range", ATTACK_RANGE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
