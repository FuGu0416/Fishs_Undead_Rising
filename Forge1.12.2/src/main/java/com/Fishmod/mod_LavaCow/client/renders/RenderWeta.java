package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelWeta;
import com.Fishmod.mod_LavaCow.entities.EntityWeta;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWeta extends RenderLiving<EntityWeta>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/weta.png"),
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
    	return TEXTURES[0];
    }
    
    @Override
	protected void preRenderCallback(EntityWeta entity, float partialTickTime) {

	}
}
