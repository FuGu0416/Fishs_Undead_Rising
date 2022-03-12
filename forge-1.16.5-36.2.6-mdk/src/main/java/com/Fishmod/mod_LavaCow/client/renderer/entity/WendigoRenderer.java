package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.WendigoModel;
import com.Fishmod.mod_LavaCow.entities.WendigoEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WendigoRenderer extends MobRenderer<WendigoEntity, WendigoModel<WendigoEntity>> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/wendigo_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/wendigo.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

	public WendigoRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new WendigoModel<WendigoEntity>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
	public ResourceLocation getTextureLocation(WendigoEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(WendigoEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(!entity.isBaby()) {
    		matrixStackIn.scale(1.5F, 1.5F, 1.5F);
    	}
	}
}
