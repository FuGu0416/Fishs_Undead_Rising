package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		PackOutput packOutput = gen.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		FURBlockTagsProvider blockTags = new FURBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
		gen.addProvider(event.includeServer(), blockTags);
		// Must be created before BiomeTagsProvider so its extended lookup (which includes
		// custom biomes like fur:luminous_undergrove) can be passed to the tag provider.
		FURDatapackBuiltinEntriesProvider datapackEntries = new FURDatapackBuiltinEntriesProvider(packOutput, lookupProvider);
		gen.addProvider(event.includeServer(), datapackEntries);
		gen.addProvider(event.includeServer(), new FURBiomeTagsProvider(packOutput, datapackEntries.getRegistryProvider(), existingFileHelper));
		gen.addProvider(event.includeServer(), new FURStructureTagsProvider(packOutput, lookupProvider, existingFileHelper));
		gen.addProvider(event.includeServer(), new FURGlobalLootModifiersProvider(packOutput));
		gen.addProvider(event.includeServer(), new FURItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
		gen.addProvider(event.includeServer(), new FURBannerPatternTagsProvider(packOutput, lookupProvider, mod_LavaCow.MODID, existingFileHelper));
		gen.addProvider(event.includeServer(), new FUREntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
	}
}
