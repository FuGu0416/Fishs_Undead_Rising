package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCursedBandage extends ItemFishCustom {
	
	public ItemCursedBandage(String registryName) {
    	super(registryName, null, mod_LavaCow.TAB_ITEMS, false);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		int i = stack.getMetadata();
		list.add(I18n.format("tootip.mod_lavacow.cursed_bandage." + i + ".desc0"));
		list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.cursed_bandage." + i + ".desc1"));
	}
	
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {	
    	if (target instanceof EntityTameable && ((EntityTameable)target).isTamed() && ((EntityTameable)target).getOwner() != null && ((EntityTameable)target).getOwner().equals(playerIn) && target.getHealth() < target.getMaxHealth())
        {
    		switch(this.getMetadata(stack)) { 
	            case 0:
	            	break;
	            case 1: 
	            	target.heal(2.0F);
	            	target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3 * 60 * 20, 0));
	                break; 
	            case 2: 
	            	target.heal(6.0F);
	                break; 
	            case 3: 
	            	target.heal(2.0F);
	            	target.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 3 * 60 * 20, 0));
	                break; 
	            default: 
	            	target.heal(2.0F);
	                break;
	        }
    		
    		if(!playerIn.isCreative())
    			stack.shrink(1);
    		
    		playerIn.playSound(SoundEvents.BLOCK_GRASS_STEP, 1.0F, 1.0F);
        	for (int i = 0; i < 5; ++i)
            {
                double d0 = new Random().nextGaussian() * 0.02D;
                double d1 = new Random().nextGaussian() * 0.02D;
                double d2 = new Random().nextGaussian() * 0.02D;
                target.world.spawnParticle(EnumParticleTypes.TOTEM, target.posX + (double)(new Random().nextFloat() * target.width * 2.0F) - (double)target.width, target.posY + 1.0D + (double)(new Random().nextFloat() * target.height), target.posZ + (double)(new Random().nextFloat() * target.width * 2.0F) - (double)target.width, d0, d1, d2);
            }
        	
    		return true;
        }
        else
        	return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
    
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 4; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
