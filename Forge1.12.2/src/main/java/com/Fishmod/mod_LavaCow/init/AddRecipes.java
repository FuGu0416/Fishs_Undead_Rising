package com.Fishmod.mod_LavaCow.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.util.TradeHandler;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class AddRecipes {
	
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
	    	
    		for (final Triple<Item, Item, Integer> item : Smelt_Recipe) {
    			GameRegistry.addSmelting(item.getLeft(), new ItemStack(item.getMiddle(), item.getRight()), 0.35f);
    		}
	    }
	    
	    public static void addBrewing() {
	    //add brewing recipes
	      	/** We can make custom brewing recipes now!!! */
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.HYPHAE), new ItemStack(FishItems.FISSIONPOTION));
	        BrewingRecipeRegistry.addRecipe(new ItemStack(FishItems.FISSIONPOTION), new ItemStack(FishItems.MOOTENHEART), new ItemStack(FishItems.POTION_OF_MOOTEN_LAVA));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.PTERA_WING), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LEAPING));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.ACIDICHEART), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), ModPotions.CORROSIVE));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.ACIDICHEART), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.CORROSIVE));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.ACIDICHEART), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.CORROSIVE));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.FOUL_BRISTLE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), ModPotions.FOULODOR));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.FOUL_BRISTLE), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.FOULODOR));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.FOUL_BRISTLE), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.FOULODOR));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.POISONSPORE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LONG_POISON));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.POISONSPORE), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.LONG_POISON));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.POISONSPORE), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.LONG_POISON));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(FishItems.PARASITE_ITEM), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), ModPotions.INFESTATION));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.PARASITE_ITEM), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.INFESTATION));
	        BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.AWKWARD), new ItemStack(FishItems.PARASITE_ITEM), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.INFESTATION));
	    }
	    
	    public static void addTrading() {
	    	//add trading recipes
		    /** "minecraft:farmer" "minecraft:librarian" "minecraft:priest" "minecraft:smith" "minecraft:butcher" "minecraft:nitwit"*/
	    	VillagerRegistry.VillagerProfession cleric=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:priest"));
	    	VillagerRegistry.VillagerProfession farmer=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer"));
	    	VillagerRegistry.VillagerProfession butcher=ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:butcher"));
	    	cleric.getCareer(1).addTrade(1,new TradeHandler.TradeClericLv1());
	    	cleric.getCareer(1).addTrade(3,new TradeHandler.TradeClericLv3());
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
	    	OreDictionary.registerOre("listAllmeatraw", FishItems.PTERA_WING);
	    	OreDictionary.registerOre("listAllmeatcooked", FishItems.PTERA_WING_COOKED);
	    	OreDictionary.registerOre("listAllmeatcooked", FishItems.MOLTENBEEF);
	    	OreDictionary.registerOre("listAllmeatraw", FishItems.PLAGUED_PORKCHOP);
	    	OreDictionary.registerOre("foodSwedishmeatballs", FishItems.MEATBALL);   	
	    }

}
