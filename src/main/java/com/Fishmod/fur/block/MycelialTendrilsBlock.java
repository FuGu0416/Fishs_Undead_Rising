package com.Fishmod.fur.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MycelialTendrilsBlock extends BushBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

	public MycelialTendrilsBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState p_54955_, BlockGetter p_54956_, BlockPos p_54957_, CollisionContext p_54958_) {
		return SHAPE;
	}

	protected boolean mayPlaceOn(BlockState p_54960_, BlockGetter p_54961_, BlockPos p_54962_) {
		return super.mayPlaceOn(p_54960_, p_54961_, p_54962_);
	}
}