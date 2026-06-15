package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.floating.WraithEntity;
import com.Fishmod.fur.client.layer.LayerWraith;
import com.Fishmod.fur.client.model.WraithModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WraithRenderer extends GeoEntityRenderer<WraithEntity> {
	
    public WraithRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new WraithModel());
    	this.addRenderLayer(new LayerWraith<>(this));
        this.shadowRadius = 0.0F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(WraithEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    @Override
    protected int getBlockLightLevel(WraithEntity entity, BlockPos pos) {
        return 8;
    }
}
