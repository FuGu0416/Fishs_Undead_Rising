package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityAmberLord;
import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSludgeLord - Fish0016054
 * Created using Tabula 7.0.1
 */
public class ModelSludgeLord extends FishModelBase {
    public ModelRenderer Body_base;
    public ModelRenderer Head_base;
    public ModelRenderer Arm_l_seg0;
    public ModelRenderer Waist0;
    public ModelRenderer Head_Moss_r;
    public ModelRenderer Arm_r_seg0;
    public ModelRenderer Head_Twig0;
    public ModelRenderer Head_Twig4;
    public ModelRenderer Head;
	private final ModelRenderer antenne_l;
	private final ModelRenderer antenne_r;
    public ModelRenderer Head_bubble;
    public ModelRenderer Head_bubble_in;
    public ModelRenderer Head_Twig1;
    public ModelRenderer Head_Moss_l;
    public ModelRenderer Head_Twig2;
    public ModelRenderer Head_Twig3;
    public ModelRenderer Jaw;
    public ModelRenderer Head_inside;
    public ModelRenderer Arm_l_seg1;
    public ModelRenderer Arm_l_seg2;
    public ModelRenderer Arm_l_knot0;
    public ModelRenderer Arm_l_knot1;
    public ModelRenderer Arm_l_Finger0_seg0;
    public ModelRenderer Arm_l_Finger1_seg0;
    public ModelRenderer Arm_l_Finger2_seg0;
    public ModelRenderer Arm_l_Finger0_Seg1;
    public ModelRenderer Arm_l_Finger1_Seg1;
    public ModelRenderer Arm_l_Finger2_Seg1;
    public ModelRenderer Waist1;
    public ModelRenderer Waist0_Twig0_l;
    public ModelRenderer Waist0_Twig0_r;
    public ModelRenderer Leg_r_seg0;
    public ModelRenderer Leg_l_seg0;
    public ModelRenderer Waist1_Twig0_l;
    public ModelRenderer Leg_r_seg1;
    public ModelRenderer Leg_l_seg1;
    public ModelRenderer Waist1_Twig1_l;
    public ModelRenderer Waist0_Twig1_l;
    public ModelRenderer Waist0_Twig2_l;
    public ModelRenderer Waist0_Twig1_r;
    public ModelRenderer Arm_r_seg1;
    public ModelRenderer Arm_r_seg2;
    public ModelRenderer Arm_r_knot0;
    public ModelRenderer Arm_r_knot1;
    public ModelRenderer Arm_r_Finger0_seg0;
    public ModelRenderer Arm_r_Finger1_seg0;
    public ModelRenderer Arm_r_Finger2_seg0;
    public ModelRenderer Arm_r_Finger0_Seg1;
    public ModelRenderer Arm_r_Finger1_Seg1;
    public ModelRenderer Arm_r_Finger2_Seg1;

