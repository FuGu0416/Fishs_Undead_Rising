package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerEnigmothSaddle;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEnigmoth extends RenderLiving<EntityEnigmoth> {
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth.png");
	private static final ResourceLocation TEXTURES_EYES = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_eyes.png");
	
	static {
		System.out.println(TEXTURES.getResourcePath());
    }

    public RenderEnigmoth(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelEnigmoth(), 0.5F);
        this.addLayer(new LayerEnigmothSaddle(this));
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYES));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityEnigmoth entity) {
    	return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityEnigmoth entity, float partialTickTime) {
    	
	}
    
    
}
