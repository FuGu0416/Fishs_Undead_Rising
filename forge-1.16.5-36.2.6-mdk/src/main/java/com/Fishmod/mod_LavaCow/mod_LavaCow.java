package com.Fishmod.mod_LavaCow;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.events.EventHandler;
import com.Fishmod.mod_LavaCow.init.FURProcessors;
import com.Fishmod.mod_LavaCow.init.FURWorldRegistry;
import com.Fishmod.mod_LavaCow.misc.FURItemGroup;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(mod_LavaCow.MODID)
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID)
public class mod_LavaCow {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "mod_lavacow";
    public static final String NAME = "Fish's Undead Rising";
    public static ItemGroup TAB = new FURItemGroup();
    public static SimpleChannel NETWORK;
    @SuppressWarnings("deprecation")
	public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    
    public mod_LavaCow() {   
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	FURWorldRegistry.register(eventBus);
    	
        // Register the setup method for modloading
    	eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
    	eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
    	eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
    	eventBus.addListener(this::doClientStuff); 
    	eventBus.addListener(this::setupParticleEvent);

        // Register ourselves for server and other game events we are interested in
        PROXY.init();
        MinecraftForge.EVENT_BUS.register(this);           
        MinecraftForge.EVENT_BUS.register(new EventHandler());           
        FURWorldRegistry.register();
        ModLoadingContext.get().registerConfig(Type.COMMON, FURConfig.SPEC, "mod_lavacow.common.toml");
	    // Register the configuration GUI factory
        /*ModLoadingContext.get().registerExtensionPoint(
        		ExtensionPoint.CONFIGGUIFACTORY,
        		() -> (mc, screen) -> new ConfigScreen()
		); */       
    }
    
    @SubscribeEvent
    public void onBiomeLoadFromJSON(BiomeLoadingEvent event) {
        FURWorldRegistry.onBiomesLoad(event);
    }
    
    @SubscribeEvent
    public void onStructuresLoadFromJSON(StructureSpawnListGatherEvent event) {
        FURWorldRegistry.onStructuresLoad(event);
    }
    
    private void setupParticleEvent(ParticleFactoryRegisterEvent event) {
        PROXY.setupParticles();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	event.enqueueWork(() -> {
    		FURWorldRegistry.setupStructures();
    		FURProcessors.registerProcessors();
        });
        PROXY.initNetwork();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        PROXY.clientInit();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
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
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
