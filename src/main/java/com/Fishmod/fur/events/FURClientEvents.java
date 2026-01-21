package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceScreen;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURMenuTypesRegistry;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID)
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
    }
}
