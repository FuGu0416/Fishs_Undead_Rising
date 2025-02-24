package com.Fishmod.fur.item;

import javax.annotation.Nullable;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class MoltenMeatItem extends FlintAndSteelItem {
	
	public MoltenMeatItem(Item.Properties properties) {
    	super(properties);
    }     
	
	public InteractionResult useOn(UseOnContext p_195939_1_) {
		Player player = p_195939_1_.getPlayer();
		ItemStack itemstack = player.getItemInHand(p_195939_1_.getHand());
		Level world = p_195939_1_.getLevel();
		
		if(!super.useOn(p_195939_1_).equals(InteractionResult.FAIL)) {
			if(!player.isCreative())
				itemstack.shrink(1);
			
			return InteractionResult.sidedSuccess(world.isClientSide());
		}
		
		return super.useOn(p_195939_1_);
	}
	
	/**
	* returns the action that specifies what animation to play when the items is being used
	*/
	@Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }
	
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 3200;
    }
}
