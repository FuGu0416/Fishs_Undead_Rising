package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.AvatonModel;
import com.Fishmod.fur.entities.floating.AvatonEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class AvatonRenderer extends GeoEntityRenderer<AvatonEntity> {
	
    public AvatonRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new AvatonModel());
        this.shadowRadius = 0.0F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(AvatonEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    @Override
    protected int getBlockLightLevel(AvatonEntity p_225624_1_, BlockPos p_225624_2_) {
        return 8;
    }
}
