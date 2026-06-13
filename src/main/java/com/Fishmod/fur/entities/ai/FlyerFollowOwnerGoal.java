package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FlyerFollowOwnerGoal extends Goal {
	private final TamableAnimal tamable;
	private LivingEntity owner;
	private final LevelReader level;
	private final double speedModifier;
	private final PathNavigation navigation;
	private int timeToRecalcPath;
	private final float stopDistance;
	private final float startDistance;
	private float oldWaterCost;
	private final boolean canFly;
	private final double teleportDistance;
	
	public FlyerFollowOwnerGoal(TamableAnimal tamableIn, double speedModifierIn, float startDistanceIn, float stopDistanceIn, boolean canFlyIn, double teleportDisIn) {
		this.tamable = tamableIn;
		this.level = tamableIn.level();
		this.speedModifier = speedModifierIn;
		this.navigation = tamableIn.getNavigation();
		this.startDistance = startDistanceIn;
		this.stopDistance = stopDistanceIn;
		this.canFly = canFlyIn;
		this.teleportDistance = teleportDisIn;
		
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		if (!(tamableIn.getNavigation() instanceof GroundPathNavigation) && !(tamableIn.getNavigation() instanceof FlyingPathNavigation)) {
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
		this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
		this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
	}

	public void tick() {
		this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = 10;
			if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
				if (this.tamable.distanceToSqr(this.owner) >= this.teleportDistance * this.teleportDistance) {
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

	private boolean maybeTeleportTo(int x, int y, int z) {
		if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
			return false;
		} else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
			return false;
		} else {
			this.tamable.moveTo((double)x + 0.5D, (double)y + 4.0D, (double)z + 0.5D, this.tamable.getYRot(), this.tamable.getXRot());
			this.navigation.stop();
			return true;
		}
	}

	@SuppressWarnings("static-access")
	private boolean canTeleportTo(BlockPos pos) {
		BlockPathTypes BlockPathTypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pos.mutable());
		if (BlockPathTypes != BlockPathTypes.WALKABLE) {
			return false;
		} else {
			BlockState blockstate = this.level.getBlockState(pos.below());
			if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
				return false;
			} else {
				BlockPos blockpos = pos.subtract(this.tamable.blockPosition());
				return this.level.noCollision(this.tamable, this.tamable.getBoundingBox().move(blockpos));
			}
		}
	}

	private int randomIntInclusive(int min, int max) {
		return this.tamable.getRandom().nextInt(max - min + 1) + min;
	}
}
