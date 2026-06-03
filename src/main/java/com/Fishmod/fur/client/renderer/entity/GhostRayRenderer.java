package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.GhostRayEntity;
import com.Fishmod.fur.client.model.GhostRayModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class GhostRayRenderer extends GeoEntityRenderer<GhostRayEntity> {

    public GhostRayRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new GhostRayModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(GhostRayEntity entity) {
    	return super.getTextureLocation(entity);
    }
}
