package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.EntityPingu;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelPingu - Mojang, edited by Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelPingu extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer wingLeft;
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer legRight;
    public ModelRenderer wingRight;
    public ModelRenderer legLeft;
    public ModelRenderer Ice;
    public ModelRenderer Ice1;
    public ModelRenderer Ice2;
    public ModelRenderer Feathers;
    public ModelRenderer beak1;
    public ModelRenderer beak2;

    public ModelPingu() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.wingLeft = new ModelRenderer(this, 19, 8);
        this.wingLeft.mirror = true;
        this.wingLeft.setRotationPoint(3.3F, 0.5F, 0.0F);
        this.wingLeft.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
        this.setRotateAngle(wingLeft, -0.091106186954104F, -3.141592653589793F, -0.08726646259971647F);
        this.body = new ModelRenderer(this, 0, 7);
        this.body.setRotationPoint(0.0F, 16.5F, -2.0F);
        this.body.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.setRotateAngle(body, 0.22759093446006054F, 0.0F, 0.0F);
        this.Ice = new ModelRenderer(this, 0, 16);
        this.Ice.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Ice.addBox(-3.0F, -4.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(Ice, -0.091106186954104F, 0.0F, 0.0F);
        this.Ice1 = new ModelRenderer(this, 0, 16);
        this.Ice1.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.Ice1.addBox(-3.0F, -4.0F, -3.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Ice1, -0.091106186954104F, 0.0F, 0.0F);
        this.Ice2 = new ModelRenderer(this, 0, 16);
        this.Ice2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Ice2.addBox(-3.0F, -4.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(Ice2, -0.091106186954104F, 0.0F, 0.0F);
        this.beak1 = new ModelRenderer(this, 12, 0);
        this.beak1.setRotationPoint(0.0F, -0.5F, -2.0F);
        this.beak1.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.legRight = new ModelRenderer(this, 14, 10);
        this.legRight.setRotationPoint(-1.0F, 5.5F, -0.5F);
        this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legRight, -0.22759093446006054F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -1.5F, -0.5F);
        this.head.addBox(-1.0F, -1.5F, -2.0F, 2, 3, 3, 0.0F);
        this.setRotateAngle(head, -1.5025539530419183F, 0.0F, 0.0F);
        this.beak2 = new ModelRenderer(this, 12, 5);
        this.beak2.setRotationPoint(0.0F, 0.5F, -2.0F);
        this.beak2.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.legLeft = new ModelRenderer(this, 14, 10);
        this.legLeft.setRotationPoint(1.0F, 5.5F, -0.5F);
        this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legLeft, -0.22759093446006054F, 0.0F, 0.0F);
        this.wingRight = new ModelRenderer(this, 19, 8);
        this.wingRight.setRotationPoint(-3.3F, 0.5F, 0.0F);
        this.wingRight.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
        this.setRotateAngle(wingRight, -0.091106186954104F, -3.141592653589793F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 22, 1);
        this.tail.setRotationPoint(0.0F, 6.0F, 1.76F);
        this.tail.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
        this.setRotateAngle(tail, 0.6829473363053812F, 0.0F, 0.0F);
        this.Feathers = new ModelRenderer(this, 24, 12);
        this.Feathers.setRotationPoint(0.0F, 1.0F, 2.0F);
        this.Feathers.addBox(0.0F, -4.0F, 0.0F, 0, 8, 4, 0.0F);
        this.body.addChild(this.wingLeft);
        this.head.addChild(this.beak1);
        this.body.addChild(this.legRight);
        this.body.addChild(this.head);
        this.head.addChild(this.beak2);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.tail);
        this.Ice.addChild(this.Feathers);
        this.body.addChild(this.Ice);
        this.body.addChild(this.Ice1);
        this.body.addChild(this.Ice2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.body.render(f5);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	this.body.rotateAngleZ = MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    	this.legRight.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    	this.legLeft.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * 0.8F + (float)Math.PI) * 0.7F * limbSwingAmount;
    	
    	if(MathHelper.abs(limbSwingAmount) > 0.1F) {
    		this.wingLeft.rotateAngleZ = -1.3F + MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    		this.wingRight.rotateAngleZ = 1.3F - MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    		this.beak2.rotateAngleX = 0.3662880558742251F;
    	}
    	else {
    		this.wingLeft.rotateAngleZ = -0.08726646259971647F;
    		this.wingRight.rotateAngleZ = 0.08726646259971647F;
    		this.beak2.rotateAngleX = 0.0F;
    	}
    	
    	if(((EntityPingu) entityIn).getHealth() < ((EntityPingu) entityIn).getMaxHealth() * 0.3F) {
    		this.head.showModel = false;
    		this.wingLeft.showModel = false;
    		this.wingRight.showModel = false;
    		this.Ice.showModel = false;
    		this.Ice1.showModel = false;
    		this.Ice2.showModel = true;
    	}
    	else if(((EntityPingu) entityIn).getHealth() < ((EntityPingu) entityIn).getMaxHealth() * 0.5F) {
    		this.head.showModel = false;
    		this.wingLeft.showModel = true;
    		this.wingRight.showModel = true;
    		this.Ice.showModel = false;
    		this.Ice1.showModel = true;
    		this.Ice2.showModel = false;
    	}
    	else {
    		this.head.showModel = true;
    		this.wingLeft.showModel = true;
    		this.wingRight.showModel = true;
    		this.Ice.showModel = true;
    		this.Ice1.showModel = false;
    		this.Ice2.showModel = false;
    	}
    
    }
}
