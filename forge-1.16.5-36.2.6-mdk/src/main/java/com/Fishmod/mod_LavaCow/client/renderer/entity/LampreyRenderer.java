package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.LampreyModel;
import com.Fishmod.mod_LavaCow.entities.aquatic.LampreyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class LampreyRenderer extends MobRenderer<LampreyEntity, LampreyModel<LampreyEntity>> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/lamprey.png")
	};
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public LampreyRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new LampreyModel<LampreyEntity>(), 0.3F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(LampreyEntity entity) {
        return TEXTURES[0];
    }
    
    @Override
	protected void scale(LampreyEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if (entity.getVehicle() != null) {
    		matrixStackIn.scale(1.2F, 1.2F, 1.2F);
			matrixStackIn.translate(0.0F, 0.0F, -0.4F);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			if ((entity.getVehicle() instanceof PlayerEntity || entity.getVehicle() instanceof ZombieEntity || entity.getVehicle() instanceof AbstractVillagerEntity || entity.getVehicle() instanceof AbstractRaiderEntity || entity.getVehicle() instanceof AbstractSkeletonEntity) && !((LivingEntity)entity.getVehicle()).isBaby()) {
				if (!(entity.getVehicle() instanceof PlayerEntity)) {
					matrixStackIn.translate(0.0F, 0.3F, -0.3F);
				} else {
					matrixStackIn.translate(0.0F, 0.5F, -0.3F);
				}
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
				
			}
		}    	
	}
    
    @Override
    protected void setupRotations(LampreyEntity entityLiving, MatrixStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(f));
               
        if (!entityLiving.isInWaterOrBubble() && !entityLiving.isAggressive() && !entityLiving.isPassenger()) {
        	p_225621_2_.translate(0.1F, -0.3F, -0.1F);
        	p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }        
	}
}
