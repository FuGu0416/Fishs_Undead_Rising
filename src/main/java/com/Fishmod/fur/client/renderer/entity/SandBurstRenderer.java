package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.projectiles.SandBurstEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Renders the Sand Tomb eruption with the vanilla Evoker Fangs model (like 1.16.5); the sandy
 * look comes from the block particles the entity itself emits.
 */
@OnlyIn(Dist.CLIENT)
public class SandBurstRenderer extends EntityRenderer<SandBurstEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
    private final EvokerFangsModel<SandBurstEntity> model;

    public SandBurstRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new EvokerFangsModel<>(context.bakeLayer(ModelLayers.EVOKER_FANGS));
    }

    @Override
    public void render(SandBurstEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float f = entity.getAnimationProgress(partialTicks);
        if (f != 0.0F) {
            float f1 = 2.0F;
            if (f > 0.9F) {
                f1 = (float) ((double) f1 * ((1.0D - (double) f) / (double) 0.1F));
            }

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
            poseStack.scale(-f1, -f1, f1);
            poseStack.translate(0.0D, (double) -0.626F, 0.0D);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setupAnim(entity, f, 0.0F, 0.0F, entity.getYRot(), entity.getXRot());
            VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SandBurstEntity entity) {
        return TEXTURE_LOCATION;
    }
}
