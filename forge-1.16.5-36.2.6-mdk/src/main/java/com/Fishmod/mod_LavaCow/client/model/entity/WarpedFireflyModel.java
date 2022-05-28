package com.Fishmod.mod_LavaCow.client.model.entity;
// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;

public class WarpedFireflyModel<T extends WarpedFireflyEntity> extends FURBaseModel<T> {
	private final ModelRenderer base;
	private final ModelRenderer antenne_r;
	private final ModelRenderer antenne_l;
	private final ModelRenderer leg0_r;
	private final ModelRenderer leg1_r;
	private final ModelRenderer leg2_r;
	private final ModelRenderer leg0_l;
	private final ModelRenderer leg1_l;
	private final ModelRenderer leg2_l;
	private final ModelRenderer elytra_r;
	private final ModelRenderer elytra_l;
	private final ModelRenderer wing_r;
	private final ModelRenderer wing_l;

	public WarpedFireflyModel() {
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 16.5F, 1.0F);
		base.texOffs(0, 0).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, 0.0F, false);

		antenne_r = new ModelRenderer(this);
		antenne_r.setPos(-2.0F, -1.5F, -5.0F);
		base.addChild(antenne_r);
		setRotateAngle(antenne_r, -0.5672F, 0.0F, 0.0F);
		antenne_r.texOffs(0, 0).addBox(-0.5F, 0.0F, -4.75F, 1.0F, 0.0F, 5.0F, 0.0F, true);

		antenne_l = new ModelRenderer(this);
		antenne_l.setPos(2.0F, -1.5F, -5.0F);
		base.addChild(antenne_l);
		setRotateAngle(antenne_l, -0.5672F, 0.0F, 0.0F);
		antenne_l.texOffs(0, 0).addBox(-0.5F, 0.0F, -4.75F, 1.0F, 0.0F, 5.0F, 0.0F, false);

		leg0_r = new ModelRenderer(this);
		leg0_r.setPos(-2.0F, 3.5F, -2.0F);
		base.addChild(leg0_r);
		setRotateAngle(leg0_r, -1.0472F, 0.0F, 0.0F);
		leg0_r.texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, true);

		leg1_r = new ModelRenderer(this);
		leg1_r.setPos(-2.0F, 3.5F, -1.0F);
		base.addChild(leg1_r);
		setRotateAngle(leg1_r, -1.0472F, 0.0F, 0.0F);
		leg1_r.texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, true);

		leg2_r = new ModelRenderer(this);
		leg2_r.setPos(-2.0F, 3.5F, 0.0F);
		base.addChild(leg2_r);
		setRotateAngle(leg2_r, -1.0472F, 0.0F, 0.0F);
		leg2_r.texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, true);

		leg0_l = new ModelRenderer(this);
		leg0_l.setPos(2.0F, 3.5F, -2.0F);
		base.addChild(leg0_l);
		setRotateAngle(leg0_l, -1.0472F, 0.0F, 0.0F);
		leg0_l.texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		leg1_l = new ModelRenderer(this);
		leg1_l.setPos(2.0F, 3.5F, -1.0F);
		base.addChild(leg1_l);
		setRotateAngle(leg1_l, -1.0472F, 0.0F, 0.0F);
		leg1_l.texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		leg2_l = new ModelRenderer(this);
		leg2_l.setPos(2.0F, 3.5F, 0.0F);
		base.addChild(leg2_l);
		setRotateAngle(leg2_l, -1.0472F, 0.0F, 0.0F);
		leg2_l.texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		elytra_r = new ModelRenderer(this);
		elytra_r.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_r);
		setRotateAngle(elytra_r, 0.7161F, -0.2747F, 0.0468F);
		elytra_r.texOffs(0, 17).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 7.0F, 0.0F, true);

		elytra_l = new ModelRenderer(this);
		elytra_l.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(elytra_l);
		setRotateAngle(elytra_l, 0.7161F, 0.2747F, -0.0468F);
		elytra_l.texOffs(0, 17).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);

		wing_r = new ModelRenderer(this);
		wing_r.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(wing_r);
		setRotateAngle(wing_r, 0.0853F, -0.153F, 0.2497F);
		wing_r.texOffs(17, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 7.0F, 0.0F, true);

		wing_l = new ModelRenderer(this);
		wing_l.setPos(0.0F, -3.5F, -2.0F);
		base.addChild(wing_l);
		setRotateAngle(wing_l, 0.0853F, 0.153F, -0.2497F);
		wing_l.texOffs(17, 0).addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        ImmutableList.of(this.base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        });
	}
}