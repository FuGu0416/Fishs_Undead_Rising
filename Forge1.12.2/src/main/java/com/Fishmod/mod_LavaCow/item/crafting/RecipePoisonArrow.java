package com.Fishmod.mod_LavaCow.item.crafting;

import java.util.List;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipePoisonArrow extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
        	int pivot = -1;
        	for (int i = 0; i < inv.getWidth(); ++i) {
        		ItemStack itemstack = inv.getStackInRowAndColumn(i, 0);
        		
        		if(itemstack.getItem().equals(FishItems.POISONSTINGER) && pivot == -1)
        			pivot = i;
        		else if(!itemstack.isEmpty() && pivot != -1)
        			return false;
            }
        	
        	if(pivot == -1) return false;
        	
        	for (int i = 0; i < inv.getWidth(); ++i)
        		for (int j = 1; j < inv.getHeight(); ++j) {
        		ItemStack itemstack = inv.getStackInRowAndColumn(i, j);
        		if(i != pivot && !itemstack.isEmpty())return false;
        		else if(i == pivot && j == 1 && !FindinOreDictionary("stickWood", itemstack))return false;
        		else if(i == pivot && j == 2 && !FindinOreDictionary("feather", itemstack))return false;
        	}

            return true;
        }
        else {
            return false;
        }
	}
	
	private boolean FindinOreDictionary(String name, ItemStack itemstack) {
		boolean flag = false;
		
		List<ItemStack> ores = OreDictionary.getOres(name);
    	for(ItemStack ore: ores)
    		if(OreDictionary.itemMatches(ore, itemstack, false)) { 
    			flag = true;
    		}
    	
    	return flag;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 4);
        PotionUtils.addPotionToItemStack(itemstack1, PotionTypes.STRONG_POISON);
        return itemstack1;
	}
	
	@Override
	public boolean canFit(int width, int height) {
        return width > 2 && height > 2;
	}

    public boolean isDynamic() {
        return true;
    }

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}
