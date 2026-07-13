package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.ParasiteModel;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ParasiteRenderer extends GeoEntityRenderer<ParasiteEntity> {	

    public ParasiteRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new ParasiteModel());
        this.shadowRadius = 0.3F;
    }
    
    @Override
	public ResourceLocation getTextureLocation(ParasiteEntity entity) {
    	return super.getTextureLocation(entity);
    }
    
    @Override
    protected void applyRotations(ParasiteEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        Direction attached = entityLiving.getAttachedBlock();

        // Wall-hug offset. An 0.8-wide AABB can't overlap the wall block, so the entity centre is pinned
        // ~half a width (0.4) off the wall face and nothing on the entity side can close that gap. Push
        // the model toward the attached face by half its width, in WORLD space and BEFORE the body-yaw,
        // so the belly plane (model Y=0) meets the wall regardless of yaw or climb direction. Replaces the
        // old implicit 0.25 nudge in the yaw-rotated frame, which under-reached and flipped with motion.
        if (!entityLiving.isPassenger() && attached.getAxis().isHorizontal()) {
            float hug = entityLiving.getBbWidth() * 0.5F;
            poseStack.translate(attached.getStepX() * hug, 0.0D, attached.getStepZ() * hug);
        }

        super.applyRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);

        if (entityLiving.isPassenger()) {
    		poseStack.scale(1.2F, 1.2F, 1.2F);

			if (!(entityLiving.getVehicle() instanceof Player
					|| entityLiving.getVehicle() instanceof Zombie
					|| entityLiving.getVehicle() instanceof AbstractVillager
					|| entityLiving.getVehicle() instanceof AbstractIllager
					|| entityLiving.getVehicle() instanceof AbstractSkeleton)) {
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			}
		} else if (attached == Direction.UP) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.translate(0.0D, -0.25D, 0.0D);
        } else if (attached != Direction.DOWN) {
            poseStack.translate(0.0D, 0.25D, 0.0D);

            switch (attached) {
                case NORTH:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    break;
                case SOUTH:
                    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    break;
                case WEST:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                    break;
                case EAST:
                    poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                    break;
                default:
                	break;
            }

            if (entityLiving.getDeltaMovement().y > -0.001F) {
                poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            }
            // (old implicit `translate(0, -0.25, 0)` wall nudge removed — the world-space hug above now
            //  sets the wall distance deterministically.)
        }
	}
}
