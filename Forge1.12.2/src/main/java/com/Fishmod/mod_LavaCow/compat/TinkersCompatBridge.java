package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.compat.tinkers.ConstructsArmoryCompat;
import com.Fishmod.mod_LavaCow.compat.tinkers.TinkersCompat;
import com.Fishmod.mod_LavaCow.compat.tinkers.TinkersCompatClient;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;

public class TinkersCompatBridge {
    private static final String TINKERS_CONSTRUCT_ID = "tconstruct";

    // Construct's Armory is only loaded when Tinkers' Construct is installed
    private static final String CONSTRUCTS_ARMORY_ID = "conarm";

    public static void loadTinkersCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(TINKERS_CONSTRUCT_ID)) {
            TinkersCompat.register();

            if (Modconfig.Tinkers_Armor_Compat && Loader.isModLoaded(CONSTRUCTS_ARMORY_ID)) {
                ConstructsArmoryCompat.register();
            }
        }
    }

    public static void loadTinkersClientCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(TINKERS_CONSTRUCT_ID)) {
            TinkersCompatClient.preInit();
        }
    }

    public static void loadTinkersClientModels(ModelRegistryEvent event) {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(TINKERS_CONSTRUCT_ID)) {
            TinkersCompatClient.registerModels(event);
        }
    }

    public static void loadTinkersPostInitCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(TINKERS_CONSTRUCT_ID)) {
            TinkersCompat.post();
        }
    }
}
