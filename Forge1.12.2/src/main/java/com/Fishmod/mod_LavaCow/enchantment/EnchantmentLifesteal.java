package com.Fishmod.mod_LavaCow.enchantment;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentLifesteal extends Enchantment {

	public EnchantmentLifesteal(String unlocalizedName, String registryName) {
		super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName(mod_LavaCow.MODID + "." + unlocalizedName);
		this.setRegistryName(registryName);
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    public boolean canApply(ItemStack stack)
    {
        return Modconfig.Enchantment_Enable ? super.canApply(stack) : false;
    }
	
    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return Modconfig.Enchantment_Enable ? super.canApplyAtEnchantingTable(stack) : false;
    }
	
    /**
     * Is this enchantment allowed to be enchanted on books via Enchantment Table
     * @return false to disable the vanilla feature
     */
	@Override
    public boolean isAllowedOnBooks()
    {
        return Modconfig.Enchantment_Enable;
    }
	
	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
		user.heal((float)user.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * (float)level * 0.05F);
	}

}
