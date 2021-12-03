package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelOSalacannon - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelSalamanderNymph extends FishModelBase {
    public ModelRenderer Body_Base;
    public ModelRenderer Head_c;
    public ModelRenderer Tail0;
    public ModelRenderer leg0_r0;
    public ModelRenderer leg0_l0;
    public ModelRenderer leg1_r0;
    public ModelRenderer leg1_l0;
    public ModelRenderer Jaw;
    public ModelRenderer Tail1_c;
    public ModelRenderer Tail2_c;
    public ModelRenderer leg0_r1;
    public ModelRenderer leg0_l1;
    public ModelRenderer leg1_r1;
    public ModelRenderer leg1_l1;
    public ModelRenderer Head_Gill_l0;
    public ModelRenderer Head_Gill_l1;
    public ModelRenderer Head_Gill_l2;
    public ModelRenderer Head_Gill_r0;
    public ModelRenderer Head_Gill_r1;
    public ModelRenderer Head_Gill_r2;

    public ModelSalamanderNymph() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body_Base = new ModelRenderer(this, 0, 17);
        this.Body_Base.setRotationPoint(0.0F, 21.0F, 5.0F);
        this.Body_Base.addBox(-3.0F, -1.5F, -12.0F, 6, 3, 12, 0.0F);
        this.setRotateAngle(Body_Base, -0.07103490055616922F, 0.0F, 0.0F);
        this.leg1_l0 = new ModelRenderer(this, 42, 0);
        this.leg1_l0.mirror = true;
        this.leg1_l0.setRotationPoint(2.5F, 0.0F, -2.0F);
        this.leg1_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_l0, 0.40980330836826856F, 0.0F, -0.40980330836826856F);
        this.Tail2_c = new ModelRenderer(this, 24, 7);
        this.Tail2_c.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail2_c.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail2_c, 0.3312634920285238F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 9);
        this.Jaw.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.Jaw.addBox(-2.5F, -0.5F, -7.0F, 5, 1, 7, 0.0F);
        this.leg1_l1 = new ModelRenderer(this, 43, 5);
        this.leg1_l1.mirror = true;
        this.leg1_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_l1, 0.0F, 0.0F, 0.4553564018453205F);
        this.Head_c = new ModelRenderer(this, 0, 0);
        this.Head_c.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Head_c.addBox(-2.5F, -1.5F, -7.0F, 5, 2, 7, 0.0F);
        this.leg0_l0 = new ModelRenderer(this, 42, 0);
        this.leg0_l0.mirror = true;
        this.leg0_l0.setRotationPoint(2.5F, 0.0F, -10.0F);
        this.leg0_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_l0, 0.0F, 0.0F, -0.40980330836826856F);
        this.leg1_r0 = new ModelRenderer(this, 42, 0);
        this.leg1_r0.setRotationPoint(-2.5F, 0.0F, -2.0F);
        this.leg1_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_r0, 0.40980330836826856F, 0.0F, 0.40980330836826856F);
        this.leg1_r1 = new ModelRenderer(this, 43, 5);
        this.leg1_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_r1, 0.0F, 0.0F, -0.4553564018453205F);
        this.leg0_r1 = new ModelRenderer(this, 43, 5);
        this.leg0_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_r1, 0.0F, 0.0F, -0.4553564018453205F);
        this.Tail1_c = new ModelRenderer(this, 24, 7);
        this.Tail1_c.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.Tail1_c.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail1_c, 0.272096830385916F, 0.0F, 0.0F);
        this.Tail0 = new ModelRenderer(this, 25, 0);
        this.Tail0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail0.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 4, 0.0F);
        this.setRotateAngle(Tail0, 0.035430183815484885F, 0.0F, 0.0F);
        this.leg0_r0 = new ModelRenderer(this, 42, 0);
        this.leg0_r0.setRotationPoint(-2.5F, 0.0F, -10.0F);
        this.leg0_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_r0, 0.0F, 0.0F, 0.40980330836826856F);
        this.leg0_l1 = new ModelRenderer(this, 43, 5);
        this.leg0_l1.mirror = true;
        this.leg0_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_l1, 0.0F, 0.0F, 0.4553564018453205F);
        
        this.Head_Gill_l2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l2.mirror = true;
        this.Head_Gill_l2.setRotationPoint(2.0F, -0.5F, -1.0F);
        this.Head_Gill_l2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l2, 0.18203784098300857F, -0.5918411493512771F, 0.5009094953223726F);
        this.Head_Gill_l1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l1.mirror = true;
        this.Head_Gill_l1.setRotationPoint(2.0F, -0.6F, -1.0F);
        this.Head_Gill_l1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l1, 0.0F, -0.8196066167365371F, 0.0F);
        this.Head_Gill_r2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r2.setRotationPoint(-2.0F, -0.5F, -1.0F);
        this.Head_Gill_r2.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r2, 0.18203784098300857F, 0.5918411493512771F, -0.5009094953223726F);
        this.Head_Gill_r1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r1.setRotationPoint(-2.0F, -0.6F, -1.0F);
        this.Head_Gill_r1.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r1, 0.0F, 0.8196066167365371F, 0.0F);
        this.Head_Gill_l0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l0.mirror = true;
        this.Head_Gill_l0.setRotationPoint(1.9F, -0.8F, -1.5F);
        this.Head_Gill_l0.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l0, -0.136659280431156F, -0.8651597102135892F, -0.5009094953223726F);
        this.Head_Gill_r0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r0.setRotationPoint(-1.9F, -0.9F, -1.5F);
        this.Head_Gill_r0.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r0, -0.136659280431156F, 0.8651597102135892F, 0.5009094953223726F);
        
        this.Body_Base.addChild(this.leg1_l0);
        this.Tail1_c.addChild(this.Tail2_c);
        this.Head_c.addChild(this.Jaw);
        this.leg1_l0.addChild(this.leg1_l1);
        this.Body_Base.addChild(this.Head_c);
        this.Body_Base.addChild(this.leg0_l0);
        this.Body_Base.addChild(this.leg1_r0);
        this.leg1_r0.addChild(this.leg1_r1);
        this.leg0_r0.addChild(this.leg0_r1);
        this.Tail0.addChild(this.Tail1_c);
        this.Body_Base.addChild(this.Tail0);
        this.Body_Base.addChild(this.leg0_r0);
        this.leg0_l0.addChild(this.leg0_l1);
        
        this.Head_c.addChild(this.Head_Gill_l2);
        this.Head_c.addChild(this.Head_Gill_l1);
        this.Head_c.addChild(this.Head_Gill_r2);
        this.Head_c.addChild(this.Head_Gill_r1);
        this.Head_c.addChild(this.Head_Gill_l0);
        this.Head_c.addChild(this.Head_Gill_r0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.Body_Base.render(f5);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	EntitySalamander Entity = ((EntitySalamander)entityIn);
    	
		if(Entity.isAggressive()/* && limbSwing == 0*/)
		{
			this.Body_Base.rotationPointY = 21.5F;
			this.Body_Base.rotateAngleX = -0.6829473363053812F;
	        this.setRotateAngle(Body_Base, -0.6829473363053812F, 0.0F, 0.0F);
			
	        this.Head_c.rotateAngleX = 0.5462880558742251F + headPitch * 0.017453292F;
	        this.Head_c.rotateAngleY = 0.0F;
	        this.Jaw.rotateAngleX = 0.50F;
	        
            this.Head_Gill_l0.rotateAngleY = 0.0F;
            this.Head_Gill_l1.rotateAngleY = 0.0F;
            this.Head_Gill_l2.rotateAngleY = 0.0F;
            this.Head_Gill_r0.rotateAngleY = 0.0F;
            this.Head_Gill_r1.rotateAngleY = 0.0F;
            this.Head_Gill_r2.rotateAngleY = 0.0F;
			
			this.Tail0.rotateAngleX = 0.5462880558742251F;
		}
		else
		{
			this.Body_Base.rotationPointY = 21.0F;
			this.Body_Base.rotateAngleX = -0.07103490055616922F;
			this.Head_c.rotateAngleX = headPitch * 0.017453292F;
            this.Head_c.rotateAngleY = netHeadYaw * 0.017453292F;
            this.Jaw.rotateAngleX = 0.08F - 0.08F * MathHelper.sin(0.03F * ageInTicks);
            
            this.Head_Gill_l0.rotateAngleY = -0.8651597102135892F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_l1.rotateAngleY = -0.8196066167365371F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_l2.rotateAngleY = -0.5918411493512771F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
            this.Head_Gill_r0.rotateAngleY = 0.8651597102135892F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_r1.rotateAngleY = 0.8196066167365371F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_r2.rotateAngleY = 0.5918411493512771F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
            
            this.Tail0.rotateAngleX = 0.08F + 0.08F * MathHelper.sin(0.06F * ageInTicks);
            this.Tail1_c.rotateAngleX = 0.135F + 0.135F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.2F);
            this.Tail2_c.rotateAngleX = 0.165F + 0.165F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.4F);
		}
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
        this.leg0_r0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leg0_l0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.leg1_r0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.leg1_l0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        
        this.leg0_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.leg0_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.leg1_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.leg1_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
    }
}
