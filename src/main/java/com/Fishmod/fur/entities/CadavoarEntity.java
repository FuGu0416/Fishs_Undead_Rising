package com.Fishmod.fur.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

/**
 * Ported from 1.16.5's UndeadSwineEntity, renamed to Cadavoar per the port request. A charging boar
 * that closes distance with {@link EntityChargeAttackGoal} (via {@link ICharging}) and tramples
 * anything (other than its own jockey) it collides with along the way, then finishes the target off
 * with a regular melee attack once adjacent. 1.16.5's hand-rolled {@code IAggressive}/{@code attackTimer}
 * (purely to drive its custom keyframe model's head swing) was dropped in favor of this codebase's
 * shared {@link FURMeleeAttackGoal} trigger-animation convention - no gameplay behaviour lost.
 */
public class CadavoarEntity extends Monster implements ICharging, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE   = RawAnimation.begin().thenLoop("cadavoar.model.idle");
    private static final RawAnimation WALK   = RawAnimation.begin().thenLoop("cadavoar.model.walk");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("cadavoar.model.attack");

    private static final List<Block> DIGOUT_SHROOM = List.of(
            FURBlockRegistry.GLOWSHROOM.get(),
            FURBlockRegistry.CORDY_SHROOM.get(),
            FURBlockRegistry.VEIL_SHROOM.get(),
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_MUSHROOM
    );

    private boolean isCharging = false;

    public CadavoarEntity(EntityType<? extends CadavoarEntity> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new AIChargeAttack(this));
        this.goalSelector.addGoal(4, new EatBlockGoal(this));

        if (!FURConfig.SunScreen_Mode.get()) {
            this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        }

        this.goalSelector.addGoal(5, new AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // Hardcoded defaults (matching FURConfig.Cadavoar_Health/Attack's own default values) rather than
    // reading the config directly - datagen (runData) invokes createAttributes() before config is
    // loaded and crashes on ConfigValue#get(), same fix already used by BeelzebubEntity/VespaEntity.
    // The config-driven value is applied afterwards in finalizeSpawn().
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    public static boolean checkCadavoarSpawnRules(EntityType<? extends CadavoarEntity> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    /**
     * While charging, tramples anything it collides with along the way (its own jockey excluded) -
     * see EntityChargeAttackGoal for the dedicated hit against the entity it's actually chasing.
     */
    @Override
    public void push(Entity entityIn) {
        super.push(entityIn);

        if (this.isCharging() && !this.hasPassenger(entityIn) && !this.isAlliedTo(entityIn) && this.doHurtTarget(entityIn)) {
            // Only knock back if the hit actually landed - push() has no cooldown of its own and fires every
            // tick two bounding boxes overlap, so without this the target's own hurt-resistance window (which
            // already prevents the damage itself from stacking) would still let the knockback reapply every
            // single tick regardless.
            if (entityIn instanceof LivingEntity living) {
                living.knockback(0.5D, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
            this.setSecondsOnFire(40);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                entityIn.setSecondsOnFire(2 * (int) f);
            }
        }

        return flag;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not
     * called when entity is reloaded from nbt. Mainly used for initializing attributes and inventory.
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Cadavoar_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Cadavoar_Attack.get());
        this.setHealth(this.getMaxHealth());

        if (this.getRandom().nextInt(100) == 0) {
            Skeleton rider = EntityType.SKELETON.create(this.level());
            if (rider != null) {
                rider.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                rider.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null, null);
                level.addFreshEntity(rider);
                rider.startRiding(this);
            }
        } else if (this.getRandom().nextInt(100) == 1) {
            Zombie rider = EntityType.ZOMBIE.create(this.level());
            if (rider != null) {
                rider.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                rider.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null, null);
                level.addFreshEntity(rider);
                rider.startRiding(this, true);
            }
        }

        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in EatBlockGoal)
     */
    @Override
    public void ate() {
        this.spawnAtLocation(DIGOUT_SHROOM.get(this.random.nextInt(DIGOUT_SHROOM.size())), 1);
    }

    @Override
    public boolean isCharging() {
        return this.isCharging;
    }

    @Override
    public void setIsCharging(boolean bool) {
        this.isCharging = bool;
    }

    /**
     * Get this Entity's MobType.
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return this.getBbHeight() * 0.6F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.CADAVOAR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.CADAVOAR_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOGLIN_STEP, 0.15F, 1.0F);
    }

    /**
     * Entity won't drop items or experience points if this returns false.
     */
    @Override
    protected boolean shouldDropLoot() {
        return !this.isOnFire() || this.lastHurtByPlayer != null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 5:
                this.triggerAnim("trigger_controller", "attack");
                break;
            default:
                super.handleEntityEvent(id);
                break;
        }
    }

    static class AIChargeAttack extends EntityChargeAttackGoal {
        public AIChargeAttack(PathfinderMob entityIn) {
            super(entityIn);
        }

        @Override
        public void start() {
            super.start();
            this.mob.playSound(FURSoundRegistry.CADAVOAR_CHARGE.get(), 1.0F, 1.0F);
        }
    }

    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.0D, true);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity target) {
            return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + target.getBbWidth());
        }

        @Override
        protected byte atkTimerEvent() {
            return (byte) 5;
        }
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        if (state.isMoving() || !this.getNavigation().isDone()) {
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
                .triggerableAnim("attack", ATTACK));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
