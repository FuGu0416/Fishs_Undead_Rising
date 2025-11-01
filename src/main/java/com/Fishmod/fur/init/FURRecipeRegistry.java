package com.Fishmod.fur.init;


import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.item.crafting.WispInaBottleRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FURRecipeRegistry {
	public static final DeferredRegister<RecipeSerializer<?>> DEF_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, mod_LavaCow.MODID);
	
	public static final RegistryObject<RecipeSerializer<?>> WISP_IN_A_BOTTLE = DEF_REG.register("wisp_in_a_bottle", () -> new SimpleCraftingRecipeSerializer<>(WispInaBottleRecipe::new));
	
	//public static final SpecialRecipeSerializer<SinisterWhetstoneRecipe> SINISTER_WHETSTONE = new SpecialRecipeSerializer<>(SinisterWhetstoneRecipe::new);  
	//event.getRegistry().register(SINISTER_WHETSTONE.setRegistryName(mod_LavaCow.MODID, "crafting_sinister_whetstone"));

}
