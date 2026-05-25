package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.MummyLordModel;
import com.Fishmod.fur.entities.MummyLordEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class MummyLordRenderer extends GeoEntityRenderer<MummyLordEntity> {

    public MummyLordRenderer(EntityRendererProvider.Context context) {
        super(context, new MummyLordModel());
        this.shadowRadius = 0.5F;
    }

    @Override
    public ResourceLocation getTextureLocation(MummyLordEntity entity) {
        return super.getTextureLocation(entity);
    }
}
