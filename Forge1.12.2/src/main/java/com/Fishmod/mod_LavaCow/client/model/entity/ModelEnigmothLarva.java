package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityEnigmothLarva;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEnigmothLarva extends ModelFlyingBase {
	private final ModelRenderer base;
	private final ModelRenderer head;
	private final ModelRenderer thorax;
	private final ModelRenderer thorax_bristle_right;
	private final ModelRenderer thorax_bristle_left;
	private final ModelRenderer abdomen;
	private final ModelRenderer tail;

	public ModelEnigmothLarva() {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 18.0F, -0.5F);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 4.0F, -4.0F);
		base.addChild(head);
		head.setTextureOffset(20, 8).addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, false);
		
		thorax = new ModelRenderer(this);
		thorax.setRotationPoint(0.0F, 6.0F, 0.5F);
		base.addChild(thorax);
		thorax.setTextureOffset(0, 0).addBox(-3.0F, -6.0F, -4.5F, 6, 6, 6, false);

		thorax_bristle_right = new ModelRenderer(this);
		thorax_bristle_right.setRotationPoint(-3.0F, -6.0F, -1.5F);
		thorax.addChild(thorax_bristle_right);
		setRotationAngle(thorax_bristle_right, 0.0F, 0.0F, -0.7854F);
		thorax_bristle_right.setTextureOffset(18, -6).addBox(0.0F, -2.0F, -3.0F, 0, 2, 6, false);

		thorax_bristle_left = new ModelRenderer(this);
		thorax_bristle_left.setRotationPoint(3.0F, -6.0F, -1.5F);
		thorax.addChild(thorax_bristle_left);
		setRotationAngle(thorax_bristle_left, 0.0F, 0.0F, 0.7854F);
		thorax_bristle_left.setTextureOffset(18, -6).addBox(0.0F, -2.0F, -3.0F, 0, 2, 6, false);

		abdomen = new ModelRenderer(this);
		abdomen.setRotationPoint(-0.5F, 3.0F, 2.0F);
		base.addChild(abdomen);
		abdomen.setTextureOffset(0, 12).addBox(-2.0F, -2.0F, 0.0F, 5, 5, 7, false);
		
		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.5F, 0.0F, 7.0F);
		abdomen.addChild(tail);
		setRotationAngle(tail, 0.5672F, 0.0F, 0.0F);
		tail.setTextureOffset(-5, 12).addBox(-1.5F, 0.0F, 0.0F, 3, 0, 5, false);
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
    	int i = ((EntityEnigmothLarva)entity).getSpellTicks();
    	float j = (1.0F - Math.abs(this.triangleWave(limbSwing, 13.0F)));
    	
    	if (i > 0) {
    	} else {
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		this.abdomen.rotationPointZ = 1.0F + (0.2F * MathHelper.sin(0.03F * ageInTicks)) + (1.0F * MathHelper.sin(limbSwingAmount));
    		this.abdomen.rotateAngleX = 0.0F;
    	}
    	
    	if (i > 0) {
    		this.base.rotateAngleY = 0.2F * MathHelper.sin(0.88F * ageInTicks);
    		
    		this.head.rotateAngleY = -0.4363F;
    		this.head.rotateAngleY = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    		this.head.rotationPointZ = -4.0F;
    		
    		this.thorax.rotateAngleX = 0.0F;
    		this.thorax.rotationPointY = 6.0F;    		
    		
    		this.abdomen.rotateAngleX = 0.6545F;
    		this.abdomen.rotationPointY = 3.0F;
    		this.abdomen.rotateAngleY = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    		this.abdomen.rotationPointZ = 2.0F;
    	} else {
    		this.base.rotateAngleY = 0.0F;
    		
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw * 0.8F, headPitch * 0.8F);
    		this.head.rotationPointZ = -4.0F + (1.0F * j);
    		
    		this.Head_Looking(this.thorax, (float)Math.toRadians(17.5F) * j, 0.0F, netHeadYaw * 0.3F, headPitch * 0.3F);
    		this.thorax.rotationPointY = 6.0F - (1.0F * j);
    		
    		this.abdomen.rotateAngleX = (float)Math.toRadians(-25.0F) * j;
    		this.abdomen.rotationPointY = 3.0F - (2.0F * j);
    		this.abdomen.rotateAngleY = 0.0F;
    		this.abdomen.rotationPointZ = 2.0F - (1.0F * j);		
    	}
    }
}
