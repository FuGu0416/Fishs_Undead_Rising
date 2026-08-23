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
import net.minecraft.core.SectionPos;
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
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.Vec3;

import com.mojang.datafixers.util.Pair;
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
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("void_glider.model.idle");
	private static final RawAnimation FLY = RawAnimation.begin().thenLoop("void_glider.model.fly");
	private static final RawAnimation ROLL_BLEND = RawAnimation.begin().thenPlay("void_glider.model.roll_blend");

	/** Entity-event id for triggering {@link #ROLL_BLEND} client-side; see {@link #handleEntityEvent}. */
	private static final byte EVENT_ROLL_BLEND = 10;
	/** 1-in-N chance per tick, while actively flying, to roll {@link #ROLL_BLEND}. */
	private static final int ROLL_BLEND_CHANCE = 300;
	/** Ticks to wait before the next roll can fire - past the clip's own 1.5s (30-tick) length. */
	private static final int ROLL_BLEND_COOLDOWN_TICKS = 40;
	private int rollBlendCooldown;

	/** Cached End City the glider auto-flies toward while ridden (transient — recomputed each mount). */
	@Nullable
	private BlockPos endCityDest;
	private int destSearchCooldown;
	/**
	 * Highest Y of {@link #endCityDest}'s own generated structure (its tallest tower), plus
	 * {@link #END_CITY_CLEARANCE} — set alongside {@link #endCityDest} in {@link #findNearestEndCity}.
	 * {@link #autoPilotToEndCity} climbs to at least this altitude while still far from the city
	 * instead of only reacting to whatever's directly ahead once already close; without it, a glider
	 * cruising below the city's height would fly straight into the island's cliff face rather than
	 * climbing over it in time. Meaningless whenever {@link #endCityDest} is null.
	 */
	private double endCitySafeAltitude;

	// Autopilot tuning
	private static final double CRUISE_SPEED = 0.6D;          // 12 blocks/s horizontal (used directly as blocks/tick - no accel/drag model here)
	private static final double CLIMB_SPEED = 0.6D;            // upward speed when climbing over terrain, matches CRUISE_SPEED
	private static final double MIN_GROUND_CLEARANCE = 12.0D;  // climb if closer than this to the terrain below
	private static final double MAX_GROUND_CLEARANCE = 32.0D;  // ease down if higher than this above terrain
	private static final int HOME_IN_RANGE = 24;              // within this horizontal distance, settle to the city's height
	private static final int GROUND_SCAN = 48;                 // how far down to look for terrain when holding altitude
	private static final double END_CITY_CLEARANCE = 10.0D;   // stay at least this many blocks above the city's tallest point while cruising
	private static final double ORBIT_RADIUS = 8.0D;           // holding-pattern radius once close enough, phantom-style
	private static final double ORBIT_ENTER_RANGE = ORBIT_RADIUS + 3.0D; // switch from straight-line approach to orbiting inside this range
	private static final double ORBIT_SPEED = 0.12D;           // tangential speed while circling

	public VoidGliderEntity(EntityType<? extends VoidGliderEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.xpReward = 1;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this, 1.0D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.12D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.4D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(2));
		this.getEntityData().define(SIZE_VARIANT, Integer.valueOf(this.getRandom().nextInt(VoidGliderEntity.SIZE.length)));
	}

	/** How far above/below a candidate spawn position to scan for island terrain; see {@link #checkVoidGliderSpawnRules}. */
	private static final int ISLAND_CLEARANCE_SCAN = 64;
	/** Minimum blocks of clearance required above whatever island ground sits below the spawn point (inclusive). */
	private static final int MIN_GROUND_SPAWN_CLEARANCE = 45;
	/** Maximum of the same, exclusive - actual requirement per attempt is randomized in this 45-50 range. */
	private static final int MAX_GROUND_SPAWN_CLEARANCE = 50;
	/** Extra chance (independent of spawn weight, which is already at its practical floor of 1) to skip a spawn attempt entirely - halves the effective spawn rate again. */
	private static final int EXTRA_SKIP_CHANCE = 2;

	/**
	 * Rejects spawn positions sitting underneath a floating island (solid terrain found somewhere
	 * above), positions too close above whatever island ground sits below them, and (independent of
	 * spawn weight) half of all attempts outright - see {@link #EXTRA_SKIP_CHANCE}.
	 */
	public static boolean checkVoidGliderSpawnRules(EntityType<? extends VoidGliderEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource rand) {
		if (!FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(type, world, reason, pos, rand) || rand.nextInt(EXTRA_SKIP_CHANCE) != 0) {
			return false;
		}

		BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();

		int topY = Math.min(pos.getY() + ISLAND_CLEARANCE_SCAN, world.getMaxBuildHeight());
		for (int y = pos.getY() + 1; y <= topY; y++) {
			if (!world.getBlockState(mp.set(pos.getX(), y, pos.getZ())).isAir()) {
				return false;
			}
		}

		int minClearance = MIN_GROUND_SPAWN_CLEARANCE + rand.nextInt(MAX_GROUND_SPAWN_CLEARANCE - MIN_GROUND_SPAWN_CLEARANCE + 1);
		int bottomY = Math.max(pos.getY() - ISLAND_CLEARANCE_SCAN, world.getMinBuildHeight());
		for (int y = pos.getY() - 1; y >= bottomY; y--) {
			if (!world.getBlockState(mp.set(pos.getX(), y, pos.getZ())).isAir()) {
				return pos.getY() - y - 1 >= minClearance;
			}
		}

		return true;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void tick() {
		super.tick();
		this.setNoGravity(true);

		if (!this.level().isClientSide()) {
			if (this.rollBlendCooldown > 0) {
				this.rollBlendCooldown--;
			} else if (!this.isVehicle() && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D && this.random.nextInt(ROLL_BLEND_CHANCE) == 0) {
				this.level().broadcastEntityEvent(this, EVENT_ROLL_BLEND);
				this.rollBlendCooldown = ROLL_BLEND_COOLDOWN_TICKS;
			}
		}
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

		// Natural-spawn height floor (raiseToMinimumSpawnHeight) now lives on FlyingMobEntity.finalizeSpawn
		// so it applies to every End-spawning flyer, not just this one.

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

	/** Matches 1.16.5 Ghost Ray, which borrowed Banshee's hurt sound rather than having its own. */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.BANSHEE_HURT.get();
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

	/** Steer deltaMovement toward {@link #endCityDest} (non-null here); cruise high, clear islands, orbit on arrival. */
	private void autoPilotToEndCity() {
		Vec3 self = this.position();
		Vec3 dest = Vec3.atCenterOf(this.endCityDest);
		double dx = dest.x - self.x;
		double dz = dest.z - self.z;
		double horiz = Math.sqrt(dx * dx + dz * dz);

		if (horiz < ORBIT_ENTER_RANGE) {
			// Close enough — hold a slow phantom-style circle above the city instead of trying to stop
			// dead: freezing outright (a brief earlier attempt) either let residual momentum carry it
			// back out and re-trigger the steer-back-in branch below (an endless "drift out, correct,
			// arrive, drift out again" loop), or just looked unnaturally static for something still
			// airborne. Orbiting is the deliberate version of that same circling instead of a bug.
			this.orbitEndCity(self, dest);
			return;
		}

		double inv = 1.0D / horiz;
		double vx = dx * inv * CRUISE_SPEED;
		double vz = dz * inv * CRUISE_SPEED;

		double vy;
		if (horiz < HOME_IN_RANGE) {
			// Closing in: settle toward the city's actual height for the rider to dismount.
			vy = Mth.clamp((dest.y - self.y) * 0.1D, -0.15D, CLIMB_SPEED);
		} else if (self.y < this.endCitySafeAltitude) {
			// Still below the city's own tallest point (plus clearance) — climb proactively instead
			// of waiting for isBlockedAhead to notice the island's cliff face only a few blocks out,
			// which can be too late to climb over it in time.
			vy = CLIMB_SPEED;
		} else if (this.isBlockedAhead(vx, vz)) {
			// Something else is dead ahead (e.g. a different island passed en route) — climb over it too.
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

	/**
	 * Phantom-style holding pattern: a slow circle at {@link #ORBIT_RADIUS} above the End City, recomputed
	 * fresh from the current position every tick (tangential component drives the circling, a small radial
	 * component gently pulls distance back toward {@link #ORBIT_RADIUS}) so it self-corrects into a stable
	 * loop from whatever angle/distance it arrived at, rather than needing a persisted orbit angle.
	 *
	 * <p>Holds altitude at {@link #endCitySafeAltitude} - already the tallest point of the actual generated
	 * structure plus clearance, computed once in {@link #findNearestEndCity} for the cruise-in climb - rather
	 * than {@code dest.y} (down near the city's base/landing height). A fixed horizontal radius has no way to
	 * know the building's real footprint (End Cities are irregular, and {@code dest} itself is already
	 * jittered off-center), so orbiting above its known-clear top-down height is what actually avoids flying
	 * into it, no extra footprint-tracking state needed. Riders dismounting mid-orbit already get Slow
	 * Falling from {@link #removePassenger}, so the extra height down to the city is intentional, not a
	 * problem to route around.
	 */
	private void orbitEndCity(Vec3 self, Vec3 dest) {
		double cx = self.x - dest.x;
		double cz = self.z - dest.z;
		double dist = Math.sqrt(cx * cx + cz * cz);
		if (dist < 1.0E-4D) {
			// Degenerate case: sitting exactly on the center — pick an arbitrary direction so a tangent
			// exists instead of dividing by zero.
			cx = ORBIT_RADIUS;
			dist = ORBIT_RADIUS;
		}
		double nx = cx / dist;
		double nz = cz / dist;

		double tx = -nz * ORBIT_SPEED;
		double tz = nx * ORBIT_SPEED;

		double radialError = ORBIT_RADIUS - dist;
		double rx = nx * radialError * 0.05D;
		double rz = nz * radialError * 0.05D;

		double vx = tx + rx;
		double vz = tz + rz;
		double vy = Mth.clamp((this.endCitySafeAltitude - self.y) * 0.1D, -0.15D, CLIMB_SPEED);
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
		if (nearest == null) {
			return null;
		}

		BlockPos pos = nearest.getFirst();
		this.endCitySafeAltitude = findEndCityTopY(serverLevel, endCity.value(), pos) + END_CITY_CLEARANCE;
		// +20 so the autopilot's "closing in" settle height (which homes toward endCityDest.y) lands
		// higher above the structure instead of at the raw located point, plus a random per-trip jitter
		// (X/Z +-10, Y +-3) so riders don't always land at the exact same spot on repeat trips.
		int offsetX = this.random.nextInt(21) - 10;
		int offsetY = this.random.nextInt(7) - 3;
		int offsetZ = this.random.nextInt(21) - 10;
		return pos.offset(offsetX, 20 + offsetY, offsetZ);
	}

	/**
	 * Highest Y of the actual generated End City structure at {@code pos} (its tallest tower/segment),
	 * read from the structure's own piece bounding box rather than the world's heightmap — the
	 * heightmap needs the terrain to actually be generated already, while structure piece data is
	 * available as soon as the chunk reaches {@code STRUCTURE_STARTS}, which
	 * {@code findNearestMapStructure} already forces. Falls back to {@code pos.getY()} (i.e. no extra
	 * climb beyond {@link #END_CITY_CLEARANCE}) if no structure start turns up there.
	 */
	private static double findEndCityTopY(ServerLevel serverLevel, Structure structure, BlockPos pos) {
		for (StructureStart start : serverLevel.structureManager().startsForStructure(SectionPos.of(pos), structure)) {
			return start.getBoundingBox().maxY();
		}
		return pos.getY();
	}

	// ── GeckoLib ──────────────────────────────────────────────────────────────

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == EVENT_ROLL_BLEND) {
			this.triggerAnim("trigger_controller", "roll_blend");
		} else {
			super.handleEntityEvent(id);
		}
	}

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		state.getController().setAnimation(state.isMoving() || !this.getNavigation().isDone() ? FLY : IDLE);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("roll_blend", ROLL_BLEND));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
