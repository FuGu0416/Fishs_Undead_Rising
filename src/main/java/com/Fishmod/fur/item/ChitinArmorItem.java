package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChitinArmorItem extends ArmorItem {	
	public ChitinArmorItem(ArmorItem.Type slot, Item.Properties properties) {
		super(FURArmorMaterial.CHITIN, slot, properties);
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.CHITIN.get();
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (slot.equals(EquipmentSlot.LEGS)) {
			return mod_LavaCow.MODID + ":textures/armors/chitin/chitin_layer_2.png";
		} else {
			return mod_LavaCow.MODID + ":textures/armors/chitin/chitin_layer_1.png";
		}
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("item.fur.chitin_armor.desc0").withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.chitin_armor.desc1").withStyle(ChatFormatting.YELLOW));
	}
}
