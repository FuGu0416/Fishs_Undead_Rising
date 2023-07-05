package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Made with Blockbench 4.6.4
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
//Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class EnigmothLarvaModel<T extends EnigmothEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer base;
	private final ModelRenderer head;
	private final ModelRenderer abdomen;

	public EnigmothLarvaModel() {
		texWidth = 64;
		texHeight = 64;

		base = new ModelRenderer(this);
		base.setPos(0.0F, 18.0F, -0.5F);
		base.texOffs(0, 0).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 4.0F, -4.0F);
		base.addChild(head);
		head.texOffs(20, 8).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		abdomen = new ModelRenderer(this);
		abdomen.setPos(-0.5F, 3.0F, 2.0F);
		base.addChild(abdomen);
		abdomen.texOffs(0, 12).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 5.0F, 7.0F, 0.0F, false);
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
    	
    	if (i > 0) {
    		this.head.xRot = -0.4363F;
    		this.head.yRot = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    		
    		this.base.yRot = 0.2F * MathHelper.sin(0.88F * ageInTicks);
    		
    		this.abdomen.xRot = 0.6545F;
    		this.abdomen.yRot = 0.75F * MathHelper.sin(0.88F * ageInTicks);
    	} else {
    		this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
    		this.abdomen.z = 1.0F + (0.2F * MathHelper.sin(0.03F * ageInTicks)) + (1.0F * MathHelper.sin(limbSwingAmount));
    		this.abdomen.xRot = 0.0F;
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}