package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelSludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSludgeLord extends RenderLiving<EntitySludgeLord>{
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/sludgelord_glow.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/sludgelord.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderSludgeLord(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelSludgeLord(), 0.5F);
    	this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySludgeLord entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntitySludgeLord entity, float partialTickTime) {
    	//if(!entity.isChild())GlStateManager.scale(1.8F, 1.8F, 1.8F);
	}
}
