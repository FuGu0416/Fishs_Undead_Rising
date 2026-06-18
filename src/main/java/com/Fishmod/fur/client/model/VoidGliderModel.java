package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.VoidGliderEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidGliderModel extends GeoModel<VoidGliderEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/void_glider/void_glider.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/void_glider/void_glider2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/void_glider/void_glider3.png")
	};

	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/void_glider.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/void_glider.geo.json");

	@Override
	public ResourceLocation getTextureResource(VoidGliderEntity object) {
		int skin = object.getSkin();
		return TEXTURES[(skin >= 0 && skin < TEXTURES.length) ? skin : 2];
	}

	@Override
	public ResourceLocation getAnimationResource(VoidGliderEntity animatable) {
		return ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelResource(VoidGliderEntity animatable) {
		return MODEL;
	}

	@Nullable
	@Override
	public RenderType getRenderType(VoidGliderEntity entity, ResourceLocation texture) {
		return RenderType.entityTranslucent(this.getTextureResource(entity));
	}
}
