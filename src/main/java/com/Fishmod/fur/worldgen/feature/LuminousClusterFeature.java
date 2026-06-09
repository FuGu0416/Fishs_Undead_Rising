package com.Fishmod.fur.worldgen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/**
 * Places a "hero cluster" for the Luminous Undergrove floor flora:
 * <ol>
 *   <li>a single centerpiece feature (e.g. a HUGE_GLOWSHROOM) at the exact origin, then</li>
 *   <li>a dense RandomPatch of mixed plants radiating around that origin.</li>
 * </ol>
 *
 * <p>The centerpiece anchors the cluster so it reads as a focal point; the patch
 * supplies the surrounding density. Both are referenced as configured features so the
 * composition lives entirely in data ({@code FURConfiguredFeatures}).
 */
public class LuminousClusterFeature extends Feature<LuminousClusterConfiguration> {

    public LuminousClusterFeature(Codec<LuminousClusterConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LuminousClusterConfiguration> context) {
        WorldGenLevel level          = context.level();
        ChunkGenerator generator     = context.chunkGenerator();
        RandomSource random          = context.random();
        BlockPos origin              = context.origin();
        LuminousClusterConfiguration config = context.config();

        // Centerpiece at the exact origin (the giant mushroom) — anchors the cluster.
        // Placed first so the surrounding plants don't get overwritten by its stem.
        boolean placedCenter = config.centerpiece().value().place(level, generator, random, origin);

        // Only grow the vegetation patch when the centerpiece actually placed, so the
        // plants form a cluster around each giant mushroom and never on their own. When the
        // mushroom is suppressed (e.g. spacing/clearance) this attempt leaves nothing behind.
        if (placedCenter) {
            config.patch().value().place(level, generator, random, origin);
        }

        return placedCenter;
    }
}
