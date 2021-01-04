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
	
	public static boolean pGlowshroomGen;
	
	public static int pSpawnRate_Lavacow;
	public static double Lavacow_Health;
	
	public static int pSpawnRate_Foglet;
	public static double Foglet_Health;
	public static double Foglet_Attack;
	
	public static int pSpawnRate_Parasite;
	public static double Parasite_Health;
	public static double Parasite_Attack;
	public static String[] Parasite_Hostlist = new String[0];
	
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
	
	public static int pSpawnRate_Wendigo;
	public static double Wendigo_Health;
	public static double Wendigo_Attack;
	
	public static int pSpawnRate_Mimic;
	public static double Mimic_Health;
	public static double Mimic_Attack;
	
	public static int pSpawnRate_SludgeLord;
	public static double SludgeLord_Health;
	public static double SludgeLord_Attack;
	public static int SludgeLord_Ability_Num;
	public static int SludgeLord_Ability_Max;
	public static int SludgeLord_Ability_Cooldown;
	
	public static int pSpawnRate_Raven;
	public static double Raven_Health;
	public static boolean Raven_Perch;
	public static boolean Raven_Slowfall;
	
	public static int pSpawnRate_Ptera;
	public static double Ptera_Health;
	public static double Ptera_Attack;
	
	public static int pSpawnRate_Vespa;
	public static double Vespa_Health;
	public static double Vespa_Attack;
	
	public static int pSpawnRate_Scarecrow;
	public static double Scarecrow_Health;
	public static double Scarecrow_Attack;
	
	public static int pSpawnRate_ZombiePiranha;
	public static double ZombiePiranha_Health;
	public static double ZombiePiranha_Attack;
	
	public static int pSpawnRate_Piranha;
	public static double Piranha_Health;
	public static double Piranha_Attack;
	
	public static int pSpawnRate_BoneWorm;
	public static double BoneWorm_Health;
	public static double BoneWorm_Attack;
	
	public static int pSpawnRate_Pingu;
	public static double Pingu_Health;
	public static double Pingu_Attack;
	
	public static int pSpawnRate_Undertaker;
	public static double Undertaker_Health;
	public static double Undertaker_Attack;
	public static int Undertaker_Ability_Num;
	public static int Undertaker_Ability_Max;
	public static int Undertaker_Ability_Cooldown;
	
	public static int pSpawnRate_GhostRay;
	public static double GhostRay_Health;
	
	public static int pSpawnRate_Banshee;
	public static double Banshee_Health;
	public static double Banshee_Attack;
	public static double Banshee_Ability_Radius;
	
	public static int pSpawnRate_Weta;
	public static double Weta_Health;
	public static double Weta_Attack;
	
	public static int pSpawnRate_Avaton;
	public static double Avaton_Health;
	public static double Avaton_Attack;
	public static int Avaton_Ability_Num;
	public static int Avaton_Ability_Max;
	public static int Avaton_Ability_Cooldown;
	
	public static double LilSludge_Health;
	public static double LilSludge_Attack;
	public static int LilSludge_Lifespan;
	
	public static double Unburied_Health;
	public static double Unburied_Attack;
	public static int Unburied_Lifespan;
	
	public static boolean pFoglet_SpawnAlly;
	public static boolean MoltenHammer_PVP;
	public static int Parasite_SandSpawn;
	public static boolean Parasite_Plague;
	public static boolean Wendigo_AnimalAttack;
	public static boolean Fission_ModEntity;
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
	public static boolean Tinkers_Compat;
	public static boolean SunScreen_Mode;
	public static int SpawnRate_Cemetery;
	public static int BoneSword_DamageCap;
	public static int[] Spawn_AllowList = new int[0];
	public static boolean Suicidal_Minion;
	public static int DreamCatcher_dur;	
	public static boolean Potion_Enable;
	public static boolean Enchantment_Enable;
	public static int MootenHeart_Damage;
	public static int[] Spawn_Cemetery_AllowList = new int[0];
	
	public final String[] usedCategories = { Configuration.CATEGORY_GENERAL, "Avaton", "Banshee", "Foglet", "Frigid", "Ghost Ray", "Ithaqua", "Lil'Sludge", "Mimicrab", "Moogma", 
			"Mycosis", "Osvermis", "Parasite", "Penghoul", "Piranha", "Ptera", "Raven", "Salamander", "Scarecrow", "Sludge Lord", "Swarmer", "Unburied", "Undead Swine", "Undertaker", 
			"Vespa", "Weta", "Glowshroom"};
	
	public void loadConfig(FMLPreInitializationEvent event) {
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
		
		pSpawnRate_Lavacow = config.get("Moogma", "moogma spawn rate", 0, "Set the spawn rate of Moogma [0-10000]", 0, 10000).getInt(0);
		Lavacow_Health = config.get("Moogma", "moogma health", 10.0D, "Maximum Moogma health [1-1000]", 1, 1000).getDouble(10.0D);
		
		pSpawnRate_Foglet = config.get("Foglet", "foglet spawn rate", 20, "Set the spawn rate of Foglet [0-10000]", 0, 10000).getInt(20);
		Foglet_Health = config.get("Foglet", "foglet health", 16.0D, "Maximum Foglet health [1-1000]", 1, 1000).getDouble(16.0D);
		Foglet_Attack = config.get("Foglet", "foglet attack", 2.0D, "Foglet strength [1-1000]", 1, 1000).getDouble(2.0D);
		pFoglet_SpawnAlly = config.get("Foglet", "foglet call reinforcement", true, "Should Foglet call reinforcement when attacking [false/true]").getBoolean(true);
		
		pSpawnRate_Parasite = config.get("Parasite", "parasite spawn rate", 10, "Set the spawn rate of Parasite [0-100]", 0, 100).getInt(10);
		Parasite_Health = config.get("Parasite", "parasite health", 6.0D, "Maximum Parasite health [1-1000]", 1, 1000).getDouble(6.0D);
		Parasite_Attack = config.get("Parasite", "parasite attack", 1.0D, "Parasite strength [1-1000]", 1, 1000).getDouble(1.0D);
		Parasite_SandSpawn = config.get("Parasite", "parasite from sand blocks", 2, "Rate of spawning Parasite when destroying sand blocks in the desert [0-100]", 0, 100).getInt(2);
		Parasite_Plague = config.get("Parasite", "parasite attacks everything", false, "Should Parasite attack ALL livings [false/true]").getBoolean(false);
		Parasite_Attach = config.get("Parasite", "parasite attacks by attaching onto target", true, "Parasite will attack their target by attaching on them [false/true]").getBoolean(true);
		Parasite_Hostlist = config.getStringList("available host for parasite", "Parasite", 
				new String[] {
						"minecraft:zombie",
						"mod_lavacow:zombiefrozen",
						"mod_lavacow:zombiemushroom"},
		"Allow Parasite to spawn from listed mob. Ex. \"minecraft:zombie\" or \"mod_lavacow:zombiefrozen\"");
		
		pSpawnRate_UndeadSwine = config.get("Undead Swine", "undeadswine spawn rate", 15, "Set the spawn rate of Undead swine [0-10000]", 0, 10000).getInt(15);
		UndeadSwine_DropHeart = config.get(Configuration.CATEGORY_GENERAL, "drop heart", 10, "Set the drop rate of Undying Heart [0-100]", 0, 100).getInt(10);
		UndeadSwine_Health = config.get("Undead Swine", "undeadswine health", 50.0D, "Maximum Undead Swine health [1-1000]", 1, 1000).getDouble(50.0D);
		UndeadSwine_Attack = config.get("Undead Swine", "undeadswine attack", 4.0D, "Undead Swine strength [1-1000]", 1, 1000).getDouble(4.0D);
		
		pSpawnRate_ZombieMushroom = config.get("Mycosis", "mycosis spawn rate", 40, "Set the spawn rate of Mycosis [0-10000]", 0, 10000).getInt(40);
		ZombieMushroom_Health = config.get("Mycosis", "mycosis health", 20.0D, "Maximum Mycosis health [1-1000]", 1, 1000).getDouble(20.0D);
		ZombieMushroom_Attack = config.get("Mycosis", "mycosis attack", 3.0D, "Mycosis strength [1-1000]", 1, 1000).getDouble(3.0D);
		ZombieMushroom_DropSpore =config.get(Configuration.CATEGORY_GENERAL, "drop poisonous spore", 2, "Set the drop rate of Poisonous Spore [0-100]", 0, 100).getInt(2);
				
		pSpawnRate_ZombieFrozen = config.get("Frigid", "frigid spawn rate", 20, "Set the spawn rate of Frigid [0-10000]", 0, 10000).getInt(20);
		ZombieFrozen_Health = config.get("Frigid", "frigid health", 20.0D, "Maximum Frigid health [1-1000]", 1, 1000).getDouble(30.0D);
		ZombieFrozen_Attack = config.get("Frigid", "frigid attack", 3.0D, "Frigid strength [1-1000]", 1, 1000).getDouble(3.0D);
		
		pSpawnRate_Salamander = config.get("Salamander", "salamander spawn rate", 30, "Set the spawn rate of Salamander [0-10000]", 0, 10000).getInt(30);
		Salamander_Health = config.get("Salamander", "salamander health", 60.0D, "Maximum Salamander health [1-1000]", 1, 1000).getDouble(60.0D);
		Salamander_Attack = config.get("Salamander", "salamander attack", 4.0D, "Salamander strength [1-1000]", 1, 1000).getDouble(4.0D);
		
		pSpawnRate_Wendigo = config.get("Ithaqua", "ithaqua spawn rate", 15, "Set the spawn rate of Ithaqua [0-10000]", 0, 10000).getInt(15);
		Wendigo_AnimalAttack = config.get("Ithaqua", "ithaqua attacks animals", true, "Should Ithaqua attack innocent animals [false/true]").getBoolean(true);
		Wendigo_Health = config.get("Ithaqua", "ithaqua health", 60.0D, "Maximum Ithaqua health [1-1000]", 1, 1000).getDouble(60.0D);
		Wendigo_Attack = config.get("Ithaqua", "ithaqua attack", 8.0D, "Ithaqua strength [1-1000]", 1, 1000).getDouble(8.0D);
		
		pSpawnRate_Mimic = config.get("Mimicrab", "mimicrab spawn rate", 20, "Set the spawn rate of Mimicrab [0-10000]", 0, 10000).getInt(20);
		Mimic_Health = config.get("Mimicrab", "mimicrab health", 10.0D, "Maximum Mimicrab health [1-1000]", 1, 1000).getDouble(10.0D);
		Mimic_Attack = config.get("Mimicrab", "mimicrab attack", 8.0D, "Mimicrab strength [1-1000]", 1, 1000).getDouble(8.0D);
		
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
		Raven_Health = config.get("Raven", "raven health", 6.0D, "Maximum Raven health [1-1000]", 1, 1000).getDouble(6.0D);
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
		ZombiePiranha_Health = config.get("Swarmer", "swarmer health", 8.0D, "Maximum Swarmer health [1-1000]", 1, 1000).getDouble(8.0D);
		ZombiePiranha_Attack = config.get("Swarmer", "swarmer attack", 1.0D, "Swarmer strength [1-1000]", 1, 1000).getDouble(1.0D);
		Piranha_AnimalAttack = config.get(Configuration.CATEGORY_GENERAL, "piranha attacks animals", true, "Should Piranha and Swarmer attack innocent animals [false/true]").getBoolean(true);
		
		pSpawnRate_Piranha = config.get("Piranha", "piranha spawn rate", 80, "Set the spawn rate of Piranha [0-100]", 0, 100).getInt(80);
		Piranha_Health = config.get("Piranha", "piranha health", 3.0D, "Maximum Piranha health [1-1000]", 1, 1000).getDouble(3.0D);
		Piranha_Attack = config.get("Piranha", "piranha attack", 1.0D, "Piranha strength [1-1000]", 1, 1000).getDouble(1.0D);
		
		pSpawnRate_BoneWorm = config.get("Osvermis", "boneworm spawn rate", 20, "Set the spawn rate of OsVermis [0-100]", 0, 100).getInt(20);
		BoneWorm_Health = config.get("Osvermis", "boneworm health", 32.0D, "Maximum OsVermis health [1-1000]", 1, 1000).getDouble(32.0D);
		BoneWorm_Attack = config.get("Osvermis", "boneworm attack", 6.0D, "OsVermis strength [1-1000]", 1, 1000).getDouble(6.0D);
		BoneWorm_DropHeart = config.get(Configuration.CATEGORY_GENERAL, "boneworm drop heart", 10, "Set the drop rate of Acidic Heart [0-100]", 0, 100).getInt(10);
		
		pSpawnRate_Pingu = config.get("Penghoul", "penghoul spawn rate", 20, "Set the spawn rate of Penghoul [0-100]", 0, 100).getInt(20);
		Pingu_Health = config.get("Penghoul", "penghoul health", 10.0D, "Maximum Penghoul health [1-1000]", 1, 1000).getDouble(10.0D);
		Pingu_Attack = config.get("Penghoul", "penghoul attack", 3.0D, "Penghoul strength [1-1000]", 1, 1000).getDouble(3.0D);
		
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
		GhostRay_Health = config.get("Ghost Ray", "ghost ray health", 20.0D, "Maximum Ghost Ray health [1-1000]", 1, 1000).getDouble(20.0D);
		
		pSpawnRate_Banshee = config.get("Banshee", "banshee spawn rate", 20, "Set the spawn rate of Banshee [0-100]", 0, 100).getInt(20);
		Banshee_Health = config.get("Banshee", "banshee health", 34.0D, "Maximum Banshee health [1-1000]", 1, 1000).getDouble(34.0D);
		Banshee_Attack = config.get("Banshee", "banshee attack", 7.0D, "Banshee strength [1-1000]", 1, 1000).getDouble(7.0D);
		Banshee_Ability_Radius = config.get("Banshee", "banshee scream radius", 3.0D, "Set the effect radius of Banshee scream [1-1000]", 1, 1000).getDouble(3.0D);
		
		pSpawnRate_Weta = config.get("Weta", "weta spawn rate", 30, "Set the spawn rate of Weta [0-100]", 0, 100).getInt(30);
		Weta_Health = config.get("Weta", "weta health", 12.0D, "Maximum Weta health [1-1000]", 1, 1000).getDouble(12.0D);
		Weta_Attack = config.get("Weta", "weta attack", 1.0D, "Weta strength [1-1000]", 1, 1000).getDouble(1.0D);
		
		pSpawnRate_Avaton = config.get("Avaton", "avaton spawn rate", 20, "Set the spawn rate of Avaton [0-100]", 0, 100).getInt(20);
		Avaton_Health = config.get("Avaton", "avaton health", 30.0D, "Maximum Avaton health [1-1000]", 1, 1000).getDouble(30.0D);
		Avaton_Attack = config.get("Avaton", "avaton attack", 5.0D, "Avaton strength [1-1000]", 1, 1000).getDouble(5.0D);
		Avaton_Ability_Num = config.get("Avaton", "avaton summon number", 2, "Set the number of Weta summoned per cast [0-100]", 0, 100).getInt(2);
		Avaton_Ability_Max = config.get("Avaton", "avaton summon max", 16, "Set the max number of Weta summoned [0-100]", 0, 100).getInt(16);
		Avaton_Ability_Cooldown = config.get("Avaton", "avaton summon cooldown", 8, "Set the cooldown of summoning Weta [0-100]", 0, 100).getInt(8);
		
		MoltenHammer_PVP = config.get(Configuration.CATEGORY_GENERAL, "allow molten hammer pvp", false, "Allow Molten Hammer active effect to hit players [false/true]").getBoolean(false);
		Fission_ModEntity = config.get(Configuration.CATEGORY_GENERAL, "fission potion works on entities from other mods", false, "Allow Potion of Fission to be used on entites from other mods [false/true]").getBoolean(false);
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
								"mod_lavacow:chitin,0.1",
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
		GoldenHeart_bl = config.getStringList("banlisted items from golden heart", Configuration.CATEGORY_GENERAL, new String[0], "BlackBanlist for items that Golden Heart are unable to mend. Ex. \"minecraft:shears\" or \"mod_lavacow:moltenhammer\"");
		
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
		DreamCatcher_dur = config.get(Configuration.CATEGORY_GENERAL, "dreamcatcher duribility", 80, "Set the duribility of Dreamcatcher, 0 = Infinite [0-10000]", 0, 10000).getInt(80);
		
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
		
		MootenHeart_Damage = config.get(Configuration.CATEGORY_GENERAL, "molten heart damage reduction", 20, "Set the fire damage reduction of Molten Heart to X% [0-10000]", 0, 10000).getInt(20);
		
		Spawn_Cemetery_AllowList = config.get(Configuration.CATEGORY_GENERAL, "cemetery spawn allow dimensions", new int[]{DimensionType.OVERWORLD.getId()}, "Cemetery are only allowed to spawn in these dimensions' IDs").getIntList();
		
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


