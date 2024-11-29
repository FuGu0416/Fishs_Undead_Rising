// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.LivingArmorEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class LivingArmorModel<T extends LivingArmorEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
	private final ModelRenderer waist;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer hat;
	private final ModelRenderer rightVisor;
	private final ModelRenderer leftVisor;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightShoulder;
	private final ModelRenderer rightItem;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftShoulder;
	private final ModelRenderer leftItem;
	private final ModelRenderer rightLeg;
	private final ModelRenderer rightTasset;
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftTasset;

	public LivingArmorModel() {
		this.texWidth = 64;
		this.texHeight = 64;

		this.waist = new ModelRenderer(this);
		this.waist.setPos(0.0F, 8.0F, 0.0F);
		
		this.body = new ModelRenderer(this);
		this.body.setPos(0.0F, -12.0F, 0.0F);
		this.waist.addChild(this.body);
		this.body.texOffs(0, 16).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 12.0F, 6.0F, 0.0F, false);

		this.head = new ModelRenderer(this);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.body.addChild(this.head);
		setRotationAngle(this.head, -0.0436F, 0.0F, 0.0F);
		this.head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.hat = new ModelRenderer(this);
		this.hat.setPos(0.0F, -2.0F, 0.0F);
		this.head.addChild(this.hat);
		this.hat.texOffs(32, 0).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 7.0F, 7.0F, 0.0F, false);

		this.rightVisor = new ModelRenderer(this);
		this.rightVisor.setPos(-3.5F, -3.0F, -0.5F);
		this.head.addChild(this.rightVisor);
		this.rightVisor.texOffs(24, 0).addBox(-1.0F, -3.0F, -3.5F, 4.0F, 4.0F, 3.0F, 0.5F, false);

		this.leftVisor = new ModelRenderer(this);
		this.leftVisor.setPos(3.5F, -3.0F, -0.5F);
		this.head.addChild(this.leftVisor);
		this.leftVisor.texOffs(24, 0).addBox(-3.0F, -3.0F, -3.5F, 4.0F, 4.0F, 3.0F, 0.5F, true);

		this.rightArm = new ModelRenderer(this);
		this.rightArm.setPos(-6.0F, 2.0F, 0.0F);
		this.body.addChild(this.rightArm);
		this.rightArm.texOffs(32, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		this.rightShoulder = new ModelRenderer(this);
		this.rightShoulder.setPos(0.0F, 0.5F, -0.5F);
		this.rightArm.addChild(this.rightShoulder);
		this.rightShoulder.texOffs(0, 47).addBox(-4.0F, -3.0F, -2.0F, 5.0F, 6.0F, 5.0F, 0.0F, false);

		this.rightItem = new ModelRenderer(this);
		this.rightItem.setPos(-1.0F, 7.0F, 1.0F);
		this.rightArm.addChild(this.rightItem);
		
		this.leftArm = new ModelRenderer(this);
		this.leftArm.setPos(6.0F, 2.0F, 0.0F);
		this.body.addChild(this.leftArm);
		this.leftArm.texOffs(32, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);

		this.leftShoulder = new ModelRenderer(this);
		this.leftShoulder.setPos(0.0F, 0.5F, -0.5F);
		this.leftArm.addChild(this.leftShoulder);
		this.leftShoulder.texOffs(0, 47).addBox(-1.0F, -3.0F, -2.0F, 5.0F, 6.0F, 5.0F, 0.0F, true);

		this.leftItem = new ModelRenderer(this);
		this.leftItem.setPos(1.0F, 7.0F, 1.0F);
		this.leftArm.addChild(this.leftItem);
		
		this.rightLeg = new ModelRenderer(this);
		this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
		this.body.addChild(this.rightLeg);
		this.rightLeg.texOffs(32, 34).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F, false);

		this.rightTasset = new ModelRenderer(this);
		this.rightTasset.setPos(0.0F, 0.0F, 0.0F);
		this.rightLeg.addChild(this.rightTasset);
		this.rightTasset.texOffs(0, 34).addBox(-3.1F, 0.0F, -3.0F, 5.0F, 7.0F, 6.0F, 0.0F, false);

		this.leftLeg = new ModelRenderer(this);
		this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
		this.body.addChild(this.leftLeg);
		this.leftLeg.texOffs(32, 34).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F, true);

		this.leftTasset = new ModelRenderer(this);
		this.leftTasset.setPos(1.0F, 0.0F, 0.0F);
		this.leftLeg.addChild(this.leftTasset);
		this.leftTasset.texOffs(0, 34).addBox(-2.9F, 0.0F, -3.0F, 5.0F, 7.0F, 6.0F, 0.0F, true);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.waist.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
	
	@Override
	protected ModelRenderer getArm(HandSide p_187074_1_) {
		return p_187074_1_ == HandSide.LEFT ? this.leftArm : this.rightArm;
	}
	
	@Override
	public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
		float f = p_225599_1_ == HandSide.RIGHT ? 1.0F : -1.0F;
		ModelRenderer modelrenderer = this.getArm(p_225599_1_);
		modelrenderer.x += f;
		modelrenderer.translateAndRotate(p_225599_2_);
		modelrenderer.x -= f;
	}
}