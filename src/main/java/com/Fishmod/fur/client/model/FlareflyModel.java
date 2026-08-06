package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.FlareflyEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FlareflyModel extends GeoModel<FlareflyEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/flarefly/flarefly.png");
	private static final ResourceLocation TEXTURE_LUSH_CAVES = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/flarefly/flarefly1.png");
	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/flarefly.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/flarefly.geo.json");

	@Override
	public ResourceLocation getTextureResource(FlareflyEntity object) {
		return object.getSkin() == 1 ? TEXTURE_LUSH_CAVES : TEXTURE;
	}

	@Override
	public ResourceLocation getAnimationResource(FlareflyEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(FlareflyEntity animatable) {
		return MODEL;
	}
}
