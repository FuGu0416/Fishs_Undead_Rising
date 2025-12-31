package com.Fishmod.fur.block.blockentity.container;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulFurnaceScreen extends AbstractContainerScreen<SoulFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/soul_furnace.png");

    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int progress = menu.getProgressScaled(24);
        gfx.blit(TEXTURE,
            leftPos + 79, topPos + 34,
            176, 0,
            progress, 16);
    }
}
