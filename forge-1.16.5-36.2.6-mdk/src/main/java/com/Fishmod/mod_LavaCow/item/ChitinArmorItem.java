package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ArmorItem;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChitinArmorItem extends ArmorItem {
	private String armorTexture;
	
	public ChitinArmorItem(EquipmentSlotType slot, Item.Properties p_i48534_3_) {
		super(FURArmorMaterial.CHITIN, slot, p_i48534_3_);

		if (slot.equals(EquipmentSlotType.LEGS)) {
			this.armorTexture = "mod_lavacow:textures/armors/chitin/chitin_layer_2.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/chitin/chitin_layer_1.png";
		}
	}
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return this.armorTexture;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:chitinarmor").withStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:chitinarmor.l2").withStyle(TextFormatting.YELLOW));
	}
}
