package com.Fishmod.fur.client.renderer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.FURModelLayers;
import com.Fishmod.fur.client.model.item.ModelVespaShield;
import com.Fishmod.fur.client.renderer.blockentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FURItemstackRenderer extends BlockEntityWithoutLevelRenderer {

    private static final ResourceLocation VESPA_SHIELD_TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/vespa/vespa.png");

    private final ModelVespaShield vespaShieldModel;

    public FURItemstackRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.vespaShieldModel = new ModelVespaShield(
                Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.VESPA_SHIELD));
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemDisplayContext displayContext, PoseStack matrixStackIn,
                             MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Context context = new Context(
                Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getBlockRenderer(),
                Minecraft.getInstance().getItemRenderer(),
                Minecraft.getInstance().getEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels(),
                Minecraft.getInstance().font);

        if (itemStackIn.getItem() == FURItemRegistry.VESPA_SHIELD.get()) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer buffer = ItemRenderer.getFoilBuffer(
                    bufferIn, RenderType.entityCutoutNoCull(VESPA_SHIELD_TEXTURE),
                    false, itemStackIn.hasFoil());
            vespaShieldModel.renderToBuffer(matrixStackIn, buffer, combinedLightIn, combinedOverlayIn,
                    1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }

        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_COMMON.get().asItem()) {
            new ScarecrowHeadTileEntityRenderer<>(0, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }

        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_STRAW.get().asItem()) {
            new ScarecrowHeadTileEntityRenderer<>(1, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }

        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_PLAGUE.get().asItem()) {
            new ScarecrowHeadTileEntityRenderer<>(2, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }
}
