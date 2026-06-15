package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.client.model.CactoidModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CactoidRenderer extends GeoEntityRenderer<CactoidEntity> {
	
    public CactoidRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new CactoidModel());
        this.shadowRadius = 0.25F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(CactoidEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    @Override
	public boolean isShaking(CactoidEntity entity) {
    	return super.isShaking(entity) || entity.isShaking();
	}
}
