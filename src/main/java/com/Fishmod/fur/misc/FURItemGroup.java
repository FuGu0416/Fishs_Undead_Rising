package com.Fishmod.fur.misc;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FURItemGroup {
	public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, mod_LavaCow.MODID);

	public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.fur.tab"))
            .icon(() -> new ItemStack(FURItemRegistry.UNDYINGHEART.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((params, output) -> {
            	output.accept(FURItemRegistry.SHARP_FANG.get());
            	output.accept(FURItemRegistry.CHITIN.get());
            	output.accept(FURItemRegistry.FOUL_BRISTLE.get());
            	output.accept(FURItemRegistry.IMP_HORN.get());
            	output.accept(FURItemRegistry.CURSED_FABRIC.get());
            	output.accept(FURItemRegistry.CURSEWEAVE_CLOTH.get());
            	output.accept(FURItemRegistry.ECTOPLASM.get());
            	output.accept(FURItemRegistry.ECTOPLASM_MASS.get());
            	output.accept(FURItemRegistry.ECTOPLASM_INGOT.get());
            	output.accept(FURItemRegistry.MOLTEN_MEAT.get());
            	output.accept(FURItemRegistry.MOLTEN_ALLOY.get());
            	output.accept(FURItemRegistry.UNDYINGHEART.get());
            	FURItemRegistry.creativeTabSpawnEggMap.forEach((spawnEgg -> output.accept(spawnEgg.get())));
            })
            .build());
}
