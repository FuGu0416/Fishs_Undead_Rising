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
    public static ResourceLocation ISNACHI = null;
    public static ResourceLocation IMP = null;
    public static ResourceLocation ZOMBIEFROZEN = null;
    public static ResourceLocation UNDEADSWINE = null;
    public static ResourceLocation SALAMANDER = null;
    public static ResourceLocation SALAMANDER_BLUE = null;
    public static ResourceLocation SALAMANDERLESSER = null;
    public static ResourceLocation SALAMANDERLESSER_BLUE = null;
    public static ResourceLocation WENDIGO = null;
    public static ResourceLocation MIMIC = null;
    public static ResourceLocation SLUDGELORD = null;
    public static ResourceLocation RAVEN = null;
    public static ResourceLocation SEAGULL = null;
    public static ResourceLocation PTERA = null;
    public static ResourceLocation PTERA1 = null;
    public static ResourceLocation PTERA2 = null;
    public static ResourceLocation PTERA3 = null;
    public static ResourceLocation VESPA = null;
    public static ResourceLocation SCARECROW = null;
    public static ResourceLocation SCARECROW1 = null;
    public static ResourceLocation SCARECROW2 = null;
    public static ResourceLocation ZOMBIEPIRANHA = null;
    public static ResourceLocation PIRANHA = null;
    public static ResourceLocation BONEWORM = null;
    public static ResourceLocation SOULWORM = null;
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
    public static ResourceLocation CACTOID = null;
    public static ResourceLocation CACTYRANT = null;
    public static ResourceLocation CACTYRANT_NETHER = null;
    public static ResourceLocation SEA_HAG = null;
    public static ResourceLocation GRAVE_ROBBER = null;
    public static ResourceLocation GRAVE_ROBBER_GHOST = null;
    public static ResourceLocation WRAITH = null;
    public static ResourceLocation GHOST_SWARMER = null;
    public static ResourceLocation AMBER_SCARAB = null;
    public static ResourceLocation AMBER_LORD = null;
    public static ResourceLocation ENIGMOTH = null;
    public static ResourceLocation ENIGMOTH_LARVA = null;
    public static ResourceLocation UNBURIED = null;
    public static ResourceLocation UNDERTAKER = null;
    public static ResourceLocation LIL_SLUDGE = null;
    public static ResourceLocation TRADE_LOOT = null;
    public static Map<Item, Integer> FISHABLE = new HashMap<Item, Integer>();
    public static Map<ItemStack, Float> LOOT_INTESTINE = new HashMap<ItemStack, Float>();
    public static Map<ItemStack, Float> LOOT_RAVEN = new HashMap<ItemStack, Float>();
    public static Map<ItemStack, Float> LOOT_SEAGULL = new HashMap<ItemStack, Float>();
    public static Map<ItemStack, Float> LOOT_SPECTRAL_RAVEN = new HashMap<ItemStack, Float>();
    public static List<Biome.SpawnListEntry> DREAMCATCHER_LIST = Lists.<Biome.SpawnListEntry>newArrayList();
    public static List<ResourceLocation> FISSION_WHITELIST = Lists.<ResourceLocation>newArrayList();
    public static List<ResourceLocation> PARASITE_HOSTLIST = Lists.<ResourceLocation>newArrayList();
    public static List<Biome.SpawnListEntry> PTERA_PASSENGERLIST = Lists.<Biome.SpawnListEntry>newArrayList();

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
    public static void addLootTable() {
        LAVACOW = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/moogma"));
        ZOMBIEMUSHROOM = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/mycosis"));
        PARASITE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/parasite"));
        PARASITE1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/parasite_desert"));
        PARASITE2 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/parasite_jungle"));
        FOGLET = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/foglet"));
        ISNACHI = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/isnachi"));
        IMP = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/imp"));
        ZOMBIEFROZEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/frigid"));
        UNDEADSWINE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/undead_swine"));
        SALAMANDER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/salamander"));
        SALAMANDER_BLUE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/salamander_blue"));
        SALAMANDERLESSER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/salamander_nymph"));
        SALAMANDERLESSER_BLUE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/salamander_nymph_blue"));
        WENDIGO = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/wendigo"));
        MIMIC = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/mimic"));
        SLUDGELORD = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/sludge_lord"));
        LIL_SLUDGE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/lil_sludge"));
        RAVEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/raven"));
        SEAGULL = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/seagull"));
        PTERA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ptera_jungle"));
        PTERA1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ptera_desert"));
        PTERA2 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ptera_savanna"));
        PTERA3 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ptera_swamp"));
        VESPA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/vespa"));
        SCARECROW = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/scarecrow"));
        SCARECROW1 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/scarecrow_straw"));
        SCARECROW2 = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/scarecrow_plague"));
        ZOMBIEPIRANHA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/swarmer"));
        PIRANHA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/piranha"));
        BONEWORM = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/boneworm"));
        SOULWORM = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/soulworm"));
        PINGU = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/pingu"));
        GHOSTRAY = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ghost_ray"));
        BANSHEE = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/banshee"));
        WETA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/weta"));
        AVATON = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/avaton"));
        SKELETON_KING = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/bosses/skeleton_king"));
        MUMMY = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/mummy"));
        FORSAKEN = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/forsaken"));
        CACTOID = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/cactoid"));
        CACTYRANT = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/cactyrant"));
        CACTYRANT_NETHER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/cactyrant_nether"));
        SEA_HAG = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/sea_hag"));
        GRAVE_ROBBER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/grave_robber"));
        GRAVE_ROBBER_GHOST = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/grave_robber_ghost"));
        WRAITH = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/wraith"));
        GHOST_SWARMER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/ghost_swarmer"));
        AMBER_LORD = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/amber_lord"));
        AMBER_SCARAB = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/amber_scarab"));
        ENIGMOTH = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/enigmoth"));
        ENIGMOTH_LARVA = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/enigmoth_larva"));
        UNBURIED = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/unburied"));
        UNDERTAKER = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "entities/undertaker"));

        CEMETERY_CHEST = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "chests/cemetery_chest"));
        DESERT_TOMB_CHEST = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "chests/desert_tomb_chest"));
        TRADE_LOOT = LootTableList.register(new ResourceLocation(mod_LavaCow.MODID, "gameplay/graverobber_bartering"));
        
        FISHABLE.put(Modblocks.item_block_glowshroom, 10);
        FISHABLE.put(FishItems.HYPHAE, 10);

        for (String S : Modconfig.Intestine_lt) {
            String[] S_splt = S.split(",");
            Item item = Item.getByNameOrId(S_splt[0]);
            if (item != null) {
                if (S_splt.length > 2)
                    LOOT_INTESTINE.put(new ItemStack(item, 1, Integer.parseInt(S_splt[2])), Float.parseFloat(S_splt[1]));
                else
                    LOOT_INTESTINE.put(new ItemStack(item, 1, 0), Float.parseFloat(S_splt[1]));
            }
        }

        LootTableHandler.LOOT_RAVEN = LootTableHandler.parseLootTable(Modconfig.Raven_Loot);
        LootTableHandler.LOOT_SEAGULL = LootTableHandler.parseLootTable(Modconfig.Seagull_Loot);
        LootTableHandler.LOOT_SPECTRAL_RAVEN = LootTableHandler.parseLootTable(Modconfig.Spectral_Raven_Loot);

        for (String S : Modconfig.Dreamcatcher_spawn) {
            String[] S_splt = S.split(",");
            Class<? extends Entity> entityClass = EntityList.getClass(new ResourceLocation(S_splt[0]));

            if (entityClass != null && S_splt.length == 4 && Integer.parseInt(S_splt[1]) > 0 && Integer.parseInt(S_splt[2]) > 0 && Integer.parseInt(S_splt[3]) > 0) {
                DREAMCATCHER_LIST.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) entityClass, Integer.parseInt(S_splt[1]), Integer.parseInt(S_splt[2]), Integer.parseInt(S_splt[3])));
            }
        }

        for (String S : Modconfig.Fission_Allowlist) {
            FISSION_WHITELIST.add(new ResourceLocation(S));
        }

        for (String S : Modconfig.Parasite_Hostlist) {
            PARASITE_HOSTLIST.add(new ResourceLocation(S));
        }

        for (String S : Modconfig.Ptera_Ability_Spawn) {
            String[] S_splt = S.split(",");
            Class<? extends Entity> entityClass = EntityList.getClass(new ResourceLocation(S_splt[0]));

            if (entityClass != null && S_splt.length == 2 && Integer.parseInt(S_splt[1]) > 0) {
                PTERA_PASSENGERLIST.add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) entityClass, Integer.parseInt(S_splt[1]), 1, 1));
            }
        }
    }

    public static void dropRareLoot(Entity entityIn, Item itemIn, int chance, @Nullable Enchantment enchantmentIn, int enchantlevel, int looting) {
        if (!entityIn.world.isRemote && new Random().nextInt(100) < chance + (2 * looting)) {
            ItemStack item = new ItemStack(itemIn, 1);
            if (Modconfig.Enchantment_Enable && enchantmentIn != null)
                ItemEnchantedBook.addEnchantment(item, new EnchantmentData(enchantmentIn, enchantlevel));
            entityIn.entityDropItem(item, 0.0F);
        }
    }

    public static void dropRareLoot(Entity entityIn, ItemStack itemstackIn, int chance, int looting) {
        if (!entityIn.world.isRemote && new Random().nextInt(100) < chance + (2 * looting)) {
            entityIn.entityDropItem(itemstackIn, 0.0F);
        }
    }
}
