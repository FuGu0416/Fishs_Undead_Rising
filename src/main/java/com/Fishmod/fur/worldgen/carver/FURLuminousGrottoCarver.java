package com.Fishmod.fur.worldgen.carver;

import java.util.function.Function;

import com.Fishmod.fur.init.FURBiomesRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class FURLuminousGrottoCarver extends WorldCarver<CaveCarverConfiguration> {

    public FURLuminousGrottoCarver(Codec<CaveCarverConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean isStartChunk(CaveCarverConfiguration config, RandomSource random) {
        return random.nextFloat() < config.probability;
    }

    @Override
    public boolean carve(CarvingContext context, CaveCarverConfiguration config,
                         ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor,
                         RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask mask) {

        int floorY = config.y.sample(random, context);
        double cx = chunkPos.getMiddleBlockX();
        double cz = chunkPos.getMiddleBlockZ();

        if (!biomeAccessor.apply(new BlockPos((int) cx, floorY, (int) cz))
                          .is(FURBiomesRegistry.LUMINOUS_UNDERGROVE)) {
            return false;
        }

        double hMult = config.horizontalRadiusMultiplier.sample(random);
        // Halve the vertical radius so cave height is ~50% of before. Each blob's floor is
        // (centerY - vRadius) = blobFloor, which is independent of the radius, so the floor level is
        // unchanged and only the ceiling drops — the cave gets shorter, not shallower.
        double vMult = config.verticalRadiusMultiplier.sample(random) * 0.5D;

        // ── Main volume: heterogeneous blob cluster ─────────────────────────────
        // A mix of wide-flat blobs and narrow-tall blobs ensures the cave cross-section
        // changes noticeably with height, eliminating the cylindrical appearance.
        // Blobs also have varied floor offsets so neither floor nor ceiling is flat.
        int    blobCount    = 10 + random.nextInt(5);          // 10–14 (more blobs → larger space)
        double clusterSpread = 12.0 + random.nextDouble() * 10.0;
        for (int i = 0; i < blobCount; i++) {
            double angle = i * (Math.PI * 2.0 / blobCount) + random.nextDouble() * 1.2;
            double dist  = clusterSpread * (0.1 + random.nextDouble() * 0.9);
            double bx    = cx + Math.cos(angle) * dist;
            double bz    = cz + Math.sin(angle) * dist;

            // 35 % of blobs are narrow-tall; the rest are wide-flat.
            // This contrast is what breaks the constant-cross-section (cylinder) look.
            double bH, bV;
            if (random.nextFloat() < 0.35f) {
                bH = (8.0  + random.nextDouble() * 6.0) * hMult;  // narrow: 8–14
                bV = (8.0  + random.nextDouble() * 6.0) * vMult;  // tall:   8–14
            } else {
                bH = (14.0 + random.nextDouble() * 8.0) * hMult;  // wide:  14–22
                bV = (4.0  + random.nextDouble() * 3.0) * vMult;  // short:  4–7
            }

            // Floor follows a smooth positional undulation (sine hills) plus a small
            // random jitter, so neighbouring blobs sit at similar heights and the floor
            // rolls gently instead of jumping randomly between blobs.
            int    blobFloor = floorY + (int) Math.round(undulateFloor(bx, bz))
                                      + random.nextInt(3);
            double bCy       = blobFloor + bV;
            carveEllipsoid(context, config, chunk, biomeAccessor, aquifer,
                bx, bCy, bz, bH, bV, mask,
                (ctx, rx, ry, rz, y) -> false);
        }

        // ── Wall nooks ─────────────────────────────────────────────────────────
        // Tall, narrow ellipsoids placed at or just beyond the cluster perimeter.
        // They carve vertical recesses (columns, alcoves) into the wall so that
        // a side-on view of any wall section shows irregular notches instead of
        // a smooth continuous surface — directly addressing the cylindrical look.
        int nookCount = 3 + random.nextInt(3);                 // 3–5 nooks
        for (int i = 0; i < nookCount; i++) {
            double angle  = random.nextDouble() * Math.PI * 2.0;
            double dist   = clusterSpread * (0.7 + random.nextDouble() * 0.5);
            double nx     = cx + Math.cos(angle) * dist;
            double nz     = cz + Math.sin(angle) * dist;
            double nH     = (3.0 + random.nextDouble() * 5.0) * hMult;  // narrow: 3–8
            double nV     = (7.0 + random.nextDouble() * 7.0) * vMult;  // tall:   7–14
            int    nFloor = floorY + (int) Math.round(undulateFloor(nx, nz))
                                   + random.nextInt(3);
            double nCy    = nFloor + nV;
            carveEllipsoid(context, config, chunk, biomeAccessor, aquifer,
                nx, nCy, nz, nH, nV, mask,
                (ctx, rx, ry, rz, y) -> false);
        }

        // ── Satellite lobes ─────────────────────────────────────────────────────
        int lobeCount = 6 + random.nextInt(4);                 // 6–9 (more lobes → wider space)
        for (int i = 0; i < lobeCount; i++) {
            double angle     = i * (Math.PI * 2.0 / lobeCount) + random.nextDouble() * 0.5;
            double dist      = 12.0 + random.nextDouble() * 14.0;
            double lx        = cx + Math.cos(angle) * dist;
            double lz        = cz + Math.sin(angle) * dist;
            int    lobeFloor = floorY + (int) Math.round(undulateFloor(lx, lz))
                                      + random.nextInt(3) - 1;
            double lh        = (7.0 + random.nextDouble() * 7.0) * hMult;
            double lv        = (3.5 + random.nextDouble() * 3.0) * vMult;
            double lobeCy    = lobeFloor + lv;
            carveEllipsoid(context, config, chunk, biomeAccessor, aquifer,
                lx, lobeCy, lz, lh, lv, mask,
                (ctx, rx, ry, rz, y) -> false);
        }

        return true;
    }

    /**
     * Smooth, seamless floor-height offset as a function of world X/Z. A sum of
     * low-frequency sine waves produces gentle rolling hills that stay continuous
     * across chunk and blob boundaries, giving the grotto floor a natural undulation
     * instead of a flat plane or randomly jagged steps.
     */
    private static double undulateFloor(double x, double z) {
        return Math.sin(x * 0.05)       * 3.0
             + Math.cos(z * 0.045)      * 2.5
             + Math.sin((x + z) * 0.03) * 2.0;
    }
}
