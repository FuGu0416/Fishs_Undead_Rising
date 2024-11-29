package com.Fishmod.mod_LavaCow.client.model.entity;
// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarabEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScarabModel<T extends ScarabEntity> extends FURBaseModel<T> implements IHasHead {
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

	public ScarabModel() {
		texWidth = 32;
		texHeight = 32;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 23.5F, 5.0F);
		base.texOffs(0, 0).addBox(-2.5F, -3.5F, -4.0F, 5.0F, 3.0F, 6.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -1.0F, -4.0F);
		base.addChild(Head);
		this.setRotateAngle(Head, -0.1309F, 0.0F, 0.0F);
		Head.texOffs(11, 13).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);

		Jaw_l = new ModelRenderer(this);
		Jaw_l.setPos(-1.5F, -0.25F, -2.0F);
		Head.addChild(Jaw_l);
		this.setRotateAngle(Jaw_l, 0.0F, -0.1745F, 0.0F);
		Jaw_l.texOffs(2, 18).addBox(-0.9176F, -0.5F, -3.0681F, 1.0F, 1.0F, 3.0F, 0.0F, true);

		Jaw_r = new ModelRenderer(this);
		Jaw_r.setPos(1.5F, 0.0F, -2.0F);
		Head.addChild(Jaw_r);
		this.setRotateAngle(Jaw_r, 0.0F, 0.1745F, 0.0F);
		Jaw_r.texOffs(2, 18).addBox(-0.0824F, -0.75F, -3.0681F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		elytra_r = new ModelRenderer(this);
		elytra_r.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_r);
		this.setRotateAngle(elytra_r, 1.0996F, -0.4803F, -0.1511F);
		elytra_r.texOffs(0, 9).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 4.0F, 0.0F, true);

		elytra_l = new ModelRenderer(this);
		elytra_l.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_l);
		this.setRotateAngle(elytra_l, 1.0996F, 0.4803F, 0.1511F);
		elytra_l.texOffs(0, 9).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 4.0F, 0.0F, false);

		leg0_r = new ModelRenderer(this);
		leg0_r.setPos(1.0F, -1.0F, -2.0F);
		base.addChild(leg0_r);
		this.setRotateAngle(leg0_r, 0.0F, 0.7854F, 0.7854F);
		leg0_r.texOffs(16, 4).addBox(-0.5F, 0.2929F, -0.5F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		leg1_r = new ModelRenderer(this);
		leg1_r.setPos(1.0F, -1.0F, -1.0F);
		base.addChild(leg1_r);
		this.setRotateAngle(leg1_r, 0.0F, 0.3927F, 0.5812F);
		leg1_r.texOffs(16, 2).addBox(-0.2278F, 0.451F, -0.6802F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		leg2_r = new ModelRenderer(this);
		leg2_r.setPos(1.0F, -1.0F, 0.0F);
		base.addChild(leg2_r);
		this.setRotateAngle(leg2_r, 0.0F, -0.3927F, 0.5812F);
		leg2_r.texOffs(16, 0).addBox(-0.2278F, 0.451F, -1.3198F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		leg0_l = new ModelRenderer(this);
		leg0_l.setPos(-1.0F, -1.0F, -2.0F);
		base.addChild(leg0_l);
		this.setRotateAngle(leg0_l, 0.0F, -0.7854F, -0.7854F);
		leg0_l.texOffs(16, 4).addBox(-2.5F, 0.2929F, -0.5F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		leg1_l = new ModelRenderer(this);
		leg1_l.setPos(-1.0F, -1.0F, -1.0F);
		base.addChild(leg1_l);
		this.setRotateAngle(leg1_l, 0.0F, -0.3927F, -0.5812F);
		leg1_l.texOffs(16, 2).addBox(-2.7722F, 0.451F, -0.6802F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		leg2_l = new ModelRenderer(this);
		leg2_l.setPos(-1.0F, -1.0F, 0.0F);
		base.addChild(leg2_l);
		this.setRotateAngle(leg2_l, 0.0F, 0.3927F, -0.5812F);
		leg2_l.texOffs(16, 0).addBox(-2.7722F, 0.451F, -1.3198F, 3.0F, 0.0F, 1.0F, 0.0F, false);

		wing_r = new ModelRenderer(this);
		wing_r.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(wing_r);
		this.setRotateAngle(wing_r, 0.0853F, -0.153F, 0.2497F);
		wing_r.texOffs(6, 9).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 4.0F, 0.0F, true);

		wing_l = new ModelRenderer(this);
		wing_l.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(wing_l);
		this.setRotateAngle(wing_l, 0.0853F, 0.153F, -0.2497F);
		wing_l.texOffs(6, 9).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		float f = ageInTicks * 2.1F;
		float i = (float)((IAggressive) entity).getAttackTimer() / 10.0F;
		
		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
		
		if (i > 0.0F) {
			this.Jaw_r.yRot = GradientAnimation_s(-0.7F, 0.6F, i);
			this.Jaw_l.yRot = GradientAnimation_s(0.7F, -0.6F, i);
		} else if (entity.isAggressive()) {
			this.Jaw_r.yRot = -0.2618F;
			this.Jaw_l.yRot = 0.2618F;
		} else {	
	    	this.SwingY_Sin(this.Jaw_r, 0.0436F, ageInTicks, 0.0125F, 0.2F, false, 0.0F);
	    	this.SwingY_Sin(this.Jaw_l, -0.0436F, ageInTicks, 0.0125F, 0.2F, true, 0.0F);
		}
		
        if (entity.isOnGround()) {
            this.leg0_l.zRot = -0.7854F;
            this.leg0_r.zRot = 0.7854F;
            this.leg1_l.zRot = -0.5812F;
            this.leg1_r.zRot = 0.5812F;
            this.leg2_l.zRot = -0.5812F;
            this.leg2_r.zRot = 0.5812F;
            this.leg0_l.yRot = -0.7854F;
            this.leg0_r.yRot = 0.7854F;
            this.leg1_l.yRot = -0.3927F;
            this.leg1_r.yRot = 0.3927F;
            this.leg2_l.yRot = 0.3927F;
            this.leg2_r.yRot = -0.3927F;
            float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
            float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
            float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
            float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
            float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
            float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
            this.leg0_l.yRot += f3;
            this.leg0_r.yRot += -f3;
            this.leg1_l.yRot += f4;
            this.leg1_r.yRot += -f4;
            this.leg2_l.yRot += f5;
            this.leg2_r.yRot += -f5;
            this.leg0_l.zRot += f7;
            this.leg0_r.zRot += -f7;
            this.leg1_l.zRot += f8;
            this.leg1_r.zRot += -f8;
            this.leg2_l.zRot += f9;
            this.leg2_r.zRot += -f9;	
            
    		this.setRotateAngle(elytra_r, 0.0856F, 0.0F, 0.0275F);
    		this.setRotateAngle(elytra_l, 0.0856F, 0.0F, -0.0275F);
    		
			this.wing_r.zRot = 0.0F;
			this.wing_r.visible = false;
			this.wing_l.zRot = -this.wing_r.zRot;
			this.wing_l.visible = false;
        } else {
    		this.setRotateAngle(this.leg0_r, 0.0F, 0.7854F, 0.7854F);
    		this.setRotateAngle(this.leg1_r, 0.0F, 0.3927F, 0.5812F);
    		this.setRotateAngle(this.leg2_r, 0.0F, -0.3927F, 0.5812F);
    		this.setRotateAngle(this.leg0_l, 0.0F, -0.7854F, -0.7854F);
    		this.setRotateAngle(this.leg1_l, 0.0F, -0.3927F, -0.5812F);
    		this.setRotateAngle(this.leg2_l, 0.0F, 0.3927F, -0.5812F);      
    		
    		this.setRotateAngle(elytra_r, 1.0996F, -0.4803F, -0.1511F);
    		this.setRotateAngle(elytra_l, 1.0996F, 0.4803F, 0.1511F);
    		
			this.wing_r.zRot = MathHelper.cos(f) * (float)Math.PI * 0.1F;
			this.wing_r.visible = true;
			this.wing_l.zRot = -this.wing_r.zRot;
			this.wing_l.visible = true;
        }
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}