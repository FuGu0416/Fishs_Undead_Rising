package com.Fishmod.fur.data.providers;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class FURTags {
    public static final TagKey<Biome> HAS_FOGLET = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_foglet"));
    public static final TagKey<Biome> HAS_ISNACHI = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_isnachi"));
    public static final TagKey<Biome> HAS_PIRANHA = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_piranha"));
    public static final TagKey<Biome> HAS_SWARMER = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_swarmer"));
    
    public static final TagKey<Structure> HAS_SEAHAG = TagKey.create(Registries.STRUCTURE, new ResourceLocation(mod_LavaCow.MODID, "has_seahag"));    
}
