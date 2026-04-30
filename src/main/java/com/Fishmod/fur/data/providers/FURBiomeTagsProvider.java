package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBiomesRegistry;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURBiomeTagsProvider extends BiomeTagsProvider {
    public static final TagKey<Biome> HAS_FOGLET = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_foglet"));
    public static final TagKey<Biome> HAS_ISNACHI = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_isnachi"));
    public static final TagKey<Biome> HAS_PIRANHA = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_piranha"));
    public static final TagKey<Biome> HAS_SWARMER = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_swarmer"));
    public static final TagKey<Biome> HAS_CACTYRANT = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_cactyrant"));
    public static final TagKey<Biome> HAS_WETA = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_weta"));
    public static final TagKey<Biome> HAS_MYCOSIS = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_mycosis"));
    public static final TagKey<Biome> IS_OVERWORLD_HOSTILE = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "is_overworld_hostile"));
    public static final TagKey<Biome> HAS_BANSHEE = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_banshee"));
    public static final TagKey<Biome> HAS_CACTOID = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_cactoid"));
    public static final TagKey<Biome> HAS_PTERA = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_ptera"));
    
    public FURBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, mod_LavaCow.MODID, existingFileHelper);
    }
    
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(HAS_FOGLET).addTag(Tags.Biomes.IS_SWAMP);
        this.tag(HAS_ISNACHI).addTag(Tags.Biomes.IS_LUSH).addTag(BiomeTags.IS_JUNGLE);
        this.tag(HAS_PIRANHA).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE);
        this.tag(HAS_SWARMER).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE).add(Biomes.LUKEWARM_OCEAN).add(Biomes.DEEP_LUKEWARM_OCEAN).add(Biomes.DEEP_DARK);
        this.tag(HAS_CACTYRANT).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.SOUL_SAND_VALLEY);
        this.tag(HAS_WETA).addTag(BiomeTags.IS_SAVANNA).add(Biomes.LUSH_CAVES).add(Biomes.DRIPSTONE_CAVES);
        this.tag(HAS_MYCOSIS).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE).add(FURBiomesRegistry.LUMINOUS_UNDERGROVE);
        this.tag(IS_OVERWORLD_HOSTILE).addTag(BiomeTags.IS_OVERWORLD).remove(Tags.Biomes.IS_MUSHROOM);
        this.tag(HAS_BANSHEE).addTag(BiomeTags.IS_HILL).addTag(BiomeTags.IS_MOUNTAIN);
        this.tag(HAS_CACTOID).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.BASALT_DELTAS);
        this.tag(HAS_PTERA).addTag(BiomeTags.IS_JUNGLE).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_SAVANNA).addTag(Tags.Biomes.IS_LUSH);
    }
}
