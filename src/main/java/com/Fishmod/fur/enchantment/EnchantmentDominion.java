package com.Fishmod.fur.enchantment;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentDominion extends Enchantment {

	public EnchantmentDominion(EnchantmentCategory type) {
		super(Rarity.VERY_RARE, type, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	/**
	 * Determines if this enchantment can be applied to a specific ItemStack.
	 */
	@Override
	public boolean canEnchant(ItemStack stack) {
		return FURConfig.Enchantment_Enable.get()
				&& (stack.getItem().equals(FURItemRegistry.UNDERTAKER_SHOVEL.get())
				|| stack.getItem().equals(FURItemRegistry.SLUDGE_WAND.get())
				|| stack.getItem().equals(FURItemRegistry.SCARAB_SCEPTER.get())
				|| stack.getItem().equals(FURItemRegistry.ANKH_SCEPTER.get())
				|| stack.getItem().equals(FURItemRegistry.FUNGAL_STAFF.get())
				|| stack.getItem().equals(FURItemRegistry.FROZEN_GRIP.get()));
	}

	/**
	 * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
	 * applies for <i>all possible</i> enchantments.
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return FURConfig.Enchantment_Enable.get() && super.canApplyAtEnchantingTable(stack);
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
	protected boolean checkCompatibility(Enchantment other) {
		return !(other instanceof DamageEnchantment);
	}
}
