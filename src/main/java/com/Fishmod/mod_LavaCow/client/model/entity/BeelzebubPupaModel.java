package com.Fishmod.mod_LavaCow.client.model.entity;
// Made with Blockbench 4.6.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeelzebubPupaModel<T extends VespaCocoonEntity> extends FURBaseModel<T> {
	private final ModelRenderer base;
	private final ModelRenderer seg0;
	private final ModelRenderer seg1;
	private final ModelRenderer seg2;
	private final ModelRenderer seg3;

	public BeelzebubPupaModel() {
		texWidth = 128;
		texHeight = 64;

		this.base = new ModelRenderer(this);
		this.base.setPos(0.0F, 20.0F, -8.5F);
		this.base.texOffs(36, 12).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.seg0 = new ModelRenderer(this);
		this.seg0.setPos(0.0F, -1.0F, 8.0F);
		this.base.addChild(this.seg0);
		this.seg0.texOffs(0, 20).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 10.0F, 8.0F, 0.0F, false);

		this.seg1 = new ModelRenderer(this);
		this.seg1.setPos(0.0F, -1.0F, 8.0F);
		this.seg0.addChild(this.seg1);
		this.seg1.texOffs(0, 0).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 12.0F, 8.0F, 0.0F, false);

		this.seg2 = new ModelRenderer(this);
		this.seg2.setPos(0.0F, 1.0F, 8.0F);
		this.seg1.addChild(this.seg2);
		this.seg2.texOffs(32, 30).addBox(-5.0F, -5.0F, -4.0F, 10.0F, 10.0F, 8.0F, 0.0F, false);

		this.seg3 = new ModelRenderer(this);
		this.seg3.setPos(0.0F, 2.0F, 7.0F);
		this.seg2.addChild(this.seg3);
		this.seg3.texOffs(0, 38).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}
}