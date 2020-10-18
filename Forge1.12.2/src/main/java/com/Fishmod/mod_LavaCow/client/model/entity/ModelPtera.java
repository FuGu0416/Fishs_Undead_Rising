package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EntityFlyingMob;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelPtera - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelPtera extends ModelFlyingBase {
    public ModelRenderer Body_Base;
    public ModelRenderer Wing_r;
    public ModelRenderer Wing_l;
    public ModelRenderer leg_r;
    public ModelRenderer leg_l;
    public ModelRenderer Body1;
    public ModelRenderer Neck;
    public ModelRenderer Wing1_r;
    public ModelRenderer Wing1_l;
    public ModelRenderer calf_r;
    public ModelRenderer calf_l;
    public ModelRenderer Tail0;
    public ModelRenderer Tail1;
    public ModelRenderer Head;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_Teeth;

    public ModelPtera() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body1 = new ModelRenderer(this, 0, 0);
        this.Body1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Body1.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Body1, 0.091106186954104F, 0.0F, 0.0F);
        this.Wing_r = new ModelRenderer(this, 0, 8);
        this.Wing_r.setRotationPoint(-3.0F, -2.0F, -4.0F);
        this.Wing_r.addBox(-9.0F, 0.0F, -2.0F, 9, 1, 7, 0.0F);
        this.Tail0 = new ModelRenderer(this, 16, 0);
        this.Tail0.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail0.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Tail0, 0.18203784098300857F, 0.0F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 46, 5);
        this.Tail1.setRotationPoint(0.0F, -1.0F, 3.0F);
        this.Tail1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Tail1, 0.18203784098300857F, 0.0F, 0.0F);
        this.Wing_l = new ModelRenderer(this, 0, 8);
        this.Wing_l.mirror = true;
        this.Wing_l.setRotationPoint(3.0F, -2.0F, -4.0F);
        this.Wing_l.addBox(0.0F, 0.0F, -2.0F, 9, 1, 7, 0.0F);
        this.Wing1_l = new ModelRenderer(this, 0, 8);
        this.Wing1_l.setRotationPoint(9.0F, 0.0F, -1.0F);
        this.Wing1_l.addBox(0.0F, 0.0F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing1_l, 0.0F, 0.0F, 0.5918411493512771F);
        this.Head = new ModelRenderer(this, 30, 18);
        this.Head.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.Head.addBox(-3.0F, -2.0F, -8.0F, 6, 6, 8, 0.0F);
        this.Jaw_Teeth = new ModelRenderer(this, 25, 9);
        this.Jaw_Teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw_Teeth.addBox(-3.0F, -1.0F, -7.0F, 6, 1, 5, 0.0F);
        this.leg_r = new ModelRenderer(this, 54, 0);
        this.leg_r.setRotationPoint(-3.0F, 2.0F, 2.0F);
        this.leg_r.addBox(-1.0F, -2.0F, -1.5F, 2, 4, 3, 0.0F);
        this.setRotateAngle(leg_r, 0.16580627893946132F, 0.0F, 0.0F);
        this.calf_r = new ModelRenderer(this, 46, 0);
        this.calf_r.setRotationPoint(0.0F, 1.7F, -1.0F);
        this.calf_r.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2, 0.0F);
        this.setRotateAngle(calf_r, 0.3020000679588679F, 0.0F, 0.0F);
        this.Body_Base = new ModelRenderer(this, 0, 17);
        this.Body_Base.setRotationPoint(0.0F, 17.0F, -1.0F);
        this.Body_Base.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 9, 0.0F);
        this.setRotateAngle(Body_Base, -0.5009094953223726F, 0.0F, 0.0F);
        this.Wing1_r = new ModelRenderer(this, 0, 8);
        this.Wing1_r.setRotationPoint(-9.0F, 0.0F, -1.0F);
        this.Wing1_r.addBox(-9.0F, 0.0F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing1_r, 0.0F, 0.0F, -0.5918411493512771F);
        this.Neck = new ModelRenderer(this, 21, 16);
        this.Neck.setRotationPoint(0.0F, -1.0F, -5.0F);
        this.Neck.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(Neck, 0.5009094953223726F, 0.0F, 0.0F);
        this.calf_l = new ModelRenderer(this, 46, 0);
        this.calf_l.mirror = true;
        this.calf_l.setRotationPoint(0.0F, 1.7F, -1.0F);
        this.calf_l.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2, 0.0F);
        this.setRotateAngle(calf_l, 0.30194196059501904F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 24, 0);
        this.Jaw.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Jaw.addBox(-3.0F, 0.0F, -7.0F, 6, 1, 7, 0.0F);
        this.setRotateAngle(Jaw, 0.4553564018453205F, 0.0F, 0.0F);
        this.leg_l = new ModelRenderer(this, 54, 0);
        this.leg_l.mirror = true;
        this.leg_l.setRotationPoint(3.0F, 2.0F, 2.0F);
        this.leg_l.addBox(-1.0F, -2.0F, -1.5F, 2, 4, 3, 0.0F);
        this.setRotateAngle(leg_l, 0.16580627893946132F, 0.0F, 0.0F);
        this.Body_Base.addChild(this.Body1);
        this.Body_Base.addChild(this.Wing_r);
        this.Body1.addChild(this.Tail0);
        this.Tail0.addChild(this.Tail1);
        this.Body_Base.addChild(this.Wing_l);
        this.Wing_l.addChild(this.Wing1_l);
        this.Neck.addChild(this.Head);
        this.Jaw.addChild(this.Jaw_Teeth);
        this.Body_Base.addChild(this.leg_r);
        this.leg_r.addChild(this.calf_r);
        this.Wing_r.addChild(this.Wing1_r);
        this.Body_Base.addChild(this.Neck);
        this.leg_l.addChild(this.calf_l);
        this.Head.addChild(this.Jaw);
        this.Body_Base.addChild(this.leg_l);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(0.0F, 1.0F, 0.0F);
    	this.Body_Base.render(f5);
    	GlStateManager.popMatrix();
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	float flap = MathHelper.sin(ageInTicks * 0.5F);
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    	
    	if(entityIn.getPassengers().isEmpty()) {
    		this.Body_Base.rotationPointY = 5.0F * flap;
    	}
    	else {
    		this.Body_Base.rotationPointY = 0.0F;
    	}
    	
    	this.Jaw.rotateAngleX = 0.3F + 0.15F * flap;
    	
    	this.Wing_r.rotateAngleZ = -0.4F + 0.74F * flap;
        this.Wing_l.rotateAngleZ = 0.4F - 0.74F * flap;
        
        this.Wing1_r.rotateAngleZ = 0.36F * flap;
        this.Wing1_l.rotateAngleZ = -0.36F * flap;
        
        this.Tail1.rotateAngleX = 0.26F - 0.18F * flap;
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float flap = MathHelper.sin(entityIn.ticksExisted * 0.5F);
    	
    	if(this.state.equals(ModelFlyingBase.State.FLYING)) {
        	this.Body1.rotateAngleX = 0.091106186954104F;
        	this.Tail0.rotateAngleX = 0.26F - 0.18F * flap;
        	
        	if(entityIn.getPassengers().isEmpty()) {
	        	this.leg_r.rotateAngleX = 1.0559241974565694F;
		        this.leg_l.rotateAngleX = 1.0559241974565694F;
        	}
        	else {
        		this.leg_r.rotateAngleX = 0.619591884457987F;
    	        this.leg_l.rotateAngleX = 0.619591884457987F; 
        	}
        		
        }
        else if(this.state.equals(ModelFlyingBase.State.ATTACKING)) {
        	this.Head.rotateAngleX = -0.5235987755982988F * 0.05F * ((EntityFlyingMob)entityIn).getAttackTimer();
        	this.Jaw.rotateAngleX = 1.415287490442202F * 0.05F * ((EntityFlyingMob)entityIn).getAttackTimer();
        }
        else {
        	this.Body1.rotateAngleX = -0.36267941856442165F;
        	this.Tail0.rotateAngleX = -0.18448130193580065F - 0.18F * flap;
        	
	        this.leg_r.rotateAngleX = 0.619591884457987F;
	        this.leg_l.rotateAngleX = 0.619591884457987F;        	
        }
    }
}
