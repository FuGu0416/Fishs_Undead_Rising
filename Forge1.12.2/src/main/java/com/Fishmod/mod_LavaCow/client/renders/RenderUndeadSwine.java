package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelUndeadSwine;
import com.Fishmod.mod_LavaCow.entities.EntityUndeadSwine;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUndeadSwine extends RenderLiving<EntityUndeadSwine>{
	
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/undeadswine.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderUndeadSwine(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUndeadSwine(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityUndeadSwine entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityUndeadSwine entity, float partialTickTime) {
    	if(!entity.isChild())GlStateManager.scale(1.8F, 1.8F, 1.8F);
	}
}
