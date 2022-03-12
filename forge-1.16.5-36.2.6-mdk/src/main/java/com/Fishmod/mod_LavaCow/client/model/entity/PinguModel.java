package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.PinguEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelPingu - Mojang, edited by Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class PinguModel<T extends PinguEntity> extends FURBaseModel<T> implements IHasHead {
    public ModelRenderer body;
    public ModelRenderer wing_l;
    public ModelRenderer wing_r;
    public ModelRenderer tail;
    public ModelRenderer leg_l;
    public ModelRenderer leg_r;
    public ModelRenderer Feathers;
    public ModelRenderer core;
    public ModelRenderer Ice;
    public ModelRenderer Ice1;
    public ModelRenderer Ice2;
    public ModelRenderer head;
    public ModelRenderer paw_l;
    public ModelRenderer paw_r;
    public ModelRenderer beak_u;
    public ModelRenderer beak_d;
    public ModelRenderer head_ice;

    public PinguModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.paw_r = new ModelRenderer(this, 8, 14);
        this.paw_r.setPos(0.0F, 3.0F, 0.5F);
        this.paw_r.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leg_l = new ModelRenderer(this, 11, 9);
        this.leg_l.mirror = true;
        this.leg_l.setPos(2.5F, -1.7F, 1.3F);
        this.leg_l.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.beak_u = new ModelRenderer(this, 0, 8);
        this.beak_u.setPos(0.0F, -1.5F, -4.0F);
        this.beak_u.addBox(-0.5F, -1.0F, -4.0F, 1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.head_ice = new ModelRenderer(this, 0, 20);
        this.head_ice.setPos(0.0F, -2.0F, -2.0F);
        this.head_ice.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Feathers = new ModelRenderer(this, 38, 0);
        this.Feathers.setPos(0.0F, -3.5F, 2.5F);
        this.Feathers.addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.core = new ModelRenderer(this, 19, 14);
        this.core.setPos(0.0F, -4.5F, 0.0F);
        this.core.addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Ice = new ModelRenderer(this, 32, 15);
        this.Ice.setPos(0.0F, 0.0F, 0.0F);
        this.Ice.addBox(-4.0F, -8.5F, -3.0F, 8.0F, 9.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Ice1 = new ModelRenderer(this, 32, 15);
        this.Ice1.setPos(0.0F, 3.0F, 0.0F);
        this.Ice1.addBox(-4.0F, -8.5F, -3.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Ice2 = new ModelRenderer(this, 32, 15);
        this.Ice2.setPos(0.0F, 6.0F, 0.0F);
        this.Ice2.addBox(-4.0F, -8.5F, -3.0F, 8.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.paw_l = new ModelRenderer(this, 8, 14);
        this.paw_l.mirror = true;
        this.paw_l.setPos(0.0F, 3.0F, 0.5F);
        this.paw_l.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 17, 0);
        this.body.setPos(0.0F, 22.7F, -2.0F);
        this.body.addBox(-2.5F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 0.017453292519943295F, 0.0F, 0.0F);
        this.wing_r = new ModelRenderer(this, 49, 0);
        this.wing_r.setPos(-4.0F, -8.0F, 0.0F);
        this.wing_r.addBox(0.0F, 0.0F, -2.5F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(wing_r, 0.02652900429741867F, -3.141592653589793F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 13, 1);
        this.tail.setPos(0.0F, 0.0F, 2.5F);
        this.tail.addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 1.1520918765257122F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, -9.5F, 2.5F);
        this.head.addBox(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leg_r = new ModelRenderer(this, 11, 9);
        this.leg_r.setPos(-2.5F, -1.7F, 1.3F);
        this.leg_r.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.beak_d = new ModelRenderer(this, 0, 14);
        this.beak_d.setPos(0.0F, -1.5F, -4.0F);
        this.beak_d.addBox(-0.5F, 0.0F, -4.0F, 1.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.wing_l = new ModelRenderer(this, 49, 0);
        this.wing_l.mirror = true;
        this.wing_l.setPos(4.0F, -8.0F, 0.0F);
        this.wing_l.addBox(-1.0F, 0.0F, -2.5F, 1.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(wing_l, 0.02652900429741867F, -3.141592653589793F, -0.08726646259971647F);
        this.leg_r.addChild(this.paw_r);
        this.body.addChild(this.leg_l);
        this.head.addChild(this.beak_u);
        this.head.addChild(this.head_ice);
        this.body.addChild(this.Feathers);
        this.body.addChild(this.core);
        this.body.addChild(this.Ice);
        this.body.addChild(this.Ice1);
        this.body.addChild(this.Ice2);
        this.leg_l.addChild(this.paw_l);
        this.body.addChild(this.wing_r);
        this.body.addChild(this.tail);
        this.body.addChild(this.head);
        this.body.addChild(this.leg_r);
        this.head.addChild(this.beak_d);
        this.body.addChild(this.wing_l);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
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
    	if (entityIn.isAggressive() || entityIn.isInWater()) {
    		this.Head_Looking(this.head, -1.5707963267948966F, 0.0F, netHeadYaw, headPitch);
    		this.head.z = -1.0F;
    		this.body.zRot = 0.0F;
    		this.wing_l.y = -5.5F;
    		wing_l.xRot = 1.5707963267948966F;
    		wing_l.yRot = 1.5707963267948966F + ((entityIn.isInWater()) ? MathHelper.sin(limbSwing * limbSwingAmount * 0.1F + (float)Math.PI) * 0.7F : 0.0F);
    		wing_l.zRot = -0.08726646259971647F;
    		this.wing_r.y = -5.5F;
    		wing_r.xRot = 1.5707963267948966F;
    		wing_r.yRot = -1.5707963267948966F + ((entityIn.isInWater()) ? MathHelper.sin(limbSwing * limbSwingAmount * 0.1F) * 0.7F : 0.0F);
    		wing_r.zRot = 0.08726646259971647F;
    	} else {
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		this.head.z = 2.5F;
    		this.body.zRot = MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
    		
    		this.wing_l.y = -7.0F;
    		wing_l.xRot = 0.02652900429741867F;
    		wing_l.yRot = -3.141592653589793F;
    		this.wing_r.y = -7.0F;
    		wing_r.xRot = 0.02652900429741867F;
    		wing_r.yRot = 3.141592653589793F;
    		
        	this.leg_r.xRot = MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        	this.leg_l.xRot = MathHelper.cos(limbSwing * 0.8F + (float)Math.PI) * 0.7F * limbSwingAmount;
        	
        	if(MathHelper.abs(limbSwingAmount) > 0.1F) {
        		this.wing_l.zRot = -1.3F + MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        		this.wing_r.zRot = 1.3F - MathHelper.cos(limbSwing * 0.8F) * 0.7F * limbSwingAmount;
        		this.beak_d.xRot = 0.3662880558742251F;
        	} else {
        		this.wing_l.zRot = -0.08726646259971647F;
        		this.wing_r.zRot = 0.08726646259971647F;
        		this.beak_d.xRot = 0.0F;
        	}
    	}
    	
    	if(entityIn.getHealth() < entityIn.getMaxHealth() * 0.3F) {
    		this.head.visible = false;
    		this.wing_l.visible = false;
    		this.wing_r.visible = false;
    		this.Ice.visible = false;
    		this.Ice1.visible = false;
    		this.Ice2.visible = true;
    	} else if(entityIn.getHealth() < entityIn.getMaxHealth() * 0.5F) {
    		this.head.visible = false;
    		this.wing_l.visible = true;
    		this.wing_r.visible = true;
    		this.Ice.visible = false;
    		this.Ice1.visible = true;
    		this.Ice2.visible = false;
    	} else {
    		this.head.visible = true;
    		this.wing_l.visible = true;
    		this.wing_r.visible = true;
    		this.Ice.visible = true;
    		this.Ice1.visible = false;
    		this.Ice2.visible = false;
    	}    	
    }
    
	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}
