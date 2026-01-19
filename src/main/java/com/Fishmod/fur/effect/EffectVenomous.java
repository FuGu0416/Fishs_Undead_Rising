package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectVenomous extends MobEffect {

	public EffectVenomous() {
        super(MobEffectCategory.BENEFICIAL, 0x6BCF4A);
	}
	
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
