package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelSwineMask;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwineArmor extends ItemArmor {
	
	private int set;
	private ModelBiped armorModel;
	private String armorTexture;
	
	public ItemSwineArmor(String registryName, int renderIndex, EntityEquipmentSlot slot, float effectlevelIn) {
		super(FishItems.ARMOR_SWINE, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);

		if (registryName.equals("swinemask"))
			this.armorTexture = "mod_lavacow:textures/armors/swine/swinemask.png";
		else if (registryName.equals("swinearmor_leggings"))
			this.armorTexture = "mod_lavacow:textures/armors/swine/armor_swine_legs.png";
		else
			this.armorTexture = "mod_lavacow:textures/armors/swine/armor_swine.png";

		this.set = 0;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.PIGBOARHIDE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
		return this.armorTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped) {
		if (this.armorModel == null) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				this.armorModel = new ModelSwineMask(1.0F);
				this.armorModel.bipedHead.showModel = false;
				this.armorModel.bipedHeadwear.showModel = false;
				this.armorModel.bipedBody.showModel = false;
				this.armorModel.bipedRightArm.showModel = false;
				this.armorModel.bipedLeftArm.showModel = false;
				this.armorModel.bipedRightLeg.showModel = false;
				this.armorModel.bipedLeftLeg.showModel = false;
			} else {
				this.armorModel = super.getArmorModel(player, stack, armorSlot, modelBiped);
			}
		}

		return this.armorModel;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
	}
	
	public int getSetBonus() {
		return this.set;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.set = 0;

		for(ItemStack armor : player.getArmorInventoryList()) {
			if (armor.getItem().getClass() == ItemSwineArmor.class) {
				this.set++;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.swinearmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.swinearmor.l2"));
	}

}
