package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.item.crafting.WispInaBottleRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURRecipeRegistry {
	public static final SpecialRecipeSerializer<WispInaBottleRecipe> WISP_IN_A_BOTTLE = new SpecialRecipeSerializer<>(WispInaBottleRecipe::new);  

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().register(WISP_IN_A_BOTTLE.setRegistryName(mod_LavaCow.MODID, "crafting_wisp_in_a_bottle"));
	}
}
