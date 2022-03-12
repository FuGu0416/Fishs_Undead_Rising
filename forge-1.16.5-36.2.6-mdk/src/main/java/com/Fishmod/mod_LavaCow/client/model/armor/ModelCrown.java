package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelCrown<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer Crown;
    
	public ModelCrown(float modelSize) {
		super(modelSize, 0, 64, 32);
        this.texWidth = 128;
        this.texHeight = 64;
        this.Crown = new ModelRenderer(this, 92, 19);
        this.Crown.setPos(0.0F, -6.0F, 0.0F);
        this.Crown.addBox(-4.5F, -4.0F, -4.5F, 9, 5, 9, 0.0F);
        this.head.addChild(this.Crown);
	}
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch) {
        if (entity instanceof ArmorStandEntity)
        {
            ArmorStandEntity ArmorStandEntity = (ArmorStandEntity)entity;
            this.head.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getHeadPose().getX();
            this.head.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getHeadPose().getY();
            this.head.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getHeadPose().getZ();
            this.head.setPos(0.0F, 1.0F, 0.0F);
            this.body.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getBodyPose().getX();
            this.body.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getBodyPose().getY();
            this.body.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getBodyPose().getZ();
            this.leftArm.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftArmPose().getX();
            this.leftArm.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftArmPose().getY();
            this.leftArm.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftArmPose().getZ();
            this.rightArm.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightArmPose().getX();
            this.rightArm.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightArmPose().getY();
            this.rightArm.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightArmPose().getZ();
            this.leftLeg.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftLegPose().getX();
            this.leftLeg.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftLegPose().getY();
            this.leftLeg.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getLeftLegPose().getZ();
            this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
            this.rightLeg.xRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightLegPose().getX();
            this.rightLeg.yRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightLegPose().getY();
            this.rightLeg.zRot = ((float)Math.PI / 180F) * ArmorStandEntity.getRightLegPose().getZ();
            this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
            this.hat.copyFrom(this.head);
        }
        else
        	super.setupAnim(entity, limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch);
	}
}
