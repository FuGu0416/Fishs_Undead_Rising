package com.Fishmod.fur.worldgen.feature;

import java.util.ArrayList;
import java.util.List;
import com.mojang.serialization.Codec;

import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.WorldGenRegion;
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
        // Require genuine ground below, not a 1-block carver sliver or a ceiling mat hanging in the
        // air (both have air under them) — otherwise the huge mushroom generates floating in mid-air.
        if (!level.getBlockState(pos.below()).isSolid() || !level.getBlockState(pos.below(2)).isSolid()) return false;

        // Spacing: during world generation, don't grow a giant mushroom right next to another one so
        // they stay spread out instead of clumping. Skipped for bonemeal (a ServerLevel, not a
        // WorldGenRegion) so players can still grow a giant exactly where they choose.
        if (level instanceof WorldGenRegion && hasGiantMushroomNearby(level, pos)) return false;

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
	public void makeCap(LevelAccessor worldIn, RandomSource rand, BlockPos position, int height, BlockPos.MutableBlockPos mutable, HugeMushroomFeatureConfiguration config) {
		for (BlockPos P : capShape(position.getX(), position.getY() + height, position.getZ(), config.foliageRadius)) {
			mutable.set(P);
			if (!worldIn.getBlockState(mutable).isSolidRender(worldIn, mutable)) {
				worldIn.setBlock(mutable, config.capProvider.getState(rand, position), 3);
			}
		}
    }

	/**
	 * The exact set of world positions the cap occupies, with the cap top layer at {@code topY}.
	 * Shared by {@link #makeCap} (places the blocks) and {@link #canPlaceCap} (checks the space is
	 * clear) so the two can never disagree. Override to change the cap silhouette.
	 *
	 * <p>This base shape is a small domed cap: a flat 3×3 top plate over a skirt that protrudes ±2
	 * in x/z and descends {@code sideHeight} layers.
	 */
	protected List<BlockPos> capShape(int x, int topY, int z, int sideHeight) {
		List<BlockPos> cap = new ArrayList<>();

		// Flat 3×3 top plate
		for (int px = -1; px <= 1; px++)
			for (int pz = -1; pz <= 1; pz++)
				cap.add(new BlockPos(x + px, topY, z + pz));

		// Side skirt descending sideHeight layers, protruding ±2 in x/z
		for (int py = 1; py <= sideHeight; py++)
			for (int off = -1; off <= 1; off++) {
				cap.add(new BlockPos(x + 2, topY - py, z + off));
				cap.add(new BlockPos(x - 2, topY - py, z + off));
				cap.add(new BlockPos(x + off, topY - py, z + 2));
				cap.add(new BlockPos(x + off, topY - py, z - 2));
			}

		return cap;
	}
	
    /**
     * Returns true only if every block position the cap would occupy is clear
     * (air or leaves). Uses the same {@link #capShape} as makeCap so there are
     * no false positives or false negatives.
     */
    private boolean canPlaceCap(WorldGenLevel level, BlockPos base, int height, int sideHeight) {
        for (BlockPos P : capShape(base.getX(), base.getY() + height, base.getZ(), sideHeight)) {
            if (!isClear(level, P)) return false;
        }
        return true;
    }

    private static boolean isClear(WorldGenLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(BlockTags.LEAVES);
    }

    /** Minimum horizontal gap (blocks) between the bases of two giant mushrooms during worldgen. */
    private static final int MIN_SPACING = 7;

    /**
     * True if any giant-mushroom block (glow shroom or glimmercap cap/stem) already exists within
     * {@link #MIN_SPACING} horizontally of {@code base}. The vertical band (±5) covers the stems of
     * neighbours sitting on a slightly higher/lower cave floor.
     */
    private boolean hasGiantMushroomNearby(WorldGenLevel level, BlockPos base) {
        BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos();
        for (int dx = -MIN_SPACING; dx <= MIN_SPACING; dx++) {
            for (int dz = -MIN_SPACING; dz <= MIN_SPACING; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (dx * dx + dz * dz > MIN_SPACING * MIN_SPACING) continue;
                for (int dy = -5; dy <= 5; dy++) {
                    m.set(base.getX() + dx, base.getY() + dy, base.getZ() + dz);
                    if (isGiantMushroom(level.getBlockState(m))) return true;
                }
            }
        }
        return false;
    }

    private static boolean isGiantMushroom(BlockState s) {
        return s.is(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM.get())
            || s.is(FURBlockRegistry.GLIMMERCAP_BLOCK_STEM.get())
            || s.is(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP.get())
            || s.is(FURBlockRegistry.GLIMMERCAP_BLOCK_CAP.get());
    }

    protected int getTreeRadiusForHeight(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
        return p_225563_4_ <= 3 ? 0 : p_225563_3_;
	}
}
