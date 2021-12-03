package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerSalamanderSaddle;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelSalamander;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSalamander extends RenderLiving<EntitySalamander>{
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_glow.png");	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderSalamander(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSalamander(), 0.5F);
        this.addLayer(new LayerSalamanderSaddle(this));
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySalamander entity) {
        if(!entity.isNymph())
        	return TEXTURES[0];
        else
        	return TEXTURES[1];
    }
    
    @Override
	protected void preRenderCallback(EntitySalamander entity, float partialTickTime) { 	
    	switch (entity.getGrowingStage()) {
			case 0:
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				break;
			case 1:
				GlStateManager.scale(0.8F, 0.8F, 0.8F);
				break;
			case 2:
				GlStateManager.scale(1.25F, 1.25F, 1.25F);
				break;
			default:
				GlStateManager.scale(1.5F, 1.5F, 1.5F);
				break;   			
		}
	}
}
