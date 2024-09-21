package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.GhostRayEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Made with Blockbench 4.10.4
//Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
@OnlyIn(Dist.CLIENT)
public class GhostRayModel<T extends GhostRayEntity> extends FlyingBaseModel<T> {
	private final ModelRenderer Body_Base;
	private final ModelRenderer Body1;
	private final ModelRenderer Tail0;
	private final ModelRenderer Tail1;
	private final ModelRenderer Tail2;
	private final ModelRenderer back_fin;
	private final ModelRenderer Wing0_r;
	private final ModelRenderer Wing1_r;
	private final ModelRenderer Wing0_l;
	private final ModelRenderer Wing1_l;
	private final ModelRenderer Fin_l;
	private final ModelRenderer Fin_r;
	private final ModelRenderer Dorsel_Fin_l;
	private final ModelRenderer Dorsel_Fin_r;
	
    public GhostRayModel() {
		texWidth = 128;
		texHeight = 128;

		Body_Base = new ModelRenderer(this);
		Body_Base.setPos(0.0F, 10.0F, -1.0F);
		Body_Base.texOffs(0, 0).addBox(-6.0F, -2.0F, -10.0F, 12.0F, 4.0F, 16.0F, 0.0F, false);

		Body1 = new ModelRenderer(this);
		Body1.setPos(0.0F, 0.0F, 7.0F);
		Body_Base.addChild(Body1);
		setRotateAngle(Body1, 0.0911F, 0.0F, 0.0F);
		Body1.texOffs(0, 36).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 4.0F, 0.0F, false);

		Tail0 = new ModelRenderer(this);
		Tail0.setPos(0.0F, 0.0F, 3.0F);
		Body1.addChild(Tail0);
		setRotateAngle(Tail0, 0.182F, 0.0F, 0.0F);
		Tail0.texOffs(34, 32).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 8.0F, 0.0F, false);

		Tail1 = new ModelRenderer(this);
		Tail1.setPos(0.0F, -1.0F, 7.0F);
		Tail0.addChild(Tail1);
		setRotateAngle(Tail1, 0.182F, 0.0F, 0.0F);
		Tail1.texOffs(16, 36).addBox(-0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);

		Tail2 = new ModelRenderer(this);
		Tail2.setPos(0.0F, 0.0F, 0.0F);
		Tail1.addChild(Tail2);
		Tail2.texOffs(30, 0).addBox(-4.5F, 1.5F, 0.0F, 9.0F, 0.0F, 10.0F, 0.0F, false);
		
		back_fin = new ModelRenderer(this);
		back_fin.setPos(0.0F, 1.0F, 1.5F);
		Body1.addChild(back_fin);
		setRotateAngle(back_fin, -0.5672F, 0.0F, 0.0F);
		back_fin.texOffs(0, 0).addBox(-0.5F, -3.0F, -2.5F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		Wing0_r = new ModelRenderer(this);
		Wing0_r.setPos(7.0F, 0.0F, -4.0F);
		Body_Base.addChild(Wing0_r);
		setRotateAngle(Wing0_r, 0.0F, 0.0F, 0.2731F);
		Wing0_r.texOffs(0, 20).addBox(-2.0F, -0.5F, -5.0F, 7.0F, 2.0F, 14.0F, 0.0F, false);

		Wing1_r = new ModelRenderer(this);
		Wing1_r.setPos(5.0F, 0.0F, 0.8F);
		Wing0_r.addChild(Wing1_r);
		setRotateAngle(Wing1_r, 0.0F, 0.0F, 0.1367F);
		Wing1_r.texOffs(28, 20).addBox(-2.0F, 0.0F, -5.0F, 9.0F, 1.0F, 11.0F, 0.0F, false);

		Wing0_l = new ModelRenderer(this);
		Wing0_l.setPos(-6.0F, 0.0F, -4.0F);
		Body_Base.addChild(Wing0_l);
		setRotateAngle(Wing0_l, 0.0F, 0.0F, -0.2731F);
		Wing0_l.texOffs(0, 20).addBox(-6.0F, -0.5F, -5.0F, 7.0F, 2.0F, 14.0F, 0.0F, true);

		Wing1_l = new ModelRenderer(this);
		Wing1_l.setPos(-5.0F, 0.0F, 0.8F);
		Wing0_l.addChild(Wing1_l);
		setRotateAngle(Wing1_l, 0.0F, 0.0F, -0.1367F);
		Wing1_l.texOffs(28, 20).addBox(-9.0F, 0.0F, -5.0F, 9.0F, 1.0F, 11.0F, 0.0F, true);

		Fin_l = new ModelRenderer(this);
		Fin_l.setPos(-4.7F, 0.0F, -10.0F);
		Body_Base.addChild(Fin_l);
		setRotateAngle(Fin_l, 0.0F, 0.0F, -1.5708F);
		Fin_l.texOffs(0, 0).addBox(-1.0F, -0.5F, -6.0F, 2.0F, 1.0F, 6.0F, 0.0F, true);

		Fin_r = new ModelRenderer(this);
		Fin_r.setPos(4.7F, 0.0F, -10.0F);
		Body_Base.addChild(Fin_r);
		setRotateAngle(Fin_r, 0.0F, 0.0F, 1.5708F);
		Fin_r.texOffs(0, 0).addBox(-1.0F, -0.5F, -6.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);
		
		Dorsel_Fin_l = new ModelRenderer(this);
		Dorsel_Fin_l.setPos(-2.0F, -2.0F, -2.0F);
		Body_Base.addChild(Dorsel_Fin_l);
		setRotationAngle(Dorsel_Fin_l, 0.0F, 0.0F, 1.0036F);
		Dorsel_Fin_l.texOffs(46, 0).addBox(-7.0F, 0.0F, -6.0F, 7.0F, 0.0F, 12.0F, 0.0F, false);

		Dorsel_Fin_r = new ModelRenderer(this);
		Dorsel_Fin_r.setPos(1.8805F, -2.0F, -2.0F);
		Body_Base.addChild(Dorsel_Fin_r);
		setRotationAngle(Dorsel_Fin_r, 0.0F, 0.0F, 2.138F);
		Dorsel_Fin_r.texOffs(46, 0).addBox(-7.0F, 0.0F, -6.0F, 7.0F, 0.0F, 12.0F, 0.0F, false);		
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_Base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    	
    	float flap = MathHelper.sin(ageInTicks * 0.1F);
    	float flap1 = MathHelper.sin(ageInTicks * 0.1F + 0.25F * (float)Math.PI);
    	float flap2 = MathHelper.sin(ageInTicks * 0.1F + 0.5F * (float)Math.PI);
    	
    	this.Body_Base.y = 22.0F + flap;
    	
    	this.Wing0_r.zRot = 0.24F * flap;
        this.Wing0_l.zRot = -0.24F * flap;
        
        this.Wing1_r.zRot = 0.36F * flap1;
        this.Wing1_l.zRot = -0.36F * flap1;        
        
        this.Tail0.xRot = 0.18F * flap1;
        
        this.Tail1.xRot = 0.09F * flap2;
        
        this.Dorsel_Fin_r.zRot = 2.138F + 0.18F * flap2;
        this.Dorsel_Fin_l.zRot = 1.0036F - 0.18F * flap2;          
    }
}
