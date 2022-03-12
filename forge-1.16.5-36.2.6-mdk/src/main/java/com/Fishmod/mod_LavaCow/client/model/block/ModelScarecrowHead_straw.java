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
 * ModelScarecrow2 - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelScarecrowHead_straw extends EntityModel<Entity> {
    public ModelRenderer Head;
    public ModelRenderer Stem;

    public ModelScarecrowHead_straw() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Head = new ModelRenderer(this, 40, 49);
        this.Head.setPos(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.Stem = new ModelRenderer(this, 33, 48);
        this.Stem.setPos(0.0F, -7.6F, 2.3F);
        this.Stem.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Stem, -0.8196066167365371F, 0.0F, 0.0F);
        this.Head.addChild(this.Stem);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
    	ImmutableList.of(this.Head).forEach((modelRenderer) -> { 
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
