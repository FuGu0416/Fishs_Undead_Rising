package com.Fishmod.mod_LavaCow.world.gen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.block.TombStoneBlock;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenCemeterySmall extends Feature<NoFeatureConfig> {
    
    public WorldGenCemeterySmall(Codec<NoFeatureConfig> p_i232004_1_)
    {
        super(p_i232004_1_);
    }
	
	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator p_241855_2_, Random rand, BlockPos position, NoFeatureConfig p_241855_5_) {
		BlockState blockstate = worldIn.getBlockState(worldIn.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position).below());
		if (blockstate.getMaterial() != Material.DIRT && blockstate.getMaterial() != Material.GRASS && blockstate.getMaterial() != Material.SAND) {			
			return false;
		}
		
		int facing = rand.nextInt(4);
		for(int i = 0; i < rand.nextInt(5) + 1; i++)
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
		return true;
    }
	
	private void Gen_Cemetery(ISeedReader worldIn, Random rand, BlockPos position, int facing) {
		
		worldIn.setBlock(position.above(), Blocks.PODZOL.defaultBlockState(), 2);
		
		switch(facing) {
			case 0: //NORTH
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST), 2);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().west().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.WEST), 2);
				worldIn.setBlock(position.above().west(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 1: //EAST
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().north().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.NORTH), 2);
				worldIn.setBlock(position.above().north(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 2: //WEST
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH), 3);
					LockableLootTileEntity.setLootTable(worldIn, rand, position, LootTableHandler.CEMETERY_CHEST);
				}
				worldIn.setBlock(position.above().south().above(), FURBlockRegistry.TOMBSTONE.defaultBlockState().setValue(TombStoneBlock.FACING, Direction.SOUTH), 2);
				worldIn.setBlock(position.above().south(), Blocks.PODZOL.defaultBlockState(), 2);
				break;
			case 3: //SOUTH
				if(rand.nextBoolean()) {
					worldIn.setBlock(position, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST), 3);
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