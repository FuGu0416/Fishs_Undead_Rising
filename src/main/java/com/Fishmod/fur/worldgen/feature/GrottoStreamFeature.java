package com.Fishmod.fur.worldgen.feature;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * A meandering, still-water stream along the grotto floor — a "river" for the Luminous Undergrove.
 *
 * <p>It traces a wandering path from the origin, following the cave floor, and stamps a small channel
 * disc at each step. The whole channel is then filled with <em>source</em> water (no flowing water, which
 * is unstable and laggy at scale): the water surface is a locally smoothed floor height, the bed is mud,
 * and any bank-facing neighbour gets a mud wall up to the waterline so the water is contained.
 *
 * <p>The path bounces inside a small box around the origin (≈17×17, like {@code CaveFloorSmootherFeature})
 * so the feature never writes into far chunks during world generation.
 */
public class GrottoStreamFeature extends Feature<NoneFeatureConfiguration> {

    /** Max horizontal drift of the path centre from the origin (keeps writes within the local area). */
    private static final int BOUND = 6;

    public GrottoStreamFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level  = context.level();
        BlockPos      origin = context.origin();
        RandomSource  random = context.random();

        BlockPos start = findFloor(level, origin.getX(), origin.getY(), origin.getZ(), 12);
        if (start == null) return false;

        int length = 18 + random.nextInt(14);   // 18–31 steps
        int radius = 1 + random.nextInt(2);      // channel half-width 1–2 (3–5 wide)
        int depth  = 1 + random.nextInt(2);      // 1–2 deep

        int ox = start.getX();
        int oz = start.getZ();

        // ── 1. Trace a meandering path along the floor, collecting channel cells -> floor Y ─────────
        Map<BlockPos, Integer> floorY = new HashMap<>();
        double angle = random.nextDouble() * Math.PI * 2.0;
        double fx = ox + 0.5;
        double fz = oz + 0.5;
        int cy = start.getY();

        for (int step = 0; step < length; step++) {
            int px = Mth.floor(fx);
            int pz = Mth.floor(fz);

            BlockPos f = findFloor(level, px, cy + 2, pz, 6);
            if (f != null) {
                cy = f.getY();
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (dx * dx + dz * dz > radius * radius + 1) continue;
                        int x = px + dx, z = pz + dz;
                        BlockPos col = findFloor(level, x, cy + 2, z, 4);
                        if (col != null) floorY.merge(new BlockPos(x, 0, z), col.getY(), Math::min);
                    }
                }
            }

            // Meander, bouncing off the bounding box so the channel stays local.
            angle += (random.nextDouble() - 0.5) * 0.7;
            double nx = fx + Math.cos(angle);
            double nz = fz + Math.sin(angle);
            if (Math.abs(nx - ox) > BOUND) { angle = Math.PI - angle; nx = fx + Math.cos(angle); }
            if (Math.abs(nz - oz) > BOUND) { angle = -angle;           nz = fz + Math.sin(angle); }
            fx = nx;
            fz = nz;
        }
        if (floorY.size() < 6) return false;

        // ── 2. Smooth the water surface: localWaterY = min floor among channel cells within radius ──
        Map<BlockPos, Integer> waterY = new HashMap<>();
        for (BlockPos c : floorY.keySet()) {
            int w = Integer.MAX_VALUE;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    Integer y = floorY.get(new BlockPos(c.getX() + dx, 0, c.getZ() + dz));
                    if (y != null) w = Math.min(w, y);
                }
            }
            waterY.put(c, w);
        }

        // ── 3. Fill water + mud bed, clear above, and wall off bank-facing neighbours ──────────────
        boolean placed = false;
        for (Map.Entry<BlockPos, Integer> e : floorY.entrySet()) {
            int x  = e.getKey().getX();
            int z  = e.getKey().getZ();
            int fY = e.getValue();
            int wY = waterY.get(e.getKey());

            for (int y = Math.max(fY, wY); y >= wY - depth; y--) {
                BlockPos p = new BlockPos(x, y, z);
                if (y > wY) {
                    level.setBlock(p, Blocks.AIR.defaultBlockState(), 3);          // open channel above water
                } else if (y <= wY - depth) {
                    level.setBlock(p, Blocks.MUD.defaultBlockState(), 3);          // channel bed
                } else if (level.getBlockState(p).getFluidState().isEmpty()) {
                    level.setBlock(p, Blocks.WATER.defaultBlockState(), 3);        // still source water
                    placed = true;
                }
            }

            // Bank walls: a 4-neighbour that isn't part of the channel gets mud up to the waterline.
            for (Direction d : Direction.Plane.HORIZONTAL) {
                BlockPos n = new BlockPos(x + d.getStepX(), 0, z + d.getStepZ());
                if (floorY.containsKey(n)) continue;
                for (int y = wY - depth; y <= wY; y++) {
                    BlockPos wall = new BlockPos(n.getX(), y, n.getZ());
                    if (!level.getBlockState(wall).isSolid()) {
                        level.setBlock(wall, Blocks.MUD.defaultBlockState(), 3);
                    }
                }
            }
        }
        return placed;
    }

    /** Scans downward from {@code yStart} (up to {@code range} blocks) for a solid floor with air above. */
    private static BlockPos findFloor(WorldGenLevel level, int x, int yStart, int z, int range) {
        for (int y = yStart; y >= yStart - range; y--) {
            BlockPos p = new BlockPos(x, y, z);
            if (level.getBlockState(p).isSolid() && level.isEmptyBlock(p.above())) {
                return p;
            }
        }
        return null;
    }
}
