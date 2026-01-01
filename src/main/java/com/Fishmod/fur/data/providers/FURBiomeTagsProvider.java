package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURTagRegistry;

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
        this.tag(FURTagRegistry.HAS_FOGLET).addTag(Tags.Biomes.IS_SWAMP);
        this.tag(FURTagRegistry.HAS_ISNACHI).addTag(Tags.Biomes.IS_LUSH).addTag(BiomeTags.IS_JUNGLE);
        this.tag(FURTagRegistry.HAS_PIRANHA).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE);
        this.tag(FURTagRegistry.HAS_SWARMER).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE).add(Biomes.LUKEWARM_OCEAN).add(Biomes.DEEP_LUKEWARM_OCEAN).add(Biomes.DEEP_DARK);
        this.tag(FURTagRegistry.HAS_CACTYRANT).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.SOUL_SAND_VALLEY);
        this.tag(FURTagRegistry.HAS_WETA).addTag(BiomeTags.IS_SAVANNA).add(Biomes.LUSH_CAVES).add(Biomes.DRIPSTONE_CAVES);
        this.tag(FURTagRegistry.HAS_MYCOSIS).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE);
        this.tag(FURTagRegistry.IS_OVERWORLD_HOSTILE).addTag(BiomeTags.IS_OVERWORLD).remove(Tags.Biomes.IS_MUSHROOM);
        this.tag(FURTagRegistry.HAS_BANSHEE).addTag(BiomeTags.IS_HILL).addTag(BiomeTags.IS_MOUNTAIN);
        this.tag(FURTagRegistry.HAS_CACTOID).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.BASALT_DELTAS);
    }
}
