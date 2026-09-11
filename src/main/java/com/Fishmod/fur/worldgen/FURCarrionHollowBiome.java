package com.Fishmod.fur.worldgen;

import com.Fishmod.fur.init.FURCarvers;
import com.Fishmod.fur.worldgen.feature.FURPlacedFeatures;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Carrion Hollow (腐屍窪谷) — a deep cave biome placed the same way as
 * {@link FURLuminousUndergroveBiome} (own climate-parameter patch zone injected by
 * {@link com.Fishmod.fur.mixin.MultiNoiseBiomeSourceMixin}, carved with the exact same
 * configured carver), but with the "Luminous" flora swapped out: the floor is blanketed in
 * mud instead of mycelial mat, no ceiling growth/giant mushrooms, and nether-fossil bone
 * piles scatter through the carved chambers (see {@link com.Fishmod.fur.worldgen.feature.CarrionFossilFeature}
 * for why those are a custom feature rather than the real vanilla structure).
 */
public class FURCarrionHollowBiome {

    // Same formula as FURLuminousUndergroveBiome; cave biomes never show sky so this only
    // matters in edge cases (spectator mode, debug views).
    private static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    public static Biome carrionHollow(
            HolderGetter<PlacedFeature> placedFeatures,
            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

        // ── Mob spawns ────────────────────────────────────────────────────────
        // Intentionally empty, same convention as Luminous Undergrove: spawn data belongs
        // in FURBiomeModifier, not the biome definition.
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        // ── Generation ────────────────────────────────────────────────────────
        BiomeGenerationSettings.Builder genBuilder =
                new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);

        // Carvers — vanilla tunnels/lakes + the SAME configured grotto carver Luminous
        // Undergrove uses, so the cave shape reads as a sibling space.
        BiomeDefaultFeatures.addDefaultCarversAndLakes(genBuilder);
        genBuilder.addCarver(GenerationStep.Carving.AIR,
                worldCarvers.getOrThrow(FURCarvers.LUMINOUS_GROTTO));

        // Ores, crystal formations, monster rooms, etc. — generic cave dressing, unchanged.
        BiomeDefaultFeatures.addDefaultCrystalFormations(genBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(genBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(genBuilder);
        BiomeDefaultFeatures.addDefaultOres(genBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(genBuilder);

        // ── Carrion Hollow exclusive features ─────────────────────────────────

        // Terrain smoothing — same carver-boundary fix as Luminous Undergrove. Runs before
        // LAKES/VEGETAL_DECORATION so the mud floor pass covers the corrected surface.
        genBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                placedFeatures.getOrThrow(FURPlacedFeatures.CAVE_FLOOR_SMOOTHER));

        // Water lakes, small pools, and springs — same generic grotto water dressing.
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.LAKE_WATER));
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.SMALL_POOL));
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.GROTTO_STREAM));
        genBuilder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS,
                placedFeatures.getOrThrow(FURPlacedFeatures.SPRING_WATER));

        // Floor: mud blanket (PLACEHOLDER block — will be replaced with a dedicated
        // decayed-ground block later). Reuses vanilla VEGETATION_PATCH the same way
        // MYCELIAL_MAT_PATCH_BONEMEAL does, just with vegetationChance 0 (no companion plant).
        // Runs before the fossils (below) so their shroom clumps land on the mud, not bare stone —
        // same ordering convention as Luminous Undergrove's LARGE_GLOW_SHROOM/GIANT_GLIMMERCAP
        // running after MYCELIAL_MAT_PATCH within the same VEGETAL_DECORATION step.
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                placedFeatures.getOrThrow(FURPlacedFeatures.CARRION_MUD_FLOOR));

        // Nether-fossil bone piles scattered through the carved chambers, each with its own
        // clump of Bloodtooth/Cordy/Veil Shroom (see CarrionFossilFeature).
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                placedFeatures.getOrThrow(FURPlacedFeatures.CARRION_FOSSIL));

        // ── Visual effects ────────────────────────────────────────────────────
        // PLACEHOLDER palette — sickly, dim, decayed tones; revisit once the real floor
        // block/flora are in.
        BiomeSpecialEffects.Builder effectsBuilder = new BiomeSpecialEffects.Builder()
                .fogColor(0x1F1B14)
                .waterColor(0x4B4229)
                .waterFogColor(0x241F14)
                .skyColor(calculateSkyColor(0.4F))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);

        // ── Assemble ──────────────────────────────────────────────────────────
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.2F)
                .specialEffects(effectsBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(genBuilder.build())
                .build();
    }
}
