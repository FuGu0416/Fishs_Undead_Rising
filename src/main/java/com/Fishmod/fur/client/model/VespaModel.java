package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.VespaEntity;

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
public class VespaModel extends GeoModel<VespaEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa.png"),
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa1.png")
    };

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/vespa.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/vespa.geo.json");

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
    
    @Override
    public void setCustomAnimations(VespaEntity animatable, long instanceId, AnimationState<VespaEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(VespaEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
