package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectFlourished extends MobEffect {

	public EffectFlourished() {
        super(MobEffectCategory.BENEFICIAL, 0x97F460);
	}
	
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
