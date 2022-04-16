package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelSwineMask;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SwineArmorItem extends ArmorItem {
	
	private int set;
	private BipedModel<?> armorModel;
	private String armorTexture;
	
	public SwineArmorItem(EquipmentSlotType slot, Item.Properties p_i48534_3_) {
		super(ArmorMaterial.IRON, slot, p_i48534_3_);
		
		if (slot.equals(EquipmentSlotType.HEAD))
			this.armorTexture = "mod_lavacow:textures/armors/swine/armor_swine_helmet.png";
		else if (slot.equals(EquipmentSlotType.LEGS))
			this.armorTexture = "mod_lavacow:textures/armors/swine/armor_swine_legs.png";
		else
			this.armorTexture = "mod_lavacow:textures/armors/swine/armor_swine.png";

		this.set = 0;
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.PIGBOARHIDE;
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type) {
		return this.armorTexture;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    @OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		if (this.armorModel == null) {
			if (armorSlot == EquipmentSlotType.HEAD) {
				this.armorModel = new ModelSwineMask<>(1.0F, this.armorTexture);
				//this.armorModel.head.visible = false;
				this.armorModel.hat.visible = false;
				this.armorModel.body.visible = false;
				this.armorModel.rightArm.visible = false;
				this.armorModel.leftArm.visible = false;
				this.armorModel.rightLeg.visible = false;
				this.armorModel.leftLeg.visible = false;
			} else {
				this.armorModel = super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
			}
		}

		return (E) this.armorModel;
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
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:swinearmor").withStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:swinearmor.l2").withStyle(TextFormatting.YELLOW));
	}
}
