package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.enchantment.EnchantmentCorrosive;
import com.Fishmod.fur.enchantment.EnchantmentDominion;
import com.Fishmod.fur.enchantment.EnchantmentRicochet;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FUREnchantmentRegistry {
	public static final DeferredRegister<Enchantment> DEF_REG = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, mod_LavaCow.MODID);

	public static final RegistryObject<Enchantment> CORROSIVE = DEF_REG.register("corrosive", () -> new EnchantmentCorrosive(EnchantmentCategory.WEAPON));
	public static final RegistryObject<Enchantment> DOMINION = DEF_REG.register("dominion", () -> new EnchantmentDominion(EnchantmentCategory.WEAPON));
	public static final RegistryObject<Enchantment> RICOCHET = DEF_REG.register("ricochet", () -> new EnchantmentRicochet());
}
