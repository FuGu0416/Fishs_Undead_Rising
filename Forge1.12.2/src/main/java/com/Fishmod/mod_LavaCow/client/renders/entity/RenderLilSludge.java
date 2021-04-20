package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelLilSludge;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLilSludge extends RenderLiving<EntityLilSludge>{
	
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/lilsludge.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderLilSludge(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelLilSludge(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityLilSludge entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityLilSludge entity, float partialTickTime) {
    	//if(!entity.isChild())GlStateManager.scale(1.8F, 1.8F, 1.8F);
	}
}
