package com.Fishmod.fur.block;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TombStoneBlock extends Block implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
	protected static final VoxelShape X_AXIS_AABB = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 16.0D, 12.0D);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 16.0D, 14.0D);
	
	public TombStoneBlock(Properties p_i48301_1_) {
		super(p_i48301_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(NATURAL, Boolean.valueOf(false)));
	}

    @Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
		return this.getShape(state, reader, pos, null);
	}

    @Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return this.getShape(state, reader, pos, context);
	}
    
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return this.getShape(state, reader, pos, null);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
    	return this.getShape(state, reader, pos, context);
    }
    
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
    	Direction enumfacing = p_220053_1_.getValue(FACING);
        return enumfacing.getAxis() == Direction.Axis.Z ? X_AXIS_AABB : Z_AXIS_AABB;
	}
    
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
    	if (!state.getValue(NATURAL) || !worldIn.isAreaLoaded(pos, 3)) return;
    	
    	Direction enumfacing = state.getValue(FACING);
        int i = worldIn.getEntitiesOfClass(UnburiedEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(8.0D)).size();
        
    	if(i < 3 && rand.nextInt(100) < FURConfig.Cemetery_SpawnRate.get() && !worldIn.isDay() && worldIn.getDifficulty() != Difficulty.PEACEFUL) {           
	        UnburiedEntity entityunburied = FUREntityRegistry.UNBURIED.get().create(worldIn);
	        	        
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
			
			entityunburied.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(entityunburied.blockPosition()), MobSpawnType.REINFORCEMENT, null, (CompoundTag)null);
			
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
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        BlockPos blockpos = p_196258_1_.getClickedPos();
        FluidState fluidstate = p_196258_1_.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(NATURAL, Boolean.valueOf(false));
        return blockstate;
    }
    
    /**
     * Determines if an entity can path through this block
     */
    @Override
    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
        return false;
	}
    
    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, WATERLOGGED, NATURAL);
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
