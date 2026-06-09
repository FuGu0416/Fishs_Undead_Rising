package com.Fishmod.fur.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Emberwick Fungus — a standalone glowing fungus split out from the old
 * {@code mycelial_tendrils} "variant 2". Unlike mycelial tendrils (an ambient ground
 * cover scattered across the mycelial mat), the Emberwick Fungus only generates inside
 * Luminous Undergrove vegetation clusters. Same small bush shape as the tendrils.
 */
public class EmberwickFungusBlock extends BushBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

	public EmberwickFungusBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}
}
