package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.CocoonEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * BEELZEBUBPUPA reuses {@link CocoonEntity} (the port of the 1.16.5 VespaCocoonEntity) but renders
 * with its own pupa texture and a placeholder geo/animation set.
 */
public class BeelzebubPupaModel extends GeoModel<CocoonEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/beelzebub/beelzebub_pupa.png");

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/beelzebub_pupa.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/beelzebub_pupa.geo.json");

    @Override
    public ResourceLocation getTextureResource(CocoonEntity object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(CocoonEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(CocoonEntity animatable) {
        return MODEL;
    }

    @Nullable
    @Override
    public RenderType getRenderType(CocoonEntity entity, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
