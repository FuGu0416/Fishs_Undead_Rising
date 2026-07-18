package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SkeletonKingModel;
import com.Fishmod.fur.entities.SkeletonKingEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SkeletonKingRenderer extends GeoEntityRenderer<SkeletonKingEntity> {

    public SkeletonKingRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonKingModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonKingEntity entity) {
        return super.getTextureLocation(entity);
    }
}
