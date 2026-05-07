package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.LayerSaddle;
import com.Fishmod.fur.client.model.VespaModel;
import com.Fishmod.fur.entities.flying.VespaEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class VespaRenderer extends GeoEntityRenderer<VespaEntity> {

    public VespaRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new VespaModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new LayerSaddle<>(this, new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa_saddle.png")));
    }
}
