package com.Fishmod.fur.entities.flying;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlyingMobEntity extends FURTameableEntity {
	private int hoverTimer;
	private int landTimer;

	// ── Banked-turn visual roll ──────────────────────────────────────────────
	// Client-only, derived each tick from how fast this.getYRot() is actually changing (which
	// FlyingMoveHelper now turns gradually toward a new heading — see MAX_TURN_RATE there — instead
	// of snapping straight to it). No networking needed: yRot/yRotO are already synced/interpolated
	// for every entity, so each client can compute the same bank angle independently, the same trick
	// vanilla's Boat uses for its own turn roll.
	private float bankAngle;
	private static final float BANK_FACTOR = 2.0F;
	private static final float MAX_BANK_ANGLE = 35.0F;
	private static final float BANK_SMOOTHING = 0.15F;

	/** Current visual bank/roll angle in degrees (positive/negative meaning is whatever
	 *  {@link com.Fishmod.fur.client.renderer.entity.FlyingMobRenderer} interprets it as — flip
	 *  {@link #BANK_FACTOR}'s sign if a mob banks the wrong way). Always 0 server-side. */
	public float getBankAngle() {
		return this.bankAngle;
	}

	public FlyingMobEntity(EntityType<? extends FlyingMobEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.moveControl = new FlyingMobEntity.FlyingMoveHelper(this);
	    this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
	    this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
	}
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.setNoGravity(true);
	}
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));		
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}
	
    public static boolean checkFlyerSpawnRules(EntityType<? extends FlyingMobEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
    	return FURTameableEntity.checkMonsterSpawnRules(entityType, level, spawnType, pos, random)
        		&& (level.canSeeSky(pos) || ((Level) level).dimension() != Level.OVERWORLD);
    }
    
    public static boolean checkFlyerSpawnRulesNoSky(EntityType<? extends FlyingMobEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
    	return FURTameableEntity.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }
    
    public static boolean checkFlyerSpawnRulesNoRestriction(EntityType<? extends FlyingMobEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
    	return level.getDifficulty() != Difficulty.PEACEFUL && FURTameableEntity.isDarkEnoughToSpawn(level, pos, random);
    }

    // Gravity for a tamed flying mount is driven entirely by aiStep() from its sit/follow/wander
    // state, so the old per-command setNoGravity() overrides (doSit/doFollow/doWander) were dropped
    // as redundant — the base FURTameableEntity versions (pure state switches) are used directly.

    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
		super.aiStep();

		if (this.level().isClientSide) {
			// yRot has already moved this tick (server-set value arrived via the normal entity-sync
			// packet, or - while ridden - RidableFlyingMobEntity's own steering set it directly), so
			// the delta against yRotO is this tick's real turn rate. Smoothed with a simple lerp
			// rather than read raw, so the roll eases in/out instead of snapping to 0 the instant a
			// turn ends (yRot stops changing dead the tick a turn completes; the roll shouldn't).
			float yawDelta = Mth.wrapDegrees(this.getYRot() - this.yRotO);
			float targetBank = Mth.clamp(-yawDelta * BANK_FACTOR, -MAX_BANK_ANGLE, MAX_BANK_ANGLE);
			this.bankAngle += (targetBank - this.bankAngle) * BANK_SMOOTHING;
		}

		if (!this.level().isClientSide && !this.isBaby()) {
	    	if (this.onGround()) {
	    		int lt = this.getLandTimer();
	    		if (lt < 20) {
	    			this.setLandTimer(lt + 1);
	    			this.level().broadcastEntityEvent(this, (byte)40);
	    		}

	    		if (this.isTame()) {
	    			// Commanded mount: SITTING settles onto the ground; otherwise stay airborne so
	    			// AIRandomFly / FlyerFollowOwnerGoal can lift it off the ground and fly.
	    			this.setNoGravity(!this.isInSittingPose());
	    		} else {
	    			// Wild flyer ambient: rest on the ground, occasionally hop back into the air.
	    			if (this.isNoGravity()) {
	    				this.setNoGravity(this.getTarget() != null);
	    			}

	    			if (!this.isVehicle() && !this.isNoGravity() && !this.isInSittingPose() && this.getRandom().nextFloat() < 0.15F) {
	    				this.setNoGravity(true);
	    				this.setDeltaMovement(this.getDeltaMovement().add(0.0F, 0.25F, 0.0F));
	    			}
	    		}
	    	} else {
	    		int lt = this.getLandTimer();
	    		if (lt > 0) {
	    			this.setLandTimer(lt - 1);
	    			this.level().broadcastEntityEvent(this, (byte)41);
	    		}
	    		
	    		if (this.isTame()) {
	    			// Sitting tamed mount descends to land; otherwise keep it airborne.
	    			this.setNoGravity(!this.isInSittingPose());
	    		} else if (!this.isNoGravity()) {
	    			this.setNoGravity(true);
	    		}
	    	}
	    		    	
	        if (this.isInSittingPose() && this.getTarget() == null) {	        	
	        	if (!this.onGround() && SpawnUtil.getHeight(this).getY() > 0) {
	        		this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.025F, 0.0F));
	        	}
	        }
		}
    }

	public int getHoverTimer() {
		return this.hoverTimer;
	}
    
	public void setHoverTimer(int i) {
		this.hoverTimer = i;
	}

	public int getLandTimer() {
		return this.landTimer;
	}

	public void setLandTimer(int i) {
		this.landTimer = i;
	}

	@Override
	public boolean onGround() {
		if (super.onGround()) return true;
		// Treat a block within 0.2 below as grounded. The vanilla flag is unreliable for a
		// no-gravity flyer — it never presses into the floor — so it stays false even while the
		// mob is resting on the ground. This matters most while ridden and right after dismounting:
		// without it, onGround() flips back to false the instant the rider leaves, snapping the
		// animation back to "fly" and re-hovering a mount that had already landed. Free flyers
		// cruise at >= groundY+2, so this only registers when they are actually touching down.
		return !this.level().noCollision(this, this.getBoundingBox().move(0.0, -0.2, 0.0));
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 40)  {
    		int lt = this.getLandTimer();
    		if (lt < 20) {
    			this.setLandTimer(lt + 1);
    		}
        } else if (id == 41)  {
    		int lt = this.getLandTimer();
    		if (lt > 0) {
    			this.setLandTimer(lt - 1);
    		}
        } else {
            super.handleEntityEvent(id);
        }
    }
	
    @Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
	}
    
	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation pathnavigateflying = new FlyingPathNavigation(this, world);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanPassDoors(true);
        return pathnavigateflying;
	}

	public Entity getLowestPassenger() {
        // Learned the hard way: don't trust getLowestRidingEntity.
        // It will literally return the last entity in the passenger list -- useless.
        Entity lowestPassenger = null;

        for (Entity passenger: this.getPassengers()) {
            if (lowestPassenger == null || passenger.getBoundingBox().minY < lowestPassenger.getBoundingBox().minY) {
                lowestPassenger = passenger;
            }
        }

        return lowestPassenger;
    }
	
	public void makeStuckInBlock(BlockState state, Vec3 motion) {
		if (state.is(Blocks.COBWEB)) {
			this.getMoveControl().setWantedPosition(this.getX(), this.getY() - 1, this.getZ(), 1.0D);
		}
			
		super.makeStuckInBlock(state, motion);
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}
	
	protected double VehicleSpeedMod() {
		return 1.0D;
	}

	/**
	 * Whether AI-driven movement (the {@link FlyingMoveHelper} MoveControl and the {@link AIRandomFly}
	 * goal) should stand down this tick because something else is driving motion. Default: while ridden —
	 * a player-steered mount seizes control in {@code travel()}. Subclasses that only sometimes take over
	 * (e.g. the Void Glider's End-City autopilot) can narrow this so the flight AI keeps running otherwise.
	 */
	protected boolean suspendAiMovement() {
		return this.isVehicle();
	}

    public void travel(Vec3 travelVector) {
    	// If the lowest passenger is colliding with the ground, get them out!
        Entity lowestPassenger = this.getLowestPassenger();

        if (lowestPassenger != null && !(lowestPassenger instanceof Player)) {
            AABB passengerBounds = lowestPassenger.getBoundingBox();

            if (!lowestPassenger.level().noCollision(lowestPassenger, passengerBounds)) {
            	this.getMoveControl().setWantedPosition(this.getX(), this.getY() + lowestPassenger.getBbHeight(), this.getZ(), 1.0D);
            }            
        }
        
        if (!this.isNoGravity() && !this.isVehicle() && !(this.getControllingPassenger() instanceof Player)) {
        	this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().x, -0.15D, this.getDeltaMovement().z);
        }
    	
    	if (this.getTarget() != null) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(1.05D));
    	}
    	
    	if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = this.blockPosition().below();
            float f = this.onGround()
                    ? this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F
                    : 0.91F;
            float f1 = 0.16277137F / (f * f * f);
            this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement().scale(this.VehicleSpeedMod()));
            this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
        }

        this.calculateEntityAnimation(false);
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean onClimbable() {    	
        return this.isBaby() ? super.onClimbable() : false;
    }
    
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {
    	if (!this.isBaby()) {
    		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
    	}

    	if (spawnType == MobSpawnType.NATURAL && !this.isBaby() && this.level().dimension() == Level.END) {
    		int targetY = END_MIN_SPAWN_Y + this.random.nextInt(END_MAX_SPAWN_Y - END_MIN_SPAWN_Y + 1);
    		if (this.getY() < targetY) {
    			this.raiseToMinimumSpawnHeight(worldIn, targetY);
    		}
    	}

        return super.finalizeSpawn(worldIn, difficulty, spawnType, entityLivingData, tag);
    }

    /**
     * Absolute floor for a flying mob's natural spawn height in the End, randomized per spawn within
     * [{@link #END_MIN_SPAWN_Y}, {@link #END_MAX_SPAWN_Y}]. Vanilla's
     * {@code NaturalSpawner.getRandomPosWithin} rolls each spawn attempt's Y uniformly between the
     * dimension's min build height (0 in the End) and the column's surface height, once per chunk, before
     * any per-species spawn-rule predicate ever runs - those predicates can only accept/reject the
     * already-chosen Y, not move it, so low rolls (e.g. ~27, out over a void gap with no island anywhere
     * below to reject against) routinely pass. Enforcing a floor has to happen after spawn, once the
     * entity has a real position to nudge. Applies to every End-spawning flyer via this shared base class.
     */
    private static final int END_MIN_SPAWN_Y = 65;
    private static final int END_MAX_SPAWN_Y = 70;

    /** Lifts the entity straight up toward {@code targetY}, stopping just below the first solid block it meets. */
    private void raiseToMinimumSpawnHeight(ServerLevelAccessor world, int targetY) {
    	int x = Mth.floor(this.getX());
    	int z = Mth.floor(this.getZ());
    	int startY = Mth.floor(this.getY());
    	BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();

    	int clearY = startY;
    	for (int y = startY + 1; y <= targetY; y++) {
    		if (!world.getBlockState(mp.set(x, y, z)).isAir()) {
    			break;
    		}
    		clearY = y;
    	}

    	if (clearY > startY) {
    		this.setPos(this.getX(), clearY, this.getZ());
    	}
    }

    static class AIRandomFly extends Goal {
        private final FlyingMobEntity parentEntity;
        private final double speed;
        private final int horizontalRange = 10;
        private final int verticalRange = 6;

        // Stuck detection
        // MIN_MOVE_SQ is intentionally higher than before: FlyingMoveHelper's lerp
        // can produce tiny residual movement even when physically blocked by a wall.
        // Using a higher threshold ensures stuckTicks accumulates reliably.
        private Vec3 lastPos = Vec3.ZERO;
        private int stuckTicks = 0;
        private static final int STUCK_THRESHOLD = 20; // Lowered: escape sooner
        private static final double MIN_MOVE_SQ = 0.01D; // Raised: ignore micro-jitter from wall lerp

        // Backoff after a failed target search. pickNewTarget() runs a 10-candidate
        // ray-sampling scan; when boxed in (every candidate rejected) MoveControl
        // parks at WAIT, which would otherwise make tick() re-run that scan every
        // single tick. This delays the retry so the scan can't thrash the CPU.
        private int repickCooldown = 0;
        private static final int REPICK_FAIL_COOLDOWN = 10;

        // Ground Y cache (~5 sec refresh). cachedGroundY starts at NaN, meaning "not yet scanned";
        // once scanned, NO_GROUND is the distinct sentinel for "scan reached the bottom of the
        // world without finding a block" (i.e. true void below, no island at this x/z) - kept
        // apart from NaN so that result is still cached instead of forcing a rescan every tick.
        private double cachedGroundY = Double.NaN;
        private int groundYCacheTimer = 0;
        private static final int GROUND_Y_CACHE_INTERVAL = 100;
        private static final double NO_GROUND = Double.NEGATIVE_INFINITY;

        // Ceiling Y cache (~3 sec refresh)
        private double cachedCeilingY = Double.NaN;
        private int ceilingYCacheTimer = 0;
        private static final int CEILING_Y_CACHE_INTERVAL = 60;

        // Reusable mutable position for clearance / lava checks
        private final BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

        // Trail of recent positions, sampled periodically, used by escape()'s Phase 2.5 to retreat
        // back out along the entity's own path when boxed in somewhere Phase 1/2 can't find a way out
        // of - e.g. tangled inside a Chorus Plant thicket, whose irregular branches can block all 24 of
        // Phase 2's straight probe rays at once. Anywhere on this trail was open air moments ago,
        // making it a far more reliable fallback than a blind random pick.
        private static final int TRAIL_SAMPLE_INTERVAL = 4; // ticks between samples
        private static final int TRAIL_LENGTH = 10;         // ~40 ticks / 2s of trail
        private final Deque<Vec3> trail = new ArrayDeque<>();
        private int trailTimer = 0;

        public AIRandomFly(FlyingMobEntity entity, double speed) {
            this.parentEntity = entity;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.parentEntity.suspendAiMovement()
                    && this.parentEntity.getTarget() == null
                    && !(this.parentEntity.getNavigation() instanceof GroundPathNavigation)
                    && !this.parentEntity.isInSittingPose();
        }

        @Override
        public boolean canContinueToUse() {
            // Mirror the runtime conditions of canUse() so the goal releases the
            // MOVE flag promptly when the mob gains a target or is told to sit,
            // instead of holding it until another goal preempts it.
            return !this.parentEntity.suspendAiMovement()
                    && this.parentEntity.getTarget() == null
                    && !this.parentEntity.isInSittingPose();
        }

        @Override
        public void start() {
            this.lastPos = this.parentEntity.position();
            this.stuckTicks = 0;
            this.repickCooldown = 0;
            this.trail.clear();
            this.trailTimer = 0;
            this.pickNewTarget();
        }

        @Override
        public void tick() {
            Vec3 current = this.parentEntity.position();

            // Stuck detection
            if (current.distanceToSqr(this.lastPos) < MIN_MOVE_SQ) {
                this.stuckTicks++;
            } else {
                this.stuckTicks = 0;
            }
            this.lastPos = current;

            // Sample the trail escape()'s Phase 2.5 retreats along
            if (++this.trailTimer >= TRAIL_SAMPLE_INTERVAL) {
                this.trailTimer = 0;
                if (this.trail.size() >= TRAIL_LENGTH) {
                    this.trail.pollFirst();
                }
                this.trail.addLast(current);
            }

            if (this.stuckTicks > STUCK_THRESHOLD) {
                this.escape(current);
                this.stuckTicks = 0;
                return;
            }

            // Back off after a failed search instead of re-scanning every tick
            if (this.repickCooldown > 0) {
                this.repickCooldown--;
                return;
            }

            // Re-pick only when target is reached
            MoveControl move = this.parentEntity.getMoveControl();
            double dx = move.getWantedX() - current.x;
            double dy = move.getWantedY() - current.y;
            double dz = move.getWantedZ() - current.z;
            double distSq = dx * dx + dy * dy + dz * dz;

            if (!move.hasWanted() || distSq < 2.0D) {
                this.pickNewTarget();
            }
        }

        // ── Escape when stuck ────────────────────────────────────────────────

        /**
         * Four-phase escape strategy:
         *
         * Phase 1 — Upward burst: if there is open sky directly above, bypass
         *   MoveControl entirely and inject an upward deltaMovement impulse.
         *   This handles the common 3-wall dead-end where the only exit is up.
         *
         * Phase 2 — Scored horizontal scan: probe 8 horizontal directions x 3
         *   Y offsets (24 candidates). Score each by the number of safe ray
         *   samples along the path. Commit to the best-scoring direction via
         *   MoveControl if its score exceeds the minimum threshold.
         *
         * Phase 2.5 — Trail retreat: if every straight probe ray is blocked (an
         *   irregular obstacle like a Chorus Plant thicket can wall off all 24 at
         *   once from the inside), back out along the entity's own recent trail
         *   instead - anywhere it stood a moment ago was open air then.
         *
         * Phase 3 — Hard velocity reset: if all of the above fail (fully boxed
         *   in with no usable trail), zero out deltaMovement and try a handful of
         *   short random nearby targets, each validated for clearance before being
         *   committed, so FlyingMoveHelper starts fresh without carrying the old
         *   stuck velocity into another guaranteed dead end.
         */
        private void escape(Vec3 current) {
            BlockPos origin = this.parentEntity.blockPosition();
            double groundY  = this.getGroundY(origin);
            if (groundY == NO_GROUND) {
                // Over the void, nothing below within world bounds - fall back to a value
                // relative to the entity's current altitude instead of leaving this at
                // NO_GROUND's -Infinity, which would send every escape candidate straight down.
                groundY = current.y - 20.0D;
            }
            double ceilingY = this.getCeilingY(origin);
            double midY     = (groundY + ceilingY) / 2.0D;
            Level  level    = this.parentEntity.level();

            // ── Phase 1: upward burst ────────────────────────────────────────
            // Check 4 blocks directly above. If they are all clear (and not lava),
            // inject a direct upward impulse, bypassing MoveControl's lerp entirely.
            boolean canGoUp = true;
            for (int i = 1; i <= 4; i++) {
                BlockPos check = origin.above(i);
                if (!level.isEmptyBlock(check)
                        || level.getBlockState(check).is(net.minecraft.world.level.block.Blocks.LAVA)) {
                    canGoUp = false;
                    break;
                }
            }

            if (canGoUp && ceilingY - current.y > 2.5D) {
                // Reset horizontal velocity, inject upward burst
                double burstSpeed = 0.4D * this.parentEntity.getAttributeValue(Attributes.FLYING_SPEED);
                this.parentEntity.setDeltaMovement(0.0D, burstSpeed, 0.0D);
                // Also tell MoveControl to aim upward so it does not immediately
                // fight the burst on the next tick
                this.parentEntity.getMoveControl().setWantedPosition(
                        current.x, ceilingY - 1.5D, current.z, this.speed);
                return;
            }

            // ── Phase 2: scored horizontal scan ─────────────────────────────
            double[] angles   = {
                0, Math.PI / 4, Math.PI / 2, Math.PI * 3 / 4,
                Math.PI, Math.PI * 5 / 4, Math.PI * 3 / 2, Math.PI * 7 / 4
            };
            double[] yOffsets = { 0.0D, 1.5D, -1.5D };

            Vec3 bestEscape = null;
            int  bestScore  = -1;

            for (double angle : angles) {
                double ex = current.x + Math.cos(angle) * 5.0D;
                double ez = current.z + Math.sin(angle) * 5.0D;

                for (double yOff : yOffsets) {
                    double ey = Mth.clamp(midY + yOff, groundY + 1.5D, ceilingY - 1.0D);
                    Vec3 candidate = new Vec3(ex, ey, ez);

                    int score = this.countSafeSamples(current, candidate, 8);
                    if (score > bestScore) {
                        bestScore  = score;
                        bestEscape = candidate;
                    }
                }
            }

            if (bestEscape != null && bestScore >= 4) {
                this.parentEntity.getMoveControl().setWantedPosition(
                        bestEscape.x, bestEscape.y, bestEscape.z, this.speed);
                return;
            }

            // ── Phase 2.5: retreat along the entity's own recent trail ───────
            // Oldest first: prefer backing out as far as the trail goes, maximizing distance from
            // whatever trapped it rather than settling for the nearest recorded point, which may
            // still be tangled in the same thicket.
            for (Vec3 trailPos : this.trail) {
                if (this.hasClearanceAt(trailPos) && this.countSafeSamples(current, trailPos, 8) >= 4) {
                    this.parentEntity.getMoveControl().setWantedPosition(
                            trailPos.x, trailPos.y, trailPos.z, this.speed);
                    return;
                }
            }

            // ── Phase 3: hard velocity reset ─────────────────────────────────
            // Completely boxed in with no usable trail (e.g. it spawned inside the obstruction).
            // Zero out velocity so it stops fighting the walls, then try a handful of short random
            // offsets - each validated with hasClearanceAt before being committed, instead of
            // trusting a single blind guess, which previously could commit to the exact same dead
            // end every time stuckTicks next crossed the threshold and leave the mob permanently
            // stuck.
            this.parentEntity.setDeltaMovement(Vec3.ZERO);
            for (int attempt = 0; attempt < 8; attempt++) {
                double angle = this.parentEntity.random.nextDouble() * Math.PI * 2.0D;
                double dist  = 1.0D + this.parentEntity.random.nextDouble() * 1.5D;
                Vec3 candidate = new Vec3(
                        current.x + Math.cos(angle) * dist,
                        Mth.clamp(midY, groundY + 1.5D, ceilingY - 1.0D),
                        current.z + Math.sin(angle) * dist);

                if (this.hasClearanceAt(candidate)) {
                    this.parentEntity.getMoveControl().setWantedPosition(
                            candidate.x, candidate.y, candidate.z, this.speed);
                    return;
                }
            }
            // Every attempt was still blocked: leave velocity at zero rather than committing to
            // another unchecked dead end - the next stuck cycle will try again.
        }

        // ── Target selection ─────────────────────────────────────────────────

        /**
         * Picks a random air position that:
         *  1. Has vertical + horizontal clearance (not in a squeeze)
         *  2. Has a clear ray from the entity's current position (reachability)
         *  3. Is not lava and has no lava immediately adjacent
         * Tries up to 10 candidates before giving up.
         */
        private void pickNewTarget() {
            Vec3 current = this.parentEntity.position();

            for (int i = 0; i < 10; i++) {
                Vec3 candidate = this.findAirPosition();

                if (candidate == null)
                    continue;

                candidate = this.clampToSafeHeight(candidate);

                // Basic clearance check at destination
                if (!this.hasClearanceAt(candidate))
                    continue;

                // Reachability: require at least 6 of 10 ray samples to be clear.
                // This filters out targets that are air but separated by a wall
                // (e.g. the other side of a cave partition).
                if (this.countSafeSamples(current, candidate, 10) < 6)
                    continue;

                this.parentEntity.getMoveControl().setWantedPosition(
                        candidate.x + 0.5D, candidate.y + 0.5D, candidate.z + 0.5D, this.speed);
                this.parentEntity.getLookControl().setLookAt(
                        candidate.x, candidate.y, candidate.z, 180.0F, 20.0F);
                return;
            }
            // No valid target found (likely boxed in): back off before retrying so
            // this scan does not run every tick. Stuck detection still escalates to
            // escape() independently if the mob is also physically pinned.
            this.repickCooldown = REPICK_FAIL_COOLDOWN;
        }

        @Nullable
        private Vec3 findAirPosition() {
            Vec3 view = this.parentEntity.getViewVector(0.0F);

            // AirAndWaterRandomPos first: it picks freely within +/-verticalRange of the entity's
            // CURRENT altitude with no ground-proximity requirement, which is what a mob that should
            // actually soar wants. HoverRandomPos (below) looks like the general-purpose flying
            // picker but isn't - per vanilla's own FlyingPathNavigation.isStableDestination(), the
            // intermediate point it walks toward must itself be a *solid, standable* block before it
            // ever gets lifted "1-3 blocks above solid ground"; if that HoverRandomPos candidate had
            // been tried first (as it originally was here) it succeeds almost any time solid terrain
            // is within range - i.e. constantly while over an island - which is why gliders kept
            // getting pulled down to skim 1-3 blocks above the ground instead of cruising. Vanilla
            // uses HoverRandomPos for things that are meant to hug a surface (e.g. Vex); it's kept
            // here only as a last-resort fallback for the rare case open-air picking fails outright.
            Vec3 pos = AirAndWaterRandomPos.getPos(
                    this.parentEntity, this.horizontalRange, this.verticalRange,
                    -2, view.x, view.y, view.z);

            if (pos != null)
                return pos;

            return HoverRandomPos.getPos(
                    this.parentEntity, this.horizontalRange, this.verticalRange,
                    view.x, view.z, (float) Math.PI / 2F, 3, 1);
        }

        // ── Height safety ────────────────────────────────────────────────────

        private Vec3 clampToSafeHeight(Vec3 target) {
            double limit    = FURConfig.FlyingHeight_limit.get();
            BlockPos origin = this.parentEntity.blockPosition();
            double groundY  = this.getGroundY(origin);
            double ceilingY = this.getCeilingY(origin);

            double minY;
            double maxY;
            if (groundY == NO_GROUND) {
                // Nothing below within the whole world height - flying over the void between
                // floating islands (the common case in the End). There's no "ground" to measure
                // FlyingHeight_limit against here, so don't apply it: doing so previously fell
                // through to groundY == world-bottom, dragging every random-fly target down toward
                // minBuildHeight + limit and making the mob sink toward the void floor the entire
                // time it crossed a gap between islands. Just stay clear of the ceiling instead.
                minY = this.parentEntity.level().getMinBuildHeight() + 8.0D;
                maxY = ceilingY - 1.0D;
            } else {
                minY = groundY + 2.0D;
                maxY = limit > 0
                        ? Math.min(groundY + limit, ceilingY - 1.0D)
                        : ceilingY - 1.0D;
            }

            if (maxY < minY) maxY = minY;

            if (target.y < minY) target = new Vec3(target.x, minY, target.z);
            if (target.y > maxY) target = new Vec3(target.x, maxY, target.z);

            // Void protection
            int minBuildHeight = this.parentEntity.level().getMinBuildHeight();
            if (this.isOverVoid(target) && target.y < minBuildHeight + 16) {
                target = new Vec3(target.x, minBuildHeight + 24, target.z);
            }

            return target;
        }

        // ── Clearance & safety checks ────────────────────────────────────────

        /**
         * Returns true if the position is flyable:
         *  - The block itself and the one above are air (vertical clearance)
         *  - Not a diagonal wall squeeze (X and Z both blocked)
         *  - No lava at or immediately adjacent to the position
         */
        private boolean hasClearanceAt(Vec3 pos) {
            int bx = Mth.floor(pos.x);
            int by = Mth.floor(pos.y);
            int bz = Mth.floor(pos.z);
            Level level = this.parentEntity.level();
            BlockPos.MutableBlockPos mp = this.checkPos;

            // Vertical clearance (air blocks cannot be lava, so no separate lava check needed here)
            if (!level.getBlockState(mp.set(bx, by, bz)).isAir()
                    || !level.getBlockState(mp.set(bx, by + 1, bz)).isAir())
                return false;

            // Below: lava check only
            if (level.getBlockState(mp.set(bx, by - 1, bz)).is(Blocks.LAVA))
                return false;

            // Cardinal: fetch once, check lava and cache air state for squeeze detection
            BlockState north = level.getBlockState(mp.set(bx, by, bz - 1));
            if (north.is(Blocks.LAVA)) return false;
            BlockState south = level.getBlockState(mp.set(bx, by, bz + 1));
            if (south.is(Blocks.LAVA)) return false;
            BlockState east  = level.getBlockState(mp.set(bx + 1, by, bz));
            if (east.is(Blocks.LAVA)) return false;
            BlockState west  = level.getBlockState(mp.set(bx - 1, by, bz));
            if (west.is(Blocks.LAVA)) return false;

            // Diagonal wall squeeze: reject if both X and Z axes are walled in
            boolean blockedX = !east.isAir() || !west.isAir();
            boolean blockedZ = !north.isAir() || !south.isAir();
            return !(blockedX && blockedZ);
        }

        /**
         * Counts how many of {@code samples} equally-spaced points along the path
         * from {@code from} to {@code to} pass hasClearanceAt().
         * Used for both reachability scoring and escape scoring.
         */
        private int countSafeSamples(Vec3 from, Vec3 to, int samples) {
            int count = 0;
            for (int i = 1; i <= samples; i++) {
                Vec3 sample = from.lerp(to, (double) i / samples);
                if (this.hasClearanceAt(sample))
                    count++;
            }
            return count;
        }

        private boolean isOverVoid(Vec3 pos) {
            BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(
                    (int) pos.x, (int) pos.y, (int) pos.z);

            for (int i = 0; i < 40; i++) {
                check.move(0, -1, 0);
                if (!this.parentEntity.level().isEmptyBlock(check))
                    return false;
            }
            return true;
        }

        // ── Cached ground / ceiling scan ─────────────────────────────────────

        private double getGroundY(BlockPos origin) {
            this.groundYCacheTimer--;
            if (Double.isNaN(this.cachedGroundY) || this.groundYCacheTimer <= 0) {
                this.cachedGroundY = this.scanGroundY(origin);
                this.groundYCacheTimer = GROUND_Y_CACHE_INTERVAL;
            }
            return this.cachedGroundY;
        }

        private double getCeilingY(BlockPos origin) {
            this.ceilingYCacheTimer--;
            if (Double.isNaN(this.cachedCeilingY) || this.ceilingYCacheTimer <= 0) {
                this.cachedCeilingY = this.scanCeilingY(origin);
                this.ceilingYCacheTimer = CEILING_Y_CACHE_INTERVAL;
            }
            return this.cachedCeilingY;
        }

        private double scanGroundY(BlockPos origin) {
            BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(
                    origin.getX(), origin.getY(), origin.getZ());
            int minY = this.parentEntity.level().getMinBuildHeight();

            while (check.getY() > minY) {
                check.move(0, -1, 0);
                if (!this.parentEntity.level().isEmptyBlock(check))
                    return check.getY() + 1.0D;
            }
            return NO_GROUND;
        }

        private double scanCeilingY(BlockPos origin) {
            BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(
                    origin.getX(), origin.getY(), origin.getZ());
            int maxScan       = origin.getY() + 64;
            int maxBuildHeight = this.parentEntity.level().getMaxBuildHeight();

            while (check.getY() < Math.min(maxScan, maxBuildHeight)) {
                check.move(0, 1, 0);
                if (!this.parentEntity.level().isEmptyBlock(check))
                    return check.getY();
            }
            return Math.min(maxScan, maxBuildHeight);
        }

    }
        static class FlyingMoveHelper extends MoveControl {
        private final FlyingMobEntity parentEntity;

        private Vec3 velocity = Vec3.ZERO;

        private static final double ACCELERATION   = 0.15D;
        private static final double DRAG           = 0.90D;
        private static final double MAX_Y_SPEED    = 0.3D;
        // Degrees/tick the flight heading is allowed to turn toward a new target direction - the
        // actual "gradual path correction" (previously desiredVelocity, direction and all, was
        // lerped straight at ACCELERATION's 15%/tick, which could swing the nose most of the way
        // around within 2-3 ticks for a sharp turn). Horizontal (yaw) only; vertical speed still
        // adjusts freely below, "向左向右" being the reported case.
        private static final float MAX_TURN_RATE   = 6.0F;

        // Consecutive blocked ticks — used to escalate avoidance strength
        private int blockedTicks = 0;
        private static final int BLOCKED_ESCALATE  = 10;

        // Set while stepping aside for aiStep()'s grounded-ambient-rest handling (see the
        // !isNoGravity() branch below), so the very next active tick can tell it just left that
        // state and resync `velocity` before computing anything.
        private boolean restingOnGround = false;

        // Pre-built probe directions: 8 horizontal angles x 3 Y tilts = 24 vectors.
        // Built once as unit vectors; scaled to current speed each tick.
        private static final double[][] PROBE_DIRS;
        static {
            double[] angles = { 0, Math.PI/4, Math.PI/2, Math.PI*3/4,
                                Math.PI, Math.PI*5/4, Math.PI*3/2, Math.PI*7/4 };
            double[] yTilts = { 0.0D, 0.25D, -0.25D };
            PROBE_DIRS = new double[angles.length * yTilts.length][3];
            int idx = 0;
            for (double a : angles) {
                for (double y : yTilts) {
                    double len = Math.sqrt(Math.cos(a)*Math.cos(a) + y*y + Math.sin(a)*Math.sin(a));
                    PROBE_DIRS[idx][0] = Math.cos(a) / len;
                    PROBE_DIRS[idx][1] = y / len;
                    PROBE_DIRS[idx][2] = Math.sin(a) / len;
                    idx++;
                }
            }
        }

        public FlyingMoveHelper(FlyingMobEntity flyer) {
            super(flyer);
            this.parentEntity = flyer;
        }

        @Override
        public void tick() {
            // When something else is driving (e.g. a player-steered mount), yield control entirely
            if (this.parentEntity.suspendAiMovement()) {
                this.velocity = Vec3.ZERO;
                this.operation = MoveControl.Operation.WAIT;
                this.restingOnGround = false;
                return;
            }

            // Sitting (no combat target): halt horizontal movement immediately so a sit command
            // aborts the current wander/path right away instead of coasting to the old target.
            // AIRandomFly drives this MoveControl directly, so switchState's navigation.stop()
            // alone won't stop it — we must zero the velocity here. Keep sinking while airborne
            // so it still settles onto the ground.
            if (this.parentEntity.isInSittingPose() && this.parentEntity.getTarget() == null) {
                double vy = this.parentEntity.getDeltaMovement().y;
                this.parentEntity.setDeltaMovement(0.0D, this.parentEntity.onGround() ? vy : vy - 0.03D, 0.0D);
                this.velocity = Vec3.ZERO;
                this.operation = MoveControl.Operation.WAIT;
                this.restingOnGround = false;
                return;
            }

            // Grounded ambient rest: a wild (non-tame) flyer with no target settles onto the
            // ground with gravity briefly re-enabled (see aiStep()'s land/hop toggle) until the
            // next random hop. Stepping out of the way here instead of steering toward an airborne
            // wanted position avoids fighting travel()'s forced -0.15D fall — without this, the
            // mob would visibly skid/slide across the ground toward the target's X/Z every tick
            // it's grounded. Leave `operation`/`velocity` untouched (don't reset to WAIT) so
            // AIRandomFly's hasWanted() check still sees a pending target and doesn't re-pick
            // every single tick; normal MOVE_TO handling resumes the instant gravity is disabled.
            if (!this.parentEntity.isNoGravity()) {
                this.restingOnGround = true;
                return;
            }

            if (this.restingOnGround) {
                // We just left grounded rest — most likely this exact tick's aiStep() rolled the
                // random hop and added an upward boost straight into deltaMovement via
                // setDeltaMovement() (aiStep() runs *after* this MoveControl's tick() within the
                // same super.aiStep() call, so the boost isn't visible yet on the tick it's added).
                // Our own `velocity` field is stale from before landing — resyncing it from the
                // entity's actual current deltaMovement means the lerp below blends from the boost
                // instead of silently overwriting it, which is why the mob used to look like it
                // kept trying to hop up only to be immediately yanked back down.
                this.velocity = this.parentEntity.getDeltaMovement();
                this.restingOnGround = false;
            }

            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.velocity = this.velocity.scale(0.8D);
                this.parentEntity.setDeltaMovement(this.velocity);
                this.blockedTicks = 0;
                return;
            }

            Vec3 currentPos = this.parentEntity.position();
            Vec3 targetPos  = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
            Vec3 toTarget   = targetPos.subtract(currentPos);
            double distance = toTarget.length();

            if (distance < 0.5D) {
                this.operation = MoveControl.Operation.WAIT;
                this.blockedTicks = 0;
                return;
            }

            Vec3 desiredDirection = toTarget.normalize();

            // Cosmetic oscillation (very small, does not affect navigation)
            double oscillation = Math.sin(this.parentEntity.tickCount * 0.3D) * 0.03D;
            desiredDirection = new Vec3(
                    desiredDirection.x,
                    desiredDirection.y + oscillation,
                    desiredDirection.z).normalize();

            // Cosmetic side drift
            Vec3 side = new Vec3(-desiredDirection.z, 0.0D, desiredDirection.x);
            desiredDirection = desiredDirection.add(
                    side.scale((this.parentEntity.random.nextDouble() - 0.5D) * 0.03D)
            ).normalize();

            // Turn-rate-limited heading: steer the horizontal (yaw) component of the flight
            // direction toward desiredDirection by at most MAX_TURN_RATE this tick, instead of
            // letting the lerp below swing the whole vector - direction included - straight at
            // desiredDirection. "Current heading" is read back off this.velocity itself (falling
            // back to the entity's own yaw if nearly stationary) rather than a separately tracked
            // field, so it self-corrects even if something else nudges velocity between ticks.
            double horizSpeed = Math.sqrt(this.velocity.x * this.velocity.x + this.velocity.z * this.velocity.z);
            float currentYawDeg = horizSpeed > 1.0E-4D
                    ? (float) (Mth.atan2(this.velocity.z, this.velocity.x) * (180D / Math.PI)) - 90.0F
                    : this.mob.getYRot();
            float desiredYawDeg = (float) (Mth.atan2(desiredDirection.z, desiredDirection.x) * (180D / Math.PI)) - 90.0F;
            float steeredYawDeg = this.rotlerp(currentYawDeg, desiredYawDeg, MAX_TURN_RATE);

            double headingRad = Math.toRadians((double) steeredYawDeg + 90.0D);
            double desiredHorizLen = Math.sqrt(desiredDirection.x * desiredDirection.x + desiredDirection.z * desiredDirection.z);
            Vec3 steeredDirection = new Vec3(Math.cos(headingRad) * desiredHorizLen, desiredDirection.y, Math.sin(headingRad) * desiredHorizLen);
            steeredDirection = steeredDirection.lengthSqr() > 1.0E-6D ? steeredDirection.normalize() : desiredDirection;

            Vec3 desiredVelocity = steeredDirection.scale(
                    0.4D * this.parentEntity.getAttributeValue(Attributes.FLYING_SPEED));

            this.velocity = this.velocity.lerp(desiredVelocity, ACCELERATION);
            this.velocity = this.velocity.scale(DRAG);

            if (this.velocity.y > MAX_Y_SPEED)
                this.velocity = new Vec3(this.velocity.x, MAX_Y_SPEED, this.velocity.z);

            // ── Collision avoidance ──────────────────────────────────────────
            // Wall-contact detection: compare velocity we WANT to apply vs what
            // Minecraft actually allows. If the entity is being stopped by a wall,
            // the actual deltaMovement after move() will be much smaller than the
            // velocity we set. We detect this by checking isPathClear BEFORE applying.
            boolean wallContact = !this.isPathClear(this.velocity);

            if (wallContact) {
                this.blockedTicks++;

                double spd     = Math.max(this.velocity.length(), 0.05D);
                Vec3 bestDir   = null;
                double bestDot = -2.0D;

                for (double[] d : PROBE_DIRS) {
                    Vec3 probe = new Vec3(d[0] * spd, d[1] * spd, d[2] * spd);

                    if (!this.isPathClear(probe))
                        continue;

                    // Reject probes heading into lava
                    Vec3 probePos    = currentPos.add(probe);
                    BlockPos probeBlock = BlockPos.containing(probePos.x, probePos.y, probePos.z);
                    if (this.parentEntity.level().getBlockState(probeBlock)
                            .is(net.minecraft.world.level.block.Blocks.LAVA))
                        continue;

                    double dot = new Vec3(d[0], d[1], d[2]).dot(desiredDirection);
                    if (dot > bestDot) {
                        bestDot = dot;
                        bestDir = probe;
                    }
                }

                if (bestDir != null) {
                    // Commit harder to the detour the longer we have been blocked.
                    // Using a higher base blend (0.6) so the detour takes effect
                    // within 1-2 ticks rather than slowly lerping over many ticks.
                    double blend = Math.min(1.0D, this.blockedTicks / (double) BLOCKED_ESCALATE);
                    this.velocity = this.velocity.lerp(bestDir, 0.6D + blend * 0.3D);
                } else {
                    // No clear probe found: full brake. AIRandomFly.escape() will
                    // trigger once stuckTicks exceeds STUCK_THRESHOLD.
                    this.velocity = Vec3.ZERO;
                    this.parentEntity.setDeltaMovement(Vec3.ZERO);
                    return;
                }
            } else {
                this.blockedTicks = 0;
            }
            // ────────────────────────────────────────────────────────────────

            this.parentEntity.setDeltaMovement(this.velocity);

            // Yaw just mirrors the already turn-rate-limited velocity heading directly (see
            // MAX_TURN_RATE above) - no second independent smoothing pass needed here anymore, so
            // the model's nose always points exactly where it's actually flying, the way a real
            // bird/plane's does. This is also what FlyingMobEntity#getBankAngle's client-side roll
            // is derived from (via consecutive yRot samples), so it stays in sync automatically.
            float yaw = (float)(Mth.atan2(this.velocity.z, this.velocity.x) * (180F / Math.PI)) - 90.0F;
            this.mob.setYRot(yaw);
        }

        private boolean isPathClear(Vec3 movement) {
            AABB box     = this.parentEntity.getBoundingBox();
            AABB nextBox = box.move(movement);
            return this.parentEntity.level().noCollision(this.parentEntity, nextBox);
        }
    }
        
    static class AIFlyingAttackMelee extends FURMeleeAttackGoal {

        public AIFlyingAttackMelee(PathfinderMob creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(this.mob.getBbWidth() * this.mob.getBbWidth()
                    + attackTarget.getBbWidth() * attackTarget.getBbWidth());
        }

    }
}