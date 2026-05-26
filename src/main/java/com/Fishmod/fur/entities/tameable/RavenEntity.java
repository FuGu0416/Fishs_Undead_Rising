package com.Fishmod.fur.entities.tameable;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
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
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RavenEntity extends FURTameableEntity implements FlyingAnimal, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.raven.idle");

    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(RavenEntity.class, EntityDataSerializers.INT);

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private boolean partyParrot;
    private BlockPos jukebox;
    private int ridingCooldown;
    public int callTimer;
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
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
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
        this.AITargetItem = new EntityAITargetItem<>(this, ItemEntity.class, true);
        this.targetSelector.addGoal(1, this.AITargetItem);
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
        super.aiStep();
        if (this.callTimer > 0) {
            this.callTimer--;
        }
        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 3.46D)
                || !this.level().getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.partyParrot = false;
            this.jukebox = null;
        }
        this.calculateFlapping();
    }

    @Override
    public boolean canPickUpLoot() {
        return super.canPickUpLoot() && this.getMainHandItem().isEmpty();
    }

    @Override
    public void tick() {
        if (this.isTame()) {
            if (this.ridingCooldown > 0) this.ridingCooldown--;

            if (this.isPassenger() && this.getVehicle() instanceof Player player) {
                this.setRot(this.getVehicle().getYRot(), 0F);

                if (FURConfig.Raven_Slowfall.get() && !this.getVehicle().onGround()
                        && this.getVehicle().getDeltaMovement().y < 0.0D
                        && !player.isFallFlying() && this.tickCount % 40 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 3 * 20, 0));
                }

                if (this.ridingCooldown == 0 && (this.getVehicle().isCrouching() || this.getVehicle().isInWater())) {
                    this.setDismount(this.getVehicle());
                }
            }

            if (!this.isInSittingPose() && !this.isPassenger() && this.getMainHandItem().isEmpty()
                    && this.tickCount % 200 == 0 && this.getRandom().nextFloat() < 0.02f) {
                ItemStack chosenDrop = this.pickLootDrop();
                if (chosenDrop == null) {
                    switch (this.getSkin()) {
                        case 1 -> chosenDrop = new ItemStack(Items.IRON_NUGGET, 1);
                        case 2 -> chosenDrop = new ItemStack(Items.TROPICAL_FISH, 1);
                        case 3 -> chosenDrop = new ItemStack(FURItemRegistry.ECTOPLASM.get(), 1);
                        default -> chosenDrop = new ItemStack(FURItemRegistry.FEATHER_BLACK.get(), 1);
                    }
                }
                int count = Math.max(1, this.getRandom().nextInt(chosenDrop.getCount()) + 1);
                this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(chosenDrop.getItem(), count));
            }
        }

        this.noPhysics = (this.getSkin() == 3
                && this.getY() > SpawnUtil.getHeight(this).getY() + 0.5D);
        super.tick();
        this.noPhysics = false;
    }

    private ItemStack pickLootDrop() {
        List<? extends String> lootList = switch (this.getSkin()) {
            case 3 -> FURConfig.Spectral_Raven_Loot.get();
            default -> FURConfig.Raven_Loot.get();
        };
        for (String entry : lootList) {
            String[] parts = entry.split(",");
            if (parts.length < 2) continue;
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parts[0].trim()));
            if (item == null || item == Items.AIR) continue;
            try {
                float prob = Float.parseFloat(parts[1].trim());
                if (this.getRandom().nextFloat() < prob) {
                    int count = (parts.length >= 3) ? Integer.parseInt(parts[2].trim()) : 1;
                    return new ItemStack(item, Math.max(1, count));
                }
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void setRecordPlayingNearby(BlockPos pos, boolean playing) {
        this.jukebox = pos;
        this.partyParrot = playing;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPartyParrot() {
        return this.partyParrot;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float) ((double) this.flapSpeed + (double) (!this.onGround() ? 4 : -1) * 0.3D);
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping = (float) ((double) this.flapping * 0.9D);
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < 0.0D) {
            this.setDeltaMovement(movement.x, movement.y * 0.6D, movement.z);
        }
        this.flap += this.flapping * 2.0F;
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
        }
    }

    @Override
    public void doFollowCommand(Player playerIn) {
        super.doFollowCommand(playerIn);
        if (!this.level().isClientSide) {
            this.setOrderedToSit(false);
        }
    }

    @Override
    public void doWanderCommand(Player playerIn) {
        super.doWanderCommand(playerIn);
        if (!this.level().isClientSide) {
            this.setOrderedToSit(false);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isOwnedBy(player) && hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide() && itemstack.isEmpty() && !this.getMainHandItem().isEmpty()) {
                player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                if (!player.getInventory().add(this.getMainHandItem().copy())) {
                    player.spawnAtLocation(this.getMainHandItem().copy());
                }
                this.getMainHandItem().shrink(this.getMainHandItem().getCount());
                return InteractionResult.CONSUME;
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
                this.setSkin(3);
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
        if (this.getSkin() == 2)
            return stack.is(ItemTags.FISHES);
        else
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
        return entity.hurt(this.damageSources().mobAttack(this), 3.0F);
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
        super.playAmbientSound();
        this.callTimer = 10;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    protected float playFlySound(float f) {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        return f + this.flapSpeed / 2.0F;
    }

    protected boolean makeFlySound() {
        return true;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public MobType getMobType() {
        return this.getSkin() == 3 ? MobType.UNDEAD : MobType.UNDEFINED;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || this.getSkin() == 3) {
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
        this.entityData.define(SKIN_TYPE, this.getRandom().nextFloat() < 0.1F ? 1 : 0);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Raven_Health.get());
        this.setHealth(this.getMaxHealth());

        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(FURItemRegistry.PARASITE_RAW.get()), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(FURItemRegistry.PARASITE_COOKED.get()), false));

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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getSkin());
        tag.putInt("MoreCropTicks", this.moreCropTicks);
    }

    @Override
    public boolean isFlying() {
        return (!this.onGround() && !this.isPassenger())
                || (this.getVehicle() != null && !this.getVehicle().onGround()
                        && this.getVehicle().getDeltaMovement().y < 0.0D);
    }

    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return super.getDefaultLootTable();
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

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, state -> {
            state.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
                this.wantsToRaid = true;
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
