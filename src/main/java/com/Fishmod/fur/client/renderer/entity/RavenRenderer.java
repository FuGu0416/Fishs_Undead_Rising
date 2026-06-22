package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.RavenModel;
import com.Fishmod.fur.entities.tameable.RavenEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RavenRenderer extends GeoEntityRenderer<RavenEntity> {
    private static final String BEAK_BONE = "beak1";

    private static final float BEAK_ITEM_SCALE = 0.8F;
    private static final float BEAK_ITEM_ROT_X = -90.0F;
    private static final float BEAK_ITEM_ROT_Y = 0.0F;
    private static final float BEAK_ITEM_ROT_Z = 0.0F;
    private static final float BEAK_ITEM_OFFSET_X = 0.0F;
    private static final float BEAK_ITEM_OFFSET_Y = 0.0F;
    private static final float BEAK_ITEM_OFFSET_Z = -0.1F;

    protected ItemStack mainHandItem;

    public RavenRenderer(EntityRendererProvider.Context context) {
        super(context, new RavenModel());
        this.shadowRadius = 0.3F;

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));

        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, RavenEntity animatable) {
                return bone.getName().equals(BEAK_BONE) ? RavenRenderer.this.mainHandItem : null;
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, RavenEntity animatable) {
                return ItemDisplayContext.GROUND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, RavenEntity animatable,
                                              MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                poseStack.translate(BEAK_ITEM_OFFSET_X, BEAK_ITEM_OFFSET_Y, BEAK_ITEM_OFFSET_Z);
                poseStack.mulPose(Axis.XP.rotationDegrees(BEAK_ITEM_ROT_X));
                poseStack.mulPose(Axis.YP.rotationDegrees(BEAK_ITEM_ROT_Y));
                poseStack.mulPose(Axis.ZP.rotationDegrees(BEAK_ITEM_ROT_Z));
                poseStack.scale(BEAK_ITEM_SCALE, BEAK_ITEM_SCALE, BEAK_ITEM_SCALE);
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(RavenEntity entity) {
        return super.getTextureLocation(entity);
    }

    @Override
    public void preRender(PoseStack poseStack, RavenEntity animatable, BakedGeoModel model,
                          MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                          float partialTick, int packedLight, int packedOverlay,
                          float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender,
                        partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.mainHandItem = animatable.getMainHandItem();
    }

    @Override
    protected int getBlockLightLevel(RavenEntity entity, BlockPos pos) {
        return entity.getSkin() == 2 ? 15 : super.getBlockLightLevel(entity, pos);
    }
}
