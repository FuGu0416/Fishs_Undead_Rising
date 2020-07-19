package com.Fishmod.mod_LavaCow.mobeffect;

public class MobEffectCorroded extends MobEffectMod {

	public MobEffectCorroded() {
		super("corroded", true, 36, 7, 7);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
