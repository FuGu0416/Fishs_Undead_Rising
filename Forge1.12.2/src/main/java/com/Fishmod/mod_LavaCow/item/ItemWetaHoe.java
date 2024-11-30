package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWetaHoe extends ItemHoe {

    public ItemWetaHoe(String registryName) {
        super(FishItems.TOOL_WETA);
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
    }

    /**
     * Called when this item is used when targeting a Block
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();
        boolean flag = false;

        if (block instanceof BlockCrops || block instanceof BlockNetherWart || block instanceof BlockBush) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos_crop = pos.add(x, y, z);
                        IBlockState iblockstate_crop = worldIn.getBlockState(pos_crop);
                        if (iblockstate_crop.getBlock() instanceof BlockCrops) {
                            if (((BlockCrops) iblockstate_crop.getBlock()).isMaxAge(iblockstate_crop)) {
                                worldIn.destroyBlock(pos_crop, true);
                                worldIn.setBlockState(pos_crop, iblockstate_crop.getBlock().getDefaultState(), 3);

                                if (!flag) {
                                    flag = true;
                                }
                            }
                        } else if (iblockstate_crop.getBlock() instanceof BlockNetherWart) {
                            if (iblockstate_crop.getValue(BlockNetherWart.AGE) >= 3) {
                                worldIn.destroyBlock(pos_crop, true);
                                worldIn.setBlockState(pos_crop, iblockstate_crop.getBlock().getDefaultState(), 3);

                                if (!flag) {
                                    flag = true;
                                }
                            }
                        } else if (iblockstate_crop.getBlock() instanceof BlockBush) {
                            worldIn.destroyBlock(pos_crop, true);

                            if (!flag) {
                                flag = true;
                            }
                        }
                    }
                }
            }

            if (!worldIn.isRemote && flag) {
                player.getHeldItem(hand).damageItem(1, player);
            }
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.YELLOW + I18n.format("tootip." + mod_LavaCow.MODID + ".weta_hoe"));
    }
}
