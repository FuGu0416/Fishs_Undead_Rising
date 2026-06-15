package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.floating.WraithEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class WraithModel extends GeoModel<WraithEntity> {
	private static final ResourceLocation TEXTURES_OVERLAY = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wraith/wraith_overlay.png");
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/wraith.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/wraith.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(WraithEntity object) {
        return TEXTURES_OVERLAY;
    }

    @Override
    public ResourceLocation getAnimationResource(WraithEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(WraithEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(WraithEntity animatable, long instanceId, AnimationState<WraithEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(WraithEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
