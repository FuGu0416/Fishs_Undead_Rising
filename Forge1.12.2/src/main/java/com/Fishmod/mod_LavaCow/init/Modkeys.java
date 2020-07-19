package com.Fishmod.mod_LavaCow.init;

import org.lwjgl.input.Keyboard;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Modkeys {
	public static KeyBinding MOUNT_SPECIAL;
	
	public static void init() {
		MOUNT_SPECIAL = new KeyBinding("key.mod_lavacow.special", Keyboard.KEY_G, mod_LavaCow.NAME);
		ClientRegistry.registerKeyBinding(MOUNT_SPECIAL);
	}
}
