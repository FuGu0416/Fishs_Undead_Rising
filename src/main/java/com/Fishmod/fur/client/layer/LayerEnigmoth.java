package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.EnigmothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class LayerEnigmoth extends GeoRenderLayer<EnigmothEntity> {
	private static final ResourceLocation[] TEXTURES_EYE = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth_eyes.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth_eyes1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth_eyes2.png")
	};
	
	public LayerEnigmoth(GeoRenderer<EnigmothEntity> mycosisRenderer) {
		super(mycosisRenderer);
	}
	
	/**
	 * Get the render type to use for this glowlayer renderer
	 * <p>
	 * Uses a custom RenderType similar to {@link RenderType#eyes(ResourceLocation)} by default, which may not be ideal in all circumstances
	 */
	protected RenderType getRenderType(EnigmothEntity animatable) {
		return AutoGlowingTexture.getRenderType(getTextureResource(animatable));
	}
	
	/**
	 * Get the texture resource path for the given {@link GeoAnimatable}.<br>
	 * By default, falls back to {@link GeoRenderer#getTextureLocation(GeoAnimatable)}
	 */
	protected ResourceLocation getTextureResource(EnigmothEntity animatable) {
		return animatable.isBaby() ? this.renderer.getTextureLocation(animatable) : TEXTURES_EYE[animatable.getSkin()];
	}

	/**
	 * This is the method that is actually called by the render for your render layer to function.<br>
	 * This is called <i>after</i> the animatable has been rendered, but before supplementary rendering like nametags.
	 */
	@Override
	public void render(PoseStack poseStack, EnigmothEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (animatable.getSkin() != 2) return;
		
		RenderType emissiveRenderType = getRenderType(animatable);

		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, emissiveRenderType,
							   bufferSource.getBuffer(emissiveRenderType), partialTick, LightTexture.FULL_SKY, OverlayTexture.NO_OVERLAY,
							   1, 1, 1, 1);
	}
}
