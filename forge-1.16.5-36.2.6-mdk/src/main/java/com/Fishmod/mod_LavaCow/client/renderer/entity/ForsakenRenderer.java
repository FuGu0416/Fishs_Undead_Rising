package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerForsakenArmor;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForsakenRenderer extends SkeletonRenderer {
    private static final ResourceLocation TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public ForsakenRenderer(EntityRendererManager p_i47191_1_)
    {
        super(p_i47191_1_);
        this.addLayer(new LayerForsakenArmor<>(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeletonEntity entity)
    {
        return TEXTURES;
    }
}
