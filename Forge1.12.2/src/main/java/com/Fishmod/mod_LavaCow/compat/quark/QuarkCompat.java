package com.Fishmod.mod_LavaCow.compat.quark;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import java.util.ArrayList;
import java.util.Arrays;

public class QuarkCompat {
    private static boolean registered = false;
    private static final String[] QUARK_TEXTURES = {
            "quark:textures/blocks/chests/acacia.png",
            "quark:textures/blocks/chests/birch.png",
            "quark:textures/blocks/chests/dark_oak.png",
            "quark:textures/blocks/chests/jungle.png",
            "quark:textures/blocks/chests/spruce.png"
    };

    public static void register() {
        if (!registered) {
            registered = true;
            init();
        } else {
            throw new RuntimeException("You can only call QuarkCompat.register() once");
        }
    }

    private static void init() {
        addQuarkMimics();
    }

    private static void addQuarkMimics() {
        EntityMimic.TEXTURE_POOL.addAll(new ArrayList<String>(Arrays.asList(QUARK_TEXTURES)));
    }
}
