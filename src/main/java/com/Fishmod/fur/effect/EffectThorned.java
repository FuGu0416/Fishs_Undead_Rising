package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectThorned extends MobEffect {

	public EffectThorned() {
        super(MobEffectCategory.BENEFICIAL, 0xD0EF63);
	}
	
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
