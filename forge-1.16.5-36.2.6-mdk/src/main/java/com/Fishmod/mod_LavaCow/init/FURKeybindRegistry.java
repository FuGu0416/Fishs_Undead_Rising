package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class FURKeybindRegistry {
	public static KeyBinding MOUNT_SPECIAL;
	public static KeyBinding MOUNT_DOWN;
	
	public static void init() {
		MOUNT_SPECIAL = new KeyBinding("key.mod_lavacow.special", 'G', mod_LavaCow.NAME);
		ClientRegistry.registerKeyBinding(MOUNT_SPECIAL);
		MOUNT_DOWN = new KeyBinding("key.mod_lavacow.down", 'C', mod_LavaCow.NAME);
		ClientRegistry.registerKeyBinding(MOUNT_DOWN);
	}
}
