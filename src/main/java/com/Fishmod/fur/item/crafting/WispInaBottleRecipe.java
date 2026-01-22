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

	public boolean matches(CraftingContainer inv, Level level) {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;

		for(int i = 0; i < inv.getContainerSize(); ++i) {
			ItemStack stack = inv.getItem(i);
			
			if (!stack.isEmpty()) {
				if (stack.is(FURItemRegistry.WISP_ASHES.get()) && !flag2) {
					flag2 = true;
	            } else if (stack.is(FURItemRegistry.ECTOPLASM.get()) && !flag1) {
	            	flag1 = true;
	            } else if (stack.is(Items.GLASS_BOTTLE) && !flag) {
	            	flag = true;
	            } else {
	            	return false;
	            }
	         }
		}

		return flag && flag2 && flag1;
	}

	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		ItemStack stack = ItemStack.EMPTY;

		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack stack1 = container.getItem(i);
			if (!stack1.isEmpty() && stack1.getItem() == FURItemRegistry.WISP_ASHES.get()) {
				stack = stack1;
	            break;
			}
		}

		ItemStack stack2 = new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE.get(), 1);
		if (stack.getTag() != null && stack.getTag().contains("WispData")) {
	        stack2.setTag(stack.getTag());
	        
	        if (stack.getHoverName().getStyle().isItalic()) {
	        	stack2.setHoverName(stack.getHoverName());
	        }
		}

		return stack2;
	}

	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		return p_194133_1_ >= 2 && p_194133_2_ >= 2;
	}

	public RecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.WISP_IN_A_BOTTLE.get();
	}
}