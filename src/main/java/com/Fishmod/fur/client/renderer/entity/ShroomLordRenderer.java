package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.ShroomLordModel;
import com.Fishmod.fur.entities.ShroomLordEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class ShroomLordRenderer extends GeoEntityRenderer<ShroomLordEntity> {

    public ShroomLordRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroomLordModel());
        this.shadowRadius = 0.9F;
        // Glowing eyes (shroomlord_glowmask.png). 1.16.5 only lit them at night; the GeckoLib
        // auto-glow layer keeps them lit at all times, which reads fine for a hostile mob.
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShroomLordEntity entity) {
        return super.getTextureLocation(entity);
    }
}
