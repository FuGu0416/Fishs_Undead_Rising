package com.Fishmod.fur.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.Set;

public interface FURBiomeSourceAccessor {
    void fur_setResourceKeyMap(Map<ResourceKey<Biome>, Holder<Biome>> map);
    Map<ResourceKey<Biome>, Holder<Biome>> fur_getResourceKeyMap();
    void fur_expandBiomesWith(Set<Holder<Biome>> biomes);
}
