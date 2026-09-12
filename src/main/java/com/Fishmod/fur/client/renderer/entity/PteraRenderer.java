package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.PteraModel;
import com.Fishmod.fur.entities.flying.PteraEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PteraRenderer extends FlyingMobRenderer<PteraEntity> {
	
    public PteraRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new PteraModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(PteraEntity entity) {
    	return super.getTextureLocation(entity);
    }    
}
