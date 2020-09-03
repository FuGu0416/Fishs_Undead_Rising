package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelAvadon;
import com.Fishmod.mod_LavaCow.entities.EntityAvadon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAvadon extends RenderLiving<EntityAvadon>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/avadon.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderAvadon(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelAvadon(), 0.0F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityAvadon entity) {
    	return TEXTURES[0];
    }
    
    @Override
	protected void preRenderCallback(EntityAvadon entity, float partialTickTime) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 0.85F);
	}
}
