package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;

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
	public ChitinArmorItem(ArmorItem.Type slot, Item.Properties p_i48534_3_) {
		super(FURArmorMaterial.CHITIN, slot, p_i48534_3_);


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
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.fur.chitinarmor.desc0").withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.chitinarmor.desc1").withStyle(ChatFormatting.YELLOW));
	}
}
