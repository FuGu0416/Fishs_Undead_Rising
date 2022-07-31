package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.floating.BansheeEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelWraith - Fish0016054
 * Created using Tabula 7.1.0
 */
public class BansheeModel<T extends BansheeEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Body_lower_front;
    public ModelRenderer Body_lower_back;
    public ModelRenderer Body_lower_l;
    public ModelRenderer Body_lower_r;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Neck0;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_l_Seg2;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Arm_r_Seg2;
    public ModelRenderer Neck1;
    public ModelRenderer Head;
    public ModelRenderer Jaw0;
    public ModelRenderer Head_teeth;
    public ModelRenderer Hair_front;
    public ModelRenderer Hair_l;
    public ModelRenderer Hair_r;
    public ModelRenderer Hair_mid;
    public ModelRenderer Hair_back;
    public ModelRenderer Jaw1;
    public ModelRenderer Jaw1_teeth;

    public BansheeModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.Head_teeth = new ModelRenderer(this, 0, 15);
        this.Head_teeth.setPos(0.0F, 0.0F, 0.0F);
        this.Head_teeth.addBox(-3.5F, 3.0F, -7.5F, 7, 1, 4, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 56, 34);
        this.Arm_r_Seg1.setPos(-1.0F, 7.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Jaw0 = new ModelRenderer(this, 24, 0);
        this.Jaw0.setPos(0.0F, 3.0F, 0.0F);
        this.Jaw0.addBox(-3.5F, -3.5F, -3.0F, 7, 5, 3, 0.0F);
        this.setRotateAngle(Jaw0, 0.5918411493512771F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 19, 15);
        this.Body_base.setPos(0.0F, 10.0F, 1.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -3.0F, 8, 14, 6, 0.0F);
        this.Body_lower_l = new ModelRenderer(this, 34, 48);
        this.Body_lower_l.mirror = true;
        this.Body_lower_l.setPos(4.0F, -2.0F, 0.0F);
        this.Body_lower_l.addBox(-3.0F, 0.0F, 0.0F, 6, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_l, 0.22759093446006054F, 1.5707963267948966F, 0.0F);
        this.Body_lower_r = new ModelRenderer(this, 34, 48);
        this.Body_lower_r.setPos(-4.0F, -2.0F, 0.0F);
        this.Body_lower_r.addBox(-3.0F, 0.0F, 0.0F, 6, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_r, -0.22759093446006054F, 1.5707963267948966F, 0.0F);
        this.Hair_back = new ModelRenderer(this, 31, 37);
        this.Hair_back.mirror = true;
        this.Hair_back.setPos(0.0F, -3.0F, 0.0F);
        this.Hair_back.addBox(-4.0F, -10.0F, 0.0F, 8, 10, 0, 0.0F);
        this.setRotateAngle(Hair_back, -1.7756979809790308F, 0.0F, 0.0F);
        this.Jaw1 = new ModelRenderer(this, 40, 2);
        this.Jaw1.setPos(0.0F, -1.0F, 0.0F);
        this.Jaw1.addBox(-3.0F, 0.0F, -7.8F, 6, 2, 6, 0.0F);
        this.setRotateAngle(Jaw1, 0.091106186954104F, 0.0F, -0.045553093477052F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 56, 20);
        this.Arm_l_Seg1.setPos(1.0F, 7.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Hair_mid = new ModelRenderer(this, 31, 37);
        this.Hair_mid.mirror = true;
        this.Hair_mid.setPos(0.0F, -3.0F, -2.5F);
        this.Hair_mid.addBox(-4.0F, -10.0F, 0.0F, 8, 10, 0, 0.0F);
        this.setRotateAngle(Hair_mid, -1.3658946726107624F, 0.0F, 0.0F);
        this.Body_lower_front = new ModelRenderer(this, 48, 48);
        this.Body_lower_front.setPos(0.0F, -2.0F, -3.0F);
        this.Body_lower_front.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_front, -0.31869712141416456F, 0.0F, 0.0F);
        this.Hair_l = new ModelRenderer(this, 21, 48);
        this.Hair_l.mirror = true;
        this.Hair_l.setPos(4.0F, 0.1F, -4.0F);
        this.Hair_l.addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, 0.0F);
        this.setRotateAngle(Hair_l, -1.1838568316277536F, 0.0F, 1.5707963267948966F);
        this.Hair_front = new ModelRenderer(this, 31, 37);
        this.Hair_front.setPos(0.0F, -3.0F, -5.0F);
        this.Hair_front.addBox(-4.0F, -10.0F, 0.0F, 8, 10, 0, 0.0F);
        this.setRotateAngle(Hair_front, -0.9105382707654417F, 0.0F, 0.0F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setPos(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck1, -1.0471975511965976F, 0.045553093477052F, -0.18203784098300857F);
        this.Arm_r_Seg2 = new ModelRenderer(this, 0, 50);
        this.Arm_r_Seg2.setPos(-1.0F, 0.0F, 0.0F);
        this.Arm_r_Seg2.addBox(0.0F, 0.0F, 0.0F, 0, 8, 6, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 48, 36);
        this.Arm_r_Seg0.setPos(-5.0F, -2.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
        this.Hair_r = new ModelRenderer(this, 21, 48);
        this.Hair_r.setPos(-4.0F, 0.1F, -4.0F);
        this.Hair_r.addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, 0.0F);
        this.setRotateAngle(Hair_r, -1.1838568316277536F, 0.0F, -1.5707963267948966F);
        this.Body_waist = new ModelRenderer(this, 0, 45);
        this.Body_waist.setPos(0.0F, -3.2F, 0.0F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 7, 8, 3, 0.0F);
        this.setRotateAngle(Body_waist, 0.091106186954104F, 0.0F, -0.045553093477052F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8, 6, 8, 0.0F);
        this.Body_lower_back = new ModelRenderer(this, 48, 48);
        this.Body_lower_back.setPos(0.0F, -2.0F, 3.0F);
        this.Body_lower_back.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
        this.setRotateAngle(Body_lower_back, 0.27314402793711257F, 0.0F, 0.0F);
        this.Arm_l_Seg2 = new ModelRenderer(this, 0, 50);
        this.Arm_l_Seg2.mirror = true;
        this.Arm_l_Seg2.setPos(1.0F, 0.0F, 0.0F);
        this.Arm_l_Seg2.addBox(0.0F, 0.0F, 0.0F, 0, 8, 6, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 48, 22);
        this.Arm_l_Seg0.setPos(5.0F, -2.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
        this.Jaw1_teeth = new ModelRenderer(this, 0, 21);
        this.Jaw1_teeth.setPos(0.0F, -1.0F, 0.0F);
        this.Jaw1_teeth.addBox(-2.5F, 0.0F, -7.5F, 5, 1, 4, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 35);
        this.Body_chest.setPos(0.0F, -8.0F, -0.5F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 10, 5, 5, 0.0F);
        this.setRotateAngle(Body_chest, 0.5009094953223726F, 0.0F, 0.0F);
        this.Neck0 = new ModelRenderer(this, 0, 26);
        this.Neck0.setPos(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Neck0, 0.36425021489121656F, 0.0F, 0.0F);
        this.Head.addChild(this.Head_teeth);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Head.addChild(this.Jaw0);
        this.Body_base.addChild(this.Body_lower_l);
        this.Body_base.addChild(this.Body_lower_r);
        this.Head.addChild(this.Hair_back);
        this.Jaw0.addChild(this.Jaw1);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Head.addChild(this.Hair_mid);
        this.Body_base.addChild(this.Body_lower_front);
        this.Head.addChild(this.Hair_l);
        this.Head.addChild(this.Hair_front);
        this.Neck0.addChild(this.Neck1);
        this.Arm_r_Seg1.addChild(this.Arm_r_Seg2);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Head.addChild(this.Hair_r);
        this.Body_base.addChild(this.Body_waist);
        this.Neck1.addChild(this.Head);
        this.Body_base.addChild(this.Body_lower_back);
        this.Arm_l_Seg1.addChild(this.Arm_l_Seg2);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Jaw1.addChild(this.Jaw1_teeth);
        this.Body_waist.addChild(this.Body_chest);
        this.Body_chest.addChild(this.Neck0);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, 0.85F);
        });
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { 
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	SwingX_Sin(this.Hair_front, -0.9105382707654417F, ageInTicks, 0.21F, 0.06F, false, 0.0F);
    	SwingX_Sin(this.Hair_mid, -1.3658946726107624F, ageInTicks, 0.23F, 0.08F, false, 0.25F * (float)Math.PI);
    	SwingX_Sin(this.Hair_back, -1.7756979809790308F, ageInTicks, 0.2F, 0.06F, false, 0.0F);
    	SwingX_Sin(this.Hair_r, -1.1838568316277536F, ageInTicks, 0.17F, 0.09F, false, 0.25F * (float)Math.PI);
    	SwingX_Sin(this.Hair_l, -1.1838568316277536F, ageInTicks, 0.19F, 0.07F, true, 0.0F);
    	this.SwingX_Sin(this.Jaw1, 0.091106186954104F, ageInTicks, -0.03F, 0.06F, false, 0.0F);
    	
    	if(limbSwingAmount < 0.08F && this.Body_base.xRot > 0.0F) {
    		this.Body_base.xRot -= 0.005F;
    		this.Head.xRot += 0.005F;
    	}
    	else if(limbSwingAmount >= 0.08F && this.Body_base.xRot < 0.35F) {
    		this.Body_base.xRot += 0.005F;
    		this.Head.xRot -= 0.005F;
    	}
    		
    	SwingX_Sin(this.Body_lower_front, -0.31869712141416456F, ageInTicks, 0.2F, 0.08F, true, 0.0F);
    	SwingZ_Sin(this.Body_lower_r, 0.0F, ageInTicks, 0.16F, 0.07F, false, 0.25F * (float)Math.PI);
    	SwingZ_Sin(this.Body_lower_l, 0.0F, ageInTicks, 0.18F, 0.09F, true, 0.45F * (float)Math.PI);
    	SwingX_Sin(this.Body_lower_back, 0.27314402793711257F, ageInTicks, 0.25F, 0.06F, false, 0.70F * (float)Math.PI);
    	
    	SwingY_Sin(this.Arm_r_Seg2, 0.0F, ageInTicks, 0.22F, 0.08F, false, 0.0F);
    	SwingY_Sin(this.Arm_l_Seg2, 0.0F, ageInTicks, 0.18F, 0.06F, true, 0.0F);

    	boolean aggressive = entityIn.isAggressive();
    	float i = (float)((BansheeEntity) entityIn).getAttackTimer() / 20.0F;
    	float j = (float)((BansheeEntity) entityIn).getSpellTicks() / 30.0F;
    	if(((BansheeEntity) entityIn).isSpellcasting()) {
    		Arm_r_Seg0.xRot = GradientAnimation(1.1046188995661776F, -0.6155776351678833F, j);
    		Arm_l_Seg0.xRot = GradientAnimation(1.1046188995661776F, -0.6155776351678833F, j);
    		Arm_r_Seg0.yRot = 2.111673824703684F;
    		Arm_l_Seg0.yRot = -2.111673824703684F;
    		Arm_r_Seg0.zRot = 1.5627678282146893F;
    		Arm_l_Seg0.zRot = -1.5627678282146893F;
    		if(this.Body_base.y > 5.0F)
    			this.Body_base.y -= 0.17F;
    	} else if (i > 0) {
    		this.Jaw0.xRot = GradientAnimation((float)Math.toRadians(57.39D), (float)Math.toRadians(18.26D), i);
    		this.Body_chest.xRot = GradientAnimation((float)Math.toRadians(-2.61D), (float)Math.toRadians(44.35D), i);
    		this.Arm_r_Seg0.xRot = GradientAnimation((float)Math.toRadians(67.83D), (float)Math.toRadians(-86.09D), i);
    		this.Arm_r_Seg0.yRot = GradientAnimation((float)Math.toRadians(101.74D), (float)Math.toRadians(-13.04D), i);
    		this.Arm_r_Seg0.zRot = GradientAnimation((float)Math.toRadians(106.96D), (float)Math.toRadians(65.22D), i);
    		this.Arm_l_Seg0.xRot = GradientAnimation((float)Math.toRadians(67.83D), (float)Math.toRadians(-86.09D), i);
    		this.Arm_l_Seg0.yRot = GradientAnimation((float)Math.toRadians(-101.74D), (float)Math.toRadians(13.04D), i);
    		this.Arm_l_Seg0.zRot = GradientAnimation((float)Math.toRadians(-106.96D), (float)Math.toRadians(-65.22D), i);
    	} else if(aggressive) {
    		this.setRotateAngle(Arm_r_Seg0, -1.9577358219620393F, 0.0F, 0.091106186954104F);
    		this.setRotateAngle(Arm_l_Seg0, -1.8212510744560826F, 0.0F, 0.045553093477052F);
    		if(this.Body_base.y < 10.0F)
    			this.Body_base.y += 0.17F;
    		else
    			this.Body_base.y = 10.0F + MathHelper.sin(0.08F * ageInTicks);
    	} else {
    		this.SwingX_Sin(this.Jaw0, 0.5918411493512771F, ageInTicks, -0.04F, 0.06F, false, 0.0F);
    		this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
    		if(this.Body_base.y < 10.0F)
    			this.Body_base.y += 0.17F;
    		else
    			this.Body_base.y = 10.0F + MathHelper.sin(0.08F * ageInTicks);
    	}
    }

	@Override
	public net.minecraft.client.renderer.model.ModelRenderer getHead() {
		return this.Head;
	}

}
