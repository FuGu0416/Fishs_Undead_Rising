package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.CactyrantModel;
import com.Fishmod.mod_LavaCow.entities.CactyrantEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CactyrantRenderer extends MobRenderer<CactyrantEntity, CactyrantModel<CactyrantEntity>> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant.png");
	private static final ResourceLocation TEXTURES_CAMO = new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant_camo.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

	public CactyrantRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CactyrantModel<>(), 0.5F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(CactyrantEntity entity) {
        return entity.isCamouflaging() ? TEXTURES_CAMO : TEXTURES;
    }
    
    @Override
	protected void scale(CactyrantEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
	}
}
