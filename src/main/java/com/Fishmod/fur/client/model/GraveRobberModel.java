package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.GraveRobberEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * Grave Robber model — GeckoLib replacement for the old vanilla-{@link
 * net.minecraft.client.model.IllagerModel}-based mesh. Same silhouette (illager body + back "bag"),
 * now driven by {@code geo/graverobber.geo.json} / {@code animations/graverobber.animation.json}
 * instead of a hand-coded {@code ModelPart} layer.
 */
public class GraveRobberModel extends GeoModel<GraveRobberEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/graverobber/graverobber.png");
	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/graverobber.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/graverobber.geo.json");

	@Override
	public ResourceLocation getTextureResource(GraveRobberEntity object) {
		return TEXTURE;
	}

	@Override
	public ResourceLocation getAnimationResource(GraveRobberEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(GraveRobberEntity animatable) {
		return MODEL;
	}

	@Override
	public void setCustomAnimations(GraveRobberEntity animatable, long instanceId, AnimationState<GraveRobberEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Nullable
	@Override
	public RenderType getRenderType(GraveRobberEntity entity, ResourceLocation texture) {
		return RenderType.entityCutoutNoCull(this.getTextureResource(entity));
	}
}
