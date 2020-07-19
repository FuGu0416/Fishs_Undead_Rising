package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelPtera;
import com.Fishmod.mod_LavaCow.entities.flying.EntityPtera;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPtera extends RenderLiving<EntityPtera>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ptera/ptera.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ptera/ptera1.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderPtera(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPtera(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityPtera entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityPtera entity, float partialTickTime) {
    	
	}
}
