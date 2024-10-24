package com.Fishmod.mod_LavaCow.integration.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	public static final ResourceLocation MOD = new ResourceLocation(mod_LavaCow.MODID, mod_LavaCow.MODID);
	
	@Override
	public ResourceLocation getPluginUid() {
		return MOD;
	}
	
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
    	List<ICraftingRecipe> recipes = new ArrayList<>();
    	IIngredientManager ingredientManager = registration.getIngredientManager();
    	IVanillaRecipeFactory vanillaRecipeFactory = registration.getVanillaRecipeFactory();
		ItemStack whetstone[] = {new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE)};
		CompoundNBT compoundnbt[] = {new CompoundNBT(), new CompoundNBT(), new CompoundNBT(), new CompoundNBT(), new CompoundNBT()};
		
		for (int i = 0; i < 5; i++) {
			compoundnbt[i].putInt("level", i + 1);
			whetstone[i].setTag(compoundnbt[i]);			
			
			recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.sinister_whetstone.lvl" + (i + 1)), "", 
					whetstone[i], 
					NonNullList.of(Ingredient.EMPTY, 
							Ingredient.of((i == 0) ? new ItemStack(FURItemRegistry.ECTOPLASM_INGOT) : whetstone[i - 1]), 
							Ingredient.of(FURItemRegistry.SCYTHE_CLAW))));    		
		}
		
    	recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.wisp_ashes"), "", 
    			new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE), 
    			NonNullList.of(Ingredient.EMPTY, 
    					Ingredient.of(Items.GLASS_BOTTLE), 
    					Ingredient.of(FURItemRegistry.WISP_ASHES), 
    					Ingredient.of(FURItemRegistry.ECTOPLASM))));
    			  	  	
    	registration.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
    	
    	if (FURConfig.Enchantment_Enable.get()) {
    		registration.addRecipes(getAnvilRecipes(LootTableHandler.ANVIL_RECIPE, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    	}
    }
    
    private List<Object> getAnvilRecipes(List<Tuple<Enchantment, Tuple<ItemStack, Integer>>> ItemLists, IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
    	List<Object> recipes = new ArrayList<>();
    	Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM);
    	Enchantment enchantment;
    	
    	for (ItemStack ingredient : ingredients) {
			if (ingredient.isEnchantable()) {
				for (Tuple<Enchantment, Tuple<ItemStack, Integer>> recipe : ItemLists) {
					enchantment =  recipe.getA();
					if (enchantment.canEnchant(ingredient)) {
						List<ItemStack> perLevelBooks = Lists.newArrayList();
						List<ItemStack> perLevelOutputs = Lists.newArrayList();
						
						perLevelBooks.add(recipe.getB().getA());
						
						ItemStack withEnchant = ingredient.copy();
						withEnchant.enchant(enchantment, recipe.getB().getB());
						perLevelOutputs.add(withEnchant);
						
						Object anvilRecipe = vanillaRecipeFactory.createAnvilRecipe(ingredient, perLevelBooks, perLevelOutputs);
						recipes.add(anvilRecipe);
					}
				}
			}
    	}
					
		return recipes;    	
    }

}
