package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.ScarabModel;
import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class ScarabRenderer extends GeoEntityRenderer<ScarabEntity> {
	
    public ScarabRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new ScarabModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(ScarabEntity entity) {
    	return super.getTextureLocation(entity);
    }   
    
    @Override
    protected void applyRotations(ScarabEntity entity, PoseStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
    	p_225621_2_.scale(0.75F, 0.75F, 0.75F);  	
	}
}
