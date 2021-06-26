package com.Fishmod.mod_LavaCow.util;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class TradeHandler{
	
	public static class TradeClericLv1 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.33F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SHARPTOOTH,26 + random.nextInt(6)),new ItemStack(Items.EMERALD , 1)));
			if(random.nextFloat() < 0.15F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.ECTOPLASM,15 + random.nextInt(4)),new ItemStack(Items.EMERALD , 1 + random.nextInt(1))));
			if(random.nextFloat() < 0.15F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.CURSED_FABRIC,18 + random.nextInt(6)),new ItemStack(Items.EMERALD , 1 + random.nextInt(1))));
		}
	}
	
	public static class TradeClericLv3 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.33F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SCYTHE_CLAW, 1 + random.nextInt(2)),new ItemStack(Items.EMERALD , 1)));
			if(random.nextFloat() < 0.33F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SILKY_SLUDGE, 3 + random.nextInt(5)),new ItemStack(Items.EMERALD , 1)));
			if(random.nextFloat() < 0.33F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.FOUL_BRISTLE, 4 + random.nextInt(4)),new ItemStack(Items.EMERALD , 1)));
			if(random.nextFloat() < 0.25F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.UNDYINGHEART, 1 + random.nextInt(1)),new ItemStack(Items.EMERALD , 1)));	
			if(random.nextFloat() < 0.25F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.ACIDICHEART, 1 + random.nextInt(1)),new ItemStack(Items.EMERALD , 1)));
			if(random.nextFloat() < 0.15F)
				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD , 2 + random.nextInt(3)),new ItemStack(FishItems.SCYTHE_CLAW, 1 + random.nextInt(3))));
			if(random.nextFloat() < 0.15F)
				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD , 2 + random.nextInt(3)),new ItemStack(FishItems.SILKY_SLUDGE, 3 + random.nextInt(5))));
			if(random.nextFloat() < 0.15F)
				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD , 2 + random.nextInt(3)),new ItemStack(FishItems.FOUL_BRISTLE, 4 + random.nextInt(4))));
		}
	}
	
	public static class TradeClericLv4 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.4F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.EMBLEM_OF_KING, 1 + random.nextInt(2)),new ItemStack(Items.EMERALD , 4 + random.nextInt(3))));
			if(random.nextFloat() < 0.4F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.HATRED_SHARD, 1 + random.nextInt(1)),new ItemStack(Items.EMERALD , 2 + random.nextInt(3))));		
		}
	}
	
	public static class TradeFishermanLv1 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.5F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.PIRANHA,6), new ItemStack(Items.EMERALD ,1), new ItemStack(FishItems.PIRANHA_COOKED,6)));
			if(random.nextFloat() < 0.35F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.ZOMBIEPIRANHA_ITEM,6), new ItemStack(Items.EMERALD ,1), new ItemStack(FishItems.ZOMBIEPIRANHA_ITEM_COOKED,6)));
			//recipeList.add(new MerchantRecipe(new ItemStack(FishItems.CHEIROLEPIS,6), new ItemStack(Items.EMERALD ,1), new ItemStack(FishItems.CHEIROLEPIS_COOKED,6)));
		}
	}
	
	public static class TradeFishermanLv2 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.33F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.MIMIC_CLAW,6), new ItemStack(Items.EMERALD ,1), new ItemStack(FishItems.MIMIC_CLAW_COOKED,6)));
		}
	}
	
	public static class TradeButcherLv2 implements ITradeList{
		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			if(random.nextFloat() < 0.2F)
				recipeList.add(new MerchantRecipe(new ItemStack(FishItems.INTESTINE, 4 + random.nextInt(3)), new ItemStack(Items.EMERALD ,1)));	
			if(random.nextFloat() < 0.1F)
				recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD , 1 + random.nextInt(3)), new ItemStack(FishItems.PLAGUED_PORKCHOP, 1 + random.nextInt(2))));
		}
	}

}

