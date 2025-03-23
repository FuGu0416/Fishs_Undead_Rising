package com.Fishmod.fur.client.renderer.entity;

import org.jetbrains.annotations.NotNull;
import com.Fishmod.fur.client.model.FogletModel;
import com.Fishmod.fur.entities.FogletEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class FogletRenderer extends GeoEntityRenderer<FogletEntity> {
    public FogletRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new FogletModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5F;
    }    

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FogletEntity entity) {
        return super.getTextureLocation(entity);
    }

    @Override
    public float getMotionAnimThreshold(FogletEntity animatable) {
        return 0.0005f;
    }
}
