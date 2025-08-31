package com.Fishmod.fur.item;

import java.util.EnumMap;
import java.util.function.Supplier;

import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum FURArmorMaterial implements ArmorMaterial {
	CHITIN("chitin", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
		p_266655_.put(ArmorItem.Type.BOOTS, 2);
		p_266655_.put(ArmorItem.Type.LEGGINGS, 5);
		p_266655_.put(ArmorItem.Type.CHESTPLATE, 6);
		p_266655_.put(ArmorItem.Type.HELMET, 2);
	}), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
		return Ingredient.of(FURItemRegistry.CHITIN.get());
	});
	   
	public static final StringRepresentable.EnumCodec<ArmorMaterials> CODEC = StringRepresentable.fromEnum(ArmorMaterials::values);
	private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
		p_266653_.put(ArmorItem.Type.BOOTS, 13);
		p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
		p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
		p_266653_.put(ArmorItem.Type.HELMET, 11);
	});
	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	private FURArmorMaterial(String p_268171_, int p_268303_, EnumMap<ArmorItem.Type, Integer> p_267941_, int p_268086_, SoundEvent p_268145_, float p_268058_, float p_268180_, Supplier<Ingredient> p_268256_) {
		this.name = p_268171_;
		this.durabilityMultiplier = p_268303_;
		this.protectionFunctionForType = p_267941_;
		this.enchantmentValue = p_268086_;
		this.sound = p_268145_;
		this.toughness = p_268058_;
		this.knockbackResistance = p_268180_;
		this.repairIngredient = new LazyLoadedValue<>(p_268256_);
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type Slot) {
		return HEALTH_FUNCTION_FOR_TYPE.get(Slot) * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type Slot) {
		return this.protectionFunctionForType.get(Slot);
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.sound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
	
	public String getSerializedName() {
		return this.name;
	}
}