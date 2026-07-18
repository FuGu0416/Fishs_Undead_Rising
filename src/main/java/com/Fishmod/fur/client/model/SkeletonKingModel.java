package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.SkeletonKingEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SkeletonKingModel extends GeoModel<SkeletonKingEntity> {
    private static final ResourceLocation TEXTURE    = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/skeletonking/skeletonking.png");
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/skeletonking.animation.json");
    private static final ResourceLocation MODEL      = new ResourceLocation(mod_LavaCow.MODID, "geo/skeletonking.geo.json");

    @Override
    public ResourceLocation getTextureResource(SkeletonKingEntity object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SkeletonKingEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(SkeletonKingEntity animatable) {
        return MODEL;
    }

    @Override
    public void setCustomAnimations(SkeletonKingEntity animatable, long instanceId, AnimationState<SkeletonKingEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
