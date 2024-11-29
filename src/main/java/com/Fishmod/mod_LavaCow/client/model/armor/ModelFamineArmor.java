package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelFamineArmor - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelFamineArmor<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer Antler_r;
    public ModelRenderer Antler_l;
    public ModelRenderer Snout;
    public ModelRenderer Jaw;

    public ModelFamineArmor(float modelSize) {
    	super(modelSize, 0, 64, (int) (64 * modelSize/0.9F));
        this.texWidth = 64;
        this.texHeight = (int) (64 * modelSize/0.9F);
        this.Antler_r = new ModelRenderer(this, 56, 33);
        this.Antler_r.setPos(-2.0F, -8.0F, 0.0F);
        this.Antler_r.addBox(-8.0F, -8.0F, 0.0F, 8, 8, 0, 0.0F);
        this.setRotateAngle(Antler_r, -0.6829473363053812F, 0.0F, 0.0F);
        this.Snout = new ModelRenderer(this, 35, 33);
        this.Snout.setPos(0.0F, -5.5F, -5.0F);
        this.Snout.addBox(-3.0F, -1.5F, -4.0F, 6, 3, 4, 0.0F);
        this.Jaw = new ModelRenderer(this, 35, 41);
        this.Jaw.setPos(0.0F, -1.6F, -5.0F);
        this.Jaw.addBox(-3.0F, -1.5F, -4.0F, 6, 3, 4, 0.0F);
        this.Antler_l = new ModelRenderer(this, 56, 33);
        this.Antler_l.mirror = true;
        this.Antler_l.setPos(2.0F, -8.0F, 0.0F);
        this.Antler_l.addBox(0.0F, -8.0F, 0.0F, 8, 8, 0, 0.0F);
        this.setRotateAngle(Antler_l, -0.6829473363053812F, 0.0F, 0.0F);
        
        this.head.addChild(this.Antler_r);
        this.head.addChild(this.Snout);
        this.head.addChild(this.Jaw);
        this.head.addChild(this.Antler_l);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
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
