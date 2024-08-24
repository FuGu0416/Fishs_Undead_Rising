package com.Fishmod.mod_LavaCow.client.model.entity;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractIllager;

public class ModelGraveRobber<T extends AbstractIllager> extends ModelIllager {
	private final ModelRenderer bag;
	private final ModelRenderer mask;

	public ModelGraveRobber(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
		super(scaleFactor, p_i47227_2_, textureWidthIn, textureHeightIn);
		
		this.bag = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.bag.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.bag.setTextureOffset(64, 0).addBox(-4.0F, -24.0F, 3.0F, 8, 14, 8, false);
		this.bag.setTextureOffset(56, 0).addBox(-2.0F, -27.0F, 5.0F, 4, 3, 4, false);
		this.mask = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
		this.mask.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mask.setTextureOffset(32, 0).addBox(-4.0F, -10.075F, -4.0F, 8, 12, 8, scaleFactor + 0.525F);
		this.head.addChild(this.mask);
	}
	
	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        ImmutableList.of(this.bag).forEach((modelRenderer) -> { 
            modelRenderer.render(scale);
        });
    }
}
