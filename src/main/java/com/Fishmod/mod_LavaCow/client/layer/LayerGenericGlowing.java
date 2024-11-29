package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!SpawnUtil.isDay(p_225628_4_.level) || p_225628_4_.level.dimensionType().hasCeiling() || !p_225628_4_.level.canSeeSky(p_225628_4_.blockPosition())) {
	    	IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(this.renderType());
	        this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
	}
          
    @Override
    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
