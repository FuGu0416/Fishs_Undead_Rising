package com.Fishmod.mod_LavaCow.worldgen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityPiranha;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

public class WorldGenAquaMob extends WorldGenerator{
	
    public WorldGenAquaMob()
    {
    }

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		Biome biome = worldIn.getBiome(position);
        
    	if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.WET) && rand.nextFloat() < 0.1F){
    		if(rand.nextInt(100) < Modconfig.pSpawnRate_ZombiePiranha && worldIn.isAreaLoaded(position.down(), 3)) {
    			if(worldIn.getBlockState(position.down()).getMaterial() == Material.WATER){
    				for(int i = 0; i < 2 + rand.nextInt(3); i++) {
	    				EntityZombiePiranha fish = new EntityZombiePiranha(worldIn);
	    				fish.setPosition(position.down().getX(), position.down().getY(), position.down().getZ());
	    				worldIn.spawnEntity(fish);
	    			}
    			}
    		}
    		
    		if(rand.nextInt(100) < Modconfig.pSpawnRate_Piranha && worldIn.isAreaLoaded(position.down(), 3)) {
    			if(worldIn.getBlockState(position.down()).getMaterial() == Material.WATER){
    				for(int i = 0; i < 2 + rand.nextInt(3); i++) {
	    				EntityPiranha fish = new EntityPiranha(worldIn);
	    				fish.setPosition(position.down().getX(), position.down().getY(), position.down().getZ());
	    				worldIn.spawnEntity(fish);
	    			}
    			}
    		}
    		
    		return true;
    	}        
        
        return false;
	}
}
