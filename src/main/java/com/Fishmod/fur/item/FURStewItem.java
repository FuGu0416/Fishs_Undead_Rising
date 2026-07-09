package com.Fishmod.fur.item;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FURStewItem extends FURItem {
	public FURStewItem(Properties properties, int tooltip) {
    	super(properties, 32, UseAnim.DRINK, tooltip);
    }

	public FURStewItem(Properties properties, UseAnim useAnim, int tooltip) {
    	super(properties.craftRemainder(Items.BOWL), 32, useAnim, tooltip);
    }
      
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
    	ItemStack itemstack = super.finishUsingItem(stack, level, living);
    	
    	if (this.equals(FURItemRegistry.GHOST_JELLY.get())) {
    		living.setDeltaMovement(0.0D, 2.0D, 0.0D);
    		living.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
    	}

    	if (!level.isClientSide && this.equals(FURItemRegistry.GLOWSHROOM_STEW.get())) {
    		living.removeEffect(FUREffectRegistry.SPOREROT.get());
    	}
    	
        if (!level.isClientSide && living instanceof Player player && !player.isCreative() && !itemstack.isEmpty()) {
        	if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
        		player.spawnAtLocation(new ItemStack(Items.BOWL));
            }
        }
        
    	return itemstack.isEmpty() ? new ItemStack(Items.BOWL) : itemstack;
    }
}
