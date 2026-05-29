package com.Fishmod.fur.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Places a small, shallow water pool on the cave floor.
 * Radius 2–3 blocks (~4×4 to ~6×6 footprint), depth 1–2 blocks.
 * Does not carve the terrain — water is placed only in existing air space.
 */
public class SmallPoolFeature extends Feature<NoneFeatureConfiguration> {

    public SmallPoolFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level  = context.level();
        BlockPos       origin = context.origin();
        RandomSource   random = context.random();

        // origin arrives at floor+1 (first air above floor) via the placed-feature
        // placement chain; scan down to confirm the solid floor block.
        BlockPos floor = null;
        for (int i = 0; i <= 12; i++) {
            BlockPos candidate = origin.below(i);
            if (level.getBlockState(candidate).isSolid()
                    && level.isEmptyBlock(candidate.above())) {
                floor = candidate;
                break;
            }
        }
        if (floor == null) return false;

        int radius = random.nextInt(2) + 2;  // 2 or 3 → ~4–6 block diameter
        int depth  = random.nextInt(2) + 1;  // 1 or 2 blocks of water
        int r2 = radius * radius;

        boolean placed = false;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > r2) continue;

                // Find the highest solid floor with air above within ±2 of the main floor Y.
                BlockPos localFloor = null;
                for (int dy = 2; dy >= -2; dy--) {
                    BlockPos candidate = floor.offset(dx, dy, dz);
                    if (level.getBlockState(candidate).isSolid()
                            && level.isEmptyBlock(candidate.above())) {
                        localFloor = candidate;
                        break;
                    }
                }
                if (localFloor == null) continue;

                for (int layer = 1; layer <= depth; layer++) {
                    BlockPos waterPos = localFloor.above(layer);
                    if (level.isEmptyBlock(waterPos)) {
                        level.setBlock(waterPos, Blocks.WATER.defaultBlockState(), 3);
                        placed = true;
                    }
                }
            }
        }
        return placed;
    }
}
