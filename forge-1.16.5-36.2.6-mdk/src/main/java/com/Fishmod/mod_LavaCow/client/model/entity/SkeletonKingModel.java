package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * ModelSkeletonKing - Fish0016054
 * Created using Tabula 7.1.0
 */
public class SkeletonKingModel<T extends SkeletonKingEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Head;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Shoulder_piece;
    public ModelRenderer weapon_handle0;
    public ModelRenderer weapon_handle1;
    public ModelRenderer weapon_handle1_1;
    public ModelRenderer weapon_base;
    public ModelRenderer weapon_horn_l;
    public ModelRenderer weapon_horn_r;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Crown;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_r_Seg1;

    public SkeletonKingModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Body_base = new ModelRenderer(this, 0, 52);
        this.Body_base.setPos(0.0F, 4.2F, 0.0F);
        this.Body_base.addBox(-5.0F, -6.0F, -3.0F, 10, 6, 6, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 39);
        this.Body_waist.setPos(0.0F, -6.0F, 2.0F);
        this.Body_waist.addBox(-3.5F, -10.0F, -3.0F, 7, 10, 3, 0.0F);
        this.setRotateAngle(Body_waist, 0.136659280431156F, 0.0F, 0.0F);
        this.Body_chest = new ModelRenderer(this, 0, 23);
        this.Body_chest.setPos(0.0F, -5.0F, 0.0F);
        this.Body_chest.addBox(-6.0F, -10.0F, -4.5F, 12, 10, 6, 0.0F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 48, 48);
        this.Leg_r_Seg1.setPos(0.0F, 12.0F, -2.0F);
        this.Leg_r_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, -10.0F, -1.0F);
        this.Head.addBox(-4.0F, -8.0F, -5.0F, 8, 8, 8, 0.0F);
        this.Shoulder_piece = new ModelRenderer(this, 38, 0);
        this.Shoulder_piece.setPos(1.0F, 0.0F, 0.0F);
        this.Shoulder_piece.addBox(0.0F, -3.0F, -3.5F, 5, 11, 7, 0.0F);
        this.weapon_handle1 = new ModelRenderer(this, 66, 43);
        this.weapon_handle1.setPos(0.0F, 0.0F, -15.0F);
        this.weapon_handle1.addBox(-0.5F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.weapon_handle1_1 = new ModelRenderer(this, 66, 52);
        this.weapon_handle1_1.setPos(0.0F, 0.0F, -6.0F);
        this.weapon_handle1_1.addBox(-1.5F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.weapon_base = new ModelRenderer(this, 96, 35);
        this.weapon_base.setPos(0.0F, 0.0F, -4.0F);
        this.weapon_base.addBox(-3.5F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 36, 22);
        this.Arm_l_Seg0.mirror = true;
        this.Arm_l_Seg0.setPos(5.0F, -8.0F, -1.5F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(Arm_l_Seg0, -0.9560913642424937F, 0.0F, -0.40980330836826856F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 45, 21);
        this.Arm_l_Seg1.setPos(1.0F, 11.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 3, 0.0F);
        this.setRotateAngle(Arm_l_Seg1, -1.9123572614101867F, 0.0F, 0.0F);
        this.weapon_horn_l = new ModelRenderer(this, 110, 52);
        this.weapon_horn_l.setPos(4.0F, 0.0F, -4.0F);
        this.weapon_horn_l.addBox(0.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.weapon_horn_r = new ModelRenderer(this, 110, 52);
        this.weapon_horn_r.mirror = true;
        this.weapon_horn_r.setPos(-4.0F, 0.0F, -4.0F);
        this.weapon_horn_r.addBox(-2.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 32, 48);
        this.Leg_l_Seg0.setPos(2.5F, -1.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402793711257F, 0.0F, -0.091106186954104F);
        this.Leg_l_Seg1 = new ModelRenderer(this, 48, 48);
        this.Leg_l_Seg1.mirror = true;
        this.Leg_l_Seg1.setPos(0.0F, 12.0F, -2.0F);
        this.Leg_l_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_l_Seg1, 0.5918411493512771F, 0.0F, 0.0F);
        this.Leg_r_Seg0 = new ModelRenderer(this, 32, 48);
        this.Leg_r_Seg0.mirror = true;
        this.Leg_r_Seg0.setPos(-2.5F, -1.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402793711257F, 0.0F, 0.091106186954104F);
        this.Crown = new ModelRenderer(this, 92, 19);
        this.Crown.setPos(0.0F, -6.0F, -1.0F);
        this.Crown.addBox(-4.5F, -4.0F, -4.5F, 9, 5, 9, 0.0F);
        this.weapon_handle0 = new ModelRenderer(this, 66, 43);
        this.weapon_handle0.setPos(-0.5F, 11.0F, 0.0F);
        this.weapon_handle0.addBox(0.0F, -0.5F, -15.0F, 1, 1, 20, 0.0F);
        this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
        this.Arm_r_Seg1 = new ModelRenderer(this, 45, 21);
        this.Arm_r_Seg1.mirror = true;
        this.Arm_r_Seg1.setPos(-1.0F, 11.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 3, 0.0F);
        this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 36, 22);
        this.Arm_r_Seg0.setPos(-5.0F, -8.0F, -1.5F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(Arm_r_Seg0, 0.0F, 0.0F, 0.5462880558742251F);
        this.Body_base.addChild(this.Body_waist);
        this.Body_waist.addChild(this.Body_chest);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Body_chest.addChild(this.Head);
        this.Arm_l_Seg0.addChild(this.Shoulder_piece);
        this.weapon_handle0.addChild(this.weapon_handle1);
        this.weapon_handle1.addChild(this.weapon_handle1_1);
        this.weapon_handle1_1.addChild(this.weapon_base);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.weapon_base.addChild(this.weapon_horn_l);
        this.weapon_base.addChild(this.weapon_horn_r);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Head.addChild(this.Crown);
        this.Arm_l_Seg1.addChild(this.weapon_handle0);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
        this.Body_chest.addChild(this.Arm_r_Seg0);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
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
    	float o = (float)(entityIn.getInvulnerableTicks()) / 140.0F;
    	if (o > 0.0F) {
    		this.Head.xRot = GradientAnimation_s(0.5866051722479385F, 0.0F, o);
    		this.Head.yRot = 0.0F;
    		this.Head.zRot = 0.0F;
    	} else {
    		this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	}
    }
    
    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	SkeletonKingEntity entity = (SkeletonKingEntity) entityIn;
    	float i = (float)(entity.getAttackTimer()) / (float)SkeletonKingEntity.ATTACK_TIMER;
    	float k = (float)(entity.getSpellTicks(0)) / 30.0F;
    	float l = (float)(entity.getSpellTicks(1)) / 15.0F;
    	float Anime_threshold[] = {1.0F, 0.5F, 0.2F};
    	float m = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float n = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);
    	float o = ((float)(entity.getInvulnerableTicks()) - 60.0F) / 80.0F;
    	
    	if (this.riding) {
    		this.Body_base.y = 4.2F;
    		this.setRotateAngle(Leg_r_Seg0, -1.6414822147638888F, 0.35185837453889574F, 0.0911061832922575F);
    		this.setRotateAngle(Leg_r_Seg1, 1.0609856682663195F, 0.0F, 0.0F);
    		this.setRotateAngle(Leg_l_Seg0, -1.6414822147638888F, -0.35185837453889574F, -0.0911061832922575F);
    		this.setRotateAngle(Leg_l_Seg1, 1.0609856682663195F, 0.0F, 0.0F);
    	} else if (i > Anime_threshold[1]) {
    		this.Leg_r_Seg0.xRot = GradientAnimation(-0.27314402793711257F, -1.1383037381507017F, m * (i - Anime_threshold[1]));
        	this.Leg_r_Seg0.yRot = 0.0F;
    		this.Leg_l_Seg0.xRot = GradientAnimation(-0.27314402793711257F, -1.1383037381507017F, m * (i - Anime_threshold[1]));
        	this.Leg_l_Seg0.yRot = 0.0F;
    		this.Leg_r_Seg1.xRot = GradientAnimation(0.5918411493512771F, 1.8212510744560826F, m * (i - Anime_threshold[1]));
    		this.Leg_l_Seg1.xRot = GradientAnimation(0.5918411493512771F, 1.8212510744560826F, m * (i - Anime_threshold[1]));
    	} else if (i > 0.0F) {
    		this.Leg_r_Seg0.xRot = GradientAnimation(-1.1383037381507017F, -0.27314402793711257F, n * i);
    		this.Leg_r_Seg0.yRot = 0.0F;
    		this.Leg_l_Seg0.xRot = GradientAnimation(-1.1383037381507017F, -0.27314402793711257F, n * i);
    		this.Leg_l_Seg0.yRot = 0.0F;
    		this.Leg_r_Seg1.xRot = GradientAnimation(1.8212510744560826F, 0.5918411493512771F, n * i);
    		this.Leg_l_Seg1.xRot = GradientAnimation(1.8212510744560826F, 0.5918411493512771F, n * i); 
    	} else {
        	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.3F, true, 0.0F);
        	this.Leg_r_Seg0.yRot = 0.0F;
        	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.3F, false, 0.0F);
        	this.Leg_l_Seg0.yRot = 0.0F;
        	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, false, 0.3F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.3F, true, 0.3F * (float)Math.PI);
    	}
    	
    	if (o > 0.0F) {
    		this.Body_base.y = GradientAnimation_s(44.0F, 4.2F, o);
    		this.Body_waist.xRot = 0.136659280431156F;
    		this.Body_chest.xRot = 0.0F;
    		
    		this.setRotateAngle(Arm_r_Seg0, 0.0F, 0.0F, 0.07714355426972991F);
    		this.setRotateAngle(Arm_r_Seg1, -0.7740534966278743F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_l_Seg0, 0.0F, 0.0F, -0.07714355426972991F);
    		this.setRotateAngle(Arm_l_Seg1, -0.7740534966278743F, 0.0F, 0.0F);
    		
    		this.weapon_handle0.visible = false;
    		this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
    	} else if (k > 0) {
    		this.Body_base.y = 4.2F;
    		this.Body_waist.xRot = GradientAnimation(0.5009094953223726F, -0.31869712141416456F, k);
    		this.Body_chest.xRot = GradientAnimation(0.091106186954104F, -0.18203784098300857F, k);   
    		this.Arm_l_Seg0.xRot = GradientAnimation(0.091106186954104F, 0.9560913642424937F, k); 
    		this.Arm_l_Seg1.xRot = -0.36425021489121656F;
    		this.weapon_handle0.visible = true;
    		this.weapon_handle0.xRot = 0.136659280431156F;
    		this.Arm_r_Seg0.xRot = GradientAnimation(0.0F, -1.6845917940249266F, k); 
    		this.Arm_r_Seg0.yRot = GradientAnimation(0.0F, 0.5462880558742251F, k); 
    		this.Arm_r_Seg0.zRot = GradientAnimation(0.5462880558742251F, 0.136659280431156F, k); 
    		this.Arm_r_Seg1.xRot = GradientAnimation(-0.7740535232594852F, -1.3658946726107624F, k); 
    	} else if (l > 0) { 		
    		this.Body_base.y = 4.2F;
    		this.Body_waist.xRot = 0.136659280431156F;
    		this.Body_chest.xRot = 0.0F;
    		
    		this.Arm_r_Seg0.xRot = GradientAnimation(0.7285004297824331F, -0.9560913642424937F, l); 
    		this.Arm_r_Seg0.yRot = 0.0F;
    		this.Arm_r_Seg0.zRot = 1.5481070465189704F;
    		this.Arm_r_Seg1.xRot = GradientAnimation(-2.1399481958702475F, -0.5918411493512771F, l); 
    		this.Arm_r_Seg1.yRot = 0.0F;
    		this.Arm_r_Seg1.zRot = 0.0F;
    		this.setRotateAngle(Arm_l_Seg0, -0.9560913642424937F, 0.0F, -0.40980330836826856F);
    		this.setRotateAngle(Arm_l_Seg1, -1.9123572614101867F, 0.0F, 0.0F);
    		this.weapon_handle0.visible = true;
    		this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
    	} else if (i > Anime_threshold[1]) {
    		this.Body_base.y = GradientAnimation(4.2F, 15.0F, m * (i - Anime_threshold[1]));
    		this.Body_waist.xRot = GradientAnimation(-0.27314402793711257F, 0.6373942428283291F, m * (i - Anime_threshold[1]));
    		this.Body_chest.xRot = GradientAnimation(-0.31869712141416456F, 0.27314402793711257F, m * (i - Anime_threshold[1]));
    		
    		this.Arm_r_Seg0.xRot = GradientAnimation(-3.050486466635689F, -1.1838568316277536F, m * (i - Anime_threshold[1])); 
    		this.Arm_r_Seg0.yRot = 0.0F;
    		this.Arm_r_Seg0.zRot = 0.0F;
    		this.Arm_r_Seg1.xRot = -0.136659280431156F; 
    		this.Arm_r_Seg1.zRot = -0.5918411493512771F;
    		this.Arm_l_Seg0.xRot = GradientAnimation(-3.141592653589793F, -0.9560913642424937F, m * (i - Anime_threshold[1])); 
    		this.Arm_l_Seg0.zRot = 0.0F;
    		this.Arm_l_Seg1.xRot = -0.22759093446006054F;
    		this.Arm_l_Seg1.zRot = 0.5462880558742251F;
    		this.weapon_handle0.visible = true;
    		this.weapon_handle0.xRot = 0.27314402793711257F;
    	} else if (i > 0.0F) {
    		this.Body_base.y = GradientAnimation(15.0F, 4.2F, n * i);
    		this.Body_waist.xRot = GradientAnimation(0.6373942428283291F, 0.136659280431156F, n * i);
    		this.Body_chest.xRot = GradientAnimation(0.27314402793711257F, 0.0F, n * i);
    		
    		this.Arm_r_Seg0.xRot = GradientAnimation(-1.1838568316277536F, 0.0F, n * i); 
    		this.Arm_r_Seg0.yRot = 0.0F;
    		this.Arm_r_Seg0.zRot = GradientAnimation(0.0F, 0.5462880558742251F, n * i); 
    		this.Arm_r_Seg1.xRot = GradientAnimation(-0.136659280431156F, -0.7740535232594852F, n * i); 
    		this.Arm_r_Seg1.zRot = GradientAnimation(-0.5918411493512771F, 0.0F, n * i);
    		this.Arm_l_Seg0.xRot = -0.9560913642424937F; 
    		this.Arm_l_Seg0.zRot = GradientAnimation(0.0F, -0.40980330836826856F, n * i);
    		this.Arm_l_Seg1.xRot = GradientAnimation(-0.22759093446006054F, -1.9123572614101867F, n * i);
    		this.Arm_l_Seg1.zRot = GradientAnimation(0.5462880558742251F, 0.0F, n * i);
    		this.weapon_handle0.visible = true;
    		this.weapon_handle0.xRot = GradientAnimation(0.27314402793711257F, -0.36425021489121656F, n * i);
    	} else {
    		this.Body_base.y = 4.2F;
    		this.Body_waist.xRot = 0.136659280431156F;
    		this.Body_chest.xRot = 0.0F;
    		
    		this.setRotateAngle(Arm_r_Seg0, 0.0F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_r_Seg1, -0.7740535232594852F, 0.0F, 0.0F);
    		this.setRotateAngle(Arm_l_Seg0, -0.9560913642424937F, 0.0F, -0.40980330836826856F);
    		this.setRotateAngle(Arm_l_Seg1, -1.9123572614101867F, 0.0F, 0.0F);
    		this.weapon_handle0.visible = true;
    		this.setRotateAngle(weapon_handle0, -0.36425021489121656F, 0.0F, 0.0F);
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
