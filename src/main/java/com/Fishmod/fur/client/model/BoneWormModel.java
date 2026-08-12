package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.BoneWormEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BoneWormModel extends GeoModel<BoneWormEntity> {
    private static final ResourceLocation TEXTURE            = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/boneworm/boneworm.png");
    private static final ResourceLocation TEXTURE_SOUL_SAND_VALLEY = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/boneworm/boneworm1.png");
    private static final ResourceLocation ANIMATIONS         = new ResourceLocation(mod_LavaCow.MODID, "animations/boneworm.animation.json");
    private static final ResourceLocation MODEL               = new ResourceLocation(mod_LavaCow.MODID, "geo/boneworm.geo.json");

    @Override
    public ResourceLocation getTextureResource(BoneWormEntity object) {
        return switch (object.getSkin()) {
            case 1 -> TEXTURE_SOUL_SAND_VALLEY;
            default -> TEXTURE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(BoneWormEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(BoneWormEntity animatable) {
        return MODEL;
    }
}
