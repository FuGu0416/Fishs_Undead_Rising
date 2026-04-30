package com.Fishmod.fur.worldgen;

import com.Fishmod.fur.worldgen.feature.FURPlacedFeatures;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FURLuminousUndergroveBiome {

    // Lush Caves sky colour — calculated from temperature 0.5.
    // Cave biomes never show sky, so this only affects the horizon colour in
    // edge cases (e.g. spectator mode, debug views).
    private static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    public static Biome luminousUndergrove(
            HolderGetter<PlacedFeature> placedFeatures,
            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

        // ── Mob spawns ────────────────────────────────────────────────────────
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        // Mob spawning populated later.

        // ── Generation ────────────────────────────────────────────────────────
        BiomeGenerationSettings.Builder genBuilder =
                new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);

        // Carvers — same cave + canyon carvers as Lush Caves
        BiomeDefaultFeatures.addDefaultCarversAndLakes(genBuilder);

        // Ores, crystal formations, monster rooms, etc.
        BiomeDefaultFeatures.addDefaultCrystalFormations(genBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(genBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(genBuilder);
        BiomeDefaultFeatures.addDefaultOres(genBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(genBuilder);

        // ── Luminous Undergrove exclusive features ────────────────────────────

        // Floor: mycelial mat patches (replace cave stone/deepslate with mat)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_PATCH));
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_PATCH_BONEMEAL));

        // Ceiling: mycelial mat on cave ceilings (must run before luminous filament)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_CEILING_PATCH));

        // Ceiling: luminous filament — only grows from mycelial_mat ceiling blocks
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.LUMINOUS_FILAMENT));

        // Floor: mixed vegetation patch (mycelial_veil / tendrils / glowshroom / glimmercap)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MIXED_FLOOR));

        // Landmark: large glow shroom tree (rare, ~1 per 6 chunks)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.LARGE_GLOW_SHROOM));

        // ── Visual effects ────────────────────────────────────────────────────
        BiomeSpecialEffects.Builder effectsBuilder = new BiomeSpecialEffects.Builder()
                .fogColor(0x1A2B2B)
                .waterColor(0x3BA7A0)
                .waterFogColor(0x1F5F5A)
                .skyColor(calculateSkyColor(0.5F))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(net.minecraft.sounds.Musics.createGameMusic(
                        SoundEvents.MUSIC_BIOME_LUSH_CAVES));

        // ── Assemble ──────────────────────────────────────────────────────────
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.9F)
                .specialEffects(effectsBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(genBuilder.build())
                .build();
    }
}
