package com.Fishmod.fur.block.blockentity;

import com.Fishmod.fur.init.FURBlockEntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ScarecrowHead_plagueTileEntity extends ScarecrowHeadTileEntity {
	public ScarecrowHead_plagueTileEntity(BlockPos Pos, BlockState State) {
		super(FURBlockEntityRegistry.SCARECROWHEAD_PLAGUE.get(), Pos, State);
	}
}
