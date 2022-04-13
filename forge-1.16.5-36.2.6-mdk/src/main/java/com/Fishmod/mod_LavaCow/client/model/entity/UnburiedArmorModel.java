package com.Fishmod.mod_LavaCow.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class UnburiedArmorModel<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Body_chest;
    public ModelRenderer Neck0;
    public ModelRenderer Neck1;
    public ModelRenderer Head;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_r_Seg1;
    
	public UnburiedArmorModel(float modelSize) {
		super(modelSize, 0.0F, 64, 32);
		
        this.Body_base = new ModelRenderer(this, 0, 0);
        this.Body_base.setPos(0.0F, 10.0F, 2.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -2.0F, 0, 0, 0, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 0);
        this.Body_waist.setPos(0.0F, -3.2F, 0.0F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Body_waist, 0.4553564018453205F, 0.0F, -0.045553093477052F);
        this.Body_chest = new ModelRenderer(this, 0, 0);
        this.Body_chest.setPos(0.0F, -8.0F, 0.0F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Body_chest, 0.5009094953223726F, 0.0F, -0.091106186954104F);
        this.Neck0 = new ModelRenderer(this, 0, 0);
        this.Neck0.setPos(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck0, 0.36425021489121656F, 0.0F, 0.0F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setPos(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck1, -1.2747884856566583F, -0.22759093446006054F, -0.18203784098300857F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 6, 8, 0.0F);
        
        this.Leg_r_Seg0 = new ModelRenderer(this, 0, 0);
        this.Leg_r_Seg0.setPos(-1.9F, 0.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402793711257F, 0.0F, 0.091106186954104F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 0, 0);
        this.Leg_r_Seg1.setPos(0.0F, 8.0F, -1.0F);
        this.Leg_r_Seg1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 0, 0);
        this.Leg_l_Seg0.setPos(1.9F, 0.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402793711257F, 0.0F, -0.091106186954104F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 0, 0);
        this.Leg_l_Seg1.setPos(0.0F, 8.0F, -1.0F);
        this.Leg_l_Seg1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -3.0F, -8.0F, 8, 8, 8, modelSize);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.hat = new ModelRenderer(this, 32, 0);
        this.hat.addBox(-4.0F, -3.0F, -8.0F, 8, 8, 8, modelSize + 0.5F);
        this.hat.setPos(0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 16);
        this.leftLeg = new ModelRenderer(this, 0, 16);
        this.leftLeg.mirror = true;
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-4.0F, -3.0F, -2.0F, 8, 8, 4, modelSize + 0.5F);
        this.body.setPos(0.0F, 0.0F, 0.0F);

        
		this.Body_base.addChild(this.Body_waist);
		this.Body_waist.addChild(this.Body_chest);
        this.Body_chest.addChild(this.Neck0);
        this.Neck0.addChild(this.Neck1);
        this.Neck1.addChild(this.Head);
        this.Head.addChild(this.head);
        this.Head.addChild(this.hat);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Body_chest.addChild(this.body);
        
        if(modelSize != 1.02F) {
	        this.Leg_r_Seg0.addChild(this.rightLeg);
	        this.Leg_l_Seg0.addChild(this.leftLeg);
	        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize - 0.45F);
	        this.rightLeg.setPos(0.0F, -1.0F, 0.0F);
	        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize - 0.45F);
	        this.leftLeg.setPos(0.0F, -1.0F, 0.0F);
        } else {
	        this.Leg_r_Seg1.addChild(this.rightLeg);
	        this.Leg_l_Seg1.addChild(this.leftLeg);   
	        this.rightLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 12, 4, modelSize);
	        this.rightLeg.setPos(0.0F, 0.0F, 1.0F);
	        this.leftLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 12, 4, modelSize);
	        this.leftLeg.setPos(0.0F, 0.0F, 1.0F);
        }
	}
	
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
	protected void SwingX_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip) {
		Point.xRot = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks)); 
	}
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	Head.xRot = headPitch * 0.017453292F;
    	Head.yRot = netHeadYaw * 0.017453292F;
        
    	this.Head.y = 0.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Body_chest.y = -8.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.3F * (float)Math.PI)); 
    	
    	this.Body_base.z = 2.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.Body_base.y = 10.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.Body_base.zRot = 0.0F + (limbSwingAmount * 0.1F * MathHelper.sin(0.6F * limbSwing)); 
    	
    	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, true);
    	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, false);
    	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing + 0.3F * (float)Math.PI, limbSwingAmount * 0.4F, 0.6F, false);
    	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing + 0.3F * (float)Math.PI, limbSwingAmount * 0.4F, 0.6F, true);
    }
}
