package com.Fishmod.mod_LavaCow.enchantment;

import com.Fishmod.mod_LavaCow.config.FURConfig;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class EnchantmentCriticalBoost extends Enchantment {

	public EnchantmentCriticalBoost(String registryName, EnchantmentType type) {
		super(Rarity.VERY_RARE, type, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
		this.setRegistryName(registryName);
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    @Override
    public boolean canEnchant(ItemStack stack) {
        return FURConfig.Enchantment_Enable.get() ? super.canEnchant(stack) : false;
    }
	
    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return FURConfig.Enchantment_Enable.get() ? super.canApplyAtEnchantingTable(stack) : false;
    }
	
    /**
     * Is this enchantment allowed to be enchanted on books via Enchantment Table
     * @return false to disable the vanilla feature
     */
	@Override
    public boolean isAllowedOnBooks() {
        return FURConfig.Enchantment_Enable.get();
    }
	
	@Override
	public boolean checkCompatibility(Enchantment p_77326_1_) {
		return !(p_77326_1_ instanceof DamageEnchantment);
	}
}
