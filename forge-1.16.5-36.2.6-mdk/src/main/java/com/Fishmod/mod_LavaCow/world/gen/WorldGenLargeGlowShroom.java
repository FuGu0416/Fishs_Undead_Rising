package com.Fishmod.mod_LavaCow.world.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class WorldGenLargeGlowShroom extends AbstractBigMushroomFeature {

    /** The mushroom type. 0 for brown, 1 for red. */
    private final List<BlockPos> GenCap = new ArrayList<BlockPos>();
    
    public WorldGenLargeGlowShroom(Codec<BigMushroomFeatureConfig> p_i231957_1_)
    {
        super(p_i231957_1_);
    }
	
	@Override
	public void makeCap(IWorld worldIn, Random rand, BlockPos position, int p_225564_4_, BlockPos.Mutable p_225564_5_, BigMushroomFeatureConfig p_225564_6_) {

		//int StemHeight = 3 + rand.nextInt(3 + rand.nextInt(2));
		int sideHeight = p_225564_6_.foliageRadius;//1 + rand.nextInt(StemHeight > 3 ? 3 : 2);

		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		
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
		for (BlockPos P : GenCap) {
			p_225564_5_.setWithOffset(P, 0, p_225564_4_, 0);
			if (worldIn.getBlockState(p_225564_5_).canBeReplacedByLeaves(worldIn, p_225564_5_))
				worldIn.setBlock(p_225564_5_, p_225564_6_.capProvider.getState(rand, position), 3);
		}
    }
	
    protected int getTreeRadiusForHeight(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
        return p_225563_4_ <= 3 ? 0 : p_225563_3_;
	}
}
