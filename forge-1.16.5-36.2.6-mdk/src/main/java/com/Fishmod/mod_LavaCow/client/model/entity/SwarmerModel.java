package com.Fishmod.mod_LavaCow.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelZombiePiranha - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class SwarmerModel<T extends Entity> extends FURBaseModel<T> implements IHasHead {
    public ModelRenderer Head;
    public ModelRenderer Body;
    public ModelRenderer Fin_dorsal;
    public ModelRenderer Fin_caudal;
    public ModelRenderer Body2;
    public ModelRenderer Fin_dorsal_1;
    public ModelRenderer Fin_pectoral_r;
    public ModelRenderer Fin_pectoral_l;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_teeth;

    public SwarmerModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Jaw_teeth = new ModelRenderer(this, 23, 10);
        this.Jaw_teeth.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw_teeth.addBox(-3.5F, -2.0F, -7.0F, 7, 2, 7, 0.0F);
        this.Fin_dorsal = new ModelRenderer(this, 16, 14);
        this.Fin_dorsal.setPos(0.0F, 16.0F, -2.0F);
        this.Fin_dorsal.addBox(-0.0F, 4.0F, 2.0F, 0, 3, 6, 0.0F);
        this.Body = new ModelRenderer(this, 0, 17);
        this.Body.setPos(0.0F, 16.0F, -2.0F);
        this.Body.addBox(-1.5F, -4.0F, 0.0F, 3, 8, 7, 0.0F);
        this.Fin_caudal = new ModelRenderer(this, 29, 15);
        this.Fin_caudal.setPos(0.0F, 16.0F, -2.0F);
        this.Fin_caudal.addBox(-0.0F, -4.0F, 9.0F, 0, 8, 5, 0.0F);
        this.Body2 = new ModelRenderer(this, 20, 24);
        this.Body2.setPos(0.0F, 16.0F, -2.0F);
        this.Body2.addBox(-1.0F, -3.0F, 7.0F, 2, 6, 2, 0.0F);
        this.Jaw = new ModelRenderer(this, 23, 0);
        this.Jaw.setPos(0.0F, 19.0F, -1.0F);
        this.Jaw.addBox(-3.5F, 0.0F, -7.0F, 7, 2, 7, 0.0F);
        this.setRotateAngle(Jaw, -0.36425021489121656F, 0.0F, 0.0F);
        this.Fin_pectoral_r = new ModelRenderer(this, 0, 11);
        this.Fin_pectoral_r.setPos(-1.5F, 16.0F, 1.5F);
        this.Fin_pectoral_r.addBox(-0.0F, -2.0F, 0.0F, 0, 2, 4, 0.0F);
        this.setRotateAngle(Fin_pectoral_r, 0.5918411493512771F, -0.7285004297824331F, 0.0F);
        this.Fin_pectoral_l = new ModelRenderer(this, 0, 11);
        this.Fin_pectoral_l.mirror = true;
        this.Fin_pectoral_l.setPos(1.5F, 16.0F, 1.5F);
        this.Fin_pectoral_l.addBox(-0.0F, -2.0F, 0.0F, 0, 2, 4, 0.0F);
        this.setRotateAngle(Fin_pectoral_l, 0.5918411493512771F, 0.7285004297824331F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 16.0F, -1.0F);
        this.Head.addBox(-2.5F, -4.0F, -3.0F, 5, 7, 6, 0.0F);
        this.setRotateAngle(Head, 0.5918411493512771F, 0.0F, 0.0F);
        this.Fin_dorsal_1 = new ModelRenderer(this, 8, 8);
        this.Fin_dorsal_1.setPos(0.0F, 16.0F, -2.0F);
        this.Fin_dorsal_1.addBox(-0.0F, -7.0F, 2.0F, 0, 3, 6, 0.0F);
        this.Jaw.addChild(this.Jaw_teeth);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	matrixStackIn.translate(0.0D, 0.2D, 0.0D);
    	ImmutableList.of(this.Fin_dorsal, this.Body, this.Fin_caudal, this.Body2, this.Jaw, this.Fin_pectoral_r, this.Fin_pectoral_l, this.Head, this.Fin_dorsal_1).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
   
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
       float f = 1.0F;
       if (!entityIn.isInWater()) {
          f = 1.5F;
       }

       this.Body2.yRot = -f * 0.15F * MathHelper.sin(0.6F * ageInTicks);
       this.Fin_caudal.yRot = this.Body2.yRot * 1.2F;
       this.Jaw.xRot = -0.36425021489121656F + (-f * 0.45F * MathHelper.sin(0.6F * ageInTicks));
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
