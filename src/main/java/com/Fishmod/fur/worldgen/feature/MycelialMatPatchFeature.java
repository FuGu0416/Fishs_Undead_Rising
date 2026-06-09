package com.Fishmod.fur.worldgen.feature;

import com.Fishmod.fur.init.FURBlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import com.Fishmod.fur.data.providers.FURBlockTagsProvider;

/**
 * Places a mycelial-mat patch on the cave floor.
 * Shape is organically irregular via 8-sector angular noise (same algorithm as
 * SmallPoolFeature) instead of the rectangular base of vanilla VegetationPatch.
 * Only replaces blocks in MAT_REPLACEABLE.
 *
 * <p>It also seeds mycelial_tendrils onto the mat at a low per-block "vegetation chance"
 * as ambient ground cover. The showy plants (glowshroom / glimmercap / emberwick fungus)
 * are NOT seeded here — they grow as clusters around the giant mushrooms.
 */
public class MycelialMatPatchFeature extends Feature<NoneFeatureConfiguration> {

    public MycelialMatPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level  = context.level();
        BlockPos       origin = context.origin();
        RandomSource   random = context.random();

        // Placement chain delivers origin at floor+1; scan down to confirm the solid floor.
        BlockPos centerFloor = null;
        for (int i = 0; i <= 4; i++) {
            BlockPos candidate = origin.below(i);
            if (level.getBlockState(candidate).isSolid()
                    && level.isEmptyBlock(candidate.above())) {
                centerFloor = candidate;
                break;
            }
        }
        if (centerFloor == null) return false;

        // Radius matches the vanilla VegetationPatch xzRadius range (4–7).
        int radius = 4 + random.nextInt(4);
        int side   = 2 * radius + 1;

        // Ambient ground cover: mycelial_tendrils seeded onto the mat at a low chance per
        // block (the mat-equivalent of VegetationPatch's vegetationChance). Resolve the
        // tendrils placed feature once. Showy plants are handled by the cluster features.
        PlacedFeature tendrils = level.registryAccess()
                .registryOrThrow(Registries.PLACED_FEATURE)
                .getHolderOrThrow(FURPlacedFeatures.MYCELIAL_TENDRILS_PLACED)
                .value();

        // Organic boundary: 8 angular sectors, each with a randomly perturbed
        // radius (±30 % of the base). Smooth interpolation between sectors
        // produces natural-looking, non-rectangular patch outlines.
        float[] sectorR = new float[8];
        for (int i = 0; i < 8; i++) {
            sectorR[i] = radius + (random.nextFloat() - 0.5f) * radius * 0.6f;
        }

        boolean[][] inPatch = new boolean[side][side];
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx == 0 && dz == 0) {
                    inPatch[dx + radius][dz + radius] = true;
                    continue;
                }
                double angle     = Math.atan2(dz, dx);
                double normAngle = (angle / Math.PI + 1.0) * 4.0;
                int    s0        = (int) normAngle & 7;
                int    s1        = (s0 + 1) & 7;
                double t         = normAngle - Math.floor(normAngle);
                float  effR      = (float) (sectorR[s0] * (1.0 - t) + sectorR[s1] * t);
                inPatch[dx + radius][dz + radius] = (dx * dx + dz * dz <= effR * effR);
            }
        }

        boolean placed = false;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (!inPatch[dx + radius][dz + radius]) continue;

                // Find the local floor surface within ±2 of the centre floor Y.
                BlockPos localFloor = null;
                for (int dy = 2; dy >= -2; dy--) {
                    BlockPos candidate = centerFloor.offset(dx, dy, dz);
                    if (level.getBlockState(candidate).isSolid()
                            && level.isEmptyBlock(candidate.above())) {
                        localFloor = candidate;
                        break;
                    }
                }
                if (localFloor == null) continue;

                // Replace moss/vegetation-patch targets PLUS any ore exposed at the
                // floor surface (MAT_REPLACEABLE = #moss_replaceable + #forge:ores) —
                // otherwise ores poke through the mat. Ores are only capped here at
                // the surface; deeper veins below the mat are left intact for mining
                // (the depth pass below stays on MOSS_REPLACEABLE).
                if (!level.getBlockState(localFloor).is(FURBlockTagsProvider.MAT_REPLACEABLE)) continue;

                level.setBlock(localFloor, FURBlockRegistry.MYCELIAL_MAT.get().defaultBlockState(), 3);
                placed = true;

                // Ambient tendrils ground cover (vegetationChance 4% per mat block; -50%).
                if (random.nextFloat() < 0.04f) {
                    tendrils.place(level, context.chunkGenerator(), random, localFloor.above());
                }

                // Replicate VegetationPatch depth behaviour: optionally replace
                // 1–2 blocks below the surface for a more embedded appearance.
                if (random.nextFloat() < 0.8f) {
                    BlockPos below1 = localFloor.below();
                    if (level.getBlockState(below1).is(BlockTags.MOSS_REPLACEABLE)) {
                        level.setBlock(below1, FURBlockRegistry.MYCELIAL_MAT.get().defaultBlockState(), 3);
                        if (random.nextFloat() < 0.5f) {
                            BlockPos below2 = below1.below();
                            if (level.getBlockState(below2).is(BlockTags.MOSS_REPLACEABLE)) {
                                level.setBlock(below2, FURBlockRegistry.MYCELIAL_MAT.get().defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
        return placed;
    }
}
