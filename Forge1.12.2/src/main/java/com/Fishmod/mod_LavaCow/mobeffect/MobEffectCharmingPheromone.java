package com.Fishmod.mod_LavaCow.mobeffect;

public class MobEffectCharmingPheromone extends MobEffectMod {

	public MobEffectCharmingPheromone() {
		super("charming_pheromone", false, 255, 225, 238);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
