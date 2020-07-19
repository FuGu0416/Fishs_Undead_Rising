package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMimic - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelMimic extends ModelBase {
    public ModelRenderer Chest_Base;
    public ModelRenderer Chest_top;
    public ModelRenderer Eye_r;
    public ModelRenderer Eye_l;
    public ModelRenderer Leg0_r_Seg0;
    public ModelRenderer Leg1_r_Seg0;
    public ModelRenderer Leg0_l_Seg0;
    public ModelRenderer Leg1_l_Seg0;
    public ModelRenderer Pincer_r_Seg0;
    public ModelRenderer Pincer_l_Seg0;
    public ModelRenderer Chest_lock;
    public ModelRenderer Leg0_r_Seg1;
    public ModelRenderer Leg0_r_Seg2;
    public ModelRenderer Leg1_r_Seg1;
    public ModelRenderer Leg1_r_Seg2;
    public ModelRenderer Leg0_l_Seg1;
    public ModelRenderer Leg0_l_Seg2;
    public ModelRenderer Leg1_l_Seg1;
    public ModelRenderer Leg1_l_Seg2;
    public ModelRenderer Pincer_r_Seg1;
    public ModelRenderer Pincer_r_Seg2;
    public ModelRenderer Pincer_r_Seg3;
    public ModelRenderer Pincer_l_Seg1;
    public ModelRenderer Pincer_l_Seg2;
    public ModelRenderer Pincer_l_Seg3;

    public ModelMimic() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Chest_Base = new ModelRenderer(this, 0, 19);
        this.Chest_Base.setRotationPoint(0.0F, 18.0F, 1.0F);
        this.Chest_Base.addBox(-7.0F, -5.0F, -7.0F, 14, 10, 14, 0.0F);
        this.Eye_r = new ModelRenderer(this, 44, 0);
        this.Eye_r.setRotationPoint(-2.0F, -4.0F, -5.0F);
        this.Eye_r.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Eye_r, 0.8155923594569501F, 0.36425021489121656F, 0.0F);
        this.Leg1_r_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg1_r_Seg1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Leg1_r_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg1_r_Seg1, 0.0F, 0.0F, 0.8651597102135892F);
        this.Leg0_l_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg0_l_Seg2.mirror = true;
        this.Leg0_l_Seg2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Leg0_l_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg0_l_Seg2, 0.0F, 0.0F, 0.5918411493512771F);
        this.Leg1_l_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg1_l_Seg1.mirror = true;
        this.Leg1_l_Seg1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Leg1_l_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg1_l_Seg1, 0.0F, 0.0F, -0.8651597102135892F);
        this.Pincer_l_Seg3 = new ModelRenderer(this, 17, 56);
        this.Pincer_l_Seg3.mirror = true;
        this.Pincer_l_Seg3.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Pincer_l_Seg3.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Leg1_l_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg1_l_Seg2.mirror = true;
        this.Leg1_l_Seg2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Leg1_l_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg1_l_Seg2, 0.0F, 0.0F, 0.5918411493512771F);
        this.Pincer_r_Seg1 = new ModelRenderer(this, 26, 45);
        this.Pincer_r_Seg1.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Pincer_r_Seg1.addBox(-1.0F, -2.0F, -2.0F, 2, 10, 4, 0.0F);
        this.setRotateAngle(Pincer_r_Seg1, -0.36425021489121656F, 0.0F, 1.2292353921796064F);
        this.Leg1_r_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg1_r_Seg0.setRotationPoint(-6.0F, 4.0F, 5.0F);
        this.Leg1_r_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg1_r_Seg0, 0.0F, 0.5462880558742251F, -0.5009094953223726F);
        this.Pincer_r_Seg0 = new ModelRenderer(this, 17, 45);
        this.Pincer_r_Seg0.setRotationPoint(-6.0F, 2.0F, -4.0F);
        this.Pincer_r_Seg0.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Pincer_r_Seg0, 0.5918411493512771F, -1.0016444577195458F, -1.3658946726107624F);
        this.Chest_top = new ModelRenderer(this, 0, 0);
        this.Chest_top.setRotationPoint(0.0F, -4.0F, 7.0F);
        this.Chest_top.addBox(-7.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
        this.setRotateAngle(Chest_top, -0.136659280431156F, 0.0F, 0.0F);
        this.Leg1_l_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg1_l_Seg0.mirror = true;
        this.Leg1_l_Seg0.setRotationPoint(6.0F, 4.0F, 5.0F);
        this.Leg1_l_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg1_l_Seg0, 0.0F, -0.5462880558742251F, 0.5009094953223726F);
        this.Chest_lock = new ModelRenderer(this, 0, 0);
        this.Chest_lock.setRotationPoint(0.0F, 0.0F, -14.0F);
        this.Chest_lock.addBox(-1.0F, -2.0F, -1.0F, 2, 4, 1, 0.0F);
        this.Leg0_l_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg0_l_Seg0.mirror = true;
        this.Leg0_l_Seg0.setRotationPoint(6.0F, 4.0F, 0.0F);
        this.Leg0_l_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg0_l_Seg0, 0.0F, 0.22759093446006054F, 0.5009094953223726F);
        this.Leg0_r_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg0_r_Seg2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Leg0_r_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg0_r_Seg2, 0.0F, 0.0F, -0.5918411493512771F);
        this.Pincer_l_Seg0 = new ModelRenderer(this, 17, 45);
        this.Pincer_l_Seg0.mirror = true;
        this.Pincer_l_Seg0.setRotationPoint(6.0F, 2.0F, -4.0F);
        this.Pincer_l_Seg0.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Pincer_l_Seg0, 0.5918411493512771F, 1.0016444577195458F, 1.3658946726107624F);
        this.Pincer_l_Seg2 = new ModelRenderer(this, 17, 56);
        this.Pincer_l_Seg2.mirror = true;
        this.Pincer_l_Seg2.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Pincer_l_Seg2.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Pincer_l_Seg2, -0.3754203221039803F, 0.0F, 0.0F);
        this.Pincer_r_Seg3 = new ModelRenderer(this, 17, 56);
        this.Pincer_r_Seg3.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Pincer_r_Seg3.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Leg0_l_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg0_l_Seg1.mirror = true;
        this.Leg0_l_Seg1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Leg0_l_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg0_l_Seg1, 0.0F, 0.0F, -0.8651597102135892F);
        this.Pincer_l_Seg1 = new ModelRenderer(this, 26, 45);
        this.Pincer_l_Seg1.mirror = true;
        this.Pincer_l_Seg1.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Pincer_l_Seg1.addBox(-1.0F, -2.0F, -2.0F, 2, 10, 4, 0.0F);
        this.setRotateAngle(Pincer_l_Seg1, -0.36425021489121656F, 0.0F, -1.2292353921796064F);
        this.Eye_l = new ModelRenderer(this, 44, 0);
        this.Eye_l.mirror = true;
        this.Eye_l.setRotationPoint(2.0F, -4.0F, -5.0F);
        this.Eye_l.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Eye_l, 0.8155923594569501F, -0.36425021489121656F, 0.0F);
        this.Leg0_r_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg0_r_Seg0.setRotationPoint(-6.0F, 4.0F, 0.0F);
        this.Leg0_r_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg0_r_Seg0, 0.0F, -0.22759093446006054F, -0.5009094953223726F);
        this.Leg0_r_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg0_r_Seg1.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Leg0_r_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg0_r_Seg1, 0.0F, 0.0F, 0.8651597102135892F);
        this.Leg1_r_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg1_r_Seg2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Leg1_r_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg1_r_Seg2, 0.0F, 0.0F, -0.5918411493512771F);
        this.Pincer_r_Seg2 = new ModelRenderer(this, 17, 56);
        this.Pincer_r_Seg2.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Pincer_r_Seg2.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Pincer_r_Seg2, -0.3754203221039803F, 0.0F, 0.0F);
        this.Chest_Base.addChild(this.Eye_r);
        this.Leg1_r_Seg0.addChild(this.Leg1_r_Seg1);
        this.Leg0_l_Seg1.addChild(this.Leg0_l_Seg2);
        this.Leg1_l_Seg0.addChild(this.Leg1_l_Seg1);
        this.Pincer_l_Seg1.addChild(this.Pincer_l_Seg3);
        this.Leg1_l_Seg1.addChild(this.Leg1_l_Seg2);
        this.Pincer_r_Seg0.addChild(this.Pincer_r_Seg1);
        this.Chest_Base.addChild(this.Leg1_r_Seg0);
        this.Chest_Base.addChild(this.Pincer_r_Seg0);
        this.Chest_Base.addChild(this.Chest_top);
        this.Chest_Base.addChild(this.Leg1_l_Seg0);
        this.Chest_top.addChild(this.Chest_lock);
        this.Chest_Base.addChild(this.Leg0_l_Seg0);
        this.Leg0_r_Seg1.addChild(this.Leg0_r_Seg2);
        this.Chest_Base.addChild(this.Pincer_l_Seg0);
        this.Pincer_l_Seg1.addChild(this.Pincer_l_Seg2);
        this.Pincer_r_Seg1.addChild(this.Pincer_r_Seg3);
        this.Leg0_l_Seg0.addChild(this.Leg0_l_Seg1);
        this.Pincer_l_Seg0.addChild(this.Pincer_l_Seg1);
        this.Chest_Base.addChild(this.Eye_l);
        this.Chest_Base.addChild(this.Leg0_r_Seg0);
        this.Leg0_r_Seg0.addChild(this.Leg0_r_Seg1);
        this.Leg1_r_Seg1.addChild(this.Leg1_r_Seg2);
        this.Pincer_r_Seg1.addChild(this.Pincer_r_Seg2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Chest_Base.render(f5);
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
    	this.Pincer_r_Seg0.rotationPointY = 1.0F + (-0.55F * MathHelper.sin(0.12F * ageInTicks)); 
    	this.Pincer_l_Seg0.rotationPointY = 1.0F + (-0.55F * MathHelper.sin(0.12F * ageInTicks)); 
    	this.Chest_Base.rotateAngleY = 0.12F * MathHelper.cos(limbSwing);
    	this.Chest_Base.rotationPointY = 18.0F + MathHelper.cos(limbSwing);
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	if((((EntityMimic)entityIn).isAggressive() || ((EntityMimic)entityIn).isTamed()) && !((EntityMimic)entityIn).isSitting())
        {
        	this.Chest_top.rotateAngleX = -0.2F + (-0.02F * MathHelper.sin(0.12F * entityIn.ticksExisted + 0.1F)); 
        	this.Chest_Base.setRotationPoint(0.0F, 18.0F, 1.0F);
        	this.Eye_r.setRotationPoint(-2.0F, -4.0F, -5.0F);
        	this.Eye_l.setRotationPoint(2.0F, -4.0F, -5.0F);
        	this.Leg0_r_Seg0.isHidden = false;
        	this.Leg0_l_Seg0.isHidden = false;
        	this.Leg0_r_Seg1.isHidden = false;
        	this.Leg0_l_Seg1.isHidden = false;
        	this.Leg0_r_Seg2.isHidden = false;
        	this.Leg0_l_Seg2.isHidden = false;
        	this.Leg1_r_Seg0.isHidden = false;
        	this.Leg1_l_Seg0.isHidden = false;
        	this.Leg1_r_Seg1.isHidden = false;
        	this.Leg1_l_Seg1.isHidden = false;
        	this.Leg1_r_Seg2.isHidden = false;
        	this.Leg1_l_Seg2.isHidden = false;
        	//this.Pincer_l_Seg0.setRotationPoint(6.0F, 2.0F, -4.0F);
        	this.Pincer_r_Seg0.isHidden = false;
        	this.Pincer_l_Seg0.isHidden = false;
        	this.Pincer_r_Seg1.isHidden = false;
        	this.Pincer_l_Seg1.isHidden = false;
        	this.Pincer_r_Seg2.isHidden = false;
        	this.Pincer_l_Seg2.isHidden = false;
        	this.Pincer_r_Seg3.isHidden = false;
        	this.Pincer_l_Seg3.isHidden = false;
        	//this.Pincer_r_Seg0.setRotationPoint(-6.0F, 2.0F, -4.0F);
        	
        	this.Leg0_r_Seg0.rotateAngleZ = -0.5F + MathHelper.cos(limbSwing) * 0.7F * limbSwingAmount;
            this.Leg0_l_Seg0.rotateAngleZ = 0.5F + MathHelper.cos(limbSwing + ((float)Math.PI * 0.5F)) * 0.7F * limbSwingAmount;
            this.Leg1_r_Seg0.rotateAngleZ = -0.5F + MathHelper.cos(limbSwing + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.Leg1_l_Seg0.rotateAngleZ = 0.5F + MathHelper.cos(limbSwing + ((float)Math.PI * 1.5F)) * 0.7F * limbSwingAmount;
        }
        else
        {
        	if(((EntityMimic)entityIn).isSitting() || entityIn.ticksExisted % 3000.0F > 2976.0F) {
        		this.Chest_top.rotateAngleX = -0.2F + (-0.02F * MathHelper.sin(0.12F * entityIn.ticksExisted + 0.1F)); 
            	this.Chest_Base.setRotationPoint(0.0F, 18.0F, 1.0F);
            	this.Eye_r.setRotationPoint(-2.0F, -4.0F, -5.0F);
            	this.Eye_l.setRotationPoint(2.0F, -4.0F, -5.0F);
        	}
        	else {
            	this.Chest_top.rotateAngleX = 0.0F;
            	this.Chest_Base.setRotationPoint(0.0F, 19.0F, 1.0F);
            	this.Eye_r.setRotationPoint(0.0F, 0.0F, 0.0F);
            	this.Eye_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        	}
        	
        	this.Chest_Base.rotateAngleY = 0.0F;
        	this.Leg0_r_Seg0.isHidden = true;
        	this.Leg0_l_Seg0.isHidden = true;
        	this.Leg0_r_Seg1.isHidden = true;
        	this.Leg0_l_Seg1.isHidden = true;
        	this.Leg0_r_Seg2.isHidden = true;
        	this.Leg0_l_Seg2.isHidden = true;
        	this.Leg1_r_Seg0.isHidden = true;
        	this.Leg1_l_Seg0.isHidden = true;
        	this.Leg1_r_Seg1.isHidden = true;
        	this.Leg1_l_Seg1.isHidden = true;
        	this.Leg1_r_Seg2.isHidden = true;
        	this.Leg1_l_Seg2.isHidden = true;
        	//this.Pincer_l_Seg0.setRotationPoint(-5.0F, 2.0F, 2.0F);
        	this.Pincer_r_Seg0.isHidden = true;
        	this.Pincer_l_Seg0.isHidden = true;
        	this.Pincer_r_Seg1.isHidden = true;
        	this.Pincer_l_Seg1.isHidden = true;
        	this.Pincer_r_Seg2.isHidden = true;
        	this.Pincer_l_Seg2.isHidden = true;
        	this.Pincer_r_Seg3.isHidden = true;
        	this.Pincer_l_Seg3.isHidden = true;
        	//this.Pincer_r_Seg0.setRotationPoint(5.0F, 2.0F, 2.0F);
        }   	
    }
}
