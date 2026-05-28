package com.Fishmod.fur.mixin;

import com.Fishmod.fur.worldgen.biome.FURBiomeSourceAccessor;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(BiomeSource.class)
public class BiomeSourceMixin implements FURBiomeSourceAccessor {

    @Mutable
    @Shadow
    public Supplier<Set<Holder<Biome>>> possibleBiomes;

    private boolean fur_expanded;
    private Map<ResourceKey<Biome>, Holder<Biome>> fur_map = new HashMap<>();

    @Override
    public void fur_setResourceKeyMap(Map<ResourceKey<Biome>, Holder<Biome>> map) {
        this.fur_map = map;
    }

    @Override
    public Map<ResourceKey<Biome>, Holder<Biome>> fur_getResourceKeyMap() {
        return fur_map;
    }

    @Override
    public void fur_expandBiomesWith(Set<Holder<Biome>> newBiomes) {
        if (!fur_expanded) {
            ImmutableSet.Builder<Holder<Biome>> builder = ImmutableSet.builder();
            builder.addAll(this.possibleBiomes.get());
            builder.addAll(newBiomes);
            possibleBiomes = Suppliers.memoize(builder::build);
            fur_expanded = true;
        }
    }
}
