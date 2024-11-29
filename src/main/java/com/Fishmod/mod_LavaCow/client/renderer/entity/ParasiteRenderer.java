package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ParasiteModel;
import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParasiteRenderer extends MobRenderer<ParasiteEntity, ParasiteModel<ParasiteEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
    public ParasiteRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ParasiteModel<ParasiteEntity>(), 0.3F);
    }
    
    @Override
	protected float getFlipDegrees(ParasiteEntity entity) {
		return 180F;
	}
    
    @Override
	public ResourceLocation getTextureLocation(ParasiteEntity entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void scale(ParasiteEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if (entity.getVehicle() != null) {
    		matrixStackIn.scale(1.2F, 1.2F, 1.2F);
			matrixStackIn.translate(0.0F, 0.0F, -0.4F);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			if((entity.getVehicle() instanceof PlayerEntity || entity.getVehicle() instanceof ZombieEntity || entity.getVehicle() instanceof AbstractVillagerEntity || entity.getVehicle() instanceof AbstractRaiderEntity || entity.getVehicle() instanceof AbstractSkeletonEntity) && !((LivingEntity)entity.getVehicle()).isBaby()) {
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
    protected void setupRotations(ParasiteEntity entity, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		if(entity.getAttachedBlock() == Direction.UP) {
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            matrixStackIn.translate(0.0D, -0.25D, 0.0D);
        } else if (entity.getAttachedBlock() != Direction.DOWN) {
            matrixStackIn.translate(0.0D, 0.25D, 0.0D);
            switch (entity.getAttachedBlock()){
                case NORTH:
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(0));
                    break;
                case SOUTH:
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    break;
                case WEST:
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
                    break;
                case EAST:
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                    break;
                default:
                	break;
            } 
            
            if (entity.getDeltaMovement().y > -0.001F) {
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-180.0F));
            }
            
            matrixStackIn.translate(0.0D, -0.25D, 0.0D);
        }
    	
    	super.setupRotations(entity, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}
