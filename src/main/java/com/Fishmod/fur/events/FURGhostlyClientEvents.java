package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FUREffectRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only: while the local player has {@link FUREffectRegistry#SPIRIT_FORM} (Ghostly Armor's
 * full-set death-prevention window), hide the held item so the player doesn't look armed while unable
 * to attack or use items. First-person view only — see {@link com.Fishmod.fur.item.GhostlyArmorItem}
 * for why third-person held-item hiding isn't attempted here.
 */
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FURGhostlyClientEvents {

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
            event.setCanceled(true);
        }
    }
}
