package com.Fishmod.mod_LavaCow.client.renders.item;

import com.Fishmod.mod_LavaCow.client.model.item.ModelBeastClaw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBeastClaw extends TileEntityItemStackRenderer {
    private final ModelBeastClaw MODEL_BEAST_CLAW = new ModelBeastClaw();
    private final ResourceLocation BEAST_CLAW_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/wendigo.png");

    @Override
    public void renderByItem(ItemStack stack) {
    	this.renderByItem(stack, 1.0F);
    }

    @Override
	public void renderByItem(ItemStack stack, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(BEAST_CLAW_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0, -1.0, -1.0);
        MODEL_BEAST_CLAW.render();
        GlStateManager.popMatrix();
    }
}
