package com.Fishmod.mod_LavaCow.integration.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREnchantmentRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

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
			
			registration.addRecipes(getAnvilRecipes(whetstone[i], FUREnchantmentRegistry.CRITICALBOOST, i + 1, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
		}
		
    	recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.wisp_ashes"), "", new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE), NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(FURItemRegistry.WISP_ASHES), Ingredient.of(FURItemRegistry.ECTOPLASM))));
    			
    	recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.sinister_whetstone.lvl1"), "", whetstone[0], NonNullList.of(Ingredient.EMPTY, Ingredient.of(FURItemRegistry.ECTOPLASM_INGOT), Ingredient.of(FURItemRegistry.SCYTHE_CLAW))));
    	
    	for (int i = 1; i < 5; i++) {  		
    		recipes.add(new ShapelessRecipe(new ResourceLocation(mod_LavaCow.MODID, "jei.sinister_whetstone.lvl" + (i + 1)), "", whetstone[i], NonNullList.of(Ingredient.EMPTY, Ingredient.of(whetstone[i - 1]), Ingredient.of(FURItemRegistry.SCYTHE_CLAW))));    		
    	}
    	  	
    	registration.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURItemRegistry.PARASITE_COMMON), Enchantments.FISHING_SPEED, 1, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURBlockRegistry.GLOWSHROOM.asItem()), Enchantments.FISHING_SPEED, 3, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURItemRegistry.POISONSPORE), FUREnchantmentRegistry.POISONOUS, 3, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURItemRegistry.UNDYINGHEART), FUREnchantmentRegistry.LIFESTEAL, 3, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURItemRegistry.ACIDICHEART), FUREnchantmentRegistry.CORROSIVE, 1, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);    	
    	registration.addRecipes(getAnvilRecipes(new ItemStack(FURItemRegistry.HOLY_WATER), Enchantments.SMITE, 3, vanillaRecipeFactory, ingredientManager), VanillaRecipeCategoryUid.ANVIL);
    }
    
    private List<Object> getAnvilRecipes(ItemStack itemstack, Enchantment enchantment, int enchantment_lvl, IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
    	List<Object> recipes = new ArrayList<>();
    	Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM);
    	
    	for (ItemStack ingredient : ingredients) {
			if (ingredient.isEnchantable()) {
				if (enchantment.canEnchant(ingredient)) {
					List<ItemStack> perLevelBooks = Lists.newArrayList();
					List<ItemStack> perLevelOutputs = Lists.newArrayList();
					
					perLevelBooks.add(itemstack);
					
					ItemStack withEnchant = ingredient.copy();
					withEnchant.enchant(enchantment, enchantment_lvl);
					perLevelOutputs.add(withEnchant);
					
					Object anvilRecipe = vanillaRecipeFactory.createAnvilRecipe(ingredient, perLevelBooks, perLevelOutputs);
					recipes.add(anvilRecipe);
				}
			}
    	}
					
		return recipes;    	
    }

}
