package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.projectiles.DeathCoilEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeathCoilRenderer extends EntityRenderer<DeathCoilEntity> {
	private static final ResourceLocation TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private final SkullModel model;

	public DeathCoilRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SkullModel(context.bakeLayer(ModelLayers.SKELETON_SKULL));
	}

	@Override
	protected int getBlockLightLevel(DeathCoilEntity entity, BlockPos pos) {
		return 15;
	}

	@Override
	public void render(DeathCoilEntity entity, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int light) {
		pose.pushPose();
		pose.scale(-1.0F, -1.0F, 1.0F);
		float f = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
		float f1 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
		VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
		this.model.setupAnim(0.0F, f, f1);
		this.model.renderToBuffer(pose, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pose.popPose();
		super.render(entity, yaw, partialTicks, pose, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(DeathCoilEntity entity) {
		return TEXTURES;
	}
}
