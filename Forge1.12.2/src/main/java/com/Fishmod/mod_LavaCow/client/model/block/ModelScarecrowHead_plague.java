package com.Fishmod.mod_LavaCow.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelScarecrow3 - Fish0016054
 * Created using Tabula 8.0.0
 */
@SideOnly(Side.CLIENT)
public class ModelScarecrowHead_plague extends ModelBase {
    public ModelRenderer Head3;
    public ModelRenderer Beak;
    public ModelRenderer Hat;
    public ModelRenderer Beak1;

    public ModelScarecrowHead_plague() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Hat = new ModelRenderer(this, 90, 0);
        this.Hat.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.Hat.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 1, 0.0F);
        this.Beak1 = new ModelRenderer(this, 80, 28);
        this.Beak1.setRotationPoint(0.0F, 3.2F, -0.7F);
        this.Beak1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Beak1, 0.500909508638178F, 0.0F, 0.0F);
        this.Beak = new ModelRenderer(this, 64, 26);
        this.Beak.setRotationPoint(0.0F, 2.5F, -2.5F);
        this.Beak.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Beak, 0.2275909337942703F, 0.0F, 0.0F);
        this.Head3 = new ModelRenderer(this, 64, 10);
        this.Head3.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.Head3.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Head3, -1.5707963267948966F, 0.0F, 0.0F);
        this.Head3.addChild(this.Hat);
        this.Beak.addChild(this.Beak1);
        this.Head3.addChild(this.Beak);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    	this.Head3.render(f5);
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
