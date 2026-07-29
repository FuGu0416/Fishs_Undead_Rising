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
	// Base ("always on", alpha-controlled by WraithRenderer#getRenderColor) texture per variant.
	// No separate "_overlay" art needed anymore — LayerWraith draws the same texture on top,
	// opaque, fading in via WraithEntity#getFadeIn.
	private static final ResourceLocation TEXTURES = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wraith/wraith.png");
	private static final ResourceLocation TEXTURES_VARIANT1 = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wraith/wraith1.png");

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/wraith.animation.json");
    private static final ResourceLocation ANIMATIONS_VARIANT1 = new ResourceLocation(mod_LavaCow.MODID, "animations/wraith1.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/wraith.geo.json");
    private static final ResourceLocation MODEL_VARIANT1 = new ResourceLocation(mod_LavaCow.MODID, "geo/wraith1.geo.json");

    @Override
    public ResourceLocation getTextureResource(WraithEntity object) {
        return object.isVariant1() ? TEXTURES_VARIANT1 : TEXTURES;
    }

    @Override
    public ResourceLocation getAnimationResource(WraithEntity animatable) {
        return animatable.isVariant1() ? ANIMATIONS_VARIANT1 : ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(WraithEntity animatable) {
		return animatable.isVariant1() ? MODEL_VARIANT1 : MODEL;
	}

    @Override
    public void setCustomAnimations(WraithEntity animatable, long instanceId, AnimationState<WraithEntity> animationState) {
        // The two geo rigs use different bone casing: "Head" on the original, "head" on wraith1's.
        CoreGeoBone head = getAnimationProcessor().getBone(animatable.isVariant1() ? "head" : "Head");

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
