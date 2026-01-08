package com.Fishmod.fur.integration.jei;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;

import net.minecraft.resources.ResourceLocation;
import mezz.jei.api.recipe.RecipeType;

public class FURJeiTypes {
    public static final RecipeType<SoulFurnaceRecipe> SOUL_FURNACE = new RecipeType<>(new ResourceLocation(mod_LavaCow.MODID, "soul_furnace"), SoulFurnaceRecipe.class);
}
