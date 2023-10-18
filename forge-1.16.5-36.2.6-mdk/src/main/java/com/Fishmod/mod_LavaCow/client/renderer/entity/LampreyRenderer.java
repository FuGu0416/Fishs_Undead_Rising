package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.LampreyModel;
import com.Fishmod.mod_LavaCow.entities.aquatic.LampreyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
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
    protected void setupRotations(LampreyEntity entityLiving, MatrixStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(f));
               
        if (!entityLiving.isInWaterOrBubble() && !entityLiving.isAggressive()) {
        	p_225621_2_.translate(0.1F, -0.3F, -0.1F);
        	p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }        
	}
}
