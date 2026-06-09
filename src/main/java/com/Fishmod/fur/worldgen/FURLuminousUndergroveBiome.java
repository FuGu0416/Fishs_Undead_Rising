package com.Fishmod.fur.worldgen;

import com.Fishmod.fur.init.FURCarvers;
import com.Fishmod.fur.worldgen.feature.FURPlacedFeatures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
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
        // Intentionally empty: all entity spawns are handled by FURBiomeModifier
        // via the biome-modifier system, keeping spawn data out of the biome definition.
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        // ── Generation ────────────────────────────────────────────────────────
        BiomeGenerationSettings.Builder genBuilder =
                new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);

        // Carvers — vanilla tunnels + our large grotto carver for open chambers
        BiomeDefaultFeatures.addDefaultCarversAndLakes(genBuilder);
        genBuilder.addCarver(GenerationStep.Carving.AIR,
                worldCarvers.getOrThrow(FURCarvers.LUMINOUS_GROTTO));

        // Ores, crystal formations, monster rooms, etc.
        BiomeDefaultFeatures.addDefaultCrystalFormations(genBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(genBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(genBuilder);
        BiomeDefaultFeatures.addDefaultOres(genBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(genBuilder);

        // ── Luminous Undergrove exclusive features ────────────────────────────

        // Terrain smoothing — fills stone ramps at height transitions between
        // carver sections, eliminating hanging walls. Must run before LAKES so
        // small pools assess the corrected floor, and before VEGETAL_DECORATION
        // so mycelial mat naturally covers the ramp surface.
        genBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                placedFeatures.getOrThrow(FURPlacedFeatures.CAVE_FLOOR_SMOOTHER));

        // Water lakes, small pools, and springs
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.LAKE_WATER));
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.SMALL_POOL));
        genBuilder.addFeature(GenerationStep.Decoration.LAKES,
                placedFeatures.getOrThrow(FURPlacedFeatures.GROTTO_STREAM));
        genBuilder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS,
                placedFeatures.getOrThrow(FURPlacedFeatures.SPRING_WATER));

        // Floor: mycelial mat patches (replace cave stone/deepslate with mat)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_PATCH));
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_PATCH_BONEMEAL));

        // Ceiling: mycelial mat on cave ceilings (must run before luminous filament)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.MYCELIAL_MAT_CEILING_PATCH));

        // Ceiling: luminous filament — only grows from mycelial_mat ceiling blocks
        // (two passes: base + a +50% top-up, since one CountPlacement caps at 256)
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.LUMINOUS_FILAMENT));
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.LUMINOUS_FILAMENT_EXTRA));

        // Floor: each giant mushroom is a cluster — the huge mushroom plus a dense clump
        // of mixed plants around it. Vegetation only grows where a mushroom grows, so the
        // plants read as clusters rather than a uniform scatter.
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.LARGE_GLOW_SHROOM));
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedFeatures.getOrThrow(FURPlacedFeatures.GIANT_GLIMMERCAP));

        // ── Visual effects ────────────────────────────────────────────────────
        BiomeSpecialEffects.Builder effectsBuilder = new BiomeSpecialEffects.Builder()
                .fogColor(0x1A2B2B)
                .waterColor(0x3BA7A0)
                .waterFogColor(0x1F5F5A)
                .skyColor(calculateSkyColor(0.5F))
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F))
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
