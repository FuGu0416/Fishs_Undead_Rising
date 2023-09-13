package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEnigmoth extends ModelFlyingBase {
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
	
	public ModelEnigmoth() {
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 12.0F, 7.5F);
		base.setTextureOffset(40, 34).addBox(-6.0F, -6.0F, -4.0F, 12, 12, 8, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 1.0F, -1.0F);
		base.addChild(head);
		head.setTextureOffset(70, 46).addBox(-4.0F, -4.0F, -12.0F, 8, 8, 10, false);

		eye_l = new ModelRenderer(this);
		eye_l.setRotationPoint(4.0F, -1.0F, -10.0F);
		head.addChild(eye_l);
		eye_l.setTextureOffset(0, 4).addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, true);

		eye_r = new ModelRenderer(this);
		eye_r.setRotationPoint(-4.0F, -1.0F, -10.0F);
		head.addChild(eye_r);
		eye_r.setTextureOffset(0, 4).addBox(-2.0F, -2.0F, -2.0F, 2, 4, 4, false);

		antennae_l = new ModelRenderer(this);
		antennae_l.setRotationPoint(2.5F, -4.0F, -12.0F);
		head.addChild(antennae_l);
		setRotateAngle(antennae_l, -0.5236F, 0.0F, 0.0F);
		antennae_l.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -8.0F, 5, 0, 8, true);

		antennae_r = new ModelRenderer(this);
		antennae_r.setRotationPoint(-2.5F, -4.0F, -12.0F);
		head.addChild(antennae_r);
		setRotateAngle(antennae_r, -0.5236F, 0.0F, 0.0F);
		antennae_r.setTextureOffset(0, 0).addBox(-4.0F, 0.0F, -8.0F, 5, 0, 8, false);

		wing0_l = new ModelRenderer(this);
		wing0_l.setRotationPoint(4.0F, -4.0F, -6.0F);
		base.addChild(wing0_l);
		setRotateAngle(wing0_l, 0.0F, -0.2087F, 0.0F);
		wing0_l.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -13.0F, 28, 0, 20, true);

		wing0_r = new ModelRenderer(this);
		wing0_r.setRotationPoint(-4.0F, -4.0F, -6.0F);
		base.addChild(wing0_r);
		setRotateAngle(wing0_r, 0.0F, 0.2087F, 0.0F);
		wing0_r.setTextureOffset(0, 0).addBox(-28.0F, 0.0F, -13.0F, 28, 0, 20, false);

		wing1_l = new ModelRenderer(this);
		wing1_l.setRotationPoint(6.0F, -4.0F, 2.0F);
		base.addChild(wing1_l);
		setRotateAngle(wing1_l, 0.0F, -0.4098F, 0.0F);
		wing1_l.setTextureOffset(0, 20).addBox(0.0F, 0.0F, -1.0F, 24, 0, 14, true);

		wing1_r = new ModelRenderer(this);
		wing1_r.setRotationPoint(-6.0F, -4.0F, 2.0F);
		base.addChild(wing1_r);
		setRotateAngle(wing1_r, 0.0F, 0.4098F, 0.0F);
		wing1_r.setTextureOffset(0, 20).addBox(-24.0F, 0.0F, -1.0F, 24, 0, 14, false);

		leg0_l = new ModelRenderer(this);
		leg0_l.setRotationPoint(4.0F, 6.0F, -1.5F);
		base.addChild(leg0_l);
		setRotateAngle(leg0_l, 0.0F, 0.7854F, 0.7854F);
		leg0_l.setTextureOffset(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, true);

		leg1_l = new ModelRenderer(this);
		leg1_l.setRotationPoint(4.0F, 6.0F, -0.5F);
		base.addChild(leg1_l);
		setRotateAngle(leg1_l, 0.0F, 0.3927F, 0.5812F);
		leg1_l.setTextureOffset(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, true);

		leg2_l = new ModelRenderer(this);
		leg2_l.setRotationPoint(4.0F, 6.0F, 0.5F);
		base.addChild(leg2_l);
		setRotateAngle(leg2_l, 0.0F, -0.3927F, 0.5812F);
		leg2_l.setTextureOffset(76, 0).addBox(-1.0F, -1.0F, -1.0F, 12, 2, 2, true);

		leg0_r = new ModelRenderer(this);
		leg0_r.setRotationPoint(-4.0F, 6.0F, -1.5F);
		base.addChild(leg0_r);
		setRotateAngle(leg0_r, 0.0F, -0.7854F, -0.7854F);
		leg0_r.setTextureOffset(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, false);

		leg1_r = new ModelRenderer(this);
		leg1_r.setRotationPoint(-4.0F, 6.0F, -0.5F);
		base.addChild(leg1_r);
		setRotateAngle(leg1_r, 0.0F, -0.3927F, -0.5812F);
		leg1_r.setTextureOffset(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, false);

		leg2_r = new ModelRenderer(this);
		leg2_r.setRotationPoint(-4.0F, 6.0F, 0.5F);
		base.addChild(leg2_r);
		setRotateAngle(leg2_r, 0.0F, 0.3927F, -0.5812F);
		leg2_r.setTextureOffset(76, 0).addBox(-11.0F, -1.0F, -1.0F, 12, 2, 2, false);

		abdomen = new ModelRenderer(this);
		abdomen.setRotationPoint(0.0F, 0.0F, 1.0F);
		base.addChild(abdomen);
		setRotateAngle(abdomen, -0.3491F, 0.0F, 0.0F);
		abdomen.setTextureOffset(0, 34).addBox(-5.0F, -5.0F, -2.0F, 10, 8, 20, false);
		
		saddle = new ModelRenderer(this);
		saddle.setRotationPoint(-0.5F, 12.0F, 1.5F);
		base.addChild(saddle);
		saddle.setTextureOffset(76, 4).addBox(-5.5F, -19.0F, -5.0F, 12, 1, 7, false);

		saddle_top = new ModelRenderer(this);
		saddle_top.setRotationPoint(0.0F, -18.5F, -1.5F);
		saddle.addChild(saddle_top);
		saddle_top.setTextureOffset(76, 12).addBox(-5.5F, -2.5F, 2.5F, 12, 2, 1, false);
	}
    
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	this.base.render(scale);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) { 	 	  	
	    	float vibrate_rate = 0.5F;
	    	float i = (float)((EntityEnigmoth)entity).getSpellTicks() / 15.0F;
	    	
	    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);  	
	    	this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		    	
	    	this.wing0_r.rotateAngleX = 0.0F;
	    	this.wing0_r.rotateAngleY = 0.2087F;
	    	this.wing0_r.rotateAngleZ = 0.5F * MathHelper.sin(0.5F * ageInTicks);
	    	this.wing1_r.rotateAngleX = 0.0F;
	    	this.wing1_r.rotateAngleY = 0.4098F;
	    	this.wing1_r.rotateAngleZ = 0.5F * MathHelper.sin(0.5F * ageInTicks + 0.15F * (float)Math.PI);
	    	this.wing0_l.rotateAngleX = 0.0F;
	    	this.wing0_l.rotateAngleY = -0.2087F;
	    	this.wing0_l.rotateAngleZ = -0.5F * MathHelper.sin(0.5F * ageInTicks);
	    	this.wing1_l.rotateAngleX = 0.0F;
	    	this.wing1_l.rotateAngleY = -0.4098F;
	    	this.wing1_l.rotateAngleZ = -0.5F * MathHelper.sin(0.5F * ageInTicks + 0.15F * (float)Math.PI);
	     
	    	this.base.rotationPointY = 7.0F + (((EntityEnigmoth)entity).isBeingRidden() ? 5.0F : 5.0F * MathHelper.sin(ageInTicks * vibrate_rate));	 
	    	
	    	this.setRotateAngle(this.leg0_r, 0.0F, 0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, -1.6845917940249266F);
	    	this.setRotateAngle(this.leg1_r, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
	    	this.setRotateAngle(this.leg2_r, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
	    	this.setRotateAngle(this.leg0_l, 0.0F, -0.4553564018453205F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI, 1.6845917940249266F);
	    	this.setRotateAngle(this.leg1_l, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
	    	this.setRotateAngle(this.leg2_l, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);       
	    	
	    	if (i > 0.0F) {
		    	this.wing0_r.rotateAngleZ = 1.0F * MathHelper.sin(0.88F * ageInTicks);
		    	this.wing1_r.rotateAngleZ = 1.0F * MathHelper.sin(0.88F * ageInTicks + 0.15F * (float)Math.PI);
		    	this.wing0_l.rotateAngleZ = -1.0F * MathHelper.sin(0.88F * ageInTicks);
		    	this.wing1_l.rotateAngleZ = -1.0F * MathHelper.sin(0.88F * ageInTicks + 0.15F * (float)Math.PI); 
	    	} else if (this.state.equals(ModelFlyingBase.State.WAITING)) {
	    		vibrate_rate = 0.05F;
	    		
	    		this.base.rotationPointY = 12.0F;
	 
		    	this.abdomen.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
		    	
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
				
		        this.leg0_r.rotateAngleZ = -0.78539816F;
		        this.leg0_l.rotateAngleZ = 0.78539816F;
		        this.leg1_r.rotateAngleZ = -0.58119464F;
		        this.leg1_l.rotateAngleZ = 0.58119464F;
		        this.leg2_r.rotateAngleZ = -0.58119464F;
		        this.leg2_l.rotateAngleZ = 0.58119464F;
		        this.leg0_r.rotateAngleY = -0.78539816F;
		        this.leg0_l.rotateAngleY = 0.78539816F;
		        this.leg1_r.rotateAngleY = -0.39269908F;
		        this.leg1_l.rotateAngleY = 0.39269908F;
		        this.leg2_r.rotateAngleY = 0.39269908F;
		        this.leg2_l.rotateAngleY = -0.39269908F;
		        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		        this.leg0_r.rotateAngleY += f3;
		        this.leg0_l.rotateAngleY += -f3;
		        this.leg1_r.rotateAngleY += f4;
		        this.leg1_l.rotateAngleY += -f4;
		        this.leg2_r.rotateAngleY += f5;
		        this.leg2_l.rotateAngleY += -f5;
		        this.leg0_r.rotateAngleZ += f7;
		        this.leg0_l.rotateAngleZ += -f7;
		        this.leg1_r.rotateAngleZ += f8;
		        this.leg1_l.rotateAngleZ += -f8;
		        this.leg2_r.rotateAngleZ += f9;
		        this.leg2_l.rotateAngleZ += -f9;			
	    	} else if (this.state.equals(ModelFlyingBase.State.FLYING) || this.state.equals(ModelFlyingBase.State.ATTACKING)) {
		    	this.abdomen.rotateAngleX = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI; 
	    	} else if (this.state.equals(ModelFlyingBase.State.HOVERING)) {
		    	this.abdomen.rotateAngleX = -0.014F * (20 - ((EntityEnigmoth)entity).getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;   
    	}
    }
}
