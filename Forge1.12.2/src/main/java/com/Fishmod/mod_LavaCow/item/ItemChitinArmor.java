package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChitinArmor extends ItemArmor {
	private int set;
	private String armorTexture;
	
	public ItemChitinArmor(String registryName, int renderIndex, EntityEquipmentSlot slot) {
		super(FishItems.ARMOR_CHITIN, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);

		if (registryName.equals("chitinarmor_leggings")) {
			this.armorTexture = "mod_lavacow:textures/armors/chitin/chitin_layer_2.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/chitin/chitin_layer_1.png";
		}
		
        this.set = 0;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.CHITIN;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
		return this.armorTexture;
	}
	
	public int getSetBonus() {
		return this.set;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.set = 0;

		for(ItemStack armor : player.getArmorInventoryList()) {
			if (armor.getItem().getClass() == ItemChitinArmor.class) {
				this.set++;
			}
		}
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.chitinarmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.chitinarmor.l2"));
	}

}
