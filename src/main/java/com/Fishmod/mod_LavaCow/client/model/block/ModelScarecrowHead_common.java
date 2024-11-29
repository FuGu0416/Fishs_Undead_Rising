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
 * ModelScarecrow - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelScarecrowHead_common extends EntityModel<Entity> {
    public ModelRenderer Head;
    public ModelRenderer Head_stem;
    public ModelRenderer Head_tooth;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_tooth;

    public ModelScarecrowHead_common() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Jaw_tooth = new ModelRenderer(this, 32, 38);
        this.Jaw_tooth.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw_tooth.addBox(-4.0F, -2.0F, -8.0F, 8, 2, 8, 0.0F);
        this.Head_stem = new ModelRenderer(this, 0, 0);
        this.Head_stem.setPos(0.0F, -4.0F, -4.0F);
        this.Head_stem.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Head_stem, -0.5462880558742251F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, -2.0F, 4.1F);
        this.Head.addBox(-4.0F, -5.0F, -8.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(Head, -0.6373942428283291F, 0.0F, 0.0F);
        this.Head_tooth = new ModelRenderer(this, 32, 25);
        this.Head_tooth.setPos(0.0F, 0.0F, 0.0F);
        this.Head_tooth.addBox(-4.0F, -1.0F, -8.0F, 8, 2, 8, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.6373942428283291F, 0.0F, 0.0F);
        this.Jaw.addChild(this.Jaw_tooth);
        this.Head.addChild(this.Head_stem);
        this.Head.addChild(this.Head_tooth);
        this.Head.addChild(this.Jaw);
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
