package com.Fishmod.mod_LavaCow.worldgen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.blocks.BlockTombStone;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCemeterySmall extends WorldGenerator {
    
    public WorldGenCemeterySmall()
    {
        super();
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
		int facing = rand.nextInt(4);
		for(int i = 0; i < rand.nextInt(5) + 1; i++)
			switch(facing) {
				case 0: //NORTH
					Gen_Cemetery(worldIn, rand, position.north(2 * i).down().down(), facing);
					break;
				case 1: //EAST
					Gen_Cemetery(worldIn, rand, position.east(2 * i).down().down(), facing);
					break;
				case 2: //WEST
					Gen_Cemetery(worldIn, rand, position.west(2 * i).down().down(), facing);
					break;
				case 3: //SOUTH
					Gen_Cemetery(worldIn, rand, position.south(2 * i).down().down(), facing);
					break;
				default:
					break;
			}	
		return true;
    }
	
	private void Gen_Cemetery(World worldIn, Random rand, BlockPos position, int facing) {

		worldIn.setBlockState(position.up(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), 2);
		
		switch(facing) {
			case 0: //NORTH
				if(rand.nextBoolean()) {
					worldIn.setBlockState(position, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.EAST), 3);
			        if (worldIn.getBlockState(position).getBlock() instanceof BlockChest) {
			            TileEntity tileentity = worldIn.getTileEntity(position);
			            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
			                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.CEMETERY_CHEST, rand.nextLong());
			            }
			        }
				}
				/*else if(rand.nextFloat() < 1.0F) {
			        worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
			        TileEntity tileentity = worldIn.getTileEntity(position);
			
			        if (tileentity instanceof TileEntityMobSpawner)
			        {
			            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityGhostRay.class));
			        }
				}*/
				worldIn.setBlockState(position.up().west().up(), Modblocks.TOMBSTONE.getDefaultState().withProperty(BlockTombStone.FACING, EnumFacing.WEST), 2);
				worldIn.setBlockState(position.up().west(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), 2);
				break;
			case 1: //EAST
				if(rand.nextBoolean()) {
					worldIn.setBlockState(position, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH), 3);
			        if (worldIn.getBlockState(position).getBlock() instanceof BlockChest) {
			            TileEntity tileentity = worldIn.getTileEntity(position);
			            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
			                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.CEMETERY_CHEST, rand.nextLong());
			            }
			        }
				}
				worldIn.setBlockState(position.up().north().up(), Modblocks.TOMBSTONE.getDefaultState().withProperty(BlockTombStone.FACING, EnumFacing.NORTH), 2);
				worldIn.setBlockState(position.up().north(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), 2);
				break;
			case 2: //WEST
				if(rand.nextBoolean()) {
					worldIn.setBlockState(position, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH), 3);
			        if (worldIn.getBlockState(position).getBlock() instanceof BlockChest) {
			            TileEntity tileentity = worldIn.getTileEntity(position);
			            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
			                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.CEMETERY_CHEST, rand.nextLong());
			            }
			        }
				}
				worldIn.setBlockState(position.up().south().up(), Modblocks.TOMBSTONE.getDefaultState().withProperty(BlockTombStone.FACING, EnumFacing.SOUTH), 2);
				worldIn.setBlockState(position.up().south(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), 2);
				break;
			case 3: //SOUTH
				if(rand.nextBoolean()) {
					worldIn.setBlockState(position, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.WEST), 3);
			        if (worldIn.getBlockState(position).getBlock() instanceof BlockChest) {
			            TileEntity tileentity = worldIn.getTileEntity(position);
			            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
			                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.CEMETERY_CHEST, rand.nextLong());
			            }
			        }
				}
				worldIn.setBlockState(position.up().east().up(), Modblocks.TOMBSTONE.getDefaultState().withProperty(BlockTombStone.FACING, EnumFacing.EAST), 2);
				worldIn.setBlockState(position.up().east(), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), 2);
				break;
			default:
				break;
		}
		

	}
}
