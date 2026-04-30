package com.Fishmod.fur.worldgen.feature;

import java.util.List;

import com.Fishmod.fur.block.GlimmercapBlock;
import com.Fishmod.fur.block.LuminousFilamentBlock;
import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURFeatureRegistry;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
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

    /** Mycelial Tendrils — single-block placement used inside the RANDOM_PATCH */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_TENDRILS_SIMPLE =
            key("mycelial_tendrils_simple");

    /** Mycelial Tendrils — vegetation scattered on the floor */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_TENDRILS =
            key("mycelial_tendrils");

    /** Luminous Filament — ceiling-hanging vines, similar to Cave Vines */
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINOUS_FILAMENT =
            key("luminous_filament");

    /** Glowshroom — single-block placement used inside the RANDOM_PATCH */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM_SIMPLE =
            key("glowshroom_simple");

    /** Glowshroom — single-block mushroom scattered on floor/mycelial mat */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM =
            key("glowshroom");

    /** Glimmercap — single-block (short) placement used by GLIMMERCAP_RANDOM */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP_SIMPLE =
            key("glimmercap_simple");

    /** Glimmercap — 2-block tall placement (BLOCK_COLUMN upward) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP_TALL =
            key("glimmercap_tall");

    /** Glimmercap — randomly picks tall (40%) or short (60%) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP_RANDOM =
            key("glimmercap_random");

    /** Glimmercap — tall grass-like fungus scattered on the floor */
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERCAP =
            key("glimmercap");

    /** Large Glow Shroom — tree-like structure using LargeGlowShroomFeature */
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GLOW_SHROOM =
            key("large_glow_shroom");

    /** Mixed floor vegetation — RANDOM_SELECTOR picking between all floor plants */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_FLOOR_RANDOM =
            key("mixed_floor_random");

    /** Mixed floor patch — RANDOM_PATCH using MIXED_FLOOR_RANDOM as inner feature */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_FLOOR_PATCH =
            key("mixed_floor_patch");

    /** Mycelial Mat ceiling patch — places mat on cave ceilings (no inner vegetation) */
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYCELIAL_MAT_CEILING_PATCH =
            key("mycelial_mat_ceiling_patch");

    // ── Bootstrap ─────────────────────────────────────────────────────────────

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        // ── Mycelial Mat patch (floor, similar to MOSS_PATCH) ─────────────────
        // Uses VegetationPatch: replaces surface blocks with mycelial_mat in a radius,
        // then seeds the patch with mycelial_veil vegetation on top.
        context.register(MYCELIAL_MAT_PATCH, new ConfiguredFeature<>(
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        // Surface block tag that can be replaced (stone, deepslate, etc.)
                        net.minecraft.tags.BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_MAT.get()),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MYCELIAL_VEIL_BONEMEAL),
                        CaveSurface.FLOOR,
                        UniformInt.of(1, 2),   // vertical depth
                        0.8F,                  // moss chance
                        5,                     // iterations
                        0.1F,                  // extra edge column chance
                        UniformInt.of(4, 7),   // horizontal radius
                        0.7F                   // vegetation chance per block
                )));

        // ── Mycelial Mat patch (bonemeal version — tighter spread) ────────────
        context.register(MYCELIAL_MAT_PATCH_BONEMEAL, new ConfiguredFeature<>(
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        net.minecraft.tags.BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_MAT.get()),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MYCELIAL_VEIL_BONEMEAL),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.6F,
                        5,
                        0.1F,
                        UniformInt.of(2, 4),
                        0.5F
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
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_TENDRILS.get()))));

        context.register(GLOWSHROOM_SIMPLE, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        BlockStateProvider.simple(FURBlockRegistry.GLOWSHROOM.get()))));

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

        context.register(GLIMMERCAP_RANDOM, new ConfiguredFeature<>(
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(new WeightedPlacedFeature(
                                context.lookup(Registries.PLACED_FEATURE)
                                        .getOrThrow(FURPlacedFeatures.GLIMMERCAP_TALL_INNER),
                                0.4F   // 40% 2-tall, 60% 1-tall
                        )),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.GLIMMERCAP_SHORT_INNER)
                )));

        // ── Mycelial Tendrils (vegetation, similar to Nether Sprouts patch) ───
        context.register(MYCELIAL_TENDRILS, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        64,   // tries
                        6,    // xz spread
                        2,    // y spread
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MYCELIAL_TENDRILS_PLACED))));

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

        // ── Glowshroom (single mushroom, scattered on floor) ──────────────────
        context.register(GLOWSHROOM, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        32,  // tries
                        4,   // xz spread
                        1,   // y spread
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.GLOWSHROOM_PLACED))));

        // ── Glimmercap (1–2 tall fungus, like GRASS patch) ────────────────────
        context.register(GLIMMERCAP, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        16,  // tries (reduced from 48)
                        5,   // xz spread
                        2,   // y spread
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.GLIMMERCAP_PLACED))));

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

        // ── Mixed floor vegetation ────────────────────────────────────────────
        // RANDOM_SELECTOR: each patch attempt randomly picks one floor plant.
        // Weights are sequential independent chances:
        //   25% → mycelial_veil  |  25% → mycelial_tendrils
        //   33% → glowshroom     |  default → glimmercap (tall/short mix)
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
                                        0.33F)
                        ),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.GLIMMERCAP_PLACED)
                )));

        // ── Mycelial Mat ceiling patch ────────────────────────────────────────
        // VegetationPatch(CEILING): covers cave ceilings with mycelial_mat.
        // vegetationChance=0 — no inner plants; luminous_filament uses a separate
        // placed feature that checks for mat overhead (like cave vines from moss).
        context.register(MYCELIAL_MAT_CEILING_PATCH, new ConfiguredFeature<>(
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        net.minecraft.tags.BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(FURBlockRegistry.MYCELIAL_MAT.get()),
                        context.lookup(Registries.PLACED_FEATURE)
                                .getOrThrow(FURPlacedFeatures.MYCELIAL_VEIL_BONEMEAL),
                        CaveSurface.CEILING,
                        ConstantInt.of(1),
                        0.4F,
                        3,
                        0.1F,
                        UniformInt.of(2, 5),
                        0.0F  // no inner vegetation; filament handled separately
                )));

        context.register(MIXED_FLOOR_PATCH, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        48,  // tries per patch
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
