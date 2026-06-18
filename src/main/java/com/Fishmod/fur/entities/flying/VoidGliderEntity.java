package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;

import com.mojang.datafixers.util.Pair;
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
 * Void Glider — a passive, drifting flyer ported from MC 1.16.5.
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>End-only: the Overworld and Nether spawn branches (and the Soul-Sand-Valley skin)
 *       were removed; it now spawns only in the End (gated by the biome modifier).</li>
 *   <li>Attracted to a player holding Enigmoth Dust (謎影蛾鱗粉) via a {@link TemptGoal}.</li>
 *   <li>Immune to the Void Dust (虛空鱗粉) effect — see {@link #canBeAffected}.</li>
 *   <li>Rendered through GeckoLib instead of a hand-coded model.</li>
 * </ul>
 */
public class VoidGliderEntity extends FlyingMobEntity implements GeoEntity {
	public static final float[] SIZE = {1.0F, 1.4F, 1.8F, 2.2F};
	/** Texture index. End-only now, so this is fixed to the End skin (2) at spawn, but kept for save compatibility. */
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(VoidGliderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SIZE_VARIANT = SynchedEntityData.defineId(VoidGliderEntity.class, EntityDataSerializers.INT);

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("void_glider.idle");

	/** Cached End City the glider auto-flies toward while ridden (transient — recomputed each mount). */
	@Nullable
	private BlockPos endCityDest;
	private int destSearchCooldown;

	// Autopilot tuning
	private static final double CRUISE_SPEED = 0.35D;          // ~7 blocks/s horizontal
	private static final double CLIMB_SPEED = 0.2D;            // upward speed when climbing over terrain
	private static final double MIN_GROUND_CLEARANCE = 12.0D;  // climb if closer than this to the terrain below
	private static final double MAX_GROUND_CLEARANCE = 32.0D;  // ease down if higher than this above terrain
	private static final int HOME_IN_RANGE = 24;              // within this horizontal distance, settle to the city's height
	private static final int GROUND_SCAN = 48;                 // how far down to look for terrain when holding altitude

	public VoidGliderEntity(EntityType<? extends VoidGliderEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this, 1.0D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.03D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.03D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(2));
		this.getEntityData().define(SIZE_VARIANT, Integer.valueOf(this.getRandom().nextInt(VoidGliderEntity.SIZE.length)));
	}

	public static boolean checkVoidGliderSpawnRules(EntityType<? extends VoidGliderEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource rand) {
		return FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(type, world, reason, pos, rand);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void tick() {
		super.tick();
		this.setNoGravity(true);
	}

	public float getScale() {
		return VoidGliderEntity.SIZE[this.getSize()];
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.7F;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance effect) {
		return effect.getEffect() != FUREffectRegistry.VOID_DUST.get() && super.canBeAffected(effect);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.VoidGlider_Health.get());
		this.setHealth(this.getMaxHealth());
		this.setSkin(2); // End variant only.

		return super.finalizeSpawn(worldIn, difficulty, reason, entityLivingData, tag);
	}

	public int getSkin() {
		return this.getEntityData().get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
		this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
	}

	public int getSize() {
		return this.getEntityData().get(SIZE_VARIANT).intValue();
	}

	public void setSize(int size) {
		this.getEntityData().set(SIZE_VARIANT, Integer.valueOf(size));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSkin(compound.getInt("Variant"));
		this.setSize(compound.getInt("Size"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", getSkin());
		compound.putInt("Size", this.getSize());
	}

	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.NEUTRAL;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.VOID_GLIDER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.VOID_GLIDER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.VOID_GLIDER_DEATH.get();
	}

	@Override
	protected float getSoundVolume() {
		return 0.5F;
	}

	// ── Riding & End-City autopilot ─────────────────────────────────────────────

	/** Right-click to climb aboard; the glider then auto-flies to the nearest End City (the rider can't steer). */
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (!player.isSecondaryUseActive() && !this.isVehicle() && !player.isPassenger()) {
			if (!this.level().isClientSide) {
				player.startRiding(this);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return super.mobInteract(player, hand);
	}

	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		if (!this.level().isClientSide && passenger instanceof Player) {
			this.endCityDest = this.findNearestEndCity();
			this.destSearchCooldown = 200;
		}
	}

	/** Leaving the glider in mid-air grants 20s of Slow Falling so the rider drifts down safely. */
	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
		if (!this.level().isClientSide && passenger instanceof Player player && !player.onGround()) {
			player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 20, 0));
		}
	}

	/** Only seize control from the flight AI while actually autopiloting to an End City. */
	@Override
	protected boolean suspendAiMovement() {
		return this.isVehicle() && this.getFirstPassenger() instanceof Player && this.endCityDest != null;
	}

	@Override
	public void travel(Vec3 travelVector) {
		// While carrying a player the rider has no control. If there is an End City to head for the
		// glider drives straight to it; otherwise it falls back to its normal random-fly wander.
		if (!this.level().isClientSide && this.isVehicle() && this.getFirstPassenger() instanceof Player) {
			this.updateEndCityDest();
			if (this.endCityDest != null) {
				this.setNoGravity(true);
				this.autoPilotToEndCity();
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.calculateEntityAnimation(false);
				return;
			}
			// No End City within range / not in the End → keep doing the normal random-fly behaviour.
		}
		super.travel(travelVector);
	}

	/** Lazily (re)acquire a target End City, retrying the costly structure scan at most every 10s. */
	private void updateEndCityDest() {
		if (this.endCityDest != null) {
			return;
		}
		if (this.destSearchCooldown > 0) {
			this.destSearchCooldown--;
			return;
		}
		this.destSearchCooldown = 200;
		this.endCityDest = this.findNearestEndCity();
	}

	/** Steer deltaMovement toward {@link #endCityDest} (non-null here); cruise high, clear islands, hover on arrival. */
	private void autoPilotToEndCity() {
		Vec3 self = this.position();
		Vec3 dest = Vec3.atCenterOf(this.endCityDest);
		double dx = dest.x - self.x;
		double dz = dest.z - self.z;
		double horiz = Math.sqrt(dx * dx + dz * dz);

		if (horiz < 4.0D) {
			// Arrived above the End City — hover so the rider can hop off.
			this.setDeltaMovement(this.getDeltaMovement().scale(0.7D));
			return;
		}

		double inv = 1.0D / horiz;
		double vx = dx * inv * CRUISE_SPEED;
		double vz = dz * inv * CRUISE_SPEED;

		double vy;
		if (horiz < HOME_IN_RANGE) {
			// Closing in: settle toward the city's actual height for the rider to dismount.
			vy = Mth.clamp((dest.y - self.y) * 0.1D, -0.15D, CLIMB_SPEED);
		} else if (this.isBlockedAhead(vx, vz)) {
			// An island is dead ahead — climb over it instead of ploughing in.
			vy = CLIMB_SPEED;
		} else {
			// Cruising: hold a high, collision-safe altitude above whatever terrain is below.
			vy = this.cruiseVerticalAdjust(self);
		}

		this.setDeltaMovement(vx, vy, vz);

		float yaw = (float) (Mth.atan2(vz, vx) * (180D / Math.PI)) - 90.0F;
		this.setYRot(yaw);
		this.yRotO = yaw;
		this.yBodyRot = yaw;
		this.yHeadRot = yaw;
		this.getLookControl().setLookAt(dest.x, dest.y, dest.z);
	}

	/** True if solid blocks sit just ahead along the travel direction (2–6 blocks out, at body height and above). */
	private boolean isBlockedAhead(double vx, double vz) {
		double len = Math.sqrt(vx * vx + vz * vz);
		if (len < 1.0E-4D) {
			return false;
		}
		double nx = vx / len;
		double nz = vz / len;
		Vec3 pos = this.position();
		int baseY = Mth.floor(pos.y);
		BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();
		for (double d = 2.0D; d <= 6.0D; d += 2.0D) {
			int bx = Mth.floor(pos.x + nx * d);
			int bz = Mth.floor(pos.z + nz * d);
			for (int yo = 0; yo <= 2; yo++) {
				if (!this.level().isEmptyBlock(mp.set(bx, baseY + yo, bz))) {
					return true;
				}
			}
		}
		return false;
	}

	/** Vertical velocity that keeps a safe clearance over the terrain below; holds altitude over the void. */
	private double cruiseVerticalAdjust(Vec3 self) {
		int bx = Mth.floor(self.x);
		int bz = Mth.floor(self.z);
		int startY = Mth.floor(self.y);
		int minY = this.level().getMinBuildHeight();
		BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();

		int groundY = Integer.MIN_VALUE;
		for (int y = startY - 1; y >= Math.max(minY, startY - GROUND_SCAN); y--) {
			if (!this.level().isEmptyBlock(mp.set(bx, y, bz))) {
				groundY = y;
				break;
			}
		}

		if (groundY == Integer.MIN_VALUE) {
			// Over the void between islands — hold altitude rather than diving.
			return 0.0D;
		}

		double clearance = self.y - (groundY + 1);
		if (clearance < MIN_GROUND_CLEARANCE) {
			return CLIMB_SPEED;
		}
		if (clearance > MAX_GROUND_CLEARANCE) {
			return -0.05D;
		}
		return 0.0D;
	}

	/** Locate the nearest End City, or {@code null} when not in the End / none within range. */
	@Nullable
	private BlockPos findNearestEndCity() {
		if (!(this.level() instanceof ServerLevel serverLevel) || serverLevel.dimension() != Level.END) {
			return null;
		}

		Registry<Structure> structures = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
		Holder<Structure> endCity = structures.getHolder(BuiltinStructures.END_CITY).orElse(null);
		if (endCity == null) {
			return null;
		}

		Pair<BlockPos, Holder<Structure>> nearest = serverLevel.getChunkSource().getGenerator()
				.findNearestMapStructure(serverLevel, HolderSet.direct(endCity), this.blockPosition(), 100, false);
		return nearest != null ? nearest.getFirst() : null;
	}

	// ── GeckoLib ──────────────────────────────────────────────────────────────

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		state.getController().setAnimation(IDLE);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
