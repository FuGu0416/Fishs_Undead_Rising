package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelLilSludge - Fish0016054
 * Created using Tabula 7.0.1
 */
public class LilSludgeModel<T extends LilSludgeEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body;
    public ModelRenderer Arm_r;
    public ModelRenderer Arm_l;
    public ModelRenderer Leg_r;
    public ModelRenderer Leg_l;
    public ModelRenderer Head_base;
    public ModelRenderer Head;
    public ModelRenderer Head_skull;

    public LilSludgeModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Arm_l = new ModelRenderer(this, 6, 27);
        this.Arm_l.mirror = true;
        this.Arm_l.setPos(2.5F, -5.0F, 0.0F);
        this.Arm_l.addBox(0.0F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Arm_l, 0.0F, 0.0F, -0.36425021489121656F);
        this.Head = new ModelRenderer(this, 0, 12);
        this.Head.setPos(0.0F, -2.0F, 0.0F);
        this.Head.addBox(-3.5F, -7.0F, -3.5F, 7, 7, 7, 0.0F);
        this.Leg_r = new ModelRenderer(this, 0, 28);
        this.Leg_r.setPos(-2.0F, 0.0F, 0.0F);
        this.Leg_r.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.Head_base = new ModelRenderer(this, 0, 0);
        this.Head_base.setPos(0.0F, -6.0F, 0.0F);
        this.Head_base.addBox(-4.0F, -2.0F, -4.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Head_base, 0.18203784098300857F, 0.0F, 0.22759093446006054F);
        this.Leg_l = new ModelRenderer(this, 0, 28);
        this.Leg_l.mirror = true;
        this.Leg_l.setPos(2.0F, 0.0F, 0.0F);
        this.Leg_l.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.Body = new ModelRenderer(this, 30, 12);
        this.Body.setPos(0.0F, 22.0F, 0.0F);
        this.Body.addBox(-2.5F, -7.0F, -2.0F, 5, 7, 4, 0.0F);
        this.Arm_r = new ModelRenderer(this, 6, 27);
        this.Arm_r.setPos(-2.5F, -5.0F, 0.0F);
        this.Arm_r.addBox(-1.0F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Arm_r, 0.0F, 0.0F, 0.4553564018453205F);
        this.Head_skull = new ModelRenderer(this, 34, 0);
        this.Head_skull.setPos(0.0F, -5.5F, 0.0F);
        this.Head_skull.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
        this.Body.addChild(this.Arm_l);
        this.Head_base.addChild(this.Head_skull);
        this.Body.addChild(this.Leg_r);
        this.Body.addChild(this.Head_base);
        this.Body.addChild(this.Leg_l);
        this.Body.addChild(this.Arm_r);
        this.Head_base.addChild(this.Head);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
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
    	this.Head_base.y = -6.0F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
    	this.Head_skull.xRot = headPitch * 0.017453292F;
    	this.Head_skull.yRot = netHeadYaw * 0.017453292F;        
    	this.Head_skull.y = -5.5F + 0.5F * MathHelper.sin(0.03F * ageInTicks);

        this.Leg_r.xRot = MathHelper.cos(limbSwing) * 0.7F * limbSwingAmount;
        this.Leg_l.xRot = MathHelper.cos(limbSwing + (float)Math.PI) * 0.7F * limbSwingAmount;
                
        if(entityIn.isAggressive()) {
        	this.Arm_r.xRot = -1.6390387005478748F;
        	this.Arm_l.xRot = -1.6390387005478748F;
        }
        else {
	        this.Arm_r.xRot = MathHelper.cos(limbSwing + (float)Math.PI);
	        this.Arm_l.xRot = MathHelper.cos(limbSwing);
        }
    }

	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.Head_base;
	}
}
