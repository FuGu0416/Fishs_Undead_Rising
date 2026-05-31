package com.Fishmod.mod_LavaCow.worldgen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.blocks.BlockGlowShroom;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlowShroom extends WorldGenerator {
    private BlockGlowShroom glowshroom = Modblocks.GLOWSHROOM;
    private int spawnRate;

    public WorldGenGlowShroom() {
    }

    public boolean generate(BlockGlowShroom shroom, World worldIn, Random rand, BlockPos position, int spawnRate) {
        this.glowshroom = shroom;
        this.spawnRate = spawnRate;
        return generate(worldIn, rand, position);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (blockpos.getY() >= 70 || rand.nextInt(100) >= this.spawnRate) {
                continue;
            }
            if (!worldIn.isAirBlock(blockpos) || !glowshroom.canBlockStay(worldIn, blockpos, glowshroom.getDefaultState())) {
                continue;
            }
            // Read stored chunk light directly — World.getLight() goes through
            // Chunk.getLightSubtracted which forces the lighting engine to flush
            // pending updates synchronously, stalling chunk population.
            Chunk chunk = worldIn.getChunk(blockpos);
            int blockLight = chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos);
            int skyLight = Math.max(0, chunk.getLightFor(EnumSkyBlock.SKY, blockpos) - worldIn.getSkylightSubtracted());
            if (Math.max(blockLight, skyLight) < 10) {
                worldIn.setBlockState(blockpos, glowshroom.withAge(rand.nextInt(2)), 2);
            }
        }

        return true;
    }
}
