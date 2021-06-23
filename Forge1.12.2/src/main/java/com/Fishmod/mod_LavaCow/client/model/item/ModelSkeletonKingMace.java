package com.Fishmod.mod_LavaCow.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * ModelSkeletonKingMace - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelSkeletonKingMace extends ModelBase {
    public ModelRenderer weapon_handle0;
    public ModelRenderer weapon_handle1;
    public ModelRenderer weapon_handle1_1;
    public ModelRenderer weapon_base;
    public ModelRenderer weapon_horn_l;
    public ModelRenderer weapon_horn_r;

    public ModelSkeletonKingMace() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.weapon_horn_l = new ModelRenderer(this, 110, 52);
        this.weapon_horn_l.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.weapon_horn_l.addBox(0.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_handle1_1 = new ModelRenderer(this, 66, 52);
        this.weapon_handle1_1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.weapon_handle1_1.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.weapon_base = new ModelRenderer(this, 96, 35);
        this.weapon_base.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.weapon_base.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.weapon_handle1 = new ModelRenderer(this, 66, 43);
        this.weapon_handle1.setRotationPoint(0.0F, 0.0F, -15.0F);
        this.weapon_handle1.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.weapon_horn_r = new ModelRenderer(this, 110, 52);
        this.weapon_horn_r.mirror = true;
        this.weapon_horn_r.setRotationPoint(-4.0F, 0.0F, -4.0F);
        this.weapon_horn_r.addBox(-3.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_handle0 = new ModelRenderer(this, 66, 43);
        this.weapon_handle0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.weapon_handle0.addBox(-0.5F, -0.5F, -15.0F, 1, 1, 20, 0.0F);
        this.weapon_base.addChild(this.weapon_horn_l);
        this.weapon_handle1.addChild(this.weapon_handle1_1);
        this.weapon_handle1_1.addChild(this.weapon_base);
        this.weapon_handle0.addChild(this.weapon_handle1);
        this.weapon_base.addChild(this.weapon_horn_r);
    }

    public void render() { 
    	this.weapon_handle0.render(0.0625F);
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
