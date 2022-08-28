package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentCorrosive;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentCriticalBoost;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentLifesteal;
import com.Fishmod.mod_LavaCow.enchantment.EnchantmentPoisonous;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FUREnchantmentRegistry {
	public static final Enchantment POISONOUS = new EnchantmentPoisonous("mod_lavacow:poisonous", EnchantmentType.WEAPON);
	public static final Enchantment LIFESTEAL = new EnchantmentLifesteal("mod_lavacow:lifesteal", EnchantmentType.WEAPON);
	public static final Enchantment CORROSIVE = new EnchantmentCorrosive("mod_lavacow:corrosive", EnchantmentType.WEAPON);
	public static final Enchantment CRITICALBOOST = new EnchantmentCriticalBoost("mod_lavacow:criticalboost", EnchantmentType.WEAPON);
	
    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        try {
            for (Field f : FUREnchantmentRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Enchantment) {
                    event.getRegistry().register((Enchantment) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
