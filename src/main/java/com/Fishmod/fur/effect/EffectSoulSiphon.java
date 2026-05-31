package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectSoulSiphon extends MobEffect {

	public EffectSoulSiphon() {
        super(MobEffectCategory.BENEFICIAL, 0x7CF4FF);
	}

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
