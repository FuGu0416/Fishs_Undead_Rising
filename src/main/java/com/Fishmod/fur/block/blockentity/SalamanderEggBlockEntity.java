package com.Fishmod.fur.block.blockentity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.init.FURBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SalamanderEggBlockEntity extends BlockEntity {
    @Nullable
    private UUID placerUUID;
    private int skin;
    
    public SalamanderEggBlockEntity(BlockPos pos, BlockState state) {
        super(FURBlockEntityRegistry.SALAMANDER_EGG.get(), pos, state);
    }

    public void setPlacer(@Nullable UUID uuid) {
        this.placerUUID = uuid;
        setChanged();
    }

    @Nullable
    public UUID getPlacer() {
        return this.placerUUID;
    }
    
    public void setSkin(int variant) {
        this.skin = variant;
        this.setChanged();
    }

    public int getSkin() {
        return skin;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (placerUUID != null) {
            tag.putUUID("Placer", placerUUID);
        }
        tag.putInt("Variant", this.skin);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.hasUUID("Placer")) {
            placerUUID = tag.getUUID("Placer");
        }
        this.skin = tag.getInt("Variant");
    }
}