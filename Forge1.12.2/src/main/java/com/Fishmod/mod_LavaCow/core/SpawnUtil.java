package com.Fishmod.mod_LavaCow.core;

import java.util.UUID;

import com.Fishmod.mod_LavaCow.client.Modconfig;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;

public class SpawnUtil {
	
	public static boolean isDay(World worldIn) {
		return worldIn.getWorldTime() <= 12000;
	}
	
	public static boolean isAllowedDimension(int dimensionIn) {
		for(int i : Modconfig.Spawn_AllowList) {
			if(i == dimensionIn)
				return true;
		}
		
		return false;
	}
	
	public static boolean isAllowedDimensionCemetery(int dimensionIn) {
		for(int i : Modconfig.Spawn_Cemetery_AllowList) {
			if(i == dimensionIn)
				return true;
		}
		
		return false;
	}
	
	/* Used to determine the relative height */
    public static BlockPos getHeight(Entity entityIn) {
    	return entityIn.world.getHeight(entityIn.getPosition());
    }
    
    public static boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
    	if(worldIn.getChunkProvider() instanceof ChunkProviderServer)
    		return ((ChunkProviderServer)worldIn.getChunkProvider()).isInsideStructure(worldIn, structureName, pos);
    	
    	return false;
    }
    
    public static EntityLivingBase getEntityByUniqueId(UUID uniqueId, World worldIn){
        
    	for(Entity E : worldIn.loadedEntityList) {
    		if(E instanceof EntityLivingBase && E.getUniqueID().equals(uniqueId))
    			return (EntityLivingBase) E;
    	}

        return null;
    }
    
    public static BlockPos isNearBlock(World worldIn, Block BlockIn, BlockPos pos, int r) {
        int dx = MathHelper.floor(pos.getX());
        int dy = MathHelper.floor(pos.getY());
        int dz = MathHelper.floor(pos.getZ());
        
        for(int i = dx - r; i < dx + r; i++)
        	for(int j = dy - r; j < dy + r; j++)
        		for(int k = dz - r; k < dz + r; k++)
        			if(worldIn.getBlockState(new BlockPos(i, j, k)).getBlock().equals(BlockIn)) {
                		return new BlockPos(i, j, k);
        			}

    	return null;
    }
}
