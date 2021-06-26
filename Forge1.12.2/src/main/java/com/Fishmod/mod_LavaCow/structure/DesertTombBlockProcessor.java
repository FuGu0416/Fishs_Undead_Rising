package com.Fishmod.mod_LavaCow.structure;

import net.minecraft.block.BlockChest;
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
    
    public DesertTombBlockProcessor(BlockPos pos, PlacementSettings settings, ResourceLocation loot) {
        super();
        this.loot_table = loot;
    }
    
	@Override
	public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {		
		
        if (blockInfoIn.blockState.getBlock() instanceof BlockChest) {
            NBTTagCompound tag = blockInfoIn.tileentityData == null ? new NBTTagCompound() : blockInfoIn.tileentityData;
            tag.setString("LootTable", this.loot_table.toString());
            Template.BlockInfo newInfo = new Template.BlockInfo(pos, blockInfoIn.blockState, tag);
            
            return newInfo;
        }     

        return blockInfoIn;
	}
}
