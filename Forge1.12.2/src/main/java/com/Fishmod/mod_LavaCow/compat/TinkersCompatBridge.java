package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.compat.tinkers.TinkersCompat;
import com.Fishmod.mod_LavaCow.compat.tinkers.TinkersCompatClient;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;

public class TinkersCompatBridge {
    private static final String COMPAT_MOD_ID = "tconstruct";

    public static void loadTinkersCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(COMPAT_MOD_ID)) {
            TinkersCompat.register();
        }
    }

    public static void loadTinkersClientCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(COMPAT_MOD_ID)) {
            TinkersCompatClient.preInit();
        }
    }

    public static void loadTinkersClientModels(ModelRegistryEvent event) {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(COMPAT_MOD_ID)) {
            TinkersCompatClient.registerModels(event);
        }
    }

    public static void loadTinkersPostInitCompat() {
        if (Modconfig.Tinkers_Compat && Loader.isModLoaded(COMPAT_MOD_ID)) {
            TinkersCompat.post();
        }
    }
}
