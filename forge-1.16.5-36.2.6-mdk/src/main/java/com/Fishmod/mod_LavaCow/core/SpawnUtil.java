package com.Fishmod.mod_LavaCow.core;

import java.util.UUID;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class SpawnUtil {
	
	public static boolean isDay(World worldIn) {
		return worldIn.getDayTime() <= 12000;
	}
	
	public static boolean isAllowedDimension(String dimensionIn) {
		for(String i : FURConfig.Spawn_AllowList.get()) {
			if(i.equalsIgnoreCase(dimensionIn))
				return true;
		}
		
		return false;
	}
	
	public static boolean isAllowedDimensionCemetery(String dimensionIn) {
		for(String i : FURConfig.Spawn_Cemetery_AllowList.get()) {
			if(i.equalsIgnoreCase(dimensionIn))
				return true;
		}
		
		return false;
	}
	
	/* Used to determine the relative height */
    public static BlockPos getHeight(Entity entityIn) {
		final BlockPos currentPosition = entityIn.blockPosition();
    	BlockPos groundPosition = entityIn.blockPosition();
		
		while(entityIn.level.getBlockState(groundPosition).getMaterial().equals(Material.AIR) || entityIn.level.getBlockState(groundPosition).getMaterial().equals(Material.LEAVES)) {
			if (World.isOutsideBuildHeight(groundPosition) && groundPosition.getY() < 0) { // Double-check to account for 1.17+ worlds build heights below 0 may be valid, but also don't want to trigger for being ABOVE build height.
				mod_LavaCow.LOGGER.warn(String.format("Attempt to get ground position of entity \"%s\" (of type \"%s\", UUID=\"%s\") at \"%s\" resulted in a position below world height - using the entity's current position instead.", entityIn.getDisplayName().getString(), entityIn.getType().getRegistryName(), entityIn.getStringUUID(), currentPosition));
				return currentPosition;
			}
			groundPosition = groundPosition.below();
		}
		
		while(!entityIn.level.getBlockState(groundPosition).getMaterial().equals(Material.AIR) && !entityIn.level.getBlockState(groundPosition).getMaterial().equals(Material.LEAVES)) {
			if (World.isOutsideBuildHeight(groundPosition)) {
				mod_LavaCow.LOGGER.warn(String.format("Attempt to get ground position of entity \"%s\" (of type \"%s\", UUID=\"%s\") at \"%s\" resulted in a position above world height - using the entity's current position instead.", entityIn.getDisplayName().getString(), entityIn.getType().getRegistryName(), entityIn.getStringUUID(), currentPosition));
				return currentPosition;
			}
			groundPosition = groundPosition.above();
		}
		
    	return groundPosition;
    }
    
    /*public static boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
    	if(worldIn.getChunkProvider() instanceof ChunkProviderServer)
    		return ((ChunkProviderServer)worldIn.getChunkProvider()).isInsideStructure(worldIn, structureName, pos);
    	
    	return false;
    }*/
    
    public static LivingEntity getEntityByUniqueId(UUID uniqueId, ServerWorld worldIn){
        Entity entity = worldIn.getEntity(uniqueId);
        
        if(entity != null && entity instanceof LivingEntity)
        	return (LivingEntity) entity;

        return null;
    }
    
    public static BlockPos isNearBlock(IWorld p_223316_1_, Block BlockIn, BlockPos pos, int r) {
        int dx = MathHelper.floor(pos.getX());
        int dy = MathHelper.floor(pos.getY());
        int dz = MathHelper.floor(pos.getZ());
        
        for(int i = dx - r; i < dx + r; i++)
        	for(int j = dy - r; j < dy + r; j++)
        		for(int k = dz - r; k < dz + r; k++)
        			if(p_223316_1_.getBlockState(new BlockPos(i, j, k)).getBlock().equals(BlockIn)) {
                		return new BlockPos(i, j, k);
        			}

    	return null;
    }
    
    public static TranslationTextComponent TimeupDeathMessage(Entity entityIn) {
    	return new TranslationTextComponent("death." + mod_LavaCow.MODID + ".timeup", new Object[] {entityIn.getDisplayName()});
    }
    
	public static RegistryKey<Biome> getRegistryKey(Biome BiomeIn) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, BiomeIn.getRegistryName());
	}
}
