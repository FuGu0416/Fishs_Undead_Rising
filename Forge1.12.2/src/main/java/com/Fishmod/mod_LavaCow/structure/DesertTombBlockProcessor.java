package com.Fishmod.mod_LavaCow.structure;

import com.Fishmod.mod_LavaCow.entities.EntityAvaton;
import com.Fishmod.mod_LavaCow.entities.EntityMummy;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
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
    private static ResourceLocation[] Desert_Tomb_SpawnEntityID = { 
    		EntityList.getKey(EntityUnburied.class), 
    		EntityList.getKey(EntityAvaton.class), 
    		EntityList.getKey(EntitySpider.class), 
    		EntityList.getKey(EntityCaveSpider.class), 
    		EntityList.getKey(EntityMummy.class)
    		};
    
    public DesertTombBlockProcessor(BlockPos pos, PlacementSettings settings, ResourceLocation loot) {
        super();
        this.loot_table = loot;
    }
    
	@Override
	public BlockInfo processBlock(World worldIn, BlockPos pos, BlockInfo blockInfoIn) {		
		
        if (blockInfoIn.blockState.getBlock() instanceof BlockChest) {
            NBTTagCompound tag = blockInfoIn.tileentityData == null ? new NBTTagCompound() : blockInfoIn.tileentityData;
            tag.setString("LootTable", this.loot_table.toString());
            tag.setString("LootTable", this.loot_table.toString());
            Template.BlockInfo newInfo = new Template.BlockInfo(pos, blockInfoIn.blockState, tag);
            
            return newInfo;
        }
        
        if (blockInfoIn.blockState.getBlock() instanceof BlockMobSpawner) {
            NBTTagCompound tag = blockInfoIn.tileentityData == null ? new NBTTagCompound() : blockInfoIn.tileentityData;
            tag.getCompoundTag("SpawnData").setString("id", Desert_Tomb_SpawnEntityID[worldIn.rand.nextInt(Desert_Tomb_SpawnEntityID.length)].toString());
            Template.BlockInfo newInfo = new Template.BlockInfo(pos, blockInfoIn.blockState, tag);
      
            return newInfo;
        }

        return blockInfoIn;
	}
}
