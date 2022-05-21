package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.UndertakerEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelUndertaker - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class UndertakerModel<T extends UndertakerEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Leg_r_Seg1;
    public ModelRenderer Leg_r_Seg2;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_l_Seg2;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Neck;
    public ModelRenderer Spine0;
    public ModelRenderer Spine1;
    public ModelRenderer Spine2;
    public ModelRenderer Coffin;
    public ModelRenderer Cloak;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_l_Seg2;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Arm_r_Seg2;
    public ModelRenderer Head;
    public ModelRenderer Head_wear;
    public ModelRenderer Jaw;
    public ModelRenderer Tooth_l;
    public ModelRenderer Tooth_r;

    public UndertakerModel() {
        this.texWidth = 128;
        this.texHeight = 128;
        this.Coffin = new ModelRenderer(this, 0, 65);
        this.Coffin.setPos(0.0F, -6.5F, 4.5F);
        this.Coffin.addBox(-5.0F, -6.0F, -11.0F, 10.0F, 6.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Coffin, -1.5707963267948966F, 0.0F, -0.3127630032889644F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 32, 48);
        this.Arm_r_Seg0.setPos(-8.0F, -7.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-6.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, -0.12269665007704787F, 0.0F, 0.0F);
        this.Tooth_r = new ModelRenderer(this, 0, 0);
        this.Tooth_r.setPos(-3.4F, 0.0F, -7.4F);
        this.Tooth_r.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 32, 48);
        this.Arm_l_Seg0.mirror = true;
        this.Arm_l_Seg0.setPos(8.0F, -7.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.12269665007704787F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 68, 0);
        this.Leg_l_Seg0.mirror = true;
        this.Leg_l_Seg0.setPos(4.0F, -1.2F, 0.1F);
        this.Leg_l_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.4098033003787853F, 0.0F, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 28);
        this.Body_chest.setPos(0.0F, -7.0F, 0.0F);
        this.Body_chest.addBox(-8.0F, -12.0F, -3.0F, 16.0F, 12.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body_chest, 0.45535640450848164F, 0.0F, -0.04555309164612875F);
        this.Leg_l_Seg2 = new ModelRenderer(this, 72, 20);
        this.Leg_l_Seg2.mirror = true;
        this.Leg_l_Seg2.setPos(0.0F, 3.9F, 1.8F);
        this.Leg_l_Seg2.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l_Seg2, -0.18203784630933073F, 0.0F, 0.0F);
        this.Cloak = new ModelRenderer(this, 56, 48);
        this.Cloak.setPos(0.0F, 0.0F, 5.0F);
        this.Cloak.addBox(-8.0F, 0.0F, -7.0F, 16.0F, 15.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Cloak, -0.35185837453889574F, 0.0F, 0.0F);
        this.Spine1 = new ModelRenderer(this, 0, 0);
        this.Spine1.setPos(-0.3F, -11.8F, 0.6F);
        this.Spine1.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Spine1, -0.0911061832922575F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 16);
        this.Neck.setPos(0.0F, -8.0F, -1.5F);
        this.Neck.addBox(-4.5F, -5.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Neck, 1.7756979436947757F, 0.0F, 0.0F);
        this.Leg_r_Seg0 = new ModelRenderer(this, 68, 0);
        this.Leg_r_Seg0.setPos(-4.0F, -1.2F, 0.1F);
        this.Leg_r_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.4098033003787853F, 0.0F, 0.0F);
        this.Spine2 = new ModelRenderer(this, 0, 0);
        this.Spine2.setPos(-0.2F, -11.5F, 3.1F);
        this.Spine2.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Spine2, -0.0911061832922575F, 0.0F, 0.0F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 74, 11);
        this.Leg_l_Seg1.mirror = true;
        this.Leg_l_Seg1.setPos(0.0F, 6.0F, -2.0F);
        this.Leg_l_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.591841146688116F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, -4.3F, -1.2F);
        this.Head.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head, -1.9577357900041064F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 52);
        this.Body_base.setPos(0.0F, 12.0F, 1.3F);
        this.Body_base.addBox(-6.0F, -8.0F, -2.0F, 12.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Spine0 = new ModelRenderer(this, 0, 0);
        this.Spine0.setPos(-0.5F, -11.5F, -1.6F);
        this.Spine0.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Spine0, -0.0911061832922575F, 0.0F, 0.0F);
        this.Arm_r_Seg2 = new ModelRenderer(this, 69, 31);
        this.Arm_r_Seg2.setPos(0.0F, 12.0F, 2.5F);
        this.Arm_r_Seg2.addBox(-2.5F, 0.0F, -5.0F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Seg2, -0.5260422258984465F, 0.0F, 0.0F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 74, 11);
        this.Leg_r_Seg1.setPos(0.0F, 6.0F, -2.0F);
        this.Leg_r_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.591841146688116F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 84, 0);
        this.Jaw.setPos(0.0F, 2.0F, 0.0F);
        this.Jaw.addBox(-4.0F, -1.0F, -8.0F, 8.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Jaw, 0.4098033003787853F, 0.0F, 0.18203784630933073F);
        this.Leg_r_Seg2 = new ModelRenderer(this, 72, 20);
        this.Leg_r_Seg2.setPos(0.0F, 3.9F, 1.8F);
        this.Leg_r_Seg2.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r_Seg2, -0.18203784630933073F, 0.0F, 0.0F);
        this.Head_wear = new ModelRenderer(this, 32, 0);
        this.Head_wear.setPos(0.0F, -4.3F, -1.2F);
        this.Head_wear.addBox(-4.5F, -4.5F, -8.5F, 9.0F, 10.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head_wear, -1.9577357900041064F, 0.0F, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 49, 31);
        this.Arm_r_Seg1.setPos(-3.0F, 2.5F, 0.0F);
        this.Arm_r_Seg1.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, 0.0F, -0.18203784630933073F, 0.10000736647217022F);
        this.Arm_l_Seg2 = new ModelRenderer(this, 69, 31);
        this.Arm_l_Seg2.mirror = true;
        this.Arm_l_Seg2.setPos(0.0F, 12.0F, 2.5F);
        this.Arm_l_Seg2.addBox(-2.5F, 0.0F, -5.0F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Seg2, -0.5260422258984465F, 0.0F, 0.0F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 89, 31);
        this.Arm_l_Seg1.setPos(3.0F, 2.5F, 0.0F);
        this.Arm_l_Seg1.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, 0.0F, 0.18203784630933073F, -0.10000736647217022F);
        this.Tooth_l = new ModelRenderer(this, 0, 0);
        this.Tooth_l.setPos(3.4F, 0.0F, -7.4F);
        this.Tooth_l.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Body_chest.addChild(this.Coffin);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Jaw.addChild(this.Tooth_r);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Body_base.addChild(this.Body_chest);
        this.Leg_l_Seg1.addChild(this.Leg_l_Seg2);
        this.Body_chest.addChild(this.Cloak);
        this.Body_chest.addChild(this.Spine1);
        this.Body_chest.addChild(this.Neck);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Body_chest.addChild(this.Spine2);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Neck.addChild(this.Head);
        this.Body_chest.addChild(this.Spine0);
        this.Arm_r_Seg1.addChild(this.Arm_r_Seg2);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Head.addChild(this.Jaw);
        this.Leg_r_Seg1.addChild(this.Leg_r_Seg2);
        this.Neck.addChild(this.Head_wear);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Arm_l_Seg1.addChild(this.Arm_l_Seg2);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Jaw.addChild(this.Tooth_l);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void translateToHand(HandSide side, MatrixStack p_225599_2_) {
    	this.Body_base.translateAndRotate(p_225599_2_);
    	this.Body_chest.translateAndRotate(p_225599_2_);
    	this.Arm_l_Seg0.translateAndRotate(p_225599_2_);
    	this.Arm_l_Seg1.translateAndRotate(p_225599_2_);
    	this.Arm_l_Seg2.translateAndRotate(p_225599_2_);
    }
    
    @Override
    protected ModelRenderer getArm(HandSide side) {
    	return side == HandSide.LEFT ? this.Arm_l_Seg0 : this.Arm_r_Seg0;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	float i = (float)entityIn.getAttackTimer();
    	float j = (float)entityIn.getSpellTicks() / 30.0F;
    	
    	this.Head_Looking(this.Neck, 1.7756979809790308F, 0.0F, netHeadYaw, headPitch);
    	this.SwingX_Sin(this.Jaw, 0.40980330836826856F, ageInTicks, -0.07F, 0.03F, false, 0.0F); 	
    	this.SwingX_Sin(this.Arm_r_Seg2, -0.5260422258984465F, ageInTicks, -0.07F, 0.03F, false, 0.5F * (float)Math.PI); 
    	this.SwingX_Sin(this.Arm_l_Seg2, -0.5260422258984465F, ageInTicks, -0.07F, 0.03F, false, 0.5F * (float)Math.PI); 
    	
    	this.Arm_r_Seg0.y = -7.0F + (-0.55F  * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Arm_l_Seg0.y = -7.0F + (-0.55F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
    	
    	if (this.riding) {
    		this.setRotateAngle(Leg_r_Seg0, -1.66085538011367F, 0.19547687289441354F, 0.0F);
    		this.setRotateAngle(Leg_l_Seg0, -1.66085538011367F, -0.19547687289441354F, 0.0F);
    	} else {
        	this.SwingX_Sin(this.Leg_r_Seg0, -0.40980330836826856F, limbSwing, limbSwingAmount * 0.7F, 0.3F, true, 0.0F);
        	Leg_r_Seg0.yRot = 0.0F;
        	this.SwingX_Sin(this.Leg_l_Seg0, -0.40980330836826856F, limbSwing, limbSwingAmount * 0.7F, 0.3F, false, 0.0F);
        	Leg_l_Seg0.yRot = 0.0F;
        	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, false, 0.3F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, true, 0.3F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg_r_Seg2, -0.18203784098300857F, limbSwing, limbSwingAmount * 0.2F, 0.3F, true, 0.5F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg_l_Seg2, -0.18203784098300857F, limbSwing, limbSwingAmount * 0.2F, 0.3F, false, 0.5F * (float)Math.PI);    		
    	}

    	if(j > 0.0F) {
    		this.Body_chest.xRot = GradientAnimation(0.4553564018453205F, -0.136659280431156F, j);
    		this.Arm_r_Seg0.xRot = GradientAnimation(-0.5918411493512771F, -1.730144887501979F, j);
    	} else if(i > 8.0F) {
    		this.Arm_l_Seg0.xRot = GradientAnimation(-1.8668041679331349F, -2.321986036853256F, (i - 9.0F)/6.0F);
    		this.Arm_r_Seg0.xRot = GradientAnimation(-1.8668041679331349F, -2.321986036853256F, (i - 9.0F)/6.0F);
    		
    		this.Body_chest.xRot = GradientAnimation(0.4553564018453205F, -0.6373942428283291F, (i - 9.0F)/6.0F);
    	} else if(i > 0.0F) {
    		this.Arm_l_Seg0.xRot = GradientAnimation(-2.321986036853256F, -0.8651597102135892F, i/9.0F);
    		this.Arm_l_Seg0.yRot = GradientAnimation(0.0F, 0.5918411493512771F, i/9.0F);
    		this.Arm_l_Seg0.zRot = GradientAnimation(-0.6373942428283291F, -0.31869712141416456F, i/9.0F);
    		this.Arm_r_Seg0.xRot = GradientAnimation(-2.321986036853256F, -0.8651597102135892F, i/9.0F);
    		this.Arm_r_Seg0.yRot = GradientAnimation(0.0F, -0.5918411493512771F, i/9.0F);
    		this.Arm_r_Seg0.zRot = GradientAnimation(0.6373942428283291F, 0.31869712141416456F, i/9.0F);
    		
    		this.Arm_l_Seg1.xRot = GradientAnimation(0.0F, -0.27314402793711257F, i/9.0F);
    		this.Arm_r_Seg1.xRot = GradientAnimation(0.0F, -0.27314402793711257F, i/9.0F);
    		
    		this.Arm_l_Seg2.xRot = GradientAnimation(-0.9560913642424937F, -0.27314402793711257F, i/9.0F);
    		this.Arm_r_Seg2.xRot = GradientAnimation(-0.9560913642424937F, -0.27314402793711257F, i/9.0F);
    		
    		this.Body_chest.xRot = GradientAnimation(-0.6373942428283291F, 0.5918411493512771F, i/9.0F);
    	} else {
    		this.setRotateAngle(Arm_l_Seg0, -0.12269665007704787F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_r_Seg0, -0.12269665007704787F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_l_Seg1, 0.0F, 0.18203784098300857F, -0.10000736613927509F);
    		this.setRotateAngle(Arm_r_Seg1, 0.0F, -0.18203784630933073F, 0.10000736647217022F);
    		this.setRotateAngle(Arm_l_Seg2, -0.5260422258984465F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_r_Seg2, -0.5260422258984465F, 0.0F, 0.0F);
    		
    		this.Body_chest.xRot = 0.4553564018453205F;
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Neck;
	}
}
