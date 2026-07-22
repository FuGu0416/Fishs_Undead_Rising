package com.Fishmod.fur.core;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREnchantmentRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

/**
 * Weapon-enchantment levels a summoned minion inherits from the weapon that summoned it.
 * NBT keys must stay identical to the legacy per-entity keys ("fire_aspect", "sharpness", ...)
 * so entities in existing saves keep their inherited levels.
 */
public class WeaponEnchantments {
	private int fire_aspect;
	private int sharpness;
	private int knockback;
	private int bane_of_arthropods;
	private int smite;
	private int unbreaking;
	private int corrosive;

	public static WeaponEnchantments fromStack(ItemStack stack) {
		WeaponEnchantments enchants = new WeaponEnchantments();
		enchants.fire_aspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
		enchants.sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
		enchants.knockback = stack.getEnchantmentLevel(Enchantments.KNOCKBACK);
		enchants.bane_of_arthropods = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
		enchants.smite = stack.getEnchantmentLevel(Enchantments.SMITE);
		enchants.unbreaking = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
		enchants.corrosive = stack.getEnchantmentLevel(FUREnchantmentRegistry.CORROSIVE.get());
		return enchants;
	}

	public void copyFrom(WeaponEnchantments other) {
		this.fire_aspect = other.fire_aspect;
		this.sharpness = other.sharpness;
		this.knockback = other.knockback;
		this.bane_of_arthropods = other.bane_of_arthropods;
		this.smite = other.smite;
		this.unbreaking = other.unbreaking;
		this.corrosive = other.corrosive;
	}

	public void save(CompoundTag compound) {
		compound.putInt("fire_aspect", this.fire_aspect);
		compound.putInt("sharpness", this.sharpness);
		compound.putInt("knockback", this.knockback);
		compound.putInt("bane_of_arthropods", this.bane_of_arthropods);
		compound.putInt("smite", this.smite);
		compound.putInt("unbreaking", this.unbreaking);
		compound.putInt("corrosive", this.corrosive);
	}

	public void load(CompoundTag compound) {
		this.fire_aspect = compound.getInt("fire_aspect");
		this.sharpness = compound.getInt("sharpness");
		this.knockback = compound.getInt("knockback");
		this.bane_of_arthropods = compound.getInt("bane_of_arthropods");
		this.smite = compound.getInt("smite");
		this.unbreaking = compound.getInt("unbreaking");
		this.corrosive = compound.getInt("corrosive");
	}

	/**
	 * Bonus melee damage from sharpness/bane/smite against the given target, matching the
	 * vanilla {@code DamageEnchantment#getDamageBonus} formulas. Applied by
	 * {@code FURServerEvents#onEHurt}; the on-hit side effects live in {@link #applyOnHit}.
	 */
	public float getBonusDamage(LivingEntity target) {
		return (this.sharpness > 0 ? 0.5F * this.sharpness + 0.5F : 0.0F)
				+ (target.getMobType().equals(MobType.ARTHROPOD) ? (float)this.bane_of_arthropods * 2.5F : 0.0F)
				+ (target.getMobType().equals(MobType.UNDEAD) ? (float)this.smite * 2.5F : 0.0F);
	}

	/**
	 * On-hit side effects: fire aspect ignite, knockback, bane-of-arthropods slowdown,
	 * corrosive corrode. Call from the minion's {@code doHurtTarget}.
	 */
	public void applyOnHit(LivingEntity attacker, LivingEntity target) {
		if (this.fire_aspect > 0)
			target.setSecondsOnFire((this.fire_aspect * 4) - 1);

		if (this.knockback > 0)
			target.knockback((float)this.knockback * 0.5F, (attacker.getX() - target.getX())/attacker.distanceTo(target), (attacker.getZ() - target.getZ())/attacker.distanceTo(target));

		if (this.bane_of_arthropods > 0 && target.getMobType().equals(MobType.ARTHROPOD)) {
			int i = 20 + attacker.getRandom().nextInt(10 * this.bane_of_arthropods);
			target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
		}

		if (this.corrosive > 0)
			target.addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20, this.corrosive - 1));
	}

	public int getBaneOfArthropods() {
		return this.bane_of_arthropods;
	}

	public int getCorrosive() {
		return this.corrosive;
	}

	public int getUnbreaking() {
		return this.unbreaking;
	}
}
