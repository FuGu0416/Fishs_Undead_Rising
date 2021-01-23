package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelWeta;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityWeta;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWeta extends RenderLiving<EntityWeta>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/weta/weta.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/weta/weta1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderWeta(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelWeta(), 0.0F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityWeta entity) {
    	return TEXTURES[entity.isChild()? 1 : 0];
    }
    
    @Override
	protected void preRenderCallback(EntityWeta entity, float partialTickTime) {
    	if(entity.isChild())GlStateManager.scale(0.5F, 0.5F, 0.5F);
	}
}
