package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUndertaker;
import com.Fishmod.mod_LavaCow.entities.EntityUndertaker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUndertaker extends RenderLiving<EntityUndertaker> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/undertaker/undertaker_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/undertaker/undertaker.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

	public RenderUndertaker(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUndertaker(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.4F, 1.8F));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityUndertaker entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityUndertaker entity, float partialTickTime) {
        GlStateManager.scale(1.33F, 1.33F, 1.33F);
        super.preRenderCallback(entity, partialTickTime);
	}
}
