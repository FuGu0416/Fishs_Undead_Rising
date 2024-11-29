package com.Fishmod.mod_LavaCow.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelSwineHelm - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class ModelSwineMask<T extends LivingEntity> extends BipedModel<T> {
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

    private ResourceLocation tex;
    
    public ModelSwineMask(float scale, String tex) {
    	super(scale, 0, 64, 64);
    	this.tex = new ResourceLocation(tex);
        this.helmLeft = new ModelRenderer(this, 0, 38);
        this.helmLeft.mirror = true;
        this.helmLeft.setPos(0.0F, 0.0F, 0.0F);
        this.helmLeft.addBox(3.5F, -5.0F, -5.5F, 1, 5, 3, 0.0F);
        this.Mask_glass_r = new ModelRenderer(this, 9, 43);
        this.Mask_glass_r.setPos(0.0F, 0.0F, 0.0F);
        this.Mask_glass_r.addBox(-3.5F, -5.0F, -5.5F, 3, 3, 1, 0.0F);
        this.Mask_Base = new ModelRenderer(this, 0, 32);
        this.Mask_Base.setPos(0.0F, 0.0F, 0.0F);
        this.Mask_Base.addBox(-3.5F, -2.0F, -5.5F, 7, 2, 1, 0.0F);
        this.Tusk_l = new ModelRenderer(this, 14, 38);
        this.Tusk_l.mirror = true;
        this.Tusk_l.setPos(2.0F, -1.0F, -5.5F);
        this.Tusk_l.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk_l, 0.5009094953223726F, 0.0F, 0.6373942428283291F);
        this.Snout = new ModelRenderer(this, 18, 32);
        this.Snout.setPos(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-2.0F, -3.0F, -7.5F, 4, 3, 2, 0.0F);
        this.Mask_ridge = new ModelRenderer(this, 9, 38);
        this.Mask_ridge.setPos(0.0F, 0.0F, 0.0F);
        this.Mask_ridge.addBox(-0.5F, -5.0F, -5.5F, 1, 3, 1, 0.0F);
        this.Mask_glass_l = new ModelRenderer(this, 9, 43);
        this.Mask_glass_l.setPos(0.0F, 0.0F, 0.0F);
        this.Mask_glass_l.addBox(0.5F, -5.0F, -5.5F, 3, 3, 1, 0.0F);
        this.helmRight = new ModelRenderer(this, 0, 38);
        this.helmRight.setPos(0.0F, 0.0F, 0.0F);
        this.helmRight.addBox(-4.5F, -5.0F, -5.5F, 1, 5, 3, 0.0F);
        this.Tusk_r = new ModelRenderer(this, 14, 38);
        this.Tusk_r.mirror = true;
        this.Tusk_r.setPos(-2.0F, -1.0F, -5.5F);
        this.Tusk_r.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk_r, 0.5009094953223726F, 0.0F, -0.6373942428283291F);
        this.Ear1 = new ModelRenderer(this, 0, 49);
        this.Ear1.setPos(-2.0F, -7.7F, -2.0F);
        this.Ear1.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Ear1, 0.136659280431156F, 0.36425021489121656F, -0.22759093446006054F);
        this.Ear2 = new ModelRenderer(this, 0, 49);
        this.Ear2.mirror = true;
        this.Ear2.setPos(2.0F, -7.7F, -2.0F);
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
        this.head.addChild(this.Mask_Base); 
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        super.renderToBuffer(matrixStackIn, Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.entityTranslucent(this.tex)), packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
