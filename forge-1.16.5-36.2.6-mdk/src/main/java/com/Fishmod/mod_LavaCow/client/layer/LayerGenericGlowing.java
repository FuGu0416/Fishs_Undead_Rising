package com.Fishmod.mod_LavaCow.client.layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerGenericGlowing<T extends Entity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private RenderType SPIDER_EYES = null;

    public LayerGenericGlowing(IEntityRenderer<T, M> p_i50921_1_, ResourceLocation textureIn) {
        super(p_i50921_1_);
        this.SPIDER_EYES = RenderType.eyes(textureIn);
    }
          
    @Override
    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
