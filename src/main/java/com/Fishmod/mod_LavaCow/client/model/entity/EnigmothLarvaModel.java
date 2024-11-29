package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Made with Blockbench 4.9.4
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
//Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class EnigmothLarvaModel<T extends EnigmothEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer base;
	private final ModelRenderer head;
	private final ModelRenderer thorax;
	private final ModelRenderer thorax_bristle_right;
	private final ModelRenderer thorax_bristle_left;
	private final ModelRenderer abdomen;
	private final ModelRenderer tail;

	public EnigmothLarvaModel() {
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 18.0F, -0.5F);
		
		head = new ModelRenderer(this);
		head.setPos(0.0F, 4.0F, -4.0F);
		base.addChild(head);
		head.texOffs(20, 8).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		thorax = new ModelRenderer(this);
		thorax.setPos(0.0F, 6.0F, 0.5F);
		base.addChild(thorax);
		thorax.texOffs(0, 0).addBox(-3.0F, -6.0F, -4.5F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		thorax_bristle_right = new ModelRenderer(this);
		thorax_bristle_right.setPos(-3.0F, -6.0F, -1.5F);
		thorax.addChild(thorax_bristle_right);
		setRotationAngle(thorax_bristle_right, 0.0F, 0.0F, -0.7854F);
		thorax_bristle_right.texOffs(18, -6).addBox(0.0F, -2.0F, -3.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);

		thorax_bristle_left = new ModelRenderer(this);
		thorax_bristle_left.setPos(3.0F, -6.0F, -1.5F);
		thorax.addChild(thorax_bristle_left);
		setRotationAngle(thorax_bristle_left, 0.0F, 0.0F, 0.7854F);
		thorax_bristle_left.texOffs(18, -6).addBox(0.0F, -2.0F, -3.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);

		abdomen = new ModelRenderer(this);
		abdomen.setPos(-0.5F, 3.0F, 2.0F);
		base.addChild(abdomen);
		abdomen.texOffs(0, 12).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 5.0F, 7.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.5F, 0.0F, 7.0F);
		abdomen.addChild(tail);
		setRotationAngle(tail, 0.5672F, 0.0F, 0.0F);
		tail.texOffs(-5, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	int i = entityIn.getSpellTicks();
    	float j = (1.0F - Math.abs(this.triangleWave(limbSwing, 13.0F)));
    	
    	if (i > 0) {
    		this.base.yRot = 0.2F * MathHelper.sin(0.88F * ageInTicks);
    		
    		this.head.xRot = -0.4363F;
    		this.head.yRot = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    		this.head.z = -4.0F;
    		
    		this.thorax.xRot = 0.0F;
    		this.thorax.y = 6.0F;    		
    		
    		this.abdomen.xRot = 0.6545F;
    		this.abdomen.y = 3.0F;
    		this.abdomen.yRot = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    		this.abdomen.z = 2.0F;
    	} else {
    		this.base.yRot = 0.0F;
    		
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw * 0.8F, headPitch * 0.8F);
    		this.head.z = -4.0F + (1.0F * j);
    		
    		this.Head_Looking(this.thorax, (float)Math.toRadians(17.5F) * j, 0.0F, netHeadYaw * 0.3F, headPitch * 0.3F);
    		this.thorax.y = 6.0F - (1.0F * j);
    		
    		this.abdomen.xRot = (float)Math.toRadians(-25.0F) * j;
    		this.abdomen.y = 3.0F - (2.0F * j);
    		this.abdomen.yRot = 0.0F;
    		this.abdomen.z = 2.0F - (1.0F * j);		
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}