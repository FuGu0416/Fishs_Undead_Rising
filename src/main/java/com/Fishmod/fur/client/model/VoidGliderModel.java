package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.VoidGliderEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidGliderModel extends GeoModel<VoidGliderEntity> {
	/**
	 * Only one real texture exists - End-only spawning (see {@link VoidGliderEntity#finalizeSpawn}) means
	 * the old multi-biome skin set (Overworld/Nether/End) this array once held is gone; {@code getSkin()}
	 * is kept only for save compatibility with older data and no longer selects a texture.
	 */
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/void_glider/void_glider.png");

	private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/void_glider.animation.json");
	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/void_glider.geo.json");

	@Override
	public ResourceLocation getTextureResource(VoidGliderEntity object) {
		return TEXTURE;
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
