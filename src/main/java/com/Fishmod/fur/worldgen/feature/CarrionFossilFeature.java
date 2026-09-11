package com.Fishmod.fur.worldgen.feature;

import java.util.Optional;

import com.mojang.serialization.Codec;

import com.Fishmod.fur.block.FURShroomBlock;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/**
 * Places one of vanilla's nether-fossil bone-pile templates (the same
 * {@code nether_fossils/fossil_1..14.nbt} files the real {@code minecraft:nether_fossil}
 * structure uses) as a standalone feature, then scatters a small clump of Bloodtooth/Cordy/Veil
 * Shroom around it so the fossils always read as a little overgrown pocket rather than a bare
 * bone pile.
 *
 * <p>This exists because {@code NetherFossilStructure} hardcodes its floor search to stop at
 * the dimension's sea level ({@code ChunkGenerator#getSeaLevel()}, y=63 in the Overworld) — it
 * scans downward from its height sample looking for a floor and gives up the moment it reaches
 * sea level, so it can never place inside a deep cave biome carved at y -56~-24 like Carrion
 * Hollow. This clones the template-pick/rotate/place half of vanilla's
 * {@code NetherFossilPieces.addPieces} without that restriction, landing wherever the placed
 * feature's own environment scan finds a valid cave floor instead.
 */
public class CarrionFossilFeature extends Feature<NoneFeatureConfiguration> {

    // Same 14 templates minecraft:nether_fossil picks from — no need to copy the assets,
    // vanilla's structure template files are loaded by ResourceLocation like any other.
    private static final ResourceLocation[] FOSSILS = {
            new ResourceLocation("minecraft", "nether_fossils/fossil_1"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_2"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_3"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_4"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_5"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_6"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_7"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_8"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_9"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_10"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_11"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_12"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_13"),
            new ResourceLocation("minecraft", "nether_fossils/fossil_14"),
    };

    // The three shrooms that grow around a fossil — picked per-attempt, not per-fossil, so a
    // single clump is a mix of all three rather than one uniform patch.
    private static final Block[] SHROOMS = {
            FURBlockRegistry.BLOODTOOTH_SHROOM.get(),
            FURBlockRegistry.CORDY_SHROOM.get(),
            FURBlockRegistry.VEIL_SHROOM.get(),
    };

    private static final int SHROOM_TRIES  = 10;
    private static final int SHROOM_RADIUS = 5;

    public CarrionFossilFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        // Placement chain delivers origin around floor level; scan down to confirm an
        // actual solid floor with open air above it (same defensive pattern as
        // MycelialMatPatchFeature's centerFloor scan).
        BlockPos floor = null;
        for (int i = 0; i <= 4; i++) {
            BlockPos candidate = origin.below(i);
            if (level.getBlockState(candidate).isSolid()
                    && level.isEmptyBlock(candidate.above())) {
                floor = candidate.above();
                break;
            }
        }
        if (floor == null) return false;

        ResourceLocation chosen = FOSSILS[random.nextInt(FOSSILS.length)];
        Optional<StructureTemplate> template = level.getLevel().getStructureManager().get(chosen);
        if (template.isEmpty()) return false;

        // Mirrors NetherFossilPieces.NetherFossilPiece#makeSettings exactly.
        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(Rotation.getRandom(random))
                .setMirror(Mirror.NONE)
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);

        if (!template.get().placeInWorld(level, floor, floor, settings, random, 2)) return false;

        scatterShrooms(level, random, floor);
        return true;
    }

    // Loose clump of the three carrion shrooms around the fossil — same "air above, sturdy face
    // below" floor check GLOWSHROOM_INNER uses, just done procedurally instead of through a
    // placed-feature predicate chain, since this only ever runs right after a fossil placement.
    private static void scatterShrooms(WorldGenLevel level, RandomSource random, BlockPos fossilFloor) {
        for (int i = 0; i < SHROOM_TRIES; i++) {
            int dx = random.nextInt(2 * SHROOM_RADIUS + 1) - SHROOM_RADIUS;
            int dz = random.nextInt(2 * SHROOM_RADIUS + 1) - SHROOM_RADIUS;
            BlockPos base = fossilFloor.offset(dx, 0, dz);

            BlockPos spot = null;
            for (int dy = 2; dy >= -2; dy--) {
                BlockPos candidate = base.offset(0, dy, 0);
                if (level.isEmptyBlock(candidate)
                        && level.getBlockState(candidate.below())
                                .isFaceSturdy(level, candidate.below(), Direction.UP)) {
                    spot = candidate;
                    break;
                }
            }
            if (spot == null) continue;

            Block shroom = SHROOMS[random.nextInt(SHROOMS.length)];
            level.setBlock(spot, shroom.defaultBlockState().setValue(FURShroomBlock.AGE, random.nextInt(3)), 3);
        }
    }
}
