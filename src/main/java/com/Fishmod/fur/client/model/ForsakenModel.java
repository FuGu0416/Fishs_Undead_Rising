package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.ForsakenEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * Forsaken model — a PLACEHOLDER plain vanilla-biped GeckoLib mesh (see {@code geo/forsaken.geo.json})
 * reusing vanilla's own skeleton texture, standing in until a real Forsaken model is authored.
 */
public class ForsakenModel extends GeoModel<ForsakenEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/forsaken.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/forsaken.geo.json");

	@Override
	public ResourceLocation getTextureResource(ForsakenEntity object) {
		return TEXTURE;
	}

	@Override
	public ResourceLocation getAnimationResource(ForsakenEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(ForsakenEntity animatable) {
		return MODEL;
	}

	@Override
	public void setCustomAnimations(ForsakenEntity animatable, long instanceId, AnimationState<ForsakenEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Nullable
	@Override
	public RenderType getRenderType(ForsakenEntity entity, ResourceLocation texture) {
		return RenderType.entityCutoutNoCull(this.getTextureResource(entity));
	}
}
