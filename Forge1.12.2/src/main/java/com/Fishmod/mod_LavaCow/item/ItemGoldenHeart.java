package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemGoldenHeart extends ItemRareLoot implements baubles.api.IBauble{

	public ItemGoldenHeart(String registryName, CreativeTabs tab, EnumRarity rarity, boolean hasTooltip) {
		super(registryName, tab, rarity, hasTooltip);
		this.setMaxStackSize(1);
		this.setMaxDamage(Modconfig.GoldenHeart_dur);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == Items.GOLD_INGOT;
	}
	
	private static boolean isBlackListed(ItemStack ItemStackIn) {
		boolean flag = ItemStackIn.getItem().equals(FishItems.GOLDENHEART);
		for(String S : Modconfig.GoldenHeart_bl)
			if(ItemStackIn.getItem().getRegistryName().toString().equals(S)) {
				flag = true;
				break;
			}
			
		return flag;
	}

    /**
     * Add Baubles support
     * @param arg0
     * @param arg1
     */
    //@Optional.Method(modid = "baubles")
    public static void onTick(ItemStack arg0, EntityPlayer arg1) {
    	boolean flag = false;
    	
    	if((arg0.getItemDamage() < arg0.getMaxDamage() - 1) || arg0.getMaxDamage() == 0) {
	    	if(arg1.getActivePotionEffect(MobEffects.REGENERATION) == null && arg1.getHealth() < arg1.getMaxHealth()) {
	    		arg1.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8*20, 0));
	    		flag = true;
	    	}
	    	else if(arg1.getHealth() == arg1.getMaxHealth())
	    		for(ItemStack item : arg1.getEquipmentAndArmor())
		        {
		        	if(!isBlackListed(item) && item.getMaxDamage() != 0 && item.getItem().isDamageable() && (item.isItemEnchantable() || item.isItemEnchanted()) && item.getItemDamage() > 0 && item.getItem().isRepairable() && !item.getHasSubtypes()) {
		        		item.setItemDamage(java.lang.Math.max(item.getItemDamage()-1, 0));
		        		flag = true;
		        	}
		        }   		  		
    	}
    	
    	if(flag && !arg1.isCreative() && arg0.getMaxDamage() != 0)
			arg0.damageItem(1, arg1);
	}
    
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack arg0) {
          return baubles.api.BaubleType.TRINKET;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase plr) {
    	if(stack.getItemDamage() > stack.getMaxDamage())stack.setItemDamage(stack.getMaxDamage());
    	if (plr instanceof EntityPlayer && plr.ticksExisted % 20 == 0 && !stack.isEmpty() && stack.getCount() > 0) {
	        ItemGoldenHeart.onTick(stack, (EntityPlayer) plr);
	    }
    }
}
