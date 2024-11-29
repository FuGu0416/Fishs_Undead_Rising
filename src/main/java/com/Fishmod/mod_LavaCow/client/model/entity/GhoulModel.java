// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.GhoulEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GhoulModel<T extends GhoulEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
	private final ModelRenderer torso;
	private final ModelRenderer head;
	private final ModelRenderer jaw;
	private final ModelRenderer jaw_teeth;
	private final ModelRenderer head_teeth;
	private final ModelRenderer arm_r;
	private final ModelRenderer claw_r;
	private final ModelRenderer arm_l;
	private final ModelRenderer claw_l;
	private final ModelRenderer leg_r;
	private final ModelRenderer leg_l;

	public GhoulModel() {
		texWidth = 64;
		texHeight = 64;

		torso = new ModelRenderer(this);
		torso.setPos(0.0F, 9.5F, -3.0F);
		setRotationAngle(torso, 0.2391F, 0.0F, 0.0F);
		torso.texOffs(26, 27).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 10.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -2.2F, -0.7F);
		torso.addChild(head);
		setRotationAngle(head, -0.2391F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -3.0F, -8.0F, 8.0F, 6.0F, 8.0F, 0.0F, false);

		jaw = new ModelRenderer(this);
		jaw.setPos(-0.5F, 3.0F, -1.0F);
		head.addChild(jaw);
		setRotationAngle(jaw, 0.3965F, 0.0F, 0.0F);
		jaw.texOffs(0, 14).addBox(-4.0F, -0.1499F, -7.569F, 9.0F, 2.0F, 8.0F, 0.0F, false);

		jaw_teeth = new ModelRenderer(this);
		jaw_teeth.setPos(0.5F, -1.1499F, 1.981F);
		jaw.addChild(jaw_teeth);
		jaw_teeth.texOffs(0, 24).addBox(-4.5F, 0.0F, -9.5F, 9.0F, 1.0F, 6.0F, 0.0F, false);

		head_teeth = new ModelRenderer(this);
		head_teeth.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(head_teeth);
		head_teeth.texOffs(24, 0).addBox(-3.5F, 3.0F, -7.5F, 7.0F, 1.0F, 4.0F, 0.0F, false);

		arm_r = new ModelRenderer(this);
		arm_r.setPos(3.0F, 3.0F, 0.0F);
		torso.addChild(arm_r);
		setRotationAngle(arm_r, -0.9019F, -0.1059F, -0.262F);
		arm_r.texOffs(0, 31).addBox(0.0F, -1.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

		claw_r = new ModelRenderer(this);
		claw_r.setPos(2.974F, 9.0F, 0.5F);
		arm_r.addChild(claw_r);
		setRotationAngle(claw_r, 0.0F, 0.0F, 0.6545F);
		claw_r.texOffs(0, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, false);

		arm_l = new ModelRenderer(this);
		arm_l.setPos(-3.0F, 3.0F, 0.0F);
		torso.addChild(arm_l);
		setRotationAngle(arm_l, -0.9019F, 0.1059F, 0.262F);
		arm_l.texOffs(0, 31).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, true);

		claw_l = new ModelRenderer(this);
		claw_l.setPos(-3.0F, 9.0F, 0.5F);
		arm_l.addChild(claw_l);
		setRotationAngle(claw_l, 0.0F, 0.0F, -0.6545F);
		claw_l.texOffs(0, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, false);

		leg_r = new ModelRenderer(this);
		leg_r.setPos(1.9F, 7.0F, 1.0F);
		torso.addChild(leg_r);
		setRotationAngle(leg_r, -0.2391F, 0.0F, 0.0F);
		leg_r.texOffs(30, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		leg_l = new ModelRenderer(this);
		leg_l.setPos(-1.9F, 7.0F, 1.0F);
		torso.addChild(leg_l);
		setRotationAngle(leg_l, -0.2391F, 0.0F, 0.0F);
		leg_l.texOffs(30, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, true);
	}
	
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.torso).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {   
		float f = 0.67F;
    	float i = Math.max(0F, (float)entityIn.getAttackTimer() - 5F) / ((float)GhoulEntity.ATTACK_TIMER);
    	
    	this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
		this.torso.zRot = MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
		
		if (this.riding) {
    		setRotationAngle(leg_r, -1.4757F, -0.2048F, -0.0757F);
    		setRotationAngle(leg_l, -1.4757F, 0.2048F, 0.0757F); 		
    	} else {
        	this.leg_r.xRot = -0.2391F + MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        	this.leg_r.yRot = 0.0F;	
        	this.leg_r.zRot = -MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
	        this.leg_l.xRot = -0.2391F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount; 
	        this.leg_l.yRot = 0.0F;	
            this.leg_l.zRot = -MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
    	}
		
		if (entityIn.getAttackTimer() > 0) {
			this.head.xRot = GradientAnimation(0.0F, (float)Math.toRadians(27.5F), i);
			this.jaw.xRot = GradientAnimation(0.3965F + (float)Math.toRadians(32.5F), 0.3965F + (float)Math.toRadians(-25F), i);
        			       		
            this.arm_r.xRot = GradientAnimation(-0.9019F + (float)Math.toRadians(73.4081F), -0.9019F + (float)Math.toRadians(-9.2895F), i);
            this.arm_r.yRot = GradientAnimation(-0.1059F + (float)Math.toRadians(-76.6079F), -0.1059F + (float)Math.toRadians(44.8287F), i);
            this.arm_r.zRot = GradientAnimation(-0.262F + (float)Math.toRadians(-141.1723F), -0.262F + (float)Math.toRadians(-7.7945F), i);
            this.arm_l.xRot = GradientAnimation(-0.9019F + (float)Math.toRadians(73.4081F), -0.9019F + (float)Math.toRadians(-9.2895F), i);	
        	this.arm_l.yRot = GradientAnimation(0.1059F + (float)Math.toRadians(76.6079F), 0.1059F + (float)Math.toRadians(-44.8287F), i);
        	this.arm_l.zRot = GradientAnimation(0.262F + (float)Math.toRadians(141.1723F), 0.262F + (float)Math.toRadians(7.7945F), i);
        } else if (entityIn.isAggressive()) {
            this.torso.setPos(0.0F, 9.5F, -3.0F);
            this.jaw.xRot = 0.5711F;
            
	        this.setRotationAngle(arm_r, -1.6025F + (0.2F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount, -0.4112F, -0.2525F);
	        this.setRotationAngle(arm_l, -1.6025F - (0.2F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount, 0.4112F, 0.2525F);
        } else { 
            this.torso.setPos(0.0F, 9.5F, -3.0F);      
	        this.jaw.xRot = 0.08F + (0.08F * MathHelper.sin(0.03F * ageInTicks));
	        
	        if (this.riding) {	
	        	this.setRotationAngle(arm_r, -0.9019F, -0.1059F, -0.262F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
	        	this.setRotationAngle(arm_l, -0.9019F, 0.1059F, 0.262F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
	        } else {
	            this.arm_r.xRot = -0.2391F + (-0.2474F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	            this.arm_r.yRot = -0.1059F;
	            this.arm_r.zRot = -0.262F;
	            this.arm_l.xRot = -0.2391F + (-0.2474F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;	        	
	        	this.arm_l.yRot = 0.1059F;		        	
	        	this.arm_l.zRot = 0.262F;
	        }
        } 
	}

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}