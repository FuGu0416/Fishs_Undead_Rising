package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceScreen;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURMenuTypesRegistry;
import com.Fishmod.fur.item.SporecallerItem;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

// Client-only: keep FML from scanning this class (and loading client-only event types like
// RegisterColorHandlersEvent.Item -> ItemColors) on the dedicated server.
@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
    	event.enqueueWork(() -> MenuScreens.register(FURMenuTypesRegistry.SOUL_FURNACE.get(), SoulFurnaceScreen::new));
    }
    
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
            (stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return PotionUtils.getColor(stack);
                }
                return 0xFFFFFFFF;
            },
            FURItemRegistry.INFUSED_BANDAGE.get()
        );

        // Sporecaller overlay (layer1 / tint index 1): tinted to the injected potion's colour, or the
        // pale-cyan default when no potion is loaded.
        event.register(
            (stack, tintIndex) -> tintIndex == 1 ? SporecallerItem.getOverlayColor(stack) : 0xFFFFFFFF,
            FURItemRegistry.SPORECALLER.get()
        );
    }
}
