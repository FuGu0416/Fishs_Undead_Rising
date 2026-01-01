package com.Fishmod.fur.block.blockentity.container;

import java.awt.Rectangle;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulFurnaceScreen extends AbstractContainerScreen<SoulFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/soul_furnace.png");
	private static final Rectangle PROGRESS_ARROW = new Rectangle(89, 25, 0, 17);
	
    public SoulFurnaceScreen(SoulFurnaceMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int progress = menu.getProgressScaled(24);
        gfx.blit(TEXTURE, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 15, progress + 1, PROGRESS_ARROW.height);
    }
}
