package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SwarmerLauncherModel;
import com.Fishmod.fur.entities.projectiles.SwarmerLauncherEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SwarmerLauncherRenderer extends GeoEntityRenderer<SwarmerLauncherEntity> {

    public SwarmerLauncherRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SwarmerLauncherModel());
        this.shadowRadius = 0.2F;
    }

    @Override
    public ResourceLocation getTextureLocation(SwarmerLauncherEntity entity) {
        return super.getTextureLocation(entity);
    }

    @Override
    protected void applyRotations(SwarmerLauncherEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        float f = (float) (4.3F * Math.sin(0.6F * ageInTicks));
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
    }
}
