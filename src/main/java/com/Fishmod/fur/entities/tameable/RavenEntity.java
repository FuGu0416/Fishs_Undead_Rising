package com.Fishmod.fur.entities.tameable;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ai.EntityAITargetItem;
import com.Fishmod.fur.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RavenEntity extends FURTameableEntity implements FlyingAnimal, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE   = RawAnimation.begin().thenLoop("raven.model.idle");
    private static final RawAnimation WALK   = RawAnimation.begin().thenLoop("raven.model.walk");
    private static final RawAnimation FLY    = RawAnimation.begin().thenLoop("raven.model.fly");
    private static final RawAnimation FLY_RIDING = RawAnimation.begin().thenLoop("raven.model.fly_riding");
    private static final RawAnimation SIT    = RawAnimation.begin().thenLoop("raven.model.sit");
    private static final RawAnimation JUKE   = RawAnimation.begin().thenLoop("raven.model.juke");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("raven.model.attack_blend");
    private static final RawAnimation CAW    = RawAnimation.begin().thenPlay("raven.model.caw_blend");

    private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(RavenEntity.class, EntityDataSerializers.INT);
    /** Synced so the client can play the juke (dance) animation; server-authoritative (see {@link #updateDanceState}). */
    private static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(RavenEntity.class, EntityDataSerializers.BOOLEAN);

    /** Gift loot tables (data/fur/loot_tables/gameplay/*) rolled for the items a tamed raven finds. */
    private static final ResourceLocation RAVEN_GIFT = new ResourceLocation(mod_LavaCow.MODID, "gameplay/raven");
    private static final ResourceLocation SPECTRAL_RAVEN_GIFT = new ResourceLocation(mod_LavaCow.MODID, "gameplay/spectral_raven");

    /** Drives the wing-flap SOUND cadence via {@link #isFlapping()}/{@link #onFlap()} (Parrot-style). */
    private float flapSpeed;
    private float nextFlap = 1.0F;
    /** Cached position of the nearby playing jukebox this raven is dancing to, or {@code null}. Server-side only. */
    private BlockPos jukebox;
    /** True once the owner has issued a follow/wander/sit command; a commanded tamed raven stops dancing. Persisted. */
    private boolean commanded;
    private int ridingCooldown;
    private int moreCropTicks;

    private EntityAITargetItem<ItemEntity> AITargetItem;

    public RavenEntity(EntityType<? extends RavenEntity> type, Level level) {
        super(type, level);
        this.ridingCooldown = 30;
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RavenEntity.DanceGoal());
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RavenEntity.RaidFarmGoal(this));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
        this.applyEntityAI();
    }

    @Override
    protected Goal wanderGoal() {
        return new WaterAvoidingRandomFlyingGoal(this, 1.0D);
    }

    @Override
    protected Goal followGoal() {
        return new FlyerFollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true, 24.0D);
    }

    protected void applyEntityAI() {
        this.AITargetItem = new RavenItemTargetGoal();
        this.targetSelector.addGoal(1, this.AITargetItem);
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ScarabEntity.class, true));

        Ingredient temptItems = Ingredient.of(FURItemRegistry.PARASITE_RAW.get(), FURItemRegistry.PARASITE_COOKED.get());
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, temptItems, false) {
            @Override
            public boolean canUse() {
                return !RavenEntity.this.isTame() && super.canUse() && this.player != null && this.player.isCrouching();
            }

            @Override
            public boolean canContinueToUse() {
                return !RavenEntity.this.isTame() && super.canContinueToUse() && this.player != null && this.player.isCrouching();
            }
        });

        // Skittish toward players: a wild raven flees a player that gets close, UNLESS the player is
        // sneaking (a sneaking player draws no flee reaction, and with a tempt item can lure it in via
        // the goal above). Tamed ravens and creative/spectator players are ignored.
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.0D, 1.4D,
                living -> !this.isTame()
                        && !living.isCrouching()
                        && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    public boolean removeWhenFarAway(double dist) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.6F;
    }

    private void setDismount(Entity ridden) {
        this.stopRiding();
        this.setPos(ridden.getX(), ridden.getY() + ridden.getBbHeight() / 2 - 0.35F, ridden.getZ());
        this.setJumping(false);
        this.getNavigation().stop();
        this.setTarget(null);
    }

    private boolean wantsMoreFood() {
        return this.moreCropTicks == 0;
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (this.moreCropTicks > 0) {
            this.moreCropTicks -= this.random.nextInt(3);
            if (this.moreCropTicks < 0) {
                this.moreCropTicks = 0;
            }
        }
    }

    @Override
    public void aiStep() {
        // Compute the dance state before super (which ticks the goals) so DanceGoal sees it this tick.
        if (!this.level().isClientSide()) {
            this.updateDanceState();
        }
        super.aiStep();
        this.updateFlapState();
    }

    /**
     * Server-side juke detection. A raven dances next to a jukebox that has a record. Vanilla only
     * notifies {@code Parrot.class} of nearby records, so we detect it ourselves and sync the flag.
     * Untamed ravens always dance near a playing jukebox; a tamed raven dances only until the owner
     * gives it a follow/wander/sit command ({@link #commanded}).
     */
    private void updateDanceState() {
        if (this.jukebox != null && !this.isPlayingJukebox(this.jukebox)) {
            this.jukebox = null;
        }
        // Re-scan only periodically when none is cached (validating a cached one is cheap).
        if (this.jukebox == null && this.tickCount % 20 == 0) {
            this.jukebox = this.findNearbyPlayingJukebox();
        }
        this.setDancing(this.jukebox != null && !(this.isTame() && this.commanded));
    }

    private boolean isPlayingJukebox(BlockPos pos) {
        // Use the block entity's actual playback state, not the HAS_RECORD blockstate: a disc left in
        // the jukebox keeps HAS_RECORD true after the song ends, which would keep the raven dancing.
        return this.level().getBlockState(pos).is(Blocks.JUKEBOX)
                && pos.closerToCenterThan(this.position(), 3.46D)
                && this.level().getBlockEntity(pos) instanceof JukeboxBlockEntity jukebox
                && jukebox.isRecordPlaying();
    }

    @Nullable
    private BlockPos findNearbyPlayingJukebox() {
        BlockPos origin = this.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-3, -3, -3), origin.offset(3, 3, 3))) {
            if (this.isPlayingJukebox(pos)) {
                return pos.immutable();
            }
        }
        return null;
    }

    public boolean isDancing() {
        return this.entityData.get(DANCING);
    }

    private void setDancing(boolean dancing) {
        this.entityData.set(DANCING, dancing);
    }

    @Override
    public boolean canPickUpLoot() {
        return super.canPickUpLoot() && this.getMainHandItem().isEmpty() && !this.isDancing();
    }

    @Override
    public void tick() {
        if (this.isTame()) {
            if (this.ridingCooldown > 0) this.ridingCooldown--;

            if (this.isPassenger() && this.getVehicle() instanceof Player player) {
                this.setRot(player.getYRot(), 0F);

                if (FURConfig.Raven_Slowfall.get() && !player.onGround()
                        && player.getDeltaMovement().y < 0.0D
                        && !player.isFallFlying() && this.tickCount % 40 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 3 * 20, 0));
                }

                if (this.ridingCooldown == 0 && (player.isCrouching() || player.isInWater())) {
                    this.setDismount(player);
                }
            }

            if (!this.isInSittingPose() && !this.isPassenger() && this.getMainHandItem().isEmpty()
                    && this.tickCount % 200 == 0 && this.getRandom().nextFloat() < 0.02f) {
                ItemStack gift = this.rollGift();
                if (!gift.isEmpty()) {
                    this.setItemInHand(InteractionHand.MAIN_HAND, gift);
                }
            }
        }

        this.noPhysics = this.shouldPhaseThroughBlocks();
        super.tick();
        this.noPhysics = false;
    }

    /**
     * A spectral raven (skin 2) phases through blocks while airborne but should still land and walk
     * on any solid surface — natural terrain OR an elevated floor/platform. It phases when there is
     * no walkable floor within ~1 block below its feet, OR when its body is currently embedded in
     * blocks (so it keeps going through a wall instead of getting stuck mid-descent). This is two
     * small AABB collision probes per tick, and only for skin-2 ravens, so the cost is negligible.
     */
    private boolean shouldPhaseThroughBlocks() {
        if (this.getSkin() != 2) {
            return false;
        }
        AABB box = this.getBoundingBox();
        AABB groundProbe = new AABB(box.minX, box.minY - 1.0D, box.minZ, box.maxX, box.minY, box.maxZ);
        boolean floorBelow = !this.level().noCollision(groundProbe);
        boolean bodyEmbedded = !this.level().noCollision(box);
        return !floorBelow || bodyEmbedded;
    }

    /**
     * Rolls the raven's "gift" loot table to pick which item it found. The table's weighted entries
     * decide the item and its built-in {@code set_count} functions decide the amount, so all of the
     * selection logic lives in the data pack (data/fur/loot_tables/gameplay/*) rather than in code.
     * Returns {@link ItemStack#EMPTY} on the client or if the table yields nothing.
     */
    private ItemStack rollGift() {
        if (!(this.level() instanceof ServerLevel server)) {
            return ItemStack.EMPTY;
        }
        ResourceLocation tableId = this.getSkin() == 2 ? SPECTRAL_RAVEN_GIFT : RAVEN_GIFT;
        LootTable table = server.getServer().getLootData().getLootTable(tableId);
        LootParams params = new LootParams.Builder(server)
                .withParameter(LootContextParams.ORIGIN, this.position())
                .withParameter(LootContextParams.THIS_ENTITY, this)
                .create(LootContextParamSets.GIFT);
        List<ItemStack> items = table.getRandomItems(params);
        return items.isEmpty() ? ItemStack.EMPTY : items.get(0);
    }

    /**
     * Advances the flap-sound cadence and slows the raven's descent so it glides down rather than
     * dropping like a stone (vanilla Parrot behaviour). Only {@code flapSpeed} is kept for the sound
     * timing in {@link #onFlap()}; the wing-flap animation itself is handled by GeckoLib.
     */
    private void updateFlapState() {
        this.flapSpeed += (!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);

        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < 0.0D) {
            this.setDeltaMovement(movement.x, movement.y * 0.6D, movement.z);
        }
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    protected boolean canSitCondition() {
        return !this.isFlying() && super.canSitCondition();
    }

    @Override
    public void doSitCommand(Player playerIn) {
        super.doSitCommand(playerIn);
        if (!this.level().isClientSide) {
            this.setOrderedToSit(true);
            this.onCommanded(playerIn);
        }
    }

    @Override
    public void doFollowCommand(Player playerIn) {
        super.doFollowCommand(playerIn);
        if (!this.level().isClientSide) {
            this.setOrderedToSit(false);
            this.onCommanded(playerIn);
        }
    }

    @Override
    public void doWanderCommand(Player playerIn) {
        super.doWanderCommand(playerIn);
        if (!this.level().isClientSide) {
            this.setOrderedToSit(false);
            this.onCommanded(playerIn);
        }
    }

    /**
     * Marks the raven as commanded so it stops dancing. Only a real player command (non-null player)
     * counts — programmatic state restores on load/dismiss pass {@code null} and must not flip it.
     */
    private void onCommanded(@Nullable Player playerIn) {
        if (playerIn != null) {
            this.commanded = true;
            this.jukebox = null;
            this.setDancing(false);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isOwnedBy(player) && hand == InteractionHand.MAIN_HAND) {
            if (itemstack.isEmpty() && !this.getMainHandItem().isEmpty()) {
                // Taking the item from the beak must win over perching, even while crouching. Select
                // this branch on both sides so the client doesn't fall through to the perch branch;
                // only perform the actual transfer on the server.
                if (!this.level().isClientSide()) {
                    player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                    if (!player.getInventory().add(this.getMainHandItem().copy())) {
                        player.spawnAtLocation(this.getMainHandItem().copy());
                    }
                    this.getMainHandItem().shrink(this.getMainHandItem().getCount());
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            } else if (FURConfig.Raven_Perch.get() && player.isShiftKeyDown() && player.getPassengers().isEmpty()) {
                this.startRiding(player);
                this.ridingCooldown = 20;
                return InteractionResult.SUCCESS;
            } else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.PARROT_EAT, SoundSource.NEUTRAL, 1.0F,
                            1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F);
                }
                this.heal(2.0F);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else if (itemstack.getItem() == FURItemRegistry.GHOST_JELLY.get() && this.getSkin() == 0) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.setSkin(2);
                this.playSound(SoundEvents.PARROT_EAT, 1.0F, 1.0F);
                this.playSound(SoundEvents.AMBIENT_CAVE.value(), 1.0F, 1.0F);
                for (int i = 0; i < 8; ++i) {
                    double d0 = this.getRandom().nextGaussian() * 0.02D;
                    double d1 = this.getRandom().nextGaussian() * 0.02D;
                    double d2 = this.getRandom().nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.SMOKE,
                            this.getX() + this.getRandom().nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                            this.getY() + this.getRandom().nextFloat() * this.getBbHeight(),
                            this.getZ() + this.getRandom().nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                            d0, d1, d2);
                }
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == FURItemRegistry.PARASITE_RAW.get()
                || stack.getItem() == FURItemRegistry.PARASITE_COOKED.get();
    }

    public static boolean checkRavenSpawnRules(EntityType<RavenEntity> type, ServerLevelAccessor level,
            MobSpawnType reason, BlockPos pos, RandomSource random) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(BlockTags.LEAVES) || below.is(Blocks.GRASS_BLOCK)
                || below.is(BlockTags.LOGS) || below.is(Blocks.AIR);
    }

    @Override
    public boolean causeFallDamage(float dist, float mult, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean canMate(Animal animal) {
        return false;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return null;
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof Player player)
            return player.getBbHeight() / 2 - 0.35F;
        return super.getMyRidingOffset();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.triggerAnim("trigger_controller", "attack");
        boolean hurt = entity.hurt(this.damageSources().mobAttack(this), 3.0F);

        if (hurt && entity instanceof Player player && this.getRandom().nextFloat() < 0.2F) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 10 * 20, 0));
        }
        return hurt;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return FURSoundRegistry.RAVEN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FURSoundRegistry.RAVEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.RAVEN_DEATH.get();
    }

    @Override
    public void playAmbientSound() {
        // No ambient call (sound + caw animation) while dancing to a jukebox.
        if (this.isDancing()) {
            return;
        }
        super.playAmbientSound();
        // The caw moves the beak/head, which clashes with an item carried in the beak — skip it then.
        if (this.getMainHandItem().isEmpty()) {
            this.triggerAnim("trigger_controller", "caw");
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public MobType getMobType() {
        return this.getSkin() == 2 ? MobType.UNDEAD : MobType.UNDEFINED;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || this.getSkin() == 2) {
            return false;
        }
        if (this.aiSit != null) {
            this.setInSittingPose(false);
        }
        if (!this.level().isClientSide() && !this.getMainHandItem().isEmpty()) {
            this.spawnAtLocation(this.getMainHandItem().copy(), 0.2f);
            this.getMainHandItem().shrink(100);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || this.isPassenger();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        // Fixed default; the natural-spawn variant is rolled in finalizeSpawn. A randomised default
        // here desyncs on relog: getNonDefaultValues() skips a value equal to the (random) default,
        // so the client keeps its own differing random roll and the skin appears to change.
        this.entityData.define(SKIN_TYPE, 0);
        this.entityData.define(DANCING, false);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Raven_Health.get());
        this.setHealth(this.getMaxHealth());

        // Roll the spawn variant here (not as the synched default) so it syncs/persists correctly.
        this.setSkin(this.getRandom().nextFloat() < 0.1F ? 1 : 0);

        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    public int getSkin() {
        return this.entityData.get(SKIN_TYPE);
    }

    public void setSkin(int skin) {
        this.entityData.set(SKIN_TYPE, skin);
    }

    @Override
    public void travel(Vec3 vec) {
        if (!this.isInSittingPose() || !this.onGround())
            super.travel(vec);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSkin(tag.getInt("Variant"));
        this.moreCropTicks = tag.getInt("MoreCropTicks");
        this.commanded = tag.getBoolean("Commanded");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getSkin());
        tag.putInt("MoreCropTicks", this.moreCropTicks);
        tag.putBoolean("Commanded", this.commanded);
    }

    @Override
    public boolean isFlying() {
        return (!this.onGround() && !this.isPassenger())
                || (this.getVehicle() != null && !this.getVehicle().onGround()
                        && this.getVehicle().getDeltaMovement().y < 0.0D);
    }

    @Override
    public void die(DamageSource cause) {
        if (!this.isTame() && !this.level().isDay()
                && this.getRandom().nextInt(100) < FURConfig.pScarecrow_PlagueDoctor.get()
                && this.level() instanceof ServerLevel serverLevel) {
            ScarecrowEntity scarecrow = SpawnUtil.trySpawnEntity(FUREntityRegistry.SCARECROW.get(), serverLevel, this.blockPosition());
            if (scarecrow != null) {
                scarecrow.setSkin(2);
                this.playSound(SoundEvents.AMBIENT_CAVE.value(), 1.0F, 1.0F);
                for (int i = 0; i < 8; i++) {
                    double d0 = this.getRandom().nextGaussian() * 0.02D;
                    double d1 = this.getRandom().nextGaussian() * 0.02D;
                    double d2 = this.getRandom().nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.SMOKE,
                            this.getX() + this.getRandom().nextDouble(),
                            this.getY() + this.getRandom().nextDouble(),
                            this.getZ() + this.getRandom().nextDouble(),
                            d0, d1, d2);
                }
                scarecrow.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 8 * 20, 2));
                scarecrow.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3 * 20, 1));
                Entity attacker = cause.getEntity();
                if (attacker instanceof LivingEntity le
                        && !(attacker instanceof Player p && p.isCreative())) {
                    scarecrow.setTarget(le);
                }
            }
        }
        super.die(cause);
    }

    // --- GeoEntity ---

    private <E extends GeoAnimatable> PlayState animPredicate(AnimationState<E> state) {
        if (this.isDancing()) {
            state.getController().setAnimation(JUKE);
        } else if (this.isInSittingPose()) {
            state.getController().setAnimation(SIT);
        } else if (this.isFlying()) {
            // isFlying() is only true while a passenger when the ridden player is falling
            // (see isFlying()), so use the riding flap variant in that case.
            state.getController().setAnimation(this.isPassenger() ? FLY_RIDING : FLY);
        } else if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::animPredicate));
        controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
                .triggerableAnim("attack", ATTACK)
                .triggerableAnim("caw", CAW));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    /**
     * Item-fetching goal. Inherits the targeting logic from {@link EntityAITargetItem} but adds two
     * raven behaviours: it never fires while dancing, and when the target item is within 4 blocks and
     * the raven is standing on the ground it walks straight to it (driving the move control at ground
     * level) instead of taking off to fly.
     */
    class RavenItemTargetGoal extends EntityAITargetItem<ItemEntity> {
        RavenItemTargetGoal() {
            super(RavenEntity.this, ItemEntity.class, true);
        }

        /** True when we should walk to the item rather than fly: on the ground and within 4 blocks. */
        private boolean shouldWalkToItem() {
            return RavenEntity.this.onGround()
                    && this.targetEntity != null
                    && this.targetEntity.isAlive()
                    && RavenEntity.this.getMainHandItem().isEmpty()
                    && RavenEntity.this.distanceToSqr(this.targetEntity) <= 16.0D;
        }

        @Override
        public boolean canUse() {
            return !RavenEntity.this.isDancing() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (RavenEntity.this.isDancing()
                    || this.targetEntity == null
                    || !this.targetEntity.isAlive()
                    || !RavenEntity.this.getMainHandItem().isEmpty()) {
                return false;
            }
            // While walking we stop the navigation, so don't require an active path to keep going.
            return this.shouldWalkToItem() || !RavenEntity.this.getNavigation().isDone();
        }

        @Override
        public void tick() {
            if (this.shouldWalkToItem()) {
                RavenEntity.this.getLookControl().setLookAt(this.targetEntity, 30.0F, 30.0F);
                RavenEntity.this.getNavigation().stop();
                RavenEntity.this.getMoveControl().setWantedPosition(
                        this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getZ(), 1.0D);
            } else {
                super.tick();
            }
        }
    }

    /**
     * Holds the MOVE flag while the raven is dancing so no movement goal can run, and keeps the
     * navigation stopped — the raven stays put (no movement) for as long as it dances.
     */
    class DanceGoal extends Goal {
        DanceGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return RavenEntity.this.isDancing();
        }

        @Override
        public boolean canContinueToUse() {
            return RavenEntity.this.isDancing();
        }

        @Override
        public void start() {
            RavenEntity.this.getNavigation().stop();
        }

        @Override
        public void tick() {
            RavenEntity.this.getNavigation().stop();
        }
    }

    static class RaidFarmGoal extends MoveToBlockGoal {
        private final RavenEntity entity;
        private boolean wantsToRaid;
        private boolean canRaid;

        public RaidFarmGoal(RavenEntity raven) {
            super(raven, 0.7F, 16);
            this.entity = raven;
        }

        @Override
        public boolean canUse() {
            if (this.nextStartTick <= 0) {
                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entity.level(), this.entity)) {
                    return false;
                }
                this.canRaid = false;
                this.wantsToRaid = this.entity.wantsMoreFood();
            }
            if (this.entity.isTame()) return false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canRaid && super.canContinueToUse();
        }

        @Override
        public void tick() {
            super.tick();
            this.entity.getLookControl().setLookAt(
                    this.blockPos.getX() + 0.5D, this.blockPos.getY() + 1,
                    this.blockPos.getZ() + 0.5D, 10.0F, (float) this.entity.getMaxHeadXRot());
            if (this.isReachedTarget()) {
                Level world = this.entity.level();
                BlockPos above = this.blockPos.above();
                BlockState state = world.getBlockState(above);
                Block block = state.getBlock();
                if (this.canRaid && block == Blocks.WHEAT) {
                    int age = state.getValue(CropBlock.AGE);
                    if (age == 0) {
                        world.setBlock(above, Blocks.AIR.defaultBlockState(), 2);
                        world.destroyBlock(above, true, this.entity);
                    } else {
                        world.setBlock(above, state.setValue(CropBlock.AGE, age - 1), 2);
                        world.levelEvent(2001, above, 0);
                    }
                    this.entity.moreCropTicks = 40;
                }
                this.canRaid = false;
                this.nextStartTick = 10;
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader world, BlockPos pos) {
            Block block = world.getBlockState(pos).getBlock();
            if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
                BlockPos above = pos.above();
                BlockState state = world.getBlockState(above);
                block = state.getBlock();
                if (block == Blocks.WHEAT && ((CropBlock) block).isMaxAge(state)) {
                    this.canRaid = true;
                    return true;
                }
            }
            return false;
        }
    }
}
