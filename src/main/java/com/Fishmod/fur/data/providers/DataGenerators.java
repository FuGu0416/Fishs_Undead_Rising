package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		PackOutput packOutput = gen.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		gen.addProvider(event.includeServer(), new FURBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
		gen.addProvider(event.includeServer(), new FURStructureTagsProvider(packOutput, lookupProvider, existingFileHelper));
		gen.addProvider(event.includeServer(), new ModDatapackBuiltinEntriesProvider(packOutput, lookupProvider));
	}
}
