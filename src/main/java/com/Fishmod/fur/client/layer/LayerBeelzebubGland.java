package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.BeelzebubEntity;
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

/**
 * Ported from 1.16.5's {@code BeelzebubRenderer.GlandLayer}: while {@link BeelzebubEntity#canHarvest()}
 * is true, re-renders the whole (already-posed) model with the {@code beelzebub_gland.png} texture
 * instead of the normal skin -- 1.16.5 painted this as a visibly "swollen" gland texture rather than
 * using an actual full-bright/emissive render pass, so this passes the normal {@code packedLight}
 * through unchanged (same as {@link LayerSaddle}), it is not an {@code AutoGlowingGeoLayer}.
 */
@OnlyIn(Dist.CLIENT)
public class LayerBeelzebubGland<T extends BeelzebubEntity> extends GeoRenderLayer<T> {
    private static final ResourceLocation GLAND_TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/beelzebub/beelzebub_gland.png");

    public LayerBeelzebubGland(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!animatable.canHarvest()) return;

        RenderType glandRenderType = RenderType.entityCutoutNoCull(GLAND_TEXTURE);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, glandRenderType,
                bufferSource.getBuffer(glandRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}
