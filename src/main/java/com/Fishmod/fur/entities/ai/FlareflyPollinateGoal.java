package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.LuminousFilamentBlock;
import com.Fishmod.fur.entities.flying.FlareflyEntity;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Bee-style pollination goal for {@link FlareflyEntity}.
 *
 * <p>The Flarefly seeks out a nearby unripe Cave Vines block (head or plant segment — both carry
 * the {@link CaveVines#BERRIES} property) or the hanging tip of a Luminous Filament, perches beside
 * it for a few seconds, then rolls a 10% chance per perch to "pollinate" it:
 * <ul>
 *   <li>Cave Vines: sets {@code berries=true} (same effect as bonemealing it — glow berries
 *       appear), mirroring how a flower gets pollinated into fruit.</li>
 *   <li>Luminous Filament: grows one block downward from its {@code LOWER} tip into open space
 *       below it, converting the old tip to {@code MIDDLE} and placing a new {@code LOWER} segment
 *       beneath — the same shape {@link LuminousFilamentBlock}'s own neighbor-update logic would
 *       produce, just triggered by the Flarefly instead of a player.</li>
 * </ul>
 */
public class FlareflyPollinateGoal extends Goal {
	private static final int SEARCH_RADIUS_HORIZONTAL = 8;
	private static final int SEARCH_RADIUS_VERTICAL = 6;
	private static final int MIN_PERCH_TICKS = 40;   // 2s
	private static final int MAX_PERCH_TICKS = 100;  // 5s
	private static final float POLLINATE_CHANCE = 0.1F;

	private final FlareflyEntity flarefly;
	private BlockPos targetPos = BlockPos.ZERO;
	private int perchTicks;
	private int perchDuration;
	private int cooldown;

	public FlareflyPollinateGoal(FlareflyEntity flarefly) {
		this.flarefly = flarefly;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (this.cooldown > 0) {
			--this.cooldown;
			return false;
		}

		BlockPos found = this.findTarget();
		if (found == null) {
			this.cooldown = 60 + this.flarefly.getRandom().nextInt(60);
			return false;
		}

		this.targetPos = found;
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		return this.perchTicks < this.perchDuration && isValidTarget(this.flarefly.level(), this.targetPos);
	}

	@Override
	public void start() {
		this.perchTicks = 0;
		this.perchDuration = MIN_PERCH_TICKS + this.flarefly.getRandom().nextInt(MAX_PERCH_TICKS - MIN_PERCH_TICKS + 1);
		this.moveToTarget();
	}

	@Override
	public void stop() {
		this.cooldown = 100 + this.flarefly.getRandom().nextInt(200);
		this.targetPos = BlockPos.ZERO;
	}

	@Override
	public void tick() {
		this.flarefly.getLookControl().setLookAt(
				this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D,
				10.0F, this.flarefly.getMaxHeadXRot());

		if (this.flarefly.distanceToSqr(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D) > 1.5D) {
			if (this.flarefly.getNavigation().isDone()) {
				this.moveToTarget();
			}
			return;
		}

		// Perched next to the target — hold still and let the perch timer run.
		this.flarefly.getNavigation().stop();
		++this.perchTicks;

		if (this.perchTicks == this.perchDuration) {
			this.pollinate();
		}
	}

	private void moveToTarget() {
		this.flarefly.getNavigation().moveTo(
				this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.2D, this.targetPos.getZ() + 0.5D, 1.0D);
	}

	private void pollinate() {
		Level level = this.flarefly.level();
		if (level.isClientSide()) {
			return;
		}

		BlockState state = level.getBlockState(this.targetPos);
		RandomSource random = this.flarefly.getRandom();

		if (isUnripeCaveVine(state)) {
			if (random.nextFloat() < POLLINATE_CHANCE) {
				level.setBlock(this.targetPos, state.setValue(CaveVines.BERRIES, Boolean.TRUE), 2);
			}
		} else if (isFilamentTip(state)) {
			BlockPos below = this.targetPos.below();

			if (random.nextFloat() < POLLINATE_CHANCE && level.getBlockState(below).isAir()) {
				// The old tip now has a same-type block beneath it, so it becomes a MIDDLE segment;
				// the new block below inherits the LOWER tip (its own canSurvive is satisfied since
				// the block directly above it is filament).
				level.setBlock(this.targetPos, state.setValue(LuminousFilamentBlock.SEGMENT, LuminousFilamentBlock.Segment.MIDDLE), 2);

				BlockState newTip = FURBlockRegistry.LUMINOUS_FILAMENT.get().defaultBlockState()
						.setValue(LuminousFilamentBlock.SEGMENT, LuminousFilamentBlock.Segment.LOWER)
						.setValue(LuminousFilamentBlock.MIRRORED, random.nextInt(4));
				level.setBlock(below, newTip, 3);
			}
		}
	}

	@Nullable
	private BlockPos findTarget() {
		Level level = this.flarefly.level();
		BlockPos origin = this.flarefly.blockPosition();
		BlockPos best = null;
		double bestDistSq = Double.MAX_VALUE;

		for (BlockPos pos : BlockPos.betweenClosed(
				origin.offset(-SEARCH_RADIUS_HORIZONTAL, -SEARCH_RADIUS_VERTICAL, -SEARCH_RADIUS_HORIZONTAL),
				origin.offset(SEARCH_RADIUS_HORIZONTAL, SEARCH_RADIUS_VERTICAL, SEARCH_RADIUS_HORIZONTAL))) {
			if (level.isLoaded(pos) && isValidTarget(level, pos)) {
				double distSq = pos.distSqr(origin);
				if (distSq < bestDistSq) {
					bestDistSq = distSq;
					best = pos.immutable();
				}
			}
		}

		return best;
	}

	private static boolean isValidTarget(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return isUnripeCaveVine(state) || isFilamentTip(state);
	}

	private static boolean isUnripeCaveVine(BlockState state) {
		return state.getBlock() instanceof CaveVines && state.hasProperty(CaveVines.BERRIES) && !state.getValue(CaveVines.BERRIES);
	}

	private static boolean isFilamentTip(BlockState state) {
		return state.is(FURBlockRegistry.LUMINOUS_FILAMENT.get()) && state.getValue(LuminousFilamentBlock.SEGMENT) == LuminousFilamentBlock.Segment.LOWER;
	}
}
