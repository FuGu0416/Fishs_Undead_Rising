package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class MobEffectFragile extends MobEffectMod {

	public MobEffectFragile() {
		super("fragile", true, 53, 39, 42);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isNonBoss() && (entityLivingBaseIn.getHealth() / entityLivingBaseIn.getMaxHealth()) < (0.05f * (amplifier + 1))) {
        	entityLivingBaseIn.attackEntityFrom(DamageSource.WITHER.setDamageIsAbsolute().setDamageBypassesArmor(), entityLivingBaseIn.getMaxHealth());
        	
        	// Safety measure in case the above doesn't function correctly
        	entityLivingBaseIn.setHealth(0.0F);
        }
    }

}
