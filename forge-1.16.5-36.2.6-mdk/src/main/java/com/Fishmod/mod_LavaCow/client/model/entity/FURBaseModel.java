package com.Fishmod.mod_LavaCow.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FURBaseModel<T extends Entity> extends EntityModel<T> {
	
	protected void Head_Looking(ModelRenderer Head, float initX, float initY, float netHeadYaw, float headPitch) {
    	Head.xRot = initX + headPitch * ((float)Math.PI / 180F);;
        Head.yRot = initY + netHeadYaw * ((float)Math.PI / 180F);;
	}
	
	protected void SwingX_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.xRot = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
	}
	
	protected void SwingY_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.yRot = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
	}
	
	protected void SwingZ_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.zRot = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
	}
	
    protected float GradientAnimation(float AnimStart, float AnimEnd, float TickIn) {
    	float y = (float) (1.0D / Math.pow(1.2D * TickIn + 1.0D, 4.5D));
    	return AnimStart * (1.0F - y) + AnimEnd * y;
    }

    protected float GradientAnimation_s(float AnimStart, float AnimEnd, float TickIn) {
    	return AnimStart * MathHelper.sin((float)Math.PI * 0.5F * TickIn) + AnimEnd * (1.0F - MathHelper.sin((float)Math.PI * 0.5F * TickIn));
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
    
    public void translateToHand(HandSide side, MatrixStack p_225599_2_) {
        this.getArm(side).translateAndRotate(p_225599_2_);
    }

     protected ModelRenderer getArm(HandSide side) {
        return null;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {		
	}
}
