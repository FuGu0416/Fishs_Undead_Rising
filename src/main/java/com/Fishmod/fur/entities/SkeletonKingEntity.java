package com.Fishmod.fur.entities;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.projectiles.DeathCoilEntity;
import com.Fishmod.fur.entities.projectiles.SandBurstEntity;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
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
 * The Skeleton King — a summoned desert boss (Cursed King's Crown on a Skeleton Skull in a hot,
 * dry, sandy biome). Three spells: Sand Tomb (a field of SandBurst eruptions), Sand Step (teleport
 * to its target) and Death Coil (a fan of three coils once below half health), plus a
 * block-shattering AoE mace swing. Ported from the 1.16.5 module to 1.20.1 Mojmap + GeckoLib.
 */
public class SkeletonKingEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE         = RawAnimation.begin().thenPlay("skeletonking.model.idle");
    private static final RawAnimation WALK         = RawAnimation.begin().thenPlay("skeletonking.model.walk");
    private static final RawAnimation ATTACK_MELEE = RawAnimation.begin().thenPlay("skeletonking.model.attack_melee");
    private static final RawAnimation CAST         = RawAnimation.begin().thenPlay("skeletonking.model.cast");
    private static final RawAnimation SUMMON       = RawAnimation.begin().thenPlay("skeletonking.model.summon");

    private static final ResourceLocation SKELETON_KING_CHEST_LOOT = new ResourceLocation(mod_LavaCow.MODID, "entities/skeletonking");

    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false);
    private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(SkeletonKingEntity.class, EntityDataSerializers.INT);
    public static final int ATTACK_TIMER = 30;
    private int attackTimer;
    protected int spellTicks[] = {0, 0};

    public SkeletonKingEntity(EntityType<? extends SkeletonKingEntity> type, Level level) {
        super(type, level);
        this.xpReward = 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SkeletonKingEntity.DoNothingGoal());
        this.goalSelector.addGoal(1, new AICastingSpell());
        this.goalSelector.addGoal(2, new SkeletonKingEntity.AITeleportSpell());
        this.goalSelector.addGoal(3, new SkeletonKingEntity.AITossSpell());
        this.goalSelector.addGoal(4, new SkeletonKingEntity.AISummoningSpell());
        this.goalSelector.addGoal(5, new AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        // 1.16.5 also alerted the (un-ported) Sand Wraiths: `.setAlertOthers(ForsakenEntity.class)`
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MAX_HEALTH, 360.0D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_INV, 0);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public boolean isSpellcasting() {
        for (int i = 0; i < 2; i++) {
            if (this.spellTicks[i] > 0)
                return true;
        }

        return false;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.95D;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.attackTimer > 0) {
            --this.attackTimer;
            if (this.attackTimer > 20 && this.getTarget() != null)
                this.getLookControl().setLookAt(this.getTarget(), 10.0F, 10.0F);
            this.setDeltaMovement(Vec3.ZERO);

            // Server-side only — the broadcast reaches the client; playing on both sides
            // (the timer runs on both) doubles the sound.
            if (this.attackTimer == 20 && !this.level().isClientSide())
                this.playSound(FURSoundRegistry.SKELETONKING_ATTACK.get(), 1.0F, 1.0F);
        }

        if (this.getInvulnerableTicks() > 0) {
            for (int i1 = 0; i1 < 3; ++i1) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + this.random.nextGaussian(), this.getY() + (double) (this.random.nextFloat() * 3.3F), this.getZ() + this.random.nextGaussian(), 0.0D, this.random.nextDouble() * 0.5D, 0.0D);
            }
        }

        for (int i = 0; i < 2; i++) {
            if (this.spellTicks[i] > 0) {
                --this.spellTicks[i];

                if (this.level().isClientSide()) {
                    for (int j = 0; j < 4; ++j) {
                        this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), this.getY() + this.random.nextDouble() * (double) this.getBbHeight() - 0.25D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), 0.0D, this.random.nextDouble() * 0.5D, 0.0D);
                    }
                }
            }
        }
    }

    /** Pathfinding only — lets the AI walk off any cliff. Fall-damage immunity is causeFallDamage. */
    @Override
    public int getMaxFallDistance() {
        return 256;
    }

    /**
     * Actually immune to fall damage (the 1.16.5 getMaxFallDistance comment claimed this but that
     * method only affects pathfinding) — matters because Sand Step teleports him around terrain.
     */
    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.getInvulnerableTicks() > 0) {
            this.setInvulnerableTicks(this.getInvulnerableTicks() - 1);
            if (this.tickCount % 5 == 0) {
                this.heal(this.getMaxHealth() * 0.03F);
            }
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    public void setAttackTimer(int i) {
        this.attackTimer = i;
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 speedMultiplier) {
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isAlliedTo(entity)) {
            return true;
        // 1.16.5 also allied with the (un-ported) Sand Wraith: `|| entity instanceof ForsakenEntity`
        } else if (entity instanceof SkeletonKingEntity) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // BYPASSES_INVULNERABILITY covers both the void and /kill (GENERIC_KILL) — the 1.16.5
        // OUT_OF_WORLD check was split into two damage types in 1.20.1 (vanilla WitherBoss idiom).
        if (this.getInvulnerableTicks() > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }

        if (source.is(DamageTypes.IN_WALL)) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float) (3.0D + this.random.nextDouble() * 1.5D), true, Level.ExplosionInteraction.MOB);
        }

        return super.hurt(source, amount);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackTimer = ATTACK_TIMER;
            this.triggerAnim("trigger_controller", "attack_melee");
        } else if (id == 10) {
            this.spellTicks[0] = 30;
            this.triggerAnim("trigger_controller", "summon");
        } else if (id == 11) {
            this.spellTicks[1] = 15;
            this.triggerAnim("trigger_controller", "cast");
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.SkeletonKing_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.SkeletonKing_Attack.get());
        this.setHealth(this.getMaxHealth());

        return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.getInvulnerableTicks() > 0) {
            this.setDeltaMovement(Vec3.ZERO);
        } else
            super.travel(travelVector);
    }

    @Override
    public void push(Entity entity) {
        if (this.getInvulnerableTicks() <= 0) {
            super.push(entity);
        }
    }

    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_ID_INV);
    }

    public void setInvulnerableTicks(int ticks) {
        this.entityData.set(DATA_ID_INV, ticks);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.spellTicks[0] = nbt.getInt("SpellTicks0");
        this.spellTicks[1] = nbt.getInt("SpellTicks1");
        this.setInvulnerableTicks(nbt.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("SpellTicks0", this.spellTicks[0]);
        nbt.putInt("SpellTicks1", this.spellTicks[1]);
        nbt.putInt("Invul", this.getInvulnerableTicks());
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return FURConfig.SkeletonKing_Loot_Option.get() ? BuiltInLootTables.EMPTY : super.getDefaultLootTable();
    }

    /** With the chest option on, the loot is buried in a chest at the death spot instead of dropped. */
    @Override
    protected void dropAllDeathLoot(DamageSource source) {
        super.dropAllDeathLoot(source);

        if (FURConfig.SkeletonKing_Loot_Option.get() && this.level() instanceof ServerLevel server
                && server.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            // 1.16.5 used the surface heightmap (SpawnUtil.getHeight), which put the chest on top
            // of the terrain when the King died underground or indoors — reported as "the chest
            // never spawned". Place it at the actual death spot instead.
            BlockPos position = this.findChestPosition();
            boolean buried = false;

            if (position != null && server.setBlock(position, Blocks.CHEST.defaultBlockState(), 3)
                    && server.getBlockState(position).getBlock() instanceof ChestBlock) {
                RandomizableContainerBlockEntity.setLootTable(server, this.random, position, SKELETON_KING_CHEST_LOOT);
                buried = true;
            }

            if (!buried) {
                // No valid spot for the chest (died out of bounds, or placement failed) — drop the
                // loot as items rather than losing it (getDefaultLootTable returned EMPTY).
                LootParams params = new LootParams.Builder(server)
                        .withParameter(LootContextParams.ORIGIN, this.position())
                        .create(LootContextParamSets.CHEST);
                server.getServer().getLootData().getLootTable(SKELETON_KING_CHEST_LOOT)
                        .getRandomItems(params).forEach(this::spawnAtLocation);
            }
        }
    }

    /**
     * A replaceable spot for the loot chest at the death position: climbs out of solid blocks
     * (died buried by an explosion/falling blocks), then settles down onto the ground so the
     * chest doesn't float (died mid-air or in water). Null when the King died outside the world
     * bounds or no replaceable spot exists nearby.
     */
    @Nullable
    private BlockPos findChestPosition() {
        BlockPos pos = this.blockPosition();
        if (!this.level().isInWorldBounds(pos)) {
            return null;
        }

        int climbs = 0;
        while (!this.level().getBlockState(pos).canBeReplaced() && climbs++ < 8) {
            pos = pos.above();
        }
        if (!this.level().isInWorldBounds(pos) || !this.level().getBlockState(pos).canBeReplaced()) {
            return null;
        }

        while (pos.getY() > this.level().getMinBuildHeight() + 1 && this.level().getBlockState(pos.below()).canBeReplaced()) {
            pos = pos.below();
        }

        return pos;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SKELETONKING_DEATH.get();
    }

    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return SkeletonKingEntity.this.getInvulnerableTicks() > 0;
        }
    }

    public class AICastingSpell extends Goal {
        public AICastingSpell() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return SkeletonKingEntity.this.isSpellcasting();
        }

        public void start() {
            super.start();
            SkeletonKingEntity.this.getNavigation().stop();
        }

        public void tick() {
            if (SkeletonKingEntity.this.getTarget() != null) {
                SkeletonKingEntity.this.getLookControl().setLookAt(SkeletonKingEntity.this.getTarget(), (float) SkeletonKingEntity.this.getMaxHeadYRot(), (float) SkeletonKingEntity.this.getMaxHeadXRot());
            }
        }
    }

    /** Sand Tomb — carpets the arena (and the target's feet) with delayed SandBurst eruptions. */
    public class AISummoningSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        // Keep the goal-side warmup in real ticks so it stays aligned with the entity-side
        // spellTicks telegraph (1.18+ otherwise only ticks goals every other game tick).
        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            // The spell goals carry no Goal.Flags, so the invulnerable-phase DoNothingGoal can't
            // block them — gate them here so the King doesn't cast during his summoning warmup.
            if (SkeletonKingEntity.this.getInvulnerableTicks() > 0)
                return false;
            if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if ((SkeletonKingEntity.this.isSpellcasting() || !SkeletonKingEntity.this.hasLineOfSight(SkeletonKingEntity.this.getTarget())) && SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D)
                return false;

            return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SkeletonKingEntity.this.spellTicks[0] = this.getCastingTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SkeletonKingEntity.this.level().broadcastEntityEvent(SkeletonKingEntity.this, (byte) 10);
            if (soundevent != null) {
                SkeletonKingEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
            for (int i = 0; i < 11; ++i) {
                // Per-column local ground at the boss's altitude — the 1.16.5 surface heightmap
                // (SpawnUtil.getHeight) put every burst on top of the terrain when fighting
                // underground or indoors.
                BlockPos blockpos = SpawnUtil.getLocalGround(SkeletonKingEntity.this.level(),
                        SkeletonKingEntity.this.blockPosition().offset(-12 + SkeletonKingEntity.this.random.nextInt(24), 0, -12 + SkeletonKingEntity.this.random.nextInt(24)));

                SandBurstEntity sandburst = new SandBurstEntity(SkeletonKingEntity.this.level(), (double) blockpos.getX() + 0.5D, (double) blockpos.getY(), (double) blockpos.getZ() + 0.5D, 0.0F, 20, SkeletonKingEntity.this);
                SkeletonKingEntity.this.level().addFreshEntity(sandburst);
            }

            LivingEntity target = SkeletonKingEntity.this.getTarget();
            if (target != null) {
                SandBurstEntity sandburst = new SandBurstEntity(SkeletonKingEntity.this.level(), target.getX(), target.getY(), target.getZ(), 0.0F, 10, SkeletonKingEntity.this);
                SkeletonKingEntity.this.level().addFreshEntity(sandburst);
            }
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return FURConfig.SkeletonKing_AbilityA_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FURSoundRegistry.SKELETONKING_SPELL_SUMMON.get();
        }
    }

    /** Sand Step — blinks to its target; twice as often below half health. */
    public class AITeleportSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            if (SkeletonKingEntity.this.getInvulnerableTicks() > 0)
                return false;
            if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if (SkeletonKingEntity.this.isSpellcasting() || SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D || SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) < 4.0D)
                return false;

            return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
        }

        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
            LivingEntity target = SkeletonKingEntity.this.getTarget();
            if (target == null)
                return;

            if (SkeletonKingEntity.this.randomTeleport(target.getX(), target.getY(), target.getZ(), false)) {
                SkeletonKingEntity.this.playSound(this.getSpellPrepareSound(), 1.0F, 1.0F);
            } else {
                // No safe spot at the target (blocked by the 3-block-tall collision box) — don't
                // burn the full cooldown on a whiffed blink, retry in a second.
                this.spellCooldown = SkeletonKingEntity.this.tickCount + 20;
            }
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return FURConfig.SkeletonKing_AbilityB_Cooldown.get() * ((SkeletonKingEntity.this.getHealth() > SkeletonKingEntity.this.getMaxHealth() * 0.5F) ? 20 : 10);
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FURSoundRegistry.SKELETONKING_SPELL_TELEPORT.get();
        }
    }

    /** Death Coil — below half health, fans three Death Coils at its target. */
    public class AITossSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            if (SkeletonKingEntity.this.getInvulnerableTicks() > 0)
                return false;
            if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getHealth() > SkeletonKingEntity.this.getMaxHealth() * 0.5F || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if ((SkeletonKingEntity.this.isSpellcasting() || !SkeletonKingEntity.this.hasLineOfSight(SkeletonKingEntity.this.getTarget())) && SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D)
                return false;

            return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SkeletonKingEntity.this.spellTicks[1] = this.getCastingTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SkeletonKingEntity.this.level().broadcastEntityEvent(SkeletonKingEntity.this, (byte) 11);
            if (soundevent != null) {
                SkeletonKingEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
            if (SkeletonKingEntity.this.getTarget() == null)
                return;

            for (int i = -1; i < 2; i++) {
                DeathCoilEntity deathcoil = new DeathCoilEntity(SkeletonKingEntity.this.level(), SkeletonKingEntity.this, 0.0D, 0.0D, 0.0D);
                deathcoil.moveTo(SkeletonKingEntity.this.getX(), SkeletonKingEntity.this.getY() + (double) SkeletonKingEntity.this.getEyeHeight() - 0.1D, SkeletonKingEntity.this.getZ());
                deathcoil.shootFromRotation(SkeletonKingEntity.this, SkeletonKingEntity.this.getXRot(), SkeletonKingEntity.this.getYRot() + (i * 30.0F), 0.0F, 0.75F, 1.0F);
                SkeletonKingEntity.this.level().addFreshEntity(deathcoil);
            }
        }

        protected int getCastWarmupTime() {
            return 15;
        }

        protected int getCastingTime() {
            return 15;
        }

        protected int getCastingInterval() {
            // Seconds -> ticks, matching abilities A and B. The 1.16.5 original used the raw
            // config value (default 6 = six TICKS), which made the below-half-health Death Coil
            // an almost continuous spam — presumed upstream unit bug.
            return FURConfig.SkeletonKing_AbilityC_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FURSoundRegistry.SKELETONKING_SPELL_TOSS.get();
        }
    }

    /**
     * Mace swing: freezes the King in place for the windup (entity-level attackTimer), then
     * shatters the block in front of it and damages every non-allied living thing within 1 block.
     */
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.0D, false, 60);
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
        protected void checkAndPerformAttack(LivingEntity target, double distToTargetSqr) {
            boolean wasIdle = this.attackTimer == 0;
            super.checkAndPerformAttack(target, distToTargetSqr);
            // Mirror the goal-internal swing timer onto the entity so its tick() can freeze
            // movement and play the swing sound, like the 1.16.5 IAggressive contract did.
            if (wasIdle && this.attackTimer == this.atkTimerMax()) {
                ((SkeletonKingEntity) this.mob).setAttackTimer(ATTACK_TIMER);
            }
        }

        @Override
        public void stop() {
            super.stop();
            // Goal interrupted (target died/lost) — release the mirrored swing freeze so the
            // King doesn't stand locked (with the swing sound still pending) for up to 30 ticks.
            ((SkeletonKingEntity) this.mob).setAttackTimer(0);
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
                    for (int i = 0; i < 8; i++) {
                        server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(below),
                                d0 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                d1 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                d2 + (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - this.mob.getBbWidth(),
                                15, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, 0.15D);
                    }
                }
            }

            // The swing's reach (getAttackReachSqr, ~3x width) is longer than the 1-block AoE
            // below, so always hit the primary target the caller already validated in range —
            // otherwise the King whiffs whenever the target backs out of the AoE mid-windup.
            super.dmgEvent(target);

            for (LivingEntity victim : this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(1.0D))) {
                if (!this.mob.equals(victim) && !victim.equals(target) && !this.mob.isAlliedTo(victim)
                        && !(victim instanceof TamableAnimal tamed && tamed.isOwnedBy(this.mob))) {
                    super.dmgEvent(victim);
                }
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double) (this.mob.getBbWidth() * 3.0F * this.mob.getBbWidth() * 3.0F + attackTarget.getBbWidth());
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
                .triggerableAnim("cast", CAST)
                .triggerableAnim("summon", SUMMON));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
