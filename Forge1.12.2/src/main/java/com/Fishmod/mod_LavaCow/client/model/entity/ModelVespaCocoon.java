package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelVespaCocoon - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelVespaCocoon extends ModelBase {
    public ModelRenderer Cocoon_base;
    public ModelRenderer Cocoon_tangle0;
    public ModelRenderer Cocoon_tangle1;
    public ModelRenderer Cocoon_top;

    public ModelVespaCocoon() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Cocoon_tangle1 = new ModelRenderer(this, 0, -4);
        this.Cocoon_tangle1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Cocoon_tangle1.addBox(0.0F, -4.0F, -12.0F, 0, 4, 24, 0.0F);
        this.setRotateAngle(Cocoon_tangle1, 0.0F, -0.7853981633974483F, 0.0F);
        this.Cocoon_top = new ModelRenderer(this, 34, 0);
        this.Cocoon_top.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.Cocoon_top.addBox(-3.0F, -2.0F, -3.0F, 6, 2, 6, 0.0F);
        this.Cocoon_tangle0 = new ModelRenderer(this, 0, -4);
        this.Cocoon_tangle0.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Cocoon_tangle0.addBox(0.0F, -4.0F, -12.0F, 0, 4, 24, 0.0F);
        this.setRotateAngle(Cocoon_tangle0, 0.0F, 0.7853981633974483F, 0.0F);
        this.Cocoon_base = new ModelRenderer(this, 0, 0);
        this.Cocoon_base.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Cocoon_base.addBox(-4.0F, -8.0F, -4.0F, 8, 10, 8, 0.0F);
        this.Cocoon_base.addChild(this.Cocoon_top);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        
    	//float f = (0.95f + 0.05f * MathHelper.sin(ageInTicks * 0.05F)) * scale;
    	
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    	this.Cocoon_tangle1.render(scale);
        this.Cocoon_tangle0.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();

        this.Cocoon_base.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	
    }
}
