package com.Fishmod.mod_LavaCow.client.model.block;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * ModelScarecrow - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelScarecrowHead_common extends ModelBase {
    public ModelRenderer Head;
    public ModelRenderer Head_stem;
    public ModelRenderer Head_tooth;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_tooth;

    public ModelScarecrowHead_common() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Jaw_tooth = new ModelRenderer(this, 32, 38);
        this.Jaw_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw_tooth.addBox(-4.0F, -2.0F, -8.0F, 8, 2, 8, 0.0F);
        this.Head_stem = new ModelRenderer(this, 0, 0);
        this.Head_stem.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.Head_stem.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Head_stem, -0.5462880558742251F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -2.0F, 4.1F);
        this.Head.addBox(-4.0F, -5.0F, -8.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(Head, -0.6373942428283291F, 0.0F, 0.0F);
        this.Head_tooth = new ModelRenderer(this, 32, 25);
        this.Head_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head_tooth.addBox(-4.0F, -1.0F, -8.0F, 8, 2, 8, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.6373942428283291F, 0.0F, 0.0F);
        this.Jaw.addChild(this.Jaw_tooth);
        this.Head.addChild(this.Head_stem);
        this.Head.addChild(this.Head_tooth);
        this.Head.addChild(this.Jaw);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    	GL11.glDisable(GL11.GL_CULL_FACE);
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
