package com.Fishmod.mod_LavaCow.structure;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class DesertTombBlockProcessor implements ITemplateProcessor {
    private ResourceLocation loot_table;
    private boolean isBadland;
    
    public DesertTombBlockProcessor(BlockPos pos, PlacementSettings settings, ResourceLocation loot, boolean isBadland) {
        super();
        this.loot_table = loot;
        this.isBadland = isBadland;
    }
    
	@SuppressWarnings("deprecation")
	@Override
	public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {				
        if (blockInfoIn.blockState.getBlock() instanceof BlockChest) {
            NBTTagCompound tag = blockInfoIn.tileentityData == null ? new NBTTagCompound() : blockInfoIn.tileentityData;
            tag.setString("LootTable", this.loot_table.toString());
            Template.BlockInfo newInfo = new Template.BlockInfo(pos, blockInfoIn.blockState, tag);
            
            return newInfo;
        }    

        if (this.isBadland) {
			if (blockInfoIn.blockState.getBlock().equals(Blocks.SAND)) {
				return new Template.BlockInfo(pos, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.SANDSTONE) && blockInfoIn.blockState.getValue(BlockSandStone.TYPE).equals(BlockSandStone.EnumType.DEFAULT)) {
				return new Template.BlockInfo(pos, Blocks.RED_SANDSTONE.getDefaultState(), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.SANDSTONE) && blockInfoIn.blockState.getValue(BlockSandStone.TYPE).equals(BlockSandStone.EnumType.CHISELED)) {
				return new Template.BlockInfo(pos, Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.SANDSTONE) && blockInfoIn.blockState.getValue(BlockSandStone.TYPE).equals(BlockSandStone.EnumType.SMOOTH)) {
				return new Template.BlockInfo(pos, Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.SANDSTONE_STAIRS)) {
				return new Template.BlockInfo(pos, Blocks.RED_SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockInfoIn.blockState.getValue(BlockStairs.FACING)), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.STONE_SLAB)) {
				return new Template.BlockInfo(pos, Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockStoneSlabNew.VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE), null);
			}
			
			if (blockInfoIn.blockState.getBlock().equals(Blocks.STAINED_HARDENED_CLAY)) {
				return new Template.BlockInfo(pos, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW), null);
			}
        }

        return blockInfoIn;
	}
}
