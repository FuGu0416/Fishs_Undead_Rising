package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.compat.quark.QuarkCompat;
import net.minecraftforge.fml.common.Loader;

public class QuarkCompatBridge {
    private static final String COMPAT_MOD_ID = "quark";

    public static void loadQuarkCompat() {
        if (Modconfig.Quark_Compat && Loader.isModLoaded(COMPAT_MOD_ID)) {
            QuarkCompat.register();
        }
    }
}
