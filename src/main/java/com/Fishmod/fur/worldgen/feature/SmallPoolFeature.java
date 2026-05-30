package com.Fishmod.fur.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Places a small, shallow mud-lined water pool carved into the floor.
 *
 * Shape: organically irregular via 8-sector angular noise.
 * Structure (per cell, driven by normalised distance d from centre):
 *   • mudTop = (waterY − depth) + floor(d² × (depth + heightDiff))
 *   • y > waterY && y > mudTop  → AIR  (cleared terrain above bank)
 *   • y > waterY && y ≤ mudTop  → MUD  (raised mud bank above waterline)
 *   • y ≤ waterY && y ≤ mudTop  → MUD  (pool wall / bottom)
 *   • y ≤ waterY && y > mudTop  → WATER
 *
 * The d² bowl curve means:
 *   centre (d=0): only a mud bottom, full water column.
 *   edge   (d=1): all mud, no open water — forms a smooth mud bank.
 *   between: gradual shallowing so different-height terrain produces a
 *            natural slope rather than abrupt vertical walls.
 *
 * Sparse sea pickles grow on the mud bottom inside the water zone.
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

        // Placement chain delivers origin at floor+1; scan down to the solid floor.
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
        int side   = 2 * radius + 1;

        // Organic boundary: 8 angular sectors, each with a randomly perturbed
        // effective radius. Adjacent sectors are linearly interpolated per cell.
        float[] sectorR = new float[8];
        for (int i = 0; i < 8; i++) {
            sectorR[i] = radius + (random.nextFloat() - 0.5f) * 2.0f; // radius ± 1.0
        }

        // Precompute pool mask and per-cell effective radius (reused in pass 2 for d).
        // Math.atan2(0,0) == 0.0 in Java so the centre cell (dx=dz=0) is safe.
        boolean[][] inPool   = new boolean[side][side];
        float[][]   cellEffR = new float[side][side];
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double angle     = Math.atan2(dz, dx);
                double normAngle = (angle / Math.PI + 1.0) * 4.0;   // 0 … 8
                int    s0        = (int) normAngle & 7;
                int    s1        = (s0 + 1) & 7;
                double t         = normAngle - Math.floor(normAngle);
                float  effR      = (float) (sectorR[s0] * (1.0 - t) + sectorR[s1] * t);
                cellEffR[dx + radius][dz + radius] = effR;
                float  dist      = (float) Math.sqrt(dx * dx + dz * dz);
                inPool  [dx + radius][dz + radius] = (dist <= effR); // dist==0 ≤ effR always
            }
        }

        // Pass 1: find local floor per cell; minimum Y becomes the flat water surface.
        BlockPos[][] localFloors = new BlockPos[side][side];
        int waterY = Integer.MAX_VALUE;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (!inPool[dx + radius][dz + radius]) continue;
                for (int dy = 2; dy >= -2; dy--) {
                    BlockPos candidate = floor.offset(dx, dy, dz);
                    if (level.getBlockState(candidate).isSolid()
                            && level.isEmptyBlock(candidate.above())) {
                        localFloors[dx + radius][dz + radius] = candidate;
                        if (candidate.getY() < waterY) waterY = candidate.getY();
                        break;
                    }
                }
            }
        }
        if (waterY == Integer.MAX_VALUE) return false;

        // Pass 2: fill each cell column using the mud-bowl formula.
        boolean placed = false;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (!inPool[dx + radius][dz + radius]) continue;
                BlockPos localFloor = localFloors[dx + radius][dz + radius];
                if (localFloor == null) continue;

                // d² bowl curve: gives a flat water centre with mud banks rising
                // smoothly toward the pool edge and into higher surrounding terrain.
                float dist       = (float) Math.sqrt(dx * dx + dz * dz);
                float effR       = cellEffR[dx + radius][dz + radius];
                float d          = (effR > 0f) ? Math.min(1f, dist / effR) : 0f;
                float d2         = d * d;

                int heightDiff   = localFloor.getY() - waterY;            // ≥ 0
                int mudTop       = (waterY - depth) + (int)(d2 * (depth + heightDiff));
                mudTop           = Math.min(mudTop, localFloor.getY());   // cap at terrain

                // Iterate from original terrain surface down to pool bottom.
                for (int y = localFloor.getY(); y >= waterY - depth; y--) {
                    BlockPos pos = localFloor.atY(y);
                    if (y > waterY) {
                        // Above waterline: mud bank if within mudTop, else clear to air.
                        level.setBlock(pos,
                            y <= mudTop ? Blocks.MUD.defaultBlockState()
                                        : Blocks.AIR.defaultBlockState(),
                            3);
                    } else if (y <= mudTop) {
                        // At/below waterline but within mudTop: pool wall / bottom mud.
                        level.setBlock(pos, Blocks.MUD.defaultBlockState(), 3);
                    } else {
                        // Above mudTop, at/below waterline: open water.
                        if (level.getBlockState(pos).getFluidState().isEmpty()) {
                            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
                            placed = true;
                        }
                    }
                }

                // Sparse sea pickle directly above the mud bottom (~15 % per water-zone cell).
                if (mudTop < waterY && random.nextFloat() < 0.15f) {
                    BlockPos seaPicklePos = localFloor.atY(mudTop + 1);
                    if (level.getBlockState(seaPicklePos.below()).is(Blocks.MUD)) {
                        level.setBlock(seaPicklePos,
                            Blocks.SEA_PICKLE.defaultBlockState()
                                .setValue(SeaPickleBlock.PICKLES, 1 + random.nextInt(3))
                                .setValue(BlockStateProperties.WATERLOGGED, true),
                            3);
                    }
                }
            }
        }
        return placed;
    }
}
