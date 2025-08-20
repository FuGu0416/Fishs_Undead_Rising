package com.Fishmod.fur.client.model.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.ScarecrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LightTexture;
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
public class ScarecrowCollarLayer<T extends ScarecrowEntity> extends GeoRenderLayer<T> {
	private static final ResourceLocation SCARF_LOCATION = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/scarecrow/scarecrow_scarf.png");

	public ScarecrowCollarLayer(GeoRenderer<T> renderer) {
		super(renderer);
	}

	/**
	 * This is the method that is actually called by the render for your render layer to function.<br>
	 * This is called <i>after</i> the animatable has been rendered, but before supplementary rendering like nametags.
	 */
	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (!animatable.isInvisible()) {
			RenderType RenderType = net.minecraft.client.renderer.RenderType.entityCutoutNoCull(SCARF_LOCATION);
			float[] afloat = animatable.getCollarColor().getTextureDiffuseColors();		
		
			getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, RenderType,
								   bufferSource.getBuffer(RenderType), partialTick, LightTexture.FULL_SKY, OverlayTexture.NO_OVERLAY,
								   afloat[0], afloat[1], afloat[2], 1);
		}
	}
}
