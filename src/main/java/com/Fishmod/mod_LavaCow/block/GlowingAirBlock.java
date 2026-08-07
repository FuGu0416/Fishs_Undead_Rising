package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Air that emits light, spawned by {@link com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity}
 * as a one-shot "light orb" when fed. Ported from the 1.20.1 branch's rewrite of this block: fully
 * self-contained, placing it schedules its own decay timeline entirely on the block, which holds
 * full brightness for a duration set by whichever item fed it ({@link #DURATION}: 0 = Glowstone
 * Dust's 8 minutes, 1 = Warped Fungus's 3 minutes - matching this version's original two-tier
 * behaviour) minus a fixed 15s fade tail, then steps {@link #LIGHT} down by 5 at each of 3 marks
 * spaced 5s apart and reverts to the original air variant on the last step.
 *
 * <p>This replaces the previous design, where {@code WarpedFireflyEntity} tracked its own
 * {@code glowTimer}/{@code GLOWING_POS} and polled every 5 ticks in {@code tick()} to place/clear
 * the block itself. That design had two real bugs: it always reverted to plain {@code Blocks.AIR}
 * regardless of what was actually there before (losing {@code cave_air}, which is what this cave
 * dweller almost always flashes over), and - much worse - the cleanup was entirely dependent on the
 * entity staying alive and ticking; if it died, despawned (it isn't persistent unless leashed),
 * unloaded, or changed dimension mid-glow, the orb it left behind had no other mechanism to ever
 * revert it and would stay lit forever. Putting the decay timeline on the block itself (as done
 * here) removes that dependency entirely - a block left behind always finishes its own timeline.
 *
 * <p>A flash landing on an already-glowing orb (two different Fireflies sharing a position, or a
 * refeed racing an interrupted glow) re-ignites it: full brightness and a fresh full-length decay
 * timeline, not just a cosmetic re-light on the interrupted orb's old clock - see {@link #onPlace}
 * for how that's told apart from this block's own fade-step transitions, and for why the previous
 * orb's already-pending tick has to be explicitly cleared rather than just scheduling a new one.
 */
public class GlowingAirBlock extends AirBlock {
	public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);
	private static final int FULL_LIGHT = 15;
	/** Which air block to restore on burnout: 0 = air, 1 = cave_air, 2 = void_air. */
	public static final IntegerProperty ORIGIN = IntegerProperty.create("origin", 0, 2);
	private static final BlockState[] ORIGIN_STATES = { Blocks.AIR.defaultBlockState(), Blocks.CAVE_AIR.defaultBlockState(), Blocks.VOID_AIR.defaultBlockState() };
	/** Which feed item lit this orb: 0 = Glowstone Dust (8 min), 1 = Warped Fungus (3 min). */
	public static final IntegerProperty DURATION = IntegerProperty.create("duration", 0, 1);
	private static final int FADE_STEP_TICKS = 5 * 20; // spacing between fade marks
	private static final int FADE_AMOUNT = 5; // LIGHT lost at each mark (3 marks: 15 -> 10 -> 5 -> 0)
	private static final int[] HOLD_TICKS = {
			Math.max(0, 8 * 60 * 20 - 3 * FADE_STEP_TICKS), // Glowstone Dust
			Math.max(0, 3 * 60 * 20 - 3 * FADE_STEP_TICKS)  // Warped Fungus
	};

	public GlowingAirBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, FULL_LIGHT).setValue(ORIGIN, 0).setValue(DURATION, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LIGHT, ORIGIN, DURATION);
	}

	/**
	 * Replaces the air block at {@code pos} with a freshly-lit orb of the given duration tier,
	 * tagging {@link #ORIGIN} with whichever air variant was there so burnout can restore it.
	 * Callers are expected to have already checked {@code level.getBlockState(pos).isAir()} - which
	 * a still-glowing orb also satisfies (it's air too), so a flash landing on one already in
	 * progress is a real case, not just brand-new air: that overlap re-ignites (see {@link #onPlace})
	 * but must carry the existing orb's {@link #ORIGIN} forward instead of re-deriving it - the block
	 * under an in-progress orb is glowing_air, not the vanilla air/cave_air/void_air {@code old.is(...)}
	 * below checks for, so skipping this would silently lose the remembered variant.
	 */
	public static void spawn(World level, BlockPos pos, int durationTier) {
		BlockState old = level.getBlockState(pos);
		int origin;
		if (old.is(FURBlockRegistry.GLOWING_AIR)) {
			origin = old.getValue(ORIGIN);
		} else {
			origin = old.is(Blocks.CAVE_AIR) ? 1 : old.is(Blocks.VOID_AIR) ? 2 : 0;
		}
		level.setBlock(pos, FURBlockRegistry.GLOWING_AIR.defaultBlockState().setValue(ORIGIN, origin).setValue(DURATION, durationTier), 3);
	}

	/**
	 * Schedules the hold-phase tick on two kinds of transition: a genuine air-to-glowing placement
	 * (once, when the orb is first created), and a {@link #spawn} landing on an already-glowing orb
	 * (a re-ignition, which resets {@link #LIGHT} back up to {@link #FULL_LIGHT}). Both are
	 * distinguished from this block's own fade-step {@code setBlock} calls in {@link #tick} - which
	 * only ever count {@link #LIGHT} down and must never re-trigger a schedule here - by checking
	 * whether the new state's LIGHT is the fully-lit value.
	 *
	 * <p>A re-ignition means an earlier hold/fade tick is still pending for this position from the
	 * orb it interrupted; left alone that stale tick would fire on the old schedule and prematurely
	 * dim the new orb. Unlike the 1.20.1 tick queue, this version's {@code ServerTickList} dedupes
	 * scheduled ticks purely by (pos, block) and ignores a second {@code scheduleTick} call for the
	 * same pair outright - it would NOT bump the old entry to the new time, silently keeping the
	 * stale one. So a re-ignition explicitly clears pending ticks first via
	 * {@code ServerWorld#getBlockTicks()#fetchTicksInArea(..., remove=true)} before scheduling the
	 * fresh one. That API only filters by X/Z (used vanilla-side for whole-chunk-column ticks), so
	 * the clear reaches the full Y column at this X/Z, not just this one block - broader than
	 * strictly necessary, but a column that was air a moment ago is never legitimately host to
	 * anyone else's pending tick in practice.
	 */
	@Override
	public void onPlace(BlockState state, World level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (level.isClientSide()) {
			return;
		}

		boolean reignition = oldState.is(this) && state.getValue(LIGHT) == FULL_LIGHT;
		if (reignition || !oldState.is(this)) {
			if (reignition && level instanceof ServerWorld) {
				ServerWorld serverLevel = (ServerWorld) level;
				serverLevel.getBlockTicks().fetchTicksInArea(new MutableBoundingBox(pos.getX(), 0, pos.getZ(), pos.getX(), 255, pos.getZ()), true, false);
			}
			level.getBlockTicks().scheduleTick(pos, this, HOLD_TICKS[state.getValue(DURATION)]);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
		if (!level.getBlockState(pos).is(this)) {
			return;
		}

		int next = state.getValue(LIGHT) - FADE_AMOUNT;
		if (next <= 0) {
			level.setBlock(pos, ORIGIN_STATES[state.getValue(ORIGIN)], 3);
		} else {
			level.setBlock(pos, state.setValue(LIGHT, next), 3);
			level.getBlockTicks().scheduleTick(pos, this, FADE_STEP_TICKS);
		}
	}

	/** Soft end-rod sparkle for the "light orb" look, client-side only, gently throttled. */
	@Override
	public void animateTick(BlockState state, World level, BlockPos pos, Random random) {
		if (random.nextInt(4) != 0) {
			return;
		}

		double x = pos.getX() + 0.3D + random.nextDouble() * 0.4D;
		double y = pos.getY() + 0.3D + random.nextDouble() * 0.4D;
		double z = pos.getZ() + 0.3D + random.nextDouble() * 0.4D;
		level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0D, 0.01D, 0.0D);
	}
}
