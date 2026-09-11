package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class FURBiomesRegistry {

    public static final ResourceKey<Biome> LUMINOUS_UNDERGROVE = register("luminous_undergrove");
    public static final ResourceKey<Biome> CARRION_HOLLOW = register("carrion_hollow");

    // ── Helper ────────────────────────────────────────────────────────────────

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, name));
    }
}
