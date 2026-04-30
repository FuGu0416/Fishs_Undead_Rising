package com.Fishmod.fur.worldgen;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBiomesRegistry;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

/**
 * TerraBlender Region for the Luminous Undergrove cave biome.
 *
 * Climate parameters are tuned to match Lush Caves-adjacent conditions
 * (warm, humid) but restricted to the underground cave layer only,
 * at a depth range corresponding to Y -20 to Y -55.
 *
 * Weight controls how often this region is chosen vs. vanilla regions.
 * A weight of 2 (out of a default vanilla total of 10) gives ~17% coverage
 * in qualifying climate zones — adjust to taste.
 */
public class FURBiomeProvider extends Region {

    public FURBiomeProvider() {
        super(new ResourceLocation(mod_LavaCow.MODID, "overworld"), RegionType.OVERWORLD, 2); // weight
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        // ── Climate parameters ────────────────────────────────────────────────
        // These mirror the underground cave placement used by Lush Caves,
        // shifted slightly warmer and wetter to give Luminous Undergrove its
        // own identity while still generating in similar zones.

        // Temperature: warm  (0.2 – 0.9)
        Climate.Parameter temperature = Climate.Parameter.span(0.2F, 0.9F);
        // Humidity: wet (0.1 – 1.0)
        Climate.Parameter humidity    = Climate.Parameter.span(0.1F, 1.0F);
        // Continentalness: underground (coast to far-inland — we don't restrict)
        Climate.Parameter continentalness = Climate.Parameter.span(-0.19F, 1.0F);
        // Erosion: any
        Climate.Parameter erosion     = Climate.Parameter.span(-1.0F, 1.0F);
        // Weirdness: any (we let depth do the restricting)
        Climate.Parameter weirdness   = Climate.Parameter.span(-1.0F, 1.0F);
        // Depth: restrict to mid-to-deep cave layer where large chambers form.
        // 0.0 = surface, 1.0 = deep underground; 0.4–0.9 avoids shallow tunnels.
        Climate.Parameter depth       = Climate.Parameter.span(0.4F, 0.9F);
        // Offset: 0 — no bias
        float offset = 0.0F;

        mapper.accept(Pair.of( Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset), FURBiomesRegistry.LUMINOUS_UNDERGROVE));
    }
}
