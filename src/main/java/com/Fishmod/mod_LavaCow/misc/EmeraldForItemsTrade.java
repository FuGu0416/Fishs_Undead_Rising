package com.Fishmod.mod_LavaCow.misc;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

public class EmeraldForItemsTrade implements VillagerTrades.ITrade {
    private final Item item;
    private final int cost;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public EmeraldForItemsTrade(IItemProvider itemIn, int costIn, int maxUsesIn, int villagerXpIn) {
       this.item = itemIn.asItem();
       this.cost = costIn;
       this.maxUses = maxUsesIn;
       this.villagerXp = villagerXpIn;
       this.priceMultiplier = 0.05F;
    }

    public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
       ItemStack itemstack = new ItemStack(this.item, this.cost);
       return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, this.priceMultiplier);
    }
 }
