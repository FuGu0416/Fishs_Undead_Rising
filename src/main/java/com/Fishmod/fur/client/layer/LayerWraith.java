package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.floating.WraithEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class LayerWraith<T extends WraithEntity> extends GeoRenderLayer<T> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wraith/wraith.png");
	private static final ResourceLocation TEXTURES_VARIANT1 = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wraith/wraith1.png");

	public LayerWraith(GeoRenderer<T> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (!animatable.isInvisible()) {
			ResourceLocation texture = animatable.getSkin() == 1 ? TEXTURES_VARIANT1 : TEXTURES;
			RenderType RenderType = net.minecraft.client.renderer.RenderType.entityTranslucent(texture);

			getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, RenderType,
								   bufferSource.getBuffer(RenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
								   1.0F, 1.0F, 1.0F, animatable.getFadeIn(partialTick));
		}
	}
}