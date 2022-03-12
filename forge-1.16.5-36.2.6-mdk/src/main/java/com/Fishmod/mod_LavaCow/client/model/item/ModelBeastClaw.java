package com.Fishmod.mod_LavaCow.client.model.item;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelWendigo - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelBeastClaw extends EntityModel<Entity> {
    public ModelRenderer Palm_l;
    public ModelRenderer Claw_0_l;
    public ModelRenderer Claw_1_l;
    public ModelRenderer Claw_2_l;
    public ModelRenderer Claw_01_l;
    public ModelRenderer Claw_11_l;
    public ModelRenderer Claw_21_l;

    public ModelBeastClaw() {
    	this.texWidth = 128;
        this.texHeight = 128;
        this.Claw_1_l = new ModelRenderer(this, 24, 32);
        this.Claw_1_l.mirror = true;
        this.Claw_1_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_1_l.addBox(0.0F, -1.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_1_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Claw_0_l = new ModelRenderer(this, 24, 32);
        this.Claw_0_l.mirror = true;
        this.Claw_0_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_0_l.addBox(0.0F, -3.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_0_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Claw_11_l = new ModelRenderer(this, 32, 40);
        this.Claw_11_l.mirror = true;
        this.Claw_11_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_11_l.addBox(-2.0F, -1.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_11_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Palm_l = new ModelRenderer(this, 40, 46);
        this.Palm_l.mirror = true;
        this.Palm_l.setPos(0.0F, 0.0F, 0.0F);
        this.Palm_l.addBox(-2.0F, -3.0F, -6.0F, 4, 6, 6, 0.0F);
        this.setRotateAngle(Palm_l, 0.0F, 0.0F, -1.5707963267948966F);
        this.Claw_2_l = new ModelRenderer(this, 24, 32);
        this.Claw_2_l.mirror = true;
        this.Claw_2_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_2_l.addBox(0.0F, 1.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_2_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Claw_01_l = new ModelRenderer(this, 32, 40);
        this.Claw_01_l.mirror = true;
        this.Claw_01_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_01_l.addBox(-2.0F, -3.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_01_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Claw_21_l = new ModelRenderer(this, 32, 40);
        this.Claw_21_l.mirror = true;
        this.Claw_21_l.setPos(0.0F, 0.0F, -5.0F);
        this.Claw_21_l.addBox(-2.0F, 1.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_21_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Palm_l.addChild(this.Claw_1_l);
        this.Palm_l.addChild(this.Claw_0_l);
        this.Palm_l.addChild(this.Claw_11_l);
        this.Palm_l.addChild(this.Claw_2_l);
        this.Palm_l.addChild(this.Claw_01_l);
        this.Palm_l.addChild(this.Claw_21_l);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Palm_l).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
