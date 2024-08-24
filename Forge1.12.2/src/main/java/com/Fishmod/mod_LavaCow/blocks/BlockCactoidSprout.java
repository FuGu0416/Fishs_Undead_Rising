package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityCactoid;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockCactoidSprout extends Block {
	private static final AxisAlignedBB SPROUT_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
    public static final PropertyInteger HATCH = PropertyInteger.create("age", 0, 2);

	public BlockCactoidSprout() {
		super(Material.PLANTS);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HATCH, Integer.valueOf(0)));
		this.setHardness(0.4F);
        this.setSoundType(SoundType.CLOTH);
        this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        Block sand = world.getBlockState(pos.down()).getBlock();
        
		if ((sand.getMaterial(state) == Material.SAND || sand instanceof BlockSoulSand) && !(world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
			int i = (int)(world.getWorldTime() % 24000L);
			if (i >= 21600 && i <= 22550 || world.rand.nextInt(500) == 0) {
				world.setBlockState(pos, state.withProperty(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
				world.setBlockToAir(pos);
				world.playEvent(2001, pos, Block.getStateId(state));
                EntityCactoid cactoid = new EntityCactoid(world);
                cactoid.setGrowingAge(-24000);
                cactoid.setLocationAndAngles((double)pos.getX() + 0.3D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                cactoid.playSound(FishItems.ENTITY_CACTYRANT_GROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
                
            	// Nether (Basalt Deltas) Variant
                if(world.provider.isNether() || sand instanceof BlockSoulSand) {
                	cactoid.setSkin(3);
                 }
                world.spawnEntity(cactoid);
			}
		}
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
    @Override
    public int quantityDropped(Random random) {
        return 0;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
	
	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}
	
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
		return SPROUT_AABB.offset(state.getOffset(world, pos));
	}
	
    protected int getAge(IBlockState state) {
        return ((Integer)state.getValue(HATCH)).intValue();
    }
	
    public int getMetaFromState(IBlockState state) {
        return this.getAge(state);
    }
    
    public int getStateFromMeta(IBlockState state) {
        return this.getAge(state);
    }
	
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HATCH);
    }
}
