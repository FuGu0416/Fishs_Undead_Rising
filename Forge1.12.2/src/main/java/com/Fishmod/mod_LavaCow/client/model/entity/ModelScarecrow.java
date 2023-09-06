package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelScarecrow - Fish0016054
 * Created using Tabula 7.1.0
 */
public class ModelScarecrow extends FishModelBase {
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
    
    public ModelScarecrow() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.5201081037785047F, 0.0F, 0.0F);
        this.Body_straw = new ModelRenderer(this, 96, 27);
        this.Body_straw.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.Body_straw.addBox(-5.0F, 0.0F, -2.0F, 10, 3, 4, 0.0F);
        this.Neck = new ModelRenderer(this, 45, 1);
        this.Neck.setRotationPoint(0.0F, -10.1F, -1.9F);
        this.Neck.addBox(-3.0F, -1.5F, -3.0F, 6, 3, 3, 0.0F);
        this.setRotateAngle(Neck, -0.4286528549687435F, 0.0F, 0.0F);
        this.Head_tooth = new ModelRenderer(this, 32, 25);
        this.Head_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head_tooth.addBox(-4.0F, -1.0F, -8.0F, 8, 2, 8, 0.0F);
        this.scepter_blade = new ModelRenderer(this, 10, 37);
        this.scepter_blade.setRotationPoint(0.0F, 0.5F, -15.0F);
        this.scepter_blade.addBox(0.0F, 0.0F, -2.5F, 0, 16, 10, 0.0F);
        this.leg_l_2 = new ModelRenderer(this, 0, 44);
        this.leg_l_2.mirror = true;
        this.leg_l_2.setRotationPoint(0.0F, 6.5F, 0.1F);
        this.leg_l_2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_l_2, -0.3761184404889726F, 0.0F, 0.0F);
        this.arm_l_0 = new ModelRenderer(this, 18, 29);
        this.arm_l_0.mirror = true;
        this.arm_l_0.setRotationPoint(7.0F, -7.0F, -1.0F);
        this.arm_l_0.addBox(-0.5F, -2.0F, -1.5F, 3, 14, 3, 0.0F);
        this.setRotateAngle(arm_l_0, -0.12967796042712246F, -0.017453292519943295F, -0.10000736647217022F);
        this.arm_r_0 = new ModelRenderer(this, 18, 29);
        this.arm_r_0.setRotationPoint(-7.0F, -7.0F, -1.0F);
        this.arm_r_0.addBox(-2.0F, -2.0F, -1.5F, 3, 14, 3, 0.0F);
        this.setRotateAngle(arm_r_0, -0.12967796042712246F, 0.017453292519943295F, 0.10000736647217022F);
        this.leg_r_0 = new ModelRenderer(this, 0, 30);
        this.leg_r_0.setRotationPoint(-2.5F, -2.0F, 0.1F);
        this.leg_r_0.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_r_0, 0.13072515697963366F, 0.0F, 0.0F);
        this.shoulder_l = new ModelRenderer(this, 64, 51);
        this.shoulder_l.mirror = true;
        this.shoulder_l.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shoulder_l.addBox(-1.5F, -3.5F, -3.0F, 5, 6, 6, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -1.0F, -3.0F);
        this.Head.addBox(-4.0F, -5.0F, -8.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(Head, 0.19547687289441354F, 0.0F, 0.0F);
        this.leg_l_1 = new ModelRenderer(this, 0, 30);
        this.leg_l_1.mirror = true;
        this.leg_l_1.setRotationPoint(0.0F, 7.0F, 0.1F);
        this.leg_l_1.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(leg_l_1, 0.5707226787179512F, 0.0F, 0.0F);
        this.leg_r_2 = new ModelRenderer(this, 0, 44);
        this.leg_r_2.setRotationPoint(0.0F, 6.5F, 0.1F);
        this.leg_r_2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_r_2, -0.3761184404889726F, 0.0F, 0.0F);
        this.Body_upper = new ModelRenderer(this, 92, 11);
        this.Body_upper.setRotationPoint(0.0F, -8.1F, 1.3F);
        this.Body_upper.addBox(-6.0F, -10.0F, -3.3F, 12, 10, 5, 0.0F);
        this.setRotateAngle(Body_upper, 0.6365215995076208F, 0.0F, 0.0F);
        this.Head_stem = new ModelRenderer(this, 0, 0);
        this.Head_stem.setRotationPoint(0.0F, -4.0F, -4.0F);
        this.Head_stem.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Head_stem, -0.5462880425584197F, 0.0F, 0.0F);
        this.shoulder_r = new ModelRenderer(this, 64, 51);
        this.shoulder_r.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shoulder_r.addBox(-3.5F, -3.5F, -3.0F, 5, 6, 6, 0.0F);
        this.Jaw_tooth = new ModelRenderer(this, 32, 38);
        this.Jaw_tooth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw_tooth.addBox(-4.0F, -2.0F, -8.0F, 8, 2, 8, 0.0F);
        this.Body_base = new ModelRenderer(this, 34, 9);
        this.Body_base.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.Body_base.addBox(-5.0F, -10.0F, -2.0F, 10, 8, 4, 0.0F);
        this.setRotateAngle(Body_base, -0.3642502295386026F, 0.0F, 0.0F);
        this.scarf_back = new ModelRenderer(this, 84, 35);
        this.scarf_back.mirror = true;
        this.scarf_back.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.scarf_back.addBox(-6.0F, 2.0F, -20.0F, 12, 0, 18, 0.0F);
        this.setRotateAngle(scarf_back, 1.5934856603340446F, 0.0F, 0.0F);
        this.leg_r_1 = new ModelRenderer(this, 0, 30);
        this.leg_r_1.setRotationPoint(0.0F, 7.0F, 0.1F);
        this.leg_r_1.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(leg_r_1, 0.5707226787179512F, 0.0F, 0.0F);
        this.scarf = new ModelRenderer(this, 64, 34);
        this.scarf.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.scarf.addBox(-6.0F, 0.0F, -3.0F, 12, 11, 6, 0.0F);
        this.setRotateAngle(scarf, -0.23457224414434488F, 0.0F, 0.0F);
        this.arm_r_1 = new ModelRenderer(this, 10, 30);
        this.arm_r_1.setRotationPoint(-0.5F, 10.0F, 0.0F);
        this.arm_r_1.addBox(-1.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_r_1, -0.27366763203903305F, 0.0F, 0.0F);
        this.arm_l_1 = new ModelRenderer(this, 10, 30);
        this.arm_l_1.mirror = true;
        this.arm_l_1.setRotationPoint(1.0F, 10.0F, 0.0F);
        this.arm_l_1.addBox(-1.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(arm_l_1, -0.27366763203903305F, 0.0F, 0.0F);
        this.leg_l_0 = new ModelRenderer(this, 0, 30);
        this.leg_l_0.mirror = true;
        this.leg_l_0.setRotationPoint(2.5F, -2.0F, 0.1F);
        this.leg_l_0.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leg_l_0, 0.13072515697963366F, 0.0F, 0.0F);
        this.scepter_base = new ModelRenderer(this, 0, 33);
        this.scepter_base.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.scepter_base.addBox(-0.5F, -0.5F, -18.0F, 1, 1, 30, 0.0F);
        /**
         * Non-Pumpkin variant
         */
        this.Head2 = new ModelRenderer(this, 40, 49);
        this.Head2.setRotationPoint(0.0F, -11.0F, -2.0F);
        this.Head2.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.Stem2 = new ModelRenderer(this, 33, 48);
        this.Stem2.setRotationPoint(0.0F, -7.6F, 2.3F);
        this.Stem2.addBox(-1.5F, -3.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Stem2, -0.8196066167365371F, 0.0F, 0.0F);
        /**
         * Plague Doctor variant
         */
        this.Beak = new ModelRenderer(this, 64, 26);
        this.Beak.setRotationPoint(0.0F, 2.5F, -2.5F);
        this.Beak.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Beak, 0.22759093446006054F, 0.0F, 0.0F);
        this.Head3 = new ModelRenderer(this, 64, 10);
        this.Head3.setRotationPoint(0.0F, 0.5F, -5.0F);
        this.Head3.addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8, 0.0F);
        this.setRotateAngle(Head3, -0.6829473363053812F, 0.0F, 0.0F);
        this.Neck2 = new ModelRenderer(this, 64, 0);
        this.Neck2.setRotationPoint(0.0F, -9.0F, -1.0F);
        this.Neck2.addBox(-2.5F, -1.5F, -6.0F, 5, 3, 6, 0.0F);
        this.setRotateAngle(Neck2, -1.0660471390760695F, 0.0F, 0.0F);
        this.Beak1 = new ModelRenderer(this, 80, 28);
        this.Beak1.setRotationPoint(0.0F, 3.2F, -0.7F);
        this.Beak1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Beak1, 0.5009094953223726F, 0.0F, 0.0F);
        this.Hat = new ModelRenderer(this, 90, 0);
        this.Hat.setRotationPoint(0.0F, 0.0F, -6.0F);
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
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.Body_base.render(scale);
    }

    
    public void postRenderArm(float scale, EnumHandSide side)
    {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.arm_l_1 : this.arm_r_1;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	switch(((EntityScarecrow) entityIn).getSkin()) { 
		        case 0: 
		        	this.Neck.isHidden = false;
		        	this.Head2.isHidden = true;
		        	this.Neck2.isHidden = true;
		        	break; 
		        case 1: 
		        	this.Neck.isHidden = true;
		        	this.Head2.isHidden = false; 
		        	this.Neck2.isHidden = true;
		            break; 
		        case 2: 
		        	this.Neck.isHidden = true;
		        	this.Head2.isHidden = true; 
		        	this.Neck2.isHidden = false;
		            break; 
		        default: 
		        	break;
    	}
    	
		if (this.isRiding) {
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
	    	this.leg_r_0.rotateAngleX = 0.13072515697963366F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;	              
	        this.setRotateAngle(leg_r_1, 0.5707226787179512F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_r_2, -0.3761184404889726F, 0.0F, 0.0F);
	        this.leg_l_0.rotateAngleX = 0.13072515697963366F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
	        this.setRotateAngle(leg_l_1, 0.5707226787179512F, 0.0F, 0.0F);
	        this.setRotateAngle(leg_l_2, -0.3761184404889726F, 0.0F, 0.0F);
		}
    	
        if(!((EntityScarecrow) entityIn).isSilent()) {
	    	
        	switch(((EntityScarecrow) entityIn).getSkin()) {
        		case 0:
    		    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head.rotateAngleX = headPitch * 0.017453292F;
    		        this.Head.rotateAngleZ = 0.22759093446006054F;
    		        this.Head.rotationPointY = -1.0F + (-0.15F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));
    		        if(((EntityScarecrow) entityIn).isAggressive())
    		        	this.Jaw.rotateAngleX = 0.67F;
    		        else 
    		        	this.Jaw.rotateAngleX = 0.22F + (-0.05F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));
        			break;
        		case 1:
    		        this.Head2.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head2.rotateAngleX = headPitch * 0.017453292F;
        			break;
        		case 2:
    		        this.Head3.rotateAngleY = netHeadYaw * 0.017453292F;
    		        this.Head3.rotateAngleX = -0.6829473363053812F + headPitch * 0.017453292F;
        			break;
        		default:
        			break;
        	}
	        
	        this.scepter_base.isHidden = false;	        
	    }
        else {
        	this.setRotateAngle(Head, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head2, 0.12F, 0.0F, -0.22759093446006054F);
        	this.setRotateAngle(Head3, 0.12F, 0.0F, -0.22759093446006054F);
        	
        	this.scepter_base.isHidden = true;     	
        }
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
        float i = ((EntityScarecrow) entityIn).getAttackTimer() / 15.0F;
    	float Anime_threshold[] = {1.0F, 0.7F, 0.25F};
    	float Anime_ctrl;
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);
    	float l = 1.0F / (Anime_threshold[2]);
        	
    	if (i > Anime_threshold[1]) {
    		Anime_ctrl = j * (i - Anime_threshold[1]);
    		if(((EntityScarecrow)entityIn).AttackStance == (byte)4) {
	    		this.Body_upper.rotateAngleZ = 0.0F;

	    		this.arm_l_0.rotateAngleX = GradientAnimation_s(-0.12967796042712246F, -3.141592653589793F, Anime_ctrl);
	    		this.arm_l_0.rotateAngleZ = GradientAnimation_s(-0.10000736647217022F, 0.2127556284944158F, Anime_ctrl);	    		
	    		this.arm_l_1.rotateAngleX = GradientAnimation_s(-0.27366763203903305F, -1.0164797856562695F, Anime_ctrl);
	    		this.arm_l_1.rotateAngleZ = 0.0F;
	    		
	    		this.arm_r_0.rotateAngleZ = GradientAnimation_s(0.10000736647217022F, 0.29548424768896214F, Anime_ctrl);	    		
	    		this.arm_r_1.rotateAngleX = -0.27366763203903305F;
	    		
	    		this.scepter_base.rotateAngleX = 0.0F;
    		} else {
    			this.PerformCleaveStance(i);
    		}
    	} else if (i > Anime_threshold[2]) {
    		Anime_ctrl = k * (i - Anime_threshold[2]);
    		if(((EntityScarecrow)entityIn).AttackStance == (byte)4) {
	    		this.Body_upper.rotateAngleZ = GradientAnimation(0.0F, 0.23457224414434488F, Anime_ctrl);

	    		this.arm_l_0.rotateAngleX = GradientAnimation(-3.141592653589793F, -0.7189010949280145F, Anime_ctrl);
	    		this.arm_l_0.rotateAngleZ = 0.2127556284944158F;	    		
	    		this.arm_l_1.rotateAngleX = GradientAnimation(-1.0164797856562695F, -0.1171115934746098F, Anime_ctrl);
	    		this.arm_l_1.rotateAngleZ = GradientAnimation(0.0F, 0.3909537457888271F, Anime_ctrl);
    		
	    		this.arm_r_0.rotateAngleZ = GradientAnimation(0.29548424768896214F, 0.6864380267673029F, Anime_ctrl);	    		
	    		this.arm_r_1.rotateAngleX = GradientAnimation(-0.27366763203903305F, -0.9382889765773795F, Anime_ctrl);
	    		
	    		this.scepter_base.rotateAngleX = GradientAnimation(0.0F, 0.8602728042869715F, Anime_ctrl);
    		} else {
    			this.PerformCleaveStance(i);
    		}
    	} else if (i > 0.0F) {
    		Anime_ctrl = l * i;
	    		if(((EntityScarecrow)entityIn).AttackStance == (byte)4) {
	    		this.Body_upper.rotateAngleZ = GradientAnimation_s(0.23457224414434488F, 0.0F, Anime_ctrl);
	    		
	    		this.arm_l_0.rotateAngleX = GradientAnimation_s(-0.7189010949280145F, -0.12967796042712246F, Anime_ctrl);
	    		this.arm_l_0.rotateAngleZ = GradientAnimation_s(0.2127556284944158F, -0.10000736647217022F, Anime_ctrl);		    		
	    		this.arm_l_1.rotateAngleX = GradientAnimation_s(-0.1171115934746098F, -0.27366763203903305F, Anime_ctrl);
	    		this.arm_l_1.rotateAngleZ = GradientAnimation_s(0.3909537457888271F, 0.0F, Anime_ctrl);
	    		
	    		this.arm_r_0.rotateAngleZ = GradientAnimation_s(0.6864380267673029F, 0.10000736647217022F, Anime_ctrl);	    		
	    		this.arm_r_1.rotateAngleX = GradientAnimation_s(-0.9382889765773795F, -0.27366763203903305F, Anime_ctrl);
	    		
	    		this.scepter_base.rotateAngleX = GradientAnimation(0.8602728042869715F, 0.0F, Anime_ctrl);
    		} else {
    			this.PerformCleaveStance(i);
    			}
    		} else if(!entityIn.isSilent()) { 	
	        	this.Body_upper.rotateAngleY = 0.0F;
	        	this.Body_upper.rotateAngleZ = 0.0F;
	        	
	        	this.setRotateAngle(arm_l_0, -0.12967796042712246F, -0.017453292519943295F, -0.10000736647217022F);
	        	this.setRotateAngle(arm_l_1, -0.27366763203903305F, 0.0F, 0.0F);
	        	this.arm_r_0.rotateAngleZ = 0.10000736647217022F;
	        	this.arm_r_1.rotateAngleX = -0.27366763203903305F;    
	        	
	        	this.scepter_base.rotateAngleX = 0.0F;
	        } else {	        	
	        	this.Body_upper.rotateAngleY = 0.0F;
	        	this.Body_upper.rotateAngleZ = 0.0F;
	        	
	        	this.setRotateAngle(arm_l_0, -0.136659280431156F, 0.0F, -1.5481070465189704F);
	        	this.setRotateAngle(arm_l_1, -0.27314402793711257F, 0.0F, 0.0F);
	        	this.setRotateAngle(arm_r_0, -0.136659280431156F, 0.0F, 1.5481070465189704F);
	        	this.setRotateAngle(arm_r_1, -0.27314402793711257F, 0.0F, 0.0F);      
	        	
	        	this.scepter_base.rotateAngleX = 0.0F;
	        }
		}
    
    private void PerformCleaveStance(float AnimCtrl) {
		this.Body_upper.rotateAngleY = (float) (Math.PI * 3.0F * (1.0F - AnimCtrl));
		this.Body_upper.rotateAngleZ = 0.0F;
		this.setRotateAngle(arm_l_0, -0.136659280431156F, 0.0F, -1.5481070465189704F);
		this.setRotateAngle(arm_l_1, -0.27314402793711257F, 0.0F, 0.0F);
    	this.setRotateAngle(arm_r_0, -0.136659280431156F, 0.0F, 1.5481070465189704F);
    	this.setRotateAngle(arm_r_1, -0.27314402793711257F, 0.0F, 0.0F);    	
    	this.scepter_base.rotateAngleX = 0.8602728042869715F;
    }
}
