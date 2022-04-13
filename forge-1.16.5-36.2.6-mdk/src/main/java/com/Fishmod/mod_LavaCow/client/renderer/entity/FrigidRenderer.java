package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.model.entity.UnburiedModel;
import com.Fishmod.mod_LavaCow.entities.FrigidEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrigidRenderer extends MobRenderer<FrigidEntity, UnburiedModel<FrigidEntity>> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied1_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied1.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public FrigidRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new UnburiedModel<FrigidEntity>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.6F, 1.0F));
        //this.addLayer(new BipedArmorLayer(this, new UnburiedArmorModel<>(0.5F), new UnburiedArmorModel<>(1.02F)));
    }
    
    @Override
	public ResourceLocation getTextureLocation(FrigidEntity entity) {
        return TEXTURES;
    }
    
    @Override
    protected void scale(FrigidEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(entity.isBaby()) {
    		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    	}
    }
}
