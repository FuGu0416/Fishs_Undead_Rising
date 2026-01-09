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

	public UndyingHeartItem(Properties properties) {
		super(properties);
	}

	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Player player = ctx.getPlayer();
		ItemStack itemstack = ctx.getItemInHand();
		
		if((level.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_COMMON.get()) || level.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_STRAW.get()) || level.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_PLAGUE.get())) 
				&& (level.getBlockState(pos.below()).getBlock().equals(Blocks.HAY_BLOCK) || level.getBlockState(pos.below()).getBlock().equals(FURBlockRegistry.DISEASED_HAY_BLOCK.get())) 
				&& (level.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK) || level.getBlockState(pos.below()).getBlock().equals(FURBlockRegistry.DISEASED_HAY_BLOCK.get()))) {
			Types type = ((ScarecrowHeadBlock)level.getBlockState(pos.above()).getBlock()).type;
			
	        if (!player.isCreative()) {
	            itemstack.shrink(1);
	        }
			
			level.destroyBlock(pos, false);
			level.destroyBlock(pos.below(), false);
			level.destroyBlock(pos.above(), false);
			
			if(!level.isClientSide) {
	        	ScarecrowEntity scarecrowentity = FUREntityRegistry.SCARECROW.get().create(level);
	        	
	        	scarecrowentity.tame(player);
	        	scarecrowentity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
	        	scarecrowentity.setSkin(type.ordinal());
	        	level.addFreshEntity(scarecrowentity);
	        	
	            for(ServerPlayer serverplayerentity1 : level.getEntitiesOfClass(ServerPlayer.class, scarecrowentity.getBoundingBox().inflate(5.0D))) {
	                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, scarecrowentity);
	            }
        	}
			
			return InteractionResult.CONSUME;
		}
						
		return super.useOn(ctx);		
	}
}
