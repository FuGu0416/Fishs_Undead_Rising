package com.Fishmod.mod_LavaCow.init;

import java.util.Set;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityAmberLord;
import com.Fishmod.mod_LavaCow.entities.EntityAvaton;
import com.Fishmod.mod_LavaCow.entities.EntityBanshee;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityCactyrant;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;
import com.Fishmod.mod_LavaCow.entities.EntityMummy;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;
import com.Fishmod.mod_LavaCow.entities.EntityPingu;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import com.Fishmod.mod_LavaCow.entities.EntityIsnachi;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntitySoulWorm;
import com.Fishmod.mod_LavaCow.entities.EntityUndeadSwine;
import com.Fishmod.mod_LavaCow.entities.EntityForsaken;
import com.Fishmod.mod_LavaCow.entities.EntityGraveRobber;
import com.Fishmod.mod_LavaCow.entities.EntityImp;
import com.Fishmod.mod_LavaCow.entities.EntityUndertaker;
import com.Fishmod.mod_LavaCow.entities.EntityVespaCocoon;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;
import com.Fishmod.mod_LavaCow.entities.EntityZombieFrozen;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityPiranha;
import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGhostSwarmer;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGraveRobberGhost;
import com.Fishmod.mod_LavaCow.entities.floating.EntitySeaHag;
import com.Fishmod.mod_LavaCow.entities.floating.EntityWraith;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityGhostRay;
import com.Fishmod.mod_LavaCow.entities.flying.EntityPtera;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;
import com.Fishmod.mod_LavaCow.entities.misc.EntityVespaBrood;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityAcidJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityFlameJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityKingsWrath;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityMothScales;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySandBurst;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySapJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySludgeJet;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySonicBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityWarSmallFireball;
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
import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
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
                    .tracker(64, 1, false)
                    .egg(0xFF2724, 0xFFDA24)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityZombieMushroom.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "zombiemushroom"), id++)
                    .name(mod_LavaCow.MODID + "." + "zombiemushroom")
                    .tracker(64, 1, false)
                    .egg(0xBCE0AC, 0x83631D)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityParasite.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "parasite"), id++)
                    .name(mod_LavaCow.MODID + "." + "parasite")
                    .tracker(64, 1, false)
                    .egg(0xAAFFEE, 0xBBFFEE)
                    .build(),

            // Used for the Brood Mother trait
            EntityEntryBuilder.create()
                    .entity(EntityVespaBrood.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "vespa_brood"), id++)
                    .name(mod_LavaCow.MODID + "." + "vespa_brood")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityFoglet.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "foglet"), id++)
                    .name(mod_LavaCow.MODID + "." + "foglet")
                    .tracker(64, 1, false)
                    .egg(0xCBD3B9, 0x41352F)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityIsnachi.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "isnachi"), id++)
                    .name(mod_LavaCow.MODID + "." + "isnachi")
                    .tracker(64, 1, false)
                    .egg(0x2F0B0D, 0xECD253)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityImp.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "imp"), id++)
                    .name(mod_LavaCow.MODID + "." + "imp")
                    .tracker(64, 1, false)
                    .egg(0xD03336, 0xFFD6A0)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityZombieFrozen.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "zombiefrozen"), id++)
                    .name(mod_LavaCow.MODID + "." + "zombiefrozen")
                    .tracker(64, 1, false)
                    .egg(0xAFE0E2, 0x59484F)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityUndeadSwine.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "undeadswine"), id++)
                    .name(mod_LavaCow.MODID + "." + "undeadswine")
                    .tracker(64, 1, false)
                    .egg(0x8A9B8A, 0x3E5C5A)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySalamander.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "salamander"), id++)
                    .name(mod_LavaCow.MODID + "." + "salamander")
                    .tracker(64, 1, false)
                    .egg(0x260606, 0xF4F142)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityWendigo.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "ithaqua"), id++)
                    .name(mod_LavaCow.MODID + "." + "ithaqua")
                    .tracker(64, 1, false)
                    .egg(0x30180C, 0xFFFAEC)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityMimic.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "mimic"), id++)
                    .name(mod_LavaCow.MODID + "." + "mimic")
                    .tracker(64, 1, false)
                    .egg(0xE168FF, 0x070000)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySludgeLord.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "sludgelord"), id++)
                    .name(mod_LavaCow.MODID + "." + "sludgelord")
                    .tracker(64, 1, false)
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
                    .tracker(64, 1, false)
                    .egg(0x2B231A, 0x7CBFA8)
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
                    .tracker(64, 1, false)
                    .egg(0x130D19, 0x192B3E)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityPtera.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "ptera"), id++)
                    .name(mod_LavaCow.MODID + "." + "ptera")
                    .tracker(80, 1, false)
                    .egg(0x083318, 0xFC0202)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityVespa.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "vespa"), id++)
                    .name(mod_LavaCow.MODID + "." + "vespa")
                    .tracker(80, 1, false)
                    .egg(0x85E214, 0xDA3119)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityScarecrow.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "scarecrow"), id++)
                    .name(mod_LavaCow.MODID + "." + "scarecrow")
                    .tracker(64, 1, false)
                    .egg(0x9CA578, 0xD4891B)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityVespaCocoon.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "vespacocoon"), id++)
                    .name(mod_LavaCow.MODID + "." + "vespacocoon")
                    .tracker(64, 1, false)
                    //.egg(0x85E214, 0xDA3119)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityPiranha.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "piranha"), id++)
                    .name(mod_LavaCow.MODID + "." + "piranha")
                    .tracker(64, 1, false)
                    .egg(0x3E3E3E, 0xE34600)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityZombiePiranha.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "zombiepiranha"), id++)
                    .name(mod_LavaCow.MODID + "." + "zombiepiranha")
                    .tracker(64, 1, false)
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
                    .tracker(64, 1, false)
                    .egg(0x989898, 0x410E0E)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySoulWorm.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "soulworm"), id++)
                    .name(mod_LavaCow.MODID + "." + "soulworm")
                    .tracker(64, 1, false)
                    .egg(0x36261F, 0x32EFEF)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityAcidJet.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "acidjet"), id++)
                    .name(mod_LavaCow.MODID + "." + "acidjet")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityFlameJet.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "flamejet"), id++)
                    .name(mod_LavaCow.MODID + "." + "flamejet")
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
                    .tracker(64, 1, false)
                    .egg(0x77A9FF, 0x797979)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityUndertaker.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "undertaker"), id++)
                    .name(mod_LavaCow.MODID + "." + "undertaker")
                    .tracker(64, 1, false)
                    .egg(0x3c424b, 0xA3AC93)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityUnburied.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "unburied"), id++)
                    .name(mod_LavaCow.MODID + "." + "unburied")
                    .tracker(64, 1, false)
                    .egg(0xD4D9BA, 0x292C32)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityGhostRay.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "ghostray"), id++)
                    .name(mod_LavaCow.MODID + "." + "ghostray")
                    .tracker(80, 1, false)
                    .egg(0x233A41, 0x7AFDFD)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityBanshee.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "banshee"), id++)
                    .name(mod_LavaCow.MODID + "." + "banshee")
                    .tracker(80, 1, false)
                    .egg(0xA2A78D, 0x34363A)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityWeta.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "weta"), id++)
                    .name(mod_LavaCow.MODID + "." + "weta")
                    .tracker(64, 1, false)
                    .egg(0x845336, 0xEACAA7)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityAvaton.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "avaton"), id++)
                    .name(mod_LavaCow.MODID + "." + "avaton")
                    .tracker(80, 1, false)
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
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityForsaken.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "forsaken"), id++)
                    .name(mod_LavaCow.MODID + "." + "forsaken")
                    .tracker(64, 1, false)
                    .egg(12698049, 4802889)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySkeletonKing.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "skeletonking"), id++)
                    .name(mod_LavaCow.MODID + "." + "skeletonking")
                    .tracker(64, 1, false)
                    .egg(0x2F2A2A, 0xA2A1A1)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySandBurst.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "sandburst"), id++)
                    .name(mod_LavaCow.MODID + "." + "sandburst")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityDeathCoil.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "deathcoil"), id++)
                    .name(mod_LavaCow.MODID + "." + "deathcoil")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityMummy.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "mummy"), id++)
                    .name(mod_LavaCow.MODID + "." + "mummy")
                    .tracker(64, 1, false)
                    .egg(0xE9DAAE, 0x9A8157)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityCactoid.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "cactoid"), id++)
                    .name(mod_LavaCow.MODID + "." + "cactoid")
                    .tracker(64, 1, false)
                    .egg(0x649832, 0xFFF25F)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityCactyrant.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "cactyrant"), id++)
                    .name(mod_LavaCow.MODID + "." + "cactyrant")
                    .tracker(64, 1, false)
                    .egg(0x649832, 0x426520)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityCactusThorn.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "cactusthorn"), id++)
                    .name(mod_LavaCow.MODID + "." + "cactusthorn")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySeaHag.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "sea_hag"), id++)
                    .name(mod_LavaCow.MODID + "." + "sea_hag")
                    .tracker(80, 1, false)
                    .egg(0x44AD9A, 0x4ADC00)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityGraveRobber.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "grave_robber"), id++)
                    .name(mod_LavaCow.MODID + "." + "grave_robber")
                    .tracker(64, 1, false)
                    .egg(0x40433E, 0x959B9B)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityGraveRobberGhost.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "grave_robber_ghost"), id++)
                    .name(mod_LavaCow.MODID + "." + "grave_robber_ghost")
                    .tracker(80, 1, false)
                    .egg(0x7AF2FF, 0x40433E)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityWraith.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "wraith"), id++)
                    .name(mod_LavaCow.MODID + "." + "wraith")
                    .tracker(80, 1, false)
                    .egg(0x2DE6FD, 0x00353B)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityGhostSwarmer.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "ghost_swarmer"), id++)
                    .name(mod_LavaCow.MODID + "." + "ghost_swarmer")
                    .tracker(80, 1, false)
                    .egg(0xA4F3F3, 0xC31919)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityAmberLord.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "amberlord"), id++)
                    .name(mod_LavaCow.MODID + "." + "amberlord")
                    .tracker(64, 1, false)
                    .egg(0x131313, 0xFFA82C)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityScarab.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "amber_scarab"), id++)
                    .name(mod_LavaCow.MODID + "." + "amber_scarab")
                    .tracker(64, 1, false)
                    .egg(0x282219, 0xFFCD55)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntitySapJet.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "sapjet"), id++)
                    .name(mod_LavaCow.MODID + "." + "sapjet")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityEnigmoth.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "enigmoth"), id++)
                    .name(mod_LavaCow.MODID + "." + "enigmoth")
                    .tracker(80, 1, false)
                    .egg(0x0D0B11, 0xA675E9)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityEnigmothLarva.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "enigmoth_larva"), id++)
                    .name(mod_LavaCow.MODID + "." + "enigmoth_larva")
                    .tracker(64, 1, false)
                    .egg(0x797AA0, 0xD2AAD5)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityMothScales.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "moth_scales"), id++)
                    .name(mod_LavaCow.MODID + "." + "moth_scales")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityBomb.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "basicbomb"), id++)
                    .name(mod_LavaCow.MODID + "." + "basicbomb")
                    .tracker(64, 1, true)
                    .build(),

            EntityEntryBuilder.create()
                    .entity(EntityKingsWrath.class)
                    .id(new ResourceLocation(mod_LavaCow.MODID, "kings_wrath"), id++)
                    .name(mod_LavaCow.MODID + "." + "kings_wrath")
                    .tracker(64, 1, true)
                    .build()
    );

    @EventBusSubscriber(modid = mod_LavaCow.MODID)
    public static class RegistrationHandler {
        /**
         * Register this mod's {@link EntityEntry}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEvent(final RegistryEvent.Register<EntityEntry> event) {
            //final IForgeRegistry<EntityEntry> registry = event.getRegistry();

            // DEBUG
            System.out.println("Registering entities");
            for (final EntityEntry entityEntry : SET_ENTITIES) {
                // DEBUG
                System.out.println("Registering entity = " + entityEntry.getEntityClass());
                IForgeRegistry<EntityEntry> registry = event.getRegistry();
                registry.register(entityEntry);
            }

            RegisterEntitySpawn();
        }
    }

    public static void RegisterEntitySpawn() {
        tweakEntitySpawn(EntityZombieMushroom.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.Type.WET);
        tweakEntitySpawn(EntityZombieMushroom.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieMushroom, 8, 16, BiomeDictionary.Type.RIVER);
        tweakEntitySpawn(EntityFoglet.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.Type.WET);
        tweakEntitySpawn(EntityFoglet.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Foglet, 8, 16, BiomeDictionary.Type.RIVER);
        tweakEntitySpawn(EntityIsnachi.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Isnachi, 8, 16, BiomeDictionary.Type.JUNGLE);
        tweakEntitySpawn(EntityImp.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Imp, 8, 16, BiomeDictionary.Type.NETHER);
        tweakEntitySpawn(EntityZombieFrozen.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_ZombieFrozen, 8, 16, BiomeDictionary.Type.COLD);
        tweakEntitySpawn(EntityUndeadSwine.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_UndeadSwine, 4, 8, BiomeDictionary.Type.FOREST);
        tweakEntitySpawn(EntitySalamander.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Salamander, 4, 8, BiomeDictionary.Type.NETHER);
        tweakEntitySpawn(EntityWendigo.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Wendigo, 1, 1, BiomeDictionary.Type.CONIFEROUS);
        for (Type C : Type.getAll()) {
            if (!C.equals(Type.NETHER) && !C.equals(Type.END)) {
                tweakEntitySpawn(EntityMimic.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Mimic, 1, 1, C);
                tweakEntitySpawn(EntityUndertaker.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Undertaker, 1, 1, C);
            }
        }
        tweakEntitySpawn(EntityUnburied.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Unburied, 8, 16, BiomeDictionary.Type.SPOOKY);
        tweakEntitySpawn(EntitySludgeLord.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_SludgeLord, 1, 1, BiomeDictionary.Type.SWAMP);
        tweakEntitySpawn(EntityAmberLord.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_AmberLord, 1, 1, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.SPOOKY);
        tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.CONIFEROUS);
        tweakEntitySpawn(EntityRaven.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Raven, 4, 8, BiomeDictionary.Type.BEACH);
        tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 4, 8, BiomeDictionary.Type.JUNGLE);
        tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 4, 8, BiomeDictionary.Type.SAVANNA);
        tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 4, 8, BiomeDictionary.Type.SWAMP);
        tweakEntitySpawn(EntityPtera.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ptera, 4, 8, BiomeDictionary.Type.DRY);
        tweakEntitySpawn(EntityVespa.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Vespa, 2, 4, BiomeDictionary.Type.JUNGLE);
        tweakEntitySpawn(EntityScarecrow.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Scarecrow, 1, 1, BiomeDictionary.Type.PLAINS);
        tweakEntitySpawn(EntityBoneWorm.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_BoneWorm, 1, 2, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntitySoulWorm.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_SoulWorm, 1, 2, BiomeDictionary.Type.NETHER);
        tweakEntitySpawn(EntityPingu.class, EnumCreatureType.CREATURE, Modconfig.pSpawnRate_Pingu, 4, 8, BiomeDictionary.Type.SNOWY);
        tweakEntitySpawn(EntityGhostRay.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_GhostRay, 1, 2, BiomeDictionary.Type.DRY);
        tweakEntitySpawn(EntityGhostRay.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_GhostRay_End, 1, 2, BiomeDictionary.Type.END);
        tweakEntitySpawn(EntityWeta.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Weta, 4, 8, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityWeta.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Weta, 4, 8, BiomeDictionary.Type.SAVANNA);
        tweakEntitySpawn(EntityBanshee.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Banshee, 1, 2, Type.RARE);
        tweakEntitySpawn(EntityBanshee.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Banshee, 1, 2, Type.HILLS);
        tweakEntitySpawn(EntityBanshee.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Banshee, 1, 2, Type.MOUNTAIN);
        tweakEntitySpawn(EntityAvaton.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Avaton, 1, 2, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityAvaton.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Avaton, 1, 2, BiomeDictionary.Type.SAVANNA);
        tweakEntitySpawn(EntityMummy.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Mummy, 8, 16, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityForsaken.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Forsaken, 4, 8, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityCactyrant.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Cactyrant, 1, 2, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntityCactoid.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Cactoid, 4, 8, BiomeDictionary.Type.SANDY);
        tweakEntitySpawn(EntitySeaHag.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Sea_Hag, 1, 2, BiomeDictionary.Type.BEACH);
        tweakEntitySpawn(EntityGraveRobber.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Grave_Robber, 1, 1, BiomeDictionary.Type.SPOOKY);
        tweakEntitySpawn(EntityGraveRobberGhost.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Grave_Robber_Ghost, 1, 1, BiomeDictionary.Type.SPOOKY);
        tweakEntitySpawn(EntityWraith.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Wraith, 1, 2, BiomeDictionary.Type.FOREST);
        tweakEntitySpawn(EntityGhostSwarmer.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Ghost_Swarmer, 1, 2, BiomeDictionary.Type.DRY);
        tweakEntitySpawn(EntityEnigmoth.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Enigmoth, 1, 2, BiomeDictionary.Type.END);
        tweakEntitySpawn(EntityEnigmothLarva.class, EnumCreatureType.MONSTER, Modconfig.pSpawnRate_Enigmoth_Larva, 1, 2, BiomeDictionary.Type.END);

        EntitySpawnPlacementRegistry.setPlacementType(EntityLavaCow.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityZombieMushroom.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityParasite.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityFoglet.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityIsnachi.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityZombieFrozen.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityUndeadSwine.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySalamander.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWendigo.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityMimic.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySludgeLord.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityLilSludge.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityRaven.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityPtera.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityVespa.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityScarecrow.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityPiranha.class, SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityZombiePiranha.class, SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityBoneWorm.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySoulWorm.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityPingu.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityScarecrow.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityUndertaker.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityGhostRay.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityBanshee.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWeta.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityAvaton.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityForsaken.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySkeletonKing.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityMummy.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityCactyrant.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityCactoid.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntitySeaHag.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityGraveRobber.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityGraveRobberGhost.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWraith.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityGhostSwarmer.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityAmberLord.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityScarab.class, SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(EntityEnigmoth.class, SpawnPlacementType.IN_AIR);
        EntitySpawnPlacementRegistry.setPlacementType(EntityEnigmothLarva.class, SpawnPlacementType.ON_GROUND);
    }

    private static boolean isInHell(Biome BiomeIn) {
        for (Biome biome : BiomeDictionary.getBiomes(Type.NETHER)) {
            if (BiomeIn.equals(biome)) return true;
        }

        return false;
    }

    private static boolean isInEnd(Biome BiomeIn) {
        for (Biome biome : BiomeDictionary.getBiomes(Type.END)) {
            if (BiomeIn.equals(biome)) return true;
        }

        return false;
    }

    private static boolean isInMushroom(Biome BiomeIn) {
        for (Biome biome : BiomeDictionary.getBiomes(Type.MUSHROOM)) {
            if (BiomeIn.equals(biome)) return true;
        }

        return false;
    }

    private static void tweakEntitySpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType creatureType, int weight, int min, int max, Type biomes) {
        for (Biome biome : BiomeDictionary.getBiomes(biomes)) {
            if (biome != null && weight > 0 && !(isInHell(biome) ^ biomes.equals(Type.NETHER)) && !(isInEnd(biome) ^ biomes.equals(Type.END)) && !isInMushroom(biome)) {
                EntityRegistry.addSpawn(entityClass, weight, min, max, creatureType, biome);
            }
        }
    }

}
