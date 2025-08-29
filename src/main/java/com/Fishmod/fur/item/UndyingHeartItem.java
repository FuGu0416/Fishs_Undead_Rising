package com.Fishmod.fur.item;

import com.Fishmod.fur.block.ScarecrowHeadBlock;
import com.Fishmod.fur.block.ScarecrowHeadBlock.Types;
import com.Fishmod.fur.entities.tameable.ScarecrowEntity;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class UndyingHeartItem extends FURItem {

	public UndyingHeartItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public InteractionResult useOn(UseOnContext p_195939_1_) {
		Level worldIn = p_195939_1_.getLevel();
		BlockPos pos = p_195939_1_.getClickedPos();
		Player player = p_195939_1_.getPlayer();
		ItemStack itemstack = p_195939_1_.getItemInHand();
		
		if((worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_COMMON.get()) || worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_STRAW.get()) || worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_PLAGUE.get())) 
				&& (worldIn.getBlockState(pos.below()).getBlock().equals(Blocks.HAY_BLOCK) || worldIn.getBlockState(pos.below()).getBlock().equals(FURBlockRegistry.DISEASED_HAY_BLOCK.get())) 
				&& (worldIn.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK) || worldIn.getBlockState(pos.below()).getBlock().equals(FURBlockRegistry.DISEASED_HAY_BLOCK.get()))) {
			Types type = ((ScarecrowHeadBlock)worldIn.getBlockState(pos.above()).getBlock()).type;
			
	        if (!player.isCreative()) {
	            itemstack.shrink(1);
	        }
			
			worldIn.destroyBlock(pos, false);
			worldIn.destroyBlock(pos.below(), false);
			worldIn.destroyBlock(pos.above(), false);
			
			if(!worldIn.isClientSide) {
	        	ScarecrowEntity scarecrowentity = FUREntityRegistry.SCARECROW.get().create(worldIn);
	        	
	        	scarecrowentity.tame(player);
	        	scarecrowentity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
	        	scarecrowentity.setSkin(type.ordinal());
	        	worldIn.addFreshEntity(scarecrowentity);
	        	
	            for(ServerPlayer serverplayerentity1 : worldIn.getEntitiesOfClass(ServerPlayer.class, scarecrowentity.getBoundingBox().inflate(5.0D))) {
	                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, scarecrowentity);
	            }
        	}
			
			return InteractionResult.CONSUME;
		}
						
		return super.useOn(p_195939_1_);		
	}
}
