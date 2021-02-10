package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * ModelSkeletonKing - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelSkeletonKing extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Head;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Shoulder_piece;
    public ModelRenderer weapon_handle0;
    public ModelRenderer weapon_handle1;
    public ModelRenderer weapon_handle1_1;
    public ModelRenderer weapon_base;
    public ModelRenderer weapon_horn_l;
    public ModelRenderer weapon_horn_r;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Crown;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_r_Seg1;

    public ModelSkeletonKing() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Body_base = new ModelRenderer(this, 0, 52);
        this.Body_base.setRotationPoint(0.0F, 4.2F, 0.0F);
        this.Body_base.addBox(-5.0F, -6.0F, -3.0F, 10, 6, 6, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 39);
        this.Body_waist.setRotationPoint(0.0F, -6.0F, 2.0F);
        this.Body_waist.addBox(-3.5F, -10.0F, -3.0F, 7, 10, 3, 0.0F);
        this.setRotateAngle(Body_waist, 0.136659280431156F, 0.0F, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 23);
        this.Body_chest.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.Body_chest.addBox(-6.0F, -10.0F, -4.5F, 12, 10, 6, 0.0F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 48, 48);
        this.Leg_r_Seg1.setRotationPoint(0.0F, 12.0F, -2.0F);
        this.Leg_r_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -10.0F, -1.0F);
        this.Head.addBox(-4.0F, -8.0F, -5.0F, 8, 8, 8, 0.0F);
        this.Shoulder_piece = new ModelRenderer(this, 38, 0);
        this.Shoulder_piece.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.Shoulder_piece.addBox(0.0F, -3.0F, -3.5F, 5, 11, 7, 0.0F);
        this.weapon_handle1 = new ModelRenderer(this, 66, 43);
        this.weapon_handle1.setRotationPoint(0.0F, 0.0F, -15.0F);
        this.weapon_handle1.addBox(-0.5F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.weapon_handle1_1 = new ModelRenderer(this, 66, 52);
        this.weapon_handle1_1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.weapon_handle1_1.addBox(-1.5F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.weapon_base = new ModelRenderer(this, 96, 35);
        this.weapon_base.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.weapon_base.addBox(-3.5F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 36, 22);
        this.Arm_l_Seg0.mirror = true;
        this.Arm_l_Seg0.setRotationPoint(5.0F, -8.0F, -1.5F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        //this.setRotateAngle(Arm_l_Seg0, 0.0F, 0.0F, -0.5462880558742251F);
        this.setRotateAngle(Arm_l_Seg0, -0.9560913642424937F, 0.0F, -0.40980330836826856F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 45, 21);
        this.Arm_l_Seg1.setRotationPoint(1.0F, 11.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 3, 0.0F);
        //this.setRotateAngle(Arm_l_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, -1.9123572614101867F, 0.0F, 0.0F);
        this.weapon_horn_l = new ModelRenderer(this, 110, 52);
        this.weapon_horn_l.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.weapon_horn_l.addBox(0.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_horn_r = new ModelRenderer(this, 110, 52);
        this.weapon_horn_r.mirror = true;
        this.weapon_horn_r.setRotationPoint(-4.0F, 0.0F, -4.0F);
        this.weapon_horn_r.addBox(-2.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 32, 48);
        this.Leg_l_Seg0.setRotationPoint(2.5F, -1.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402793711257F, 0.0F, -0.091106186954104F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 48, 48);
        this.Leg_l_Seg1.mirror = true;
        this.Leg_l_Seg1.setRotationPoint(0.0F, 12.0F, -2.0F);
        this.Leg_l_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Leg_r_Seg0 = new ModelRenderer(this, 32, 48);
        this.Leg_r_Seg0.mirror = true;
        this.Leg_r_Seg0.setRotationPoint(-2.5F, -1.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402793711257F, 0.0F, 0.091106186954104F);
        this.Crown = new ModelRenderer(this, 28, 36);
        this.Crown.setRotationPoint(0.0F, -6.0F, -1.0F);
        this.Crown.addBox(-4.5F, -3.0F, -4.5F, 9, 3, 9, 0.0F);
        this.weapon_handle0 = new ModelRenderer(this, 66, 43);
        this.weapon_handle0.setRotationPoint(-0.5F, 11.0F, 0.0F);
        this.weapon_handle0.addBox(0.0F, -0.5F, -15.0F, 1, 1, 20, 0.0F);
        this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 45, 21);
        this.Arm_r_Seg1.mirror = true;
        this.Arm_r_Seg1.setRotationPoint(-1.0F, 11.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 3, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 36, 22);
        this.Arm_r_Seg0.setRotationPoint(-5.0F, -8.0F, -1.5F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, 0.0F, 0.0F, 0.5462880558742251F);
        this.Body_base.addChild(this.Body_waist);
        this.Body_waist.addChild(this.Body_chest);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Body_chest.addChild(this.Head);
        this.Arm_l_Seg0.addChild(this.Shoulder_piece);
        this.weapon_handle0.addChild(this.weapon_handle1);
        this.weapon_handle1.addChild(this.weapon_handle1_1);
        this.weapon_handle1_1.addChild(this.weapon_base);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.weapon_base.addChild(this.weapon_horn_l);
        this.weapon_base.addChild(this.weapon_horn_r);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Head.addChild(this.Crown);
        this.Arm_l_Seg1.addChild(this.weapon_handle0);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Body_chest.addChild(this.Arm_r_Seg0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5);
    }
    
    @Override
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.3F, true, 0.0F);
    	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.3F, false, 0.0F);
    	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, false, 0.3F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, true, 0.3F * (float)Math.PI);
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	EntitySkeletonKing entity = (EntitySkeletonKing) entityIn;
    	float i = (float)(entity.getAttackTimer());
    	
    	if(i > 0.0F) {
    		
    	}
    	else {
    		this.setRotateAngle(Arm_r_Seg0, 0.0F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_l_Seg0, -0.9560913642424937F, 0.0F, -0.40980330836826856F);
    		this.setRotateAngle(Arm_l_Seg1, -1.9123572614101867F, 0.0F, 0.0F);
    		this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
    	}
    }
}
