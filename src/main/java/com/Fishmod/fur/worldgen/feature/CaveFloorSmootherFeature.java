package com.Fishmod.fur.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;

/**
 * Smooths abrupt floor-height transitions between cave sections carved at
 * different elevations, eliminating hanging walls (懸壁).
 *
 * Algorithm per invocation:
 *   1. Sample the cave floor Y at every (x,z) cell within RADIUS blocks.
 *   2. Apply iterative slope relaxation: adjacent cells may differ by at most
 *      MAX_SLOPE (1 block). Cells are only raised, never lowered.
 *   3. Fill the gap between the original and smoothed floor with stone
 *      (deepslate below Y=0), building a ramp where carvers left a cliff.
 *
 * Stone/deepslate are in BlockTags.MOSS_REPLACEABLE, so the subsequent
 * MYCELIAL_MAT_PATCH pass covers the ramp surface naturally.
 */
public class CaveFloorSmootherFeature extends Feature<NoneFeatureConfiguration> {

    private static final int RADIUS    = 8;
    private static final int SIDE      = 2 * RADIUS + 1;   // 17×17 grid
    private static final int MAX_SLOPE = 1;                 // max height difference per adjacent cell
    private static final int NO_FLOOR  = Integer.MIN_VALUE;

    public CaveFloorSmootherFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level  = context.level();
        BlockPos       origin = context.origin();

        // Placement chain delivers origin at floor+1; scan down to the solid cave floor.
        BlockPos centerFloor = null;
        for (int i = 0; i <= 12; i++) {
            BlockPos c = origin.below(i);
            if (level.getBlockState(c).isSolid() && level.isEmptyBlock(c.above())) {
                centerFloor = c;
                break;
            }
        }
        if (centerFloor == null) return false;

        // ── Step 1: sample floor Y for every cell in the scan grid ────────────
        // Search ±8 blocks vertically from centre-floor Y to catch height
        // differences created by carvers working at different elevations.
        int[][] origFloor = new int[SIDE][SIDE];
        for (int[] row : origFloor) Arrays.fill(row, NO_FLOOR);

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                for (int dy = 8; dy >= -8; dy--) {
                    BlockPos c = centerFloor.offset(dx, dy, dz);
                    if (level.getBlockState(c).isSolid() && level.isEmptyBlock(c.above())) {
                        origFloor[dx + RADIUS][dz + RADIUS] = c.getY();
                        break;
                    }
                }
            }
        }

        // ── Step 2: slope relaxation (raises cells only) ──────────────────────
        // Each pass enforces: adjacent non-missing cells differ by ≤ MAX_SLOPE.
        // A forward and backward sweep per pass ensures fast convergence:
        // a cliff in the middle of the grid resolves in a single pass.
        int[][] smooth = new int[SIDE][SIDE];
        for (int x = 0; x < SIDE; x++) smooth[x] = Arrays.copyOf(origFloor[x], SIDE);

        boolean changed = true;
        int pass = 0;
        while (changed && pass++ < RADIUS * 2) {
            changed = false;
            for (int x = 0; x < SIDE; x++) {
                for (int z = 0; z < SIDE; z++) {
                    if (smooth[x][z] == NO_FLOOR) continue;
                    int mx = neighborMax(smooth, x, z);
                    if (mx != NO_FLOOR && mx - smooth[x][z] > MAX_SLOPE) {
                        smooth[x][z] = mx - MAX_SLOPE;
                        changed = true;
                    }
                }
            }
            for (int x = SIDE - 1; x >= 0; x--) {
                for (int z = SIDE - 1; z >= 0; z--) {
                    if (smooth[x][z] == NO_FLOOR) continue;
                    int mx = neighborMax(smooth, x, z);
                    if (mx != NO_FLOOR && mx - smooth[x][z] > MAX_SLOPE) {
                        smooth[x][z] = mx - MAX_SLOPE;
                        changed = true;
                    }
                }
            }
        }

        // ── Step 3: fill air blocks between original and smoothed floor ────────
        boolean placed = false;
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                int orig   = origFloor[dx + RADIUS][dz + RADIUS];
                int target = smooth  [dx + RADIUS][dz + RADIUS];
                if (orig == NO_FLOOR || target <= orig) continue;

                // Safety cap: never raise a cell more than RADIUS blocks regardless
                // of what the relaxation computed at the grid boundary.
                target = Math.min(target, orig + RADIUS);

                for (int y = orig + 1; y <= target; y++) {
                    BlockPos pos = new BlockPos(
                            centerFloor.getX() + dx, y, centerFloor.getZ() + dz);
                    if (level.isEmptyBlock(pos)) {
                        level.setBlock(pos,
                                y < 0 ? Blocks.DEEPSLATE.defaultBlockState()
                                      : Blocks.STONE.defaultBlockState(),
                                3);
                        placed = true;
                    }
                }
            }
        }
        return placed;
    }

    private static int neighborMax(int[][] arr, int x, int z) {
        int max = NO_FLOOR;
        if (x > 0        && arr[x-1][z] != NO_FLOOR && arr[x-1][z] > max) max = arr[x-1][z];
        if (x < SIDE - 1 && arr[x+1][z] != NO_FLOOR && arr[x+1][z] > max) max = arr[x+1][z];
        if (z > 0        && arr[x][z-1] != NO_FLOOR && arr[x][z-1] > max) max = arr[x][z-1];
        if (z < SIDE - 1 && arr[x][z+1] != NO_FLOOR && arr[x][z+1] > max) max = arr[x][z+1];
        return max;
    }
}
