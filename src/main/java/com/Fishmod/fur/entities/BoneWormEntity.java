package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.projectiles.AcidJetEntity;
import com.Fishmod.fur.entities.projectiles.EnchantableFireBallEntity;
import com.Fishmod.fur.entities.projectiles.FlameJetEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
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
 * Boneworm (Osvermis) — a burrowing undead worm that spits Acid Jet (or, in the Soul Sand
 * Valley, fire-immune Flame Jet) globs from range, then dives underground to become briefly
 * untouchable while it repositions. Ported from 1.16.5.
 *
 * <p>The dig cycle is driven by {@link #getLocationFix()} (0.0-3.5ish, backed by synced data as of
 * the fixes below): increments while walking on solid ground, decrements otherwise. It gates the
 * {@link #isUnderGround()} flag + dig animation triggers (crossing 0 going down / 1.5 coming back
 * up), the actual hitbox height via {@link #getDimensions}/{@link #refreshDimensions()} (see the
 * 2026-08-12 entry below — 1.16.5's equivalent {@code EntitySize.scalable(...)} call discarded its
 * own return value and never did anything; this now really shrinks/regrows the collision box, the
 * same live mechanic 1.12.2's {@code Entity#setSize(...)} always had), and — client-side, in
 * {@link com.Fishmod.fur.client.renderer.entity.BoneWormRenderer} — the sink-and-spin-out-of-view
 * render trick 1.16.5 used in place of real geometry hiding.
 *
 * <p><b>Fixed vs. 1.16.5 (2026-08-12, pre-existing logic issues, not port artifacts):</b>
 * <ul>
 *   <li>{@link #hurt} used to reject <i>every</i> damage source once {@code locationFix > 3.0},
 *       including {@code /kill} and void damage — the same class of bug {@code SkeletonKingEntity}
 *       was fixed for (see its {@code hurt()} javadoc). Now bypassed via
 *       {@code DamageTypeTags.BYPASSES_INVULNERABILITY}, same pattern.</li>
 *   <li>1.16.5 also used two different thresholds for "invisible" (renderer: {@code < 1.5}) and
 *       "invulnerable" ({@code hurt()}: {@code > 3.0}), leaving a {@code [1.5, 3.0)} window where
 *       the worm was already fully hidden (nothing to render at all past 1.5) but still fully
 *       hittable. Unified under {@link #isHidden()}, used by both {@code hurt()} and the renderer.</li>
 *   <li><b>locationFix is now synced data</b> ({@code LOCATION_FIX}), computed server-side only.
 *       1.16.5 (and this port, originally) kept it a plain field that both logical sides computed
 *       independently off identical local inputs — cheap, but it meant a client that stops
 *       tracking this entity (walks out of range, or a chunk unload/reload) and re-tracks it later
 *       gets a freshly-constructed instance starting back at 0, with no way to recover the real
 *       value: a truly-submerged, invulnerable worm would pop back into (visible, hittable) view
 *       for anyone who looked away and back, even though the server never stopped treating it as
 *       hidden. A first attempt at fixing this by falling back to the already-synced
 *       {@link #isUnderGround()} flag in {@link #isHidden()} turned out to be wrong: that flag
 *       flips true the instant digging *starts* (locationFix just above 0), not once it's actually
 *       hidden (locationFix &ge; 1.5) — it would have made the worm invisible/invulnerable almost
 *       immediately after starting to walk, ~11 ticks too early. Syncing the real value outright
 *       avoids needing an approximation at all.</li>
 *   <li>{@link RangedAttackGoal} had no idea this entity could be hidden, so it kept telegraphing
 *       attacks (windup timer + entity event) on its normal cooldown even while fully submerged —
 *       harmless visually (nothing renders), but it silently burned the goal's ~2-3 s attack
 *       cadence on windups that could never land. {@link BoneWormRangedAttackGoal} now refuses to
 *       (continue to) run while {@link #isHidden()}, so the cooldown isn't wasted and the goal
 *       yields to wandering while burrowed.</li>
 *   <li><b>Found while fixing the above, same root cause, not one of the four originally asked
 *       for:</b> {@code tick()}'s spit-trigger check (and therefore all of {@link #spit}) ran
 *       completely unguarded on both logical sides — since {@code LivingEntity#tick()} runs
 *       client-side too, every shot was independently re-triggered by the client, which spawned
 *       its own local, server-unauthoritative {@code addFreshEntity} projectile (a visible ghost
 *       duplicate of the real server-spawned one) and played the launch sound a second time. The
 *       {@code SAND_BREAK} footstep-crunch sound in {@code aiStep()} had the same "runs on both
 *       sides" bug on a smaller scale (just a doubled sound, no ghost entity). Both are now guarded
 *       with {@code !level().isClientSide()}, the exact pattern already used across the rest of the
 *       mod's entities for this bug class (see the 2026-07-17 "review修正#4" edit-log entry).</li>
 *   <li><b>Hitbox now actually shrinks/regrows (2026-08-12, follow-up request, made 1.20.1 match
 *       1.12.2's always-live behaviour):</b> {@link #getDimensions} overridden to compute height
 *       from {@code locationFix} the same way 1.16.5's dead {@code EntitySize.scalable(...)} call
 *       tried to; {@link #setLocationFix} calls {@link #refreshDimensions()} to apply it
 *       immediately, and {@link #onSyncedDataUpdated} does the same on the client when the synced
 *       value arrives — the same two-hook pattern this codebase already uses for
 *       {@code CactoidEntity}/{@code CactyrantEntity}/{@code SalamanderEntity}'s growth-stage
 *       resizing (those only refresh eye height via {@code getStandingEyeHeight}, since none of
 *       them override {@code getDimensions} itself; this is the first FUR 1.20.1 entity with an
 *       actual dynamic hitbox).</li>
 * </ul>
 */
public class BoneWormEntity extends Monster implements GeoEntity, RangedAttackMob {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE          = RawAnimation.begin().thenLoop("boneworm.model.idle");
    private static final RawAnimation WALK          = RawAnimation.begin().thenLoop("boneworm.model.walk");
    private static final RawAnimation ATTACK_WINDUP = RawAnimation.begin().thenPlay("boneworm.model.attack_windup");
    private static final RawAnimation BITE          = RawAnimation.begin().thenPlay("boneworm.model.bite");
    private static final RawAnimation BURROW_DOWN   = RawAnimation.begin().thenPlay("boneworm.model.burrow_down");
    private static final RawAnimation BURROW_UP     = RawAnimation.begin().thenPlay("boneworm.model.burrow_up");

    private static final EntityDataAccessor<Boolean> UNDERGROUND  = SynchedEntityData.defineId(BoneWormEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SKIN_TYPE    = SynchedEntityData.defineId(BoneWormEntity.class, EntityDataSerializers.INT);
    /** 0.0-3.5ish: how deep the worm has burrowed. Server-authoritative (only ever written from
     *  {@link #aiStep()}'s {@code !isClientSide()} branch); synced so a freshly (re)tracked client
     *  starts from the real current value instead of 0. See the class doc. */
    private static final EntityDataAccessor<Float>   LOCATION_FIX = SynchedEntityData.defineId(BoneWormEntity.class, EntityDataSerializers.FLOAT);

    public final int[] attackTimer  = {0, 0};
    public final int[] diggingTimer = {0, 0};

    private RangedAttackGoal rangeAttackGoal;
    private AvoidEntityGoal<Player> avoidPlayerGoal;
    private int avoidCooldown;

    public BoneWormEntity(EntityType<? extends BoneWormEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.rangeAttackGoal = new BoneWormRangedAttackGoal(1.0D, 40, 60, 12.0F);
        this.avoidPlayerGoal = new AvoidEntityGoal<>(this, Player.class, 10.0F, 1.0D, 1.2D);

        this.goalSelector.addGoal(0, this.rangeAttackGoal);
        if (!FURConfig.SunScreen_Mode.get())
            this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
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
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    public static boolean checkBoneWormSpawnRules(EntityType<? extends BoneWormEntity> type,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, rand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(UNDERGROUND, false);
        this.entityData.define(SKIN_TYPE, 0);
        this.entityData.define(LOCATION_FIX, 0.0F);
    }

    private boolean isWalking() {
        return Math.abs(this.getX() - this.xo) > 0.05D || Math.abs(this.getY() - this.yo) > 0.05D || Math.abs(this.getZ() - this.zo) > 0.05D;
    }

    private boolean isDigging() {
        return this.diggingTimer[0] != 0 || this.diggingTimer[1] != 0;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        BlockPos belowPos = this.blockPosition().below();
        BlockState state = this.level().getBlockState(belowPos);
        boolean walkingOnSolid = this.isWalking() && state.isSolid();

        if (this.isWalking()) {
            if (state.isSolid() && this.level().isClientSide()) {
                this.spawnDigParticles(state, belowPos, 0.02D);
            }

            // Server-only: playSound() on the server already broadcasts to every tracking client,
            // so calling it here too (unguarded, as 1.16.5 did) doubled the crunch sound for
            // anyone in range of their own worm.
            if (!this.level().isClientSide() && this.tickCount % 10 == 0) {
                this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
            }
        }

        if (!this.level().isClientSide()) {
            float locationFix = this.getRawLocationFix();

            if (locationFix > 0 && !this.isUnderGround() && !this.isDigging()) {
                this.clearFire();
                this.diggingTimer[0] = 30;
                this.setUnderGround(true);
                this.level().broadcastEntityEvent(this, (byte) 6);
                this.playSound(FURSoundRegistry.BONEWORM_BURROW.get(), 0.25F, 1.0F);
            } else if (locationFix <= 1.5D && this.isUnderGround() && !this.isDigging()) {
                this.diggingTimer[1] = 20;
                this.setUnderGround(false);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.playSound(FURSoundRegistry.BONEWORM_BURROW.get(), 0.25F, 1.0F);
            }

            if (walkingOnSolid) {
                if (locationFix <= 3.5F)
                    this.setLocationFix(locationFix + 0.125F);
            } else if (locationFix > 0.0F && state.isSolid()) {
                this.setLocationFix(locationFix - 0.125F);
            }

            if (this.avoidCooldown == 0) {
                this.goalSelector.addGoal(0, this.rangeAttackGoal);
                this.goalSelector.removeGoal(this.avoidPlayerGoal);
                this.avoidCooldown = -1;
            }

            if (this.avoidCooldown > 0)
                this.avoidCooldown--;
        }

        // Client-only: same particle burst 1.16.5 fired from both the increment and decrement
        // branches (identical either way) - mirrored here off the now-synced locationFix rather
        // than a locally-computed one, so it can't disagree with what the server is doing.
        if (this.level().isClientSide() && state.isSolid() && (walkingOnSolid || this.getLocationFix() > 0.0D)) {
            this.spawnDigParticles(state, belowPos, 0.1D);
        }
    }

    /** Called only from client-guarded branches of {@link #aiStep()} — not itself annotated
     *  {@code @OnlyIn}, since {@link net.minecraft.world.level.Level#addParticle} is a plain
     *  both-sides method (matches every other particle call in this class). */
    private void spawnDigParticles(BlockState state, BlockPos belowPos, double yJitter) {
        for (int i = 0; i < 4; i++)
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(belowPos),
                    this.getX() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                    this.getY() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                    this.getZ() + (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                    this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * yJitter, this.getRandom().nextGaussian() * 0.02D);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 2; i++) {
            if (this.attackTimer[i] > 0) {
                --this.attackTimer[i];
            }

            if (this.diggingTimer[i] > 0) {
                --this.diggingTimer[i];
            }
        }

        // Server-only: spit() spawns a real projectile via addFreshEntity and plays its launch
        // sound - both already reach every tracking client on their own (the spawn packet, and the
        // server's own playSound broadcast). Running this unguarded (as 1.16.5 did) meant every
        // shot's launch also ran client-side, producing a second, purely-local, server-unauthoritative
        // "ghost" projectile alongside the real one, plus a doubled launch sound.
        if (!this.level().isClientSide() && this.getTarget() != null && this.getSensing().hasLineOfSight(this.getTarget())
                && this.attackTimer[0] == 7 && this.deathTime <= 0 && this.getLocationFix() == 0) {
            this.spit(this.getTarget());
        }

        if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
            this.setSecondsOnFire(8);
        }

        if (this.isWalking())
            this.clearFire();

        super.tick();
    }

    public double getLocationFix() {
        return this.getRawLocationFix();
    }

    private float getRawLocationFix() {
        return this.entityData.get(LOCATION_FIX);
    }

    private void setLocationFix(float value) {
        this.entityData.set(LOCATION_FIX, value);
        this.refreshDimensions();
    }

    /** Shrinks the hitbox height as the worm digs in, same 2.0 → 0.5 range 1.12.2's live
     *  {@code Entity#setSize(...)} call always drove; width is untouched. */
    @Override
    public EntityDimensions getDimensions(Pose pose) {
        float width = this.getType().getDimensions().width;
        float height = Math.max(2.0F - this.getRawLocationFix(), 0.5F);
        return EntityDimensions.scalable(width, height);
    }

    /** Mirrors {@link #setLocationFix}'s explicit {@link #refreshDimensions()} call onto the
     *  client, for whichever side receives the {@code LOCATION_FIX} update over the network
     *  instead of computing it locally — same two-hook pattern as {@code CactoidEntity}/
     *  {@code CactyrantEntity}/{@code SalamanderEntity}'s growth-stage resizing. */
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (LOCATION_FIX.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    /**
     * True once the worm is far enough into its dig that the renderer stops drawing it at all —
     * the single source of truth for both {@link #hurt} and {@link BoneWormRenderer}, so the two
     * can't drift onto different thresholds like 1.16.5's render (locationFix &lt; 1.5) and hurt
     * (locationFix &gt; 3.0) checks did.
     */
    public boolean isHidden() {
        return this.getRawLocationFix() >= 1.5F;
    }

    /** Untouchable while hidden, except for damage that's meant to bypass invulnerability
     *  (/kill, void, etc.) — mirrors {@code SkeletonKingEntity#hurt}'s fix for the same bug. */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isHidden() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    private void spit(LivingEntity target) {
        EnchantableFireBallEntity projectile;
        SoundEvent sound;

        if (this.getSkin() == 1) {
            projectile = new FlameJetEntity(FUREntityRegistry.FLAMEJET.get(), this, 0.0D, 0.0D, 0.0D, this.level());
            sound = SoundEvents.BLAZE_SHOOT;
        } else {
            projectile = new AcidJetEntity(FUREntityRegistry.ACIDJET.get(), this, 0.0D, 0.0D, 0.0D, this.level());
            sound = FURSoundRegistry.BONEWORM_ATTACK.get();
        }

        projectile.moveTo(this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ(), this.getYRot(), this.getXRot());

        double d0 = target.getY() + target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - projectile.getY();
        double d3 = target.getZ() - this.getZ();
        float f = Mth.sqrt((float) (d1 * d1 + d3 * d3)) * 0.2F;
        projectile.shoot(d1, d2 + (double) f, d3, 1.6F, 1.0F);
        this.playSound(sound, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(projectile);

        if (target instanceof Player)
            this.setRunning(100);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        this.attackTimer[0] = 15;
        this.level().broadcastEntityEvent(this, (byte) 4);
    }

    public void setRunning(int cooldown) {
        this.avoidPlayerGoal = new AvoidEntityGoal<>(this, Player.class, 10.0F, 1.0D, 1.2D);

        this.goalSelector.removeGoal(this.rangeAttackGoal);
        this.goalSelector.addGoal(1, this.avoidPlayerGoal);
        this.avoidCooldown = cooldown;
    }

    /** Kept for 1.16.5 fidelity: mirrors the bite-animation timer/event on a successful melee hit,
     *  even though no goal in {@link #registerGoals()} currently drives BoneWorm into melee range
     *  on its own (it prefers to flee via {@link #setRunning}) — same as 1.16.5. */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        if (flag) {
            this.attackTimer[1] = 16;
            this.level().broadcastEntityEvent(this, (byte) 5);
        }

        return flag;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, lootingLevel, recentlyHitIn);
        Entity entity = source.getEntity();

        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(this.getSkin() == 1 ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL);
            }
        }
    }

    public int getSkin() {
        return this.entityData.get(SKIN_TYPE);
    }

    public void setSkin(int skin) {
        this.entityData.set(SKIN_TYPE, skin);
    }

    public int getAttackTimer(int i) {
        return this.attackTimer[i];
    }

    public boolean isUnderGround() {
        return this.entityData.get(UNDERGROUND);
    }

    public void setUnderGround(boolean underGround) {
        this.entityData.set(UNDERGROUND, underGround);
    }

    @Override
    public boolean fireImmune() {
        return this.getSkin() == 1 || super.fireImmune();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackTimer[0] = 15;
            this.triggerAnim("trigger_controller", "attack_windup");
        } else if (id == 5) {
            this.attackTimer[1] = 16;
            this.triggerAnim("trigger_controller", "bite");
        } else if (id == 6) {
            this.diggingTimer[0] = 30;
            this.triggerAnim("trigger_controller", "burrow_down");
        } else if (id == 7) {
            this.diggingTimer[1] = 20;
            this.triggerAnim("trigger_controller", "burrow_up");
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.BoneWorm_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.BoneWorm_Attack.get());
        this.setHealth(this.getMaxHealth());

        if (world.getBiome(this.blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
            this.setSkin(1);
        }

        return super.finalizeSpawn(world, difficulty, spawnType, groupData, tag);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.BONEWORM_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.BONEWORM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BONEWORM_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    /** Vanilla {@link RangedAttackGoal} has no idea this entity can go {@link #isHidden()} —
     *  without this override it kept telegraphing (and wasting) attacks on its normal cooldown
     *  while fully submerged. See the class javadoc. */
    private class BoneWormRangedAttackGoal extends RangedAttackGoal {
        BoneWormRangedAttackGoal(double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
            super(BoneWormEntity.this, speedModifier, attackIntervalMin, attackIntervalMax, attackRadius);
        }

        @Override
        public boolean canUse() {
            return !BoneWormEntity.this.isHidden() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !BoneWormEntity.this.isHidden() && super.canContinueToUse();
        }
    }

    // -------------------------------------------------------------------------
    // GeckoLib
    // -------------------------------------------------------------------------

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        if ((state.isMoving() || !this.getNavigation().isDone()) && !this.isDigging()) {
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
                .triggerableAnim("attack_windup", ATTACK_WINDUP)
                .triggerableAnim("bite", BITE)
                .triggerableAnim("burrow_down", BURROW_DOWN)
                .triggerableAnim("burrow_up", BURROW_UP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
