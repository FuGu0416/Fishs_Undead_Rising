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
    public ModelRenderer Head;
    public ModelRenderer Tail0;
    public ModelRenderer leg0_r0;
    public ModelRenderer leg0_l0;
    public ModelRenderer leg1_r0;
    public ModelRenderer leg1_l0;
    public ModelRenderer Jaw;
    public ModelRenderer Head_Gill_l0;
    public ModelRenderer Head_Gill_l1;
    public ModelRenderer Head_Gill_l2;
    public ModelRenderer Head_Gill_r0;
    public ModelRenderer Head_Gill_r1;
    public ModelRenderer Head_Gill_r2;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;
    public ModelRenderer leg0_r1;
    public ModelRenderer leg0_l1;
    public ModelRenderer leg1_r1;
    public ModelRenderer leg1_l1;

    public ModelSalamanderNymph() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Head_Gill_r2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r2.setRotationPoint(-2.0F, -0.5F, -1.0F);
        this.Head_Gill_r2.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r2, 0.18203784630933073F, 0.591841146688116F, -0.500909508638178F);
        this.leg1_r0 = new ModelRenderer(this, 42, 0);
        this.leg1_r0.setRotationPoint(-2.5F, 0.0F, -2.0F);
        this.leg1_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_r0, 0.4098033003787853F, 0.0F, 0.4098033003787853F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Head.addBox(-2.5F, -1.5F, -7.0F, 5, 2, 7, 0.0F);
        this.setRotateAngle(Head, -0.19547687289441354F, 0.0F, 0.0F);
        this.Head_Gill_l2 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l2.mirror = true;
        this.Head_Gill_l2.setRotationPoint(2.0F, -0.5F, -1.0F);
        this.Head_Gill_l2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l2, 0.18203784630933073F, -0.591841146688116F, 0.500909508638178F);
        this.Head_Gill_l1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l1.mirror = true;
        this.Head_Gill_l1.setRotationPoint(2.0F, -0.6F, -1.0F);
        this.Head_Gill_l1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l1, 0.0F, -0.8196066007575706F, 0.0F);
        this.Tail2 = new ModelRenderer(this, 24, 7);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail2.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail2, 0.3312634840390405F, 0.0F, 0.0F);
        this.Head_Gill_r1 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r1.setRotationPoint(-2.0F, -0.6F, -1.0F);
        this.Head_Gill_r1.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r1, 0.0F, 0.8196066007575706F, 0.0F);
        this.Body_Base = new ModelRenderer(this, 0, 17);
        this.Body_Base.setRotationPoint(0.0F, 21.0F, 5.0F);
        this.Body_Base.addBox(-3.0F, -1.5F, -12.0F, 6, 3, 12, 0.0F);
        this.setRotateAngle(Body_Base, -0.07103490355222543F, 0.0F, 0.0F);
        this.Tail0 = new ModelRenderer(this, 25, 0);
        this.Tail0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Tail0.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 4, 0.0F);
        this.setRotateAngle(Tail0, 0.035430183316142186F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 9);
        this.Jaw.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.Jaw.addBox(-2.5F, -0.5F, -7.0F, 5, 1, 7, 0.0F);
        this.setRotateAngle(Jaw, 0.4300491170387584F, 0.0F, 0.0F);
        this.leg0_r1 = new ModelRenderer(this, 43, 5);
        this.leg0_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_r1, 0.0F, 0.0F, -0.45535640450848164F);
        this.leg1_l0 = new ModelRenderer(this, 42, 0);
        this.leg1_l0.mirror = true;
        this.leg1_l0.setRotationPoint(2.5F, 0.0F, -2.0F);
        this.leg1_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg1_l0, 0.4098033003787853F, 0.0F, -0.4098033003787853F);
        this.Head_Gill_l0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_l0.mirror = true;
        this.Head_Gill_l0.setRotationPoint(1.9F, -0.8F, -1.5F);
        this.Head_Gill_l0.addBox(0.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_l0, -0.13665927909957545F, -0.8651597048872669F, -0.500909508638178F);
        this.Head_Gill_r0 = new ModelRenderer(this, 20, 0);
        this.Head_Gill_r0.setRotationPoint(-1.9F, -0.8F, -1.5F);
        this.Head_Gill_r0.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 0, 0.0F);
        this.setRotateAngle(Head_Gill_r0, -0.13665927909957545F, 0.8651597048872669F, 0.500909508638178F);
        this.leg0_l1 = new ModelRenderer(this, 43, 5);
        this.leg0_l1.mirror = true;
        this.leg0_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg0_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg0_l1, 0.0F, 0.0F, 0.45535640450848164F);
        this.leg0_l0 = new ModelRenderer(this, 42, 0);
        this.leg0_l0.mirror = true;
        this.leg0_l0.setRotationPoint(2.5F, 0.0F, -10.0F);
        this.leg0_l0.addBox(0.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_l0, 0.0F, 0.0F, -0.4098033003787853F);
        this.leg1_l1 = new ModelRenderer(this, 43, 5);
        this.leg1_l1.mirror = true;
        this.leg1_l1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_l1.addBox(0.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_l1, 0.0F, 0.0F, 0.45535640450848164F);
        this.leg1_r1 = new ModelRenderer(this, 43, 5);
        this.leg1_r1.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.leg1_r1.addBox(-1.0F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(leg1_r1, 0.0F, 0.0F, -0.45535640450848164F);
        this.leg0_r0 = new ModelRenderer(this, 42, 0);
        this.leg0_r0.setRotationPoint(-2.5F, 0.0F, -10.0F);
        this.leg0_r0.addBox(-1.0F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(leg0_r0, 0.0F, 0.0F, 0.4098033003787853F);
        this.Tail1 = new ModelRenderer(this, 24, 7);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.Tail1.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(Tail1, 0.27209683304907706F, 0.0F, 0.0F);
        this.Head.addChild(this.Head_Gill_r2);
        this.Body_Base.addChild(this.leg1_r0);
        this.Body_Base.addChild(this.Head);
        this.Head.addChild(this.Head_Gill_l2);
        this.Head.addChild(this.Head_Gill_l1);
        this.Tail1.addChild(this.Tail2);
        this.Head.addChild(this.Head_Gill_r1);
        this.Body_Base.addChild(this.Tail0);
        this.Head.addChild(this.Jaw);
        this.leg0_r0.addChild(this.leg0_r1);
        this.Body_Base.addChild(this.leg1_l0);
        this.Head.addChild(this.Head_Gill_l0);
        this.Head.addChild(this.Head_Gill_r0);
        this.leg0_l0.addChild(this.leg0_l1);
        this.Body_Base.addChild(this.leg0_l0);
        this.leg1_l0.addChild(this.leg1_l1);
        this.leg1_r0.addChild(this.leg1_r1);
        this.Body_Base.addChild(this.leg0_r0);
        this.Tail0.addChild(this.Tail1);
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
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    	if(((EntitySalamander)entity).isSitting()) {
    		this.Body_Base.rotationPointY = 23.4F;
    		this.Body_Base.rotateAngleX = -0.07103490055616922F;
    		
			this.Head.rotateAngleX = -0.6646214111173737F + headPitch * 0.017453292F;
            this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
            this.Jaw.rotateAngleX = 0.08F - 0.08F * MathHelper.sin(0.03F * ageInTicks);

            this.Head_Gill_l0.rotateAngleY = -0.8651597102135892F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_l1.rotateAngleY = -0.8196066167365371F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_l2.rotateAngleY = -0.5918411493512771F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
            this.Head_Gill_r0.rotateAngleY = 0.8651597102135892F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_r1.rotateAngleY = 0.8196066167365371F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_r2.rotateAngleY = 0.5918411493512771F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);

            this.Tail0.rotateAngleX = 0.08F + 0.08F * MathHelper.sin(0.06F * ageInTicks);
            this.Tail1.rotateAngleX = 0.135F + 0.135F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.2F);
            this.Tail2.rotateAngleX = 0.165F + 0.165F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.4F);
 
	        this.leg0_r0.rotateAngleX = -1.1728612040769677F;
	        this.leg0_l0.rotateAngleX = -1.1728612040769677F;
	        this.leg1_r0.rotateAngleX = 1.5044737619558903F;
	        this.leg1_l0.rotateAngleX = 1.5044737619558903F;
	        
	        this.leg0_r0.rotateAngleZ = 0.40980330836826856F;
	        this.leg0_l0.rotateAngleZ = -0.40980330836826856F;
	        this.leg1_r0.rotateAngleZ = 0.40980330836826856F;
	        this.leg1_l0.rotateAngleZ = -0.40980330836826856F;        
    	} else if(((EntitySalamander)entity).isAggressive()) {
			this.Body_Base.rotationPointY = 21.5F;
			this.Body_Base.rotateAngleX = -0.6829473363053812F;
			
	        this.Head.rotateAngleX = 0.5462880558742251F + headPitch * 0.017453292F;
	        this.Head.rotateAngleY = 0.0F;
        	this.Jaw.rotateAngleX = 0.5F;
	        
            this.Head_Gill_l0.rotateAngleY = 0.0F;
            this.Head_Gill_l1.rotateAngleY = 0.0F;
            this.Head_Gill_l2.rotateAngleY = 0.0F;
            this.Head_Gill_r0.rotateAngleY = 0.0F;
            this.Head_Gill_r1.rotateAngleY = 0.0F;
            this.Head_Gill_r2.rotateAngleY = 0.0F;
			
			this.Tail0.rotateAngleX = 0.5462880558742251F;
            this.Tail1.rotateAngleX = 0.27209683304907706F;
            this.Tail2.rotateAngleX = 0.3312634840390405F;
            
            this.leg0_r0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            this.leg0_l0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_r0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_l0.rotateAngleX = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            
            this.leg0_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
            this.leg0_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_r0.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
            this.leg1_l0.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
		} else {
			this.Body_Base.rotationPointY = 21.0F;
			this.Body_Base.rotateAngleX = -0.07103490055616922F;
			
			this.Head.rotateAngleX = headPitch * 0.017453292F;
            this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
            this.Jaw.rotateAngleX = 0.08F - 0.08F * MathHelper.sin(0.03F * ageInTicks);
            
            this.Head_Gill_l0.rotateAngleY = -0.8651597102135892F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_l1.rotateAngleY = -0.8196066167365371F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_l2.rotateAngleY = -0.5918411493512771F + 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
            this.Head_Gill_r0.rotateAngleY = 0.8651597102135892F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.0F);
            this.Head_Gill_r1.rotateAngleY = 0.8196066167365371F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.2F);
            this.Head_Gill_r2.rotateAngleY = 0.5918411493512771F - 0.135F * MathHelper.sin(0.18F * ageInTicks + (float)Math.PI * 0.4F);
            
            this.Tail0.rotateAngleX = 0.08F + 0.08F * MathHelper.sin(0.06F * ageInTicks);
            this.Tail1.rotateAngleX = 0.135F + 0.135F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.2F);
            this.Tail2.rotateAngleX = 0.165F + 0.165F * MathHelper.sin(0.12F * ageInTicks + (float)Math.PI * 0.4F);
            
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
