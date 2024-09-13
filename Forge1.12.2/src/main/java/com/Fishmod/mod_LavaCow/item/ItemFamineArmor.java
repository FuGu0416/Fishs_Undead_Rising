package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelFamineArmor;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFamineArmor extends ItemArmor {
	
	private int set;
	private ModelFamineArmor armorModel;
	private String armorTexture;
	
	public ItemFamineArmor(String registryName, int renderIndex, EntityEquipmentSlot slot) {
		super(FishItems.ARMOR_FAMINE, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);

		if (registryName.equals("faminearmor_leggings")) {
			this.armorTexture = "mod_lavacow:textures/armors/famine/armor_famine_legs.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/famine/armor_famine.png";
		}

        this.set = 0;
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
		return material.getItem() == FishItems.FOUL_HIDE;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		//return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
		if (this.armorModel == null) {
			if (armorSlot == EntityEquipmentSlot.LEGS) {
				this.armorModel = new ModelFamineArmor(0.45F);
			} else {
				this.armorModel = new ModelFamineArmor(0.9F);
			}
		}

		return this.armorModel;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return this.armorTexture;
	}
	
	public int getSetBonus() {
		return this.set;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.set = 0;

		for(ItemStack armor : player.getArmorInventoryList()) {
			if (armor.getItem().getClass() == ItemFamineArmor.class) {
				this.set++;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.faminearmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.faminearmor.l2"));
	}
}
