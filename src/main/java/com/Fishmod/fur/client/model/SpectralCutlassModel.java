package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.SpectralCutlassEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * GeoModel for the Spectral Cutlass summon. The geometry is essentially a rig — the visible blade is the
 * item itself, rendered onto the {@code base} bone by {@link com.Fishmod.fur.client.renderer.entity.SpectralCutlassRenderer}
 * so it follows the bone's idle/dash/attack animation. The texture is only sampled by whatever cubes the
 * geo defines; it points at the item texture so it stays consistent.
 */
public class SpectralCutlassModel extends GeoModel<SpectralCutlassEntity> {

	private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/spectral_cutlass.geo.json");
	private static final ResourceLocation ANIMATION = new ResourceLocation(mod_LavaCow.MODID, "animations/spectral_cutlass.animation.json");
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/item/spectral_cutlass.png");

	@Override
	public ResourceLocation getModelResource(SpectralCutlassEntity animatable) {
		return MODEL;
	}

	@Override
	public ResourceLocation getAnimationResource(SpectralCutlassEntity animatable) {
		return ANIMATION;
	}

	@Override
	public ResourceLocation getTextureResource(SpectralCutlassEntity animatable) {
		return TEXTURE;
	}
}
