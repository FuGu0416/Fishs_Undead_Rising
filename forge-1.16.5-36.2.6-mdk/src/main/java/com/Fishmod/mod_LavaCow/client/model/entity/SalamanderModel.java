package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelOSalacannon - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class SalamanderModel<T extends SalamanderEntity> extends FURBaseModel<T> implements IHasHead {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Body2;
    public ModelRenderer Body3;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer CannonBase;
    public ModelRenderer Jaw_upper;
    public ModelRenderer Jaw_lower;
    public ModelRenderer Tooth_upper;
    public ModelRenderer Tooth_r;
    public ModelRenderer Tooth_l;
    public ModelRenderer Tooth_lower;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;
    public ModelRenderer Tail3;
    public ModelRenderer RightArm2;
    public ModelRenderer LeftArm2;
    public ModelRenderer RightLeg2;
    public ModelRenderer LeftLeg2;
    public ModelRenderer Cannon1;
    public ModelRenderer Cannon2;
    public ModelRenderer Saddle_base;
    public ModelRenderer Cannon3;

    private SalamanderNymphModel<SalamanderEntity> ChildModel = new SalamanderNymphModel<>();
    public boolean nymph = true;
    
    public SalamanderModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Tail2 = new ModelRenderer(this, 64, 20);
        this.Tail2.setPos(0.0F, -1.0F, 8.0F);
        this.Tail2.addBox(-3.0F, -2.0F, 0.0F, 6.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Tail2, 0.0911061832922575F, 0.0F, 0.0F);
        this.Cannon3 = new ModelRenderer(this, 58, 50);
        this.Cannon3.setPos(0.0F, -1.0F, -6.0F);
        this.Cannon3.addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg2 = new ModelRenderer(this, 78, 0);
        this.LeftLeg2.mirror = true;
        this.LeftLeg2.setPos(2.0F, 3.5F, 1.0F);
        this.LeftLeg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftLeg2, 0.2275909337942703F, 0.0F, 0.500909508638178F);
        this.Tooth_r = new ModelRenderer(this, 32, 46);
        this.Tooth_r.setPos(-4.0F, 2.0F, -9.0F);
        this.Tooth_r.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Tooth_r, 0.5604252334680235F, 0.9105382388075086F, 0.0F);
        this.Saddle_base = new ModelRenderer(this, 88, 60);
        this.Saddle_base.setPos(-4.0F, -7.0F, 1.0F);
        this.Saddle_base.addBox(0.0F, 0.0F, 0.0F, 8.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 18);
        this.RightArm.setPos(-4.0F, 0.0F, 2.6F);
        this.RightArm.addBox(-4.0F, -2.0F, -4.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightArm, 0.0911061832922575F, 0.0F, 0.956091342937205F);
        this.Tooth_l = new ModelRenderer(this, 32, 46);
        this.Tooth_l.setPos(4.0F, 2.0F, -9.0F);
        this.Tooth_l.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Tooth_l, 0.5604252334680235F, -0.9093165136574348F, 0.0F);
        this.Jaw_lower = new ModelRenderer(this, 0, 48);
        this.Jaw_lower.setPos(0.0F, 0.0F, -2.0F);
        this.Jaw_lower.addBox(-5.0F, 1.0F, -10.0F, 10.0F, 4.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Jaw_lower, 0.19547687289441354F, 0.0F, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 18);
        this.LeftArm.mirror = true;
        this.LeftArm.setPos(4.0F, 0.0F, 2.6F);
        this.LeftArm.addBox(0.0F, -2.0F, -4.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftArm, 0.0911061832922575F, 0.0F, -0.956091342937205F);
        this.RightLeg = new ModelRenderer(this, 0, 18);
        this.RightLeg.setPos(-4.0F, -2.0F, 12.0F);
        this.RightLeg.addBox(-4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightLeg, -0.2275909337942703F, 0.0F, 0.4098033003787853F);
        this.Body2 = new ModelRenderer(this, 84, 0);
        this.Body2.setPos(0.0F, -4.0F, 2.0F);
        this.Body2.addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body2, 0.591841146688116F, 0.0F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 92, 20);
        this.Tail1.setPos(0.0F, 2.0F, 6.0F);
        this.Tail1.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 18);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setPos(4.0F, -2.0F, 12.0F);
        this.LeftLeg.addBox(0.0F, -2.0F, -2.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftLeg, -0.2275909337942703F, 0.0F, -0.4098033003787853F);
        this.Body3 = new ModelRenderer(this, 41, 0);
        this.Body3.setPos(0.0F, -4.0F, 10.0F);
        this.Body3.addBox(-4.5F, -4.0F, -6.0F, 9.0F, 8.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body3, -0.18203784630933073F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.0F, -7.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Jaw_upper = new ModelRenderer(this, 30, 0);
        this.Jaw_upper.setPos(0.0F, 0.0F, -8.0F);
        this.Jaw_upper.addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Cannon1 = new ModelRenderer(this, 92, 38);
        this.Cannon1.setPos(0.0F, -6.5F, 0.0F);
        this.Cannon1.addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Cannon1, 0.4098033003787853F, 0.0F, 0.0F);
        this.Tail3 = new ModelRenderer(this, 40, 20);
        this.Tail3.setPos(0.0F, 0.0F, 6.0F);
        this.Tail3.addBox(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Tail3, 0.0911061832922575F, 0.0F, 0.0F);
        this.RightArm2 = new ModelRenderer(this, 78, 0);
        this.RightArm2.setPos(-2.0F, 4.0F, -1.0F);
        this.RightArm2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightArm2, -0.0911061832922575F, 0.0F, -0.9536478926370572F);
        this.Tooth_lower = new ModelRenderer(this, 34, 53);
        this.Tooth_lower.setPos(0.0F, 1.0F, -7.0F);
        this.Tooth_lower.addBox(-4.0F, -1.0F, -2.5F, 8.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Cannon2 = new ModelRenderer(this, 68, 34);
        this.Cannon2.setPos(0.0F, 0.0F, -6.0F);
        this.Cannon2.addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 16);
        this.Body.setPos(0.0F, 17.5F, -8.2F);
        this.Body.addBox(-4.0F, -4.0F, -7.0F, 8.0F, 8.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.RightLeg2 = new ModelRenderer(this, 78, 0);
        this.RightLeg2.setPos(-2.0F, 3.5F, 1.0F);
        this.RightLeg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RightLeg2, 0.2275909337942703F, 0.0F, -0.500909508638178F);
        this.CannonBase = new ModelRenderer(this, 76, 46);
        this.CannonBase.setPos(0.0F, -8.0F, 7.0F);
        this.CannonBase.addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(CannonBase, -0.4098033003787853F, 0.0F, 0.0F);
        this.Tooth_upper = new ModelRenderer(this, 34, 46);
        this.Tooth_upper.setPos(0.0F, 1.5F, 0.0F);
        this.Tooth_upper.addBox(-3.5F, -0.5F, -2.0F, 7.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.LeftArm2 = new ModelRenderer(this, 78, 0);
        this.LeftArm2.mirror = true;
        this.LeftArm2.setPos(2.0F, 4.0F, -1.0F);
        this.LeftArm2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(LeftArm2, -0.0911061832922575F, 0.0F, 0.9536478926370572F);
        this.Tail1.addChild(this.Tail2);
        this.Cannon2.addChild(this.Cannon3);
        this.LeftLeg.addChild(this.LeftLeg2);
        this.Jaw_lower.addChild(this.Tooth_r);
        this.Cannon1.addChild(this.Saddle_base);
        this.Body.addChild(this.RightArm);
        this.Jaw_lower.addChild(this.Tooth_l);
        this.Head.addChild(this.Jaw_lower);
        this.Body.addChild(this.LeftArm);
        this.Body.addChild(this.RightLeg);
        this.Body.addChild(this.Body2);
        this.Body3.addChild(this.Tail1);
        this.Body.addChild(this.LeftLeg);
        this.Body.addChild(this.Body3);
        this.Body.addChild(this.Head);
        this.Head.addChild(this.Jaw_upper);
        this.CannonBase.addChild(this.Cannon1);
        this.Tail2.addChild(this.Tail3);
        this.RightArm.addChild(this.RightArm2);
        this.Jaw_lower.addChild(this.Tooth_lower);
        this.Cannon1.addChild(this.Cannon2);
        this.RightLeg.addChild(this.RightLeg2);
        this.Body.addChild(this.CannonBase);
        this.Jaw_upper.addChild(this.Tooth_upper);
        this.LeftArm.addChild(this.LeftArm2);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	if(!this.nymph) {
	    	ImmutableList.of(this.Body).forEach((modelRenderer) -> { 
	            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        });
        } else if (this.young) {
        	ChildModel.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	SalamanderEntity Entity = ((SalamanderEntity)entityIn);
    	float i = Entity.getAttackTimer() / 80.0F;
    	float Anime_threshold[] = {0.85F, 0.67F, 0.12F};
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);    	
    	
    	if (!Entity.isNymph()) {	        
        	if(Entity.isBaby()) {
        		if(Entity.getGrowingStage() == 1) {
	        		this.Tooth_l.visible = false;
	        		this.Tooth_r.visible = false;
        		} else {
            		this.Tooth_l.visible = true;
            		this.Tooth_r.visible = true;
        		}
        		
        		this.CannonBase.visible = false;
        		this.Cannon1.visible = false;
        		this.Cannon2.visible = false;
        		this.Cannon3.visible = false;
        	} else {
        		this.Tooth_l.visible = true;
        		this.Tooth_r.visible = true;
        		this.CannonBase.visible = true;
        		this.Cannon1.visible = true;
        		this.Cannon2.visible = true;
        		this.Cannon3.visible = true;
        	}
	    	
	    	if(entityIn.isInSittingPose() && !entityIn.isVehicle()) {
		        this.Head_Looking(this.Head, -0.35185837453889574F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.zRot = 0.0F;
	    		this.Jaw_lower.xRot = 0.19547687289441354F + (-0.08F * MathHelper.sin(0.03F * ageInTicks));
	    		
		        this.RightArm.xRot = -0.7689920923971514F;
		        this.LeftArm.xRot = -0.7689920923971514F;
		        this.RightLeg.xRot = 0.6326818538479445F;
		        this.LeftLeg.xRot = 0.6326818538479445F;
		        
		        this.RightArm.zRot = 0.956091342937205F;
		        this.LeftArm.zRot = -0.956091342937205F;
		        this.RightLeg.zRot = 0.40980330836826856F;
		        this.LeftLeg.zRot = -0.40980330836826856F;
		        		        
		        this.Body.xRot = 0.0F;
		        this.Body.y = 20.0F;	
		        
		        this.Tail1.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks);
		        this.Tail2.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.02F);
		        this.Tail3.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.04F);
		        
		        this.Cannon2.z = -3.0F;
		        this.Cannon3.z = -3.0F;	    		
	    	} else if (i <= Anime_threshold[0] && i > Anime_threshold[1]) {
	    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.zRot = GradientAnimation(0.0F, -0.36425021489121656F, j * (i - Anime_threshold[1]));
	    		this.Jaw_lower.xRot = GradientAnimation(0.0F, 0.6829473363053812F, j * (i - Anime_threshold[1]));
	    		
		        this.RightArm.xRot = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
		        this.LeftArm.xRot = GradientAnimation(0.091106186954104F, -0.5009094953223726F, j * (i - Anime_threshold[1]));
		        this.RightLeg.xRot = -0.22759093446006054F;
		        this.LeftLeg.xRot = -0.22759093446006054F;

		        this.RightArm.zRot = 0.9560913642424937F;
		        this.LeftArm.zRot = -0.9560913642424937F;
		        this.RightLeg.zRot = 0.40980330836826856F;
		        this.LeftLeg.zRot = -0.40980330836826856F;
		        
		        this.Body.xRot = GradientAnimation(0.0F, -0.18203784098300857F, j * (i - Anime_threshold[1]));
		        this.Body.y = GradientAnimation(17.5F, 16.2F, j * (i - Anime_threshold[1]));
		        
		        this.Tail1.yRot = GradientAnimation(0.0F, -0.136659280431156F, j * (i - Anime_threshold[1]));
		        this.Tail2.yRot = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
		        this.Tail3.yRot = GradientAnimation(0.0F, -0.40980330836826856F, j * (i - Anime_threshold[1]));
		        
		        this.Cannon2.z = -3.0F;
		        this.Cannon3.z = -3.0F;
	    	} else if (i <= Anime_threshold[1] && i > Anime_threshold[2]) {
	    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.zRot = GradientAnimation(-0.36425021489121656F, 0.0F, k * (i - Anime_threshold[2]));
	    		this.Jaw_lower.xRot = GradientAnimation(0.6829473363053812F, 0.0F, k * (i - Anime_threshold[2]));
	    		
		        this.RightArm.xRot = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
		        this.LeftArm.xRot = GradientAnimation(-0.5009094953223726F, 0.091106186954104F, k * (i - Anime_threshold[2]));
		        this.RightLeg.xRot = -0.22759093446006054F;
		        this.LeftLeg.xRot = -0.22759093446006054F;

		        this.RightArm.zRot = 0.9560913642424937F;
		        this.LeftArm.zRot = -0.9560913642424937F;
		        this.RightLeg.zRot = 0.40980330836826856F;
		        this.LeftLeg.zRot = -0.40980330836826856F;
		        
		        this.Body.xRot = GradientAnimation(-0.18203784098300857F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Body.y = GradientAnimation(16.2F, 17.5F, k * (i - Anime_threshold[2]));
		        
		        this.Tail1.yRot = GradientAnimation(-0.136659280431156F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Tail2.yRot = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
		        this.Tail3.yRot = GradientAnimation(-0.40980330836826856F, 0.0F, k * (i - Anime_threshold[2]));
		        
		        this.Cannon2.z = -1.8F + 1.2F * MathHelper.sin(1.0F * ageInTicks);
		        this.Cannon3.z = -1.3F + 1.7F * MathHelper.sin(1.0F * ageInTicks);
	    	} else {
	    		final float walking_f = 0.44F;
	    		final float walking_a = 0.4F;
	    		
		        this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
	    		this.Head.zRot = 0.0F;
	    		this.Jaw_lower.xRot = 0.19547687289441354F + (-0.08F * MathHelper.sin(0.03F * ageInTicks));
	    		
		        this.RightArm.xRot = 0.091106186954104F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        this.LeftArm.xRot = 0.091106186954104F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.RightLeg.xRot = -0.22759093446006054F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.LeftLeg.xRot = -0.22759093446006054F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        
		        this.RightArm.zRot = 0.9560913642424937F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;
		        this.LeftArm.zRot = -0.9560913642424937F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.RightLeg.zRot = 0.40980330836826856F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * walking_a * limbSwingAmount;
		        this.LeftLeg.zRot = -0.40980330836826856F + MathHelper.cos(limbSwing * walking_f) * walking_a * limbSwingAmount;

		        this.RightArm.y = 0.0F + MathHelper.cos(limbSwing * walking_f) * 1.0F * limbSwingAmount;
		        this.LeftArm.y = 0.0F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * 1.0F * limbSwingAmount;
		        this.RightLeg.y = -0.2F + MathHelper.cos(limbSwing * walking_f + (float)Math.PI) * 1.0F * limbSwingAmount;
		        this.LeftLeg.y = -0.2F + MathHelper.cos(limbSwing * walking_f) * 1.0F * limbSwingAmount;
		        
		        this.Body.xRot = 0.0F;
		        this.Body.y = 17.5F;	
		        
		        this.Tail1.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks);
		        this.Tail2.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.02F);
		        this.Tail3.yRot = 0.15F * MathHelper.sin(0.03F * ageInTicks + 0.04F);
		        
		        this.Cannon2.z = -3.0F;
		        this.Cannon3.z = -3.0F;
	    	}
    	} else {
    		ChildModel.setupAnim(Entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
