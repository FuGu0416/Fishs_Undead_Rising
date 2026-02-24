package com.Fishmod.fur.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LuminousFilamentBlock extends Block {
    public static final EnumProperty<Segment> SEGMENT = EnumProperty.create("segment", Segment.class);
    public static final IntegerProperty MIRRORED = IntegerProperty.create("mirrored", 0, 3);

    public LuminousFilamentBlock(Properties properties) {
        super(properties.lightLevel(state -> {
        		Segment segment = state.getValue(SEGMENT);
	            return switch (segment) {
	                case UPPER -> 3; 
	                case MIDDLE -> 8;
	                case LOWER -> 10;
	                default -> 0;
	            };
	        })
		);
        this.registerDefaultState(this.stateDefinition.any().setValue(SEGMENT, Segment.MIDDLE).setValue(MIRRORED, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SEGMENT, MIRRORED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.isSolid() || above.is(this);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int mirrored = context.getLevel().getRandom().nextInt(4);

        BlockState state = this.defaultBlockState().setValue(MIRRORED, mirrored);

        return recomputeSegment(context.getLevel(), context.getClickedPos(), state);
    }
    
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (!canSurvive(state, level, pos)) {
                return Blocks.AIR.defaultBlockState();
            }

            return recomputeSegment(level, pos, state);
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
    
    private BlockState recomputeSegment(LevelReader level, BlockPos pos, BlockState state) {
    	boolean aboveSame = level.getBlockState(pos.above()).is(this);
    	boolean belowSame = level.getBlockState(pos.below()).is(this);

    	Segment segment;

    	if (!aboveSame && belowSame) {
			segment = Segment.UPPER;
    	} else if (!belowSame) {
    		segment = Segment.LOWER;
    	} else {
    		segment = Segment.MIDDLE;
    	}

    	return state.setValue(SEGMENT, segment);
    }

    public enum Segment implements StringRepresentable {
        UPPER("upper"),
        MIDDLE("middle"),
        LOWER("lower");

        private final String name;

        Segment(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}