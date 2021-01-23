package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelGhostRay;
import com.Fishmod.mod_LavaCow.entities.flying.EntityGhostRay;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGhostRay extends RenderLiving<EntityGhostRay>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ghostray.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderGhostRay(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGhostRay(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityGhostRay entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityGhostRay entity, float partialTickTime) {
    	if(!entity.isChild())GlStateManager.scale(EntityGhostRay.SIZE[entity.getSize()], EntityGhostRay.SIZE[entity.getSize()], EntityGhostRay.SIZE[entity.getSize()]);
	}
}
