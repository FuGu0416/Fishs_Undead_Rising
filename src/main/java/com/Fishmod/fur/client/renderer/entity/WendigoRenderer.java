package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.WendigoEntity;
import com.Fishmod.fur.client.model.WendigoModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class WendigoRenderer extends GeoEntityRenderer<WendigoEntity> {
	
    public WendigoRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new WendigoModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(WendigoEntity entity) {
    	return super.getTextureLocation(entity);
    }    
}
