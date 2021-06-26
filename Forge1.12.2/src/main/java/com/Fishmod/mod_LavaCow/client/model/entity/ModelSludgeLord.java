package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSludgeLord - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelSludgeLord extends ModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Head_base;
    public ModelRenderer Arm_r_seg0;
    public ModelRenderer Arm_l_seg0;
    public ModelRenderer Waist0;
    public ModelRenderer Head_Moss_r;
    public ModelRenderer Head_Moss_l;
    public ModelRenderer Head_Twig0_l;
    public ModelRenderer Head_Twig0_r;
    public ModelRenderer Head_Twig0_m;
    public ModelRenderer Head;
    public ModelRenderer Head_bubble;
    public ModelRenderer Head_bubble_in;
    public ModelRenderer Head_Twig1_l;
    public ModelRenderer Head_Twig1_r;
    public ModelRenderer Head_Twig1_m;
    public ModelRenderer Jaw;
    public ModelRenderer Arm_r_seg1;
    public ModelRenderer Arm_l_seg1;
    public ModelRenderer Arm_l_seg2;
    public ModelRenderer Waist1;
    public ModelRenderer Waist_Twig0_l;
    public ModelRenderer Waist_Twig0_r;
    public ModelRenderer Leg_r_seg0;
    public ModelRenderer Leg_l_seg0;
    public ModelRenderer Leg_r_seg1;
    public ModelRenderer Leg_l_seg1;
    public ModelRenderer Waist_Twig1_l;
    public ModelRenderer Waist_Twig2_l;
    public ModelRenderer Waist_Twig1_r;

    public ModelSludgeLord() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.Head_Moss_r = new ModelRenderer(this, 60, -16);
        this.Head_Moss_r.setRotationPoint(-4.0F, 2.0F, -8.0F);
        this.Head_Moss_r.addBox(0.0F, 0.0F, -8.0F, 0, 12, 16, 0.0F);
        this.setRotateAngle(Head_Moss_r, 0.0017453292519943296F, 0.0F, 0.0F);
        this.Leg_r_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_r_seg1.setRotationPoint(0.0F, 8.0F, -2.6F);
        this.Leg_r_seg1.addBox(-4.0F, -2.0F, -2.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(Leg_r_seg1, 0.6276203990171609F, 0.0F, -0.5967280712568612F);
        this.Waist_Twig1_l = new ModelRenderer(this, 40, 0);
        this.Waist_Twig1_l.setRotationPoint(0.0F, -14.8F, 0.4F);
        this.Waist_Twig1_l.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist_Twig1_l, 0.6829473363053812F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 146, 92);
        this.Head.setRotationPoint(0.0F, -22.0F, -1.0F);
        this.Head.addBox(-4.0F, -3.0F, -4.0F, 8, 6, 8, 0.0F);
        this.Head_Twig1_l = new ModelRenderer(this, 0, 0);
        this.Head_Twig1_l.mirror = true;
        this.Head_Twig1_l.setRotationPoint(0.0F, -5.6F, 0.0F);
        this.Head_Twig1_l.addBox(-2.0F, -8.0F, -3.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig1_l, -0.9560913642424937F, 0.0F, 0.0F);
        this.Head_Twig0_l = new ModelRenderer(this, 0, 0);
        this.Head_Twig0_l.mirror = true;
        this.Head_Twig0_l.setRotationPoint(12.0F, -4.0F, -12.0F);
        this.Head_Twig0_l.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig0_l, 0.6373942428283291F, -0.8196066167365371F, 0.0F);
        this.Waist_Twig0_l = new ModelRenderer(this, 20, 0);
        this.Waist_Twig0_l.mirror = true;
        this.Waist_Twig0_l.setRotationPoint(4.0F, 6.0F, 4.0F);
        this.Waist_Twig0_l.addBox(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
        this.setRotateAngle(Waist_Twig0_l, -1.593485607070823F, 0.4553564018453205F, 0.0F);
        this.Waist1 = new ModelRenderer(this, 0, 110);
        this.Waist1.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Waist1.addBox(-6.0F, 0.0F, -4.0F, 12, 10, 8, 0.0F);
        this.setRotateAngle(Waist1, 0.36425021489121656F, 0.0F, 0.0F);
        this.Arm_l_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_l_seg0.mirror = true;
        this.Arm_l_seg0.setRotationPoint(10.0F, 0.0F, 0.0F);
        this.Arm_l_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Arm_l_seg0, 0.0F, 0.0F, -1.0927506446736497F);
        this.Head_Twig0_r = new ModelRenderer(this, 0, 0);
        this.Head_Twig0_r.setRotationPoint(-12.0F, -4.0F, -12.0F);
        this.Head_Twig0_r.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig0_r, 0.6373942428283291F, 0.8196066167365371F, 0.0F);
        this.Head_Twig1_m = new ModelRenderer(this, 0, 0);
        this.Head_Twig1_m.setRotationPoint(0.0F, -5.6F, 0.0F);
        this.Head_Twig1_m.addBox(-2.0F, -8.0F, -3.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig1_m, -0.9560913642424937F, 0.0F, 0.0F);
        this.Head_bubble_in = new ModelRenderer(this, 144, 40);
        this.Head_bubble_in.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Head_bubble_in.addBox(-12.0F, -24.0F, -12.0F, 24, 24, 24, 0.0F);
        this.Head_Twig1_r = new ModelRenderer(this, 0, 0);
        this.Head_Twig1_r.setRotationPoint(0.0F, -5.6F, 0.0F);
        this.Head_Twig1_r.addBox(-2.0F, -8.0F, -3.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig1_r, -0.9560913642424937F, 0.0F, 0.0F);
        this.Leg_l_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_l_seg1.mirror = true;
        this.Leg_l_seg1.setRotationPoint(0.0F, 8.0F, -2.6F);
        this.Leg_l_seg1.addBox(-2.0F, -2.0F, -2.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(Leg_l_seg1, 0.6276203990171609F, 0.0F, 0.5967280712568612F);
        this.Arm_r_seg1 = new ModelRenderer(this, 104, 20);
        this.Arm_r_seg1.setRotationPoint(-3.0F, 10.0F, -1.0F);
        this.Arm_r_seg1.addBox(-4.0F, -2.0F, -2.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(Arm_r_seg1, 0.0F, 0.0F, -1.2487830798019426F);
        this.Waist_Twig0_r = new ModelRenderer(this, 20, 0);
        this.Waist_Twig0_r.setRotationPoint(-4.0F, 6.0F, 4.0F);
        this.Waist_Twig0_r.addBox(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
        this.setRotateAngle(Waist_Twig0_r, -1.4114477660878142F, -0.7740535232594852F, 0.0F);
        this.Waist0 = new ModelRenderer(this, 0, 88);
        this.Waist0.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Waist0.addBox(-8.0F, 0.0F, -5.0F, 16, 10, 10, 0.0F);
        this.setRotateAngle(Waist0, 0.6283185307179586F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 204, 92);
        this.Jaw.setRotationPoint(0.0F, 4.0F, 4.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 3, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.5918411493512771F, 0.0F, 0.0F);
        this.Waist_Twig2_l = new ModelRenderer(this, 40, 0);
        this.Waist_Twig2_l.mirror = true;
        this.Waist_Twig2_l.setRotationPoint(-0.4F, -10.8F, 0.0F);
        this.Waist_Twig2_l.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist_Twig2_l, 0.0F, 0.0F, 0.6373942428283291F);
        this.Head_bubble = new ModelRenderer(this, 0, 24);
        this.Head_bubble.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Head_bubble.addBox(-10.0F, -22.0F, -10.0F, 20, 20, 20, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 68);
        this.Body_base.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.Body_base.addBox(-10.0F, -4.0F, -10.0F, 20, 6, 12, 0.0F);
        this.Leg_l_seg0 = new ModelRenderer(this, 112, 0);
        this.Leg_l_seg0.mirror = true;
        this.Leg_l_seg0.setRotationPoint(4.0F, 8.0F, -4.0F);
        this.Leg_l_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_l_seg0, -1.593485607070823F, -0.5918411493512771F, 0.0F);
        this.Head_Twig0_m = new ModelRenderer(this, 0, 0);
        this.Head_Twig0_m.setRotationPoint(0.0F, -4.0F, -12.0F);
        this.Head_Twig0_m.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(Head_Twig0_m, 0.8196066167365371F, 0.0F, 0.0F);
        this.Arm_r_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_r_seg0.setRotationPoint(-10.0F, 0.0F, 0.0F);
        this.Arm_r_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Arm_r_seg0, 0.0F, 0.776846050062676F, 0.6829473363053812F);
        this.Arm_l_seg1 = new ModelRenderer(this, 96, 64);
        this.Arm_l_seg1.setRotationPoint(5.0F, 10.0F, 0.0F);
        this.Arm_l_seg1.addBox(-4.0F, -6.0F, -4.0F, 8, 12, 8, 0.0F);
        this.setRotateAngle(Arm_l_seg1, 0.0F, 0.0F, 1.0927506446736497F);
        this.Head_base = new ModelRenderer(this, 136, 0);
        this.Head_base.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.Head_base.addBox(-14.0F, -6.0F, -14.0F, 28, 6, 28, 0.0F);
        this.setRotateAngle(Head_base, 0.36425021489121656F, 0.0F, -0.18203784098300857F);
        this.Head_Moss_l = new ModelRenderer(this, 60, -4);
        this.Head_Moss_l.setRotationPoint(4.0F, 2.0F, -8.0F);
        this.Head_Moss_l.addBox(0.0F, 0.0F, -8.0F, 0, 12, 16, 0.0F);
        this.setRotateAngle(Head_Moss_l, 0.0017453292519943296F, 0.0F, 0.0F);
        this.Arm_l_seg2 = new ModelRenderer(this, 88, 88);
        this.Arm_l_seg2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Arm_l_seg2.addBox(-5.0F, -6.0F, -5.0F, 10, 20, 10, 0.0F);
        this.Leg_r_seg0 = new ModelRenderer(this, 112, 0);
        this.Leg_r_seg0.setRotationPoint(-4.0F, 8.0F, -4.0F);
        this.Leg_r_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_r_seg0, -1.593485607070823F, 0.5918411493512771F, 0.0F);
        this.Waist_Twig1_r = new ModelRenderer(this, 40, 0);
        this.Waist_Twig1_r.setRotationPoint(0.4F, -14.8F, 0.0F);
        this.Waist_Twig1_r.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist_Twig1_r, 0.0F, 0.0F, -0.4553564018453205F);
        this.Body_base.addChild(this.Head_Moss_r);
        this.Leg_r_seg0.addChild(this.Leg_r_seg1);
        this.Waist_Twig0_l.addChild(this.Waist_Twig1_l);
        this.Head_base.addChild(this.Head);
        this.Head_Twig0_l.addChild(this.Head_Twig1_l);
        this.Head_base.addChild(this.Head_Twig0_l);
        this.Waist0.addChild(this.Waist_Twig0_l);
        this.Waist0.addChild(this.Waist1);
        this.Body_base.addChild(this.Arm_l_seg0);
        this.Head_base.addChild(this.Head_Twig0_r);
        this.Head_Twig0_m.addChild(this.Head_Twig1_m);
        this.Head_base.addChild(this.Head_bubble_in);
        this.Head_Twig0_r.addChild(this.Head_Twig1_r);
        this.Leg_l_seg0.addChild(this.Leg_l_seg1);
        this.Arm_r_seg0.addChild(this.Arm_r_seg1);
        this.Waist0.addChild(this.Waist_Twig0_r);
        this.Body_base.addChild(this.Waist0);
        this.Head.addChild(this.Jaw);
        this.Waist_Twig1_l.addChild(this.Waist_Twig2_l);
        this.Head_base.addChild(this.Head_bubble);
        this.Waist1.addChild(this.Leg_l_seg0);
        this.Head_base.addChild(this.Head_Twig0_m);
        this.Body_base.addChild(this.Arm_r_seg0);
        this.Arm_l_seg0.addChild(this.Arm_l_seg1);
        this.Body_base.addChild(this.Head_base);
        this.Body_base.addChild(this.Head_Moss_l);
        this.Arm_l_seg1.addChild(this.Arm_l_seg2);
        this.Waist1.addChild(this.Leg_r_seg0);
        this.Waist_Twig0_r.addChild(this.Waist_Twig1_r);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.Body_base.render(f5);
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
    	this.Head_base.rotationPointY = 0.5F * MathHelper.sin(0.03F * ageInTicks);
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;        
    	this.Head.rotationPointY = -22.0F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
        this.Jaw.rotateAngleX = 0.15F + 0.11F * MathHelper.sin(0.03F * ageInTicks);
        
        this.Waist0.rotateAngleY = 0.35F * MathHelper.cos(limbSwing * 0.3F);
        
        this.Arm_l_seg0.rotateAngleX = 0.55F * MathHelper.cos(limbSwing * 0.15F + (float)Math.PI);
        
        this.Leg_r_seg0.rotateAngleX = -1.593485607070823F + MathHelper.cos(limbSwing * 0.3F) * 0.7F * limbSwingAmount;
        this.Leg_l_seg0.rotateAngleX = -1.593485607070823F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 0.7F * limbSwingAmount;
    }
}
