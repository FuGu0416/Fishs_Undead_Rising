package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFishCustom extends Item {
	
	private Item return_item = null;
	protected String Tooltip = null;
	
	public ItemFishCustom(String registryName, Item afteruse, CreativeTabs tab, boolean hasTooltip) {
    	super();
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        setCreativeTab(tab);
        return_item = afteruse;
        if(hasTooltip)this.Tooltip = "tootip." + mod_LavaCow.MODID + "." + registryName;
    }
	
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(!worldIn.isRemote && entityLiving instanceof EntityPlayer)
        	((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(this.return_item));
    	return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		if(Tooltip != null)
			list.add(TextFormatting.YELLOW + I18n.format(Tooltip));
	}

}
