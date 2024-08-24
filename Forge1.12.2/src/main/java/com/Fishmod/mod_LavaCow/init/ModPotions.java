package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class ModPotions {
	public static final PotionType CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 900)).setRegistryName(mod_LavaCow.MODID + ":corrosive");
	public static final PotionType STRONG_CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 450, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_corrosive");
	public static final PotionType LONG_CORROSIVE = new PotionType(mod_LavaCow.MODID + ":corrosive", new PotionEffect(ModMobEffects.CORRODED, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_corrosive");
	public static final PotionType FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 900)).setRegistryName(mod_LavaCow.MODID + ":foulodor");
	public static final PotionType STRONG_FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 450, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_foulodor");
	public static final PotionType LONG_FOULODOR = new PotionType(mod_LavaCow.MODID + ":foulodor", new PotionEffect(ModMobEffects.SOILED, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_foulodor");
	public static final PotionType INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 1800)).setRegistryName(mod_LavaCow.MODID + ":infestation");
	public static final PotionType STRONG_INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 900, 2)).setRegistryName(mod_LavaCow.MODID + ":strong_infestation");
	public static final PotionType LONG_INFESTATION = new PotionType(mod_LavaCow.MODID + ":infestation", new PotionEffect(ModMobEffects.INFESTED, 3600)).setRegistryName(mod_LavaCow.MODID + ":long_infestation");
	public static final PotionType FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 900)).setRegistryName(mod_LavaCow.MODID + ":fragile");
	public static final PotionType STRONG_FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 450, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_fragile");
	public static final PotionType LONG_FRAGILE = new PotionType(mod_LavaCow.MODID + ":fragile", new PotionEffect(ModMobEffects.FRAGILE, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_fragile");
	public static final PotionType THORN = new PotionType(mod_LavaCow.MODID + ":thorned", new PotionEffect(ModMobEffects.THORNED, 1800)).setRegistryName(mod_LavaCow.MODID + ":thorn");
	public static final PotionType STRONG_THORN = new PotionType(mod_LavaCow.MODID + ":thorned", new PotionEffect(ModMobEffects.THORNED, 900, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_thorn");
	public static final PotionType LONG_THORN = new PotionType(mod_LavaCow.MODID + ":thorned", new PotionEffect(ModMobEffects.THORNED, 3600)).setRegistryName(mod_LavaCow.MODID + ":long_thorn");
	public static final PotionType IMMOLATION = new PotionType(mod_LavaCow.MODID + ":immolation", new PotionEffect(ModMobEffects.IMMOLATION, 1800)).setRegistryName(mod_LavaCow.MODID + ":immolation");
	public static final PotionType STRONG_IMMOLATION = new PotionType(mod_LavaCow.MODID + ":immolation", new PotionEffect(ModMobEffects.IMMOLATION, 900, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_immolation");
	public static final PotionType LONG_IMMOLATION = new PotionType(mod_LavaCow.MODID + ":immolation", new PotionEffect(ModMobEffects.IMMOLATION, 3600)).setRegistryName(mod_LavaCow.MODID + ":long_immolation");
	public static final PotionType FLOURISHED = new PotionType(mod_LavaCow.MODID + ":flourished", new PotionEffect(ModMobEffects.FLOURISHED, 3600)).setRegistryName(mod_LavaCow.MODID + ":flourished");
	public static final PotionType STRONG_FLOURISHED = new PotionType(mod_LavaCow.MODID + ":flourished", new PotionEffect(ModMobEffects.FLOURISHED, 1800, 1)).setRegistryName(mod_LavaCow.MODID + ":strong_flourished");
	public static final PotionType LONG_FLOURISHED = new PotionType(mod_LavaCow.MODID + ":flourished", new PotionEffect(ModMobEffects.FLOURISHED, 9600)).setRegistryName(mod_LavaCow.MODID + ":long_flourished");
	public static final PotionType VOID_DUST = new PotionType(mod_LavaCow.MODID + ":void_dust", new PotionEffect(ModMobEffects.VOID_DUST, 900)).setRegistryName(mod_LavaCow.MODID + ":void_dust");
	public static final PotionType STRONG_VOID_DUST = new PotionType(mod_LavaCow.MODID + ":void_dust", new PotionEffect(ModMobEffects.VOID_DUST, 450, 2)).setRegistryName(mod_LavaCow.MODID + ":strong_void_dust");
	public static final PotionType LONG_VOID_DUST = new PotionType(mod_LavaCow.MODID + ":void_dust", new PotionEffect(ModMobEffects.VOID_DUST, 1800)).setRegistryName(mod_LavaCow.MODID + ":long_void_dust");
}
