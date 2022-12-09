package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.GhostRayEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelGhostRay - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class GhostRayModel<T extends GhostRayEntity> extends FlyingBaseModel<T> {
    public ModelRenderer Body_Base;
    public ModelRenderer Body1;
    public ModelRenderer Wing0_r;
    public ModelRenderer Wing0_l;
    public ModelRenderer Fin_l;
    public ModelRenderer Fin_r;
    public ModelRenderer Tail0;
    public ModelRenderer Tail1;
    public ModelRenderer Wing1_r;
    public ModelRenderer Wing2_r;
    public ModelRenderer Wing1_l;
    public ModelRenderer Wing2_l;

    public GhostRayModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Body_Base = new ModelRenderer(this, 9, 16);
        this.Body_Base.setPos(0.0F, 22.0F, -1.0F);
        this.Body_Base.addBox(-3.0F, -2.0F, -6.0F, 6, 4, 12, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 18);
        this.Tail1.mirror = true;
        this.Tail1.setPos(0.0F, -1.0F, 7.0F);
        this.Tail1.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail1, 0.18203784630933073F, 0.0F, 0.0F);
        this.Wing0_r = new ModelRenderer(this, 26, 0);
        this.Wing0_r.setPos(-3.0F, 0.0F, -4.0F);
        this.Wing0_r.addBox(-5.0F, -1.5F, -2.0F, 7, 3, 11, 0.0F);
        this.setRotateAngle(Wing0_r, 0.0F, 0.0F, -0.27314402127920984F);
        this.Fin_l = new ModelRenderer(this, 17, 0);
        this.Fin_l.mirror = true;
        this.Fin_l.setPos(2.7F, -0.5F, -6.0F);
        this.Fin_l.addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, 0.0F);
        this.setRotateAngle(Fin_l, 0.0F, 0.0F, 0.4098033003787853F);
        this.Wing1_r = new ModelRenderer(this, 34, 18);
        this.Wing1_r.setPos(-5.0F, 0.0F, 0.8F);
        this.Wing1_r.addBox(-5.0F, -1.0F, -2.0F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Wing1_r, 0.0F, 0.0F, -0.13665927909957545F);
        this.Body1 = new ModelRenderer(this, 0, 0);
        this.Body1.setPos(0.0F, 0.0F, 7.0F);
        this.Body1.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Body1, 0.0911061832922575F, 0.0F, 0.0F);
        this.Wing0_l = new ModelRenderer(this, 26, 0);
        this.Wing0_l.mirror = true;
        this.Wing0_l.setPos(3.0F, 0.0F, -4.0F);
        this.Wing0_l.addBox(-1.0F, -1.5F, -2.0F, 7, 3, 11, 0.0F);
        this.setRotateAngle(Wing0_l, 0.0F, 0.0F, 0.27314402127920984F);
        this.Tail0 = new ModelRenderer(this, 0, 18);
        this.Tail0.setPos(0.0F, 0.0F, 3.0F);
        this.Tail0.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail0, 0.18203784630933073F, 0.0F, 0.0F);
        this.Wing2_l = new ModelRenderer(this, 0, 8);
        this.Wing2_l.mirror = true;
        this.Wing2_l.setPos(6.0F, -0.5F, -0.5F);
        this.Wing2_l.addBox(0.0F, -0.5F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing2_l, 0.0F, 0.0F, 0.45535640450848164F);
        this.Fin_r = new ModelRenderer(this, 17, 0);
        this.Fin_r.setPos(-2.7F, -0.5F, -6.0F);
        this.Fin_r.addBox(-1.0F, -0.5F, -6.0F, 2, 1, 6, 0.0F);
        this.setRotateAngle(Fin_r, 0.0F, 0.0F, -0.4098033003787853F);
        this.Wing2_r = new ModelRenderer(this, 0, 8);
        this.Wing2_r.setPos(-4.0F, -0.5F, -0.5F);
        this.Wing2_r.addBox(-9.0F, -0.5F, -1.0F, 9, 1, 7, 0.0F);
        this.setRotateAngle(Wing2_r, 0.0F, 0.0F, -0.45535640450848164F);
        this.Wing1_l = new ModelRenderer(this, 34, 18);
        this.Wing1_l.mirror = true;
        this.Wing1_l.setPos(5.0F, 0.0F, 0.8F);
        this.Wing1_l.addBox(0.0F, -1.0F, -2.0F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Wing1_l, 0.0F, 0.0F, 0.13665927909957545F);
        this.Tail0.addChild(this.Tail1);
        this.Body_Base.addChild(this.Wing0_r);
        this.Body_Base.addChild(this.Fin_l);
        this.Wing0_r.addChild(this.Wing1_r);
        this.Body_Base.addChild(this.Body1);
        this.Body_Base.addChild(this.Wing0_l);
        this.Body1.addChild(this.Tail0);
        this.Wing1_l.addChild(this.Wing2_l);
        this.Body_Base.addChild(this.Fin_r);
        this.Wing1_r.addChild(this.Wing2_r);
        this.Wing0_l.addChild(this.Wing1_l);
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
    	
    	this.Body_Base.y = 22.0F + flap;
    	
    	this.Wing0_r.zRot = 0.24F * flap;
        this.Wing0_l.zRot = -0.24F * flap;
        
        this.Wing1_r.zRot = 0.36F * flap;
        this.Wing1_l.zRot = -0.36F * flap;
        
        this.Wing2_r.zRot = 0.18F * flap;
        this.Wing2_l.zRot = -0.18F * flap;
        
        this.Tail0.xRot = 0.18F * flap;
        
        this.Tail1.xRot = 0.09F * flap;

    	if(this.state.equals(FlyingBaseModel.State.FLYING)) {
        	this.Body1.xRot = 0.091106186954104F;
        }
        else {
        	this.Body1.xRot = -0.36267941856442165F;
        }
    }
}
