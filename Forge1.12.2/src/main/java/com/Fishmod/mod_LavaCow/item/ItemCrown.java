package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrown extends ItemFishCustom{    
	public ItemCrown(String registryName, CreativeTabs tab, boolean hasTooltip) {
		super(registryName, null, tab, hasTooltip);		
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	   
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
    	return EnumRarity.UNCOMMON;
    }   
	
    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.getHeldItem(hand).getMetadata() == 1 && worldIn.getBlockState(pos).getBlock().equals(Blocks.SKULL)) {
        	TileEntity tileentity = worldIn.getTileEntity(pos);
        	
        	if (tileentity instanceof TileEntitySkull) {
        		TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
        		
        		if(tileentityskull.getSkullType() == 0) {
        			if(!player.isCreative())
    					player.getHeldItem(hand).shrink(1);
        			
        			worldIn.destroyBlock(pos, false);
        			worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), true));
        			
    				if(!worldIn.isRemote) {
    		        	EntitySkeletonKing entityskeletonking = new EntitySkeletonKing(worldIn);
    		        	
    		        	entityskeletonking.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
    		        	worldIn.spawnEntity(entityskeletonking);
    	        	}
    				
        			return EnumActionResult.PASS;
        		}
        	}
        }
        
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		int i = stack.getMetadata();
		list.add(I18n.format("tootip.mod_lavacow.kings_crown." + i));
		list.add("");
		list.add(TextFormatting.ITALIC + I18n.format("tootip.mod_lavacow.kings_crown." + i + ".desc0"));
		list.add(TextFormatting.ITALIC + I18n.format("tootip.mod_lavacow.kings_crown." + i + ".desc1"));
		list.add(TextFormatting.ITALIC + I18n.format("tootip.mod_lavacow.kings_crown." + i + ".desc2"));
	}
	
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 2; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
