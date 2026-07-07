package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.ShroomlingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

/**
 * Collar-style overlay (modelled on {@link ScarecrowCollarLayer}) that re-renders the Shroomling with
 * the {@code shroomling_bubble} texture tinted by the colour of the potion effect it currently carries.
 */
@OnlyIn(Dist.CLIENT)
public class ShroomlingBubbleLayer<T extends ShroomlingEntity> extends GeoRenderLayer<T> {
	private static final ResourceLocation BUBBLE_LOCATION = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/shroomling/shroomling_bubble.png");

	public ShroomlingBubbleLayer(GeoRenderer<T> renderer) {
		super(renderer);
	}

	/** Saturation multiplier (push channels away from grey) so muted effect colours stay a vivid hue. */
	private static final float SATURATION = 1.8F;

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (!animatable.isInvisible()) {
			int color = animatable.getSporeColor();
			float r = ((color >> 16) & 0xFF) / 255.0F;
			float g = ((color >> 8) & 0xFF) / 255.0F;
			float b = (color & 0xFF) / 255.0F;

			// Boost saturation (push away from luminance grey) so muted effect colours read as a vivid hue.
			float lum = 0.3F * r + 0.59F * g + 0.11F * b;
			r = Mth.clamp(lum + (r - lum) * SATURATION, 0.0F, 1.0F);
			g = Mth.clamp(lum + (g - lum) * SATURATION, 0.0F, 1.0F);
			b = Mth.clamp(lum + (b - lum) * SATURATION, 0.0F, 1.0F);

			// Single, normally-lit translucent pass. The bubble texture's own alpha gradient shapes a soft
			// bubble and lets the Shroomling show through, tinted by the effect colour. The previous
			// emissive + additive "eyes" second pass over-brightened the tint into an opaque, washed-out
			// shell (and simply lowering its alpha instead caused translucent depth-sort artefacts), so
			// both are dropped. ShroomlingRenderer already forces block-light 15, so the bubble stays
			// bright without an emissive pass.
			RenderType bubbleType = RenderType.entityTranslucent(BUBBLE_LOCATION);
			getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, bubbleType,
								   bufferSource.getBuffer(bubbleType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
								   r, g, b, 1.0F);
		}
	}
}
