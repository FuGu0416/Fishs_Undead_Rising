package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelOSalacannon - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelSalamander extends FishModelBase {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Body2;
    public ModelRenderer Body3;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer CannonBase;
    public ModelRenderer Jaw_upper;
    public ModelRenderer Jaw_lower;
    public ModelRenderer Tooth_r;
    public ModelRenderer Tooth_l;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;
    public ModelRenderer Tail3;
    public ModelRenderer RightArm2;
    public ModelRenderer LeftArm2;
    public ModelRenderer RightLeg2;
    public ModelRenderer LeftLeg2;
    public ModelRenderer Cannon1;
    public ModelRenderer Cannon2;
    public ModelRenderer Cannon3;
    public ModelRenderer Saddle_base;
    
    /**
     * Child Model
     */
    public ModelRenderer Body_Base;
    public ModelRenderer Head_c;
    public ModelRenderer Tail0;
    public ModelRenderer leg0_r0;
    public ModelRenderer leg0_l0;
    public ModelRenderer leg1_r0;
    public ModelRenderer leg1_l0;
    public ModelRenderer Jaw;
    public ModelRenderer Tail1_c;
    public ModelRenderer Tail2_c;
    public ModelRenderer leg0_r1;
    public ModelRenderer leg0_l1;
    public ModelRenderer leg1_r1;
    public ModelRenderer leg1_l1;
    public ModelRenderer Head_Gill_l0;
    public ModelRenderer Head_Gill_l1;
    public ModelRenderer Head_Gill_l2;
    public ModelRenderer Head_Gill_r0;
    public ModelRenderer Head_Gill_r1;
    public ModelRenderer Head_Gill_r2;

    public ModelSalamander() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightLeg2 = new ModelRenderer(this, 39, 0);
        this.RightLeg2.setRotationPoint(-1.0F, 2.0F, 0.5F);
        this.RightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(RightLeg2, 0.22759093446006054F, 0.0F, -0.5009094953223726F);
        this.Tail3 = new ModelRenderer(this, 20, 10);
        this.Tail3.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail3.addBox(-1.0F, -0.5F, 0.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Tail3, 0.091106186954104F, 0.0F, 0.0F);
        this.CannonBase = new ModelRenderer(this, 38, 23);
        this.CannonBase.setRotationPoint(0.0F, -4.0F, 3.5F);
        this.CannonBase.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(CannonBase, -0.40980330836826856F, 0.0F, 0.0F);
        this.Jaw_lower = new ModelRenderer(this, 0, 24);
        this.Jaw_lower.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.Jaw_lower.addBox(-2.5F, 0.5F, -5.0F, 5, 2, 6, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -3.5F);
        this.Head.addBox(-2.0F, -1.5F, -4.0F, 4, 2, 4, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 9);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(2.0F, 0.0F, 1.3F);
        this.LeftArm.addBox(0.0F, -1.0F, -2.0F, 2, 4, 3, 0.0F);
        this.setRotateAngle(LeftArm, -0.091106186954104F, 0.40980330836826856F, -0.9560913642424937F);
        this.Cannon1 = new ModelRenderer(this, 46, 19);
        this.Cannon1.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Cannon1.addBox(-2.0F, -2.5F, -3.0F, 4, 4, 5, 0.0F);
        this.setRotateAngle(Cannon1, 0.40980330836826856F, 0.0F, 0.0F);
        this.Cannon3 = new ModelRenderer(this, 29, 25);
        this.Cannon3.setRotationPoint(0.0F, -0.5F, -3.0F);
        this.Cannon3.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 5, 0.0F);
        this.Body = new ModelRenderer(this, 0, 8);
        this.Body.setRotationPoint(0.0F, 21.0F, -3.6F);
        this.Body.addBox(-2.0F, -2.0F, -3.5F, 4, 4, 11, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 9);
        this.RightLeg.setRotationPoint(-2.0F, -1.0F, 6.0F);
        this.RightLeg.addBox(-2.0F, -1.0F, -1.0F, 2, 4, 3, 0.0F);
        this.setRotateAngle(RightLeg, -0.22759093446006054F, 0.0F, 0.40980330836826856F);
        this.LeftLeg2 = new ModelRenderer(this, 39, 0);
        this.LeftLeg2.mirror = true;
        this.LeftLeg2.setRotationPoint(1.0F, 2.0F, 0.5F);
        this.LeftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(LeftLeg2, 0.22759093446006054F, 0.0F, 0.5009094953223726F);
        this.Body2 = new ModelRenderer(this, 42, 0);
        this.Body2.setRotationPoint(0.0F, -2.0F, 1.0F);
        this.Body2.addBox(-2.5F, -2.0F, -3.0F, 5, 4, 6, 0.0F);
        this.setRotateAngle(Body2, 0.5918411493512771F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 9);
        this.RightArm.setRotationPoint(-2.0F, 0.0F, 1.3F);
        this.RightArm.addBox(-2.0F, -1.0F, -2.0F, 2, 4, 3, 0.0F);
        this.setRotateAngle(RightArm, 0.091106186954104F, 0.0F, 0.9560913642424937F);
        this.LeftLeg = new ModelRenderer(this, 0, 9);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, -1.0F, 6.0F);
        this.LeftLeg.addBox(0.0F, -1.0F, -1.0F, 2, 4, 3, 0.0F);
        this.setRotateAngle(LeftLeg, -0.22759093446006054F, 0.0F, -0.40980330836826856F);
        this.Tooth_r = new ModelRenderer(this, 16, 23);
        this.Tooth_r.setRotationPoint(-1.8F, 1.0F, -4.5F);
        this.Tooth_r.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Tooth_r, 0.5604252228153792F, 0.9105382707654417F, 0.0F);
        this.Tail2 = new ModelRenderer(this, 32, 10);
        this.Tail2.setRotationPoint(0.0F, -0.5F, 4.0F);
        this.Tail2.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 4, 0.0F);
        this.setRotateAngle(Tail2, 0.091106186954104F, 0.0F, 0.0F);
        this.Cannon2 = new ModelRenderer(this, 34, 17);
        this.Cannon2.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.Cannon2.addBox(-1.5F, -2.0F, -3.0F, 3, 3, 3, 0.0F);
        this.Body3 = new ModelRenderer(this, 22, 0);
        this.Body3.setRotationPoint(0.0F, -2.0F, 5.0F);
        this.Body3.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Body3, -0.18203784098300857F, 0.0F, 0.0F);
        this.LeftArm2 = new ModelRenderer(this, 39, 0);
        this.LeftArm2.mirror = true;
        this.LeftArm2.setRotationPoint(1.0F, 2.0F, -0.5F);
        this.LeftArm2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(LeftArm2, -0.12775810124598494F, 0.0F, 0.9536479032897017F);
        this.Tail1 = new ModelRenderer(this, 46, 10);
        this.Tail1.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.Tail1.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 5, 0.0F);
        this.RightArm2 = new ModelRenderer(this, 39, 0);
        this.RightArm2.setRotationPoint(-1.0F, 2.0F, -0.5F);
        this.RightArm2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(RightArm2, -0.12775810124598494F, 0.0F, -0.9536479032897017F);
        this.Jaw_upper = new ModelRenderer(this, 15, 0);
        this.Jaw_upper.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Jaw_upper.addBox(-2.0F, -0.5F, -1.0F, 4, 1, 1, 0.0F);
        this.Tooth_l = new ModelRenderer(this, 16, 23);
        this.Tooth_l.setRotationPoint(2.0F, 1.0F, -4.5F);
        this.Tooth_l.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Tooth_l, 0.5604252228153792F, -0.9093165402890457F, 0.0F);
        this.Saddle_base = new ModelRenderer(this, 44, 30);
        this.Saddle_base.setRotationPoint(-2.0F, -3.5F, 0.5F);
        this.Saddle_base.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.RightLeg.addChild(this.RightLeg2);
        this.Tail2.addChild(this.Tail3);
        this.Body.addChild(this.CannonBase);
        this.Head.addChild(this.Jaw_lower);
        this.Body.addChild(this.Head);
        this.Body.addChild(this.LeftArm);
        this.CannonBase.addChild(this.Cannon1);
        this.Cannon2.addChild(this.Cannon3);
        this.Body.addChild(this.RightLeg);
        this.LeftLeg.addChild(this.LeftLeg2);
        this.Body.addChild(this.Body2);
        this.Body.addChild(this.RightArm);
        this.Body.addChild(this.LeftLeg);
        this.Jaw_lower.addChild(this.Tooth_r);
        this.Tail1.addChild(this.Tail2);
        this.Cannon1.addChild(this.Cannon2);
        this.Body.addChild(this.Body3);
        this.LeftArm.addChild(this.LeftArm2);
        this.Body3.addChild(this.Tail1);
        this.RightArm.addChild(this.RightArm2);
        this.Head.addChild(this.Jaw_upper);
        this.Jaw_lower.addChild(this.Tooth_l);
        this.Cannon1.addChild(this.Saddle_base);

        /**
         * Child Model
         */
        this.Body_Base = new ModelRenderer(this, 0, 17);
        this.Body_Base.setRotationPoint(0.0F, 21.0F, 5.0F);
        this.Body_Base.addBox(-3.0F, -1.5F, -12.0F, 6, 3, 12, 0.0F);
        this.setRotateAngle(Body_Base, -0.07103490055616922F, 0.0F, 0.0F);
        this.leg1_l0 = new ModelRenderer(this, 42, 0);
        this.leg1_l0.mirror = true;
        this.leg1_l0.setRotationPoint(2.5F, 0.0F, -2.0F);
        this.leg1_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_l0, 0.40980330836826856F, 0.0F, -0.40980330836826856F);
        this.Tail2_c = new ModelRenderer(this, 24, 7);
        this.Tail2_c.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail2_c.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail2_c, 0.3312634920285238F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 9);
        this.Jaw.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.Jaw.addBox(-2.5F, -0.5F, -7.0F, 5, 1, 7, 0.0F);
        this.leg1_l1 = new ModelRenderer(this, 43, 5);
        this.leg1_l1.mirror = true;
        this.leg1_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_l1, 0.0F, 0.0F, 0.4553564018453205F);
        this.Head_c = new ModelRenderer(this, 0, 0);
        this.Head_c.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Head_c.addBox(-2.5F, -1.5F, -7.0F, 5, 2, 7, 0.0F);
        this.leg0_l0 = new ModelRenderer(this, 42, 0);
        this.leg0_l0.mirror = true;
        this.leg0_l0.setRotationPoint(2.5F, 0.0F, -10.0F);
        this.leg0_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_l0, 0.0F, 0.0F, -0.40980330836826856F);
        this.leg1_r0 = new ModelRenderer(this, 42, 0);
        this.leg1_r0.setRotationPoint(-2.5F, 0.0F, -2.0F);
        this.leg1_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_r0, 0.40980330836826856F, 0.0F, 0.40980330836826856F);
        this.leg1_r1 = new ModelRenderer(this, 43, 5);
        this.leg1_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_r1, 0.0F, 0.0F, -0.4553564018453205F);
        this.leg0_r1 = new ModelRenderer(this, 43, 5);
        this.leg0_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_r1, 0.0F, 0.0F, -0.4553564018453205F);
        this.Tail1_c = new ModelRenderer(this, 24, 7);
        this.Tail1_c.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.Tail1_c.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail1_c, 0.272096830385916F, 0.0F, 0.0F);
        this.Tail0 = new ModelRenderer(this, 25, 0);
        this.Tail0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail0.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 4, 0.0F);
        this.setRotateAngle(Tail0, 0.035430183815484885F, 0.0F, 0.0F);
        this.leg0_r0 = new ModelRenderer(this, 42, 0);
        this.leg0_r0.setRotationPoint(-2.5F, 0.0F, -10.0F);
        this.leg0_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_r0, 0.0F, 0.0F, 0.40980330836826856F);
        this.leg0_l1 = new ModelRenderer(this, 43, 5);
        this.leg0_l1.mirror = true;
        this.leg0_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_l1, 0.0F, 0.0F, 0.4553564018453205F);
        
        this.Head_Gill_l2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l2.mirror = true;
        this.Head_Gill_l2.setRotationPoint(2.0F, -0.5F, -1.0F);
        this.Head_Gill_l2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l2, 0.18203784098300857F, -0.5918411493512771F, 0.5009094953223726F);
        this.Head_Gill_l1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l1.mirror = true;
        this.Head_Gill_l1.setRotationPoint(2.0F, -0.6F, -1.0F);
        this.Head_Gill_l1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l1, 0.0F, -0.8196066167365371F, 0.0F);
        this.Head_Gill_r2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r2.setRotationPoint(-2.0F, -0.5F, -1.0F);
        this.Head_Gill_r2.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r2, 0.18203784098300857F, 0.5918411493512771F, -0.5009094953223726F);
        this.Head_Gill_r1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r1.setRotationPoint(-2.0F, -0.6F, -1.0F);
        this.Head_Gill_r1.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r1, 0.0F, 0.8196066167365371F, 0.0F);
        this.Head_Gill_l0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l0.mirror = true;
        this.Head_Gill_l0.setRotationPoint(1.9F, -0.8F, -1.5F);
        this.Head_Gill_l0.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l0, -0.136659280431156F, -0.8651597102135892F, -0.5009094953223726F);
        this.Head_Gill_r0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r0.setRotationPoint(-1.9F, -0.9F, -1.5F);
        this.Head_Gill_r0.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r0, -0.136659280431156F, 0.8651597102135892F, 0.5009094953223726F);
        
        this.Body_Base.addChild(this.leg1_l0);
        this.Tail1_c.addChild(this.Tail2_c);
        this.Head_c.addChild(this.Jaw);
        this.leg1_l0.addChild(this.leg1_l1);
        this.Body_Base.addChild(this.Head_c);
        this.Body_Base.addChild(this.leg0_l0);
        this.Body_Base.addChild(this.leg1_r0);
        this.leg1_r0.addChild(this.leg1_r1);
        this.leg0_r0.addChild(this.leg0_r1);
        this.Tail0.addChild(this.Tail1_c);
        this.Body_Base.addChild(this.Tail0);
        this.Body_Base.addChild(this.leg0_r0);
        this.leg0_l0.addChild(this.leg0_l1);
        
        this.Head_c.addChild(this.Head_Gill_l2);
        this.Head_c.addChild(this.Head_Gill_l1);
        this.Head_c.addChild(this.Head_Gill_r2);
        this.Head_c.addChild(this.Head_Gill_r1);
        this.Head_c.addChild(this.Head_Gill_l0);
        this.Head_c.addChild(this.Head_Gill_r0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        if(!((EntitySalamander)entity).isNymph()) {
        	this.Body.render(f5);
        }
        else
        	this.Body_Base.render(f5);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	EntitySalamander Entity = ((EntitySalamander)entityIn);
    	
    	if(!Entity.isNymph())
    	{
	    	this.Head.rotateAngleX = headPitch * 0.017453292F;
	        this.Head.rotateAngleY = netHeadYaw * 0.0003F;
	        
        	if(this.isChild) {
        		if(Entity.getGrowingStage() == 1) {
	        		this.Tooth_l.isHidden = true;
	        		this.Tooth_r.isHidden = true;
        		} else {
            		this.Tooth_l.isHidden = false;
            		this.Tooth_r.isHidden = false;
        		}
        		
        		this.CannonBase.isHidden = true;
        		this.Cannon1.isHidden = true;
        		this.Cannon2.isHidden = true;
        		this.Cannon3.isHidden = true;
        	}
        	else {
        		this.Tooth_l.isHidden = false;
        		this.Tooth_r.isHidden = false;
        		this.CannonBase.isHidden = false;
        		this.Cannon1.isHidden = false;
        		this.Cannon2.isHidden = false;
        		this.Cannon3.isHidden = false;
        	}
    	}
    	else
    	{
    		if(Entity.isAggressive()/* && limbSwing == 0*/)
    		{
    			this.Body_Base.rotationPointY = 21.5F;
    			this.Body_Base.rotateAngleX = -0.6829473363053812F;
    	        this.setRotateAngle(Body_Base, -0.6829473363053812F, 0.0F, 0.0F);
    			
    	        this.Head_c.rotateAngleX = 0.5462880558742251F + headPitch * 0.017453292F;
    	        this.Head_c.rotateAngleY = 0.0F;
    	        this.Jaw.rotateAngleX = 0.50F;
    	        
	            this.Head_Gill_l0.rotateAngleY = 0.0F;
	            this.Head_Gill_l1.rotateAngleY = 0.0F;
	            this.Head_Gill_l2.rotateAngleY = 0.0F;
	            this.Head_Gill_r0.rotateAngleY = 0.0F;
	            this.Head_Gill_r1.rotateAngleY = 0.0F;
	            this.Head_Gill_r2.rotateAngleY = 0.0F;
    			
    			this.Tail0.rotateAngleX = 0.5462880558742251F;
    		}
    		else
    		{
    			this.Body_Base.rotationPointY = 21.0F;
    			this.Body_Base.rotateAngleX = -0.07103490055616922F;
    			this.Head_c.rotateAngleX = headPitch * 0.017453292F;
	            this.Head_c.rotateAngleY = netHeadYaw * 0.017453292F;
	            this.Jaw.rotateAngleX = 0.08F - 0.08F * MathHelper.sin(0.03F * ageInTicks);
	            
	            this.Head_Gill_l0.rotateAngleY = -0.8651597102135892F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
	            this.Head_Gill_l1.rotateAngleY = -0.8196066167365371F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
	            this.Head_Gill_l2.rotateAngleY = -0.5918411493512771F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
	            this.Head_Gill_r0.rotateAngleY = 0.8651597102135892F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
	            this.Head_Gill_r1.rotateAngleY = 0.8196066167365371F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
	            this.Head_Gill_r2.rotateAngleY = 0.5918411493512771F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
	            
	            this.Tail0.rotateAngleX = 0.08F + 0.08F * MathHelper.sin(0.06F * ageInTicks);
	            this.Tail1_c.rotateAngleX = 0.135F + 0.135F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.2F);
	            this.Tail2_c.rotateAngleX = 0.165F + 0.165F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.4F);
    		}
    	}
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float i = (float)((EntitySalamander) entityIn).getAttackTimer() / 80.0F;
    	float Anime_threshold[] = {0.85F, 0.67F, 0.12F};
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);
    	
    	if(i <= Anime_threshold[0] && i > Anime_threshold[1]) {
    		this.Head.rotateAngleZ = GradientAnimation(0.0F, -0.36425021489121656F, j * (i - Anime_threshold[1]));
    		this.Jaw_lower.rotateAngleX = GradientAnimation(0.0F, 0.6829473363053812F, j * (i - Anime_threshold[1]));
	        this.RightArm.rotateAngleX = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
	        this.LeftArm.rotateAngleX = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
	        this.Body.rotateAngleX = GradientAnimation(0.0F, -0.18203784098300857F, j * (i - Anime_threshold[1]));
	        this.Body.rotationPointY = GradientAnimation(21.0F, 19.7F, j * (i - Anime_threshold[1]));
	        this.Tail1.rotateAngleY = GradientAnimation(0.0F, -0.136659280431156F, j * (i - Anime_threshold[1]));
	        this.Tail2.rotateAngleY = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
	        this.Tail3.rotateAngleY = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
    	}
    	else if(i <= Anime_threshold[1] && i > Anime_threshold[2]){
    		this.Head.rotateAngleZ = GradientAnimation(-0.36425021489121656F, 0.0F, k * (i - Anime_threshold[2]));
    		this.Jaw_lower.rotateAngleX = GradientAnimation(0.6829473363053812F, 0.0F, k * (i - Anime_threshold[2]));
	        this.RightArm.rotateAngleX = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
	        this.LeftArm.rotateAngleX = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
	        this.Body.rotateAngleX = GradientAnimation(-0.18203784098300857F, 0.0F, k * (i - Anime_threshold[2]));
	        this.Body.rotationPointY = GradientAnimation(19.7F, 21.0F, k * (i - Anime_threshold[2]));
	        this.Tail1.rotateAngleY = GradientAnimation(-0.136659280431156F, 0.0F, k * (i - Anime_threshold[2]));
	        this.Tail2.rotateAngleY = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
	        this.Tail3.rotateAngleY = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
	        
	        this.Cannon2.rotationPointZ = -1.8F + 1.2F * MathHelper.sin(1.0F * entityIn.ticksExisted);
	        this.Cannon3.rotationPointZ = -1.3F + 1.7F * MathHelper.sin(1.0F * entityIn.ticksExisted);
    	}
    	else if(!entityIn.isChild() && i <= Anime_threshold[2]){
    		this.Head.rotateAngleZ = 0.0F;
    		this.Jaw_lower.rotateAngleX = -0.08F + (-0.08F * MathHelper.sin(0.03F * entityIn.ticksExisted));
	        this.RightArm.rotateAngleX = 0.091106186954104F;
	        this.LeftArm.rotateAngleX = 0.091106186954104F;
	        this.Body.rotateAngleX = 0.0F;
	        this.Body.rotationPointY = 21.0F;		
	        this.Tail1.rotateAngleY = 0.15F * MathHelper.sin(0.03F * entityIn.ticksExisted);
	        this.Tail2.rotateAngleY = 0.15F * MathHelper.sin(0.03F * entityIn.ticksExisted + 0.02F);
	        this.Tail3.rotateAngleY = 0.15F * MathHelper.sin(0.03F * entityIn.ticksExisted + 0.04F);
	        
	        this.Cannon2.rotationPointZ = -3.0F;
	        this.Cannon3.rotationPointZ = -3.0F;
    	}
    	
    	if(!((EntitySalamander)entityIn).isNymph()) {
	        this.RightArm.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
	        this.LeftArm.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.RightLeg.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.LeftLeg.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
	        
	        this.RightArm.rotateAngleZ = 0.9560913642424937F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
	        this.LeftArm.rotateAngleZ = -0.9560913642424937F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.RightLeg.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.LeftLeg.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
    	}
    	else {
            this.leg0_r0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            this.leg0_l0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_r0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_l0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            
            this.leg0_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            this.leg0_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
    	}
    }
}
