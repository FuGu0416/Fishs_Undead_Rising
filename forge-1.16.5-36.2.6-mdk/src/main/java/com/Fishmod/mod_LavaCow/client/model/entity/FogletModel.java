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
	private final ModelRenderer torso;
	private final ModelRenderer head;
	private final ModelRenderer head_ear_r;
	private final ModelRenderer head_ear_l;
	private final ModelRenderer horn0_l;
	private final ModelRenderer horn1_l;
	private final ModelRenderer horn2_l;
	private final ModelRenderer horn0_r;
	private final ModelRenderer horn1_r;
	private final ModelRenderer horn2_r;
	private final ModelRenderer arm_r;
	private final ModelRenderer arm_l;
	private final ModelRenderer leg_r;
	private final ModelRenderer leg_l;
	private final ModelRenderer tail0;
	private final ModelRenderer tail1;
	private final ModelRenderer tail2;
    
    public FogletModel() {
    	this.texWidth = 64;
		this.texHeight = 32;

		this.torso = new ModelRenderer(this);
		this.torso.setPos(0.0F, 11.0F, -3.0F);
		this.torso.texOffs(0, 14).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 8.0F, 4.0F, 0.0F, false);

		this.head = new ModelRenderer(this);
		this.head.setPos(0.0F, 1.0F, 0.0F);
		this.torso.addChild(this.head);
		this.head.texOffs(16, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.head_ear_r = new ModelRenderer(this);
		this.head_ear_r.setPos(0.0F, 0.0F, 0.0F);
		this.head.addChild(this.head_ear_r);
		this.head_ear_r.texOffs(16, 0).addBox(4.0F, -6.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, true);

		this.head_ear_l = new ModelRenderer(this);
		this.head_ear_l.setPos(0.0F, 0.0F, 0.0F);
		this.head.addChild(this.head_ear_l);
		this.head_ear_l.texOffs(16, 0).addBox(-7.0F, -6.0F, 0.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

		this.horn0_l = new ModelRenderer(this);
		this.horn0_l.setPos(-2.0F, -7.0F, -3.0F);
		this.head.addChild(this.horn0_l);
		this.setRotationAngle(this.horn0_l, 0.821F, 0.0F, 0.0F);
		this.horn0_l.texOffs(56, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		this.horn1_l = new ModelRenderer(this);
		this.horn1_l.setPos(0.0F, -3.0F, -1.0F);
		this.horn0_l.addChild(this.horn1_l);
		this.setRotationAngle(this.horn1_l, -1.2901F, 0.0F, 0.0F);
		this.horn1_l.texOffs(46, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		this.horn2_l = new ModelRenderer(this);
		this.horn2_l.setPos(0.0F, -3.0F, 0.0F);
		this.horn1_l.addChild(this.horn2_l);
		this.setRotationAngle(this.horn2_l, -0.7037F, 0.0F, 0.0F);
		this.horn2_l.texOffs(41, 0).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);

		this.horn0_r = new ModelRenderer(this);
		this.horn0_r.setPos(2.0F, -7.0F, -3.0F);
		this.head.addChild(this.horn0_r);
		this.setRotationAngle(this.horn0_r, 0.821F, 0.0F, 0.0F);
		this.horn0_r.texOffs(56, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		this.horn1_r = new ModelRenderer(this);
		this.horn1_r.setPos(0.0F, -3.0F, -1.0F);
		this.horn0_r.addChild(this.horn1_r);
		this.setRotationAngle(this.horn1_r, -1.2901F, 0.0F, 0.0F);
		this.horn1_r.texOffs(46, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		this.horn2_r = new ModelRenderer(this);
		this.horn2_r.setPos(0.0F, -3.0F, 0.0F);
		this.horn1_r.addChild(this.horn2_r);
		this.setRotationAngle(this.horn2_r, -0.7037F, 0.0F, 0.0F);
		this.horn2_r.texOffs(41, 0).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		this.arm_r = new ModelRenderer(this);
		this.arm_r.setPos(3.0F, 3.0F, 0.0F);
		this.torso.addChild(this.arm_r);
		this.setRotationAngle(this.arm_r, 0.0F, 0.1F, -0.1F);
		this.arm_r.texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		this.arm_l = new ModelRenderer(this);
		this.arm_l.setPos(-3.0F, 3.0F, 0.0F);
		this.torso.addChild(this.arm_l);
		this.setRotationAngle(this.arm_l, 0.0F, -0.1F, 0.1F);
		this.arm_l.texOffs(0, 0).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, true);

		this.leg_r = new ModelRenderer(this);
		this.leg_r.setPos(1.9F, 7.0F, 0.0F);
		torso.addChild(this.leg_r);
		this.leg_r.texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		this.leg_l = new ModelRenderer(this);
		this.leg_l.setPos(-1.9F, 7.0F, 0.0F);
		this.torso.addChild(this.leg_l);
		this.leg_l.texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, true);

		this.tail0 = new ModelRenderer(this);
		this.tail0.setPos(0.0F, 7.9F, 0.9F);
		this.torso.addChild(this.tail0);
		this.setRotationAngle(this.tail0, -2.1857F, 0.0F, 0.0F);
		this.tail0.texOffs(60, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		this.tail1 = new ModelRenderer(this);
		this.tail1.setPos(0.0F, -6.0F, 0.5F);
		this.tail0.addChild(this.tail1);
		this.setRotationAngle(this.tail1, 0.8994F, 0.0F, 0.0F);
		this.tail1.texOffs(55, 7).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		this.tail2 = new ModelRenderer(this);
		this.tail2.setPos(0.0F, -5.0F, 0.0F);
		this.tail1.addChild(this.tail2);
		this.setRotationAngle(this.tail2, 0.939F, 0.0F, 0.0F);
		this.tail2.texOffs(50, 8).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.torso).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {     
    	float f = 0.67F; 	
    	float i = entityIn.getAttackTimer() / 35.0F;
    	
    	this.head.yRot = netHeadYaw * 0.017453292F;
    	this.SwingX_Sin(this.tail0, -2.1857F, ageInTicks, 0.2F, 0.1F, false, 0.0F);
    	this.SwingX_Sin(this.tail1, 0.8994F, ageInTicks, 0.2F, 0.1F, false, 0.1F);
    	this.SwingX_Sin(this.tail2, 0.939F, ageInTicks, 0.2F, 0.1F, false, 0.2F);

    	this.torso.zRot = MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
    	
    	if (this.riding) {
    		setRotationAngle(leg_r, -1.4757F, -0.2048F, -0.0757F);
    		setRotationAngle(leg_l, -1.4757F, 0.2048F, 0.0757F); 		
    	} else if (entityIn.getIsClimbing()) {
        	this.leg_r.xRot = MathHelper.cos(ageInTicks * 0.3F) * 0.4F;
        	this.leg_r.yRot = 0.0F;		
        	this.leg_r.zRot = 0.0F;		
            this.leg_l.xRot = MathHelper.cos(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F; 
            this.leg_l.yRot = 0.0F;	
            this.leg_l.zRot = 0.0F;	
    	} else if (entityIn.getIsHanging()) {
            this.setRotateAngle(leg_l, -0.5009094953223726F, 0.0F, 0.0F);
            this.setRotateAngle(leg_r, -0.5009094953223726F, 0.0F, 0.0F);   		 
    	} else if (i > 0) {
        	this.leg_r.xRot = GradientAnimation(0.0F, 0.46914448828868976F, i);
        	this.leg_r.yRot = 0.0F;	
        	this.leg_r.zRot = 0.0F;	
	        this.leg_l.xRot = -0.899542712456844F;
	        this.leg_l.yRot = 0.0F;	  
	        this.leg_l.yRot = 0.0F;	
    	} else {
        	this.leg_r.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        	this.leg_r.yRot = 0.0F;	
        	this.leg_r.zRot = MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
	        this.leg_l.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount; 
	        this.leg_l.yRot = 0.0F;	
            this.leg_l.zRot = MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
    	}
    	
    	if (entityIn.getIsClimbing()) {           
            this.head.xRot = headPitch * 0.017453292F;        
            this.torso.setPos(0.0F, 11.0F, -3.0F);
	        this.torso.xRot = 0.0F;         
        	this.arm_r.xRot = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F + 0.2F * (float)Math.PI) * 0.4F;
            this.arm_l.xRot = -2.0488420089161434F + MathHelper.sin(ageInTicks * 0.3F) * 0.4F; 
        } else if (entityIn.getIsHanging()) {
            this.head.xRot = -1.1383037381507017F + headPitch * 0.017453292F;
            this.torso.setPos(-0.0F, 16.0F, 0.0F);
            this.torso.xRot = -2.86844862565268F;        
        	this.setRotationAngle(arm_r, -0.0977F, -0.0216F, -1.8879F);
            this.setRotationAngle(arm_l, -0.0977F, 0.0216F, 1.8879F);
        } else if (entityIn.isSpellcastingC()) {
        	this.head.xRot = headPitch * 0.017453292F;
            this.torso.setPos(0.0F, 11.0F, -3.0F);
	        this.torso.xRot = 0.0F;
        	this.arm_r.xRot = 0.0F;
        	this.arm_l.xRot = 0.0F;
        	this.arm_r.zRot = -0.9560F + MathHelper.sin(ageInTicks * 0.6F) * 0.8196F;
            this.arm_l.zRot = 0.9560F - MathHelper.sin(ageInTicks * 0.6F) * 0.8196F; 
        } else if (i > 0) {
        	this.head.xRot = headPitch * 0.017453292F;
	        this.torso.setPos(-0.0F, GradientAnimation(8.0F, 10.0F, i), -3.0F);
	        this.torso.xRot = GradientAnimation(0.07086036663228437F, 0.8908160661968724F, i);	
        	this.arm_r.xRot = GradientAnimation(-0.5195845263081952F, 0.5359905973084921F, i);	
        	
        	if (i > 0.5F) {
        		this.arm_l.xRot = GradientAnimation(1.4978415587352114F, 3.141592653589793F, i);	       		
        	} else {
        		this.arm_l.xRot = GradientAnimation(-3.141592653589793F, -1.9046777647645121F, i);	      		
        	}
        	        	
        	this.arm_r.zRot = 0.10000736613927509F;
        	this.arm_l.zRot = -0.10000736613927509F; 
        } else if (entityIn.isAggressive()) {
        	this.head.xRot = headPitch * 0.017453292F;
            this.torso.setPos(0.0F, 11.0F, -3.0F);
	        this.torso.xRot = 0.0F;
	        this.setRotationAngle(arm_r, -1.7014F, -0.073F, -0.0773F);
	        this.setRotationAngle(arm_l, -1.7014F, 0.073F, 0.0773F);
        } else {
            this.head.xRot = headPitch * 0.017453292F;  
            this.torso.setPos(0.0F, 11.0F, -3.0F);
	        this.torso.xRot = 0.0F;	        
	        
	        if (this.riding) {	
	        	this.setRotationAngle(arm_r, -0.0174F, 0.0985F, -0.2754F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
	        	this.setRotationAngle(arm_l, -0.0174F, -0.0985F, 0.2754F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
	        } else {
	            this.arm_r.xRot = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	            this.arm_r.yRot = 0.1F;
	            this.arm_r.zRot = -0.1F;
	            this.arm_l.xRot = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;	        	
	        	this.arm_l.yRot = -0.1F;		        	
	        	this.arm_l.zRot = 0.1F;
	        }
        }                
    }
    
	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.head;
	}
}
