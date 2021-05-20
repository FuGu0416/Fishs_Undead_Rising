package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelScarecrow - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelScarecrow extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Body_upper;
    public ModelRenderer leg_l_0;
    public ModelRenderer leg_r_0;
    public ModelRenderer arm_l_0;
    public ModelRenderer arm_r_0;
    public ModelRenderer Neck;
    public ModelRenderer arm_l_1;
    public ModelRenderer arm_r_1;
    public ModelRenderer Head;
    public ModelRenderer Head_stem;
    public ModelRenderer Head_tooth;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_tooth;
    public ModelRenderer leg_l_1;
    public ModelRenderer leg_l_2;
    public ModelRenderer leg_r_1;
    public ModelRenderer leg_r_2;
    public ModelRenderer scepter_base;
    public ModelRenderer scepter_blade;
    /**
     * Non-Pumpkin variant
     */
    public ModelRenderer Head2;
    public ModelRenderer Stem2;
    /**
     * Plague Doctor variant
     */
    public ModelRenderer Neck2;
    public ModelRenderer scarf;
    public ModelRenderer scarf_back;
    public ModelRenderer shoulder_l;
    public ModelRenderer shoulder_r;
    public ModelRenderer Head3;
    public ModelRenderer Beak;
    public ModelRenderer Hat;
    public ModelRenderer Beak1;
    
    public ModelScarecrow() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.arm_l_1 = new ModelRenderer(this, 10, 30);
        this.arm_l_1.mirror = true;
        this.arm_l_1.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.arm_l_1.addBox(-1.0F, -1.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(arm_l_1, -0.8196066167365371F, 0.22759093446006054F, 0.4553564018453205F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -1.0F, -3.0F);
        this.Head.addBox(-4.0F, -5.0F, -8.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(Head, 0.0F, 0.0F, 0.0F);//-0.22759093446006054F);
        this.Body_upper = new ModelRenderer(this, 40, 9);
        this.Body_upper.setRotationPoint(0.0F, -8.1F, 1.3F);
        this.Body_upper.addBox(-4.0F, -10.0F, -2.0F, 8, 10, 4, 0.0F);
        this.setRotateAngle(Body_upper, 1.1838568316277536F, 0.0F, 0.0F);
        this.leg_l_1 = new ModelRenderer(this, 0, 30);
        this.leg_l_1.mirror = true;
        this.leg_l_1.setRotationPoint(0.0F, 9.0F, 0.1F);
        this.leg_l_1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_l_1, 0.7740535232594852F, 0.0F, 0.0F);
        this.Head_tooth = new ModelRenderer(this, 32, 25);
        this.Head_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head_tooth.addBox(-4.0F, -1.0F, -8.0F, 8, 2, 8, 0.0F);
        this.leg_l_2 = new ModelRenderer(this, 0, 44);
        this.leg_l_2.mirror = true;
        this.leg_l_2.setRotationPoint(0.0F, 9.0F, 0.1F);
        this.leg_l_2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_l_2, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_r_1 = new ModelRenderer(this, 10, 30);
        this.arm_r_1.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.arm_r_1.addBox(-1.0F, -1.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(arm_r_1, -0.8196066167365371F, -0.22759093446006054F, -0.4553564018453205F);
        this.arm_r_0 = new ModelRenderer(this, 10, 30);
        this.arm_r_0.setRotationPoint(-5.0F, -6.0F, -1.0F);
        this.arm_r_0.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(arm_r_0, -0.36425021489121656F, 0.31869712141416456F, 0.10000736613927509F);
        this.leg_r_1 = new ModelRenderer(this, 0, 30);
        this.leg_r_1.setRotationPoint(0.0F, 9.0F, 0.1F);
        this.leg_r_1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_r_1, 0.7740535232594852F, 0.0F, 0.0F);
        this.Head_stem = new ModelRenderer(this, 0, 0);
        this.Head_stem.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.Head_stem.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Head_stem, -0.5462880558742251F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.6373942428283291F, 0.0F, 0.0F);
        this.leg_r_2 = new ModelRenderer(this, 0, 44);
        this.leg_r_2.setRotationPoint(0.0F, 9.0F, 0.1F);
        this.leg_r_2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_r_2, -0.5009094953223726F, 0.0F, 0.0F);
        this.leg_r_0 = new ModelRenderer(this, 0, 30);
        this.leg_r_0.setRotationPoint(-2.5F, -0.5F, 0.1F);
        this.leg_r_0.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_r_0, 0.091106186954104F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 48, 2);
        this.Neck.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Neck.addBox(-3.0F, -1.5F, -3.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(Neck, -0.8196066167365371F, 0.0F, 0.0F);
        this.leg_l_0 = new ModelRenderer(this, 0, 30);
        this.leg_l_0.mirror = true;
        this.leg_l_0.setRotationPoint(2.5F, -0.5F, 0.1F);
        this.leg_l_0.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_l_0, 0.091106186954104F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 40, 9);
        this.Body_base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body_base.addBox(-4.0F, -10.0F, -2.0F, 8, 10, 4, 0.0F);
        this.setRotateAngle(Body_base, -0.36425021489121656F, 0.0F, 0.0F);
        this.Jaw_tooth = new ModelRenderer(this, 32, 38);
        this.Jaw_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw_tooth.addBox(-4.0F, -2.0F, -8.0F, 8, 2, 8, 0.0F);
        this.arm_l_0 = new ModelRenderer(this, 10, 30);
        this.arm_l_0.mirror = true;
        this.arm_l_0.setRotationPoint(5.0F, -6.0F, -1.0F);
        this.arm_l_0.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(arm_l_0, -0.36425021489121656F, -0.31869712141416456F, -0.10000736613927509F);
        this.scepter_base = new ModelRenderer(this, 0, 33);
        this.scepter_base.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.scepter_base.addBox(-0.5F, -0.5F, -27.5F, 1, 1, 30, 0.0F);
        this.scepter_blade = new ModelRenderer(this, 10, 37);
        this.scepter_blade.setRotationPoint(0.0F, 0.5F, -25.0F);
        this.scepter_blade.addBox(0.0F, 0.0F, -2.5F, 0, 16, 10, 0.0F);
        /**
         * Non-Pumpkin variant
         */
        this.Head2 = new ModelRenderer(this, 40, 49);
        this.Head2.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.Head2.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.setRotateAngle(Head2, -0.6829473363053812F, 0.0F, -0.22759093446006054F);
        this.Stem2 = new ModelRenderer(this, 33, 48);
        this.Stem2.setRotationPoint(0.0F, -7.6F, 2.3F);
        this.Stem2.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Stem2, -0.8196066167365371F, 0.0F, 0.0F);
        /**
         * Plague Doctor variant
         */
        this.shoulder_l = new ModelRenderer(this, 64, 51);
        this.shoulder_l.mirror = true;
        this.shoulder_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shoulder_l.addBox(-1.5F, -3.5F, -3.0F, 5, 6, 6, 0.0F);
        this.shoulder_r = new ModelRenderer(this, 64, 51);
        this.shoulder_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shoulder_r.addBox(-3.5F, -3.5F, -3.0F, 5, 6, 6, 0.0F);
        this.Beak = new ModelRenderer(this, 64, 26);
        this.Beak.setRotationPoint(0.0F, 2.5F, -2.5F);
        this.Beak.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Beak, 0.22759093446006054F, 0.0F, 0.0F);
        this.Head3 = new ModelRenderer(this, 64, 10);
        this.Head3.setRotationPoint(0.0F, 0.5F, -5.0F);
        this.Head3.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Head3, -0.6829473363053812F, 0.0F, 0.0F);
        this.Neck2 = new ModelRenderer(this, 64, 0);
        this.Neck2.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Neck2.addBox(-2.5F, -1.5F, -6.0F, 5, 3, 6, 0.0F);
        this.setRotateAngle(Neck2, -1.4570008595648662F, 0.0F, 0.0F);
        this.scarf = new ModelRenderer(this, 64, 36);
        this.scarf.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.scarf.addBox(-6.0F, 0.0F, -3.0F, 12, 8, 6, 0.0F);
        this.setRotateAngle(scarf, -0.31869712141416456F, 0.0F, 0.0F);
        this.Beak1 = new ModelRenderer(this, 80, 28);
        this.Beak1.setRotationPoint(0.0F, 3.2F, -0.7F);
        this.Beak1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Beak1, 0.5009094953223726F, 0.0F, 0.0F);
        this.scarf_back = new ModelRenderer(this, 64, 44);
        this.scarf_back.mirror = true;
        this.scarf_back.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.scarf_back.addBox(-6.0F, 2.5F, -8.3F, 12, 0, 6, 0.0F);
        this.setRotateAngle(scarf_back, 1.593485607070823F, 0.0F, 0.0F);
        this.Hat = new ModelRenderer(this, 90, 0);
        this.Hat.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.Hat.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 1, 0.0F);
        
        this.arm_l_0.addChild(this.arm_l_1);
        this.Neck.addChild(this.Head);
        this.Body_base.addChild(this.Body_upper);
        this.leg_l_0.addChild(this.leg_l_1);
        this.Head.addChild(this.Head_tooth);
        this.leg_l_1.addChild(this.leg_l_2);
        this.arm_r_0.addChild(this.arm_r_1);
        this.Body_upper.addChild(this.arm_r_0);
        this.leg_r_0.addChild(this.leg_r_1);
        this.Head.addChild(this.Head_stem);
        this.Head.addChild(this.Jaw);
        this.leg_r_1.addChild(this.leg_r_2);
        this.Body_base.addChild(this.leg_r_0);
        this.Body_upper.addChild(this.Neck);
        this.Body_base.addChild(this.leg_l_0);
        this.Jaw.addChild(this.Jaw_tooth);
        this.Body_upper.addChild(this.arm_l_0);
        this.arm_l_1.addChild(this.scepter_base);
        this.scepter_base.addChild(this.scepter_blade);
        /**
         * Non-Pumpkin variant
         */
        this.Body_upper.addChild(this.Head2);
        this.Head2.addChild(this.Stem2);
        /**
         * Plague Doctor variant
         */
        this.arm_l_0.addChild(this.shoulder_l);
        this.arm_r_0.addChild(this.shoulder_r);
        this.Head3.addChild(this.Beak);
        this.Neck2.addChild(this.Head3);
        this.Body_upper.addChild(this.Neck2);
        this.Body_upper.addChild(this.scarf);
        this.Beak.addChild(this.Beak1);
        this.Body_upper.addChild(this.scarf_back);
        this.Head3.addChild(this.Hat);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5);
    }

    
    public void postRenderArm(float scale, EnumHandSide side)
    {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.arm_l_1 : this.arm_r_1;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	switch(((EntityScarecrow) entityIn).getSkin()) { 
		        case 0: 
		        	this.Neck.isHidden = false;
		        	this.Head2.isHidden = true;
		        	this.Neck2.isHidden = true;
		        	this.scarf.isHidden = true;
		        	this.scarf_back.isHidden = true;
		        	this.shoulder_l.isHidden = true;
		        	this.shoulder_r.isHidden = true;
		        	break; 
		        case 1: 
		        	this.Neck.isHidden = true;
		        	this.Head2.isHidden = false; 
		        	this.Neck2.isHidden = true;
		        	this.scarf.isHidden = true;
		        	this.scarf_back.isHidden = true;
		        	this.shoulder_l.isHidden = true;
		        	this.shoulder_r.isHidden = true;
		            break; 
		        case 2: 
		        	this.Neck.isHidden = true;
		        	this.Head2.isHidden = true; 
		        	this.Neck2.isHidden = false;
		        	this.scarf.isHidden = false;
		        	this.scarf_back.isHidden = false;
		        	this.shoulder_l.isHidden = false;
		        	this.shoulder_r.isHidden = false;
		            break; 
		        default: 
		        	break;
    	}
    	
        if(!((EntityScarecrow) entityIn).isSilent()) {
	    	
        	switch(((EntityScarecrow) entityIn).getSkin()) {
        		case 0:
    		    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head.rotateAngleX = headPitch * 0.017453292F;
    		        this.Head.rotateAngleZ = 0.22759093446006054F;
    		        
    		        this.Head.rotationPointY = -1.0F + (-0.15F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));
        			break;
        		case 1:
    		        this.Head2.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head2.rotateAngleX = -0.6829473363053812F + headPitch * 0.017453292F;
        			break;
        		case 2:
    		        this.Head3.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head3.rotateAngleX = -0.6829473363053812F + headPitch * 0.017453292F;
        			break;
        		default:
        			break;
        	}
	    		    	
	    	this.leg_r_0.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
	        this.leg_l_0.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        
	        this.setRotateAngle(leg_r_1, 0.7740535232594852F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_r_2, -0.5009094953223726F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_l_1, 0.7740535232594852F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_l_2, -0.5009094953223726F, 0.0F, 0.0F);
	        
	        this.scepter_base.isHidden = false;	        
	    }
        else {
        	this.setRotateAngle(Head, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head2, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head3, 0.12F, 0.0F, -0.22759093446006054F);
        	
        	this.setRotateAngle(arm_l_0, -0.136659280431156F, 0.0F, -1.5481070465189704F);
        	this.setRotateAngle(arm_l_1, -0.27314402793711257F, 0.0F, 0.0F);
        	this.setRotateAngle(arm_r_0, -0.136659280431156F, 0.0F, 1.5481070465189704F);
        	this.setRotateAngle(arm_r_1, -0.27314402793711257F, 0.0F, 0.0F);
        	
        	this.setRotateAngle(leg_l_0, 0.36425021489121656F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_l_1, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_l_2, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_0, 0.36425021489121656F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_1, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_2, 0.0F, 0.0F, 0.0F);
        	
        	this.scepter_base.isHidden = true;     	
        }
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
        float i = ((EntityScarecrow) entityIn).getAttackTimer() / 15.0F;
        
    	if(((EntityScarecrow) entityIn).getSkin() == 0) {
	        if(((EntityScarecrow) entityIn).isAggressive())
	        	this.Jaw.rotateAngleX = 0.67F;
	        else 
	        	this.Jaw.rotateAngleX = 0.22F + (-0.05F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));
    	}
        
	    if(i > 0) {
		    	this.setRotateAngle(arm_r_0, -0.36425021489121656F, 0.31869712141416456F, 0.10000736613927509F);
	        	this.setRotateAngle(arm_r_1, -0.8196066167365371F, -0.22759093446006054F, -0.4553564018453205F);
	        	
	        	if(((EntityScarecrow)entityIn).AttackStance == (byte)4) {
					/*
					 * Normal Attack
					 */
	        		this.Body_upper.rotateAngleX = GradientAnimation(0.0F, 1.1838568316277536F, i);
	        		this.Head.rotateAngleX = GradientAnimation(entityIn.rotationPitch * 0.017453292F, entityIn.rotationPitch * 0.017453292F - 1.1838568316277536F, i);
	
		        	this.arm_l_0.rotateAngleX = GradientAnimation(-3.096039560112741F, -1.2292353921796064F, i);
		        	this.arm_l_0.rotateAngleZ = GradientAnimation(0.31869712141416456F, 0.4553564018453205F, i);
		        	
		        	this.arm_l_1.rotateAngleX = GradientAnimation(-0.8196066167365371F, 0.091106186954104F, i);
		        	
		        	this.setRotateAngle(scepter_base, 0.0F, 0.0F, 0.0F);        			        		
	        	}
	        	else {
					/*
					 * Swipe Attack
					 */
	        		this.setRotateAngle(arm_l_0, -0.36425021489121656F, -0.31869712141416456F, -0.40980330836826856F);
	        		this.setRotateAngle(arm_l_1, -1.6845917940249266F, 0.22759093446006054F, 3.141592653589793F);
	        		this.setRotateAngle(scepter_base, 1.8212510744560826F, 1.6390387005478748F, 1.6390387005478748F);
	        		
	        		this.Body_upper.rotateAngleZ = GradientAnimation(-0.6F, 0.6F, i);	                		
	        		this.arm_l_0.rotateAngleX = GradientAnimation(-0.36425021489121656F, -0.9105382707654417F, i);
	        		this.arm_l_1.rotateAngleY = GradientAnimation(0.22759093446006054F, -1.1383037381507017F, i);
	        		this.scepter_base.rotateAngleZ = GradientAnimation(1.6390387005478748F, 0.18203784098300857F, i);
	        	}
	        }
	        else {
	        	this.setRotateAngle(Body_upper, 1.1838568316277536F, 0.0F, -0.18203784098300857F);
	        	
	        	this.setRotateAngle(arm_r_0, -0.36425021489121656F, 0.31869712141416456F, 0.10000736613927509F);
	        	this.setRotateAngle(arm_r_1, -2.504198410761464F, -0.22759093446006054F, -0.22759093446006054F);
	        	this.setRotateAngle(arm_l_0, -0.36425021489121656F, -0.31869712141416456F, -0.10000736613927509F);
	        	this.setRotateAngle(arm_l_1, -1.6845917940249266F, 0.22759093446006054F, 0.4553564018453205F);        	
	        	
	        	this.arm_r_0.rotationPointY = -6.0F + (-0.55F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
	        	this.arm_l_0.rotationPointY = -6.0F + (-0.55F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));        	
	        	        
	        	this.setRotateAngle(scepter_base, 1.3203415791337103F, 0.27314402793711257F, 1.6390387005478748F);
	        }
		}
}
