package com.Fishmod.mod_LavaCow.core;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class FURBrewingRecipe extends BrewingRecipe {

	public FURBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		super(input, ingredient, output);
	}
	
    @Override
    public boolean isInput(@Nonnull ItemStack stack)
    {
    	boolean flag = true;
    	CompoundNBT nbt1, nbt2;
    	
    	nbt1 = stack.getTag();
    	nbt2 = this.getInput().getItems()[0].getTag();
   	
    	if(nbt1 != null && nbt2 != null) {    	
    		flag = nbt1.getString("Potion").compareTo(nbt2.getString("Potion")) == 0;
    	}
    	
    	return this.getInput().test(stack) && flag;
    }

}
