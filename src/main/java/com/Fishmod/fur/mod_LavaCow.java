package com.Fishmod.fur;

import org.slf4j.Logger;

import com.Fishmod.fur.client.model.layered.FURModelLayers;
import com.Fishmod.fur.events.EventBusHandler;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.misc.FURItemGroup;
import com.Fishmod.fur.worldgen.FURStructureModifier;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
    public static SimpleChannel NETWORK;
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
        //MinecraftForge.EVENT_BUS.register(new EventHandler());                   
        //ModLoadingContext.get().registerConfig(Type.COMMON, FURConfig.SPEC, "mod_lavacow.common.toml");
        FUREntityRegistry.DEF_REG.register(eventBus);
        FURItemGroup.DEF_REG.register(eventBus);
        FURItemRegistry.DEF_REG.register(eventBus);
        FURSoundRegistry.DEF_REG.register(eventBus);
        FUREffectRegistry.EFFECT_DEF_REG.register(eventBus);
        FUREffectRegistry.POTION_DEF_REG.register(eventBus);
        EventBusHandler.create(eventBus);       
        
        final DeferredRegister<Codec<? extends StructureModifier>> structureModifiers = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, mod_LavaCow.MODID);
        structureModifiers.register(eventBus);
        structureModifiers.register("structure_spawns", FURStructureModifier.Modifier::makeCodec);
        eventBus.addListener(FURStructureModifier::generateStructureModifiers);                    
        
	    // Register the configuration GUI factory
        /*ModLoadingContext.get().registerExtensionPoint(
        		ExtensionPoint.CONFIGGUIFACTORY,
        		() -> (mc, screen) -> new ConfigScreen()
		); */          
        PROXY.commonInit();
        
        GeckoLib.initialize();
    }
    
   /* @SubscribeEvent
    public void onBiomeLoadFromJSON(BiomeLoadingEvent event) {
        //FURWorldRegistry.onBiomesLoad(event);
    }*/
    
    /*@SubscribeEvent
    public void onStructuresLoadFromJSON(StructureSpawnListGatherEvent event) {
        //FURWorldRegistry.onStructuresLoad(event);
    }*/
    
    /*private void setupParticleEvent(ParticleFactoryRegisterEvent event) {
        PROXY.setupParticles();
    }*/

    private void setup(final FMLCommonSetupEvent event) {
    	/*event.enqueueWork(() -> {
    		FURWorldRegistry.setupStructures();
    		FURProcessors.registerProcessors();
            //FURWorldRegistry.register();
            LootTableHandler.addLootTable();
        });*/
        //PROXY.initNetwork();
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
