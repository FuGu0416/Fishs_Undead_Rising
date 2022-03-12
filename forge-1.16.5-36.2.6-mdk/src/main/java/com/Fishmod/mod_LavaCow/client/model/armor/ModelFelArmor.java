package com.Fishmod.mod_LavaCow.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelFelArmor - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class ModelFelArmor<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer horn_l_0;
    public ModelRenderer horn_r_0;
    public ModelRenderer horn_l_1;
    public ModelRenderer horn_l_2;
    public ModelRenderer horn_r_1;
    public ModelRenderer horn_r_2;

    public ModelFelArmor(float modelSize) {
    	super(modelSize, 0, 64, (int) (64 * modelSize/0.9F));
        this.texWidth = 64;
        this.texHeight = (int) (64 * modelSize/0.9F);
        this.horn_l_1 = new ModelRenderer(this, 37, 32);
        this.horn_l_1.mirror = true;
        this.horn_l_1.setPos(-0.2F, 0.0F, -3.5F);
        this.horn_l_1.addBox(-1.0F, -0.5F, -4.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(horn_l_1, -0.4553564018453205F, 0.6373942428283291F, -0.4553564018453205F);
        this.horn_l_2 = new ModelRenderer(this, 47, 32);
        this.horn_l_2.mirror = true;
        this.horn_l_2.setPos(0.0F, 0.0F, -3.7F);
        this.horn_l_2.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(horn_l_2, -0.31869712141416456F, 0.6829473363053812F, 0.0F);
        this.horn_l_0 = new ModelRenderer(this, 50, 32);
        this.horn_l_0.mirror = true;
        this.horn_l_0.setPos(4.2F, -6.0F, 0.0F);
        this.horn_l_0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(horn_l_0, 0.36425021489121656F, -0.7740535232594852F, 0.0F);
        this.horn_r_2 = new ModelRenderer(this, 47, 32);
        this.horn_r_2.setPos(0.0F, 0.0F, -3.7F);
        this.horn_r_2.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(horn_r_2, -0.31869712141416456F, -0.6829473363053812F, 0.0F);
        this.horn_r_0 = new ModelRenderer(this, 50, 32);
        this.horn_r_0.setPos(-4.2F, -6.0F, 0.0F);
        this.horn_r_0.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(horn_r_0, 0.36425021489121656F, 0.7740535232594852F, 0.0F);
        this.horn_r_1 = new ModelRenderer(this, 37, 32);
        this.horn_r_1.setPos(0.2F, 0.0F, -3.5F);
        this.horn_r_1.addBox(-1.0F, -0.5F, -4.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(horn_r_1, -0.4553564018453205F, -0.6373942428283291F, 0.4553564018453205F);
        
        this.horn_l_0.addChild(this.horn_l_1);
        this.horn_l_1.addChild(this.horn_l_2);
        this.head.addChild(this.horn_l_0);
        this.horn_r_1.addChild(this.horn_r_2);
        this.head.addChild(this.horn_r_0);
        this.horn_r_0.addChild(this.horn_r_1);    
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
