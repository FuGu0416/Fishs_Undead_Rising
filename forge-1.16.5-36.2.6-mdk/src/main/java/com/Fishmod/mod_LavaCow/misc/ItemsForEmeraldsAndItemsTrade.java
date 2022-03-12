package com.Fishmod.mod_LavaCow.misc;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

public class ItemsForEmeraldsAndItemsTrade implements VillagerTrades.ITrade {
    private final ItemStack fromItem;
    private final int fromCount;
    private final int emeraldCost;
    private final ItemStack toItem;
    private final int toCount;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50533_1_, int p_i50533_2_, Item p_i50533_3_, int p_i50533_4_, int p_i50533_5_, int p_i50533_6_) {
       this(p_i50533_1_, p_i50533_2_, 1, p_i50533_3_, p_i50533_4_, p_i50533_5_, p_i50533_6_);
    }

    public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50534_1_, int p_i50534_2_, int p_i50534_3_, Item p_i50534_4_, int p_i50534_5_, int p_i50534_6_, int p_i50534_7_) {
       this.fromItem = new ItemStack(p_i50534_1_);
       this.fromCount = p_i50534_2_;
       this.emeraldCost = p_i50534_3_;
       this.toItem = new ItemStack(p_i50534_4_);
       this.toCount = p_i50534_5_;
       this.maxUses = p_i50534_6_;
       this.villagerXp = p_i50534_7_;
       this.priceMultiplier = 0.05F;
    }

    @Nullable
    public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
       return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}
