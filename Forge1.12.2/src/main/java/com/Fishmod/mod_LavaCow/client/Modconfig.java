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
	private File configFolder;
	
	public static Configuration config = null;
	
	//public static final String GENERAL = "general";
	
	public static boolean pGlowshroomGen;
	public static int pSpawnRate_Lavacow;
	public static int pSpawnRate_Foglet;
	public static int pSpawnRate_Parasite;
	public static int pSpawnRate_UndeadSwine;
	public static int pSpawnRate_ZombieMushroom;
	public static int pSpawnRate_ZombieFrozen;
	public static int pSpawnRate_Salamander;
	public static int pSpawnRate_Wendigo;
	public static int pSpawnRate_Mimic;
	public static int pSpawnRate_SludgeLord;
	public static int pSpawnRate_Raven;
	public static int pSpawnRate_Ptera;
	public static int pSpawnRate_Vespa;
	public static int pSpawnRate_Scarecrow;
	public static int pSpawnRate_ZombiePiranha;
	public static int pSpawnRate_Piranha;
	public static int pSpawnRate_BoneWorm;
	public static int pSpawnRate_Pingu;
	public static int pSpawnRate_Undertaker;
	public static int pSpawnRate_GhostRay;
	public static int pSpawnRate_Banshee;
	public static int pSpawnRate_Weta;
	public static int pSpawnRate_Avaton;
	
	public static boolean pFoglet_SpawnAlly;
	public static boolean MoltenHammer_PVP;
	public static int Parasite_SandSpawn;
	public static boolean Parasite_Plague;
	public static boolean Wendigo_AnimalAttack;
	public static boolean Fission_ModEntity;
	//public static boolean Mimic_noGUI;
	//public static boolean ModBiomes_spawn;
	public static boolean Parasite_Attach;
	public static int UndeadSwine_DropHeart;
	public static int ZombieMushroom_DropSpore;
	public static int General_Intestine;
	public static int GoldenHeart_dur;
	public static String[] GoldenHeart_bl = new String[0];
	public static int pSpawnRate_Glowshroom;
	public static int pSpreadRate_Glowshroom;
	public static boolean Piranha_AnimalAttack;
	public static int FlyingHeight_limit;
	public static int BoneSword_Damage;
	public static int HaloNecklace_Damage;
	public static String[] Intestine_lt = new String[0];
	public static String[] Intestine_banlist = new String[0];
	public static int pScarecrow_PlagueDoctor;
	public static String[] DreamCatcher_spawn = new String[0];
	public static boolean Shattered_Ice;
	public static int SludgeWand_Cooldown;
	public static int Undertaker_Shovel_Cooldown;
	public static int BoneWorm_DropHeart;
	public static int Undertaker_Ability_Num;
	public static int Undertaker_Ability_Max;
	public static int Undertaker_Ability_Cooldown;
	public static int SludgeLord_Ability_Num;
	public static int SludgeLord_Ability_Max;
	public static int SludgeLord_Ability_Cooldown;
	public static boolean Tinkers_Compat;
	public static boolean SunScreen_Mode;
	public static int SpawnRate_Cemetery;
	public static int BoneSword_DamageCap;
	public static int[] Spawn_AllowList = new int[0];
	public static int Avaton_Ability_Num;
	public static int Avaton_Ability_Max;
	public static int Avaton_Ability_Cooldown;
	public static boolean Suicidal_Minion;
	
	public static boolean Potion_Enable;
	public static boolean Enchantment_Enable;
	
	public static double UndeadSwine_Health;
	public static double UndeadSwine_Attack;

	public static double Salamander_Health;
	public static double Salamander_Attack;

	public static double Wendigo_Health;
	public static double Wendigo_Attack;

	public static double SludgeLord_Health;
	public static double SludgeLord_Attack;

	public static double Scarecrow_Health;
	public static double Scarecrow_Attack;

	public static double BoneWorm_Health;
	public static double BoneWorm_Attack;
	
	public static double Undertaker_Health;
	public static double Undertaker_Attack;
	
	public static double LilSludge_Health;
	public static double LilSludge_Attack;
	public static int LilSludge_Lifespan;
	
	public static double Unburied_Health;
	public static double Unburied_Attack;
	public static int Unburied_Lifespan;
	
	public static boolean Raven_Perch;
	public static boolean Raven_Slowfall;
	
	public static double Ptera_Health;
	public static double Ptera_Attack;
	
	public static double Vespa_Health;
	public static double Vespa_Attack;
	
	public static double GhostRay_Health;
	
	public static double Banshee_Health;
	public static double Banshee_Attack;
	
	public static double Avaton_Health;
	public static double Avaton_Attack;
	
	public final String[] usedCategories = { Configuration.CATEGORY_GENERAL, "Avaton", "Banshee", "Moogma", "Mycosis", "Parasite", "Foglet", "Frigid", "Ghost Ray", "Undead Swine",
			"Salamander", "Ithaqua", "Mimicrab", "Sludge Lord", "Lil'Sludge", "Raven", "Ptera", "Vespa", "Scarecrow", /*"Vespa Cocoon",*/ "Swarmer",
			"Piranha", "Osvermis", "Pingu", "Undertaker", "Unburied", "Weta", "Glowshroom"};
	
	public void loadConfig(FMLPreInitializationEvent event) {
		//File configFile = event.getSuggestedConfigurationFile();
		File configFile = new File(Loader.instance().getConfigDir(), "Fishs_Undead_Rising.cfg");
		configFolder = configFile.getParentFile();
		config = new Configuration(configFile);

		config.load();
		syncConfigs();
	}
	
	private void syncConfigs() {
		
		pGlowshroomGen = config.get("Glowshroom", "glowshroom generation", true, "Generate Glowshroom in the overworld [false/true]").getBoolean(true);
		pSpawnRate_Glowshroom = config.get("Glowshroom", "glowshroom spawn rate", 20, "Set the spawn rate of Glowshroom [0-100]", 0, 100).getInt(20);
		pSpreadRate_Glowshroom = config.get("Glowshroom", "glowshroom spread rate", 100, "Set the spread rate of Glowshroom [0-100]", 0, 100).getInt(100);
		
		pSpawnRate_Lavacow = config.get("Moogma", "lavacow spawn rate", 0, "Set the spawn rate of Moogma [0-10000]", 0, 10000).getInt(0);
		
		pSpawnRate_Foglet = config.get("Foglet", "foglet spawn rate", 20, "Set the spawn rate of Foglet [0-10000]", 0, 10000).getInt(20);
		pFoglet_SpawnAlly = config.get("Foglet", "foglet call reinforcement", true, "Should Foglet call reinforcement when attacking [false/true]").getBoolean(true);
		
		pSpawnRate_Parasite = config.get("Parasite", "parasite spawn rate", 10, "Set the spawn rate of Parasite [0-100]", 0, 100).getInt(10);
		Parasite_SandSpawn = config.get("Parasite", "parasite from sand blocks", 2, "Rate of spawning Parasite when destroying sand blocks in the desert [0-100]", 0, 100).getInt(2);
		Parasite_Plague = config.get("Parasite", "parasite attacks everything", false, "Should Parasite attack ALL livings [false/true]").getBoolean(false);
		Parasite_Attach = config.get("Parasite", "parasite attacks by attaching onto target", true, "Parasite will attack their target by attaching on them [false/true]").getBoolean(true);
		
		pSpawnRate_UndeadSwine = config.get("Undead Swine", "undeadswine spawn rate", 15, "Set the spawn rate of Undead swine [0-10000]", 0, 10000).getInt(15);
		UndeadSwine_DropHeart = config.get(Configuration.CATEGORY_GENERAL, "drop heart", 10, "Set the drop rate of Undying Heart [0-100]", 0, 100).getInt(10);
		UndeadSwine_Health = config.get("Undead Swine", "undeadswine health", 50.0D, "Maximum Undead Swine health [1-1000]", 1, 1000).getDouble(50.0D);
		UndeadSwine_Attack = config.get("Undead Swine", "undeadswine attack", 4.0D, "Undead Swine strength [1-1000]", 1, 1000).getDouble(4.0D);
		
		pSpawnRate_ZombieMushroom = config.get("Ciuperca", "zombiemushroom spawn rate", 40, "Set the spawn rate of Ciuperca [0-10000]", 0, 10000).getInt(40);
		ZombieMushroom_DropSpore =config.get(Configuration.CATEGORY_GENERAL, "zombiemushroom drop spore", 2, "Set the drop rate of Poisonous Spore [0-100]", 0, 100).getInt(2);
				
		pSpawnRate_ZombieFrozen = config.get("Ingheta", "zombiefrozen spawn rate", 20, "Set the spawn rate of Ingheta [0-10000]", 0, 10000).getInt(20);
		
		pSpawnRate_Salamander = config.get("Salamander", "salamander spawn rate", 30, "Set the spawn rate of Salamander [0-10000]", 0, 10000).getInt(30);
		Salamander_Health = config.get("Salamander", "salamander health", 60.0D, "Maximum Salamander health [1-1000]", 1, 1000).getDouble(60.0D);
		Salamander_Attack = config.get("Salamander", "salamander attack", 4.0D, "Salamander strength [1-1000]", 1, 1000).getDouble(4.0D);
		
		pSpawnRate_Wendigo = config.get("Ithaqua", "ithaqua spawn rate", 15, "Set the spawn rate of Ithaqua [0-10000]", 0, 10000).getInt(15);
		Wendigo_AnimalAttack = config.get("Ithaqua", "ithaqua attacks animals", true, "Should Ithaqua attack innocent animals [false/true]").getBoolean(true);
		Wendigo_Health = config.get("Ithaqua", "ithaqua health", 60.0D, "Maximum Ithaqua health [1-1000]", 1, 1000).getDouble(60.0D);
		Wendigo_Attack = config.get("Ithaqua", "ithaqua attack", 8.0D, "Ithaqua strength [1-1000]", 1, 1000).getDouble(8.0D);
		
		pSpawnRate_Mimic = config.get("Mimicrab", "mimicrab spawn rate", 20, "Set the spawn rate of Mimic [0-10000]", 0, 10000).getInt(20);
		//Mimic_noGUI = config.get(Configuration.CATEGORY_GENERAL, "mimicrab open GUI with right-click when sneaking", true, "Right-clicking when sneaking will still open Mimic's GUI [false/true]").getBoolean(true);
		
		pSpawnRate_SludgeLord = config.get("Sludge Lord", "sludge lord spawn rate", 15, "Set the spawn rate of Sludge Lord [0-10000]", 0, 10000).getInt(15);
		SludgeLord_Health = config.get("Sludge Lord", "sludge lord health", 70.0D, "Maximum Sludge Lord health [1-1000]", 1, 1000).getDouble(70.0D);
		SludgeLord_Attack = config.get("Sludge Lord", "sludge lord attack", 5.0D, "Sludge Lord strength [1-1000]", 1, 1000).getDouble(5.0D);
		SludgeLord_Ability_Num = config.get("Sludge Lord", "sludge lord summon number", 3, "Set the number of Lil'Sludge summoned per cast [0-100]", 0, 100).getInt(3);
		SludgeLord_Ability_Max = config.get("Sludge Lord", "sludge lord summon max", 8, "Set the max number of Lil'Sludge summoned [0-100]", 0, 100).getInt(8);
		SludgeLord_Ability_Cooldown = config.get("Sludge Lord", "sludge lord summon cooldown", 17, "Set the cooldown of summoning Lil'Sludge [0-100]", 0, 100).getInt(17);
		
		LilSludge_Lifespan = config.get("Lil'Sludge", "lil'sludge lifespan", 60, "Lil'Sludge lifespan [1-10000]", 1, 10000).getInt(60);
		LilSludge_Health = config.get("Lil'Sludge", "lil'sludge health", 20.0D, "Maximum Lil'Sludge health [1-1000]", 1, 1000).getDouble(20.0D);
		LilSludge_Attack = config.get("Lil'Sludge", "lil'sludge attack", 3.0D, "Lil'Sludge strength [1-1000]", 1, 1000).getDouble(3.0D);
		
		pSpawnRate_Raven = config.get("Raven", "raven spawn rate", 8, "Set the spawn rate of Raven [0-10000]", 0, 10000).getInt(8);
		Raven_Perch = config.get("Raven", "raven perching", true, "Should tamed Raven perch on owner's head [false/true]").getBoolean(true);
		Raven_Slowfall = config.get("Raven", "raven slow down falling", true, "Should perching Raven slow down owner's falling speed [false/true]").getBoolean(true);
		
		pSpawnRate_Ptera = config.get("Ptera", "ptera spawn rate", 20, "Set the spawn rate of Ptera [0-10000]", 0, 10000).getInt(20);
		Ptera_Health = config.get("Ptera", "ptera health", 10.0D, "Maximum Ptera health [1-1000]", 1, 1000).getDouble(10.0D);
		Ptera_Attack = config.get("Ptera", "ptera attack", 3.0D, "Ptera strength [1-1000]", 1, 1000).getDouble(3.0D);
		
		pSpawnRate_Vespa = config.get("Vespa", "vespa spawn rate", 20, "Set the spawn rate of Vespa [0-100]", 0, 100).getInt(20);
		Vespa_Health = config.get("Vespa", "vespa health", 20.0D, "Maximum Vespa health [1-1000]", 1, 1000).getDouble(20.0D);
		Vespa_Attack = config.get("Vespa", "vespa attack", 5.0D, "Vespa strength [1-1000]", 1, 1000).getDouble(5.0D);
		
		pSpawnRate_Scarecrow = config.get("Scarecrow", "scarecrow spawn rate", 15, "Set the spawn rate of Scarecrow [0-100]", 0, 100).getInt(15);
		Scarecrow_Health = config.get("Scarecrow", "scarecrow health", 40.0D, "Maximum Scarecrow health [1-1000]", 1, 1000).getDouble(40.0D);
		Scarecrow_Attack = config.get("Scarecrow", "scarecrow attack", 8.0D, "Scarecrow strength [1-1000]", 1, 1000).getDouble(8.0D);
		pScarecrow_PlagueDoctor = config.get("Scarecrow", "plague doctor scarecrow spawn rate", 30, "Set the spawn rate of Plague Doctor Scarecrow when a Raven was killed. [0-100]", 0, 100).getInt(30);
		
		pSpawnRate_ZombiePiranha = config.get("Swarmer", "swarmer spawn rate", 40, "Set the spawn rate of Swarmer [0-100]", 0, 100).getInt(40);
		Piranha_AnimalAttack = config.get(Configuration.CATEGORY_GENERAL, "piranha attacks animals", true, "Should Piranha and Swarmer attack innocent animals [false/true]").getBoolean(true);
		
		pSpawnRate_Piranha = config.get("Piranha", "piranha spawn rate", 80, "Set the spawn rate of Piranha [0-100]", 0, 100).getInt(80);
		
		pSpawnRate_BoneWorm = config.get("Osvermis", "boneworm spawn rate", 20, "Set the spawn rate of OsVermis [0-100]", 0, 100).getInt(20);
		BoneWorm_Health = config.get("Osvermis", "boneworm health", 32.0D, "Maximum OsVermis health [1-1000]", 1, 1000).getDouble(32.0D);
		BoneWorm_Attack = config.get("Osvermis", "boneworm attack", 6.0D, "OsVermis strength [1-1000]", 1, 1000).getDouble(6.0D);
		BoneWorm_DropHeart = config.get(Configuration.CATEGORY_GENERAL, "boneworm drop heart", 10, "Set the drop rate of Acidic Heart [0-100]", 0, 100).getInt(10);
		
		pSpawnRate_Pingu = config.get("Pingu", "pingu spawn rate", 20, "Set the spawn rate of Penghoul [0-100]", 0, 100).getInt(20);
		
		pSpawnRate_Undertaker = config.get("Undertaker", "undertaker spawn rate", 8, "Set the spawn rate of Undertaker [0-100]", 0, 100).getInt(8);
		Undertaker_Health = config.get("Undertaker", "undertaker health", 40.0D, "Maximum Undertaker health [1-1000]", 1, 1000).getDouble(40.0D);
		Undertaker_Attack = config.get("Undertaker", "undertaker attack", 6.0D, "Undertaker strength [1-1000]", 1, 1000).getDouble(6.0D);
		Undertaker_Ability_Num = config.get("Undertaker", "undertaker summon number", 4, "Set the number of Unburied summoned per cast [0-100]", 0, 100).getInt(4);
		Undertaker_Ability_Max = config.get("Undertaker", "undertaker summon max", 4, "Set the max number of Unburied summoned [0-100]", 0, 100).getInt(4);
		Undertaker_Ability_Cooldown = config.get("Undertaker", "undertaker summon cooldown", 15, "Set the cooldown of summoning Unburied [0-100]", 0, 100).getInt(15);
		
		Unburied_Lifespan = config.get("Unburied", "unburied lifespan", 20, "Unburied lifespan [1-10000]", 1, 10000).getInt(20);
		Unburied_Health = config.get("Unburied", "unburied health", 20.0D, "Maximum Unburied health [1-1000]", 1, 1000).getDouble(20.0D);
		Unburied_Attack = config.get("Unburied", "unburied attack", 3.0D, "Unburied strength [1-1000]", 1, 1000).getDouble(3.0D);
		
		pSpawnRate_GhostRay = config.get("Ghost Ray", "ghost ray spawn rate", 10, "Set the spawn rate of Ghost Ray [0-100]", 0, 100).getInt(10);
		GhostRay_Health = config.get("Ghost Ray", "ghost ray health", 20.0D, "Maximum Unburied health [1-1000]", 1, 1000).getDouble(20.0D);
		
		pSpawnRate_Banshee = config.get("Banshee", "banshee spawn rate", 20, "Set the spawn rate of Banshee [0-100]", 0, 100).getInt(20);
		Banshee_Health = config.get("Banshee", "banshee health", 34.0D, "Maximum Banshee health [1-1000]", 1, 1000).getDouble(34.0D);
		Banshee_Attack = config.get("Banshee", "banshee attack", 7.0D, "Banshee strength [1-1000]", 1, 1000).getDouble(7.0D);
		
		pSpawnRate_Weta = config.get("Weta", "weta spawn rate", 30, "Set the spawn rate of Weta [0-100]", 0, 100).getInt(30);
		
		pSpawnRate_Avaton = config.get("Avaton", "avaton spawn rate", 20, "Set the spawn rate of Avaton [0-100]", 0, 100).getInt(20);
		Avaton_Health = config.get("Avaton", "avaton health", 30.0D, "Maximum Avaton health [1-1000]", 1, 1000).getDouble(30.0D);
		Avaton_Attack = config.get("Avaton", "avaton attack", 5.0D, "Avaton strength [1-1000]", 1, 1000).getDouble(5.0D);
		Avaton_Ability_Num = config.get("Avaton", "avaton summon number", 2, "Set the number of Weta summoned per cast [0-100]", 0, 100).getInt(2);
		Avaton_Ability_Max = config.get("Avaton", "avaton summon max", 16, "Set the max number of Weta summoned [0-100]", 0, 100).getInt(16);
		Avaton_Ability_Cooldown = config.get("Avaton", "avaton summon cooldown", 8, "Set the cooldown of summoning Weta [0-100]", 0, 100).getInt(8);
		
		MoltenHammer_PVP = config.get(Configuration.CATEGORY_GENERAL, "allow molten hammer pvp", false, "Allow Molten Hammer active effect to hit players [false/true]").getBoolean(false);
		Fission_ModEntity = config.get(Configuration.CATEGORY_GENERAL, "fission potion works on entities from other mods", false, "Allow Potion of Fission to be used on entites from other mods [false/true]").getBoolean(false);
		//ModBiomes_spawn = config.get(Configuration.CATEGORY_GENERAL, "spawn mobs at biomes from other mods", false, "Allow Mobs to spawn at biomes from other mods [false/true]").getBoolean(false);
		General_Intestine = config.get(Configuration.CATEGORY_GENERAL, "entity drop intestine", 4, "Set the drop rate of Intestine [0-100]", 0, 100).getInt(4);
		Intestine_lt = config.getStringList("loot table for intestine", Configuration.CATEGORY_GENERAL, 
				new String[] {	"minecraft:slime_ball,0.4",
								"minecraft:dye,0.4,15",
								"mod_lavacow:sharptooth,0.1",
								"minecraft:beetroot_seeds,0.1",
								"minecraft:wheat_seeds,0.1",
								"minecraft:melon_seeds,0.1",
								"minecraft:pumpkin_seeds,0.1",
								"minecraft:clay_ball,0.1",
								"minecraft:gold_nugget,0.05",
								"minecraft:iron_nugget,0.05",
								"minecraft:diamond,0.01"},
				"Customize Items and their drop rates for the Intestine. Ex. \"minecraft:slime_ball,0.4\" or \"mod_lavacow:sharptooth,0.1\"");
		Intestine_banlist = config.getStringList("mobs that intestine should not drop from", Configuration.CATEGORY_GENERAL,
				new String[] {	"minecraft:blaze",
					      		"minecraft:slime",
					      		"minecraft:skeleton"},
				"Customize the banlist for which mobs that intestines shouldn't drop from. Ex. \"minecraft:slime\" or \"mod_lavacow:vespa\"");
		
		GoldenHeart_dur = config.get(Configuration.CATEGORY_GENERAL, "golden heart duribility", 250, "Set the duribility of Golden Heart, 0 = Infinite [0-10000]", 0, 10000).getInt(250);
		GoldenHeart_bl = config.getStringList("blacklisted items from golden heart", Configuration.CATEGORY_GENERAL, new String[0], "Blacklist for items that Golden Heart are unable to mend. Ex. \"minecraft:shears\" or \"mod_lavacow:moltenhammer\"");
		
		FlyingHeight_limit = config.get(Configuration.CATEGORY_GENERAL, "flying height limit", 16, "Set the height limit to X blocks above the ground for flyers, 0 = Infinite [0-100]", 0, 100).getInt(16);
		
		BoneSword_Damage = config.get(Configuration.CATEGORY_GENERAL, "bonesword bonus damage", 5, "Set the bonus damage of Bone Sword to X% [0-100]", 0, 100).getInt(5);
		
		HaloNecklace_Damage = config.get(Configuration.CATEGORY_GENERAL, "halo necklace bonus damage", 10, "Set the bonus damage of Halo Necklace to X% [0-10000]", 0, 10000).getInt(10);
		
		Shattered_Ice = config.get(Configuration.CATEGORY_GENERAL, "allow shattered ice drop", true, "Allow Shattered Ice to drop when an ice block is broken. [false/true]").getBoolean(true);
		
		DreamCatcher_spawn = config.getStringList("spawn list for dreamcatcher", Configuration.CATEGORY_GENERAL, 
				new String[] {	"mod_lavacow:foglet,40,1,2",
								"mod_lavacow:undeadswine,20,1,1",
								"mod_lavacow:ithaqua,20,1,1",
								"mod_lavacow:sludgelord,20,1,1",
								"mod_lavacow:vespa,20,1,1",
								"mod_lavacow:scarecrow,20,1,1",
								"mod_lavacow:boneworm,20,1,1",
								"mod_lavacow:pingu,40,4,8",
								"mod_lavacow:undertaker,20,1,1",
								"mod_lavacow:banshee,20,1,1",
								"mod_lavacow:avaton,20,1,1"},
				"Customize the Spawn list for the Dreamcatcher. Ex. \"mod_lavacow:foglet,40,1,2\" or \"mod_lavacow:vespa,20,1,1\"");
		
		Potion_Enable = config.get(Configuration.CATEGORY_GENERAL, "enable brewing recipe", true, "Adding new brewing recipe (existing property will be preserved). [false/true]").getBoolean(true);
		Enchantment_Enable = config.get(Configuration.CATEGORY_GENERAL, "enable enchantment", true, "Adding new enchantment (existing property will be preserved). [false/true]").getBoolean(true);
		
		SludgeWand_Cooldown = config.get(Configuration.CATEGORY_GENERAL, "pestilence cooldown", 60, "Ability cooldown of \"Pestilence\" [1-10000]", 1, 10000).getInt(60);
		Undertaker_Shovel_Cooldown = config.get(Configuration.CATEGORY_GENERAL, "midnight mourne cooldown", 60, "Ability cooldown of Midnight Mourne [1-10000]", 1, 10000).getInt(60);
		
		Tinkers_Compat = config.get(Configuration.CATEGORY_GENERAL, "tinkers compatibility", true, "Adding new materials to Tinkers Construct. [false/true]").getBoolean(true);
		SunScreen_Mode = config.get(Configuration.CATEGORY_GENERAL, "sunscreen mode", false, "Mobs in this mod will not burn under daylight. [false/true]").getBoolean(false);
		
		SpawnRate_Cemetery = config.get(Configuration.CATEGORY_GENERAL, "cemetery spawn rate", 1000, "Spawn rate of Cemetery (higher number = less frequent) [1-10000]", 1, 10000).getInt(1000);
		
		BoneSword_DamageCap = config.get(Configuration.CATEGORY_GENERAL, "bonesword bonus damage cap", 10000, "Set the bonus damage cap of Bone Sword [0-10000]", 0, 10000).getInt(10000);
		
		Spawn_AllowList = config.get(Configuration.CATEGORY_GENERAL, "mob spawn allow dimensions", new int[]{DimensionType.OVERWORLD.getId(), DimensionType.NETHER.getId(), DimensionType.THE_END.getId()}, "All mobs are only allowed to spawn in these dimensions' IDs").getIntList();
		
		Suicidal_Minion = config.get(Configuration.CATEGORY_GENERAL, "suicidal minion", true, "Entities summoned by other mobs die when their summoner dies. [false/true]").getBoolean(true);
		
		if (config.hasChanged())
			config.save();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (mod_LavaCow.MODID.equals(event.getModID())) {
			syncConfigs();
		}
	}
	
	/*private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) 
	{
		if(loadFromConfigFile)
			config.load();	
		
		Property GlowshroomGen = config.get(GENERAL, "glowshroom generation", true);
		GlowshroomGen.setLanguageKey("config.mod_lavacow.glowshroomgen");
		GlowshroomGen.setComment("Generate glowshroom in the overworld [false/true|true]");
		Property SpawnRate_Lavacow = config.get(GENERAL, "lavacow spawn rate", 100);
		SpawnRate_Lavacow.setLanguageKey("config.mod_lavacow.spawn.lavacow");
		SpawnRate_Lavacow.setComment("Set the spawn rate of lavacow [0-100]");
		SpawnRate_Lavacow.setMinValue(0);
		SpawnRate_Lavacow.setMaxValue(100);
		Property SpawnRate_Foglet = config.get(GENERAL, "foglet spawn rate", 100);
		SpawnRate_Foglet.setLanguageKey("config.mod_lavacow.spawn.foglet");
		SpawnRate_Foglet.setComment("Set the spawn rate of foglet [0-100]");
		SpawnRate_Foglet.setMinValue(0);
		SpawnRate_Foglet.setMaxValue(100);
		Property SpawnRate_Parasite = config.get(GENERAL, "parasite spawn rate", 100);
		SpawnRate_Parasite.setLanguageKey("config.mod_lavacow.spawn.parasite");
		SpawnRate_Parasite.setComment("Set the spawn rate of parasite [0-100]");
		SpawnRate_Parasite.setMinValue(0);
		SpawnRate_Parasite.setMaxValue(100);
		Property SpawnRate_UndeadSwine = config.get(GENERAL, "undeadswine spawn rate", 100);
		SpawnRate_UndeadSwine.setLanguageKey("config.mod_lavacow.spawn.undeadswine");
		SpawnRate_UndeadSwine.setComment("Set the spawn rate of undead swine [0-100]");
		SpawnRate_UndeadSwine.setMinValue(0);
		SpawnRate_UndeadSwine.setMaxValue(100);
		Property SpawnRate_ZombieMushroom = config.get(GENERAL, "zombiemushroom spawn rate", 100);
		SpawnRate_ZombieMushroom.setLanguageKey("config.mod_lavacow.spawn.zombiemushroom");
		SpawnRate_ZombieMushroom.setComment("Set the spawn rate of cordy [0-100]");
		SpawnRate_ZombieMushroom.setMinValue(0);
		SpawnRate_ZombieMushroom.setMaxValue(100);
		Property SpawnRate_ZombieFrozen = config.get(GENERAL, "zombiefrozen spawn rate", 100);
		SpawnRate_ZombieFrozen.setLanguageKey("config.mod_lavacow.spawn.zombiefrozen");
		SpawnRate_ZombieFrozen.setComment("Set the spawn rate of refriger [0-100]");
		SpawnRate_ZombieFrozen.setMinValue(0);
		SpawnRate_ZombieFrozen.setMaxValue(100);
		
		List<String> propertyOrder = new ArrayList<String>();
		propertyOrder.add(GlowshroomGen.getName());
		propertyOrder.add(SpawnRate_Lavacow.getName());
		propertyOrder.add(SpawnRate_Foglet.getName());
		propertyOrder.add(SpawnRate_Parasite.getName());
		propertyOrder.add(SpawnRate_UndeadSwine.getName());
		propertyOrder.add(SpawnRate_ZombieMushroom.getName());
		propertyOrder.add(SpawnRate_ZombieFrozen.getName());
		config.setCategoryPropertyOrder(GENERAL, propertyOrder);
		
		if(readFieldsFromConfig) {
			pGlowshroomGen = GlowshroomGen.getBoolean();
			pSpawnRate_Lavacow = SpawnRate_Lavacow.getInt();
			pSpawnRate_Foglet = SpawnRate_Foglet.getInt();
			pSpawnRate_Parasite = SpawnRate_Parasite.getInt();
			pSpawnRate_UndeadSwine = SpawnRate_UndeadSwine.getInt();
			pSpawnRate_ZombieMushroom = SpawnRate_ZombieMushroom.getInt();
			pSpawnRate_ZombieFrozen = SpawnRate_ZombieFrozen.getInt();
		}
		
		GlowshroomGen.set(pGlowshroomGen);
		SpawnRate_Lavacow.set(pSpawnRate_Lavacow);	
		SpawnRate_Foglet.set(pSpawnRate_Foglet);
		SpawnRate_Parasite.set(pSpawnRate_Parasite);
		SpawnRate_UndeadSwine.set(pSpawnRate_UndeadSwine);
		SpawnRate_ZombieMushroom.set(pSpawnRate_ZombieMushroom);
		SpawnRate_ZombieFrozen.set(pSpawnRate_ZombieFrozen);
		
		if(config.hasChanged())
			config.save();
	}*/
}


