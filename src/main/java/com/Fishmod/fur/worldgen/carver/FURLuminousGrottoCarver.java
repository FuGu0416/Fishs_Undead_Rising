package com.Fishmod.fur.worldgen.carver;

import java.util.function.Function;

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

        int baseCy = config.y.sample(random, context);
        double cx = chunkPos.getMiddleBlockX();
        double cz = chunkPos.getMiddleBlockZ();

        // Primary chamber — larger than a single vanilla cave, anchors the room
        double primaryH = (12.0 + random.nextDouble() * 6.0) * config.horizontalRadiusMultiplier.sample(random);
        double primaryV = (4.0 + random.nextDouble() * 3.0) * config.verticalRadiusMultiplier.sample(random);
        carveEllipsoid(context, config, chunk, biomeAccessor, aquifer,
            cx, baseCy, cz, primaryH, primaryV, mask,
            (ctx, relX, relY, relZ, y) -> false);

        // Satellite lobes distributed evenly around the chamber.
        // Each lobe is offset 8–18 blocks from the center so it reaches into neighboring
        // start chunks' territory — adjacent rooms' primaries and lobes overlap and merge
        // into one continuous open space rather than isolated bubbles.
        int lobeCount = 4 + random.nextInt(3);
        for (int i = 0; i < lobeCount; i++) {
            double angle = i * (Math.PI * 2.0 / lobeCount) + random.nextDouble() * 0.5;
            double dist  = 8.0 + random.nextDouble() * 10.0;
            double lx    = cx + Math.cos(angle) * dist;
            double lz    = cz + Math.sin(angle) * dist;
            int    ly    = baseCy + random.nextInt(7) - 3;
            double lh    = (5.0 + random.nextDouble() * 5.0) * config.horizontalRadiusMultiplier.sample(random);
            double lv    = (2.5 + random.nextDouble() * 2.0) * config.verticalRadiusMultiplier.sample(random);
            carveEllipsoid(context, config, chunk, biomeAccessor, aquifer,
                lx, ly, lz, lh, lv, mask,
                (ctx, relX, relY, relZ, y) -> false);
        }

        return true;
    }
}
