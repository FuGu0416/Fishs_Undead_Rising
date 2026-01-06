package com.Fishmod.fur.client.renderer.item;

import com.Fishmod.fur.entities.projectiles.FangDaggerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;

public class FangDaggerRenderer extends EntityRenderer<FangDaggerEntity> {
    private final ItemRenderer itemRenderer;

    public FangDaggerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(FangDaggerEntity entity, float yaw, float ticks, PoseStack stack, MultiBufferSource bufferSource, int i) {
        super.render(entity, yaw, ticks, stack, bufferSource, i);
        stack.pushPose();

        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(ticks, entity.yRotO, entity.getYRot()) - 90.0F));
        stack.translate(0, 0.1, 0);

        if (entity.isInGround()) {
            float shakeTime = (float) entity.shakeTime - ticks;
            float rotation = -120;
            if (entity.blockSide != null) {
                Direction direction = entity.blockSide;
                if (direction == Direction.UP) {
                    rotation = -210;
                } else if (direction == Direction.DOWN) {
                    rotation = -30;
                }
            }
            stack.mulPose(Axis.ZP.rotationDegrees(rotation));
            
            if (entity.blockSide != null) {
                if (entity.blockSide == Direction.DOWN) {
                    stack.translate(0, -0.1, 0);
                } else if (entity.blockSide != Direction.UP) {
                    stack.translate(0, -0.1, 0);
                }
            }
            
            if (shakeTime > 0.0F) {
                float f10 = -Mth.sin(shakeTime * 1.5f) * shakeTime;
                stack.translate(0, 0.2, 0);
                stack.mulPose(Axis.XN.rotationDegrees(f10));
                stack.translate(0, -0.2, 0);
            }
        }

        itemRenderer.render(entity.getRenderItem(), ItemDisplayContext.FIXED, false, stack, bufferSource, i, OverlayTexture.NO_OVERLAY,
                itemRenderer.getModel(entity.getRenderItem(), entity.level(), null, entity.getId()));

        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FangDaggerEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
