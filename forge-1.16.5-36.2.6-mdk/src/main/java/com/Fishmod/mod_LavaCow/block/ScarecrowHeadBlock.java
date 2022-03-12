package com.Fishmod.mod_LavaCow.block;

import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_commonTileEntity;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_plagueTileEntity;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_strawTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class ScarecrowHeadBlock extends ContainerBlock implements IArmorVanishable {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    public int type = 0;
    
    public ScarecrowHeadBlock(int typeIn, AbstractBlock.Properties p_i48452_2_)
    {
        super(p_i48452_2_);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, Integer.valueOf(0)));
        this.type = typeIn;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
	}
    
    @Override
    public VoxelShape getOcclusionShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
        return VoxelShapes.empty();
	}

    @Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return this.defaultBlockState().setValue(ROTATION, Integer.valueOf(MathHelper.floor((double)(p_196258_1_.getRotation() * 16.0F / 360.0F) + 0.5D) & 15));
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		switch(this.type) {
			case 1:
				return new ScarecrowHead_strawTileEntity();
			case 2:
				return new ScarecrowHead_plagueTileEntity();
			case 0:
			default:
				return new ScarecrowHead_commonTileEntity();
		}
	}

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(ROTATION, Integer.valueOf(p_185499_2_.rotate(p_185499_1_.getValue(ROTATION), 16)));
	}

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.setValue(ROTATION, Integer.valueOf(p_185471_2_.mirror(p_185471_1_.getValue(ROTATION), 16)));
	}

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(ROTATION);
	}
    
    @Override
    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
	}
}
