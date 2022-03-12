package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.BoneWormEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelBoneWorm - Fish0016054
 * Created using Tabula 7.1.0
 */
public class BoneWormModel<T extends BoneWormEntity> extends FURBaseModel<T> implements IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Body_seg0;
    public ModelRenderer Body_seg1;
    public ModelRenderer Body_seg2;
    public ModelRenderer Appendage_l0_seg0;
    public ModelRenderer Appendage_l1_seg0;
    public ModelRenderer Appendage_r0_seg0;
    public ModelRenderer Appendage_r1_seg0;
    public ModelRenderer Spine1;
    public ModelRenderer Head0;
    public ModelRenderer Head1;
    public ModelRenderer Head2;
    public ModelRenderer Spine0;
    public ModelRenderer Appendage_l0_seg1;
    public ModelRenderer Appendage_l1_seg1;
    public ModelRenderer Appendage_r0_seg1;
    public ModelRenderer Appendage_r1_seg1;

    public BoneWormModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Appendage_l0_seg0 = new ModelRenderer(this, 56, 12);
        this.Appendage_l0_seg0.mirror = true;
        this.Appendage_l0_seg0.setPos(5.0F, -7.0F, -3.0F);
        this.Appendage_l0_seg0.addBox(-1.0F, -2.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_l0_seg0, -1.2747884856566583F, -0.6829473363053812F, 0.0F);
        this.Body_seg2 = new ModelRenderer(this, 28, 16);
        this.Body_seg2.setPos(0.0F, -10.0F, 0.0F);
        this.Body_seg2.addBox(-4.0F, -6.0F, -6.0F, 8, 6, 6, 0.0F);
        this.setRotateAngle(Body_seg2, 0.6829473363053812F, 0.0F, 0.0F);
        this.Appendage_l1_seg0 = new ModelRenderer(this, 56, 12);
        this.Appendage_l1_seg0.mirror = true;
        this.Appendage_l1_seg0.setPos(5.0F, -4.0F, -3.0F);
        this.Appendage_l1_seg0.addBox(-1.0F, -2.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_l1_seg0, -0.9560913642424937F, -0.6829473363053812F, 0.0F);
        this.Spine1 = new ModelRenderer(this, 56, 12);
        this.Spine1.mirror = true;
        this.Spine1.setPos(0.0F, -7.9F, 0.0F);
        this.Spine1.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Spine1, 1.4570008595648662F, 0.0F, 0.0F);
        this.Appendage_r1_seg0 = new ModelRenderer(this, 56, 12);
        this.Appendage_r1_seg0.mirror = true;
        this.Appendage_r1_seg0.setPos(-5.0F, -4.0F, -3.0F);
        this.Appendage_r1_seg0.addBox(-1.0F, -2.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_r1_seg0, -0.9560913642424937F, 0.6829473363053812F, 0.0F);
        this.Appendage_r0_seg1 = new ModelRenderer(this, 56, 22);
        this.Appendage_r0_seg1.mirror = true;
        this.Appendage_r0_seg1.setPos(-1.0F, 6.0F, 0.0F);
        this.Appendage_r0_seg1.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_r0_seg1, 0.0F, 0.0F, -1.0927506446736497F);
        this.Appendage_l1_seg1 = new ModelRenderer(this, 56, 22);
        this.Appendage_l1_seg1.mirror = true;
        this.Appendage_l1_seg1.setPos(1.0F, 6.0F, 0.0F);
        this.Appendage_l1_seg1.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_l1_seg1, 0.0F, 0.0F, 1.0927506446736497F);
        this.Appendage_r1_seg1 = new ModelRenderer(this, 56, 22);
        this.Appendage_r1_seg1.mirror = true;
        this.Appendage_r1_seg1.setPos(-1.0F, 6.0F, 0.0F);
        this.Appendage_r1_seg1.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_r1_seg1, 0.0F, 0.0F, -1.0927506446736497F);
        this.Body_seg1 = new ModelRenderer(this, 0, 16);
        this.Body_seg1.setPos(0.0F, -10.0F, 0.0F);
        this.Body_seg1.addBox(-4.0F, -10.0F, -6.0F, 8, 10, 6, 0.0F);
        this.setRotateAngle(Body_seg1, 0.27314402793711257F, 0.0F, 0.0F);
        this.Appendage_r0_seg0 = new ModelRenderer(this, 56, 12);
        this.Appendage_r0_seg0.mirror = true;
        this.Appendage_r0_seg0.setPos(-5.0F, -7.0F, -3.0F);
        this.Appendage_r0_seg0.addBox(-1.0F, -2.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_r0_seg0, -1.2747884856566583F, 0.6829473363053812F, 0.0F);
        this.Head1 = new ModelRenderer(this, 32, 0);
        this.Head1.setPos(-3.0F, -5.0F, -4.0F);
        this.Head1.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Head1, -1.3658946726107624F, -0.36425021489121656F, -0.7285004297824331F);
        this.Spine0 = new ModelRenderer(this, 56, 12);
        this.Spine0.mirror = true;
        this.Spine0.setPos(0.0F, -2.2F, 0.0F);
        this.Spine0.addBox(-1.0F, 0.0F, -2.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Spine0, 1.2292353921796064F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 16);
        this.Body_base.setPos(0.0F, 24.0F, 0.0F);
        this.Body_base.addBox(-4.0F, -10.0F, -3.0F, 8, 10, 6, 0.0F);
        this.Head0 = new ModelRenderer(this, 0, 0);
        this.Head0.setPos(3.0F, -5.0F, -3.0F);
        this.Head0.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(Head0, -0.8651597102135892F, 0.0F, 0.27314402793711257F);
        this.Head2 = new ModelRenderer(this, 32, 0);
        this.Head2.setPos(-1.0F, -5.0F, 0.0F);
        this.Head2.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Head2, -2.276432943376204F, -0.22759093446006054F, 0.31869712141416456F);
        this.Body_seg0 = new ModelRenderer(this, 0, 16);
        this.Body_seg0.setPos(0.0F, -10.0F, 3.0F);
        this.Body_seg0.addBox(-4.0F, -10.0F, -6.0F, 8, 10, 6, 0.0F);
        this.setRotateAngle(Body_seg0, 0.27314402793711257F, 0.0F, 0.0F);
        this.Appendage_l0_seg1 = new ModelRenderer(this, 56, 22);
        this.Appendage_l0_seg1.mirror = true;
        this.Appendage_l0_seg1.setPos(1.0F, 6.0F, 0.0F);
        this.Appendage_l0_seg1.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Appendage_l0_seg1, 0.0F, 0.0F, 1.0927506446736497F);
        this.Body_seg1.addChild(this.Appendage_l0_seg0);
        this.Body_seg1.addChild(this.Body_seg2);
        this.Body_seg1.addChild(this.Appendage_l1_seg0);
        this.Body_seg1.addChild(this.Spine1);
        this.Body_seg1.addChild(this.Appendage_r1_seg0);
        this.Appendage_r0_seg0.addChild(this.Appendage_r0_seg1);
        this.Appendage_l1_seg0.addChild(this.Appendage_l1_seg1);
        this.Appendage_r1_seg0.addChild(this.Appendage_r1_seg1);
        this.Body_seg0.addChild(this.Body_seg1);
        this.Body_seg1.addChild(this.Appendage_r0_seg0);
        this.Body_seg2.addChild(this.Head1);
        this.Body_seg2.addChild(this.Spine0);
        this.Body_seg2.addChild(this.Head0);
        this.Body_seg2.addChild(this.Head2);
        this.Body_base.addChild(this.Body_seg0);
        this.Appendage_l0_seg0.addChild(this.Appendage_l0_seg1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { 
        this.Head_Looking(Head0, -0.8651597102135892F, 0.0F, netHeadYaw, headPitch);
        this.Head_Looking(Head1, -1.3658946726107624F, -0.36425021489121656F, netHeadYaw, headPitch);
        
    	float f = (limbSwingAmount < 0.02F) ? 1.0F : 1.5F;
    	float g = 0.1F;
    	int i = ((BoneWormEntity)entityIn).getAttackTimer(0);
    	int j = ((BoneWormEntity)entityIn).getAttackTimer(1);
    	int k = ((BoneWormEntity)entityIn).diggingTimer[0];
    	int l = ((BoneWormEntity)entityIn).diggingTimer[1];    	
    	
    	if (j > 0) {
    		this.setRotateAngle(Appendage_r0_seg0, -1.2747884856566583F, -0.18203784098300857F, 0.0F);
    		this.setRotateAngle(Appendage_l1_seg0, -1.3203415791337103F, 0.5009094953223726F, 0.0F);
    		this.setRotateAngle(Appendage_r0_seg1, 0.0F, 0.0F, -1.0927506446736497F);
    		this.setRotateAngle(Appendage_l0_seg0, -1.6390387005478748F, 0.31869712141416456F, 0.0F);
    		
    		if(j > 8) {
            	this.Body_seg0.xRot = GradientAnimation(0.27314402793711257F, 0.8196066167365371F, ((float)j - 8.0F)/8.0F);
            	this.Body_seg1.xRot = GradientAnimation(0.27314402793711257F, 0.9560913642424937F, ((float)j - 8.0F)/8.0F);
            	this.Body_seg2.xRot = GradientAnimation(0.6829473363053812F, 1.0471975511965976F, ((float)j - 8.0F)/8.0F);
    		}
    	}
    	else if (k > 0) {
    		f = 3.5F;
    		g = 0.5F;
        	this.Appendage_r0_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks));
        	this.Appendage_r0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks));
        	this.Appendage_r1_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_r1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_l0_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l1_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.75F * (float)Math.PI));
        	this.Appendage_l1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.75F * (float)Math.PI));
        	
        	this.Body_seg0.xRot = GradientAnimation(0.27314402793711257F, 0.0F, (float)k / 30.0F);
        	this.Body_seg1.xRot = GradientAnimation(0.27314402793711257F, 0.0F, (float)k / 30.0F);
        	this.Body_seg2.xRot = GradientAnimation(0.6829473363053812F, 0.0F, (float)k / 30.0F);  
    	}
    	else if (l > 0) {
    		f = 3.5F;
    		g = 0.5F;
        	this.Appendage_r0_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks));
        	this.Appendage_r0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks));
        	this.Appendage_r1_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_r1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_l0_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l1_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.75F * (float)Math.PI));
        	this.Appendage_l1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.75F * (float)Math.PI));
        	
        	this.Body_seg0.xRot = GradientAnimation(0.0F, 0.27314402793711257F, (float)l / 20.0F);
        	this.Body_seg1.xRot = GradientAnimation(0.0F, 0.27314402793711257F, (float)l / 20.0F);
        	this.Body_seg2.xRot = GradientAnimation(0.0F, 0.6829473363053812F, (float)l / 20.0F);  
    	}
    	else if (i > 0) {
    		this.Appendage_l0_seg0.xRot = GradientAnimation(-1.6845917940249266F, -1.6390387005478748F, (float)i / 15.0F);
    		this.Appendage_l0_seg0.yRot = GradientAnimation(-2.0943951023931953F, 0.31869712141416456F, (float)i / 15.0F);
    		this.Appendage_l1_seg0.yRot = GradientAnimation(-1.9577358219620393F, 0.5009094953223726F, (float)i / 15.0F);
    		
    		this.Appendage_r0_seg0.xRot = GradientAnimation(-1.6845917940249266F, -1.6390387005478748F, (float)i / 15.0F);
    		this.Appendage_r0_seg0.yRot = GradientAnimation(2.0943951023931953F, -0.31869712141416456F, (float)i / 15.0F);
    		this.Appendage_r1_seg0.yRot = GradientAnimation(1.9577358219620393F, -0.5009094953223726F, (float)i / 15.0F);
    		
        	this.Body_seg0.xRot = (i * -0.8F / 15.0F) + 0.27314402793711257F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));
        	this.Body_seg1.xRot = (i * -0.8F / 15.0F) + 0.27314402793711257F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));
        	this.Body_seg2.xRot = (i * -1.8F / 15.0F) + 0.6829473363053812F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));  
    	}
    	else {
    		g = 0.1F;
        	this.Appendage_r0_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks));
        	this.Appendage_r0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks));
        	this.Appendage_r1_seg0.yRot = 0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_r1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.25F * (float)Math.PI));
        	this.Appendage_l0_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l0_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.50F * (float)Math.PI));
        	this.Appendage_l1_seg0.yRot = -0.6829473363053812F + (f * -0.16F * MathHelper.sin(f * g * ageInTicks + 0.75F * (float)Math.PI));
        	this.Appendage_l1_seg1.yRot = (f * -0.25F * MathHelper.sin(g * ageInTicks + 0.75F * (float)Math.PI));
        	
        	this.Body_seg0.xRot = 0.27314402793711257F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));
        	this.Body_seg1.xRot = 0.27314402793711257F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));
        	this.Body_seg2.xRot = 0.6829473363053812F + (f * -0.07F * MathHelper.sin(0.03F * ageInTicks));  
    	}
    }

	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.Head0;
	}
}
