package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelSwineHelm - Fish0016054
 * Created using Tabula 7.0.1
 */
@SideOnly(Side.CLIENT)
public class ModelSwineMask extends ModelBiped {
    public ModelRenderer Mask_Base;
    public ModelRenderer helmRight;
    public ModelRenderer helmLeft;
    public ModelRenderer Snout;
    public ModelRenderer Mask_ridge;
    public ModelRenderer Mask_glass_r;
    public ModelRenderer Mask_glass_l;
    public ModelRenderer Tusk_r;
    public ModelRenderer Tusk_l;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;

    public ModelSwineMask(float scale) {
    	super(scale, 0, 64, 64);
        this.helmLeft = new ModelRenderer(this, 0, 38);
        this.helmLeft.mirror = true;
        this.helmLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmLeft.addBox(3.5F, -5.0F, -5.5F, 1, 5, 3, 0.0F);
        this.Mask_glass_r = new ModelRenderer(this, 9, 43);
        this.Mask_glass_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mask_glass_r.addBox(-3.5F, -5.0F, -5.5F, 3, 3, 1, 0.0F);
        this.Mask_Base = new ModelRenderer(this, 0, 32);
        this.Mask_Base.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mask_Base.addBox(-3.5F, -2.0F, -5.5F, 7, 2, 1, 0.0F);
        this.Tusk_l = new ModelRenderer(this, 14, 38);
        this.Tusk_l.mirror = true;
        this.Tusk_l.setRotationPoint(2.0F, -1.0F, -5.5F);
        this.Tusk_l.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk_l, 0.5009094953223726F, 0.0F, 0.6373942428283291F);
        this.Snout = new ModelRenderer(this, 18, 32);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-2.0F, -3.0F, -7.5F, 4, 3, 2, 0.0F);
        this.Mask_ridge = new ModelRenderer(this, 9, 38);
        this.Mask_ridge.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mask_ridge.addBox(-0.5F, -5.0F, -5.5F, 1, 3, 1, 0.0F);
        this.Mask_glass_l = new ModelRenderer(this, 9, 43);
        this.Mask_glass_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Mask_glass_l.addBox(0.5F, -5.0F, -5.5F, 3, 3, 1, 0.0F);
        this.helmRight = new ModelRenderer(this, 0, 38);
        this.helmRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmRight.addBox(-4.5F, -5.0F, -5.5F, 1, 5, 3, 0.0F);
        this.Tusk_r = new ModelRenderer(this, 14, 38);
        this.Tusk_r.mirror = true;
        this.Tusk_r.setRotationPoint(-2.0F, -1.0F, -5.5F);
        this.Tusk_r.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk_r, 0.5009094953223726F, 0.0F, -0.6373942428283291F);
        this.Ear1 = new ModelRenderer(this, 0, 49);
        this.Ear1.setRotationPoint(-2.0F, -7.7F, -2.0F);
        this.Ear1.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Ear1, 0.136659280431156F, 0.36425021489121656F, -0.22759093446006054F);
        this.Ear2 = new ModelRenderer(this, 0, 49);
        this.Ear2.mirror = true;
        this.Ear2.setRotationPoint(2.0F, -7.7F, -2.0F);
        this.Ear2.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Ear2, 0.136659280431156F, -0.36425021489121656F, 0.22759093446006054F);
        this.Mask_Base.addChild(this.helmLeft);
        this.Mask_Base.addChild(this.Mask_glass_r);
        this.Snout.addChild(this.Tusk_l);
        this.Mask_Base.addChild(this.Snout);
        this.Mask_Base.addChild(this.Mask_ridge);
        this.Mask_Base.addChild(this.Mask_glass_l);
        this.Mask_Base.addChild(this.helmRight);
        this.Snout.addChild(this.Tusk_r);
        this.Mask_Base.addChild(this.Ear1);
        this.Mask_Base.addChild(this.Ear2);
        this.bipedHead.addChild(this.Mask_Base);    
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel) { 
		GlStateManager.pushMatrix();
		GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.render(entity, limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel);
        GlStateManager.disableBlend();
		GlStateManager.popMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel, Entity entity) {		
        if (entity instanceof EntityArmorStand) {
            EntityArmorStand entityarmorstand = (EntityArmorStand)entity;
            this.Mask_Base.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.Mask_Base.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.Mask_Base.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.Mask_Base.setRotationPoint(0.0F, 0.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        } 
        else/* if (entity instanceof EntityPlayer)*/ {
        	super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel, entity);
        }
	}
}
