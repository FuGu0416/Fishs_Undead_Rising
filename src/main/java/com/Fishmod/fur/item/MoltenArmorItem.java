package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class MoltenArmorItem extends ArmorItem {
	
	/** 
	 * Bonus damage to burning mobs
	 */
	public float effectlevel;
	/** 
	 * Reduced damage from fire
	 */
	public float fireprooflevel;

	public MoltenArmorItem(ArmorItem.Type slot, Item.Properties p_i48534_3_, float effectlevelIn) {
		super(ArmorMaterials.DIAMOND, slot, p_i48534_3_);
        this.effectlevel = effectlevelIn * 0.2F;
        this.fireprooflevel = effectlevelIn * 0.5F;
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.MOLTEN_ALLOY.get();
	}

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getArmorProperties());
    }

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (slot == EquipmentSlot.LEGS) {
			return mod_LavaCow.MODID + ":textures/armors/molten/molten_layer_2.png";
		} else {
			return mod_LavaCow.MODID + ":textures/armors/molten/molten_layer_1.png";
		}
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.fur.molten_armor.desc0", (int)(this.effectlevel * 100.0F)).withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.molten_armor.desc1", (int)(this.fireprooflevel * 100.0F)).withStyle(ChatFormatting.YELLOW));
	}
}
