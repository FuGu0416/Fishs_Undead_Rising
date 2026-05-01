package com.Fishmod.fur.worldgen.feature;

import java.util.ArrayList;
import java.util.List;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class LargeGlowShroomFeature extends AbstractHugeMushroomFeature {
    private final List<BlockPos> GenCap = new ArrayList<BlockPos>();
    
    public LargeGlowShroomFeature(Codec<HugeMushroomFeatureConfiguration> p_i231957_1_) {
        super(p_i231957_1_);
    }

    // Override to accept any solid block as ground (vanilla requires MUSHROOM_GROW_BLOCK tag).
    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos       = context.origin();
        RandomSource rand  = context.random();
        HugeMushroomFeatureConfiguration config = context.config();
        int height = rand.nextInt(3) + 4;

        if (pos.getY() + height + 1 >= level.getMaxBuildHeight()) return false;
        if (!level.getBlockState(pos.below()).isSolid()) return false;

        // Only verify the center stem column is clear. The cap spreads freely into
        // available air, allowing generation in typical cave heights (5-8 blocks).
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int y = 0; y < height; ++y) {
            mutable.setWithOffset(pos, 0, y, 0);
            BlockState s = level.getBlockState(mutable);
            if (!s.isAir() && !s.is(BlockTags.LEAVES)) return false;
        }

        this.makeCap(level, rand, pos, height, new BlockPos.MutableBlockPos(), config);

        BlockPos.MutableBlockPos stem = new BlockPos.MutableBlockPos();
        for (int y = 0; y < height; ++y) {
            stem.setWithOffset(pos, 0, y, 0);
            BlockState s = level.getBlockState(stem);
            if (s.isAir() || s.is(BlockTags.LEAVES)) {
                level.setBlock(stem, config.stemProvider.getState(rand, pos), 2);
            }
        }
        return true;
    }
	
	@Override
	public void makeCap(LevelAccessor worldIn, RandomSource rand, BlockPos position, int p_225564_4_, BlockPos.MutableBlockPos p_225564_5_, HugeMushroomFeatureConfiguration p_225564_6_) {
		int sideHeight = p_225564_6_.foliageRadius;
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		
		GenCap.clear();
		
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
			if (!worldIn.getBlockState(p_225564_5_).isSolidRender(worldIn, p_225564_5_)) {
				worldIn.setBlock(p_225564_5_, p_225564_6_.capProvider.getState(rand, position), 3);
			}
		}
    }
	
    protected int getTreeRadiusForHeight(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
        return p_225563_4_ <= 3 ? 0 : p_225563_3_;
	}
}
