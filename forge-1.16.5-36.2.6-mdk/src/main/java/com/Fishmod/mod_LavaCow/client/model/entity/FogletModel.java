package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.FogletEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class FogletModel<T extends FogletEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer torso;
    public ModelRenderer head;
    public ModelRenderer arm_r;
    public ModelRenderer arm_l;
    public ModelRenderer leg_r;
    public ModelRenderer leg_l;
    public ModelRenderer tail0;
    public ModelRenderer head_ear_r;
    public ModelRenderer head_ear_l;
    public ModelRenderer horn0_l;
    public ModelRenderer horn0_r;
    public ModelRenderer horn1_l;
    public ModelRenderer horn2_l;
    public ModelRenderer horn1_r;
    public ModelRenderer horn2_r;
    public ModelRenderer tail1;
    public ModelRenderer tail2;
    
    public FogletModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.leg_r = new ModelRenderer(this, 8, 0);
        this.leg_r.setPos(-1.9F, 7.0F, 0.0F);
        this.leg_r.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 16, 0);
        this.head.setPos(0.0F, 1.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(head, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_l = new ModelRenderer(this, 0, 0);
        this.arm_l.mirror = true;
        this.arm_l.setPos(3.0F, 3.0F, 0.0F);
        this.arm_l.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_l, -0.9023352232810683F, 0.10000736613927509F, -0.10000736613927509F);
        this.head_ear_r = new ModelRenderer(this, 16, 0);
        this.head_ear_r.setPos(0.0F, 0.0F, 0.0F);
        this.head_ear_r.addBox(-7.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.head_ear_l = new ModelRenderer(this, 16, 0);
        this.head_ear_l.mirror = true;
        this.head_ear_l.setPos(0.0F, 0.0F, 0.0F);
        this.head_ear_l.addBox(4.0F, -6.0F, 0.0F, 3, 4, 1, 0.0F);
        this.leg_l = new ModelRenderer(this, 8, 0);
        this.leg_l.mirror = true;
        this.leg_l.setPos(1.9F, 7.0F, 0.0F);
        this.leg_l.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
        this.arm_r = new ModelRenderer(this, 0, 0);
        this.arm_r.setPos(-3.0F, 3.0F, 0.0F);
        this.arm_r.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_r, -0.9023352232810683F, -0.10000736613927509F, 0.10000736613927509F);
        this.torso = new ModelRenderer(this, 0, 14);
        this.torso.setPos(-0.0F, 8.0F, -3.0F);
        this.torso.addBox(-3.0F, 0.0F, -2.0F, 6, 8, 4, 0.0F);
        this.setRotateAngle(torso, 0.5009094953223726F, 0.0F, 0.0F);
        this.horn1_r = new ModelRenderer(this, 46, 0);
        this.horn1_r.setPos(0.0F, -3.0F, -1.0F);
        this.horn1_r.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn1_r, -1.2901473511162753F, 0.0F, 0.0F);
        this.horn0_l = new ModelRenderer(this, 56, 0);
        this.horn0_l.mirror = true;
        this.horn0_l.setPos(2.0F, -7.0F, -3.0F);
        this.horn0_l.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn0_l, 0.8210028961170991F, 0.0F, 0.0F);
        this.tail1 = new ModelRenderer(this, 55, 7);
        this.tail1.setPos(0.0F, -6.0F, 0.5F);
        this.tail1.addBox(-0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail1, 0.8993681422473893F, 0.0F, 0.0F);
        this.horn2_l = new ModelRenderer(this, 41, 0);
        this.horn2_l.mirror = true;
        this.horn2_l.setPos(0.0F, -3.0F, 0.0F);
        this.horn2_l.addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn2_l, -0.7037167490777915F, 0.0F, 0.0F);
        this.horn0_r = new ModelRenderer(this, 56, 0);
        this.horn0_r.setPos(-2.0F, -7.0F, -3.0F);
        this.horn0_r.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn0_r, 0.8210028961170991F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 50, 8);
        this.tail2.setPos(0.0F, -5.0F, 0.0F);
        this.tail2.addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail2, 0.9389871242571438F, 0.0F, 0.0F);
        this.tail0 = new ModelRenderer(this, 60, 6);
        this.tail0.setPos(0.0F, 7.9F, 0.9F);
        this.tail0.addBox(-0.5F, -6.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail0, -2.88380744850172F, 0.0F, 0.0F);
        this.horn2_r = new ModelRenderer(this, 41, 0);
        this.horn2_r.setPos(0.0F, -3.0F, 0.0F);
        this.horn2_r.addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn2_r, -0.7037167490777915F, 0.0F, 0.0F);
        this.horn1_l = new ModelRenderer(this, 46, 0);
        this.horn1_l.mirror = true;
        this.horn1_l.setPos(0.0F, -3.0F, -1.0F);
        this.horn1_l.addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horn1_l, -1.2901473511162753F, 0.0F, 0.0F);
        
        this.torso.addChild(this.leg_r);
        this.horn0_r.addChild(this.horn1_r);
        this.torso.addChild(this.arm_r);
        this.head.addChild(this.horn0_l);
        this.tail0.addChild(this.tail1);
        this.torso.addChild(this.arm_l);
        this.head.addChild(this.head_ear_r);
        this.horn1_l.addChild(this.horn2_l);
        this.head.addChild(this.head_ear_l);
        this.head.addChild(this.horn0_r);
        this.torso.addChild(this.leg_l);
        this.tail1.addChild(this.tail2);
        this.torso.addChild(this.tail0);
        this.horn1_r.addChild(this.horn2_r);
        this.horn0_l.addChild(this.horn1_l);
        this.torso.addChild(this.head);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.torso).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {        
    	FogletEntity entityfoglet = (FogletEntity)entityIn;  	
    	this.head.yRot = netHeadYaw * 0.017453292F;
    	this.SwingX_Sin(this.tail0, -2.88380744850172F, ageInTicks, 0.2F, 0.1F, false, 0.0F);
    	this.SwingX_Sin(this.tail1, 0.8993681422473893F, ageInTicks, 0.2F, 0.1F, false, 0.1F);
    	this.SwingX_Sin(this.tail2, 0.9389871242571438F, ageInTicks, 0.2F, 0.1F, false, 0.2F);
    	
    	if (this.riding) {
    		this.leg_r.xRot = -1.8301522642938983F;
    		this.leg_r.yRot = 0.27366763203903305F;		
    		this.leg_l.xRot = -1.8301522642938983F;
    		this.leg_l.yRot = -0.27366763203903305F;    		
    	} else if (entityfoglet.getIsClimbing()) {
        	this.leg_r.xRot = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F) * 0.4F;
        	this.leg_r.yRot = 0.0F;		
            this.leg_l.xRot = -2.0032889154390916F + MathHelper.cos(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F; 
            this.leg_l.yRot = 0.0F;	
    	} else if (entityfoglet.getIsHanging()) {
            this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
            this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);   		 
    	} else {
        	this.leg_r.xRot = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        	this.leg_r.yRot = 0.0F;	
	        this.leg_l.xRot = -0.5009094953223726F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount; 
	        this.leg_l.yRot = 0.0F;	
    	}
    	
    	if (entityfoglet.getIsClimbing()) {           
            this.head.xRot = -0.5009094953223726F + headPitch * 0.017453292F;        
            this.torso.setPos(-0.0F, 8.0F, -3.0F);
            this.torso.xRot = 0.5009094953223726F;          
        	this.arm_r.xRot = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F;
            this.arm_l.xRot = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F) * 0.4F; 
        } else if (entityfoglet.getIsHanging()) {
            this.head.xRot = -1.1383037381507017F + headPitch * 0.017453292F;
            this.torso.setPos(-0.0F, 16.0F, 0.0F);
            this.torso.xRot = -2.86844862565268F;        
        	this.setRotateAngle(arm_l, 2.86844862565268F, 0.10000736613927509F, 0.22759093446006054F);
            this.setRotateAngle(arm_r, 2.86844862565268F, -0.10000736613927509F, -0.22759093446006054F);
        } else if (entityfoglet.isSpellcastingC()) {
        	this.head.xRot = -0.5009094953223726F + headPitch * 0.017453292F;
	        this.torso.setPos(-0.0F, 8.0F, -3.0F);
	        this.torso.xRot = 0.5009094953223726F;	
        	this.arm_r.xRot = 2.6862362517444724F;
        	this.arm_l.xRot = 2.6862362517444724F;
        	this.arm_r.zRot = -0.9560913642424937F + MathHelper.sin(ageInTicks * 0.6F) * 0.8196F;
            this.arm_l.zRot = 0.9560913642424937F - MathHelper.sin(ageInTicks * 0.6F) * 0.8196F; 
        } else if (entityfoglet.isAggressive()) {
        	this.head.xRot = -0.5009094953223726F + headPitch * 0.017453292F;
	        this.torso.setPos(-0.0F, 8.0F, -3.0F);
	        this.torso.xRot = 0.5009094953223726F;	
        	this.arm_r.xRot = -2.0032889154390916F;
        	this.arm_l.xRot = -2.0032889154390916F;
        	this.arm_r.zRot = 0.10000736613927509F;
        	this.arm_l.zRot = -0.10000736613927509F; 
        } else {
            this.head.xRot = -0.5009094953223726F + headPitch * 0.017453292F;  
	        this.torso.setPos(-0.0F, 8.0F, -3.0F);
	        this.torso.xRot = 0.5009094953223726F;	        
	        
	        if (this.riding) {	
	    		this.arm_r.xRot = -0.9023352365968739F;
	    		this.arm_r.yRot = 0.33004175888896664F;
	    		this.arm_r.zRot = 0.10000736613927509F;
	    		this.arm_l.xRot = -0.9023352365968739F;
	    		this.arm_l.yRot = -0.33004175888896664F;	    	        	
	        	this.arm_l.zRot = -0.10000736613927509F;
	        } else {
	            this.arm_r.xRot = -0.9023352232810683F + (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	            this.arm_r.yRot = 0.10000736613927509F;
	            this.arm_r.zRot = 0.10000736613927509F;
	            this.arm_l.xRot = -0.9023352232810683F + (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;	        	
	        	this.arm_l.yRot = -0.10000736613927509F;		        	
	        	this.arm_l.zRot = -0.10000736613927509F;
	        }
        }                
    }
    
    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }

	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.head;
	}
}
