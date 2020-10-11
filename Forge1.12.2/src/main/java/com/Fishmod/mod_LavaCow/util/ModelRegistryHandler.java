package com.Fishmod.mod_LavaCow.util;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.compat.TinkersCompatBridge;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ModelRegistryHandler {
	
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Field field : FishItems.class.getDeclaredFields()) {
            Object obj;
			try {
				obj = field.get(null);
                if (obj instanceof Item) {
                	Item item = (Item) obj;
                	registerModel(item);
                }
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        
        TinkersCompatBridge.loadTinkersClientModels(event);
    }
 
    private static void registerModel(Item item) {
    	if(item.equals(FishItems.PARASITE_ITEM)) {
    		for(Integer i = 0; i < 3 ; i++)
    			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + i.toString()));  
    	}
    	else if(item.equals(FishItems.PTERA_WING)) {
    		for(Integer i = 0; i < 2 ; i++)
    			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + i.toString()));  
    	}
    	else
    		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));  
    }
}
