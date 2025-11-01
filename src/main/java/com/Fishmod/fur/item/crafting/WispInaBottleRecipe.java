package com.Fishmod.fur.item.crafting;

import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURRecipeRegistry;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class WispInaBottleRecipe extends CustomRecipe {
	public WispInaBottleRecipe(ResourceLocation resource, CraftingBookCategory category) {
		super(resource, category);
	}

	public boolean matches(CraftingContainer p_77569_1_, Level p_77569_2_) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;

		for(int i = 0; i < p_77569_1_.getContainerSize(); ++i) {
			ItemStack itemstack = p_77569_1_.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() == FURItemRegistry.WISP_ASHES.get() && !flag2) {
					flag2 = true;
	            } else if (itemstack.getItem() == FURItemRegistry.ECTOPLASM.get() && !flag1) {
	            	flag1 = true;
	            } else if (itemstack.getItem() == Items.GLASS_BOTTLE && !flag) {
	            	flag = true;
	            } else {
	            	return false;
	            }
	         }
		}

		return flag && flag2 && flag1;
	}

	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		ItemStack itemstack = ItemStack.EMPTY;

		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack1 = container.getItem(i);
			if (!itemstack1.isEmpty() && itemstack1.getItem() == FURItemRegistry.WISP_ASHES.get()) {
				itemstack = itemstack1;
	            break;
			}
		}

		ItemStack itemstack2 = new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE.get(), 1);
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

	public RecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.WISP_IN_A_BOTTLE.get();
	}
}