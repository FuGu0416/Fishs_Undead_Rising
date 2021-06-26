package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelLilSludge;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLilSludge extends RenderLiving<EntityLilSludge>{
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/lilsludge/lilsludge.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/lilsludge/lilsludge2.png")
	};

	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderLilSludge(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelLilSludge(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityLilSludge entity) {
		return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityLilSludge entity, float partialTickTime) {
	}
}
