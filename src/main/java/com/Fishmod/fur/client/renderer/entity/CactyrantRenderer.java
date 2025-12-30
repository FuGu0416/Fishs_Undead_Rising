package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.client.model.CactyrantModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CactyrantRenderer extends GeoEntityRenderer<CactyrantEntity> {
	
    public CactyrantRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new CactyrantModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(CactyrantEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    @Override
	public boolean isShaking(CactyrantEntity p_116561_) {
    	return super.isShaking(p_116561_) || p_116561_.isShaking();
	}
}
