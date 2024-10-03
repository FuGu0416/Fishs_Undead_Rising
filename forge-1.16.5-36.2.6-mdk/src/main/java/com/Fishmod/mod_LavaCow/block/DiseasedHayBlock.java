package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HayBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DiseasedHayBlock extends HayBlock {	
	public DiseasedHayBlock(AbstractBlock.Properties p_i49982_1_) {
		super(p_i49982_1_);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(8) == 0) {
            Vector3d center = Vector3d.upFromBottomCenterOf(pos, 1).add(rand.nextFloat() - 0.5F, rand.nextFloat() * 0.5F + 0.2F, rand.nextFloat() - 0.5F);
            worldIn.addParticle(FURParticleRegistry.LOCUST_SWARM, center.x, center.y, center.z, center.x, center.y, center.z);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
}
