package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.WispEntity;
import com.Fishmod.fur.client.model.WispModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WispRenderer extends GeoEntityRenderer<WispEntity> {	
    public WispRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new WispModel());
        this.shadowRadius = 0.0F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(WispEntity entity) {
    	return super.getTextureLocation(entity);
    }
    
	@Override
	public void preRender(PoseStack poseStack, WispEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

		float swellFactor = animatable.getSwelling(partialTick);
		float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
		swellFactor = (float)Math.pow(Mth.clamp(swellFactor, 0f, 1f), 3);
		float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
		float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;

		poseStack.scale(horizontalSwell, verticalSwell, horizontalSwell);
	}

	@Override
	public int getPackedOverlay(WispEntity animatable, float u, float partialTick) {
		return super.getPackedOverlay(animatable, getSwellOverlay(animatable, u) ,partialTick);
	}

	protected float getSwellOverlay(WispEntity entity, float u) {
		float swell = entity.getSwelling(u);

		return (int) (swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
	}
    
	@Override
    protected int getBlockLightLevel(WispEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
}
