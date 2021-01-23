package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelWendigo;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWendigo extends RenderLiving<EntityWendigo>{
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/wendigo_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/wendigo.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

	public RenderWendigo(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelWendigo(), 0.5F);
        this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityWendigo entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityWendigo entity, float partialTickTime) {
    	if(!entity.isChild())GlStateManager.scale(1.5F, 1.5F, 1.5F);
	}
}
