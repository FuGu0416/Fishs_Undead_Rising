package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.CadavoarEntity;
import com.Fishmod.fur.client.model.CadavoarModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class CadavoarRenderer extends GeoEntityRenderer<CadavoarEntity> {

    public CadavoarRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new CadavoarModel());
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(CadavoarEntity entity) {
    	return super.getTextureLocation(entity);
    }
}
