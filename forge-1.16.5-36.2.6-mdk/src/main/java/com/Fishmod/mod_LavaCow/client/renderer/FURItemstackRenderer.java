package com.Fishmod.mod_LavaCow.client.renderer;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelIllagerNose;
import com.Fishmod.mod_LavaCow.client.model.item.ModelBeastClaw;
import com.Fishmod.mod_LavaCow.client.model.item.ModelSkeletonKingMace;
import com.Fishmod.mod_LavaCow.client.model.item.ModelVespaShield;
import com.Fishmod.mod_LavaCow.client.renderer.tileentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FURItemstackRenderer extends ItemStackTileEntityRenderer {
    private static final ModelBeastClaw BEAST_CLAW_MODEL = new ModelBeastClaw();
    private static final ResourceLocation BEAST_CLAW_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/wendigo.png");
    private static final ModelSkeletonKingMace SKELETONKING_MACE_MODEL = new ModelSkeletonKingMace();
    private static final ResourceLocation SKELETONKING_MACE_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking.png");
    private static final ModelVespaShield VESPA_SHIELD_MODEL = new ModelVespaShield();
    private static final ResourceLocation VESPA_SHIELD_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa.png");
    private static final ModelIllagerNose<?> ILLAGER_NOSE_MODEL = new ModelIllagerNose<>(1.0F);
    private static final ResourceLocation ILLAGER_NOSE_TEXTURE = new ResourceLocation("mod_lavacow:textures/armors/illager_nose.png");
    
    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (itemStackIn.getItem() == FURItemRegistry.BEAST_CLAW) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(BEAST_CLAW_TEXTURE), false, itemStackIn.hasFoil());
            BEAST_CLAW_MODEL.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
        
        if (itemStackIn.getItem() == FURItemRegistry.SKELETONKING_MACE) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(SKELETONKING_MACE_TEXTURE), false, itemStackIn.hasFoil());
            SKELETONKING_MACE_MODEL.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
        
        if (itemStackIn.getItem() == FURItemRegistry.VESPA_SHIELD) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(VESPA_SHIELD_TEXTURE), false, itemStackIn.hasFoil());
            VESPA_SHIELD_MODEL.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_COMMON.asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(0, TileEntityRendererDispatcher.instance).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_STRAW.asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(1, TileEntityRendererDispatcher.instance).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_PLAGUE.asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(2, TileEntityRendererDispatcher.instance).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        if (itemStackIn.getItem() == FURItemRegistry.ILLAGER_NOSE){
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(ILLAGER_NOSE_TEXTURE), false, itemStackIn.hasFoil());
            ILLAGER_NOSE_MODEL.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }
}
