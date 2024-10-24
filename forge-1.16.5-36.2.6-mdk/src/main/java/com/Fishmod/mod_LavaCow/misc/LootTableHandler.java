package com.Fishmod.mod_LavaCow.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREnchantmentRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.google.common.collect.Lists;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.registries.ForgeRegistries;

public class LootTableHandler {
	public static final ResourceLocation CEMETERY_CHEST = new ResourceLocation(mod_LavaCow.MODID, "chests/cemetery_chest");
	public static final ResourceLocation SKELETON_KING = new ResourceLocation(mod_LavaCow.MODID, "entities/skeletonking");
	public static final ResourceLocation DESERT_TOMB_CHEST = new ResourceLocation(mod_LavaCow.MODID, "chests/desert_tomb_chest");
	public static final ResourceLocation TRADE_LOOT = new ResourceLocation(mod_LavaCow.MODID, "gameplay/graverobber_bartering");
	
	public static Map<Item, Integer> FISHABLE = new HashMap<Item, Integer>();
	public static Map<ItemStack, Float> LOOT_INTESTINE = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_RAVEN = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_SEAGULL = new HashMap<ItemStack, Float>();
	public static Map<ItemStack, Float> LOOT_SPECTRAL_RAVEN = new HashMap<ItemStack, Float>();
	public static List<MobSpawnInfo.Spawners> DREAMCATCHER_LIST = Lists.<MobSpawnInfo.Spawners>newArrayList();
	public static List<MobSpawnInfo.Spawners> PTERA_LIST = Lists.<MobSpawnInfo.Spawners>newArrayList();
	public static List<Tuple<Enchantment, Tuple<ItemStack, Integer>>> ANVIL_RECIPE = new ArrayList<>();
	
	public static Map<ItemStack, Float> parseLootTable(List<? extends String> list) {
		Map<ItemStack, Float> lootTable = new HashMap<ItemStack, Float>();

		for (String line : list) {
			String[] lineSplit = line.split(",");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(lineSplit[0]));

			if (item != null) {
				int amount = 1;

				if (lineSplit.length > 2) {
					amount = Integer.parseInt(lineSplit[2]);
				}

				lootTable.put(new ItemStack(item, amount), Float.parseFloat(lineSplit[1]));
			}
		}

		return lootTable;
	}

	public static void addLootTable() {				
		LootTableHandler.LOOT_INTESTINE = LootTableHandler.parseLootTable(FURConfig.Intestine_lt.get());
		LootTableHandler.LOOT_RAVEN = LootTableHandler.parseLootTable(FURConfig.Raven_Loot.get());
		LootTableHandler.LOOT_SEAGULL = LootTableHandler.parseLootTable(FURConfig.Seagull_Loot.get());
		LootTableHandler.LOOT_SPECTRAL_RAVEN = LootTableHandler.parseLootTable(FURConfig.Spectral_Raven_Loot.get());
		ItemStack whetstone[] = {new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE), new ItemStack(FURItemRegistry.SINISTER_WHETSTONE)};
		CompoundNBT compoundnbt[] = {new CompoundNBT(), new CompoundNBT(), new CompoundNBT(), new CompoundNBT(), new CompoundNBT()};
		
		for(String S : FURConfig.DreamCatcher_spawn.get()) {
			String[] S_splt = S.split(",");
			EntityType<?> entityClass = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(S_splt[0]));
			
			if(entityClass != null && S_splt.length == 4 && Integer.parseInt(S_splt[1]) > 0 && Integer.parseInt(S_splt[2]) > 0 && Integer.parseInt(S_splt[3]) > 0) {
				DREAMCATCHER_LIST.add(new MobSpawnInfo.Spawners(entityClass, Integer.parseInt(S_splt[1]), Integer.parseInt(S_splt[2]), Integer.parseInt(S_splt[3])));
			}
		}		
		
		for(String S : FURConfig.Ptera_Ability_Spawn.get()) {
			String[] S_splt = S.split(",");
			EntityType<?> entityClass = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(S_splt[0]));
			
			if(entityClass != null && S_splt.length == 2 && Integer.parseInt(S_splt[1]) > 0) {
				PTERA_LIST.add(new MobSpawnInfo.Spawners(entityClass, Integer.parseInt(S_splt[1]), 1, 1));
			}
		}
				
		ANVIL_RECIPE.add(new Tuple<>(Enchantments.FISHING_SPEED, new Tuple<>(new ItemStack(FURItemRegistry.PARASITE_COMMON), 1)));
		ANVIL_RECIPE.add(new Tuple<>(Enchantments.FISHING_SPEED, new Tuple<>(new ItemStack(FURItemRegistry.PARASITE_DESERT), 1)));
		ANVIL_RECIPE.add(new Tuple<>(Enchantments.FISHING_SPEED, new Tuple<>(new ItemStack(FURItemRegistry.PARASITE_MAGGOT), 1)));
		ANVIL_RECIPE.add(new Tuple<>(Enchantments.FISHING_SPEED, new Tuple<>(new ItemStack(FURBlockRegistry.GLOWSHROOM.asItem()), 3)));
		ANVIL_RECIPE.add(new Tuple<>(FUREnchantmentRegistry.POISONOUS, new Tuple<>(new ItemStack(FURItemRegistry.POISONSPORE), 3)));
		ANVIL_RECIPE.add(new Tuple<>(FUREnchantmentRegistry.LIFESTEAL, new Tuple<>(new ItemStack(FURItemRegistry.UNDYINGHEART), 3)));
		ANVIL_RECIPE.add(new Tuple<>(FUREnchantmentRegistry.CORROSIVE, new Tuple<>(new ItemStack(FURItemRegistry.ACIDICHEART), 1)));
		ANVIL_RECIPE.add(new Tuple<>(Enchantments.SMITE, new Tuple<>(new ItemStack(FURItemRegistry.HOLY_WATER), 3)));
		
		for (int i = 0; i < 5; i++) {
			compoundnbt[i].putInt("level", i + 1);
			whetstone[i].setTag(compoundnbt[i]);
			
			ANVIL_RECIPE.add(new Tuple<>(FUREnchantmentRegistry.CRITICALBOOST, new Tuple<>(whetstone[i], i + 1)));		
		}
	}
	
	public static void dropRareLoot(Entity entityIn, Item itemIn, int chance,@Nullable Enchantment enchantmentIn, int enchantlevel, int looting) {
		if (!entityIn.level.isClientSide() && new Random().nextInt(100) < chance + (2 * looting)) {			
			ItemStack item = new ItemStack(itemIn, 1);
			if (FURConfig.Enchantment_Enable.get() && enchantmentIn != null)
				EnchantedBookItem.addEnchantment(item, new EnchantmentData(enchantmentIn, enchantlevel));
			entityIn.spawnAtLocation(item, 0.0F);
    	}
	}
	
	public static void dropRareLoot(Entity entityIn, ItemStack itemstackIn, int chance, int looting) {
		if (!entityIn.level.isClientSide() && new Random().nextInt(100) < chance + (2 * looting)) {			
			entityIn.spawnAtLocation(itemstackIn, 0.0F);
    	}
	}
}
