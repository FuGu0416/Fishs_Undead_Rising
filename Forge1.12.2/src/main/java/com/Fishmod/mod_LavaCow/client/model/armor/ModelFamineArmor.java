package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

/**
 * ModelFamineArmor - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelFamineArmor extends ModelBiped {
    public ModelRenderer Antler_r;
    public ModelRenderer Antler_l;
    public ModelRenderer Snout;
    public ModelRenderer Jaw;

    public ModelFamineArmor(float modelSize) {
    	super(modelSize, 0, 64, 32);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Antler_r = new ModelRenderer(this, 56, 0);
        this.Antler_r.setRotationPoint(-2.0F, -8.0F, 0.0F);
        this.Antler_r.addBox(-8.0F, -8.0F, 0.0F, 8, 8, 0, 0.0F);
        this.setRotateAngle(Antler_r, -0.6829473363053812F, 0.0F, 0.0F);
        this.Snout = new ModelRenderer(this, 35, 0);
        this.Snout.setRotationPoint(0.0F, -5.5F, -5.0F);
        this.Snout.addBox(-3.0F, -1.5F, -4.0F, 6, 3, 4, 0.0F);
        this.Jaw = new ModelRenderer(this, 35, 8);
        this.Jaw.setRotationPoint(0.0F, -1.6F, -5.0F);
        this.Jaw.addBox(-3.0F, -1.5F, -4.0F, 6, 3, 4, 0.0F);
        this.Antler_l = new ModelRenderer(this, 56, 0);
        this.Antler_l.mirror = true;
        this.Antler_l.setRotationPoint(2.0F, -8.0F, 0.0F);
        this.Antler_l.addBox(0.0F, -8.0F, 0.0F, 8, 8, 0, 0.0F);
        this.setRotateAngle(Antler_l, -0.6829473363053812F, 0.0F, 0.0F);
        
        this.bipedHead.addChild(this.Antler_r);
        this.bipedHead.addChild(this.Snout);
        this.bipedHead.addChild(this.Jaw);
        this.bipedHead.addChild(this.Antler_l);
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
		
		this.bipedHeadwear.showModel = false;
		
        if (entity instanceof EntityArmorStand)
        {
            EntityArmorStand entityarmorstand = (EntityArmorStand)entity;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
        else
        	super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel, entity);
	}
}
