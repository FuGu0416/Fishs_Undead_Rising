package com.Fishmod.mod_LavaCow.world.gen.processor;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURProcessors;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.registries.ForgeRegistries;

public class DesertTombProcessor extends StructureProcessor {
    public static final DesertTombProcessor INSTANCE = new DesertTombProcessor();
    public static final Codec<DesertTombProcessor> CODEC = Codec.unit(() -> INSTANCE);
    
    private static List<EntityType<?>> Desert_Tomb_SpawnEntityID = Lists.newArrayList( 
    		FUREntityRegistry.UNBURIED, FUREntityRegistry.AVATON, FUREntityRegistry.MUMMY, EntityType.SPIDER, EntityType.CAVE_SPIDER
    		);
    
    @Override
    public Template.BlockInfo process(IWorldReader worldReader, BlockPos pos, BlockPos pos2, Template.BlockInfo infoIn1, Template.BlockInfo infoIn2, PlacementSettings settings,@Nullable Template template) {
        Random rand = settings.getRandom(infoIn2.pos);
        
        if (infoIn2.state.getBlock() == Blocks.SPAWNER) {
            CompoundNBT tag = new CompoundNBT();
            CompoundNBT spawnData = new CompoundNBT();
            ResourceLocation spawnerMobId = ForgeRegistries.ENTITIES.getKey(Desert_Tomb_SpawnEntityID.get(rand.nextInt(Desert_Tomb_SpawnEntityID.size())));
            if (spawnerMobId != null) {
                spawnData.putString("id", spawnerMobId.toString());
                tag.remove("SpawnPotentials");
                tag.put("SpawnData", spawnData.copy());
            }
            Template.BlockInfo newInfo = new Template.BlockInfo(infoIn2.pos, Blocks.SPAWNER.defaultBlockState(), tag);
            return newInfo;
        }
 
        if (infoIn2.state.getBlock() == Blocks.CHEST) {
        	LockableLootTileEntity.setLootTable(worldReader, rand, infoIn2.pos, LootTableHandler.DESERT_TOMB_CHEST);
        }
        
        if (infoIn2.state.getBlock() == Blocks.CUT_SANDSTONE || infoIn2.state.getBlock() == Blocks.CHISELED_SANDSTONE) {
        	if(rand.nextFloat() < 0.02F) {
                Template.BlockInfo newInfo = new Template.BlockInfo(infoIn2.pos, Blocks.SAND.defaultBlockState(), null);
                return newInfo;        		
        	} else if(rand.nextFloat() < 0.1F) {
                Template.BlockInfo newInfo = new Template.BlockInfo(infoIn2.pos, Blocks.SANDSTONE.defaultBlockState(), null);
                return newInfo;       		
        	}
        	
        	return infoIn2;
        }
        
        return infoIn2;
    }
    
	@Override
	protected IStructureProcessorType<?> getType() {
		return FURProcessors.DESERTTOMBPROCESSOR;
	}
}
