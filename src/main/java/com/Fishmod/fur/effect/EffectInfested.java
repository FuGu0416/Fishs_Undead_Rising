package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectInfested extends MobEffect {

	public EffectInfested() {
        super(MobEffectCategory.HARMFUL, 0xA28100);
	}

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player) {
            ((Player)entityLivingBaseIn).causeFoodExhaustion(0.005F * (float)(amplifier + 1));
        }
    }

}
