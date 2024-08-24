package com.Fishmod.mod_LavaCow.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.FishBrewingRecipe;
import com.Fishmod.mod_LavaCow.util.TradeHandler;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class AddRecipes {	
	public static final BannerPattern PATTERN_SKELETONKING = addBannerPattern("skeletonking", new ItemStack(FishItems.EMBLEM_OF_KING));
	
	public static void addRecipies(){
	       addSmelting();
	       if(Modconfig.Potion_Enable)
	    	   addBrewing();
	       addTrading();
	       addOreDictionary();
	    }

	    public static void addSmelting(){
	    //add smelting recipes
	    	List<Triple<Item, Item, Integer>> Smelt_Recipe = new ArrayList<>();
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.PARASITE_ITEM, FishItems.PARASITE_ITEM_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.PIRANHA, FishItems.PIRANHA_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.CHEIROLEPIS, FishItems.CHEIROLEPIS_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.MOUSSE, FishItems.MEATBALL, 4));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.FROZENTHIGH, Items.ROTTEN_FLESH, 4));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.MIMIC_CLAW, FishItems.MIMIC_CLAW_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.PTERA_WING, FishItems.PTERA_WING_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.ZOMBIEPIRANHA_ITEM, FishItems.ZOMBIEPIRANHA_ITEM_COOKED, 1));
	    	Smelt_Recipe.add(new ImmutableTriple<Item, Item, Integer>(FishItems.ENIGMOTH_LARVA_ITEM, FishItems.ENIGMOTH_LARVA_ITEM_COOKED, 1));
	    	
    		for (final Triple<Item, Item, Integer> item : Smelt_Recipe) {
    			GameRegistry.addSmelting(item.getLeft(), new ItemStack(item.getMiddle(), item.getRight()), 0.35f);
    		}
    		
			GameRegistry.addSmelting(new ItemStack(FishItems.ECTOPLASM_MASS), new ItemStack(FishItems.ECTOPLASM_INGOT), 0.7f);
	    }
	    
	    public static void addBrewing() {
	    	// Special Potion Recipes
	        BrewingRecipeRegistry.addRecipe(new FishBrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.PHEROMONE_GLAND), new ItemStack(FishItems.CHARMING_CATALYST)));
	        BrewingRecipeRegistry.addRecipe(new FishBrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_REGENERATION), new ItemStack(FishItems.HYPHAE), new ItemStack(FishItems.FISSIONPOTION)));
	        BrewingRecipeRegistry.addRecipe(new FishBrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.HOLY_SLUDGE), new ItemStack(FishItems.HOLY_WATER)));
	        BrewingRecipeRegistry.addRecipe(new ItemStack(FishItems.FISSIONPOTION), new ItemStack(FishItems.MOOTENHEART), new ItemStack(FishItems.POTION_OF_MOOTEN_LAVA));
	        
	        // Normal Potion Recipes
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.PTERA_WING), PotionTypes.SWIFTNESS);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.ACIDICHEART), ModPotions.CORROSIVE);
			PotionHelper.addMix(ModPotions.CORROSIVE, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_CORROSIVE);
			PotionHelper.addMix(ModPotions.CORROSIVE, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_CORROSIVE);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.FOUL_BRISTLE), ModPotions.FOULODOR);
			PotionHelper.addMix(ModPotions.FOULODOR, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_FOULODOR);
			PotionHelper.addMix(ModPotions.FOULODOR, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_FOULODOR);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.POISONSPORE), PotionTypes.LONG_POISON);
			
			PotionHelper.addMix(PotionTypes.SLOWNESS, Ingredient.fromItem(FishItems.PARASITE_ITEM), ModPotions.INFESTATION);
			PotionHelper.addMix(PotionTypes.LONG_SLOWNESS, Ingredient.fromItem(FishItems.PARASITE_ITEM), ModPotions.LONG_INFESTATION);
			PotionHelper.addMix(ModPotions.INFESTATION, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_INFESTATION);
			PotionHelper.addMix(ModPotions.INFESTATION, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_INFESTATION);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.HATRED_SHARD), ModPotions.FRAGILE);
			PotionHelper.addMix(ModPotions.FRAGILE, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_FRAGILE);
			PotionHelper.addMix(ModPotions.FRAGILE, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_FRAGILE);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.CACTUS_FRUIT), ModPotions.THORN);
			PotionHelper.addMix(ModPotions.THORN, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_THORN);
			PotionHelper.addMix(ModPotions.THORN, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_THORN);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.IMP_HORN), ModPotions.IMMOLATION);
			PotionHelper.addMix(ModPotions.IMMOLATION, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_IMMOLATION);
			PotionHelper.addMix(ModPotions.IMMOLATION, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_IMMOLATION);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.ANCIENT_AMBER), ModPotions.FLOURISHED);
			PotionHelper.addMix(ModPotions.FLOURISHED, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_FLOURISHED);
			PotionHelper.addMix(ModPotions.FLOURISHED, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_FLOURISHED);
			
			PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromItem(FishItems.ENIGMOTH_LARVA_ITEM), ModPotions.VOID_DUST);
			PotionHelper.addMix(ModPotions.VOID_DUST, Ingredient.fromItem(Items.REDSTONE), ModPotions.LONG_VOID_DUST);
			PotionHelper.addMix(ModPotions.VOID_DUST, Ingredient.fromItem(Items.GLOWSTONE_DUST), ModPotions.STRONG_VOID_DUST);
	    }
	    
	    public static void addTrading() {
	    	//add trading recipes
		    /** "minecraft:farmer" "minecraft:librarian" "minecraft:priest" "minecraft:smith" "minecraft:butcher" "minecraft:nitwit"*/
	    	VillagerRegistry.VillagerProfession cleric=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:priest"));
	    	VillagerRegistry.VillagerProfession farmer=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer"));
	    	VillagerRegistry.VillagerProfession butcher=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:butcher"));
	    	cleric.getCareer(1).addTrade(1,new TradeHandler.TradeClericLv1());
	    	cleric.getCareer(1).addTrade(3,new TradeHandler.TradeClericLv3());
	    	cleric.getCareer(1).addTrade(4,new TradeHandler.TradeClericLv4());
	    	//farmer.getCareer(1).addTrade(1,new TradeHandler.TradeFishermanLv1());
	    	farmer.getCareer(1).addTrade(2,new TradeHandler.TradeFishermanLv2());
	    	butcher.getCareer(0).addTrade(2,new TradeHandler.TradeButcherLv2());
		}
	    
	    public static void addOreDictionary() {
	    	OreDictionary.registerOre("slimeball", FishItems.SILKY_SLUDGE);
	    	OreDictionary.registerOre("stickWood", FishItems.MOSSY_STICK);
	    	OreDictionary.registerOre("feather", FishItems.FEATHER_BLACK);
	    	OreDictionary.registerOre("listAllfishraw", FishItems.ZOMBIEPIRANHA_ITEM);
	    	OreDictionary.registerOre("listAllfishcooked", FishItems.ZOMBIEPIRANHA_ITEM_COOKED);
	    	OreDictionary.registerOre("listAllfishraw", FishItems.PIRANHA);
	    	OreDictionary.registerOre("listAllfishcooked", FishItems.PIRANHA_COOKED);
	    	OreDictionary.registerOre("listAllfishraw", FishItems.CHEIROLEPIS);
	    	OreDictionary.registerOre("listAllfishcooked", FishItems.CHEIROLEPIS_COOKED);
	    	OreDictionary.registerOre("listAllmushroom", Modblocks.item_block_glowshroom);
	    	OreDictionary.registerOre("listAllmushroom", Modblocks.item_block_bloodtooth_shroom);
	    	OreDictionary.registerOre("listAllmushroom", Modblocks.item_block_cordy_shroom);
	    	OreDictionary.registerOre("listAllmushroom", Modblocks.item_block_veil_shroom);
	    	OreDictionary.registerOre("toolSkillet", FishItems.MOLTENPAN);
	    	OreDictionary.registerOre("foodHotwings", FishItems.PTERA_WING_COOKED);
	    	OreDictionary.registerOre("foodBaconandeggs", FishItems.GREEN_BACON_AND_EGGS);
	    	OreDictionary.registerOre("foodGreeneggsandham", FishItems.GREEN_BACON_AND_EGGS);
	    	OreDictionary.registerOre("listAllfishraw", FishItems.ZOMBIEPIRANHA_ITEM);
	    	OreDictionary.registerOre("foodCrabraw", FishItems.MIMIC_CLAW);
	    	OreDictionary.registerOre("foodCrabcooked", FishItems.MIMIC_CLAW_COOKED);
	    	OreDictionary.registerOre("listAllmeatraw", new ItemStack(FishItems.PTERA_WING, 1, 0));
	    	OreDictionary.registerOre("listAllmeatraw", new ItemStack(FishItems.PTERA_WING, 1, 1));
	    	OreDictionary.registerOre("listAllmeatraw", new ItemStack(FishItems.PTERA_WING, 1, 2));
	    	OreDictionary.registerOre("listAllmeatraw", new ItemStack(FishItems.PTERA_WING, 1, 3));
	    	OreDictionary.registerOre("listAllmeatcooked", FishItems.PTERA_WING_COOKED);
	    	OreDictionary.registerOre("listAllmeatcooked", FishItems.MOLTENBEEF);
	    	OreDictionary.registerOre("listAllmeatraw", FishItems.PLAGUED_PORKCHOP);
	    	OreDictionary.registerOre("foodSwedishmeatballs", FishItems.MEATBALL);   
	    	OreDictionary.registerOre("paper", FishItems.CURSEWEAVE_CLOTH);
			OreDictionary.registerOre("ectoplasm", FishItems.ECTOPLASM);
			OreDictionary.registerOre("heartUndying", FishItems.UNDYINGHEART);
			OreDictionary.registerOre("heartUndying", FishItems.ACIDICHEART);
			OreDictionary.registerOre("gemAmber", FishItems.ANCIENT_AMBER);
	    }
	    
	    public static BannerPattern addBannerPattern(String name, ItemStack ItemStackIn) {
	        Class<?>[] classes = {String.class, String.class, ItemStack.class};
	        Object[] names = {name, "mod_lavacow." + name, ItemStackIn};
	        return EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), classes, names);
	    }
}
