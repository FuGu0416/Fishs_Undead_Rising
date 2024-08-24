package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.enchantment.EnchantmentCorrosive;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentCriticalBoost;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentLifesteal;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentPoisonous;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class ModEnchantments {
	public static final Enchantment POISONOUS = new EnchantmentPoisonous("poisonous", "poisonous", EnumEnchantmentType.WEAPON);
	public static final Enchantment LIFESTEAL = new EnchantmentLifesteal("lifesteal", "lifesteal", EnumEnchantmentType.WEAPON);
	public static final Enchantment CORROSIVE = new EnchantmentCorrosive("corrosive", "corrosive", EnumEnchantmentType.WEAPON);
	public static final Enchantment CRITICAL_BOOST = new EnchantmentCriticalBoost("critical_boost", "critical_boost", EnumEnchantmentType.WEAPON);
}
