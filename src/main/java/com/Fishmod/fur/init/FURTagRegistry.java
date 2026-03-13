package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.structure.Structure;

public class FURTagRegistry {
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
    
    public static final TagKey<Structure> HAS_SEAHAG = TagKey.create(Registries.STRUCTURE, new ResourceLocation(mod_LavaCow.MODID, "has_seahag"));   
    public static final TagKey<Structure> HAS_MUMMY = TagKey.create(Registries.STRUCTURE, new ResourceLocation(mod_LavaCow.MODID, "has_mummy"));
    public static final TagKey<Structure> HAS_MIMIC = TagKey.create(Registries.STRUCTURE, new ResourceLocation(mod_LavaCow.MODID, "has_mimic"));
    
    public static final TagKey<EntityType<?>> PIRANHA_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "piranha_targets"));
    public static final TagKey<EntityType<?>> SWARMER_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "swarmer_targets"));
    public static final TagKey<EntityType<?>> WENDIGO_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "wendigo_targets"));
    //public static final ResourceLocation INTESTINE_DROP_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "intestine_drop_targets");
    //public static final ResourceLocation BEELZEBUB_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "beelzebub_targets");
    public static final TagKey<EntityType<?>> ENIGMOTH_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "enigmoth_targets"));
    public static final TagKey<EntityType<?>> PARASITE_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "parasite_targets"));
    public static final TagKey<EntityType<?>> PTERA_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "ptera_targets"));
    //public static final ResourceLocation VESPA_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "vespa_targets");
    //public static final ResourceLocation LAMPREY_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "lamprey_targets");
    
    public static final TagKey<Item> SERVING_CONTAINERS = TagKey.create(Registries.ITEM, new ResourceLocation("farmersdelight", "serving_containers"));
    
    public static final TagKey<Block> SALAMANDER_EGG_HATCH_BOOST = TagKey.create(Registries.BLOCK, new ResourceLocation(mod_LavaCow.MODID, "salamander_egg_hatch_boost"));
    
    public static final TagKey<BannerPattern> PATTERN_SKELETONKING = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(mod_LavaCow.MODID, "skeletonking"));
    public static final TagKey<BannerPattern> PATTERN_WENDIGO = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(mod_LavaCow.MODID, "wendigo"));
}
