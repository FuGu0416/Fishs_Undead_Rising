package com.Fishmod.mod_LavaCow.item;

import java.util.Map;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemIntestine extends ItemFishCustom
{
	
	public ItemIntestine(String registryName) {
    	super(registryName, null, mod_LavaCow.TAB_ITEMS, true);
        this.setMaxStackSize(64);
    }
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.NONE;
    }
      
    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack stack = playerIn.getHeldItem(handIn);
    	
    	if (!playerIn.capabilities.isCreativeMode)stack.shrink(1);
    	playerIn.playSound(SoundEvents.BLOCK_SLIME_HIT, 1.0F, 1.0F);
    	for(Map.Entry<ItemStack, Float> entry : LootTableHandler.LOOT_INTESTINE.entrySet())
    	{
    		if(!worldIn.isRemote && Item.itemRand.nextFloat() < entry.getValue()) {
    			ItemStack s = entry.getKey();
    			s.setCount(Item.itemRand.nextInt(2)+1);
    			playerIn.dropItem(s, true);
    			//playerIn.dropItem(new ItemStack(entry.getKey(), entry.getKey() == Items.DIAMOND ? 1 : Item.itemRand.nextInt(2)+1, entry.getKey() == Items.DYE ? 15 : 0), true);
    		}
    	}
    	return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }
}
