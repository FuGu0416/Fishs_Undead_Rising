package com.Fishmod.mod_LavaCow.item;

import java.util.Map;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class IntestineItem extends FURItem {
	
	public IntestineItem() {
		super(new Item.Properties().tab(mod_LavaCow.TAB), 0, UseAction.NONE, 1);
    }
	     
    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
    	ItemStack stack = playerIn.getItemInHand(handIn);
    	
    	if (!playerIn.isCreative())stack.shrink(1);
    	playerIn.playSound(SoundEvents.SLIME_BLOCK_HIT, 1.0F, 1.0F);
    	for(Map.Entry<ItemStack, Float> entry : LootTableHandler.LOOT_INTESTINE.entrySet()) {
    		if(!worldIn.isClientSide() && Item.random.nextFloat() < entry.getValue()) {
    			ItemStack s = entry.getKey();
    			s.setCount(Item.random.nextInt(2)+1);
    			playerIn.drop(s, true);
    		}
    	}
    	return ActionResult.pass(playerIn.getItemInHand(handIn));
    }
}
