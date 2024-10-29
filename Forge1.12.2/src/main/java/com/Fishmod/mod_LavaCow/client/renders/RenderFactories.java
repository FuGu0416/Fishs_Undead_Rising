package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.renders.entity.RenderAcidJet;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderAmberLord;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderAvaton;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderBanshee;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderBomb;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderBoneWorm;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderCactoid;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderCactusThorn;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderCactyrant;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderDeathCoil;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderEnigmoth;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderEnigmothLarva;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderFlameJet;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderFoglet;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderForsaken;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderGhostBomb;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderGhostRay;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderGhostSwarmer;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderGraveRobber;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderGraveRobberGhost;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderHolyGrenade;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderImp;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderKingsWrath;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderLavaCow;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderLilSludge;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderMimic;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderMothScales;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderMummy;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderParasite;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderPingu;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderPiranhaLauncher;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderPtera;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderRaven;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSalamander;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSandBurst;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSapJet;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderScarab;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderScarecrow;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSeaHag;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSkeletonKing;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderIsnachi;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSludgeJet;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSludgeLord;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSonicBomb;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSoulWorm;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderUnburied;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderUndeadSwine;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderUndertaker;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderVespa;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderVespaCocoon;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderWendigo;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderWeta;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderWraith;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderZombieFrozen;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderZombieMushroom;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderZombiePiranha;
import com.Fishmod.mod_LavaCow.client.renders.item.RenderBeastClaw;
import com.Fishmod.mod_LavaCow.client.renders.item.RenderSkeletonKingMace;
import com.Fishmod.mod_LavaCow.client.renders.item.RenderVespaShield;
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
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderFactories {

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityLavaCow.class, RenderFactoryEntityLavaCow.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieMushroom.class, RenderFactoryEntityZombieMushroom.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityParasite.class, RenderFactoryEntityParasite.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityFoglet.class, RenderFactoryEntityFoglet.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityIsnachi.class, RenderFactoryEntityIsnachi.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, RenderFactoryEntityImp.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieFrozen.class, RenderFactoryEntityZombieFrozen.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityUndeadSwine.class, RenderFactoryEntityUndeadSwine.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySalamander.class, RenderFactoryEntitySalamander.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityWendigo.class, RenderFactoryEntityWendigo.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderFactoryEntityMimic.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySludgeLord.class, RenderFactoryEntitySludgeLord.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityAmberLord.class, RenderFactoryEntityAmberLord.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySludgeJet.class, RenderFactoryEntitySludgeJet.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySapJet.class, RenderFactoryEntitySapJet.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityLilSludge.class, RenderFactoryEntityLilSludge.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityWarSmallFireball.class, RenderFactoryEntityWarSmallFireball.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityRaven.class, RenderFactoryEntityRaven.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityPtera.class, RenderFactoryEntityPtera.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityVespa.class, RenderFactoryEntityVespa.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityScarecrow.class, RenderFactoryEntityScarecrow.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityVespaCocoon.class, RenderFactoryEntityVespaCocoon.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityPiranha.class, RenderFactoryEntityPiranha.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombiePiranha.class, RenderFactoryEntityZombiePiranha.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityPiranhaLauncher.class, RenderFactoryEntityPiranhaLauncher.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoneWorm.class, RenderFactoryEntityBoneWorm.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySoulWorm.class, RenderFactoryEntitySoulWorm.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityAcidJet.class, RenderFactoryEntityAcidJet.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlameJet.class, RenderFactoryEntityFlameJet.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityHolyGrenade.class, RenderFactoryEntityHolyGrenade.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityPingu.class, RenderFactoryEntityPingu.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityUndertaker.class, RenderFactoryEntityUndertaker.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityUnburied.class, RenderFactoryEntityUnburied.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostRay.class, RenderFactoryEntityGhostRay.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityBanshee.class, RenderFactoryEntityBanshee.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityWeta.class, RenderFactoryEntityWeta.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityAvaton.class, RenderFactoryEntityAvaton.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostBomb.class, RenderFactoryEntityGhostBomb.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySonicBomb.class, RenderFactoryEntitySonicBomb.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityForsaken.class, RenderFactoryEntityForsaken.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonKing.class, RenderFactoryEntitySkeletonKing.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySandBurst.class, RenderFactoryEntitySandBurst.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityDeathCoil.class, RenderFactoryEntityDeathCoil.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class, RenderFactoryEntityMummy.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityCactoid.class, RenderFactoryEntityCactoid.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityCactyrant.class, RenderFactoryEntityCactyrant.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityCactusThorn.class, RenderFactoryEntityCactusThorn.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySeaHag.class, RenderFactoryEntitySeaHag.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityGraveRobber.class, RenderFactoryEntityGraveRobber.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityGraveRobberGhost.class, RenderFactoryEntityGraveRobberGhost.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityWraith.class, RenderFactoryEntityWraith.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostSwarmer.class, RenderFactoryEntityGhostSwarmer.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityScarab.class, RenderFactoryEntityScarab.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnigmoth.class, RenderFactoryEntityEnigmoth.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnigmothLarva.class, RenderFactoryEntityEnigmothLarva.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityMothScales.class, RenderFactoryEntityMothScales.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, RenderFactoryEntityBasicBomb.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityKingsWrath.class, RenderFactoryEntityKingsWrath.INSTANCE);

        FishItems.VESPA_SHIELD.setTileEntityItemStackRenderer(new RenderVespaShield());
        FishItems.BEAST_CLAW.setTileEntityItemStackRenderer(new RenderBeastClaw());
        FishItems.SKELETONKING_MACE.setTileEntityItemStackRenderer(new RenderSkeletonKingMace());
    }

    public static class RenderFactoryEntityLavaCow implements IRenderFactory<EntityLavaCow> {
        public final static RenderFactoryEntityLavaCow INSTANCE = new RenderFactoryEntityLavaCow();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityLavaCow> createRenderFor(RenderManager manager) {
            return new RenderLavaCow(manager);
        }
    }

    public static class RenderFactoryEntityZombieMushroom implements IRenderFactory<EntityZombieMushroom> {
        public final static RenderFactoryEntityZombieMushroom INSTANCE = new RenderFactoryEntityZombieMushroom();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityZombieMushroom> createRenderFor(RenderManager manager) {
            return new RenderZombieMushroom(manager);
        }
    }

    public static class RenderFactoryEntityParasite implements IRenderFactory<EntityParasite> {
        public final static RenderFactoryEntityParasite INSTANCE = new RenderFactoryEntityParasite();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityParasite> createRenderFor(RenderManager manager) {
            return new RenderParasite(manager);
        }
    }

    public static class RenderFactoryEntityFoglet implements IRenderFactory<EntityFoglet> {
        public final static RenderFactoryEntityFoglet INSTANCE = new RenderFactoryEntityFoglet();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityFoglet> createRenderFor(RenderManager manager) {
            return new RenderFoglet(manager);
        }
    }

    public static class RenderFactoryEntityIsnachi implements IRenderFactory<EntityIsnachi> {
        public final static RenderFactoryEntityIsnachi INSTANCE = new RenderFactoryEntityIsnachi();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityIsnachi> createRenderFor(RenderManager manager) {
            return new RenderIsnachi(manager);
        }
    }

    public static class RenderFactoryEntityImp implements IRenderFactory<EntityImp> {
        public final static RenderFactoryEntityImp INSTANCE = new RenderFactoryEntityImp();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityImp> createRenderFor(RenderManager manager) {
            return new RenderImp(manager);
        }
    }

    public static class RenderFactoryEntityZombieFrozen implements IRenderFactory<EntityZombieFrozen> {
        public final static RenderFactoryEntityZombieFrozen INSTANCE = new RenderFactoryEntityZombieFrozen();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityZombieFrozen> createRenderFor(RenderManager manager) {
            return new RenderZombieFrozen(manager);
        }
    }

    public static class RenderFactoryEntityUndeadSwine implements IRenderFactory<EntityUndeadSwine> {
        public final static RenderFactoryEntityUndeadSwine INSTANCE = new RenderFactoryEntityUndeadSwine();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityUndeadSwine> createRenderFor(RenderManager manager) {
            return new RenderUndeadSwine(manager);
        }
    }

    public static class RenderFactoryEntitySalamander implements IRenderFactory<EntitySalamander> {
        public final static RenderFactoryEntitySalamander INSTANCE = new RenderFactoryEntitySalamander();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntitySalamander> createRenderFor(RenderManager manager) {
            return new RenderSalamander(manager);
        }
    }

    public static class RenderFactoryEntityWendigo implements IRenderFactory<EntityWendigo> {
        public final static RenderFactoryEntityWendigo INSTANCE = new RenderFactoryEntityWendigo();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityWendigo> createRenderFor(RenderManager manager) {
            return new RenderWendigo(manager);
        }
    }

    public static class RenderFactoryEntityMimic implements IRenderFactory<EntityMimic> {
        public final static RenderFactoryEntityMimic INSTANCE = new RenderFactoryEntityMimic();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityMimic> createRenderFor(RenderManager manager) {
            return new RenderMimic(manager);
        }
    }

    public static class RenderFactoryEntitySludgeLord implements IRenderFactory<EntitySludgeLord> {
        public final static RenderFactoryEntitySludgeLord INSTANCE = new RenderFactoryEntitySludgeLord();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntitySludgeLord> createRenderFor(RenderManager manager) {
            return new RenderSludgeLord(manager);
        }
    }

    public static class RenderFactoryEntityAmberLord implements IRenderFactory<EntityAmberLord> {
        public final static RenderFactoryEntityAmberLord INSTANCE = new RenderFactoryEntityAmberLord();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityAmberLord> createRenderFor(RenderManager manager) {
            return new RenderAmberLord(manager);
        }
    }

    public static class RenderFactoryEntitySludgeJet implements IRenderFactory<EntityFireball> {
        public final static RenderFactoryEntitySludgeJet INSTANCE = new RenderFactoryEntitySludgeJet();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityFireball> createRenderFor(RenderManager manager) {
            return new RenderSludgeJet(manager);
        }
    }

    public static class RenderFactoryEntitySapJet implements IRenderFactory<EntityFireball> {
        public final static RenderFactoryEntitySapJet INSTANCE = new RenderFactoryEntitySapJet();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityFireball> createRenderFor(RenderManager manager) {
            return new RenderSapJet(manager);
        }
    }

    public static class RenderFactoryEntityLilSludge implements IRenderFactory<EntityLilSludge> {
        public final static RenderFactoryEntityLilSludge INSTANCE = new RenderFactoryEntityLilSludge();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityLilSludge> createRenderFor(RenderManager manager) {
            return new RenderLilSludge(manager);
        }
    }

    public static class RenderFactoryEntityWarSmallFireball implements IRenderFactory<EntityWarSmallFireball> {
        public final static RenderFactoryEntityWarSmallFireball INSTANCE = new RenderFactoryEntityWarSmallFireball();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderFireball createRenderFor(RenderManager manager) {
            return new RenderFireball(manager, 0.5F);
        }
    }

    public static class RenderFactoryEntityRaven implements IRenderFactory<EntityRaven> {
        public final static RenderFactoryEntityRaven INSTANCE = new RenderFactoryEntityRaven();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityRaven> createRenderFor(RenderManager manager) {
            return new RenderRaven(manager);
        }
    }

    public static class RenderFactoryEntityPtera implements IRenderFactory<EntityPtera> {
        public final static RenderFactoryEntityPtera INSTANCE = new RenderFactoryEntityPtera();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityPtera> createRenderFor(RenderManager manager) {
            return new RenderPtera(manager);
        }
    }

    public static class RenderFactoryEntityVespa implements IRenderFactory<EntityVespa> {
        public final static RenderFactoryEntityVespa INSTANCE = new RenderFactoryEntityVespa();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityVespa> createRenderFor(RenderManager manager) {
            return new RenderVespa(manager);
        }
    }

    public static class RenderFactoryEntityScarecrow implements IRenderFactory<EntityScarecrow> {
        public final static RenderFactoryEntityScarecrow INSTANCE = new RenderFactoryEntityScarecrow();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityScarecrow> createRenderFor(RenderManager manager) {
            return new RenderScarecrow(manager);
        }
    }

    public static class RenderFactoryEntityVespaCocoon implements IRenderFactory<EntityVespaCocoon> {
        public final static RenderFactoryEntityVespaCocoon INSTANCE = new RenderFactoryEntityVespaCocoon();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityVespaCocoon> createRenderFor(RenderManager manager) {
            return new RenderVespaCocoon(manager);
        }
    }

    public static class RenderFactoryEntityPiranha implements IRenderFactory<EntityPiranha> {
        public final static RenderFactoryEntityPiranha INSTANCE = new RenderFactoryEntityPiranha();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityZombiePiranha> createRenderFor(RenderManager manager) {
            return new RenderZombiePiranha(manager);
        }
    }

    public static class RenderFactoryEntityZombiePiranha implements IRenderFactory<EntityZombiePiranha> {
        public final static RenderFactoryEntityZombiePiranha INSTANCE = new RenderFactoryEntityZombiePiranha();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityZombiePiranha> createRenderFor(RenderManager manager) {
            return new RenderZombiePiranha(manager);
        }
    }

    public static class RenderFactoryEntityPiranhaLauncher implements IRenderFactory<EntityPiranhaLauncher> {
        public final static RenderFactoryEntityPiranhaLauncher INSTANCE = new RenderFactoryEntityPiranhaLauncher();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityPiranhaLauncher> createRenderFor(RenderManager manager) {
            return new RenderPiranhaLauncher(manager);
        }
    }

    public static class RenderFactoryEntityBoneWorm implements IRenderFactory<EntityBoneWorm> {
        public final static RenderFactoryEntityBoneWorm INSTANCE = new RenderFactoryEntityBoneWorm();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityBoneWorm> createRenderFor(RenderManager manager) {
            return new RenderBoneWorm(manager);
        }
    }

    public static class RenderFactoryEntitySoulWorm implements IRenderFactory<EntitySoulWorm> {
        public final static RenderFactoryEntitySoulWorm INSTANCE = new RenderFactoryEntitySoulWorm();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntitySoulWorm> createRenderFor(RenderManager manager) {
            return new RenderSoulWorm(manager);
        }
    }

    public static class RenderFactoryEntityAcidJet implements IRenderFactory<EntityAcidJet> {
        public final static RenderFactoryEntityAcidJet INSTANCE = new RenderFactoryEntityAcidJet();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityAcidJet> createRenderFor(RenderManager manager) {
            return new RenderAcidJet(manager, FishItems.POISONSPORE);
        }
    }

    public static class RenderFactoryEntityFlameJet implements IRenderFactory<EntityFlameJet> {
        public final static RenderFactoryEntityFlameJet INSTANCE = new RenderFactoryEntityFlameJet();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderFlameJet createRenderFor(RenderManager manager) {
            return new RenderFlameJet(manager, FishItems.ECTOPLASM_MASS);
        }
    }

    public static class RenderFactoryEntityHolyGrenade implements IRenderFactory<EntityHolyGrenade> {
        public final static RenderFactoryEntityHolyGrenade INSTANCE = new RenderFactoryEntityHolyGrenade();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityHolyGrenade> createRenderFor(RenderManager manager) {
            return new RenderHolyGrenade(manager, FishItems.HOLY_GRENADE);
        }
    }

    public static class RenderFactoryEntityPingu implements IRenderFactory<EntityPingu> {
        public final static RenderFactoryEntityPingu INSTANCE = new RenderFactoryEntityPingu();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityPingu> createRenderFor(RenderManager manager) {
            return new RenderPingu(manager);
        }
    }

    public static class RenderFactoryEntityUndertaker implements IRenderFactory<EntityUndertaker> {
        public final static RenderFactoryEntityUndertaker INSTANCE = new RenderFactoryEntityUndertaker();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityUndertaker> createRenderFor(RenderManager manager) {
            return new RenderUndertaker(manager);
        }
    }

    public static class RenderFactoryEntityUnburied implements IRenderFactory<EntityUnburied> {
        public final static RenderFactoryEntityUnburied INSTANCE = new RenderFactoryEntityUnburied();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityUnburied> createRenderFor(RenderManager manager) {
            return new RenderUnburied(manager);
        }
    }

    public static class RenderFactoryEntityGhostRay implements IRenderFactory<EntityGhostRay> {
        public final static RenderFactoryEntityGhostRay INSTANCE = new RenderFactoryEntityGhostRay();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityGhostRay> createRenderFor(RenderManager manager) {
            return new RenderGhostRay(manager);
        }
    }

    public static class RenderFactoryEntityBanshee implements IRenderFactory<EntityBanshee> {
        public final static RenderFactoryEntityBanshee INSTANCE = new RenderFactoryEntityBanshee();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityBanshee> createRenderFor(RenderManager manager) {
            return new RenderBanshee(manager);
        }
    }

    public static class RenderFactoryEntityWeta implements IRenderFactory<EntityWeta> {
        public final static RenderFactoryEntityWeta INSTANCE = new RenderFactoryEntityWeta();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityWeta> createRenderFor(RenderManager manager) {
            return new RenderWeta(manager);
        }
    }

    public static class RenderFactoryEntityAvaton implements IRenderFactory<EntityAvaton> {
        public final static RenderFactoryEntityAvaton INSTANCE = new RenderFactoryEntityAvaton();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityAvaton> createRenderFor(RenderManager manager) {
            return new RenderAvaton(manager);
        }
    }

    public static class RenderFactoryEntityGhostBomb implements IRenderFactory<EntityGhostBomb> {
        public final static RenderFactoryEntityGhostBomb INSTANCE = new RenderFactoryEntityGhostBomb();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderGhostBomb createRenderFor(RenderManager manager) {
            return new RenderGhostBomb(manager, FishItems.GHOSTBOMB);
        }
    }

    public static class RenderFactoryEntitySonicBomb implements IRenderFactory<EntitySonicBomb> {
        public final static RenderFactoryEntitySonicBomb INSTANCE = new RenderFactoryEntitySonicBomb();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntitySonicBomb> createRenderFor(RenderManager manager) {
            return new RenderSonicBomb(manager, FishItems.SONICBOMB);
        }
    }

    public static class RenderFactoryEntityForsaken implements IRenderFactory<EntityForsaken> {
        public final static RenderFactoryEntityForsaken INSTANCE = new RenderFactoryEntityForsaken();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderForsaken createRenderFor(RenderManager manager) {
            return new RenderForsaken(manager);
        }
    }

    public static class RenderFactoryEntitySkeletonKing implements IRenderFactory<EntitySkeletonKing> {
        public final static RenderFactoryEntitySkeletonKing INSTANCE = new RenderFactoryEntitySkeletonKing();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderSkeletonKing createRenderFor(RenderManager manager) {
            return new RenderSkeletonKing(manager);
        }
    }

    public static class RenderFactoryEntitySandBurst implements IRenderFactory<EntitySandBurst> {
        public final static RenderFactoryEntitySandBurst INSTANCE = new RenderFactoryEntitySandBurst();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderSandBurst createRenderFor(RenderManager manager) {
            return new RenderSandBurst(manager);
        }
    }

    public static class RenderFactoryEntityDeathCoil implements IRenderFactory<EntityDeathCoil> {
        public final static RenderFactoryEntityDeathCoil INSTANCE = new RenderFactoryEntityDeathCoil();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderDeathCoil createRenderFor(RenderManager manager) {
            return new RenderDeathCoil(manager);
        }
    }

    public static class RenderFactoryEntityMummy implements IRenderFactory<EntityMummy> {
        public final static RenderFactoryEntityMummy INSTANCE = new RenderFactoryEntityMummy();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityMummy> createRenderFor(RenderManager manager) {
            return new RenderMummy(manager);
        }
    }

    public static class RenderFactoryEntityCactusThorn implements IRenderFactory<EntityCactusThorn> {
        public final static RenderFactoryEntityCactusThorn INSTANCE = new RenderFactoryEntityCactusThorn();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderCactusThorn createRenderFor(RenderManager manager) {
            return new RenderCactusThorn(manager);
        }
    }

    public static class RenderFactoryEntityCactoid implements IRenderFactory<EntityCactoid> {
        public final static RenderFactoryEntityCactoid INSTANCE = new RenderFactoryEntityCactoid();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityCactoid> createRenderFor(RenderManager manager) {
            return new RenderCactoid(manager);
        }
    }

    public static class RenderFactoryEntityCactyrant implements IRenderFactory<EntityCactyrant> {
        public final static RenderFactoryEntityCactyrant INSTANCE = new RenderFactoryEntityCactyrant();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityCactyrant> createRenderFor(RenderManager manager) {
            return new RenderCactyrant(manager);
        }
    }

    public static class RenderFactoryEntitySeaHag implements IRenderFactory<EntitySeaHag> {
        public final static RenderFactoryEntitySeaHag INSTANCE = new RenderFactoryEntitySeaHag();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntitySeaHag> createRenderFor(RenderManager manager) {
            return new RenderSeaHag(manager);
        }
    }

    public static class RenderFactoryEntityGraveRobber implements IRenderFactory<EntityGraveRobber> {
        public final static RenderFactoryEntityGraveRobber INSTANCE = new RenderFactoryEntityGraveRobber();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityGraveRobber> createRenderFor(RenderManager manager) {
            return new RenderGraveRobber(manager);
        }
    }

    public static class RenderFactoryEntityGraveRobberGhost implements IRenderFactory<EntityGraveRobberGhost> {
        public final static RenderFactoryEntityGraveRobberGhost INSTANCE = new RenderFactoryEntityGraveRobberGhost();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityGraveRobberGhost> createRenderFor(RenderManager manager) {
            return new RenderGraveRobberGhost(manager);
        }
    }

    public static class RenderFactoryEntityWraith implements IRenderFactory<EntityWraith> {
        public final static RenderFactoryEntityWraith INSTANCE = new RenderFactoryEntityWraith();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityWraith> createRenderFor(RenderManager manager) {
            return new RenderWraith(manager);
        }
    }

    public static class RenderFactoryEntityGhostSwarmer implements IRenderFactory<EntityGhostSwarmer> {
        public final static RenderFactoryEntityGhostSwarmer INSTANCE = new RenderFactoryEntityGhostSwarmer();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityGhostSwarmer> createRenderFor(RenderManager manager) {
            return new RenderGhostSwarmer(manager);
        }
    }

    public static class RenderFactoryEntityScarab implements IRenderFactory<EntityScarab> {
        public final static RenderFactoryEntityScarab INSTANCE = new RenderFactoryEntityScarab();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityScarab> createRenderFor(RenderManager manager) {
            return new RenderScarab(manager);
        }
    }

    public static class RenderFactoryEntityEnigmoth implements IRenderFactory<EntityEnigmoth> {
        public final static RenderFactoryEntityEnigmoth INSTANCE = new RenderFactoryEntityEnigmoth();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityEnigmoth> createRenderFor(RenderManager manager) {
            return new RenderEnigmoth(manager);
        }
    }

    public static class RenderFactoryEntityEnigmothLarva implements IRenderFactory<EntityEnigmothLarva> {
        public final static RenderFactoryEntityEnigmothLarva INSTANCE = new RenderFactoryEntityEnigmothLarva();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityEnigmothLarva> createRenderFor(RenderManager manager) {
            return new RenderEnigmothLarva(manager);
        }
    }

    public static class RenderFactoryEntityMothScales implements IRenderFactory<EntityMothScales> {
        public final static RenderFactoryEntityMothScales INSTANCE = new RenderFactoryEntityMothScales();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderMothScales createRenderFor(RenderManager manager) {
            return new RenderMothScales(manager);
        }
    }

    public static class RenderFactoryEntityBasicBomb implements IRenderFactory<EntityBomb> {
        public final static RenderFactoryEntityBasicBomb INSTANCE = new RenderFactoryEntityBasicBomb();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public Render<EntityBomb> createRenderFor(RenderManager manager) {
            return new RenderBomb(manager, FishItems.BASICBOMB);
        }
    }

    public static class RenderFactoryEntityKingsWrath implements IRenderFactory<EntityKingsWrath> {
        public final static RenderFactoryEntityKingsWrath INSTANCE = new RenderFactoryEntityKingsWrath();

        /* (non-Javadoc)
         * @see net.minecraftforge.fml.client.registry.IRenderFactory#createRenderFor(net.minecraft.client.renderer.entity.RenderManager)
         */
        @Override
        public RenderKingsWrath createRenderFor(RenderManager manager) {
            return new RenderKingsWrath(manager, FishItems.EMBLEM_OF_KING);
        }
    }
}
