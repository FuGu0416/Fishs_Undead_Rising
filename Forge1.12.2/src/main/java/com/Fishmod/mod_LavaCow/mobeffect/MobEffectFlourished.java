package com.Fishmod.mod_LavaCow.mobeffect;

public class MobEffectFlourished extends MobEffectMod {

	public MobEffectFlourished() {
		super("flourished", false, 151, 244, 96);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
