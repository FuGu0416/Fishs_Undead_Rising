package com.Fishmod.mod_LavaCow.core;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipe;

// Fixes how NBT or metadata is handled
public class FishBrewingRecipe extends BrewingRecipe {
	private ItemStack input;

	public FishBrewingRecipe(@Nonnull ItemStack input, @Nonnull ItemStack ingredient, @Nonnull ItemStack output) {
		super(input, ingredient, output);
		this.input = input;
	}
	
    @Override
    public boolean isInput(@Nonnull ItemStack stack) {
        return super.isInput(stack) && ItemStack.areItemStackTagsEqual(input, stack);
    }
}
