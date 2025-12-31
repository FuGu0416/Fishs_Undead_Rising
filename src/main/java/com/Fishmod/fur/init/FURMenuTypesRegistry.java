package com.Fishmod.fur.init;

import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;

public class FURMenuTypesRegistry {
    public static final DeferredRegister<MenuType<?>> DEF_REG = DeferredRegister.create(Registries.MENU, mod_LavaCow.MODID);

    public static final Supplier<MenuType<SoulFurnaceMenu>> SOUL_FURNACE = DEF_REG.register("soul_furnace", () -> IForgeMenuType.create(SoulFurnaceMenu::new));
}
