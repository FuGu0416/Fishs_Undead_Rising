package com.Fishmod.fur.events;

import com.Fishmod.fur.entities.flying.BeelzebubEntity;
import com.Fishmod.fur.message.MessageBeelzebubBite;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only: while riding a Beelzebub that has a prey grabbed in its mouth, the Attack key (left
 * click by default) triggers a bite on that prey instead of the rider's normal attack/swing.
 */
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FURBeelzebubClientEvents {

    @SubscribeEvent
    public static void onInteractionKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getVehicle() instanceof BeelzebubEntity beelzebub
                && beelzebub.hasGrabbedPrey()) {
            event.setCanceled(true);
            mod_LavaCow.NETWORK.sendToServer(new MessageBeelzebubBite(beelzebub.getId()));
        }
    }
}
