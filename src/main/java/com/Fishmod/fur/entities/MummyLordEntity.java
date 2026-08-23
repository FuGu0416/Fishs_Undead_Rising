package com.Fishmod.fur.entities;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.ai.FURRangeAttackGoal;
import com.Fishmod.fur.entities.projectiles.LocustSwarmEntity;
import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
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

public class MummyLordEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE   		= RawAnimation.begin().thenPlay("mummy_lord.model.idle");
    private static final RawAnimation WALK   		= RawAnimation.begin().thenPlay("mummy_lord.model.walk");
    private static final RawAnimation ATTACK_MELEE 	= RawAnimation.begin().thenPlay("mummy_lord.model.attack_melee");
    private static final RawAnimation ATTACK_RANGE 	= RawAnimation.begin().thenPlay("mummy_lord.model.attack_range");
    private static final RawAnimation CAST   		= RawAnimation.begin().thenPlay("mummy_lord.model.cast");
    private static final RawAnimation SUMMON   		= RawAnimation.begin().thenPlay("mummy_lord.model.summon");

    public static final int ATTACK_TIMER = 25;
    public static final int SPELL_TIMER  = 40;
    /** The Scarab-summon ability (see {@link AIUseSummonSpell}) only becomes usable this many ticks after
     *  combat starts - originally a short (60-tick) "let melee happen first" grace period like
     *  {@link UndertakerEntity#OPENING_MELEE_GRACE_TICKS} (without it, the very first target lock instantly
     *  wins the goal-priority race against melee/ranged attacks and freezes this guard into its cast
     *  animation before it can deal any damage at all), now widened to a full 8 seconds specifically for
     *  the swarm-of-12-scarabs summon so it can't fire the instant a fight begins. */
    public static final int SUMMON_DELAY_TICKS = 8 * 20;

    /** How far this guard will wander/chase from {@link #homePos} - e.g. a structure-placed guard
     *  (Royal Tomb) shouldn't leave its post to wander off or chase a target across the map. */
    private static final int GUARD_RADIUS = 8;

    /** 0 = default, 1 = Royal Tomb variant (mummy_lord1.png, +50% max health). Only ever set via the
     *  {@code Variant} tag baked into the Royal Tomb structure's guard entity - never rolled at spawn,
     *  so this variant can't appear anywhere else. See {@link #setSkin}. */
    private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(MummyLordEntity.class, EntityDataSerializers.INT);

    protected int spellTicks;
    /** Anchor for {@link #restrictTo}; captured from wherever this entity first ticks (structure
     *  placement, spawner, summon, etc.) and persisted, since Mob's restriction fields themselves
     *  aren't saved to NBT - re-applied every tick so it survives chunk save/reload. */
    private BlockPos homePos;
    /** tickCount at which the current target was freshly acquired (no target -> a target); see
     *  {@link #setTarget} and {@link #SUMMON_DELAY_TICKS}. */
    private int combatStartTick = -1;

    public MummyLordEntity(EntityType<? extends MummyLordEntity> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    /** See {@link UndertakerEntity#setTarget} - same fix, same reasoning. */
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target != null && this.getTarget() == null) {
            this.combatStartTick = this.tickCount;
        }
        super.setTarget(target);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
    }

    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    /** Setting skin 1 also bumps max health +50% over the configured baseline (idempotent - safe to
     *  call on every NBT load, not just once). Current health isn't touched here; see
     *  {@link #readAdditionalSaveData} for why the caller re-applies it afterward. */
    public void setSkin(int skin) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skin));
        if (skin == 1) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.MummyLord_Health.get() * 1.5D);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingSpell());
        this.goalSelector.addGoal(2, new AIUseSummonSpell());
        this.goalSelector.addGoal(3, new FURRangeAttackGoal<LocustSwarmEntity>(this, 
        		FUREntityRegistry.LOCUST_SWARM.get(), 
        		FURSoundRegistry.AVATON_SPELL.get(), 1, 4, 0.0D, 8.0D, 1.2D, 0.6D, 1.2D).withWindup(20));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    public static boolean checkMummyLordSpawnRules(EntityType<? extends MummyLordEntity> type,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, rand);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public boolean isSpellcasting() {
        return this.spellTicks > 0;
    }

    public int getSpellTicks() {
        return this.spellTicks;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.spellTicks > 0) {
            --this.spellTicks;
        }

        if (this.homePos == null) {
            this.homePos = this.blockPosition();
        }
        this.restrictTo(this.homePos, GUARD_RADIUS);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.MummyLord_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.MummyLord_Attack.get());
        this.setHealth(this.getMaxHealth());
        return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.triggerAnim("trigger_controller", "attack_melee");
        } else if (id == 70) {
            this.triggerAnim("trigger_controller", "attack_range");
        } else if (id == 71) {
            this.triggerAnim("trigger_controller", "cast");
            this.spellTicks = SPELL_TIMER;
        } else if (id == 72) {
            this.triggerAnim("trigger_controller", "summon");
            this.spellTicks = SPELL_TIMER;            
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("SpellTicks", this.spellTicks);
        if (this.homePos != null) {
            nbt.put("HomePos", NbtUtils.writeBlockPos(this.homePos));
        }
        nbt.putInt("Variant", this.getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.spellTicks = nbt.getInt("SpellTicks");
        if (nbt.contains("HomePos")) {
            this.homePos = NbtUtils.readBlockPos(nbt.getCompound("HomePos"));
        }
        if (nbt.contains("Variant")) {
            this.setSkin(nbt.getInt("Variant"));
            // setSkin(1) raises max health *after* super.readAdditionalSaveData() already clamped
            // "Health" to the old (lower) cap, so the structure-baked guard's saved 180 HP would
            // otherwise get silently clamped down to 120. Re-apply the saved value now that the
            // cap is correct - a no-op for skin 0 and for any already-consistent reload.
            if (nbt.contains("Health")) {
                this.setHealth(nbt.getFloat("Health"));
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.MUMMY_LORD_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.MUMMY_LORD_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.MUMMY_LORD_DEATH.get();
    }

    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    public class AICastingSpell extends Goal {
        public AICastingSpell() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return MummyLordEntity.this.isSpellcasting();
        }

        public void start() {
            super.start();
            MummyLordEntity.this.getNavigation().stop();
        }

        public void tick() {
            if (MummyLordEntity.this.getTarget() != null) {
                MummyLordEntity.this.getLookControl().setLookAt(
                    MummyLordEntity.this.getTarget(),
                    (float) MummyLordEntity.this.getMaxHeadYRot(),
                    (float) MummyLordEntity.this.getMaxHeadXRot()
                );
            }
        }
    }

    public class AIUseSummonSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        // Keep the warmup in real ticks (1.18+ otherwise only ticks goals every other game
        // tick). The scarabs now rise at tick 20 of the 40-tick summon animation, vanilla
        // evoker style (warmup < casting time), instead of at its very end.
        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            if (MummyLordEntity.this.getTarget() == null)
                return false;
            if (MummyLordEntity.this.isSpellcasting() || !MummyLordEntity.this.hasLineOfSight(MummyLordEntity.this.getTarget()))
                return false;
            if (MummyLordEntity.this.tickCount - MummyLordEntity.this.combatStartTick < SUMMON_DELAY_TICKS)
                return false;
            int count = MummyLordEntity.this.level().getEntitiesOfClass(ScarabEntity.class,
                    MummyLordEntity.this.getBoundingBox().inflate(16.0D)).size();
            return MummyLordEntity.this.tickCount >= this.spellCooldown && count < FURConfig.MummyLord_Ability_Max.get();
        }

        public boolean canContinueToUse() {
            return MummyLordEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        public void start() {
            this.spellWarmup = 20;
            MummyLordEntity.this.spellTicks = SPELL_TIMER;
            this.spellCooldown = MummyLordEntity.this.tickCount + FURConfig.MummyLord_Ability_Cooldown.get() * 20;
            MummyLordEntity.this.level().broadcastEntityEvent(MummyLordEntity.this, (byte) 72);
            MummyLordEntity.this.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 1.0F, 1.0F);
        }

        public void tick() {
            --this.spellWarmup;
            if (this.spellWarmup == 0) {
                this.castSpell();
                MummyLordEntity.this.playSound(MummyLordEntity.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < FURConfig.MummyLord_Ability_Num.get(); ++i) {
                if (MummyLordEntity.this.level() instanceof ServerLevel server) {
                    BlockPos blockpos = MummyLordEntity.this.blockPosition().offset(
                        -6 + MummyLordEntity.this.getRandom().nextInt(12), 0,
                        -6 + MummyLordEntity.this.getRandom().nextInt(12)
                    );
                    ScarabEntity entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.SCARAB.get(), server, blockpos);
                    if (entity != null) {
                        entity.setOwnerUUID(MummyLordEntity.this.getUUID());

                        if (MummyLordEntity.this.getTarget() != null) {
                            entity.setTarget(MummyLordEntity.this.getTarget());
                        }
                    }
                }
            }
        }
    }

    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.2D, false, 32);
        }

        protected int atkTimerMax() {
            return ATTACK_TIMER;
        }

        protected int atkTimerHit() {
            return 5;
        }

        protected byte atkTimerEvent() {
            return (byte) 4;
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
                .triggerableAnim("attack_range", ATTACK_RANGE)
                .triggerableAnim("cast", CAST)
                .triggerableAnim("summon", SUMMON));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
