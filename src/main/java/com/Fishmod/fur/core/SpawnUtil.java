package com.Fishmod.fur.core;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnUtil {
	
	/*public static boolean isDay(World worldIn) {
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
	}*/
	
	/* Used to determine the relative height */
    public static BlockPos getHeight(Entity entityIn) {
    	BlockPos pos = entityIn.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entityIn.blockPosition());
    	
    	if (entityIn.level().dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!entityIn.level().getBlockState(pos).isAir());

    		do {
    			pos = pos.below();
            } while (entityIn.level().getBlockState(pos).isAir() && pos.getY() > 0);
    	}

    	return pos;
    }

    public static BlockPos getHeight(Level worldIn, BlockPos posIn) {
    	BlockPos pos = worldIn.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, posIn);
    	
    	if (worldIn.dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!worldIn.getBlockState(pos).isAir());

    		do {
    			pos = pos.below();
            } while (worldIn.getBlockState(pos).isAir() && pos.getY() > 0);
    	}

    	return pos;
    }
    
    public static LivingEntity getEntityByUniqueId(UUID uniqueId, ServerLevel worldIn){
        Entity entity = worldIn.getEntity(uniqueId);
        
        if(entity != null && entity instanceof LivingEntity)
        	return (LivingEntity) entity;

        return null;
    }
    
    public static BlockPos isNearBlock(ServerLevelAccessor p_223316_1_, Block BlockIn, BlockPos pos, int r) {
        int dx = (int) Math.floor(pos.getX());
        int dy = (int) Math.floor(pos.getY());
        int dz = (int) Math.floor(pos.getZ());
        
        for(int i = dx - r; i < dx + r; i++)
        	for(int j = dy - r; j < dy + r; j++)
        		for(int k = dz - r; k < dz + r; k++)
        			if(p_223316_1_.getBlockState(new BlockPos(i, j, k)).getBlock().equals(BlockIn)) {
                		return new BlockPos(i, j, k);
        			}

    	return null;
    }
    
	/*public static Entity gotRiderEntity(List<Entity> listIn, EntityType<? extends Entity> typeIn) {
		for(Entity C : listIn) {
			if (C.getType().equals(typeIn)) {
				return C;	
			}
		}
		
		return null;
	}*/
    
    public static Component TimeupDeathMessage(Entity entityIn) {
    	return Component.translatable("death." + mod_LavaCow.MODID + ".timeup", new Object[] {entityIn.getDisplayName()});
    }
    
	/*public static RegistryKey<Biome> getRegistryKey(Biome BiomeIn) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, BiomeIn.getRegistryName());
	}*/
	
    @Nullable
    public static <T extends LivingEntity> T trySpawnEntity(EntityType<T> entityIn, ServerLevel worldIn, BlockPos blockpos) {
    	for(int i = 0; i < 10; ++i) {
    		int d0 = (i == 0) ? 0 : (worldIn.random.nextInt(4) - 2);
    		int d1 = (i == 0) ? 0 : (worldIn.random.nextInt(4) - 2);
    		BlockPos blockpos1 = findSpawnPositionInColumn(worldIn, blockpos, d0, d1);
    		if (blockpos1 != null) {
    			return entityIn.spawn(worldIn, blockpos1.below(), MobSpawnType.MOB_SUMMONED);
    		}
    	}
    	
    	return null;
	}

	@Nullable
	private static BlockPos findSpawnPositionInColumn(ServerLevel worldIn, BlockPos p_241433_1_, int p_241433_2_, int p_241433_4_) {
		BlockPos blockpos = p_241433_1_.offset(p_241433_2_, 6, p_241433_4_);
		BlockState blockstate = worldIn.getBlockState(blockpos);

		for (int j = 6; j >= -6; --j) {
			BlockPos blockpos1 = blockpos;
			BlockState blockstate1 = blockstate;
			blockpos = blockpos.below();
			blockstate = worldIn.getBlockState(blockpos);
			if ((blockstate1.isAir() || blockstate1.liquid()) && blockstate.isSolid()) {
				return blockpos1.offset(0, 1, 0);
			}
		}

		return null;
	}
	
	public static double clamp(double input, double min, double max) {
		if (input < min) {
			return min;
		} else {
			return input > max ? max : input;
		}
	}
}
