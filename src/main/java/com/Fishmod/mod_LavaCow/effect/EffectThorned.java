package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectThorned extends Effect {

	public EffectThorned() {
        super(EffectType.BENEFICIAL, 0xD0EF63);
        this.setRegistryName(mod_LavaCow.MODID, "thorned");
	}
}
