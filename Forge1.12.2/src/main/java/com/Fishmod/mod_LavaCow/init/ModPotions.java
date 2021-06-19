package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class ModPotions {
	public static final PotionType CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 600)).setRegistryName(mod_LavaCow.MODID + ":corrosive");
	public static final PotionType STRONG_CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 600, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_corrosive");
	public static final PotionType LONG_CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 1200)).setRegistryName(mod_LavaCow.MODID + ":long_corrosive");
	public static final PotionType FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 600)).setRegistryName(mod_LavaCow.MODID + ":foulodor");
	public static final PotionType LONG_FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 1200)).setRegistryName(mod_LavaCow.MODID + ":long_foulodor");
	public static final PotionType INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 1800)).setRegistryName(mod_LavaCow.MODID + ":infestation");
	public static final PotionType STRONG_INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 1800, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_infestation");
	public static final PotionType LONG_INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 3600)).setRegistryName(mod_LavaCow.MODID + ":long_infestation");
	public static final PotionType FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 600)).setRegistryName(mod_LavaCow.MODID + ":fragile");
	public static final PotionType STRONG_FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 600, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_fragile");
	public static final PotionType LONG_FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 1200)).setRegistryName(mod_LavaCow.MODID + ":long_fragile");
}
