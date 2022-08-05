package com.Fishmod.mod_LavaCow.item.crafting;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURRecipeRegistry;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WispInaBottleRecipe extends SpecialRecipe {
	public WispInaBottleRecipe(ResourceLocation p_i50020_1_) {
		super(p_i50020_1_);
	}

	public boolean matches(CraftingInventory p_77569_1_, World p_77569_2_) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;

		for(int i = 0; i < p_77569_1_.getContainerSize(); ++i) {
			ItemStack itemstack = p_77569_1_.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() == FURItemRegistry.WISP_ASHES && !flag2) {
					flag2 = true;
	            } else if (itemstack.getItem() == FURItemRegistry.ECTOPLASM && !flag1) {
	            	flag1 = true;
	            } else if (itemstack.getItem() == Items.GLASS_BOTTLE && !flag) {
	            	flag = true;
	            }
	         }
		}

		return flag && flag2 && flag1;
	}

	public ItemStack assemble(CraftingInventory p_77572_1_) {
		ItemStack itemstack = ItemStack.EMPTY;

		for(int i = 0; i < p_77572_1_.getContainerSize(); ++i) {
			ItemStack itemstack1 = p_77572_1_.getItem(i);
			if (!itemstack1.isEmpty() && itemstack1.getItem() == FURItemRegistry.WISP_ASHES) {
				itemstack = itemstack1;
	            break;
			}
		}

		ItemStack itemstack2 = new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE, 1);
		if (itemstack.getTag() != null && itemstack.getTag().contains("WispData")) {
	        itemstack2.setTag(itemstack.getTag());
	        
	        if (itemstack.getHoverName().getStyle().isItalic()) {
	        	itemstack2.setHoverName(itemstack.getHoverName());
	        }
		}

		return itemstack2;
	}

	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		return p_194133_1_ >= 2 && p_194133_2_ >= 2;
	}

	public IRecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.WISP_IN_A_BOTTLE;
	}
}