package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import static com.Fishmod.mod_LavaCow.mobeffect.MobEffectFragile.FRAGILE_DAMAGE;

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
        float maxHealth = entityLivingBaseIn.getMaxHealth();
        if ((entityLivingBaseIn.getHealth() / maxHealth) < (0.05f * (amplifier + 1))) {
            if (entityLivingBaseIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                if (player.isSpectator() || player.isCreative())
                    return;
            }
            entityLivingBaseIn.attackEntityFrom(FRAGILE_DAMAGE, maxHealth);
        }
    }
}
