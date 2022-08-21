package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.UUID;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EntityAIFollowEntity extends Goal
{
    private final MobEntity tameable;
    private UUID ownerID;
    private LivingEntity owner;
    World world;
    private final double followSpeed;
    private final PathNavigator petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    public EntityAIFollowEntity(MobEntity tameableIn, UUID uniqueIDIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.ownerID = uniqueIDIn;
        this.world = tameableIn.level;
        this.followSpeed = followSpeedIn;
        this.petPathfinder = tameableIn.getNavigation();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;

        if (!(tameableIn.getNavigation() instanceof GroundPathNavigator) && !(tameableIn.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if(this.world instanceof ServerWorld) {
	    	LivingEntity LivingEntity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.world);
	
	        if (LivingEntity == null) {        	
	        	return false;
	        } else if (LivingEntity instanceof PlayerEntity && ((PlayerEntity)LivingEntity).isSpectator()) {
	            return false;
	        } else if (this.tameable.distanceToSqr(LivingEntity) < (double)(this.minDist * this.minDist)) {
	            return false;
	        } else {
	            this.owner = LivingEntity;
	            return true;
	        }
        }
        
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return !this.petPathfinder.isDone() && this.tameable.distanceToSqr(this.owner) > (double)(this.maxDist * this.maxDist);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathfindingMalus(PathNodeType.WATER);
        this.tameable.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.owner = null;
        this.petPathfinder.stop();
        this.tameable.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.tameable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tameable.getMaxHeadXRot());

        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;

            if (!this.tameable.isLeashed() && !this.tameable.isPassenger()) {
                if (this.tameable.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                 } else {
                    this.petPathfinder.moveTo(this.owner, this.followSpeed);
                 }
            }
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();

        for(int i = 0; i < 10; ++i) {
           int j = this.randomIntInclusive(-3, 3);
           int k = this.randomIntInclusive(-1, 1);
           int l = this.randomIntInclusive(-3, 3);
           boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
           if (flag) {
              return;
           }
        }

     }

     private boolean maybeTeleportTo(int p_226328_1_, int p_226328_2_, int p_226328_3_) {
        if (Math.abs((double)p_226328_1_ - this.owner.getX()) < 2.0D && Math.abs((double)p_226328_3_ - this.owner.getZ()) < 2.0D) {
           return false;
        } else if (!this.canTeleportTo(new BlockPos(p_226328_1_, p_226328_2_, p_226328_3_))) {
           return false;
        } else {
           this.tameable.moveTo((double)p_226328_1_ + 0.5D, (double)p_226328_2_, (double)p_226328_3_ + 0.5D, this.tameable.yRot, this.tameable.xRot);
           this.petPathfinder.stop();
           return true;
        }
     }

     private boolean canTeleportTo(BlockPos p_226329_1_) {
        PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(this.world, p_226329_1_.mutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
           return false;
        } else {
           BlockState blockstate = this.world.getBlockState(p_226329_1_.below());
           if (blockstate.getBlock() instanceof LeavesBlock) {
              return false;
           } else {
              BlockPos blockpos = p_226329_1_.subtract(this.tameable.blockPosition());
              return this.world.noCollision(this.tameable, this.tameable.getBoundingBox().move(blockpos));
           }
        }
     }

     private int randomIntInclusive(int p_226327_1_, int p_226327_2_) {
        return this.tameable.getRandom().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
     }
}