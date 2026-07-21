package com.Fishmod.fur.enchantment;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.item.FangDaggerItem;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentRicochet extends Enchantment {
	public static final EnchantmentCategory FANG_DAGGER = EnchantmentCategory.create("FUR_FANG_DAGGER", (item) -> item instanceof FangDaggerItem);

	public EnchantmentRicochet() {
		super(Rarity.RARE, FANG_DAGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
		return FURConfig.Enchantment_Enable.get() && super.canEnchant(stack);
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
}
