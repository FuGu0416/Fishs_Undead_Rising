package com.Fishmod.fur;

import com.Fishmod.fur.client.particle.FearParticle;
import com.Fishmod.fur.client.particle.GastroAcidParticle;
import com.Fishmod.fur.client.particle.LocustSwarmParticle;
import com.Fishmod.fur.client.renderer.FURItemRenderProperties;
import com.Fishmod.fur.client.renderer.blockentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.fur.client.renderer.entity.AvatonRenderer;
import com.Fishmod.fur.client.renderer.entity.CactusThornRenderer;
import com.Fishmod.fur.client.renderer.entity.CactyrantRenderer;
import com.Fishmod.fur.client.renderer.entity.FogletRenderer;
import com.Fishmod.fur.client.renderer.entity.LavaCowRenderer;
import com.Fishmod.fur.client.renderer.entity.ScarecrowRenderer;
import com.Fishmod.fur.client.renderer.entity.SeaHagRenderer;
import com.Fishmod.fur.client.renderer.entity.SwarmerRenderer;
import com.Fishmod.fur.client.renderer.entity.WendigoRenderer;
import com.Fishmod.fur.client.renderer.entity.WetaRenderer;
import com.Fishmod.fur.client.renderer.item.FURArmorRenderProperties;
import com.Fishmod.fur.init.FURBlockEntityRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    public void commonInit(){
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	//FURKeybindRegistry.init();
    	bus.addListener(ClientProxy::setupParticles);
    }
    
    public void clientInit() {
    	EntityRenderers.register(FUREntityRegistry.LAVACOW.get(), LavaCowRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.FOGLET.get(), FogletRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.ISNACHI.get(), FogletRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.IMP.get(), FogletRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.SEAHAG.get(), SeaHagRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.PIRANHA.get(), SwarmerRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.SWARMER.get(), SwarmerRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.CACTYRANT.get(), CactyrantRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.WENDIGO.get(), WendigoRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.SCARECROW.get(), ScarecrowRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.WETA.get(), WetaRenderer::new);  
    	EntityRenderers.register(FUREntityRegistry.AVATON.get(), AvatonRenderer::new);  
    	
    	EntityRenderers.register(FUREntityRegistry.CACTUS_THORN.get(), CactusThornRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.BASIC_BOMB.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.HOLY_GRENADE.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.GHOSTBOMB.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.SONICBOMB.get(), ThrownItemRenderer::new);
        
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_COMMON.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(0, manager));
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_STRAW.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(1, manager));
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_PLAGUE.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(2, manager));
        
    	/*ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MYCOSIS, manager -> new MycosisRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PARASITE, manager -> new ParasiteRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.IMP, manager -> new FogletRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FRIGID, manager -> new FrigidRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNDEADSWINE, manager -> new UndeadSwineRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SALAMANDER, manager -> new SalamanderRenderer(manager));        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MIMIC, manager -> new MimicRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SLUDGELORD, manager -> new SludgeLordRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.LILSLUDGE, manager -> new LilSludgeRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.RAVEN, manager -> new RavenRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SEAGULL, manager -> new RavenRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PTERA, manager -> new PteraRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.VESPA, manager -> new VespaRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.VESPACOCOON, manager -> new VespaCocoonRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PIRANHA, manager -> new SwarmerRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BONEWORM, manager -> new BoneWormRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PINGU, manager -> new PinguRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNDERTAKER, manager -> new UndertakerRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.UNBURIED, manager -> new UnburiedRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOSTRAY, manager -> new GhostRayRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BANSHEE, manager -> new BansheeRenderer(manager));      
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FORSAKEN, manager -> new ForsakenRenderer(manager));   
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SKELETONKING, manager -> new SkeletonKingRenderer(manager));   
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MUMMY, manager -> new MummyRenderer(manager));        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.CACTOID, manager -> new CactoidRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WARPEDFIREFLY, manager -> new WarpedFireflyRenderer(manager));
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
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOUL, manager -> new GhoulRenderer(manager)); 
        //RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.LIVING_ARMOR, manager -> new LivingArmorRenderer(manager)); 
        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WAR_SMALL_FIREBALL, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.75F, true));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.PIRANHA_LAUNCHER, manager -> new PiranhaLauncherRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.ACIDJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SLUDGEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SANDBURST, manager -> new SandBurstRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.DEATHCOIL, manager -> new DeathCoilRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FLAMEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SAPJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MOTH_SCALES, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOUL_ARROW, manager -> new FURArrowRenderer(manager, 0));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FANG_ARROW, manager -> new FURArrowRenderer(manager, 1));*/
        
        /*RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.SLUDGEPILE, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.BLOODTOOTH_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.CORDY_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.VEIL_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.TOMBSTONE, RenderType.cutout());*/
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.ECTOPLASM_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.DISEASED_HAY_BLOCK.get(), RenderType.solid());
                
        /*ItemModelsProperties.register(FURItemRegistry.VESPA_SHIELD, new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
        });*/
    }
    
    @Override
    public Object getArmorProperties() {
        return new FURArmorRenderProperties();
    }
    
    @Override
    public Object getISTERProperties() {
        return new FURItemRenderProperties();
    }
    
    public static void setupParticles(RegisterParticleProvidersEvent registry) {
		registry.registerSpriteSet(FURParticleRegistry.GASTRO_ACID.get(), GastroAcidParticle.GastroAcidFactory::new);
		registry.registerSpriteSet(FURParticleRegistry.LOCUST_SWARM.get(), LocustSwarmParticle.Factory::new);
		registry.registerSpriteSet(FURParticleRegistry.SLUDGE_JET.get(), GastroAcidParticle.SludgeJetFactory::new);
		registry.registerSpriteSet(FURParticleRegistry.GHOST_FLAME.get(), FlameParticle.Provider::new);
		registry.registerSpriteSet(FURParticleRegistry.WITHER_FLAME.get(), FlameParticle.Provider::new);
		registry.registerSpriteSet(FURParticleRegistry.SAP_JET.get(), GastroAcidParticle.SapJetFactory::new);
		registry.registerSpriteSet(FURParticleRegistry.FEAR.get(), FearParticle.Factory::new);
    }
}
