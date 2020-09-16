package com.Fishmod.mod_LavaCow.core;

import com.Fishmod.mod_LavaCow.client.Modconfig;

import net.minecraft.world.World;

public class SpawnUtil {
	
	public static boolean isDay(World worldIn) {
		return worldIn.getWorldTime() <= 12000;
	}
	
	public static boolean isAllowedDimension(int dimensionIn) {
		for(int i : Modconfig.Spawn_AllowList) {
			if(i == dimensionIn)
				return true;
		}
		
		return false;
	}
}
