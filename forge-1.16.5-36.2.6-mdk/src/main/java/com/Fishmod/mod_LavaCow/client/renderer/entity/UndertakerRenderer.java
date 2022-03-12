package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.model.entity.UndertakerModel;
import com.Fishmod.mod_LavaCow.entities.UndertakerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class UndertakerRenderer extends MobRenderer<UndertakerEntity, UndertakerModel<UndertakerEntity>> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/undertaker/undertaker_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/undertaker/undertaker.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

	public UndertakerRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new UndertakerModel<>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.4F, 1.8F));
    }
    
    @Override
    public ResourceLocation getTextureLocation(UndertakerEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(UndertakerEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(!entity.isBaby()) {
    		matrixStackIn.scale(1.33F, 1.33F, 1.33F);
    	}
	}
}
