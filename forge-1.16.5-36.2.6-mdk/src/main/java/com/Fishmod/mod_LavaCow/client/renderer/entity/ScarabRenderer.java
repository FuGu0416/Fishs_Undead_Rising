package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ScarabModel;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarabEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ScarabRenderer extends MobRenderer<ScarabEntity, ScarabModel<ScarabEntity>> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/scarab.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public ScarabRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ScarabModel<ScarabEntity>(), 0.5F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(ScarabEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(ScarabEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(!entity.isBaby()) {
    		matrixStackIn.scale(0.75F, 0.75F, 0.75F);
    	}
	}
}
