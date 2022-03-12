package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.block.ScarecrowHeadBlock;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarecrowEntity;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UndyingHeartItem extends Item {

	public UndyingHeartItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		World worldIn = p_195939_1_.getLevel();
		BlockPos pos = p_195939_1_.getClickedPos();
		PlayerEntity player = p_195939_1_.getPlayer();
		ItemStack itemstack = p_195939_1_.getItemInHand();
		
		if((worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_COMMON) || worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_STRAW) || worldIn.getBlockState(pos.above()).getBlock().equals(FURBlockRegistry.SCARECROWHEAD_PLAGUE)) && worldIn.getBlockState(pos.below()).getBlock().equals(Blocks.HAY_BLOCK) && worldIn.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK)) {
			int type = ((ScarecrowHeadBlock)worldIn.getBlockState(pos.above()).getBlock()).type;
			
	        if (!player.isCreative()) {
	            itemstack.shrink(1);
	        }
			
			worldIn.destroyBlock(pos, false);
			worldIn.destroyBlock(pos.below(), false);
			worldIn.destroyBlock(pos.above(), false);
			
			if(!worldIn.isClientSide) {
	        	ScarecrowEntity scarecrowentity = FUREntityRegistry.SCARECROW.create(worldIn);
	        	
	        	scarecrowentity.tame(player);
	        	scarecrowentity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
	        	scarecrowentity.setSkin(type);
	        	worldIn.addFreshEntity(scarecrowentity);
	        	
	            for(ServerPlayerEntity serverplayerentity1 : worldIn.getEntitiesOfClass(ServerPlayerEntity.class, scarecrowentity.getBoundingBox().inflate(5.0D))) {
	                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, scarecrowentity);
	            }
        	}
			
			return ActionResultType.CONSUME;
		}
						
		return super.useOn(p_195939_1_);		
	}
}
