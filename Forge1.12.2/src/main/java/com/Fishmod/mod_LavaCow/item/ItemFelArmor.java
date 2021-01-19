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

public class ItemFelArmor extends ItemArmor {
	
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
	
	public ItemFelArmor(String registryName, int renderIndex, EntityEquipmentSlot slot, float effectlevelIn) {
		super(ArmorMaterial.DIAMOND, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        this.effectlevel = effectlevelIn * 0.2F;
        this.fireprooflevel = effectlevelIn * 0.5F;

		if (registryName.equals("felarmor_leggings")) {
			this.armorTexture = "mod_lavacow:textures/armors/fel/armor_fel_legs.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/fel/armor_fel.png";
		}
	}
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.MOLTENBEEF;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		//return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
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
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.felarmor") + (int)(this.effectlevel * 100.0F) + "%");
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.felarmor.l2") + (int)(this.fireprooflevel * 100.0F) + "%");
	}
}
