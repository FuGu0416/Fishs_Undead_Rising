package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGhoul - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelUnburied extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Shroom_base;
    public ModelRenderer Neck0;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Neck1;
    public ModelRenderer Head;
    public ModelRenderer Jaw0;
    public ModelRenderer Head_teeth;
    public ModelRenderer Jaw1;
    public ModelRenderer Jaw1_teeth;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_r_Seg1;
    
    public ModelUnburied() {
    	this(0.0F, 0.0F);
    }

    public ModelUnburied(float modelSize, float p_i1149_2_) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Leg_r_Seg0 = new ModelRenderer(this, 32, 36);
        this.Leg_r_Seg0.setRotationPoint(-1.9F, 0.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402793711257F, 0.0F, 0.091106186954104F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 40, 36);
        this.Leg_r_Seg1.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Leg_r_Seg1.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 56);
        this.Body_base.setRotationPoint(0.0F, 10.0F, 2.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, 0.0F);
        this.Head_teeth = new ModelRenderer(this, 0, 15);
        this.Head_teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head_teeth.addBox(-3.5F, 3.0F, -7.5F, 7, 1, 4, 0.0F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setRotationPoint(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck1, -1.2747884856566583F, -0.22759093446006054F, -0.18203784098300857F);
        this.Shroom_base = new ModelRenderer(this, 0, 0);
        this.Shroom_base.setRotationPoint(0.0F, -1.0F, 2.2F);
        this.Shroom_base.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Shroom_base, -1.1383037381507017F, 0.0F, 0.0F);
        this.Jaw0 = new ModelRenderer(this, 24, 0);
        this.Jaw0.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.Jaw0.addBox(-3.5F, -3.5F, -3.0F, 7, 5, 3, 0.0F);
        this.setRotateAngle(Jaw0, 0.7285004297824331F, 0.0F, 0.0F);
        this.Jaw1_teeth = new ModelRenderer(this, 0, 21);
        this.Jaw1_teeth.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Jaw1_teeth.addBox(-2.5F, 0.0F, -7.5F, 5, 1, 4, 0.0F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 40, 22);
        this.Leg_l_Seg1.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.Leg_l_Seg1.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 48, 36);
        this.Arm_r_Seg0.setRotationPoint(-5.0F, -2.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 56, 20);
        this.Arm_l_Seg1.setRotationPoint(1.0F, 7.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 32, 22);
        this.Leg_l_Seg0.setRotationPoint(1.9F, 0.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402793711257F, 0.0F, -0.091106186954104F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 56, 34);
        this.Arm_r_Seg1.setRotationPoint(-1.0F, 7.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 48, 22);
        this.Arm_l_Seg0.setRotationPoint(5.0F, -2.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
        this.Body_chest = new ModelRenderer(this, 0, 35);
        this.Body_chest.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 10, 5, 5, 0.0F);
        this.setRotateAngle(Body_chest, 0.5009094953223726F, 0.0F, -0.091106186954104F);
        this.Neck0 = new ModelRenderer(this, 0, 26);
        this.Neck0.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Neck0, 0.36425021489121656F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 6, 8, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 45);
        this.Body_waist.setRotationPoint(0.0F, -3.2F, 0.0F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 7, 8, 3, 0.0F);
        this.setRotateAngle(Body_waist, 0.4553564018453205F, 0.0F, -0.045553093477052F);
        this.Jaw1 = new ModelRenderer(this, 40, 2);
        this.Jaw1.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Jaw1.addBox(-3.0F, 0.0F, -7.8F, 6, 2, 6, 0.0F);
        this.setRotateAngle(Jaw1, 0.091106186954104F, 0.0F, -0.045553093477052F);
        
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Head.addChild(this.Head_teeth);
        this.Neck0.addChild(this.Neck1);
        this.Body_chest.addChild(this.Shroom_base);
        this.Head.addChild(this.Jaw0);
        this.Jaw1.addChild(this.Jaw1_teeth);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Body_waist.addChild(this.Body_chest);
        this.Body_chest.addChild(this.Neck0);
        this.Neck1.addChild(this.Head);
        this.Body_base.addChild(this.Body_waist);
        this.Jaw0.addChild(this.Jaw1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body_base.render(f5); 
        
        /*this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
        this.bipedHeadwear.render(f5);*/
    }
    
    public void postRenderArm(float scale, EnumHandSide side)
    {
    	this.Body_base.postRender(scale);
    	this.Body_waist.postRender(scale);
    	this.Body_chest.postRender(scale);
    	this.Arm_l_Seg0.postRender(scale);
    	this.Arm_l_Seg1.postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.Arm_l_Seg1 : this.Arm_r_Seg1;
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	
    	//this.Head.postRender(10 * scaleFactor);
    	
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.SwingX_Sin(this.Jaw0, 0.7285004297824331F, ageInTicks, -0.04F, 0.06F, false, 0.0F);
    	this.SwingX_Sin(this.Jaw1, 0.091106186954104F, ageInTicks, -0.03F, 0.06F, false, 0.0F);
    	
    	this.Head.rotationPointY = 0.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Body_chest.rotationPointY = -8.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.3F * (float)Math.PI)); 
    	this.Arm_r_Seg0.rotationPointY = -2.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.5F * (float)Math.PI)); 
    	this.Arm_l_Seg0.rotationPointY = -2.0F + (-0.55F * MathHelper.sin(0.06F * ageInTicks + 0.5F * (float)Math.PI)); 
    	this.SwingX_Sin(this.Arm_r_Seg1, -0.7740535232594852F, ageInTicks, -0.03F, 0.06F, true, 0.0F);
    	this.SwingX_Sin(this.Arm_l_Seg1, -0.7740535232594852F, ageInTicks, -0.03F, 0.06F, false, 0.0F);
    	
    	this.Body_base.rotationPointZ = 2.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.Body_base.rotationPointY = 10.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	this.SwingZ_Sin(this.Body_base, 0.0F, limbSwing, limbSwingAmount * 0.1F, 0.6F, false, 0.0F);
    	
    	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, true, 0.0F);
    	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, false, 0.0F);
    	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.6F, false, 0.3F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.6F, true, 0.3F * (float)Math.PI);
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float i = (float)((IAggressive) entityIn).getAttackTimer() / 5.0F;
    	boolean aggressive = ((IAggressive) entityIn).isAggressive();
        ItemStack itemstack = ((EntityLivingBase) entityIn).getHeldItemMainhand();
        ItemStack itemstack1 = ((EntityLivingBase) entityIn).getHeldItemOffhand();
        
    	if(i > 0) {
    		if(itemstack != null)
    			this.Arm_l_Seg0.rotateAngleX = GradientAnimation(-3.141592653589793F, -1.3203415791337103F, i);
    		if(itemstack1 != null)
    			this.Arm_r_Seg0.rotateAngleX = GradientAnimation(-3.141592653589793F, -1.3203415791337103F, i);
    	}
    	else if(aggressive) {
    		this.setRotateAngle(Arm_r_Seg0, -1.9577358219620393F, 0.0F, 0.091106186954104F);
    		this.setRotateAngle(Arm_l_Seg0, -1.8212510744560826F, 0.0F, 0.045553093477052F);
    	}
    	else {
    		this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
    	}
    	
    	/*if(entityIn instanceof EntityUnburied && ageInTicks < 20) {
    		float j = 19 - ageInTicks;
            //System.out.println("OXO " + j);
    		this.Body_base.rotationPointY = 10.0F + (1.5F * j);
    	}*/
    }
}
