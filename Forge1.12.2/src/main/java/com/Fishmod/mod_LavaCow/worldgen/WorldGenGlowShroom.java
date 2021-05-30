package com.Fishmod.mod_LavaCow.worldgen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.blocks.BlockGlowShroom;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlowShroom extends WorldGenerator{
	
	private BlockGlowShroom glowshroom = Modblocks.GLOWSHROOM;
	
    public WorldGenGlowShroom()
    {
    }
    
    public boolean generate(BlockGlowShroom shroom, World worldIn, Random rand, BlockPos position) {
    	this.glowshroom = shroom;
    	return generate(worldIn, rand, position);
    }

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {	
        if(Modconfig.pGlowshroomGen)
        {
	        for (int i = 0; i < 64; ++i)
	        {
	            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
	
	            if (worldIn.isAirBlock(blockpos) && worldIn.getLight(blockpos) < 10 /*&& worldIn.provider.isSurfaceWorld()*/ && blockpos.getY() < 70 && glowshroom.canBlockStay(worldIn, blockpos, glowshroom.getDefaultState()) && rand.nextInt(100) < Modconfig.pSpawnRate_Glowshroom)
	            {
	            	worldIn.setBlockState(blockpos, glowshroom.withAge(rand.nextInt(2)), 2);
	            }
	        }
	        
	        return true;
        }
        
        return false;
	}
}
