package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.WetaModel;
import com.Fishmod.mod_LavaCow.entities.tameable.WetaEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WetaRenderer extends MobRenderer<WetaEntity, WetaModel<WetaEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/weta/weta.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/weta/weta1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public WetaRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new WetaModel<>(), 0.0F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(WetaEntity entity) {
    	return TEXTURES[entity.isBaby()? 1 : 0];
    }
    
    @Override
    protected void scale(WetaEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(entity.isBaby()) {
    		matrixStackIn.scale(0.5F, 0.5F, 0.5F);
    	}
	}
}
