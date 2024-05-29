package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelSludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntityAmberLord;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAmberLord extends RenderLiving<EntityAmberLord> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/amberlord/amberlord.png")
	};
	private static final ResourceLocation TEXTURES_EYES = new ResourceLocation("mod_lavacow:textures/mobs/amberlord/amberlord_glow.png");
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderAmberLord(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelSludgeLord(), 1.0F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYES));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityAmberLord entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityAmberLord entity, float partialTickTime) {
	}
}
