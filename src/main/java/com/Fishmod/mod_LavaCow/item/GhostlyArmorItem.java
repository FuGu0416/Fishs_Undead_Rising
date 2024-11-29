package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhostlyArmorItem extends ArmorItem {
	private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	private static final double[] HEALTH_ADDITION = new double[]{-2.0D, -3.0D, -3.0D, -2.0D};
	private String armorTexture;
	private Multimap<Attribute, AttributeModifier> attributeMap;
	
	public GhostlyArmorItem(EquipmentSlotType slot, Item.Properties p_i48534_3_) {
		super(ArmorMaterial.NETHERITE, slot, p_i48534_3_);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", ArmorMaterial.NETHERITE.getDefenseForSlot(slot), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", ArmorMaterial.NETHERITE.getToughness(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Armor health", HEALTH_ADDITION[slot.getIndex()], AttributeModifier.Operation.ADDITION));
		this.attributeMap = builder.build();
	      
		if (slot.equals(EquipmentSlotType.LEGS)) {
			this.armorTexture = "mod_lavacow:textures/armors/ghostly/ghostly_layer_2.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/ghostly/ghostly_layer_1.png";
		}
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == this.slot ? attributeMap : super.getDefaultAttributeModifiers(equipmentSlot);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return this.armorTexture;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:ghostlyarmor").withStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:ghostlyarmor.l2").withStyle(TextFormatting.YELLOW));
	}
}
