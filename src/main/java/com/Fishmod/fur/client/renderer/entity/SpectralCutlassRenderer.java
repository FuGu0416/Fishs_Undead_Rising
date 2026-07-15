package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SpectralCutlassModel;
import com.Fishmod.fur.entities.tameable.SpectralCutlassEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

/**
 * GeckoLib renderer for {@link SpectralCutlassEntity}. The geometry ({@code spectral_cutlass.geo.json}) is
 * a bare rig whose {@code base} bone carries the idle / dash / attack animation; the actual visible blade
 * is the cutlass <em>item</em>, rendered onto that bone via {@link BlockAndItemGeoLayer} so it moves exactly
 * with the bone. The item transform is {@link ItemDisplayContext#NONE} (bone animation drives placement),
 * with an extra +90° yaw so the blade faces edge-on rather than flat.
 */
@OnlyIn(Dist.CLIENT)
public class SpectralCutlassRenderer extends GeoEntityRenderer<SpectralCutlassEntity> {

	public SpectralCutlassRenderer(EntityRendererProvider.Context context) {
		super(context, new SpectralCutlassModel());
		this.shadowRadius = 0.0F;

		this.addRenderLayer(new BlockAndItemGeoLayer<SpectralCutlassEntity>(this) {
			@Override
			protected ItemStack getStackForBone(GeoBone bone, SpectralCutlassEntity animatable) {
				return "base".equals(bone.getName()) ? animatable.getDisplayStack() : null;
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, SpectralCutlassEntity animatable) {
				return ItemDisplayContext.NONE;
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, SpectralCutlassEntity animatable,
											  MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
				// Render the blade emissive (full-bright), so it glows regardless of world light.
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, LightTexture.FULL_BRIGHT, packedOverlay);
			}
		});
	}
}
