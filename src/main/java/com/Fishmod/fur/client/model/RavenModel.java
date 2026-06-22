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
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        // Head tracking. Skipped while perched on a player: the body yaw is forced to the rider's yaw
        // every tick while the look goal keeps tracking the rider (at ~zero distance), so netHeadYaw
        // flips between extremes each tick and the head jitters. Resting the head at its default pose
        // (forward relative to the body, which already follows the player) avoids that.
        if (head != null && !entity.isPassenger()) {
        	EntityModelData modelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(modelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(modelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Nullable
    @Override
    public RenderType getRenderType(RavenEntity entity, ResourceLocation texture) {
        if (entity.getSkin() == 2) {
            return RenderType.entityTranslucent(this.getTextureResource(entity));
        }
        return super.getRenderType(entity, texture);
    }
}
