package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WetaHoeItem extends HoeItem {
	private Item repair_material;
	
	public WetaHoeItem(Properties p_i48487_1_, String registryName, IItemTier materialIn, int damageIn, float attackspeedIn, Item repair) {
		super(materialIn, damageIn, attackspeedIn, p_i48487_1_);
		this.repair_material = repair;
		this.setRegistryName(registryName);
	}

	/**
	* Called when this item is used when targeting a Block
	*/
	@Override
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		World worldIn = p_195939_1_.getLevel();
		BlockPos pos = p_195939_1_.getClickedPos();
		Block block = worldIn.getBlockState(pos).getBlock();
		boolean flag = false;
		
		if (block instanceof CropsBlock || block instanceof NetherWartBlock || block instanceof BushBlock) {
	    	for (int x = -1; x <= 1 ; x++) {
	    		for (int y = -1; y <= 1 ; y++) {
	    			for (int z = -1; z <= 1 ; z++) {
	    				BlockPos pos_crop = pos.offset(x, y, z);
	    				BlockState iblockstate_crop = worldIn.getBlockState(pos_crop);
			            if (iblockstate_crop.getBlock() instanceof CropsBlock) {
			            	if (((CropsBlock)iblockstate_crop.getBlock()).isMaxAge(iblockstate_crop)) {
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
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	tooltip.add(new TranslationTextComponent("tooltip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}	      
}
