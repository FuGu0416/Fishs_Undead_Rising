package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemCactusFruit extends ItemFishCustomFood {
	public ItemCactusFruit(String registryName, int amount, float saturation, boolean isWolfFood, int duration, boolean hasTooltip) {
		super(registryName, amount, saturation, isWolfFood, duration, hasTooltip);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block sand = world.getBlockState(pos).getBlock();
        
        if (player.isSneaking() && facing == EnumFacing.UP && world.isAirBlock(pos.up()) && sand.getMaterial(state) == Material.SAND | sand instanceof BlockSoulSand) {
            world.setBlockState(pos.up(), Modblocks.CACTOID_SPROUT.getDefaultState());
            
            world.playSound(player, pos, FishItems.RANDOM_FRUIT_PLANT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);
            }
            
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        
        else {
            return EnumActionResult.FAIL;
        }
    }
}
