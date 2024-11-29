package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.BoneTroutModel;
import com.Fishmod.mod_LavaCow.entities.aquatic.UndeadFishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class BoneTroutRenderer extends MobRenderer<UndeadFishEntity, BoneTroutModel<UndeadFishEntity>> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/fish/bone_trout.png");
	
    public BoneTroutRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BoneTroutModel<UndeadFishEntity>(), 0.3F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(UndeadFishEntity p_110775_1_) {
		return TEXTURES;
	}
    
    @Override
    protected void setupRotations(UndeadFishEntity entityLiving, MatrixStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(f));
              
        if (!entityLiving.isInWaterOrBubble() && !entityLiving.isAggressive()) {
        	p_225621_2_.translate(0.1F, -0.3F, -0.1F);
        	p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }        
     }
}
