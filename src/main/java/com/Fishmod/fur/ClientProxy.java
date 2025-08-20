package com.Fishmod.fur;

import com.Fishmod.fur.client.renderer.entity.CactusThornRenderer;
import com.Fishmod.fur.client.renderer.entity.CactyrantRenderer;
import com.Fishmod.fur.client.renderer.entity.FogletRenderer;
import com.Fishmod.fur.client.renderer.entity.LavaCowRenderer;
import com.Fishmod.fur.client.renderer.entity.ScarecrowRenderer;
import com.Fishmod.fur.client.renderer.entity.SeaHagRenderer;
import com.Fishmod.fur.client.renderer.entity.SwarmerRenderer;
import com.Fishmod.fur.client.renderer.entity.WendigoRenderer;
import com.Fishmod.fur.client.renderer.item.FURArmorRenderProperties;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.client.renderer.entity.EntityRenderers;

public class ClientProxy extends CommonProxy {
    public void commonInit(){
    	//IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	//FURKeybindRegistry.init();
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
        
    	EntityRenderers.register(FUREntityRegistry.CACTUS_THORN.get(), CactusThornRenderer::new);
    	
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
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.WETA, manager -> new WetaRenderer(manager));  
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.AVATON, manager -> new AvatonRenderer(manager));  
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
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.HOLY_GRENADE, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOSTBOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SONICBOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SLUDGEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SANDBURST, manager -> new SandBurstRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.DEATHCOIL, manager -> new DeathCoilRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FLAMEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));        
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.SAPJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.MOTH_SCALES, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.BASIC_BOMB, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.GHOUL_ARROW, manager -> new FURArrowRenderer(manager, 0));
        RenderingRegistry.registerEntityRenderingHandler(FUREntityRegistry.FANG_ARROW, manager -> new FURArrowRenderer(manager, 1));
        
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.SLUDGEPILE, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM, RenderType.solid());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.BLOODTOOTH_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.CORDY_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.VEIL_SHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.TOMBSTONE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.ECTOPLASM_BLOCK, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.DISEASED_HAY_BLOCK, RenderType.solid());
        
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_COMMON, manager -> new ScarecrowHeadTileEntityRenderer<>(0, manager));
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_STRAW, manager -> new ScarecrowHeadTileEntityRenderer<>(1, manager));
        ClientRegistry.bindTileEntityRenderer(FURTileEntityRegistry.SCARECROWHEAD_PLAGUE, manager -> new ScarecrowHeadTileEntityRenderer<>(2, manager));
        
        ItemModelsProperties.register(FURItemRegistry.VESPA_SHIELD, new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
        });*/
    }
    
    @Override
    public Object getArmorProperties() {
        return new FURArmorRenderProperties();
    }
    
    /*public Item.Properties setupISTER(Item.Properties group) {
        return group.setISTER(ClientProxy::getTEISR);
    }*/

    /*@OnlyIn(Dist.CLIENT)
    public static Callable<ItemStackTileEntityRenderer> getTEISR() {
        return FURItemstackRenderer::new;
    }*/
    
    /*public void setupParticles() {
    	Minecraft instance = Minecraft.getInstance();
		instance.particleEngine.register(FURParticleRegistry.GASTRO_ACID, GastroAcidParticle.GastroAcidFactory::new);
		instance.particleEngine.register(FURParticleRegistry.LOCUST_SWARM, LocustSwarmParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.SLUDGE_JET, GastroAcidParticle.SludgeJetFactory::new);
		instance.particleEngine.register(FURParticleRegistry.GHOST_FLAME, FlameParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.WITHER_FLAME, FlameParticle.Factory::new);
		instance.particleEngine.register(FURParticleRegistry.SAP_JET, GastroAcidParticle.SapJetFactory::new);
		instance.particleEngine.register(FURParticleRegistry.FEAR, FearParticle.Factory::new);
    }*/
}
