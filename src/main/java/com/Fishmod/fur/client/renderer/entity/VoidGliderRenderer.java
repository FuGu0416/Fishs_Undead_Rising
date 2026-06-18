package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.VoidGliderEntity;
import com.Fishmod.fur.client.model.VoidGliderModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class VoidGliderRenderer extends GeoEntityRenderer<VoidGliderEntity> {

    public VoidGliderRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new VoidGliderModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(VoidGliderEntity entity) {
    	return super.getTextureLocation(entity);
    }
}
