package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelVespa - Fish0416
 * Created using Tabula 7.0.1
 */
public class ModelVespa extends ModelFlyingBase {
    public ModelRenderer Throax_base;
    public ModelRenderer Head;
    public ModelRenderer Throax_0;
    public ModelRenderer Wing_0_r;
    public ModelRenderer Wing_0_l;
    public ModelRenderer Wing_1_r;
    public ModelRenderer Wing_1_l;
    public ModelRenderer leg_r_0;
    public ModelRenderer leg_r_1;
    public ModelRenderer leg_r_2;
    public ModelRenderer leg_l_0;
    public ModelRenderer leg_l_1;
    public ModelRenderer leg_l_2;
    public ModelRenderer Waist;
    public ModelRenderer Cheek_r;
    public ModelRenderer Cheek_l;
    public ModelRenderer Eye_r;
    public ModelRenderer Eye_l;
    public ModelRenderer Mouth;
    public ModelRenderer Antennae_r;
    public ModelRenderer Antennae_l;
    public ModelRenderer UAbdomen1;
    public ModelRenderer UAbdomen2;
    public ModelRenderer UAbdomen3;
    public ModelRenderer UAbdomen4;
    public ModelRenderer Stinger;
	private final ModelRenderer saddle;
	private final ModelRenderer saddle_top;
	
