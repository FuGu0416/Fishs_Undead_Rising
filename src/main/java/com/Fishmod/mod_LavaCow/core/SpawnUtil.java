package com.Fishmod.mod_LavaCow.core;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.Heightmap;

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
    	BlockPos pos = entityIn.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, entityIn.blockPosition());
    	
    	if (entityIn.level.dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!entityIn.level.getBlockState(pos).getMaterial().equals(Material.AIR));

    		do {
    			pos = pos.below();
            } while (entityIn.level.getBlockState(pos).getMaterial().equals(Material.AIR) && pos.getY() > 0);
    	}

    	return pos;
    }

    public static BlockPos getHeight(World worldIn, BlockPos posIn) {
    	BlockPos pos = worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, posIn);
    	
    	if (worldIn.dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!worldIn.getBlockState(pos).getMaterial().equals(Material.AIR));

    		do {
    			pos = pos.below();
            } while (worldIn.getBlockState(pos).getMaterial().equals(Material.AIR) && pos.getY() > 0);
    	}

    	return pos;
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
    
	public static Entity gotRiderEntity(List<Entity> listIn, EntityType<? extends Entity> typeIn) {
		for(Entity C : listIn) {
			if (C.getType().equals(typeIn)) {
				return C;	
			}
		}
		
		return null;
	}
    
    public static TranslationTextComponent TimeupDeathMessage(Entity entityIn) {
    	return new TranslationTextComponent("death." + mod_LavaCow.MODID + ".timeup", new Object[] {entityIn.getDisplayName()});
    }
    
	public static RegistryKey<Biome> getRegistryKey(Biome BiomeIn) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, BiomeIn.getRegistryName());
	}
	
    @Nullable
    public static <T extends CreatureEntity> T trySpawnEntity(EntityType<T> entityIn, ServerWorld worldIn, BlockPos blockpos) {
    	for(int i = 0; i < 10; ++i) {
    		double d0 = (i == 0) ? 0.0D : (double)(worldIn.random.nextInt(4) - 2);
    		double d1 = (i == 0) ? 0.0D : (double)(worldIn.random.nextInt(4) - 2);
    		BlockPos blockpos1 = findSpawnPositionInColumn(worldIn, blockpos, d0, d1);
    		if (blockpos1 != null) {
    			T summonedEntity = entityIn.create(worldIn, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos1, SpawnReason.MOB_SUMMONED, false, false);
    			if (summonedEntity != null) {
    				if (summonedEntity.checkSpawnRules(worldIn, SpawnReason.MOB_SUMMONED) && summonedEntity.checkSpawnObstruction(worldIn)) {
    					worldIn.addFreshEntityWithPassengers(summonedEntity);
    					return summonedEntity;
    				}

    				summonedEntity.remove();
    			}
    		}
    	}
    	
    	return null;
	}

	@Nullable
	private static BlockPos findSpawnPositionInColumn(ServerWorld worldIn, BlockPos p_241433_1_, double p_241433_2_, double p_241433_4_) {
		BlockPos blockpos = p_241433_1_.offset(p_241433_2_, 6.0D, p_241433_4_);
		BlockState blockstate = worldIn.getBlockState(blockpos);

		for (int j = 6; j >= -6; --j) {
			BlockPos blockpos1 = blockpos;
			BlockState blockstate1 = blockstate;
			blockpos = blockpos.below();
			blockstate = worldIn.getBlockState(blockpos);
			if ((blockstate1.getMaterial().equals(Material.AIR) || blockstate1.getMaterial().isLiquid()) && blockstate.getMaterial().isSolidBlocking()) {
				return blockpos1.offset(0.0D, 1.0D, 0.0D);
			}
		}

		return null;
	}
}
