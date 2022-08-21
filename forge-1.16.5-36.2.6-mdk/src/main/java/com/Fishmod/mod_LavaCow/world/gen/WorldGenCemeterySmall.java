package com.Fishmod.mod_LavaCow.world.gen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.block.TombStoneBlock;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.GraveRobberEntity;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenCemeterySmall extends Feature<NoFeatureConfig> {
    
    public WorldGenCemeterySmall(Codec<NoFeatureConfig> p_i232004_1_) {
        super(p_i232004_1_);
    }
	
	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator p_241855_2_, Random rand, BlockPos position, NoFeatureConfig p_241855_5_) {		
		if (!FURConfig.Generate_Cemetery.get() || (rand.nextInt(10000) > FURConfig.Cemetery_SpawnRate.get())) {			
			return false;
		}
		
		int facing = rand.nextInt(4);
		for(int i = 0; i < rand.nextInt(5) + 1; i++) {
			switch(facing) {
				case 0: //NORTH
					Gen_Cemetery(worldIn, rand, worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.north(2 * i)).below().below(), facing);
					break;
				case 1: //EAST
					Gen_Cemetery(worldIn, rand, worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.east(2 * i)).below().below(), facing);
					break;
				case 2: //WEST
					Gen_Cemetery(worldIn, rand, worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.west(2 * i)).below().below(), facing);
					break;
				case 3: //SOUTH
					Gen_Cemetery(worldIn, rand, worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.south(2 * i)).below().below(), facing);
					break;
				default:
					break;
			}	
		}
		
		if (rand.nextInt(60) < FURConfig.pSpawnRate_GraveRobber.get()) {
			BlockPos pos = worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position);
	        	
	    	for (int i = 0; i < rand.nextInt(5); ++i) {
	            BlockPos blockpos = pos.offset(-2 + rand.nextInt(5), 0, -2 + rand.nextInt(5));
	            GraveRobberEntity entity = FUREntityRegistry.GRAVEROBBER.create(worldIn.getLevel());	            
	            entity.moveTo(blockpos, 0.0F, 0.0F);      
	            entity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(blockpos), SpawnReason.NATURAL, null, (CompoundNBT)null);
	            entity.setPersistenceRequired();
	            worldIn.addFreshEntity(entity);
	        }	
		}
    	
		return true;
    }
	
	private void Gen_Cemetery(ISeedReader worldIn, Random rand, BlockPos position, int facing) {
		BlockState blockstate = worldIn.getBlockState(worldIn.getHeightmapPos(Heightmap.Type.WORLD_SURFACE, position).below());

		if (blockstate.getMaterial() != Material.DIRT && blockstate.getMaterial() != Material.GRASS && blockstate.getMaterial() != Material.SAND) {			
			return;
		}
		
		worldIn.setBlock(position.above(), Blocks.PODZOL.defaultBlockState(), 2);
		
		switch(facing) {
			case 0: //NORTH
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.BARREL.defaultBlockState(), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().west().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.WEST), 2);
				worldIn.setBlock(position.above().west(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 1: //EAST
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.BARREL.defaultBlockState(), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().north().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.NORTH), 2);
				worldIn.setBlock(position.above().north(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 2: //WEST
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.BARREL.defaultBlockState(), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().south().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.SOUTH), 2);
				worldIn.setBlock(position.above().south(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 3: //SOUTH
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.BARREL.defaultBlockState(), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().east().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.EAST), 2);
				worldIn.setBlock(position.above().east(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			default:
				break;
		}
		

	}
}
