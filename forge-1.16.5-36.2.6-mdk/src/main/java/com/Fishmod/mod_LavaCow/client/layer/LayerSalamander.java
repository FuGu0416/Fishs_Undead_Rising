package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.SalamanderModel;
import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
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
public class LayerSalamander<T extends SalamanderEntity, M extends SalamanderModel<T>> extends LayerRenderer<T, M> {
	private static final ResourceLocation[] TEXTURES_GLOW = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_glow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_glow1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser_glow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser_glow1.png")
	};
	
	public LayerSalamander(IEntityRenderer<T, M> p_i226039_1_) {
		super(p_i226039_1_);
	}

	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(this.renderType(p_225628_4_));
		this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
	}

	public RenderType renderType(T p_225628_4_) {
		ResourceLocation texture;
		
		if(p_225628_4_.isBaby()) {
			texture = TEXTURES_GLOW[p_225628_4_.getSkin() + 2];
		} else texture = TEXTURES_GLOW[p_225628_4_.getSkin()];
		
		return RenderType.eyes(texture);
	}
}