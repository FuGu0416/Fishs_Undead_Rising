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
	public MycelialMatBlock(BlockBehaviour.Properties p_153790_) {
		super(p_153790_);
	}

	public boolean isValidBonemealTarget(LevelReader p_256507_, BlockPos p_256224_, BlockState p_256628_, boolean p_256093_) {
		return p_256507_.getBlockState(p_256224_.above()).isAir();
	}

	public boolean isBonemealSuccess(Level p_221538_, RandomSource p_221539_, BlockPos p_221540_, BlockState p_221541_) {
		return true;
	}

	public void performBonemeal(ServerLevel p_221533_, RandomSource p_221534_, BlockPos p_221535_, BlockState p_221536_) {
		p_221533_.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((p_258973_) -> {
			return p_258973_.getHolder(FURConfiguredFeatures.MIXED_FLOOR_PATCH);
		}).ifPresent((p_255669_) -> {
			p_255669_.value().place(p_221533_, p_221533_.getChunkSource().getGenerator(), p_221534_, p_221535_.above());
		});
	}
}
