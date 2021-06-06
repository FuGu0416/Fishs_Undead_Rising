package com.Fishmod.mod_LavaCow.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * ModelWendigo - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelBeastClaw extends ModelBase {
    public ModelRenderer Palm_l;
    public ModelRenderer Claw_0_l;
    public ModelRenderer Claw_1_l;
    public ModelRenderer Claw_2_l;
    public ModelRenderer Claw_01_l;
    public ModelRenderer Claw_11_l;
    public ModelRenderer Claw_21_l;

    public ModelBeastClaw() {
    	this.textureWidth = 128;
        this.textureHeight = 128;
        this.Claw_1_l = new ModelRenderer(this, 24, 32);
        this.Claw_1_l.mirror = true;
        this.Claw_1_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_1_l.addBox(0.0F, -1.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_1_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Claw_0_l = new ModelRenderer(this, 24, 32);
        this.Claw_0_l.mirror = true;
        this.Claw_0_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_0_l.addBox(0.0F, -3.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_0_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Claw_11_l = new ModelRenderer(this, 32, 40);
        this.Claw_11_l.mirror = true;
        this.Claw_11_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_11_l.addBox(-2.0F, -1.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_11_l, 0.0F, 0.4553564018453205F, 0.0F);
        this.Palm_l = new ModelRenderer(this, 40, 46);
        this.Palm_l.mirror = true;
        this.Palm_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Palm_l.addBox(-2.0F, -3.0F, -6.0F, 4, 6, 6, 0.0F);
        this.setRotateAngle(Palm_l, 0.0F, 0.0F, -1.5707963267948966F);
        this.Claw_2_l = new ModelRenderer(this, 24, 32);
        this.Claw_2_l.mirror = true;
        this.Claw_2_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_2_l.addBox(0.0F, 1.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Claw_2_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Claw_01_l = new ModelRenderer(this, 32, 40);
        this.Claw_01_l.mirror = true;
        this.Claw_01_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_01_l.addBox(-2.0F, -3.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_01_l, -0.091106186954104F, 0.4553564018453205F, 0.0F);
        this.Claw_21_l = new ModelRenderer(this, 32, 40);
        this.Claw_21_l.mirror = true;
        this.Claw_21_l.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Claw_21_l.addBox(-2.0F, 1.0F, -10.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(Claw_21_l, 0.136659280431156F, 0.4553564018453205F, 0.0F);
        this.Palm_l.addChild(this.Claw_1_l);
        this.Palm_l.addChild(this.Claw_0_l);
        this.Palm_l.addChild(this.Claw_11_l);
        this.Palm_l.addChild(this.Claw_2_l);
        this.Palm_l.addChild(this.Claw_01_l);
        this.Palm_l.addChild(this.Claw_21_l);
    }

    public void render() { 
    	this.Palm_l.render(0.0625F);
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
