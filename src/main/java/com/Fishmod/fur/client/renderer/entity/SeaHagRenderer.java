package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SeaHagModel;
import com.Fishmod.fur.entities.floating.SeaHagEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SeaHagRenderer extends GeoEntityRenderer<SeaHagEntity> {
	
    public SeaHagRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new SeaHagModel());
        this.shadowRadius = 0.0F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(SeaHagEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    protected int getBlockLightLevel(SeaHagEntity entity, BlockPos pos) {
        return 8;
    }
}
