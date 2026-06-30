package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.ShroomLordEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ShroomLordModel extends GeoModel<ShroomLordEntity> {
    private static final ResourceLocation TEXTURE    = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/shroomlord/shroomlord.png");
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/shroomlord.animation.json");
    private static final ResourceLocation MODEL      = new ResourceLocation(mod_LavaCow.MODID, "geo/shroomlord.geo.json");

    @Override
    public ResourceLocation getTextureResource(ShroomLordEntity object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ShroomLordEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(ShroomLordEntity animatable) {
        return MODEL;
    }

    @Override
    public void setCustomAnimations(ShroomLordEntity animatable, long instanceId, AnimationState<ShroomLordEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Nullable
    @Override
    public RenderType getRenderType(ShroomLordEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }
}
