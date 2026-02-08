package com.Fishmod.fur.client.recipebook;

import java.util.function.Supplier;

import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURRecipeTypeRegistry;
import com.google.common.base.Suppliers;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

public class RecipeCategories {
	public static final Supplier<RecipeBookCategories> COOKING = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING", new ItemStack(FURItemRegistry.GHOST_JELLY.get())));
	
	public static void init(RegisterRecipeBookCategoriesEvent event) {
		event.registerRecipeCategoryFinder(FURRecipeTypeRegistry.SOUL_FURNACE.get(), recipe -> COOKING.get());
	}
}
