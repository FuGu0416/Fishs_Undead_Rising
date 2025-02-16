package com.Fishmod.mod_LavaCow.client.renders.item;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelIllagerNose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderIllagerNose extends TileEntityItemStackRenderer {
    private final ModelIllagerNose MODEL_ILLAGER_NOSE = new ModelIllagerNose(1.0F);
    private final ResourceLocation ILLAGER_NOSE_TEXTURE = new ResourceLocation("mod_lavacow:textures/armors/illager_nose.png");

    @Override
    public void renderByItem(ItemStack stack) {
        this.renderByItem(stack, 1.0F);
    }

    @Override
    public void renderByItem(ItemStack stack, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ILLAGER_NOSE_TEXTURE);
        MODEL_ILLAGER_NOSE.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}
