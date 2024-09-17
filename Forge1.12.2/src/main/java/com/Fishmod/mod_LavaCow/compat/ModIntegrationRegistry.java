package com.Fishmod.mod_LavaCow.compat;

public class ModIntegrationRegistry {
	public static ModIntegration registerModIntegration(ModIntegration integration) {
		if (integration instanceof FURJERIntegration) {
			integration.init();
		}
				
		return integration;
	}
}
