package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityEnigmothLarva;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEnigmothLarva extends ModelFlyingBase {
	private final ModelRenderer base;
	private final ModelRenderer head;
	private final ModelRenderer abdomen;

	public ModelEnigmothLarva() {
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 18.0F, -0.5F);
		base.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -4.0F, 6, 6, 6, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 4.0F, -4.0F);
		base.addChild(head);
		head.setTextureOffset(20, 8).addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, false);

		abdomen = new ModelRenderer(this);
		abdomen.setRotationPoint(-0.5F, 3.0F, 2.0F);
		base.addChild(abdomen);
		abdomen.setTextureOffset(0, 12).addBox(-2.0F, -2.0F, 0.0F, 5, 5, 7, false);
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
    	
		this.head.rotateAngleX = -0.4363F;
		this.head.rotateAngleY = 0.75F * MathHelper.sin(0.88F * ageInTicks);
		
		this.base.rotateAngleY = 0.2F * MathHelper.sin(0.88F * ageInTicks);
		
		this.abdomen.rotateAngleX = 0.6545F;
		this.abdomen.rotateAngleY = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    	
    	if (i > 0) {
    	} else {
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		this.abdomen.rotationPointZ = 1.0F + (0.2F * MathHelper.sin(0.03F * ageInTicks)) + (1.0F * MathHelper.sin(limbSwingAmount));
    		this.abdomen.rotateAngleX = 0.0F;
    	}
    }
}
