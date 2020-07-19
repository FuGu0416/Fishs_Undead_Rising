package com.Fishmod.mod_LavaCow.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * ModelScarecrow2 - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelScarecrowHead_straw extends ModelBase {
    public ModelRenderer Head;
    public ModelRenderer Stem;

    public ModelScarecrowHead_straw() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Head = new ModelRenderer(this, 40, 49);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.Stem = new ModelRenderer(this, 33, 48);
        this.Stem.setRotationPoint(0.0F, -7.6F, 2.3F);
        this.Stem.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Stem, -0.8196066167365371F, 0.0F, 0.0F);
        this.Head.addChild(this.Stem);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    	this.Head.render(f5);
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
