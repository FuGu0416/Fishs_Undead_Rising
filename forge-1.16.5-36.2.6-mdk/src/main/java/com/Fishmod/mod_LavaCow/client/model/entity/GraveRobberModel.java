package com.Fishmod.mod_LavaCow.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;

public class GraveRobberModel<T extends AbstractIllagerEntity> extends IllagerModel<T> {
	private final ModelRenderer bag;
	
	public GraveRobberModel(float p_i47227_1_, float p_i47227_2_, int p_i47227_3_, int p_i47227_4_) {
		super(p_i47227_1_, p_i47227_2_, p_i47227_3_, p_i47227_4_);
		
		bag = (new ModelRenderer(this)).setTexSize(p_i47227_3_, p_i47227_4_);
		bag.setPos(0.0F, 24.0F, 0.0F);
		bag.texOffs(64, 0).addBox(-4.0F, -24.0F, 3.0F, 8.0F, 14.0F, 8.0F, 0.0F, false);
		bag.texOffs(56, 0).addBox(-2.0F, -27.0F, 5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
	}
	
	public Iterable<ModelRenderer> parts() {
		return super.parts();
	}
	
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	this.parts().forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
        ImmutableList.of(this.bag).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

}
