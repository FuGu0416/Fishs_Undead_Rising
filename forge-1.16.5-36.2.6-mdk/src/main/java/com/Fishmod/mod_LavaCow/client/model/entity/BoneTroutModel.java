// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.aquatic.UndeadFishEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BoneTroutModel<T extends UndeadFishEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer head;
	private final ModelRenderer chest;
	private final ModelRenderer fin_anal;
	private final ModelRenderer fin_caudal;
	private final ModelRenderer body;
	private final ModelRenderer fin_dorsal;
	private final ModelRenderer fin_pectoral_r;
	private final ModelRenderer fin_pectoral_l;

	public BoneTroutModel() {
		this.texWidth = 32;
		this.texHeight = 32;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 23.0F, -5.0F);
		head.texOffs(0, 14).addBox(-1.5F, -4.0F, -1.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);

		chest = new ModelRenderer(this);
		chest.setPos(0.0F, 24.0F, -2.0F);
		chest.texOffs(0, 0).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);

		fin_anal = new ModelRenderer(this);
		fin_anal.setPos(0.0F, 23.0F, -2.0F);
		fin_anal.texOffs(11, 0).addBox(0.0F, 1.0F, 5.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);

		fin_caudal = new ModelRenderer(this);
		fin_caudal.setPos(0.0F, 21.5F, 7.0F);
		fin_caudal.texOffs(14, 12).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 23.0F, -2.0F);
		body.texOffs(0, 0).addBox(0.0F, -4.0F, 0.0F, 0.0F, 5.0F, 9.0F, 0.0F, false);

		fin_dorsal = new ModelRenderer(this);
		fin_dorsal.setPos(0.0F, 23.0F, -2.0F);
		fin_dorsal.texOffs(10, 8).addBox(0.0F, -6.0F, 2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);

		fin_pectoral_r = new ModelRenderer(this);
		fin_pectoral_r.setPos(1.5F, 24.0F, -1.5F);
		setRotationAngle(fin_pectoral_r, 0.5918F, 0.7285F, 0.0F);
		fin_pectoral_r.texOffs(16, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);

		fin_pectoral_l = new ModelRenderer(this);
		fin_pectoral_l.setPos(-1.5F, 24.0F, -1.5F);
		setRotationAngle(fin_pectoral_l, 0.5918F, -0.7285F, 0.0F);
		fin_pectoral_l.texOffs(16, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){	
        ImmutableList.of(this.head, this.chest, this.fin_anal, this.fin_caudal, this.body, this.fin_dorsal, this.fin_pectoral_r, this.fin_pectoral_l).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        });
	}
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
	      float f = 1.0F;
	      if (!entity.isInWater()) {
	         f = 1.5F;
	      }

	      this.fin_pectoral_r.yRot = 0.7285F - f * 0.25F * MathHelper.sin(0.6F * ageInTicks);
	      this.fin_pectoral_l.yRot = -0.7285F - f * 0.25F * MathHelper.sin(0.6F * ageInTicks + 0.3F * (float)Math.PI);
	      this.fin_caudal.yRot = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
	}

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}