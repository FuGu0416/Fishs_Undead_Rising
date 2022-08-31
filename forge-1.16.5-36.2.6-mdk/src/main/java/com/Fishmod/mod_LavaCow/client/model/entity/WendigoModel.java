package com.Fishmod.mod_LavaCow.client.model.entity;


import com.Fishmod.mod_LavaCow.entities.WendigoEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelWendigo - Fish0016054
 * Created using Tabula 7.0.1
 */
public class WendigoModel<T extends WendigoEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Pelvis;
    public ModelRenderer Tail;
    public ModelRenderer Thigh_r;
    public ModelRenderer Thigh_l;
    public ModelRenderer Waist;
    public ModelRenderer Leg_r;
    public ModelRenderer Feet_r;
    public ModelRenderer Leg_l;
    public ModelRenderer Feet_l;
    public ModelRenderer Inside;
    public ModelRenderer Chest;
    public ModelRenderer Humerus_l;
    public ModelRenderer Humerus_r;
    public ModelRenderer Head;
    public ModelRenderer Vertebra0;
    public ModelRenderer Vertebra1;
    public ModelRenderer Vertebra2;
    public ModelRenderer Arm_l;
    public ModelRenderer Palm_l;
    public ModelRenderer Arm_fur_l;
    public ModelRenderer Claw_0_l;
    public ModelRenderer Claw_1_l;
    public ModelRenderer Claw_2_l;
    public ModelRenderer Claw_01_l;
    public ModelRenderer Claw_11_l;
    public ModelRenderer Claw_21_l;
    public ModelRenderer Arm_r;
    public ModelRenderer Palm_r;
    public ModelRenderer Arm_fur_r;
    public ModelRenderer Claw_0_r;
    public ModelRenderer Claw_1_r;
    public ModelRenderer Claw_2_r;
    public ModelRenderer Claw_01_r;
    public ModelRenderer Claw_11_r;
    public ModelRenderer Claw_21_r;
    public ModelRenderer Ear_r;
    public ModelRenderer Jaw_up;
    public ModelRenderer Jaw_lower;
    public ModelRenderer Ear_l;
    public ModelRenderer Horn0_l;
    public ModelRenderer Horn0_r;
    public ModelRenderer Horn1_l;
    public ModelRenderer Horn2_l;
    public ModelRenderer Horn3_l;
    public ModelRenderer Horn4_l;
    public ModelRenderer Horn5_l;
    public ModelRenderer Horn6_l;
    public ModelRenderer Horn1_r;
    public ModelRenderer Horn2_r;
    public ModelRenderer Horn3_r;
    public ModelRenderer Horn4_r;
    public ModelRenderer Horn5_r;
    public ModelRenderer Horn6_r;
    
    public WendigoModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.Horn6_l = new ModelRenderer(this, 20, 16);
        this.Horn6_l.mirror = true;
        this.Horn6_l.setPos(-1.0F, -0.5F, 5.0F);
        this.Horn6_l.addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn6_l, 0.0F, -0.7037167490777915F, 0.0F);
        this.Horn1_l = new ModelRenderer(this, 21, 3);
        this.Horn1_l.mirror = true;
        this.Horn1_l.setPos(0.5F, -2.0F, 0.0F);
        this.Horn1_l.addBox(-1.0F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn1_l, 0.0F, 0.0F, -0.5473352640780661F);
        this.Inside = new ModelRenderer(this, 23, 36);
        this.Inside.setPos(0.0F, 0.0F, 0.0F);
        this.Inside.addBox(-2.5F, -7.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.Jaw_lower = new ModelRenderer(this, 17, 4);
        this.Jaw_lower.setPos(0.0F, 2.0F, 0.5F);
        this.Jaw_lower.addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Jaw_lower, 0.5462880425584197F, 0.0F, 0.0F);
        this.Jaw_up = new ModelRenderer(this, 0, 12);
        this.Jaw_up.setPos(0.0F, -1.0F, -2.5F);
        this.Jaw_up.addBox(-1.5F, 0.0F, -5.5F, 3.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.Horn5_r = new ModelRenderer(this, 39, 0);
        this.Horn5_r.setPos(1.0F, -5.0F, 4.0F);
        this.Horn5_r.addBox(-3.0F, 0.0F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn5_r, 0.0F, 0.0F, 0.7819074915776542F);
        this.Horn2_r = new ModelRenderer(this, 25, 0);
        this.Horn2_r.setPos(0.0F, -1.5F, 0.0F);
        this.Horn2_r.addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Arm_fur_l = new ModelRenderer(this, 29, 53);
        this.Arm_fur_l.mirror = true;
        this.Arm_fur_l.setPos(0.0F, 0.0F, -3.3F);
        this.Arm_fur_l.addBox(-1.0F, 0.7F, -4.0F, 2.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Claw_01_r = new ModelRenderer(this, 16, 20);
        this.Claw_01_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_01_r.addBox(0.0F, -1.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_01_r, -0.0911061832922575F, -0.45535640450848164F, 0.0F);
        this.Ear_l = new ModelRenderer(this, 48, 0);
        this.Ear_l.mirror = true;
        this.Ear_l.setPos(2.5F, -2.5F, 0.0F);
        this.Ear_l.addBox(0.0F, 0.0F, -1.0F, 5.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Ear_l, 0.0F, 0.0F, 0.3909537457888271F);
        this.Palm_r = new ModelRenderer(this, 20, 23);
        this.Palm_r.setPos(-0.1F, -0.3F, -6.8F);
        this.Palm_r.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Palm_r, 0.0F, -0.27314402127920984F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.0F, -5.0F);
        this.Head.addBox(-2.5F, -3.0F, -3.0F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.Humerus_r = new ModelRenderer(this, 0, 21);
        this.Humerus_r.setPos(-2.0F, 1.0F, 1.5F);
        this.Humerus_r.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Humerus_r, 0.2275909337942703F, -0.18203784630933073F, 0.591841146688116F);
        this.Horn1_r = new ModelRenderer(this, 21, 3);
        this.Horn1_r.setPos(-0.5F, -2.0F, 0.0F);
        this.Horn1_r.addBox(0.0F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn1_r, 0.0F, 0.0F, 0.5473352640780661F);
        this.Palm_l = new ModelRenderer(this, 20, 23);
        this.Palm_l.mirror = true;
        this.Palm_l.setPos(0.1F, -0.3F, -6.8F);
        this.Palm_l.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Palm_l, 0.0F, 0.27314402127920984F, 0.0F);
        this.Horn4_l = new ModelRenderer(this, 25, 0);
        this.Horn4_l.mirror = true;
        this.Horn4_l.setPos(-1.0F, -5.0F, 0.0F);
        this.Horn4_l.addBox(0.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn4_l, 0.0F, 0.0F, -0.8210028961170991F);
        this.Claw_1_l = new ModelRenderer(this, 12, 16);
        this.Claw_1_l.mirror = true;
        this.Claw_1_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_1_l.addBox(0.0F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_1_l, 0.0F, 0.45535640450848164F, 0.0F);
        this.Claw_21_r = new ModelRenderer(this, 16, 20);
        this.Claw_21_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_21_r.addBox(0.0F, 0.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_21_r, 0.13665927909957545F, -0.45535640450848164F, 0.0F);
        this.Claw_11_l = new ModelRenderer(this, 16, 20);
        this.Claw_11_l.mirror = true;
        this.Claw_11_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_11_l.addBox(-1.0F, -0.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_11_l, 0.0F, 0.45535640450848164F, 0.0F);
        this.Arm_r = new ModelRenderer(this, 6, 26);
        this.Arm_r.setPos(-1.0F, 8.0F, 0.9F);
        this.Arm_r.addBox(-1.0F, -1.3F, -7.3F, 2.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_r, 0.5462880425584197F, 0.0F, 0.0F);
        this.Horn5_l = new ModelRenderer(this, 39, 0);
        this.Horn5_l.mirror = true;
        this.Horn5_l.setPos(-1.0F, -5.0F, 4.0F);
        this.Horn5_l.addBox(0.0F, 0.0F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn5_l, 0.0F, 0.0F, -0.7819074915776542F);
        this.Claw_2_r = new ModelRenderer(this, 12, 16);
        this.Claw_2_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_2_r.addBox(-1.0F, 0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_2_r, 0.13665927909957545F, -0.45535640450848164F, 0.0F);
        this.Horn2_l = new ModelRenderer(this, 25, 0);
        this.Horn2_l.mirror = true;
        this.Horn2_l.setPos(0.0F, -1.5F, 0.0F);
        this.Horn2_l.addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Claw_11_r = new ModelRenderer(this, 16, 20);
        this.Claw_11_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_11_r.addBox(0.0F, -0.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_11_r, 0.0F, -0.45535640450848164F, 0.0F);
        this.Leg_r = new ModelRenderer(this, 0, 52);
        this.Leg_r.setPos(-1.0F, 7.0F, 0.5F);
        this.Leg_r.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_r, 0.4098033003787853F, 0.0F, 0.0F);
        this.Tail = new ModelRenderer(this, 56, 59);
        this.Tail.setPos(-1.0F, 4.0F, 1.0F);
        this.Tail.addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Tail, 0.4098033003787853F, 0.0F, 0.0F);
        this.Claw_1_r = new ModelRenderer(this, 12, 16);
        this.Claw_1_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_1_r.addBox(-1.0F, -0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_1_r, 0.0F, -0.45535640450848164F, 0.0F);
        this.Ear_r = new ModelRenderer(this, 48, 0);
        this.Ear_r.setPos(-2.5F, -2.5F, 0.0F);
        this.Ear_r.addBox(-5.0F, 0.0F, -1.0F, 5.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Ear_r, 0.0F, 0.0F, -0.3909537457888271F);
        this.Claw_0_l = new ModelRenderer(this, 12, 16);
        this.Claw_0_l.mirror = true;
        this.Claw_0_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_0_l.addBox(0.0F, -1.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_0_l, -0.0911061832922575F, 0.45535640450848164F, 0.0F);
        this.Humerus_l = new ModelRenderer(this, 0, 21);
        this.Humerus_l.mirror = true;
        this.Humerus_l.setPos(2.0F, 1.0F, 1.5F);
        this.Humerus_l.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Humerus_l, 0.2275909337942703F, 0.18203784630933073F, -0.591841146688116F);
        this.Horn0_l = new ModelRenderer(this, 21, 0);
        this.Horn0_l.mirror = true;
        this.Horn0_l.setPos(-1.0F, -2.5F, 0.0F);
        this.Horn0_l.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn0_l, 0.0F, 0.0F, -0.5473352640780661F);
        this.Thigh_r = new ModelRenderer(this, 0, 37);
        this.Thigh_r.setPos(-2.5F, 3.0F, 0.0F);
        this.Thigh_r.addBox(-2.0F, 0.0F, -2.0F, 2.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Thigh_r, -1.5481069932557485F, 0.0F, 0.0F);
        this.Pelvis = new ModelRenderer(this, 46, 44);
        this.Pelvis.setPos(0.0F, 8.0F, 1.0F);
        this.Pelvis.addBox(-2.5F, -1.5F, -2.0F, 5.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Pelvis, 1.092750655326294F, 0.0F, 0.0F);
        this.Claw_21_l = new ModelRenderer(this, 16, 20);
        this.Claw_21_l.mirror = true;
        this.Claw_21_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_21_l.addBox(-1.0F, 0.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_21_l, 0.13665927909957545F, 0.45535640450848164F, 0.0F);
        this.Horn0_r = new ModelRenderer(this, 21, 0);
        this.Horn0_r.setPos(1.0F, -2.5F, 0.0F);
        this.Horn0_r.addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn0_r, 0.0F, 0.0F, 0.5473352640780661F);
        this.Horn3_l = new ModelRenderer(this, 31, 0);
        this.Horn3_l.mirror = true;
        this.Horn3_l.setPos(0.0F, -3.0F, -0.5F);
        this.Horn3_l.addBox(-1.0F, -5.0F, -0.5F, 1.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn3_l, 0.0F, 0.0F, -0.5473352640780661F);
        this.Arm_fur_r = new ModelRenderer(this, 29, 53);
        this.Arm_fur_r.setPos(0.0F, 0.0F, -3.3F);
        this.Arm_fur_r.addBox(-1.0F, 0.7F, -4.0F, 2.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Claw_01_l = new ModelRenderer(this, 16, 20);
        this.Claw_01_l.mirror = true;
        this.Claw_01_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_01_l.addBox(-1.0F, -1.5F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_01_l, -0.0911061832922575F, 0.45535640450848164F, 0.0F);
        this.Claw_0_r = new ModelRenderer(this, 12, 16);
        this.Claw_0_r.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_0_r.addBox(-1.0F, -1.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_0_r, -0.0911061832922575F, -0.45535640450848164F, 0.0F);
        this.Arm_l = new ModelRenderer(this, 6, 26);
        this.Arm_l.mirror = true;
        this.Arm_l.setPos(1.0F, 8.0F, 0.9F);
        this.Arm_l.addBox(-1.0F, -1.3F, -7.3F, 2.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Arm_l, 0.5462880425584197F, 0.0F, 0.0F);
        this.Feet_l = new ModelRenderer(this, 12, 57);
        this.Feet_l.mirror = true;
        this.Feet_l.setPos(1.0F, 7.0F, 2.0F);
        this.Feet_l.addBox(-1.5F, -1.0F, -4.0F, 3.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Feet_l, 0.06736970729605787F, 0.0F, 0.0F);
        this.Vertebra2 = new ModelRenderer(this, 16, 0);
        this.Vertebra2.setPos(0.0F, 0.0F, 0.0F);
        this.Vertebra2.addBox(-0.5F, -4.0F, 2.2F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Waist = new ModelRenderer(this, 40, 25);
        this.Waist.setPos(0.0F, -2.0F, 1.0F);
        this.Waist.addBox(-3.0F, -7.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Waist, -0.5462880425584197F, 0.0F, 0.0F);
        this.Claw_2_l = new ModelRenderer(this, 12, 16);
        this.Claw_2_l.mirror = true;
        this.Claw_2_l.setPos(0.0F, 0.0F, -2.5F);
        this.Claw_2_l.addBox(0.0F, 0.5F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Claw_2_l, 0.13665927909957545F, 0.45535640450848164F, 0.0F);
        this.Leg_l = new ModelRenderer(this, 0, 52);
        this.Leg_l.mirror = true;
        this.Leg_l.setPos(0.0F, 7.0F, 0.5F);
        this.Leg_l.addBox(0.0F, 0.0F, 0.0F, 2.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Leg_l, 0.4098033003787853F, 0.0F, 0.0F);
        this.Feet_r = new ModelRenderer(this, 12, 57);
        this.Feet_r.setPos(0.0F, 7.0F, 2.0F);
        this.Feet_r.addBox(-1.5F, -1.0F, -4.0F, 3.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Feet_r, 0.06736970729605787F, 0.0F, 0.0F);
        this.Horn4_r = new ModelRenderer(this, 25, 0);
        this.Horn4_r.setPos(1.0F, -5.0F, 0.0F);
        this.Horn4_r.addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn4_r, 0.0F, 0.0F, 0.8210028961170991F);
        this.Horn6_r = new ModelRenderer(this, 20, 16);
        this.Horn6_r.setPos(1.0F, -0.5F, 4.5F);
        this.Horn6_r.addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn6_r, 0.0F, 0.7037167490777915F, 0.0F);
        this.Vertebra0 = new ModelRenderer(this, 16, 0);
        this.Vertebra0.setPos(0.0F, 0.0F, 0.0F);
        this.Vertebra0.addBox(-0.5F, -4.0F, -1.8F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Thigh_l = new ModelRenderer(this, 0, 37);
        this.Thigh_l.mirror = true;
        this.Thigh_l.setPos(2.5F, 3.0F, 0.0F);
        this.Thigh_l.addBox(0.0F, 0.0F, -2.0F, 2.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Thigh_l, -1.5481069932557485F, 0.0F, 0.0F);
        this.Horn3_r = new ModelRenderer(this, 31, 0);
        this.Horn3_r.setPos(0.0F, -3.0F, -0.5F);
        this.Horn3_r.addBox(0.0F, -5.0F, -0.5F, 1.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Horn3_r, 0.0F, 0.0F, 0.5473352640780661F);
        this.Vertebra1 = new ModelRenderer(this, 16, 0);
        this.Vertebra1.setPos(0.0F, 0.0F, 0.0F);
        this.Vertebra1.addBox(-0.5F, -4.0F, 0.2F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Chest = new ModelRenderer(this, 34, 10);
        this.Chest.setPos(0.0F, -7.5F, -1.0F);
        this.Chest.addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Chest, -0.500909508638178F, 0.0F, 0.0F);
        this.Horn3_l.addChild(this.Horn6_l);
        this.Horn0_l.addChild(this.Horn1_l);
        this.Waist.addChild(this.Inside);
        this.Head.addChild(this.Jaw_lower);
        this.Head.addChild(this.Jaw_up);
        this.Horn3_r.addChild(this.Horn5_r);
        this.Horn1_r.addChild(this.Horn2_r);
        this.Arm_l.addChild(this.Arm_fur_l);
        this.Palm_r.addChild(this.Claw_01_r);
        this.Head.addChild(this.Ear_l);
        this.Arm_r.addChild(this.Palm_r);
        this.Chest.addChild(this.Head);
        this.Chest.addChild(this.Humerus_r);
        this.Horn0_r.addChild(this.Horn1_r);
        this.Arm_l.addChild(this.Palm_l);
        this.Horn3_l.addChild(this.Horn4_l);
        this.Palm_l.addChild(this.Claw_1_l);
        this.Palm_r.addChild(this.Claw_21_r);
        this.Palm_l.addChild(this.Claw_11_l);
        this.Humerus_r.addChild(this.Arm_r);
        this.Horn3_l.addChild(this.Horn5_l);
        this.Palm_r.addChild(this.Claw_2_r);
        this.Horn1_l.addChild(this.Horn2_l);
        this.Palm_r.addChild(this.Claw_11_r);
        this.Thigh_r.addChild(this.Leg_r);
        this.Pelvis.addChild(this.Tail);
        this.Palm_r.addChild(this.Claw_1_r);
        this.Head.addChild(this.Ear_r);
        this.Palm_l.addChild(this.Claw_0_l);
        this.Chest.addChild(this.Humerus_l);
        this.Head.addChild(this.Horn0_l);
        this.Pelvis.addChild(this.Thigh_r);
        this.Palm_l.addChild(this.Claw_21_l);
        this.Head.addChild(this.Horn0_r);
        this.Horn1_l.addChild(this.Horn3_l);
        this.Arm_r.addChild(this.Arm_fur_r);
        this.Palm_l.addChild(this.Claw_01_l);
        this.Palm_r.addChild(this.Claw_0_r);
        this.Humerus_l.addChild(this.Arm_l);
        this.Leg_l.addChild(this.Feet_l);
        this.Chest.addChild(this.Vertebra2);
        this.Pelvis.addChild(this.Waist);
        this.Palm_l.addChild(this.Claw_2_l);
        this.Thigh_l.addChild(this.Leg_l);
        this.Leg_r.addChild(this.Feet_r);
        this.Horn3_r.addChild(this.Horn4_r);
        this.Horn3_r.addChild(this.Horn6_r);
        this.Chest.addChild(this.Vertebra0);
        this.Pelvis.addChild(this.Thigh_l);
        this.Horn1_r.addChild(this.Horn3_r);
        this.Chest.addChild(this.Vertebra1);
        this.Waist.addChild(this.Chest);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Pelvis).forEach((modelRenderer) -> { 
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
    	int i = ((WendigoEntity) entityIn).getAttackTimer();
    	byte j = ((WendigoEntity) entityIn).getAttackStance();
    	
    	this.Head.xRot = headPitch * 0.017453292F;
        this.Head.yRot = netHeadYaw * 0.017453292F;
        this.Head.y = -0.3F * MathHelper.sin(0.03F * ageInTicks);
    	this.Jaw_lower.xRot = 0.275F + (-0.07F * MathHelper.sin(0.03F * ageInTicks));

    	this.Leg_r.xRot = 0.40980330836826856F - MathHelper.cos(limbSwing * 0.3F + 0.3F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Leg_l.xRot = 0.40980330836826856F - MathHelper.cos(limbSwing * 0.3F + 1.3F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Feet_r.xRot = 0.06736970912698112F + MathHelper.cos(limbSwing * 0.3F + 0.5F * (float)Math.PI) * 0.7F * limbSwingAmount;
    	this.Feet_l.xRot = 0.06736970912698112F + MathHelper.cos(limbSwing * 0.3F + 1.5F * (float)Math.PI) * 0.7F * limbSwingAmount;

    	if (this.riding) {
    		this.Thigh_r.xRot = -2.681872992530353F;
    		this.Thigh_l.xRot = -2.681872992530353F;
    	} else {
        	this.Thigh_r.xRot = -1.5481070465189704F + MathHelper.cos(limbSwing * 0.3F) * 0.7F * limbSwingAmount;
        	this.Thigh_l.xRot = -1.5481070465189704F + MathHelper.cos(limbSwing * 0.3F + (float)Math.PI) * 0.7F * limbSwingAmount;   		
    	}
    	
    	if (i > 10) {  		
    		if (j == (byte)4 || j == (byte)5) {
	    		this.Humerus_r.xRot = -0.27314402793711257F;
	    		this.Humerus_r.yRot = -0.18203784098300857F + 2.8F * MathHelper.sin((float)Math.PI * 0.125F * (i - 11));
	    		this.Humerus_r.zRot = 1.3203415791337103F;
	    		this.Chest.yRot = GradientAnimation(1.1383037381507017F, 0.0F, (i - 10.0F)/10.0F);
    		}
    		
    		if (j == (byte)4 || j == (byte)6) {
	    		this.Humerus_l.xRot = -0.27314402793711257F;
	    		this.Humerus_l.yRot = 0.18203784098300857F - 2.8F * MathHelper.sin((float)Math.PI * 0.125F * (i - 11));
	    		this.Humerus_l.zRot = -1.3203415791337103F;
	    		this.Chest.yRot = GradientAnimation(-1.1383037381507017F, 0.0F, (i - 10.0F)/10.0F);
    		}
    		
    		if (j == (byte)4) {
    			this.Chest.yRot = 0.0F;
    			this.Chest.xRot = GradientAnimation(-1.1383037381507017F, -0.5009094953223726F, (i - 10.0F)/10.0F);
    		}
    		
    		this.Waist.xRot = GradientAnimation(-0.5691518690753509F, -0.136659280431156F, (i - 10.0F)/10.0F); 		
    	} else if (i > 0) {
    		if (j == (byte)4 || j == (byte)5) {
	        	this.Humerus_r.xRot = -0.18203784098300857F; 	
	        	this.Humerus_r.yRot = -0.18203784098300857F;// - 0.8F * this.triangleWave((float)i/* - ageInTicks*/, 11.0F);
	        	this.Humerus_r.zRot = 0.22759093446006054F;
    		}
    		
        	if (j == (byte)4 || j == (byte)6) {
	        	this.Humerus_l.xRot = -0.18203784098300857F;
	        	this.Humerus_l.yRot = 0.18203784098300857F;// + 0.8F * this.triangleWave((float)i/* - ageInTicks*/, 11.0F);
	        	this.Humerus_l.zRot = -0.22759093446006054F;
        	}
        } else {      	
        	this.setRotateAngle(Humerus_r, 0.22759093446006054F, -0.18203784098300857F, 0.5918411493512771F);
        	this.setRotateAngle(Humerus_l, 0.22759093446006054F, 0.18203784098300857F, -0.5918411493512771F);
        	
        	this.Humerus_r.y = 1.0F + (-0.55F  * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI)); 
        	this.Humerus_l.y = 1.0F + (-0.55F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));   
        	
        	this.setRotateAngle(Chest, -0.5009094953223726F, 0.0F, 0.0F);
        	this.Waist.xRot = -0.5691518690753509F;       	
        }
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
