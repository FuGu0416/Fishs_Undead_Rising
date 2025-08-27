package com.Fishmod.fur.block.blockentity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ScarecrowHeadTileEntity extends BlockEntity {

	public ScarecrowHeadTileEntity(BlockEntityType<?> Type, BlockPos Pos, BlockState State) {
		super(Type, Pos, State);
	}

	@Override
	protected void saveAdditional(CompoundTag p_189515_1_) {
		super.saveAdditional(p_189515_1_);
	}

	@Override
	public void load(CompoundTag p_230337_2_) {
		super.load(p_230337_2_);
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}
}
