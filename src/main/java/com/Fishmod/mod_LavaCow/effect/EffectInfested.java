package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectInfested extends Effect {

	public EffectInfested() {
        super(EffectType.HARMFUL, 0xA28100);
        this.setRegistryName(mod_LavaCow.MODID, "infested");
	}

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof PlayerEntity)
        {
            ((PlayerEntity)entityLivingBaseIn).causeFoodExhaustion(0.005F * (float)(amplifier + 1));
        }
    }

}
