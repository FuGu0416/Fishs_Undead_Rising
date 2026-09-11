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
    public static final TagKey<Biome> HAS_SCARECROW = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_scarecrow"));
    public static final TagKey<Biome> HAS_RAVEN = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_raven"));
    public static final TagKey<Biome> HAS_GRAVEYARD = TagKey.create(Registries.BIOME, new ResourceLocation(mod_LavaCow.MODID, "has_graveyard"));
    // Vanilla structure-eligibility tag, not a FUR-namespaced tag - adding to it (replace:false by
    // default) appends our biome onto vanilla's existing mineshaft biome list rather than replacing it.
    public static final TagKey<Biome> HAS_STRUCTURE_MINESHAFT = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "has_structure/mineshaft"));

    public FURBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, mod_LavaCow.MODID, existingFileHelper);
    }
    
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(HAS_FOGLET).addTag(Tags.Biomes.IS_SWAMP);
        this.tag(HAS_ISNACHI).addTag(Tags.Biomes.IS_LUSH).addTag(BiomeTags.IS_JUNGLE);
        this.tag(HAS_PIRANHA).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE);
        this.tag(HAS_SWARMER).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE).add(Biomes.LUKEWARM_OCEAN).add(Biomes.DEEP_LUKEWARM_OCEAN).add(Biomes.DEEP_DARK).add(FURBiomesRegistry.CARRION_HOLLOW);
        this.tag(HAS_CACTYRANT).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.SOUL_SAND_VALLEY);
        this.tag(HAS_WETA).addTag(BiomeTags.IS_SAVANNA).add(Biomes.LUSH_CAVES).add(Biomes.DRIPSTONE_CAVES).add(FURBiomesRegistry.CARRION_HOLLOW);
        this.tag(HAS_MYCOSIS).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_JUNGLE).add(FURBiomesRegistry.LUMINOUS_UNDERGROVE);
        this.tag(IS_OVERWORLD_HOSTILE).addTag(BiomeTags.IS_OVERWORLD).remove(Tags.Biomes.IS_MUSHROOM);
        this.tag(HAS_BANSHEE).addTag(BiomeTags.IS_HILL).addTag(BiomeTags.IS_MOUNTAIN);
        this.tag(HAS_CACTOID).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).add(Biomes.BASALT_DELTAS);
        this.tag(HAS_PTERA).addTag(BiomeTags.IS_JUNGLE).addTag(Tags.Biomes.IS_DESERT).addTag(BiomeTags.IS_BADLANDS).addTag(Tags.Biomes.IS_SWAMP).addTag(BiomeTags.IS_SAVANNA).addTag(Tags.Biomes.IS_LUSH);
        this.tag(HAS_SCARECROW)
                .addTag(BiomeTags.HAS_VILLAGE_PLAINS)
                .addTag(BiomeTags.HAS_VILLAGE_DESERT)
                .addTag(BiomeTags.HAS_VILLAGE_SAVANNA)
                .addTag(BiomeTags.HAS_VILLAGE_SNOWY)
                .addTag(BiomeTags.HAS_VILLAGE_TAIGA);
        // 1.16.5 spawned ravens in SPOOKY (dark forest) + CONIFEROUS (taiga family); broadened here
        // with forests and plains. IS_FOREST already includes dark_forest.
        // Flower forest + cherry grove are added explicitly so ravens spawn there (cherry grove isn't
        // in IS_FOREST); these biomes spawn the white "skin 1" variant (see RavenEntity#finalizeSpawn).
        this.tag(HAS_RAVEN)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_TAIGA)
                .addTag(BiomeTags.HAS_VILLAGE_PLAINS)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.CHERRY_GROVE);
        // Grass-predominant overworld surface biomes: forest + plains + savanna + taiga families,
        // explicitly excluding jungle (none of the tags above pull it in anyway, but excluded
        // outright to guarantee that stays true if any of them ever change).
        this.tag(HAS_GRAVEYARD)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(Tags.Biomes.IS_PLAINS)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(BiomeTags.IS_TAIGA)
                .remove(BiomeTags.IS_JUNGLE);
        // Luminous Undergrove is thematically closest to vanilla's Dripstone Caves/Lush Caves, both of
        // which are already in this tag - was simply never opted in when the biome was added.
        this.tag(HAS_STRUCTURE_MINESHAFT).add(FURBiomesRegistry.LUMINOUS_UNDERGROVE);
    }
}
