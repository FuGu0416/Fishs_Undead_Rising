package com.Fishmod.mod_LavaCow.compat.quark;

import com.Fishmod.mod_LavaCow.client.layer.LayerMimicChest;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.Arrays;

public class QuarkCompat {
	private static boolean registered = false;
	private static final ResourceLocation[] QUARK_TEXTURES = {
            new ResourceLocation("quark:textures/blocks/chests/acacia.png"),
            new ResourceLocation("quark:textures/blocks/chests/birch.png"),
            new ResourceLocation("quark:textures/blocks/chests/dark_oak.png"),
            new ResourceLocation("quark:textures/blocks/chests/jungle.png"),
            new ResourceLocation("quark:textures/blocks/chests/spruce.png")
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
        LayerMimicChest.texturePool.addAll(new ArrayList<ResourceLocation>(Arrays.asList(QUARK_TEXTURES)));
    }
}
