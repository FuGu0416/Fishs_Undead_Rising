package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.EntityFoglet;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelFoglet extends ModelBase {
    public ModelRenderer arm_r;
    public ModelRenderer leg_r;
    public ModelRenderer head;
    public ModelRenderer torso;
    public ModelRenderer arm_l;
    public ModelRenderer leg_l;
    public ModelRenderer head_ear_r;
    public ModelRenderer head_ear_l;

    public ModelFoglet() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg_r = new ModelRenderer(this, 8, 0);
        this.leg_r.setRotationPoint(-1.9F, 7.0F, 0.0F);
        this.leg_r.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 16, 0);
        this.head.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(head, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_l = new ModelRenderer(this, 0, 0);
        this.arm_l.mirror = true;
        this.arm_l.setRotationPoint(3.0F, 3.0F, 0.0F);
        this.arm_l.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_l, -0.9023352232810683F, 0.10000736613927509F, -0.10000736613927509F);
        this.head_ear_r = new ModelRenderer(this, 16, 0);
        this.head_ear_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head_ear_r.addBox(-7.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.head_ear_l = new ModelRenderer(this, 16, 0);
        this.head_ear_l.mirror = true;
        this.head_ear_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head_ear_l.addBox(4.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.leg_l = new ModelRenderer(this, 8, 0);
        this.leg_l.mirror = true;
        this.leg_l.setRotationPoint(1.9F, 7.0F, 0.0F);
        this.leg_l.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_r = new ModelRenderer(this, 0, 0);
        this.arm_r.setRotationPoint(-3.0F, 3.0F, 0.0F);
        this.arm_r.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_r, -0.9023352232810683F, -0.10000736613927509F, 0.10000736613927509F);
        this.torso = new ModelRenderer(this, 0, 14);
        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
        this.torso.addBox(-3.0F, 0.0F, -2.0F, 6, 8, 4, 0.0F);
        this.setRotateAngle(torso, 0.5009094953223726F, 0.0F, 0.0F);
        this.torso.addChild(this.leg_r);
        this.torso.addChild(this.head);
        this.torso.addChild(this.arm_l);
        this.head.addChild(this.head_ear_r);
        this.head.addChild(this.head_ear_l);
        this.torso.addChild(this.leg_l);
        this.torso.addChild(this.arm_r);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {    	
    	this.torso.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
    	float f = 1.0F;

    	
    	
        if (flag)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }
        
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        if(((EntityFoglet) entityIn).getIsClimbing()) {           
            this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;
        	this.leg_r.rotateAngleX = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F) * 0.4F;
            this.leg_l.rotateAngleX = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F;            
            this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
            this.torso.rotateAngleX = 0.5009094953223726F;
            //this.torso.rotateAngleY = entityIn.rotationYaw * 0.017453292F;
            //this.setRotateAngle(torso, 0.5009094953223726F, 0.0F, 0.0F);
            //System.out.println("O_O catch" + this.torso.rotateAngleY);
        }
        else if(((EntityFoglet) entityIn).getIsHanging()) {
            this.head.rotateAngleX = -1.1383037381507017F + headPitch * 0.017453292F;
            this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
            this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);
            this.torso.setRotationPoint(-0.0F, 16.0F, 0.0F);
            this.torso.rotateAngleX = -2.86844862565268F;
            //this.setRotateAngle(torso, -2.86844862565268F, 0.0F, 0.0F);
        }
        else {
            this.head.rotateAngleX = -0.5009094953223726F + headPitch * 0.017453292F;
        	this.leg_r.rotateAngleX = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
	        this.leg_l.rotateAngleX = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;   
	        this.torso.setRotationPoint(-0.0F, 8.0F, -3.0F);
	        this.torso.rotateAngleX = 0.5009094953223726F;
	        //this.setRotateAngle(torso, 0.5009094953223726F, 0.0F, 0.0F);
        }
        
        //this.arm_r.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
        //this.arm_l.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityFoglet entityfoglet = (EntityFoglet)entitylivingbaseIn;
        boolean isAggressive = entityfoglet.isAggressive();
        
        //System.out.println("O_O catch" + entityfoglet.getIsClimbing());
        //System.out.println("OAO " + partialTickTime);
        //System.out.println("O_O " + MathHelper.sin(partialTickTime * 0.003F));
        if(entityfoglet.getIsHanging()) {
        	this.setRotateAngle(arm_l, 2.86844862565268F, 0.10000736613927509F, 0.22759093446006054F);
            this.setRotateAngle(arm_r, 2.86844862565268F, -0.10000736613927509F, -0.22759093446006054F);
        }
        else if(entityfoglet.getIsClimbing()) {
        	this.arm_r.rotateAngleX = -2.0488420089161434F + MathHelper.sin(entityfoglet.ticksExisted * 0.3F + 0.2F * (float)Math.PI) * 0.4F;
            this.arm_l.rotateAngleX = -2.0488420089161434F + MathHelper.sin(entityfoglet.ticksExisted * 0.3F) * 0.4F;           
        }
        else if(entityfoglet.isSpellcastingC()) {
        	//System.out.println("O_O catch");
        	this.arm_r.rotateAngleX = 2.6862362517444724F;
        	this.arm_l.rotateAngleX = 2.6862362517444724F;
        	this.arm_r.rotateAngleZ = -0.9560913642424937F + MathHelper.sin(entityfoglet.ticksExisted * 0.6F) * 0.8196F;
            this.arm_l.rotateAngleZ = 0.9560913642424937F - MathHelper.sin(entityfoglet.ticksExisted * 0.6F) * 0.8196F;
        }
        else if(isAggressive/*entityfoglet.setFog_counter > 0*/)
        {
        	//System.out.println("O_O catch" + entityfoglet.setFog_counter);
        	this.arm_r.rotateAngleX = -2.0032889154390916F;
        	this.arm_l.rotateAngleX = -2.0032889154390916F;
        	this.arm_r.rotateAngleZ = 0.10000736613927509F;
        	this.arm_l.rotateAngleZ = -0.10000736613927509F;
        }
        else
        {
            this.arm_r.rotateAngleX = -0.9023352232810683F + (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            this.arm_l.rotateAngleX = -0.9023352232810683F + (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
        	this.arm_r.rotateAngleZ = 0.10000736613927509F;
        	this.arm_l.rotateAngleZ = -0.10000736613927509F;
        }
        
    }
    
    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}
