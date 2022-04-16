package com.Fishmod.mod_LavaCow.item;

import java.util.function.Supplier;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum FURItemTier implements IItemTier {
	SPECTRAL(2, 0, 6.0F, 2.0F, 14, () -> {
	      return Ingredient.of(FURItemRegistry.ECTOPLASM);
	});

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazyValue<Ingredient> repairIngredient;

	private FURItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
		this.level = p_i48458_3_;
		this.uses = p_i48458_4_;
		this.speed = p_i48458_5_;
		this.damage = p_i48458_6_;
		this.enchantmentValue = p_i48458_7_;
		this.repairIngredient = new LazyValue<>(p_i48458_8_);
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
