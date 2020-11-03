package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityUndertaker;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelUndertaker - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelUndertaker extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Leg_r_Seg1;
    public ModelRenderer Leg_r_Seg2;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_l_Seg2;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Neck;
    public ModelRenderer Spine0;
    public ModelRenderer Spine1;
    public ModelRenderer Spine2;
    public ModelRenderer Spine3;
    public ModelRenderer Spine4;
    public ModelRenderer Spine5;
    public ModelRenderer Spine6;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_l_Seg2;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Arm_r_Seg2;
    public ModelRenderer Head;
    public ModelRenderer Head_wear;
    public ModelRenderer Jaw;
    public ModelRenderer Tooth_l;
    public ModelRenderer Tooth_r;

    public ModelUndertaker() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Leg_r_Seg0 = new ModelRenderer(this, 68, 0);
        this.Leg_r_Seg0.setRotationPoint(-4.0F, -1.2F, 0.1F);
        this.Leg_r_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.40980330836826856F, 0.0F, 0.0F);
        this.Spine4 = new ModelRenderer(this, 0, 0);
        this.Spine4.setRotationPoint(-1.3F, -6.5F, 4.5F);
        this.Spine4.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine4, -1.6845917940249266F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 68, 0);
        this.Leg_l_Seg0.mirror = true;
        this.Leg_l_Seg0.setRotationPoint(4.0F, -1.2F, 0.1F);
        this.Leg_l_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.40980330836826856F, 0.0F, 0.0F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 74, 11);
        this.Leg_l_Seg1.mirror = true;
        this.Leg_l_Seg1.setRotationPoint(0.0F, 6.0F, -2.0F);
        this.Leg_l_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Tooth_l = new ModelRenderer(this, 0, 0);
        this.Tooth_l.setRotationPoint(3.4F, 0.0F, -7.4F);
        this.Tooth_l.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.Head_wear = new ModelRenderer(this, 32, 0);
        this.Head_wear.setRotationPoint(0.0F, -4.3F, -1.2F);
        this.Head_wear.addBox(-4.5F, -4.5F, -8.5F, 9, 10, 9, 0.0F);
        this.setRotateAngle(Head_wear, -1.9577358219620393F, 0.0F, 0.0F);
        this.Spine0 = new ModelRenderer(this, 0, 0);
        this.Spine0.setRotationPoint(-0.5F, -11.5F, -1.6F);
        this.Spine0.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine0, -0.091106186954104F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 32, 48);
        this.Arm_r_Seg0.setRotationPoint(-8.0F, -7.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-6.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, -0.5918411493512771F, 0.0F, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 32, 48);
        this.Arm_l_Seg0.mirror = true;
        this.Arm_l_Seg0.setRotationPoint(8.0F, -7.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.5918411493512771F, 0.0F, 0.0F);
        this.Tooth_r = new ModelRenderer(this, 0, 0);
        this.Tooth_r.setRotationPoint(-3.4F, 0.0F, -7.4F);
        this.Tooth_r.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 49, 31);
        this.Arm_r_Seg1.setRotationPoint(-3.0F, 2.5F, 0.0F);
        this.Arm_r_Seg1.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, 0.0F, -0.18203784098300857F, 0.10000736613927509F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -4.3F, -1.2F);
        this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 6, 8, 0.0F);
        this.setRotateAngle(Head, -1.9577358219620393F, 0.0F, 0.0F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 74, 11);
        this.Leg_r_Seg1.setRotationPoint(0.0F, 6.0F, -2.0F);
        this.Leg_r_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Spine2 = new ModelRenderer(this, 0, 0);
        this.Spine2.setRotationPoint(-0.2F, -11.5F, 3.1F);
        this.Spine2.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine2, -0.091106186954104F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 52);
        this.Body_base.setRotationPoint(0.0F, 12.0F, 1.3F);
        this.Body_base.addBox(-6.0F, -8.0F, -2.0F, 12, 8, 4, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 28);
        this.Body_chest.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Body_chest.addBox(-8.0F, -12.0F, -3.0F, 16, 12, 8, 0.0F);
        this.setRotateAngle(Body_chest, 0.4553564018453205F, 0.0F, -0.045553093477052F);
        this.Neck = new ModelRenderer(this, 0, 16);
        this.Neck.setRotationPoint(0.0F, -7.0F, -1.5F);
        this.Neck.addBox(-4.5F, -5.0F, -3.0F, 9, 5, 6, 0.0F);
        this.setRotateAngle(Neck, 1.7756979809790308F, 0.0F, 0.0F);
        this.Spine5 = new ModelRenderer(this, 0, 0);
        this.Spine5.setRotationPoint(-0.6F, -3.9F, 4.5F);
        this.Spine5.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine5, -1.6845917940249266F, 0.0F, 0.0F);
        this.Spine1 = new ModelRenderer(this, 0, 0);
        this.Spine1.setRotationPoint(-0.3F, -11.8F, 0.6F);
        this.Spine1.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine1, -0.091106186954104F, 0.0F, 0.0F);
        this.Leg_l_Seg2 = new ModelRenderer(this, 72, 20);
        this.Leg_l_Seg2.mirror = true;
        this.Leg_l_Seg2.setRotationPoint(0.0F, 3.9F, 1.8F);
        this.Leg_l_Seg2.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.setRotateAngle(Leg_l_Seg2, -0.18203784098300857F, 0.0F, 0.0F);
        this.Arm_r_Seg2 = new ModelRenderer(this, 69, 31);
        this.Arm_r_Seg2.setRotationPoint(0.0F, 12.0F, 2.5F);
        this.Arm_r_Seg2.addBox(-2.5F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.setRotateAngle(Arm_r_Seg2, -0.9560913642424937F, 0.0F, 0.0F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 89, 31);
        this.Arm_l_Seg1.setRotationPoint(3.0F, 2.5F, 0.0F);
        this.Arm_l_Seg1.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, 0.0F, 0.18203784098300857F, -0.10000736613927509F);
        this.Arm_l_Seg2 = new ModelRenderer(this, 69, 31);
        this.Arm_l_Seg2.mirror = true;
        this.Arm_l_Seg2.setRotationPoint(0.0F, 12.0F, 2.5F);
        this.Arm_l_Seg2.addBox(-2.5F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.setRotateAngle(Arm_l_Seg2, -0.9560913642424937F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 84, 0);
        this.Jaw.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Jaw.addBox(-4.0F, -1.0F, -8.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.40980330836826856F, 0.0F, 0.18203784098300857F);
        this.Leg_r_Seg2 = new ModelRenderer(this, 72, 20);
        this.Leg_r_Seg2.setRotationPoint(0.0F, 3.9F, 1.8F);
        this.Leg_r_Seg2.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.setRotateAngle(Leg_r_Seg2, -0.18203784098300857F, 0.0F, 0.0F);
        this.Spine6 = new ModelRenderer(this, 0, 0);
        this.Spine6.setRotationPoint(-0.6F, -1.4F, 4.0F);
        this.Spine6.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine6, -1.6845917940249266F, 0.0F, 0.0F);
        this.Spine3 = new ModelRenderer(this, 0, 0);
        this.Spine3.setRotationPoint(-0.6F, -9.5F, 4.5F);
        this.Spine3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Spine3, -1.6845917940249266F, 0.0F, 0.0F);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Body_chest.addChild(this.Spine4);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Jaw.addChild(this.Tooth_l);
        this.Neck.addChild(this.Head_wear);
        this.Body_chest.addChild(this.Spine0);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Jaw.addChild(this.Tooth_r);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Neck.addChild(this.Head);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Body_chest.addChild(this.Spine2);
        this.Body_base.addChild(this.Body_chest);
        this.Body_chest.addChild(this.Neck);
        this.Body_chest.addChild(this.Spine5);
        this.Body_chest.addChild(this.Spine1);
        this.Leg_l_Seg1.addChild(this.Leg_l_Seg2);
        this.Arm_r_Seg1.addChild(this.Arm_r_Seg2);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Arm_l_Seg1.addChild(this.Arm_l_Seg2);
        this.Head.addChild(this.Jaw);
        this.Leg_r_Seg1.addChild(this.Leg_r_Seg2);
        this.Body_chest.addChild(this.Spine6);
        this.Body_chest.addChild(this.Spine3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5);
    }
    
    public void postRenderArm(float scale, EnumHandSide side)
    {
        //this.getArmForSide(side).postRender(scale);
    	this.Body_base.postRender(scale);
    	this.Body_chest.postRender(scale);
    	this.Arm_l_Seg0.postRender(scale);
    	this.Arm_l_Seg1.postRender(scale);
    	this.Arm_l_Seg2.postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.Arm_l_Seg2 : this.Arm_r_Seg2;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	this.Head_Looking(this.Neck, 1.7756979809790308F, 0.0F, netHeadYaw, headPitch);
    	this.SwingX_Sin(this.Jaw, 0.40980330836826856F, ageInTicks, -0.07F, 0.03F, false, 0.0F);
    	this.SwingX_Sin(this.Leg_r_Seg0, -0.40980330836826856F, limbSwing, limbSwingAmount * 0.7F, 0.3F, true, 0.0F);
    	this.SwingX_Sin(this.Leg_l_Seg0, -0.40980330836826856F, limbSwing, limbSwingAmount * 0.7F, 0.3F, false, 0.0F);
    	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, false, 0.3F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, true, 0.3F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg_r_Seg2, -0.18203784098300857F, limbSwing, limbSwingAmount * 0.2F, 0.3F, true, 0.5F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg_l_Seg2, -0.18203784098300857F, limbSwing, limbSwingAmount * 0.2F, 0.3F, false, 0.5F * (float)Math.PI);
 	
    	this.SwingX_Sin(this.Arm_r_Seg2, -0.9560913642424937F, ageInTicks, -0.07F, 0.03F, false, 0.5F * (float)Math.PI); 
    	this.SwingX_Sin(this.Arm_l_Seg2, -0.9560913642424937F, ageInTicks, -0.07F, 0.03F, false, 0.5F * (float)Math.PI); 
    	
    	this.Arm_r_Seg0.rotationPointY = -7.0F + (-0.55F  * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Arm_l_Seg0.rotationPointY = -7.0F + (-0.55F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float i = (float)((EntityUndertaker) entityIn).getAttackTimer();
    	float j = (float)((EntityUndertaker) entityIn).getSpellTicks() / 30.0F;
    	if(j > 0.0F) {
    		this.Body_chest.rotateAngleX = GradientAnimation(0.4553564018453205F, -0.136659280431156F, j);
    		this.Arm_r_Seg0.rotateAngleX = GradientAnimation(-0.5918411493512771F, -1.730144887501979F, j);
    	}
    	else if(i > 8.0F) {
    		this.Arm_l_Seg0.rotateAngleX = GradientAnimation(-1.8668041679331349F, -2.321986036853256F, (i - 9.0F)/6.0F);
    		this.Arm_r_Seg0.rotateAngleX = GradientAnimation(-1.8668041679331349F, -2.321986036853256F, (i - 9.0F)/6.0F);
    		
    		this.Body_chest.rotateAngleX = GradientAnimation(0.4553564018453205F, -0.6373942428283291F, (i - 9.0F)/6.0F);
    	}
    	else if(i > 0.0F) {
    		this.Arm_l_Seg0.rotateAngleX = GradientAnimation(-2.321986036853256F, -0.8651597102135892F, i/9.0F);
    		this.Arm_l_Seg0.rotateAngleY = GradientAnimation(0.0F, 0.5918411493512771F, i/9.0F);
    		this.Arm_l_Seg0.rotateAngleZ = GradientAnimation(-0.6373942428283291F, -0.31869712141416456F, i/9.0F);
    		this.Arm_r_Seg0.rotateAngleX = GradientAnimation(-2.321986036853256F, -0.8651597102135892F, i/9.0F);
    		this.Arm_r_Seg0.rotateAngleY = GradientAnimation(0.0F, -0.5918411493512771F, i/9.0F);
    		this.Arm_r_Seg0.rotateAngleZ = GradientAnimation(0.6373942428283291F, 0.31869712141416456F, i/9.0F);
    		
    		this.Arm_l_Seg1.rotateAngleX = GradientAnimation(0.0F, -0.27314402793711257F, i/9.0F);
    		this.Arm_r_Seg1.rotateAngleX = GradientAnimation(0.0F, -0.27314402793711257F, i/9.0F);
    		
    		this.Arm_l_Seg2.rotateAngleX = GradientAnimation(-0.9560913642424937F, -0.27314402793711257F, i/9.0F);
    		this.Arm_r_Seg2.rotateAngleX = GradientAnimation(-0.9560913642424937F, -0.27314402793711257F, i/9.0F);
    		
    		this.Body_chest.rotateAngleX = GradientAnimation(-0.6373942428283291F, 0.5918411493512771F, i/9.0F);
    	}
    	else {
    		this.setRotateAngle(Arm_l_Seg0, -0.5918411493512771F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_r_Seg0, -0.5918411493512771F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_l_Seg1, 0.0F, 0.18203784098300857F, -0.10000736613927509F);
    		this.setRotateAngle(Arm_r_Seg1, 0.0F, -0.18203784098300857F, 0.10000736613927509F);
    		this.setRotateAngle(Arm_l_Seg2, -0.9560913642424937F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_r_Seg2, -0.9560913642424937F, 0.0F, 0.0F);
    		
    		this.Body_chest.rotateAngleX = 0.4553564018453205F;

    	}
    }
}
