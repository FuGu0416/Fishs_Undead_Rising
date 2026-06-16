package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.BeelzebubEntity;

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

@OnlyIn(Dist.CLIENT)
public class BeelzebubModel extends GeoModel<BeelzebubEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/beelzebub/beelzebub.png");

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/beelzebub.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/beelzebub.geo.json");

    @Override
    public ResourceLocation getTextureResource(BeelzebubEntity entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(BeelzebubEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(BeelzebubEntity animatable) {
        return MODEL;
    }

    @Override
    public void setCustomAnimations(BeelzebubEntity animatable, long instanceId, AnimationState<BeelzebubEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Nullable
    @Override
    public RenderType getRenderType(BeelzebubEntity entity, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
