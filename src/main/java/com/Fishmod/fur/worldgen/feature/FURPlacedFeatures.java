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
import net.minecraft.tags.BlockTags;
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
    public static final ResourceKey<PlacedFeature> EMBERWICK_INNER =
            key("emberwick_inner");
    public static final ResourceKey<PlacedFeature> GLIMMERCAP_SHORT_INNER =
            key("glimmercap_short_inner");
    public static final ResourceKey<PlacedFeature> GLIMMERCAP_TALL_INNER =
            key("glimmercap_tall_inner");
    public static final ResourceKey<PlacedFeature> GLOWSHROOM_INNER =
            key("glowshroom_inner");
    public static final ResourceKey<PlacedFeature> MIXED_FLOOR_INNER =
            key("mixed_floor_inner");

    // World-level placements (added to biome generation)
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_PATCH =
            key("mycelial_mat_patch");
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_PATCH_BONEMEAL =
            key("mycelial_mat_patch_bonemeal");
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAT_CEILING_PATCH =
            key("mycelial_mat_ceiling_patch");
    public static final ResourceKey<PlacedFeature> LUMINOUS_FILAMENT =
            key("luminous_filament");
    /** +50% filament top-up pass (CountPlacement caps at 256, so the extra count lives here) */
    public static final ResourceKey<PlacedFeature> LUMINOUS_FILAMENT_EXTRA =
            key("luminous_filament_extra");
    public static final ResourceKey<PlacedFeature> LARGE_GLOW_SHROOM =
            key("large_glow_shroom");
    public static final ResourceKey<PlacedFeature> GIANT_GLIMMERCAP =
            key("giant_glimmercap");
    public static final ResourceKey<PlacedFeature> LAKE_WATER =
            key("lake_water");
    public static final ResourceKey<PlacedFeature> SMALL_POOL =
            key("small_pool");
    public static final ResourceKey<PlacedFeature> GROTTO_STREAM =
            key("grotto_stream");
    public static final ResourceKey<PlacedFeature> SPRING_WATER =
            key("spring_water");
    public static final ResourceKey<PlacedFeature> CAVE_FLOOR_SMOOTHER =
            key("cave_floor_smoother");
    /** Internal: Bone Pile single-block (inner target of BONE_PILE_PATCH) */
    public static final ResourceKey<PlacedFeature> BONE_PILE_INNER =
            key("bone_pile_inner");
    /** World: Bone Pile scattered patch (added to desert biomes) */
    public static final ResourceKey<PlacedFeature> BONE_PILE_PATCH =
            key("bone_pile_patch");

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

        // ── Internal: Emberwick Fungus simple-block (used by the cluster selector) ──
        context.register(EMBERWICK_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.EMBERWICK_SIMPLE),
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

        // ── World: Luminous Filament — +50% top-up pass ───────────────────────
        // Same placement as above with 128 extra attempts (255 + 128 = 383 ≈ +50%),
        // because a single CountPlacement is capped at 256.
        context.register(LUMINOUS_FILAMENT_EXTRA,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LUMINOUS_FILAMENT),
                        List.of(
                                CountPlacement.of(128),
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

        // ── World: Large Glow Shroom cluster ──────────────────────────────────
        // Places the glow-shroom CLUSTER (mushroom + a vegetation clump around it), not a
        // bare mushroom, so every giant glow shroom comes with its own patch of plants.
        context.register(LARGE_GLOW_SHROOM,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LUMINOUS_CLUSTER_GLOWSHROOM),
                        List.of(
                                CountPlacement.of(144),
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

        // ── World: Giant Glimmercap cluster ───────────────────────────────────
        // Same placement strategy as the glow shroom, and likewise places the glimmercap
        // CLUSTER (mushroom + vegetation clump) so every giant glimmercap has its patch.
        context.register(GIANT_GLIMMERCAP,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LUMINOUS_CLUSTER_GLIMMERCAP),
                        List.of(
                                CountPlacement.of(144),
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

        // ── World: Underground water lake ─────────────────────────────────────
        // Once every ~9 chunks on average, anywhere in the cave height band.
        context.register(LAKE_WATER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.LAKE_WATER),
                        List.of(
                                RarityFilter.onAverageOnceEvery(9),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(56)),
                                BiomeFilter.biome()
                        )));

        // ── World: Small shallow pool ─────────────────────────────────────────
        // 3 attempts per chunk, each kept once every 2 chunks on average → ~1.5/chunk
        // (3× the previous 0.5/chunk, i.e. +200%); floor-scanning ensures placement on
        // actual cave floor surfaces.
        context.register(SMALL_POOL,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.SMALL_POOL),
                        List.of(
                                CountPlacement.of(3),
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

        // ── World: Grotto stream (meandering still-water river) ───────────────
        // Once every ~8 chunks on average; floor-scanning lands it on the cave floor, then the
        // feature traces a winding water channel from there (kept local to avoid far-chunk writes).
        context.register(GROTTO_STREAM,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.GROTTO_STREAM),
                        List.of(
                                RarityFilter.onAverageOnceEvery(8),
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

        // ── World: Cave floor smoother ────────────────────────────────────────
        // Runs in LOCAL_MODIFICATIONS (before LAKES and VEGETAL_DECORATION) so
        // the ramp blocks are present when mat patches and small pools are placed.
        // 3 attempts per chunk, each covering a 17×17 area; the high overlap
        // ensures all carver-boundary cliffs within the biome get smoothed.
        context.register(CAVE_FLOOR_SMOOTHER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.CAVE_FLOOR_SMOOTHER),
                        List.of(
                                CountPlacement.of(3),
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

        // ── World: Underground water spring ───────────────────────────────────
        // ~25 spring attempts per chunk, spread across the full cave height band.
        context.register(SPRING_WATER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.SPRING_WATER),
                        List.of(
                                CountPlacement.of(25),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(128)),
                                BiomeFilter.biome()
                        )));

        // ── Internal: Bone Pile single-block (air at pos, sand below) ─────────
        context.register(BONE_PILE_INNER,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.BONE_PILE_SIMPLE),
                        List.of(
                                BlockPredicateFilter.forPredicate(
                                        BlockPredicate.allOf(
                                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                                BlockPredicate.matchesTag(
                                                        new BlockPos(0, -1, 0), BlockTags.SAND))))));

        // ── World: Bone Pile scattered patch (desert surface) ─────────────────
        // Once every ~6 chunks on average; surface heightmap places it on top of the sand.
        context.register(BONE_PILE_PATCH,
                new PlacedFeature(
                        features.getOrThrow(FURConfiguredFeatures.BONE_PILE_PATCH),
                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )));

        // ── World: Luminous floor clusters ────────────────────────────────────
        // Rare, dense hero clusters instead of a uniform per-chunk scatter. The dark
        // space between clusters is intentional. Floor vegetation is no longer a
        // standalone placed feature — it now grows as a clump around each giant mushroom
        // (see LARGE_GLOW_SHROOM / GIANT_GLIMMERCAP above, which place the cluster features).
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                new ResourceLocation(mod_LavaCow.MODID, name));
    }
}
