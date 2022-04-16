package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelFamineArmor;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

public class FamineArmorItem extends ArmorItem {
	
	private int set;
	private ModelFamineArmor<?> armorModel;
	private String armorTexture;
	
	public FamineArmorItem(EquipmentSlotType slot, Item.Properties p_i48534_3_) {
		super(ArmorMaterial.DIAMOND, slot, p_i48534_3_);

		if (slot.equals(EquipmentSlotType.LEGS)) {
			this.armorTexture = "mod_lavacow:textures/armors/famine/armor_famine_legs.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/famine/armor_famine.png";
		}

        this.set = 0;
	}
		
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.FOUL_BRISTLE;
	}

	@SuppressWarnings("unchecked")
	@Override
    @OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		if (this.armorModel == null) {
			if (armorSlot.equals(EquipmentSlotType.LEGS)) {
				this.armorModel = new ModelFamineArmor<>(0.45F);
			} else {
				this.armorModel = new ModelFamineArmor<>(0.9F);
			}
		}

		return (E) this.armorModel;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return this.armorTexture;
	}
	
	public int getSetBonus() {
		return this.set;
	}
	
	public void setSetBonus(int setIn) {
		this.set = setIn;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:faminearmor").withStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:faminearmor.l2").withStyle(TextFormatting.YELLOW));
	}
}
