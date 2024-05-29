package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelAvaton;
import com.Fishmod.mod_LavaCow.entities.floating.EntitySeaHag;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSeaHag extends RenderLiving<EntitySeaHag>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/sea_hag.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderSeaHag(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAvaton(), 0.0F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySeaHag entity) {
    	return TEXTURES[0];
    }
    
    @Override
	protected void preRenderCallback(EntitySeaHag entity, float partialTickTime) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 0.85F);
	}
}
