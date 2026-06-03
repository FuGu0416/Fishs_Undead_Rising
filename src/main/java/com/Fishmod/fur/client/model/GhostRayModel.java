package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.GhostRayEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GhostRayModel extends GeoModel<GhostRayEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghostray/ghostray.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghostray/ghostray2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghostray/ghostray3.png")
	};

	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/ghostray.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/ghostray.geo.json");

	@Override
	public ResourceLocation getTextureResource(GhostRayEntity object) {
		int skin = object.getSkin();
		return TEXTURES[(skin >= 0 && skin < TEXTURES.length) ? skin : 2];
	}

	@Override
	public ResourceLocation getAnimationResource(GhostRayEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(GhostRayEntity animatable) {
		return MODEL;
	}

	@Nullable
	@Override
	public RenderType getRenderType(GhostRayEntity entity, ResourceLocation texture) {
		return RenderType.entityTranslucent(this.getTextureResource(entity));
	}
}
