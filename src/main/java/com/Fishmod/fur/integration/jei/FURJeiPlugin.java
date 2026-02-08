package com.Fishmod.fur.integration.jei;

import java.util.ArrayList;
import java.util.List;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURRecipeTypeRegistry;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

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
        var recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(FURRecipeTypeRegistry.SOUL_FURNACE.get());
        List<CraftingRecipe> craft_recipes = new ArrayList<>();
        List<ItemStack> potionStacks = new ArrayList<>();
        
        for (Potion potion : BuiltInRegistries.POTION) {
            if (potion.getEffects().isEmpty()) continue;

            ItemStack stack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(stack, potion);
            potionStacks.add(stack);
        }        
        
        reg.addRecipes(FURJeiTypes.SOUL_FURNACE.get(), recipes);
					
		craft_recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.wisp_ashes"), 
				"jei.wisp_ashes", CraftingBookCategory.MISC, 
				new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE.get()), 
    			NonNullList.of(Ingredient.EMPTY, 
    					Ingredient.of(Items.GLASS_BOTTLE), 
    					Ingredient.of(FURItemRegistry.WISP_ASHES.get()), 
    					Ingredient.of(FURItemRegistry.ECTOPLASM.get()))));
		
		ItemStack previewResult = new ItemStack(FURItemRegistry.INFUSED_BANDAGE.get());
		PotionUtils.setPotion(previewResult, Potions.WATER);
		
	    ShapelessRecipe jeiRecipe = createInfusedBandageJeiRecipe(
	                    new ResourceLocation(mod_LavaCow.MODID, "infused_bandage_jei"),
	                    potionStacks,
	                    previewResult
	            );

	    reg.addRecipes(RecipeTypes.CRAFTING, List.of(jeiRecipe));
		reg.addRecipes(RecipeTypes.CRAFTING, craft_recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(
            new ItemStack(FURBlockRegistry.SOUL_FURNACE.get()),
            FURJeiTypes.SOUL_FURNACE.get()
        );
    }
    
    private static ShapelessRecipe createInfusedBandageJeiRecipe(ResourceLocation id, List<ItemStack> potionInputs, ItemStack resultPreview) {
        NonNullList<Ingredient> ingredients = NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(potionInputs.toArray(ItemStack[]::new)), // POTION（輪播）
                Ingredient.of(FURItemRegistry.CURSEWEAVE_CLOTH.get()),
                Ingredient.of(net.minecraftforge.common.Tags.Items.STRING)
        );

        return new ShapelessRecipe(id, "jei.infused_bandage", CraftingBookCategory.MISC, resultPreview, ingredients);
    }
}
