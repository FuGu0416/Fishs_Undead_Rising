package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.RavenEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RavenModel extends GeoModel<RavenEntity> {
    private static final ResourceLocation[] TEXTURES = {
        new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/raven/raven.png"),
        new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/raven/raven1.png"),
        new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/raven/raven2.png"),
        new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/raven/raven3.png"),
    };

    private static final ResourceLocation ANIMATIONS =
            new ResourceLocation(mod_LavaCow.MODID, "animations/raven.animation.json");
    private static final ResourceLocation MODEL =
            new ResourceLocation(mod_LavaCow.MODID, "geo/raven.geo.json");

    @Override
    public ResourceLocation getTextureResource(RavenEntity entity) {
        int skin = entity.getSkin();
        return TEXTURES[skin >= 0 && skin < TEXTURES.length ? skin : 0];
    }

    @Override
    public ResourceLocation getAnimationResource(RavenEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(RavenEntity animatable) {
        return MODEL;
    }

    @Override
    public void setCustomAnimations(RavenEntity entity, long instanceId, AnimationState<RavenEntity> animationState) {
        CoreGeoBone head     = getAnimationProcessor().getBone("head");
        CoreGeoBone beak1    = getAnimationProcessor().getBone("beak1");
        CoreGeoBone beak2    = getAnimationProcessor().getBone("beak2");
        CoreGeoBone wingL    = getAnimationProcessor().getBone("wing_left");
        CoreGeoBone wingR    = getAnimationProcessor().getBone("wing_right");
        CoreGeoBone legL     = getAnimationProcessor().getBone("leg_left");
        CoreGeoBone legR     = getAnimationProcessor().getBone("leg_right");
        CoreGeoBone tail     = getAnimationProcessor().getBone("tail");

        EntityModelData modelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        float limbSwing       = entity.walkAnimation.position();
        float limbSwingAmount = entity.walkAnimation.speed();

        // Head tracking
        if (head != null) {
            head.setRotX(modelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(modelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        // Beak: open when calling
        if (beak1 != null && beak2 != null && entity.callTimer > 0) {
            beak1.setRotX(0.18F);
            beak2.setRotX(-0.18F);
        }

        boolean isSitting = entity.isInSittingPose();
        boolean isFlying  = entity.isFlying();
        float flap = Mth.cos(entity.tickCount * 1.5F) * 0.5F;

        // Wings
        if (wingL != null && wingR != null) {
            if (isSitting) {
                wingL.setRotZ(-0.0873F);
                wingR.setRotZ(0.0873F);
            } else if (isFlying) {
                wingL.setRotZ(-1.5934F + flap);
                wingR.setRotZ(1.5934F - flap);
            } else {
                float walkFlap = Mth.cos(limbSwing * 1.5F) * 0.2F * limbSwingAmount;
                wingL.setRotZ(-1.5934F - walkFlap);
                wingR.setRotZ(1.5934F + walkFlap);
            }
        }

        // Legs
        if (legL != null && legR != null) {
            if (isSitting) {
                legL.setRotX(0.9701F);
                legR.setRotX(0.9701F);
            } else if (isFlying) {
                legL.setRotX(0.6682F);
                legR.setRotX(0.6682F);
            } else {
                float swing = Mth.cos(limbSwing * 1.4F) * 0.5F * limbSwingAmount;
                legL.setRotX(-0.0299F + swing);
                legR.setRotX(-0.0299F - swing);
            }
        }

        // Tail
        if (tail != null) {
            if (isSitting) {
                tail.setRotX(1.5389F);
            } else {
                float tailSwing = Mth.cos(limbSwing * 1.4F) * 0.2F * limbSwingAmount;
                tail.setRotX(1.015F + tailSwing);
            }
        }
    }

    @Nullable
    @Override
    public RenderType getRenderType(RavenEntity entity, ResourceLocation texture) {
        if (entity.getSkin() == 3) {
            return RenderType.entityTranslucent(this.getTextureResource(entity));
        }
        return super.getRenderType(entity, texture);
    }
}
