package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;

public class MobEffectRavensGrace extends MobEffectMod {

	public MobEffectRavensGrace() {
		super("ravens_grace", false, 255, 255, 255);
	}
	   
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {    	    	
		if (!entityLivingBaseIn.onGround && entityLivingBaseIn.motionY < -0.4) {
			entityLivingBaseIn.motionY += 0.1;
			entityLivingBaseIn.velocityChanged = true;
			entityLivingBaseIn.fallDistance = 0;
		}
    }
    
    @Override
    public boolean isReady(int duration, int amplifier) {
       return true;
    }    
}
