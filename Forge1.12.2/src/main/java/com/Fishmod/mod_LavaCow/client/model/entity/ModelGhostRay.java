package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGhostRay - Fish0016054
 * Created using Tabula 8.0.0
 */
public class ModelGhostRay extends ModelFlyingBase {
	private final ModelRenderer Body_Base;
	private final ModelRenderer Body1;
	private final ModelRenderer Tail0;
	private final ModelRenderer Tail1;
	private final ModelRenderer back_fin;
	private final ModelRenderer Wing0_r;
	private final ModelRenderer Wing1_r;
	private final ModelRenderer Wing0_l;
	private final ModelRenderer Wing1_l;
	private final ModelRenderer Fin_l;
	private final ModelRenderer Fin_r;

    public ModelGhostRay() {
		textureWidth = 128;
		textureHeight = 128;

		Body_Base = new ModelRenderer(this);
		Body_Base.setRotationPoint(0.0F, 10.0F, -1.0F);
		Body_Base.setTextureOffset(0, 0).addBox(-6.0F, -2.0F, -10.0F, 12, 4, 16, false);

		Body1 = new ModelRenderer(this);
		Body1.setRotationPoint(0.0F, 0.0F, 7.0F);
		Body_Base.addChild(Body1);
		setRotateAngle(Body1, 0.0911F, 0.0F, 0.0F);
		Body1.setTextureOffset(0, 36).addBox(-4.0F, -1.0F, -1.0F, 8, 2, 4, false);

		Tail0 = new ModelRenderer(this);
		Tail0.setRotationPoint(0.0F, 0.0F, 3.0F);
		Body1.addChild(Tail0);
		setRotateAngle(Tail0, 0.182F, 0.0F, 0.0F);
		Tail0.setTextureOffset(34, 32).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 8, false);

		Tail1 = new ModelRenderer(this);
		Tail1.setRotationPoint(0.0F, -1.0F, 7.0F);
		Tail0.addChild(Tail1);
		setRotateAngle(Tail1, 0.182F, 0.0F, 0.0F);
		Tail1.setTextureOffset(16, 36).addBox(-0.5F, 0.5F, 0.0F, 1, 1, 8, false);

		back_fin = new ModelRenderer(this);
		back_fin.setRotationPoint(0.0F, 1.0F, 1.5F);
		Body1.addChild(back_fin);
		setRotateAngle(back_fin, -0.5672F, 0.0F, 0.0F);
		back_fin.setTextureOffset(0, 0).addBox(-0.5F, -3.0F, -2.5F, 1, 3, 2, false);

		Wing0_r = new ModelRenderer(this);
		Wing0_r.setRotationPoint(7.0F, 0.0F, -4.0F);
		Body_Base.addChild(Wing0_r);
		setRotateAngle(Wing0_r, 0.0F, 0.0F, 0.2731F);
		Wing0_r.setTextureOffset(0, 20).addBox(-2.0F, -0.5F, -5.0F, 7, 2, 14, false);

		Wing1_r = new ModelRenderer(this);
		Wing1_r.setRotationPoint(5.0F, 0.0F, 0.8F);
		Wing0_r.addChild(Wing1_r);
		setRotateAngle(Wing1_r, 0.0F, 0.0F, 0.1367F);
		Wing1_r.setTextureOffset(28, 20).addBox(-2.0F, 0.0F, -5.0F, 9, 1, 11, false);

		Wing0_l = new ModelRenderer(this);
		Wing0_l.setRotationPoint(-6.0F, 0.0F, -4.0F);
		Body_Base.addChild(Wing0_l);
		setRotateAngle(Wing0_l, 0.0F, 0.0F, -0.2731F);
		Wing0_l.setTextureOffset(0, 20).addBox(-6.0F, -0.5F, -5.0F, 7, 2, 14, true);

		Wing1_l = new ModelRenderer(this);
		Wing1_l.setRotationPoint(-5.0F, 0.0F, 0.8F);
		Wing0_l.addChild(Wing1_l);
		setRotateAngle(Wing1_l, 0.0F, 0.0F, -0.1367F);
		Wing1_l.setTextureOffset(28, 20).addBox(-9.0F, 0.0F, -5.0F, 9, 1, 11, true);

		Fin_l = new ModelRenderer(this);
		Fin_l.setRotationPoint(-4.7F, 0.0F, -10.0F);
		Body_Base.addChild(Fin_l);
		setRotateAngle(Fin_l, 0.0F, 0.0F, -1.5708F);
		Fin_l.setTextureOffset(0, 0).addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, true);

		Fin_r = new ModelRenderer(this);
		Fin_r.setRotationPoint(4.7F, 0.0F, -10.0F);
		Body_Base.addChild(Fin_r);
		setRotateAngle(Fin_r, 0.0F, 0.0F, 1.5708F);
		Fin_r.setTextureOffset(0, 0).addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, false);
    }
    
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
    	
    	float flap = MathHelper.sin(ageInTicks * 0.1F);
    	
    	this.Body_Base.rotationPointY = 22.0F + flap;
    	
    	this.Wing0_r.rotateAngleZ = 0.24F * flap;
        this.Wing0_l.rotateAngleZ = -0.24F * flap;
        
        this.Wing1_r.rotateAngleZ = 0.36F * flap;
        this.Wing1_l.rotateAngleZ = -0.36F * flap;        
        
        this.Tail0.rotateAngleX = 0.18F * flap;
        
        this.Tail1.rotateAngleX = 0.09F * flap;
    }
}
