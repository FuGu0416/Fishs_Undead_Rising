package com.Fishmod.mod_LavaCow.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.google.common.collect.Lists;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableHandler {
	public static ResourceLocation LAVACOW = null;
	public static ResourceLocation ZOMBIEMUSHROOM = null;
	public static ResourceLocation PARASITE = null;
	public static ResourceLocation PARASITE1 = null;
	public static ResourceLocation PARASITE2 = null;
	public static ResourceLocation FOGLET = null;
	public static ResourceLocation ZOMBIEFROZEN = null;
	public static ResourceLocation UNDEADSWINE = null;
	public static ResourceLocation SALAMANDER = null;
	public static ResourceLocation SALAMANDERLESSER = null;
	public static ResourceLocation WENDIGO = null;
	public static ResourceLocation MIMIC = null;
	public static ResourceLocation SLUDGELORD = null;
	public static ResourceLocation RAVEN = null;
	public static ResourceLocation SEAGULL = null;
	public static ResourceLocation PTERA = null;
	public static ResourceLocation PTERA1 = null;
	public static ResourceLocation VESPA = null;
	public static ResourceLocation SCARECROW = null;
	public static ResourceLocation SCARECROW1 = null;
	public static ResourceLocation SCARECROW2 = null;
	public static ResourceLocation ZOMBIEPIRANHA = null;
	public static ResourceLocation PIRANHA = null;
	public static ResourceLocation BONEWORM = null;
	public static ResourceLocation PINGU = null;
	public static ResourceLocation GHOSTRAY = null;
	public static ResourceLocation CEMETERY_CHEST = null;
	public static ResourceLocation BANSHEE = null;
	public static ResourceLocation WETA = null;
	public static ResourceLocation AVATON = null;
	public static ResourceLocation SKELETON_KING = null;
	public static ResourceLocation DESERT_TOMB_CHEST = null;
	public static ResourceLocation MUMMY = null;
	public static ResourceLocation FORSAKEN = null;	
	public static Map<Item, Integer> FISHABLE = new HashMap<Item, Integer>();
	public static Map<ItemStack, Float> LOOT_INTESTINE = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_RAVEN = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_SEAGULL = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_SPECTRAL_RAVEN = new HashMap<ItemStack, Float>();
	public static List<Biome.SpawnListEntry> DREAMCATCHER_LIST = Lists.<Biome.SpawnListEntry>newArrayList();
	public static List<ResourceLocation> PARASITE_HOSTLIST = Lists.<ResourceLocation>newArrayList();

	public static Map<ItemStack, Float> parseLootTable(String[] lootTableConfiguration) {
		Map<ItemStack, Float> lootTable = new HashMap<ItemStack, Float>();

		for (String line : lootTableConfiguration) {
			String[] lineSplit = line.split(",");
			String[] nameSplit = lineSplit[0].split("@");
			Item item = Item.getByNameOrId(nameSplit[0]);

			if (item != null) {
				int amount = 1;
				int meta = 0;

				if (lineSplit.length > 2) {
					amount = Integer.parseInt(lineSplit[2]);
				}

				if (nameSplit.length > 1) {
					meta = Integer.parseInt(nameSplit[1]);
				}

				lootTable.put(new ItemStack(item, amount, meta), Float.parseFloat(lineSplit[1]));
			}
		}

		return lootTable;
	}

	@SuppressWarnings("unchecked")
	public static void addLootTable()
	{
		LAVACOW = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "lavacow"));
		ZOMBIEMUSHROOM = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "zombiemushroom"));
		PARASITE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "parasite"));
		PARASITE1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "parasite1"));
		PARASITE2 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "parasite2"));
		FOGLET = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "foglet"));
		ZOMBIEFROZEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "zombiefrozen"));
		UNDEADSWINE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "undeadswine"));
		SALAMANDER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "salamander"));
		SALAMANDERLESSER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "salamanderlesser"));
		WENDIGO = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "wendigo"));
		MIMIC = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "mimic"));
		SLUDGELORD = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "sludgelord"));
		RAVEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "raven"));
		SEAGULL = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "seagull"));
		PTERA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "ptera"));
		PTERA1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "ptera1"));
		VESPA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "vespa"));
		SCARECROW = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "scarecrow"));
		SCARECROW1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "scarecrow1"));
		SCARECROW2 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "scarecrow2"));
		ZOMBIEPIRANHA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "zombiepiranha"));
		PIRANHA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "piranha"));
		BONEWORM = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "boneworm"));
		PINGU = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "pingu"));
		GHOSTRAY = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "ghostray"));
		CEMETERY_CHEST = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "chests/cemetery_chest"));
		BANSHEE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "banshee"));
		WETA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "weta"));
		AVATON = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "avaton"));
		SKELETON_KING = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "skeleton_king"));
		DESERT_TOMB_CHEST = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "chests/desert_tomb_chest"));
		MUMMY = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "mummy"));
		FORSAKEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "forsaken"));
		
		FISHABLE.put(Modblocks.item_block_glowshroom, 10);
		FISHABLE.put(FishItems.HYPHAE, 10);
		
		for(String S : Modconfig.Intestine_lt) {
			String[] S_splt = S.split(",");
			Item item = Item.getByNameOrId(S_splt[0]);
			if(item != null) {
				if(S_splt.length > 2)
					LOOT_INTESTINE.put(new ItemStack(item, 1, Integer.parseInt(S_splt[2])), Float.parseFloat(S_splt[1]));
				else
					LOOT_INTESTINE.put(new ItemStack(item, 1, 0), Float.parseFloat(S_splt[1]));
			}
		}

		LootTableHandler.LOOT_RAVEN = LootTableHandler.parseLootTable(Modconfig.Raven_Loot);
		LootTableHandler.LOOT_SEAGULL = LootTableHandler.parseLootTable(Modconfig.Seagull_Loot);
		LootTableHandler.LOOT_SPECTRAL_RAVEN = LootTableHandler.parseLootTable(Modconfig.Spectral_Raven_Loot);

		for(String S : Modconfig.DreamCatcher_spawn) {
			String[] S_splt = S.split(",");
			Class<? extends Entity> entityClass = EntityList.getClass(new ResourceLocation(S_splt[0]));
			
			if(entityClass != null && S_splt.length == 4 && Integer.parseInt(S_splt[1]) > 0 &&Integer.parseInt(S_splt[2]) > 0 &&Integer.parseInt(S_splt[3]) > 0) {
				DREAMCATCHER_LIST.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) entityClass, Integer.parseInt(S_splt[1]), Integer.parseInt(S_splt[2]), Integer.parseInt(S_splt[3])));
			}
		}
		
		for(String S : Modconfig.Parasite_Hostlist) {
			PARASITE_HOSTLIST.add(new ResourceLocation(S));
		}
	}
	
	public static void dropRareLoot(Entity entityIn, Item itemIn, int chance,@Nullable Enchantment enchantmentIn, int enchantlevel, int looting) {
		if (!entityIn.world.isRemote && new Random().nextInt(100) < chance + (2 * looting))
    	{			
			ItemStack item = new ItemStack(itemIn, 1);
			if(Modconfig.Enchantment_Enable && enchantmentIn != null)
				ItemEnchantedBook.addEnchantment(item, new EnchantmentData(enchantmentIn, enchantlevel));
			entityIn.entityDropItem(item, 0.0F);
    	}
	}
	
	public static void dropRareLoot(Entity entityIn, ItemStack itemstackIn, int chance, int looting) {
		if (!entityIn.world.isRemote && new Random().nextInt(100) < chance + (2 * looting))
    	{			
			entityIn.entityDropItem(itemstackIn, 0.0F);
    	}
	}
}
