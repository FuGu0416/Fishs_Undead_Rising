package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURBiomeTagsProvider extends BiomeTagsProvider {
    public FURBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, mod_LavaCow.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(FURTags.HAS_FOGLET).addTag(Tags.Biomes.IS_SWAMP);
        this.tag(FURTags.HAS_ISNACHI).addTag(Tags.Biomes.IS_LUSH).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE);
        this.tag(FURTags.HAS_PIRANHA).addTag(Tags.Biomes.IS_SWAMP).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE);
        this.tag(FURTags.HAS_SWARMER).addTag(Tags.Biomes.IS_SWAMP).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE).add(Biomes.LUKEWARM_OCEAN).add(Biomes.DEEP_LUKEWARM_OCEAN).add(Biomes.DEEP_DARK);
        this.tag(FURTags.HAS_CACTYRANT).addTag(Tags.Biomes.IS_DESERT).add(Biomes.SOUL_SAND_VALLEY);
        this.tag(FURTags.HAS_WETA).addTag(BiomeTags.IS_SAVANNA).add(Biomes.LUSH_CAVES).add(Biomes.DRIPSTONE_CAVES);
    }
}
