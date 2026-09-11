package com.Fishmod.fur.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public interface FURMultiNoiseBiomeSourceAccessor {
    void fur_setWorldSeed(long seed);
    void fur_setDimension(ResourceKey<Level> dimension);
    void fur_setLuminousHolder(Holder<Biome> holder);
    Holder<Biome> fur_getLuminousHolder();
    void fur_setCarrionHollowHolder(Holder<Biome> holder);
    Holder<Biome> fur_getCarrionHollowHolder();
}
