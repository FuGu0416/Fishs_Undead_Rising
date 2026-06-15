package com.Fishmod.fur.block;

import com.Fishmod.fur.worldgen.feature.FURConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MycelialMatBlock extends Block implements BonemealableBlock {
	public MycelialMatBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return level.getBlockState(pos.above()).isAir();
	}

	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> {
			return registry.getHolder(FURConfiguredFeatures.LUMINOUS_CLUSTER_PATCH_SMALL);
		}).ifPresent((holder) -> {
			holder.value().place(level, level.getChunkSource().getGenerator(), random, pos.above());
		});
	}
}
