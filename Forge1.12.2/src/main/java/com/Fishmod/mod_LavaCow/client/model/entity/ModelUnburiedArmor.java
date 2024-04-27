package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelUnburiedArmor extends ModelBiped {
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
    
	public ModelUnburiedArmor(float modelSize) {
		super(modelSize, 0.0F, 64, 32);
		
        this.Body_base = new ModelRenderer(this, 0, 0);
        this.Body_base.setRotationPoint(0.0F, 10.0F, 2.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -2.0F, 0, 0, 0, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 0);
        this.Body_waist.setRotationPoint(0.0F, -3.2F, 0.0F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Body_waist, 0.4553564018453205F, 0.0F, -0.045553093477052F);
        this.Body_chest = new ModelRenderer(this, 0, 0);
        this.Body_chest.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Body_chest, 0.5009094953223726F, 0.0F, -0.091106186954104F);
        this.Neck0 = new ModelRenderer(this, 0, 0);
        this.Neck0.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck0, 0.36425021489121656F, 0.0F, 0.0F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setRotationPoint(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck1, -1.2747884856566583F, -0.22759093446006054F, -0.18203784098300857F);
        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 6, 8, 0.0F);
        
        this.Leg_r_Seg0 = new ModelRenderer(this, 0, 0);
        this.Leg_r_Seg0.setRotationPoint(-1.9F, 0.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402793711257F, 0.0F, 0.091106186954104F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 0, 0);
        this.Leg_r_Seg1.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Leg_r_Seg1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 0, 0);
        this.Leg_l_Seg0.setRotationPoint(1.9F, 0.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402793711257F, 0.0F, -0.091106186954104F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 0, 0);
        this.Leg_l_Seg1.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Leg_l_Seg1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -3.0F, -8.0F, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -3.0F, -8.0F, 8, 8, 8, modelSize + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, -3.0F, -2.0F, 8, 8, 4, modelSize + 0.5F);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);

        
		this.Body_base.addChild(this.Body_waist);
		this.Body_waist.addChild(this.Body_chest);
        this.Body_chest.addChild(this.Neck0);
        this.Neck0.addChild(this.Neck1);
        this.Neck1.addChild(this.Head);
        this.Head.addChild(this.bipedHead);
        this.Head.addChild(this.bipedHeadwear);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Body_chest.addChild(this.bipedBody);
        
        if(modelSize != 1.0F) {
	        this.Leg_r_Seg0.addChild(this.bipedRightLeg);
	        this.Leg_l_Seg0.addChild(this.bipedLeftLeg);
	        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize - 0.45F);
	        this.bipedRightLeg.setRotationPoint(0.0F, -1.0F, 0.0F);
	        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize - 0.45F);
	        this.bipedLeftLeg.setRotationPoint(0.0F, -1.0F, 0.0F);
        }
        else {
	        this.Leg_r_Seg1.addChild(this.bipedRightLeg);
	        this.Leg_l_Seg1.addChild(this.bipedLeftLeg);   
	        this.bipedRightLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedRightLeg.setRotationPoint(0.0F, 0.0F, 1.0F);
	        this.bipedLeftLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedLeftLeg.setRotationPoint(0.0F, 0.0F, 1.0F);
        }
	}
	
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
    	this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    	this.Body_base.render(scale); 
    }
    
	protected void SwingX_Sin(ModelRenderer Point, float Init, float Ticks, float Amplitude, float Frequency, boolean Flip) {
		Point.rotateAngleX = Init + (Flip ? -1 : 1) * (Amplitude * MathHelper.sin(Frequency * Ticks)); 
	}
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	Head.rotateAngleX = headPitch * 0.017453292F;
    	Head.rotateAngleY = netHeadYaw * 0.017453292F;
        
    	this.Head.rotationPointY = 0.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Body_chest.rotationPointY = -8.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.3F * (float)Math.PI)); 
    	
    	this.Body_base.rotationPointZ = 2.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.Body_base.rotationPointY = 10.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.Body_base.rotateAngleZ = 0.0F + (limbSwingAmount * 0.1F * MathHelper.sin(0.6F * limbSwing)); 
    	
    	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, true);
    	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, false);
    	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing + 0.3F * (float)Math.PI, limbSwingAmount * 0.4F, 0.6F, false);
    	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing + 0.3F * (float)Math.PI, limbSwingAmount * 0.4F, 0.6F, true);
    }
}
