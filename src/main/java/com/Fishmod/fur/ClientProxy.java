package com.Fishmod.fur;

import com.Fishmod.fur.client.particle.BansheeShriekParticle;
import com.Fishmod.fur.client.particle.FearParticle;
import com.Fishmod.fur.client.particle.GastroAcidParticle;
import com.Fishmod.fur.client.particle.LocustSwarmParticle;
import com.Fishmod.fur.client.renderer.FURItemRenderProperties;
import com.Fishmod.fur.client.renderer.blockentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.fur.client.renderer.entity.AvatonRenderer;
import com.Fishmod.fur.client.renderer.entity.BansheeRenderer;
import com.Fishmod.fur.client.renderer.entity.CactoidRenderer;
import com.Fishmod.fur.client.renderer.entity.CactusThornRenderer;
import com.Fishmod.fur.client.renderer.entity.CactyrantRenderer;
import com.Fishmod.fur.client.renderer.entity.FURArrowRenderer;
import com.Fishmod.fur.client.renderer.entity.FogletRenderer;
import com.Fishmod.fur.client.renderer.entity.LavaCowRenderer;
import com.Fishmod.fur.client.renderer.entity.MimicRenderer;
import com.Fishmod.fur.client.renderer.entity.MycosisRenderer;
import com.Fishmod.fur.client.renderer.entity.PteraRenderer;
import com.Fishmod.fur.client.renderer.entity.ScarecrowRenderer;
import com.Fishmod.fur.client.renderer.entity.SeaHagRenderer;
import com.Fishmod.fur.client.renderer.entity.SwarmerRenderer;
import com.Fishmod.fur.client.renderer.entity.UnburiedRenderer;
import com.Fishmod.fur.client.renderer.entity.AbstractUnburiedRenderer;
import com.Fishmod.fur.client.renderer.entity.UndertakerRenderer;
import com.Fishmod.fur.client.renderer.entity.WendigoRenderer;
import com.Fishmod.fur.client.renderer.entity.WetaRenderer;
import com.Fishmod.fur.client.renderer.entity.WispRenderer;
import com.Fishmod.fur.client.renderer.entity.WraithRenderer;
import com.Fishmod.fur.client.renderer.item.FURArmorRenderProperties;
import com.Fishmod.fur.client.renderer.item.FangDaggerRenderer;
import com.Fishmod.fur.init.FURBlockEntityRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
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
    	EntityRenderers.register(FUREntityRegistry.WRAITH.get(), WraithRenderer::new); 
    	EntityRenderers.register(FUREntityRegistry.WISP.get(), WispRenderer::new);  
    	EntityRenderers.register(FUREntityRegistry.UNBURIED.get(), UnburiedRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.MYCOSIS.get(), MycosisRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.FRIGID.get(), UnburiedRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.MUMMY.get(), AbstractUnburiedRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.UNDERTAKER.get(), UndertakerRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.BANSHEE.get(), BansheeRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.CACTOID.get(), CactoidRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.MIMIC.get(), MimicRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.PTERA.get(), PteraRenderer::new);
    	
    	EntityRenderers.register(FUREntityRegistry.CACTUS_THORN.get(), CactusThornRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.BASIC_BOMB.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.HOLY_GRENADE.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.GHOST_BOMB.get(), ThrownItemRenderer::new);
    	EntityRenderers.register(FUREntityRegistry.SONIC_BOMB.get(), ThrownItemRenderer::new);
        EntityRenderers.register(FUREntityRegistry.GHOUL_ARROW.get(), manager -> new FURArrowRenderer(manager, 0));
        EntityRenderers.register(FUREntityRegistry.FANG_ARROW.get(), manager -> new FURArrowRenderer(manager, 1));
        EntityRenderers.register(FUREntityRegistry.FANG_DAGGER.get(), FangDaggerRenderer::new);
        
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_COMMON.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(0, manager));
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_STRAW.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(1, manager));
    	BlockEntityRenderers.register(FURBlockEntityRegistry.SCARECROWHEAD_PLAGUE.get(), manager -> new ScarecrowHeadTileEntityRenderer<>(2, manager));
        
    	/*ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        EntityRenderers.register(FUREntityRegistry.PARASITE, manager -> new ParasiteRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.UNDEADSWINE, manager -> new UndeadSwineRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.SALAMANDER, manager -> new SalamanderRenderer(manager));        
        EntityRenderers.register(FUREntityRegistry.SLUDGELORD, manager -> new SludgeLordRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.LILSLUDGE, manager -> new LilSludgeRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.RAVEN, manager -> new RavenRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.SEAGULL, manager -> new RavenRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.VESPA, manager -> new VespaRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.VESPACOCOON, manager -> new VespaCocoonRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.BONEWORM, manager -> new BoneWormRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.PINGU, manager -> new PinguRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.GHOSTRAY, manager -> new GhostRayRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.FORSAKEN, manager -> new ForsakenRenderer(manager));   
        EntityRenderers.register(FUREntityRegistry.SKELETONKING, manager -> new SkeletonKingRenderer(manager));   
        EntityRenderers.register(FUREntityRegistry.WARPEDFIREFLY, manager -> new WarpedFireflyRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.GRAVEROBBER, manager -> new GraveRobberRenderer(manager));  
        EntityRenderers.register(FUREntityRegistry.GRAVEROBBERGHOST, manager -> new GraveRobberGhostRenderer(manager));  
        EntityRenderers.register(FUREntityRegistry.SCARAB, manager -> new ScarabRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.BEELZEBUB, manager -> new BeelzebubRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.BEELZEBUBPUPA, manager -> new BeelzebubPupaRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.ENIGMOTH, manager -> new EnigmothRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.MUMMIFIEDCOD, manager -> new MummifiedCodRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.BONETROUT, manager -> new BoneTroutRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.LAMPREY, manager -> new LampreyRenderer(manager)); 
        EntityRenderers.register(FUREntityRegistry.GHOUL, manager -> new GhoulRenderer(manager)); 
        //EntityRenderers.register(FUREntityRegistry.LIVING_ARMOR, manager -> new LivingArmorRenderer(manager)); 
        
        EntityRenderers.register(FUREntityRegistry.WAR_SMALL_FIREBALL, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.75F, true));
        EntityRenderers.register(FUREntityRegistry.PIRANHA_LAUNCHER, manager -> new PiranhaLauncherRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.ACIDJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));
        EntityRenderers.register(FUREntityRegistry.SLUDGEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        EntityRenderers.register(FUREntityRegistry.SANDBURST, manager -> new SandBurstRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.DEATHCOIL, manager -> new DeathCoilRenderer(manager));
        EntityRenderers.register(FUREntityRegistry.FLAMEJET, manager -> new SpriteRenderer<>(manager, itemRendererIn));        
        EntityRenderers.register(FUREntityRegistry.SAPJET, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, false));
        EntityRenderers.register(FUREntityRegistry.MOTH_SCALES, manager -> new SpriteRenderer<>(manager, itemRendererIn, 0.0F, true));
        */
        
        /*
        RenderTypeLookup.setRenderLayer(FURBlockRegistry.SLUDGEPILE, RenderType.solid());
        */
    	
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.GLOWSHROOM.get(), RenderType.cutout());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM.get(), RenderType.solid());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP.get(), RenderType.translucent());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.BLOODTOOTH_SHROOM.get(), RenderType.cutout());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.CORDY_SHROOM.get(), RenderType.cutout());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.VEIL_SHROOM.get(), RenderType.cutout());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.TOMBSTONE.get(), RenderType.cutout());
    	ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.ECTOPLASM_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.DISEASED_HAY_BLOCK.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(FURBlockRegistry.SOUL_FURNACE.get(), RenderType.solid());
        
        /*ItemModelsProperties.register(FURItemRegistry.VESPA_SHIELD, new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
        });*/
        
    	ItemProperties.register(FURItemRegistry.PARASITE_RAW.get(), new ResourceLocation(mod_LavaCow.MODID, "variant"),
    		    (stack, level, entity, seed) -> {
    		        if (!stack.hasTag()) return 0.0f;
    		        return stack.getTag().getInt("variant") / 10.0f;
    		    }
    		);
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
		registry.registerSpriteSet(FURParticleRegistry.BANSHEE_SHRIEK.get(), BansheeShriekParticle.Provider::new);
    }
}
