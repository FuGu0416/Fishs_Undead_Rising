package com.Fishmod.mod_LavaCow.client.renders.item;

import com.Fishmod.mod_LavaCow.client.model.item.ModelVespaShield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderVespaShield extends TileEntityItemStackRenderer {
    private final ModelVespaShield MODEL_SHIELD = new ModelVespaShield();
    private final ResourceLocation SHIELD_PARTS_TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/vespa.png");

    @Override
    public void renderByItem(ItemStack stack) {
    	this.renderByItem(stack, 1.0F);
    }

    @Override
	public void renderByItem(ItemStack stack, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_PARTS_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0, -1.0, -1.0);
        MODEL_SHIELD.render();
        GlStateManager.popMatrix();
    }
    
    /*private void renderItem(ItemStack stack){
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null));
    }*/
}
