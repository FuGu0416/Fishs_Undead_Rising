package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelVespaCocoon - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class VespaCocoonModel<T extends VespaCocoonEntity> extends FURBaseModel<T> {
    public ModelRenderer Cocoon_base;
    public ModelRenderer Cocoon_tangle0;
    public ModelRenderer Cocoon_tangle1;
    public ModelRenderer Cocoon_top;

    public VespaCocoonModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Cocoon_tangle1 = new ModelRenderer(this, 0, -4);
        this.Cocoon_tangle1.setPos(0.0F, 24.0F, 0.0F);
        this.Cocoon_tangle1.addBox(0.0F, -4.0F, -12.0F, 0, 4, 24, 0.0F);
        this.setRotateAngle(Cocoon_tangle1, 0.0F, -0.7853981633974483F, 0.0F);
        this.Cocoon_top = new ModelRenderer(this, 34, 0);
        this.Cocoon_top.setPos(0.0F, -8.0F, 0.0F);
        this.Cocoon_top.addBox(-3.0F, -2.0F, -3.0F, 6, 2, 6, 0.0F);
        this.Cocoon_tangle0 = new ModelRenderer(this, 0, -4);
        this.Cocoon_tangle0.setPos(0.0F, 24.0F, 0.0F);
        this.Cocoon_tangle0.addBox(0.0F, -4.0F, -12.0F, 0, 4, 24, 0.0F);
        this.setRotateAngle(Cocoon_tangle0, 0.0F, 0.7853981633974483F, 0.0F);
        this.Cocoon_base = new ModelRenderer(this, 0, 0);
        this.Cocoon_base.setPos(0.0F, 24.0F, 0.0F);
        this.Cocoon_base.addBox(-4.0F, -8.0F, -4.0F, 8, 10, 8, 0.0F);
        this.Cocoon_base.addChild(this.Cocoon_top);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Cocoon_base, this.Cocoon_tangle0, this.Cocoon_tangle1).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	
    }
}
