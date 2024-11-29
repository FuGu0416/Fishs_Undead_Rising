package com.Fishmod.mod_LavaCow.client.model.block;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelScarecrow3 - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelScarecrowHead_plague extends EntityModel<Entity> {
    public ModelRenderer Head3;
    public ModelRenderer Beak;
    public ModelRenderer Hat;
    public ModelRenderer Beak1;

    public ModelScarecrowHead_plague() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Hat = new ModelRenderer(this, 90, 0);
        this.Hat.setPos(0.0F, 0.0F, -6.0F);
        this.Hat.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 1, 0.0F);
        this.Beak1 = new ModelRenderer(this, 80, 28);
        this.Beak1.setPos(0.0F, 3.2F, -0.7F);
        this.Beak1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Beak1, 0.500909508638178F, 0.0F, 0.0F);
        this.Beak = new ModelRenderer(this, 64, 26);
        this.Beak.setPos(0.0F, 2.5F, -2.5F);
        this.Beak.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Beak, 0.2275909337942703F, 0.0F, 0.0F);
        this.Head3 = new ModelRenderer(this, 64, 10);
        this.Head3.setPos(0.0F, 0.0F, 3.0F);
        this.Head3.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Head3, -1.5707963267948966F, 0.0F, 0.0F);
        this.Head3.addChild(this.Hat);
        this.Beak.addChild(this.Beak1);
        this.Head3.addChild(this.Beak);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
    	ImmutableList.of(this.Head3).forEach((modelRenderer) -> { 
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
