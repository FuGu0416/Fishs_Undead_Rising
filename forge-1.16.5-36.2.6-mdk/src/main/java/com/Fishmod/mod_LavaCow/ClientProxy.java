package com.Fishmod.mod_LavaCow;

import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.Callable;

import com.Fishmod.mod_LavaCow.client.particle.FearParticle;
import com.Fishmod.mod_LavaCow.client.particle.GastroAcidParticle;
import com.Fishmod.mod_LavaCow.client.particle.LocustSwarmParticle;
import com.Fishmod.mod_LavaCow.client.renderer.FURItemstackRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.AvatonRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.BansheeRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.BeelzebubPupaRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.BeelzebubRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.BoneTroutRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.BoneWormRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.CactoidRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.CactusThornRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.CactyrantRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.DeathCoilRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.EnigmothRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.FogletRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.ForsakenRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.FrigidRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.GhostRayRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.GhostSwarmerRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.GraveRobberGhostRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.GraveRobberRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.LampreyRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.LavaCowRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.LilSludgeRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.MimicRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.MummifiedCodRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.MummyRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.MycosisRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.ParasiteRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.PinguRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.PiranhaLauncherRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.PteraRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.RavenRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SalamanderRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SandBurstRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.ScarabRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.ScarecrowRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SeaHagRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SkeletonKingRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SludgeLordRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.SwarmerRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.UnburiedRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.UndeadSwineRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.UndertakerRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.VespaCocoonRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.VespaRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.WarpedFireflyRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.WendigoRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.WetaRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.WispRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.entity.WraithRenderer;
import com.Fishmod.mod_LavaCow.client.renderer.tileentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURKeybindRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;
import com.Fishmod.mod_LavaCow.init.FURTileEntityRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    public void init(){
    	FURKeybindRegistry.init();
    }
    
    public void clientInit() {
    	ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.LAVACOW, manager -> new LavaCowRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MYCOSIS, manager -> new MycosisRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PARASITE, manager -> new ParasiteRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FOGLET, manager -> new FogletRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.IMP, manager -> new FogletRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FRIGID, manager -> new FrigidRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNDEADSWINE, manager -> new UndeadSwineRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SALAMANDER, manager -> new SalamanderRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WENDIGO, manager -> new WendigoRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MIMIC, manager -> new MimicRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SLUDGELORD, manager -> new SludgeLordRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.LILSLUDGE, manager -> new LilSludgeRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.RAVEN, manager -> new RavenRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SEAGULL, manager -> new RavenRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PTERA, manager -> new PteraRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.VESPA, manager -> new VespaRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SCARECROW, manager -> new ScarecrowRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.VESPACOCOON, manager -> new VespaCocoonRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PIRANHA, manager -> new SwarmerRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SWARMER, manager -> new SwarmerRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BONEWORM, manager -> new BoneWormRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PINGU, manager -> new PinguRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNDERTAKER, manager -> new UndertakerRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNBURIED, manager -> new UnburiedRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOSTRAY, manager -> new GhostRayRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BANSHEE, manager -> new BansheeRenderer(manager));      
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WETA, manager -> new WetaRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.AVATON, manager -> new AvatonRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FORSAKEN, manager -> new ForsakenRenderer(manager));   
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SKELETONKING, manager -> new SkeletonKingRenderer(manager));   
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MUMMY, manager -> new MummyRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.CACTYRANT, manager -> new CactyrantRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.CACTOID, manager -> new CactoidRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WARPEDFIREFLY, manager -> new WarpedFireflyRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SEAHAG, manager -> new SeaHagRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WISP, manager -> new WispRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GRAVEROBBER, manager -> new GraveRobberRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GRAVEROBBERGHOST, manager -> new GraveRobberGhostRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WRAITH, manager -> new WraithRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOSTSWARMER, manager -> new GhostSwarmerRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SCARAB, manager -> new ScarabRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BEELZEBUB, manager -> new BeelzebubRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BEELZEBUBPUPA, manager -> new BeelzebubPupaRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.ENIGMOTH, manager -> new EnigmothRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MUMMIFIEDCOD, manager -> new MummifiedCodRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BONETROUT, manager -> new BoneTroutRenderer(manager)); 
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.LAMPREY, manager -> new LampreyRenderer(manager)); 
        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WAR_SMALL_FIREBALL, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.75F, true));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PIRANHA_LAUNCHER, manager -> new PiranhaLauncherRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.ACIDJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.HOLY_GRENADE, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOSTBOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SONICBOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SLUDGEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SANDBURST, manager -> new SandBurstRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.DEATHCOIL, manager -> new DeathCoilRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FLAMEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.CACTUS_THORN, manager -> new CactusThornRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SAPJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MOTH_SCALES, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BASIC_BOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.SLUDGEPILE, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.BLOODTOOTH_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.CORDY_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.VEIL_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.TOMBSTONE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.ECTOPLASM_BLOCK, RenderType.translucent());
        
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_COMMON, manager -> new ScarecrowHeadTileEntityRenderer<>(0, manager));
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_STRAW, manager -> new ScarecrowHeadTileEntityRenderer<>(1, manager));
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_PLAGUE, manager -> new ScarecrowHeadTileEntityRenderer<>(2, manager));
        
        ItemModelsProperties.register(FURItemRegistry.VESPA_SHIELD, new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }
    
    public Item.Properties setupISTER(Item.Properties group) {
        return group.setISTER(ClientProxy::getTEISR);
    }

    @OnlyIn(Dist.CLIENT)
    public static Callable<ItemStackTileEntityRenderer> getTEISR() {
        return FURItemstackRenderer::new;
    }
    
    public void setupParticles() {
    	Minecraft instance = Minecraft.getInstance();
		instance.particleEngine.register(FURParticleRegistry.GASTRO_ACID, GastroAcidParticle.GastroAcidFactory::new);
		instance.particleEngine.register(FURParticleRegistry.LOCUST_SWARM, LocustSwarmParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.SLUDGE_JET, GastroAcidParticle.SludgeJetFactory::new);
		instance.particleEngine.register(FURParticleRegistry.GHOST_FLAME, FlameParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.WITHER_FLAME, FlameParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.SAP_JET, GastroAcidParticle.SapJetFactory::new);
		instance.particleEngine.register(FURParticleRegistry.FEAR, FearParticle.Factory::new);
    }
}
