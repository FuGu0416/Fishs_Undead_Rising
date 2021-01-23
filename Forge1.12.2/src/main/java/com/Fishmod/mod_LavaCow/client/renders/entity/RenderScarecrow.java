package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelScarecrow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScarecrow extends RenderLiving<EntityScarecrow>{
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

	public RenderScarecrow(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelScarecrow(), 0.5F);
    	this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityScarecrow entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityScarecrow entity, float partialTickTime) {
    	//if(!entity.isChild())GlStateManager.scale(1.8F, 1.8F, 1.8F);
	}
}
