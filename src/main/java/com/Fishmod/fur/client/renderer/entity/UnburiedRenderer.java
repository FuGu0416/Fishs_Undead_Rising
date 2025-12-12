package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Fishmod.fur.client.model.UnburiedModel;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

@OnlyIn(Dist.CLIENT)
public class UnburiedRenderer extends GeoEntityRenderer<UnburiedEntity> {
	// Pre-define our bone names for easy and consistent reference later
	private static final String LEFT_HAND = "handle_r";
	private static final String RIGHT_HAND = "handle_l";
	private static final String LEFT_BOOT = "boot_r";
	private static final String RIGHT_BOOT = "boot_l";
	private static final String LEFT_ARMOR_LEG = "armor_leg_r";
	private static final String RIGHT_ARMOR_LEG = "armor_leg_l";
	private static final String CHESTPLATE = "chestplate";
	private static final String RIGHT_SLEEVE = "sleeve_l";
	private static final String LEFT_SLEEVE = "sleeve_r";
	private static final String HELMET = "helmet";

	protected ItemStack mainHandItem;
	protected ItemStack offhandItem;
	
    public UnburiedRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new UnburiedModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;

		// Add some armor rendering
		addRenderLayer(new ItemArmorGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getArmorItemForBone(GeoBone bone, UnburiedEntity animatable) {
				// Return the items relevant to the bones being rendered for additional rendering
				return switch (bone.getName()) {
					case LEFT_BOOT, RIGHT_BOOT -> this.bootsStack;
					case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG -> this.leggingsStack;
					case CHESTPLATE, RIGHT_SLEEVE, LEFT_SLEEVE -> this.chestplateStack;
					case HELMET -> this.helmetStack;
					default -> null;
				};
			}

			// Return the equipment slot relevant to the bone we're using
			@Nonnull
			@Override
			protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, UnburiedEntity animatable) {
				return switch (bone.getName()) {
					case LEFT_BOOT, RIGHT_BOOT -> EquipmentSlot.FEET;
					case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG -> EquipmentSlot.LEGS;
					case RIGHT_SLEEVE -> !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
					case LEFT_SLEEVE -> animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
					case CHESTPLATE -> EquipmentSlot.CHEST;
					case HELMET -> EquipmentSlot.HEAD;
					default -> super.getEquipmentSlotForBone(bone, stack, animatable);
				};
			}

			// Return the ModelPart responsible for the armor pieces we want to render
			@Nonnull
			@Override
			protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, UnburiedEntity animatable, HumanoidModel<?> baseModel) {
				return switch (bone.getName()) {
					case LEFT_BOOT, LEFT_ARMOR_LEG -> baseModel.leftLeg;
					case RIGHT_BOOT, RIGHT_ARMOR_LEG -> baseModel.rightLeg;
					case RIGHT_SLEEVE -> baseModel.rightArm;
					case LEFT_SLEEVE -> baseModel.leftArm;
					case CHESTPLATE -> baseModel.body;
					case HELMET -> baseModel.head;
					default -> super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
				};
			}
		});

		// Add some held item rendering
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, UnburiedEntity animatable) {
				// Retrieve the items in the entity's hands for the relevant bone
				return switch (bone.getName()) {
					case LEFT_HAND -> animatable.isLeftHanded() ?
							UnburiedRenderer.this.mainHandItem : UnburiedRenderer.this.offhandItem;
					case RIGHT_HAND -> animatable.isLeftHanded() ?
							UnburiedRenderer.this.offhandItem : UnburiedRenderer.this.mainHandItem;
					default -> null;
				};
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, UnburiedEntity animatable) {
				// Apply the camera transform for the given hand
				return switch (bone.getName()) {
					case LEFT_HAND, RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
					default -> ItemDisplayContext.NONE;
				};
			}

			// Do some quick render modifications depending on what the item is
			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, UnburiedEntity animatable,
											  MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				if (stack == UnburiedRenderer.this.mainHandItem) {
					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem)
						poseStack.translate(0, 0.125, -0.25);
				}
				else if (stack == UnburiedRenderer.this.offhandItem) {
					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem) {
						poseStack.translate(0, 0.125, 0.25);
						poseStack.mulPose(Axis.YP.rotationDegrees(180));
					}
				}

				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
    }
    
    @Override
    public ResourceLocation getTextureLocation(UnburiedEntity entity) {
    	return super.getTextureLocation(entity);
    }  
    
	@Override
	public void preRender(PoseStack poseStack, UnburiedEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

		this.mainHandItem = animatable.getMainHandItem();
		this.offhandItem = animatable.getOffhandItem();
	}
}
