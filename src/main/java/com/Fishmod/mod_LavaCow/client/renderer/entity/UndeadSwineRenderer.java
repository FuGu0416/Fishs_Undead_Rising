package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.UndeadSwineModel;
import com.Fishmod.mod_LavaCow.entities.UndeadSwineEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class UndeadSwineRenderer extends MobRenderer<UndeadSwineEntity, UndeadSwineModel<UndeadSwineEntity>> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/undeadswine.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public UndeadSwineRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new UndeadSwineModel<UndeadSwineEntity>(), 0.5F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(UndeadSwineEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(UndeadSwineEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(!entity.isBaby()) {
    		matrixStackIn.scale(1.8F, 1.8F, 1.8F);
    	}
	}
}
