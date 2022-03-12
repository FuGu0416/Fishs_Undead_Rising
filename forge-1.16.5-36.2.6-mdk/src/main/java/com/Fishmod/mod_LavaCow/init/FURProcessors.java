package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.world.gen.processor.DesertTombProcessor;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class FURProcessors {
	public static IStructureProcessorType<DesertTombProcessor> DESERTTOMBPROCESSOR = () -> DesertTombProcessor.CODEC;
	public static void registerProcessors() {
		Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(mod_LavaCow.MODID, "desert_tomb_processor"), DESERTTOMBPROCESSOR);
	}
}
