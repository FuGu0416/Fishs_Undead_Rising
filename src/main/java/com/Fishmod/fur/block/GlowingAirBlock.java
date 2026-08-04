package com.Fishmod.fur.block;

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

/**
 * Air that emits light, spawned by {@link com.Fishmod.fur.entities.flying.FlareflyEntity}
 * as a one-shot "light orb" when fed. Fully self-contained: placing it schedules its own decay
 * timeline entirely on the block, which holds full brightness for the first 50s of its 60s life,
 * then steps {@link #LIGHT} down by 5 at the 50s/55s/60s marks and reverts to plain air on the
 * last step. A block left behind therefore always finishes its timeline and cleans itself up with
 * no dependency on the entity that placed it still existing, still ticking, or being in the same
 * dimension - unlike an entity-tracked-position approach, which could leak a permanent light
 * source if the placing entity died, despawned, or changed dimension mid-glow. The fixed 3-event
 * schedule (vs. a per-tick or many-small-steps decay) also keeps the light-engine cost of a single
 * orb to exactly 3 relights over its whole lifetime.
 */
public class GlowingAirBlock extends AirBlock {
	public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);

	private static final int FULL_BRIGHTNESS_TICKS = 50 * 20; // holds LIGHT=15 until the 50s mark
	private static final int FADE_STEP_TICKS = 5 * 20; // spacing between the 50s/55s/60s marks
	private static final int FADE_AMOUNT = 5; // LIGHT lost at each of those marks

	public GlowingAirBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, 15));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIGHT);
	}

	/** Only schedules on a genuine air→glowing transition (i.e. once, when the orb is created). */
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!level.isClientSide() && !oldState.is(this)) {
			level.scheduleTick(pos, this, FULL_BRIGHTNESS_TICKS);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.getBlockState(pos).is(this)) {
			return;
		}

		int next = state.getValue(LIGHT) - FADE_AMOUNT;
		if (next <= 0) {
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
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
