package com.Fishmod.fur.core;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class FURBrewingRecipe extends BrewingRecipe {

	public FURBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		super(input, ingredient, output);
	}
	
    @Override
    public boolean isInput(@Nonnull ItemStack stack) {
    	boolean flag = true;
    	CompoundTag nbt1, nbt2;
    	
    	nbt1 = stack.getTag();
    	nbt2 = this.getInput().getItems()[0].getTag();
   	
    	if(nbt1 != null && nbt2 != null) {    	
    		flag = nbt1.getString("Potion").compareTo(nbt2.getString("Potion")) == 0;
    	}
    	
    	return this.getInput().test(stack) && flag;
    }

}
