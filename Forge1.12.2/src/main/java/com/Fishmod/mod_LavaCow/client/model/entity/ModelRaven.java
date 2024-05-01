package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
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
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.setupAnim(getState((EntityRaven)entityIn), entityIn.ticksExisted, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

    	if (((EntityLivingBase) entityIn).getHeldItemMainhand().isEmpty()) {        	
            if (((EntityRaven) entityIn).callTimer > 0) {
            	this.beak1.rotateAngleX = -0.18F;
            	this.beak1.rotationPointY = -0.7F;
            	this.beak2.rotateAngleX = 0.18F;
            	this.beak2.rotationPointY = 1.0F;
            } else {
            	this.beak1.rotateAngleX = 0.0F;
            	this.beak1.rotationPointY = -0.5F;
            	this.beak2.rotateAngleX = 0.0F;
            	this.beak2.rotationPointY = 0.5F;
            }                  	
    	} else if (((EntityLivingBase) entityIn).getHeldItemMainhand().getItem() instanceof ItemBlock) {	        	
        	this.beak1.rotateAngleX = -0.2037433592119174F;
        	this.beak1.rotationPointY = -0.7F;
        	this.beak2.rotateAngleX = 0.08552113334772216F;
        	this.beak2.rotationPointY = 1.0F;
        } else {
        	this.beak1.rotateAngleX = 0.0F;
        	this.beak1.rotationPointY = -0.5F;
        	this.beak2.rotateAngleX = 0.0F;
        	this.beak2.rotationPointY = 0.5F;	        	
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    	this.prepare(getState((EntityRaven)entityIn));
    }
    
    private void setupAnim(ModelRaven.State p_217162_1_, int p_217162_2_, float p_217162_3_, float p_217162_4_, float p_217162_5_, float p_217162_6_, float p_217162_7_) {
        this.head.rotateAngleX = p_217162_7_ * ((float)Math.PI / 180F);
        this.head.rotateAngleY = p_217162_6_ * ((float)Math.PI / 180F);
        this.head.rotateAngleZ = 0.0F;
        this.head.rotationPointX = 0.0F;
        this.body.rotationPointX = 0.0F;
        this.tail.rotationPointX = 0.0F;
        this.wingRight.rotationPointX = -1.5F;
        this.wingLeft.rotationPointX = 1.5F;
        switch(p_217162_1_) {
        case SITTING:
        	break;
        case PARTY:
        	float f = MathHelper.cos((float)p_217162_2_);
           	float f1 = MathHelper.sin((float)p_217162_2_);
           	this.head.rotationPointX = f;
           	this.head.rotationPointY = 15.69F + f1;
           	this.head.rotateAngleX = 0.0F;
           	this.head.rotateAngleY = 0.0F;
           	this.head.rotateAngleZ = MathHelper.sin((float)p_217162_2_) * 0.4F;
           	this.body.rotationPointX = f;
           	this.body.rotationPointY = 16.5F + f1;
           	this.wingLeft.rotateAngleZ = -0.0873F - p_217162_5_;
           	this.wingLeft.rotationPointX = 1.5F + f;
           	this.wingLeft.rotationPointY = 16.94F + f1;
           	this.wingRight.rotateAngleZ = 0.0873F + p_217162_5_;
           	this.wingRight.rotationPointX = -1.5F + f;
           	this.wingRight.rotationPointY = 16.94F + f1;
           	this.tail.rotationPointX = f;
           	this.tail.rotationPointY = 21.07F + f1;
           	break;
        case STANDING:
        	this.legLeft.rotateAngleX += MathHelper.cos(p_217162_3_ * 0.6662F) * 1.4F * p_217162_4_;
           	this.legRight.rotateAngleX += MathHelper.cos(p_217162_3_ * 0.6662F + (float)Math.PI) * 1.4F * p_217162_4_;
           	this.head.rotationPointY = 15.69F;
           	this.tail.rotateAngleX = 1.015F + MathHelper.cos(p_217162_3_ * 0.6662F) * 0.3F * p_217162_4_;
           	this.tail.rotationPointY = 21.07F;
           	this.body.rotationPointY = 16.5F;
           	this.wingLeft.rotateAngleZ = -0.0873F;
           	this.wingLeft.rotationPointY = 16.94F;
           	this.wingRight.rotateAngleZ = 0.0873F;
           	this.wingRight.rotationPointY = 16.94F;
           	this.legLeft.rotationPointY = 22.0F;
           	this.legRight.rotationPointY = 22.0F;
           	break;
        case FLYING:
        default:
           	this.head.rotationPointY = 15.69F;
           	this.tail.rotateAngleX = 1.015F + MathHelper.cos(p_217162_3_ * 0.6662F) * 0.3F * p_217162_4_;
           	this.tail.rotationPointY = 21.07F;
           	this.body.rotationPointY = 16.5F;
           	this.wingLeft.rotateAngleZ = -0.0873F - p_217162_5_;
           	this.wingLeft.rotationPointY = 16.94F;
           	this.wingRight.rotateAngleZ = 0.0873F + p_217162_5_;
           	this.wingRight.rotationPointY = 16.94F;
           	this.legLeft.rotationPointY = 22.0F;
           	this.legRight.rotationPointY = 22.0F;
        }
	}
    
	private void prepare(ModelRaven.State p_217160_1_) {
        this.feather.rotateAngleX = -0.2214F;
        this.body.rotateAngleX = 0.4937F;
        this.wingLeft.rotateAngleX = -0.6981F;
        this.wingLeft.rotateAngleY = -(float)Math.PI;
        this.wingRight.rotateAngleX = -0.6981F;
        this.wingRight.rotateAngleY = -(float)Math.PI;
        this.legLeft.rotateAngleX = -0.0299F;
        this.legRight.rotateAngleX = -0.0299F;
        this.legLeft.rotationPointY = 22.0F;
        this.legRight.rotationPointY = 22.0F;
        this.legLeft.rotateAngleZ = 0.0F;
        this.legRight.rotateAngleZ = 0.0F;
        
        switch(p_217160_1_) {
        case SITTING:
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
           	break;
        case PARTY:
        	this.legLeft.rotateAngleZ = -0.34906584F;
           	this.legRight.rotateAngleZ = 0.34906584F;
        case STANDING:
        default:
        	break;
    	case FLYING:
        	this.legLeft.rotateAngleX += 0.6981317F;
        	this.legRight.rotateAngleX += 0.6981317F;
        }

	}
    
	private static ModelRaven.State getState(EntityRaven p_217158_0_) {
		if (p_217158_0_.isPartying()) {
			return ModelRaven.State.PARTY;
    	} else if (p_217158_0_.isSitting()) {
    		return ModelRaven.State.SITTING;
        } else {
        	return p_217158_0_.isFlying() ? ModelRaven.State.FLYING : ModelRaven.State.STANDING;
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
