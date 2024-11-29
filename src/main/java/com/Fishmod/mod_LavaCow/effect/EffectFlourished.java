package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectFlourished extends Effect {

	public EffectFlourished() {
        super(EffectType.BENEFICIAL, 0x97F460);
        this.setRegistryName(mod_LavaCow.MODID, "flourished");
	}
}
