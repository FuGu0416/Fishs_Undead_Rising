package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWetaHoe extends ItemHoe{

	public ItemWetaHoe(String registryName) {
		super(ToolMaterial.IRON);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
	}
	
    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote)
        {
            stack.damageItem(1, entityLiving);
        }

        Block block = state.getBlock();
        if (block instanceof BlockCrops) {
        	for(int x = -1; x <= 1 ; x++)
        		for(int y = -1; y <= 1 ; y++)
        			for(int z = -1; z <= 1 ; z++) {
        				BlockPos pos_crop = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        				IBlockState iblockstate_crop = worldIn.getBlockState(pos_crop);
			            if (iblockstate_crop.getBlock() instanceof BlockCrops && ((BlockCrops)iblockstate_crop.getBlock()).isMaxAge(iblockstate_crop))
			            {
			                worldIn.destroyBlock(pos_crop, true);
			            }    
        			}
        }
        else if (block instanceof BlockBush) {
        	for(int x = -1; x <= 1 ; x++)
        		for(int y = -1; y <= 1 ; y++)
        			for(int z = -1; z <= 1 ; z++) {
        				BlockPos pos_crop = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
        				IBlockState iblockstate_crop = worldIn.getBlockState(pos_crop);
			            if (iblockstate_crop.getBlock() instanceof BlockBush)
			            {
			                worldIn.destroyBlock(pos_crop, true);
			            }    
        			}
        	
        }
        
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		list.add(TextFormatting.YELLOW + I18n.format("tootip." + mod_LavaCow.MODID + ".weta_hoe"));
	}
        
}
