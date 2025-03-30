package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectFragile extends MobEffect {

	public EffectFragile() {
        super(MobEffectCategory.HARMFUL, 0x35272A);
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if ((entityLivingBaseIn.getHealth() / entityLivingBaseIn.getMaxHealth()) < (0.05f * (amplifier + 1))) {
        	entityLivingBaseIn.hurt(entityLivingBaseIn.damageSources().wither(), 1005.0F);
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
       return true;
    }

}
