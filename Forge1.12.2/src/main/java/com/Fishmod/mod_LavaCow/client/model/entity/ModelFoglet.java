package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelFoglet extends FishModelBase {
	public ModelRenderer torso;
    public ModelRenderer head;
    public ModelRenderer arm_r;
    public ModelRenderer arm_l;
    public ModelRenderer leg_r;
    public ModelRenderer leg_l;
    public ModelRenderer tail0;
    public ModelRenderer head_ear_r;
    public ModelRenderer head_ear_l;
    public ModelRenderer horn0_l;
    public ModelRenderer horn0_r;
    public ModelRenderer horn1_l;
    public ModelRenderer horn2_l;
    public ModelRenderer horn1_r;
    public ModelRenderer horn2_r;
    public ModelRenderer tail1;
    public ModelRenderer tail2;

    public ModelFoglet() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_r = new ModelRenderer(this, 8, 0);
        this.leg_r.setRotationPoint(-1.9F, 7.0F, 0.0F);
        this.leg_r.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 16, 0);
        this.head.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(head, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_l = new ModelRenderer(this, 0, 0);
        this.arm_l.mirror = true;
        this.arm_l.setRotationPoint(3.0F, 3.0F, 0.0F);
        this.arm_l.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_l, -0.9023352232810683F, 0.10000736613927509F, -0.10000736613927509F);
        this.head_ear_r = new ModelRenderer(this, 16, 0);
        this.head_ear_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head_ear_r.addBox(-7.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.head_ear_l = new ModelRenderer(this, 16, 0);
        this.head_ear_l.mirror = true;
        this.head_ear_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head_ear_l.addBox(4.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.leg_l = new ModelRenderer(this, 8, 0);
        this.leg_l.mirror = true;
        this.leg_l.setRotationPoint(1.9F, 7.0F, 0.0F);
        this.leg_l.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_r = new ModelRenderer(this, 0, 0);
        this.arm_r.setRotationPoint(-3.0F, 3.0F, 0.0F);
        this.arm_r.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_r, -0.9023352232810683F, -0.10000736613927509F, 0.10000736613927509F);
        this.torso = new ModelRenderer(this, 0, 14);
        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
        this.torso.addBox(-3.0F, 0.0F, -2.0F, 6, 8, 4, 0.0F);
        this.setRotateAngle(torso, 0.5009094953223726F, 0.0F, 0.0F);
        this.horn1_r = new ModelRenderer(this, 46, 0);
        this.horn1_r.setRotationPoint(0.0F, -3.0F, -1.0F);
        this.horn1_r.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(horn1_r, -1.2901473511162753F, 0.0F, 0.0F);
        this.horn0_l = new ModelRenderer(this, 56, 0);
        this.horn0_l.mirror = true;
        this.horn0_l.setRotationPoint(2.0F, -7.0F, -3.0F);
        this.horn0_l.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(horn0_l, 0.8210028961170991F, 0.0F, 0.0F);
        this.tail1 = new ModelRenderer(this, 55, 7);
        this.tail1.setRotationPoint(0.0F, -6.0F, 0.5F);
        this.tail1.addBox(-0.5F, -5.0F, -1.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(tail1, 0.8993681422473893F, 0.0F, 0.0F);
        this.horn2_l = new ModelRenderer(this, 41, 0);
        this.horn2_l.mirror = true;
        this.horn2_l.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.horn2_l.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(horn2_l, -0.7037167490777915F, 0.0F, 0.0F);
        this.horn0_r = new ModelRenderer(this, 56, 0);
        this.horn0_r.setRotationPoint(-2.0F, -7.0F, -3.0F);
        this.horn0_r.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(horn0_r, 0.8210028961170991F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 50, 8);
        this.tail2.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.tail2.addBox(-0.5F, -4.0F, -1.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(tail2, 0.9389871242571438F, 0.0F, 0.0F);
        this.tail0 = new ModelRenderer(this, 60, 6);
        this.tail0.setRotationPoint(0.0F, 7.9F, 0.9F);
        this.tail0.addBox(-0.5F, -6.0F, -0.5F, 1, 6, 1, 0.0F);
        this.setRotateAngle(tail0, -2.88380744850172F, 0.0F, 0.0F);
        this.horn2_r = new ModelRenderer(this, 41, 0);
        this.horn2_r.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.horn2_r.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(horn2_r, -0.7037167490777915F, 0.0F, 0.0F);
        this.horn1_l = new ModelRenderer(this, 46, 0);
        this.horn1_l.mirror = true;
        this.horn1_l.setRotationPoint(0.0F, -3.0F, -1.0F);
        this.horn1_l.addBox(-1.0F, -3.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(horn1_l, -1.2901473511162753F, 0.0F, 0.0F);
        
        this.torso.addChild(this.leg_r);
        this.horn0_r.addChild(this.horn1_r);
        this.torso.addChild(this.arm_r);
        this.head.addChild(this.horn0_l);
        this.tail0.addChild(this.tail1);
        this.torso.addChild(this.arm_l);
        this.head.addChild(this.head_ear_r);
        this.horn1_l.addChild(this.horn2_l);
        this.head.addChild(this.head_ear_l);
        this.head.addChild(this.horn0_r);
        this.torso.addChild(this.leg_l);
        this.tail1.addChild(this.tail2);
        this.torso.addChild(this.tail0);
        this.horn1_r.addChild(this.horn2_r);
        this.horn0_l.addChild(this.horn1_l);
        this.torso.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {    	
    	this.torso.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	EntityFoglet entityfoglet = (EntityFoglet)entityIn;  	
    	float i = entityfoglet.getAttackTimer() / 35.0F;
    	
    	this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    	this.SwingX_Sin(this.tail0, -2.88380744850172F, ageInTicks, 0.2F, 0.1F, false, 0.0F);
    	this.SwingX_Sin(this.tail1, 0.8993681422473893F, ageInTicks, 0.2F, 0.1F, false, 0.1F);
    	this.SwingX_Sin(this.tail2, 0.9389871242571438F, ageInTicks, 0.2F, 0.1F, false, 0.2F);
    	
    	if (this.isRiding) {
    		this.leg_r.rotateAngleX = -1.8301522642938983F;
    		this.leg_r.rotateAngleY = 0.27366763203903305F;		
    		this.leg_l.rotateAngleX = -1.8301522642938983F;
    		this.leg_l.rotateAngleY = -0.27366763203903305F;    		
    	} else if (entityfoglet.getIsClimbing()) {
        	this.leg_r.rotateAngleX = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F) * 0.4F;
        	this.leg_r.rotateAngleY = 0.0F;		
            this.leg_l.rotateAngleX = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F; 
            this.leg_l.rotateAngleY = 0.0F;	
    	} else if (entityfoglet.getIsHanging()) {
            this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
            this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);   		 
    	} else if (i > 0) {
        	this.leg_r.rotateAngleX = GradientAnimation(0.0F, 0.46914448828868976F, i);
        	this.leg_r.rotateAngleY = 0.0F;	
	        this.leg_l.rotateAngleX = -0.899542712456844F;
	        this.leg_l.rotateAngleY = 0.0F;	   		
    	} else {
        	this.leg_r.rotateAngleX = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        	this.leg_r.rotateAngleY = 0.0F;	
	        this.leg_l.rotateAngleX = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount; 
	        this.leg_l.rotateAngleY = 0.0F;	
    	}
    	
    	if (entityfoglet.getIsClimbing()) {           
            this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;        
            this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
            this.torso.rotateAngleX = 0.5009094953223726F;          
        	this.arm_r.rotateAngleX = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F;
            this.arm_l.rotateAngleX = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F) * 0.4F; 
        } else if (entityfoglet.getIsHanging()) {
            this.head.rotateAngleX = -1.1383037381507017F + headPitch * 0.017453292F;
            this.torso.setRotationPoint(-0.0F, 16.0F, 0.0F);
            this.torso.rotateAngleX = -2.86844862565268F;        
        	this.setRotateAngle(arm_l, 2.86844862565268F, 0.10000736613927509F, 0.22759093446006054F);
            this.setRotateAngle(arm_r, 2.86844862565268F, -0.10000736613927509F, -0.22759093446006054F);
        } else if (entityfoglet.isSpellcastingC()) {
        	this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;
	        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
	        this.torso.rotateAngleX = 0.5009094953223726F;	
        	this.arm_r.rotateAngleX = 2.6862362517444724F;
        	this.arm_l.rotateAngleX = 2.6862362517444724F;
        	this.arm_r.rotateAngleZ = -0.9560913642424937F + MathHelper.sin(ageInTicks * 0.6F) * 0.8196F;
            this.arm_l.rotateAngleZ = 0.9560913642424937F - MathHelper.sin(ageInTicks * 0.6F) * 0.8196F; 
        } else if (i > 0) {
        	this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;
	        this.torso.setRotationPoint(-0.0F, GradientAnimation(8.0F, 10.0F, i), -3.0F);
	        this.torso.rotateAngleX = GradientAnimation(0.07086036663228437F, 0.8908160661968724F, i);	
        	this.arm_r.rotateAngleX = GradientAnimation(-0.5195845263081952F, 0.5359905973084921F, i);	
        	
        	if (i > 0.5F) {
        		this.arm_l.rotateAngleX = GradientAnimation(1.4978415587352114F, 3.141592653589793F, i);	       		
        	} else {
        		this.arm_l.rotateAngleX = GradientAnimation(-3.141592653589793F, -1.9046777647645121F, i);	      		
        	}
        	        	
        	this.arm_r.rotateAngleZ = 0.10000736613927509F;
        	this.arm_l.rotateAngleZ = -0.10000736613927509F; 
        } else if (entityfoglet.isAggressive()) {
        	this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;
	        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
	        this.torso.rotateAngleX = 0.5009094953223726F;	
        	this.arm_r.rotateAngleX = -2.0032889154390916F;
        	this.arm_l.rotateAngleX = -2.0032889154390916F;
        	this.arm_r.rotateAngleZ = 0.10000736613927509F;
        	this.arm_l.rotateAngleZ = -0.10000736613927509F; 
        } else {
            this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;  
	        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
	        this.torso.rotateAngleX = 0.5009094953223726F;	        
	        
	        if (this.isRiding) {	
	    		this.arm_r.rotateAngleX = -0.9023352365968739F;
	    		this.arm_r.rotateAngleY = 0.33004175888896664F;
	    		this.arm_r.rotateAngleZ = 0.10000736613927509F;
	    		this.arm_l.rotateAngleX = -0.9023352365968739F;
	    		this.arm_l.rotateAngleY = -0.33004175888896664F;	    	        	
	        	this.arm_l.rotateAngleZ = -0.10000736613927509F;
	        } else {
	            this.arm_r.rotateAngleX = -0.9023352232810683F + (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	            this.arm_r.rotateAngleY = 0.10000736613927509F;
	            this.arm_r.rotateAngleZ = 0.10000736613927509F;
	            this.arm_l.rotateAngleX = -0.9023352232810683F + (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;	        	
	        	this.arm_l.rotateAngleY = -0.10000736613927509F;		        	
	        	this.arm_l.rotateAngleZ = -0.10000736613927509F;
	        }
        }
    }
}
