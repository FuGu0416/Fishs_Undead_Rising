package com.Fishmod.fur.block;

import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class LuminousMyceliumBlock extends SpreadingSnowyDirtBlock {
	private static final DustParticleOptions SPORE = new DustParticleOptions(new Vector3f(0.2F, 0.8F, 1.0F), 1.0F);
	
	public LuminousMyceliumBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
	    if ((random.nextInt(20) == 0) && (level.getMaxLocalRawBrightness(pos) < 7)) {
	        level.addParticle(SPORE, pos.getX() + random.nextDouble(), pos.getY() + 1.1D, pos.getZ() + random.nextDouble(), (random.nextDouble() - 0.5) * 0.01D, 0.02D, (random.nextDouble() - 0.5) * 0.01D);
	    }
	}
}