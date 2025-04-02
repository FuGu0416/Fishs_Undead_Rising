package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.EntityGhoul;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhoul extends FishModelBase {
    private final ModelRenderer torso;
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer jaw_teeth;
    private final ModelRenderer head_teeth;
    private final ModelRenderer arm_r;
    private final ModelRenderer claw_r;
    private final ModelRenderer arm_l;
    private final ModelRenderer claw_l;
    private final ModelRenderer leg_r;
    private final ModelRenderer leg_l;

    public ModelGhoul() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, 9.5F, -3.0F);
        setRotationAngle(torso, 0.2391F, 0.0F, 0.0F);
        torso.setTextureOffset(26, 27).addBox(-3.0F, 0.0F, -2.0F, 6, 10, 4, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -2.2F, -0.7F);
        torso.addChild(head);
        setRotationAngle(head, -0.2391F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, -8.0F, 8, 6, 8, false);

        jaw = new ModelRenderer(this);
        jaw.setRotationPoint(-0.5F, 3.0F, -1.0F);
        head.addChild(jaw);
        setRotationAngle(jaw, 0.3965F, 0.0F, 0.0F);
        jaw.setTextureOffset(0, 14).addBox(-4.0F, -0.1499F, -7.569F, 9, 2, 8, false);

        jaw_teeth = new ModelRenderer(this);
        jaw_teeth.setRotationPoint(0.5F, -1.1499F, 1.981F);
        jaw.addChild(jaw_teeth);
        jaw_teeth.setTextureOffset(0, 24).addBox(-4.5F, 0.0F, -9.5F, 9, 1, 6, false);

        head_teeth = new ModelRenderer(this);
        head_teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(head_teeth);
        head_teeth.setTextureOffset(24, 0).addBox(-3.5F, 3.0F, -7.5F, 7, 1, 4, false);

        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(3.0F, 3.0F, 0.0F);
        torso.addChild(arm_r);
        setRotationAngle(arm_r, -0.9019F, -0.1059F, -0.262F);
        arm_r.setTextureOffset(0, 31).addBox(0.0F, -1.0F, -1.0F, 3, 10, 3, false);

        claw_r = new ModelRenderer(this);
        claw_r.setRotationPoint(2.974F, 9.0F, 0.5F);
        arm_r.addChild(claw_r);
        setRotationAngle(claw_r, 0.0F, 0.0F, 0.6545F);
        claw_r.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -1.5F, 0, 2, 3, false);

        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(-3.0F, 3.0F, 0.0F);
        torso.addChild(arm_l);
        setRotationAngle(arm_l, -0.9019F, 0.1059F, 0.262F);
        arm_l.setTextureOffset(0, 31).addBox(-3.0F, -1.0F, -1.0F, 3, 10, 3, true);

        claw_l = new ModelRenderer(this);
        claw_l.setRotationPoint(-3.0F, 9.0F, 0.5F);
        arm_l.addChild(claw_l);
        setRotationAngle(claw_l, 0.0F, 0.0F, -0.6545F);
        claw_l.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -1.5F, 0, 2, 3, false);

        leg_r = new ModelRenderer(this);
        leg_r.setRotationPoint(1.9F, 7.0F, 1.0F);
        torso.addChild(leg_r);
        setRotationAngle(leg_r, -0.2391F, 0.0F, 0.0F);
        leg_r.setTextureOffset(30, 12).addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, false);

        leg_l = new ModelRenderer(this);
        leg_l.setRotationPoint(-1.9F, 7.0F, 1.0F);
        torso.addChild(leg_l);
        setRotationAngle(leg_l, -0.2391F, 0.0F, 0.0F);
        leg_l.setTextureOffset(30, 12).addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, true);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.torso.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = 0.67F;
        float i = Math.max(0F, (float) ((EntityGhoul) entityIn).getAttackTimer() - 5F) / ((float) EntityGhoul.ATTACK_TIMER);

        this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);
        this.torso.rotateAngleZ = MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;

        if (this.isRiding) {
            setRotationAngle(leg_r, -1.4757F, -0.2048F, -0.0757F);
            setRotationAngle(leg_l, -1.4757F, 0.2048F, 0.0757F);
        } else {
            this.leg_r.rotateAngleX = -0.2391F + MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.leg_r.rotateAngleY = 0.0F;
            this.leg_r.rotateAngleZ = -MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
            this.leg_l.rotateAngleX = -0.2391F + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.2F * limbSwingAmount;
            this.leg_l.rotateAngleY = 0.0F;
            this.leg_l.rotateAngleZ = -MathHelper.cos(limbSwing * f) * 0.1F * limbSwingAmount;
        }

        if (((EntityGhoul) entityIn).getAttackTimer() > 0) {
            this.head.rotateAngleX = GradientAnimation(0.0F, (float) Math.toRadians(27.5F), i);
            this.jaw.rotateAngleX = GradientAnimation(0.3965F + (float) Math.toRadians(32.5F), 0.3965F + (float) Math.toRadians(-25F), i);

            this.arm_r.rotateAngleX = GradientAnimation(-0.9019F + (float) Math.toRadians(73.4081F), -0.9019F + (float) Math.toRadians(-9.2895F), i);
            this.arm_r.rotateAngleY = GradientAnimation(-0.1059F + (float) Math.toRadians(-76.6079F), -0.1059F + (float) Math.toRadians(44.8287F), i);
            this.arm_r.rotateAngleZ = GradientAnimation(-0.262F + (float) Math.toRadians(-141.1723F), -0.262F + (float) Math.toRadians(-7.7945F), i);
            this.arm_l.rotateAngleX = GradientAnimation(-0.9019F + (float) Math.toRadians(73.4081F), -0.9019F + (float) Math.toRadians(-9.2895F), i);
            this.arm_l.rotateAngleY = GradientAnimation(0.1059F + (float) Math.toRadians(76.6079F), 0.1059F + (float) Math.toRadians(-44.8287F), i);
            this.arm_l.rotateAngleZ = GradientAnimation(0.262F + (float) Math.toRadians(141.1723F), 0.262F + (float) Math.toRadians(7.7945F), i);
        } else if (((EntityGhoul) entityIn).isAggressive()) {
            this.torso.setRotationPoint(0.0F, 9.5F, -3.0F);
            this.jaw.rotateAngleX = 0.5711F;

            this.setRotationAngle(arm_r, -1.6025F + (0.2F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount, -0.4112F, -0.2525F);
            this.setRotationAngle(arm_l, -1.6025F - (0.2F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount, 0.4112F, 0.2525F);
        } else {
            this.torso.setRotationPoint(0.0F, 9.5F, -3.0F);
            this.jaw.rotateAngleX = 0.08F + (0.08F * MathHelper.sin(0.03F * ageInTicks));

            if (this.isRiding) {
                this.setRotationAngle(arm_r, -0.9019F, -0.1059F, -0.262F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
                this.setRotationAngle(arm_l, -0.9019F, 0.1059F, 0.262F + MathHelper.sin(ageInTicks * 0.3F) * 0.03F);
            } else {
                this.arm_r.rotateAngleX = -0.2391F + (-0.2474F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
                this.arm_r.rotateAngleY = -0.1059F;
                this.arm_r.rotateAngleZ = -0.262F;
                this.arm_l.rotateAngleX = -0.2391F + (-0.2474F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
                this.arm_l.rotateAngleY = 0.1059F;
                this.arm_l.rotateAngleZ = 0.262F;
            }
        }
    }
}
