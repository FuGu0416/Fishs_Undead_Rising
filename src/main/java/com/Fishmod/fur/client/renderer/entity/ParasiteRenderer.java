package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.ParasiteModel;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
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
        super.applyRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);
    	
        if (entityLiving.getVehicle() != null) {
    		poseStack.scale(1.2F, 1.2F, 1.2F);
			poseStack.translate(0.0F, 0.0F, -0.4F);
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			
			if ((entityLiving.getVehicle() instanceof Player 
					|| entityLiving.getVehicle() instanceof Zombie 
					|| entityLiving.getVehicle() instanceof AbstractVillager 
					|| entityLiving.getVehicle() instanceof AbstractIllager 
					|| entityLiving.getVehicle() instanceof AbstractSkeleton) 
					&& !((LivingEntity)entityLiving.getVehicle()).isBaby()) {
				if (!(entityLiving.getVehicle() instanceof Player)) {
					poseStack.translate(0.0F, 0.3F, -0.3F);
				} else {
					poseStack.translate(0.0F, 0.5F, -0.3F);
				}
				
				poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));				
			}
		} else if(entityLiving.getAttachedBlock() == Direction.UP) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.translate(0.0D, -0.25D, 0.0D);
        } else if (entityLiving.getAttachedBlock() != Direction.DOWN) {
            poseStack.translate(0.0D, 0.25D, 0.0D);
            
            switch (entityLiving.getAttachedBlock()) {
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
            
            poseStack.translate(0.0D, -0.25D, 0.0D);
        }
	}
}
