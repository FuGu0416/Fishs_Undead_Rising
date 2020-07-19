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
	
	public ItemFamineArmor(String registryName, int renderIndex, EntityEquipmentSlot slot) {
		super(ArmorMaterial.DIAMOND, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
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
		return material.getItem() == FishItems.FOUL_BRISTLE;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		//return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
		
		return new ModelFamineArmor(itemStack.getItem().equals(FishItems.FAMINEARMOR_LEGGINGS) ? 0.45F : 0.9F);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "mod_lavacow:textures/armors/famine/armor_famine" + (stack.getItem().equals(FishItems.FAMINEARMOR_LEGGINGS) ? "_legs.png" : ".png");
	}
	
	public int getSetBonus() {
		return this.set;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.set = 0;
		
		for(ItemStack E : player.getEquipmentAndArmor()) {
			if(E.getItem() instanceof ItemFamineArmor)this.set++;
		}
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.faminearmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.faminearmor.l2"));
	}
}
