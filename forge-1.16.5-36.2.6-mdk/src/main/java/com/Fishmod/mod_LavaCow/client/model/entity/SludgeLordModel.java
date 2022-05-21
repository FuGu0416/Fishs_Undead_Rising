package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.SludgeLordEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelSludgeLord - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SludgeLordModel<T extends SludgeLordEntity> extends FURBaseModel<T> {
    public ModelRenderer Body_base;
    public ModelRenderer Head_base;
    public ModelRenderer Arm_l_seg0;
    public ModelRenderer Waist0;
    public ModelRenderer Head_Moss_r;
    public ModelRenderer Arm_r_seg0;
    public ModelRenderer Head_Twig0;
    public ModelRenderer Head_Twig4;
    public ModelRenderer Head;
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

    public SludgeLordModel() {
        this.texWidth = 256;
        this.texHeight = 128;
        this.Arm_l_seg2 = new ModelRenderer(this, 88, 88);
        this.Arm_l_seg2.mirror = true;
        this.Arm_l_seg2.setPos(0.0F, 10.0F, 0.0F);
        this.Arm_l_seg2.addBox(-5.0F, -6.0F, -5.0F, 10.0F, 20.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_seg2, 0.0F, 0.0F, 0.46931902520863084F);
        this.Arm_l_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_l_seg0.mirror = true;
        this.Arm_l_seg0.setPos(14.0F, -1.0F, 2.0F);
        this.Arm_l_seg0.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_seg0, 0.0F, 0.0F, -1.092750655326294F);
        this.Waist0 = new ModelRenderer(this, 0, 80);
        this.Waist0.setPos(0.0F, 4.0F, 1.0F);
        this.Waist0.addBox(-11.0F, -10.0F, -5.5F, 22.0F, 10.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0, 0.5232497017584168F, 0.0F, 0.0F);
        this.Arm_l_knot0 = new ModelRenderer(this, 0, 10);
        this.Arm_l_knot0.setPos(5.0F, -1.0F, 0.0F);
        this.Arm_l_knot0.addBox(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Head_inside = new ModelRenderer(this, 146, 77);
        this.Head_inside.setPos(0.0F, -1.0F, 0.0F);
        this.Head_inside.addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Leg_l_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_l_seg1.mirror = true;
        this.Leg_l_seg1.setPos(0.0F, 8.6F, 0.4F);
        this.Leg_l_seg1.addBox(-4.0F, -2.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l_seg1, 0.26424285474405396F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 204, 92);
        this.Jaw.setPos(0.0F, 0.0F, 4.0F);
        this.Jaw.addBox(-4.0F, 0.0F, -8.0F, 8.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Jaw, 0.2275909337942703F, 0.0F, 0.0F);
        this.Arm_l_Finger2_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger2_seg0.mirror = true;
        this.Arm_l_Finger2_seg0.setPos(-3.5F, 12.0F, -0.6F);
        this.Arm_l_Finger2_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger2_seg0, -0.46931902520863084F, 1.5707963267948966F, 0.0F);
        this.Head_Twig3 = new ModelRenderer(this, 68, 36);
        this.Head_Twig3.setPos(5.5F, -6.0F, -10.0F);
        this.Head_Twig3.addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Waist0_Twig0_l = new ModelRenderer(this, 20, 0);
        this.Waist0_Twig0_l.mirror = true;
        this.Waist0_Twig0_l.setPos(5.5F, -5.6F, 4.0F);
        this.Waist0_Twig0_l.addBox(-2.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0_Twig0_l, -1.5934856603340446F, 0.45535640450848164F, 0.0F);
        this.Arm_r_Finger2_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_r_Finger2_Seg1.mirror = true;
        this.Arm_r_Finger2_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger2_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger2_Seg1, 1.5707963267948966F, 0.0F, 0.0F);
        this.Arm_l_knot1 = new ModelRenderer(this, 0, 10);
        this.Arm_l_knot1.setPos(5.0F, 7.0F, 0.0F);
        this.Arm_l_knot1.addBox(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Head_bubble = new ModelRenderer(this, 0, 24);
        this.Head_bubble.setPos(0.0F, -6.0F, 0.0F);
        this.Head_bubble.addBox(-7.0F, -15.0F, -7.0F, 14.0F, 14.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.Head_bubble_in = new ModelRenderer(this, 144, 40);
        this.Head_bubble_in.setPos(0.0F, -6.0F, 0.0F);
        this.Head_bubble_in.addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.Waist1_Twig0_l = new ModelRenderer(this, 70, 48);
        this.Waist1_Twig0_l.setPos(5.0F, 4.5F, 3.0F);
        this.Waist1_Twig0_l.addBox(-1.5F, -14.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist1_Twig0_l, -1.3723523429062416F, 0.6176720116281489F, 0.35185837453889574F);
        this.Leg_l_seg0 = new ModelRenderer(this, 78, 28);
        this.Leg_l_seg0.mirror = true;
        this.Leg_l_seg0.setPos(6.5F, 9.0F, 0.7F);
        this.Leg_l_seg0.addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l_seg0, -0.26424285474405396F, 0.0F, 0.0F);
        this.Arm_l_seg1 = new ModelRenderer(this, 96, 64);
        this.Arm_l_seg1.setPos(0.5F, 13.0F, 0.0F);
        this.Arm_l_seg1.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
        this.Waist0_Twig0_r = new ModelRenderer(this, 20, 0);
        this.Waist0_Twig0_r.setPos(-6.0F, -3.7F, 4.0F);
        this.Waist0_Twig0_r.addBox(-2.0F, -16.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0_Twig0_r, -1.411447814024714F, -0.7740534966278743F, 0.0F);
        this.Head_Moss_r = new ModelRenderer(this, 98, 5);
        this.Head_Moss_r.setPos(0.0F, 5.0F, -3.0F);
        this.Head_Moss_r.addBox(0.0F, 0.0F, -8.0F, 0.0F, 12.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head_Moss_r, 0.001745329278001762F, 1.5707963267948966F, 0.0F);
        this.Arm_l_Finger2_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_l_Finger2_Seg1.mirror = true;
        this.Arm_l_Finger2_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger2_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger2_Seg1, 1.5707963267948966F, 0.0F, 0.0F);
        this.Head_Moss_l = new ModelRenderer(this, 58, 0);
        this.Head_Moss_l.setPos(0.0F, 0.0F, -8.0F);
        this.Head_Moss_l.addBox(-10.0F, -1.4F, -2.0F, 20.0F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head_Moss_l, -0.27366763203903305F, 0.0F, 0.0F);
        this.Arm_r_Finger0_Seg1 = new ModelRenderer(this, 130, 97);
        this.Arm_r_Finger0_Seg1.mirror = true;
        this.Arm_r_Finger0_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger0_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger0_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Waist0_Twig1_l = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig1_l.setPos(0.0F, -14.8F, 0.4F);
        this.Waist0_Twig1_l.addBox(-2.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0_Twig1_l, 0.6829473549475088F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 146, 92);
        this.Head.setPos(0.0F, -13.0F, -1.0F);
        this.Head.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Arm_r_knot1 = new ModelRenderer(this, 0, 10);
        this.Arm_r_knot1.mirror = true;
        this.Arm_r_knot1.setPos(-5.0F, 7.0F, 0.0F);
        this.Arm_r_knot1.addBox(-2.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Head_Twig2 = new ModelRenderer(this, 59, 44);
        this.Head_Twig2.setPos(0.0F, -6.0F, -10.0F);
        this.Head_Twig2.addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Arm_r_Finger2_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger2_seg0.mirror = true;
        this.Arm_r_Finger2_seg0.setPos(3.5F, 12.0F, -0.6F);
        this.Arm_r_Finger2_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger2_seg0, -0.46931902520863084F, -1.5707963267948966F, 0.0F);
        this.Head_base = new ModelRenderer(this, 136, 0);
        this.Head_base.setPos(0.0F, -3.4F, -1.9F);
        this.Head_base.addBox(-11.0F, -6.0F, -11.0F, 22.0F, 6.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Head_base, 0.27366763203903305F, 0.0F, 0.0F);
        this.Arm_r_Finger1_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_r_Finger1_Seg1.mirror = true;
        this.Arm_r_Finger1_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_r_Finger1_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger1_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Arm_r_seg2 = new ModelRenderer(this, 88, 88);
        this.Arm_r_seg2.setPos(0.0F, 10.0F, 0.0F);
        this.Arm_r_seg2.addBox(-5.0F, -6.0F, -5.0F, 10.0F, 20.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_seg2, 0.0F, 0.0F, -0.46931902520863084F);
        this.Leg_r_seg1 = new ModelRenderer(this, 104, 44);
        this.Leg_r_seg1.setPos(0.0F, 8.6F, 0.4F);
        this.Leg_r_seg1.addBox(-4.0F, -2.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r_seg1, 0.26424285474405396F, 0.0F, 0.0F);
        this.Waist0_Twig1_r = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig1_r.setPos(2.0F, -16.0F, 0.0F);
        this.Waist0_Twig1_r.addBox(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0_Twig1_r, 0.0F, 0.0F, -0.45535640450848164F);
        this.Arm_r_seg1 = new ModelRenderer(this, 96, 64);
        this.Arm_r_seg1.mirror = true;
        this.Arm_r_seg1.setPos(-0.5F, 13.0F, 0.0F);
        this.Arm_r_seg1.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        this.Leg_r_seg0 = new ModelRenderer(this, 78, 28);
        this.Leg_r_seg0.setPos(-6.5F, 9.0F, 0.7F);
        this.Leg_r_seg0.addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r_seg0, -0.26424285474405396F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 54);
        this.Body_base.setPos(0.0F, -12.5F, -1.0F);
        this.Body_base.addBox(-14.0F, -7.0F, -4.0F, 28.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Body_base, -0.5110324169681646F, 0.0F, 0.0F);
        this.Arm_r_knot0 = new ModelRenderer(this, 0, 10);
        this.Arm_r_knot0.mirror = true;
        this.Arm_r_knot0.setPos(-5.0F, -1.0F, 0.0F);
        this.Arm_r_knot0.addBox(-2.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Head_Twig1 = new ModelRenderer(this, 59, 36);
        this.Head_Twig1.setPos(-5.0F, -6.0F, -10.0F);
        this.Head_Twig1.addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.Waist0_Twig2_l = new ModelRenderer(this, 40, 0);
        this.Waist0_Twig2_l.mirror = true;
        this.Waist0_Twig2_l.setPos(-0.4F, -10.8F, 0.0F);
        this.Waist0_Twig2_l.addBox(-2.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist0_Twig2_l, 0.0F, 0.0F, 0.6373942508178124F);
        this.Arm_l_Finger0_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger0_seg0.mirror = true;
        this.Arm_l_Finger0_seg0.setPos(4.0F, 13.0F, -2.7F);
        this.Arm_l_Finger0_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger0_seg0, 0.3127630032889644F, -1.2189378856769737F, 0.0F);
        this.Waist1 = new ModelRenderer(this, 0, 102);
        this.Waist1.setPos(0.0F, -1.5F, 0.0F);
        this.Waist1.addBox(-10.0F, 0.0F, -4.5F, 20.0F, 10.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.Arm_r_seg0 = new ModelRenderer(this, 112, 0);
        this.Arm_r_seg0.setPos(-14.0F, -1.0F, 2.0F);
        this.Arm_r_seg0.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_seg0, 0.0F, 0.0F, 1.092750655326294F);
        this.Arm_l_Finger1_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_l_Finger1_seg0.mirror = true;
        this.Arm_l_Finger1_seg0.setPos(4.0F, 13.0F, 2.7F);
        this.Arm_l_Finger1_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger1_seg0, 0.23457224414434488F, -1.9226547679128194F, 0.0F);
        this.Arm_r_Finger0_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger0_seg0.mirror = true;
        this.Arm_r_Finger0_seg0.setPos(-4.0F, 13.0F, -2.7F);
        this.Arm_r_Finger0_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger0_seg0, 0.3127630032889644F, 1.2189378856769737F, 0.0F);
        this.Arm_l_Finger0_Seg1 = new ModelRenderer(this, 130, 97);
        this.Arm_l_Finger0_Seg1.mirror = true;
        this.Arm_l_Finger0_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger0_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger0_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Head_Twig0 = new ModelRenderer(this, 45, 24);
        this.Head_Twig0.setPos(-9.5F, -6.0F, -9.5F);
        this.Head_Twig0.addBox(-1.5F, -9.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Waist1_Twig1_l = new ModelRenderer(this, 84, 48);
        this.Waist1_Twig1_l.setPos(-1.5F, -14.0F, 0.0F);
        this.Waist1_Twig1_l.addBox(0.0F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist1_Twig1_l, 0.0F, 0.0F, 0.650833294047652F);
        this.Arm_r_Finger1_seg0 = new ModelRenderer(this, 130, 86);
        this.Arm_r_Finger1_seg0.mirror = true;
        this.Arm_r_Finger1_seg0.setPos(-4.0F, 13.0F, 2.7F);
        this.Arm_r_Finger1_seg0.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r_Finger1_seg0, 0.23457224414434488F, 1.9226547679128194F, 0.0F);
        this.Arm_l_Finger1_Seg1 = new ModelRenderer(this, 130, 0);
        this.Arm_l_Finger1_Seg1.mirror = true;
        this.Arm_l_Finger1_Seg1.setPos(0.0F, 6.0F, 0.0F);
        this.Arm_l_Finger1_Seg1.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 5.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l_Finger1_Seg1, 1.3962634015954636F, 0.0F, 0.0F);
        this.Head_Twig4 = new ModelRenderer(this, 59, 24);
        this.Head_Twig4.setPos(9.5F, -6.0F, -9.5F);
        this.Head_Twig4.addBox(-1.5F, -8.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
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
        this.Head_base.addChild(this.Head_bubble);
        this.Head_base.addChild(this.Head_bubble_in);
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
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Waist1).forEach((modelRenderer) -> { 
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
    	this.Head_base.y = -3.4F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
    	this.Head.xRot = headPitch * 0.017453292F;
    	this.Head.yRot = netHeadYaw * 0.017453292F;        
    	this.Head.y = -13.0F + 0.5F * MathHelper.sin(0.03F * ageInTicks);
        this.Jaw.xRot = 0.15F + 0.11F * MathHelper.sin(0.03F * ageInTicks);                 
    }
    
    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks) {
    	float i = (float)entityIn.getAttackTimer() / 30.0F;  
    	float Anime_threshold[] = {1.0F, 0.5F, 0.2F};
    	float j = 1.0F / (Anime_threshold[0] - Anime_threshold[1]);
    	float k = 1.0F / (Anime_threshold[1] - Anime_threshold[2]);

    	float spl = (float)entityIn.getSpellTicks() / 100.0F;
    	float spl_Anime_threshold[] = {1.0F, 0.72F, 0.27F, 0.15F};
    	float spl0 = 1.0F / (spl_Anime_threshold[0] - spl_Anime_threshold[1]);
    	float spl1 = 1.0F / (spl_Anime_threshold[1] - spl_Anime_threshold[2]);
    	float spl3 = 1.0F / spl_Anime_threshold[3];

    	float Ratk = (float)entityIn.getRAttackTimer() / 40.0F; 
    	float Ratk_Anime_threshold[] = {1.0F, 0.5F, 0.2F};
    	float Ratk0 = 1.0F / (Ratk_Anime_threshold[0] - Ratk_Anime_threshold[1]);
    	float Ratk1 = 1.0F / (Ratk_Anime_threshold[1] - Ratk_Anime_threshold[2]);
    	float Ratk2 = 1.0F / Ratk_Anime_threshold[2];
    	
		this.Waist0.yRot = 0.0F;
		this.Body_base.yRot = 0.0F;
		
		if (this.riding) {
			this.Waist1.y = -1.5F; 
			this.setRotateAngle(Leg_r_seg0, -1.3589133662554294F, 0.4300491170387584F, 0.0F);
			this.setRotateAngle(Leg_r_seg1, 0.6161012126381928F, 0.0F, 0.0F);
			this.setRotateAngle(Leg_l_seg0, -1.3589133662554294F, 0.4300491170387584F, 0.0F);
			this.setRotateAngle(Leg_l_seg1, 0.6161012126381928F, 0.0F, 0.0F);
		} else if (spl > spl_Anime_threshold[1]) {
			this.Waist1.y = -1.5F;
			this.Leg_r_seg0.xRot = -0.26424285474405396F;
			this.Leg_r_seg0.yRot = 0.0F;
			this.Leg_r_seg1.xRot = 0.26424285474405396F;
			this.Leg_l_seg0.xRot = -0.26424285474405396F;
			this.Leg_l_seg0.yRot = 0.0F;
			this.Leg_l_seg1.xRot = 0.26424285474405396F;
		} else if (spl > spl_Anime_threshold[2]) {
			this.Waist1.y = GradientAnimation_s(-1.5F, 1.0F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.xRot = GradientAnimation_s(-0.26424285474405396F, -0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.yRot = 0.0F;
			this.Leg_r_seg1.xRot = GradientAnimation_s(0.26424285474405396F, 0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.xRot = GradientAnimation_s(-0.26424285474405396F, -0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.yRot = 0.0F;
			this.Leg_l_seg1.xRot = GradientAnimation_s(0.26424285474405396F, 0.8506735067168082F, spl1 * (spl - Anime_threshold[2]));   	
		} else if (spl > spl_Anime_threshold[3]) {
			this.Waist1.y = 1.0F;
			this.Leg_r_seg0.xRot = -0.8506735067168082F;
			this.Leg_r_seg0.yRot = 0.0F;
			this.Leg_r_seg1.xRot = 0.8506735067168082F;
			this.Leg_l_seg0.xRot = -0.8506735067168082F;
			this.Leg_l_seg0.yRot = 0.0F;
			this.Leg_l_seg1.xRot = 0.8506735067168082F;
		} else if (spl > 0.0F) {
			this.Waist1.y = GradientAnimation_s(1.0F, -1.5F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.xRot = GradientAnimation_s(-0.8506735067168082F, -0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_r_seg0.yRot = 0.0F;
			this.Leg_r_seg1.xRot = GradientAnimation_s(0.8506735067168082F, 0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.xRot = GradientAnimation_s(-0.8506735067168082F, -0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
			this.Leg_l_seg0.yRot = 0.0F;
			this.Leg_l_seg1.xRot = GradientAnimation_s(0.8506735067168082F, 0.26424285474405396F, spl1 * (spl - Anime_threshold[2]));
    	} else {
    		this.Waist1.y = -1.5F;      	
            this.Leg_r_seg0.xRot = -0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F) * 1.2F * limbSwingAmount;
            this.Leg_r_seg0.yRot = 0.0F;
            this.Leg_l_seg0.xRot = -0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 1.2F * limbSwingAmount;   
            this.Leg_l_seg0.yRot = 0.0F;
            this.Leg_r_seg1.xRot = 0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 1.2F * limbSwingAmount;
            this.Leg_l_seg1.xRot = 0.26424285474405396F + MathHelper.cos(limbSwing * 0.3F) * 1.2F * limbSwingAmount;  
    	}
		
    	if (spl > spl_Anime_threshold[1]) {  		
    		this.Waist0.xRot = GradientAnimation_s(0.5232497017584168F, 0.09320058471965828F, spl0 * (spl - Anime_threshold[1]));
    		this.Body_base.xRot = GradientAnimation_s(-0.5110324169681646F, -0.5892231261785137F, spl0 * (spl - Anime_threshold[1]));
    		this.Head_base.xRot = GradientAnimation_s(0.27366763203903305F, 0.27366763203903305F, spl0 * (spl - Anime_threshold[1]));
   		
    		this.Arm_r_seg0.xRot = 0.0F;
    		this.Arm_r_seg0.yRot = GradientAnimation_s(0.0F, 2.502104026311715F, spl0 * (spl - Anime_threshold[1]));
    		this.Arm_r_seg0.zRot = 1.092750655326294F;
    		this.Arm_l_seg0.xRot = 0.0F;
    		this.Arm_l_seg0.yRot = GradientAnimation_s(0.0F, -2.502104026311715F, spl0 * (spl - Anime_threshold[1]));
    		this.Arm_l_seg0.zRot = -1.092750655326294F;

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;     
    	} else if (spl > spl_Anime_threshold[2]) {  		
    		this.Waist0.xRot = GradientAnimation_s(0.09320058471965828F, 0.8361872419673221F, spl1 * (spl - Anime_threshold[2]));
    		this.Body_base.xRot = GradientAnimation_s(-0.5892231261785137F, 0.8182103719770693F, spl1 * (spl - Anime_threshold[2]));
    		this.Head_base.xRot = GradientAnimation_s(0.27366763203903305F, -1.13376586611655F, spl1 * (spl - Anime_threshold[2]));

    		this.Arm_r_seg0.xRot = GradientAnimation_s(0.0F, -0.35185837453889574F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_r_seg0.yRot = GradientAnimation_s(2.502104026311715F, 0.9379399693165247F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_r_seg0.zRot = GradientAnimation_s(1.092750655326294F, 2.226516521442844F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.xRot = GradientAnimation_s(0.0F, -0.35185837453889574F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.yRot = GradientAnimation_s(-2.502104026311715F, -0.9379399693165247F, spl1 * (spl - Anime_threshold[2]));
    		this.Arm_l_seg0.zRot = GradientAnimation_s(-1.092750655326294F, -2.226516521442844F, spl1 * (spl - Anime_threshold[2]));

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.zRot = GradientAnimation_s(-0.46931902520863084F, 0.0F, spl1 * (spl - Anime_threshold[2]));
        	this.Arm_l_seg2.zRot = GradientAnimation_s(0.46931902520863084F, 0.0F, spl1 * (spl - Anime_threshold[2]));      
    	} else if (spl > spl_Anime_threshold[3]) {   		
    		this.Waist0.xRot = 0.8361872419673221F;
    		this.Body_base.xRot = 0.8182103719770693F;
    		this.Head_base.xRot = -1.13376586611655F;

    		this.Arm_r_seg0.xRot = -0.35185837453889574F;
    		this.Arm_r_seg0.yRot = 0.9379399693165247F;
    		this.Arm_r_seg0.zRot = 2.226516521442844F;
    		this.Arm_l_seg0.xRot = -0.35185837453889574F;
    		this.Arm_l_seg0.yRot = -0.9379399693165247F;
    		this.Arm_l_seg0.zRot = -2.226516521442844F;

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.zRot = 0.0F;
        	this.Arm_l_seg2.zRot = 0.0F;     
    	} else if (spl > 0.0F) {  		
    		this.Waist0.xRot = GradientAnimation(0.8361872419673221F, 0.5232497017584168F, spl3 * spl);
    		this.Body_base.xRot = GradientAnimation(0.8182103719770693F, -0.5110324169681646F, spl3 * spl);
    		this.Head_base.xRot = GradientAnimation(-1.13376586611655F, 0.27366763203903305F, spl3 * spl);

    		this.Arm_r_seg0.xRot = GradientAnimation(-0.35185837453889574F, 0.0F, spl3 * spl);
    		this.Arm_r_seg0.yRot = GradientAnimation(0.9379399693165247F, 0.0F, spl3 * spl);
    		this.Arm_r_seg0.zRot = GradientAnimation(2.226516521442844F, 1.092750655326294F, spl3 * spl);
    		this.Arm_l_seg0.xRot = GradientAnimation(-0.35185837453889574F, 0.0F, spl3 * spl);
    		this.Arm_l_seg0.yRot = GradientAnimation(-0.9379399693165247F, 0.0F, spl3 * spl);
    		this.Arm_l_seg0.zRot = GradientAnimation(-2.226516521442844F, -1.092750655326294F, spl3 * spl);

        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
 
        	this.Arm_r_seg2.zRot = GradientAnimation_s(0.0F, -0.46931902520863084F, spl3 * spl);
        	this.Arm_l_seg2.zRot = GradientAnimation_s(0.0F, 0.46931902520863084F, spl3 * spl); 	
    	} else if (Ratk > Ratk_Anime_threshold[1]) {    		
    		this.Waist0.xRot = 0.5232497017584168F;
    		this.Waist0.yRot = GradientAnimation_s(0.0F, -0.3127630032889644F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Body_base.xRot = GradientAnimation_s(-0.5110324169681646F, -0.5892231261785137F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Body_base.yRot = GradientAnimation_s(0.0F, -0.0781907508222411F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Head_base.xRot = 0.27366763203903305F;
    		
    		this.Arm_r_seg0.xRot = 0.0F;
    		this.Arm_r_seg0.yRot = 0.0F;
    		this.Arm_r_seg0.zRot = 1.092750655326294F;
    		this.Arm_l_seg0.xRot = 0.0F;
    		this.Arm_l_seg0.yRot = 0.0F;
    		this.Arm_l_seg0.zRot = GradientAnimation_s(-1.092750655326294F, -2.2670081547627903F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));

    		this.Arm_r_seg1.xRot = -0.6646214111173737F;
    		this.Arm_r_seg1.yRot = GradientAnimation(0.0F, 0.6646214111173737F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Arm_r_seg1.zRot = -1.1016518052166933F;
    		this.Arm_l_seg1.xRot = GradientAnimation(-0.6646214111173737F, -1.407433498155583F, Ratk0 * (Ratk - Ratk_Anime_threshold[1]));
    		this.Arm_l_seg1.yRot = 0.0F;
    		this.Arm_l_seg1.zRot = 1.1016518052166933F;

        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;
    	} else if (Ratk > Ratk_Anime_threshold[2]) {   		
    		this.Waist0.xRot = GradientAnimation(0.5232497017584168F, 0.679631186758142F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Waist0.yRot = GradientAnimation(-0.3127630032889644F, 0.5082398928281348F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Body_base.xRot = GradientAnimation(-0.5892231261785137F, -0.31555552742899423F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Body_base.yRot = GradientAnimation(-0.0781907508222411F, 0.19547687289441354F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Head_base.xRot = 0.27366763203903305F;
    		
    		this.Arm_r_seg0.xRot = 0.0F;
    		this.Arm_r_seg0.yRot = 0.0F;
    		this.Arm_r_seg0.zRot = GradientAnimation(1.092750655326294F, 2.226516521442844F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg0.xRot = GradientAnimation(0.0F, -0.9382889765773795F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg0.yRot = 0.0F;
    		this.Arm_l_seg0.zRot = GradientAnimation(-2.2670081547627903F, -1.6414822147638888F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));

    		this.Arm_r_seg1.xRot = -0.6646214111173737F;
    		this.Arm_r_seg1.yRot = 0.6646214111173737F;
    		this.Arm_r_seg1.zRot = -1.1016518052166933F;
    		this.Arm_l_seg1.xRot = GradientAnimation(-1.407433498155583F, -1.0555751236166873F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg1.yRot = GradientAnimation(0.0F, -0.46914448828868976F, Ratk1 * (Ratk - Ratk_Anime_threshold[2]));
    		this.Arm_l_seg1.zRot = 1.1016518052166933F;
 
        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;       
    	} else if (Ratk > 0.0F) {
    		
    		this.Waist0.xRot = GradientAnimation(0.679631186758142F, 0.2764601561790629F, Ratk2 * Ratk);
    		this.Waist0.yRot = GradientAnimation(0.5082398928281348F, 0.0F, Ratk2 * Ratk);
    		this.Body_base.xRot = GradientAnimation(-0.31555552742899423F, -0.5110324169681646F, Ratk2 * Ratk);
    		this.Body_base.yRot = GradientAnimation(0.19547687289441354F, 0.0F, Ratk2 * Ratk);
    		this.Head_base.xRot = 0.27366763203903305F;

    		this.Arm_r_seg0.xRot = 0.0F;
    		this.Arm_r_seg0.yRot = 0.0F;
    		this.Arm_r_seg0.zRot = GradientAnimation(2.226516521442844F, 1.092750655326294F, Ratk2 * Ratk);
    		this.Arm_l_seg0.xRot = GradientAnimation(-0.9382889765773795F, 0.0F, Ratk2 * Ratk);
    		this.Arm_l_seg0.yRot = 0.0F;
    		this.Arm_l_seg0.zRot = GradientAnimation(-1.6414822147638888F, -1.092750655326294F, Ratk2 * Ratk);

    		this.Arm_r_seg1.xRot = -0.6646214111173737F;
    		this.Arm_r_seg1.yRot = GradientAnimation(0.6646214111173737F, 0.0F, Ratk2 * Ratk);
    		this.Arm_r_seg1.zRot = -1.1016518052166933F;
    		this.Arm_l_seg1.xRot = GradientAnimation(-1.0555751236166873F, -0.6637486932281548F, Ratk2 * Ratk);
    		this.Arm_l_seg1.yRot = GradientAnimation(-0.46914448828868976F, 0.0F, Ratk2 * Ratk);
    		this.Arm_l_seg1.zRot = 1.1016518052166933F;

        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;       
    	} else if (i > Anime_threshold[1]) {
    		this.Waist0.xRot = GradientAnimation(0.2764601561790629F, 0.6283185307179586F, j * (i - Anime_threshold[1]));
    		this.Body_base.xRot = GradientAnimation(-0.5110324169681646F, 0.2708751078990032F, j * (i - Anime_threshold[1]));
    		this.Head_base.xRot = GradientAnimation(-0.11728612207217244F, 0.1563815016444822F, j * (i - Anime_threshold[1]));

    		this.Arm_r_seg0.xRot = GradientAnimation(-1.602910321115726F, -1.7201964681550337F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg0.yRot = GradientAnimation(1.6948892472643378F, 0.5602506632585689F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg0.zRot = GradientAnimation(0.428129277498434F, 0.0F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.xRot = GradientAnimation(-1.602910321115726F, -1.7201964681550337F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.yRot = GradientAnimation(-1.6948892472643378F, -0.5602506632585689F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg0.zRot = GradientAnimation(-0.428129277498434F, 0.0F, j * (i - Anime_threshold[1]));

    		this.Arm_r_seg1.xRot = GradientAnimation(-0.6646214111173737F, 0.3127630032889644F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg1.yRot = GradientAnimation(0.0F, -0.23457224414434488F, j * (i - Anime_threshold[1]));
    		this.Arm_r_seg1.zRot = GradientAnimation(-1.1016518052166933F, -1.140747209756138F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.xRot = GradientAnimation(-0.6646214111173737F, 0.3127630032889644F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.yRot = GradientAnimation(0.0F, 0.23457224414434488F, j * (i - Anime_threshold[1]));
    		this.Arm_l_seg1.zRot = GradientAnimation(1.1016518052166933F, 1.140747209756138F, j * (i - Anime_threshold[1]));
    		
        	this.Arm_l_Finger0_seg0.xRot = 0.3127630032889644F;
        	this.Arm_l_Finger0_Seg1.xRot = 1.3962634015954636F;
        	this.Arm_l_Finger1_seg0.xRot = 0.23457224414434488F;
        	this.Arm_l_Finger1_Seg1.xRot = 1.3962634015954636F;
        	this.Arm_l_Finger2_seg0.xRot = -0.46931902520863084F;
        	this.Arm_l_Finger2_Seg1.xRot = 1.5707963267948966F;
        	
        	this.Arm_r_Finger0_seg0.xRot = 0.3127630032889644F;
        	this.Arm_r_Finger0_Seg1.xRot = 1.3962634015954636F;
        	this.Arm_r_Finger1_seg0.xRot = 0.23457224414434488F;
        	this.Arm_r_Finger1_Seg1.xRot = 1.3962634015954636F;
        	this.Arm_r_Finger2_seg0.xRot = -0.46931902520863084F;
        	this.Arm_r_Finger2_Seg1.xRot = 1.5707963267948966F;
        	
        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;
    	} else if (i > 0.0F) {
    		this.Waist0.xRot = GradientAnimation(0.6283185307179586F, 0.5892231261785137F, k * i);
    		this.Body_base.xRot = GradientAnimation(0.2708751078990032F, -0.5892231261785137F, k * i);
    		this.Head_base.xRot = GradientAnimation(0.1563815016444822F, 0.27366763203903305F, k * i);

    		this.Arm_r_seg0.xRot = GradientAnimation(-1.7201964681550337F, 0.0F, k * i);
    		this.Arm_r_seg0.yRot = GradientAnimation(0.5602506632585689F, 0.0F, k * i);
    		this.Arm_r_seg0.zRot = GradientAnimation(0.0F, 1.092750655326294F, k * i);
    		this.Arm_l_seg0.xRot = GradientAnimation(-1.7201964681550337F, 0.0F, k * i);
    		this.Arm_l_seg0.yRot = GradientAnimation(-0.5602506632585689F, 0.0F, k * i);
    		this.Arm_l_seg0.zRot = GradientAnimation(0.0F, -1.092750655326294F, k * i);

    		this.Arm_r_seg1.xRot = GradientAnimation(0.3127630032889644F, -0.6646214111173737F, k * i);
    		this.Arm_r_seg1.yRot = GradientAnimation(-0.23457224414434488F, 0.0F, k * i);
    		this.Arm_r_seg1.zRot = GradientAnimation(-1.140747209756138F, -1.1016518052166933F, k * i);
    		this.Arm_l_seg1.xRot = GradientAnimation(0.3127630032889644F, -0.6646214111173737F, k * i);
    		this.Arm_l_seg1.yRot = GradientAnimation(0.23457224414434488F, 0.0F, k * i);
    		this.Arm_l_seg1.zRot = GradientAnimation(1.140747209756138F, 1.1016518052166933F, k * i);   
    		
        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;
    	} else {
    		this.Waist0.xRot = 0.5232497017584168F;
    		
    		this.Body_base.xRot = -0.5892231261785137F;
    		this.Head_base.xRot = 0.27366763203903305F;
    		
        	this.Arm_r_seg0.zRot = 1.092750655326294F;
        	this.Arm_l_seg0.zRot = -1.092750655326294F;
        	
        	this.setRotateAngle(Arm_r_seg1, -0.6637486932281548F, 0.0F, -1.1016518052166933F);
        	this.setRotateAngle(Arm_l_seg1, -0.6637486932281548F, 0.0F, 1.1016518052166933F);
        	
            if(limbSwingAmount > 0.2F) {
            	this.Waist0.yRot = 0.35F * MathHelper.cos(limbSwing * 0.3F);
            	
            	this.Arm_r_seg0.xRot = 0.55F * MathHelper.cos(limbSwing * 0.3F) * 0.6F * limbSwingAmount;
            	this.Arm_l_seg0.xRot = 0.55F * MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 0.6F * limbSwingAmount;
            	
            	if(this.Arm_r_seg0.yRot < 31.36F * ((float)Math.PI / 180F))
            		this.Arm_r_seg0.yRot += 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_r_seg0.yRot = 31.36F * ((float)Math.PI / 180F);
            	
            	if(this.Arm_l_seg0.yRot > -31.36F * ((float)Math.PI / 180F))       	
            		this.Arm_l_seg0.yRot -= 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_l_seg0.yRot = -31.36F * ((float)Math.PI / 180F);
            } else {
            	this.Waist0.yRot = 0.0F;
            	
            	this.Arm_r_seg0.xRot = 0.0F;
            	this.Arm_l_seg0.xRot = 0.0F;
            	
            	if(this.Arm_r_seg0.yRot > 0.0F && this.Arm_r_seg0.yRot <= 31.36F * ((float)Math.PI / 180F))
            		this.Arm_r_seg0.yRot -= 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_r_seg0.yRot = 0.0F;
            	
            	if(this.Arm_l_seg0.yRot < 0.0F && this.Arm_l_seg0.yRot >= -31.36F * ((float)Math.PI / 180F))       	
            		this.Arm_l_seg0.yRot += 0.2F * ((float)Math.PI / 180F);
            	else
            		this.Arm_l_seg0.yRot = 0.0F;        
            }    	
            
        	this.Arm_l_Finger0_seg0.xRot = -0.3909537457888271F - MathHelper.cos(entityIn.tickCount * 0.03F) * 0.1F;
        	this.Arm_l_Finger0_Seg1.xRot = 0.8600982340775168F + MathHelper.cos(entityIn.tickCount * 0.03F) * 0.22F;
        	this.Arm_l_Finger1_seg0.xRot = -0.3909537457888271F - MathHelper.cos(entityIn.tickCount * 0.03F + 0.02F) * 0.1F;
        	this.Arm_l_Finger1_Seg1.xRot = 0.9773843811168246F + MathHelper.cos(entityIn.tickCount * 0.03F + 0.02F) * 0.22F;
        	this.Arm_l_Finger2_seg0.xRot = -0.46931902520863084F - MathHelper.cos(entityIn.tickCount * 0.03F + 0.04F) * 0.1F;
        	this.Arm_l_Finger2_Seg1.xRot = 0.9382889765773795F + MathHelper.cos(entityIn.tickCount * 0.03F + 0.04F) * 0.22F;
        	
        	this.Arm_r_Finger0_seg0.xRot = -0.3909537457888271F - MathHelper.cos(entityIn.tickCount * 0.03F) * 0.1F;
        	this.Arm_r_Finger0_Seg1.xRot = 0.8600982340775168F + MathHelper.cos(entityIn.tickCount * 0.03F) * 0.22F;
        	this.Arm_r_Finger1_seg0.xRot = -0.3909537457888271F - MathHelper.cos(entityIn.tickCount * 0.03F + 0.02F) * 0.1F;
        	this.Arm_r_Finger1_Seg1.xRot = 0.9773843811168246F + MathHelper.cos(entityIn.tickCount * 0.03F + 0.02F) * 0.22F;
        	this.Arm_r_Finger2_seg0.xRot = -0.46931902520863084F - MathHelper.cos(entityIn.tickCount * 0.03F + 0.04F) * 0.1F;
        	this.Arm_r_Finger2_Seg1.xRot = 0.9382889765773795F + MathHelper.cos(entityIn.tickCount * 0.03F + 0.04F) * 0.22F;
        	
        	this.Arm_r_seg2.zRot = -0.46931902520863084F;
        	this.Arm_l_seg2.zRot = 0.46931902520863084F;
    	}
    }
}
