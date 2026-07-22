package com.Fishmod.mod_LavaCow;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.compat.CompatUtilBridge;
import com.Fishmod.mod_LavaCow.init.AddRecipes;
import com.Fishmod.mod_LavaCow.message.PacketMountSpecial;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.proxy.IProxy;
import com.Fishmod.mod_LavaCow.util.CreativeTab;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.Fishmod.mod_LavaCow.util.ModEventHandler;
import com.Fishmod.mod_LavaCow.util.RegistryHandler;
import com.Fishmod.mod_LavaCow.worldgen.StructureGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = mod_LavaCow.MODID, name = mod_LavaCow.NAME, version = mod_LavaCow.VERSION, acceptedMinecraftVersions = mod_LavaCow.MC_VERSION, dependencies = mod_LavaCow.DEPENDENCIES, guiFactory = "com.Fishmod.mod_LavaCow.client.gui.FishGuiFactory")
public class mod_LavaCow {
    public static final String MODID = "mod_lavacow";
    public static final String NAME = "Fish's Undead Rising";
    public static final String VERSION = "1.6.3";
    public static final String MC_VERSION = "[1.12.2]";
    public static final String DEPENDENCIES = "after:tconstruct;after:conarm;after:bettercombatmod;after:quark;after:somanyenchantments;after:jeresources";

    public static final String CLIENT = "com.Fishmod.mod_LavaCow.proxy.ClientProxy";
    public static final String SERVER = "com.Fishmod.mod_LavaCow.proxy.ServerProxy";

    @SidedProxy(clientSide = mod_LavaCow.CLIENT, serverSide = mod_LavaCow.SERVER)
    public static IProxy PROXY;

    @Instance(mod_LavaCow.MODID)
    public static mod_LavaCow INSTANCE;

    public static Logger logger = LogManager.getLogger(); // Swapped from setting to event.getModLog(); in preinit

    public static SimpleNetworkWrapper NETWORK_WRAPPER;

    public static CreativeTabs TAB_ITEMS;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TAB_ITEMS = new CreativeTab(MODID + "_items");
        Modconfig.INSTANCE.loadConfig(event);
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        CompatUtilBridge.preInit();

        PROXY.preRender();
        PROXY.registerItemAndBlockRenderers();
        PROXY.preInit(event);

        NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(mod_LavaCow.MODID);
        NETWORK_WRAPPER.registerMessage(PacketMountSpecial.class, PacketMountSpecial.class, 3, Side.SERVER);
        NETWORK_WRAPPER.registerMessage(PacketParticle.class, PacketParticle.class, 0, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(Modconfig.INSTANCE);
        GameRegistry.registerWorldGenerator(new StructureGenerator(), 0);
        AddRecipes.addRecipies();
        LootTableHandler.addLootTable();
        CompatUtilBridge.init();

        // Implements mobs to the monster spawner list
        if (Modconfig.MonsterSpawner_Mobs) {
            DungeonHooks.addDungeonMob(new ResourceLocation(mod_LavaCow.MODID + ":" + "unburied"), 50);
            DungeonHooks.addDungeonMob(new ResourceLocation(mod_LavaCow.MODID + ":" + "foglet"), 50);
        }

        PROXY.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CompatUtilBridge.postInit();
        PROXY.postInit(event);
    }
}
