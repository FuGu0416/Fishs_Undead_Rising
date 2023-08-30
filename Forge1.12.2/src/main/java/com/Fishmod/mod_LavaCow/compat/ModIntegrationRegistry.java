package com.Fishmod.mod_LavaCow.compat;

import com.Fishmod.mod_LavaCow.compat.jer.FURJERIntegration;

public class ModIntegrationRegistry {
	public static ModIntegration registerModIntegration(ModIntegration integration) {
		if (integration instanceof FURJERIntegration) {
			integration.init();
		}
				
		return integration;
	}
}
