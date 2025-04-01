package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID)
public class BlockDiseasedHay extends BlockRotatedPillar {
    public BlockDiseasedHay() {
        super(Material.GRASS, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
        this.setHardness(0.5F);
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        this.setSoundType(SoundType.PLANT);
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
     * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
     * of whether the block can receive random update ticks
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(8) == 0) {
            spawnParticles(worldIn, pos);
        }

        super.randomDisplayTick(stateIn, worldIn, pos, rand);
    }

    public static void spawnParticles(World worldIn, BlockPos pos) {
        Random random = worldIn.rand;

        for (EnumFacing enumfacing : EnumFacing.values()) {
            BlockPos blockpos = pos.offset(enumfacing);
            if (!worldIn.getBlockState(blockpos).isOpaqueCube() && worldIn.isRemote) {
                EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
                double d1 = enumfacing$axis == EnumFacing.Axis.X ? 0.5D + 0.5625D * (double) enumfacing.getXOffset() : (double) random.nextFloat();
                double d2 = enumfacing$axis == EnumFacing.Axis.Y ? 0.5D + 0.5625D * (double) enumfacing.getYOffset() : (double) random.nextFloat();
                double d3 = enumfacing$axis == EnumFacing.Axis.Z ? 0.5D + 0.5625D * (double) enumfacing.getZOffset() : (double) random.nextFloat();
                mod_LavaCow.PROXY.spawnCustomParticle("locust_swarm", worldIn, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D, 0.0F, 0.98F, 0.93F);
            }
        }

    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 0.2F);
    }
}
