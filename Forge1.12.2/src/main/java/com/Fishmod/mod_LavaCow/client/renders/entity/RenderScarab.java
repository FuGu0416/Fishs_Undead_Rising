package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelScarab;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarab;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScarab extends RenderLiving<EntityScarab> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/scarab/scarab.png")
	};
	private static final ResourceLocation TEXTURES_EYES = new ResourceLocation("mod_lavacow:textures/mobs/scarab/scarab_glow.png");
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderScarab(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelScarab(), 0.5F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYES));
    }
    
    @Override
	protected float getDeathMaxRotation(EntityScarab entity) {
		return 180.0F;
	}
    
    @Override
    protected ResourceLocation getEntityTexture(EntityScarab entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityScarab entity, float partialTickTime) {
    	if(!entity.isChild()) {
    		GlStateManager.scale(0.75F, 0.75F, 0.75F);
    	}
	}
}
