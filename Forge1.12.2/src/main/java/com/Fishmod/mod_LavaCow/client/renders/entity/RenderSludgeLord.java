package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelSludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSludgeLord extends RenderLiving<EntitySludgeLord> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord.png")
	};
	private static final ResourceLocation TEXTURES_EYES = new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow.png");
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderSludgeLord(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelSludgeLord(), 1.0F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYES));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySludgeLord entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntitySludgeLord entity, float partialTickTime) {
	}
}
