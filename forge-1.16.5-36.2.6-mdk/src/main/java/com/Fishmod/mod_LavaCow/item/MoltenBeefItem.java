package com.Fishmod.mod_LavaCow.item;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class MoltenBeefItem extends FlintAndSteelItem {
	
	public MoltenBeefItem(String registryName) {
    	super(new Item.Properties().durability(0).stacksTo(64).fireResistant().tab(mod_LavaCow.TAB));
    }     
	
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		PlayerEntity player = p_195939_1_.getPlayer();
		ItemStack itemstack = player.getItemInHand(p_195939_1_.getHand());
		World world = p_195939_1_.getLevel();
		
		if(!super.useOn(p_195939_1_).equals(ActionResultType.FAIL)) {
			if(!player.isCreative())
				itemstack.shrink(1);
			
			return ActionResultType.sidedSuccess(world.isClientSide());
		}
		
		return super.useOn(p_195939_1_);
	}
	
	/**
	* returns the action that specifies what animation to play when the items is being used
	*/
	@Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BLOCK;
    }
	
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
        return 3200;
    }
}
