package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.ScarecrowEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelScarecrow - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ScarecrowModel<T extends ScarecrowEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Body_upper;
    public ModelRenderer leg_l_0;
    public ModelRenderer leg_r_0;
    public ModelRenderer Body_straw;
    public ModelRenderer arm_l_0;
    public ModelRenderer arm_r_0;
    public ModelRenderer Neck;
    public ModelRenderer scarf;
    public ModelRenderer scarf_back;
    public ModelRenderer arm_l_1;
    public ModelRenderer shoulder_l;
    public ModelRenderer scepter_base;
    public ModelRenderer scepter_blade;
    public ModelRenderer arm_r_1;
    public ModelRenderer shoulder_r;
    public ModelRenderer Head;
    public ModelRenderer Head_stem;
    public ModelRenderer Head_tooth;
    public ModelRenderer Jaw;
    public ModelRenderer Jaw_tooth;
    public ModelRenderer leg_l_1;
    public ModelRenderer leg_l_2;
    public ModelRenderer leg_r_1;
    public ModelRenderer leg_r_2;
    /**
     * Non-Pumpkin variant
     */
    public ModelRenderer Head2;
    public ModelRenderer Stem2;
    /**
     * Plague Doctor variant
     */
    public ModelRenderer Neck2;
    public ModelRenderer Head3;
    public ModelRenderer Beak;
    public ModelRenderer Hat;
    public ModelRenderer Beak1;
    
    public ScarecrowModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Jaw, 0.5201081037785047F, 0.0F, 0.0F);
        this.Body_straw = new ModelRenderer(this, 96, 27);
        this.Body_straw.setPos(0.0F, -2.0F, 0.0F);
        this.Body_straw.addBox(-5.0F, 0.0F, -2.0F, 10.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 45, 1);
        this.Neck.setPos(0.0F, -10.1F, -1.9F);
        this.Neck.addBox(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Neck, -0.4286528549687435F, 0.0F, 0.0F);
        this.Head_tooth = new ModelRenderer(this, 32, 25);
        this.Head_tooth.setPos(0.0F, 0.0F, 0.0F);
        this.Head_tooth.addBox(-4.0F, -1.0F, -8.0F, 8.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.scepter_blade = new ModelRenderer(this, 10, 37);
        this.scepter_blade.setPos(0.0F, 0.5F, -15.0F);
        this.scepter_blade.addBox(0.0F, 0.0F, -2.5F, 0.0F, 16.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.leg_l_2 = new ModelRenderer(this, 0, 44);
        this.leg_l_2.mirror = true;
        this.leg_l_2.setPos(0.0F, 6.5F, 0.1F);
        this.leg_l_2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_l_2, -0.3761184404889726F, 0.0F, 0.0F);
        this.arm_l_0 = new ModelRenderer(this, 18, 29);
        this.arm_l_0.mirror = true;
        this.arm_l_0.setPos(7.0F, -7.0F, -1.0F);
        this.arm_l_0.addBox(-0.5F, -2.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm_l_0, -0.12967796042712246F, -0.017453292519943295F, -0.10000736647217022F);
        this.arm_r_0 = new ModelRenderer(this, 18, 29);
        this.arm_r_0.setPos(-7.0F, -7.0F, -1.0F);
        this.arm_r_0.addBox(-2.0F, -2.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm_r_0, -0.12967796042712246F, 0.017453292519943295F, 0.10000736647217022F);
        this.leg_r_0 = new ModelRenderer(this, 0, 30);
        this.leg_r_0.setPos(-2.5F, -2.0F, 0.1F);
        this.leg_r_0.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_r_0, 0.13072515697963366F, 0.0F, 0.0F);
        this.shoulder_l = new ModelRenderer(this, 64, 51);
        this.shoulder_l.mirror = true;
        this.shoulder_l.setPos(0.0F, 0.0F, 0.0F);
        this.shoulder_l.addBox(-1.5F, -3.5F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, -1.0F, -3.0F);
        this.Head.addBox(-4.0F, -5.0F, -8.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head, 0.19547687289441354F, 0.0F, 0.0F);
        this.leg_l_1 = new ModelRenderer(this, 0, 30);
        this.leg_l_1.mirror = true;
        this.leg_l_1.setPos(0.0F, 7.0F, 0.1F);
        this.leg_l_1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_l_1, 0.5707226787179512F, 0.0F, 0.0F);
        this.leg_r_2 = new ModelRenderer(this, 0, 44);
        this.leg_r_2.setPos(0.0F, 6.5F, 0.1F);
        this.leg_r_2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_r_2, -0.3761184404889726F, 0.0F, 0.0F);
        this.Body_upper = new ModelRenderer(this, 92, 11);
        this.Body_upper.setPos(0.0F, -8.1F, 1.3F);
        this.Body_upper.addBox(-6.0F, -10.0F, -3.3F, 12.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body_upper, 0.6365215995076208F, 0.0F, 0.0F);
        this.Head_stem = new ModelRenderer(this, 0, 0);
        this.Head_stem.setPos(0.0F, -4.0F, -4.0F);
        this.Head_stem.addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head_stem, -0.5462880425584197F, 0.0F, 0.0F);
        this.shoulder_r = new ModelRenderer(this, 64, 51);
        this.shoulder_r.setPos(0.0F, 0.0F, 0.0F);
        this.shoulder_r.addBox(-3.5F, -3.5F, -3.0F, 5.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Jaw_tooth = new ModelRenderer(this, 32, 38);
        this.Jaw_tooth.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw_tooth.addBox(-4.0F, -2.0F, -8.0F, 8.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 34, 9);
        this.Body_base.setPos(0.0F, 5.0F, 0.0F);
        this.Body_base.addBox(-5.0F, -10.0F, -2.0F, 10.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body_base, -0.3642502295386026F, 0.0F, 0.0F);
        this.scarf_back = new ModelRenderer(this, 84, 35);
        this.scarf_back.mirror = true;
        this.scarf_back.setPos(0.0F, -11.0F, 0.0F);
        this.scarf_back.addBox(-6.0F, 2.0F, -20.0F, 12.0F, 0.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(scarf_back, 1.5934856603340446F, 0.0F, 0.0F);
        this.leg_r_1 = new ModelRenderer(this, 0, 30);
        this.leg_r_1.setPos(0.0F, 7.0F, 0.1F);
        this.leg_r_1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_r_1, 0.5707226787179512F, 0.0F, 0.0F);
        this.scarf = new ModelRenderer(this, 64, 34);
        this.scarf.setPos(0.0F, -11.0F, 0.0F);
        this.scarf.addBox(-6.0F, 0.0F, -3.0F, 12.0F, 11.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(scarf, -0.23457224414434488F, 0.0F, 0.0F);
        this.arm_r_1 = new ModelRenderer(this, 10, 30);
        this.arm_r_1.setPos(-0.5F, 10.0F, 0.0F);
        this.arm_r_1.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm_r_1, -0.27366763203903305F, 0.0F, 0.0F);
        this.arm_l_1 = new ModelRenderer(this, 10, 30);
        this.arm_l_1.mirror = true;
        this.arm_l_1.setPos(1.0F, 10.0F, 0.0F);
        this.arm_l_1.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(arm_l_1, -0.27366763203903305F, 0.0F, 0.0F);
        this.leg_l_0 = new ModelRenderer(this, 0, 30);
        this.leg_l_0.mirror = true;
        this.leg_l_0.setPos(2.5F, -2.0F, 0.1F);
        this.leg_l_0.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_l_0, 0.13072515697963366F, 0.0F, 0.0F);
        this.scepter_base = new ModelRenderer(this, 0, 33);
        this.scepter_base.setPos(0.0F, 9.5F, 0.0F);
        this.scepter_base.addBox(-0.5F, -0.5F, -18.0F, 1.0F, 1.0F, 30.0F, 0.0F, 0.0F, 0.0F);
        /**
         * Non-Pumpkin variant
         */
        this.Head2 = new ModelRenderer(this, 40, 49);
        this.Head2.setPos(0.0F, -11.0F, -2.0F);
        this.Head2.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.Stem2 = new ModelRenderer(this, 33, 48);
        this.Stem2.setPos(0.0F, -7.6F, 2.3F);
        this.Stem2.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Stem2, -0.8196066167365371F, 0.0F, 0.0F);
        /**
         * Plague Doctor variant
         */
        this.Beak = new ModelRenderer(this, 64, 26);
        this.Beak.setPos(0.0F, 2.5F, -2.5F);
        this.Beak.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Beak, 0.22759093446006054F, 0.0F, 0.0F);
        this.Head3 = new ModelRenderer(this, 64, 10);
        this.Head3.setPos(0.0F, 0.5F, -5.0F);
        this.Head3.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Head3, -0.6829473363053812F, 0.0F, 0.0F);
        this.Neck2 = new ModelRenderer(this, 64, 0);
        this.Neck2.setPos(0.0F, -9.0F, -1.0F);
        this.Neck2.addBox(-2.5F, -1.5F, -6.0F, 5, 3, 6, 0.0F);
        this.setRotateAngle(Neck2, -1.0660471390760695F, 0.0F, 0.0F);
        this.Beak1 = new ModelRenderer(this, 80, 28);
        this.Beak1.setPos(0.0F, 3.2F, -0.7F);
        this.Beak1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Beak1, 0.5009094953223726F, 0.0F, 0.0F);
        this.Hat = new ModelRenderer(this, 90, 0);
        this.Hat.setPos(0.0F, 0.0F, -6.0F);
        this.Hat.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 1, 0.0F);
        
        this.Head.addChild(this.Jaw);
        this.Body_base.addChild(this.Body_straw);
        this.Body_upper.addChild(this.Neck);
        this.Head.addChild(this.Head_tooth);
        this.scepter_base.addChild(this.scepter_blade);
        this.leg_l_1.addChild(this.leg_l_2);
        this.Body_upper.addChild(this.arm_l_0);
        this.Body_upper.addChild(this.arm_r_0);
        this.Body_base.addChild(this.leg_r_0);
        this.arm_l_0.addChild(this.shoulder_l);
        this.Neck.addChild(this.Head);
        this.leg_l_0.addChild(this.leg_l_1);
        this.leg_r_1.addChild(this.leg_r_2);
        this.Body_base.addChild(this.Body_upper);
        this.Head.addChild(this.Head_stem);
        this.arm_r_0.addChild(this.shoulder_r);
        this.Jaw.addChild(this.Jaw_tooth);
        this.Body_upper.addChild(this.scarf_back);
        this.leg_r_0.addChild(this.leg_r_1);
        this.Body_upper.addChild(this.scarf);
        this.arm_r_0.addChild(this.arm_r_1);
        this.arm_l_0.addChild(this.arm_l_1);
        this.Body_base.addChild(this.leg_l_0);
        this.arm_l_1.addChild(this.scepter_base);
        /**
         * Non-Pumpkin variant
         */
        this.Body_upper.addChild(this.Head2);
        this.Head2.addChild(this.Stem2);
        /**
         * Plague Doctor variant
         */
        this.arm_l_0.addChild(this.shoulder_l);
        this.arm_r_0.addChild(this.shoulder_r);
        this.Head3.addChild(this.Beak);
        this.Neck2.addChild(this.Head3);
        this.Body_upper.addChild(this.Neck2);
        this.Body_upper.addChild(this.scarf);
        this.Beak.addChild(this.Beak1);
        this.Body_upper.addChild(this.scarf_back);
        this.Head3.addChild(this.Hat);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
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
		switch(entityIn.getSkin()) { 
		        case 0: 
		        	this.Neck.visible = true;
		        	this.Head2.visible = false;
		        	this.Neck2.visible = false;
		        	break; 
		        case 1: 
		        	this.Neck.visible = false;
		        	this.Head2.visible = true; 
		        	this.Neck2.visible = false;
		            break; 
		        case 2: 
		        	this.Neck.visible = false;
		        	this.Head2.visible = false; 
		        	this.Neck2.visible = true;
		            break; 
		        default: 
		        	break;
		} 
		
		if (this.riding) {
			this.setRotateAngle(leg_r_0, -1.0030407424264298F, 0.5082398928281348F, 0.0F);
			this.setRotateAngle(leg_r_1, 0.5707226787179512F, 0.0F, 0.0F);
			this.setRotateAngle(leg_r_2, -0.3761184404889726F, 0.0F, 0.0F);
			this.setRotateAngle(leg_l_0, -1.0030407424264298F, -0.5082398928281348F, 0.0F);
			this.setRotateAngle(leg_l_1, 0.5707226787179512F, 0.0F, 0.0F);
			this.setRotateAngle(leg_l_2, -0.3761184404889726F, 0.0F, 0.0F);
		} else if (entityIn.isSilent()) {
        	this.setRotateAngle(leg_l_0, 0.36425021489121656F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_l_1, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_l_2, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_0, 0.36425021489121656F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_1, 0.0F, 0.0F, 0.0F);
        	this.setRotateAngle(leg_r_2, 0.0F, 0.0F, 0.0F);
		} else {
	    	this.leg_r_0.xRot = 0.13072515697963366F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;	              
	        this.setRotateAngle(leg_r_1, 0.5707226787179512F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_r_2, -0.3761184404889726F, 0.0F, 0.0F);
	        this.leg_l_0.xRot = 0.13072515697963366F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.setRotateAngle(leg_l_1, 0.5707226787179512F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_l_2, -0.3761184404889726F, 0.0F, 0.0F);
		}
			
        if(!entityIn.isSilent()) {
        	switch(entityIn.getSkin()) {
        		case 0:
    		    	this.Head.yRot = netHeadYaw * 0.017453292F;
    		        this.Head.xRot = headPitch * 0.017453292F;
    		        this.Head.zRot = 0.22759093446006054F;		        
    		        this.Head.y = -1.0F + (-0.15F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));  		        
    		        if(entityIn.isAggressive())
    		        	this.Jaw.xRot = 0.67F;
    		        else 
    		        	this.Jaw.xRot = 0.22F + (-0.05F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));  		        
        			break;
        		case 1:
    		        this.Head2.yRot = netHeadYaw * 0.017453292F;
    		        this.Head2.xRot = headPitch * 0.017453292F;
        			break;
        		case 2:
    		        this.Head3.yRot = netHeadYaw * 0.017453292F;
    		        this.Head3.xRot = -0.6829473363053812F + headPitch * 0.017453292F;
        			break;
        		default:
        			break;
        	}
	    		    	        
    		if (entityIn.getMainHandItem().isEmpty()) {
    			this.scepter_base.visible = true;
    		} else {
    			this.scepter_base.visible = false;
    		}	        
	    } else {
        	this.setRotateAngle(Head, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head2, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head3, 0.12F, 0.0F, -0.22759093446006054F);
        	        	
        	this.scepter_base.visible = false;    	
        }      
    }
    
    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float i = (float)entityIn.getAttackTimer() / (float)ScarecrowEntity.ATTACK_TIMER;  
    	float Anime_threshold[] = {1.0F, 0.7F, 0.25F};
    	float Anime_ctrl;
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);
    	float l = 1.0F / (Anime_threshold[2]);
    	
    	if (i > Anime_threshold[1]) {
    		Anime_ctrl = j * (i - Anime_threshold[1]);
    		if(entityIn.AttackStance == (byte)4) {
	    		this.Body_upper.zRot = 0.0F;

	    		this.arm_l_0.xRot = GradientAnimation_s(-0.12967796042712246F, -3.141592653589793F, Anime_ctrl);
	    		this.arm_l_0.zRot = GradientAnimation_s(-0.10000736647217022F, 0.2127556284944158F, Anime_ctrl);	    		
	    		this.arm_l_1.xRot = GradientAnimation_s(-0.27366763203903305F, -1.0164797856562695F, Anime_ctrl);
	    		this.arm_l_1.zRot = 0.0F;
	    		
	    		this.arm_r_0.zRot = GradientAnimation_s(0.10000736647217022F, 0.29548424768896214F, Anime_ctrl);	    		
	    		this.arm_r_1.xRot = -0.27366763203903305F;
	    		
	    		this.scepter_base.xRot = 0.0F;
    		} else {
    			this.PerformCleaveStance(i);
    		}
    	} else if (i > Anime_threshold[2]) {
    		Anime_ctrl = k * (i - Anime_threshold[2]);
    		if(entityIn.AttackStance == (byte)4) {
	    		this.Body_upper.zRot = GradientAnimation(0.0F, 0.23457224414434488F, Anime_ctrl);

	    		this.arm_l_0.xRot = GradientAnimation(-3.141592653589793F, -0.7189010949280145F, Anime_ctrl);
	    		this.arm_l_0.zRot = 0.2127556284944158F;	    		
	    		this.arm_l_1.xRot = GradientAnimation(-1.0164797856562695F, -0.1171115934746098F, Anime_ctrl);
	    		this.arm_l_1.zRot = GradientAnimation(0.0F, 0.3909537457888271F, Anime_ctrl);
    		
	    		this.arm_r_0.zRot = GradientAnimation(0.29548424768896214F, 0.6864380267673029F, Anime_ctrl);	    		
	    		this.arm_r_1.xRot = GradientAnimation(-0.27366763203903305F, -0.9382889765773795F, Anime_ctrl);
	    		
	    		this.scepter_base.xRot = GradientAnimation(0.0F, 0.8602728042869715F, Anime_ctrl);
    		} else {
    			this.PerformCleaveStance(i);
    		}
    	} else if (i > 0.0F) {
    		Anime_ctrl = l * i;
	    		if(entityIn.AttackStance == (byte)4) {
	    		this.Body_upper.zRot = GradientAnimation_s(0.23457224414434488F, 0.0F, Anime_ctrl);
	    		
	    		this.arm_l_0.xRot = GradientAnimation_s(-0.7189010949280145F, -0.12967796042712246F, Anime_ctrl);
	    		this.arm_l_0.zRot = GradientAnimation_s(0.2127556284944158F, -0.10000736647217022F, Anime_ctrl);		    		
	    		this.arm_l_1.xRot = GradientAnimation_s(-0.1171115934746098F, -0.27366763203903305F, Anime_ctrl);
	    		this.arm_l_1.zRot = GradientAnimation_s(0.3909537457888271F, 0.0F, Anime_ctrl);
	    		
	    		this.arm_r_0.zRot = GradientAnimation_s(0.6864380267673029F, 0.10000736647217022F, Anime_ctrl);	    		
	    		this.arm_r_1.xRot = GradientAnimation_s(-0.9382889765773795F, -0.27366763203903305F, Anime_ctrl);
	    		
	    		this.scepter_base.xRot = GradientAnimation(0.8602728042869715F, 0.0F, Anime_ctrl);
    		} else {
    			this.PerformCleaveStance(i);
    		}
    	} else if(!entityIn.isSilent()) {
    		this.Body_upper.yRot = 0.0F;
    		this.Body_upper.zRot = 0.0F;    		
    		this.setRotateAngle(arm_l_0, -0.12967796042712246F, -0.017453292519943295F, -0.10000736647217022F);
    		this.setRotateAngle(arm_l_1, -0.27366763203903305F, 0.0F, 0.0F);
    		this.arm_r_0.zRot = 0.10000736647217022F;
    		this.arm_r_1.xRot = -0.27366763203903305F;
    		this.scepter_base.xRot = 0.0F;
    	} else {
    		this.Body_upper.yRot = 0.0F;
    		this.Body_upper.zRot = 0.0F;    		
    		this.setRotateAngle(arm_l_0, -0.136659280431156F, 0.0F, -1.5481070465189704F);
    		this.setRotateAngle(arm_l_1, -0.27314402793711257F, 0.0F, 0.0F);
        	this.setRotateAngle(arm_r_0, -0.136659280431156F, 0.0F, 1.5481070465189704F);
        	this.setRotateAngle(arm_r_1, -0.27314402793711257F, 0.0F, 0.0F);
        	this.scepter_base.xRot = 0.0F;
    	}
    }
    
    private void PerformCleaveStance(float AnimCtrl) {
		this.Body_upper.yRot = (float) (Math.PI * 3.0F * (1.0F - AnimCtrl));
		this.Body_upper.zRot = 0.0F;
		this.setRotateAngle(arm_l_0, -0.136659280431156F, 0.0F, -1.5481070465189704F);
		this.setRotateAngle(arm_l_1, -0.27314402793711257F, 0.0F, 0.0F);
    	this.setRotateAngle(arm_r_0, -0.136659280431156F, 0.0F, 1.5481070465189704F);
    	this.setRotateAngle(arm_r_1, -0.27314402793711257F, 0.0F, 0.0F);    	
    	this.scepter_base.xRot = 0.8602728042869715F;
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
	
    @Override
    public void translateToHand(HandSide side, MatrixStack p_225599_2_) {
    	this.Body_base.translateAndRotate(p_225599_2_);
    	this.Body_upper.translateAndRotate(p_225599_2_);
    	this.arm_l_0.translateAndRotate(p_225599_2_);
    	this.arm_l_1.translateAndRotate(p_225599_2_);
    }
}
