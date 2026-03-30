package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.GhoulEntity;
import com.Fishmod.fur.client.model.GhoulModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class GhoulRenderer extends GeoEntityRenderer<GhoulEntity> {
	
    public GhoulRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new GhoulModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(GhoulEntity entity) {
    	return super.getTextureLocation(entity);
    }    
}
