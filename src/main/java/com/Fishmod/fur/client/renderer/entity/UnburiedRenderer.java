package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.client.model.UnburiedModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class UnburiedRenderer extends GeoEntityRenderer<UnburiedEntity> {
	
    public UnburiedRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new UnburiedModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(UnburiedEntity entity) {
    	return super.getTextureLocation(entity);
    }    
}
