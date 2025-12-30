package com.Fishmod.mod_LavaCow.util;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class TradeHandler {
    public static class TradeArmorerLv4 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 9 + random.nextInt(6)), new ItemStack(FishItems.ECTOPLASM_INGOT, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 20 + random.nextInt(11)), new ItemStack(FishItems.MOLTEN_ALLOY, 1)));
        }
    }

    public static class TradeButcherLv2 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.PLAGUED_PORKCHOP, 4 + random.nextInt(3)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 4 + random.nextInt(3)), new ItemStack(FishItems.INTESTINE, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 44 + random.nextInt(7)), new ItemStack(FishItems.MOLTENPAN, 1)));
        }
    }

    public static class TradeClericLv1 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.ECTOPLASM, 4 + random.nextInt(3)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.CURSED_FABRIC, 8 + random.nextInt(5)), new ItemStack(Items.EMERALD, 1)));
        }
    }

    public static class TradeClericLv3 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SCYTHE_CLAW, 2 + random.nextInt(3)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SILKY_SLUDGE, 3 + random.nextInt(6)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.FOUL_BRISTLE, 4 + random.nextInt(5)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.UNDYINGHEART, 1), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.ACIDICHEART, 1), new ItemStack(Items.EMERALD, 1)));
        }
    }

    public static class TradeClericLv4 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.EMBLEM_OF_KING, 1 + random.nextInt(3)), new ItemStack(Items.EMERALD, 4 + random.nextInt(5))));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.HATRED_SHARD, 2 + random.nextInt(3)), new ItemStack(Items.EMERALD, 2 + random.nextInt(2))));
        }
    }

    public static class TradeFarmerLv2 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.DISEASED_BREAD, 4), new ItemStack(Items.EMERALD, 1), new ItemStack(Items.BREAD, 4)));
            recipeList.add(new MerchantRecipe(new ItemStack(Modblocks.item_block_glowshroom, 2 + random.nextInt(2)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Modblocks.item_block_veil_shroom, 2 + random.nextInt(2)), new ItemStack(Items.EMERALD, 1)));
        }
    }

    public static class TradeFarmerLv3 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Modblocks.item_block_bloodtooth_shroom, 1 + random.nextInt(2)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Modblocks.item_block_cordy_shroom, 1 + random.nextInt(2)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 2 + random.nextInt(2)), new ItemStack(FishItems.CACTUS_FRUIT, 1 + random.nextInt(2))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(5)), new ItemStack(FishItems.WETA_HOE)));
        }
    }

    public static class TradeFishermanLv1 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.MOUSSE, 12 + random.nextInt(5)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.PIRANHA, 6), new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.PIRANHA_COOKED, 6)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.LAMPREY, 6), new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.LAMPREY_COOKED, 6)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.ZOMBIEPIRANHA_ITEM, 4 + random.nextInt(3))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.CHEIROLEPIS_COOKED, 4 + random.nextInt(3))));
        }
    }

    public static class TradeFletcherLv1 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.SHARPTOOTH, 15 + random.nextInt(6)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.GHOUL_CLAW, 3 + random.nextInt(2)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.CACTUS_THORN, 8 + random.nextInt(5))));
        }
    }

    public static class TradeFletcherLv2 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.FEATHER_BLACK, 5 + random.nextInt(6))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.FANG_ARROW, 4 + random.nextInt(3))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(FishItems.GHOUL_ARROW, 4 + random.nextInt(3))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 8 + random.nextInt(5)), new ItemStack(FishItems.THORN_SHOOTER)));
        }
    }

    public static class LeatherworkerLv1 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.PIGBOARHIDE, 3 + random.nextInt(4)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.CHITIN, 9 + random.nextInt(4)), new ItemStack(Items.EMERALD, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(FishItems.VESPA_CARAPACE, 3 + random.nextInt(4)), new ItemStack(Items.EMERALD, 1)));
        }
    }

    public static class LeatherworkerLv2 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(5)), new ItemStack(FishItems.CHITIN_BOOTS, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 7 + random.nextInt(6)), new ItemStack(FishItems.CHITIN_LEGGINGS, 1)));
        }
    }

    public static class LeatherworkerLv3 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 8 + random.nextInt(7)), new ItemStack(FishItems.CHITIN_CHESTPLATE, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6 + random.nextInt(5)), new ItemStack(FishItems.CHITIN_HELMET, 1)));
        }
    }

    public static class TradeToolSmithLv3 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 9 + random.nextInt(6)), new ItemStack(FishItems.ECTOPLASM_INGOT, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 20 + random.nextInt(11)), new ItemStack(FishItems.MOLTEN_ALLOY, 1)));
        }
    }

    public static class TradeWeaponSmithLv3 implements ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 9 + random.nextInt(6)), new ItemStack(FishItems.ECTOPLASM_INGOT, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 20 + random.nextInt(11)), new ItemStack(FishItems.MOLTEN_ALLOY, 1)));
        }
    }
}

