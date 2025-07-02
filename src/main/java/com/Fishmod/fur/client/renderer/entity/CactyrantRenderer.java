package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.client.model.CactyrantModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
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
    
    protected int getBlockLightLevel(CactyrantEntity p_225624_1_, BlockPos p_225624_2_) {
        return 8;
    }
}