    public ModelVespa() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Waist = new ModelRenderer(this, 100, 50);
        this.Waist.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.Waist.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 8, 0.0F);
        this.leg_r_2 = new ModelRenderer(this, 56, 0);
        this.leg_r_2.setRotationPoint(-4.0F, 6.0F, -2.5F);
        this.leg_r_2.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_r_2, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
        this.Mouth = new ModelRenderer(this, 24, 22);
        this.Mouth.setRotationPoint(0.0F, 2.0F, -12.0F);
        this.Mouth.addBox(-2.0F, -3.0F, -2.0F, 4, 6, 2, 0.0F);
        this.leg_l_2 = new ModelRenderer(this, 56, 0);
        this.leg_l_2.setRotationPoint(4.0F, 6.0F, -2.5F);
        this.leg_l_2.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_l_2, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 2.0F, -8.0F);
        this.Head.addBox(-4.0F, -4.0F, -12.0F, 8, 8, 12, 0.0F);
        this.Eye_l = new ModelRenderer(this, 8, 22);
        this.Eye_l.setRotationPoint(4.0F, -1.0F, -10.0F);
        this.Eye_l.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.Wing_1_l = new ModelRenderer(this, 0, 30);
        this.Wing_1_l.mirror = true;
        this.Wing_1_l.setRotationPoint(6.0F, -4.0F, -5.0F);
        this.Wing_1_l.addBox(0.0F, 0.0F, -1.0F, 16, 0, 6, 0.0F);
        this.setRotateAngle(this.Wing_1_l, 0.0F, -0.40980330836826856F, 0.0F);
        this.Wing_0_l = new ModelRenderer(this, 0, 36);
        this.Wing_0_l.mirror = true;
        this.Wing_0_l.setRotationPoint(6.0F, -4.0F, -8.0F);
        this.Wing_0_l.addBox(0.0F, 0.0F, -1.0F, 24, 0, 8, 0.0F);
        this.setRotateAngle(this.Wing_0_l, 0.0F, 0.22759093446006054F, 0.0F);
        this.Antennae_r = new ModelRenderer(this, 0, 22);
        this.Antennae_r.setRotationPoint(-2.5F, -3.0F, -10.0F);
        this.Antennae_r.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(this.Antennae_r, -0.27314402793711257F, 0.0F, 0.0F);
        this.Cheek_l = new ModelRenderer(this, 40, 0);
        this.Cheek_l.mirror = true;
        this.Cheek_l.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.Cheek_l.addBox(0.0F, -2.0F, -4.0F, 4, 6, 8, 0.0F);
        this.Cheek_r = new ModelRenderer(this, 40, 0);
        this.Cheek_r.setRotationPoint(-4.0F, 0.0F, -4.0F);
        this.Cheek_r.addBox(-4.0F, -2.0F, -4.0F, 4, 6, 8, 0.0F);
        this.UAbdomen2 = new ModelRenderer(this, 88, 0);
        this.UAbdomen2.setRotationPoint(0.0F, 2.0F, 4.0F);
        this.UAbdomen2.addBox(-6.0F, -8.0F, 0.0F, 12, 12, 8, 0.0F);
        this.setRotateAngle(this.UAbdomen2, -0.22759093446006054F, 0.0F, 0.0F);
        this.Eye_r = new ModelRenderer(this, 8, 22);
        this.Eye_r.setRotationPoint(-4.0F, -1.0F, -10.0F);
        this.Eye_r.addBox(-4.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.Throax_base = new ModelRenderer(this, 38, 14);
        this.Throax_base.mirror = true;
        this.Throax_base.setRotationPoint(0.0F, 12.0F, 7.5F);
        this.Throax_base.addBox(-6.0F, -6.0F, -4.0F, 12, 12, 8, 0.0F);
        this.Stinger = new ModelRenderer(this, 72, 8);
        this.Stinger.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.Stinger.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 12, 0.0F);
        this.leg_l_1 = new ModelRenderer(this, 56, 0);
        this.leg_l_1.setRotationPoint(4.0F, 6.0F, -3.5F);
        this.leg_l_1.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_l_1, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
        this.UAbdomen3 = new ModelRenderer(this, 100, 28);
        this.UAbdomen3.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.UAbdomen3.addBox(-4.0F, -6.0F, 0.0F, 8, 8, 6, 0.0F);
        this.setRotateAngle(this.UAbdomen3, -0.22759093446006054F, 0.0F, 0.0F);
        this.leg_r_0 = new ModelRenderer(this, 56, 0);
        this.leg_r_0.setRotationPoint(-4.0F, 6.0F, -4.5F);
        this.leg_r_0.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_r_0, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.Wing_0_r = new ModelRenderer(this, 0, 36);
        this.Wing_0_r.setRotationPoint(-6.0F, -4.0F, -8.0F);
        this.Wing_0_r.addBox(-24.0F, 0.0F, -1.0F, 24, 0, 8, 0.0F);
        this.setRotateAngle(this.Wing_0_r, 0.0F, -0.22759093446006054F, 0.0F);
        this.Antennae_l = new ModelRenderer(this, 0, 22);
        this.Antennae_l.setRotationPoint(2.5F, -3.0F, -10.0F);
        this.Antennae_l.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(this.Antennae_l, -0.27314402793711257F, 0.0F, 0.0F);
        this.UAbdomen4 = new ModelRenderer(this, 84, 28);
        this.UAbdomen4.setRotationPoint(0.0F, -1.0F, 5.0F);
        this.UAbdomen4.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(this.UAbdomen4, -0.22759093446006054F, 0.0F, 0.0F);
        this.Throax_0 = new ModelRenderer(this, 0, 44);
        this.Throax_0.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Throax_0.addBox(-6.5F, -6.0F, -4.0F, 13, 12, 8, 0.0F);
        this.setRotateAngle(this.Throax_0, 0.27314402793711257F, 0.0F, 0.0F);
        this.leg_r_1 = new ModelRenderer(this, 56, 0);
        this.leg_r_1.setRotationPoint(-4.0F, 6.0F, -3.5F);
        this.leg_r_1.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_r_1, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
        this.UAbdomen1 = new ModelRenderer(this, 66, 48);
        this.UAbdomen1.setRotationPoint(0.0F, 1.0F, 5.0F);
        this.UAbdomen1.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 6, 0.0F);
        this.setRotateAngle(this.UAbdomen1, 0.22759093446006054F, 0.0F, 0.0F);
        this.Wing_1_r = new ModelRenderer(this, 0, 30);
        this.Wing_1_r.setRotationPoint(-6.0F, -4.0F, -5.0F);
        this.Wing_1_r.addBox(-16.0F, 0.0F, -1.0F, 16, 0, 6, 0.0F);
        this.setRotateAngle(this.Wing_1_r, 0.0F, 0.40980330836826856F, 0.0F);
        this.leg_l_0 = new ModelRenderer(this, 56, 0);
        this.leg_l_0.setRotationPoint(4.0F, 6.0F, -4.5F);
        this.leg_l_0.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(this.leg_l_0, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
        this.Throax_base.addChild(this.Waist);
        this.Throax_base.addChild(this.leg_r_2);
        this.Head.addChild(this.Mouth);
        this.Throax_base.addChild(this.leg_l_2);
        this.Throax_base.addChild(this.Head);
        this.Head.addChild(this.Eye_l);
        this.Throax_base.addChild(this.Wing_1_l);
        this.Throax_base.addChild(this.Wing_0_l);
        this.Head.addChild(this.Antennae_r);
        this.Head.addChild(this.Cheek_l);
        this.Head.addChild(this.Cheek_r);
        this.UAbdomen1.addChild(this.UAbdomen2);
        this.Head.addChild(this.Eye_r);
        this.UAbdomen4.addChild(this.Stinger);
        this.Throax_base.addChild(this.leg_l_1);
        this.UAbdomen2.addChild(this.UAbdomen3);
        this.Throax_base.addChild(this.leg_r_0);
        this.Throax_base.addChild(this.Wing_0_r);
        this.Head.addChild(this.Antennae_l);
        this.UAbdomen3.addChild(this.UAbdomen4);
        this.Throax_base.addChild(this.Throax_0);
        this.Throax_base.addChild(this.leg_r_1);
        this.Waist.addChild(this.UAbdomen1);
        this.Throax_base.addChild(this.Wing_1_r);
        this.Throax_base.addChild(this.leg_l_0);
        
        this.saddle = new ModelRenderer(this);
        this.saddle.setRotationPoint(0.0F, 12.0F, 1.5F);
        this.Throax_0.addChild(this.saddle);
        this.saddle.setTextureOffset(34, 44).addBox(-6.0F, -19.0F, -5.0F, 12, 1, 7, false);

        this.saddle_top = new ModelRenderer(this);
        this.saddle_top.setRotationPoint(0.0F, -18.5F, -1.5F);
        this.saddle.addChild(this.saddle_top);
        this.saddle_top.setTextureOffset(66, 44).addBox(-6.0F, -2.5F, 2.5F, 12, 2, 1, false);
    }
    
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.Throax_base.render(scale);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {  	 	  	
    	float vibrate_rate = 0.5F;
    	float i = (float)((EntityVespa)entity).getAttackTimer();
    	
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);    	
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	
    	this.Wing_0_r.rotateAngleX = 0.0F;
    	this.Wing_0_r.rotateAngleY = -0.22759093446006054F;
    	this.Wing_0_r.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks);
    	this.Wing_1_r.rotateAngleX = 0.0F;
    	this.Wing_1_r.rotateAngleY = 0.40980330836826856F;
    	this.Wing_1_r.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks + (float)Math.PI);
    	this.Wing_0_l.rotateAngleX = 0.0F;
    	this.Wing_0_l.rotateAngleY = 0.22759093446006054F;
    	this.Wing_0_l.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks + (float)Math.PI);
    	this.Wing_1_l.rotateAngleX = 0.0F;
    	this.Wing_1_l.rotateAngleY = -0.40980330836826856F;
    	this.Wing_1_l.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks);
      
    	this.Throax_base.rotationPointY = 7.0F + (((EntityVespa)entity).isBeingRidden() ? 5.0F : 5.0F * MathHelper.sin(ageInTicks * vibrate_rate));  	    	
    	this.Stinger.rotationPointZ = -3.0F;
    	
    	this.setRotateAngle(this.leg_r_0, 0.0F, 0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, -1.6845917940249266F);
    	this.setRotateAngle(this.leg_r_1, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
    	this.setRotateAngle(this.leg_r_2, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
    	this.setRotateAngle(this.leg_l_0, 0.0F, -0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, 1.6845917940249266F);
    	this.setRotateAngle(this.leg_l_1, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
    	this.setRotateAngle(this.leg_l_2, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);    
    	
    	if (this.state.equals(ModelFlyingBase.State.WAITING)) {
    		vibrate_rate = 0.05F;
    		
    		this.Throax_base.rotationPointY = 12.0F;
 
	    	this.Waist.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen4.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.Stinger.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	
    		this.setRotateAngle(this.Wing_0_r, 0.8600982340775168F, 1.2971286947558636F, -0.5082398928281348F);
    		this.setRotateAngle(this.Wing_1_r, 0.0781907508222411F, 1.2699016010353295F, -1.2901473511162753F);
    		this.setRotateAngle(this.Wing_0_l, 0.8600982340775168F, -1.2971286947558636F, 0.5082398928281348F);
    		this.setRotateAngle(this.Wing_1_l, 0.0781907508222411F, -1.2699016010353295F, 1.2901473511162753F);
 
	    	this.setRotateAngle(this.leg_r_0, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
	    	this.setRotateAngle(this.leg_r_1, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg_r_2, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg_l_0, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
	    	this.setRotateAngle(this.leg_l_1, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
	    	this.setRotateAngle(this.leg_l_2, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
	    	
	        this.leg_r_0.rotateAngleZ = -0.78539816F;
	        this.leg_l_0.rotateAngleZ = 0.78539816F;
	        this.leg_r_1.rotateAngleZ = -0.58119464F;
	        this.leg_l_1.rotateAngleZ = 0.58119464F;
	        this.leg_r_2.rotateAngleZ = -0.58119464F;
	        this.leg_l_2.rotateAngleZ = 0.58119464F;
	        this.leg_r_0.rotateAngleY = -0.78539816F;
	        this.leg_l_0.rotateAngleY = 0.78539816F;
	        this.leg_r_1.rotateAngleY = -0.39269908F;
	        this.leg_l_1.rotateAngleY = 0.39269908F;
	        this.leg_r_2.rotateAngleY = 0.39269908F;
	        this.leg_l_2.rotateAngleY = -0.39269908F;
	        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
	        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
	        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        this.leg_r_0.rotateAngleY += f3;
	        this.leg_l_0.rotateAngleY += -f3;
	        this.leg_r_1.rotateAngleY += f4;
	        this.leg_l_1.rotateAngleY += -f4;
	        this.leg_r_2.rotateAngleY += f5;
	        this.leg_l_2.rotateAngleY += -f5;
	        this.leg_r_0.rotateAngleZ += f7;
	        this.leg_l_0.rotateAngleZ += -f7;
	        this.leg_r_1.rotateAngleZ += f8;
	        this.leg_l_1.rotateAngleZ += -f8;
	        this.leg_r_2.rotateAngleZ += f9;
	        this.leg_l_2.rotateAngleZ += -f9;	
    	} else if (this.state.equals(ModelFlyingBase.State.FLYING)) {
	    	this.Waist.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen4.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.Stinger.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;	   
    	} else if (this.state.equals(ModelFlyingBase.State.ATTACKING)) {
    		if (i > 8.0F) {
    	    	this.Waist.rotateAngleX = GradientAnimation(0.0F, -1.0472F, (i - 8.0F) / 12.0F);
    	    	this.UAbdomen1.rotateAngleX = GradientAnimation(0.2276F, -0.7323F, (i - 8.0F) / 12.0F);
    	    	this.UAbdomen2.rotateAngleX = GradientAnimation(-0.2276F, -0.6203F, (i - 8.0F) / 12.0F);
    	    	this.UAbdomen3.rotateAngleX = GradientAnimation(-0.2276F, -0.7512F, (i - 8.0F) / 12.0F);
    	    	this.UAbdomen4.rotateAngleX = GradientAnimation(-0.2276F, -0.0094F, (i - 8.0F) / 12.0F);
    	    	this.Stinger.rotateAngleX = 0.0F;
    	    	this.Stinger.rotationPointZ = GradientAnimation(-3.0F, 3.0F, (i - 8.0F) / 12.0F);
    		} else if (i > 5.0F) {
    	    	this.Waist.rotateAngleX = -1.0472F;
    	    	this.UAbdomen1.rotateAngleX = -0.7323F;
    	    	this.UAbdomen2.rotateAngleX = -0.6203F;
    	    	this.UAbdomen3.rotateAngleX = -0.7512F;
    	    	this.UAbdomen4.rotateAngleX = -0.0094F;
    	    	this.Stinger.rotateAngleX = 0.0F;
    	    	this.Stinger.rotationPointZ = 3.0F;
    		} else if (i > 0.0F) {
    	    	this.Waist.rotateAngleX = GradientAnimation_s(-1.0472F, 0.0F, i / 5.0F);
    	    	this.UAbdomen1.rotateAngleX = GradientAnimation_s(-0.7323F, 0.2276F, i / 5.0F);
    	    	this.UAbdomen2.rotateAngleX = GradientAnimation_s(-0.6203F, -0.2276F, i / 5.0F);
    	    	this.UAbdomen3.rotateAngleX = GradientAnimation_s(-0.7512F, -0.2276F, i / 5.0F);
    	    	this.UAbdomen4.rotateAngleX = GradientAnimation_s(-0.0094F, -0.2276F, i / 5.0F);	
    			this.Stinger.rotateAngleX = 0.0F;
    			this.Stinger.rotationPointZ = GradientAnimation_s(3.0F, -3.0F, i / 5.0F);
    		}
    	} else if (this.state.equals(ModelFlyingBase.State.HOVERING)) {
	    	this.Waist.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen4.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.Stinger.rotateAngleX = -0.014F * (20 - ((EntityVespa)entity).getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;	    
    	}
    }
}
