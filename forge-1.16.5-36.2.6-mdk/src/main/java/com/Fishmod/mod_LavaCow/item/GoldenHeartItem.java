package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

//@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class GoldenHeartItem extends FURItem /*implements baubles.api.IBauble*/{

	public GoldenHeartItem(Properties PropertiesIn) {
		super(PropertiesIn, 0, UseAction.NONE, 1);
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(Items.GOLD_INGOT);
	}
	
	private static boolean isBlackListed(ItemStack ItemStackIn) {
		boolean flag = ItemStackIn.getItem().equals(FURItemRegistry.GOLDENHEART);
		for(String S : FURConfig.GoldenHeart_bl.get())
			if(ItemStackIn.getItem().getRegistryName().toString().equals(S)) {
				flag = true;
				break;
			}
			
		return flag;
	}

	@Override
    public void inventoryTick(ItemStack stack, World WorldIn, Entity EntityIn, int itemSlot, boolean isSelected) {
		if(EntityIn instanceof PlayerEntity && itemSlot < 9 && EntityIn.tickCount % 20 == 0)
			onTick(stack, (PlayerEntity)EntityIn);
    }
	
    /**
     * Add Baubles support
     * @param stack
     * @param PlayerIn
     */
    public static void onTick(ItemStack stack, PlayerEntity PlayerIn) {
    	boolean flag = false;

    	if (((stack.getMaxDamage() > 0 && stack.getDamageValue() != stack.getMaxDamage()) || stack.getMaxDamage() == 0) && FURConfig.GoldenHeart_GrantsRegeneration.get()) {
	    	if (!PlayerIn.hasEffect(Effects.REGENERATION) && PlayerIn.isHurt()) {
	    		PlayerIn.addEffect(new EffectInstance(Effects.REGENERATION, 8 * 20, 0));
	    		flag = true;
	    	} else if (PlayerIn.getHealth() == PlayerIn.getMaxHealth() && FURConfig.GoldenHeart_RepairsEquipment.get()) {
	    		for(ItemStack item : PlayerIn.getAllSlots()) {
		        	if (!isBlackListed(item) && item.getMaxDamage() != 0 && item.isDamageableItem() && (item.isEnchantable() || item.isEnchanted()) && item.getDamageValue() > 0) {		        		
		        		item.setDamageValue(java.lang.Math.max(item.getDamageValue() - 1, 0));		        		
		        		flag = true;
		        	}
		        }  
	    	}
    	}
    	
    	if (flag && !PlayerIn.isCreative() && stack.getMaxDamage() != 0 && PlayerIn.getRandom().nextInt(100) < FURConfig.GoldenHeart_dur.get()) {
			stack.hurtAndBreak(1, PlayerIn, e -> e.broadcastBreakEvent(net.minecraft.inventory.EquipmentSlotType.OFFHAND));
    	}
	}
    
    /*@Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack stack, EntityLivingBase PlayerIn) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack stack, EntityLivingBase PlayerIn) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack stack) {
          return baubles.api.BaubleType.TRINKET;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase plr) {
    	if(stack.getItemDamage() > stack.getMaxDamage())stack.setItemDamage(stack.getMaxDamage());
    	if (plr instanceof EntityPlayer && plr.ticksExisted % 20 == 0 && !stack.isEmpty() && stack.getCount() > 0) {
	        GoldenHeartItem.onTick(stack, (EntityPlayer) plr);
	    }
    }*/
}
