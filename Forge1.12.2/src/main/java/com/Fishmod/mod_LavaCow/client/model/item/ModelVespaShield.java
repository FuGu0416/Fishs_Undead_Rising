package com.Fishmod.mod_LavaCow.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * ModelVespaShield - Fish0416
 * Created using Tabula 7.1.0
 */
public class ModelVespaShield extends ModelBase {
    public ModelRenderer UAbdomen1;
    public ModelRenderer UAbdomen2;
    public ModelRenderer Stinger1;
    public ModelRenderer UAbdomen3;
    public ModelRenderer Stinger2;
    public ModelRenderer UAbdomen4;
    public ModelRenderer Stinger3;

    public ModelVespaShield() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Stinger3 = new ModelRenderer(this, 84, 36);
        this.Stinger3.setRotationPoint(0.0F, 0.5F, 3.6F);
        this.Stinger3.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(Stinger3, 1.5707963267948966F, 0.0F, 0.0F);
        this.UAbdomen2 = new ModelRenderer(this, 88, 0);
        this.UAbdomen2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.UAbdomen2.addBox(-6.0F, -2.0F, 0.0F, 12, 4, 8, 0.0F);
        this.setRotateAngle(UAbdomen2, -0.22759093446006054F, 0.0F, 0.0F);
        this.UAbdomen4 = new ModelRenderer(this, 84, 28);
        this.UAbdomen4.setRotationPoint(0.0F, -1.0F, 5.0F);
        this.UAbdomen4.addBox(-2.0F, -0.5F, 0.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(UAbdomen4, -0.22759093446006054F, 0.0F, 0.0F);
        this.UAbdomen1 = new ModelRenderer(this, 66, 48);
        this.UAbdomen1.setRotationPoint(0.0F, 1.0F, 5.0F);
        this.UAbdomen1.addBox(-5.0F, -1.5F, 0.0F, 10, 3, 6, 0.0F);
        this.setRotateAngle(UAbdomen1, -1.0927506446736497F, 3.141592653589793F, 0.0F);
        this.UAbdomen3 = new ModelRenderer(this, 100, 28);
        this.UAbdomen3.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.UAbdomen3.addBox(-4.0F, -1.5F, 0.0F, 8, 3, 6, 0.0F);
        this.setRotateAngle(UAbdomen3, -0.22759093446006054F, 0.0F, 0.0F);
        this.Stinger2 = new ModelRenderer(this, 84, 36);
        this.Stinger2.mirror = true;
        this.Stinger2.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.Stinger2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(Stinger2, 1.5707963267948966F, 0.0F, 0.0F);
        this.Stinger1 = new ModelRenderer(this, 84, 36);
        this.Stinger1.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.Stinger1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(Stinger1, 1.5707963267948966F, 0.0F, 0.0F);
        this.UAbdomen3.addChild(this.Stinger3);
        this.UAbdomen1.addChild(this.UAbdomen2);
        this.UAbdomen3.addChild(this.UAbdomen4);
        this.UAbdomen2.addChild(this.UAbdomen3);
        this.UAbdomen2.addChild(this.Stinger2);
        this.UAbdomen1.addChild(this.Stinger1);
    }

    public void render() { 
        this.UAbdomen1.render(0.0625F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
