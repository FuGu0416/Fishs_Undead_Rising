package com.Fishmod.fur.integration.jei;

import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;

import mezz.jei.api.recipe.RecipeType;

public class FURJeiTypes {
    public static final Supplier<RecipeType<SoulFurnaceRecipe>> SOUL_FURNACE = () -> RecipeType.create(mod_LavaCow.MODID, "cooking", SoulFurnaceRecipe.class);
}
