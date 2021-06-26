package com.Fishmod.mod_LavaCow.client.renders.item;

import com.Fishmod.mod_LavaCow.client.model.item.ModelSkeletonKingMace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSkeletonKingMace extends TileEntityItemStackRenderer {
    private final ModelSkeletonKingMace MODEL_SKELETONKING_MACE = new ModelSkeletonKingMace();
    private final ResourceLocation SKELETONKING_MACE_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking.png");

    @Override
    public void renderByItem(ItemStack stack) {
    	this.renderByItem(stack, 1.0F);
    }

    @Override
	public void renderByItem(ItemStack stack, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(SKELETONKING_MACE_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0, -1.0, -1.0);
        MODEL_SKELETONKING_MACE.render();
        GlStateManager.popMatrix();
    }
}
