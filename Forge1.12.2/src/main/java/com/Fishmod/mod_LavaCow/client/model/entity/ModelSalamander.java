package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelOSalacannon - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelSalamander extends FishModelBase {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Body2;
    public ModelRenderer Body3;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer CannonBase;
    public ModelRenderer Jaw_upper;
    public ModelRenderer Jaw_lower;
    public ModelRenderer Tooth_upper;
    public ModelRenderer Tooth_r;
    public ModelRenderer Tooth_l;
    public ModelRenderer Tooth_lower;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;
    public ModelRenderer Tail3;
    public ModelRenderer RightArm2;
    public ModelRenderer LeftArm2;
    public ModelRenderer RightLeg2;
    public ModelRenderer LeftLeg2;
    public ModelRenderer Cannon1;
    public ModelRenderer Cannon2;
    public ModelRenderer Saddle_base;
    public ModelRenderer Cannon3;

	private ModelSalamanderNymph ChildModel = new ModelSalamanderNymph();
    public ModelSalamander() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Tail2 = new ModelRenderer(this, 64, 20);
        this.Tail2.setRotationPoint(0.0F, -1.0F, 8.0F);
        this.Tail2.addBox(-3.0F, -2.0F, 0.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Tail2, 0.0911061832922575F, 0.0F, 0.0F);
        this.Cannon3 = new ModelRenderer(this, 58, 50);
        this.Cannon3.setRotationPoint(0.0F, -1.0F, -6.0F);
        this.Cannon3.addBox(-2.0F, -2.0F, -10.0F, 4, 4, 10, 0.0F);
        this.LeftLeg2 = new ModelRenderer(this, 78, 0);
        this.LeftLeg2.mirror = true;
        this.LeftLeg2.setRotationPoint(2.0F, 3.5F, 1.0F);
        this.LeftLeg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(LeftLeg2, 0.2275909337942703F, 0.0F, 0.500909508638178F);
        this.Tooth_r = new ModelRenderer(this, 32, 46);
        this.Tooth_r.setRotationPoint(-4.0F, 2.0F, -9.0F);
        this.Tooth_r.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Tooth_r, 0.5604252334680235F, 0.9105382388075086F, 0.0F);
        this.Saddle_base = new ModelRenderer(this, 88, 60);
        this.Saddle_base.setRotationPoint(-4.0F, -7.0F, 1.0F);
        this.Saddle_base.addBox(0.0F, 0.0F, 0.0F, 8, 2, 2, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 18);
        this.RightArm.setRotationPoint(-4.0F, 0.0F, 2.6F);
        this.RightArm.addBox(-4.0F, -2.0F, -4.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(RightArm, 0.0911061832922575F, 0.0F, 0.956091342937205F);
        this.Tooth_l = new ModelRenderer(this, 32, 46);
        this.Tooth_l.setRotationPoint(4.0F, 2.0F, -9.0F);
        this.Tooth_l.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Tooth_l, 0.5604252334680235F, -0.9093165136574348F, 0.0F);
        this.Jaw_lower = new ModelRenderer(this, 0, 48);
        this.Jaw_lower.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.Jaw_lower.addBox(-5.0F, 1.0F, -10.0F, 10, 4, 12, 0.0F);
        this.setRotateAngle(Jaw_lower, 0.19547687289441354F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 18);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(4.0F, 0.0F, 2.6F);
        this.LeftArm.addBox(0.0F, -2.0F, -4.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(LeftArm, 0.0911061832922575F, 0.0F, -0.956091342937205F);
        this.RightLeg = new ModelRenderer(this, 0, 18);
        this.RightLeg.setRotationPoint(-4.0F, -2.0F, 12.0F);
        this.RightLeg.addBox(-4.0F, -2.0F, -2.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(RightLeg, -0.2275909337942703F, 0.0F, 0.4098033003787853F);
        this.Body2 = new ModelRenderer(this, 84, 0);
        this.Body2.setRotationPoint(0.0F, -4.0F, 2.0F);
        this.Body2.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
        this.setRotateAngle(Body2, 0.591841146688116F, 0.0F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 92, 20);
        this.Tail1.setRotationPoint(0.0F, 2.0F, 6.0F);
        this.Tail1.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 18);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(4.0F, -2.0F, 12.0F);
        this.LeftLeg.addBox(0.0F, -2.0F, -2.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(LeftLeg, -0.2275909337942703F, 0.0F, -0.4098033003787853F);
        this.Body3 = new ModelRenderer(this, 41, 0);
        this.Body3.setRotationPoint(0.0F, -4.0F, 10.0F);
        this.Body3.addBox(-4.5F, -4.0F, -6.0F, 9, 8, 12, 0.0F);
        this.setRotateAngle(Body3, -0.18203784630933073F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -7.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 4, 8, 0.0F);
        this.Jaw_upper = new ModelRenderer(this, 30, 0);
        this.Jaw_upper.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.Jaw_upper.addBox(-4.0F, -1.0F, -2.0F, 8, 2, 2, 0.0F);
        this.Cannon1 = new ModelRenderer(this, 92, 38);
        this.Cannon1.setRotationPoint(0.0F, -6.5F, 0.0F);
        this.Cannon1.addBox(-4.0F, -5.0F, -6.0F, 8, 8, 10, 0.0F);
        this.setRotateAngle(Cannon1, 0.4098033003787853F, 0.0F, 0.0F);
        this.Tail3 = new ModelRenderer(this, 40, 20);
        this.Tail3.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.Tail3.addBox(-2.0F, -1.0F, 0.0F, 4, 4, 8, 0.0F);
        this.setRotateAngle(Tail3, 0.0911061832922575F, 0.0F, 0.0F);
        this.RightArm2 = new ModelRenderer(this, 78, 0);
        this.RightArm2.setRotationPoint(-2.0F, 4.0F, -1.0F);
        this.RightArm2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(RightArm2, -0.0911061832922575F, 0.0F, -0.9536478926370572F);
        this.Tooth_lower = new ModelRenderer(this, 34, 53);
        this.Tooth_lower.setRotationPoint(0.0F, 1.0F, -7.0F);
        this.Tooth_lower.addBox(-4.0F, -1.0F, -2.5F, 8, 1, 6, 0.0F);
        this.Cannon2 = new ModelRenderer(this, 68, 34);
        this.Cannon2.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.Cannon2.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 6, 0.0F);
        this.Body = new ModelRenderer(this, 0, 16);
        this.Body.setRotationPoint(0.0F, 17.5F, -8.2F);
        this.Body.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 22, 0.0F);
        this.RightLeg2 = new ModelRenderer(this, 78, 0);
        this.RightLeg2.setRotationPoint(-2.0F, 3.5F, 1.0F);
        this.RightLeg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(RightLeg2, 0.2275909337942703F, 0.0F, -0.500909508638178F);
        this.CannonBase = new ModelRenderer(this, 76, 46);
        this.CannonBase.setRotationPoint(0.0F, -8.0F, 7.0F);
        this.CannonBase.addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(CannonBase, -0.4098033003787853F, 0.0F, 0.0F);
        this.Tooth_upper = new ModelRenderer(this, 34, 46);
        this.Tooth_upper.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.Tooth_upper.addBox(-3.5F, -0.5F, -2.0F, 7, 1, 6, 0.0F);
        this.LeftArm2 = new ModelRenderer(this, 78, 0);
        this.LeftArm2.mirror = true;
        this.LeftArm2.setRotationPoint(2.0F, 4.0F, -1.0F);
        this.LeftArm2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(LeftArm2, -0.0911061832922575F, 0.0F, 0.9536478926370572F);
        this.Tail1.addChild(this.Tail2);
        this.Cannon2.addChild(this.Cannon3);
        this.LeftLeg.addChild(this.LeftLeg2);
        this.Jaw_lower.addChild(this.Tooth_r);
        this.Cannon1.addChild(this.Saddle_base);
        this.Body.addChild(this.RightArm);
        this.Jaw_lower.addChild(this.Tooth_l);
        this.Head.addChild(this.Jaw_lower);
        this.Body.addChild(this.LeftArm);
        this.Body.addChild(this.RightLeg);
        this.Body.addChild(this.Body2);
        this.Body3.addChild(this.Tail1);
        this.Body.addChild(this.LeftLeg);
        this.Body.addChild(this.Body3);
        this.Body.addChild(this.Head);
        this.Head.addChild(this.Jaw_upper);
        this.CannonBase.addChild(this.Cannon1);
        this.Tail2.addChild(this.Tail3);
        this.RightArm.addChild(this.RightArm2);
        this.Jaw_lower.addChild(this.Tooth_lower);
        this.Cannon1.addChild(this.Cannon2);
        this.RightLeg.addChild(this.RightLeg2);
        this.Body.addChild(this.CannonBase);
        this.Jaw_upper.addChild(this.Tooth_upper);
        this.LeftArm.addChild(this.LeftArm2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        if(!((EntitySalamander)entity).isNymph()) {
        	this.Body.render(f5);
        } else {
        	ChildModel.render(entity, f, f1, f2, f3, f4, f5);
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {  	
    	EntitySalamander Entity = ((EntitySalamander)entity);
    	float i = Entity.getAttackTimer() / 80.0F;
    	float Anime_threshold[] = {0.85F, 0.67F, 0.12F};
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);    	
    	
    	if (!Entity.isNymph()) {	        
        	if(Entity.isChild()) {
        		if(Entity.getGrowingStage() == 1) {
	        		this.Tooth_l.isHidden = true;
	        		this.Tooth_r.isHidden = true;
        		} else {
            		this.Tooth_l.isHidden = false;
            		this.Tooth_r.isHidden = false;
        		}
        		
        		this.CannonBase.isHidden = true;
        		this.Cannon1.isHidden = true;
        		this.Cannon2.isHidden = true;
        		this.Cannon3.isHidden = true;
        	} else {
        		this.Tooth_l.isHidden = false;
        		this.Tooth_r.isHidden = false;
        		this.CannonBase.isHidden = false;
        		this.Cannon1.isHidden = false;
        		this.Cannon2.isHidden = false;
        		this.Cannon3.isHidden = false;
        	}
	    	
	    	if(Entity.isSitting() && !Entity.isBeingRidden()) {
		        this.Head_Looking(this.Head, -0.35185837453889574F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.rotateAngleZ = 0.0F;
	    		this.Jaw_lower.rotateAngleX = 0.19547687289441354F + (-0.08F * MathHelper.sin(0.03F * ageInTicks));
	    		
		        this.RightArm.rotateAngleX = -0.7689920923971514F;
		        this.LeftArm.rotateAngleX = -0.7689920923971514F;
		        this.RightLeg.rotateAngleX = 0.6326818538479445F;
		        this.LeftLeg.rotateAngleX = 0.6326818538479445F;
		        
		        this.RightArm.rotateAngleZ = 0.956091342937205F;
		        this.LeftArm.rotateAngleZ = -0.956091342937205F;
		        this.RightLeg.rotateAngleZ = 0.40980330836826856F;
		        this.LeftLeg.rotateAngleZ = -0.40980330836826856F;
		        		        
		        this.Body.rotateAngleX = 0.0F;
		        this.Body.rotationPointY = 20.0F;	
		        
		        this.Tail1.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks);
		        this.Tail2.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.02F);
		        this.Tail3.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.04F);
		        
		        this.Cannon2.rotationPointZ = -3.0F;
		        this.Cannon3.rotationPointZ = -3.0F;
	    	} else if (i <= Anime_threshold[0] && i > Anime_threshold[1]) {
	    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.rotateAngleZ = GradientAnimation(0.0F, -0.36425021489121656F, j * (i - Anime_threshold[1]));
	    		this.Jaw_lower.rotateAngleX = GradientAnimation(0.0F, 0.6829473363053812F, j * (i - Anime_threshold[1]));
	    		
		        this.RightArm.rotateAngleX = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
		        this.LeftArm.rotateAngleX = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
		        this.RightLeg.rotateAngleX = -0.22759093446006054F;
		        this.LeftLeg.rotateAngleX = -0.22759093446006054F;

		        this.RightArm.rotateAngleZ = 0.9560913642424937F;
		        this.LeftArm.rotateAngleZ = -0.9560913642424937F;
		        this.RightLeg.rotateAngleZ = 0.40980330836826856F;
		        this.LeftLeg.rotateAngleZ = -0.40980330836826856F;
		        
		        this.Body.rotateAngleX = GradientAnimation(0.0F, -0.18203784098300857F, j * (i - Anime_threshold[1]));
		        this.Body.rotationPointY = GradientAnimation(17.5F, 16.2F, j * (i - Anime_threshold[1]));
		        
		        this.Tail1.rotateAngleY = GradientAnimation(0.0F, -0.136659280431156F, j * (i - Anime_threshold[1]));
		        this.Tail2.rotateAngleY = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
		        this.Tail3.rotateAngleY = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
		        
		        this.Cannon2.rotationPointZ = -3.0F;
		        this.Cannon3.rotationPointZ = -3.0F;
	    	} else if (i <= Anime_threshold[1] && i > Anime_threshold[2]) {
	    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.rotateAngleZ = GradientAnimation(-0.36425021489121656F, 0.0F, k * (i - Anime_threshold[2]));
	    		this.Jaw_lower.rotateAngleX = GradientAnimation(0.6829473363053812F, 0.0F, k * (i - Anime_threshold[2]));
	    		
		        this.RightArm.rotateAngleX = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
		        this.LeftArm.rotateAngleX = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
		        this.RightLeg.rotateAngleX = -0.22759093446006054F;
		        this.LeftLeg.rotateAngleX = -0.22759093446006054F;

		        this.RightArm.rotateAngleZ = 0.9560913642424937F;
		        this.LeftArm.rotateAngleZ = -0.9560913642424937F;
		        this.RightLeg.rotateAngleZ = 0.40980330836826856F;
		        this.LeftLeg.rotateAngleZ = -0.40980330836826856F;
		        
		        this.Body.rotateAngleX = GradientAnimation(-0.18203784098300857F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Body.rotationPointY = GradientAnimation(16.2F, 17.5F, k * (i - Anime_threshold[2]));
		        
		        this.Tail1.rotateAngleY = GradientAnimation(-0.136659280431156F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Tail2.rotateAngleY = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Tail3.rotateAngleY = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
		        
		        this.Cannon2.rotationPointZ = -1.8F + 1.2F * MathHelper.sin(1.0F * ageInTicks);
		        this.Cannon3.rotationPointZ = -1.3F + 1.7F * MathHelper.sin(1.0F * ageInTicks);
	    	} else {
	    		final float walking_f = 0.44F;
	    		final float walking_a = 0.4F;
	    		
		        this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.rotateAngleZ = 0.0F;
	    		this.Jaw_lower.rotateAngleX = 0.19547687289441354F + (-0.08F * MathHelper.sin(0.03F * ageInTicks));
	    		
		        this.RightArm.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        this.LeftArm.rotateAngleX = 0.091106186954104F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.RightLeg.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.LeftLeg.rotateAngleX = -0.22759093446006054F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        
		        this.RightArm.rotateAngleZ = 0.9560913642424937F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        this.LeftArm.rotateAngleZ = -0.9560913642424937F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.RightLeg.rotateAngleZ = 0.40980330836826856F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.LeftLeg.rotateAngleZ = -0.40980330836826856F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;

		        this.RightArm.rotationPointY = 0.0F + MathHelper.cos(limbSwing * walking_f) * 1.0F * limbSwingAmount;
		        this.LeftArm.rotationPointY = 0.0F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * 1.0F * limbSwingAmount;
		        this.RightLeg.rotationPointY = -0.2F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * 1.0F * limbSwingAmount;
		        this.LeftLeg.rotationPointY = -0.2F + MathHelper.cos(limbSwing * walking_f) * 1.0F * limbSwingAmount;
		        
		        this.Body.rotateAngleX = 0.0F;
		        this.Body.rotationPointY = 17.5F;	
		        
		        this.Tail1.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks);
		        this.Tail2.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.02F);
		        this.Tail3.rotateAngleY = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.04F);
		        
		        this.Cannon2.rotationPointZ = -3.0F;
		        this.Cannon3.rotationPointZ = -3.0F;
	    	}
    	} else {
    		ChildModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
    	}
    }
}
