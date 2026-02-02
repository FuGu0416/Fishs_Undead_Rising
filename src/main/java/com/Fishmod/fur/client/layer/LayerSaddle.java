package com.Fishmod.fur.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class LayerSaddle<T extends LivingEntity & Saddleable & GeoEntity> extends GeoRenderLayer<T> {

	private final ResourceLocation saddleTexture;

    public LayerSaddle(GeoRenderer<T> renderer, ResourceLocation saddleTexture) {
        super(renderer);
        this.saddleTexture = saddleTexture;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!animatable.isSaddled()) return;

        RenderType saddleRenderType = RenderType.entityCutoutNoCull(this.saddleTexture);
		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, saddleRenderType,
				   bufferSource.getBuffer(saddleRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
				   1.0F, 1.0F, 1.0F, 1.0F);
    }
}