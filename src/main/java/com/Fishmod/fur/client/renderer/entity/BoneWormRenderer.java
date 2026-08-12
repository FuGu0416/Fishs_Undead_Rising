package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.BoneWormModel;
import com.Fishmod.fur.entities.BoneWormEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

/**
 * Boneworm's renderer replicates 1.16.5's dig-transition trick: rather than any real "sink into
 * the ground" geometry, the whole model is translated down and spun around Y by
 * {@code locationFix} blocks/degrees each frame, and simply isn't drawn once
 * {@link BoneWormEntity#isHidden()} — the worm just isn't there to see once it's mostly
 * submerged. Shadow shrinks to nothing at the same point, and {@code isHidden()} is the same
 * check {@link BoneWormEntity#hurt} uses to go untouchable, so visibility and invulnerability can
 * no longer disagree the way 1.16.5's separate 1.5/3.0 thresholds did.
 */
@OnlyIn(Dist.CLIENT)
public class BoneWormRenderer extends GeoEntityRenderer<BoneWormEntity> {

    public BoneWormRenderer(EntityRendererProvider.Context context) {
        super(context, new BoneWormModel());
        this.shadowRadius = 0.5F;

        // Eyes only glow on the Soul Sand Valley skin (skin 1): AutoGlowingGeoLayer looks up a
        // "<active texture>_glowmask" file per frame, and only boneworm1_glowmask.png exists, so
        // skin 0 naturally renders with no glow layer at all - same conditional the 1.16.5 EyeLayer
        // enforced explicitly (`if (skin != 0)`).
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void render(BoneWormEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        boolean hidden = entity.isHidden();
        this.shadowRadius = hidden ? 0.0F : 0.5F;

        if (!hidden) {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }
    }

    @Override
    public void preRender(PoseStack poseStack, BoneWormEntity animatable, BakedGeoModel model,
                          MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                          float partialTick, int packedLight, int packedOverlay,
                          float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender,
                        partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        poseStack.translate(0.0D, -animatable.getLocationFix(), 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F * (float) animatable.getLocationFix()));
    }
}
