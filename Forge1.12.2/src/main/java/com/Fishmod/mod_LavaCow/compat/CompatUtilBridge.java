package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.compat.quark.QuarkCompat;
import com.Fishmod.mod_LavaCow.compat.rlcombat.RLCombatCompat;
import com.Fishmod.mod_LavaCow.compat.somanyenchantments.SoManyEnchantmentsCompat;
import com.Fishmod.mod_LavaCow.compat.tinkers.ConstructsArmoryCompat;
import com.Fishmod.mod_LavaCow.compat.tinkers.TinkersCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class CompatUtilBridge {
    public static final String BETTER_COMBAT_MODID = "bettercombatmod";
    public static final String RLCOMBAT_MODNAME = "RLCombat";
    public static final String CONSTRUCTS_ARMORY_MODID = "conarm";
    public static final String JUST_ENOUGH_RESOURCES_MODID = "jeresources";
    public static final String QUARK_MODID = "quark";
    public static final String SME_MODID = "somanyenchantments";
    public static final String TINKERS_CONSTRUCT_MODID = "tconstruct";

    public static void preInit() {
        if (Loader.isModLoaded(QUARK_MODID) && Modconfig.Quark_Compat) {
            QuarkCompat.preInit();
        }

        if (Loader.isModLoaded(TINKERS_CONSTRUCT_MODID) && Modconfig.Tinkers_Compat) {
            TinkersCompat.preInit();

            // Only load Construct's Armory if Tinkers' Construct is also loaded
            if (Loader.isModLoaded(CONSTRUCTS_ARMORY_MODID) && Modconfig.Tinkers_Armor_Compat) {
                ConstructsArmoryCompat.preInit();
            }
        }

        // RLCombat is a fork of Better Combat Rebirth, we want to specifically check for this fork to prevent issues with the original mod and its other forks
        if (Loader.isModLoaded(BETTER_COMBAT_MODID) && Loader.instance().getIndexedModList().get(BETTER_COMBAT_MODID).getName().equals(RLCOMBAT_MODNAME) && Modconfig.RLCombat_Compat) {
            MinecraftForge.EVENT_BUS.register(RLCombatCompat.class);
        }

        if (Loader.isModLoaded(SME_MODID) && Modconfig.SME_Compat) {
            SoManyEnchantmentsCompat.preInit();
        }
    }

    public static void init() {
    }

    public static void postInit() {
        // TODO: Add config option for JER compat
        if (Loader.isModLoaded(JUST_ENOUGH_RESOURCES_MODID)) {
            FURJERIntegration.postInit();
        }

        if (Loader.isModLoaded(TINKERS_CONSTRUCT_MODID) && Modconfig.Tinkers_Compat) {
            TinkersCompat.postInit();
        }
    }
}
