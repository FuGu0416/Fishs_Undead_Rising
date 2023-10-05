package com.Fishmod.mod_LavaCow.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;

//@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class DreamCatcherItem extends FURItem /*implements baubles.api.IBauble*/ {

	public DreamCatcherItem(Properties PropertiesIn) {
		super(PropertiesIn, 0, UseAction.NONE, 1);
	}
	
	@Override
    public void inventoryTick(ItemStack stack, World WorldIn, Entity EntityIn, int itemSlot, boolean isSelected) {
		if(stack.getDamageValue() > stack.getMaxDamage())
			stack.setDamageValue(stack.getMaxDamage());
    }
		
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(Items.COBWEB);
	}
	
    /**
     * Add Baubles support
     * @param arg0
     * @param arg1
     */  
   /* @Override
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

    }
    
	@Override
	@Optional.Method(modid = "baubles")
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, .75F, 1.9f);
	}

	@Override
	@Optional.Method(modid = "baubles")
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, .75F, 2f);
	}*/
}
