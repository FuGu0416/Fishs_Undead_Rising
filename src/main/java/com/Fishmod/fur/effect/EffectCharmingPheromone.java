package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectCharmingPheromone extends MobEffect {

	public EffectCharmingPheromone() {
        super(MobEffectCategory.BENEFICIAL, 0x6E9070);
	}
	
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
