package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarab;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelScarab - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelScarab extends FishModelBase {
	private final ModelRenderer base;
	private final ModelRenderer Head;
	private final ModelRenderer Jaw_l;
	private final ModelRenderer Jaw_r;
	private final ModelRenderer elytra_r;
	private final ModelRenderer elytra_l;
	private final ModelRenderer leg0_r;
	private final ModelRenderer leg1_r;
	private final ModelRenderer leg2_r;
	private final ModelRenderer leg0_l;
	private final ModelRenderer leg1_l;
	private final ModelRenderer leg2_l;
	private final ModelRenderer wing_r;
	private final ModelRenderer wing_l;

	public ModelScarab() {
		textureWidth = 32;
		textureHeight = 32;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 23.5F, 5.0F);
		base.setTextureOffset(0, 0).addBox(-2.5F, -3.5F, -4.0F, 5, 3, 6, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -1.0F, -4.0F);
		base.addChild(Head);
		this.setRotateAngle(Head, -0.1309F, 0.0F, 0.0F);
		Head.setTextureOffset(11, 13).addBox(-2.0F, -1.5F, -3.0F, 4, 2, 3, false);

		Jaw_l = new ModelRenderer(this);
		Jaw_l.setRotationPoint(-1.5F, -0.25F, -2.0F);
		Head.addChild(Jaw_l);
		this.setRotateAngle(Jaw_l, 0.0F, -0.1745F, 0.0F);
		Jaw_l.setTextureOffset(2, 18).addBox(-0.9176F, -0.5F, -3.0681F, 1, 1, 3, true);

		Jaw_r = new ModelRenderer(this);
		Jaw_r.setRotationPoint(1.5F, 0.0F, -2.0F);
		Head.addChild(Jaw_r);
		this.setRotateAngle(Jaw_r, 0.0F, 0.1745F, 0.0F);
		Jaw_r.setTextureOffset(2, 18).addBox(-0.0824F, -0.75F, -3.0681F, 1, 1, 3, false);

		elytra_r = new ModelRenderer(this);
		elytra_r.setRotationPoint(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_r);
		this.setRotateAngle(elytra_r, 1.0996F, -0.4803F, -0.1511F);
		elytra_r.setTextureOffset(0, 9).addBox(-3.0F, 0.0F, 0.0F, 3, 3, 4, true);

		elytra_l = new ModelRenderer(this);
		elytra_l.setRotationPoint(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_l);
		this.setRotateAngle(elytra_l, 1.0996F, 0.4803F, 0.1511F);
		elytra_l.setTextureOffset(0, 9).addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, false);

		leg0_r = new ModelRenderer(this);
		leg0_r.setRotationPoint(1.0F, -1.0F, -2.0F);
		base.addChild(leg0_r);
		this.setRotateAngle(leg0_r, 0.0F, 0.7854F, 0.7854F);
		leg0_r.setTextureOffset(16, 4).addBox(-0.5F, 0.2929F, -0.5F, 3, 0, 1, false);

		leg1_r = new ModelRenderer(this);
		leg1_r.setRotationPoint(1.0F, -1.0F, -1.0F);
		base.addChild(leg1_r);
		this.setRotateAngle(leg1_r, 0.0F, 0.3927F, 0.5812F);
		leg1_r.setTextureOffset(16, 2).addBox(-0.2278F, 0.451F, -0.6802F, 3, 0, 1, false);

		leg2_r = new ModelRenderer(this);
		leg2_r.setRotationPoint(1.0F, -1.0F, 0.0F);
		base.addChild(leg2_r);
		this.setRotateAngle(leg2_r, 0.0F, -0.3927F, 0.5812F);
		leg2_r.setTextureOffset(16, 0).addBox(-0.2278F, 0.451F, -1.3198F, 3, 0, 1, false);

		leg0_l = new ModelRenderer(this);
		leg0_l.setRotationPoint(-1.0F, -1.0F, -2.0F);
		base.addChild(leg0_l);
		this.setRotateAngle(leg0_l, 0.0F, -0.7854F, -0.7854F);
		leg0_l.setTextureOffset(16, 4).addBox(-2.5F, 0.2929F, -0.5F, 3, 0, 1, false);

		leg1_l = new ModelRenderer(this);
		leg1_l.setRotationPoint(-1.0F, -1.0F, -1.0F);
		base.addChild(leg1_l);
		this.setRotateAngle(leg1_l, 0.0F, -0.3927F, -0.5812F);
		leg1_l.setTextureOffset(16, 2).addBox(-2.7722F, 0.451F, -0.6802F, 3, 0, 1, false);

		leg2_l = new ModelRenderer(this);
		leg2_l.setRotationPoint(-1.0F, -1.0F, 0.0F);
		base.addChild(leg2_l);
		this.setRotateAngle(leg2_l, 0.0F, 0.3927F, -0.5812F);
		leg2_l.setTextureOffset(16, 0).addBox(-2.7722F, 0.451F, -1.3198F, 3, 0, 1, false);

		wing_r = new ModelRenderer(this);
		wing_r.setRotationPoint(0.0F, -3.5F, -2.0F);
		base.addChild(wing_r);
		this.setRotateAngle(wing_r, 0.0853F, -0.153F, 0.2497F);
		wing_r.setTextureOffset(6, 9).addBox(-3.0F, 0.0F, 0.0F, 3, 0, 4, true);

		wing_l = new ModelRenderer(this);
		wing_l.setRotationPoint(0.0F, -3.5F, -2.0F);
		base.addChild(wing_l);
		this.setRotateAngle(wing_l, 0.0853F, 0.153F, -0.2497F);
		wing_l.setTextureOffset(6, 9).addBox(0.0F, 0.0F, 0.0F, 3, 0, 4, false);
	}

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.base.render(scale);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    	EntityScarab Entity = ((EntityScarab)entity);
		float f = ageInTicks * 2.1F;
		float i = (float)((IAggressive) entity).getAttackTimer() / 10.0F;
		
		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
		
		if (i > 0.0F) {
			this.Jaw_r.rotateAngleY = GradientAnimation_s(-0.7F, 0.6F, i);
			this.Jaw_l.rotateAngleY = GradientAnimation_s(0.7F, -0.6F, i);
		} else if (Entity.isAggressive()) {
			this.Jaw_r.rotateAngleY = -0.2618F;
			this.Jaw_l.rotateAngleY = 0.2618F;
		} else {	
	    	this.SwingY_Sin(this.Jaw_r, 0.0436F, ageInTicks, 0.0125F, 0.2F, false, 0.0F);
	    	this.SwingY_Sin(this.Jaw_l, -0.0436F, ageInTicks, 0.0125F, 0.2F, true, 0.0F);
		}
		
        if (Entity.onGround) {
            this.leg0_l.rotateAngleZ = -0.7854F;
            this.leg0_r.rotateAngleZ = 0.7854F;
            this.leg1_l.rotateAngleZ = -0.5812F;
            this.leg1_r.rotateAngleZ = 0.5812F;
            this.leg2_l.rotateAngleZ = -0.5812F;
            this.leg2_r.rotateAngleZ = 0.5812F;
            this.leg0_l.rotateAngleY = -0.7854F;
            this.leg0_r.rotateAngleY = 0.7854F;
            this.leg1_l.rotateAngleY = -0.3927F;
            this.leg1_r.rotateAngleY = 0.3927F;
            this.leg2_l.rotateAngleY = 0.3927F;
            this.leg2_r.rotateAngleY = -0.3927F;
            float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
            float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
            float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
            float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
            float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
            float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
            this.leg0_l.rotateAngleY += f3;
            this.leg0_r.rotateAngleY += -f3;
            this.leg1_l.rotateAngleY += f4;
            this.leg1_r.rotateAngleY += -f4;
            this.leg2_l.rotateAngleY += f5;
            this.leg2_r.rotateAngleY += -f5;
            this.leg0_l.rotateAngleZ += f7;
            this.leg0_r.rotateAngleZ += -f7;
            this.leg1_l.rotateAngleZ += f8;
            this.leg1_r.rotateAngleZ += -f8;
            this.leg2_l.rotateAngleZ += f9;
            this.leg2_r.rotateAngleZ += -f9;	
            
    		this.setRotateAngle(elytra_r, 0.0856F, 0.0F, 0.0275F);
    		this.setRotateAngle(elytra_l, 0.0856F, 0.0F, -0.0275F);
    		
			this.wing_r.rotateAngleZ = 0.0F;
			this.wing_r.isHidden = true;
			this.wing_l.rotateAngleZ = -this.wing_r.rotateAngleZ;
			this.wing_l.isHidden = true;
        } else {
    		this.setRotateAngle(this.leg0_r, 0.0F, 0.7854F, 0.7854F);
    		this.setRotateAngle(this.leg1_r, 0.0F, 0.3927F, 0.5812F);
    		this.setRotateAngle(this.leg2_r, 0.0F, -0.3927F, 0.5812F);
    		this.setRotateAngle(this.leg0_l, 0.0F, -0.7854F, -0.7854F);
    		this.setRotateAngle(this.leg1_l, 0.0F, -0.3927F, -0.5812F);
    		this.setRotateAngle(this.leg2_l, 0.0F, 0.3927F, -0.5812F);      
    		
    		this.setRotateAngle(elytra_r, 1.0996F, -0.4803F, -0.1511F);
    		this.setRotateAngle(elytra_l, 1.0996F, 0.4803F, 0.1511F);
    		
			this.wing_r.rotateAngleZ = MathHelper.cos(f) * (float)Math.PI * 0.1F;
			this.wing_r.isHidden = false;
			this.wing_l.rotateAngleZ = -this.wing_r.rotateAngleZ;
			this.wing_l.isHidden = false;
        }
    }
}
