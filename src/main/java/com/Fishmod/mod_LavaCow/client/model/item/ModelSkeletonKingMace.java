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
 * ModelSkeletonKingMace - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelSkeletonKingMace extends EntityModel<Entity> {
    public ModelRenderer weapon_handle0;
    public ModelRenderer weapon_handle1;
    public ModelRenderer weapon_handle1_1;
    public ModelRenderer weapon_base;
    public ModelRenderer weapon_horn_l;
    public ModelRenderer weapon_horn_r;

    public ModelSkeletonKingMace() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.weapon_horn_l = new ModelRenderer(this, 110, 52);
        this.weapon_horn_l.setPos(4.0F, 0.0F, -4.0F);
        this.weapon_horn_l.addBox(0.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_handle1_1 = new ModelRenderer(this, 66, 52);
        this.weapon_handle1_1.setPos(0.0F, 0.0F, -6.0F);
        this.weapon_handle1_1.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.weapon_base = new ModelRenderer(this, 96, 35);
        this.weapon_base.setPos(0.0F, 0.0F, -4.0F);
        this.weapon_base.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.weapon_handle1 = new ModelRenderer(this, 66, 43);
        this.weapon_handle1.setPos(0.0F, 0.0F, -15.0F);
        this.weapon_handle1.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.weapon_horn_r = new ModelRenderer(this, 110, 52);
        this.weapon_horn_r.mirror = true;
        this.weapon_horn_r.setPos(-4.0F, 0.0F, -4.0F);
        this.weapon_horn_r.addBox(-3.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_handle0 = new ModelRenderer(this, 66, 43);
        this.weapon_handle0.setPos(0.0F, 0.0F, 0.0F);
        this.weapon_handle0.addBox(-0.5F, -0.5F, -15.0F, 1, 1, 20, 0.0F);
        this.weapon_base.addChild(this.weapon_horn_l);
        this.weapon_handle1.addChild(this.weapon_handle1_1);
        this.weapon_handle1_1.addChild(this.weapon_base);
        this.weapon_handle0.addChild(this.weapon_handle1);
        this.weapon_base.addChild(this.weapon_horn_r);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.weapon_handle0).forEach((modelRenderer) -> { 
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
