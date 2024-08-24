package com.Fishmod.mod_LavaCow.mobeffect;

public class MobEffectSoiled extends MobEffectMod {

	public MobEffectSoiled() {
		super("soiled", true, 111, 92, 60);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
