package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.RavenEntity;
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
public class LayerRavenGlow extends GeoRenderLayer<RavenEntity> {
    private static final ResourceLocation EYES_TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/raven/raven_eyes.png");

    public LayerRavenGlow(GeoRenderer<RavenEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, RavenEntity animatable, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource,
                       VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType eyeRenderType = RenderType.eyes(EYES_TEXTURE);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, eyeRenderType,
                bufferSource.getBuffer(eyeRenderType), partialTick, 15728640,
                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
