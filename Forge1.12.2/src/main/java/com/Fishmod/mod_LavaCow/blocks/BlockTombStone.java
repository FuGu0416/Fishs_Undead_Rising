package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTombStone extends Block{
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.25D, 0.875D, 1.0D, 0.75D);
	protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.125D, 0.75D, 1.0D, 0.875D);
	
	public BlockTombStone() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.setTickRandomly(true);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        return enumfacing.getAxis() == EnumFacing.Axis.Z ? X_AXIS_AABB : Z_AXIS_AABB;
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        int i = worldIn.getEntitiesWithinAABB(EntityUnburied.class, (new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).grow(8.0D)).size();

    	if(i < 8 && worldIn.isAreaLoaded(pos, 3) && rand.nextFloat() < 0.1F && !worldIn.isDaytime() && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL) {
	        EntityUnburied entityunburied = new EntityUnburied(worldIn);
	        
			switch(enumfacing) {
				case NORTH:
					entityunburied.moveToBlockPosAndAngles(pos.south(), 0.0F, 0.0F);
					break;
				case EAST:
					entityunburied.moveToBlockPosAndAngles(pos.west(), 0.0F, 0.0F);
					break;
				case WEST:
					entityunburied.moveToBlockPosAndAngles(pos.east(), 0.0F, 0.0F);
					break;
				case SOUTH:
					entityunburied.moveToBlockPosAndAngles(pos.north(), 0.0F, 0.0F);
					break;
				default:
					break;
			}	
			
			entityunburied.onInitialSpawn(worldIn.getDifficultyForLocation(pos), (IEntityLivingData)null);

	        if(!worldIn.isRemote)
	        	worldIn.spawnEntity(entityunburied);
        }
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }
    
    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    /**
     * Determines if an entity can path through this block
     */
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        return i;
    }
    
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

}
