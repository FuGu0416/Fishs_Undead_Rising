package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.IAggressive;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelGraveRobberGhost extends ModelFlyingBase {
    private final ModelRenderer head;
    private final ModelRenderer nose;
    private final ModelRenderer arm_l;
    private final ModelRenderer arm_r;
    private final ModelRenderer body;
    private final ModelRenderer arm_fold;
    private final ModelRenderer hand_fold;

    public ModelGraveRobberGhost() {
        textureWidth = 128;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, false);

        nose = new ModelRenderer(this);
        nose.setRotationPoint(0.0F, -2.0F, 0.0F);
        head.addChild(nose);
        nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, false);

        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(5.0F, 2.0F, 0.0F);
        setRotateAngle(arm_l, -1.2654F, 0.0F, 0.0F);
        arm_l.setTextureOffset(40, 46).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, false);

        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(-5.0F, 2.0F, 0.0F);
        setRotateAngle(arm_r, -1.2654F, 0.0F, 0.0F);
        arm_r.setTextureOffset(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, true);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, false);

        arm_fold = new ModelRenderer(this);
        arm_fold.setRotationPoint(0.0F, 3.0F, -1.0F);
        setRotateAngle(arm_fold, -0.75F, 0.0F, 0.0F);
        arm_fold.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, false);
        arm_fold.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, false);

        hand_fold = new ModelRenderer(this);
        hand_fold.setRotationPoint(0.0F, 0.0F, 0.0F);
        arm_fold.addChild(hand_fold);
        hand_fold.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, true);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        head.render(scale);
        arm_l.render(scale);
        arm_r.render(scale);
        body.render(scale);
        arm_fold.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        this.Head_Looking(this.head, 0.0F, 0.0F, netHeadYaw, headPitch);

        boolean aggressive = ((IAggressive) entity).isAggressive();

        if (aggressive) {
            arm_l.isHidden = false;
            arm_r.isHidden = false;
            arm_fold.isHidden = true;
        } else {
            arm_l.isHidden = true;
            arm_r.isHidden = true;
            arm_fold.isHidden = false;
        }
    }
}
