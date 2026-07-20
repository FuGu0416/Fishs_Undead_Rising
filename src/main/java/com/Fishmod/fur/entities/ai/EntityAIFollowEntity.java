package com.Fishmod.fur.entities.ai;

import java.util.UUID;

import com.Fishmod.fur.core.SpawnUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

/**
 * Owner-follow for mobs that only know their master by UUID (e.g. skeletons enthralled by the
 * Crown of Rule) — a FollowOwnerGoal that works without TamableAnimal. Ported from 1.16.5.
 */
public class EntityAIFollowEntity extends Goal {
    private final Mob tameable;
    private UUID ownerID;
    private LivingEntity owner;
    Level world;
    private final double followSpeed;
    private final PathNavigation petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    public UUID getOwnerId() {
        return this.ownerID;
    }

    public EntityAIFollowEntity(Mob tameableIn, UUID uniqueIDIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.ownerID = uniqueIDIn;
        this.world = tameableIn.level();
        this.followSpeed = followSpeedIn;
        this.petPathfinder = tameableIn.getNavigation();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;

        if (!(tameableIn.getNavigation() instanceof GroundPathNavigation) && !(tameableIn.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    public boolean canUse() {
        if (this.world instanceof ServerLevel server) {
            LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, server);

            if (livingentity == null) {
                return false;
            } else if (livingentity instanceof Player player && player.isSpectator()) {
                return false;
            } else if (this.tameable.distanceToSqr(livingentity) < (double) (this.minDist * this.minDist)) {
                return false;
            } else {
                this.owner = livingentity;
                return true;
            }
        }

        return false;
    }

    public boolean canContinueToUse() {
        return !this.petPathfinder.isDone() && this.tameable.distanceToSqr(this.owner) > (double) (this.maxDist * this.maxDist);
    }

    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathfindingMalus(BlockPathTypes.WATER);
        this.tameable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public void stop() {
        this.owner = null;
        this.petPathfinder.stop();
        this.tameable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    public void tick() {
        this.tameable.getLookControl().setLookAt(this.owner, 10.0F, (float) this.tameable.getMaxHeadXRot());

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

        for (int i = 0; i < 10; ++i) {
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
        if (Math.abs((double) x - this.owner.getX()) < 2.0D && Math.abs((double) z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.tameable.moveTo((double) x + 0.5D, (double) y, (double) z + 0.5D, this.tameable.getYRot(), this.tameable.getXRot());
            this.petPathfinder.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.world, pos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.world.getBlockState(pos.below());
            if (blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pos.subtract(this.tameable.blockPosition());
                return this.world.noCollision(this.tameable, this.tameable.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int min, int max) {
        return this.tameable.getRandom().nextInt(max - min + 1) + min;
    }
}
