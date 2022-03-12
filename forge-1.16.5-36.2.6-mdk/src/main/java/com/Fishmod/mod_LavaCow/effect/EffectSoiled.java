package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectSoiled extends Effect {

	public EffectSoiled() {
        super(EffectType.HARMFUL, 0x6F5C3C);
        this.setRegistryName(mod_LavaCow.MODID, "soiled");
	}
	
}
