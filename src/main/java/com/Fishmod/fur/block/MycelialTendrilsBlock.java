package com.Fishmod.fur.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MycelialTendrilsBlock extends BushBlock {
	// Variant 2 was split out into the standalone EmberwickFungusBlock, so only 0–1 remain.
	public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 1);
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

	public MycelialTendrilsBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, Integer.valueOf(0)));
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
		level.setBlock(pos, state.setValue(VARIANT, level.random.nextInt(2)), 3);
	}

	public VoxelShape getShape(BlockState p_54955_, BlockGetter p_54956_, BlockPos p_54957_, CollisionContext p_54958_) {
		return SHAPE;
	}

	protected boolean mayPlaceOn(BlockState p_54960_, BlockGetter p_54961_, BlockPos p_54962_) {
		return super.mayPlaceOn(p_54960_, p_54961_, p_54962_);
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}