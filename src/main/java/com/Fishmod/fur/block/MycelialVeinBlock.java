package com.Fishmod.fur.block;

import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MycelialVeinBlock extends CarpetBlock {
    public static final IntegerProperty PHASE = IntegerProperty.create("phase", 0, 3);
    private static final DustParticleOptions SPORE = new DustParticleOptions(new Vector3f(0.0F, 0.995F, 0.982F), 0.75F);

    public MycelialVeinBlock(BlockBehaviour.Properties properties) {
        super(properties.lightLevel(state -> {
                    int phase = state.getValue(PHASE);
                    return switch (phase) {
                        case 1 -> 3; 
                        case 2 -> 5;
                        default -> 0;
                    };
                })
        );

        this.registerDefaultState(this.stateDefinition.any().setValue(PHASE, 0));
    }
    
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity) {
            int phase = state.getValue(PHASE);

            if (phase == 0) {
                level.setBlock(pos, state.setValue(PHASE, 1), 3);
                level.scheduleTick(pos, this, 10);
            }
        }

        super.entityInside(state, level, pos, entity);
    }
    
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int phase = state.getValue(PHASE);

        if (phase == 1) {
        	level.sendParticles(SPORE, pos.getX() + 0.5D, pos.getY() + 0.05D, pos.getZ() + 0.5D, 4, 0.2D, 0.0D, 0.2D, 0.01D);
            level.setBlock(pos, state.setValue(PHASE, 2), 3);
            level.scheduleTick(pos, this, 30);
        } else if (phase == 2) {
            level.setBlock(pos, state.setValue(PHASE, 3), 3);
            level.scheduleTick(pos, this, 10);
        } else if (phase == 3) {
            level.setBlock(pos, state.setValue(PHASE, 0), 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PHASE);
    }
}
