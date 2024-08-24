package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class MobEffectFragileKing extends MobEffectMod {

	public MobEffectFragileKing() {
		super("fragile_king", true, 25, 18, 25);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if ((entityLivingBaseIn.getHealth() / entityLivingBaseIn.getMaxHealth()) < (0.05f * (amplifier + 1))) {
        	entityLivingBaseIn.attackEntityFrom(DamageSource.WITHER.setDamageIsAbsolute().setDamageBypassesArmor(), entityLivingBaseIn.getMaxHealth());
        	
        	// Safety measure in case the above doesn't function correctly
        	entityLivingBaseIn.setHealth(0.0F);
        }
    }
}
