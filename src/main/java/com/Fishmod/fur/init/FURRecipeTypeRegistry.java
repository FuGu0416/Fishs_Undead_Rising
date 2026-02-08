package com.Fishmod.fur.init;

import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURRecipeTypeRegistry {
	public static final DeferredRegister<RecipeType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, mod_LavaCow.MODID);
	
    public static final RegistryObject<RecipeType<SoulFurnaceRecipe>> SOUL_FURNACE = DEF_REG.register("cooking", () -> registerRecipeType("cooking"));
    
	public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
		return new RecipeType<>()
		{
			public String toString() {
				return mod_LavaCow.MODID + ":" + identifier;
			}
		};
	}
}
