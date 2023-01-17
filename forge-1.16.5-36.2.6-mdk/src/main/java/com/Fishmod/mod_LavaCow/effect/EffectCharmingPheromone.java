package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectCharmingPheromone extends Effect {

	public EffectCharmingPheromone() {
        super(EffectType.BENEFICIAL, 0xFFE1EE);
        this.setRegistryName(mod_LavaCow.MODID, "charming_pheromone");
	}
}
