package com.Fishmod.mod_LavaCow.init;

import java.util.Set;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityAvaton;
import com.Fishmod.mod_LavaCow.entities.EntityBanshee;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;
import com.Fishmod.mod_LavaCow.entities.EntityPingu;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntityUndeadSwine;
import com.Fishmod.mod_LavaCow.entities.EntityUndertaker;
import com.Fishmod.mod_LavaCow.entities.EntityVespaCocoon;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;
import com.Fishmod.mod_LavaCow.entities.EntityZombieFrozen;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityPiranha;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import com.Fishmod.mod_LavaCow.entities.flying.EntityGhostRay;
import com.Fishmod.mod_LavaCow.entities.flying.EntityPtera;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityAcidJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySludgeJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySonicBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityWarSmallFireball;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityWeta;
import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEntities {
	
	//public static final EnumCreatureType WATER_MONSTER =  net.minecraftforge.common.util.EnumHelper.addCreatureType("water_monster", IMob.class, 12, Material.WATER, false, true);
	   // instantiate EntityEntry list
	private static int id = 0;
    public static final Set<EntityEntry> SET_ENTITIES = ImmutableSet.of(
            EntityEntryBuilder.create()
            .entity(EntityLavaCow.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "lavacow"), id++)
            .name(mod_LavaCow.MODID + "." + "lavacow")
            .tracker(80, 3, false)
            .egg(0xFF2724, 0xFFDA24)
            //.spawn(EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Lavacow, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityZombieMushroom.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "zombiemushroom"), id++)
            .name(mod_LavaCow.MODID + "." + "zombiemushroom")
            .tracker(80, 3, false)
            .egg(0xBCE0AC, 0x83631D)
            //.egg(0x228B22, 0xB22222)
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.getBiomes(BiomeDictionary.Type.WET))
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityParasite.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "parasite"), id++)
            .name(mod_LavaCow.MODID + "." + "parasite")
            .tracker(80, 3, false)
            .egg(0xAAFFEE, 0xBBFFEE)
            //.spawn(EnumCreatureType.MONSTER, 0, 0, 0, BiomeDictionary.getBiomes(BiomeDictionary.Type.VOID))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityFoglet.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "foglet"), id++)
            .name(mod_LavaCow.MODID + "." + "foglet")
            .tracker(80, 3, false)
            .egg(0xCBD3B9, 0x41352F)
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP))
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityZombieFrozen.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "zombiefrozen"), id++)
            .name(mod_LavaCow.MODID + "." + "zombiefrozen")
            .tracker(80, 3, false)
            .egg(0xAFE0E2, 0x59484F)
            //.egg(0x87B2C9, 0xC2F4F0)
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieFrozen, 8, 16, BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityUndeadSwine.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "undeadswine"), id++)
            .name(mod_LavaCow.MODID + "." + "undeadswine")
            .tracker(80, 3, false)
            .egg(0x8A9B8A, 0x3E5C5A)
            //.spawn(EnumCreatureType.MONSTER, Modconfig.pSpawnRate_UndeadSwine, 4, 8, BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST))
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntitySalamander.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "salamander"), id++)
            .name(mod_LavaCow.MODID + "." + "salamander")
            .tracker(80, 3, false)
            .egg(0x260606, 0xF4F142)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityWendigo.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "ithaqua"), id++)
            .name(mod_LavaCow.MODID + "." + "ithaqua")
            .tracker(80, 3, false)
            .egg(0x30180C, 0xFFFAEC)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityMimic.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "mimic"), id++)
            .name(mod_LavaCow.MODID + "." + "mimic")
            .tracker(80, 3, false)
            .egg(0xE168FF, 0x070000)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntitySludgeLord.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "sludgelord"), id++)
            .name(mod_LavaCow.MODID + "." + "sludgelord")
            .tracker(80, 3, false)
            .egg(0x282119, 0x81DDFF)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntitySludgeJet.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "sludgejet"), id++)
            .name(mod_LavaCow.MODID + "." + "sludgejet")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityLilSludge.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "lilsludge"), id++)
            .name(mod_LavaCow.MODID + "." + "lilsludge")
            .tracker(80, 3, false)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityWarSmallFireball.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "warsmallfireball"), id++)
            .name(mod_LavaCow.MODID + "." + "warsmallfireball")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityRaven.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "raven"), id++)
            .name(mod_LavaCow.MODID + "." + "raven")
            .tracker(80, 3, false)
            .egg(0x130D19, 0x192B3E)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityPtera.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "ptera"), id++)
            .name(mod_LavaCow.MODID + "." + "ptera")
            .tracker(80, 3, false)
            .egg(0x083318, 0xFC0202)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityVespa.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "vespa"), id++)
            .name(mod_LavaCow.MODID + "." + "vespa")
            .tracker(80, 3, false)
            .egg(0x85E214, 0xDA3119)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityScarecrow.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "scarecrow"), id++)
            .name(mod_LavaCow.MODID + "." + "scarecrow")
            .tracker(80, 3, false)
            .egg(0x9CA578, 0xD4891B)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityVespaCocoon.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "vespacocoon"), id++)
            .name(mod_LavaCow.MODID + "." + "vespacocoon")
            .tracker(80, 3, false)
            //.egg(0x85E214, 0xDA3119)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityPiranha.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "piranha"), id++)
            .name(mod_LavaCow.MODID + "." + "piranha")
            .tracker(80, 3, false)
            .egg(0x3E3E3E, 0xE34600)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityZombiePiranha.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "zombiepiranha"), id++)
            .name(mod_LavaCow.MODID + "." + "zombiepiranha")
            .tracker(80, 3, false)
            .egg(0x5D5D5D, 0x880909)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityPiranhaLauncher.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "piranhalauncher"), id++)
            .name(mod_LavaCow.MODID + "." + "piranhalauncher")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityBoneWorm.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "boneworm"), id++)
            .name(mod_LavaCow.MODID + "." + "boneworm")
            .tracker(80, 3, false)
            .egg(0x989898, 0x410E0E)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityAcidJet.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "acidjet"), id++)
            .name(mod_LavaCow.MODID + "." + "acidjet")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityHolyGrenade.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "holygrenade"), id++)
            .name(mod_LavaCow.MODID + "." + "holygrenade")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityPingu.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "pingu"), id++)
            .name(mod_LavaCow.MODID + "." + "pingu")
            .tracker(80, 3, false)
            .egg(0x77A9FF, 0x797979)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityUndertaker.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "undertaker"), id++)
            .name(mod_LavaCow.MODID + "." + "undertaker")
            .tracker(80, 3, false)
            .egg(0x3c424b, 0xA3AC93)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityUnburied.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "unburied"), id++)
            .name(mod_LavaCow.MODID + "." + "unburied")
            .tracker(80, 3, false)
            .egg(0xD4D9BA, 0x292C32)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityGhostRay.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "ghostray"), id++)
            .name(mod_LavaCow.MODID + "." + "ghostray")
            .tracker(80, 3, false)
            .egg(0x233A41, 0x7AFDFD)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityBanshee.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "banshee"), id++)
            .name(mod_LavaCow.MODID + "." + "banshee")
            .tracker(80, 3, false)
            .egg(0xA2A78D, 0x34363A)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityWeta.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "weta"), id++)
            .name(mod_LavaCow.MODID + "." + "weta")
            .tracker(80, 3, false)
            .egg(0x845336, 0xEACAA7)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityAvaton.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "avaton"), id++)
            .name(mod_LavaCow.MODID + "." + "avaton")
            .tracker(80, 3, false)
            .egg(0xAAA48E, 0x222829)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntityGhostBomb.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "ghostbomb"), id++)
            .name(mod_LavaCow.MODID + "." + "ghostbomb")
            .tracker(64, 1, true)
            .build(),
            
            EntityEntryBuilder.create()
            .entity(EntitySonicBomb.class)
            .id(new ResourceLocation(mod_LavaCow.MODID, "sonicbomb"), id++)
            .name(mod_LavaCow.MODID + "." + "sonicbomb")
            .tracker(64, 1, true)
            .build()
            );

    @EventBusSubscriber(modid = mod_LavaCow.MODID)
    public static class RegistrationHandler
    {
        /**
         * Register this mod's {@link EntityEntry}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEvent(final RegistryEvent.Register<EntityEntry> event)
        {
            //final IForgeRegistry<EntityEntry> registry = event.getRegistry();

            // DEBUG
            System.out.println("Registering entities");
            for (final EntityEntry entityEntry : SET_ENTITIES)
            {
            	// DEBUG
                System.out.println("Registering entity = " + entityEntry.getEntityClass());
                IForgeRegistry<EntityEntry> registry = event.getRegistry();
                registry.register(entityEntry);
            }
           
            //EntitySpawnPlacementRegistry.setPlacementType(EntityZombiePiranha.class, SpawnPlacementType.IN_WATER);
            //EntitySpawnPlacementRegistry.setPlacementType(EntityPiranha.class, SpawnPlacementType.IN_WATER);
            //EntitySpawnPlacementRegistry.setPlacementType(EntityPtera.class, SpawnPlacementType.IN_AIR);
            //EntitySpawnPlacementRegistry.setPlacementType(EntityVespa.class, SpawnPlacementType.IN_AIR);
            /*if(!Modconfig.ModBiomes_spawn)*/RegisterEntitySpawn();
        }
    }
    
    public static void RegisterEntitySpawn() {
        tweakEntitySpawn(EntityLavaCow.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Lavacow, 1, 1, BiomeDictionary.Type.DRY);
        tweakEntitySpawn(EntityZombieMushroom.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.Type.WET);
        tweakEntitySpawn(EntityZombieMushroom.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.Type.RIVER);
        tweakEntitySpawn(EntityFoglet.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.Type.WET);
        tweakEntitySpawn(EntityFoglet.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.Type.RIVER);
        tweakEntitySpawn(EntityZombieFrozen.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieFrozen, 8, 16, BiomeDictionary.Type.COLD);
        tweakEntitySpawn(EntityUndeadSwine.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_UndeadSwine, 4, 8, BiomeDictionary.Type.FOREST);
        tweakEntitySpawn(EntitySalamander.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Salamander, 4, 8, BiomeDictionary.Type.NETHER);
        tweakEntitySpawn(EntityWendigo.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Wendigo, 1, 1, BiomeDictionary.Type.CONIFEROUS);
		for(Type C : Type.getAll()) {
			tweakEntitySpawn(EntityMimic.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Mimic, 1, 1, C);
			tweakEntitySpawn(EntityUndertaker.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Undertaker, 1, 1, C);
		}
		tweakEntitySpawn(EntitySludgeLord.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_SludgeLord, 1, 1, BiomeDictionary.Type.SWAMP);
		tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.SPOOKY);
		tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.CONIFEROUS);
		tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.BEACH);
		tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 4, 8, BiomeDictionary.Type.JUNGLE);
		tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 2, 4, BiomeDictionary.Type.DRY);
		tweakEntitySpawn(EntityScarecrow.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Scarecrow, 1, 1, BiomeDictionary.Type.PLAINS);
		tweakEntitySpawn(EntityBoneWorm.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_BoneWorm, 1, 2, BiomeDictionary.Type.SANDY);
		tweakEntitySpawn(EntityPingu.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Pingu, 4, 8, BiomeDictionary.Type.SNOWY);
		tweakEntitySpawn(EntityGhostRay.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_GhostRay, 1, 2, BiomeDictionary.Type.DRY);
		tweakEntitySpawn(EntityGhostRay.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_GhostRay, 1, 2, BiomeDictionary.Type.OCEAN);
		tweakEntitySpawn(EntityBanshee.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Banshee, 1, 2, BiomeDictionary.Type.FOREST);
		tweakEntitySpawn(EntityBanshee.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Banshee, 1, 2, BiomeDictionary.Type.COLD);
		tweakEntitySpawn(EntityWeta.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Weta, 4, 8, BiomeDictionary.Type.SANDY);
		tweakEntitySpawn(EntityWeta.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Weta, 4, 8, BiomeDictionary.Type.SAVANNA);
		tweakEntitySpawn(EntityAvaton.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Avaton, 1, 2, BiomeDictionary.Type.SANDY);
		tweakEntitySpawn(EntityAvaton.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Avaton, 1, 2, BiomeDictionary.Type.SAVANNA);
    }
    
    private static boolean isInHell(Biome BiomeIn) {
    	for (Biome biome : BiomeDictionary.getBiomes(Type.NETHER)) {
    		if(BiomeIn.equals(biome))return true;
    	}
    	
    	return false;
    }
    
    private static boolean isInMushroom(Biome BiomeIn) {
    	for (Biome biome : BiomeDictionary.getBiomes(Type.MUSHROOM)) {
    		if(BiomeIn.equals(biome))return true;
    	}
    	
    	return false;
    }
    
    private static void tweakEntitySpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType creatureType, int weight, int min, int max, Type biomes) {
    	for (Biome biome : BiomeDictionary.getBiomes(biomes)) {
            if (biome != null && weight > 0 && !(isInHell(biome) ^ biomes.equals(Type.NETHER)) && !isInMushroom(biome)) {
            	EntityRegistry.addSpawn(entityClass, weight, min, max, creatureType, biome);
            }
    	}
    }
    
}
