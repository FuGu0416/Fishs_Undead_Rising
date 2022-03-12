package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelRaven - Mojang, edited by Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class RavenModel<T extends RavenEntity> extends FURBaseModel<T> {
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
    private RavenModel.State state = RavenModel.State.STANDING;

    public RavenModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.wingLeft = new ModelRenderer(this, 19, 8);
        this.wingLeft.mirror = true;
        this.wingLeft.setPos(1.5F, 16.94F, -2.76F);
        this.wingLeft.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(wingLeft, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F);
        this.beak1 = new ModelRenderer(this, 19, 17);
        this.beak1.setPos(0.0F, -0.5F, -2.0F);
        this.beak1.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.wingRight = new ModelRenderer(this, 19, 8);
        this.wingRight.setPos(-1.5F, 16.94F, -2.76F);
        this.wingRight.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3, 0.0F);
        this.setRotateAngle(wingRight, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F);
        this.legLeft = new ModelRenderer(this, 14, 18);
        this.legLeft.setPos(1.0F, 22.0F, -1.05F);
        this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legLeft, 0.6682865705886288F, 0.0F, 0.0F);
        this.feather = new ModelRenderer(this, 2, 18);
        this.feather.setPos(0.0F, -2.15F, 0.15F);
        this.feather.addBox(0.0F, -4.0F, -2.0F, 0, 5, 4, 0.0F);
        this.setRotateAngle(feather, -0.2214822820780804F, 0.0F, 0.0F);
        this.beak2 = new ModelRenderer(this, 19, 22);
        this.beak2.setPos(0.0F, 0.5F, -2.0F);
        this.beak2.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.head = new ModelRenderer(this, 2, 2);
        this.head.setPos(0.0F, 14.59F, -2.76F);
        this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 4, 2, 0.0F);
        this.body = new ModelRenderer(this, 2, 8);
        this.body.setPos(0.0F, 16.5F, -3.0F);
        this.body.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.setRotateAngle(body, 0.49375364538919575F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 14, 18);
        this.legRight.setPos(-1.0F, 22.0F, -1.05F);
        this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(legRight, 0.6682865705886288F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 22, 1);
        this.tail.setPos(0.0F, 21.07F, 1.16F);
        this.tail.addBox(-1.5F, -1.0F, -1.0F, 3, 4, 1, 0.0F);
        this.setRotateAngle(tail, 1.015083492959902F, 0.0F, 0.0F);
        this.head2 = new ModelRenderer(this, 10, 0);
        this.head2.setPos(0.0F, -0.5F, -1.0F);
        this.head2.addBox(-1.0F, -1.0F, -1.0F, 2, 3, 1, 0.0F);
        this.head.addChild(this.beak1);
        this.head.addChild(this.feather);
        this.head.addChild(this.beak2);
        this.head.addChild(this.head2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {        
    	ImmutableList.of(this.wingLeft, this.wingRight, this.legLeft, this.head, this.body, this.legRight, this.tail).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 0.0F;
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.zRot = 0.0F;
        this.head.x = 0.0F;
        this.body.x = 0.0F;
        this.tail.x = 0.0F;
        this.wingRight.x = -1.5F;
        this.wingLeft.x = 1.5F;

        if (this.state != RavenModel.State.FLYING)
        {
            if (this.state == RavenModel.State.SITTING)
            {
                return;
            }

            if (this.state == RavenModel.State.PARTY)
            {
                float f1 = MathHelper.cos(ageInTicks);
                float f2 = MathHelper.sin(ageInTicks);
                this.head.x = f1;
                this.head.y = 15.69F + f2;
                this.head.xRot = 0.0F;
                this.head.yRot = 0.0F;
                this.head.zRot = MathHelper.sin((float)entityIn.tickCount) * 0.4F;
                this.body.x = f1;
                this.body.y = 16.5F + f2;
                this.wingLeft.x = 1.5F + f1;
                this.wingLeft.y = 16.94F + f2;
                this.wingRight.x = -1.5F + f1;
                this.wingRight.y = 16.94F + f2;
                this.tail.x = f1;
                this.tail.y = 21.07F + f2;
                return;
            }
            this.beak2.xRot = 0.0F;
            this.wingLeft.zRot = -0.0873F;
            this.wingRight.zRot = 0.0873F;
            this.legLeft.xRot += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legRight.xRot += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        }
        else {
	        this.head.y = 15.69F + f;
	        if(entityIn.getMainHandItem().isEmpty())
	        	this.beak2.xRot = 0.3662880558742251F;
	        this.tail.xRot = 1.015F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
	        this.tail.y = 21.07F + f;
	        this.body.y = 16.5F + f;
	        this.wingLeft.zRot = -0.0873F - ageInTicks;
	        this.wingLeft.y = 16.94F + f;
	        this.wingRight.zRot = 0.0873F + ageInTicks;
	        this.wingRight.y = 16.94F + f;
	        this.legLeft.y = 22.0F + f;
	        this.legRight.y = 22.0F + f;
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
        this.feather.xRot = -0.2214F;
        this.body.xRot = 0.4937F;
        this.wingLeft.xRot = -((float)Math.PI * 2F / 9F);
        this.wingLeft.yRot = -(float)Math.PI;
        this.wingRight.xRot = -((float)Math.PI * 2F / 9F);
        this.wingRight.yRot = -(float)Math.PI;
        this.legLeft.xRot = -0.0299F;
        this.legRight.xRot = -0.0299F;
        this.legLeft.y = 22.0F;
        this.legRight.y = 22.0F;

        if (entityIn.isPartyParrot())
        {
            this.wingRight.zRot = 0.0873F;
        	this.legLeft.zRot = -0.34906584F;
            this.legRight.zRot = 0.34906584F;
            this.state = RavenModel.State.PARTY;
            return;
        }

        if (entityIn.isFlying())
        {
            this.legLeft.xRot += ((float)Math.PI * 2F / 9F);
            this.legRight.xRot += ((float)Math.PI * 2F / 9F);
            this.state = RavenModel.State.FLYING;
        }
        else if (entityIn.isInSittingPose())
        {
            //float f = 1.9F;
            this.head.y = 17.59F;
            this.tail.xRot = 1.5388988F;
            this.tail.y = 22.97F;
            this.body.y = 18.4F;
            this.wingLeft.zRot = -0.0873F;
            this.wingLeft.y = 18.84F;
            this.wingRight.zRot = 0.0873F;
            this.wingRight.y = 18.84F;
            ++this.legLeft.y;
            ++this.legRight.y;
            ++this.legLeft.xRot;
            ++this.legRight.xRot;
            this.state = RavenModel.State.SITTING;
        }
        else
        {
            this.state = RavenModel.State.STANDING;
        }

        this.legLeft.zRot = 0.0F;
        this.legRight.zRot = 0.0F;
    }

    static enum State
    {
        FLYING,
        STANDING,
        SITTING,
        PARTY;
    }
}
