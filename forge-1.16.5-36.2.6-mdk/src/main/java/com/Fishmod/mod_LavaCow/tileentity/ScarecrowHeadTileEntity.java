package com.Fishmod.mod_LavaCow.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ScarecrowHeadTileEntity extends TileEntity {

	public ScarecrowHeadTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	@Override
	public CompoundNBT save(CompoundNBT p_189515_1_) {
		super.save(p_189515_1_);
		return p_189515_1_;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 4, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}
}
