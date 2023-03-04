package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class BeelzebubModel<T extends BeelzebubEntity> extends FlyingBaseModel<T> implements IHasHead {
	private final ModelRenderer Throax_base;
	private final ModelRenderer Head;
	private final ModelRenderer Cheek_r;
	private final ModelRenderer Cheek_l;
	private final ModelRenderer Eye_r;
	private final ModelRenderer Eye_l;
	private final ModelRenderer Jaw_r;
	private final ModelRenderer Jaw_l;
	private final ModelRenderer Throax_0;
	private final ModelRenderer saddle;
	private final ModelRenderer saddle_top;
	private final ModelRenderer Wing_0_l;
	private final ModelRenderer Wing_0_r;
	private final ModelRenderer leg_l_0;
	private final ModelRenderer leg_l_0_1;
	private final ModelRenderer leg_l_1;
	private final ModelRenderer leg_l_2;
	private final ModelRenderer leg_r_0;
	private final ModelRenderer leg_r_0_1;
	private final ModelRenderer leg_r_1;
	private final ModelRenderer leg_r_2;
	private final ModelRenderer Waist;
	private final ModelRenderer UAbdomen1;
	private final ModelRenderer UAbdomen2;
	private final ModelRenderer UAbdomen3;
	public boolean isHarvestable = false;
	
	public BeelzebubModel() {
		this.texWidth = 128;
		this.texHeight = 128;

		this.Throax_base = new ModelRenderer(this);
		this.Throax_base.setPos(0.0F, 4.0F, 7.5F);
		this.Throax_base.texOffs(57, 25).addBox(-5.5F, -5.5F, -4.0F, 11.0F, 11.0F, 8.0F, 0.0F, true);

		this.Head = new ModelRenderer(this);
		this.Head.setPos(0.0F, 2.0F, -8.0F);
		this.Throax_base.addChild(this.Head);
		this.Head.texOffs(0, 0).addBox(-4.0F, -4.0F, -12.0F, 8.0F, 8.0F, 12.0F, 0.0F, false);

		this.Cheek_r = new ModelRenderer(this);
		this.Cheek_r.setPos(4.0F, 0.0F, -4.0F);
		this.Head.addChild(this.Cheek_r);
		this.Cheek_r.texOffs(40, 0).addBox(0.0F, -2.0F, -4.0F, 4.0F, 6.0F, 8.0F, 0.0F, false);

		this.Cheek_l = new ModelRenderer(this);
		this.Cheek_l.setPos(-4.0F, 0.0F, -4.0F);
		this.Head.addChild(this.Cheek_l);
		this.Cheek_l.texOffs(40, 0).addBox(-4.0F, -2.0F, -4.0F, 4.0F, 6.0F, 8.0F, 0.0F, true);

		this.Eye_r = new ModelRenderer(this);
		this.Eye_r.setPos(4.0F, -1.0F, -10.0F);
		this.Head.addChild(Eye_r);
		this.Eye_r.texOffs(8, 22).addBox(0.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		this.Eye_l = new ModelRenderer(this);
		this.Eye_l.setPos(-4.0F, -1.0F, -10.0F);
		this.Head.addChild(this.Eye_l);
		this.Eye_l.texOffs(8, 22).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		this.Jaw_r = new ModelRenderer(this);
		this.Jaw_r.setPos(4.0F, 3.0F, -11.3F);
		this.Head.addChild(this.Jaw_r);
		this.setRotateAngle(this.Jaw_r, -0.2731F, -1.0928F, -0.0911F);
		this.Jaw_r.texOffs(25, 22).addBox(-4.5F, 0.0F, -1.5F, 6.0F, 3.0F, 3.0F, 0.0F, false);

		this.Jaw_l = new ModelRenderer(this);
		this.Jaw_l.setPos(-4.0F, 3.0F, -11.3F);
		this.Head.addChild(this.Jaw_l);
		this.setRotateAngle(this.Jaw_l, -0.2731F, 1.0928F, 0.0911F);
		this.Jaw_l.texOffs(25, 22).addBox(-1.5F, 0.0F, -1.5F, 6.0F, 3.0F, 3.0F, 0.0F, true);

		this.Throax_0 = new ModelRenderer(this);
		this.Throax_0.setPos(0.0F, 0.0F, -5.0F);
		this.Throax_base.addChild(this.Throax_0);
		this.setRotateAngle(this.Throax_0, 0.2731F, 0.0F, 0.0F);
		this.Throax_0.texOffs(0, 44).addBox(-6.5F, -6.0F, -4.0F, 13.0F, 12.0F, 8.0F, 0.0F, false);

		this.saddle = new ModelRenderer(this);
		this.saddle.setPos(-0.5F, 12.0F, 1.5F);
		this.Throax_0.addChild(this.saddle);
		this.saddle.texOffs(0, 64).addBox(-5.5F, -19.0F, -5.0F, 12.0F, 1.0F, 7.0F, 0.0F, false);

		this.saddle_top = new ModelRenderer(this);
		this.saddle_top.setPos(0.0F, -18.5F, -1.5F);
		this.saddle.addChild(this.saddle_top);
		this.saddle_top.texOffs(31, 64).addBox(-5.5F, -2.5F, 2.5F, 12.0F, 2.0F, 1.0F, 0.0F, false);
		
		this.Wing_0_l = new ModelRenderer(this);
		this.Wing_0_l.setPos(6.5F, -4.0F, -8.0F);
		this.Throax_base.addChild(this.Wing_0_l);
		this.setRotateAngle(this.Wing_0_l, 0.0F, 0.2276F, 0.0F);
		this.Wing_0_l.texOffs(0, 31).addBox(0.0F, 0.0F, -1.0F, 24.0F, 0.0F, 9.0F, 0.0F, true);

		this.Wing_0_r = new ModelRenderer(this);
		this.Wing_0_r.setPos(-6.5F, -4.0F, -8.0F);
		this.Throax_base.addChild(this.Wing_0_r);
		this.setRotateAngle(this.Wing_0_r, 0.0F, -0.2276F, 0.0F);
		this.Wing_0_r.texOffs(0, 31).addBox(-24.0F, 0.0F, -1.0F, 24.0F, 0.0F, 9.0F, 0.0F, false);

		this.leg_l_0 = new ModelRenderer(this);
		this.leg_l_0.setPos(4.0F, 6.0F, -4.5F);
		this.Throax_base.addChild(this.leg_l_0);
		this.setRotateAngle(this.leg_l_0, 0.0F, 0.7854F, 0.7854F);
		this.leg_l_0.texOffs(56, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		this.leg_l_0_1 = new ModelRenderer(this);
		this.leg_l_0_1.setPos(9.3F, 0.0F, 0.0F);
		this.leg_l_0.addChild(this.leg_l_0_1);
		this.setRotateAngle(this.leg_l_0_1, 0.0F, 0.0F, 1.3203F);
		this.leg_l_0_1.texOffs(44, 16).addBox(-2.0F, -4.0F, -2.0F, 12.0F, 4.0F, 4.0F, 0.0F, false);

		this.leg_l_1 = new ModelRenderer(this);
		this.leg_l_1.setPos(4.0F, 6.0F, -3.5F);
		this.Throax_base.addChild(this.leg_l_1);
		this.setRotateAngle(this.leg_l_1, 0.0F, 0.3927F, 0.5812F);
		this.leg_l_1.texOffs(56, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		this.leg_l_2 = new ModelRenderer(this);
		this.leg_l_2.setPos(4.0F, 6.0F, -2.5F);
		this.Throax_base.addChild(this.leg_l_2);
		this.setRotateAngle(this.leg_l_2, 0.0F, -0.1367F, 0.5812F);
		this.leg_l_2.texOffs(56, 0).addBox(-1.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		this.leg_r_0 = new ModelRenderer(this);
		this.leg_r_0.setPos(-4.0F, 6.0F, -4.5F);
		this.Throax_base.addChild(this.leg_r_0);
		this.setRotateAngle(this.leg_r_0, 0.0F, -0.7854F, -0.7854F);
		this.leg_r_0.texOffs(56, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		this.leg_r_0_1 = new ModelRenderer(this);
		this.leg_r_0_1.setPos(-9.3F, 0.0F, 0.0F);
		this.leg_r_0.addChild(this.leg_r_0_1);
		this.setRotateAngle(this.leg_r_0_1, 0.0F, 0.0F, -1.3203F);
		this.leg_r_0_1.texOffs(44, 16).addBox(-10.0F, -4.0F, -2.0F, 12.0F, 4.0F, 4.0F, 0.0F, true);

		this.leg_r_1 = new ModelRenderer(this);
		this.leg_r_1.setPos(-4.0F, 6.0F, -3.5F);
		this.Throax_base.addChild(this.leg_r_1);
		this.setRotateAngle(this.leg_r_1, 0.0F, -0.3927F, -0.5812F);
		this.leg_r_1.texOffs(56, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		this.leg_r_2 = new ModelRenderer(this);
		this.leg_r_2.setPos(-4.0F, 6.0F, -2.5F);
		this.Throax_base.addChild(this.leg_r_2);
		this.setRotateAngle(this.leg_r_2, 0.0F, 0.3927F, -0.5812F);
		this.leg_r_2.texOffs(56, 0).addBox(-11.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		this.Waist = new ModelRenderer(this);
		this.Waist.setPos(0.0F, 0.0F, 3.0F);
		this.Throax_base.addChild(this.Waist);
		this.Waist.texOffs(98, 49).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 5.0F, 0.0F, false);

		this.UAbdomen1 = new ModelRenderer(this);
		this.UAbdomen1.setPos(0.0F, 0.0F, 3.0F);
		this.Waist.addChild(this.UAbdomen1);
		this.setRotateAngle(this.UAbdomen1, -0.1367F, 0.0F, 0.0F);
		this.UAbdomen1.texOffs(42, 44).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 8.0F, 0.0F, false);

		this.UAbdomen2 = new ModelRenderer(this);
		this.UAbdomen2.setPos(0.0F, 0.0F, 6.0F);
		this.UAbdomen1.addChild(this.UAbdomen2);
		this.setRotateAngle(this.UAbdomen2, -0.2276F, 0.0F, 0.0F);
		this.UAbdomen2.texOffs(80, 0).addBox(-7.0F, -7.0F, 0.0F, 14.0F, 14.0F, 10.0F, 0.0F, false);

		this.UAbdomen3 = new ModelRenderer(this);
		this.UAbdomen3.setPos(0.0F, 0.0F, 8.0F);
		this.UAbdomen2.addChild(this.UAbdomen3);
		this.setRotateAngle(this.UAbdomen3, -0.2276F, 0.0F, 0.0F);
		this.UAbdomen3.texOffs(96, 28).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 10.0F, 6.0F, 0.0F, false);
	}

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Throax_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
        
        if(this.isHarvestable) {
        	matrixStackIn.pushPose();
        	matrixStackIn.scale(1.05F, 1.3F, 1.05F);
        	this.Throax_base.translateAndRotate(matrixStackIn);
        	this.Waist.translateAndRotate(matrixStackIn);
        	matrixStackIn.translate(0.0D, -0.2D, -0.05D);
        	this.UAbdomen1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        	matrixStackIn.popPose();
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {  	 	  	
    	float vibrate_rate = 0.5F;
    	float i = (float)entityIn.getSpellTicks() / 30.0F;
    	
    	super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);    	
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.SwingY_Sin(this.Jaw_l, 1.0928F, ageInTicks, 0.125F, 0.4F, true, 0.0F);
    	this.SwingY_Sin(this.Jaw_r, -1.0928F, ageInTicks, 0.125F, 0.4F, false, 0.0F);
    	
    	this.Wing_0_r.xRot = 0.0F;
    	this.Wing_0_r.yRot = -0.22759093446006054F;
    	this.Wing_0_r.zRot = 0.5F * MathHelper.sin(4.0F * ageInTicks);
    	this.Wing_0_l.xRot = 0.0F;
    	this.Wing_0_l.yRot = 0.22759093446006054F;
    	this.Wing_0_l.zRot = 0.5F * MathHelper.sin(4.0F * ageInTicks + (float)Math.PI);
      
    	this.Throax_base.y = 7.0F + (entityIn.isVehicle() ? 5.0F : 5.0F * MathHelper.sin(ageInTicks * vibrate_rate));  	    	
    	
    	this.setRotateAngle(this.leg_r_0, 0.0F, -0.7854F, -0.7354F + 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI));
    	this.setRotateAngle(this.leg_r_1, 0.0F, 0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, -1.593485607070823F);
    	this.setRotateAngle(this.leg_r_2, 0.0F, 1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, -1.3658946726107624F);
    	this.setRotateAngle(this.leg_l_0, 0.0F, 0.7854F, 0.7354F - 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI));
    	this.setRotateAngle(this.leg_l_1, 0.0F, -0.7740535232594852F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI, 1.593485607070823F);
    	this.setRotateAngle(this.leg_l_2, 0.0F, -1.2292353921796064F + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI, 1.3658946726107624F);    
 
		this.leg_r_0_1.zRot = -1.2703F + 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.6F * (float)Math.PI);
		this.leg_l_0_1.zRot = 1.2703F - 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.7F * (float)Math.PI);

    	this.Waist.zRot = 0.0F;
    	this.UAbdomen1.zRot = 0.0F;
    	this.UAbdomen2.zRot = 0.0F;
    	this.UAbdomen3.zRot = 0.0F;
    	
    	if (i > 0.0F) {	    		    	
	    	this.Waist.zRot = 0.1F * MathHelper.sin(ageInTicks * vibrate_rate);
	    	this.UAbdomen1.zRot = -0.1F * MathHelper.sin(ageInTicks * vibrate_rate);
	    	this.UAbdomen2.zRot = 0.1F * MathHelper.sin(ageInTicks * vibrate_rate);
	    	this.UAbdomen3.zRot = -0.1F * MathHelper.sin(ageInTicks * vibrate_rate);    
    	} else {
	    	this.Waist.zRot = 0.0F;
	    	this.UAbdomen1.zRot = 0.0F;
	    	this.UAbdomen2.zRot = 0.0F;
	    	this.UAbdomen3.zRot = 0.0F;
    	}
    	
    	if (this.state.equals(FlyingBaseModel.State.WAITING)) {
    		vibrate_rate = 0.05F;
    		
    		this.Throax_base.y = 12.0F;
 
	    	this.Waist.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.xRot = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.xRot = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;
	    	
    		this.setRotateAngle(this.Wing_0_r, 0.8600982340775168F, 1.2971286947558636F, -0.5082398928281348F);
    		this.setRotateAngle(this.Wing_0_l, 0.8600982340775168F, -1.2971286947558636F, 0.5082398928281348F);
 
    		this.setRotateAngle(this.leg_r_0, -0.6863F, -0.4176F, 0.3257F);
	    	this.setRotateAngle(this.leg_r_1, 0.0F, -0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg_r_2, 0.0F, 0.39269908169872414F, -0.5811946409141118F);
	    	this.setRotateAngle(this.leg_l_0, -0.6863F, 0.4176F, -0.3257F);
	    	this.setRotateAngle(this.leg_l_1, 0.0F, 0.39269908169872414F, 0.5811946409141118F);
	    	this.setRotateAngle(this.leg_l_2, 0.0F, -0.39269908169872414F, 0.5811946409141118F);

	    	this.setRotateAngle(this.leg_r_0_1, 0.0F, 0.0F, -1.3203F);
	    	this.setRotateAngle(this.leg_l_0_1, 0.0F, 0.0F, 1.3203F);
	    	
			this.leg_r_0_1.zRot = -1.2703F + 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.6F * (float)Math.PI);
			this.leg_l_0_1.zRot = 1.2703F - 0.05F * MathHelper.cos(ageInTicks * vibrate_rate + 0.7F * (float)Math.PI);
			
	        this.leg_r_1.zRot = -0.58119464F;
	        this.leg_l_1.zRot = 0.58119464F;
	        this.leg_r_2.zRot = -0.58119464F;
	        this.leg_l_2.zRot = 0.58119464F;
	        this.leg_r_1.yRot = -0.39269908F;
	        this.leg_l_1.yRot = 0.39269908F;
	        this.leg_r_2.yRot = 0.39269908F;
	        this.leg_l_2.yRot = -0.39269908F;
	        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
	        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
	        this.leg_r_1.yRot += f4;
	        this.leg_l_1.yRot += -f4;
	        this.leg_r_2.yRot += f5;
	        this.leg_l_2.yRot += -f5;
	        this.leg_r_1.zRot += f8;
	        this.leg_l_1.zRot += -f8;
	        this.leg_r_2.zRot += f9;
	        this.leg_l_2.zRot += -f9;	
    	} else if (this.state.equals(FlyingBaseModel.State.FLYING) || this.state.equals(FlyingBaseModel.State.ATTACKING)) {
	    	this.Waist.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.xRot = 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.xRot = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.xRot = 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;	   
    	} else if (this.state.equals(FlyingBaseModel.State.HOVERING)) {
	    	this.Waist.xRot = -0.014F * (20 - entityIn.getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.5F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen1.xRot = -0.014F * (20 - entityIn.getHoverTimer()) + 0.02F * MathHelper.cos(ageInTicks * vibrate_rate + 0.10F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen2.xRot = -0.014F * (20 - entityIn.getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.15F * (float)Math.PI) * (float)Math.PI;
	    	this.UAbdomen3.xRot = -0.014F * (20 - entityIn.getHoverTimer()) + 0.01F * MathHelper.cos(ageInTicks * vibrate_rate + 0.20F * (float)Math.PI) * (float)Math.PI;    
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}