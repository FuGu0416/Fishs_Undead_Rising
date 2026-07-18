package com.Fishmod.fur.events;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.events.loot.AddItemModifier;
import com.Fishmod.fur.events.loot.SmeltLootModifier;
import com.mojang.serialization.Codec;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EventBusHandler {
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> DEF_REG = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, mod_LavaCow.MODID);

    public static final RegistryObject<Codec<AddItemModifier>> ADD_ITEM = DEF_REG.register("add_item", () -> AddItemModifier.CODEC);
    public static final RegistryObject<Codec<SmeltLootModifier>> SMELT = DEF_REG.register("molten_axe_smelt", () -> SmeltLootModifier.CODEC);

    public static void create(IEventBus bus) {
    	DEF_REG.register(bus);
    }
}
