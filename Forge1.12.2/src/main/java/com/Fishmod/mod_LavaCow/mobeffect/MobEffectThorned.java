package com.Fishmod.mod_LavaCow.mobeffect;

public class MobEffectThorned extends MobEffectMod {

	public MobEffectThorned() {
		super("thorned", false, 208, 239, 99);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
