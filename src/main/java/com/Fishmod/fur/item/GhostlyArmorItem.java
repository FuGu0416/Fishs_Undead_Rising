package com.Fishmod.fur.item;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhostlyArmorItem extends ArmorItem {
	private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_SLOT = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
		p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
		p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
		p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
		p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
	});
	private static final double[] HEALTH_ADDITION = new double[]{-2.0D, -3.0D, -3.0D, -2.0D};
	private Multimap<Attribute, AttributeModifier> attributeMap;
	
	public GhostlyArmorItem(ArmorItem.Type slot, Item.Properties properties) {
		super(ArmorMaterials.NETHERITE, slot, properties);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT.get(slot);
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", ArmorMaterials.NETHERITE.getDefenseForType(slot), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", ArmorMaterials.NETHERITE.getToughness(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Armor health", HEALTH_ADDITION[slot.getSlot().getIndex()], AttributeModifier.Operation.ADDITION));
		this.attributeMap = builder.build();
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == this.type.getSlot() ? this.attributeMap : super.getDefaultAttributeModifiers(equipmentSlot);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (slot.equals(EquipmentSlot.LEGS)) {
			return mod_LavaCow.MODID + ":textures/armors/ghostly/ghostly_layer_2.png";
		} else {
			return mod_LavaCow.MODID + ":textures/armors/ghostly/ghostly_layer_1.png";
		}
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("item.fur.ghostly_armor.desc0").withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.ghostly_armor.desc1").withStyle(ChatFormatting.YELLOW));
	}
}
