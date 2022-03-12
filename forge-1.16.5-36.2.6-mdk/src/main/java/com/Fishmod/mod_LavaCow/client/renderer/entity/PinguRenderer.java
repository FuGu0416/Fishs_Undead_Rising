package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.PinguModel;
import com.Fishmod.mod_LavaCow.entities.PinguEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class PinguRenderer extends MobRenderer<PinguEntity, PinguModel<PinguEntity>> {
	
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/pingu.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public PinguRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new PinguModel<>(), 0.3F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(PinguEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(PinguEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(entity.isAggressive() || entity.isInWater()) {
    		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(75.0F));
    		matrixStackIn.translate(0.0F, 0.0F, 0.25F);
    	}
	}
    
    @Nullable
    @Override
    protected RenderType getRenderType(PinguEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
