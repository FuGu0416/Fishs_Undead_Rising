package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelFelArmor;
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

public class FelArmorItem extends ArmorItem {
	
	/** 
	 * Bonus damage to burning mobs
	 */
	public float effectlevel;
	/** 
	 * Reduced damage from fire
	 */
	public float fireprooflevel;

	private ModelFelArmor<?> armorModel;
	private String armorTexture;
	
	public FelArmorItem(EquipmentSlotType slot, Item.Properties p_i48534_3_, float effectlevelIn) {
		super(ArmorMaterial.DIAMOND, slot, p_i48534_3_);
        this.effectlevel = effectlevelIn * 0.2F;
        this.fireprooflevel = effectlevelIn * 0.5F;

		if (slot.equals(EquipmentSlotType.LEGS)) {
			this.armorTexture = "mod_lavacow:textures/armors/fel/armor_fel_legs.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/fel/armor_fel.png";
		}
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.MOLTENBEEF;
	}

	@SuppressWarnings("unchecked")
	@Override
    @OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		if (this.armorModel == null) {
			if (armorSlot.equals(EquipmentSlotType.LEGS)) {
				this.armorModel = new ModelFelArmor<>(0.45F);
			} else {
				this.armorModel = new ModelFelArmor<>(0.9F);
			}
		}

		return (E) this.armorModel;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return this.armorTexture;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:felarmor", (int)(this.effectlevel * 100.0F)).withStyle(TextFormatting.YELLOW));
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:felarmor.l2", (int)(this.fireprooflevel * 100.0F)).withStyle(TextFormatting.YELLOW));
	}
}
