package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelVespa;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderVespa extends RenderLiving<EntityVespa>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderVespa(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelVespa(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityVespa entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityVespa entity, float partialTickTime) {
    	
	}
}
