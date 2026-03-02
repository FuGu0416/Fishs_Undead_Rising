package com.Fishmod.fur.entities.flying;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;

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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
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
    	/*if (SpawnUtil.getHeight(this).getY() > 0 || this.isBaby()) {   		
    		this.setNoGravity(false);
    	}*/
    	
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
        private final int voidCheckDepth = 12;

        public AIRandomFly(FlyingMobEntity entity, double speed) {
            this.parentEntity = entity;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.parentEntity.getTarget() != null)
                return false;

            MoveControl move = this.parentEntity.getMoveControl();

            if (!move.hasWanted())
                return true;

            double dx = move.getWantedX() - this.parentEntity.getX();
            double dy = move.getWantedY() - this.parentEntity.getY();
            double dz = move.getWantedZ() - this.parentEntity.getZ();
            double distSq = dx * dx + dy * dy + dz * dz;

            return distSq < 2.0D || distSq > 400.0D;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {

            for (int i = 0; i < 5; i++) {
                Vec3 target = findAirPosition();

                if (target == null)
                    continue;

                target = adjustHeightSafety(target);

                this.parentEntity.getMoveControl().setWantedPosition(target.x + 0.5D, target.y + 0.5D, target.z + 0.5D, this.speed);

                this.parentEntity.getLookControl().setLookAt(target.x, target.y, target.z, 180.0F, 20.0F);

                break;
            }
        }

        @Nullable
        private Vec3 findAirPosition() {
            Vec3 view = this.parentEntity.getViewVector(0.0F);
            Vec3 pos = HoverRandomPos.getPos(this.parentEntity, horizontalRange, verticalRange, view.x, view.z, (float) Math.PI / 2F, 3, 1);

            if (pos != null)
                return pos;

            return AirAndWaterRandomPos.getPos(this.parentEntity, horizontalRange, verticalRange, -2, view.x, view.y, view.z);
        }

        private Vec3 adjustHeightSafety(Vec3 target) {
            double currentY = this.parentEntity.getY();
            double limit = FURConfig.FlyingHeight_limit.get();

            if (limit > 0) {
                double minY = currentY - limit;
                double maxY = currentY + limit;

                if (target.y < minY)
                    target = new Vec3(target.x, minY, target.z);

                if (target.y > maxY)
                    target = new Vec3(target.x, maxY, target.z);
            }

            if (isOverVoid(target)) {
                double safeHeight = Math.max(currentY + 4.0D, 40.0D);
                target = new Vec3(target.x, safeHeight, target.z);
            }

            if (target.y < -20) {
                target = new Vec3(target.x, 50, target.z);
            }

            return target;
        }

        private boolean isOverVoid(Vec3 pos) {
            BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(pos.x, pos.y, pos.z);

            for (int i = 0; i < this.voidCheckDepth; i++) {
                checkPos.move(0, -1, 0);

                if (!this.parentEntity.level().isEmptyBlock(checkPos)) {
                    return false;
                }
            }

            return true;
        }
    }
    
    static class FlyingMoveHelper extends MoveControl {
        private final FlyingMobEntity parentEntity;
        private int courseChangeCooldown;
		double entityMoveSpeed;
		
        public FlyingMoveHelper(FlyingMobEntity flyer) {
            super(flyer);
            this.parentEntity = flyer;
            this.entityMoveSpeed = flyer.getAttributeValue(Attributes.FLYING_SPEED);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
                    Vec3 vector3d = new Vec3(this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
                    double d0 = vector3d.length();
                    vector3d = vector3d.normalize();
                    if (this.isNotColliding(this.wantedX, this.wantedY, this.wantedZ, d0) && this.isNotPassengerColliding(this.wantedX, this.wantedY, this.wantedZ, d0)) {
                        this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(this.entityMoveSpeed)));
                        
                        float yaw = (float)(Mth.atan2(this.wantedZ - this.parentEntity.getZ(), this.wantedX - this.parentEntity.getX()) * (180D / Math.PI)) - 90.0F;
                        this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yaw, Movement2RotationAngle(entityMoveSpeed)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }
                
    	        if (this.parentEntity.isInSittingPose() && this.parentEntity.getTarget() == null) {
    	        	this.operation = MoveControl.Operation.WAIT;
    	        }
            }
        }
        
        private float Movement2RotationAngle(double movement) {
        	return (float) (movement * 1214.2857F - 31.428571428571427F);
        }

        /**
         * Checks if the lowest passenger's entity bounding box is not colliding with terrain
         */
        private boolean isNotPassengerColliding(double x, double y, double z, double distance) {
            if (this.parentEntity.getPassengers().isEmpty()) {
                return true;
            }

            double d0 = (x - this.parentEntity.getX()) / distance;
            double d1 = (y - this.parentEntity.getY()) / distance;
            double d2 = (z - this.parentEntity.getZ()) / distance;
            Entity lowestPassenger = this.parentEntity.getLowestPassenger();
            AABB axisalignedbb = lowestPassenger.getBoundingBox();

            for (int i = 1; (double)i < distance; ++i) {
                axisalignedbb = axisalignedbb.move(d0, d1, d2);

                if (!lowestPassenger.level().noCollision(lowestPassenger, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }


        /**
         * Checks if entity bounding box is not colliding with terrain
         */
        private boolean isNotColliding(double x, double y, double z, double distance) {
            double d0 = (x - this.parentEntity.getX()) / distance;
            double d1 = (y - this.parentEntity.getY()) / distance;
            double d2 = (z - this.parentEntity.getZ()) / distance;
            AABB axisalignedbb = this.parentEntity.getBoundingBox();

            for (int i = 1; (double)i < distance; ++i) {
                axisalignedbb = axisalignedbb.move(d0, d1, d2);

                if (!this.parentEntity.level().noCollision(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }
    
    static class AIFlyingAttackMelee extends MeleeAttackGoal {

		public AIFlyingAttackMelee(PathfinderMob creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}

	    protected double getAttackReachSqr(LivingEntity attackTarget) {
	        return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + attackTarget.getBbWidth() * attackTarget.getBbWidth());
	    }
    	
    }

}