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
		
		this.bag = (new ModelRenderer(this)).setTexSize(p_i47227_3_, p_i47227_4_);
		this.bag.setPos(0.0F, 24.0F, 0.0F);
		this.bag.texOffs(64, 0).addBox(-4.0F, -24.0F, 3.0F, 8.0F, 14.0F, 8.0F, 0.0F, false);
		this.bag.texOffs(56, 0).addBox(-2.0F, -27.0F, 5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
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
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	/*if (entityIn instanceof GraveRobberEntity && ((GraveRobberEntity)entityIn).tradeTimer > 0) {
            this.getHead().xRot = 0.5F;
            this.getHead().yRot = 0.0F;
            //this.getArm(HandSide.LEFT).yRot = 0.5F;
            //this.getArm(HandSide.LEFT).xRot = -0.9F;
    	} else {*/
    		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    	//}
    }

}
