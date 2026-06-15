package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.FURModelLayers;
import com.Fishmod.fur.client.model.GraveRobberModel;
import com.Fishmod.fur.entities.GraveRobberEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraveRobberRenderer extends IllagerRenderer<GraveRobberEntity> {
	private static final ResourceLocation GRAVEROBBER = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/graverobber/graverobber.png");

	public GraveRobberRenderer(EntityRendererProvider.Context context) {
		super(context, new GraveRobberModel<>(context.bakeLayer(FURModelLayers.GRAVEROBBER)), 0.5F);
		this.addLayer(new ItemInHandLayer<GraveRobberEntity, IllagerModel<GraveRobberEntity>>(this, context.getItemInHandRenderer()) {
			public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, GraveRobberEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				if (entity.isAggressive() || !entity.getOffhandItem().isEmpty()) {
					super.render(poseStack, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
				}
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(GraveRobberEntity entity) {
		return GRAVEROBBER;
	}
}
