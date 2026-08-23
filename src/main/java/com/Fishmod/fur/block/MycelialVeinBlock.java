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
        if (!level.isClientSide && entity instanceof LivingEntity && state.getValue(PHASE) == 0) {
            activate((ServerLevel) level, pos, state);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int phase = state.getValue(PHASE);

        if (phase == 0) {
            // Woken by a neighboring vein's wave (see activate()) rather than direct entity contact.
            activate(level, pos, state);
        } else if (phase == 1) {
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

    /**
     * Lights this vein up and nudges any still-dormant (phase 0) neighboring vein blocks to catch the
     * wave a few ticks later, so a whole connected patch lights up as an outward-rippling wave instead
     * of everything flashing on at once. Terminates naturally - it only ever wakes phase-0 neighbors, so
     * it can't ping back into a tile that's already lit (or chain-react instantly, since each hop is
     * staggered by its own scheduled tick).
     *
     * <p>Covers all 8 surrounding tiles, not just the 4 orthogonal ones - a carpet-like block laid on a
     * diagonal-stepped patch (e.g. covering a stairs-shaped floor) can have two veins only touching
     * corner-to-corner (x+1,z+1 etc.) with no orthogonally-adjacent tile between them, and the wave
     * should still cross that gap instead of stopping dead at the diagonal seam.
     */
    private void activate(ServerLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(PHASE, 1), 3);
        level.scheduleTick(pos, this, 10);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                BlockPos neighborPos = pos.offset(dx, 0, dz);
                BlockState neighborState = level.getBlockState(neighborPos);
                if (neighborState.is(this) && neighborState.getValue(PHASE) == 0) {
                    level.scheduleTick(neighborPos, this, 6 + level.random.nextInt(4));
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PHASE);
    }
}
