package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.layer.LayerRavenGlow;
import com.Fishmod.fur.client.model.RavenModel;
import com.Fishmod.fur.entities.tameable.RavenEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

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
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RavenRenderer extends GeoEntityRenderer<RavenEntity> {
    private static final String BEAK_BONE = "beak1";

    protected ItemStack mainHandItem;

    public RavenRenderer(EntityRendererProvider.Context context) {
        super(context, new RavenModel());
        this.shadowRadius = 0.3F;

        this.addRenderLayer(new LayerRavenGlow(this));

        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, RavenEntity animatable) {
                return bone.getName().equals(BEAK_BONE) ? RavenRenderer.this.mainHandItem : null;
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, RavenEntity animatable) {
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
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

        if (!isReRender && animatable.getSkin() == 1) {
            poseStack.scale(1.2F, 1.2F, 1.2F);
        }
    }

    @Override
    protected int getBlockLightLevel(RavenEntity entity, BlockPos pos) {
        return entity.getSkin() == 3 ? 15 : super.getBlockLightLevel(entity, pos);
    }
}
