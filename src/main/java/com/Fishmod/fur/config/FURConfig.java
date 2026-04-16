package com.Fishmod.fur.config;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

public final class FURConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Lavacow_Health;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Lavacow_Texture;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Foglet_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Foglet_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> pSpawnRate_Parasite;
	public static final ForgeConfigSpec.ConfigValue<Double> Parasite_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Parasite_Attack;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Parasite_Attach;
	public static final ForgeConfigSpec.ConfigValue<Integer> Parasite_Lifespan;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Parasite_Pickup;
	
	public static final ForgeConfigSpec.ConfigValue<Double> UndeadSwine_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> UndeadSwine_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> Mycosis_Lifespan;
	public static final ForgeConfigSpec.ConfigValue<Double> Mycosis_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Mycosis_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> Frigid_Lifespan;
	public static final ForgeConfigSpec.ConfigValue<Double> Frigid_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Frigid_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Salamander_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Salamander_Attack;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Salamander_Defender;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Wendigo_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Wendigo_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Mimic_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Mimic_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> pSpawnRate_DeathMimic;
	
	public static final ForgeConfigSpec.ConfigValue<Double> SludgeLord_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> SludgeLord_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> SludgeLord_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> SludgeLord_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> SludgeLord_Ability_Cooldown;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Raven_Health;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Raven_Perch;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Raven_Slowfall;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Ptera_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Ptera_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ptera_Ability_Chance;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Ptera_Ability_Spawn;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> pEvolveRate_Vespa;
	public static final ForgeConfigSpec.ConfigValue<Double> Vespa_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Vespa_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> pBeeConvertRate_Vespa;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Scarecrow_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Scarecrow_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Swarmer_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Swarmer_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Piranha_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Piranha_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> BoneWorm_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> BoneWorm_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Pingu_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Pingu_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Undertaker_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Undertaker_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Undertaker_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> Undertaker_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> Undertaker_Ability_Cooldown;
	
	public static final ForgeConfigSpec.ConfigValue<Double> GhostRay_Health;

	public static final ForgeConfigSpec.ConfigValue<Double> Banshee_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Banshee_Attack;
	public static final ForgeConfigSpec.ConfigValue<Double> Banshee_Ability_Radius;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Weta_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Weta_Attack;
	public static final ForgeConfigSpec.ConfigValue<Double> Weta_Harvest_Diseased_Wheat;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Avaton_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Avaton_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Avaton_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> Avaton_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> Avaton_Ability_Cooldown;
	
	public static final ForgeConfigSpec.ConfigValue<Double> LilSludge_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> LilSludge_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> LilSludge_Lifespan;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Unburied_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Unburied_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Unburied_Lifespan;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Forsaken_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Forsaken_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> Spawn_SkeletonKing;
	public static final ForgeConfigSpec.ConfigValue<Double> SkeletonKing_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> SkeletonKing_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> SkeletonKing_AbilityA_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> SkeletonKing_AbilityB_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> SkeletonKing_AbilityC_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SkeletonKing_Loot_Option;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> Mummy_Lifespan;
	public static final ForgeConfigSpec.ConfigValue<Double> Mummy_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Mummy_Attack;

	public static final ForgeConfigSpec.ConfigValue<Double> Cactyrant_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Cactyrant_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Cactyrant_Ability_Cooldown;

	public static final ForgeConfigSpec.ConfigValue<Double> Cactoid_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Cactoid_Attack;

	public static final ForgeConfigSpec.ConfigValue<Double> WarpedFirefly_Health;

	public static final ForgeConfigSpec.ConfigValue<Double> Imp_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Imp_Attack;

	public static final ForgeConfigSpec.ConfigValue<Double> SeaHag_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> SeaHag_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> SeaHag_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> SeaHag_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> SeaHag_Ability_Cooldown;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Wisp_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Wisp_ExplosionPower;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Wisp_Tamed_Explosion;
	
	public static final ForgeConfigSpec.ConfigValue<Double> GraveRobber_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> GraveRobber_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> pSpawnRate_GraveRobberGhost;
	public static final ForgeConfigSpec.ConfigValue<Double> GraveRobberGhost_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> GraveRobberGhost_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Wraith_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Wraith_Attack;

	public static final ForgeConfigSpec.ConfigValue<Double> Scarab_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Scarab_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Scarab_Lifespan;

	public static final ForgeConfigSpec.ConfigValue<Double> Beelzebub_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Beelzebub_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Beelzebub_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> Beelzebub_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> Beelzebub_Ability_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Beelzebub_Ability_Cooldown_Mount;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Enigmoth_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Enigmoth_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Enigmoth_Ability_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Enigmoth_Ability_Cooldown_Mount;
	
	public static final ForgeConfigSpec.ConfigValue<Integer> pSpawnRate_Lamprey;
	public static final ForgeConfigSpec.ConfigValue<Double> Lamprey_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Lamprey_Attack;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Lamprey_Attach;
	public static final ForgeConfigSpec.ConfigValue<Integer> Lamprey_Lifespan;

	public static final ForgeConfigSpec.ConfigValue<Double> Ghoul_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Ghoul_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ghoul_targetHPThreshold;

	//public static final ForgeConfigSpec.ConfigValue<Double> Living_Armor_Health;
	//public static final ForgeConfigSpec.ConfigValue<Double> Living_Armor_Attack;
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> MoltenHammer_PVP;
	public static final ForgeConfigSpec.ConfigValue<Double> MoltenHammer_Damage;
	public static final ForgeConfigSpec.ConfigValue<Integer> MoltenHammer_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Double> SoulFireHammer_Damage;
	public static final ForgeConfigSpec.ConfigValue<Integer> SoulFireHammer_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Fission_ModEntity;
	public static final ForgeConfigSpec.ConfigValue<Integer> GoldenHeart_dur;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> GoldenHeart_bl;
	public static final ForgeConfigSpec.ConfigValue<Boolean> GoldenHeart_GrantsRegeneration;
	public static final ForgeConfigSpec.ConfigValue<Boolean> GoldenHeart_RepairsEquipment;
	public static final ForgeConfigSpec.ConfigValue<Integer> FlyingHeight_limit;
	public static final ForgeConfigSpec.ConfigValue<Integer> BoneSword_Damage;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Raven_Loot;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Seagull_Loot;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Spectral_Raven_Loot;
	public static final ForgeConfigSpec.ConfigValue<Integer> pScarecrow_PlagueDoctor;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DreamCatcher_spawn;
	public static final ForgeConfigSpec.ConfigValue<Integer> SludgeWand_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Undertaker_Shovel_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SunScreen_Mode;
	public static final ForgeConfigSpec.ConfigValue<Integer> SpawnRate_Cemetery;
	public static final ForgeConfigSpec.ConfigValue<Integer> BoneSword_DamageCap;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Spawn_AllowList;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Suicidal_Minion;
	public static final ForgeConfigSpec.ConfigValue<Integer> DreamCatcher_dur;	
	public static final ForgeConfigSpec.ConfigValue<Boolean> Potion_Enable;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Enchantment_Enable;
	public static final ForgeConfigSpec.ConfigValue<Integer> MootenHeart_Damage;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Spawn_Cemetery_AllowList;
	public static final ForgeConfigSpec.ConfigValue<Integer> Cemetery_SpawnRate; 
	public static final ForgeConfigSpec.ConfigValue<Integer> SpawnRate_Desert_Tomb; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> BonusVillagerTrades; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> BonusWanderingTraderTrades; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> Generate_Cemetery;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Generate_Desert_Tomb; 
	public static final ForgeConfigSpec.ConfigValue<Integer> General_IllagerNose;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Show_Expire_Death_Messege; 
	public static final ForgeConfigSpec.ConfigValue<Integer> ScarabScepter_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ankh_Scepter_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Fungal_Staff_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Frozen_Grip_Cooldown;
	
	static {		
		BUILDER.push("Moogma");
		Lavacow_Health = BUILDER.comment("Maximum Moogma health [1-1000]").defineInRange("moogma health", 10.0D, 1.0D, 1000.0D);
		Lavacow_Texture = BUILDER.comment("Loading Moogma with classic texture [false/true]").define("moogma classic texture", false);
		BUILDER.pop();
		
		BUILDER.push("Foglet");
		Foglet_Health = BUILDER.comment("Maximum Foglet health [1-1000]").defineInRange("foglet health", 16.0D, 1.0D, 1000.0D);
		Foglet_Attack = BUILDER.comment("Foglet strength [1-1000]").defineInRange("foglet attack", 2.0D, 1.0D, 1000.0D);	
		BUILDER.pop();

		BUILDER.push("Parasite");
		pSpawnRate_Parasite = BUILDER.comment("Set the spawn rate of Parasite [0-100]").defineInRange("parasite spawn rate", 10, 0, 100);
		Parasite_Health = BUILDER.comment("Maximum Parasite health [1-1000]").defineInRange("parasite health", 6.0D, 1.0D, 1000.0D);
		Parasite_Attack = BUILDER.comment("Parasite strength [1-1000]").defineInRange("parasite attack", 1.0D, 1.0D, 1000.0D);
		Parasite_Attach = BUILDER.comment("Parasite will attack their target by attaching on them [false/true]").define("parasite attacks by attaching onto target", true);
		Parasite_Lifespan = BUILDER.comment("The amount of seconds before parasites naturally die or form into cocoons").defineInRange("parasite lifespan", 16, 0, 10000);
		Parasite_Pickup = BUILDER.comment("You can pick up parasites by right clicking them with an empty main hand while sneaking [false/true]").define("parasite pickup", true);
		BUILDER.pop();
		
		BUILDER.push("Undead Swine");
		UndeadSwine_Health = BUILDER.comment("Maximum Undead Swine health [1-1000]").defineInRange("undeadswine health", 50.0D, 1.0D, 1000.0D);
		UndeadSwine_Attack = BUILDER.comment("Undead Swine strength [1-1000]").defineInRange("undeadswine attack", 4.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Mycosis");
		Mycosis_Lifespan = BUILDER.comment("Mycosis lifespan [1-10000]").defineInRange("mycosis lifespan", 20, 0, 10000);
		Mycosis_Health = BUILDER.comment("Maximum Mycosis health [1-1000]").defineInRange("mycosis health", 20.0D, 1.0D, 1000.0D);
		Mycosis_Attack = BUILDER.comment("Mycosis strength [1-1000]").defineInRange("mycosis attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Frigid");
		Frigid_Lifespan = BUILDER.comment("Frigid lifespan [1-10000]").defineInRange("frigid lifespan", 20, 0, 10000);
		Frigid_Health = BUILDER.comment("Maximum Frigid health [1-1000]").defineInRange("frigid health", 30.0D, 1.0D, 1000.0D);
		Frigid_Attack = BUILDER.comment("Frigid strength [1-1000]").defineInRange("frigid attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Salamander");
		Salamander_Health = BUILDER.comment("Maximum Salamander health [1-1000]").defineInRange("salamander health", 60.0D, 1.0D, 1000.0D);
		Salamander_Attack = BUILDER.comment("Salamander strength [1-1000]").defineInRange("salamander attack", 4.0D, 1.0D, 1000.0D);
		Salamander_Defender = BUILDER.comment("Should tamed Salamander defend its owner [false/true]").define("salamander defender", false);
		BUILDER.pop();
		
		BUILDER.push("Wendigo");
		Wendigo_Health = BUILDER.comment("Maximum Wendigo health [1-1000]").defineInRange("wendigo health", 60.0D, 1.0D, 1000.0D);
		Wendigo_Attack = BUILDER.comment("Wendigo strength [1-1000]").defineInRange("wendigo attack", 8.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Mimicrab");
		Mimic_Health = BUILDER.comment("Maximum Mimicrab health [1-1000]").defineInRange("mimicrab health", 10.0D, 1.0D, 1000.0D);
		Mimic_Attack = BUILDER.comment("Mimicrab strength [1-1000]").defineInRange("mimicrab attack", 8.0D, 1.0D, 1000.0D);
		pSpawnRate_DeathMimic = BUILDER.comment("Set the spawn rate of Mimicrab near player death [0-1000]").defineInRange("mimicrab spawn rate near player death", 250, 0, 1000);
		BUILDER.pop();

		BUILDER.push("Sludge Lord");
		SludgeLord_Health = BUILDER.comment("Maximum Sludge Lord health [1-1000]").defineInRange("sludge lord health", 70.0D, 1.0D, 1000.0D);
		SludgeLord_Attack = BUILDER.comment("Sludge Lord strength [1-1000]").defineInRange("sludge lord attack", 5.0D, 1.0D, 1000.0D);
		SludgeLord_Ability_Num = BUILDER.comment("Set the number of Lil'Sludge summoned per cast [0-100]").defineInRange("sludge lord summon number", 3, 0, 100);
		SludgeLord_Ability_Max = BUILDER.comment("Set the max number of Lil'Sludge summoned [0-100]").defineInRange("sludge lord summon max", 8, 0, 100);
		SludgeLord_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Lil'Sludge [0-100]").defineInRange("sludge lord summon cooldown", 17, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Lil'Sludge");
		LilSludge_Lifespan = BUILDER.comment("Lil'Sludge lifespan [false/true]").defineInRange("lil'sludge lifespan", 60, 0, 10000);
		LilSludge_Health = BUILDER.comment("Maximum Lil'Sludge health [1-1000]").defineInRange("lil'sludge health", 20.0D, 1.0D, 1000.0D);
		LilSludge_Attack = BUILDER.comment("Lil'Sludge strength [1-1000]").defineInRange("lil'sludge attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Raven");
		Raven_Health = BUILDER.comment("Maximum Raven health [1-1000]").defineInRange("raven health", 6.0D, 1.0D, 1000.0D);
		Raven_Perch = BUILDER.comment("Should tamed Raven perch on owner's head [false/true]").define("raven perching", true);
		Raven_Slowfall = BUILDER.comment("Should perching Raven slow down owner's falling speed [false/true]").define("raven slow down falling", true);
		Raven_Loot = BUILDER.comment("Customize drop rates of the items which ravens can find. Ex. \\\"minecraft:tropical_fish,0.4,2\\\" or \\\"mod_lavacow:sharptooth,0.1\\\"").defineList("loot table for ravens", 
				Lists.newArrayList(
						"minecraft:beetroot_seeds,0.15",
						"minecraft:wheat_seeds,0.15,2",
						"minecraft:melon_seeds,0.15",
						"minecraft:pumpkin_seeds,0.15",
						"minecraft:gold_nugget,0.1,2",
						"minecraft:iron_nugget,0.1,2"), 
				o -> o instanceof String);
		Seagull_Loot = BUILDER.comment("Customize drop rates of the items which seagulls can find. Ex. \\\"minecraft:tropical_fish,0.4,2\\\" or \\\"mod_lavacow:sharptooth,0.1\\\"").defineList("loot table for seagulls", 
				Lists.newArrayList(
						"minecraft:cod,0.15",
						"minecraft:tropical_fish,0.15"), 
				o -> o instanceof String);
		Spectral_Raven_Loot = BUILDER.comment("Customize drop rates of the items which spectral ravens can find. Ex. \\\"minecraft:tropical_fish,0.4,2\\\" or \\\"mod_lavacow:sharptooth,0.1\\\"").defineList("loot table for spectral ravens", 
				Lists.newArrayList(
						"minecraft:gold_nugget,0.15,3",
						"minecraft:iron_nugget,0.15,3"), 
				o -> o instanceof String);
		BUILDER.pop();
		
		BUILDER.push("Ptera");
		Ptera_Health = BUILDER.comment("Maximum Ptera health [1-1000]").defineInRange("ptera health", 10.0D, 1.0D, 1000.0D);
		Ptera_Attack = BUILDER.comment("Ptera strength [1-1000]").defineInRange("ptera attack", 3.0D, 1.0D, 1000.0D);
		Ptera_Ability_Chance = BUILDER.comment("Chance of Ptera to carry a passenger when spawned [0-100]").defineInRange("ptera carries passenger chance", 10, 0, 100);
		Ptera_Ability_Spawn = BUILDER.comment("Customize the passenger list for the Ptera. Ex. \\\"mod_lavacow:foglet,40\\\" or \\\"minecraft:spider,20\\\"").defineList("passenger list for ptera", 
				Lists.newArrayList(
						"mod_lavacow:foglet,40",
						"minecraft:zombie,40",
						"minecraft:creeper,20"), 
				o -> o instanceof String);
		
		BUILDER.pop();
		
		BUILDER.push("Vespa");
		pEvolveRate_Vespa = BUILDER.comment("Set the chance of Vespa transformed from a Parasite [0-100]").defineInRange("vespa evolve rate", 20, 0, 100);
		Vespa_Health = BUILDER.comment("Maximum Vespa health [1-1000]").defineInRange("vespa health", 20.0D, 1.0D, 1000.0D);
		Vespa_Attack = BUILDER.comment("Vespa strength [1-1000]").defineInRange("vespa attack", 5.0D, 1.0D, 1000.0D);
		pBeeConvertRate_Vespa = BUILDER.comment("Set the chance of Vespa converted from a lightning-struck Bee [0-100]").defineInRange("vespa convert rate", 10, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Scarecrow");
		Scarecrow_Health = BUILDER.comment("Maximum Scarecrow health [1-1000]").defineInRange("scarecrow health", 40.0D, 1.0D, 1000.0D);
		Scarecrow_Attack = BUILDER.comment("Scarecrow strength [1-1000]").defineInRange("scarecrow attack", 8.0D, 1.0D, 1000.0D);
		pScarecrow_PlagueDoctor = BUILDER.comment("Should spawn Plague Doctor Scarecrow when a Raven was killed. [0-100]").defineInRange("plague doctor scarecrow should spawn", 30, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Swarmer");
		Swarmer_Health = BUILDER.comment("Maximum Swarmer health [1-1000]").defineInRange("swarmer health", 8.0D, 1.0D, 1000.0D);
		Swarmer_Attack = BUILDER.comment("Swarmer strength [1-1000]").defineInRange("swarmer attack", 1.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Piranha");
		Piranha_Health = BUILDER.comment("Maximum Piranha health [1-1000]").defineInRange("piranha health", 3.0D, 1.0D, 1000.0D);
		Piranha_Attack = BUILDER.comment("Piranha strength [1-1000]").defineInRange("piranha attack", 1.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Osvermis");
		BoneWorm_Health = BUILDER.comment("Maximum OsVermis health [1-1000]").defineInRange("boneworm health", 32.0D, 1.0D, 1000.0D);
		BoneWorm_Attack = BUILDER.comment("OsVermis strength [1-1000]").defineInRange("boneworm attack", 6.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Penghoul");
		Pingu_Health = BUILDER.comment("Maximum Penghoul health [1-1000]").defineInRange("penghoul health", 10.0D, 1.0D, 1000.0D);
		Pingu_Attack = BUILDER.comment("Penghoul strength [1-1000]").defineInRange("penghoul attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Undertaker");
		Undertaker_Health = BUILDER.comment("Maximum Undertaker health [1-1000]").defineInRange("undertaker health", 40.0D, 1.0D, 1000.0D);
		Undertaker_Attack = BUILDER.comment("Undertaker strength [1-1000]").defineInRange("undertaker attack", 6.0D, 1.0D, 1000.0D);
		Undertaker_Ability_Num = BUILDER.comment("Set the number of Unburied summoned per cast [0-100]").defineInRange("undertaker summon number", 4, 0, 100);
		Undertaker_Ability_Max = BUILDER.comment("Set the max number of Unburied summoned [0-100]").defineInRange("undertaker summon max", 4, 0, 100);
		Undertaker_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Unburied [0-100]").defineInRange("undertaker summon cooldown", 15, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Unburied");
		Unburied_Lifespan = BUILDER.comment("Unburied lifespan [1-10000]").defineInRange("unburied lifespan", 20, 0, 10000);
		Unburied_Health = BUILDER.comment("Maximum Unburied health [1-1000]").defineInRange("unburied health", 20.0D, 1.0D, 1000.0D);
		Unburied_Attack = BUILDER.comment("Unburied strength [1-1000]").defineInRange("unburied attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Ghost Ray");
		GhostRay_Health = BUILDER.comment("Maximum Ghost Ray health [1-1000]").defineInRange("ghost ray health", 20.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Banshee");
		Banshee_Health = BUILDER.comment("Maximum Banshee health [1-1000]").defineInRange("banshee health", 34.0D, 1.0D, 1000.0D);
		Banshee_Attack = BUILDER.comment("Banshee strength [1-1000]").defineInRange("banshee attack", 7.0D, 1.0D, 1000.0D);
		Banshee_Ability_Radius = BUILDER.comment("Set the effect radius of Banshee scream [1-1000]").defineInRange("banshee scream radius", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Weta");
		Weta_Health = BUILDER.comment("Maximum Weta health [1-1000]").defineInRange("weta health", 12.0D, 1.0D, 1000.0D);
		Weta_Attack = BUILDER.comment("Weta strength [1-1000]").defineInRange("weta attack", 1.0D, 1.0D, 1000.0D);
		Weta_Harvest_Diseased_Wheat = BUILDER.comment("Chance of dropping Diseased Wheat [1-1000]").defineInRange("diseased wheat drop rate", 0.15D, 0.0D, 1.0D);
		BUILDER.pop();
		
		BUILDER.push("Avaton");
		Avaton_Health = BUILDER.comment("Maximum Avaton health [1-1000]").defineInRange("avaton health", 30.0D, 1.0D, 1000.0D);
		Avaton_Attack = BUILDER.comment("Avaton strength [1-1000]").defineInRange("avaton attack", 5.0D, 1.0D, 1000.0D);
		Avaton_Ability_Num = BUILDER.comment("Set the number of Weta summoned per cast [0-100]").defineInRange("avaton summon number", 2, 0, 100);
		Avaton_Ability_Max = BUILDER.comment("Set the max number of Weta summoned [0-100]").defineInRange("avaton summon max", 16, 0, 100);
		Avaton_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Weta [0-100]").defineInRange("avaton summon cooldown", 8, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Forsaken");
		Forsaken_Health = BUILDER.comment("Maximum Forsaken health [1-1000]").defineInRange("forsaken health", 30.0D, 1.0D, 1000.0D);
		Forsaken_Attack = BUILDER.comment("Forsaken strength [1-1000]").defineInRange("forsaken attack", 4.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Skeleton King");
		Spawn_SkeletonKing = BUILDER.comment("Should Skeleton King be summoned with the crown [false/true]").define("skeleton king summon", true);
		SkeletonKing_Health = BUILDER.comment("Maximum Skeleton King health [1-1000]").defineInRange("skeleton king health", 360.0D, 1.0D, 1000.0D);
		SkeletonKing_Attack = BUILDER.comment("Skeleton King strength [1-1000]").defineInRange("skeleton king attack", 16.0D, 1.0D, 1000.0D);
		SkeletonKing_AbilityA_Cooldown = BUILDER.comment("Set the cooldown of Sand Tomb [0-100]").defineInRange("skeleton king sand tomb cooldown", 10, 0, 100);
		SkeletonKing_AbilityB_Cooldown = BUILDER.comment("Set the cooldown of Sand Wraith [0-100]").defineInRange("skeleton king sand wraith cooldown", 16, 0, 100);
		SkeletonKing_AbilityC_Cooldown = BUILDER.comment("Set the cooldown of Death Coil [0-100]").defineInRange("skeleton king death coil cooldown", 6, 0, 100);
		SkeletonKing_Loot_Option = BUILDER.comment("Should Skeleton King drop its loots inside a chest [false/true]").define("skeleton king loot in chest", true);
		BUILDER.pop();
		
		BUILDER.push("Mummy");
		Mummy_Lifespan = BUILDER.comment("Mummy lifespan [1-10000]").defineInRange("mummy lifespan", 20, 0, 10000);
		Mummy_Health = BUILDER.comment("Maximum Mummy health [1-1000]").defineInRange("mummy health", 24.0D, 1.0D, 1000.0D);
		Mummy_Attack = BUILDER.comment("Mummy strength [1-1000]").defineInRange("mummy attack", 4.0D, 1.0D, 1000.0D);
		BUILDER.pop();

		BUILDER.push("Cactyrant");
		Cactyrant_Health = BUILDER.comment("Maximum Cactyrant health [1-1000]").defineInRange("cactyrant health", 60.0D, 1.0D, 1000.0D);
		Cactyrant_Attack = BUILDER.comment("Cactyrant strength [1-1000]").defineInRange("cactyrant attack", 8.0D, 1.0D, 1000.0D);
		Cactyrant_Ability_Cooldown = BUILDER.comment("Set the cooldown of thorn barrage [0-100]").defineInRange("cactyrant summon cooldown", 3, 0, 100);
		BUILDER.pop();

		BUILDER.push("Cactoid");
		Cactoid_Health = BUILDER.comment("Maximum Cactoid health [1-1000]").defineInRange("cactoid health", 20.0D, 1.0D, 1000.0D);
		Cactoid_Attack = BUILDER.comment("Cactoid strength [1-1000]").defineInRange("cactoid attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Warped Firefly");
		WarpedFirefly_Health = BUILDER.comment("Maximum Warped Firefly health [1-1000]").defineInRange("warped firefly health", 10.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Imp");
		Imp_Health = BUILDER.comment("Maximum Imp health [1-1000]").defineInRange("imp health", 16.0D, 1.0D, 1000.0D);
		Imp_Attack = BUILDER.comment("Imp strength [1-1000]").defineInRange("imp attack", 2.0D, 1.0D, 1000.0D);	
		BUILDER.pop();

		BUILDER.push("Sea Hag");
		SeaHag_Health = BUILDER.comment("Maximum Sea Hag health [1-1000]").defineInRange("sea hag health", 30.0D, 1.0D, 1000.0D);
		SeaHag_Attack = BUILDER.comment("Sea Hag strength [1-1000]").defineInRange("sea hag attack", 5.0D, 1.0D, 1000.0D);	
		SeaHag_Ability_Num = BUILDER.comment("Set the number of Pufferfish summoned per cast [0-100]").defineInRange("sea hag summon number", 4, 0, 100);
		SeaHag_Ability_Max = BUILDER.comment("Set the max number of Pufferfish summoned [0-100]").defineInRange("sea hag summon max", 8, 0, 100);
		SeaHag_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Pufferfish [0-100]").defineInRange("sea hag summon cooldown", 12, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Wisp");
		Wisp_Health = BUILDER.comment("Maximum Wisp health [1-1000]").defineInRange("wisp health", 8.0D, 1.0D, 1000.0D);
		Wisp_ExplosionPower = BUILDER.comment("Wisp strength [1-1000]").defineInRange("wisp attack", 3.0D, 0.0D, 10.0D);	
		Wisp_Tamed_Explosion = BUILDER.comment("Should tamed Wisp use explosion attack. [false/true]").define("tamed wisp explodes", true);
		BUILDER.pop();

		BUILDER.push("Grave Robber");
		GraveRobber_Health = BUILDER.comment("Maximum Grave Robber health [1-1000]").defineInRange("grave robber health", 20.0D, 1.0D, 1000.0D);
		GraveRobber_Attack = BUILDER.comment("Grave Robber strength [1-1000]").defineInRange("grave robber attack", 5.0D, 1.0D, 1000.0D);	
		pSpawnRate_GraveRobberGhost = BUILDER.comment("Set the spawn rate of Ghost of Grave Robber [0-100]").defineInRange("grave robber ghost spawn rate", 40, 0, 100);
		GraveRobberGhost_Health = BUILDER.comment("Maximum Ghost of Grave Robber health [1-1000]").defineInRange("grave robber ghost health", 8.0D, 1.0D, 1000.0D);
		GraveRobberGhost_Attack = BUILDER.comment("Ghost of Grave Robber strength [1-1000]").defineInRange("grave robber ghost attack", 5.0D, 1.0D, 1000.0D);	
		BUILDER.pop();
		
		BUILDER.push("Wraith");
		Wraith_Health = BUILDER.comment("Maximum Wraith health [1-1000]").defineInRange("wraith health", 20.0D, 1.0D, 1000.0D);
		Wraith_Attack = BUILDER.comment("Wraith strength [1-1000]").defineInRange("wraith attack", 5.0D, 1.0D, 1000.0D);
		BUILDER.pop();		

		BUILDER.push("Amber Scarab");
		Scarab_Lifespan = BUILDER.comment("Amber Scarab lifespan [1-10000]").defineInRange("amber scarab lifespan", 60, 0, 10000);
		Scarab_Health = BUILDER.comment("Maximum Amber Scarab health [1-1000]").defineInRange("amber scarab health", 8.0D, 1.0D, 1000.0D);
		Scarab_Attack = BUILDER.comment("Amber Scarab strength [1-1000]").defineInRange("amber scarab attack", 1.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Beelzebub");
		Beelzebub_Health = BUILDER.comment("Maximum Beelzebub health [1-1000]").defineInRange("beelzebub health", 40.0D, 1.0D, 1000.0D);
		Beelzebub_Attack = BUILDER.comment("Beelzebub strength [1-1000]").defineInRange("beelzebub attack", 5.0D, 1.0D, 1000.0D);
		Beelzebub_Ability_Num = BUILDER.comment("Set the number of Parasite summoned per cast [0-100]").defineInRange("beelzebub summon number", 3, 0, 100);
		Beelzebub_Ability_Max = BUILDER.comment("Set the max number of Parasite summoned [0-100]").defineInRange("beelzebub summon max", 12, 0, 100);
		Beelzebub_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Parasite [0-100]").defineInRange("beelzebub summon cooldown", 11, 0, 100);
		Beelzebub_Ability_Cooldown_Mount = BUILDER.comment("Set the cooldown of summoning Parasite when mounted [0-100]").defineInRange("mounted beelzebub summon cooldown", 8, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Enigmoth");
		Enigmoth_Health = BUILDER.comment("Maximum Enigmoth health [1-1000]").defineInRange("enigmoth health", 50.0D, 1.0D, 1000.0D);
		Enigmoth_Attack = BUILDER.comment("Enigmoth strength [1-1000]").defineInRange("enigmoth attack", 8.0D, 1.0D, 1000.0D);
		Enigmoth_Ability_Cooldown = BUILDER.comment("Set the cooldown of spreading scales [0-100]").defineInRange("enigmoth spell cooldown", 8, 0, 100);
		Enigmoth_Ability_Cooldown_Mount = BUILDER.comment("Set the cooldown of spreading scales when mounted [0-100]").defineInRange("mounted enigmoth spell cooldown", 4, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Lamprey");
		pSpawnRate_Lamprey = BUILDER.comment("Set the spawn rate of Lamprey [0-100]").defineInRange("lamprey spawn rate", 10, 0, 100);
		Lamprey_Health = BUILDER.comment("Maximum Lamprey health [1-1000]").defineInRange("lamprey health", 6.0D, 1.0D, 1000.0D);
		Lamprey_Attack = BUILDER.comment("Lamprey strength [1-1000]").defineInRange("lamprey attack", 1.0D, 1.0D, 1000.0D);
		Lamprey_Attach = BUILDER.comment("Lamprey will attack their target by attaching on them [false/true]").define("lamprey attacks by attaching onto target", true);
		Lamprey_Lifespan = BUILDER.comment("The amount of seconds before Lamprey naturally die").defineInRange("lamprey lifespan", 16, 0, 10000);
		BUILDER.pop();

		BUILDER.push("Ghoul");
		Ghoul_Health = BUILDER.comment("Maximum Ghoul health [1-1000]").defineInRange("ghoul health", 16.0D, 1.0D, 1000.0D);
		Ghoul_Attack = BUILDER.comment("Ghoul strength [1-1000]").defineInRange("ghoul attack", 3.0D, 1.0D, 1000.0D);
		Ghoul_targetHPThreshold = BUILDER.comment("Set the health threshold of becoming Ghoul's target [0-100]").defineInRange("ghoul target health threshold", 40, 0, 100);
		BUILDER.pop();
		
		//BUILDER.push("Living Armor");
		//Living_Armor_Health = BUILDER.comment("Maximum Living Armor health [1-1000]").defineInRange("living armor health", 40.0D, 1.0D, 1000.0D);
		//Living_Armor_Attack = BUILDER.comment("Living Armor strength [1-1000]").defineInRange("living armor attack", 8.0D, 1.0D, 1000.0D);
		//BUILDER.pop();
		
		BUILDER.push("Item");
		MoltenHammer_PVP = BUILDER.comment("Allow Molten Hammer active effect to hit players [false/true]").define("allow molten hammer pvp", false);
		MoltenHammer_Damage = BUILDER.comment("Area Damage of Molten Hammer [1-1000]").defineInRange("molten hammer attack", 8.0D, 1.0D, 1000.0D);
		MoltenHammer_Cooldown = BUILDER.comment("Ability cooldown of Molten Hammer [1-10000]").defineInRange("molten hammer cooldown", 4, 0, 10000);
		SoulFireHammer_Damage = BUILDER.comment("Area Damage of Soulforged Hammer [1-1000]").defineInRange("soulforged hammer attack", 10.0D, 1.0D, 1000.0D);
		SoulFireHammer_Cooldown = BUILDER.comment("Ability cooldown of Soulforged Hammer [1-10000]").defineInRange("soulforged hammer cooldown", 4, 0, 10000);
		Fission_ModEntity = BUILDER.comment("Allow Potion of Fission to be used on entites from other mods [false/true]").define("fission potion works on entities from other mods", false);
		GoldenHeart_dur = BUILDER.comment("Set the chances of Golden Heart to drop 1 durability per tick , 0 = Infinite [0-100]").defineInRange("golden heart durability", 100, 0, 100);
		GoldenHeart_bl = BUILDER.comment("BlackBanlist for items that Golden Heart are unable to mend. Ex. \\\"minecraft:shears\\\" or \\\"mod_lavacow:moltenhammer\\\"").defineList("banlisted items from golden heart", 
				Lists.newArrayList(), o -> o instanceof String);		
		GoldenHeart_GrantsRegeneration = BUILDER.comment("Enables the Regeneration effect of the Golden Heart. [false/true]").define("golden heart grants regeneration", true);
		GoldenHeart_RepairsEquipment = BUILDER.comment("Allow the Golden Heart to repair worn equipment. [false/true]").define("golden heart repairs equipment", true);	
		BoneSword_Damage = BUILDER.comment("Set the bonus damage of Bone Sword to X% [0-100]").defineInRange("bonesword bonus damage", 5, 0, 100);		
		DreamCatcher_spawn = BUILDER.comment("Customize the Spawn list for the Dreamcatcher. Ex. \\\"mod_lavacow:foglet,40,1,2\\\" or \\\"mod_lavacow:vespa,20,1,1\\\"").defineList("spawn list for dreamcatcher", 
				Lists.newArrayList(
						"mod_lavacow:foglet,40,1,2",
						"mod_lavacow:undeadswine,20,1,1",
						"mod_lavacow:wendigo,20,1,1",
						"mod_lavacow:sludgelord,20,1,1",
						"mod_lavacow:vespa,20,1,1",
						"mod_lavacow:scarecrow,20,1,1",
						"mod_lavacow:boneworm,20,1,1",
						"mod_lavacow:pingu,40,4,8",
						"mod_lavacow:undertaker,20,1,1",
						"mod_lavacow:banshee,20,1,1",
						"mod_lavacow:avaton,20,1,1"), 
				o -> o instanceof String);
		DreamCatcher_dur = BUILDER.comment("The durability lost each time when the Dreamcatcher is triggered, 0 = Infinite [0-120]").defineInRange("dreamcatcher durability drop", 30, 0, 120);				
		SludgeWand_Cooldown = BUILDER.comment("Ability cooldown of \\\"Pestilence\\\" [1-10000]").defineInRange("pestilence cooldown", 60, 0, 10000);
		Undertaker_Shovel_Cooldown = BUILDER.comment("Ability cooldown of Midnight Mourne [1-10000]").defineInRange("midnight mourne cooldown", 60, 0, 10000);							
		BoneSword_DamageCap = BUILDER.comment("Set the bonus damage cap of Bone Sword [0-10000]").defineInRange("bonesword bonus damage cap", 10000, 0, 10000);		
		MootenHeart_Damage = BUILDER.comment("Set the fire damage reduction of Molten Heart to X% [0-10000]").defineInRange("molten heart damage reduction", 20, 0, 10000);	
		General_IllagerNose = BUILDER.comment("Set the drop rate of Illager Nose [0-100]").defineInRange("illager nose drop rate", 2, 0, 100);
		ScarabScepter_Cooldown = BUILDER.comment("Ability cooldown of Scarab Scepter [1-10000]").defineInRange("scarab scepter cooldown", 60, 0, 10000);
		Ankh_Scepter_Cooldown = BUILDER.comment("Ability cooldown of Ankh Scepter [1-10000]").defineInRange("ankh scepter cooldown", 60, 0, 10000);
		Fungal_Staff_Cooldown = BUILDER.comment("Ability cooldown of Fungal Staff [1-10000]").defineInRange("fungal staff cooldown", 60, 0, 10000);
		Frozen_Grip_Cooldown = BUILDER.comment("Ability cooldown of Frozen Grip [1-10000]").defineInRange("frozen grip cooldown", 60, 0, 10000);
		BUILDER.pop();
		
		BUILDER.push("Structure");
		Generate_Cemetery = BUILDER.comment("Generate Cemetery in the Overworld. [false/true]").define("generate cemetery", true);
		SpawnRate_Cemetery = BUILDER.comment("Spawn rate of Cemetery [1-10000]").defineInRange("cemetery should spawn", 2, 0, 10000);
		Spawn_Cemetery_AllowList = BUILDER.comment("Cemetery are only allowed to spawn in these dimensions' IDs").defineList("cemetery spawn allow dimensions", 
				Lists.newArrayList(Level.OVERWORLD.location().toString()), o -> o instanceof String);
		Cemetery_SpawnRate = BUILDER.comment("Cemetery spawns Unburied occasionally. [0-100]").defineInRange("cemetery spawns unburied", 40, 0, 100);	
		Generate_Desert_Tomb = BUILDER.comment("Generate Desert Tomb in the Overworld. [false/true]").define("generate desert tomb", true);
		SpawnRate_Desert_Tomb = BUILDER.comment("Spawn rate of Desert Tomb [1-1000]").defineInRange("desert tomb should spawn", 500, 0, 1000);
		BUILDER.pop();
		
		BUILDER.push("General");
		FlyingHeight_limit = BUILDER.comment("Set the height limit to X blocks above the ground for flyers, 0 = Infinite [0-100]").defineInRange("flying height limit", 16, 0, 100);		
		Potion_Enable = BUILDER.comment("Adding new brewing recipe (existing property will be preserved). [false/true]").define("enable brewing recipe", true);
		Enchantment_Enable = BUILDER.comment("Adding new enchantment (existing property will be preserved). [false/true]").define("enable enchantment", true);
		SunScreen_Mode = BUILDER.comment("Mobs in this mod will not burn under daylight. [false/true]").define("sunscreen mode", false);	
		Spawn_AllowList = BUILDER.comment("All mobs are only allowed to spawn in these dimensions' IDs").defineList("mob spawn allow dimensions", 
				Lists.newArrayList(Level.OVERWORLD.location().toString(), Level.NETHER.location().toString(), Level.END.location().toString()), o -> o instanceof String);		
		Suicidal_Minion = BUILDER.comment("Entities summoned by other mobs die when their summoner dies. [false/true]").define("suicidal", true);
		BonusVillagerTrades = BUILDER.comment("Offers bonus Villager trades. [false/true]").define("bonus villager trades", true);
		BonusWanderingTraderTrades = BUILDER.comment("Offers bonus Wandering Trader trades. [false/true]").define("bonus wandering trader trades", true); 
		Show_Expire_Death_Messege = BUILDER.comment("Show custom death messege when summoned mobs expired. [false/true]").define("show summoned mobs death messege", true); 
		BUILDER.pop();
		
		SPEC = BUILDER.build();
	}
}
