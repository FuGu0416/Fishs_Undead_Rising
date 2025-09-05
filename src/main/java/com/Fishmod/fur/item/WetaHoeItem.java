package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WetaHoeItem extends HoeItem {
	private Item repair_material;
	
	public WetaHoeItem(Properties p_i48487_1_, Tier materialIn, int damageIn, float attackspeedIn, Item repair) {
		super(materialIn, damageIn, attackspeedIn, p_i48487_1_);
		this.repair_material = repair;
	}

	/**
	* Called when this item is used when targeting a Block
	*/
	@Override
	public InteractionResult useOn(UseOnContext p_195939_1_) {
		Level worldIn = p_195939_1_.getLevel();
		BlockPos pos = p_195939_1_.getClickedPos();
		Block block = worldIn.getBlockState(pos).getBlock();
		boolean flag = false;
		
		if (block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof BushBlock) {
	    	for (int x = -1; x <= 1 ; x++) {
	    		for (int y = -1; y <= 1 ; y++) {
	    			for (int z = -1; z <= 1 ; z++) {
	    				BlockPos pos_crop = pos.offset(x, y, z);
	    				BlockState iblockstate_crop = worldIn.getBlockState(pos_crop);
			            if (iblockstate_crop.getBlock() instanceof CropBlock) {
			            	if (((CropBlock)iblockstate_crop.getBlock()).isMaxAge(iblockstate_crop)) {
				                worldIn.destroyBlock(pos_crop, true);
				                worldIn.setBlock(pos_crop, iblockstate_crop.getBlock().defaultBlockState(), 3);
				                
				                if (!flag) {
				                	flag = true;
				                }
			            	}
			            } else if (iblockstate_crop.getBlock() instanceof NetherWartBlock) {
			            	if (iblockstate_crop.getValue(NetherWartBlock.AGE) >= 3) {
				                worldIn.destroyBlock(pos_crop, true);
				                worldIn.setBlock(pos_crop, iblockstate_crop.getBlock().defaultBlockState(), 3);
				                
				                if (!flag) {
				                	flag = true;
				                }
			            	}
			            } else if (iblockstate_crop.getBlock() instanceof BushBlock) {
			            	worldIn.destroyBlock(pos_crop, true);
			            	
			                if (!flag) {
			                	flag = true;
			                }
			            }
	    			}		
				}
			}
	    	
	    	p_195939_1_.getPlayer().getItemInHand(p_195939_1_.getHand()).hurtAndBreak(1, p_195939_1_.getPlayer(), (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
		}

		return super.useOn(p_195939_1_);
	}
    
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(this.repair_material);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    	tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
	}	      
}
