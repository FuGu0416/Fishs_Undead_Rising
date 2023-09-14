package com.Fishmod.mod_LavaCow.blocks;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEctoplasm extends Block {
    protected static final AxisAlignedBB ECTOPLASM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.975D, 1.0D);
    
	public BlockEctoplasm() {
		super(Material.IRON, MapColor.CYAN);
		this.setHardness(1.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.setSoundType(SoundType.STONE);
	}
	
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
        return ECTOPLASM_AABB;
    }
	
	@Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
		entity.motionX *= 0.4D;
		entity.motionZ *= 0.4D;
    }
	
	@Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }
}
