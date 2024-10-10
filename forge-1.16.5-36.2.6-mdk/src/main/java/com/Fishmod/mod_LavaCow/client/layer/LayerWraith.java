package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.AvatonModel;
import com.Fishmod.mod_LavaCow.entities.floating.WraithEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerWraith<T extends WraithEntity, M extends AvatonModel<T>> extends LayerRenderer<T, M> {
	private final ResourceLocation textureLocation;
	private final M model;

	public LayerWraith(IEntityRenderer<T, M> p_i232478_1_, M p_i232478_2_, ResourceLocation p_i232478_3_) {
		super(p_i232478_1_);
		this.model = p_i232478_2_;
		this.textureLocation = p_i232478_3_;
	}

	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		this.getParentModel().copyPropertiesTo(this.model);
		this.model.prepareMobModel(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
		this.model.setupAnim(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
		IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucent(this.textureLocation));
		this.model.renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, p_225628_4_.getFadeIn(p_225628_8_));
	}
}