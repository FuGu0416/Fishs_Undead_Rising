package com.Fishmod.fur.client.renderer;

import com.Fishmod.fur.client.renderer.blockentity.ScarecrowHeadTileEntityRenderer;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FURItemstackRenderer extends BlockEntityWithoutLevelRenderer  {
    /*private static final ModelBeastClaw BEAST_CLAW_MODEL = new ModelBeastClaw();
    private static final ResourceLocation BEAST_CLAW_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/wendigo.png");
    private static final ModelSkeletonKingMace SKELETONKING_MACE_MODEL = new ModelSkeletonKingMace();
    private static final ResourceLocation SKELETONKING_MACE_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking.png");
    private static final ModelVespaShield VESPA_SHIELD_MODEL = new ModelVespaShield();
    private static final ResourceLocation VESPA_SHIELD_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa.png");
    private static final ModelIllagerNose<?> ILLAGER_NOSE_MODEL = new ModelIllagerNose<>(1.0F);
    private static final ResourceLocation ILLAGER_NOSE_TEXTURE = new ResourceLocation("mod_lavacow:textures/armors/illager_nose.png");*/
    
    public FURItemstackRenderer() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
    public void renderByItem(ItemStack itemStackIn, ItemDisplayContext p_239207_2_, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        @SuppressWarnings("resource")
		Context context = new Context(Minecraft.getInstance().getBlockEntityRenderDispatcher(), 
        		Minecraft.getInstance().getBlockRenderer(), 
        		Minecraft.getInstance().getItemRenderer(), 
        		Minecraft.getInstance().getEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), Minecraft.getInstance().font);
		
		/*if (itemStackIn.getItem() == FURItemRegistry.BEAST_CLAW) {
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
        }*/
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_COMMON.get().asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(0, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_STRAW.get().asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(1, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        if (itemStackIn.getItem() == FURBlockRegistry.SCARECROWHEAD_PLAGUE.get().asItem()) {
        	new ScarecrowHeadTileEntityRenderer<>(2, context).render(null, combinedOverlayIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
        
        /*if (itemStackIn.getItem() == FURItemRegistry.ILLAGER_NOSE){
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, RenderType.entityCutoutNoCull(ILLAGER_NOSE_TEXTURE), false, itemStackIn.hasFoil());
            ILLAGER_NOSE_MODEL.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }*/
    }
}
