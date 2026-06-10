package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.WarpedFireflyEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WarpedFireflyModel extends GeoModel<WarpedFireflyEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/warpedfirefly/warpedfirefly.png");
	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/warpedfirefly.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/warpedfirefly.geo.json");

	@Override
	public ResourceLocation getTextureResource(WarpedFireflyEntity object) {
		return TEXTURE;
	}

	@Override
	public ResourceLocation getAnimationResource(WarpedFireflyEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(WarpedFireflyEntity animatable) {
		return MODEL;
	}
}
