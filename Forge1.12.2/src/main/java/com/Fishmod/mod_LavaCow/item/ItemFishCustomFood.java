package com.Fishmod.mod_LavaCow.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFishCustomFood extends ItemFood {
	
	/** Number of ticks to run while 'EnumAction'ing until result. */
	/** Which means a player needs 32 ticks to finish eating an item*/
	private int itemDuraion = 32;
	private EnumAction act = EnumAction.EAT;
	private int PotionType = 0;//0 = single/no effect, 1 = multiple effect, 2 = random effect
	private final List<Triple<Potion, Integer, Integer>> PotionList = new ArrayList<>();
	private String Tooltip = null;
	
	public ItemFishCustomFood(String registryName, int amount, float saturation, boolean isWolfFood, int duration, boolean hasTooltip) {
		super(amount, saturation, isWolfFood);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
		setRegistryName(registryName);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.itemDuraion = duration;
		if(hasTooltip)this.Tooltip = "tootip." + mod_LavaCow.MODID + "." + registryName;
	}

	public ItemFishCustomFood setPotionEffect(PotionEffect potion, float probility)
    {
		this.PotionType = 0;
		super.setPotionEffect(potion, probility);
        return this;
    }
	
	public ItemFishCustomFood setMultiPotionEffect(List<Triple<Potion, Integer, Integer>> list)
    {
		this.PotionType = 1;
		PotionList.clear();
		if(list != null)
			for (final Triple<Potion, Integer, Integer> item : list) {
				PotionList.add(item);  
			}
        return this;
    }
	
	public ItemFishCustomFood setRandPotionEffect(List<Triple<Potion, Integer, Integer>> list)
    {
		this.PotionType = 2;
		PotionList.clear();
		if(list != null)
			for (final Triple<Potion, Integer, Integer> item : list) {
				PotionList.add(item);
			}
        return this;
    }
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
		super.onFoodEaten(stack, worldIn, player);
		
		if(stack.getItem().equals(FishItems.SHATTERED_ICE) && player.isBurning())
			player.extinguish();
		
		switch(PotionType)
        {
        	case 0: 
        		super.onFoodEaten(stack, worldIn, player);
        		break;
        	case 1:
        		//System.out.println("OXOXOXOXOXOXOXOXO " + PotionList.get(0).getEffectName() + " " + PotionList.get(0).getDuration());
        		if (!worldIn.isRemote && PotionList != null && PotionList.size() > 0)
	        		for (final Triple<Potion, Integer, Integer> item : PotionList) {
	        			player.addPotionEffect(new PotionEffect(item.getLeft(), item.getMiddle(), item.getRight()));
	        		}
        		break;
        	case 2:
        		//System.out.println("OXO " + PotionList.get(a).getLeft() + " " + PotionList.get(a).getMiddle());
        		if (!worldIn.isRemote && PotionList != null && PotionList.size() > 0) {
            		int a = Item.itemRand.nextInt(PotionList.size());
        			player.addPotionEffect(new PotionEffect(PotionList.get(a).getLeft(), PotionList.get(a).getMiddle(), PotionList.get(a).getRight()));
        		}
        		break;
        	default:
        		super.onFoodEaten(stack, worldIn, player);
        		break;
        }
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemStack) {
	    return itemDuraion;
	}
	
	public ItemFishCustomFood setItemUseAction(EnumAction a)
	{
		act = a;
		return this;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return act;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		if(Tooltip != null)
			list.add(TextFormatting.YELLOW + I18n.format(Tooltip));
	}
}
