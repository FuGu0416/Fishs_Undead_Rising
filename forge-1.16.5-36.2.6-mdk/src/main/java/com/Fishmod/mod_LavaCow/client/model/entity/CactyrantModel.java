package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.CactyrantEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelCacti - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class CactyrantModel<T extends CactyrantEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Leg0_Seg0;
    public ModelRenderer Leg1_Seg0;
    public ModelRenderer Leg2_Seg0;
    public ModelRenderer Body_Seg1;
    public ModelRenderer Leg0_Seg1;
    public ModelRenderer Leg0_Seg2;
    public ModelRenderer Leg1_Seg1;
    public ModelRenderer Leg1_Seg2;
    public ModelRenderer Leg2_Seg1;
    public ModelRenderer Leg2_Seg2;
    public ModelRenderer Body_Seg2;
    public ModelRenderer thorn0_Seg1;
    public ModelRenderer thorn1_Seg1;
    public ModelRenderer Head;
    public ModelRenderer Limb0_Seg0;
    public ModelRenderer Limb1_Seg0;
    public ModelRenderer thorn0_Seg2;
    public ModelRenderer thorn1_Seg2;
    public ModelRenderer Head_Flower0;
    public ModelRenderer Head_Flower1;
    public ModelRenderer Limb0_Seg1;
    public ModelRenderer Limb0_Fruit0;
    public ModelRenderer Limb0_Fruit1;
    public ModelRenderer Limb0_Fruit2;
    public ModelRenderer Limb1_Seg1;
    public ModelRenderer Limb1_Fruit0;
    public ModelRenderer Limb1_Fruit1;

    public CactyrantModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Body_Seg2 = new ModelRenderer(this, 32, 26);
        this.Body_Seg2.setPos(0.0F, -11.0F, 0.0F);
        this.Body_Seg2.addBox(-4.0F, -12.0F, -4.0F, 8, 12, 8, 0.0F);
        this.Limb0_Fruit1 = new ModelRenderer(this, 0, 0);
        this.Limb0_Fruit1.mirror = true;
        this.Limb0_Fruit1.setPos(3.9F, -13.0F, 0.7F);
        this.Limb0_Fruit1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.thorn1_Seg2 = new ModelRenderer(this, 89, 14);
        this.thorn1_Seg2.setPos(0.0F, -1.0F, 0.0F);
        this.thorn1_Seg2.addBox(-7.0F, -12.0F, 0.0F, 14, 12, 0, 0.0F);
        this.setRotateAngle(thorn1_Seg2, 0.0F, -0.7853981633974483F, 0.0F);
        this.Leg1_Seg1 = new ModelRenderer(this, 0, 43);
        this.Leg1_Seg1.setPos(0.0F, 0.0F, -3.0F);
        this.Leg1_Seg1.addBox(-2.0F, -2.0F, -4.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Leg1_Seg1, -0.22759093446006054F, 0.0F, 0.0F);
        this.thorn1_Seg1 = new ModelRenderer(this, 89, 14);
        this.thorn1_Seg1.setPos(0.0F, 0.0F, 0.0F);
        this.thorn1_Seg1.addBox(-7.0F, -12.0F, 0.0F, 14, 12, 0, 0.0F);
        this.setRotateAngle(thorn1_Seg1, 0.0F, -0.7853981633974483F, 0.0F);
        this.thorn0_Seg2 = new ModelRenderer(this, 89, 14);
        this.thorn0_Seg2.setPos(0.0F, -1.0F, 0.0F);
        this.thorn0_Seg2.addBox(-7.0F, -12.0F, 0.0F, 14, 12, 0, 0.0F);
        this.setRotateAngle(thorn0_Seg2, 0.0F, 0.7853981633974483F, 0.0F);
        this.Leg2_Seg0 = new ModelRenderer(this, 0, 37);
        this.Leg2_Seg0.setPos(0.0F, -2.0F, 5.0F);
        this.Leg2_Seg0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Leg2_Seg0, 0.0F, 3.141592653589793F, 0.0F);
        this.Body_base = new ModelRenderer(this, 24, 49);
        this.Body_base.setPos(0.0F, 18.5F, 0.0F);
        this.Body_base.addBox(-5.0F, -5.0F, -5.0F, 10, 5, 10, 0.0F);
        this.Leg0_Seg1 = new ModelRenderer(this, 0, 43);
        this.Leg0_Seg1.mirror = true;
        this.Leg0_Seg1.setPos(0.0F, 0.0F, -3.0F);
        this.Leg0_Seg1.addBox(-2.0F, -2.0F, -4.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Leg0_Seg1, -0.22759093446006054F, 0.0F, 0.0F);
        this.Limb1_Seg0 = new ModelRenderer(this, 0, 20);
        this.Limb1_Seg0.setPos(-4.0F, -3.0F, -0.5F);
        this.Limb1_Seg0.addBox(-1.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(Limb1_Seg0, 0.0F, 3.141592653589793F, 0.22759093446006054F);
        this.Leg1_Seg0 = new ModelRenderer(this, 0, 37);
        this.Leg1_Seg0.setPos(-4.0F, -2.0F, -4.0F);
        this.Leg1_Seg0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Leg1_Seg0, 0.0F, 0.9105382707654417F, 0.0F);
        this.Body_Seg1 = new ModelRenderer(this, 32, 26);
        this.Body_Seg1.setPos(0.0F, -4.0F, 0.0F);
        this.Body_Seg1.addBox(-4.0F, -12.0F, -4.0F, 8, 12, 8, 0.0F);
        this.Limb0_Seg1 = new ModelRenderer(this, 40, 0);
        this.Limb0_Seg1.mirror = true;
        this.Limb0_Seg1.setPos(4.5F, -0.5F, 0.0F);
        this.Limb0_Seg1.addBox(0.0F, -13.0F, -3.0F, 6, 16, 6, 0.0F);
        this.setRotateAngle(Limb0_Seg1, 0.0F, 0.0F, 0.27314402793711257F);
        this.Limb1_Fruit1 = new ModelRenderer(this, 0, 0);
        this.Limb1_Fruit1.setPos(4.8F, -8.8F, -0.2F);
        this.Limb1_Fruit1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Limb1_Fruit1, 0.0F, -0.27314402793711257F, 0.136659280431156F);
        this.Head_Flower0 = new ModelRenderer(this, 0, 28);
        this.Head_Flower0.setPos(-4.2F, -8.4F, -2.8F);
        this.Head_Flower0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head_Flower0, 0.40980330836826856F, 0.0F, -0.27314402793711257F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, -11.0F, 0.0F);
        this.Head.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
        this.Leg0_Seg0 = new ModelRenderer(this, 0, 37);
        this.Leg0_Seg0.mirror = true;
        this.Leg0_Seg0.setPos(4.0F, -2.0F, -4.0F);
        this.Leg0_Seg0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(Leg0_Seg0, 0.0F, -0.9105382707654417F, 0.0F);
        this.Limb1_Seg1 = new ModelRenderer(this, 64, 0);
        this.Limb1_Seg1.setPos(4.5F, 0.0F, -0.5F);
        this.Limb1_Seg1.addBox(0.0F, -9.5F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(Limb1_Seg1, 0.0F, 0.0F, 0.22759093446006054F);
        this.Head_Flower1 = new ModelRenderer(this, 77, 0);
        this.Head_Flower1.setPos(0.0F, -3.0F, 0.0F);
        this.Head_Flower1.addBox(-6.0F, 0.0F, -6.0F, 12, 0, 12, 0.0F);
        this.Limb0_Fruit0 = new ModelRenderer(this, 0, 0);
        this.Limb0_Fruit0.setPos(1.0F, -12.0F, -1.0F);
        this.Limb0_Fruit0.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Limb0_Fruit0, 0.0F, -0.091106186954104F, -0.40980330836826856F);
        this.Limb0_Seg0 = new ModelRenderer(this, 0, 20);
        this.Limb0_Seg0.mirror = true;
        this.Limb0_Seg0.setPos(4.0F, -6.0F, -0.5F);
        this.Limb0_Seg0.addBox(-1.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(Limb0_Seg0, 0.0F, 0.0F, -0.27314402793711257F);
        this.Limb0_Fruit2 = new ModelRenderer(this, 0, 0);
        this.Limb0_Fruit2.setPos(5.3F, -10.1F, 0.0F);
        this.Limb0_Fruit2.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Limb0_Fruit2, 0.0F, 0.27314402793711257F, 0.9105382707654417F);
        this.Leg2_Seg2 = new ModelRenderer(this, 0, 52);
        this.Leg2_Seg2.setPos(0.0F, 2.0F, -2.0F);
        this.Leg2_Seg2.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Leg2_Seg2, 0.22759093446006054F, 0.0F, 0.0F);
        this.Leg0_Seg2 = new ModelRenderer(this, 0, 52);
        this.Leg0_Seg2.mirror = true;
        this.Leg0_Seg2.setPos(0.0F, 2.0F, -2.0F);
        this.Leg0_Seg2.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Leg0_Seg2, 0.22759093446006054F, 0.0F, 0.0F);
        this.Limb1_Fruit0 = new ModelRenderer(this, 0, 0);
        this.Limb1_Fruit0.mirror = true;
        this.Limb1_Fruit0.setPos(1.8F, -8.8F, -1.0F);
        this.Limb1_Fruit0.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Limb1_Fruit0, 0.0F, 0.091106186954104F, -0.27314402793711257F);
        this.Leg1_Seg2 = new ModelRenderer(this, 0, 52);
        this.Leg1_Seg2.setPos(0.0F, 2.0F, -2.0F);
        this.Leg1_Seg2.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Leg1_Seg2, 0.22759093446006054F, 0.0F, 0.0F);
        this.Leg2_Seg1 = new ModelRenderer(this, 0, 43);
        this.Leg2_Seg1.setPos(0.0F, 0.0F, -3.0F);
        this.Leg2_Seg1.addBox(-2.0F, -2.0F, -4.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Leg2_Seg1, -0.22759093446006054F, 0.0F, 0.0F);
        this.thorn0_Seg1 = new ModelRenderer(this, 89, 14);
        this.thorn0_Seg1.setPos(0.0F, 0.0F, 0.0F);
        this.thorn0_Seg1.addBox(-7.0F, -12.0F, 0.0F, 14, 12, 0, 0.0F);
        this.setRotateAngle(thorn0_Seg1, 0.0F, 0.7853981633974483F, 0.0F);
        this.Body_Seg1.addChild(this.Body_Seg2);
        this.Limb0_Seg1.addChild(this.Limb0_Fruit1);
        this.Body_Seg2.addChild(this.thorn1_Seg2);
        this.Leg1_Seg0.addChild(this.Leg1_Seg1);
        this.Body_Seg1.addChild(this.thorn1_Seg1);
        this.Body_Seg2.addChild(this.thorn0_Seg2);
        this.Body_base.addChild(this.Leg2_Seg0);
        this.Leg0_Seg0.addChild(this.Leg0_Seg1);
        this.Body_Seg2.addChild(this.Limb1_Seg0);
        this.Body_base.addChild(this.Leg1_Seg0);
        this.Body_base.addChild(this.Body_Seg1);
        this.Limb0_Seg0.addChild(this.Limb0_Seg1);
        this.Limb1_Seg1.addChild(this.Limb1_Fruit1);
        this.Head.addChild(this.Head_Flower0);
        this.Body_Seg2.addChild(this.Head);
        this.Body_base.addChild(this.Leg0_Seg0);
        this.Limb1_Seg0.addChild(this.Limb1_Seg1);
        this.Head_Flower0.addChild(this.Head_Flower1);
        this.Limb0_Seg1.addChild(this.Limb0_Fruit0);
        this.Body_Seg2.addChild(this.Limb0_Seg0);
        this.Limb0_Seg1.addChild(this.Limb0_Fruit2);
        this.Leg2_Seg1.addChild(this.Leg2_Seg2);
        this.Leg0_Seg1.addChild(this.Leg0_Seg2);
        this.Limb1_Seg1.addChild(this.Limb1_Fruit0);
        this.Leg1_Seg1.addChild(this.Leg1_Seg2);
        this.Leg2_Seg0.addChild(this.Leg2_Seg1);
        this.Body_Seg1.addChild(this.thorn0_Seg1);
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
    	float spl = (float)entityIn.getSpellTicks() / 20.0F;
    	float i = (float)entityIn.getAttackTimer() / 15.0F;  
    	float Anime_threshold[] = {1.0F, 0.7F, 0.25F};
    	float Anime_ctrl;
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);
    	float l = 1.0F / (Anime_threshold[2]);
    	
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	
    	if(entityIn.isCamouflaging()) {
    		this.Body_base.y = 25.0F;
    		this.Leg0_Seg0.xRot = 0.0F;
    		this.Leg1_Seg0.xRot = 0.0F;
    		this.Leg2_Seg0.xRot = 0.0F;
    	} else {
    		this.Body_base.y = 19.5F - 0.5F * MathHelper.sin(limbSwing * 1.2F);
        	this.SwingX_Sin(this.Leg0_Seg0, 0.0F, limbSwing, limbSwingAmount * 0.7F, 1.2F, false, 0.0F);
        	this.SwingX_Sin(this.Leg1_Seg0, 0.0F, limbSwing, limbSwingAmount * 0.7F, 1.2F, false, 0.33F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg2_Seg0, 0.0F, limbSwing, limbSwingAmount * 0.7F, 1.2F, false, 0.67F * (float)Math.PI);
    	}
    	   	
    	switch(entityIn.getGrowingStage()) {
	    	case 0:
    	    	this.Head_Flower0.visible = false;
    	    	this.Limb0_Fruit0.visible = false;
    	    	this.Limb0_Fruit1.visible = false;
    	    	this.Limb0_Fruit2.visible = false;
    	    	this.Limb1_Fruit0.visible = false;
    	    	this.Limb1_Fruit1.visible = false;
	    		break;
	    	case 1:
    	    	this.Head_Flower0.visible = true;
    	    	this.Limb0_Fruit0.visible = false;
    	    	this.Limb0_Fruit1.visible = false;
    	    	this.Limb0_Fruit2.visible = false;
    	    	this.Limb1_Fruit0.visible = false;
    	    	this.Limb1_Fruit1.visible = false;
	    		break;
	    	case 2:
    		default:
    	    	this.Head_Flower0.visible = true;
    	    	this.Limb0_Fruit0.visible = true;
    	    	this.Limb0_Fruit1.visible = true;
    	    	this.Limb0_Fruit2.visible = true;
    	    	this.Limb1_Fruit0.visible = true;
    	    	this.Limb1_Fruit1.visible = true;
    			break;
    	}
    	
    	if(spl > 0.0F) {
    		this.Body_Seg1.xRot = 0.0F;
    		this.Body_Seg1.yRot = (float) (Math.PI * 10.0F * (1.0F - spl));
    		this.Body_Seg2.xRot = 0.0F;
    		this.Body_Seg2.yRot = 0.0F;
    		this.Limb0_Seg0.yRot = 0.0F;
    		this.Limb0_Seg1.xRot = 0.0F;
    		this.Limb0_Seg1.zRot = 0.27314402793711257F;
    		this.Limb1_Seg0.yRot = 3.141592653589793F;
    		this.Limb1_Seg0.zRot = 0.22759093446006054F;	
    	} else if (i > Anime_threshold[1]) {
    		Anime_ctrl = j * (i - Anime_threshold[1]);
    		this.Body_Seg1.xRot = 0.0F;
    		this.Body_Seg1.yRot = GradientAnimation_s(0.0F, -0.5082398928281348F, Anime_ctrl);
    		this.Body_Seg2.xRot = 0.0F;
    		this.Body_Seg2.yRot = GradientAnimation_s(0.0F, -0.23457224414434488F, Anime_ctrl);
    		this.Limb0_Seg0.yRot = 0.0F;
    		this.Limb0_Seg1.xRot = 0.0F;
    		this.Limb0_Seg1.zRot = GradientAnimation_s(0.27314402793711257F, 0.5077162820683115F, Anime_ctrl);
    		this.Limb1_Seg0.yRot = 3.141592653589793F;
    		this.Limb1_Seg0.zRot = GradientAnimation_s(0.22759093446006054F, -0.3979350561389017F, Anime_ctrl);	    		   		
    	} else if (i > Anime_threshold[2]) {
    		Anime_ctrl = k * (i - Anime_threshold[2]);    		
    		this.Body_Seg1.xRot = GradientAnimation(0.0F, 0.6257005102083563F, Anime_ctrl);
    		this.Body_Seg1.yRot = GradientAnimation(-0.5082398928281348F, 0.19547687289441354F, Anime_ctrl);
    		this.Body_Seg2.xRot = GradientAnimation(0.0F, 0.6255260065779288F, Anime_ctrl);
    		this.Body_Seg2.yRot = GradientAnimation(-0.23457224414434488F, 0.23457224414434488F, Anime_ctrl);
    		this.Limb0_Seg0.yRot = GradientAnimation(0.0F, 0.7037167490777915F, Anime_ctrl);
    		this.Limb0_Seg1.xRot = GradientAnimation(0.0F, 0.7428121536172365F, Anime_ctrl);
    		this.Limb0_Seg1.zRot = 0.5077162820683115F;
    		this.Limb1_Seg0.yRot = GradientAnimation(3.141592653589793F, -3.141592653589793F, Anime_ctrl);
    		this.Limb1_Seg0.zRot = -0.3979350561389017F;	 
    	} else if (i > 0.0F) {
    		Anime_ctrl = l * i;
        	this.setRotateAngle(Limb0_Seg0, 0.0F, 0.0F, -0.27314402793711257F);
        	this.setRotateAngle(Limb0_Seg1, 0.0F, 0.0F, 0.27314402793711257F);
        	this.setRotateAngle(Limb1_Seg0, 0.0F, 3.141592653589793F, 0.22759093446006054F);
        	this.setRotateAngle(Limb1_Seg1, 0.0F, 0.0F, 0.22759093446006054F);
        	
    		this.Body_Seg1.xRot = GradientAnimation_s(0.6257005102083563F, 0.0F, Anime_ctrl);
    		this.Body_Seg1.yRot = GradientAnimation_s(0.19547687289441354F, 0.0F, Anime_ctrl);
    		this.Body_Seg2.xRot = GradientAnimation_s(0.6255260065779288F, 0.0F, Anime_ctrl);
    		this.Body_Seg2.yRot = GradientAnimation_s(0.23457224414434488F, 0.0F, Anime_ctrl);
    		this.Limb0_Seg0.yRot = GradientAnimation_s(0.7037167490777915F, 0.0F, Anime_ctrl);
    		this.Limb0_Seg1.xRot = GradientAnimation_s(0.7428121536172365F, 0.0F, Anime_ctrl);
    		this.Limb0_Seg1.zRot = GradientAnimation_s(0.5077162820683115F, 0.27314402793711257F, Anime_ctrl);
    		this.Limb1_Seg0.yRot = GradientAnimation_s(-3.141592653589793F, 3.141592653589793F, Anime_ctrl);
    		this.Limb1_Seg0.zRot = GradientAnimation_s(-0.3979350561389017F, 0.22759093446006054F, Anime_ctrl);	 
    	} else {
    		this.Body_Seg1.xRot = 0.0F;
    		this.Body_Seg1.yRot = 0.0F;
    		this.Body_Seg2.xRot = 0.0F;
    		this.Body_Seg2.yRot = 0.0F;
    		this.Limb0_Seg0.yRot = 0.0F;
    		this.Limb0_Seg1.xRot = 0.0F;
    		this.Limb0_Seg1.zRot = 0.27314402793711257F;
    		this.Limb1_Seg0.yRot = 3.141592653589793F;
    		this.Limb1_Seg0.zRot = 0.22759093446006054F;	    		
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
