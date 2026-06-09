package com.Fishmod.fur.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * Configuration for {@link LuminousClusterFeature}.
 *
 * <p>A hero cluster is a single {@code centerpiece} feature stamped at the exact
 * placement origin, surrounded by a dense {@code patch} of mixed floor plants. This
 * lets the Luminous Undergrove floor flora read as rare, dense focal points rather
 * than a uniform per-chunk scatter.
 *
 * @param centerpiece feature placed once at the origin (e.g. a HUGE_GLOWSHROOM)
 * @param patch       dense vegetation patch radiating around the origin (a RandomPatch)
 */
public record LuminousClusterConfiguration(
        Holder<ConfiguredFeature<?, ?>> centerpiece,
        Holder<ConfiguredFeature<?, ?>> patch) implements FeatureConfiguration {

    public static final Codec<LuminousClusterConfiguration> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                    ConfiguredFeature.CODEC.fieldOf("centerpiece")
                            .forGetter(LuminousClusterConfiguration::centerpiece),
                    ConfiguredFeature.CODEC.fieldOf("patch")
                            .forGetter(LuminousClusterConfiguration::patch)
            ).apply(inst, LuminousClusterConfiguration::new));
}
