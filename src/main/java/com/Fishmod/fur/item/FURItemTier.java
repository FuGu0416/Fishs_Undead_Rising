package com.Fishmod.fur.item;

import java.util.function.Supplier;

import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Custom weapon/tool tiers for Fish's Undead Rising.
 *
 * SPECTRAL powers the Spectral Dagger. Its durability ({@link #getUses()}) is the pool that the
 * summoned phantom blade spends while it fights; {@link SpectralDaggerItem} clamps wear so the
 * value can never actually reach 0 (the item is designed to never break).
 */
public enum FURItemTier implements Tier {
	SPECTRAL(250, 6.0F, 2.0F, 2, 14, () -> Ingredient.of(FURItemRegistry.ECTOPLASM_INGOT.get()));

	private final int uses;
	private final float speed;
	private final float damage;
	private final int level;
	private final int enchantmentValue;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	FURItemTier(int uses, float speed, float damage, int level, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.level = level;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.damage;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}
