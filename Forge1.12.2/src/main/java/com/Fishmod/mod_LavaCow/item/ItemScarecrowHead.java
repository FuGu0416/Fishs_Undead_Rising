package com.Fishmod.mod_LavaCow.item;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemScarecrowHead extends ItemBlock {

	public ItemScarecrowHead(Block block) {
		super(block);
	}
    
    /**
     * Override this to set a non-default armor slot for an ItemStack, but
     * <em>do not use this to get the armor slot of said stack; for that, use
     * {@link net.minecraft.entity.EntityLiving#getSlotForItemStack(ItemStack)}.</em>
     *
     * @param stack the ItemStack
     * @return the armor slot of the ItemStack, or {@code null} to let the default
     * vanilla logic as per {@code EntityLiving.getSlotForItemStack(stack)} decide
     */
    @Nullable
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
    	return EntityEquipmentSlot.HEAD;
    }

}
