package com.Fishmod.fur.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerGenericGlowing<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private RenderType SPIDER_EYES = null;

    public LayerGenericGlowing(RenderLayerParent<T, M> parent, ResourceLocation textureIn) {
        super(parent);
        this.SPIDER_EYES = RenderType.eyes(textureIn);
    }
    
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.level().isDay() || entity.level().dimensionType().hasCeiling() || !entity.level().canSeeSky(entity.blockPosition())) {
        	VertexConsumer ivertexbuilder = buffer.getBuffer(this.renderType());
	        this.getParentModel().renderToBuffer(poseStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
	}
          
    @Override
    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
