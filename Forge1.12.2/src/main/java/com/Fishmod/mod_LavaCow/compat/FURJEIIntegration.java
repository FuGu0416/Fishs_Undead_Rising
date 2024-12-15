package com.Fishmod.mod_LavaCow.compat;

import java.util.Collections;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@JEIPlugin
public class FURJEIIntegration implements IModPlugin {
    @JERPlugin
    public static IJERAPI jerAPI;
    public static World world;

    @Override
    public void register(IModRegistry registry) {
        // Only enable JEI support when anvil recipes are enabled for Soulforged items
        if (Modconfig.Soulforged_Anvil_Recipes) {
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.MOLTENAXE)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_AXE)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.MOLTENHAMMER)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HAMMER)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.MOLTENPAN)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_PAN)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.FELARMOR_HELMET)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGEDARMOR_HELMET)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.FELARMOR_CHESTPLATE)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGEDARMOR_CHESTPLATE)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.FELARMOR_LEGGINGS)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGEDARMOR_LEGGINGS)))), VanillaRecipeCategoryUid.ANVIL);
            registry.addRecipes(Collections.singletonList(new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(FishItems.FELARMOR_BOOTS)), Collections.singletonList(new ItemStack(FishItems.SOULFORGED_HEART)), Collections.singletonList(new ItemStack(FishItems.SOULFORGEDARMOR_BOOTS)))), VanillaRecipeCategoryUid.ANVIL);
        }
    }
}
