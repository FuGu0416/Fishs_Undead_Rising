package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.BansheeModel;
import com.Fishmod.fur.entities.floating.BansheeEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class BansheeRenderer extends GeoEntityRenderer<BansheeEntity> {
	
    public BansheeRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new BansheeModel());
        this.shadowRadius = 0.0F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(BansheeEntity entity) {
    	return super.getTextureLocation(entity);
    }    
}
