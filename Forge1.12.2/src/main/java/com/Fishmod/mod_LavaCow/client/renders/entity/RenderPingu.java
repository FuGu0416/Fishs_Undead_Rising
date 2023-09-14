package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelPingu;
import com.Fishmod.mod_LavaCow.entities.EntityPingu;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPingu extends RenderLiving<EntityPingu>{
	
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/pingu.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderPingu(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPingu(), 0.3F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityPingu entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityPingu entity, float partialTickTime) {
    	if(entity.isAggressive() || entity.isOverWater()) {
    		GlStateManager.rotate(75.0F, 1.0F, 0.0F, 0.0F);
    		GlStateManager.translate(0.0F, 0.0F, 0.25F);
    	}
	}
}
