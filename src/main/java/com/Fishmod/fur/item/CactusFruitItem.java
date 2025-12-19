package com.Fishmod.fur.item;

import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CactusFruitItem extends FURItem {
	public CactusFruitItem(Properties p_i48487_1_, int TooltipIn) {
		super(p_i48487_1_, TooltipIn);
	}

	@Override
	public InteractionResult useOn(UseOnContext p_195939_1_) {
		Level worldIn = p_195939_1_.getLevel();
		Player player = p_195939_1_.getPlayer();
		InteractionHand hand = p_195939_1_.getHand();
		ItemStack itemstack = player.getItemInHand(hand);
        BlockPos blockpos = p_195939_1_.getClickedPos();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        BlockState blockstate1 = worldIn.getBlockState(blockpos.above());
        
        if (!(blockstate.is(BlockTags.SAND) || blockstate.getBlock().equals(Blocks.SOUL_SAND)) || !blockstate1.isAir()) {
        	return super.useOn(p_195939_1_);
        }
        
        if (player.isShiftKeyDown()) {
	        worldIn.playSound(player, blockpos, FURSoundRegistry.RANDOM_FRUIT_PLANT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
	        
	        if (!worldIn.isClientSide) {
	            if (player == null || !player.isCreative()){
	            	itemstack.shrink(1);
	            }
	            
	            worldIn.setBlock(blockpos.above(), FURBlockRegistry.CACTOID_SPROUT.get().defaultBlockState(), 3);
	                      
	            if (player instanceof ServerPlayer) {
	                  CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
	            }          
	        }
        } else {
        	return super.useOn(p_195939_1_); 
        }
             
        return InteractionResult.sidedSuccess(worldIn.isClientSide);
	}
}
