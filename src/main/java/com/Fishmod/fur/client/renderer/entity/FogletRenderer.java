package com.Fishmod.fur.client.renderer.entity;

import org.jetbrains.annotations.NotNull;
import com.Fishmod.fur.client.model.FogletModel;
import com.Fishmod.fur.entities.FogletEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class FogletRenderer extends GeoEntityRenderer<FogletEntity> {
	//private int currentTick = -1;
	
    public FogletRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new FogletModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }    

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FogletEntity entity) {
        return super.getTextureLocation(entity);
    }
    
	@Override
	public void renderFinal(PoseStack poseStack, FogletEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {		
		/*if (animatable.getSkin() == 2 && animatable.isSpellcastingC() && (this.currentTick < 0 || this.currentTick != animatable.tickCount)) {
			this.currentTick = animatable.tickCount;
			
			// Find the earbone and use it as the point of reference
			this.model.getBone("arm_r").ifPresent(arm_r -> {
				Vector3d armPos = arm_r.getWorldPosition();

				animatable.getCommandSenderWorld().addParticle(ParticleTypes.FLAME,
						armPos.x() + 0.05D * arm_r.getRotationVector().x,
						armPos.y() + 0.36D * arm_r.getRotationVector().y,
						armPos.z() + 0.05D * arm_r.getRotationVector().z,
						0.0D,
						0.0D,
						0.0D);
			});
			
			this.model.getBone("arm_l").ifPresent(arm_l -> {
				Vector3d armPos = arm_l.getWorldPosition();

				animatable.getCommandSenderWorld().addParticle(ParticleTypes.FLAME,
						armPos.x() + 0.05D * arm_l.getRotationVector().x,
						armPos.y() + 0.36D * arm_l.getRotationVector().y,
						armPos.z() + 0.05D * arm_l.getRotationVector().z,
						0.0D,
						0.0D,
						0.0D);
			});
		}*/

		super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	/**
	 * Applies rotation transformations to the renderer prior to render time to account for various entity states
	 */
	protected void applyRotations(FogletEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
		if (animatable.getIsHanging()) {
			poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
			poseStack.translate(0.0D, -1.2D, 0.0D);
		}
		
		super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
	}

    @Override
    public float getMotionAnimThreshold(FogletEntity animatable) {
        return 0.0005f;
    }
}
