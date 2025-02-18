package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlyingMobEntity extends FURTameableEntity implements IAggressive {
	private int attackTimer;
	private int hoverTimer;
	private int landTimer;
	
	public FlyingMobEntity(EntityType<? extends FlyingMobEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.moveControl = new FlyingMobEntity.FlyingMoveHelper(this);
	    this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
	    this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
	}
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.setNoGravity(true);
	}
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new AIFlyingAttackMelee(this, 1.0D, true));		
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}
	
    public static boolean checkFlyerSpawnRules(EntityType<? extends FlyingMobEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
    	return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_)
        		&& (p_223316_1_.canSeeSky(p_223316_3_) || ((World) p_223316_1_).dimension() != World.OVERWORLD);
    }
    
    public static boolean checkFlyerSpawnRulesNoSky(EntityType<? extends FlyingMobEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
    	return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    public static boolean checkFlyerSpawnRulesNoRestriction(EntityType<? extends FlyingMobEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
    	return p_223316_1_.getDifficulty() != Difficulty.PEACEFUL && FURTameableEntity.isDarkEnoughToSpawn((IServerWorld) p_223316_1_, p_223316_3_, p_223316_4_);
    }
    
    @Override
    public void doSitCommand(PlayerEntity playerIn) {
    	/*if (SpawnUtil.getHeight(this).getY() > 0 || this.isBaby()) {   		
    		this.setNoGravity(false);
    	}*/
    	
    	super.doSitCommand(playerIn);
    }
    
    @Override
    public void doFollowCommand(PlayerEntity playerIn) {
    	if (!this.isBaby()) {
    		this.setNoGravity(true);
    	}
    	
    	super.doFollowCommand(playerIn);
    }
    
    @Override
    public void doWanderCommand(PlayerEntity playerIn) {
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
		
		if (this.attackTimer > 0) {
            --this.attackTimer;
        }
		
		if (!this.level.isClientSide && !this.isBaby()) {
	    	if (this.isOnGround()) {
	    		if (this.getLandTimer() < 20) {
	    			this.setLandTimer(this.getLandTimer() + 1);
	    			this.level.broadcastEntityEvent(this, (byte)40);
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
	    			this.level.broadcastEntityEvent(this, (byte)41);
	    		}
	    		
	    		if (!this.isNoGravity()) {
	    			this.setNoGravity(true);
	    		}
	    	}
	    		    	
	        if (this.isInSittingPose() && this.getTarget() == null) {	        	
	        	if (!this.isOnGround() && SpawnUtil.getHeight(this).getY() > 0) {
	        		this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.025F, 0.0F));
	        	}
	        }
		}
    }

	@Override
    public boolean doHurtTarget(Entity entityIn) {
		if (this.attackTimer == 0) {
			this.attackTimer = 20;
    		this.level.broadcastEntityEvent(this, (byte)4);
		}
		
        return super.doHurtTarget(entityIn);
    }
    
    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
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
    	if (id == 4)  {
            this.attackTimer = 20;
        } else if (id == 40)  {
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
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
    	return false;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}
    
	@Override
	protected PathNavigator createNavigation(World world) {
		FlyingPathNavigator pathnavigateflying = new FlyingPathNavigator(this, world);
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
	
	public void makeStuckInBlock(BlockState p_213295_1_, Vector3d p_213295_2_) {
		if (p_213295_1_.is(Blocks.COBWEB)) {
			this.getMoveControl().setWantedPosition(this.getX(), this.getY() - 1, this.getZ(), 1.0D);
		}
			
		super.makeStuckInBlock(p_213295_1_, p_213295_2_);
	}
	
	@Override
	protected boolean makeFlySound() {
		return true;
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	}
	
	protected double VehicleSpeedMod() {
		return 1.0D;
	}

    public void travel(Vector3d p_213352_1_) {
    	// If the lowest passenger is colliding with the ground, get them out!
        Entity lowestPassenger = this.getLowestPassenger();

        if (lowestPassenger != null && !(lowestPassenger instanceof PlayerEntity)) {
            AxisAlignedBB passengerBounds = lowestPassenger.getBoundingBox();

            if (!lowestPassenger.level.noCollision(lowestPassenger, passengerBounds)) {
            	this.getMoveControl().setWantedPosition(this.getX(), this.getY() + lowestPassenger.getBbHeight(), this.getZ(), 1.0D);
            }            
        }
        
        if (!this.isNoGravity() && !this.isVehicle() && !(this.getControllingPassenger() instanceof PlayerEntity)) {
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
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            float f = 0.91F;
            if (this.onGround) {
               f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
               f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, p_213352_1_);
            this.move(MoverType.SELF, this.getDeltaMovement().scale(this.VehicleSpeedMod()));
            this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
        }

        this.calculateEntityAnimation(this, false);
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean onClimbable() {    	
        return this.isBaby() ? super.onClimbable() : false;
    }
    
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {         
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
           Vector3d vector3d = this.findPos();
           if (vector3d != null) {
              FlyingMobEntity.this.navigation.moveTo(FlyingMobEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
           }

        }

        @Nullable
        private Vector3d findPos() {
           Vector3d vector3d;
           vector3d = FlyingMobEntity.this.getViewVector(0.0F);
           Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FlyingMobEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
           return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FlyingMobEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
        }
     }
    
    static class AIRandomFly extends Goal {
        private final FlyingMobEntity parentEntity;

        public AIRandomFly(FlyingMobEntity entityFlyingMob) {
            this.parentEntity = entityFlyingMob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            MovementController entitymovehelper = this.parentEntity.getMoveControl();
            
            if (this.parentEntity.getNavigation() instanceof GroundPathNavigator) {
            	return false;
            }
            
            if (!entitymovehelper.hasWanted()) {
                return true;
            } else if (this.parentEntity.getTarget() != null) {
            	return false;
            } else {
                double d0 = entitymovehelper.getWantedX() - this.parentEntity.getX();
                double d1 = entitymovehelper.getWantedY() - this.parentEntity.getY();
                double d2 = entitymovehelper.getWantedZ() - this.parentEntity.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {           
            for (int i = 0; i < 3; ++i) {
            	Vector3d vector3d = this.findPos();
            	       
            	if (vector3d != null) {
            		BlockPos pos = new BlockPos(vector3d.x, vector3d.y, vector3d.z);
            		int groundHeight;
            		
            		if (this.parentEntity.level.dimension() != World.END) {
            			groundHeight = SpawnUtil.getHeight(this.parentEntity.level, pos).getY();
            		} else {
            			groundHeight = this.parentEntity.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos).getY();
            			
            			if (groundHeight == 0) {
            				groundHeight = Math.min(((this.parentEntity.blockPosition().getY() / 16) + 1) * 8, 60);
            			}
            		}
            		
    	            if (this.parentEntity.isInWaterRainOrBubble()) {
    	            	vector3d = new Vector3d(vector3d.x, Math.min(vector3d.y, groundHeight + 3.0D), vector3d.z);       	            
                	} else if (FURConfig.FlyingHeight_limit.get() != 0 && ((vector3d.y > (double)(groundHeight + FURConfig.FlyingHeight_limit.get()) + 4.0D) || (vector3d.y < (double)(groundHeight + FURConfig.FlyingHeight_limit.get()) - 8.0D ))) {
            			vector3d = new Vector3d(vector3d.x, groundHeight + FURConfig.FlyingHeight_limit.get(), vector3d.z);
    	            }
                         
            		this.parentEntity.moveControl.setWantedPosition(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 1.0D);
            		if (this.parentEntity.getTarget() == null) {
            			this.parentEntity.getLookControl().setLookAt(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 180.0F, 20.0F);
            		}
            		
            		break;
            	}
            }
        }
        
        @Nullable
        private Vector3d findPos() {
           Vector3d vector3d;
           vector3d = this.parentEntity.getViewVector(0.0F);
           Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(this.parentEntity, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
           return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(this.parentEntity, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
        }
    }
    
    static class FlyingMoveHelper extends MovementController {
        private final FlyingMobEntity parentEntity;
        private int courseChangeCooldown;
		double entityMoveSpeed;
		
        public FlyingMoveHelper(FlyingMobEntity flyer) {
            super(flyer);
            this.parentEntity = flyer;
            this.entityMoveSpeed = flyer.getAttributeValue(Attributes.FLYING_SPEED);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
                    Vector3d vector3d = new Vector3d(this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
                    double d0 = vector3d.length();
                    vector3d = vector3d.normalize();
                    if (this.isNotColliding(this.wantedX, this.wantedY, this.wantedZ, d0) && this.isNotPassengerColliding(this.wantedX, this.wantedY, this.wantedZ, d0)) {
                        this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(this.entityMoveSpeed)));
                        
                        float yaw = (float)(MathHelper.atan2(this.wantedZ - this.parentEntity.getZ(), this.wantedX - this.parentEntity.getX()) * (180D / Math.PI)) - 90.0F;
                        this.mob.yRot = this.rotlerp(this.mob.yRot, yaw, Movement2RotationAngle(entityMoveSpeed));
                    } else {
                        this.operation = MovementController.Action.WAIT;
                    }
                }
                
    	        if (this.parentEntity.isInSittingPose() && this.parentEntity.getTarget() == null) {
    	        	this.operation = MovementController.Action.WAIT;
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
            AxisAlignedBB axisalignedbb = lowestPassenger.getBoundingBox();

            for (int i = 1; (double)i < distance; ++i) {
                axisalignedbb = axisalignedbb.move(d0, d1, d2);

                if (!lowestPassenger.level.noCollision(lowestPassenger, axisalignedbb)) {
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
            AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

            for (int i = 1; (double)i < distance; ++i) {
                axisalignedbb = axisalignedbb.move(d0, d1, d2);

                if (!this.parentEntity.level.noCollision(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }
    
    static class AIFlyingAttackMelee extends MeleeAttackGoal {

		public AIFlyingAttackMelee(CreatureEntity creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}

	    protected double getAttackReachSqr(LivingEntity attackTarget) {
	        return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + attackTarget.getBbWidth() * attackTarget.getBbWidth());
	    }
    	
    }

}
