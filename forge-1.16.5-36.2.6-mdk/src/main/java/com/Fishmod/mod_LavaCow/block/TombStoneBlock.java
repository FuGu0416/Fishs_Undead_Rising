package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.UnburiedEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class TombStoneBlock extends Block implements IWaterLoggable {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape X_AXIS_AABB = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 16.0D, 12.0D);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 16.0D, 14.0D);
	
	public TombStoneBlock(AbstractBlock.Properties p_i48301_1_) {
		super(p_i48301_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

    @Override
	public VoxelShape getOcclusionShape(BlockState state, IBlockReader reader, BlockPos pos) {
		return this.getShape(state, reader, pos, null);
	}

    @Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return this.getShape(state, reader, pos, context);
	}
    
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return this.getShape(state, reader, pos, null);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
    	return this.getShape(state, reader, pos, context);
    }
    
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
    	Direction enumfacing = p_220053_1_.getValue(FACING);
        return enumfacing.getAxis() == Direction.Axis.Z ? X_AXIS_AABB : Z_AXIS_AABB;
	}
    
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    	Direction enumfacing = state.getValue(FACING);
        int i = worldIn.getEntitiesOfClass(UnburiedEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(8.0D)).size();
        
    	if(i < 3 && worldIn.isAreaLoaded(pos, 3) && rand.nextInt(100) < FURConfig.Cemetery_SpawnRate.get() && !worldIn.isDay() && worldIn.getDifficulty() != Difficulty.PEACEFUL) {           
	        UnburiedEntity entityunburied = FUREntityRegistry.UNBURIED.create(worldIn);
	        
			switch(enumfacing) {
				case NORTH:
					entityunburied.moveTo(pos.south(), 0.0F, 0.0F);
					break;
				case EAST:
					entityunburied.moveTo(pos.west(), 0.0F, 0.0F);
					break;
				case WEST:
					entityunburied.moveTo(pos.east(), 0.0F, 0.0F);
					break;
				case SOUTH:
					entityunburied.moveTo(pos.north(), 0.0F, 0.0F);
					break;
				default:
					break;
			}	
			
	        if(!worldIn.isClientSide())
	        	worldIn.addFreshEntity(entityunburied);
        }
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        BlockPos blockpos = p_196258_1_.getClickedPos();
        FluidState fluidstate = p_196258_1_.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return blockstate;
    }
    
    /**
     * Determines if an entity can path through this block
     */
    @Override
    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
	}
    
    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, WATERLOGGED);
	}
	
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }
}
