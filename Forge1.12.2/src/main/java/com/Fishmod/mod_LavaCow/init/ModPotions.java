package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class ModPotions {
	public static final PotionType CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 600)).setRegistryName(mod_LavaCow.MODID + ":corrosive");
	public static final PotionType FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 600)).setRegistryName(mod_LavaCow.MODID + ":foulodor");
	public static final PotionType INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 1800)).setRegistryName(mod_LavaCow.MODID + ":infestation");
}
