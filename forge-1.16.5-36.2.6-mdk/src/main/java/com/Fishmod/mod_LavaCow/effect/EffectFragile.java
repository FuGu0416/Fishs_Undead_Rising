package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class EffectFragile extends Effect {

	public EffectFragile() {
        super(EffectType.HARMFUL, 0x35272A);
        this.setRegistryName(mod_LavaCow.MODID, "fragile");
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if ((entityLivingBaseIn.getHealth() / entityLivingBaseIn.getMaxHealth()) < (0.05f * (amplifier + 1)))
        {
        	entityLivingBaseIn.hurt(DamageSource.WITHER.bypassInvul().bypassArmor(), 1005.0F);
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
       return true;
    }

}
