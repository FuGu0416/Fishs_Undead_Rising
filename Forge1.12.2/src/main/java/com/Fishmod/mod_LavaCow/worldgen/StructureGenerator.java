package com.Fishmod.mod_LavaCow.worldgen;

import java.util.Random;

import com.Fishmod.mod_LavaCow.client.Modconfig;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureGenerator implements IWorldGenerator {
	public static final WorldGenCemeterySmall CEMETERY_SMALL = new WorldGenCemeterySmall();
	public static final WorldGenAquaMob AQUA_MOB = new WorldGenAquaMob();
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		int x = (chunkX * 16) + 8;
        int z = (chunkZ * 16) + 8;
    	
    	BlockPos pos = world.getHeight(new BlockPos(x, 0, z));
    	Biome biome = world.getBiomeForCoordsBody(pos);
		
    	if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.HILLS) && world.provider.isSurfaceWorld() && rand.nextInt(Modconfig.SpawnRate_Cemetery + 1) == 0 && world.getBlockState(pos.down()).isOpaqueCube() && isSolidGround(pos.down(), world)) {
    		CEMETERY_SMALL.generate(world, rand, pos);
    	}
    	
    	if (true) {
    		AQUA_MOB.generate(world, rand, pos);
    	}
		
	}
	
	private static boolean isSolidGround(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);
        return !(state.getBlock() instanceof BlockLog || state.getBlock() instanceof BlockLeaves || state.getBlock() instanceof BlockLiquid);
	}

}
