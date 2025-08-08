package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class FURTagRegistry {
    //public static final ResourceLocation INTESTINE_DROP_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "intestine_drop_targets");
    //public static final ResourceLocation BEELZEBUB_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "beelzebub_targets");
    //public static final ResourceLocation ENIGMOTH_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "enigmoth_targets");
    //public static final ResourceLocation PARASITE_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "parasite_targets");
    public static final TagKey<EntityType<?>> PIRANHA_TARGETS = create(new ResourceLocation(mod_LavaCow.MODID, "piranha_targets"));
    //public static final ResourceLocation PTERA_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "ptera_targets");
    public static final TagKey<EntityType<?>> SWARMER_TARGETS = create(new ResourceLocation(mod_LavaCow.MODID, "swarmer_targets"));
    //public static final ResourceLocation VESPA_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "vespa_targets");
    public static final TagKey<EntityType<?>> WENDIGO_TARGETS = create(new ResourceLocation(mod_LavaCow.MODID, "wendigo_targets"));
    //public static final ResourceLocation LAMPREY_TARGETS = new ResourceLocation(mod_LavaCow.MODID, "lamprey_targets");
    
    private static TagKey<EntityType<?>> create(ResourceLocation resource) {
        return TagKey.create(Registries.ENTITY_TYPE, resource);
	}
}
