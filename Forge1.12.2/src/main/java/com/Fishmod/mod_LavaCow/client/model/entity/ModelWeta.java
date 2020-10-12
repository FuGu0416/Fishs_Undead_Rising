package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelWeta - Fish0416
 * Created using Tabula 7.1.0
 */
public class ModelWeta extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Abdomen;
    public ModelRenderer Head;
    public ModelRenderer Leg2_Seg0_l;
    public ModelRenderer Leg2_Seg0_r;
    public ModelRenderer Leg1_Seg0_l;
    public ModelRenderer Leg0_Seg0_l;
    public ModelRenderer Leg1_Seg0_r;
    public ModelRenderer Leg0_Seg0_r;
    public ModelRenderer Stinger;
    public ModelRenderer Antenna_l;
    public ModelRenderer Antenna_r;
    public ModelRenderer Jaw_l;
    public ModelRenderer Jaw_r;
    public ModelRenderer Leg2_Seg1_l;
    public ModelRenderer Leg2_Seg2_l;
    public ModelRenderer Leg2_Seg1_r;
    public ModelRenderer Leg2_Seg2_r;
    public ModelRenderer Leg1_Seg1_l;
    public ModelRenderer Leg0_Seg1_l;
    public ModelRenderer Leg1_Seg1_r;
    public ModelRenderer Leg0_Seg1_r;

    public ModelWeta() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Leg2_Seg0_r = new ModelRenderer(this, 36, 0);
        this.Leg2_Seg0_r.setRotationPoint(-2.0F, 2.0F, 1.1F);
        this.Leg2_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
        this.setRotateAngle(Leg2_Seg0_r, 0.5918411493512771F, -0.40980330836826856F, 0.0F);
        this.Leg2_Seg1_r = new ModelRenderer(this, 41, 9);
        this.Leg2_Seg1_r.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.Leg2_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg0_Seg0_r = new ModelRenderer(this, 18, 0);
        this.Leg0_Seg0_r.setRotationPoint(-2.0F, 2.0F, -1.0F);
        this.Leg0_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg0_Seg0_r, 0.5009094953223726F, -2.0488420089161434F, 0.0F);
        this.Antenna_l = new ModelRenderer(this, 11, 13);
        this.Antenna_l.mirror = true;
        this.Antenna_l.setRotationPoint(1.0F, -2.0F, -4.0F);
        this.Antenna_l.addBox(-0.5F, 0.0F, -10.0F, 1, 0, 10, 0.0F);
        this.setRotateAngle(Antenna_l, -1.2292353921796064F, 0.0F, 0.0F);
        this.Stinger = new ModelRenderer(this, 9, 0);
        this.Stinger.setRotationPoint(0.0F, 2.0F, 8.0F);
        this.Stinger.addBox(-0.5F, 0.0F, 0.0F, 1, 0, 4, 0.0F);
        this.setRotateAngle(Stinger, 0.31869712141416456F, 0.0F, 0.0F);
        this.Leg2_Seg1_l = new ModelRenderer(this, 41, 9);
        this.Leg2_Seg1_l.mirror = true;
        this.Leg2_Seg1_l.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.Leg2_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.5F, -2.0F);
        this.Head.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.Antenna_r = new ModelRenderer(this, 11, 13);
        this.Antenna_r.setRotationPoint(-1.0F, -2.0F, -4.0F);
        this.Antenna_r.addBox(-0.5F, 0.0F, -10.0F, 1, 0, 10, 0.0F);
        this.setRotateAngle(Antenna_r, -1.2292353921796064F, 0.0F, 0.0F);
        this.Leg0_Seg1_l = new ModelRenderer(this, 20, 5);
        this.Leg0_Seg1_l.mirror = true;
        this.Leg0_Seg1_l.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Leg0_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 6, 1, 0.0F);
        this.Leg0_Seg0_l = new ModelRenderer(this, 18, 0);
        this.Leg0_Seg0_l.mirror = true;
        this.Leg0_Seg0_l.setRotationPoint(2.0F, 2.0F, -1.0F);
        this.Leg0_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg0_Seg0_l, 0.5009094953223726F, 2.0488420089161434F, 0.0F);
        this.Leg1_Seg0_r = new ModelRenderer(this, 27, 0);
        this.Leg1_Seg0_r.setRotationPoint(-2.0F, 2.0F, 0.0F);
        this.Leg1_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg1_Seg0_r, 0.18203784098300857F, -1.2292353921796064F, 0.0F);
        this.Jaw_l = new ModelRenderer(this, 13, 9);
        this.Jaw_l.mirror = true;
        this.Jaw_l.setRotationPoint(1.9F, 1.2F, -4.0F);
        this.Jaw_l.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Jaw_l, 0.0F, -0.7740535232594852F, -0.5462880558742251F);
        this.Leg0_Seg1_r = new ModelRenderer(this, 20, 5);
        this.Leg0_Seg1_r.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Leg0_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 6, 1, 0.0F);
        this.Leg1_Seg0_l = new ModelRenderer(this, 27, 0);
        this.Leg1_Seg0_l.mirror = true;
        this.Leg1_Seg0_l.setRotationPoint(2.0F, 2.0F, 0.0F);
        this.Leg1_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg1_Seg0_l, 0.18203784098300857F, 1.2292353921796064F, 0.0F);
        this.Jaw_r = new ModelRenderer(this, 13, 9);
        this.Jaw_r.setRotationPoint(-1.9F, 1.2F, -4.0F);
        this.Jaw_r.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Jaw_r, 0.0F, 0.7740535232594852F, 0.5462880558742251F);
        this.Abdomen = new ModelRenderer(this, 0, 18);
        this.Abdomen.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.Abdomen.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F);
        this.setRotateAngle(Abdomen, -0.18203784098300857F, 0.0F, 0.0F);
        this.Leg2_Seg0_l = new ModelRenderer(this, 36, 0);
        this.Leg2_Seg0_l.mirror = true;
        this.Leg2_Seg0_l.setRotationPoint(2.0F, 2.0F, 1.1F);
        this.Leg2_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
        this.setRotateAngle(Leg2_Seg0_l, 0.5918411493512771F, 0.40980330836826856F, 0.0F);
        this.Leg1_Seg1_r = new ModelRenderer(this, 29, 5);
        this.Leg1_Seg1_r.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Leg1_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 8);
        this.Body_base.setRotationPoint(0.0F, 20.0F, -2.5F);
        this.Body_base.addBox(-2.0F, -2.5F, -2.0F, 4, 5, 4, 0.0F);
        this.Leg2_Seg2_l = new ModelRenderer(this, 41, 19);
        this.Leg2_Seg2_l.mirror = true;
        this.Leg2_Seg2_l.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.Leg2_Seg2_l.addBox(0.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg2_Seg2_r = new ModelRenderer(this, 41, 19);
        this.Leg2_Seg2_r.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.Leg2_Seg2_r.addBox(-1.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg1_Seg1_l = new ModelRenderer(this, 29, 5);
        this.Leg1_Seg1_l.mirror = true;
        this.Leg1_Seg1_l.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Leg1_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.Body_base.addChild(this.Leg2_Seg0_r);
        this.Leg2_Seg0_r.addChild(this.Leg2_Seg1_r);
        this.Body_base.addChild(this.Leg0_Seg0_r);
        this.Head.addChild(this.Antenna_l);
        this.Abdomen.addChild(this.Stinger);
        this.Leg2_Seg0_l.addChild(this.Leg2_Seg1_l);
        this.Body_base.addChild(this.Head);
        this.Head.addChild(this.Antenna_r);
        this.Leg0_Seg0_l.addChild(this.Leg0_Seg1_l);
        this.Body_base.addChild(this.Leg0_Seg0_l);
        this.Body_base.addChild(this.Leg1_Seg0_r);
        this.Head.addChild(this.Jaw_l);
        this.Leg0_Seg0_r.addChild(this.Leg0_Seg1_r);
        this.Body_base.addChild(this.Leg1_Seg0_l);
        this.Head.addChild(this.Jaw_r);
        this.Body_base.addChild(this.Abdomen);
        this.Body_base.addChild(this.Leg2_Seg0_l);
        this.Leg1_Seg0_r.addChild(this.Leg1_Seg1_r);
        this.Leg2_Seg1_l.addChild(this.Leg2_Seg2_l);
        this.Leg2_Seg1_r.addChild(this.Leg2_Seg2_r);
        this.Leg1_Seg0_l.addChild(this.Leg1_Seg1_l);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5);
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.SwingY_Sin(this.Jaw_l, -0.7740535232594852F, ageInTicks, 0.125F, 0.4F, true, 0.0F);
    	this.SwingY_Sin(this.Jaw_r, 0.7740535232594852F, ageInTicks, 0.125F, 0.4F, false, 0.0F);
    	
    	this.SwingX_Sin(this.Antenna_l, -1.2292353921796064F, ageInTicks, 0.4F, 0.41F, false, 0.0F);
    	this.SwingX_Sin(this.Antenna_r, -1.2292353921796064F, ageInTicks, 0.4F, 0.32F, false, 0.25F * (float)Math.PI);
    	
    	this.SwingX_Sin(this.Leg0_Seg0_r, 0.5009094953223726F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.0F);
    	this.SwingX_Sin(this.Leg0_Seg0_l, 0.5009094953223726F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.5F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg1_Seg0_r, 0.18203784098300857F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.33F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg1_Seg0_l, 0.18203784098300857F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.83F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg0_r, 0.5918411493512771F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.66F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg0_l, 0.5918411493512771F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 1.16F * (float)Math.PI);
    	
    	this.SwingY_Sin(this.Leg0_Seg0_r, -2.0488420089161434F, limbSwing, 0.3F * limbSwingAmount, 1.3F, true, 0.0F);
    	this.SwingY_Sin(this.Leg0_Seg0_l, 2.0488420089161434F, limbSwing, 0.3F * limbSwingAmount, 1.3F, false, 0.5F * (float)Math.PI);
    	this.SwingY_Sin(this.Leg1_Seg0_r, -1.2292353921796064F, limbSwing, 0.3F * limbSwingAmount, 1.3F, true, 0.33F * (float)Math.PI);
    	this.SwingY_Sin(this.Leg1_Seg0_l, 1.2292353921796064F, limbSwing, 0.3F * limbSwingAmount, 1.3F, false, 0.83F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg1_r, 0.0F, limbSwing, 0.8F * limbSwingAmount, 1.3F, false, 0.66F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg1_l, 0.0F, limbSwing, 0.8F * limbSwingAmount, 1.3F, false, 1.16F * (float)Math.PI);
    }
}
