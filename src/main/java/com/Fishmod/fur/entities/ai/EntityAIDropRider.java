package com.Fishmod.fur.entities.ai;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class EntityAIDropRider extends Goal {

    protected PathfinderMob attacker;
    /** The PathEntity of our entity. */
    Path path;

    /** Horizontal distance threshold to consider Ptera on top of the target. */
    private static final double DROP_XZ_THRESHOLD_SQ = 2.0D;
    /** Minimum height above target before dropping the rider. */
    private static final double DROP_Y_THRESHOLD = 4.0D;
    /** How far above the target Ptera tries to fly before dropping. */
    private static final double FLY_HEIGHT_OFFSET = 16.0D;

    public EntityAIDropRider(PathfinderMob creature) {
        super();
        this.attacker = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        LivingEntity target = this.attacker.getTarget();

        if (target == null)
            return false;

        return !this.attacker.getPassengers().isEmpty();
    }

    /**
     * Execute a one shot task or start executing a continuous task.
     */
    @Override
    public void start() {
        LivingEntity target = this.attacker.getTarget();

        if (target != null) {
            this.path = this.attacker.getNavigation().createPath(target, 0);
            this.attacker.getNavigation().moveTo(this.path, 1.0D);
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one.
     */
    @Override
    public void stop() {
        LivingEntity target = this.attacker.getTarget();

        if (target instanceof Player player && (player.isSpectator() || player.isCreative())) {
            this.attacker.setTarget(null);
        }

        this.attacker.getNavigation().stop();
    }

    /**
     * Keep ticking a continuous task that has already been started.
     */
    @Override
    public void tick() {
        LivingEntity target = this.attacker.getTarget();

        if (target == null || this.attacker.getPassengers().isEmpty())
            return;

        if (this.isOnTop(this.attacker, target)) {
            // Apply Slow Falling to prevent the rider from dying on impact
            LivingEntity passenger = (LivingEntity) this.attacker.getPassengers().get(0);
            passenger.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5 * 20, 0));

            if (passenger instanceof Monster monsterPassenger) {
                monsterPassenger.setTarget(target);
            }

            this.attacker.ejectPassengers();
        } else {
            // Fly above the target before dropping
            this.path = this.attacker.getNavigation().createPath(
                    target.getX(),
                    target.getY() + FLY_HEIGHT_OFFSET,
                    target.getZ(),
                    0
            );
            this.attacker.getNavigation().moveTo(this.path, 1.0D);
        }
    }

    /**
     * Returns true if Ptera is directly above the target within drop thresholds.
     * Checks both XZ proximity and that Ptera is sufficiently above the target.
     */
    private boolean isOnTop(PathfinderMob flyer, LivingEntity target) {
        double xzDistSq = target.distanceToSqr(flyer.getX(), target.getY(), flyer.getZ());
        double yDiff = flyer.getY() - target.getY();

        return xzDistSq < DROP_XZ_THRESHOLD_SQ && yDiff >= DROP_Y_THRESHOLD;
    }
}