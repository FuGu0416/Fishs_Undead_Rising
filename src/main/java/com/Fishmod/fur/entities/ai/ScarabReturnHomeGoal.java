package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import com.Fishmod.fur.block.BonePileBlock;
import com.Fishmod.fur.entities.tameable.ScarabEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * Makes a Bone Pile's scarab physically walk back to its home pile at night. The pile's block entity
 * absorbs it once it arrives (see {@code BonePileBlockEntity#recallScarabs}); this goal only handles
 * the navigation. Scarabs with no home (e.g. the angry swarm from a broken pile) never run it.
 */
public class ScarabReturnHomeGoal extends Goal {
    // Keep pathing until within 2 blocks; the block entity's absorb radius is slightly larger so it
    // catches the scarab while this goal is still steering it in.
    private static final double ARRIVE_DIST_SQR = 4.0D;

    private final ScarabEntity scarab;
    private BlockPos home;

    public ScarabReturnHomeGoal(ScarabEntity scarab) {
        this.scarab = scarab;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    private boolean homeValid(BlockPos pos) {
        return pos != null && this.scarab.level().getBlockState(pos).getBlock() instanceof BonePileBlock;
    }

    private double distSqrToHome() {
        return this.scarab.distanceToSqr(this.home.getX() + 0.5D, this.home.getY() + 0.5D, this.home.getZ() + 0.5D);
    }

    @Override
    public boolean canUse() {
        if (this.scarab.getTarget() != null) {
            return false;   // fighting takes precedence
        }
        if (this.scarab.level().isDay()) {
            return false;   // only head home at night
        }
        BlockPos h = this.scarab.getHomePos();
        if (!homeValid(h)) {
            return false;
        }
        this.home = h;
        return distSqrToHome() > ARRIVE_DIST_SQR;
    }

    @Override
    public boolean canContinueToUse() {
        return this.scarab.getTarget() == null
                && !this.scarab.level().isDay()
                && homeValid(this.home)
                && distSqrToHome() > ARRIVE_DIST_SQR;
    }

    @Override
    public void start() {
        moveToHome();
    }

    @Override
    public void stop() {
        this.home = null;
        this.scarab.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.home == null) {
            return;
        }
        this.scarab.getLookControl().setLookAt(this.home.getX() + 0.5D, this.home.getY() + 0.5D, this.home.getZ() + 0.5D);
        if (this.scarab.getNavigation().isDone()) {
            moveToHome();   // re-path if the navigation stalled or finished short
        }
    }

    private void moveToHome() {
        this.scarab.getNavigation().moveTo(this.home.getX() + 0.5D, this.home.getY() + 0.5D, this.home.getZ() + 0.5D, 1.0D);
    }
}
