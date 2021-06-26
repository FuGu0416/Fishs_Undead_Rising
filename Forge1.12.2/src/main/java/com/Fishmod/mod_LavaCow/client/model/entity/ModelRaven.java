package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelRaven - Mojang, edited by Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelRaven extends ModelBase {

    public ModelRenderer body;
    public ModelRenderer wingLeft;
    public ModelRenderer tail;
    public ModelRenderer head;
    public ModelRenderer legRight;
    public ModelRenderer wingRight;
    public ModelRenderer legLeft;
    public ModelRenderer head2;
    public ModelRenderer beak1;
    public ModelRenderer beak2;
    public ModelRenderer feather;
    private ModelRaven.State state = ModelRaven.State.STANDING;

    public ModelRaven() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.wingLeft = new ModelRenderer(this, 19, 8);
        this.wingLeft.mirror = true;
        this.wingLeft.setRotationPoint(1.5F, 16.94F, -2.76F);
        this.wingLeft.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(wingLeft, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F);
        this.beak1 = new ModelRenderer(this, 19, 17);
        this.beak1.setRotationPoint(0.0F, -0.5F, -2.0F);
        this.beak1.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.wingRight = new ModelRenderer(this, 19, 8);
        this.wingRight.setRotationPoint(-1.5F, 16.94F, -2.76F);
        this.wingRight.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(wingRight, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F);
        this.legLeft = new ModelRenderer(this, 14, 18);
        this.legLeft.setRotationPoint(1.0F, 22.0F, -1.05F);
        this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legLeft, 0.6682865705886288F, 0.0F, 0.0F);
        this.feather = new ModelRenderer(this, 2, 18);
        this.feather.setRotationPoint(0.0F, -2.15F, 0.15F);
        this.feather.addBox(0.0F, -4.0F, -2.0F, 0, 5, 4, 0.0F);
        this.setRotateAngle(feather, -0.2214822820780804F, 0.0F, 0.0F);
        this.beak2 = new ModelRenderer(this, 19, 22);
        this.beak2.setRotationPoint(0.0F, 0.5F, -2.0F);
        this.beak2.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.head = new ModelRenderer(this, 2, 2);
        this.head.setRotationPoint(0.0F, 14.59F, -2.76F);
        this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 4, 2, 0.0F);
        this.body = new ModelRenderer(this, 2, 8);
        this.body.setRotationPoint(0.0F, 16.5F, -3.0F);
        this.body.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.setRotateAngle(body, 0.49375364538919575F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 14, 18);
        this.legRight.setRotationPoint(-1.0F, 22.0F, -1.05F);
        this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legRight, 0.6682865705886288F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 22, 1);
        this.tail.setRotationPoint(0.0F, 21.07F, 1.16F);
        this.tail.addBox(-1.5F, -1.0F, -1.0F, 3, 4, 1, 0.0F);
        this.setRotateAngle(tail, 1.015083492959902F, 0.0F, 0.0F);
        this.head2 = new ModelRenderer(this, 10, 0);
        this.head2.setRotationPoint(0.0F, -0.5F, -1.0F);
        this.head2.addBox(-1.0F, -1.0F, -1.0F, 2, 3, 1, 0.0F);
        this.head.addChild(this.beak1);
        this.head.addChild(this.feather);
        this.head.addChild(this.beak2);
        this.head.addChild(this.head2);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {         
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.wingLeft.render(scale);
        this.wingRight.render(scale);
        this.legLeft.render(scale);
        this.head.render(scale);
        this.body.render(scale);
        this.legRight.render(scale);
        this.tail.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float f = 0.0F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleZ = 0.0F;
        this.head.rotationPointX = 0.0F;
        this.body.rotationPointX = 0.0F;
        this.tail.rotationPointX = 0.0F;
        this.wingRight.rotationPointX = -1.5F;
        this.wingLeft.rotationPointX = 1.5F;

        if (this.state != ModelRaven.State.FLYING)
        {
            if (this.state == ModelRaven.State.SITTING)
            {
                return;
            }

            if (this.state == ModelRaven.State.PARTY)
            {
                float f1 = MathHelper.cos(ageInTicks);
                float f2 = MathHelper.sin(ageInTicks);
                this.head.rotationPointX = f1;
                this.head.rotationPointY = 15.69F + f2;
                this.head.rotateAngleX = 0.0F;
                this.head.rotateAngleY = 0.0F;
                this.head.rotateAngleZ = MathHelper.sin((float)entityIn.ticksExisted) * 0.4F;
                this.body.rotationPointX = f1;
                this.body.rotationPointY = 16.5F + f2;
                this.wingLeft.rotationPointX = 1.5F + f1;
                this.wingLeft.rotationPointY = 16.94F + f2;
                this.wingRight.rotationPointX = -1.5F + f1;
                this.wingRight.rotationPointY = 16.94F + f2;
                this.tail.rotationPointX = f1;
                this.tail.rotationPointY = 21.07F + f2;
                return;
            }
            this.beak2.rotateAngleX = 0.0F;
            this.wingLeft.rotateAngleZ = -0.0873F;
            this.wingRight.rotateAngleZ = 0.0873F;
            this.legLeft.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legRight.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        }
        else {
	        this.head.rotationPointY = 15.69F + f;
	        if(((EntityLivingBase) entityIn).getHeldItemMainhand().isEmpty())
	        	this.beak2.rotateAngleX = 0.3662880558742251F;
	        this.tail.rotateAngleX = 1.015F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
	        this.tail.rotationPointY = 21.07F + f;
	        this.body.rotationPointY = 16.5F + f;
	        this.wingLeft.rotateAngleZ = -0.0873F - ageInTicks;
	        this.wingLeft.rotationPointY = 16.94F + f;
	        this.wingRight.rotateAngleZ = 0.0873F + ageInTicks;
	        this.wingRight.rotationPointY = 16.94F + f;
	        this.legLeft.rotationPointY = 22.0F + f;
	        this.legRight.rotationPointY = 22.0F + f;
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        this.feather.rotateAngleX = -0.2214F;
        this.body.rotateAngleX = 0.4937F;
        this.wingLeft.rotateAngleX = -((float)Math.PI * 2F / 9F);
        this.wingLeft.rotateAngleY = -(float)Math.PI;
        this.wingRight.rotateAngleX = -((float)Math.PI * 2F / 9F);
        this.wingRight.rotateAngleY = -(float)Math.PI;
        this.legLeft.rotateAngleX = -0.0299F;
        this.legRight.rotateAngleX = -0.0299F;
        this.legLeft.rotationPointY = 22.0F;
        this.legRight.rotationPointY = 22.0F;

        if (entitylivingbaseIn instanceof EntityRaven)
        {
            EntityRaven entityparrot = (EntityRaven)entitylivingbaseIn;

            if (entityparrot.isPartying())
            {
                this.wingRight.rotateAngleZ = 0.0873F;
            	this.legLeft.rotateAngleZ = -0.34906584F;
                this.legRight.rotateAngleZ = 0.34906584F;
                this.state = ModelRaven.State.PARTY;
                return;
            }

            if (entityparrot.isFlying())
            {
                this.legLeft.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.legRight.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.state = ModelRaven.State.FLYING;
            }
            else if (entityparrot.isSitting())
            {
                //float f = 1.9F;
                this.head.rotationPointY = 17.59F;
                this.tail.rotateAngleX = 1.5388988F;
                this.tail.rotationPointY = 22.97F;
                this.body.rotationPointY = 18.4F;
                this.wingLeft.rotateAngleZ = -0.0873F;
                this.wingLeft.rotationPointY = 18.84F;
                this.wingRight.rotateAngleZ = 0.0873F;
                this.wingRight.rotationPointY = 18.84F;
                ++this.legLeft.rotationPointY;
                ++this.legRight.rotationPointY;
                ++this.legLeft.rotateAngleX;
                ++this.legRight.rotateAngleX;
                this.state = ModelRaven.State.SITTING;
            }
            else
            {
                this.state = ModelRaven.State.STANDING;
            }

            this.legLeft.rotateAngleZ = 0.0F;
            this.legRight.rotateAngleZ = 0.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    static enum State
    {
        FLYING,
        STANDING,
        SITTING,
        PARTY;
    }
}
