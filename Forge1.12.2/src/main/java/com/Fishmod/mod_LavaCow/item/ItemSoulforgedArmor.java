package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelFelArmor;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulforgedArmor extends ItemArmor {
	
	/** 
	 * Bonus damage to burning mobs
	 */
	public float effectlevel;
	/** 
	 * Reduced damage from fire
	 */
	public float fireprooflevel;

	private ModelFelArmor armorModel;
	private String armorTexture;
	
	public ItemSoulforgedArmor(String registryName, int renderIndex, EntityEquipmentSlot slot, float effectlevelIn) {
		super(FishItems.ARMOR_SOULFORGED, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        this.effectlevel = effectlevelIn * 0.4F;
        this.fireprooflevel = effectlevelIn * 1.0F;

		if (registryName.equals("soulforgedarmor_leggings")) {
			this.armorTexture = "mod_lavacow:textures/armors/soulforged/soulforged_layer_2.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/soulforged/soulforged_layer_1.png";
		}
	}
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.ECTOPLASM_INGOT;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (this.armorModel == null) {
			if (armorSlot == EntityEquipmentSlot.LEGS) {
				this.armorModel = new ModelFelArmor(0.45F);
			} else {
				this.armorModel = new ModelFelArmor(0.9F);
			}
		}

		return this.armorModel;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return this.armorTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.soulforgedarmor") + (int)(this.effectlevel * 100.0F) + "%");
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.soulforgedarmor.l2") + (int)(this.fireprooflevel * 100.0F) + "%");
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.soulforgedarmor.l3"));
	}
}
