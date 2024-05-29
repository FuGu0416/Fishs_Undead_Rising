package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSinisterWhetstone extends ItemFishCustom {
	public ItemSinisterWhetstone(String registryName) {
		super(registryName, null, mod_LavaCow.TAB_ITEMS, false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		String s = "I";
	    NBTTagCompound compoundnbt = stack.getTagCompound();
	    if (compoundnbt != null)
	      switch (compoundnbt.getInteger("level")) {
	        case 0:
	        case 1:
	          break;
	        case 2:
	          s = "II";
	          break;
	        case 3:
	          s = "III";
	          break;
	        case 4:
	          s = "IV";
	          break;
	        default:
	          s = "V";
	          break;
	    }
	    
	    list.add(TextFormatting.GRAY + I18n.format("enchantment.mod_lavacow.critical_boost") + " " + s);
	}
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
