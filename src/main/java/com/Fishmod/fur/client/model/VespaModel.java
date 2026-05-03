package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.VespaEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class VespaModel extends GeoModel<VespaEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa.png"),
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa1.png")
    };

    private static final ResourceLocation ANIMATIONS =
            new ResourceLocation(mod_LavaCow.MODID, "animations/vespa.animation.json");

    private static final ResourceLocation MODEL =
            new ResourceLocation(mod_LavaCow.MODID, "geo/vespa.geo.json");

    @Override
    public ResourceLocation getTextureResource(VespaEntity entity) {
        int skin = entity.getSkin();
        return TEXTURES[skin < TEXTURES.length ? skin : 0];
    }

    @Override
    public ResourceLocation getAnimationResource(VespaEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(VespaEntity animatable) {
        return MODEL;
    }
}
