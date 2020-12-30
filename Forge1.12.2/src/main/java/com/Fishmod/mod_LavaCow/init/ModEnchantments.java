package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.enchantment.EnchantmentCorrosive;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentLifesteal;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentPoisonous;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

//@ObjectHolder(mod_LavaCow.MODID)
public class ModEnchantments {
	//public static final EnumEnchantmentType WEAPONS = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemSword || item instanceof ItemBow));
	public static final Enchantment POISONOUS = new EnchantmentPoisonous("poisonous", "poisonous", EnumEnchantmentType.WEAPON);
	public static final Enchantment LIFESTEAL = new EnchantmentLifesteal("lifesteal", "lifesteal");
	public static final Enchantment CORROSIVE = new EnchantmentCorrosive("corrosive", "corrosive", EnumEnchantmentType.WEAPON);
}
