package com.Fishmod.fur.item.crafting;

import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURRecipeRegistry;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;

public class InfusedBandageRecipe extends CustomRecipe {
    public InfusedBandageRecipe(ResourceLocation resource, CraftingBookCategory category) {
		super(resource, category);
	}

	@Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean hasCloth = false;
        boolean hasString = false;
        boolean hasPotion = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

			if (!stack.isEmpty()) {
				if (stack.is(FURItemRegistry.CURSEWEAVE_CLOTH.get()) && !hasCloth) {
					hasCloth = true;
	            } else if (stack.is(Items.STRING) && !hasString) {
	            	hasString = true;
	            } else if (stack.is(net.minecraft.world.item.Items.POTION) && !hasPotion) {
	            	hasPotion = true;
	            } else {
	            	return false;
	            }
	         }
        }

        return hasCloth && hasString && hasPotion;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        ItemStack potion = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof PotionItem) {
                potion = stack;
                break;
            }
        }

        ItemStack result = new ItemStack(FURItemRegistry.INFUSED_BANDAGE.get());
        result.getOrCreateTag().put("Potion", potion.getTag().get("Potion"));

        return result;
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {

        NonNullList<ItemStack> remains =
            NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.getItem() instanceof PotionItem) {
                remains.set(i, new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE));
            }
        }

        return remains;
    }

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width >= 2 && height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.INFUSED_BANDAGE.get();
	}
}
