package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectCorroded extends Effect {

	public EffectCorroded() {
        super(EffectType.HARMFUL, 0x240707);
        this.setRegistryName(mod_LavaCow.MODID, "corroded");
	}
}
