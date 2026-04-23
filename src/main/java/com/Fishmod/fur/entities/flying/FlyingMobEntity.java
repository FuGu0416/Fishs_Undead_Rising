package com.Fishmod.fur.entities.flying;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
	
	public FlyingMobEntity(EntityType<? extends FlyingMobEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
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
		this.goalSelector.addGoal(2, new AIFlyingAttackMelee(this, 1.0D, true));		
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}
	
    public static boolean checkFlyerSpawnRules(EntityType<? extends FlyingMobEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
    	return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_)
        		&& (p_223316_1_.canSeeSky(p_223316_3_) || ((Level) p_223316_1_).dimension() != Level.OVERWORLD);
    }
    
    public static boolean checkFlyerSpawnRulesNoSky(EntityType<? extends FlyingMobEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
    	return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    public static boolean checkFlyerSpawnRulesNoRestriction(EntityType<? extends FlyingMobEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
    	return p_223316_1_.getDifficulty() != Difficulty.PEACEFUL && FURTameableEntity.isDarkEnoughToSpawn(p_223316_1_, p_223316_3_, p_223316_4_);
    }
    
    @Override
    public void doSitCommand(Player playerIn) {
    	super.doSitCommand(playerIn);
    }
    
    @Override
    public void doFollowCommand(Player playerIn) {
    	if (!this.isBaby()) {
    		this.setNoGravity(true);
    	}
    	
    	super.doFollowCommand(playerIn);
    }
    
    @Override
    public void doWanderCommand(Player playerIn) {
    	if (!this.isBaby()) {
    		this.setNoGravity(true);
    	}
    	
    	super.doWanderCommand(playerIn);
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
		super.aiStep();

		if (!this.level().isClientSide && !this.isBaby()) {
	    	if (this.onGround()) {
	    		if (this.getLandTimer() < 20) {
	    			this.setLandTimer(this.getLandTimer() + 1);
	    			this.level().broadcastEntityEvent(this, (byte)40);
	    		}
	    		
	    		if (this.isNoGravity()) {
	    			this.setNoGravity(this.getTarget() != null);
	    		}
	    		
	    		if (!this.isNoGravity() && !this.isInSittingPose() && this.getRandom().nextFloat() < 0.15F) {
	    			this.setNoGravity(true);
	    			this.setDeltaMovement(this.getDeltaMovement().add(0.0F, 0.25F, 0.0F));
	    		}
	    	} else {
	    		if (this.getLandTimer() > 0) {
	    			this.setLandTimer(this.getLandTimer() - 1);
	    			this.level().broadcastEntityEvent(this, (byte)41);
	    		}
	    		
	    		if (!this.isNoGravity()) {
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
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 40)  {
    		if (this.getLandTimer() < 20) {
    			this.setLandTimer(this.getLandTimer() + 1);
    		}
        } else if (id == 41)  {
    		if (this.getLandTimer() > 0) {
    			this.setLandTimer(this.getLandTimer() - 1);
    		}
        } else {
            super.handleEntityEvent(id);
        }
    }
	
    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
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
	
	public void makeStuckInBlock(BlockState p_213295_1_, Vec3 p_213295_2_) {
		if (p_213295_1_.is(Blocks.COBWEB)) {
			this.getMoveControl().setWantedPosition(this.getX(), this.getY() - 1, this.getZ(), 1.0D);
		}
			
		super.makeStuckInBlock(p_213295_1_, p_213295_2_);
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	}
	
	protected double VehicleSpeedMod() {
		return 1.0D;
	}

    public void travel(Vec3 p_213352_1_) {
    	// If the lowest passenger is colliding with the ground, get them out!
        Entity lowestPassenger = this.getLowestPassenger();

        if (lowestPassenger != null && !(lowestPassenger instanceof Player)) {
            AABB passengerBounds = lowestPassenger.getBoundingBox();

            if (!lowestPassenger.level().noCollision(lowestPassenger, passengerBounds)) {
            	this.getMoveControl().setWantedPosition(this.getX(), this.getY() + lowestPassenger.getBbHeight(), this.getZ(), 1.0D);
            }            
        }
        
        if (!this.isNoGravity() && !this.isVehicle() && !(this.getControllingPassenger() instanceof Player)) {
        	this.moveRelative(0.02F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().x, -0.15D, this.getDeltaMovement().z);
        }
    	
    	if (this.getTarget() != null) {
            this.moveRelative(0.02F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(1.05D));
    	}
    	
    	if (this.isInWater()) {
            this.moveRelative(0.02F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = this.blockPosition().below();
            float f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround()) {
               f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, p_213352_1_);
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
    
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag p_213386_5_) {         
    	if (!this.isBaby()) {
    		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));       
    	}
        return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
    }
       
    class WanderGoal extends Goal {
        WanderGoal() {
           this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
           return FlyingMobEntity.this.navigation.isDone() && FlyingMobEntity.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
           return FlyingMobEntity.this.navigation.isInProgress();
        }

        public void start() {
           Vec3 vector3d = this.findPos();
           if (vector3d != null) {
              FlyingMobEntity.this.navigation.moveTo(FlyingMobEntity.this.navigation.createPath(new BlockPos((int)vector3d.x, (int)vector3d.y, (int)vector3d.z), 1), 1.0D);
           }

        }

        @Nullable
        private Vec3 findPos() {
           Vec3 vector3d;
           vector3d = FlyingMobEntity.this.getViewVector(0.0F);
           Vec3 vector3d2 = HoverRandomPos.getPos(FlyingMobEntity.this, 8, 7, vector3d.x, vector3d.z, ((float)Math.PI / 2F), 2, 1);
           return vector3d2 != null ? vector3d2 : AirAndWaterRandomPos.getPos(FlyingMobEntity.this, 8, 4, -2, vector3d.x, vector3d.y, vector3d.z);
        }
	}
    
    static class AIRandomFly extends Goal {
        private final FlyingMobEntity parentEntity;
        private final double speed;
        private final int horizontalRange = 10;
        private final int verticalRange = 6;

        // Stuck detection
        private Vec3 lastPos = Vec3.ZERO;
        private int stuckTicks = 0;
        private static final int STUCK_THRESHOLD = 40;
        private static final double MIN_MOVE_SQ = 0.002D;

        // Ground Y cache (~5 sec refresh)
        private double cachedGroundY = Double.NaN;
        private int groundYCacheTimer = 0;
        private static final int GROUND_Y_CACHE_INTERVAL = 100;

        // Ceiling Y cache (~3 sec refresh)
        private double cachedCeilingY = Double.NaN;
        private int ceilingYCacheTimer = 0;
        private static final int CEILING_Y_CACHE_INTERVAL = 60;

        public AIRandomFly(FlyingMobEntity entity, double speed) {
            this.parentEntity = entity;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.parentEntity.getTarget() == null
                    && !(this.parentEntity.getNavigation() instanceof GroundPathNavigation)
                    && !this.parentEntity.isInSittingPose();
        }

        @Override
        public boolean canContinueToUse() {
            return true;
        }

        @Override
        public void start() {
            this.lastPos = this.parentEntity.position();
            this.stuckTicks = 0;
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

            if (this.stuckTicks > STUCK_THRESHOLD) {
                this.escape(current);
                this.stuckTicks = 0;
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
         * Three-phase escape strategy:
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
         * Phase 3 — Hard velocity reset: if both phases fail (fully boxed in),
         *   zero out deltaMovement and set a very short random nearby target so
         *   FlyingMoveHelper starts fresh without carrying the old stuck velocity.
         */
        private void escape(Vec3 current) {
            double groundY  = this.getGroundY(this.parentEntity.blockPosition());
            double ceilingY = this.getCeilingY(this.parentEntity.blockPosition());
            double midY     = (groundY + ceilingY) / 2.0D;
            Level  level    = this.parentEntity.level();

            // ── Phase 1: upward burst ────────────────────────────────────────
            // Check 4 blocks directly above. If they are all clear (and not lava),
            // inject a direct upward impulse, bypassing MoveControl's lerp entirely.
            BlockPos above = this.parentEntity.blockPosition();
            boolean canGoUp = true;
            for (int i = 1; i <= 4; i++) {
                BlockPos check = above.above(i);
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

            // ── Phase 3: hard velocity reset ─────────────────────────────────
            // Completely boxed in. Zero out velocity so the entity stops fighting
            // the walls, then pick a random near target 2 blocks away so that the
            // MoveControl operation is reset to MOVE_TO on the next evaluation.
            this.parentEntity.setDeltaMovement(Vec3.ZERO);
            double rx = current.x + (this.parentEntity.getRandom().nextDouble() - 0.5D) * 2.0D;
            double rz = current.z + (this.parentEntity.getRandom().nextDouble() - 0.5D) * 2.0D;
            this.parentEntity.getMoveControl().setWantedPosition(rx, midY, rz, this.speed);
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
            // No valid target found this tick; keep current target until next tick.
        }

        @Nullable
        private Vec3 findAirPosition() {
            Vec3 view = this.parentEntity.getViewVector(0.0F);
            Vec3 pos = HoverRandomPos.getPos(
                    this.parentEntity, this.horizontalRange, this.verticalRange,
                    view.x, view.z, (float) Math.PI / 2F, 3, 1);

            if (pos != null)
                return pos;

            return AirAndWaterRandomPos.getPos(
                    this.parentEntity, this.horizontalRange, this.verticalRange,
                    -2, view.x, view.y, view.z);
        }

        // ── Height safety ────────────────────────────────────────────────────

        private Vec3 clampToSafeHeight(Vec3 target) {
            double limit    = FURConfig.FlyingHeight_limit.get();
            double groundY  = this.getGroundY(this.parentEntity.blockPosition());
            double ceilingY = this.getCeilingY(this.parentEntity.blockPosition());

            double minY = groundY + 2.0D;
            double maxY = limit > 0
                    ? Math.min(groundY + limit, ceilingY - 1.0D)
                    : ceilingY - 1.0D;

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
            BlockPos base  = BlockPos.containing(pos.x, pos.y, pos.z);
            Level    level = this.parentEntity.level();

            // Vertical clearance
            if (!level.isEmptyBlock(base) || !level.isEmptyBlock(base.above()))
                return false;

            // Lava check: reject if the block itself or any face-adjacent block is lava
            if (this.isLava(level, base)
                    || this.isLava(level, base.above())
                    || this.isLava(level, base.below())
                    || this.isLava(level, base.north())
                    || this.isLava(level, base.south())
                    || this.isLava(level, base.east())
                    || this.isLava(level, base.west()))
                return false;

            // Diagonal wall squeeze: reject if both X and Z axes are walled in
            boolean blockedX = !level.isEmptyBlock(base.east()) || !level.isEmptyBlock(base.west());
            boolean blockedZ = !level.isEmptyBlock(base.north()) || !level.isEmptyBlock(base.south());

            return !(blockedX && blockedZ);
        }

        /**
         * Returns true if the block at pos is lava (source or flowing).
         */
        private boolean isLava(Level level, BlockPos pos) {
            return level.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.LAVA)
                || level.getBlockState(pos).is(net.minecraft.world.level.block.Blocks.LAVA);
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
            return minY;
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

        // Consecutive blocked ticks — used to escalate avoidance strength
        private int blockedTicks = 0;
        private static final int BLOCKED_ESCALATE  = 10;

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
            // Sitting with no target: glide downward
            if (this.parentEntity.isInSittingPose()
                    && this.parentEntity.getTarget() == null
                    && !this.parentEntity.onGround()) {
                Vec3 motion = this.parentEntity.getDeltaMovement();
                this.parentEntity.setDeltaMovement(motion.x * 0.5D, motion.y - 0.03D, motion.z * 0.5D);
                this.operation = MoveControl.Operation.WAIT;
                return;
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
                    side.scale((this.parentEntity.getRandom().nextDouble() - 0.5D) * 0.03D)
            ).normalize();

            Vec3 desiredVelocity = desiredDirection.scale(
                    0.4D * this.parentEntity.getAttributeValue(Attributes.FLYING_SPEED));

            this.velocity = this.velocity.lerp(desiredVelocity, ACCELERATION);
            this.velocity = this.velocity.scale(DRAG);

            if (this.velocity.y > MAX_Y_SPEED)
                this.velocity = new Vec3(this.velocity.x, MAX_Y_SPEED, this.velocity.z);

            // ── Collision avoidance ──────────────────────────────────────────
            if (!this.isPathClear(this.velocity)) {
                this.blockedTicks++;

                double spd     = Math.max(this.velocity.length(), 0.05D);
                Vec3 bestDir   = null;
                double bestDot = -2.0D;

                // Test pre-built probe directions scaled to current speed.
                // Also reject any probe heading into lava.
                for (double[] d : PROBE_DIRS) {
                    Vec3 probe = new Vec3(d[0] * spd, d[1] * spd, d[2] * spd);

                    if (!this.isPathClear(probe))
                        continue;

                    // Reject probes that move into lava
                    Vec3 probePos = currentPos.add(probe);
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
                    // Escalate blend the longer we are blocked, so we commit harder to the detour
                    double blend = Math.min(1.0D, this.blockedTicks / (double) BLOCKED_ESCALATE);
                    this.velocity = this.velocity.lerp(bestDir, 0.4D + blend * 0.4D);
                } else {
                    // Completely boxed in: brake sharply, let AIRandomFly escape() take over
                    this.velocity = this.velocity.scale(0.2D);
                }
            } else {
                this.blockedTicks = 0;
            }
            // ────────────────────────────────────────────────────────────────

            this.parentEntity.setDeltaMovement(this.velocity);

            // Smooth yaw rotation
            float yaw = (float)(Mth.atan2(this.velocity.z, this.velocity.x) * (180F / Math.PI)) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yaw, 10.0F));
        }

        private boolean isPathClear(Vec3 movement) {
            AABB box     = this.parentEntity.getBoundingBox();
            AABB nextBox = box.move(movement);
            return this.parentEntity.level().noCollision(this.parentEntity, nextBox);
        }
    }
        static class AIFlyingAttackMelee extends MeleeAttackGoal {

        public AIFlyingAttackMelee(PathfinderMob creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(this.mob.getBbWidth() * this.mob.getBbWidth()
                    + attackTarget.getBbWidth() * attackTarget.getBbWidth());
        }

    }

}