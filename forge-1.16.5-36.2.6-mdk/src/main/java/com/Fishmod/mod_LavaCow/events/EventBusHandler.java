package com.Fishmod.mod_LavaCow.events;

import javax.annotation.Nonnull;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.events.loot.SmeltLootModifier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusHandler {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
                new SmeltLootModifier.Serializer().setRegistryName(new ResourceLocation(mod_LavaCow.MODID, "moltenaxe_smelt")),
                new SmeltLootModifier.Serializer().setRegistryName(new ResourceLocation(mod_LavaCow.MODID, "soulfirenaxe_smelt"))
        );
    }
}
