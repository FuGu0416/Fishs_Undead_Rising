package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class MobEffectFragile extends MobEffectMod {
    // TODO: Use custom damage type (for death message)
    public static final DamageSource FRAGILE_DAMAGE = new DamageSource("wither").setDamageIsAbsolute().setDamageBypassesArmor();

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
        float maxHealth = entityLivingBaseIn.getMaxHealth();
        if (entityLivingBaseIn.isNonBoss() && (entityLivingBaseIn.getHealth() / maxHealth) < (0.05f * (amplifier + 1))) {
            if (entityLivingBaseIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                if (player.isSpectator() || player.isCreative())
                    return;
            }
        	entityLivingBaseIn.attackEntityFrom(FRAGILE_DAMAGE, maxHealth);
        }
    }

}
