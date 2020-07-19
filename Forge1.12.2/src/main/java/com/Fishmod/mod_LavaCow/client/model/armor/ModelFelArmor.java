package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelFelArmor - Fish0016054
 * Created using Tabula 7.0.1
 */
@SideOnly(Side.CLIENT)
public class ModelFelArmor extends ModelBiped {
    public ModelRenderer horn_l_0;
    public ModelRenderer horn_r_0;
    public ModelRenderer horn_l_1;
    public ModelRenderer horn_l_2;
    public ModelRenderer horn_r_1;
    public ModelRenderer horn_r_2;

    public ModelFelArmor(float modelSize) {
    	super(modelSize, 0, 64, 32);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.horn_l_1 = new ModelRenderer(this, 37, 0);
        this.horn_l_1.mirror = true;
        this.horn_l_1.setRotationPoint(-0.2F, 0.0F, -3.5F);
        this.horn_l_1.addBox(-1.0F, -0.5F, -4.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(horn_l_1, -0.4553564018453205F, 0.6373942428283291F, -0.4553564018453205F);
        this.horn_l_2 = new ModelRenderer(this, 47, 0);
        this.horn_l_2.mirror = true;
        this.horn_l_2.setRotationPoint(0.0F, 0.0F, -3.7F);
        this.horn_l_2.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(horn_l_2, -0.31869712141416456F, 0.6829473363053812F, 0.0F);
        this.horn_l_0 = new ModelRenderer(this, 50, 0);
        this.horn_l_0.mirror = true;
        this.horn_l_0.setRotationPoint(4.2F, -6.0F, 0.0F);
        this.horn_l_0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(horn_l_0, 0.36425021489121656F, -0.7740535232594852F, 0.0F);
        this.horn_r_2 = new ModelRenderer(this, 47, 0);
        this.horn_r_2.setRotationPoint(0.0F, 0.0F, -3.7F);
        this.horn_r_2.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(horn_r_2, -0.31869712141416456F, -0.6829473363053812F, 0.0F);
        this.horn_r_0 = new ModelRenderer(this, 50, 0);
        this.horn_r_0.setRotationPoint(-4.2F, -6.0F, 0.0F);
        this.horn_r_0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(horn_r_0, 0.36425021489121656F, 0.7740535232594852F, 0.0F);
        this.horn_r_1 = new ModelRenderer(this, 37, 0);
        this.horn_r_1.setRotationPoint(0.2F, 0.0F, -3.5F);
        this.horn_r_1.addBox(-1.0F, -0.5F, -4.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(horn_r_1, -0.4553564018453205F, -0.6373942428283291F, 0.4553564018453205F);
        this.horn_l_0.addChild(this.horn_l_1);
        this.horn_l_1.addChild(this.horn_l_2);
        this.bipedHead.addChild(this.horn_l_0);
        this.horn_r_1.addChild(this.horn_r_2);
        this.bipedHead.addChild(this.horn_r_0);
        this.horn_r_0.addChild(this.horn_r_1);
    }
    
    /*@Override
    public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel) { 
        setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel, entity);
        super.render(entity, limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-0.1F, 0.0F, 0F);
        this.bipedLeftArm.render(unitPixel);
        GlStateManager.translate(0.2F, 0.0F, 0F);
        this.bipedRightArm.render(unitPixel);
		GlStateManager.popMatrix();
    }*/

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
