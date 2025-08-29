package com.Fishmod.fur.block;

import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DiseasedHayBlock extends HayBlock {	
	public DiseasedHayBlock(BlockBehaviour.Properties p_i49982_1_) {
		super(p_i49982_1_);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (rand.nextInt(8) == 0) {
            Vec3 center = Vec3.upFromBottomCenterOf(pos, 1).add(rand.nextFloat() - 0.5F, rand.nextFloat() * 0.5F + 0.2F, rand.nextFloat() - 0.5F);
            worldIn.addParticle(FURParticleRegistry.LOCUST_SWARM.get(), center.x, center.y, center.z, center.x, center.y, center.z);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
}
