package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only: while the local player has {@link FUREffectRegistry#SPOREROT}, replace the vanilla
 * hunger bar with an "infected" drumstick set.
 *
 * <p>The layout mirrors {@code ForgeGui#renderFood} (1.20.1) — same anchor, right-to-left icon loop,
 * empty slot drawn as backdrop with a full/half drumstick layered on top, and the saturation-zero
 * jitter. The sprites, however, are three standalone 9x9 textures ({@link #FOOD_EMPTY} / {@link #FOOD_FULL}
 * / {@link #FOOD_HALF}) blitted at full size, rather than regions of the vanilla icons.png atlas. The
 * vanilla hunger-effect texture swap is dropped: this infected set is the bar's only appearance while
 * Sporerot is active.
 */
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FURFoodOverlayHandler {

    private static final ResourceLocation FOOD_EMPTY = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/infected_food_empty.png");
    private static final ResourceLocation FOOD_FULL = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/infected_food_full.png");
    private static final ResourceLocation FOOD_HALF = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/infected_food_half.png");

    /** Local jitter source, reseeded per frame from the gui tick like vanilla does with its own field. */
    private static final RandomSource RANDOM = RandomSource.create();

    @SubscribeEvent
    public static void onRenderFoodOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() != VanillaGuiOverlay.FOOD_LEVEL.type()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        // Not afflicted (or nothing to draw): let vanilla render the normal hunger bar.
        if (player == null || !player.hasEffect(FUREffectRegistry.SPOREROT.get())) {
            return;
        }

        // Replicate ForgeGui's FOOD_LEVEL guards so we only take over when vanilla would actually draw
        // food (not mounted on a health-bearing vehicle, gui shown, survival-like game mode).
        Entity vehicle = player.getVehicle();
        boolean isMounted = vehicle != null && vehicle.showVehicleHealth();
        boolean survivalElements = mc.gameMode.canHurtPlayer() && mc.getCameraEntity() instanceof Player;
        if (isMounted || mc.options.hideGui || !survivalElements || !(mc.gui instanceof ForgeGui gui)) {
            return;
        }

        event.setCanceled(true);
        renderInfectedFood(event, mc, player, gui);
    }

    private static void renderInfectedFood(RenderGuiOverlayEvent.Pre event, Minecraft mc, Player player, ForgeGui gui) {
        RenderSystem.enableBlend();

        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        int left = width / 2 + 91;
        int top = height - gui.rightHeight;
        // Advance the shared right-column cursor exactly like ForgeGui#renderFood, so overlays drawn
        // after food (e.g. air bubbles) keep their vanilla vertical position even though we cancelled it.
        gui.rightHeight += 10;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        FoodData stats = player.getFoodData();
        int level = stats.getFoodLevel();
        int tickCount = mc.gui.getGuiTicks();
        RANDOM.setSeed((long) tickCount * 312871L);

        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;

            if (stats.getSaturationLevel() <= 0.0F && tickCount % (level * 3 + 1) == 0) {
                y = top + (RANDOM.nextInt(3) - 1);
            }

            // Empty slot is the backdrop for all ten icons; the full/half drumstick layers on top.
            guiGraphics.blit(FOOD_EMPTY, x, y, 0.0F, 0.0F, 9, 9, 9, 9);

            if (idx < level) {
                guiGraphics.blit(FOOD_FULL, x, y, 0.0F, 0.0F, 9, 9, 9, 9);
            } else if (idx == level) {
                guiGraphics.blit(FOOD_HALF, x, y, 0.0F, 0.0F, 9, 9, 9, 9);
            }
        }
    }
}
