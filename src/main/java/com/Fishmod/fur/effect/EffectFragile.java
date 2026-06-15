package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectFragile extends MobEffect {

	public EffectFragile() {
        super(MobEffectCategory.HARMFUL, 0x35272A);
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    	float maxHealth = entityLivingBaseIn.getMaxHealth();
        if ((entityLivingBaseIn.getHealth() / maxHealth) < (0.05f * (amplifier + 1))) {
            if (entityLivingBaseIn instanceof Player player) {
                if (player.isSpectator() || player.isCreative())
                    return;
            }
        	entityLivingBaseIn.hurt(entityLivingBaseIn.damageSources().wither(), maxHealth);
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
       return true;
    }

}
