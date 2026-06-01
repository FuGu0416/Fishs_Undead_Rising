package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.ShroomlingEntity;
import com.Fishmod.fur.client.model.ShroomlingModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ShroomlingRenderer extends GeoEntityRenderer<ShroomlingEntity> {

    public ShroomlingRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new ShroomlingModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public ResourceLocation getTextureLocation(ShroomlingEntity entity) {
    	return super.getTextureLocation(entity);
    }
}
