package com.Fishmod.mod_LavaCow.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class FishModelBase extends ModelBase {
	
	protected void Head_Looking(ModelRenderer Head, float initX, float initY, float netHeadYaw, float headPitch) {
    	Head.rotateAngleX = initX + headPitch * 0.017453292F;
        Head.rotateAngleY = initY + netHeadYaw * 0.017453292F;
	}
	
	protected void SwingX_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.rotateAngleX = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
	}
	
	protected void SwingY_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.rotateAngleY = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
	}
	
	protected void SwingZ_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip, float delay) {
		Point.rotateAngleZ = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks + delay)); 
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
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void postRenderArm(float scale, EnumHandSide side) {
    	
    }

    protected ModelRenderer getArmForSide(EnumHandSide side) {
        return null;
    }

}
