package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityPingu;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelPingu - Mojang, edited by Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelPingu extends FishModelBase {
    public ModelRenderer body;
    public ModelRenderer wing_l;
    public ModelRenderer wing_r;
    public ModelRenderer tail;
    public ModelRenderer leg_l;
    public ModelRenderer leg_r;
    public ModelRenderer Feathers;
    public ModelRenderer core;
    public ModelRenderer Ice;
    public ModelRenderer Ice1;
    public ModelRenderer Ice2;
    public ModelRenderer head;
    public ModelRenderer paw_l;
    public ModelRenderer paw_r;
    public ModelRenderer beak_u;
    public ModelRenderer beak_d;
    public ModelRenderer head_ice;

    public ModelPingu() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.paw_r = new ModelRenderer(this, 8, 14);
        this.paw_r.setRotationPoint(0.0F, 3.0F, 0.5F);
        this.paw_r.addBox(-1.0F, 0.0F, -3.0F, 2, 0, 3, 0.0F);
        this.leg_l = new ModelRenderer(this, 11, 9);
        this.leg_l.mirror = true;
        this.leg_l.setRotationPoint(2.5F, -1.7F, 1.3F);
        this.leg_l.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.beak_u = new ModelRenderer(this, 0, 8);
        this.beak_u.setRotationPoint(0.0F, -1.5F, -4.0F);
        this.beak_u.addBox(-0.5F, -1.0F, -4.0F, 1, 2, 4, 0.0F);
        this.head_ice = new ModelRenderer(this, 0, 20);
        this.head_ice.setRotationPoint(0.0F, -2.0F, -2.0F);
        this.head_ice.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.Feathers = new ModelRenderer(this, 38, 0);
        this.Feathers.setRotationPoint(0.0F, -3.5F, 2.5F);
        this.Feathers.addBox(0.0F, -3.5F, 0.0F, 0, 7, 2, 0.0F);
        this.core = new ModelRenderer(this, 19, 14);
        this.core.setRotationPoint(0.0F, -4.5F, 0.0F);
        this.core.addBox(-1.5F, -2.0F, -1.5F, 3, 4, 3, 0.0F);
        this.Ice = new ModelRenderer(this, 32, 15);
        this.Ice.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Ice.addBox(-4.0F, -8.5F, -3.0F, 8, 9, 8, 0.0F);
        this.Ice1 = new ModelRenderer(this, 32, 15);
        this.Ice1.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.Ice1.addBox(-4.0F, -8.5F, -3.0F, 8, 6, 8, 0.0F);
        this.Ice2 = new ModelRenderer(this, 32, 15);
        this.Ice2.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Ice2.addBox(-4.0F, -8.5F, -3.0F, 8, 3, 8, 0.0F);
        this.paw_l = new ModelRenderer(this, 8, 14);
        this.paw_l.mirror = true;
        this.paw_l.setRotationPoint(0.0F, 3.0F, 0.5F);
        this.paw_l.addBox(-1.0F, 0.0F, -3.0F, 2, 0, 3, 0.0F);
        this.body = new ModelRenderer(this, 17, 0);
        this.body.setRotationPoint(0.0F, 22.7F, -2.0F);
        this.body.addBox(-2.5F, -8.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(body, 0.017453292519943295F, 0.0F, 0.0F);
        this.wing_r = new ModelRenderer(this, 49, 0);
        this.wing_r.setRotationPoint(-4.0F, -8.0F, 0.0F);
        this.wing_r.addBox(0.0F, 0.0F, -2.5F, 1, 8, 4, 0.0F);
        this.setRotateAngle(wing_r, 0.02652900429741867F, -3.141592653589793F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 13, 1);
        this.tail.setRotationPoint(0.0F, 0.0F, 2.5F);
        this.tail.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 0, 0.0F);
        this.setRotateAngle(tail, 1.1520918765257122F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -9.5F, 2.5F);
        this.head.addBox(-2.0F, -4.0F, -4.0F, 4, 4, 4, 0.0F);
        this.leg_r = new ModelRenderer(this, 11, 9);
        this.leg_r.setRotationPoint(-2.5F, -1.7F, 1.3F);
        this.leg_r.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.beak_d = new ModelRenderer(this, 0, 14);
        this.beak_d.setRotationPoint(0.0F, -1.5F, -4.0F);
        this.beak_d.addBox(-0.5F, 0.0F, -4.0F, 1, 1, 4, 0.0F);
        this.wing_l = new ModelRenderer(this, 49, 0);
        this.wing_l.mirror = true;
        this.wing_l.setRotationPoint(4.0F, -8.0F, 0.0F);
        this.wing_l.addBox(-1.0F, 0.0F, -2.5F, 1, 8, 4, 0.0F);
        this.setRotateAngle(wing_l, 0.02652900429741867F, -3.141592653589793F, -0.08726646259971647F);
        this.leg_r.addChild(this.paw_r);
        this.body.addChild(this.leg_l);
        this.head.addChild(this.beak_u);
        this.head.addChild(this.head_ice);
        this.body.addChild(this.Feathers);
        this.body.addChild(this.core);
        this.body.addChild(this.Ice);
        this.body.addChild(this.Ice1);
        this.body.addChild(this.Ice2);
        this.leg_l.addChild(this.paw_l);
        this.body.addChild(this.wing_r);
        this.body.addChild(this.tail);
        this.body.addChild(this.head);
        this.body.addChild(this.leg_r);
        this.head.addChild(this.beak_d);
        this.body.addChild(this.wing_l);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.body.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {  
    	if (((EntityPingu)entity).isAggressive() || ((EntityPingu)entity).isInWater()) {
    		this.Head_Looking(this.head, -1.5707963267948966F, 0.0F, netHeadYaw, headPitch);
    		this.head.rotationPointZ = -1.0F;
    		this.body.rotateAngleZ = 0.0F;
    		this.wing_l.rotationPointY = -5.5F;
    		wing_l.rotateAngleX = 1.5707963267948966F;
    		wing_l.rotateAngleY = 1.5707963267948966F + ((((EntityPingu)entity).isInWater()) ? MathHelper.sin(limbSwing * limbSwingAmount * 0.1F + (float)Math.PI) * 0.7F : 0.0F);
    		wing_l.rotateAngleZ = -0.08726646259971647F;
    		this.wing_r.rotationPointY = -5.5F;
    		wing_r.rotateAngleX = 1.5707963267948966F;
    		wing_r.rotateAngleY = -1.5707963267948966F + ((((EntityPingu)entity).isInWater()) ? MathHelper.sin(limbSwing * limbSwingAmount * 0.1F) * 0.7F : 0.0F);
    		wing_r.rotateAngleZ = 0.08726646259971647F;
    	} else {
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		this.head.rotationPointZ = 2.5F;
    		this.body.rotateAngleZ = MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    		
    		this.wing_l.rotationPointY = -7.0F;
    		wing_l.rotateAngleX = 0.02652900429741867F;
    		wing_l.rotateAngleY = -3.141592653589793F;
    		this.wing_r.rotationPointY = -7.0F;
    		wing_r.rotateAngleX = 0.02652900429741867F;
    		wing_r.rotateAngleY = 3.141592653589793F;
    		
        	this.leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        	this.leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.8F + (float)Math.PI) * 0.7F * limbSwingAmount;
        	
        	if(MathHelper.abs(limbSwingAmount) > 0.1F) {
        		this.wing_l.rotateAngleZ = -1.3F + MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        		this.wing_r.rotateAngleZ = 1.3F - MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        		this.beak_d.rotateAngleX = 0.3662880558742251F;
        	} else {
        		this.wing_l.rotateAngleZ = -0.08726646259971647F;
        		this.wing_r.rotateAngleZ = 0.08726646259971647F;
        		this.beak_d.rotateAngleX = 0.0F;
        	}
    	}
    	
    	if(((EntityPingu)entity).getHealth() < ((EntityPingu)entity).getMaxHealth() * 0.3F) {
    		this.head.isHidden = true;
    		this.wing_l.isHidden = true;
    		this.wing_r.isHidden = true;
    		this.Ice.isHidden = true;
    		this.Ice1.isHidden = true;
    		this.Ice2.isHidden = false;
    	} else if(((EntityPingu)entity).getHealth() < ((EntityPingu)entity).getMaxHealth() * 0.5F) {
    		this.head.isHidden = true;
    		this.wing_l.isHidden = false;
    		this.wing_r.isHidden = false;
    		this.Ice.isHidden = true;
    		this.Ice1.isHidden = false;
    		this.Ice2.isHidden = true;
    	} else {
    		this.head.isHidden = false;
    		this.wing_l.isHidden = false;
    		this.wing_r.isHidden = false;
    		this.Ice.isHidden = false;
    		this.Ice1.isHidden = true;
    		this.Ice2.isHidden = true;
    	}    	
    }
}
