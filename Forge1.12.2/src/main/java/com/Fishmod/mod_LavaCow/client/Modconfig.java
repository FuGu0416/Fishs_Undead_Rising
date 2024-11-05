package com.Fishmod.mod_LavaCow.client;

import java.io.File;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Modconfig {

    public static final Modconfig INSTANCE = new Modconfig();
    @SuppressWarnings("unused")
    private File configFolder;

    public static Configuration config = null;

    public static double Lavacow_Health;
    public static boolean Lavacow_Bucket;
    public static double Lavacow_Classic_Texture_Chance;

    public static int pSpawnRate_Foglet;
    public static double Foglet_Health;
    public static double Foglet_Attack;

    public static int pSpawnRate_Isnachi;
    public static double Isnachi_Health;
    public static double Isnachi_Attack;

    public static int pSpawnRate_Imp;
    public static double Imp_Health;
    public static double Imp_Attack;

    public static int pSpawnRate_Parasite;
    public static double Parasite_Health;
    public static double Parasite_Attack;
    public static boolean Parasite_Hosts;
    public static String[] Parasite_Hostlist = new String[0];
    public static int Parasite_Lifespan;
    public static boolean Parasite_Pickup;

    public static int pSpawnRate_UndeadSwine;
    public static double UndeadSwine_Health;
    public static double UndeadSwine_Attack;

    public static int pSpawnRate_ZombieMushroom;
    public static double ZombieMushroom_Health;
    public static double ZombieMushroom_Attack;

    public static int pSpawnRate_ZombieFrozen;
    public static double ZombieFrozen_Health;
    public static double ZombieFrozen_Attack;

    public static int pSpawnRate_Salamander;
    public static double Salamander_Health;
    public static double Salamander_Attack;
    public static boolean Salamander_Defender;

    public static int pSpawnRate_Wendigo;
    public static double Wendigo_Health;
    public static double Wendigo_Attack;

    public static int pSpawnRate_Mimic;
    public static double Mimic_Health;
    public static double Mimic_Attack;
    public static double pSpawnRate_DeathMimic;

    public static int pSpawnRate_SludgeLord;
    public static double SludgeLord_Health;
    public static double SludgeLord_Attack;
    public static int SludgeLord_Ability_Num;
    public static int SludgeLord_Ability_Max;
    public static int SludgeLord_Ability_Cooldown;

    public static int pSpawnRate_AmberLord;
    public static double AmberLord_Health;
    public static double AmberLord_Attack;

    public static int pSpawnRate_Raven;
    public static double Raven_Health;
    public static boolean Raven_Perch;
    public static boolean Raven_Slowfall;

    public static int pSpawnRate_Ptera;
    public static double Ptera_Health;
    public static double Ptera_Attack;
    public static double Ptera_Ability_Chance;
    public static int Ptera_FlyingHeight_limit;
    public static String[] Ptera_Ability_Spawn = new String[0];

    public static int pSpawnRate_Vespa;
    public static int pEvolveRate_Vespa;
    public static double Vespa_Health;
    public static double Vespa_Attack;
    public static int Vespa_FlyingHeight_limit;
    public static boolean Vespa_Spread_Parasites;
    public static double Vespa_Hornet_Variant;

    public static int pSpawnRate_Scarecrow;
    public static double Scarecrow_Health;
    public static double Scarecrow_Attack;
    public static boolean Scarecrow_Old_Sounds;

    public static int pSpawnRate_ZombiePiranha;
    public static double ZombiePiranha_Health;
    public static double ZombiePiranha_Attack;

    public static int pSpawnRate_Piranha;
    public static double Piranha_Health;
    public static double Piranha_Attack;

    public static int pSpawnRate_BoneWorm;
    public static double BoneWorm_Health;
    public static double BoneWorm_Attack;

    public static int pSpawnRate_SoulWorm;
    public static double SoulWorm_Health;
    public static double SoulWorm_Attack;

    public static int pSpawnRate_Pingu;
    public static double Pingu_Health;
    public static double Pingu_Attack;
    public static boolean Pingu_Extra_Ice;

    public static int pSpawnRate_Undertaker;
    public static double Undertaker_Health;
    public static double Undertaker_Attack;
    public static int Undertaker_Ability_Num;
    public static int Undertaker_Ability_Max;
    public static int Undertaker_Ability_Cooldown;

    public static int pSpawnRate_GhostRay;
    public static int pSpawnRate_GhostRay_End;
    public static double GhostRay_Health;
    public static int GhostRay_FlyingHeight_limit;
    public static boolean GhostRay_Middle_End_Island;
    public static boolean GhostRay_Ghostly_Touch;
    public static boolean GhostRay_End_Variants;

    public static int pSpawnRate_Banshee;
    public static double Banshee_Health;
    public static double Banshee_Attack;
    public static double Banshee_Ability_Radius;

    public static int pSpawnRate_Weta;
    public static double Weta_Health;
    public static double Weta_Attack;
    public static int Weta_Lifespan;
    public static double Weta_Harvest_Diseased_Wheat;

    public static int pSpawnRate_Avaton;
    public static double Avaton_Health;
    public static double Avaton_Attack;
    public static int Avaton_Ability_Num;
    public static int Avaton_Ability_Max;
    public static int Avaton_Ability_Cooldown;

    public static double LilSludge_Health;
    public static double LilSludge_Attack;
    public static int LilSludge_Lifespan;

    public static int pSpawnRate_Unburied;
    public static double Unburied_Health;
    public static double Unburied_Attack;
    public static int Unburied_Lifespan;

    public static int pSpawnRate_Forsaken;
    public static double Forsaken_Health;
    public static double Forsaken_Attack;

    public static boolean pSpawnRate_SkeletonKing;
    public static double SkeletonKing_Health;
    public static double SkeletonKing_Attack;
    public static int SkeletonKing_Ability_Sand_Tomb_Cooldown;
    public static int SkeletonKing_Ability_Sand_Wraith_Cooldown;
    public static int SkeletonKing_Ability_Projectile_Cooldown;
    public static int SkeletonKing_Ability_Summon_Cooldown;
    public static int SkeletonKing_Ability_Summon_Num;
    public static int SkeletonKing_Ability_Summon_Max;
    public static int SkeletonKing_Minion_Lifespan;
    public static boolean SkeletonKing_Loot_Option;

    public static int pSpawnRate_Mummy;
    public static double Mummy_Health;
    public static double Mummy_Attack;

    public static int pSpawnRate_Cactyrant;
    public static double Cactyrant_Health;
    public static double Cactyrant_Attack;
    public static int Cactyrant_Ability_Cooldown;

    public static int pSpawnRate_Cactoid;
    public static double Cactoid_Health;
    public static double Cactoid_Attack;

    public static int pSpawnRate_Sea_Hag;
    public static double Sea_Hag_Health;
    public static double Sea_Hag_Attack;
    public static int Sea_Hag_Ability_Num;
    public static int Sea_Hag_Ability_Max;
    public static int Sea_Hag_Ability_Cooldown;

    public static int pSpawnRate_Grave_Robber;
    public static double Grave_Robber_Ghost_Chance;
    public static double Grave_Robber_Health;
    public static double Grave_Robber_Attack;
    public static int Grave_Robber_Ghost_Ability_Num;
    public static int Grave_Robber_Ghost_Ability_Max;
    public static int Grave_Robber_Ghost_Ability_Cooldown;
    public static int Grave_Robber_Ghost_Minion_Lifespan;

    public static int pSpawnRate_Grave_Robber_Ghost;
    public static double Grave_Robber_Ghost_Health;
    public static double Grave_Robber_Ghost_Attack;

    public static int pSpawnRate_Wraith;
    public static double Wraith_Health;
    public static double Wraith_Attack;
    public static double Wraith_Ability_Radius;

    public static int pSpawnRate_Ghost_Swarmer;
    public static double Ghost_Swarmer_Health;
    public static double Ghost_Swarmer_Attack;
    public static int Ghost_Swarmer_Lifespan;

    public static double Amber_Scarab_Health;
    public static double Amber_Scarab_Attack;
    public static int Amber_Scarab_Lifespan;

    public static int pSpawnRate_Enigmoth;
    public static double Enigmoth_Health;
    public static double Enigmoth_Attack;
    public static int Enigmoth_Ability_Cooldown;
    public static int Enigmoth_Ability_Cooldown_Mount;
    public static int Enigmoth_FlyingHeight_limit;
    public static boolean Enigmoth_Middle_End_Island;

    public static int pSpawnRate_Enigmoth_Larva;
    public static double Enigmoth_Larva_Health;
    public static double Enigmoth_Larva_Attack;
    public static int Enigmoth_Larva_Ability_Cooldown;
    public static boolean Enigmoth_Larva_Middle_End_Island;
    public static boolean Enigmoth_Larva_Pickup;

    public static boolean pFoglet_SpawnAlly;
    public static boolean MoltenHammer_PVP;
    public static int Cocoon_Lifespan;
    public static int Parasite_SandSpawn;
    public static boolean Parasite_Plague;
    public static boolean Wendigo_AnimalAttack;
    public static boolean Fission_ModEntity;
    public static String[] Fission_Allowlist = new String[0];
    public static boolean Parasite_Attach;
    public static int ZombieMushroom_DropSpore;
    public static int General_Intestine;
    public static boolean GoldenHeart_BookEnchantability;
    public static int GoldenHeart_dur;
    public static int GoldenHeart_dur_drop;
    public static String[] GoldenHeart_bl = new String[0];
    public static boolean GoldenHeart_GrantsRegeneration;
    public static int GoldenHeart_Regeneration_Amount;
    public static int GoldenHeart_Repair_Amount;
    public static boolean GoldenHeart_RepairsEquipment;
    public static int pSpawnRate_Glowshroom;
    public static int pSpreadRate_Glowshroom;
    public static boolean Piranha_AnimalAttack;
    public static int BoneSword_Damage;
    public static boolean BoneSword_Boss_Damage;
    public static int HaloNecklace_Damage;
    public static String[] Intestine_lt = new String[0];
    public static String[] Intestine_banlist = new String[0];
    public static String[] Raven_Loot = new String[0];
    public static String[] Seagull_Loot = new String[0];
    public static String[] Spectral_Raven_Loot = new String[0];
    public static int pScarecrow_PlagueDoctor;
    public static int SludgeWand_Cooldown;
    public static int ScarabWand_Cooldown;
    public static int Undertaker_Shovel_Cooldown;
    public static boolean Tinkers_Compat;
    public static boolean Tinkers_Armor_Compat;
    public static boolean Quark_Compat;
    public static boolean SunScreen_Mode;
    public static int SpawnRate_Cemetery;
    public static int BoneSword_DamageCap;
    public static int[] Spawn_AllowList = new int[0];
    public static boolean Suicidal_Minion;
    public static boolean Dreamcatcher_BookEnchantability;
    public static int Dreamcatcher_dur;
    public static int Dreamcatcher_dur_drop;
    public static String[] Dreamcatcher_spawn = new String[0];
    public static boolean Potion_Enable;
    public static boolean Enchantment_Enable;
    public static boolean Enchantment_Anvil_Enable;
    public static boolean Soulforged_Anvil_Recipes;
    public static int MootenHeart_Damage;
    public static int SoulforgedHeart_Healing;
    public static int[] Spawn_Cemetery_AllowList = new int[0];
    public static int Cemetery_SpawnRate;
    public static int SpawnRate_Desert_Tomb;
    public static boolean Generate_Cemetery;
    public static boolean Generate_Desert_Tomb;
    public static boolean Bowls_Stack;
    public static boolean Should_Villager_Fear;
    public static int pSpawnRate_Bloodtooth;
    public static int pSpawnRate_Cordyceps;
    public static int pSpawnRate_Veilshroom;
    public static boolean MonsterSpawner_Mobs;

    public final String[] usedCategories = {Configuration.CATEGORY_GENERAL, "Amber Lord", "Amber Scarab", "Avaton", "Banshee", "Cactoid", "Cactyrant", "Enigmoth", "Enigmoth Caterpillar", "Foglet", "Slothoman",
            "Imp", "Forsaken", "Frigid", "Ghost Ray", "Ghost Swarmer", "Ithaqua", "Lil' Sludge", "Mimicrab", "Moogma", "Mummy", "Mycosis", "Osvermis", "Parasite", "Penghoul", "Piranha", "Ptera", "Raven", "Warmander",
            "Scarecrow", "Skeleton King", "Sludge Lord", "Swarmer", "Unburied", "Undead Swine", "Undertaker", "Vespa", "Weta", "Sea Hag", "Grave Robber", "Ghost of Grave Robber", "Revenant", "Shroom"};

    public void loadConfig(FMLPreInitializationEvent event) {
        File configFile = new File(Loader.instance().getConfigDir(), "Fishs_Undead_Rising.cfg");
        configFolder = configFile.getParentFile();
        config = new Configuration(configFile);

        config.load();
        syncConfigs();
    }

    private void syncConfigs() {

        pSpawnRate_Glowshroom = config.get("Shroom", "glowshroom spawn rate", 20, "Set the spawn rate of Glowshroom [0-100]", 0, 100).getInt(20);
        pSpreadRate_Glowshroom = config.get("Shroom", "glowshroom spread rate", 100, "Set the spread rate of Glowshroom [0-100]", 0, 100).getInt(100);
        pSpawnRate_Bloodtooth = config.get("Shroom", "bloodtooth spawn rate", 20, "Set the spawn rate of Bloodtooth [0-100]", 0, 100).getInt(20);
        pSpawnRate_Cordyceps = config.get("Shroom", "cordyceps spawn rate", 20, "Set the spawn rate of Cordyceps [0-100]", 0, 100).getInt(20);
        pSpawnRate_Veilshroom = config.get("Shroom", "veilshroom spawn rate", 20, "Set the spawn rate of Veil Shroom [0-100]", 0, 100).getInt(20);

        Lavacow_Health = config.get("Moogma", "moogma health", 10.0D, "Maximum Moogma health [1-1000]", 1, 1000).getDouble(10.0D);
        Lavacow_Bucket = config.get("Moogma", "moogma lava source", true, "Should lava buckets be obtainable from Moogma [false/true]").getBoolean(true);
        Lavacow_Classic_Texture_Chance = config.get("Moogma", "moogma classic texture chance", 1.0D, "Chance for Moogmas to spawn with the classic texture (setting it to 1.0 will always cause it to spawn) [0-1]", 0, 1).getDouble(1.0D);

        pSpawnRate_Foglet = config.get("Foglet", "foglet spawn rate", 20, "Set the spawn rate of Foglet [0-10000]", 0, 10000).getInt(20);
        Foglet_Health = config.get("Foglet", "foglet health", 16.0D, "Maximum Foglet health [1-1000]", 1, 1000).getDouble(16.0D);
        Foglet_Attack = config.get("Foglet", "foglet attack", 2.0D, "Foglet strength [1-1000]", 1, 1000).getDouble(2.0D);

        pSpawnRate_Isnachi = config.get("Isnachi", "isnachi spawn rate", 20, "Set the spawn rate of Isnachi [0-10000]", 0, 10000).getInt(20);
        Isnachi_Health = config.get("Isnachi", "isnachi health", 20.0D, "Maximum Isnachi health [1-1000]", 1, 1000).getDouble(20.0D);
        Isnachi_Attack = config.get("Isnachi", "isnachi attack", 3.0D, "Isnachi strength [1-1000]", 1, 1000).getDouble(3.0D);

        pSpawnRate_Imp = config.get("Imp", "imp spawn rate", 3, "Set the spawn rate of Imp [0-10000]", 0, 10000).getInt(3);
        Imp_Health = config.get("Imp", "imp health", 25.0D, "Maximum Imp health [1-1000]", 1, 1000).getDouble(25.0D);
        Imp_Attack = config.get("Imp", "imp attack", 4.0D, "Imp strength [1-1000]", 1, 1000).getDouble(4.0D);

        pSpawnRate_Parasite = config.get("Parasite", "parasite spawn rate", 10, "Set the spawn rate of Parasite [0-100]", 0, 100).getInt(10);
        Parasite_Health = config.get("Parasite", "parasite health", 6.0D, "Maximum Parasite health [1-1000]", 1, 1000).getDouble(6.0D);
        Parasite_Attack = config.get("Parasite", "parasite attack", 1.0D, "Parasite strength [1-1000]", 1, 1000).getDouble(1.0D);
        Parasite_SandSpawn = config.get("Parasite", "parasite from sand blocks", 2, "Rate of spawning Parasite when destroying sand blocks in the desert [0-100]", 0, 100).getInt(2);
        Parasite_Plague = config.get("Parasite", "parasite attacks everything", false, "Should Parasite attack ALL livings [false/true]").getBoolean(false);
        Parasite_Attach = config.get("Parasite", "parasite attacks by attaching onto target", true, "Parasite will attack their target by attaching on them [false/true]").getBoolean(true);
        Parasite_Lifespan = config.get("Parasite", "parasite lifespan", 16, "The amount of seconds before Parasites naturally die or form into cocoons").getInt(16);
        Parasite_Pickup = config.get("Parasite", "parasite pickup", true, "You can pick up Parasites by right clicking them with an empty main hand while sneaking [false/true]").getBoolean(true);
        Parasite_Hosts = config.get("Parasite", "parasite hosts", true, "Should Parasites have a chance to pop out from dying mobs in the host list or mobs under the Infested effect [false/true]").getBoolean(true);
        Parasite_Hostlist = config.getStringList("Available hosts for parasite", "Parasite",
                new String[]{
                        "minecraft:zombie",
                        "minecraft:husk",
                        "mod_lavacow:zombiefrozen",
                        "mod_lavacow:zombiemushroom",
                        "mod_lavacow:unburied",
                        "mod_lavacow:mummy"
                },
                "Allow Parasite to spawn from listed mob. Ex. \"minecraft:zombie\" or \"mod_lavacow:zombiefrozen\"");

        pSpawnRate_UndeadSwine = config.get("Undead Swine", "undeadswine spawn rate", 15, "Set the spawn rate of Undead swine [0-10000]", 0, 10000).getInt(15);
        UndeadSwine_Health = config.get("Undead Swine", "undeadswine health", 50.0D, "Maximum Undead Swine health [1-1000]", 1, 1000).getDouble(50.0D);
        UndeadSwine_Attack = config.get("Undead Swine", "undeadswine attack", 4.0D, "Undead Swine strength [1-1000]", 1, 1000).getDouble(4.0D);

        pSpawnRate_ZombieMushroom = config.get("Mycosis", "mycosis spawn rate", 40, "Set the spawn rate of Mycosis [0-10000]", 0, 10000).getInt(40);
        ZombieMushroom_Health = config.get("Mycosis", "mycosis health", 20.0D, "Maximum Mycosis health [1-1000]", 1, 1000).getDouble(20.0D);
        ZombieMushroom_Attack = config.get("Mycosis", "mycosis attack", 3.0D, "Mycosis strength [1-1000]", 1, 1000).getDouble(3.0D);
        ZombieMushroom_DropSpore = config.get(Configuration.CATEGORY_GENERAL, "drop poisonous spore", 2, "Set the drop rate of Poisonous Spore [0-100]", 0, 100).getInt(2);

        pSpawnRate_ZombieFrozen = config.get("Frigid", "frigid spawn rate", 20, "Set the spawn rate of Frigid [0-10000]", 0, 10000).getInt(20);
        ZombieFrozen_Health = config.get("Frigid", "frigid health", 20.0D, "Maximum Frigid health [1-1000]", 1, 1000).getDouble(30.0D);
        ZombieFrozen_Attack = config.get("Frigid", "frigid attack", 3.0D, "Frigid strength [1-1000]", 1, 1000).getDouble(3.0D);

        pSpawnRate_Salamander = config.get("Warmander", "warmander spawn rate", 30, "Set the spawn rate of Warmander [0-10000]", 0, 10000).getInt(30);
        Salamander_Health = config.get("Warmander", "warmander health", 60.0D, "Maximum Warmander health [1-1000]", 1, 1000).getDouble(60.0D);
        Salamander_Attack = config.get("Warmander", "warmander attack", 4.0D, "Warmander strength [1-1000]", 1, 1000).getDouble(4.0D);
        Salamander_Defender = config.get("Warmander", "warmander defender", false, "Should tamed Warmander defend its owner [false/true]").getBoolean(false);

        pSpawnRate_Wendigo = config.get("Ithaqua", "ithaqua spawn rate", 15, "Set the spawn rate of Ithaqua [0-10000]", 0, 10000).getInt(15);
        Wendigo_AnimalAttack = config.get("Ithaqua", "ithaqua attacks animals", true, "Should Ithaqua attack innocent animals [false/true]").getBoolean(true);
        Wendigo_Health = config.get("Ithaqua", "ithaqua health", 60.0D, "Maximum Ithaqua health [1-1000]", 1, 1000).getDouble(60.0D);
        Wendigo_Attack = config.get("Ithaqua", "ithaqua attack", 8.0D, "Ithaqua strength [1-1000]", 1, 1000).getDouble(8.0D);

        pSpawnRate_Mimic = config.get("Mimicrab", "mimicrab spawn rate", 20, "Set the spawn rate of Mimicrab [0-10000]", 0, 10000).getInt(20);
        Mimic_Health = config.get("Mimicrab", "mimicrab health", 10.0D, "Maximum Mimicrab health [1-1000]", 1, 1000).getDouble(10.0D);
        Mimic_Attack = config.get("Mimicrab", "mimicrab attack", 8.0D, "Mimicrab strength [1-1000]", 1, 1000).getDouble(8.0D);
        pSpawnRate_DeathMimic = config.get("Mimicrab", "mimicrab spawn rate near player death", 0.0D, "Set the spawn rate of Mimicrab near player death [0-1]", 0, 1).getDouble(0.0D);

        pSpawnRate_SludgeLord = config.get("Sludge Lord", "sludge lord spawn rate", 15, "Set the spawn rate of Sludge Lord [0-10000]", 0, 10000).getInt(15);
        SludgeLord_Health = config.get("Sludge Lord", "sludge lord health", 70.0D, "Maximum Sludge Lord health [1-1000]", 1, 1000).getDouble(70.0D);
        SludgeLord_Attack = config.get("Sludge Lord", "sludge lord attack", 5.0D, "Sludge Lord strength [1-1000]", 1, 1000).getDouble(5.0D);
        SludgeLord_Ability_Num = config.get("Sludge Lord", "sludge lord summon number", 3, "Set the number of Lil' Sludges summoned per cast [0-100]", 0, 100).getInt(3);
        SludgeLord_Ability_Max = config.get("Sludge Lord", "sludge lord summon max", 8, "Set the max number of Lil' Sludges summoned [0-100]", 0, 100).getInt(8);
        SludgeLord_Ability_Cooldown = config.get("Sludge Lord", "sludge lord summon cooldown", 17, "Set the cooldown of summoning Lil' Sludges [0-100]", 0, 100).getInt(17);

        pSpawnRate_AmberLord = config.get("Amber Lord", "amber lord spawn rate", 10, "Set the spawn rate of Amber Lord [0-10000]", 0, 10000).getInt(10);
        AmberLord_Health = config.get("Amber Lord", "amber lord health", 70.0D, "Maximum Amber Lord health [1-1000]", 1, 1000).getDouble(70.0D);
        AmberLord_Attack = config.get("Amber Lord", "amber lord attack", 5.0D, "Amber Lord strength [1-1000]", 1, 1000).getDouble(5.0D);

        LilSludge_Health = config.get("Lil' Sludge", "lil'sludge health", 16.0D, "Maximum Lil' Sludge health [1-1000]", 1, 1000).getDouble(16.0D);
        LilSludge_Attack = config.get("Lil' Sludge", "lil'sludge attack", 3.5D, "Lil' Sludge strength [1-1000]", 1, 1000).getDouble(3.5D);
        LilSludge_Lifespan = config.get("Lil' Sludge", "lil'sludge lifespan", 60, "Summoned Lil' Sludge lifespan [1-10000]", 1, 10000).getInt(60);

        pSpawnRate_Raven = config.get("Raven", "raven spawn rate", 8, "Set the spawn rate of Raven [0-10000]", 0, 10000).getInt(8);
        Raven_Health = config.get("Raven", "raven health", 6.0D, "Maximum Raven health [1-1000]", 1, 1000).getDouble(6.0D);
        Raven_Perch = config.get("Raven", "raven perching", true, "Should tamed Raven perch on owner's head [false/true]").getBoolean(true);
        Raven_Slowfall = config.get("Raven", "raven slow down falling", true, "Should perching Raven slow down owner's falling speed [false/true]").getBoolean(true);

        pSpawnRate_Ptera = config.get("Ptera", "ptera spawn rate", 20, "Set the spawn rate of Ptera [0-10000]", 0, 10000).getInt(20);
        Ptera_Health = config.get("Ptera", "ptera health", 16.0D, "Maximum Ptera health [1-1000]", 1, 1000).getDouble(16.0D);
        Ptera_Attack = config.get("Ptera", "ptera attack", 3.0D, "Ptera strength [1-1000]", 1, 1000).getDouble(3.0D);
        Ptera_Ability_Chance = config.get("Ptera", "ptera carries passenger chance", 0.1D, "Chance of Ptera to carry a passenger when spawned [0-1]", 0, 1).getDouble(0.1D);
        Ptera_FlyingHeight_limit = config.get("Ptera", "ptera height limit", 16, "Set the height limit to X blocks above the ground for Pteras, 0 = Infinite [0-100]", 0, 100).getInt(16);
        Ptera_Ability_Spawn = config.getStringList("passenger list for ptera", "Ptera",
                new String[]{
                        "mod_lavacow:slothoman,40",
                        "mod_lavacow:mummy,40",
                        "minecraft:husk,40",
                        "minecraft:creeper,20",
                        "minecraft:vindication_illager,10",
                        "minecraft:evoker,1"
                },
                "Customize the passenger list for the Ptera. Do note that some mobs especially smaller ones might cause the Ptera to behave strangely! Ex. \"mod_lavacow:foglet,40\" or \"minecraft:spider,20\"");

        pSpawnRate_Vespa = config.get("Vespa", "vespa spawn rate", 20, "Set the spawn rate of Vespa [0-100]", 0, 100).getInt(20);
        pEvolveRate_Vespa = config.get("Vespa", "vespa evolve rate", 20, "Set the chance of Vespa transformed from a Parasite [0-100]", 0, 100).getInt(20);
        Vespa_Health = config.get("Vespa", "vespa health", 50.0D, "Maximum Vespa health [1-1000]", 1, 1000).getDouble(50.0D);
        Vespa_Attack = config.get("Vespa", "vespa attack", 5.0D, "Vespa strength [1-1000]", 1, 1000).getDouble(5.0D);
        Vespa_FlyingHeight_limit = config.get("Vespa", "vespa height limit", 16, "Set the height limit to X blocks above the ground for Vespas, 0 = Infinite [0-100]", 0, 100).getInt(16);
        Vespa_Spread_Parasites = config.get("Vespa", "vespa spread parasites", true, "Should Vespas spread parasites by sometimes inflicting Infested when hitting a target [false/true]").getBoolean(true);
        Vespa_Hornet_Variant = config.get("Vespa", "vespa hornet variant chance", 0.1D, "Set the chance for naturally spawned Vespa to become a hornet variant (setting it to 1.0 will always cause it to spawn) [0-1]", 0, 1).getDouble(0.1D);

        pSpawnRate_Scarecrow = config.get("Scarecrow", "scarecrow spawn rate", 15, "Set the spawn rate of Scarecrow [0-100]", 0, 100).getInt(15);
        Scarecrow_Health = config.get("Scarecrow", "scarecrow health", 40.0D, "Maximum Scarecrow health [1-1000]", 1, 1000).getDouble(40.0D);
        Scarecrow_Attack = config.get("Scarecrow", "scarecrow attack", 8.0D, "Scarecrow strength [1-1000]", 1, 1000).getDouble(8.0D);
        Scarecrow_Old_Sounds = config.get("Scarecrow", "scarecrow old sounds", false, "Should Scarecrows play sounds from old versions of Fish's Undead Rising [false/true]").getBoolean(false);
        pScarecrow_PlagueDoctor = config.get("Scarecrow", "plague doctor scarecrow spawn rate", 30, "Set the spawn rate of Plague Doctor Scarecrow when a Raven was killed. [0-100]", 0, 100).getInt(30);

        pSpawnRate_ZombiePiranha = config.get("Swarmer", "swarmer spawn rate", 40, "Set the spawn rate of Swarmer [0-100]", 0, 100).getInt(40);
        ZombiePiranha_Health = config.get("Swarmer", "swarmer health", 8.0D, "Maximum Swarmer health [1-1000]", 1, 1000).getDouble(8.0D);
        ZombiePiranha_Attack = config.get("Swarmer", "swarmer attack", 3.5D, "Swarmer strength [1-1000]", 1, 1000).getDouble(3.5D);
        Piranha_AnimalAttack = config.get(Configuration.CATEGORY_GENERAL, "piranha attacks animals", true, "Should Piranha and Swarmer attack innocent animals [false/true]").getBoolean(true);

        pSpawnRate_Piranha = config.get("Piranha", "piranha spawn rate", 80, "Set the spawn rate of Piranha [0-100]", 0, 100).getInt(80);
        Piranha_Health = config.get("Piranha", "piranha health", 5.0D, "Maximum Piranha health [1-1000]", 1, 1000).getDouble(5.0D);
        Piranha_Attack = config.get("Piranha", "piranha attack", 3.5D, "Piranha strength [1-1000]", 1, 1000).getDouble(3.5D);

        pSpawnRate_BoneWorm = config.get("Osvermis", "boneworm spawn rate", 20, "Set the spawn rate of Osvermis [0-100]", 0, 100).getInt(20);
        BoneWorm_Health = config.get("Osvermis", "boneworm health", 40.0D, "Maximum Osvermis health [1-1000]", 1, 1000).getDouble(40.0D);
        BoneWorm_Attack = config.get("Osvermis", "boneworm attack", 6.0D, "Osvermis strength [1-1000]", 1, 1000).getDouble(6.0D);

        pSpawnRate_SoulWorm = config.get("Osvermis Spiritus", "soulworm spawn rate", 3, "Set the spawn rate of Osvermis Spiritus [0-100]", 0, 100).getInt(3);
        SoulWorm_Health = config.get("Osvermis Spiritus", "soulworm health", 50.0D, "Maximum Osvermis Spiritus health [1-1000]", 1, 1000).getDouble(50.0D);
        SoulWorm_Attack = config.get("Osvermis Spiritus", "soulworm attack", 6.0D, "Osvermis Spiritus strength [1-1000]", 1, 1000).getDouble(6.0D);

        pSpawnRate_Pingu = config.get("Penghoul", "penghoul spawn rate", 20, "Set the spawn rate of Penghoul [0-100]", 0, 100).getInt(20);
        Pingu_Health = config.get("Penghoul", "penghoul health", 10.0D, "Maximum Penghoul health [1-1000]", 1, 1000).getDouble(10.0D);
        Pingu_Attack = config.get("Penghoul", "penghoul attack", 3.0D, "Penghoul strength [1-1000]", 1, 1000).getDouble(3.0D);
        Pingu_Extra_Ice = config.get("Penghoul", "penghoul extra ice", false, "Should Penghoul drop extra shattered ice while their ice armor is broken [false/true]").getBoolean(false);

        pSpawnRate_Undertaker = config.get("Undertaker", "undertaker spawn rate", 8, "Set the spawn rate of Undertaker [0-100]", 0, 100).getInt(8);
        Undertaker_Health = config.get("Undertaker", "undertaker health", 40.0D, "Maximum Undertaker health [1-1000]", 1, 1000).getDouble(40.0D);
        Undertaker_Attack = config.get("Undertaker", "undertaker attack", 6.0D, "Undertaker strength [1-1000]", 1, 1000).getDouble(6.0D);
        Undertaker_Ability_Num = config.get("Undertaker", "undertaker summon number", 4, "Set the number of Unburied summoned per cast [0-100]", 0, 100).getInt(4);
        Undertaker_Ability_Max = config.get("Undertaker", "undertaker summon max", 4, "Set the max number of Unburied summoned [0-100]", 0, 100).getInt(4);
        Undertaker_Ability_Cooldown = config.get("Undertaker", "undertaker summon cooldown", 15, "Set the cooldown of summoning Unburied [0-100]", 0, 100).getInt(15);

        pSpawnRate_Unburied = config.get("Unburied", "unburied spawn rate", 20, "Set the spawn rate of Unburied [0-10000]", 0, 10000).getInt(20);
        Unburied_Health = config.get("Unburied", "unburied health", 20.0D, "Maximum Unburied health [1-1000]", 1, 1000).getDouble(20.0D);
        Unburied_Attack = config.get("Unburied", "unburied attack", 3.0D, "Unburied strength [1-1000]", 1, 1000).getDouble(3.0D);
        Unburied_Lifespan = config.get("Unburied", "unburied lifespan", 60, "Summoned Unburied (and variants) lifespan [1-10000]", 1, 10000).getInt(60);

        pSpawnRate_GhostRay = config.get("Ghost Ray", "ghost ray spawn rate", 10, "Set the spawn rate of Ghost Ray [0-100]", 0, 100).getInt(10);
        pSpawnRate_GhostRay_End = config.get("Ghost Ray", "ghost ray end dimension spawn rate", 3, "Set the spawn rate of Ghost Ray in the End dimension [0-100]", 0, 100).getInt(3);
        GhostRay_Health = config.get("Ghost Ray", "ghost ray health", 20.0D, "Maximum Ghost Ray health [1-1000]", 1, 1000).getDouble(20.0D);
        GhostRay_FlyingHeight_limit = config.get("Ghost Ray", "ghost ray height limit", 48, "Set the height limit to X blocks above the ground for Ghost Rays, 0 = Infinite [0-100]", 0, 100).getInt(48);
        GhostRay_Middle_End_Island = config.get("Ghost Ray", "ghost ray middle end island spawn", false, "Should Ghost Rays spawn at the middle end island where the dragon is located [false/true]").getBoolean(false);
        GhostRay_Ghostly_Touch = config.get("Ghost Ray", "ghost ray ghostly touch", true, "Should Ghost Rays inflict an effect (weakness/void dust) on hit [false/true]").getBoolean(true);
        GhostRay_End_Variants = config.get("Ghost Ray", "ghost ray end variants", true, "Should End Ghost Rays have alternate texture variants when spawning? [false/true]").getBoolean(true);

        pSpawnRate_Banshee = config.get("Banshee", "banshee spawn rate", 20, "Set the spawn rate of Banshee [0-100]", 0, 100).getInt(20);
        Banshee_Health = config.get("Banshee", "banshee health", 34.0D, "Maximum Banshee health [1-1000]", 1, 1000).getDouble(34.0D);
        Banshee_Attack = config.get("Banshee", "banshee attack", 7.0D, "Banshee strength [1-1000]", 1, 1000).getDouble(7.0D);
        Banshee_Ability_Radius = config.get("Banshee", "banshee scream radius", 3.0D, "Set the effect radius of Banshee scream [1-1000]", 1, 1000).getDouble(3.0D);

        pSpawnRate_Weta = config.get("Weta", "weta spawn rate", 30, "Set the spawn rate of Weta [0-100]", 0, 100).getInt(30);
        Weta_Health = config.get("Weta", "weta health", 12.0D, "Maximum Weta health [1-1000]", 1, 1000).getDouble(12.0D);
        Weta_Attack = config.get("Weta", "weta attack", 3.0D, "Weta strength [1-1000]", 1, 1000).getDouble(3.0D);
        Weta_Lifespan = config.get("Weta", "weta lifespan", 60, "Summoned Weta lifespan [1-10000]", 1, 10000).getInt(60);
        Weta_Harvest_Diseased_Wheat = config.get("Weta", "weta diseased wheat drop rate", 0.15D, "Chance for diseased Weta to drop diseased wheat when breaking crops [0-1]", 0, 1).getDouble(0.15D);

        pSpawnRate_Avaton = config.get("Avaton", "avaton spawn rate", 20, "Set the spawn rate of Avaton [0-100]", 0, 100).getInt(20);
        Avaton_Health = config.get("Avaton", "avaton health", 30.0D, "Maximum Avaton health [1-1000]", 1, 1000).getDouble(30.0D);
        Avaton_Attack = config.get("Avaton", "avaton attack", 5.0D, "Avaton strength [1-1000]", 1, 1000).getDouble(5.0D);
        Avaton_Ability_Num = config.get("Avaton", "avaton summon number", 2, "Set the number of Weta summoned per cast [0-100]", 0, 100).getInt(2);
        Avaton_Ability_Max = config.get("Avaton", "avaton summon max", 16, "Set the max number of Weta summoned [0-100]", 0, 100).getInt(16);
        Avaton_Ability_Cooldown = config.get("Avaton", "avaton summon cooldown", 8, "Set the cooldown of summoning Weta [0-100]", 0, 100).getInt(8);

        pSpawnRate_Forsaken = config.get("Forsaken", "forsaken spawn rate", 10, "Set the spawn rate of Forsaken [0-10000]", 0, 10000).getInt(10);
        Forsaken_Health = config.get("Forsaken", "forsaken health", 30.0D, "Maximum Forsaken health [1-1000]", 1, 1000).getDouble(30.0D);
        Forsaken_Attack = config.get("Forsaken", "forsaken attack", 4.0D, "Forsaken strength [1-1000]", 1, 1000).getDouble(4.0D);

        pSpawnRate_SkeletonKing = config.get("Skeleton King", "skeleton king summon", true, "Should Skeleton King be summoned with the crown [false/true]").getBoolean(true);
        SkeletonKing_Health = config.get("Skeleton King", "skeleton king health", 360.0D, "Maximum Skeleton King health [1-1000]", 1, 1000).getDouble(360.0D);
        SkeletonKing_Attack = config.get("Skeleton King", "skeleton king attack", 16.0D, "Skeleton King strength [1-1000]", 1, 1000).getDouble(16.0D);
        SkeletonKing_Ability_Sand_Tomb_Cooldown = config.get("Skeleton King", "skeleton king sand tomb cooldown", 10, "Set the cooldown of Sand Tomb [0-100]", 0, 100).getInt(10);
        SkeletonKing_Ability_Sand_Wraith_Cooldown = config.get("Skeleton King", "skeleton king sand wraith cooldown", 16, "Set the cooldown of Sand Wraith [0-100]", 0, 100).getInt(16);
        SkeletonKing_Ability_Projectile_Cooldown = config.get("Skeleton King", "skeleton king projectile cooldown", 80, "Set the cooldown of fired projectiles [0-100]", 0, 100).getInt(80);
        SkeletonKing_Ability_Summon_Cooldown = config.get("Skeleton King", "skeleton king summon cooldown", 16, "Set the cooldown of summoning Unburied [0-100]", 0, 100).getInt(16);
        SkeletonKing_Ability_Summon_Num = config.get("Skeleton King", "skeleton king summon number", 6, "Set the number of Forsaken summoned per cast [0-100]", 0, 100).getInt(6);
        SkeletonKing_Ability_Summon_Max = config.get("Skeleton King", "skeleton king summon max", 24, "Set the max number of Forsaken summoned [0-100]", 0, 100).getInt(24);
        SkeletonKing_Minion_Lifespan = config.get("Skeleton King", "skeleton king minion lifespan", 120, "Summoned Forsaken lifespan [1-10000]", 1, 10000).getInt(120);
        SkeletonKing_Loot_Option = config.get("Skeleton King", "skeleton king loot in chest", true, "Should Skeleton King drop its loot inside a chest [false/true]").getBoolean(true);

        pSpawnRate_Mummy = config.get("Mummy", "mummy spawn rate", 20, "Set the spawn rate of Mummy [0-10000]", 0, 10000).getInt(20);
        Mummy_Health = config.get("Mummy", "mummy health", 24.0D, "Maximum Mummy health [1-1000]", 1, 1000).getDouble(24.0D);
        Mummy_Attack = config.get("Mummy", "mummy attack", 4.0D, "Mummy strength [1-1000]", 1, 1000).getDouble(4.0D);

        pSpawnRate_Cactyrant = config.get("Cactyrant", "cactyrant spawn rate", 10, "Set the spawn rate of Cactyrant [0-10000]", 0, 10000).getInt(10);
        Cactyrant_Health = config.get("Cactyrant", "cactyrant health", 60.0D, "Maximum Cactyrant health [1-1000]", 1, 1000).getDouble(60.0D);
        Cactyrant_Attack = config.get("Cactyrant", "cactyrant attack", 8.0D, "Cactyrant strength [1-1000]", 1, 1000).getDouble(8.0D);
        Cactyrant_Ability_Cooldown = config.get("Cactyrant", "cactyrant summon cooldown", 3, "Set the cooldown of thorn barrage [0-100]", 0, 100).getInt(3);

        pSpawnRate_Cactoid = config.get("Cactoid", "cactoid spawn rate", 15, "Set the spawn rate of Cactoid  [0-10000]", 0, 10000).getInt(15);
        Cactoid_Health = config.get("Cactoid", "cactoid health", 20.0D, "Maximum Cactoid health [1-1000]", 1, 1000).getDouble(20.0D);
        Cactoid_Attack = config.get("Cactoid", "cactoid attack", 4.0D, "Cactoid strength [1-1000]", 1, 1000).getDouble(4.0D);

        pSpawnRate_Sea_Hag = config.get("Sea Hag", "sea hag spawn rate", 20, "Set the spawn rate of Sea Hag [0-100]", 0, 100).getInt(20);
        Sea_Hag_Health = config.get("Sea Hag", "sea hag health", 30.0D, "Maximum Sea Hag health [1-1000]", 1, 1000).getDouble(30.0D);
        Sea_Hag_Attack = config.get("Sea Hag", "sea hag attack", 5.0D, "Sea Hag strength [1-1000]", 1, 1000).getDouble(5.0D);
        Sea_Hag_Ability_Num = config.get("Sea Hag", "sea hag summon number", 4, "Set the number of Ghost Swarmers summoned per cast [0-100]", 0, 100).getInt(4);
        Sea_Hag_Ability_Max = config.get("Sea Hag", "sea hag summon max", 8, "Set the max number of Ghost Swarmers summoned [0-100]", 0, 100).getInt(8);
        Sea_Hag_Ability_Cooldown = config.get("Sea Hag", "sea hag summon cooldown", 12, "Set the cooldown of summoning Ghost Swarmers [0-100]", 0, 100).getInt(12);

        pSpawnRate_Grave_Robber = config.get("Grave Robber", "grave robber spawn rate", 15, "Set the spawn rate of Grave Robber [0-100]", 0, 100).getInt(15);
        Grave_Robber_Ghost_Chance = config.get("Grave Robber", "grave robber ghost chance", 0.5D, "The chance for a Ghost of Grave Robber to appear after a Grave Robber dies [0-100]", 0, 100).getDouble(0.5D);
        Grave_Robber_Health = config.get("Grave Robber", "grave robber health", 34.0D, "Maximum Grave Robber health [1-1000]", 1, 1000).getDouble(34.0D);
        Grave_Robber_Attack = config.get("Grave Robber", "grave robber attack", 6.0D, "Grave Robber strength [1-1000]", 1, 1000).getDouble(6.0D);

        pSpawnRate_Grave_Robber_Ghost = config.get("Ghost of Grave Robber", "grave robber ghost spawn rate", 2, "Set the spawn rate of Ghost of Grave Robber [0-100]", 0, 100).getInt(2);
        Grave_Robber_Ghost_Health = config.get("Ghost of Grave Robber", "grave robber ghost health", 34.0D, "Maximum Ghost of Grave Robber health [1-1000]", 1, 1000).getDouble(34.0D);
        Grave_Robber_Ghost_Attack = config.get("Ghost of Grave Robber", "grave robber ghost attack", 6.0D, "Ghost of Grave Robber strength [1-1000]", 1, 1000).getDouble(6.0D);
        Grave_Robber_Ghost_Ability_Num = config.get("Ghost of Grave Robber", "grave robber ghost summon number", 2, "Set the number of Ghost Swarmers summoned per cast [0-100]", 0, 100).getInt(2);
        Grave_Robber_Ghost_Ability_Max = config.get("Ghost of Grave Robber", "grave robber ghost summon max", 8, "Set the max number of Ghost Swarmers summoned [0-100]", 0, 100).getInt(8);
        Grave_Robber_Ghost_Ability_Cooldown = config.get("Ghost of Grave Robber", "grave robber ghost summon cooldown", 20, "Set the cooldown of summoning Ghost Swarmers [0-100]", 0, 100).getInt(20);
        Grave_Robber_Ghost_Minion_Lifespan = config.get("Ghost of Grave Robber", "grave robber ghost minion lifespan", 60, "Summoned Vex lifespan [1-10000]", 1, 10000).getInt(60);

        pSpawnRate_Wraith = config.get("Revenant", "revenant spawn rate", 20, "Set the spawn rate of Revenant [0-10000]", 0, 10000).getInt(20);
        Wraith_Health = config.get("Revenant", "revenant health", 25.0D, "Maximum Revenant health [1-1000]", 1, 1000).getDouble(25.0D);
        Wraith_Attack = config.get("Revenant", "revenant attack", 5.0D, "Revenant strength [1-1000]", 1, 1000).getDouble(5.0D);
        Wraith_Ability_Radius = config.get("Revenant", "revenant scream radius", 8.0D, "Set the effect radius of Revenant scream [1-1000]", 1, 1000).getDouble(8.0D);

        pSpawnRate_Ghost_Swarmer = config.get("Ghost Swarmer", "ghost swarmer spawn rate", 0, "Set the spawn rate of Ghost Swarmer [0-100]", 0, 100).getInt(0);
        Ghost_Swarmer_Health = config.get("Ghost Swarmer", "ghost swarmer health", 8.0D, "Maximum Ghost Swarmer health [1-1000]", 1, 1000).getDouble(8.0D);
        Ghost_Swarmer_Attack = config.get("Ghost Swarmer", "ghost swarmer attack", 3.5D, "Ghost Swarmer strength [1-1000]", 1, 1000).getDouble(3.5D);
        Ghost_Swarmer_Lifespan = config.get("Ghost Swarmer", "ghost swarmer lifespan", 60, "Summoned Ghost Swarmer lifespan [1-10000]", 1, 10000).getInt(60);

        Amber_Scarab_Health = config.get("Amber Scarab", "amber scarab health", 14.0D, "Maximum Amber Scarab health [1-1000]", 1, 1000).getDouble(14.0D);
        Amber_Scarab_Attack = config.get("Amber Scarab", "amber scarab attack", 3.5D, "Amber Scarab strength [1-1000]", 1, 1000).getDouble(3.5D);
        Amber_Scarab_Lifespan = config.get("Amber Scarab", "amber scarab lifespan", 60, "Summoned Amber Scarab lifespan [1-10000]", 1, 10000).getInt(60);

        pSpawnRate_Enigmoth = config.get("Enigmoth", "enigmoth spawn rate", 1, "Set the spawn rate of Enigmoth [0-10000]", 0, 10000).getInt(1);
        Enigmoth_Health = config.get("Enigmoth", "enigmoth health", 60.0D, "Maximum Enigmoth health [1-1000]", 1, 1000).getDouble(60.0D);
        Enigmoth_Attack = config.get("Enigmoth", "enigmoth attack", 8.0D, "Enigmoth strength [1-1000]", 1, 1000).getDouble(8.0D);
        Enigmoth_Ability_Cooldown = config.get("Enigmoth", "enigmoth spell cooldown", 6, "Set the cooldown of spreading scales [0-100]", 0, 100).getInt(6);
        Enigmoth_Ability_Cooldown_Mount = config.get("Enigmoth", "mounted enigmoth spell cooldown", 6, "Set the cooldown of spreading scales when mounted [0-100]", 0, 100).getInt(6);
        Enigmoth_FlyingHeight_limit = config.get("Enigmoth", "enigmoth height limit", 16, "Set the height limit to X blocks above the ground for Enigmoths, 0 = Infinite [0-100]", 0, 100).getInt(16);
        Enigmoth_Middle_End_Island = config.get("Enigmoth", "enigmoth middle end island spawn", false, "Should Enigmoths spawn at the middle end island where the dragon is located [false/true]").getBoolean(false);

        pSpawnRate_Enigmoth_Larva = config.get("Enigmoth Caterpillar", "enigmoth caterpillar spawn rate", 2, "Set the spawn rate of Enigmoth Caterpillar [0-10000]", 0, 10000).getInt(2);
        Enigmoth_Larva_Health = config.get("Enigmoth Caterpillar", "enigmoth caterpillar health", 12.0D, "Maximum Enigmoth Caterpillar health [1-1000]", 1, 1000).getDouble(12.0D);
        Enigmoth_Larva_Attack = config.get("Enigmoth Caterpillar", "enigmoth caterpillar attack", 2.0D, "Enigmoth Caterpillar strength [1-1000]", 1, 1000).getDouble(2.0D);
        Enigmoth_Larva_Ability_Cooldown = config.get("Enigmoth Caterpillar", "enigmoth caterpillar spell cooldown", 6, "Set the cooldown of vanishing [0-100]", 0, 100).getInt(6);
        Enigmoth_Larva_Middle_End_Island = config.get("Enigmoth Caterpillar", "enigmoth caterpillar middle end island spawn", false, "Should Enigmoth Caterpillars spawn at the middle end island where the dragon is located [false/true]").getBoolean(false);
        Enigmoth_Larva_Pickup = config.get("Enigmoth Caterpillar", "enigmoth caterpillar pickup", false, "You can pick up Enigmoth Caterpillars by right clicking them with an empty main hand while sneaking [false/true]").getBoolean(false);

        MoltenHammer_PVP = config.get(Configuration.CATEGORY_GENERAL, "allow molten hammer pvp", false, "Allow Molten Hammer active effect to hit players [false/true]").getBoolean(false);
        Fission_ModEntity = config.get(Configuration.CATEGORY_GENERAL, "Global Potion of Fission", false, "Allows the Potion of Fission to be used on any mob regardless of the list [false/true]").getBoolean(false);
        Fission_Allowlist = config.getStringList("Potion of Fission List", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:chicken",
                        "minecraft:cow",
                        "minecraft:donkey",
                        "minecraft:horse",
                        "minecraft:llama",
                        "minecraft:mooshroom",
                        "minecraft:mule",
                        "minecraft:ocelot",
                        "minecraft:parrot",
                        "minecraft:pig",
                        "minecraft:polar_bear",
                        "minecraft:rabbit",
                        "minecraft:sheep",
                        "minecraft:wolf",
                        "mod_lavacow:lavacow",
                        "mod_lavacow:mimic",
                        "mod_lavacow:raven",
                        "mod_lavacow:salamander",
                        "mod_lavacow:scarecrow"

                },
                "Customize the list of mobs that the Potion of Fission can work on (ignore this if the global setting is enabled). Ex. \"minecraft:zombie\" or \"mod_lavacow:undertaker\"");
        General_Intestine = config.get(Configuration.CATEGORY_GENERAL, "entity drop intestine", 4, "Set the drop rate of Intestine [0-100]", 0, 100).getInt(4);
        Intestine_lt = config.getStringList("loot table for intestine", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:slime_ball,0.4",
                        "minecraft:dye,0.4,15",
                        "mod_lavacow:sharptooth,0.1",
                        "minecraft:beetroot_seeds,0.1",
                        "minecraft:wheat_seeds,0.1",
                        "minecraft:melon_seeds,0.1",
                        "minecraft:pumpkin_seeds,0.1",
                        "minecraft:clay_ball,0.1",
                        "mod_lavacow:chitin,0.1",
                        "minecraft:gold_nugget,0.05",
                        "minecraft:iron_nugget,0.05",
                        "minecraft:diamond,0.01"},
                "Customize Items and their drop rates for the Intestine. Ex. \"minecraft:slime_ball,0.4\" or \"mod_lavacow:sharptooth,0.1\"");
        Intestine_banlist = config.getStringList("mobs that intestine should not drop from", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:blaze",
                        "minecraft:slime",
                        "minecraft:magma_cube",
                        "minecraft:ender_dragon",
                        "minecraft:wither",
                        "minecraft:skeleton",
                        "minecraft:stray",
                        "minecraft:chicken",
                        "minecraft:squid",
                        "minecraft:snow_golem",
                        "minecraft:iron_golem",
                        "minecraft:skeleton_horse",
                        "minecraft:enderman",
                        "minecraft:silverfish",
                        "minecraft:endermite",
                        "minecraft:shulker",
                        "mod_lavacow:sludgelord",
                        "mod_lavacow:lilsludge",
                        "mod_lavacow:scarecrow",
                        "mod_lavacow:zombiepiranha",
                        "mod_lavacow:piranha",
                        "mod_lavacow:parasite",
                        "mod_lavacow:ghostray",
                        "mod_lavacow:banshee",
                        "mod_lavacow:avaton",
                        "mod_lavacow:forsaken",
                        "mod_lavacow:skeletonking",
                        "mod_lavacow:cactoid",
                        "mod_lavacow:cactyrant",
                        "mod_lavacow:sea_hag",
                        "mod_lavacow:wraith",
                        "mod_lavacow:ghost_swarmer",
                        "mod_lavacow:amber_lord",
                        "mod_lavacow:amber_scarab",
                        "mod_lavacow:enigmoth_larva"},
                "Customize the banlist for which mobs that intestines shouldn't drop from. Ex. \"minecraft:slime\" or \"mod_lavacow:vespa\"");

        Raven_Loot = config.getStringList("loot table for ravens", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:beetroot_seeds,0.15",
                        "minecraft:wheat_seeds,0.15,2",
                        "minecraft:melon_seeds,0.15",
                        "minecraft:pumpkin_seeds,0.15",
                        "minecraft:gold_nugget,0.1,2",
                        "minecraft:iron_nugget,0.1,2"
                },
                "Customize drop rates of the items which ravens can find. Ex. \"minecraft:fish@3,0.4,2\" or \"mod_lavacow:sharptooth,0.1\"");

        Seagull_Loot = config.getStringList("loot table for seagulls", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:fish@0,0.15",
                        "minecraft:fish@2,0.15"
                },
                "Customize drop rates of the items which seagulls can find. Ex. \"minecraft:fish@3,0.4,2\" or \"mod_lavacow:sharptooth,0.1\"");

        Spectral_Raven_Loot = config.getStringList("loot table for spectral ravens", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:gold_nugget,0.15,3",
                        "minecraft:iron_nugget,0.15,3"
                },
                "Customize drop rates of the items which spectral ravens can find. Ex. \"minecraft:fish@3,0.4,2\" or \"mod_lavacow:sharptooth,0.1\"");

        Cocoon_Lifespan = config.get(Configuration.CATEGORY_GENERAL, "cocoon lifespan", 8, "The amount of seconds before cocoons hatch into an enigmoth or a vespa").getInt(8);

        GoldenHeart_dur = config.get(Configuration.CATEGORY_GENERAL, "golden heart durability", 250, "Set the durability of Golden Heart, 0 = Infinite [0-10000]", 0, 10000).getInt(250);
        GoldenHeart_dur_drop = config.get(Configuration.CATEGORY_GENERAL, "golden heart durability drop", 100, "Set the chance for Golden Heart to drop 1 durability per tick, 0 = Infinite [0-100]", 0, 100).getInt(100);
        GoldenHeart_bl = config.getStringList("banlisted items from golden heart", Configuration.CATEGORY_GENERAL, new String[0], "BlackBanlist for items that the Golden Heart is unable to mend. Ex. \"minecraft:shears\" or \"mod_lavacow:moltenhammer\"");
        GoldenHeart_BookEnchantability = config.get(Configuration.CATEGORY_GENERAL, "golden heart book enchantability", false, "Should the Golden Heart be enchantable with enchanted books? [false/true]").getBoolean(false);
        GoldenHeart_GrantsRegeneration = config.get(Configuration.CATEGORY_GENERAL, "golden heart grants regeneration", true, "Enables the Regeneration effect of the Golden Heart. [false/true]").getBoolean(true);
        GoldenHeart_Regeneration_Amount = config.get(Configuration.CATEGORY_GENERAL, "golden heart regeneration amount", 8, "Amount of seconds of Regeneration applied by the Golden Heart, 0 = Infinite [0-10000]", 0, 10000).getInt(8);
        GoldenHeart_Repair_Amount = config.get(Configuration.CATEGORY_GENERAL, "golden heart repair amount", 1, "Amount of durability repaired per second by the Golden Heart. [0-10000]", 0, 10000).getInt(1);
        GoldenHeart_RepairsEquipment = config.get(Configuration.CATEGORY_GENERAL, "golden heart repairs equipment", true, "Allow the Golden Heart to repair worn equipment. [false/true]").getBoolean(true);

        BoneSword_Boss_Damage = config.get(Configuration.CATEGORY_GENERAL, "bone sword boss damage", false, "Allow the Bone Sword to deal extra damage to bosses. [false/true]").getBoolean(false);
        BoneSword_Damage = config.get(Configuration.CATEGORY_GENERAL, "bone sword bonus damage", 5, "Set the bonus damage of Bone Sword to X% [0-100]", 0, 100).getInt(5);
        BoneSword_DamageCap = config.get(Configuration.CATEGORY_GENERAL, "bone sword bonus damage cap", 10000, "Set the bonus damage cap of Bone Sword [0-10000]", 0, 10000).getInt(10000);

        HaloNecklace_Damage = config.get(Configuration.CATEGORY_GENERAL, "halo necklace bonus damage", 10, "Set the bonus damage of Halo Necklace to X% [0-10000]", 0, 10000).getInt(10);

        Dreamcatcher_BookEnchantability = config.get(Configuration.CATEGORY_GENERAL, "dreamcatcher book enchantability", false, "Should the Dreamcatcher be enchantable with enchanted books? [false/true]").getBoolean(false);
        Dreamcatcher_dur = config.get(Configuration.CATEGORY_GENERAL, "dreamcatcher durability", 120, "Set the durability of Dreamcatcher, 0 = Infinite [0-10000]", 0, 10000).getInt(120);
        Dreamcatcher_dur_drop = config.get(Configuration.CATEGORY_GENERAL, "dreamcatcher durability drop", 30, "The durability lost each time the Dreamcatcher is triggered, 0 = Infinite [0-10000]", 0, 10000).getInt(30);
        Dreamcatcher_spawn = config.getStringList("spawn list for dreamcatcher", Configuration.CATEGORY_GENERAL,
                new String[]{
                        "minecraft:zombie,40,1,2",
                        "minecraft:skeleton,40,1,2",
                        "minecraft:spider,40,1,2",
                        "minecraft:silverfish,40,1,2",
                        "minecraft:bat,40,4,8",
                        "mod_lavacow:foglet,40,1,2",
                        "mod_lavacow:slothoman,40,1,2",
                        "mod_lavacow:unburied,40,1,2",
                        "mod_lavacow:ptera,40,1,2",
                        "mod_lavacow:lilsludge,40,1,2",
                        "mod_lavacow:amber_scarab,40,1,2",
                        "mod_lavacow:ghost_swarmer,40,1,2",
                        "mod_lavacow:pingu,40,1,2",
                        "mod_lavacow:weta,40,1,2",
                        "minecraft:enderman,20,1,1",
                        "minecraft:cave_spider,20,1,1",
                        "minecraft:witch,20,1,1",
                        "mod_lavacow:undeadswine,20,1,1",
                        "mod_lavacow:ithaqua,20,1,1",
                        "mod_lavacow:sludgelord,20,1,1",
                        "mod_lavacow:amberlord,20,1,1",
                        "mod_lavacow:vespa,20,1,1",
                        "mod_lavacow:scarecrow,20,1,1",
                        "mod_lavacow:boneworm,20,1,1",
                        "mod_lavacow:undertaker,20,1,1",
                        "mod_lavacow:banshee,20,1,1",
                        "mod_lavacow:avaton,20,1,1",
                        "mod_lavacow:wraith,20,1,1",
                        "mod_lavacow:sea_hag,20,1,1"
                },
                "Customize the Spawn list for the Dreamcatcher. Ex. \"mod_lavacow:foglet,40,1,2\" or \"mod_lavacow:vespa,20,1,1\"");

        Potion_Enable = config.get(Configuration.CATEGORY_GENERAL, "enable brewing recipe", true, "Should new brewing recipes be added (existing properties will be preserved). [false/true]").getBoolean(true);
        Enchantment_Enable = config.get(Configuration.CATEGORY_GENERAL, "enable enchantment", true, "Should new enchantments be added (existing properties will be preserved). [false/true]").getBoolean(true);
        Enchantment_Anvil_Enable = config.get(Configuration.CATEGORY_GENERAL, "enable anvil enchantment", true, "Should certain items apply enchantments on the anvil (e.g. Poisonous Spore applies the Poisonous II enchant on swords). [false/true]").getBoolean(true);
        Soulforged_Anvil_Recipes = config.get(Configuration.CATEGORY_GENERAL, "enable soulforged anvil recipes", true, "Should soulforged armor/tools be created when combining a soulforged heart with any molten armor piece/tool on the anvil (e.g. Molten Warhammer + Soulforged Heart = Soulforged Warhammer). [false/true]").getBoolean(true);

        SludgeWand_Cooldown = config.get(Configuration.CATEGORY_GENERAL, "pestilence cooldown", 60, "Ability cooldown of \"Pestilence\" [1-10000]", 1, 10000).getInt(60);
        ScarabWand_Cooldown = config.get(Configuration.CATEGORY_GENERAL, "scarab scepter cooldown", 60, "Ability cooldown of Scarab Scepter [1-10000]", 1, 10000).getInt(60);
        Undertaker_Shovel_Cooldown = config.get(Configuration.CATEGORY_GENERAL, "midnight mourne cooldown", 60, "Ability cooldown of Midnight Mourne [1-10000]", 1, 10000).getInt(60);

        Tinkers_Compat = config.get(Configuration.CATEGORY_GENERAL, "tinkers' construct integration", true, "Should new tool materials be added to Tinkers' Construct when installed? [false/true]").getBoolean(true);
        Tinkers_Armor_Compat = config.get(Configuration.CATEGORY_GENERAL, "construct's armory integration", true, "Should new armor materials be added to Construct's Armory when installed? (requires Tinkers' Construct) [false/true]").getBoolean(true);
        Quark_Compat = config.get(Configuration.CATEGORY_GENERAL, "quark integration", true, "Should new features be added when Quark is also installed? [false/true]").getBoolean(true);
        SunScreen_Mode = config.get(Configuration.CATEGORY_GENERAL, "sunscreen mode", false, "Mobs in this mod will not burn under daylight. [false/true]").getBoolean(false);

        SpawnRate_Cemetery = config.get(Configuration.CATEGORY_GENERAL, "cemetery spawn rate", 500, "Spawn rate of Cemetery (higher number = less frequent) [1-10000]", 1, 10000).getInt(500);

        Spawn_AllowList = config.get(Configuration.CATEGORY_GENERAL, "mob spawn allow dimensions", new int[]{DimensionType.OVERWORLD.getId(), DimensionType.NETHER.getId(), DimensionType.THE_END.getId()}, "All mobs are only allowed to spawn in these dimensions' IDs").getIntList();

        Suicidal_Minion = config.get(Configuration.CATEGORY_GENERAL, "suicidal minion", true, "Entities summoned by other mobs vanish when their summoner dies or despawns. [false/true]").getBoolean(true);

        MootenHeart_Damage = config.get(Configuration.CATEGORY_GENERAL, "molten heart damage reduction", 20, "Set the fire damage reduction of Molten Heart/Soulforged Heart to X% [0-10000]", 0, 10000).getInt(20);
        SoulforgedHeart_Healing = config.get(Configuration.CATEGORY_GENERAL, "soulforged heart healing boost", 25, "Sets the amount of extra health healed with a Soulforged Heart equipped to X% [0-10000]", 0, 10000).getInt(25);

        Spawn_Cemetery_AllowList = config.get(Configuration.CATEGORY_GENERAL, "cemetery spawn allow dimensions", new int[]{DimensionType.OVERWORLD.getId()}, "Cemetery are only allowed to spawn in these dimensions' IDs").getIntList();
        Cemetery_SpawnRate = config.get(Configuration.CATEGORY_GENERAL, "cemetery spawns unburied", 40, "Cemetery spawns Unburied occasionally. [0-100]", 0, 100).getInt(40);
        Generate_Cemetery = config.get(Configuration.CATEGORY_GENERAL, "generate cemetery", true, "Generate Cemetery in the Overworld. [false/true]").getBoolean(true);

        SpawnRate_Desert_Tomb = config.get(Configuration.CATEGORY_GENERAL, "desert tomb spawn rate", 750, "Spawn rate of Desert Tomb (higher number = less frequent) [0-10000]", 0, 10000).getInt(750);
        Generate_Desert_Tomb = config.get(Configuration.CATEGORY_GENERAL, "generate desert tomb", true, "Generate Desert Tomb in the Overworld. [false/true]").getBoolean(true);

        Bowls_Stack = config.get(Configuration.CATEGORY_GENERAL, "bowls stack", true, "All bowl food items from the mod will stack up to 64. [false/true]").getBoolean(true);

        Should_Villager_Fear = config.get(Configuration.CATEGORY_GENERAL, "zombies scare villagers", true, "Should zombies (Unburied and variants) scare villagers [false/true]").getBoolean(true);

        MonsterSpawner_Mobs = config.get(Configuration.CATEGORY_GENERAL, "monster spawner mobs", true, "Should certain mobs (Unburied and Foglet) spawn in monster spawners [false/true]").getBoolean(true);

        if (config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (mod_LavaCow.MODID.equals(event.getModID())) {
            syncConfigs();
        }
    }
}


