package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.floating.EntityFloatingMob;
import com.Fishmod.mod_LavaCow.entities.floating.EntitySeaHag;
import com.Fishmod.mod_LavaCow.entities.floating.EntityWraith;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelAvaton - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelAvaton extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Body_lower_front;
    public ModelRenderer Body_lower_back;
    public ModelRenderer Body_lower_l;
    public ModelRenderer Body_lower_r;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Neck0;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_l_Seg2;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Arm_r_Seg2;
    public ModelRenderer Neck1;
    public ModelRenderer Head;
    public ModelRenderer Hair_front;
    public ModelRenderer Hair_l;
    public ModelRenderer Hair_r;
    public ModelRenderer Bangs;

    public ModelAvaton() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Hair_r = new ModelRenderer(this, 21, 48);
        this.Hair_r.setRotationPoint(-4.0F, -3.0F, -3.0F);
        this.Hair_r.addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, 0.0F);
        this.setRotateAngle(Hair_r, -1.4114477660878142F, -1.5707963267948966F, -1.5707963267948966F);
        this.Body_lower_front = new ModelRenderer(this, 48, 48);
        this.Body_lower_front.setRotationPoint(0.0F, -2.0F, -3.0F);
        this.Body_lower_front.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_front, -0.31869712141416456F, 0.0F, 0.0F);
        this.Hair_front = new ModelRenderer(this, 31, 37);
        this.Hair_front.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Hair_front.addBox(-4.0F, -10.0F, 0.0F, 8, 10, 0, 0.0F);
        this.setRotateAngle(Hair_front, -2.0032889154390916F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 48, 36);
        this.Arm_r_Seg0.setRotationPoint(-5.0F, -2.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
        this.Arm_l_Seg2 = new ModelRenderer(this, 0, 50);
        this.Arm_l_Seg2.mirror = true;
        this.Arm_l_Seg2.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.Arm_l_Seg2.addBox(0.0F, 0.0F, 0.0F, 0, 8, 6, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 48, 22);
        this.Arm_l_Seg0.setRotationPoint(5.0F, -2.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
        this.Body_lower_r = new ModelRenderer(this, 34, 48);
        this.Body_lower_r.setRotationPoint(-4.0F, -2.0F, 0.0F);
        this.Body_lower_r.addBox(-3.0F, 0.0F, 0.0F, 6, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_r, -0.0F, 1.5707963267948966F, 0.0F);
        this.Body_lower_l = new ModelRenderer(this, 34, 48);
        this.Body_lower_l.mirror = true;
        this.Body_lower_l.setRotationPoint(4.0F, -2.0F, 0.0F);
        this.Body_lower_l.addBox(-3.0F, 0.0F, 0.0F, 6, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_l, 0.0F, 1.5707963267948966F, 0.0F);
        this.Body_base = new ModelRenderer(this, 19, 15);
        this.Body_base.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -3.0F, 8, 14, 6, 0.0F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 56, 20);
        this.Arm_l_Seg1.setRotationPoint(1.0F, 7.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 35);
        this.Body_chest.setRotationPoint(0.0F, -8.0F, -0.5F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 10, 5, 5, 0.0F);
        this.setRotateAngle(Body_chest, 0.5009094953223726F, 0.0F, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 45);
        this.Body_waist.setRotationPoint(0.0F, -2.6F, 0.8F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 7, 8, 3, 0.0F);
        this.setRotateAngle(Body_waist, 0.7285004297824331F, 0.0F, -0.045553093477052F);
        this.Hair_l = new ModelRenderer(this, 21, 48);
        this.Hair_l.mirror = true;
        this.Hair_l.setRotationPoint(4.0F, -3.0F, -3.0F);
        this.Hair_l.addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, 0.0F);
        this.setRotateAngle(Hair_l, -1.4114477660878142F, 1.5707963267948966F, 1.5707963267948966F);
        this.Body_lower_back = new ModelRenderer(this, 48, 48);
        this.Body_lower_back.setRotationPoint(0.0F, -2.0F, 3.0F);
        this.Body_lower_back.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_back, 0.27314402793711257F, 0.0F, 0.0F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setRotationPoint(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck1, -1.0471975511965976F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 7, 8, 0.0F);
        this.Arm_r_Seg2 = new ModelRenderer(this, 0, 50);
        this.Arm_r_Seg2.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.Arm_r_Seg2.addBox(0.0F, 0.0F, 0.0F, 0, 8, 6, 0.0F);
        this.Neck0 = new ModelRenderer(this, 0, 26);
        this.Neck0.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Neck0, -0.091106186954104F, 0.0F, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 56, 34);
        this.Arm_r_Seg1.setRotationPoint(-1.0F, 7.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Bangs = new ModelRenderer(this, 31, 37);
        this.Bangs.setRotationPoint(0.0F, -3.0F, -8.0F);
        this.Bangs.addBox(-4.0F, -4.0F, 0.0F, 8, 4, 0, 0.0F);
        this.setRotateAngle(Bangs, 2.5953045977155678F, 0.0F, 0.0F);
        this.Head.addChild(this.Hair_r);
        this.Body_base.addChild(this.Body_lower_front);
        this.Head.addChild(this.Hair_front);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Arm_l_Seg1.addChild(this.Arm_l_Seg2);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Body_base.addChild(this.Body_lower_r);
        this.Body_base.addChild(this.Body_lower_l);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Body_waist.addChild(this.Body_chest);
        this.Body_base.addChild(this.Body_waist);
        this.Head.addChild(this.Hair_l);
        this.Body_base.addChild(this.Body_lower_back);
        this.Neck0.addChild(this.Neck1);
        this.Neck1.addChild(this.Head);
        this.Arm_r_Seg1.addChild(this.Arm_r_Seg2);
        this.Body_chest.addChild(this.Neck0);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Head.addChild(this.Bangs);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5);
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    	EntityFloatingMob Entity = ((EntityFloatingMob)entity);
    	float j = Entity.getSpellTicks() / 30.0F;
    	
    	SwingX_Sin(this.Hair_r, -1.4114477660878142F, ageInTicks, 0.09F, 0.067F, false, 0.25F * (float)Math.PI);
    	SwingX_Sin(this.Hair_l, -1.4114477660878142F, ageInTicks, 0.11F, 0.06F, true, 0.0F);
 
    	if(limbSwingAmount < 0.08F && this.Body_base.rotateAngleX > 0.0F) {
    		this.Body_base.rotateAngleX -= 0.005F;
    		this.Head.rotateAngleX += 0.005F;
    	} else if(limbSwingAmount >= 0.08F && this.Body_base.rotateAngleX < 0.35F) {
    		this.Body_base.rotateAngleX += 0.005F;
    		this.Head.rotateAngleX -= 0.005F;
    	}
    	
    	if (entity instanceof EntityWraith || entity instanceof EntitySeaHag) {   		
    		this.Body_waist.rotateAngleX = 0.1613F;
    		this.Body_waist.rotateAngleZ = 0.0F;
    		this.Body_chest.rotateAngleX = 0.1955F;
    		this.Neck0.rotateAngleX = 0.2143F;
    		this.Head.setRotationPoint(0.0F, -4.0F, 2.0F);
    		this.Head_Looking(this.Head, 0.5672F, 0.0F, netHeadYaw, headPitch);
    		SwingX_Sin(this.Hair_front, -2.8323F, ageInTicks, 0.12F, 0.07F, false, 0.0F);
    	} else {		
    		this.Body_waist.rotateAngleX = 0.7285004297824331F; 	
    		this.Body_waist.rotateAngleZ = -0.045553093477052F;
    		this.Body_chest.rotateAngleX = 0.5009094953223726F;
    		this.Neck0.rotateAngleX = -0.091106186954104F;
    		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		SwingX_Sin(this.Hair_front, -2.0032889154390916F, ageInTicks, 0.12F, 0.07F, false, 0.0F);
    	}
    		
    	SwingX_Sin(this.Body_lower_front, -0.5369F, ageInTicks, 0.3F, 0.08F, true, 0.0F);
    	SwingZ_Sin(this.Body_lower_r, 0.4894F, ageInTicks, 0.24F, 0.07F, false, 0.25F * (float)Math.PI);
    	SwingZ_Sin(this.Body_lower_l, -0.4894F, ageInTicks, 0.27F, 0.09F, true, 0.45F * (float)Math.PI);
    	SwingX_Sin(this.Body_lower_back, 0.5786F, ageInTicks, 0.38F, 0.06F, false, 0.70F * (float)Math.PI);
    	
    	SwingY_Sin(this.Arm_r_Seg2, 0.0F, ageInTicks, 0.22F, 0.08F, false, 0.0F);
    	SwingY_Sin(this.Arm_l_Seg2, 0.0F, ageInTicks, 0.18F, 0.06F, true, 0.0F);
 	
    	if(Entity.isSpellcasting()) {
    		Arm_r_Seg0.rotateAngleX = GradientAnimation(1.1046188995661776F, -0.6155776351678833F, j);
    		Arm_l_Seg0.rotateAngleX = GradientAnimation(1.1046188995661776F, -0.6155776351678833F, j);
    		Arm_r_Seg0.rotateAngleY = 2.111673824703684F;
    		Arm_l_Seg0.rotateAngleY = -2.111673824703684F;
    		Arm_r_Seg0.rotateAngleZ = 1.5627678282146893F;
    		Arm_l_Seg0.rotateAngleZ = -1.5627678282146893F;
    		if(this.Body_base.rotationPointY > 5.0F)
    			this.Body_base.rotationPointY -= 0.17F;
    	} else if(Entity.isAggressive()) {
    		this.setRotateAngle(Arm_r_Seg0, -1.9577358219620393F, 0.0F, 0.091106186954104F);
    		this.setRotateAngle(Arm_l_Seg0, -1.8212510744560826F, 0.0F, 0.045553093477052F);
    		if(this.Body_base.rotationPointY < 10.0F)
    			this.Body_base.rotationPointY += 0.17F;
    		else
    			this.Body_base.rotationPointY = 11.0F + MathHelper.sin(0.08F * ageInTicks);
    	} else {
    		this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
    		if(this.Body_base.rotationPointY < 10.0F)
    			this.Body_base.rotationPointY += 0.17F;
    		else
    			this.Body_base.rotationPointY = 11.0F + MathHelper.sin(0.08F * ageInTicks);
    	}
    }
}
