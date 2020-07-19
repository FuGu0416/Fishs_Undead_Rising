package com.Fishmod.mod_LavaCow.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLargeGlowShroom extends WorldGenerator {

    /** The mushroom type. 0 for brown, 1 for red. */
    private final Block mushroomCap;
    private final Block mushroomStem;
    private final List<BlockPos> GenCap = new ArrayList<BlockPos>();
    
    public WorldGenLargeGlowShroom(Block Cap, Block Stem)
    {
        super(true);
        this.mushroomCap = Cap;
        this.mushroomStem = Stem;
    }

    public WorldGenLargeGlowShroom()
    {
        super(false);
        this.mushroomCap = Blocks.LAPIS_BLOCK;
        this.mushroomStem = Blocks.COAL_BLOCK;
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {

		int StemHeight = 3 + rand.nextInt(3 + rand.nextInt(2));
		int sideHeight = 1 + rand.nextInt(StemHeight > 3 ? 3 : 2);

		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		
		if (!this.isAir(worldIn, x, y, z, x, y + StemHeight - sideHeight, z) || !this.isAir(worldIn, x - 2, y + StemHeight - sideHeight + 1, z - 2, x + 2, y + StemHeight + 1, z + 2))
			return false;

		//Generate Stem
		for (int i = y; i <= y + StemHeight; i++)
			worldIn.setBlockState(new BlockPos(x, i, z), this.mushroomStem.getDefaultState(), 2);
		
		y += StemHeight + 1;

		for (int px = -1; px <= 1; px++)
			for (int pz = -1; pz <= 1; pz++)
				GenCap.add(new BlockPos(x + px, y, z + pz));

		for (int py = 1; py <= sideHeight; py++)
			for (int off = -1; off <= 1; off++) {
				GenCap.add(new BlockPos(x + 2, y - py, z + off));
				GenCap.add(new BlockPos(x - 2, y - py, z + off));
				GenCap.add(new BlockPos(x + off, y - py, z + 2));
				GenCap.add(new BlockPos(x + off, y - py, z - 2));
			}
		
		//Generate Cap
		for (BlockPos P : GenCap)
			worldIn.setBlockState(new BlockPos(P.getX(), P.getY(), P.getZ()), this.mushroomCap.getDefaultState());

		return true;
    }
	
	private boolean isAir(World worldIn, int X_s, int Y_s, int Z_s, int X_e, int Y_e, int Z_e) {
		for(int i = X_s; i <= X_e; i++)
			for(int j = Y_s; j <= Y_e; j++)
				for(int k = Z_s; k <= Z_e; k++) {
					IBlockState state = worldIn.getBlockState(new BlockPos(i, j, k));
					if (state != Blocks.AIR && !worldIn.isAirBlock(new BlockPos(i, j, k)))
						return false;
				}
		
		return true;
	}
}
