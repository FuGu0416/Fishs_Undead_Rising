package com.Fishmod.fur.worldgen.feature;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

/**
 * Giant Glimmercap: reuses {@link LargeGlowShroomFeature}'s grounded placement and stem, but swaps the
 * cap silhouette for a wide, flat single-layer disc so it reads like a vanilla giant brown mushroom
 * (flat-topped) instead of the glow shroom's small domed cap.
 */
public class GiantGlimmercapFeature extends LargeGlowShroomFeature {

    public GiantGlimmercapFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    // Cap radius: r = 2 yields a 5-block-wide flat cap (2r+1).
    private static final int CAP_RADIUS = 2;

    /**
     * A single flat layer at the top: a {@code (2r+1)×(2r+1)} square with the four corners removed,
     * where {@code r = CAP_RADIUS} (= 2, so a 5-block-wide cap). This is the vanilla brown-mushroom
     * cap shape — a flat disc — rather than a domed/skirted cap.
     */
    @Override
    protected List<BlockPos> capShape(int x, int topY, int z, int sideHeight) {
        int r = CAP_RADIUS;
        List<BlockPos> cap = new ArrayList<>();

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                // Cut the four corners so the flat cap has a rounded (octagonal) outline
                if (Math.abs(dx) == r && Math.abs(dz) == r) continue;
                cap.add(new BlockPos(x + dx, topY, z + dz));
            }
        }

        return cap;
    }
}
