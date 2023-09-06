package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusFruitItem extends FURItem {
	public CactusFruitItem(Properties p_i48487_1_, int TooltipIn) {
		super(p_i48487_1_, TooltipIn);
	}

	@Override
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		World worldIn = p_195939_1_.getLevel();
		PlayerEntity player = p_195939_1_.getPlayer();
		Hand hand = p_195939_1_.getHand();
		ItemStack itemstack = player.getItemInHand(hand);
        BlockPos blockpos = p_195939_1_.getClickedPos();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        BlockState blockstate1 = worldIn.getBlockState(blockpos.above());
        
        if (!blockstate.is(BlockTags.SAND) || !blockstate1.getMaterial().equals(Material.AIR)) {
        	return super.useOn(p_195939_1_);
        }
        
        worldIn.playSound(player, blockpos, FURSoundRegistry.RANDOM_FRUIT_PLANT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        if (!worldIn.isClientSide) {
            if(player == null || !player.isCreative()){
            	itemstack.shrink(1);
            }
            
            worldIn.setBlock(blockpos.above(), FURBlockRegistry.CACTOID_SPROUT.defaultBlockState(), 3);
                      
            if (player instanceof ServerPlayerEntity) {
                  CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos, itemstack);
            }          
        }
             
        return ActionResultType.sidedSuccess(worldIn.isClientSide);
	}
}
