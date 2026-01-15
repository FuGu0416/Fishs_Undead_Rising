package com.Fishmod.fur.block;

import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURHugeShroomBlock extends HugeMushroomBlock {	
	public FURHugeShroomBlock(Properties p_i49982_1_) {
		super(p_i49982_1_);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (this == FURBlockRegistry.GLOWSHROOM_BLOCK_CAP.get() && rand.nextInt(16) == 0) {
			FURShroomBlock.spawnParticles(worldIn, pos);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
}
