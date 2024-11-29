package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURHugeShroomBlock extends HugeMushroomBlock {	
	public FURHugeShroomBlock(AbstractBlock.Properties p_i49982_1_) {
		super(p_i49982_1_);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this == FURBlockRegistry.GLOWSHROOM_BLOCK_CAP && rand.nextInt(16) == 0) {
			FURShroomBlock.spawnParticles(worldIn, pos);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
}
