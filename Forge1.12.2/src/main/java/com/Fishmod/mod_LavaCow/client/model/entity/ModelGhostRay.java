package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGhostRay - Fish0016054
 * Created using Tabula 8.0.0
 */
public class ModelGhostRay extends ModelFlyingBase {
    public ModelRenderer Body_Base;
    public ModelRenderer Body1;
    public ModelRenderer Wing0_r;
    public ModelRenderer Wing0_l;
    public ModelRenderer Fin_l;
    public ModelRenderer Fin_r;
    public ModelRenderer Tail0;
    public ModelRenderer Tail1;
    public ModelRenderer Wing1_r;
    public ModelRenderer Wing2_r;
    public ModelRenderer Wing1_l;
    public ModelRenderer Wing2_l;

    public ModelGhostRay() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body_Base = new ModelRenderer(this, 9, 16);
        this.Body_Base.setRotationPoint(0.0F, 22.0F, -1.0F);
        this.Body_Base.addBox(-3.0F, -2.0F, -6.0F, 6, 4, 12, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 18);
        this.Tail1.mirror = true;
        this.Tail1.setRotationPoint(0.0F, -1.0F, 7.0F);
        this.Tail1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail1, 0.18203784630933073F, 0.0F, 0.0F);
        this.Wing0_r = new ModelRenderer(this, 26, 0);
        this.Wing0_r.setRotationPoint(-3.0F, 0.0F, -4.0F);
        this.Wing0_r.addBox(-5.0F, -1.5F, -2.0F, 7, 3, 11, 0.0F);
        this.setRotateAngle(Wing0_r, 0.0F, 0.0F, -0.27314402127920984F);
        this.Fin_l = new ModelRenderer(this, 17, 0);
        this.Fin_l.mirror = true;
        this.Fin_l.setRotationPoint(2.7F, -0.5F, -6.0F);
        this.Fin_l.addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, 0.0F);
        this.setRotateAngle(Fin_l, 0.0F, 0.0F, 0.4098033003787853F);
        this.Wing1_r = new ModelRenderer(this, 34, 18);
        this.Wing1_r.setRotationPoint(-5.0F, 0.0F, 0.8F);
        this.Wing1_r.addBox(-5.0F, -1.0F, -2.0F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Wing1_r, 0.0F, 0.0F, -0.13665927909957545F);
        this.Body1 = new ModelRenderer(this, 0, 0);
        this.Body1.setRotationPoint(0.0F, 0.0F, 7.0F);
        this.Body1.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Body1, 0.0911061832922575F, 0.0F, 0.0F);
        this.Wing0_l = new ModelRenderer(this, 26, 0);
        this.Wing0_l.mirror = true;
        this.Wing0_l.setRotationPoint(3.0F, 0.0F, -4.0F);
        this.Wing0_l.addBox(-1.0F, -1.5F, -2.0F, 7, 3, 11, 0.0F);
        this.setRotateAngle(Wing0_l, 0.0F, 0.0F, 0.27314402127920984F);
        this.Tail0 = new ModelRenderer(this, 0, 18);
        this.Tail0.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Tail0.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail0, 0.18203784630933073F, 0.0F, 0.0F);
        this.Wing2_l = new ModelRenderer(this, 0, 8);
        this.Wing2_l.mirror = true;
        this.Wing2_l.setRotationPoint(6.0F, -0.5F, -0.5F);
        this.Wing2_l.addBox(0.0F, -0.5F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing2_l, 0.0F, 0.0F, 0.45535640450848164F);
        this.Fin_r = new ModelRenderer(this, 17, 0);
        this.Fin_r.setRotationPoint(-2.7F, -0.5F, -6.0F);
        this.Fin_r.addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, 0.0F);
        this.setRotateAngle(Fin_r, 0.0F, 0.0F, -0.4098033003787853F);
        this.Wing2_r = new ModelRenderer(this, 0, 8);
        this.Wing2_r.setRotationPoint(-4.0F, -0.5F, -0.5F);
        this.Wing2_r.addBox(-9.0F, -0.5F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing2_r, 0.0F, 0.0F, -0.45535640450848164F);
        this.Wing1_l = new ModelRenderer(this, 34, 18);
        this.Wing1_l.mirror = true;
        this.Wing1_l.setRotationPoint(5.0F, 0.0F, 0.8F);
        this.Wing1_l.addBox(0.0F, -1.0F, -2.0F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Wing1_l, 0.0F, 0.0F, 0.13665927909957545F);
        this.Tail0.addChild(this.Tail1);
        this.Body_Base.addChild(this.Wing0_r);
        this.Body_Base.addChild(this.Fin_l);
        this.Wing0_r.addChild(this.Wing1_r);
        this.Body_Base.addChild(this.Body1);
        this.Body_Base.addChild(this.Wing0_l);
        this.Body1.addChild(this.Tail0);
        this.Wing1_l.addChild(this.Wing2_l);
        this.Body_Base.addChild(this.Fin_r);
        this.Wing1_r.addChild(this.Wing2_r);
        this.Wing0_l.addChild(this.Wing1_l);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.Body_Base.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	float flap = MathHelper.sin(ageInTicks * 0.1F);
    	
    	this.Body_Base.rotationPointY = 22.0F + flap;
    	
    	this.Wing0_r.rotateAngleZ = 0.24F * flap;
        this.Wing0_l.rotateAngleZ = -0.24F * flap;
        
        this.Wing1_r.rotateAngleZ = 0.36F * flap;
        this.Wing1_l.rotateAngleZ = -0.36F * flap;
        
        this.Wing2_r.rotateAngleZ = 0.18F * flap;
        this.Wing2_l.rotateAngleZ = -0.18F * flap;
        
        this.Tail0.rotateAngleX = 0.18F * flap;
        
        this.Tail1.rotateAngleX = 0.09F * flap;
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float flap = MathHelper.sin(entityIn.ticksExisted * 0.1F);
    	
    	if(this.state.equals(ModelFlyingBase.State.FLYING)) {
        	this.Body1.rotateAngleX = 0.091106186954104F;
        	//this.Tail0.rotateAngleX = 0.26F - 0.18F * flap;
        }
        else {
        	this.Body1.rotateAngleX = -0.36267941856442165F;
        	//this.Tail0.rotateAngleX = -0.18448130193580065F - 0.18F * flap;      	
        }
    }
}
