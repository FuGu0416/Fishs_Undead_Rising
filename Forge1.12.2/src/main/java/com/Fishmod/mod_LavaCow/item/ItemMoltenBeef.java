package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMoltenBeef extends ItemFlintAndSteel
{
	
	public ItemMoltenBeef(String registryName) {
    	super();
    	this.setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        this.setRegistryName(registryName);
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
    }     
	
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		
		if(super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ).equals(EnumActionResult.SUCCESS)) {
			if(!player.isCreative())
				itemstack.shrink(1);
			
			return EnumActionResult.SUCCESS;
		}
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	/**
	* returns the action that specifies what animation to play when the items is being used
	*/
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }
		
    /**
     * @return the fuel burn time for this itemStack in a furnace.
     * Return 0 to make it not act as a fuel.
     * Return -1 to let the default vanilla logic decide.
     */
	@Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 16000;
    }
}
