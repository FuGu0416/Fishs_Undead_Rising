package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerForsakenArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.ForsakenModel;
import com.Fishmod.mod_LavaCow.entities.ForsakenEntity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForsakenRenderer extends BipedRenderer<ForsakenEntity, ForsakenModel<ForsakenEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public ForsakenRenderer(EntityRendererManager p_i47191_1_) {
        super(p_i47191_1_, new ForsakenModel<>(), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new ForsakenModel<>(0.5F, true), new ForsakenModel<>(1.0F, true)));
        this.addLayer(new LayerForsakenArmor<>(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
	public ResourceLocation getTextureLocation(ForsakenEntity entity) {
        return TEXTURES;
    }
}
