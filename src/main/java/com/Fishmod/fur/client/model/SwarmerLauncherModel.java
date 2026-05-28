package com.Fishmod.fur.client.model;

import com.Fishmod.fur.entities.projectiles.SwarmerLauncherEntity;
import com.Fishmod.fur.mod_LavaCow;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SwarmerLauncherModel extends GeoModel<SwarmerLauncherEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer.png");
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/swarmer.animation.json");
    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/swarmer.geo.json");

    @Override
    public ResourceLocation getTextureResource(SwarmerLauncherEntity object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SwarmerLauncherEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(SwarmerLauncherEntity animatable) {
        return MODEL;
    }
}
