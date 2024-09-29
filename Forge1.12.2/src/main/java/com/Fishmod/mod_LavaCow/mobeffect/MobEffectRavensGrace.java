package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;

public class MobEffectRavensGrace extends MobEffectMod {

	public MobEffectRavensGrace() {
		super("ravens_grace", false, 165, 92, 92);
	}
	   
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {    	    	
		if (!entityLivingBaseIn.onGround && entityLivingBaseIn.motionY < -0.1) {
			entityLivingBaseIn.motionY *= 0.8;
			entityLivingBaseIn.fallDistance = 0;
		}
    }
    
    @Override
    public boolean isReady(int duration, int amplifier) {
       return true;
    }    
}
