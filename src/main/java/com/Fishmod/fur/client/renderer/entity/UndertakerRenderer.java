package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.UndertakerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import javax.annotation.Nullable;

import com.Fishmod.fur.client.model.UndertakerModel;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
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

@OnlyIn(Dist.CLIENT)
public class UndertakerRenderer extends GeoEntityRenderer<UndertakerEntity> {
	private static final String LEFT_HAND = "handle_l";
	private static final String RIGHT_HAND = "handle_r";
	
	protected ItemStack mainHandItem;
	protected ItemStack offhandItem;
	
    public UndertakerRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new UndertakerModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
        
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
 			@Nullable
 			@Override
 			protected ItemStack getStackForBone(GeoBone bone, UndertakerEntity animatable) {
 				// Retrieve the items in the entity's hands for the relevant bone
 				return switch (bone.getName()) {
 					case LEFT_HAND -> animatable.isLeftHanded() ?
 							UndertakerRenderer.this.mainHandItem : UndertakerRenderer.this.offhandItem;
 					case RIGHT_HAND -> animatable.isLeftHanded() ?
 							UndertakerRenderer.this.offhandItem : UndertakerRenderer.this.mainHandItem;
 					default -> null;
 				};
 			}

 			@Override
 			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, UndertakerEntity animatable) {
 				// Apply the camera transform for the given hand
 				return switch (bone.getName()) {
 					case LEFT_HAND, RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
 					default -> ItemDisplayContext.NONE;
 				};
 			}

 			// Do some quick render modifications depending on what the item is
 			@Override
 			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, UndertakerEntity animatable,
 											  MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
 				if (stack == UndertakerRenderer.this.mainHandItem) {
 					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

 					if (stack.getItem() instanceof ShieldItem)
 						poseStack.translate(0, 0.125, -0.25);
 					
 					poseStack.scale(1.5F, 1.5F, 1.5F);
 					poseStack.translate(0.0F, -0.15F, 0.0F);
 					
 				} else if (stack == UndertakerRenderer.this.offhandItem) {
 					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

 					if (stack.getItem() instanceof ShieldItem) {
 						poseStack.translate(0, 0.125, 0.25);
 						poseStack.mulPose(Axis.YP.rotationDegrees(180));
 					}
 					
 					poseStack.scale(1.33F, 1.33F, 1.33F);
 					poseStack.translate(0.0F, 0.15F, 0.0F);
 				}

 				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
 			}
 		});
    }
    
    @Override
    public ResourceLocation getTextureLocation(UndertakerEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
	@Override
	public void preRender(PoseStack poseStack, UndertakerEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

		this.mainHandItem = animatable.getMainHandItem();
		this.offhandItem = animatable.getOffhandItem();
	}
	
    @Override
    protected void applyRotations(UndertakerEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
		poseStack.scale(1.5F, 1.5F, 1.5F);
	}   	
}
