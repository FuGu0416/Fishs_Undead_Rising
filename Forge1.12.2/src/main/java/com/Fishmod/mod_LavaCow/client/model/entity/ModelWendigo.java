package com.Fishmod.mod_LavaCow.client.model.entity;


import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityWendigo;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelWendigo - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelWendigo extends FishModelBase {
    public ModelRenderer Chest;
    public ModelRenderer Humerus_l;
    public ModelRenderer Humerus_r;
    public ModelRenderer Waist;
    public ModelRenderer Head;
    public ModelRenderer Vertebra0;
    public ModelRenderer Vertebra1;
    public ModelRenderer Vertebra2;
    public ModelRenderer Arm_l;
    public ModelRenderer Palm_l;
    public ModelRenderer Arm_fur_l;
    public ModelRenderer Claw_0_l;
    public ModelRenderer Claw_1_l;
    public ModelRenderer Claw_2_l;
    public ModelRenderer Claw_01_l;
    public ModelRenderer Claw_11_l;
    public ModelRenderer Claw_21_l;
    public ModelRenderer Arm_r;
    public ModelRenderer Palm_r;
    public ModelRenderer Arm_fur_r;
    public ModelRenderer Claw_0_r;
    public ModelRenderer Claw_1_r;
    public ModelRenderer Claw_2_r;
    public ModelRenderer Claw_01_r;
    public ModelRenderer Claw_11_r;
    public ModelRenderer Claw_21_r;
    public ModelRenderer Pelvis;
    public ModelRenderer Inside;
    public ModelRenderer Tail;
    public ModelRenderer Thigh_r;
    public ModelRenderer Thigh_l;
    public ModelRenderer Leg_r;
    public ModelRenderer Feet_r;
    public ModelRenderer Leg_l;
    public ModelRenderer Feet_l;
    public ModelRenderer Ear_r;
    public ModelRenderer Ear_l;
    public ModelRenderer Jaw_up;
    public ModelRenderer Jaw_lower;

    public ModelWendigo() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Claw_01_r = new ModelRenderer(this, 16, 20);
        this.Claw_01_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_01_r.addBox(0.0F, -1.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_01_r, -0.091106186954104F, -0.4553564018453205F, 0.0F);
        this.Inside = new ModelRenderer(this, 23, 36);
        this.Inside.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Inside.addBox(-2.5F, -7.0F, -2.5F, 5, 8, 5, 0.0F);
        this.Arm_fur_r = new ModelRenderer(this, 29, 53);
        this.Arm_fur_r.setRotationPoint(0.0F, 0.0F, -3.3F);
        this.Arm_fur_r.addBox(-1.0F, 0.7F, -4.0F, 2, 3, 8, 0.0F);
        this.Thigh_r = new ModelRenderer(this, 0, 37);
        this.Thigh_r.setRotationPoint(-2.5F, 3.0F, 0.0F);
        this.Thigh_r.addBox(-2.0F, 0.0F, -2.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(Thigh_r, -1.5481070465189704F, 0.0F, 0.0F);
        this.Claw_11_r = new ModelRenderer(this, 16, 20);
        this.Claw_11_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_11_r.addBox(0.0F, -0.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_11_r, 0.0F, -0.4553564018453205F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Head.addBox(-2.5F, -3.0F, -3.0F, 5, 5, 5, 0.0F);
        this.Arm_fur_l = new ModelRenderer(this, 29, 53);
        this.Arm_fur_l.mirror = true;
        this.Arm_fur_l.setRotationPoint(0.0F, 0.0F, -3.3F);
        this.Arm_fur_l.addBox(-1.0F, 0.7F, -4.0F, 2, 3, 8, 0.0F);
        this.Pelvis = new ModelRenderer(this, 46, 44);
        this.Pelvis.setRotationPoint(0.0F, 8.0F, 1.0F);
        this.Pelvis.addBox(-2.5F, -1.5F, -2.0F, 5, 6, 4, 0.0F);
        this.setRotateAngle(Pelvis, 1.0927506446736497F, 0.0F, 0.0F);
        this.Vertebra2 = new ModelRenderer(this, 16, 0);
        this.Vertebra2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Vertebra2.addBox(-0.5F, -4.0F, 2.2F, 1, 1, 1, 0.0F);
        this.Claw_0_l = new ModelRenderer(this, 12, 16);
        this.Claw_0_l.mirror = true;
        this.Claw_0_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_0_l.addBox(0.0F, -1.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_0_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Claw_21_r = new ModelRenderer(this, 16, 20);
        this.Claw_21_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_21_r.addBox(0.0F, 0.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_21_r, 0.136659280431156F, -0.4553564018453205F, 0.0F);
        this.Tail = new ModelRenderer(this, 56, 59);
        this.Tail.setRotationPoint(-1.0F, 4.0F, 1.0F);
        this.Tail.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Tail, 0.40980330836826856F, 0.0F, 0.0F);
        this.Arm_l = new ModelRenderer(this, 6, 26);
        this.Arm_l.mirror = true;
        this.Arm_l.setRotationPoint(1.0F, 8.0F, 0.9F);
        this.Arm_l.addBox(-1.0F, -1.3F, -7.3F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Arm_l, 0.5462880558742251F, 0.0F, 0.0F);
        this.Claw_1_r = new ModelRenderer(this, 12, 16);
        this.Claw_1_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_1_r.addBox(-1.0F, -0.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_1_r, 0.0F, -0.4553564018453205F, 0.0F);
        this.Jaw_up = new ModelRenderer(this, 0, 12);
        this.Jaw_up.setRotationPoint(0.0F, -1.0F, -2.5F);
        this.Jaw_up.addBox(-1.5F, 0.0F, -5.5F, 3, 4, 5, 0.0F);
        this.Vertebra0 = new ModelRenderer(this, 16, 0);
        this.Vertebra0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Vertebra0.addBox(-0.5F, -4.0F, -1.8F, 1, 1, 1, 0.0F);
        this.Palm_r = new ModelRenderer(this, 20, 23);
        this.Palm_r.setRotationPoint(-0.1F, -0.3F, -6.8F);
        this.Palm_r.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 3, 0.0F);
        this.setRotateAngle(Palm_r, 0.0F, -0.27314402793711257F, 0.0F);
        this.Ear_r = new ModelRenderer(this, 48, 0);
        this.Ear_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Ear_r.addBox(-9.0F, -11.0F, 0.0F, 8, 8, 0, 0.0F);
        this.Leg_l = new ModelRenderer(this, 0, 52);
        this.Leg_l.mirror = true;
        this.Leg_l.setRotationPoint(0.0F, 7.0F, 0.5F);
        this.Leg_l.addBox(0.0F, 0.0F, 0.0F, 2, 7, 3, 0.0F);
        this.setRotateAngle(Leg_l, 0.40980330836826856F, 0.0F, 0.0F);
        this.Claw_11_l = new ModelRenderer(this, 16, 20);
        this.Claw_11_l.mirror = true;
        this.Claw_11_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_11_l.addBox(-1.0F, -0.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_11_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Ear_l = new ModelRenderer(this, 48, 0);
        this.Ear_l.mirror = true;
        this.Ear_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Ear_l.addBox(1.0F, -11.0F, 0.0F, 8, 8, 0, 0.0F);
        this.Claw_01_l = new ModelRenderer(this, 16, 20);
        this.Claw_01_l.mirror = true;
        this.Claw_01_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_01_l.addBox(-1.0F, -1.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_01_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Vertebra1 = new ModelRenderer(this, 16, 0);
        this.Vertebra1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Vertebra1.addBox(-0.5F, -4.0F, 0.2F, 1, 1, 1, 0.0F);
        this.Leg_r = new ModelRenderer(this, 0, 52);
        this.Leg_r.setRotationPoint(-1.0F, 7.0F, 0.5F);
        this.Leg_r.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 3, 0.0F);
        this.setRotateAngle(Leg_r, 0.40980330836826856F, 0.0F, 0.0F);
        this.Feet_l = new ModelRenderer(this, 12, 57);
        this.Feet_l.mirror = true;
        this.Feet_l.setRotationPoint(1.0F, 7.0F, 2.0F);
        this.Feet_l.addBox(-1.5F, -1.0F, -4.0F, 3, 2, 5, 0.0F);
        this.setRotateAngle(Feet_l, 0.06736970912698112F, 0.0F, 0.0F);
        this.Jaw_lower = new ModelRenderer(this, 17, 4);
        this.Jaw_lower.setRotationPoint(0.0F, 2.0F, 0.5F);
        this.Jaw_lower.addBox(-1.5F, -3.0F, -8.0F, 3, 3, 8, 0.0F);
        this.setRotateAngle(Jaw_lower, 0.5462880558742251F, 0.0F, 0.0F);
        this.Claw_2_r = new ModelRenderer(this, 12, 16);
        this.Claw_2_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_2_r.addBox(-1.0F, 0.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_2_r, 0.136659280431156F, -0.4553564018453205F, 0.0F);
        this.Thigh_l = new ModelRenderer(this, 0, 37);
        this.Thigh_l.mirror = true;
        this.Thigh_l.setRotationPoint(2.5F, 3.0F, 0.0F);
        this.Thigh_l.addBox(0.0F, 0.0F, -2.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(Thigh_l, -1.5481070465189704F, 0.0F, 0.0F);
        this.Humerus_l = new ModelRenderer(this, 0, 21);
        this.Humerus_l.mirror = true;
        this.Humerus_l.setRotationPoint(2.0F, 1.0F, 1.5F);
        this.Humerus_l.addBox(0.0F, 0.0F, -1.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(Humerus_l, 0.22759093446006054F, 0.18203784098300857F, -0.5918411493512771F);
        this.Claw_21_l = new ModelRenderer(this, 16, 20);
        this.Claw_21_l.mirror = true;
        this.Claw_21_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_21_l.addBox(-1.0F, 0.5F, -5.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Claw_21_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Arm_r = new ModelRenderer(this, 6, 26);
        this.Arm_r.setRotationPoint(-1.0F, 8.0F, 0.9F);
        this.Arm_r.addBox(-1.0F, -1.3F, -7.3F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Arm_r, 0.5462880558742251F, 0.0F, 0.0F);
        this.Chest = new ModelRenderer(this, 34, 10);
        this.Chest.setRotationPoint(0.0F, -7.5F, -1.0F);
        this.Chest.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
        this.setRotateAngle(Chest, -0.5009094953223726F, 0.0F, 0.0F);
        this.Waist = new ModelRenderer(this, 40, 25);
        this.Waist.setRotationPoint(0.0F, -2.0F, 1.0F);
        this.Waist.addBox(-3.0F, -7.0F, -3.0F, 6, 9, 6, 0.0F);
        this.setRotateAngle(Waist, -0.5691518690753509F, 0.0F, 0.0F);
        this.Claw_0_r = new ModelRenderer(this, 12, 16);
        this.Claw_0_r.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_0_r.addBox(-1.0F, -1.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_0_r, -0.091106186954104F, -0.4553564018453205F, 0.0F);
        this.Feet_r = new ModelRenderer(this, 12, 57);
        this.Feet_r.setRotationPoint(0.0F, 7.0F, 2.0F);
        this.Feet_r.addBox(-1.5F, -1.0F, -4.0F, 3, 2, 5, 0.0F);
        this.setRotateAngle(Feet_r, 0.06736970912698112F, 0.0F, 0.0F);
        this.Claw_1_l = new ModelRenderer(this, 12, 16);
        this.Claw_1_l.mirror = true;
        this.Claw_1_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_1_l.addBox(0.0F, -0.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_1_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Humerus_r = new ModelRenderer(this, 0, 21);
        this.Humerus_r.setRotationPoint(-2.0F, 1.0F, 1.5F);
        this.Humerus_r.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(Humerus_r, 0.22759093446006054F, -0.18203784098300857F, 0.5918411493512771F);
        this.Palm_l = new ModelRenderer(this, 20, 23);
        this.Palm_l.mirror = true;
        this.Palm_l.setRotationPoint(0.1F, -0.3F, -6.8F);
        this.Palm_l.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 3, 0.0F);
        this.setRotateAngle(Palm_l, 0.0F, 0.27314402793711257F, 0.0F);
        this.Claw_2_l = new ModelRenderer(this, 12, 16);
        this.Claw_2_l.mirror = true;
        this.Claw_2_l.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.Claw_2_l.addBox(0.0F, 0.5F, -5.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(Claw_2_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Palm_r.addChild(this.Claw_01_r);
        this.Waist.addChild(this.Inside);
        this.Arm_r.addChild(this.Arm_fur_r);
        this.Pelvis.addChild(this.Thigh_r);
        this.Palm_r.addChild(this.Claw_11_r);
        this.Chest.addChild(this.Head);
        this.Arm_l.addChild(this.Arm_fur_l);
        this.Pelvis.addChild(this.Waist);
        this.Chest.addChild(this.Vertebra2);
        this.Palm_l.addChild(this.Claw_0_l);
        this.Palm_r.addChild(this.Claw_21_r);
        this.Pelvis.addChild(this.Tail);
        this.Humerus_l.addChild(this.Arm_l);
        this.Palm_r.addChild(this.Claw_1_r);
        this.Head.addChild(this.Jaw_up);
        this.Chest.addChild(this.Vertebra0);
        this.Arm_r.addChild(this.Palm_r);
        this.Head.addChild(this.Ear_r);
        this.Thigh_l.addChild(this.Leg_l);
        this.Palm_l.addChild(this.Claw_11_l);
        this.Head.addChild(this.Ear_l);
        this.Palm_l.addChild(this.Claw_01_l);
        this.Chest.addChild(this.Vertebra1);
        this.Thigh_r.addChild(this.Leg_r);
        this.Leg_l.addChild(this.Feet_l);
        this.Head.addChild(this.Jaw_lower);
        this.Palm_r.addChild(this.Claw_2_r);
        this.Pelvis.addChild(this.Thigh_l);
        this.Chest.addChild(this.Humerus_l);
        this.Palm_l.addChild(this.Claw_21_l);
        this.Humerus_r.addChild(this.Arm_r);
        this.Waist.addChild(this.Chest);
        this.Palm_r.addChild(this.Claw_0_r);
        this.Leg_r.addChild(this.Feet_r);
        this.Palm_l.addChild(this.Claw_1_l);
        this.Chest.addChild(this.Humerus_r);
        this.Arm_l.addChild(this.Palm_l);
        this.Palm_l.addChild(this.Claw_2_l);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.Pelvis.render(scale);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Head.rotationPointY = -0.3F * MathHelper.sin(0.03F * ageInTicks);
    	this.Jaw_lower.rotateAngleX = 0.275F + (-0.07F * MathHelper.sin(0.03F * ageInTicks));
    	this.Thigh_r.rotateAngleX = -1.5481070465189704F + MathHelper.cos(limbSwing * 0.3F) * 0.7F * limbSwingAmount;
    	this.Thigh_l.rotateAngleX = -1.5481070465189704F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Leg_r.rotateAngleX = 0.40980330836826856F - MathHelper.cos(limbSwing * 0.3F + 0.3F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Leg_l.rotateAngleX = 0.40980330836826856F - MathHelper.cos(limbSwing * 0.3F + 1.3F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Feet_r.rotateAngleX = 0.06736970912698112F + MathHelper.cos(limbSwing * 0.3F + 0.5F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Feet_l.rotateAngleX = 0.06736970912698112F + MathHelper.cos(limbSwing * 0.3F + 1.5F * (float)Math.PI) * 0.7F * limbSwingAmount;
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	int i = ((EntityWendigo) entityIn).getAttackTimer();
    	byte j = ((EntityWendigo) entityIn).getAttackStance();

    	if (i > 10) {
    		if (j == (byte)40 || j == (byte)41) {
	    		this.Humerus_r.rotateAngleX = -0.27314402793711257F;
	    		this.Humerus_r.rotateAngleY = -0.18203784098300857F + 2.8F * MathHelper.sin((float)Math.PI * 0.125F * (i - 11));
	    		this.Humerus_r.rotateAngleZ = 1.3203415791337103F;
	    		this.Chest.rotateAngleY = GradientAnimation(1.1383037381507017F, 0.0F, (i - 10.0F)/10.0F);
    		}
    		
    		if (j == (byte)40 || j == (byte)42) {
	    		this.Humerus_l.rotateAngleX = -0.27314402793711257F;
	    		this.Humerus_l.rotateAngleY = 0.18203784098300857F - 2.8F * MathHelper.sin((float)Math.PI * 0.125F * (i - 11));
	    		this.Humerus_l.rotateAngleZ = -1.3203415791337103F;
	    		this.Chest.rotateAngleY = GradientAnimation(-1.1383037381507017F, 0.0F, (i - 10.0F)/10.0F);
    		}
    		
    		if (j == (byte)40) {
    			this.Chest.rotateAngleY = 0.0F;
    			this.Chest.rotateAngleX = GradientAnimation(-1.1383037381507017F, -0.5009094953223726F, (i - 10.0F)/10.0F);
    		}
    		
    		this.Waist.rotateAngleX = GradientAnimation(-0.5691518690753509F, -0.136659280431156F, (i - 10.0F)/10.0F); 		
    	}
    	else if (i > 0) {
    		if (j == (byte)40 || j == (byte)41) {
	        	this.Humerus_r.rotateAngleX = -0.18203784098300857F; 	
	        	this.Humerus_r.rotateAngleY = -0.18203784098300857F;// - 0.8F * this.triangleWave((float)i/* - ageInTicks*/, 11.0F);
	        	this.Humerus_r.rotateAngleZ = 0.22759093446006054F;
    		}
    		
        	if (j == (byte)40 || j == (byte)42) {
	        	this.Humerus_l.rotateAngleX = -0.18203784098300857F;
	        	this.Humerus_l.rotateAngleY = 0.18203784098300857F;// + 0.8F * this.triangleWave((float)i/* - ageInTicks*/, 11.0F);
	        	this.Humerus_l.rotateAngleZ = -0.22759093446006054F;
        	}
        }
        else
        {      	
        	this.setRotateAngle(Humerus_r, 0.22759093446006054F, -0.18203784098300857F, 0.5918411493512771F);
        	this.setRotateAngle(Humerus_l, 0.22759093446006054F, 0.18203784098300857F, -0.5918411493512771F);
        	
        	this.Humerus_r.rotationPointY = 1.0F + (-0.55F  * MathHelper.sin(0.03F * entityIn.ticksExisted + 0.2F * (float)Math.PI)); 
        	this.Humerus_l.rotationPointY = 1.0F + (-0.55F * MathHelper.sin(0.03F * entityIn.ticksExisted + 0.2F * (float)Math.PI));   
        	
        	this.Waist.rotateAngleX = -0.5691518690753509F;       	
        }
    }
}
