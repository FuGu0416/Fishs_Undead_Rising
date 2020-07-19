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
	
	public ItemSwineArmor(String registryName, int renderIndex, EntityEquipmentSlot slot, float effectlevelIn) {
		super(ArmorMaterial.IRON, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        this.set = 0;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.PIGBOARHIDE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
		if(stack.getItem().equals(FishItems.SWINEMASK))
			return "mod_lavacow:textures/armors/swine/swinemask.png";
		else if(stack.getItem().equals(FishItems.SWINEARMOR_LEGGINGS))
			return "mod_lavacow:textures/armors/swine/armor_swine_legs.png"; 
		else
			return "mod_lavacow:textures/armors/swine/armor_swine.png"; 
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped) {		
		if(stack.getItem().equals(FishItems.SWINEMASK)) {
			ModelSwineMask model = new ModelSwineMask(1.0F);
			model.bipedHead.showModel = false;
			model.bipedHeadwear.showModel = false;
			model.bipedBody.showModel = false;
			model.bipedRightArm.showModel = false;
			model.bipedLeftArm.showModel = false;
			model.bipedRightLeg.showModel = false;
			model.bipedLeftLeg.showModel = false;
	
			return model;
		}
		else
			return super.getArmorModel(player, stack, armorSlot, modelBiped);
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
		
		for(ItemStack E : player.getEquipmentAndArmor()) {
			if(E.getItem() instanceof ItemSwineArmor)this.set++;
		}
		
		/*if(this.getSetBonus() >= 4) {
			if(player.isPotionActive(MobEffects.POISON))
				player.removePotionEffect(MobEffects.POISON);
		}*/
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.swinearmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.swinearmor.l2"));
	}

}
