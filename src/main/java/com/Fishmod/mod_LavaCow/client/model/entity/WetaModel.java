package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.WetaEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelWeta - Fish0416
 * Created using Tabula 7.1.0
 */
public class WetaModel<T extends WetaEntity> extends FURBaseModel<T> implements IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Abdomen;
    public ModelRenderer Head;
    public ModelRenderer Leg2_Seg0_l;
    public ModelRenderer Leg2_Seg0_r;
    public ModelRenderer Leg1_Seg0_l;
    public ModelRenderer Leg0_Seg0_l;
    public ModelRenderer Leg1_Seg0_r;
    public ModelRenderer Leg0_Seg0_r;
    public ModelRenderer Stinger;
    public ModelRenderer Antenna_Seg0_l;
    public ModelRenderer Antenna_Seg0_r;
    public ModelRenderer Antenna_Seg1_l;
    public ModelRenderer Antenna_Seg1_r;
    public ModelRenderer Jaw_l;
    public ModelRenderer Jaw_r;
    public ModelRenderer Leg2_Seg1_l;
    public ModelRenderer Leg2_Seg2_l;
    public ModelRenderer Leg2_Seg1_r;
    public ModelRenderer Leg2_Seg2_r;
    public ModelRenderer Leg1_Seg1_l;
    public ModelRenderer Leg0_Seg1_l;
    public ModelRenderer Leg1_Seg1_r;
    public ModelRenderer Leg0_Seg1_r;

    public WetaModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Leg2_Seg0_r = new ModelRenderer(this, 36, 0);
        this.Leg2_Seg0_r.setPos(-2.0F, 2.0F, 1.1F);
        this.Leg2_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
        this.setRotateAngle(Leg2_Seg0_r, 0.5918411493512771F, -0.40980330836826856F, 0.0F);
        this.Leg2_Seg1_r = new ModelRenderer(this, 41, 9);
        this.Leg2_Seg1_r.setPos(0.0F, 0.0F, 6.0F);
        this.Leg2_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg0_Seg0_r = new ModelRenderer(this, 18, 0);
        this.Leg0_Seg0_r.setPos(-2.0F, 2.0F, -1.0F);
        this.Leg0_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg0_Seg0_r, 0.5009094953223726F, -2.0488420089161434F, 0.0F);
        this.Antenna_Seg0_r = new ModelRenderer(this, 17, 19);
        this.Antenna_Seg0_r.setPos(-1.0F, -2.0F, -4.0F);
        this.Antenna_Seg0_r.addBox(-0.5F, 0.0F, -4.0F, 1, 0, 4, 0.0F);
        this.setRotateAngle(Antenna_Seg0_r, -1.2292353921796064F, 0.0F, 0.0F);
        this.Antenna_Seg1_r = new ModelRenderer(this, 15, 13);
        this.Antenna_Seg1_r.setPos(0.0F, 0.0F, -4.0F);
        this.Antenna_Seg1_r.addBox(-0.5F, 0.0F, -6.0F, 1, 0, 6, 0.0F);
        this.setRotateAngle(Antenna_Seg1_r, 0.5462880558742251F, 0.0F, 0.0F);
        this.Stinger = new ModelRenderer(this, 9, 0);
        this.Stinger.setPos(0.0F, 2.0F, 8.0F);
        this.Stinger.addBox(-0.5F, 0.0F, 0.0F, 1, 0, 4, 0.0F);
        this.setRotateAngle(Stinger, 0.31869712141416456F, 0.0F, 0.0F);
        this.Leg2_Seg1_l = new ModelRenderer(this, 41, 9);
        this.Leg2_Seg1_l.mirror = true;
        this.Leg2_Seg1_l.setPos(0.0F, 0.0F, 6.0F);
        this.Leg2_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.5F, -2.0F);
        this.Head.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.Antenna_Seg0_l = new ModelRenderer(this, 17, 19);
        this.Antenna_Seg0_l.mirror = true;
        this.Antenna_Seg0_l.setPos(1.0F, -2.0F, -4.0F);
        this.Antenna_Seg0_l.addBox(-0.5F, 0.0F, -4.0F, 1, 0, 4, 0.0F);
        this.setRotateAngle(Antenna_Seg0_l, -1.2292353921796064F, 0.0F, 0.0F);
        this.Antenna_Seg1_l = new ModelRenderer(this, 15, 13);
        this.Antenna_Seg1_l.mirror = true;
        this.Antenna_Seg1_l.setPos(0.0F, 0.0F, -4.0F);
        this.Antenna_Seg1_l.addBox(-0.5F, 0.0F, -6.0F, 1, 0, 6, 0.0F);
        this.setRotateAngle(Antenna_Seg1_l, 0.5462880558742251F, 0.0F, 0.0F);
        this.Leg0_Seg1_l = new ModelRenderer(this, 20, 5);
        this.Leg0_Seg1_l.mirror = true;
        this.Leg0_Seg1_l.setPos(0.0F, 0.0F, 3.0F);
        this.Leg0_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 6, 1, 0.0F);
        this.Leg0_Seg0_l = new ModelRenderer(this, 18, 0);
        this.Leg0_Seg0_l.mirror = true;
        this.Leg0_Seg0_l.setPos(2.0F, 2.0F, -1.0F);
        this.Leg0_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg0_Seg0_l, 0.5009094953223726F, 2.0488420089161434F, 0.0F);
        this.Leg1_Seg0_r = new ModelRenderer(this, 27, 0);
        this.Leg1_Seg0_r.setPos(-2.0F, 2.0F, 0.0F);
        this.Leg1_Seg0_r.addBox(-1.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg1_Seg0_r, 0.18203784098300857F, -1.2292353921796064F, 0.0F);
        this.Jaw_l = new ModelRenderer(this, 13, 9);
        this.Jaw_l.mirror = true;
        this.Jaw_l.setPos(1.9F, 1.2F, -4.0F);
        this.Jaw_l.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Jaw_l, 0.0F, -0.7740535232594852F, -0.5462880558742251F);
        this.Leg0_Seg1_r = new ModelRenderer(this, 20, 5);
        this.Leg0_Seg1_r.setPos(0.0F, 0.0F, 3.0F);
        this.Leg0_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 6, 1, 0.0F);
        this.Leg1_Seg0_l = new ModelRenderer(this, 27, 0);
        this.Leg1_Seg0_l.mirror = true;
        this.Leg1_Seg0_l.setPos(2.0F, 2.0F, 0.0F);
        this.Leg1_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(Leg1_Seg0_l, 0.18203784098300857F, 1.2292353921796064F, 0.0F);
        this.Jaw_r = new ModelRenderer(this, 13, 9);
        this.Jaw_r.setPos(-1.9F, 1.2F, -4.0F);
        this.Jaw_r.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(Jaw_r, 0.0F, 0.7740535232594852F, 0.5462880558742251F);
        this.Abdomen = new ModelRenderer(this, 0, 18);
        this.Abdomen.setPos(0.0F, 0.0F, 1.5F);
        this.Abdomen.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 8, 0.0F);
        this.setRotateAngle(Abdomen, -0.18203784098300857F, 0.0F, 0.0F);
        this.Leg2_Seg0_l = new ModelRenderer(this, 36, 0);
        this.Leg2_Seg0_l.mirror = true;
        this.Leg2_Seg0_l.setPos(2.0F, 2.0F, 1.1F);
        this.Leg2_Seg0_l.addBox(0.0F, -1.0F, 0.0F, 1, 2, 6, 0.0F);
        this.setRotateAngle(Leg2_Seg0_l, 0.5918411493512771F, 0.40980330836826856F, 0.0F);
        this.Leg1_Seg1_r = new ModelRenderer(this, 29, 5);
        this.Leg1_Seg1_r.setPos(0.0F, 0.0F, 3.0F);
        this.Leg1_Seg1_r.addBox(-1.0F, -1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 8);
        this.Body_base.setPos(0.0F, 20.0F, -2.5F);
        this.Body_base.addBox(-2.0F, -2.5F, -2.0F, 4, 5, 4, 0.0F);
        this.Leg2_Seg2_l = new ModelRenderer(this, 41, 19);
        this.Leg2_Seg2_l.mirror = true;
        this.Leg2_Seg2_l.setPos(0.0F, 0.0F, 1.0F);
        this.Leg2_Seg2_l.addBox(0.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg2_Seg2_r = new ModelRenderer(this, 41, 19);
        this.Leg2_Seg2_r.setPos(0.0F, 0.0F, 1.0F);
        this.Leg2_Seg2_r.addBox(-1.0F, -1.0F, 0.0F, 1, 8, 1, 0.0F);
        this.Leg1_Seg1_l = new ModelRenderer(this, 29, 5);
        this.Leg1_Seg1_l.mirror = true;
        this.Leg1_Seg1_l.setPos(0.0F, 0.0F, 3.0F);
        this.Leg1_Seg1_l.addBox(0.0F, -1.0F, 0.0F, 1, 4, 1, 0.0F);
        this.Body_base.addChild(this.Leg2_Seg0_r);
        this.Leg2_Seg0_r.addChild(this.Leg2_Seg1_r);
        this.Body_base.addChild(this.Leg0_Seg0_r);
        this.Head.addChild(this.Antenna_Seg0_l);
        this.Abdomen.addChild(this.Stinger);
        this.Leg2_Seg0_l.addChild(this.Leg2_Seg1_l);
        this.Body_base.addChild(this.Head);
        this.Head.addChild(this.Antenna_Seg0_r);
        this.Leg0_Seg0_l.addChild(this.Leg0_Seg1_l);
        this.Body_base.addChild(this.Leg0_Seg0_l);
        this.Body_base.addChild(this.Leg1_Seg0_r);
        this.Head.addChild(this.Jaw_l);
        this.Leg0_Seg0_r.addChild(this.Leg0_Seg1_r);
        this.Body_base.addChild(this.Leg1_Seg0_l);
        this.Head.addChild(this.Jaw_r);
        this.Body_base.addChild(this.Abdomen);
        this.Body_base.addChild(this.Leg2_Seg0_l);
        this.Leg1_Seg0_r.addChild(this.Leg1_Seg1_r);
        this.Leg2_Seg1_l.addChild(this.Leg2_Seg2_l);
        this.Leg2_Seg1_r.addChild(this.Leg2_Seg2_r);
        this.Leg1_Seg0_l.addChild(this.Leg1_Seg1_l);
        this.Antenna_Seg0_l.addChild(this.Antenna_Seg1_l);
        this.Antenna_Seg0_r.addChild(this.Antenna_Seg1_r);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	float f = (entityIn.getSkin() == 2) ? 0.5F : 1.0F;
    	
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.Body_base.y = 20.0F - 0.5F * MathHelper.sin(limbSwing * 1.3F);
    	
    	this.SwingY_Sin(this.Jaw_l, -0.7740535232594852F, ageInTicks, 0.125F, 0.4F, true, 0.0F);
    	this.SwingY_Sin(this.Jaw_r, 0.7740535232594852F, ageInTicks, 0.125F, 0.4F, false, 0.0F);
    	
    	this.SwingX_Sin(this.Antenna_Seg0_l, -1.2292353921796064F, ageInTicks, 0.4F, 0.41F * f, false, 0.0F);
    	this.SwingX_Sin(this.Antenna_Seg0_r, -1.2292353921796064F, ageInTicks, 0.4F, 0.32F * f, false, 0.25F * (float)Math.PI);
    	this.SwingX_Sin(this.Antenna_Seg1_l, 0.5462880558742251F, ageInTicks, 0.4F, 0.41F * f, false, 0.0F);
    	this.SwingX_Sin(this.Antenna_Seg1_r, 0.5462880558742251F, ageInTicks, 0.4F, 0.32F * f, false, 0.25F * (float)Math.PI);
    	
    	this.SwingX_Sin(this.Leg0_Seg0_r, 0.5009094953223726F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.0F);
    	this.SwingX_Sin(this.Leg0_Seg0_l, 0.5009094953223726F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.5F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg1_Seg0_r, 0.18203784098300857F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.33F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg1_Seg0_l, 0.18203784098300857F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.83F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg0_r, 0.5918411493512771F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 0.66F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg0_l, 0.5918411493512771F, limbSwing, 0.5F * limbSwingAmount, 1.3F, false, 1.16F * (float)Math.PI);
    	
    	this.SwingY_Sin(this.Leg0_Seg0_r, -2.0488420089161434F, limbSwing, 0.3F * limbSwingAmount, 1.3F, true, 0.0F);
    	this.SwingY_Sin(this.Leg0_Seg0_l, 2.0488420089161434F, limbSwing, 0.3F * limbSwingAmount, 1.3F, false, 0.5F * (float)Math.PI);
    	this.SwingY_Sin(this.Leg1_Seg0_r, -1.2292353921796064F, limbSwing, 0.3F * limbSwingAmount, 1.3F, true, 0.33F * (float)Math.PI);
    	this.SwingY_Sin(this.Leg1_Seg0_l, 1.2292353921796064F, limbSwing, 0.3F * limbSwingAmount, 1.3F, false, 0.83F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg1_r, 0.0F, limbSwing, 0.8F * limbSwingAmount, 1.3F, false, 0.66F * (float)Math.PI);
    	this.SwingX_Sin(this.Leg2_Seg1_l, 0.0F, limbSwing, 0.8F * limbSwingAmount, 1.3F, false, 1.16F * (float)Math.PI);
    }

	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.Head;
	}
}
