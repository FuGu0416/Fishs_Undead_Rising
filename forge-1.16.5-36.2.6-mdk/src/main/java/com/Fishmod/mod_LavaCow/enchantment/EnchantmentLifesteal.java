package com.Fishmod.mod_LavaCow.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentLifesteal extends Enchantment {

	public EnchantmentLifesteal(String registryName, EnchantmentType type) {
		super(Rarity.VERY_RARE, type, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
		this.setRegistryName(registryName);
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
	/*@Override
    public boolean canEnchant(ItemStack stack) {
        return Modconfig.Enchantment_Enable ? super.canEnchant(stack) : false;
    }*/
	
    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
	/*@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Modconfig.Enchantment_Enable ? super.canApplyAtEnchantingTable(stack) : false;
    }*/
	
    /**
     * Is this enchantment allowed to be enchanted on books via Enchantment Table
     * @return false to disable the vanilla feature
     */
	/*@Override
    public boolean isAllowedOnBooks() {
        return Modconfig.Enchantment_Enable;
    }*/
	
	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level) {
		user.heal((float)user.getAttributeValue(Attributes.ATTACK_DAMAGE) * (float)level * 0.05F);
	}

}
