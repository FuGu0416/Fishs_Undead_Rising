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
	private static final String LEFT_HAND = "handle_l";
	private static final String RIGHT_HAND = "handle_r";

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

				// Grave Robber is always left-handed (GraveRobberEntity#isLeftHanded), so the main
				// hand item goes on handle_l, not handle_r - same swap UndertakerRenderer does off
				// its own isLeftHanded() check.
				return switch (bone.getName()) {
					case LEFT_HAND -> animatable.isLeftHanded() ?
							GraveRobberRenderer.this.mainHandItem : GraveRobberRenderer.this.offhandItem;
					case RIGHT_HAND -> animatable.isLeftHanded() ?
							GraveRobberRenderer.this.offhandItem : GraveRobberRenderer.this.mainHandItem;
					default -> null;
				};
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, GraveRobberEntity animatable) {
				return switch (bone.getName()) {
					case LEFT_HAND, RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
					default -> ItemDisplayContext.NONE;
				};
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
