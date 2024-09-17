package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.entities.EntityAmberLord;
import com.Fishmod.mod_LavaCow.entities.EntityAvaton;
import com.Fishmod.mod_LavaCow.entities.EntityBanshee;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityCactyrant;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.entities.EntityForsaken;
import com.Fishmod.mod_LavaCow.entities.EntityGraveRobber;
import com.Fishmod.mod_LavaCow.entities.EntityImp;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;
import com.Fishmod.mod_LavaCow.entities.EntityMummy;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;
import com.Fishmod.mod_LavaCow.entities.EntityPingu;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import com.Fishmod.mod_LavaCow.entities.EntityIsnachi;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntityUndeadSwine;
import com.Fishmod.mod_LavaCow.entities.EntityUndertaker;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;
import com.Fishmod.mod_LavaCow.entities.EntityZombieFrozen;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityPiranha;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGhostSwarmer;
import com.Fishmod.mod_LavaCow.entities.floating.EntitySeaHag;
import com.Fishmod.mod_LavaCow.entities.floating.EntityWraith;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityGhostRay;
import com.Fishmod.mod_LavaCow.entities.flying.EntityPtera;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityCactoid;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityEnigmothLarva;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarab;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityWeta;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;
import net.minecraft.world.World;

public class FURJERIntegration implements ModIntegration {	
	@JERPlugin
	public static IJERAPI jerAPI;
	public static World world;

	@Override
	public void init() {
		world = jerAPI.getWorld();
		// Loot table mob loot
		jerAPI.getMobRegistry().register(new EntityLavaCow(world), LootTableHandler.LAVACOW);
		jerAPI.getMobRegistry().register(new EntityZombieMushroom(world), LightLevel.hostile, LootTableHandler.ZOMBIEMUSHROOM);
		jerAPI.getMobRegistry().register(new EntityParasite(world), LightLevel.hostile, LootTableHandler.PARASITE);
		jerAPI.getMobRegistry().register(new EntityFoglet(world), LightLevel.hostile, LootTableHandler.FOGLET);
		jerAPI.getMobRegistry().register(new EntityIsnachi(world), LightLevel.hostile, LootTableHandler.ISNACHI);
		jerAPI.getMobRegistry().register(new EntityImp(world), LightLevel.hostile, LootTableHandler.IMP);
		jerAPI.getMobRegistry().register(new EntityZombieFrozen(world), LightLevel.hostile, LootTableHandler.ZOMBIEFROZEN);
		jerAPI.getMobRegistry().register(new EntityUndeadSwine(world), LightLevel.hostile, LootTableHandler.UNDEADSWINE);
		jerAPI.getMobRegistry().register(new EntitySalamander(world), LightLevel.hostile, LootTableHandler.SALAMANDER);
		jerAPI.getMobRegistry().register(new EntityWendigo(world), LightLevel.hostile, LootTableHandler.WENDIGO);
		jerAPI.getMobRegistry().register(new EntityMimic(world), LootTableHandler.MIMIC);
		jerAPI.getMobRegistry().register(new EntityLilSludge(world), LightLevel.hostile, LootTableHandler.LIL_SLUDGE);
		jerAPI.getMobRegistry().register(new EntitySludgeLord(world), LightLevel.hostile, LootTableHandler.SLUDGELORD);
		jerAPI.getMobRegistry().register(new EntityRaven(world), LootTableHandler.RAVEN);
		jerAPI.getMobRegistry().register(new EntityPtera(world), LightLevel.hostile, LootTableHandler.PTERA);
		jerAPI.getMobRegistry().register(new EntityVespa(world), LightLevel.hostile, LootTableHandler.VESPA);
		jerAPI.getMobRegistry().register(new EntityScarecrow(world), LightLevel.hostile, LootTableHandler.SCARECROW);
		jerAPI.getMobRegistry().register(new EntityPiranha(world), LootTableHandler.PIRANHA);
		jerAPI.getMobRegistry().register(new EntityZombiePiranha(world), LootTableHandler.ZOMBIEPIRANHA);
		jerAPI.getMobRegistry().register(new EntityBoneWorm(world), LightLevel.hostile, LootTableHandler.BONEWORM);
		jerAPI.getMobRegistry().register(new EntityPingu(world), LootTableHandler.PINGU);
		jerAPI.getMobRegistry().register(new EntityUndertaker(world), LightLevel.hostile, LootTableHandler.UNDERTAKER);
		jerAPI.getMobRegistry().register(new EntityUnburied(world), LightLevel.hostile, LootTableHandler.UNBURIED);
		jerAPI.getMobRegistry().register(new EntityGhostRay(world), LightLevel.hostile, LootTableHandler.GHOSTRAY);
		jerAPI.getMobRegistry().register(new EntityBanshee(world), LightLevel.hostile, LootTableHandler.BANSHEE);
		jerAPI.getMobRegistry().register(new EntityWeta(world), LightLevel.hostile, LootTableHandler.WETA);
		jerAPI.getMobRegistry().register(new EntityAvaton(world), LightLevel.hostile, LootTableHandler.AVATON);
		jerAPI.getMobRegistry().register(new EntityForsaken(world), LightLevel.hostile, LootTableHandler.FORSAKEN);
		jerAPI.getMobRegistry().register(new EntitySkeletonKing(world), LightLevel.hostile, LootTableHandler.SKELETON_KING);
		jerAPI.getMobRegistry().register(new EntityMummy(world), LightLevel.hostile, LootTableHandler.MUMMY);
		jerAPI.getMobRegistry().register(new EntityCactoid(world), LootTableHandler.CACTOID);
		jerAPI.getMobRegistry().register(new EntityCactyrant(world), LootTableHandler.CACTYRANT);
		jerAPI.getMobRegistry().register(new EntitySeaHag(world), LightLevel.hostile, LootTableHandler.SEA_HAG);
		jerAPI.getMobRegistry().register(new EntityWraith(world), LightLevel.hostile, LootTableHandler.WRAITH);
		jerAPI.getMobRegistry().register(new EntityGhostSwarmer(world), LightLevel.hostile, LootTableHandler.GHOST_SWARMER);
		jerAPI.getMobRegistry().register(new EntityScarab(world), LightLevel.hostile,  LootTableHandler.AMBER_SCARAB);
		jerAPI.getMobRegistry().register(new EntityAmberLord(world), LightLevel.hostile, LootTableHandler.AMBER_LORD);
		jerAPI.getMobRegistry().register(new EntityEnigmoth(world), LootTableHandler.ENIGMOTH);
		jerAPI.getMobRegistry().register(new EntityEnigmothLarva(world), LootTableHandler.ENIGMOTH_LARVA);
		jerAPI.getMobRegistry().register(new EntityGraveRobber(world), LootTableHandler.GRAVE_ROBBER);
		
		//Chest loot
		jerAPI.getDungeonRegistry().registerCategory("cemetery_chest", "JER.catagory.mod_lavacow.cemetery_chest");
		jerAPI.getDungeonRegistry().registerChest("cemetery_chest", LootTableHandler.CEMETERY_CHEST);
		jerAPI.getDungeonRegistry().registerCategory("desert_tomb_chest", "JER.catagory.mod_lavacow.desert_tomb_chest");
		jerAPI.getDungeonRegistry().registerChest("desert_tomb_chest", LootTableHandler.DESERT_TOMB_CHEST);
	}
}
