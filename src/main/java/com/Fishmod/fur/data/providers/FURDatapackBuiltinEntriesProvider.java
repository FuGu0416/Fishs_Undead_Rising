package com.Fishmod.fur.data.providers;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBiomesRegistry;
import com.Fishmod.fur.worldgen.FURBiomeModifier;
import com.Fishmod.fur.worldgen.FURLuminousUndergroveBiome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class FURDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, FURBiomeModifier::bootstrap)
            .add(Registries.BIOME, context -> {
                HolderGetter<PlacedFeature> placedFeatures =
                        context.lookup(Registries.PLACED_FEATURE);
                HolderGetter<ConfiguredWorldCarver<?>> worldCarvers =
                        context.lookup(Registries.CONFIGURED_CARVER);

                context.register(FURBiomesRegistry.LUMINOUS_UNDERGROVE, FURLuminousUndergroveBiome.luminousUndergrove(placedFeatures, worldCarvers));
            });

    public FURDatapackBuiltinEntriesProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(mod_LavaCow.MODID));
    }
}
