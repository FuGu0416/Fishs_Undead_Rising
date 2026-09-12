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
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

@OnlyIn(Dist.CLIENT)
public class GraveRobberRenderer extends GeoEntityRenderer<GraveRobberEntity> {
	private static final String RIGHT_HAND = "handle_r";
	private static final String HANDLE_FOLD = "handle_fold";

	/** Cosmetic-only prop for the dig2 pose - the robber's actual equipped item never changes while
	 *  looting-gesturing (see TombLootFlavorGoal), so this is drawn in place of the real mainhand item. */
	private static final ItemStack BRUSH_STACK = new ItemStack(Items.BRUSH);

	protected ItemStack mainHandItem;
	protected ItemStack offhandItem;

	public GraveRobberRenderer(EntityRendererProvider.Context context) {
		super(context, new GraveRobberModel());
		this.shadowRadius = 0.5F;

		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, GraveRobberEntity animatable) {
				// Each pose uses its own dedicated bone regardless of handedness - unlike the old
				// vanilla-IllagerModel rendering this replaced, the new rig doesn't mirror handle_l/
				// handle_r off isLeftHanded(), so that swap (still used by e.g. UndertakerRenderer) no
				// longer applies here; isLeftHanded()/getArmPose() are effectively vestigial now that
				// nothing reads them for rendering.
				return switch (bone.getName()) {
					case RIGHT_HAND -> {
						if (animatable.isAggressive()) {
							yield GraveRobberRenderer.this.mainHandItem;
						} else if (animatable.isLootingGesture()) {
							yield BRUSH_STACK;
						} else {
							yield null;
						}
					}
					case HANDLE_FOLD -> {
						if (animatable.isUsingItem()) {
							// RetreatAndHealGoal swaps the healing potion into the mainhand slot for
							// the duration of the drink, so mainHandItem already is the potion here.
							yield GraveRobberRenderer.this.mainHandItem;
						} else if (animatable.isOfferGesture()) {
							yield GraveRobberRenderer.this.offhandItem;
						} else {
							yield null;
						}
					}
					default -> null;
				};
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, GraveRobberEntity animatable) {
				return switch (bone.getName()) {
					case RIGHT_HAND, HANDLE_FOLD -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
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
