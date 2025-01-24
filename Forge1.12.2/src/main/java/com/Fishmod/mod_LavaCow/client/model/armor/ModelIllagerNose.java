package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIllagerNose extends ModelBiped {
    public ModelRenderer nose;

    public ModelIllagerNose(float modelSize) {
        super(modelSize, 0, 64, 32);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.nose = new ModelRenderer(this);
        this.nose.setRotationPoint(0.0F, 1.0F, -5.0F);
        this.nose.setTextureOffset(56, 32).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, false);
        this.bipedHead.addChild(this.nose);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel, Entity entity) {
        if (entity instanceof EntityArmorStand) {
            EntityArmorStand armorStand = (EntityArmorStand) entity;
            this.bipedHead.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorStand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            copyModelAngles(this.bipedHead, this.bipedHeadwear);
        } else
            super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, unitPixel, entity);
    }
}
