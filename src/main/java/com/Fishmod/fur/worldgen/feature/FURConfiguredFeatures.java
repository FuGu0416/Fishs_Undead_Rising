package com.Fishmod.fur.worldgen.feature;

import java.util.List;

import com.Fishmod.fur.block.GlimmercapBlock;
import com.Fishmod.fur.block.LuminousFilamentBlock;
import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURFeatureRegistry;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.material.Fluids;
import com.Fishmod.fur.block.FURShroomBlock;
import com.Fishmod.fur.block.MycelialTendrilsBlock;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class FURConfiguredFeatures {

    // ── Resource Keys ─────────────────────────────────────────────────────────

    /** Mycelial Mat patch — floor coverage, similar to Moss Block */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_MAT_PATCH =
            key("mycelial_mat_patch");

    /** Mycelial Mat patch triggered by bonemeal */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_MAT_PATCH_BONEMEAL =
            key("mycelial_mat_patch_bonemeal");

    /** Mycelial Veil — thin carpet layer on top of mycelial mat, similar to Moss Carpet */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_VEIL =
            key("mycelial_veil");

    /** Mycelial Tendrils — single-block placement used by MYCELIAL_TENDRILS_PLACED */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_TENDRILS_SIMPLE =
            key("mycelial_tendrils_simple");

    /** Luminous Filament — ceiling-hanging vines, similar to Cave Vines */
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINOUS_FILAMENT =
            key("luminous_filament");

    /** Glowshroom — single-block placement used by GLOWSHROOM_INNER */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM_SIMPLE =
            key("glowshroom_simple");

    /** Glimmercap — single-block (short) placement used by GLIMMERCAP_SHORT_INNER */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP_SIMPLE =
            key("glimmercap_simple");

    /** Glimmercap — 2-block tall placement (BLOCK_COLUMN upward) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP_TALL =
            key("glimmercap_tall");

    /** Large Glow Shroom — tree-like structure using LargeGlowShroomFeature */
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GLOW_SHROOM =
            key("large_glow_shroom");

    /** Giant Glimmercap — flat brown-mushroom-shaped huge mushroom, reuses the same feature */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_GLIMMERCAP =
            key("giant_glimmercap");

    /** Mixed floor vegetation — RANDOM_SELECTOR picking between all floor plants */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_FLOOR_RANDOM =
            key("mixed_floor_random");

    /** Mixed floor patch — RANDOM_PATCH using MIXED_FLOOR_RANDOM as inner feature */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_FLOOR_PATCH =
            key("mixed_floor_patch");

    /** Mycelial Mat ceiling patch — places mat on cave ceilings (no inner vegetation) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_MAT_CEILING_PATCH =
            key("mycelial_mat_ceiling_patch");

    /** Underground water lake */
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAKE_WATER =
            key("lake_water");

    /** Small shallow pool on cave floor (4×4–6×6, depth 1–2) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_POOL =
            key("small_pool");

    /** Cave floor smoother — fills height transitions between carver sections */
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_FLOOR_SMOOTHER =
            key("cave_floor_smoother");

    /** Underground water spring */
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_WATER =
            key("spring_water");

    // ── Bootstrap ─────────────────────────────────────────────────────────────

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        // ── Mycelial Mat patch (floor) ────────────────────────────────────────
        // Uses a custom feature so the patch boundary is an organic blob rather
        // than the rectangular shape produced by vanilla VegetationPatch.
        // Plant seeding is handled by the separate MIXED_FLOOR placed feature.
        context.register(MYCELIAL_MAT_PATCH, new ConfiguredFeature<>(
                FURFeatureRegistry.MYCELIAL_MAT_PATCH.get(),
                NoneFeatureConfiguration.INSTANCE
        ));

        // ── Mycelial Mat patch (bonemeal version — tighter spread) ────────────
        context.register(MYCELIAL_MAT_PATCH_BONEMEAL, new ConfiguredFeature<>(
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        net.minecraft.tags.BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_MAT.get()),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MIXED_FLOOR_INNER),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.6F,
                        5,
                        0.15F,                 // vegetationChance
                        UniformInt.of(2, 4),
                        0.8F                   // extraEdgeColumnChance
                )));

        // ── Mycelial Veil (carpet layer on top of mycelial mat) ───────────────
        context.register(MYCELIAL_VEIL, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_VEIL.get()))));

        // ── Simple-block primitives (used as the inner target of RANDOM_PATCH) ─
        context.register(MYCELIAL_TENDRILS_SIMPLE, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FURBlockRegistry.MYCELIAL_TENDRILS.get().defaultBlockState().setValue(MycelialTendrilsBlock.VARIANT, 0), 1)
                                        .add(FURBlockRegistry.MYCELIAL_TENDRILS.get().defaultBlockState().setValue(MycelialTendrilsBlock.VARIANT, 1), 1)
                                        .add(FURBlockRegistry.MYCELIAL_TENDRILS.get().defaultBlockState().setValue(MycelialTendrilsBlock.VARIANT, 2), 1)
                                        .build()))));

        context.register(GLOWSHROOM_SIMPLE, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FURBlockRegistry.GLOWSHROOM.get().defaultBlockState().setValue(FURShroomBlock.AGE, 0), 1)
                                        .add(FURBlockRegistry.GLOWSHROOM.get().defaultBlockState().setValue(FURShroomBlock.AGE, 1), 1)
                                        .add(FURBlockRegistry.GLOWSHROOM.get().defaultBlockState().setValue(FURShroomBlock.AGE, 2), 1)
                                        .build()))));

        context.register(GLIMMERCAP_SIMPLE, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        BlockStateProvider.simple(FURBlockRegistry.GLIMMERCAP.get()))));

        context.register(GLIMMERCAP_TALL, new ConfiguredFeature<>(
                Feature.BLOCK_COLUMN,
                new net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration(
                        List.of(
                                new net.minecraft.world.level.levelgen.feature.configurations
                                        .BlockColumnConfiguration.Layer(
                                        ConstantInt.of(1),
                                        BlockStateProvider.simple(FURBlockRegistry.GLIMMERCAP.get()
                                                .defaultBlockState()
                                                .setValue(GlimmercapBlock.TALL, true)
                                                .setValue(GlimmercapBlock.HALF, DoubleBlockHalf.LOWER))),
                                new net.minecraft.world.level.levelgen.feature.configurations
                                        .BlockColumnConfiguration.Layer(
                                        ConstantInt.of(1),
                                        BlockStateProvider.simple(FURBlockRegistry.GLIMMERCAP.get()
                                                .defaultBlockState()
                                                .setValue(GlimmercapBlock.TALL, true)
                                                .setValue(GlimmercapBlock.HALF, DoubleBlockHalf.UPPER)))
                        ),
                        Direction.UP,
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        false
                )));

        // ── Luminous Filament (ceiling vines, similar to Cave Vines) ──────────
        // Three-layer column hanging DOWN from the ceiling:
        //   UPPER (1 block)  — the segment touching the solid ceiling
        //   MIDDLE (0–6 blocks) — the body; total chain length = 2–8 blocks
        //   LOWER (1 block)  — the dangling tip
        context.register(LUMINOUS_FILAMENT, new ConfiguredFeature<>(
                Feature.BLOCK_COLUMN,
                new net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration(
                        List.of(
                                new net.minecraft.world.level.levelgen.feature.configurations
                                        .BlockColumnConfiguration.Layer(
                                        ConstantInt.of(1),
                                        BlockStateProvider.simple(FURBlockRegistry.LUMINOUS_FILAMENT.get()
                                                .defaultBlockState()
                                                .setValue(LuminousFilamentBlock.SEGMENT, LuminousFilamentBlock.Segment.UPPER))),
                                new net.minecraft.world.level.levelgen.feature.configurations
                                        .BlockColumnConfiguration.Layer(
                                        UniformInt.of(0, 6),
                                        BlockStateProvider.simple(FURBlockRegistry.LUMINOUS_FILAMENT.get()
                                                .defaultBlockState()
                                                .setValue(LuminousFilamentBlock.SEGMENT, LuminousFilamentBlock.Segment.MIDDLE))),
                                new net.minecraft.world.level.levelgen.feature.configurations
                                        .BlockColumnConfiguration.Layer(
                                        ConstantInt.of(1),
                                        BlockStateProvider.simple(FURBlockRegistry.LUMINOUS_FILAMENT.get()
                                                .defaultBlockState()
                                                .setValue(LuminousFilamentBlock.SEGMENT, LuminousFilamentBlock.Segment.LOWER)))
                        ),
                        Direction.DOWN,
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        true   // prioritise tip (removes from top when space-limited)
                )));

        // ── Large Glow Shroom (tree-like structure) ───────────────────────────
        context.register(LARGE_GLOW_SHROOM, new ConfiguredFeature<>(
                FURFeatureRegistry.HUGE_GLOWSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        // Cap provider — uses glowshroom_block_cap
                        BlockStateProvider.simple(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP.get()),
                        // Stem provider — uses glowshroom_block_stem
                        BlockStateProvider.simple(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM.get()),
                        3  // foliageRadius — side height of the cap skirt
                )));

        // ── Giant Glimmercap (flat brown-mushroom-shaped huge mushroom) ───────
        // Same custom huge-mushroom feature as the glow shroom (flat cap, brown-
        // mushroom silhouette), but built from the glimmercap cap/stem blocks.
        context.register(GIANT_GLIMMERCAP, new ConfiguredFeature<>(
                FURFeatureRegistry.HUGE_GLOWSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(FURBlockRegistry.GLIMMERCAP_BLOCK_CAP.get()),
                        BlockStateProvider.simple(FURBlockRegistry.GLIMMERCAP_BLOCK_STEM.get()),
                        3  // foliageRadius — side height of the cap skirt
                )));

        // ── Mixed floor vegetation ────────────────────────────────────────────
        // RANDOM_SELECTOR: sequential independent chances (first match wins):
        //   25% → mycelial_veil  |  25% → mycelial_tendrils
        //   33% → glowshroom  |  10% → glimmercap tall  |  8% → glimmercap short
        //   default → mycelial_tendrils (glimmercap is now an explicit low-weight entry)
        context.register(MIXED_FLOOR_RANDOM, new ConfiguredFeature<>(
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        context.lookup(Registries.PLACED_FEATURE)
                                                .getOrThrow(FURPlacedFeatures.MYCELIAL_VEIL_BONEMEAL),
                                        0.25F),
                                new WeightedPlacedFeature(
                                        context.lookup(Registries.PLACED_FEATURE)
                                                .getOrThrow(FURPlacedFeatures.MYCELIAL_TENDRILS_PLACED),
                                        0.25F),
                                new WeightedPlacedFeature(
                                        context.lookup(Registries.PLACED_FEATURE)
                                                .getOrThrow(FURPlacedFeatures.GLOWSHROOM_INNER),
                                        0.33F),
                                new WeightedPlacedFeature(
                                        context.lookup(Registries.PLACED_FEATURE)
                                                .getOrThrow(FURPlacedFeatures.GLIMMERCAP_TALL_INNER),
                                        0.1F),
                                new WeightedPlacedFeature(
                                        context.lookup(Registries.PLACED_FEATURE)
                                                .getOrThrow(FURPlacedFeatures.GLIMMERCAP_SHORT_INNER),
                                        0.08F)
                        ),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MYCELIAL_VEIL_BONEMEAL)
                )));

        // ── Mycelial Mat ceiling patch ────────────────────────────────────────
        // VegetationPatch(CEILING): covers cave ceilings with mycelial_mat.
        // Inner feature is MIXED_FLOOR_INNER but all sub-PF predicates require mat below,
        // so no vegetation is ever placed from the ceiling surface.
        // Luminous filament uses a separate placed feature that checks for mat overhead.
        context.register(MYCELIAL_MAT_CEILING_PATCH, new ConfiguredFeature<>(
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        net.minecraft.tags.BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_MAT.get()),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MIXED_FLOOR_INNER),
                        CaveSurface.CEILING,
                        ConstantInt.of(1),
                        0.4F,
                        3,
                        0.0F,
                        UniformInt.of(2, 5),
                        0.0F
                )));

        // ── Underground water lake ────────────────────────────────────────────
        context.register(LAKE_WATER, new ConfiguredFeature<>(
                Feature.LAKE,
                new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                        BlockStateProvider.simple(Blocks.STONE.defaultBlockState())
                )));

        // ── Small shallow pool ────────────────────────────────────────────────
        context.register(SMALL_POOL, new ConfiguredFeature<>(
                FURFeatureRegistry.SMALL_POOL.get(),
                NoneFeatureConfiguration.INSTANCE
        ));

        // ── Cave floor smoother ───────────────────────────────────────────────
        context.register(CAVE_FLOOR_SMOOTHER, new ConfiguredFeature<>(
                FURFeatureRegistry.CAVE_FLOOR_SMOOTHER.get(),
                NoneFeatureConfiguration.INSTANCE
        ));

        // ── Underground water spring ──────────────────────────────────────────
        context.register(SPRING_WATER, new ConfiguredFeature<>(
                Feature.SPRING,
                new SpringConfiguration(
                        Fluids.WATER.defaultFluidState(),
                        true, 4, 1,
                        HolderSet.direct(
                                Block::builtInRegistryHolder,
                                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE,
                                Blocks.DEEPSLATE, Blocks.CALCITE, Blocks.TUFF, Blocks.GRAVEL
                        )
                )));

        context.register(MIXED_FLOOR_PATCH, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        48,   // tries per patch
                        6,   // xz spread
                        2,   // y spread
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MIXED_FLOOR_INNER))));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                new ResourceLocation(mod_LavaCow.MODID, name));
    }
}
