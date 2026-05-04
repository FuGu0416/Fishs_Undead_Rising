package com.Fishmod.fur;

import org.slf4j.Logger;

import com.Fishmod.fur.client.layer.FURModelLayers;
import com.Fishmod.fur.client.recipebook.RecipeCategories;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.events.EventBusHandler;
import com.Fishmod.fur.events.FURClientEvents;
import com.Fishmod.fur.events.FURServerEvents;
import com.Fishmod.fur.init.FURBlockEntityRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURFeatureRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURMenuTypesRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.init.FURRecipeRegistry;
import com.Fishmod.fur.init.FURRecipeTypeRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.misc.FURItemGroup;
import com.Fishmod.fur.worldgen.FURBiomeProvider;
import com.Fishmod.fur.worldgen.FURStructureModifier;
import com.mojang.logging.LogUtils;
import terrablender.api.Regions;
import com.mojang.serialization.Codec;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(mod_LavaCow.MODID)
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class mod_LavaCow {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "fur";
    public static final String NAME = "Fish's Undead Rising";
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final ResourceLocation PACKET_NETWORK_NAME = new ResourceLocation(mod_LavaCow.MODID, "main");
    public static SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
            .named(PACKET_NETWORK_NAME)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    
    public mod_LavaCow() {   
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	//FURWorldRegistry.register(eventBus);
    	
        // Register the setup method for modloading
    	eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
    	eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
    	//eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
    	eventBus.addListener(this::doClientStuff); 
    	//eventBus.addListener(this::setupParticleEvent);
        /*eventBus.addGenericListener(Feature.class, EventPriority.LOW,
                (final RegistryEvent.Register<Feature<?>> event) -> FURWorldRegistry.register());*/
    	eventBus.addListener(this::registerLayerDefinitions);
        // Register ourselves for server and other game events we are interested in
    	
        MinecraftForge.EVENT_BUS.register(this);      
        MinecraftForge.EVENT_BUS.register(new FURServerEvents());         
        ModLoadingContext.get().registerConfig(Type.COMMON, FURConfig.SPEC, "fur.common.toml");
        FUREntityRegistry.DEF_REG.register(eventBus);
        FURItemGroup.DEF_REG.register(eventBus);
        FURBlockRegistry.DEF_REG.register(eventBus);
        FURBlockEntityRegistry.DEF_REG.register(eventBus);
        FURItemRegistry.DEF_REG.register(eventBus);
        FURItemRegistry.BANNER_DEF_REG.register(eventBus);
        FURSoundRegistry.DEF_REG.register(eventBus);
        FURParticleRegistry.DEF_REG.register(eventBus);
        FUREffectRegistry.EFFECT_DEF_REG.register(eventBus);
        FUREffectRegistry.POTION_DEF_REG.register(eventBus);
        FURRecipeRegistry.DEF_REG.register(eventBus);
        FURMenuTypesRegistry.DEF_REG.register(eventBus);
        FURRecipeTypeRegistry.DEF_REG.register(eventBus);
        FURFeatureRegistry.DEF_REG.register(eventBus);
        EventBusHandler.create(eventBus);       
        
        final DeferredRegister<Codec<? extends StructureModifier>> structureModifiers = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, mod_LavaCow.MODID);
        structureModifiers.register(eventBus);
        structureModifiers.register("structure_spawns", FURStructureModifier.Modifier::makeCodec);
        eventBus.addListener(FURStructureModifier::generateStructureModifiers);                    
        eventBus.addListener(FURClientEvents::clientSetup);                    
        eventBus.addListener(FURClientEvents::registerItemColors);    
        eventBus.addListener(RecipeCategories::init);
        
	    // Register the configuration GUI factory
        /*ModLoadingContext.get().registerExtensionPoint(
        		ExtensionPoint.CONFIGGUIFACTORY,
        		() -> (mc, screen) -> new ConfigScreen()
		); */          
        PROXY.commonInit();
        
        GeckoLib.initialize();
    }
    
    private void setup(final FMLCommonSetupEvent event) {
    	event.enqueueWork(() -> {
    		FURItemRegistry.SetCompostables();
            Regions.register(new FURBiomeProvider());
        });
        PROXY.initNetwork();
    	FUREffectRegistry.onInitItems();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    	event.enqueueWork(() -> PROXY.clientInit());
    }
    
    private void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        FURModelLayers.register(event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        /*InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
        
		if (ModList.get().isLoaded("curios")) {
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
			//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
			//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
			//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
			//InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
		}*/
    }

    /*private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }*/
}
