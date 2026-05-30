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
        if (!level.getBlockState(pos.below()).isSolid()) return false;

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int clearance = 0;
        while (clearance < 20) {
            mutable.setWithOffset(pos, 0, clearance, 0);
            BlockState ps = level.getBlockState(mutable);
            if (!ps.isAir() && !ps.is(BlockTags.LEAVES)) break;
            clearance++;
        }
        int maxHeight = Math.min(clearance - 1, 12);
        if (maxHeight < 4) return false;
        int height = 4 + rand.nextInt(maxHeight - 3);

        if (pos.getY() + height + 1 >= level.getMaxBuildHeight()) return false;

        if (!canPlaceCap(level, pos, height, config.foliageRadius)) return false;

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
		
		List<BlockPos> genCap = new ArrayList<>();

		for (int px = -1; px <= 1; px++)
			for (int pz = -1; pz <= 1; pz++)
				genCap.add(new BlockPos(x + px, y, z + pz));

		for (int py = 1; py <= sideHeight; py++)
			for (int off = -1; off <= 1; off++) {
				genCap.add(new BlockPos(x + 2, y - py, z + off));
				genCap.add(new BlockPos(x - 2, y - py, z + off));
				genCap.add(new BlockPos(x + off, y - py, z + 2));
				genCap.add(new BlockPos(x + off, y - py, z - 2));
			}

		for (BlockPos P : genCap) {
			p_225564_5_.setWithOffset(P, 0, p_225564_4_, 0);
			if (!worldIn.getBlockState(p_225564_5_).isSolidRender(worldIn, p_225564_5_)) {
				worldIn.setBlock(p_225564_5_, p_225564_6_.capProvider.getState(rand, position), 3);
			}
		}
    }
	
    /**
     * Returns true only if every block position the cap would occupy is clear
     * (air or leaves). Mirrors the exact position set built in makeCap so there
     * are no false positives or false negatives.
     */
    private boolean canPlaceCap(WorldGenLevel level, BlockPos base, int height, int sideHeight) {
        int x = base.getX(), y = base.getY(), z = base.getZ();

        // Flat top — 3×3 plate at y + height
        for (int px = -1; px <= 1; px++) {
            for (int pz = -1; pz <= 1; pz++) {
                if (!isClear(level, new BlockPos(x + px, y + height, z + pz))) return false;
            }
        }

        // Side skirt — descends py layers below the top, protruding ±2 in x/z
        for (int py = 1; py <= sideHeight; py++) {
            int wy = y + height - py;
            for (int off = -1; off <= 1; off++) {
                if (!isClear(level, new BlockPos(x + 2,   wy, z + off))) return false;
                if (!isClear(level, new BlockPos(x - 2,   wy, z + off))) return false;
                if (!isClear(level, new BlockPos(x + off, wy, z + 2  ))) return false;
                if (!isClear(level, new BlockPos(x + off, wy, z - 2  ))) return false;
            }
        }

        return true;
    }

    private static boolean isClear(WorldGenLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(BlockTags.LEAVES);
    }

    protected int getTreeRadiusForHeight(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
        return p_225563_4_ <= 3 ? 0 : p_225563_3_;
	}
}