    public ModelSludgeLord() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.Arm_l_seg2 = new ModelRenderer(this, 88, 88);
        this.Arm_l_seg2.mirror = true;
        this.Arm_l_seg2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Arm_l_seg2.addBox(-5.0F, -6.0F, -5.0F, 10, 20, 10, 0.0F);
        this.setRotateAngle(Arm_l_seg2, 0.0F, 0.0F, 0.46931902520863084F);
        this.Arm_l_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_l_seg0.mirror = true;
        this.Arm_l_seg0.setRotationPoint(14.0F, -1.0F, 2.0F);
        this.Arm_l_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Arm_l_seg0, 0.0F, 0.0F, -1.092750655326294F);
        this.Waist0 = new ModelRenderer(this, 0, 80);
        this.Waist0.setRotationPoint(0.0F, 4.0F, 1.0F);
        this.Waist0.addBox(-11.0F, -10.0F, -5.5F, 22, 10, 11, 0.0F);
        this.setRotateAngle(Waist0, 0.5232497017584168F, 0.0F, 0.0F);
        this.Arm_l_knot0 = new ModelRenderer(this, 0, 10);
        this.Arm_l_knot0.setRotationPoint(5.0F, -1.0F, 0.0F);
        this.Arm_l_knot0.addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.Head_inside = new ModelRenderer(this, 146, 77);
        this.Head_inside.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Head_inside.addBox(-3.0F, -4.0F, -3.0F, 6, 4, 6, 0.0F);
        this.Leg_l_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_l_seg1.mirror = true;
        this.Leg_l_seg1.setRotationPoint(0.0F, 8.6F, 0.4F);
        this.Leg_l_seg1.addBox(-4.0F, -2.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(Leg_l_seg1, 0.26424285474405396F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 204, 92);
        this.Jaw.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8, 3, 8, 0.0F);
        this.setRotateAngle(Jaw, 0.2275909337942703F, 0.0F, 0.0F);
        this.Arm_l_Finger2_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger2_seg0.mirror = true;
        this.Arm_l_Finger2_seg0.setRotationPoint(-3.5F, 12.0F, -0.6F);
        this.Arm_l_Finger2_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger2_seg0, -0.46931902520863084F, 1.5707963267948966F, 0.0F);
        this.Head_Twig3 = new ModelRenderer(this, 68, 36);
        this.Head_Twig3.setRotationPoint(5.5F, -6.0F, -10.0F);
        this.Head_Twig3.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Waist0_Twig0_l = new ModelRenderer(this, 20, 0);
        this.Waist0_Twig0_l.mirror = true;
        this.Waist0_Twig0_l.setRotationPoint(5.5F, -5.6F, 4.0F);
        this.Waist0_Twig0_l.addBox(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
        this.setRotateAngle(Waist0_Twig0_l, -1.5934856603340446F, 0.45535640450848164F, 0.0F);
        this.Arm_r_Finger2_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_r_Finger2_Seg1.mirror = true;
        this.Arm_r_Finger2_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger2_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger2_Seg1, 1.5707963267948966F, 0.0F, 0.0F);
        this.Arm_l_knot1 = new ModelRenderer(this, 0, 10);
        this.Arm_l_knot1.setRotationPoint(5.0F, 7.0F, 0.0F);
        this.Arm_l_knot1.addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.Head_bubble = new ModelRenderer(this, 0, 24);
        this.Head_bubble.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Head_bubble.addBox(-7.0F, -15.0F, -7.0F, 14, 14, 14, 0.0F);
        this.Head_bubble_in = new ModelRenderer(this, 144, 40);
        this.Head_bubble_in.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.Head_bubble_in.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
        this.Waist1_Twig0_l = new ModelRenderer(this, 70, 48);
        this.Waist1_Twig0_l.setRotationPoint(5.0F, 4.5F, 3.0F);
        this.Waist1_Twig0_l.addBox(-1.5F, -14.0F, -1.5F, 3, 14, 3, 0.0F);
        this.setRotateAngle(Waist1_Twig0_l, -1.3723523429062416F, 0.6176720116281489F, 0.35185837453889574F);
        this.Leg_l_seg0 = new ModelRenderer(this, 78, 28);
        this.Leg_l_seg0.mirror = true;
        this.Leg_l_seg0.setRotationPoint(6.5F, 9.0F, 0.7F);
        this.Leg_l_seg0.addBox(-3.0F, -2.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(Leg_l_seg0, -0.26424285474405396F, 0.0F, 0.0F);
        this.Arm_l_seg1 = new ModelRenderer(this, 96, 64);
        this.Arm_l_seg1.setRotationPoint(0.5F, 13.0F, 0.0F);
        this.Arm_l_seg1.addBox(-4.0F, -6.0F, -4.0F, 8, 12, 8, 0.0F);
        this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
        this.Waist0_Twig0_r = new ModelRenderer(this, 20, 0);
        this.Waist0_Twig0_r.setRotationPoint(-6.0F, -3.7F, 4.0F);
        this.Waist0_Twig0_r.addBox(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
        this.setRotateAngle(Waist0_Twig0_r, -1.411447814024714F, -0.7740534966278743F, 0.0F);
        this.Head_Moss_r = new ModelRenderer(this, 98, 5);
        this.Head_Moss_r.setRotationPoint(0.0F, 5.0F, -3.0F);
        this.Head_Moss_r.addBox(0.0F, 0.0F, -8.0F, 0, 12, 16, 0.0F);
        this.setRotateAngle(Head_Moss_r, 0.001745329278001762F, 1.5707963267948966F, 0.0F);
        this.Arm_l_Finger2_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_l_Finger2_Seg1.mirror = true;
        this.Arm_l_Finger2_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger2_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger2_Seg1, 1.5707963267948966F, 0.0F, 0.0F);
        this.Head_Moss_l = new ModelRenderer(this, 58, 0);
        this.Head_Moss_l.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.Head_Moss_l.addBox(-10.0F, -1.4F, -2.0F, 20, 14, 6, 0.0F);
        this.setRotateAngle(Head_Moss_l, -0.27366763203903305F, 0.0F, 0.0F);
        this.Arm_r_Finger0_Seg1 = new ModelRenderer(this, 130, 97);
        this.Arm_r_Finger0_Seg1.mirror = true;
        this.Arm_r_Finger0_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger0_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger0_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Waist0_Twig1_l = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig1_l.setRotationPoint(0.0F, -14.8F, 0.4F);
        this.Waist0_Twig1_l.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist0_Twig1_l, 0.6829473549475088F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 146, 92);
        this.Head.setRotationPoint(0.0F, -13.0F, -1.0F);
        this.Head.addBox(-4.0F, -6.0F, -4.0F, 8, 8, 8, 0.0F);
        
        this.antenne_l = new ModelRenderer(this);
        this.antenne_l.setRotationPoint(3.0F, -5.6F, -3.1F);
        this.Head.addChild(antenne_l);
        this.setRotateAngle(antenne_l, -0.5672F, 0.0F, 0.0F);
		this.antenne_l.setTextureOffset(0, 0).addBox(-1.5F, 0.0F, -4.75F, 1, 0, 5, false);

		this.antenne_r = new ModelRenderer(this);
		this.antenne_r.setRotationPoint(-2.0F, -5.6F, -3.1F);
		this.Head.addChild(antenne_r);
		this.setRotateAngle(antenne_r, -0.5672F, 0.0F, 0.0F);
		this.antenne_r.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -4.75F, 1, 0, 5, true);
		
        this.Arm_r_knot1 = new ModelRenderer(this, 0, 10);
        this.Arm_r_knot1.mirror = true;
        this.Arm_r_knot1.setRotationPoint(-5.0F, 7.0F, 0.0F);
        this.Arm_r_knot1.addBox(-2.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.Head_Twig2 = new ModelRenderer(this, 59, 44);
        this.Head_Twig2.setRotationPoint(0.0F, -6.0F, -10.0F);
        this.Head_Twig2.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.Arm_r_Finger2_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger2_seg0.mirror = true;
        this.Arm_r_Finger2_seg0.setRotationPoint(3.5F, 12.0F, -0.6F);
        this.Arm_r_Finger2_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger2_seg0, -0.46931902520863084F, -1.5707963267948966F, 0.0F);
        this.Head_base = new ModelRenderer(this, 136, 0);
        this.Head_base.setRotationPoint(0.0F, -3.4F, -1.9F);
        this.Head_base.addBox(-11.0F, -6.0F, -11.0F, 22, 6, 22, 0.0F);
        this.setRotateAngle(Head_base, 0.27366763203903305F, 0.0F, 0.0F);
        this.Arm_r_Finger1_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_r_Finger1_Seg1.mirror = true;
        this.Arm_r_Finger1_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger1_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger1_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Arm_r_seg2 = new ModelRenderer(this, 88, 88);
        this.Arm_r_seg2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Arm_r_seg2.addBox(-5.0F, -6.0F, -5.0F, 10, 20, 10, 0.0F);
        this.setRotateAngle(Arm_r_seg2, 0.0F, 0.0F, -0.46931902520863084F);
        this.Leg_r_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_r_seg1.setRotationPoint(0.0F, 8.6F, 0.4F);
        this.Leg_r_seg1.addBox(-4.0F, -2.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(Leg_r_seg1, 0.26424285474405396F, 0.0F, 0.0F);
        this.Waist0_Twig1_r = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig1_r.setRotationPoint(2.0F, -16.0F, 0.0F);
        this.Waist0_Twig1_r.addBox(-4.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist0_Twig1_r, 0.0F, 0.0F, -0.45535640450848164F);
        this.Arm_r_seg1 = new ModelRenderer(this, 96, 64);
        this.Arm_r_seg1.mirror = true;
        this.Arm_r_seg1.setRotationPoint(-0.5F, 13.0F, 0.0F);
        this.Arm_r_seg1.addBox(-4.0F, -6.0F, -4.0F, 8, 12, 8, 0.0F);
        this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        this.Leg_r_seg0 = new ModelRenderer(this, 78, 28);
        this.Leg_r_seg0.setRotationPoint(-6.5F, 9.0F, 0.7F);
        this.Leg_r_seg0.addBox(-3.0F, -2.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(Leg_r_seg0, -0.26424285474405396F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 54);
        this.Body_base.setRotationPoint(0.0F, -12.5F, -1.0F);
        this.Body_base.addBox(-14.0F, -7.0F, -4.0F, 28, 12, 12, 0.0F);
        this.setRotateAngle(Body_base, -0.5110324169681646F, 0.0F, 0.0F);
        this.Arm_r_knot0 = new ModelRenderer(this, 0, 10);
        this.Arm_r_knot0.mirror = true;
        this.Arm_r_knot0.setRotationPoint(-5.0F, -1.0F, 0.0F);
        this.Arm_r_knot0.addBox(-2.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.Head_Twig1 = new ModelRenderer(this, 59, 36);
        this.Head_Twig1.setRotationPoint(-5.0F, -6.0F, -10.0F);
        this.Head_Twig1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
        this.Waist0_Twig2_l = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig2_l.mirror = true;
        this.Waist0_Twig2_l.setRotationPoint(-0.4F, -10.8F, 0.0F);
        this.Waist0_Twig2_l.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Waist0_Twig2_l, 0.0F, 0.0F, 0.6373942508178124F);
        this.Arm_l_Finger0_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger0_seg0.mirror = true;
        this.Arm_l_Finger0_seg0.setRotationPoint(4.0F, 13.0F, -2.7F);
        this.Arm_l_Finger0_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger0_seg0, 0.3127630032889644F, -1.2189378856769737F, 0.0F);
        this.Waist1 = new ModelRenderer(this, 0, 102);
        this.Waist1.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Waist1.addBox(-10.0F, 0.0F, -4.5F, 20, 10, 9, 0.0F);
        this.Arm_r_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_r_seg0.setRotationPoint(-14.0F, -1.0F, 2.0F);
        this.Arm_r_seg0.addBox(-2.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Arm_r_seg0, 0.0F, 0.0F, 1.092750655326294F);
        this.Arm_l_Finger1_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger1_seg0.mirror = true;
        this.Arm_l_Finger1_seg0.setRotationPoint(4.0F, 13.0F, 2.7F);
        this.Arm_l_Finger1_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger1_seg0, 0.23457224414434488F, -1.9226547679128194F, 0.0F);
        this.Arm_r_Finger0_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger0_seg0.mirror = true;
        this.Arm_r_Finger0_seg0.setRotationPoint(-4.0F, 13.0F, -2.7F);
        this.Arm_r_Finger0_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger0_seg0, 0.3127630032889644F, 1.2189378856769737F, 0.0F);
        this.Arm_l_Finger0_Seg1 = new ModelRenderer(this, 130, 97);
        this.Arm_l_Finger0_Seg1.mirror = true;
        this.Arm_l_Finger0_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger0_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger0_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Head_Twig0 = new ModelRenderer(this, 45, 24);
        this.Head_Twig0.setRotationPoint(-9.5F, -6.0F, -9.5F);
        this.Head_Twig0.addBox(-1.5F, -9.0F, -1.5F, 3, 9, 3, 0.0F);
        this.Waist1_Twig1_l = new ModelRenderer(this, 84, 48);
        this.Waist1_Twig1_l.setRotationPoint(-1.5F, -14.0F, 0.0F);
        this.Waist1_Twig1_l.addBox(0.0F, -12.0F, -1.5F, 3, 12, 3, 0.0F);
        this.setRotateAngle(Waist1_Twig1_l, 0.0F, 0.0F, 0.650833294047652F);
        this.Arm_r_Finger1_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger1_seg0.mirror = true;
        this.Arm_r_Finger1_seg0.setRotationPoint(-4.0F, 13.0F, 2.7F);
        this.Arm_r_Finger1_seg0.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2, 0.0F);
        this.setRotateAngle(Arm_r_Finger1_seg0, 0.23457224414434488F, 1.9226547679128194F, 0.0F);
        this.Arm_l_Finger1_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_l_Finger1_Seg1.mirror = true;
        this.Arm_l_Finger1_Seg1.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger1_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2, 0.0F);
        this.setRotateAngle(Arm_l_Finger1_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Head_Twig4 = new ModelRenderer(this, 59, 24);
        this.Head_Twig4.setRotationPoint(9.5F, -6.0F, -9.5F);
        this.Head_Twig4.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
        this.Arm_l_seg1.addChild(this.Arm_l_seg2);
        this.Body_base.addChild(this.Arm_l_seg0);
        this.Waist1.addChild(this.Waist0);
        this.Arm_l_seg2.addChild(this.Arm_l_knot0);
        this.Head.addChild(this.Head_inside);
        this.Leg_l_seg0.addChild(this.Leg_l_seg1);
        this.Head.addChild(this.Jaw);
        this.Arm_l_seg2.addChild(this.Arm_l_Finger2_seg0);
        this.Head_base.addChild(this.Head_Twig3);
        this.Waist0.addChild(this.Waist0_Twig0_l);
        this.Arm_r_Finger2_seg0.addChild(this.Arm_r_Finger2_Seg1);
        this.Arm_l_seg2.addChild(this.Arm_l_knot1);
        this.Waist1.addChild(this.Waist1_Twig0_l);
        this.Waist1.addChild(this.Leg_l_seg0);
        this.Arm_l_seg0.addChild(this.Arm_l_seg1);
        this.Waist0.addChild(this.Waist0_Twig0_r);
        this.Body_base.addChild(this.Head_Moss_r);
        this.Arm_l_Finger2_seg0.addChild(this.Arm_l_Finger2_Seg1);
        this.Head_base.addChild(this.Head_Moss_l);
        this.Arm_r_Finger0_seg0.addChild(this.Arm_r_Finger0_Seg1);
        this.Waist0_Twig0_l.addChild(this.Waist0_Twig1_l);
        this.Head_base.addChild(this.Head);
        this.Head_base.addChild(this.Head_bubble);
        this.Head_base.addChild(this.Head_bubble_in);
        this.Arm_r_seg2.addChild(this.Arm_r_knot1);
        this.Head_base.addChild(this.Head_Twig2);
        this.Arm_r_seg2.addChild(this.Arm_r_Finger2_seg0);
        this.Body_base.addChild(this.Head_base);
        this.Arm_r_Finger1_seg0.addChild(this.Arm_r_Finger1_Seg1);
        this.Arm_r_seg1.addChild(this.Arm_r_seg2);
        this.Leg_r_seg0.addChild(this.Leg_r_seg1);
        this.Waist0_Twig0_r.addChild(this.Waist0_Twig1_r);
        this.Arm_r_seg0.addChild(this.Arm_r_seg1);
        this.Waist1.addChild(this.Leg_r_seg0);
        this.Waist0.addChild(this.Body_base);
        this.Arm_r_seg2.addChild(this.Arm_r_knot0);
        this.Head_base.addChild(this.Head_Twig1);
        this.Waist0_Twig1_l.addChild(this.Waist0_Twig2_l);
        this.Arm_l_seg2.addChild(this.Arm_l_Finger0_seg0);
        this.Body_base.addChild(this.Arm_r_seg0);
        this.Arm_l_seg2.addChild(this.Arm_l_Finger1_seg0);
        this.Arm_r_seg2.addChild(this.Arm_r_Finger0_seg0);
        this.Arm_l_Finger0_seg0.addChild(this.Arm_l_Finger0_Seg1);
        this.Head_base.addChild(this.Head_Twig0);
        this.Waist1_Twig0_l.addChild(this.Waist1_Twig1_l);
        this.Arm_r_seg2.addChild(this.Arm_r_Finger1_seg0);
        this.Arm_l_Finger1_seg0.addChild(this.Arm_l_Finger1_Seg1);
        this.Head_base.addChild(this.Head_Twig4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.Waist1.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	this.Head_base.rotationPointY = -3.4F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
    	this.Head.rotateAngleX = headPitch * 0.017453292F;
    	this.Head.rotateAngleY = netHeadYaw * 0.017453292F;        
    	this.Head.rotationPointY = entityIn instanceof EntityAmberLord ? -13.0F : -11.0F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
        this.Jaw.rotateAngleX = 0.15F + 0.11F * MathHelper.sin(0.03F * ageInTicks);                 
    }
    
    @Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	EntitySludgeLord Entity = ((EntitySludgeLord)entity);
    	float i = (float)Entity.getAttackTimer() / (float)EntitySludgeLord.ATTACK_TIMER;  
    	float Anime_threshold[] = {1.0F, 0.5F, 0.2F};
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);

    	float spl = (float)Entity.getSpellTicks() / 100.0F;
    	float spl_Anime_threshold[] = {1.0F, 0.72F, 0.27F, 0.15F};
    	float spl0 = 1.0F / (spl_Anime_threshold[0] - spl_Anime_threshold[1]);
    	float spl1 = 1.0F / (spl_Anime_threshold[1] - spl_Anime_threshold[2]);
    	float spl3 = 1.0F / spl_Anime_threshold[3];

    	float Ratk = (float)Entity.getRAttackTimer() / 40.0F; 
    	float Ratk_Anime_threshold[] = {1.0F, 0.5F, 0.2F};
    	float Ratk0 = 1.0F / (Ratk_Anime_threshold[0] - Ratk_Anime_threshold[1]);
    	float Ratk1 = 1.0F / (Ratk_Anime_threshold[1] - Ratk_Anime_threshold[2]);
    	float Ratk2 = 1.0F / Ratk_Anime_threshold[2];
    	
		this.Waist0.rotateAngleY = 0.0F;
		this.Body_base.rotateAngleY = 0.0F;
		
		if (this.isRiding) {
			this.Waist1.rotationPointY = -1.5F; 
			this.setRotateAngle(Leg_r_seg0, -1.3589133662554294F, 0.4300491170387584F, 0.0F);
			this.setRotateAngle(Leg_r_seg1, 0.6161012126381928F, 0.0F, 0.0F);
			this.setRotateAngle(Leg_l_seg0, -1.3589133662554294F, 0.4300491170387584F, 0.0F);
			this.setRotateAngle(Leg_l_seg1, 0.6161012126381928F, 0.0F, 0.0F);
		} else if (spl > spl_Anime_threshold[1]) {
			this.Waist1.rotationPointY = -1.5F;
			this.Leg_r_seg0.rotateAngleX = -0.26424285474405396F;
			this.Leg_r_seg0.rotateAngleY = 0.0F;
			this.Leg_r_seg1.rotateAngleX = 0.26424285474405396F;
			this.Leg_l_seg0.rotateAngleX = -0.26424285474405396F;
			this.Leg_l_seg0.rotateAngleY = 0.0F;
			this.Leg_l_seg1.rotateAngleX = 0.26424285474405396F;
		} else if (spl > spl_Anime_threshold[2]) {
			this.Waist1.rotationPointY = GradientAnimation_s(-1.5F, 1.0F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.rotateAngleX = GradientAnimation_s(-0.26424285474405396F, -0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.rotateAngleY = 0.0F;
			this.Leg_r_seg1.rotateAngleX = GradientAnimation_s(0.26424285474405396F, 0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.rotateAngleX = GradientAnimation_s(-0.26424285474405396F, -0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.rotateAngleY = 0.0F;
			this.Leg_l_seg1.rotateAngleX = GradientAnimation_s(0.26424285474405396F, 0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));   	
		} else if (spl > spl_Anime_threshold[3]) {
			this.Waist1.rotationPointY = 1.0F;
			this.Leg_r_seg0.rotateAngleX = -0.8506735067168082F;
			this.Leg_r_seg0.rotateAngleY = 0.0F;
			this.Leg_r_seg1.rotateAngleX = 0.8506735067168082F;
			this.Leg_l_seg0.rotateAngleX = -0.8506735067168082F;
			this.Leg_l_seg0.rotateAngleY = 0.0F;
			this.Leg_l_seg1.rotateAngleX = 0.8506735067168082F;
		} else if (spl > 0.0F) {
			this.Waist1.rotationPointY = GradientAnimation_s(1.0F, -1.5F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.rotateAngleX = GradientAnimation_s(-0.8506735067168082F, -0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.rotateAngleY = 0.0F;
			this.Leg_r_seg1.rotateAngleX = GradientAnimation_s(0.8506735067168082F, 0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.rotateAngleX = GradientAnimation_s(-0.8506735067168082F, -0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.rotateAngleY = 0.0F;
			this.Leg_l_seg1.rotateAngleX = GradientAnimation_s(0.8506735067168082F, 0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
    	} else {
    		this.Waist1.rotationPointY = -1.5F;      	
            this.Leg_r_seg0.rotateAngleX = -0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F) * 1.2F * limbSwingAmount;
            this.Leg_r_seg0.rotateAngleY = 0.0F;
            this.Leg_l_seg0.rotateAngleX = -0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 1.2F * limbSwingAmount;   
            this.Leg_l_seg0.rotateAngleY = 0.0F;
            this.Leg_r_seg1.rotateAngleX = 0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 1.2F * limbSwingAmount;
            this.Leg_l_seg1.rotateAngleX = 0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F) * 1.2F * limbSwingAmount;  
    	}
		
    	if (spl > spl_Anime_threshold[1]) {  		
    		this.Waist0.rotateAngleX = GradientAnimation_s(0.5232497017584168F, 0.09320058471965828F, spl0 * (spl - Anime_threshold[1]));
    		this.Body_base.rotateAngleX = GradientAnimation_s(-0.5110324169681646F, -0.5892231261785137F, spl0 * (spl - Anime_threshold[1]));
    		this.Head_base.rotateAngleX = GradientAnimation_s(0.27366763203903305F, 0.27366763203903305F, spl0 * (spl - Anime_threshold[1]));
   		
    		this.Arm_r_seg0.rotateAngleX = 0.0F;
    		this.Arm_r_seg0.rotateAngleY = GradientAnimation_s(0.0F, 2.502104026311715F, spl0 * (spl - Anime_threshold[1]));
    		this.Arm_r_seg0.rotateAngleZ = 1.092750655326294F;
    		this.Arm_l_seg0.rotateAngleX = 0.0F;
    		this.Arm_l_seg0.rotateAngleY = GradientAnimation_s(0.0F, -2.502104026311715F, spl0 * (spl - Anime_threshold[1]));
    		this.Arm_l_seg0.rotateAngleZ = -1.092750655326294F;

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;     
    	} else if (spl > spl_Anime_threshold[2]) {  		
    		this.Waist0.rotateAngleX = GradientAnimation_s(0.09320058471965828F, 0.8361872419673221F, spl1 * (spl - Anime_threshold[2]));
    		this.Body_base.rotateAngleX = GradientAnimation_s(-0.5892231261785137F, 0.8182103719770693F, spl1 * (spl - Anime_threshold[2]));
    		this.Head_base.rotateAngleX = GradientAnimation_s(0.27366763203903305F, -1.13376586611655F, spl1 * (spl - Anime_threshold[2]));

    		this.Arm_r_seg0.rotateAngleX = GradientAnimation_s(0.0F, -0.35185837453889574F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_r_seg0.rotateAngleY = GradientAnimation_s(2.502104026311715F, 0.9379399693165247F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation_s(1.092750655326294F, 2.226516521442844F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation_s(0.0F, -0.35185837453889574F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.rotateAngleY = GradientAnimation_s(-2.502104026311715F, -0.9379399693165247F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation_s(-1.092750655326294F, -2.226516521442844F, spl1 * (spl - Anime_threshold[2]));

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.rotateAngleZ = GradientAnimation_s(-0.46931902520863084F, 0.0F, spl1 * (spl - Anime_threshold[2]));
        	this.Arm_l_seg2.rotateAngleZ = GradientAnimation_s(0.46931902520863084F, 0.0F, spl1 * (spl - Anime_threshold[2]));      
    	} else if (spl > spl_Anime_threshold[3]) {   		
    		this.Waist0.rotateAngleX = 0.8361872419673221F;
    		this.Body_base.rotateAngleX = 0.8182103719770693F;
    		this.Head_base.rotateAngleX = -1.13376586611655F;

    		this.Arm_r_seg0.rotateAngleX = -0.35185837453889574F;
    		this.Arm_r_seg0.rotateAngleY = 0.9379399693165247F;
    		this.Arm_r_seg0.rotateAngleZ = 2.226516521442844F;
    		this.Arm_l_seg0.rotateAngleX = -0.35185837453889574F;
    		this.Arm_l_seg0.rotateAngleY = -0.9379399693165247F;
    		this.Arm_l_seg0.rotateAngleZ = -2.226516521442844F;

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.rotateAngleZ = 0.0F;
        	this.Arm_l_seg2.rotateAngleZ = 0.0F;     
    	} else if (spl > 0.0F) {  		
    		this.Waist0.rotateAngleX = GradientAnimation(0.8361872419673221F, 0.5232497017584168F, spl3 * spl);
    		this.Body_base.rotateAngleX = GradientAnimation(0.8182103719770693F, -0.5110324169681646F, spl3 * spl);
    		this.Head_base.rotateAngleX = GradientAnimation(-1.13376586611655F, 0.27366763203903305F, spl3 * spl);

    		this.Arm_r_seg0.rotateAngleX = GradientAnimation(-0.35185837453889574F, 0.0F, spl3 * spl);
    		this.Arm_r_seg0.rotateAngleY = GradientAnimation(0.9379399693165247F, 0.0F, spl3 * spl);
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation(2.226516521442844F, 1.092750655326294F, spl3 * spl);
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation(-0.35185837453889574F, 0.0F, spl3 * spl);
    		this.Arm_l_seg0.rotateAngleY = GradientAnimation(-0.9379399693165247F, 0.0F, spl3 * spl);
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation(-2.226516521442844F, -1.092750655326294F, spl3 * spl);

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.rotateAngleZ = GradientAnimation_s(0.0F, -0.46931902520863084F, spl3 * spl);
        	this.Arm_l_seg2.rotateAngleZ = GradientAnimation_s(0.0F, 0.46931902520863084F, spl3 * spl); 	
    	} else if (Ratk > Ratk_Anime_threshold[1]) {    		
    		this.Waist0.rotateAngleX = 0.5232497017584168F;
    		this.Waist0.rotateAngleY = GradientAnimation_s(0.0F, -0.3127630032889644F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Body_base.rotateAngleX = GradientAnimation_s(-0.5110324169681646F, -0.5892231261785137F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Body_base.rotateAngleY = GradientAnimation_s(0.0F, -0.0781907508222411F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Head_base.rotateAngleX = 0.27366763203903305F;
    		
    		this.Arm_r_seg0.rotateAngleX = 0.0F;
    		this.Arm_r_seg0.rotateAngleY = 0.0F;
    		this.Arm_r_seg0.rotateAngleZ = 1.092750655326294F;
    		this.Arm_l_seg0.rotateAngleX = 0.0F;
    		this.Arm_l_seg0.rotateAngleY = 0.0F;
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation_s(-1.092750655326294F, -2.2670081547627903F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));

    		this.Arm_r_seg1.rotateAngleX = -0.6646214111173737F;
    		this.Arm_r_seg1.rotateAngleY = GradientAnimation(0.0F, 0.6646214111173737F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Arm_r_seg1.rotateAngleZ = -1.1016518052166933F;
    		this.Arm_l_seg1.rotateAngleX = GradientAnimation(-0.6646214111173737F, -1.407433498155583F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Arm_l_seg1.rotateAngleY = 0.0F;
    		this.Arm_l_seg1.rotateAngleZ = 1.1016518052166933F;

        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;
    	} else if (Ratk > Ratk_Anime_threshold[2]) {   		
    		this.Waist0.rotateAngleX = GradientAnimation(0.5232497017584168F, 0.679631186758142F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Waist0.rotateAngleY = GradientAnimation(-0.3127630032889644F, 0.5082398928281348F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Body_base.rotateAngleX = GradientAnimation(-0.5892231261785137F, -0.31555552742899423F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Body_base.rotateAngleY = GradientAnimation(-0.0781907508222411F, 0.19547687289441354F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Head_base.rotateAngleX = 0.27366763203903305F;
    		
    		this.Arm_r_seg0.rotateAngleX = 0.0F;
    		this.Arm_r_seg0.rotateAngleY = 0.0F;
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation(1.092750655326294F, 2.226516521442844F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation(0.0F, -0.9382889765773795F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg0.rotateAngleY = 0.0F;
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation(-2.2670081547627903F, -1.6414822147638888F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));

    		this.Arm_r_seg1.rotateAngleX = -0.6646214111173737F;
    		this.Arm_r_seg1.rotateAngleY = 0.6646214111173737F;
    		this.Arm_r_seg1.rotateAngleZ = -1.1016518052166933F;
    		this.Arm_l_seg1.rotateAngleX = GradientAnimation(-1.407433498155583F, -1.0555751236166873F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg1.rotateAngleY = GradientAnimation(0.0F, -0.46914448828868976F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg1.rotateAngleZ = 1.1016518052166933F;
 
        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;       
    	} else if (Ratk > 0.0F) {
    		
    		this.Waist0.rotateAngleX = GradientAnimation(0.679631186758142F, 0.2764601561790629F, Ratk2 * Ratk);
    		this.Waist0.rotateAngleY = GradientAnimation(0.5082398928281348F, 0.0F, Ratk2 * Ratk);
    		this.Body_base.rotateAngleX = GradientAnimation(-0.31555552742899423F, -0.5110324169681646F, Ratk2 * Ratk);
    		this.Body_base.rotateAngleY = GradientAnimation(0.19547687289441354F, 0.0F, Ratk2 * Ratk);
    		this.Head_base.rotateAngleX = 0.27366763203903305F;

    		this.Arm_r_seg0.rotateAngleX = 0.0F;
    		this.Arm_r_seg0.rotateAngleY = 0.0F;
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation(2.226516521442844F, 1.092750655326294F, Ratk2 * Ratk);
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation(-0.9382889765773795F, 0.0F, Ratk2 * Ratk);
    		this.Arm_l_seg0.rotateAngleY = 0.0F;
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation(-1.6414822147638888F, -1.092750655326294F, Ratk2 * Ratk);

    		this.Arm_r_seg1.rotateAngleX = -0.6646214111173737F;
    		this.Arm_r_seg1.rotateAngleY = GradientAnimation(0.6646214111173737F, 0.0F, Ratk2 * Ratk);
    		this.Arm_r_seg1.rotateAngleZ = -1.1016518052166933F;
    		this.Arm_l_seg1.rotateAngleX = GradientAnimation(-1.0555751236166873F, -0.6637486932281548F, Ratk2 * Ratk);
    		this.Arm_l_seg1.rotateAngleY = GradientAnimation(-0.46914448828868976F, 0.0F, Ratk2 * Ratk);
    		this.Arm_l_seg1.rotateAngleZ = 1.1016518052166933F;

        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;       
    	} else if (i > Anime_threshold[1]) {
    		this.Waist0.rotateAngleX = GradientAnimation(0.2764601561790629F, 0.6283185307179586F, j * (i - Anime_threshold[1]));
    		this.Body_base.rotateAngleX = GradientAnimation(-0.5110324169681646F, 0.2708751078990032F, j * (i - Anime_threshold[1]));
    		this.Head_base.rotateAngleX = GradientAnimation(-0.11728612207217244F, 0.1563815016444822F, j * (i - Anime_threshold[1]));

    		this.Arm_r_seg0.rotateAngleX = GradientAnimation(-1.602910321115726F, -1.7201964681550337F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg0.rotateAngleY = GradientAnimation(1.6948892472643378F, 0.5602506632585689F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation(0.428129277498434F, 0.0F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation(-1.602910321115726F, -1.7201964681550337F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.rotateAngleY = GradientAnimation(-1.6948892472643378F, -0.5602506632585689F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation(-0.428129277498434F, 0.0F, j * (i - Anime_threshold[1]));

    		this.Arm_r_seg1.rotateAngleX = GradientAnimation(-0.6646214111173737F, 0.3127630032889644F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg1.rotateAngleY = GradientAnimation(0.0F, -0.23457224414434488F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg1.rotateAngleZ = GradientAnimation(-1.1016518052166933F, -1.140747209756138F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.rotateAngleX = GradientAnimation(-0.6646214111173737F, 0.3127630032889644F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.rotateAngleY = GradientAnimation(0.0F, 0.23457224414434488F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.rotateAngleZ = GradientAnimation(1.1016518052166933F, 1.140747209756138F, j * (i - Anime_threshold[1]));
    		
        	this.Arm_l_Finger0_seg0.rotateAngleX = 0.3127630032889644F;
        	this.Arm_l_Finger0_Seg1.rotateAngleX = 1.3962634015954636F;
        	this.Arm_l_Finger1_seg0.rotateAngleX = 0.23457224414434488F;
        	this.Arm_l_Finger1_Seg1.rotateAngleX = 1.3962634015954636F;
        	this.Arm_l_Finger2_seg0.rotateAngleX = -0.46931902520863084F;
        	this.Arm_l_Finger2_Seg1.rotateAngleX = 1.5707963267948966F;
        	
        	this.Arm_r_Finger0_seg0.rotateAngleX = 0.3127630032889644F;
        	this.Arm_r_Finger0_Seg1.rotateAngleX = 1.3962634015954636F;
        	this.Arm_r_Finger1_seg0.rotateAngleX = 0.23457224414434488F;
        	this.Arm_r_Finger1_Seg1.rotateAngleX = 1.3962634015954636F;
        	this.Arm_r_Finger2_seg0.rotateAngleX = -0.46931902520863084F;
        	this.Arm_r_Finger2_Seg1.rotateAngleX = 1.5707963267948966F;
        	
        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;
    	} else if (i > 0.0F) {
    		this.Waist0.rotateAngleX = GradientAnimation(0.6283185307179586F, 0.5892231261785137F, k * i);
    		this.Body_base.rotateAngleX = GradientAnimation(0.2708751078990032F, -0.5892231261785137F, k * i);
    		this.Head_base.rotateAngleX = GradientAnimation(0.1563815016444822F, 0.27366763203903305F, k * i);

    		this.Arm_r_seg0.rotateAngleX = GradientAnimation(-1.7201964681550337F, 0.0F, k * i);
    		this.Arm_r_seg0.rotateAngleY = GradientAnimation(0.5602506632585689F, 0.0F, k * i);
    		this.Arm_r_seg0.rotateAngleZ = GradientAnimation(0.0F, 1.092750655326294F, k * i);
    		this.Arm_l_seg0.rotateAngleX = GradientAnimation(-1.7201964681550337F, 0.0F, k * i);
    		this.Arm_l_seg0.rotateAngleY = GradientAnimation(-0.5602506632585689F, 0.0F, k * i);
    		this.Arm_l_seg0.rotateAngleZ = GradientAnimation(0.0F, -1.092750655326294F, k * i);

    		this.Arm_r_seg1.rotateAngleX = GradientAnimation(0.3127630032889644F, -0.6646214111173737F, k * i);
    		this.Arm_r_seg1.rotateAngleY = GradientAnimation(-0.23457224414434488F, 0.0F, k * i);
    		this.Arm_r_seg1.rotateAngleZ = GradientAnimation(-1.140747209756138F, -1.1016518052166933F, k * i);
    		this.Arm_l_seg1.rotateAngleX = GradientAnimation(0.3127630032889644F, -0.6646214111173737F, k * i);
    		this.Arm_l_seg1.rotateAngleY = GradientAnimation(0.23457224414434488F, 0.0F, k * i);
    		this.Arm_l_seg1.rotateAngleZ = GradientAnimation(1.140747209756138F, 1.1016518052166933F, k * i);   
    		
        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;
    	} else {
    		this.Waist0.rotateAngleX = 0.5232497017584168F;
    		
    		this.Body_base.rotateAngleX = -0.5892231261785137F;
    		this.Head_base.rotateAngleX = 0.27366763203903305F;
    		
        	this.Arm_r_seg0.rotateAngleZ = 1.092750655326294F;
        	this.Arm_l_seg0.rotateAngleZ = -1.092750655326294F;
        	
        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
        	
            if(limbSwingAmount > 0.2F) {
            	this.Waist0.rotateAngleY = 0.35F * MathHelper.cos(limbSwing * 0.3F);
            	
            	this.Arm_r_seg0.rotateAngleX = 0.55F * MathHelper.cos(limbSwing * 0.3F) * 0.6F * limbSwingAmount;
            	this.Arm_l_seg0.rotateAngleX = 0.55F * MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 0.6F * limbSwingAmount;
            	
            	if(this.Arm_r_seg0.rotateAngleY < 31.36F * ((float)Math.PI / 180F))
            		this.Arm_r_seg0.rotateAngleY += 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_r_seg0.rotateAngleY = 31.36F * ((float)Math.PI / 180F);
            	
            	if(this.Arm_l_seg0.rotateAngleY > -31.36F * ((float)Math.PI / 180F))       	
            		this.Arm_l_seg0.rotateAngleY -= 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_l_seg0.rotateAngleY = -31.36F * ((float)Math.PI / 180F);
            } else {
            	this.Waist0.rotateAngleY = 0.0F;
            	
            	this.Arm_r_seg0.rotateAngleX = 0.0F;
            	this.Arm_l_seg0.rotateAngleX = 0.0F;
            	
            	if(this.Arm_r_seg0.rotateAngleY > 0.0F && this.Arm_r_seg0.rotateAngleY <= 31.36F * ((float)Math.PI / 180F))
            		this.Arm_r_seg0.rotateAngleY -= 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_r_seg0.rotateAngleY = 0.0F;
            	
            	if(this.Arm_l_seg0.rotateAngleY < 0.0F && this.Arm_l_seg0.rotateAngleY >= -31.36F * ((float)Math.PI / 180F))       	
            		this.Arm_l_seg0.rotateAngleY += 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_l_seg0.rotateAngleY = 0.0F;        
            }    	
            
        	this.Arm_l_Finger0_seg0.rotateAngleX = -0.3909537457888271F - MathHelper.cos(entity.ticksExisted * 0.03F) * 0.1F;
        	this.Arm_l_Finger0_Seg1.rotateAngleX = 0.8600982340775168F + MathHelper.cos(entity.ticksExisted * 0.03F) * 0.22F;
        	this.Arm_l_Finger1_seg0.rotateAngleX = -0.3909537457888271F - MathHelper.cos(entity.ticksExisted * 0.03F + 0.02F) * 0.1F;
        	this.Arm_l_Finger1_Seg1.rotateAngleX = 0.9773843811168246F + MathHelper.cos(entity.ticksExisted * 0.03F + 0.02F) * 0.22F;
        	this.Arm_l_Finger2_seg0.rotateAngleX = -0.46931902520863084F - MathHelper.cos(entity.ticksExisted * 0.03F + 0.04F) * 0.1F;
        	this.Arm_l_Finger2_Seg1.rotateAngleX = 0.9382889765773795F + MathHelper.cos(entity.ticksExisted * 0.03F + 0.04F) * 0.22F;
        	
        	this.Arm_r_Finger0_seg0.rotateAngleX = -0.3909537457888271F - MathHelper.cos(entity.ticksExisted * 0.03F) * 0.1F;
        	this.Arm_r_Finger0_Seg1.rotateAngleX = 0.8600982340775168F + MathHelper.cos(entity.ticksExisted * 0.03F) * 0.22F;
        	this.Arm_r_Finger1_seg0.rotateAngleX = -0.3909537457888271F - MathHelper.cos(entity.ticksExisted * 0.03F + 0.02F) * 0.1F;
        	this.Arm_r_Finger1_Seg1.rotateAngleX = 0.9773843811168246F + MathHelper.cos(entity.ticksExisted * 0.03F + 0.02F) * 0.22F;
        	this.Arm_r_Finger2_seg0.rotateAngleX = -0.46931902520863084F - MathHelper.cos(entity.ticksExisted * 0.03F + 0.04F) * 0.1F;
        	this.Arm_r_Finger2_Seg1.rotateAngleX = 0.9382889765773795F + MathHelper.cos(entity.ticksExisted * 0.03F + 0.04F) * 0.22F;
        	
        	this.Arm_r_seg2.rotateAngleZ = -0.46931902520863084F;
        	this.Arm_l_seg2.rotateAngleZ = 0.46931902520863084F;
    	}
    }
}
