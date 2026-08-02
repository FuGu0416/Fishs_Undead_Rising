package com.Fishmod.fur.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.fur.client.model.GraveRobberModel;
import com.Fishmod.fur.entities.GraveRobberEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

@OnlyIn(Dist.CLIENT)
public class GraveRobberRenderer extends GeoEntityRenderer<GraveRobberEntity> {
	private static final String RIGHT_ARM = "arm_r";
	private static final String LEFT_ARM = "arm_l";

	protected ItemStack mainHandItem;
	protected ItemStack offhandItem;

	public GraveRobberRenderer(EntityRendererProvider.Context context) {
		super(context, new GraveRobberModel());
		this.shadowRadius = 0.5F;

		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, GraveRobberEntity animatable) {
				// Matches the old ItemInHandLayer gating: crossed-arm idle/walk/dig/celebrate poses
				// never show a held item (nothing to attach it to visually), only the attacking and
				// offer-item (offhand held out) poses do.
				if (!animatable.isAggressive() && animatable.getOffhandItem().isEmpty()) {
					return null;
				}

				return switch (bone.getName()) {
					case RIGHT_ARM -> GraveRobberRenderer.this.mainHandItem;
					case LEFT_ARM -> GraveRobberRenderer.this.offhandItem;
					default -> null;
				};
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, GraveRobberEntity animatable) {
				return switch (bone.getName()) {
					case RIGHT_ARM, LEFT_ARM -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
					default -> ItemDisplayContext.NONE;
				};
			}

			// PLACEHOLDER offset: arm_r/arm_l have no dedicated hand locator (unlike e.g.
			// UndertakerModel's handle_l/handle_r), so this estimates the hand position by walking
			// down the arm bone's own length (pivot sits near the shoulder, cube is 12 units tall) -
			// needs a real visual check once the geo/animation are finished.
			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, GraveRobberEntity animatable,
					MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.translate(0.0D, -0.625D, 0.0D);

				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(GraveRobberEntity entity) {
		return super.getTextureLocation(entity);
	}

	@Override
	public void preRender(PoseStack poseStack, GraveRobberEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource,
			VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

		this.mainHandItem = animatable.getMainHandItem();
		this.offhandItem = animatable.getOffhandItem();
	}
}
