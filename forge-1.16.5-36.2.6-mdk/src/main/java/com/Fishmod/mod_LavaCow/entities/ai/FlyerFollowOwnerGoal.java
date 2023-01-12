package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.EnumSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class FlyerFollowOwnerGoal extends Goal {
	private final TameableEntity tamable;
	private LivingEntity owner;
	private final IWorldReader level;
	private final double speedModifier;
	private final PathNavigator navigation;
	private int timeToRecalcPath;
	private final float stopDistance;
	private final float startDistance;
	private float oldWaterCost;
	private final boolean canFly;

	public FlyerFollowOwnerGoal(TameableEntity p_i225711_1_, double p_i225711_2_, float p_i225711_4_, float p_i225711_5_, boolean p_i225711_6_) {
		this.tamable = p_i225711_1_;
		this.level = p_i225711_1_.level;
		this.speedModifier = p_i225711_2_;
		this.navigation = p_i225711_1_.getNavigation();
		this.startDistance = p_i225711_4_;
		this.stopDistance = p_i225711_5_;
		this.canFly = p_i225711_6_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		if (!(p_i225711_1_.getNavigation() instanceof GroundPathNavigator) && !(p_i225711_1_.getNavigation() instanceof FlyingPathNavigator)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}

	public boolean canUse() {
		LivingEntity livingentity = this.tamable.getOwner();
		if (livingentity == null) {
			return false;
		} else if (livingentity.isSpectator()) {
			return false;
		} else if (this.tamable.isOrderedToSit()) {
			return false;
		} else if (this.tamable.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
			return false;
		} else {
			this.owner = livingentity;
			return true;
		}
	}

	public boolean canContinueToUse() {
		if (this.navigation.isDone()) {
			return false;
		} else if (this.tamable.isOrderedToSit()) {
			return false;
		} else {
			return !(this.tamable.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
		}
	}

	public void start() {
		this.timeToRecalcPath = 0;
		this.oldWaterCost = this.tamable.getPathfindingMalus(PathNodeType.WATER);
		this.tamable.setPathfindingMalus(PathNodeType.WATER, 0.0F);
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.tamable.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
	}

	public void tick() {
		this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = 10;
			if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
				if (this.tamable.distanceToSqr(this.owner) >= 576.0D) {
					this.teleportToOwner();
				} else {
					this.navigation.moveTo(this.owner.blockPosition().getX(), this.owner.blockPosition().above().above().getY(), this.owner.blockPosition().getZ(), this.speedModifier);
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
			this.tamable.moveTo((double)p_226328_1_ + 0.5D, (double)p_226328_2_ + 4.0D, (double)p_226328_3_ + 0.5D, this.tamable.yRot, this.tamable.xRot);
			this.navigation.stop();
			return true;
		}
	}

	private boolean canTeleportTo(BlockPos p_226329_1_) {
		PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(this.level, p_226329_1_.mutable());
		if (pathnodetype != PathNodeType.WALKABLE) {
			return false;
		} else {
			BlockState blockstate = this.level.getBlockState(p_226329_1_.below());
			if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
				return false;
			} else {
				BlockPos blockpos = p_226329_1_.subtract(this.tamable.blockPosition());
				return this.level.noCollision(this.tamable, this.tamable.getBoundingBox().move(blockpos));
			}
		}
	}

	private int randomIntInclusive(int p_226327_1_, int p_226327_2_) {
		return this.tamable.getRandom().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
	}
}
