package com.Fishmod.fur.worldgen.feature;

import java.util.List;

import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.mod_LavaCow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class FURPlacedFeatures {

    // ── Resource Keys ─────────────────────────────────────────────────────────

    // Internal simple-block placements (used by RandomPatch / VegetationPatch)
    public static final ResourceKey<PlacedFeature> MYCELIAL_VEIL_BONEMEAL =
            key("mycelial_veil_bonemeal");
    public static final ResourceKey<PlacedFeature> MYCELIAL_TENDRILS_PLACED =
            key("mycelial_tendrils_placed");
    public static final ResourceKey<PlacedFeature> GLIMMERCAP_SHORT_INNER =
            key("glimmercap_short_inner");
    public static final ResourceKey<PlacedFeature> GLIMMERCAP_TALL_INNER =
            key("glimmercap_tall_inner");
    public static final ResourceKey<PlacedFeature> GLOWSHROOM_INNER =
            key("glowshroom_inner");
    public static final ResourceKey<PlacedFeature> MIXED_FLOOR_INNER =
            key("mixed_floor_inner");
    public static final ResourceKey<PlacedFeature> MIXED_FLOOR =
            key("mixed_floor");

    // World-level placements (added to biome generation)
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_PATCH =
            key("mycelial_mat_patch");
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_PATCH_BONEMEAL =
            key("mycelial_mat_patch_bonemeal");
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_CEILING_PATCH =
            key("mycelial_mat_ceiling_patch");
    public static final ResourceKey<PlacedFeature> LUMINOUS_FILAMENT =
            key("luminous_filament");
    public static final ResourceKey<PlacedFeature> LARGE_GLOW_SHROOM =
            key("large_glow_shroom");

    // ── Bootstrap ─────────────────────────────────────────────────────────────

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var features = context.lookup(Registries.CONFIGURED_FEATURE);

        // ── Internal: Mycelial Veil ───────────────────────────────────────────
        // Only places on mycelial_mat (flat carpet covering the mat surface).
        context.register(MYCELIAL_VEIL_BONEMEAL,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MYCELIAL_VEIL),
                        List.of(
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.allOf(
                                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                                BlockPredicate.matchesBlocks(
                                                        new BlockPos(0, -1, 0),
                                                        FURBlockRegistry.MYCELIAL_MAT.get()))))));

        // ── Internal: Mycelial Tendrils simple-block ──────────────────────────
        context.register(MYCELIAL_TENDRILS_PLACED,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MYCELIAL_TENDRILS_SIMPLE),
                        List.of(PlacementUtils.isEmpty())));

        // ── Internal: Glimmercap short (1-tall) ───────────────────────────────
        // Air (regular or cave) at pos, sturdy face below.
        context.register(GLIMMERCAP_SHORT_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.GLIMMERCAP_SIMPLE),
                        List.of(
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.allOf(
                                                BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.CAVE_AIR),
                                                BlockPredicate.hasSturdyFace(
                                                        new BlockPos(0, -1, 0), Direction.UP))))));

        // ── Internal: Glimmercap tall (2-tall) ────────────────────────────────
        // Air (regular or cave) at pos AND pos+1, sturdy face below.
        context.register(GLIMMERCAP_TALL_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.GLIMMERCAP_TALL),
                        List.of(
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.allOf(
                                                BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.CAVE_AIR),
                                                BlockPredicate.matchesBlocks(
                                                        new BlockPos(0, 1, 0), Blocks.AIR, Blocks.CAVE_AIR),
                                                BlockPredicate.hasSturdyFace(
                                                        new BlockPos(0, -1, 0), Direction.UP))))));

        // ── Internal: Glowshroom (used by MIXED_FLOOR_RANDOM) ─────────────────
        context.register(GLOWSHROOM_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.GLOWSHROOM_SIMPLE),
                        List.of(
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.allOf(
                                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                                BlockPredicate.hasSturdyFace(
                                                        new BlockPos(0, -1, 0), Direction.UP))))));

        // ── Internal: Mixed floor inner ───────────────────────────────────────
        context.register(MIXED_FLOOR_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MIXED_FLOOR_RANDOM),
                        List.of()));

        // ── World: Mycelial Mat patch (floor) ─────────────────────────────────
        // Increased count for denser mat coverage. Double-scan ensures underground.
        context.register(MYCELIAL_MAT_PATCH,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MYCELIAL_MAT_PATCH),
                        List.of(
                                CountPlacement.of(255),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                BiomeFilter.biome()
                        )));

        // ── World: Mycelial Mat patch (bonemeal) ──────────────────────────────
        context.register(MYCELIAL_MAT_PATCH_BONEMEAL,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MYCELIAL_MAT_PATCH_BONEMEAL),
                        List.of(
                                RarityFilter.onAverageOnceEvery(2),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                BiomeFilter.biome()
                        )));

        // ── World: Mycelial Mat ceiling patch ─────────────────────────────────
        // Mirrors MYCELIAL_MAT_PATCH but anchors to the cave ceiling. Runs before
        // LUMINOUS_FILAMENT so the mat is present when filament checks for it.
        context.register(MYCELIAL_MAT_CEILING_PATCH,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MYCELIAL_MAT_CEILING_PATCH),
                        List.of(
                                CountPlacement.of(255),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                BiomeFilter.biome()
                        )));

        // ── World: Luminous Filament (ceiling vines) ──────────────────────────
        // Requires the ceiling block to be mycelial_mat — like cave vines growing
        // only from moss blocks. MYCELIAL_MAT_CEILING_PATCH must run first.
        context.register(LUMINOUS_FILAMENT,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LUMINOUS_FILAMENT),
                        List.of(
                                CountPlacement.of(255),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        12),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.matchesBlocks(
                                                new BlockPos(0, 1, 0),
                                                FURBlockRegistry.MYCELIAL_MAT.get())),
                                BiomeFilter.biome()
                        )));

        // ── World: Large Glow Shroom (tree) ───────────────────────────────────
        context.register(LARGE_GLOW_SHROOM,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LARGE_GLOW_SHROOM),
                        List.of(
                                CountPlacement.of(180),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.matchesBlocks(
                                                new BlockPos(0, -1, 0),
                                                FURBlockRegistry.MYCELIAL_MAT.get())),
                                BiomeFilter.biome()
                        )));

        // ── World: Mixed floor vegetation ─────────────────────────────────────
        // Restricted to mycelial_mat surface only — creating dense plant clusters
        // on mat patches. Count is high since the mat filter discards most attempts.
        context.register(MIXED_FLOOR,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.MIXED_FLOOR_PATCH),
                        List.of(
                                CountPlacement.of(32),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.UP,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                                EnvironmentScanPlacement.scanningFor(
                                        Direction.DOWN,
                                        BlockPredicate.solid(),
                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                        32),
                                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.matchesBlocks(
                                                new BlockPos(0, -1, 0),
                                                FURBlockRegistry.MYCELIAL_MAT.get())),
                                BiomeFilter.biome()
                        )));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                new ResourceLocation(mod_LavaCow.MODID, name));
    }
}
