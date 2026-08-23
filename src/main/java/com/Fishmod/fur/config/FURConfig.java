package com.Fishmod.fur.config;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

public final class FURConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Lavacow_Health;
	
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
	public static final ForgeConfigSpec.ConfigValue<Integer> Mimic_SpawnRadius;
	public static final ForgeConfigSpec.ConfigValue<Integer> Mimic_SpawnCap;
	public static final ForgeConfigSpec.ConfigValue<Integer> pSpawnRate_DeathMimic;
	
	public static final ForgeConfigSpec.ConfigValue<Double> ShroomLord_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> ShroomLord_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> ShroomLord_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> ShroomLord_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> ShroomLord_Ability_Cooldown;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Raven_Health;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Raven_Perch;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Raven_Slowfall;
	
	public static final ForgeConfigSpec.ConfigValue<Double> Ptera_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Ptera_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ptera_Ability_Chance;
	
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
	
	public static final ForgeConfigSpec.ConfigValue<Double> VoidGlider_Health;

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
	
	public static final ForgeConfigSpec.ConfigValue<Double> Shroomling_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Shroomling_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Shroomling_Lifespan;
	
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

	public static final ForgeConfigSpec.ConfigValue<Double> MummyLord_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> MummyLord_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> MummyLord_Ability_Num;
	public static final ForgeConfigSpec.ConfigValue<Integer> MummyLord_Ability_Max;
	public static final ForgeConfigSpec.ConfigValue<Integer> MummyLord_Ability_Cooldown;

	public static final ForgeConfigSpec.ConfigValue<Double> Cactyrant_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Cactyrant_Attack;
	public static final ForgeConfigSpec.ConfigValue<Integer> Cactyrant_Ability_Cooldown;

	public static final ForgeConfigSpec.ConfigValue<Double> Cactoid_Health;
	public static final ForgeConfigSpec.ConfigValue<Double> Cactoid_Attack;

	public static final ForgeConfigSpec.ConfigValue<Double> Flarefly_Health;
	public static final ForgeConfigSpec.ConfigValue<Integer> Flarefly_Light_Duration;
	public static final ForgeConfigSpec.ConfigValue<Integer> Flarefly_Feed_Cooldown;

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
	public static final ForgeConfigSpec.ConfigValue<Integer> pScarecrow_PlagueDoctor;
	public static final ForgeConfigSpec.ConfigValue<Integer> SludgeWand_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Undertaker_Shovel_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SunScreen_Mode;
	public static final ForgeConfigSpec.ConfigValue<Integer> BoneSword_DamageCap;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Spawn_AllowList;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Suicidal_Minion;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Dreamcatcher_Enabled;
	public static final ForgeConfigSpec.ConfigValue<Integer> Dreamcatcher_HpBudgetPerStage;
	public static final ForgeConfigSpec.ConfigValue<Integer> Dreamcatcher_MaxMobsPerWave;
	public static final ForgeConfigSpec.ConfigValue<Integer> Dreamcatcher_ChargeRadius;
	public static final ForgeConfigSpec.ConfigValue<Integer> Dreamcatcher_SpawnRingMin;
	public static final ForgeConfigSpec.ConfigValue<Integer> Dreamcatcher_SpawnRingMax;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> Dreamcatcher_Blacklist;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Potion_Enable;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Enchantment_Enable;
	public static final ForgeConfigSpec.ConfigValue<Integer> MootenHeart_Damage;
	public static final ForgeConfigSpec.ConfigValue<Boolean> BonusVillagerTrades;
	public static final ForgeConfigSpec.ConfigValue<Boolean> BonusWanderingTraderTrades;
	public static final ForgeConfigSpec.ConfigValue<Boolean> Show_Expire_Death_Message;
	public static final ForgeConfigSpec.ConfigValue<Integer> Pestilence_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Sere_Shovel_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Virulent_Shovel_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Frore_Shovel_Cooldown;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ghostly_DodgeChance;
	public static final ForgeConfigSpec.ConfigValue<Integer> Ghostly_DeathPreventionCooldown;
	public static final ForgeConfigSpec.ConfigValue<Double> Ghostly_SpiritFormDuration;

	static {
		BUILDER.push("Moogma");
		Lavacow_Health = BUILDER.comment("Maximum Moogma health [1-1000]").defineInRange("moogma health", 10.0D, 1.0D, 1000.0D);
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
		Mimic_SpawnRadius = BUILDER.comment("Radius in blocks for the Mimicrab population cap at natural spawn [1-64]").defineInRange("mimicrab spawn radius", 8, 1, 64);
		Mimic_SpawnCap = BUILDER.comment("Max Mimicrabs allowed within the spawn radius; none spawn naturally once this many are already nearby [1-100]").defineInRange("mimicrab max nearby", 1, 1, 100);
		pSpawnRate_DeathMimic = BUILDER.comment("Set the spawn rate of Mimicrab near player death [0-1000]").defineInRange("mimicrab spawn rate near player death", 250, 0, 1000);
		BUILDER.pop();

		BUILDER.push("Shroomlord");
		ShroomLord_Health = BUILDER.comment("Maximum Shroomlord health [1-1000]").defineInRange("shroomlord health", 70.0D, 1.0D, 1000.0D);
		ShroomLord_Attack = BUILDER.comment("Shroomlord strength [1-1000]").defineInRange("shroomlord attack", 5.0D, 1.0D, 1000.0D);
		ShroomLord_Ability_Num = BUILDER.comment("Set the number of Lil'Sludge summoned per cast [0-100]").defineInRange("shroomlord summon number", 3, 0, 100);
		ShroomLord_Ability_Max = BUILDER.comment("Set the max number of Lil'Sludge summoned [0-100]").defineInRange("shroomlord summon max", 8, 0, 100);
		ShroomLord_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Lil'Sludge [0-100]").defineInRange("shroomlord summon cooldown", 17, 0, 100);
		BUILDER.pop();
		
		BUILDER.push("Shroomling");
		Shroomling_Lifespan = BUILDER.comment("Shroomling lifespan [1-10000]").defineInRange("shroomling lifespan", 60, 0, 10000);
		Shroomling_Health = BUILDER.comment("Maximum Shroomling health [1-1000]").defineInRange("shroomling health", 20.0D, 1.0D, 1000.0D);
		Shroomling_Attack = BUILDER.comment("Shroomling strength [1-1000]").defineInRange("shroomling attack", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Raven");
		Raven_Health = BUILDER.comment("Maximum Raven health [1-1000]").defineInRange("raven health", 6.0D, 1.0D, 1000.0D);
		Raven_Perch = BUILDER.comment("Should tamed Raven perch on owner's head [false/true]").define("raven perching", true);
		Raven_Slowfall = BUILDER.comment("Should perching Raven slow down owner's falling speed [false/true]").define("raven slow down falling", true);
		// Raven find-drop tables moved out of this config into config/fur/raven_loot.json (see RavenLootConfig).
		BUILDER.pop();
		
		BUILDER.push("Ptera");
		Ptera_Health = BUILDER.comment("Maximum Ptera health [1-1000]").defineInRange("ptera health", 10.0D, 1.0D, 1000.0D);
		Ptera_Attack = BUILDER.comment("Ptera strength [1-1000]").defineInRange("ptera attack", 3.0D, 1.0D, 1000.0D);
		Ptera_Ability_Chance = BUILDER.comment("Chance of Ptera to carry a passenger when spawned [0-100]").defineInRange("ptera carries passenger chance", 10, 0, 100);		
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
		
		BUILDER.push("OsVermis");
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
		
		BUILDER.push("Void Glider");
		VoidGlider_Health = BUILDER.comment("Maximum Void Glider health [1-1000]").defineInRange("void glider health", 20.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Banshee");
		Banshee_Health = BUILDER.comment("Maximum Banshee health [1-1000]").defineInRange("banshee health", 34.0D, 1.0D, 1000.0D);
		Banshee_Attack = BUILDER.comment("Banshee strength [1-1000]").defineInRange("banshee attack", 7.0D, 1.0D, 1000.0D);
		Banshee_Ability_Radius = BUILDER.comment("Set the effect radius of Banshee scream [1-1000]").defineInRange("banshee scream radius", 3.0D, 1.0D, 1000.0D);
		BUILDER.pop();
		
		BUILDER.push("Weta");
		Weta_Health = BUILDER.comment("Maximum Weta health [1-1000]").defineInRange("weta health", 12.0D, 1.0D, 1000.0D);
		Weta_Attack = BUILDER.comment("Weta strength [1-1000]").defineInRange("weta attack", 1.0D, 1.0D, 1000.0D);
		Weta_Harvest_Diseased_Wheat = BUILDER.comment("Chance of dropping Diseased Wheat [0.0-1.0]").defineInRange("diseased wheat drop rate", 0.15D, 0.0D, 1.0D);
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

		BUILDER.push("Mummy Lord");
		MummyLord_Health = BUILDER.comment("Maximum Mummy Lord health [1-1000]").defineInRange("mummy lord health", 120.0D, 1.0D, 1000.0D);
		MummyLord_Attack = BUILDER.comment("Mummy Lord strength [1-1000]").defineInRange("mummy lord attack", 10.0D, 1.0D, 1000.0D);
		MummyLord_Ability_Num = BUILDER.comment("Set the number of Scarabs summoned per cast [0-100]").defineInRange("mummy lord scarab summon number", 12, 0, 100);
		MummyLord_Ability_Max = BUILDER.comment("Set the max number of Scarabs that can be summoned [0-100]").defineInRange("mummy lord scarab summon max", 24, 0, 100);
		MummyLord_Ability_Cooldown = BUILDER.comment("Set the cooldown of summoning Scarabs in seconds [0-100]").defineInRange("mummy lord scarab summon cooldown", 20, 0, 100);
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
		
		BUILDER.push("Flarefly");
		Flarefly_Health = BUILDER.comment("Maximum Flarefly health [1-1000]").defineInRange("flarefly health", 10.0D, 1.0D, 1000.0D);
		Flarefly_Light_Duration = BUILDER.comment("Lifetime in seconds of the light orb spawned by feeding/hitting a Flarefly, from full brightness to fading out [15-10000]").defineInRange("flarefly light orb duration", 60, 15, 10000);
		Flarefly_Feed_Cooldown = BUILDER.comment("Cooldown in seconds before a Flarefly can spawn another light orb (feeding or being hit) [1-10000]").defineInRange("flarefly feed cooldown", 30, 1, 10000);
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
		BUILDER.pop();
		
		BUILDER.push("Wraith");
		Wraith_Health = BUILDER.comment("Maximum Wraith health [1-1000]").defineInRange("wraith health", 20.0D, 1.0D, 1000.0D);
		Wraith_Attack = BUILDER.comment("Wraith strength [1-1000]").defineInRange("wraith attack", 5.0D, 1.0D, 1000.0D);
		BUILDER.pop();		

		BUILDER.push("Scarab");
		Scarab_Lifespan = BUILDER.comment("Scarab lifespan [1-10000]").defineInRange("scarab lifespan", 60, 0, 10000);
		Scarab_Health = BUILDER.comment("Maximum Scarab health [1-1000]").defineInRange("scarab health", 8.0D, 1.0D, 1000.0D);
		Scarab_Attack = BUILDER.comment("Scarab strength [1-1000]").defineInRange("scarab attack", 1.0D, 1.0D, 1000.0D);
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
		
		BUILDER.push("Item");
		MoltenHammer_PVP = BUILDER.comment("Allow Molten Hammer active effect to hit players [false/true]").define("allow molten hammer pvp", false);
		MoltenHammer_Damage = BUILDER.comment("Area Damage of Molten Hammer [1-1000]").defineInRange("molten hammer attack", 8.0D, 1.0D, 1000.0D);
		MoltenHammer_Cooldown = BUILDER.comment("Ability cooldown of Molten Hammer [1-10000]").defineInRange("molten hammer cooldown", 4, 0, 10000);
		SoulFireHammer_Damage = BUILDER.comment("Area Damage of Soulforged Hammer [1-1000]").defineInRange("soulforged hammer attack", 10.0D, 1.0D, 1000.0D);
		SoulFireHammer_Cooldown = BUILDER.comment("Ability cooldown of Soulforged Hammer [1-10000]").defineInRange("soulforged hammer cooldown", 4, 0, 10000);
		Fission_ModEntity = BUILDER.comment("Allow Potion of Fission to be used on entities from other mods [false/true]").define("fission potion works on entities from other mods", false);
		GoldenHeart_dur = BUILDER.comment("Set the chance of Golden Heart dropping 1 durability per tick, 0 = Infinite [0-100]").defineInRange("golden heart durability", 100, 0, 100);
		GoldenHeart_bl = BUILDER.comment("Ban list for items that Golden Heart are unable to mend. Ex. \\\"minecraft:shears\\\" or \\\"fur:molten_hammer\\\"").defineList("banlisted items from golden heart",
				Lists.newArrayList(), o -> o instanceof String);		
		GoldenHeart_GrantsRegeneration = BUILDER.comment("Enables the Regeneration effect of the Golden Heart. [false/true]").define("golden heart grants regeneration", true);
		GoldenHeart_RepairsEquipment = BUILDER.comment("Allow the Golden Heart to repair worn equipment. [false/true]").define("golden heart repairs equipment", true);	
		BoneSword_Damage = BUILDER.comment("Set the bonus damage of Bone Sword to X% [0-100]").defineInRange("bonesword bonus damage", 5, 0, 100);		
		SludgeWand_Cooldown = BUILDER.comment("Ability cooldown of \\\"Pestilence\\\" [1-10000]").defineInRange("pestilence cooldown", 60, 0, 10000);
		Undertaker_Shovel_Cooldown = BUILDER.comment("Ability cooldown of Undertaker's Shovel [1-10000]").defineInRange("undertaker shovel cooldown", 60, 0, 10000);
		BoneSword_DamageCap = BUILDER.comment("Set the bonus damage cap of Bone Sword [0-10000]").defineInRange("bonesword bonus damage cap", 10000, 0, 10000);		
		MootenHeart_Damage = BUILDER.comment("Set the fire damage reduction of Molten Heart to X% [0-10000]").defineInRange("molten heart damage reduction", 20, 0, 10000);	
		Pestilence_Cooldown = BUILDER.comment("Ability cooldown of Pestilence [1-10000]").defineInRange("pestilence cooldown", 60, 0, 10000);
		Sere_Shovel_Cooldown = BUILDER.comment("Ability cooldown of Sere Shovel [1-10000]").defineInRange("sere shovel cooldown", 60, 0, 10000);
		Virulent_Shovel_Cooldown = BUILDER.comment("Ability cooldown of Virulent Shovel [1-10000]").defineInRange("virulent shovel cooldown", 60, 0, 10000);
		Frore_Shovel_Cooldown = BUILDER.comment("Ability cooldown of Frore Shovel [1-10000]").defineInRange("frore shovel cooldown", 60, 0, 10000);
		BUILDER.pop();

		BUILDER.push("Dreamcatcher");
		Dreamcatcher_Enabled = BUILDER.comment("Master switch for the Dreamcatcher block (charging + nightmare summoning). [false/true]").define("dreamcatcher enabled", true);
		Dreamcatcher_HpBudgetPerStage = BUILDER.comment("Base max-health budget granted per charge stage. Total wave budget = charge * this value [1-10000]").defineInRange("dreamcatcher hp budget per stage", 60, 1, 10000);
		Dreamcatcher_MaxMobsPerWave = BUILDER.comment("Maximum number of nightmare mobs summoned in a single wave [1-64]").defineInRange("dreamcatcher max mobs per wave", 8, 1, 64);
		Dreamcatcher_ChargeRadius = BUILDER.comment("Horizontal/vertical radius (blocks) around a sleeping player in which dreamcatchers gain charge [1-64]").defineInRange("dreamcatcher charge radius", 16, 1, 64);
		Dreamcatcher_SpawnRingMin = BUILDER.comment("Minimum horizontal distance (blocks) from the block that nightmare mobs spawn [1-64]").defineInRange("dreamcatcher spawn ring min", 8, 1, 64);
		Dreamcatcher_SpawnRingMax = BUILDER.comment("Maximum horizontal distance (blocks) from the block that nightmare mobs spawn [1-64]").defineInRange("dreamcatcher spawn ring max", 16, 1, 64);
		Dreamcatcher_Blacklist = BUILDER.comment("Entity ids excluded from the dreamcatcher spawn pool (in addition to the forge:bosses tag). Ex. \\\"minecraft:warden\\\"").defineList("dreamcatcher entity blacklist",
				Lists.newArrayList(), o -> o instanceof String);
		BUILDER.pop();

		BUILDER.push("Ghostly Armor");
		Ghostly_DodgeChance = BUILDER.comment("2-piece bonus: chance for incoming damage to be fully negated (dodged) [0-100]").defineInRange("ghostly dodge chance", 8, 0, 100);
		Ghostly_DeathPreventionCooldown = BUILDER.comment("Full-set bonus: cooldown in seconds before the death-prevention effect can trigger again [1-10000]").defineInRange("ghostly death prevention cooldown", 180, 1, 10000);
		Ghostly_SpiritFormDuration = BUILDER.comment("Full-set bonus: duration in seconds of the invulnerable spirit form triggered by death prevention [0.5-300]").defineInRange("ghostly spirit form duration", 2.0D, 0.5D, 300.0D);
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
		Show_Expire_Death_Message = BUILDER.comment("Show custom death message when summoned mobs expired. [false/true]").define("show summoned mobs death message", true);
		BUILDER.pop();
		
		SPEC = BUILDER.build();
	}
}
