package com.Fishmod.fur.data.providers;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_FOGLET = registerKey("has_foglet");
    public static final TagKey<Biome> HAS_ISNACHI = registerKey("has_isnachi");
    
    public static TagKey<Biome> registerKey(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, name));
    }
}
