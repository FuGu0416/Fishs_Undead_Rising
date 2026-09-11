package com.Fishmod.fur.entities.flying;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

public class BeelzebubEntity extends RidableFlyingMobEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE            = RawAnimation.begin().thenLoop("beelzebub.model.idle");
    private static final RawAnimation WALK            = RawAnimation.begin().thenLoop("beelzebub.model.walk");
    private static final RawAnimation FLY             = RawAnimation.begin().thenLoop("beelzebub.model.fly");
    /** Played once on a normal (melee) attack landing. */
    private static final RawAnimation ATTACK          = RawAnimation.begin().thenPlay("beelzebub.model.attack_blend");
    /** Played once per grab attempt -- wild AIWildDevourGoal latching on (always a hit, the goal only
     *  starts once a target is already found), or a ridden MOUNT_SPECIAL grab (plays on a whiff too --
     *  see MessageMountSpecial). */
    private static final RawAnimation GRAB            = RawAnimation.begin().thenPlay("beelzebub.model.grab_blend");
    /** Looped while a wild (untamed) Beelzebub is holding grabbed prey. */
    private static final RawAnimation HOLD_LOOP_WILD  = RawAnimation.begin().thenLoop("beelzebub.model.hold_loop_wild_blend");
    /** Looped while a tamed/ridden Beelzebub is holding grabbed prey. */
    private static final RawAnimation HOLD_LOOP_TAMED = RawAnimation.begin().thenLoop("beelzebub.model.hold_loop_tamed_blend");
    /** Played once per ridden left-click bite (MessageBeelzebubBite). The wild AI-driven bite
     *  (AIWildDevourGoal) has no one-shot pulse of its own -- HOLD_LOOP_WILD alone carries it. */
    private static final RawAnimation ATTACK_HOLD     = RawAnimation.begin().thenPlay("beelzebub.model.attack_hold_blend");

    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(BeelzebubEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_HARVEST =
            SynchedEntityData.defineId(BeelzebubEntity.class, EntityDataSerializers.BOOLEAN);

    // Formerly FURConfig.Beelzebub_Ability_Cooldown_Mount -- fixed at its old default value on request
    // rather than left configurable.
    private static final int MOUNTED_BITE_COOLDOWN_SECONDS = 1; // between ridden left-click bites

    private int pheromoneTick;
    /** Cooldown after a wild low-HP devour finishes, ticked down every server tick regardless of goal state. */
    private int wildDevourCooldown;
    /** Server-authoritative cooldown between bites while a rider has a prey grabbed. */
    private int biteCooldown;
    /** Ticks left until a ridden MOUNT_SPECIAL grab attempt's catch is resolved -- grab_blend plays
     *  immediately on press, but whether anything was actually caught isn't decided until the snap
     *  closes partway through the clip. Set from MessageMountSpecial, resolved in tick(). */
    private int grabCheckTicks;

    public BeelzebubEntity(EntityType<? extends BeelzebubEntity> type, Level world) {
        super(type, world);
        this.pheromoneTick = 0;
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new AIWildDevourGoal());
        // Lower priority than AIWildDevourGoal: a badly wounded wild Beelzebub still prefers to latch
        // on and devour over a normal bite.
        this.goalSelector.addGoal(3, new AttackGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ParasiteEntity.class));
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
                // Was still the 1.16.5 value (0.08, carried straight over at port time). FlyingMoveHelper/
                // AIRandomFly's physics-based mover (FlyingMobEntity) scales desired velocity off this
                // attribute directly (0.4 * FLYING_SPEED); every other 1.20.1 flyer was already bumped into
                // this system's real working range (Vespa/Ptera 1.0, Enigmoth 0.67, Flarefly/Raven 0.6,
                // Void Glider 0.4) when the AI was rewritten -- Beelzebub was missed. At 0.08 its steady-state
                // speed sat under AIRandomFly's MIN_MOVE_SQ stuck-detection threshold, so it was flagged
                // stuck almost every tick and thrashed through escape()/repick instead of wandering normally.
                // Matched to Vespa (its closest sibling: same RidableFlyingMobEntity base, comparable size).
                .add(Attributes.FLYING_SPEED, 1.0D);
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
        // MOUNT_SPECIAL only toggles grabbing/releasing prey now -- this is a debounce between
        // toggles, not a real ability cooldown (the actual bite interval while riding is
        // biteCooldown, gated by MOUNTED_BITE_COOLDOWN_SECONDS, see isBiteReady()).
        // 2s (40 ticks): on top of abilityRequiresFreshPress() below, gives grab_blend's 1.5s clip
        // room to actually finish before the key can register another toggle.
        return 40;
    }

    @Override
    protected boolean abilityRequiresFreshPress() {
        // Grab/release is a manual toggle, not a hold-to-repeat ability -- see abilityCooldown()'s
        // comment. Holding the key with the default isDown() polling would re-fire every cooldown
        // tick, immediately releasing what was just grabbed before grab_blend (or anything else
        // about the grab) had a chance to actually be seen.
        return true;
    }

    private int canHarvestLimit() {
        return 300;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.wildDevourCooldown > 0) {
            this.wildDevourCooldown--;
        }
        if (this.biteCooldown > 0) {
            this.biteCooldown--;
        }
        if (this.grabCheckTicks > 0 && --this.grabCheckTicks == 0 && !this.level().isClientSide()) {
            this.resolveGrabAttempt();
        }

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

    /** The non-controlling passenger currently grabbed in Beelzebub's mouth, wild or ridden. */
    @Nullable
    public LivingEntity getGrabbedPrey() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger != this.getControllingPassenger() && passenger instanceof LivingEntity living) {
                return living;
            }
        }
        return null;
    }

    public boolean hasGrabbedPrey() {
        return this.getGrabbedPrey() != null;
    }

    /** Nearest grabbable creature in front of Beelzebub, used by the ridden MOUNT_SPECIAL grab. */
    @Nullable
    public LivingEntity findGrabTarget() {
        // While ridden, aim off the rider's own full look vector rather than this entity's -- travel()
        // only ever applies HALF the rider's pitch to Beelzebub's own rotation (getXRot() * 0.5F, so the
        // ridden model doesn't visually tilt as steeply as the camera), so this.getLookAngle() alone
        // would aim the detection box well short of wherever the rider is actually looking on any real
        // up/down angle -- likely why findGrabTarget() kept coming back null even with a target in the
        // rider's crosshair (found via debug logging, 2026-09-12 -- see that date's edit log).
        LivingEntity controller = this.getControllingPassenger();
        Vec3 look = controller != null ? controller.getLookAngle() : this.getLookAngle();
        Vec3 center = this.position().add(0.0D, this.getBbHeight() * 0.5D, 0.0D).add(look.scale(1.5D));
        AABB grabBox = AABB.ofSize(center, 2.0D, 2.0D, 2.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, grabBox, e -> isGrabbable(e, this));

        LivingEntity nearest = null;
        double bestDistSq = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates) {
            double distSq = this.distanceToSqr(candidate);
            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                nearest = candidate;
            }
        }
        return nearest;
    }

    /** Called from MessageMountSpecial right after grab_blend's entity-event broadcast: the animation
     *  plays immediately, but whether anything was actually caught isn't decided until the snap closes
     *  partway through the clip, resolved by resolveGrabAttempt() below. */
    public void startGrabCheck() {
        this.grabCheckTicks = 18; // 0.92s: when grab_blend's snap actually closes
    }

    /** Server-only, fired from tick() once grabCheckTicks elapses. Re-runs findGrabTarget() at that
     *  later moment (not whatever it found when the key was first pressed) so a target that has since
     *  moved out of range, died, or was never there in the first place is judged as of the snap, not
     *  the press. */
    private void resolveGrabAttempt() {
        if (!this.isAlive() || this.getGrabbedPrey() != null) {
            return;
        }
        LivingEntity target = this.findGrabTarget();
        if (target != null) {
            target.startRiding(this, true);
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
        }
    }

    public boolean isBiteReady() {
        return this.biteCooldown <= 0;
    }

    public void startBiteCooldown() {
        this.biteCooldown = MOUNTED_BITE_COOLDOWN_SECONDS * 20;
    }

    /** Shared grab/devour eligibility: alive, not a player, not another Beelzebub, small enough. */
    private static boolean isGrabbable(LivingEntity candidate, Entity beelzebub) {
        float maxSize = FURConfig.Beelzebub_Grab_Size.get().floatValue();
        return candidate != null
                && candidate.isAlive()
                && candidate != beelzebub
                && !(candidate instanceof Player)
                && !candidate.getType().equals(FUREntityRegistry.BEELZEBUB.get())
                && candidate.getBbWidth() <= maxSize
                && candidate.getBbHeight() <= maxSize;
    }

    // Position the grabbed prey at Beelzebub's mouth (in front, roughly head height) instead of the
    // normal on-the-back passenger seat used for the controlling rider.
    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        if (passenger == this.getGrabbedPrey()) {
            Vec3 look = this.getLookAngle();
            Vec3 mouthPos = this.position()
                    .add(0.0D, this.getBbHeight() * 0.5D - 0.5D, 0.0D)
                    .add(look.scale(this.getBbWidth() * 0.5D + 0.3D));
            moveFunction.accept(passenger, mouthPos.x, mouthPos.y, mouthPos.z);
            return;
        }
        super.positionRider(passenger, moveFunction);
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
                this.triggerAnim("trigger_controller", "attack");
                break;
            case 11:
                this.pheromoneTick = this.canHarvestLimit();
                break;
            case 12:
                this.triggerAnim("trigger_controller", "grab");
                break;
            case 13:
                this.triggerAnim("trigger_controller", "attack_hold");
                break;
            default:
                super.handleEntityEvent(id);
                break;
        }
    }

    /**
     * Normal melee attack (priority 3, below AIWildDevourGoal's 2). Windup/hit timing overridden to
     * match attack_blend's own 30-tick (1.5s) length -- the trigger fires at windup start (attackTimer
     * == atkTimerMax()) so the swing has time to visually carry through to the actual hit frame, same
     * convention VespaEntity.AttackGoal uses for its own attack_blend. FURMeleeAttackGoal.attackTimer
     * counts DOWN from atkTimerMax() to 0 (one tick per game tick), so elapsed clip time at the hit is
     * atkTimerMax() - atkTimerHit(), not atkTimerHit() itself -- hit lands at 0.96s (~19 ticks) into the
     * clip, so atkTimerHit() = 30 - 19 = 11.
     */
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
            super(mob, 1.0D, false);
        }

        protected int atkTimerMax() {
            return 30;
        }

        protected int atkTimerHit() {
            return 11;
        }

        protected byte atkTimerEvent() {
            return (byte) 10;
        }
    }

    /**
     * Wild-only: once badly wounded, chases down the nearest grabbable creature, latches onto it as a
     * passenger, and bites it once a second until it dies, healing a little on each bite. Takes over
     * from the normal target-selector behaviour (priority 2) while active, then sits out a 30s cooldown
     * before it can trigger again.
     */
    public class AIWildDevourGoal extends Goal {
        private static final int BITE_INTERVAL = 20;        // 1s between bites once grabbed
        private static final int DEVOUR_COOLDOWN = 30 * 20;  // 30s before this can trigger again
        private static final double GRAB_RANGE_SQ = 4.0D;    // ~2 blocks
        private static final int REPATH_INTERVAL = 10;

        private LivingEntity grabbed;
        private int biteTimer;
        private int repathTimer;

        public AIWildDevourGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        // Keep the bite/chase timing in real ticks (1.18+ otherwise only ticks goals every other
        // game tick, same reasoning FURMeleeAttackGoal.requiresUpdateEveryTick() documents).
        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public boolean canUse() {
            return !BeelzebubEntity.this.isTame()
                    && BeelzebubEntity.this.wildDevourCooldown <= 0
                    && BeelzebubEntity.this.getHealth() < BeelzebubEntity.this.getMaxHealth() * 0.5F
                    && this.findNearestGrabbable() != null;
        }

        public boolean canContinueToUse() {
            if (this.grabbed != null) {
                return this.grabbed.isAlive();
            }
            LivingEntity target = BeelzebubEntity.this.getTarget();
            return target != null && target.isAlive() && isGrabbable(target, BeelzebubEntity.this);
        }

        public void start() {
            LivingEntity target = this.findNearestGrabbable();
            BeelzebubEntity.this.setTarget(target);
            this.grabbed = null;
            this.biteTimer = 0;
            this.repathTimer = 0;
        }

        public void stop() {
            // Release cleanly if this goal got preempted mid-grab rather than ending by the prey's death
            // (which already detaches it on its own via the normal entity-removal/passenger cleanup).
            if (this.grabbed != null && this.grabbed.isAlive()) {
                this.grabbed.stopRiding();
            }
            this.grabbed = null;
            BeelzebubEntity.this.wildDevourCooldown = DEVOUR_COOLDOWN;
            BeelzebubEntity.this.setTarget(null);
        }

        public void tick() {
            if (this.grabbed == null) {
                LivingEntity target = BeelzebubEntity.this.getTarget();
                if (target == null) {
                    return;
                }

                BeelzebubEntity.this.getLookControl().setLookAt(target, 30.0F, 30.0F);

                if (BeelzebubEntity.this.distanceToSqr(target) <= GRAB_RANGE_SQ) {
                    this.grabbed = target;
                    target.startRiding(BeelzebubEntity.this, true);
                    BeelzebubEntity.this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                    BeelzebubEntity.this.level().broadcastEntityEvent(BeelzebubEntity.this, (byte) 12);
                    this.biteTimer = BITE_INTERVAL;
                } else if (--this.repathTimer <= 0) {
                    this.repathTimer = REPATH_INTERVAL;
                    BeelzebubEntity.this.getNavigation().moveTo(target, 1.2D);
                }
                return;
            }

            if (--this.biteTimer <= 0) {
                this.biteTimer = BITE_INTERVAL;
                float damage = (float) BeelzebubEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE);
                this.grabbed.hurt(BeelzebubEntity.this.damageSources().mobAttack(BeelzebubEntity.this), damage);
                BeelzebubEntity.this.heal(BeelzebubEntity.this.getMaxHealth() * 0.05F);
                BeelzebubEntity.this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            }
        }

        private LivingEntity findNearestGrabbable() {
            List<LivingEntity> candidates = BeelzebubEntity.this.level().getEntitiesOfClass(
                    LivingEntity.class, BeelzebubEntity.this.getBoundingBox().inflate(16.0D),
                    e -> isGrabbable(e, BeelzebubEntity.this));

            LivingEntity nearest = null;
            double bestDistSq = Double.MAX_VALUE;
            for (LivingEntity candidate : candidates) {
                double distSq = BeelzebubEntity.this.distanceToSqr(candidate);
                if (distSq < bestDistSq) {
                    bestDistSq = distSq;
                    nearest = candidate;
                }
            }
            return nearest;
        }
    }

    private <E extends GeoAnimatable> PlayState animPredicate(AnimationState<E> state) {
        if (!this.onGround()) {
            state.getController().setAnimation(FLY);
        } else if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

    // Layered over animPredicate's locomotion loop: holds a grabbed-prey pose (jaw clamped) for as
    // long as this Beelzebub has one, wild or ridden -- independent of idle/walk/fly underneath.
    private <E extends GeoAnimatable> PlayState holdPredicate(AnimationState<E> state) {
        if (!this.hasGrabbedPrey()) {
            return PlayState.STOP;
        }
        state.getController().setAnimation(this.isTame() ? HOLD_LOOP_TAMED : HOLD_LOOP_WILD);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::animPredicate));
        controllers.add(new AnimationController<>(this, "hold_controller", 5, this::holdPredicate));
        controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
                .triggerableAnim("attack", ATTACK)
                .triggerableAnim("grab", GRAB)
                .triggerableAnim("attack_hold", ATTACK_HOLD));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
