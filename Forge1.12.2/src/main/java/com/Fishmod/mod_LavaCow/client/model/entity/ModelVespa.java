package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EntityFlyingMob;
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

    public ModelVespa() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Waist = new ModelRenderer(this, 100, 50);
        this.Waist.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.Waist.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 8, 0.0F);
        this.leg_r_2 = new ModelRenderer(this, 56, 0);
        this.leg_r_2.setRotationPoint(-4.0F, 6.0F, -2.5F);
        this.leg_r_2.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_r_2, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
        this.Mouth = new ModelRenderer(this, 24, 22);
        this.Mouth.setRotationPoint(0.0F, 2.0F, -12.0F);
        this.Mouth.addBox(-2.0F, -3.0F, -2.0F, 4, 6, 2, 0.0F);
        this.leg_l_2 = new ModelRenderer(this, 56, 0);
        this.leg_l_2.setRotationPoint(4.0F, 6.0F, -2.5F);
        this.leg_l_2.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_l_2, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
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
        this.setRotateAngle(Wing_1_l, 0.0F, -0.40980330836826856F, 0.0F);
        this.Wing_0_l = new ModelRenderer(this, 0, 36);
        this.Wing_0_l.mirror = true;
        this.Wing_0_l.setRotationPoint(6.0F, -4.0F, -8.0F);
        this.Wing_0_l.addBox(0.0F, 0.0F, -1.0F, 24, 0, 8, 0.0F);
        this.setRotateAngle(Wing_0_l, 0.0F, 0.22759093446006054F, 0.0F);
        this.Antennae_r = new ModelRenderer(this, 0, 22);
        this.Antennae_r.setRotationPoint(-2.5F, -3.0F, -10.0F);
        this.Antennae_r.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Antennae_r, -0.27314402793711257F, 0.0F, 0.0F);
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
        this.setRotateAngle(UAbdomen2, -0.22759093446006054F, 0.0F, 0.0F);
        this.Eye_r = new ModelRenderer(this, 8, 22);
        this.Eye_r.setRotationPoint(-4.0F, -1.0F, -10.0F);
        this.Eye_r.addBox(-4.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.Throax_base = new ModelRenderer(this, 0, 44);
        this.Throax_base.mirror = true;
        this.Throax_base.setRotationPoint(0.0F, 12.0F, 3.5F);
        this.Throax_base.addBox(-6.0F, -6.0F, -4.0F, 12, 12, 8, 0.0F);
        this.Stinger = new ModelRenderer(this, 56, 26);
        this.Stinger.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Stinger.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.leg_l_1 = new ModelRenderer(this, 56, 0);
        this.leg_l_1.setRotationPoint(4.0F, 6.0F, -3.5F);
        this.leg_l_1.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_l_1, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
        this.UAbdomen3 = new ModelRenderer(this, 100, 28);
        this.UAbdomen3.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.UAbdomen3.addBox(-4.0F, -6.0F, 0.0F, 8, 8, 6, 0.0F);
        this.setRotateAngle(UAbdomen3, -0.22759093446006054F, 0.0F, 0.0F);
        this.leg_r_0 = new ModelRenderer(this, 56, 0);
        this.leg_r_0.setRotationPoint(-4.0F, 6.0F, -4.5F);
        this.leg_r_0.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_r_0, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
        this.Wing_0_r = new ModelRenderer(this, 0, 36);
        this.Wing_0_r.setRotationPoint(-6.0F, -4.0F, -8.0F);
        this.Wing_0_r.addBox(-24.0F, 0.0F, -1.0F, 24, 0, 8, 0.0F);
        this.setRotateAngle(Wing_0_r, 0.0F, -0.22759093446006054F, 0.0F);
        this.Antennae_l = new ModelRenderer(this, 0, 22);
        this.Antennae_l.setRotationPoint(2.5F, -3.0F, -10.0F);
        this.Antennae_l.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Antennae_l, -0.27314402793711257F, 0.0F, 0.0F);
        this.UAbdomen4 = new ModelRenderer(this, 84, 28);
        this.UAbdomen4.setRotationPoint(0.0F, -1.0F, 5.0F);
        this.UAbdomen4.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(UAbdomen4, -0.22759093446006054F, 0.0F, 0.0F);
        this.Throax_0 = new ModelRenderer(this, 0, 44);
        this.Throax_0.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Throax_0.addBox(-6.0F, -6.0F, -4.0F, 12, 12, 8, 0.0F);
        this.setRotateAngle(Throax_0, 0.27314402793711257F, 0.0F, 0.0F);
        this.leg_r_1 = new ModelRenderer(this, 56, 0);
        this.leg_r_1.setRotationPoint(-4.0F, 6.0F, -3.5F);
        this.leg_r_1.addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_r_1, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
        this.UAbdomen1 = new ModelRenderer(this, 66, 48);
        this.UAbdomen1.setRotationPoint(0.0F, 1.0F, 5.0F);
        this.UAbdomen1.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 6, 0.0F);
        this.setRotateAngle(UAbdomen1, 0.22759093446006054F, 0.0F, 0.0F);
        this.Wing_1_r = new ModelRenderer(this, 0, 30);
        this.Wing_1_r.setRotationPoint(-6.0F, -4.0F, -5.0F);
        this.Wing_1_r.addBox(-16.0F, 0.0F, -1.0F, 16, 0, 6, 0.0F);
        this.setRotateAngle(Wing_1_r, 0.0F, 0.40980330836826856F, 0.0F);
        this.leg_l_0 = new ModelRenderer(this, 56, 0);
        this.leg_l_0.setRotationPoint(4.0F, 6.0F, -4.5F);
        this.leg_l_0.addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(leg_l_0, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
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
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Throax_base.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
  	  	float vibrate_rate = 0.5F;
    	
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;

    	this.Wing_0_r.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks);
    	this.Wing_1_r.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks + (float)Math.PI);
    	this.Wing_0_l.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks + (float)Math.PI);
    	this.Wing_1_l.rotateAngleZ = 0.5F * MathHelper.sin(4.0F * ageInTicks);
      
    	this.Throax_base.rotationPointY = 7.0F + 5.0F * MathHelper.sin(ageInTicks * vibrate_rate);
    	
    	if(this.state.equals(ModelFlyingBase.State.FLYING)) {
	    	this.Waist.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen4.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.Stinger.rotateAngleX = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	
	    	this.setRotateAngle(leg_r_0, 0.0F, 0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, -1.6845917940249266F);
	    	this.setRotateAngle(leg_r_1, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
	    	this.setRotateAngle(leg_r_2, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
	    	this.setRotateAngle(leg_l_0, 0.0F, -0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, 1.6845917940249266F);
	    	this.setRotateAngle(leg_l_1, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
	    	this.setRotateAngle(leg_l_2, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);
    	}
    	else if(this.state.equals(ModelFlyingBase.State.ATTACKING)) {
	    	this.Waist.rotateAngleX = -0.18F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	this.UAbdomen1.rotateAngleX = -0.18F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	this.UAbdomen2.rotateAngleX = -0.20F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	this.UAbdomen3.rotateAngleX = -0.20F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	this.UAbdomen4.rotateAngleX = -0.22F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	this.Stinger.rotateAngleX = -0.24F * MathHelper.abs(10 - ((EntityFlyingMob)entityIn).getAttackTimer());
	    	
	    	this.setRotateAngle(leg_r_0, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
	    	this.setRotateAngle(leg_r_1, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(leg_r_2, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(leg_l_0, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
	    	this.setRotateAngle(leg_l_1, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
	    	this.setRotateAngle(leg_l_2, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
    	}
    	else {
	    	this.Waist.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen4.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.Stinger.rotateAngleX = -0.028F * (10 - this.HoverCounter) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	
	    	this.setRotateAngle(leg_r_0, 0.0F, 0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, -1.6845917940249266F);
	    	this.setRotateAngle(leg_r_1, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
	    	this.setRotateAngle(leg_r_2, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
	    	this.setRotateAngle(leg_l_0, 0.0F, -0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, 1.6845917940249266F);
	    	this.setRotateAngle(leg_l_1, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
	    	this.setRotateAngle(leg_l_2, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);
    	}
    }
}
