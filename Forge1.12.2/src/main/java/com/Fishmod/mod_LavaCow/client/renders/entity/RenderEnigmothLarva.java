package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelEnigmothLarva;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityEnigmothLarva;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEnigmothLarva extends RenderLiving<EntityEnigmothLarva> {
	private static final ResourceLocation TEXTURES_EYES = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_larva_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_larva.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_larva1.png")
	};

    public RenderEnigmothLarva(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelEnigmothLarva(), 0.3F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYES));
    }
    
    @Override
	protected float getDeathMaxRotation(EntityEnigmothLarva entity) {
		return 180.0F;
	}
    
    @Override
    protected ResourceLocation getEntityTexture(EntityEnigmothLarva entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityEnigmothLarva entity, float partialTickTime) {
    	
	}
    
    
}
