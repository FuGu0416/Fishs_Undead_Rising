package com.Fishmod.mod_LavaCow.client.model.entity;
// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.Fishmod.mod_LavaCow.entities.floating.GraveRobberGhostEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;

public class GraveRobberGhostModel<T extends GraveRobberGhostEntity> extends FlyingBaseModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer nose;
	private final ModelRenderer arm_l;
	private final ModelRenderer arm_r;
	private final ModelRenderer body;
	private final ModelRenderer arm_fold;
	private final ModelRenderer hand_fold;

	public GraveRobberGhostModel() {
		texWidth = 128;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		nose = new ModelRenderer(this);
		nose.setPos(0.0F, -2.0F, 0.0F);
		head.addChild(nose);
		nose.texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		arm_l = new ModelRenderer(this);
		arm_l.setPos(5.0F, 2.0F, 0.0F);
		setRotateAngle(arm_l, -1.2654F, 0.0F, 0.0F);
		arm_l.texOffs(40, 46).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		arm_r = new ModelRenderer(this);
		arm_r.setPos(-5.0F, 2.0F, 0.0F);
		setRotateAngle(arm_r, -1.2654F, 0.0F, 0.0F);
		arm_r.texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.0F, false);

		arm_fold = new ModelRenderer(this);
		arm_fold.setPos(0.0F, 3.0F, -1.0F);
		setRotateAngle(arm_fold, -0.75F, 0.0F, 0.0F);
		arm_fold.texOffs(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
		arm_fold.texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);

		hand_fold = new ModelRenderer(this);
		hand_fold.setPos(0.0F, 0.0F, 0.0F);
		arm_fold.addChild(hand_fold);
		hand_fold.texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
		
		if(entity.isAggressive()) {
			arm_l.visible = true;
			arm_r.visible = true;
			arm_fold.visible = false;
		} else {
			arm_l.visible = false;
			arm_r.visible = false;
			arm_fold.visible = true;			
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha * 0.75F);
		arm_l.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha * 0.75F);
		arm_r.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha * 0.75F);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha * 0.75F);
		arm_fold.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha * 0.75F);
	}
}