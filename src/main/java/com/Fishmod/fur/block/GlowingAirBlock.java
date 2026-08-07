package com.Fishmod.fur.block;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/**
 * Air that emits light, spawned by {@link com.Fishmod.fur.entities.flying.FlareflyEntity}
 * as a one-shot "light orb" when fed. Fully self-contained: placing it schedules its own decay
 * timeline entirely on the block, which holds full brightness for {@link FURConfig#Flarefly_Light_Duration}
 * minus the fixed 15s fade tail, then steps {@link #LIGHT} down by 5 at each of 3 marks spaced 5s
 * apart and reverts to plain air on the last step. A block left behind therefore always finishes
 * its timeline and cleans itself up with no dependency on the entity that placed it still existing,
 * still ticking, or being in the same dimension - unlike an entity-tracked-position approach, which
 * could leak a permanent light source if the placing entity died, despawned, or changed dimension
 * mid-glow. The fixed 3-event fade tail (vs. a per-tick or many-small-steps decay) also keeps the
 * light-engine cost of a single orb to exactly 3 relights, regardless of the configured duration.
 * The pre-glow air variant (plain air / cave air / void air) is remembered in the {@link #ORIGIN}
 * state property so burnout restores the same block instead of always falling back to plain air -
 * relevant because Flarefly (a cave dweller) almost always flashes over {@code cave_air}, not
 * {@code air}. A flash landing on an already-glowing orb (a realistic overlap - the default feed
 * cooldown is shorter than the default glow duration) re-ignites it: full brightness and a fresh
 * full-length decay timeline, not just a cosmetic re-light on the interrupted orb's old clock. See
 * {@link #onPlace} for how that's told apart from this block's own fade-step transitions.
 */
public class GlowingAirBlock extends AirBlock {
	public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);
	private static final int FULL_LIGHT = 15;
	/** Which air block to restore on burnout: 0 = air, 1 = cave_air, 2 = void_air. */
	public static final IntegerProperty ORIGIN = IntegerProperty.create("origin", 0, 2);
	private static final BlockState[] ORIGIN_STATES = { Blocks.AIR.defaultBlockState(), Blocks.CAVE_AIR.defaultBlockState(), Blocks.VOID_AIR.defaultBlockState() };

	private static final int FADE_STEP_TICKS = 5 * 20; // spacing between fade marks
	private static final int FADE_AMOUNT = 5; // LIGHT lost at each mark (3 marks: 15 -> 10 -> 5 -> 0)

	public GlowingAirBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, FULL_LIGHT).setValue(ORIGIN, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIGHT, ORIGIN);
	}

	/**
	 * Replaces the air block at {@code pos} with a freshly-lit orb, tagging {@link #ORIGIN} with
	 * whichever air variant was there so burnout can restore it. Callers are expected to have
	 * already checked {@code level.getBlockState(pos).isAir()} - which a still-glowing orb also
	 * satisfies (it's air too), so a flash landing on top of one already in progress is a real case,
	 * not just brand-new air: that overlap re-lights to full brightness and restarts the full decay
	 * timeline (see {@link #onPlace}), but must carry the existing orb's {@link #ORIGIN} forward
	 * instead of re-deriving it - the block under an in-progress orb is glowing_air, not the vanilla
	 * air/cave_air/void_air {@code old.is(...)} below checks for, so skipping this would silently
	 * lose the remembered variant and burn out to plain air regardless of what was really there.
	 */
	public static void spawn(Level level, BlockPos pos) {
		BlockState old = level.getBlockState(pos);
		int origin;
		if (old.is(FURBlockRegistry.GLOWING_AIR.get())) {
			origin = old.getValue(ORIGIN);
		} else {
			origin = old.is(Blocks.CAVE_AIR) ? 1 : old.is(Blocks.VOID_AIR) ? 2 : 0;
		}
		level.setBlock(pos, FURBlockRegistry.GLOWING_AIR.get().defaultBlockState().setValue(ORIGIN, origin), 3);
	}

	/**
	 * Schedules the hold-phase tick on two kinds of transition: a genuine air→glowing placement
	 * (once, when the orb is first created), and a {@link #spawn} landing on an already-glowing orb
	 * (a re-ignition, which resets {@link #LIGHT} back up to {@link #FULL_LIGHT}). Both are
	 * distinguished from this block's own fade-step {@code setBlock} calls in {@link #tick} - which
	 * only ever count {@link #LIGHT} down and must never re-trigger a schedule here - by checking
	 * whether the new state's LIGHT is the fully-lit value.
	 *
	 * <p>A re-ignition means an earlier hold/fade tick is still pending for this position from the
	 * orb it interrupted; left alone that stale tick would fire on the old schedule and prematurely
	 * dim the new orb. So a re-ignition also clears every scheduled tick at {@code pos} before
	 * scheduling the fresh one - a 1x1x1-block-radius clear, not type-filtered to this block, but
	 * a position that was air a moment ago is never legitimately host to anyone else's pending tick.
	 */
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (level.isClientSide()) {
			return;
		}

		boolean reignition = oldState.is(this) && state.getValue(LIGHT) == FULL_LIGHT;
		if (reignition || !oldState.is(this)) {
			if (reignition && level instanceof ServerLevel serverLevel) {
				serverLevel.getBlockTicks().clearArea(new BoundingBox(pos));
			}
			int holdTicks = Math.max(0, FURConfig.Flarefly_Light_Duration.get() * 20 - 3 * FADE_STEP_TICKS);
			level.scheduleTick(pos, this, holdTicks);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.getBlockState(pos).is(this)) {
			return;
		}

		int next = state.getValue(LIGHT) - FADE_AMOUNT;
		if (next <= 0) {
			level.setBlock(pos, ORIGIN_STATES[state.getValue(ORIGIN)], 3);
		} else {
			level.setBlock(pos, state.setValue(LIGHT, next), 3);
			level.scheduleTick(pos, this, FADE_STEP_TICKS);
		}
	}

	/** Soft end-rod sparkle for the "light orb" look, client-side only, gently throttled. */
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(4) != 0) {
			return;
		}

		double x = pos.getX() + 0.3D + random.nextDouble() * 0.4D;
		double y = pos.getY() + 0.3D + random.nextDouble() * 0.4D;
		double z = pos.getZ() + 0.3D + random.nextDouble() * 0.4D;
		level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0D, 0.01D, 0.0D);
	}
}
