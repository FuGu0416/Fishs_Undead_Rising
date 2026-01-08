package com.Fishmod.fur.integration.jei;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.init.FURBlockRegistry;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class FURJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(mod_LavaCow.MODID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        reg.addRecipeCategories(new SoulFurnaceJeiCategory(reg.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg) {
        var recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(SoulFurnaceRecipe.TYPE);
        reg.addRecipes(FURJeiTypes.SOUL_FURNACE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(
            new ItemStack(FURBlockRegistry.SOUL_FURNACE.get()),
            FURJeiTypes.SOUL_FURNACE
        );
    }
}
