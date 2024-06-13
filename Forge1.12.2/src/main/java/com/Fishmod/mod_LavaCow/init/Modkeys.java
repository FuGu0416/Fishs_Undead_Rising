package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Modkeys {
	public static KeyBinding MOUNT_SPECIAL;
	public static KeyBinding MOUNT_DOWN;
	
	public static void init() {
		// G Key
		MOUNT_SPECIAL = new KeyBinding("key.mod_lavacow.special", 34, mod_LavaCow.NAME);
		ClientRegistry.registerKeyBinding(MOUNT_SPECIAL);
		
		// Z Key
		MOUNT_DOWN = new KeyBinding("key.mod_lavacow.down", 44, mod_LavaCow.NAME);
		ClientRegistry.registerKeyBinding(MOUNT_DOWN);
	}
}
