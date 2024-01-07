package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.capability.SimpleCapProvider;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class GoldenHeartItem extends FURItem {

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
		if (EntityIn instanceof PlayerEntity && itemSlot < 9 && EntityIn.tickCount % 20 == 0)
			onTick(stack, (PlayerEntity)EntityIn);
    }
	
    /**
     * Add Baubles support
     * @param stack
     * @param PlayerIn
     */
    public static void onTick(ItemStack stack, PlayerEntity PlayerIn) {
    	boolean flag = false;

    	if (((stack.getMaxDamage() > 0 && stack.getDamageValue() != stack.getMaxDamage()) || stack.getMaxDamage() == 0)) {
	    	if (!PlayerIn.hasEffect(Effects.REGENERATION) && PlayerIn.isHurt() && FURConfig.GoldenHeart_GrantsRegeneration.get()) {
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
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
    	if (ModList.get().isLoaded("curios")) {
	    	return new SimpleCapProvider<>(CuriosCapability.ITEM, new ICurio() {
		        @Override
		        public void curioTick(String identifier, int index, LivingEntity livingEntity) {
		        	if (livingEntity instanceof PlayerEntity && livingEntity.tickCount % 20 == 0) {
		        		onTick(stack, (PlayerEntity)livingEntity);
		        	}
		        }
	    	});
    	}
    	
    	return super.initCapabilities(stack, unused);
    }
}
