package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_common;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_plague;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_straw;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockScarecrowHead extends BlockContainer {
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 1.0D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.5D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 0.5D, 0.75D, 0.75D);
    public int type = 0;
    
    public BlockScarecrowHead(int typeIn)
    {
        super(Material.CIRCUITS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
        this.type = typeIn;
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
    
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	return DEFAULT_AABB;
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		switch(this.type) {
			case 0:
				return new TileEntityScarecrowHead_common();
			case 1:
				return new TileEntityScarecrowHead_straw();
			case 2:
				return new TileEntityScarecrowHead_plague();
			default:
				return new TileEntityScarecrowHead_common();
		}
	}
	
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		switch(this.type) {
			case 0:
				return new ItemStack(Modblocks.item_scarecrowhead_common, 1);
			case 1:
				return new ItemStack(Modblocks.item_scarecrowhead_straw, 1);
			case 2:
				return new ItemStack(Modblocks.item_scarecrowhead_plague, 1);
			default:
				return new ItemStack(Modblocks.item_scarecrowhead_common, 1);
		}
    }
    
    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(!player.isCreative())
        	this.dropBlockAsItem(worldIn, pos, state, 0);

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		Block blocks;
    	
    	switch(this.type) {
			case 0:
				blocks = Blocks.LIT_PUMPKIN;
				break;
			case 1:
				blocks = Blocks.HAY_BLOCK;
				break;
			case 2:
				blocks = Blocks.COAL_BLOCK;
				break;
			default:
				blocks = Blocks.LIT_PUMPKIN;
				break;
		}
    	
    	worldIn.playEvent(2001, pos, Block.getStateId(blocks.getDefaultState()));
    	super.breakBlock(worldIn, pos, state);
    }
    
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess worldIn, BlockPos pos, IBlockState state, int fortune)
    {
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityScarecrowHead)
            {
                ItemStack itemstack;

        		switch(this.type) {
        			case 0:
        				itemstack = new ItemStack(Modblocks.item_scarecrowhead_common, 1);
        				break;
        			case 1:
        				itemstack = new ItemStack(Modblocks.item_scarecrowhead_straw, 1);
        				break;
        			case 2:
        				itemstack = new ItemStack(Modblocks.item_scarecrowhead_plague, 1);
        				break;
        			default:
        				itemstack = new ItemStack(Modblocks.item_scarecrowhead_common, 1);
        				break;
        		}

                drops.add(itemstack);
            }
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		switch(this.type) {
			case 0:
				return Modblocks.item_scarecrowhead_common;
			case 1:
				return Modblocks.item_scarecrowhead_straw;
			case 2:
				return Modblocks.item_scarecrowhead_plague;
			default:
				return Modblocks.item_scarecrowhead_common;
		}
    }

    public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack)
    {
    	return false;
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

    /**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     * 
     * @return an approximation of the form of the given face
     */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
