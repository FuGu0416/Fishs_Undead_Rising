package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Made with Blockbench 4.6.4
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
//Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class EnigmothModel<T extends EnigmothEntity> extends FlyingBaseModel<T> implements IHasHead {
	private final ModelRenderer base;
	private final ModelRenderer head;
	private final ModelRenderer eye_l;
	private final ModelRenderer eye_r;
	private final ModelRenderer antennae_l;
	private final ModelRenderer antennae_r;
	private final ModelRenderer wing0_l;
	private final ModelRenderer wing0_r;
	private final ModelRenderer wing1_l;
	private final ModelRenderer wing1_r;
	private final ModelRenderer leg0_l;
	private final ModelRenderer leg1_l;
	private final ModelRenderer leg2_l;
	private final ModelRenderer leg0_r;
	private final ModelRenderer leg1_r;
	private final ModelRenderer leg2_r;
	private final ModelRenderer abdomen;
	private final ModelRenderer saddle;
	private final ModelRenderer saddle_top;

	public EnigmothModel() {
		texWidth = 128;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 12.0F, 3.5F);
		base.texOffs(40, 34).addBox(-6.0F, -6.0F, -4.0F, 12.0F, 12.0F, 8.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 1.0F, -1.0F);
		base.addChild(head);
		head.texOffs(70, 46).addBox(-4.0F, -4.0F, -12.0F, 8.0F, 8.0F, 10.0F, 0.0F, false);

		eye_l = new ModelRenderer(this);
		eye_l.setPos(4.0F, -1.0F, -10.0F);
		head.addChild(eye_l);
		eye_l.texOffs(0, 4).addBox(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, true);

		eye_r = new ModelRenderer(this);
		eye_r.setPos(-4.0F, -1.0F, -10.0F);
		head.addChild(eye_r);
		eye_r.texOffs(0, 4).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);

		antennae_l = new ModelRenderer(this);
		antennae_l.setPos(2.5F, -4.0F, -12.0F);
		head.addChild(antennae_l);
		setRotateAngle(antennae_l, -0.5236F, 0.0F, 0.0F);
		antennae_l.texOffs(0, 0).addBox(-1.0F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, 0.0F, true);

		antennae_r = new ModelRenderer(this);
		antennae_r.setPos(-2.5F, -4.0F, -12.0F);
		head.addChild(antennae_r);
		setRotateAngle(antennae_r, -0.5236F, 0.0F, 0.0F);
		antennae_r.texOffs(0, 0).addBox(-4.0F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, 0.0F, false);

		wing0_l = new ModelRenderer(this);
		wing0_l.setPos(4.0F, -4.0F, -6.0F);
		base.addChild(wing0_l);
		setRotateAngle(wing0_l, 0.0F, -0.2087F, 0.0F);
		wing0_l.texOffs(0, 0).addBox(0.0F, 0.0F, -13.0F, 28.0F, 0.0F, 20.0F, 0.0F, true);

		wing0_r = new ModelRenderer(this);
		wing0_r.setPos(-4.0F, -4.0F, -6.0F);
		base.addChild(wing0_r);
		setRotateAngle(wing0_r, 0.0F, 0.2087F, 0.0F);
		wing0_r.texOffs(0, 0).addBox(-28.0F, 0.0F, -13.0F, 28.0F, 0.0F, 20.0F, 0.0F, false);

		wing1_l = new ModelRenderer(this);
		wing1_l.setPos(6.0F, -4.0F, 2.0F);
		base.addChild(wing1_l);
		setRotateAngle(wing1_l, 0.0F, -0.4098F, 0.0F);
		wing1_l.texOffs(0, 20).addBox(0.0F, 0.0F, -1.0F, 24.0F, 0.0F, 14.0F, 0.0F, true);

		wing1_r = new ModelRenderer(this);
		wing1_r.setPos(-6.0F, -4.0F, 2.0F);
		base.addChild(wing1_r);
		setRotateAngle(wing1_r, 0.0F, 0.4098F, 0.0F);
		wing1_r.texOffs(0, 20).addBox(-24.0F, 0.0F, -1.0F, 24.0F, 0.0F, 14.0F, 0.0F, false);

		leg0_l = new ModelRenderer(this);
		leg0_l.setPos(4.0F, 6.0F, -1.5F);
		base.addChild(leg0_l);
		setRotateAngle(leg0_l, 0.0F, 0.7854F, 0.7854F);
		leg0_l.texOffs(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		leg1_l = new ModelRenderer(this);
		leg1_l.setPos(4.0F, 6.0F, -0.5F);
		base.addChild(leg1_l);
		setRotateAngle(leg1_l, 0.0F, 0.3927F, 0.5812F);
		leg1_l.texOffs(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		leg2_l = new ModelRenderer(this);
		leg2_l.setPos(4.0F, 6.0F, 0.5F);
		base.addChild(leg2_l);
		setRotateAngle(leg2_l, 0.0F, -0.3927F, 0.5812F);
		leg2_l.texOffs(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		leg0_r = new ModelRenderer(this);
		leg0_r.setPos(-4.0F, 6.0F, -1.5F);
		base.addChild(leg0_r);
		setRotateAngle(leg0_r, 0.0F, -0.7854F, -0.7854F);
		leg0_r.texOffs(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		leg1_r = new ModelRenderer(this);
		leg1_r.setPos(-4.0F, 6.0F, -0.5F);
		base.addChild(leg1_r);
		setRotateAngle(leg1_r, 0.0F, -0.3927F, -0.5812F);
		leg1_r.texOffs(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		leg2_r = new ModelRenderer(this);
		leg2_r.setPos(-4.0F, 6.0F, 0.5F);
		base.addChild(leg2_r);
		setRotateAngle(leg2_r, 0.0F, 0.3927F, -0.5812F);
		leg2_r.texOffs(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		abdomen = new ModelRenderer(this);
		abdomen.setPos(0.0F, 0.0F, 1.0F);
		base.addChild(abdomen);
		setRotateAngle(abdomen, -0.3491F, 0.0F, 0.0F);
		abdomen.texOffs(0, 34).addBox(-5.0F, -5.0F, -2.0F, 10.0F, 8.0F, 20.0F, 0.0F, false);
		
		saddle = new ModelRenderer(this);
		saddle.setPos(-0.5F, 12.0F, 1.5F);
		base.addChild(saddle);
		saddle.texOffs(76, 4).addBox(-5.5F, -19.0F, -5.0F, 12.0F, 1.0F, 7.0F, 0.0F, false);

		saddle_top = new ModelRenderer(this);
		saddle_top.setPos(0.0F, -18.5F, -1.5F);
		saddle.addChild(saddle_top);
		saddle_top.texOffs(76, 12).addBox(-5.5F, -2.5F, 2.5F, 12.0F, 2.0F, 1.0F, 0.0F, false);
	}


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {  	 	  	
    	float vibrate_rate = 0.5F;
    	//float i = (float)entityIn.getSpellTicks() / 30.0F;
    	
    	super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);    	
    	this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	
    	this.wing0_r.xRot = 0.0F;
    	this.wing0_r.yRot = 0.2087F;
    	this.wing0_r.zRot = 0.5F * MathHelper.sin(0.5F * ageInTicks);
    	this.wing1_r.xRot = 0.0F;
    	this.wing1_r.yRot = 0.4098F;
    	this.wing1_r.zRot = 0.5F * MathHelper.sin(0.5F * ageInTicks + 0.15F * (float)Math.PI);
    	this.wing0_l.xRot = 0.0F;
    	this.wing0_l.yRot = -0.2087F;
    	this.wing0_l.zRot = -0.5F * MathHelper.sin(0.5F * ageInTicks);
    	this.wing1_l.xRot = 0.0F;
    	this.wing1_l.yRot = -0.4098F;
    	this.wing1_l.zRot = -0.5F * MathHelper.sin(0.5F * ageInTicks + 0.15F * (float)Math.PI);
     
    	this.base.y = 7.0F + (entityIn.isVehicle() ? 5.0F : 5.0F * MathHelper.sin(ageInTicks * vibrate_rate));  	    	
    	
    	this.setRotateAngle(this.leg0_r, 0.0F, 0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, -1.6845917940249266F);
    	this.setRotateAngle(this.leg1_r, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
    	this.setRotateAngle(this.leg2_r, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
    	this.setRotateAngle(this.leg0_l, 0.0F, -0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, 1.6845917940249266F);
    	this.setRotateAngle(this.leg1_l, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
    	this.setRotateAngle(this.leg2_l, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);       
    	
    	if (this.state.equals(FlyingBaseModel.State.WAITING)) {
    		vibrate_rate = 0.05F;
    		
    		this.base.y = 12.0F;
 
	    	this.abdomen.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	
	    	this.setRotateAngle(wing0_r, 0.0F, 0.2087F, 0.0F);
    		this.setRotateAngle(wing0_l, 0.0F, -0.2087F, 0.0F);
    		this.setRotateAngle(wing1_r, 0.0F, 0.4098F, 0.0F);
    		this.setRotateAngle(wing1_l, 0.0F, -0.4098F, 0.0F);
    		
	    	this.setRotateAngle(this.leg0_r, 0.0F, -0.7853981633974483F, -0.7853981633974483F);
	    	this.setRotateAngle(this.leg1_r, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg2_r, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg0_l, 0.0F, 0.7853981633974483F, 0.7853981633974483F);
	    	this.setRotateAngle(this.leg1_l, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
	    	this.setRotateAngle(this.leg2_l, 0.0F, -0.39269908169872414F, 0.5811946409141118F);
			
	        this.leg0_r.zRot = -0.78539816F;
	        this.leg0_l.zRot = 0.78539816F;
	        this.leg1_r.zRot = -0.58119464F;
	        this.leg1_l.zRot = 0.58119464F;
	        this.leg2_r.zRot = -0.58119464F;
	        this.leg2_l.zRot = 0.58119464F;
	        this.leg0_r.yRot = -0.78539816F;
	        this.leg0_l.yRot = 0.78539816F;
	        this.leg1_r.yRot = -0.39269908F;
	        this.leg1_l.yRot = 0.39269908F;
	        this.leg2_r.yRot = 0.39269908F;
	        this.leg2_l.yRot = -0.39269908F;
	        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
	        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
	        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        this.leg0_r.yRot += f3;
	        this.leg0_l.yRot += -f3;
	        this.leg1_r.yRot += f4;
	        this.leg1_l.yRot += -f4;
	        this.leg2_r.yRot += f5;
	        this.leg2_l.yRot += -f5;
	        this.leg0_r.zRot += f7;
	        this.leg0_l.zRot += -f7;
	        this.leg1_r.zRot += f8;
	        this.leg1_l.zRot += -f8;
	        this.leg2_r.zRot += f9;
	        this.leg2_l.zRot += -f9;			
    	} else if (this.state.equals(FlyingBaseModel.State.FLYING) || this.state.equals(FlyingBaseModel.State.ATTACKING)) {
	    	this.abdomen.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI; 
    	} else if (this.state.equals(FlyingBaseModel.State.HOVERING)) {
	    	this.abdomen.xRot = -0.014F * (20 - entityIn.getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;   
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}