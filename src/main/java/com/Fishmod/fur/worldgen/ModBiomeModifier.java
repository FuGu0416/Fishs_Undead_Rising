package com.Fishmod.fur.worldgen;

import java.util.List;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.data.providers.ModBiomeTags;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifier {
	public static final ResourceKey<BiomeModifier> ADD_FOGLET = registerKey("add_foglet");
	public static final ResourceKey<BiomeModifier> ADD_ISNACHI = registerKey("add_isnachi");
	public static final ResourceKey<BiomeModifier> ADD_IMP = registerKey("add_imp");
	
    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(mod_LavaCow.MODID, name));
    }
    
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        
        addSpawn(context, ADD_FOGLET, biomes.getOrThrow(ModBiomeTags.HAS_FOGLET),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.FOGLET.get(), 20, 8, 16));
        addSpawn(context, ADD_ISNACHI, biomes.getOrThrow(ModBiomeTags.HAS_ISNACHI),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.ISNACHI.get(), 20, 8, 16));
        addSpawn(context, ADD_IMP, HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.IMP.get(), 3, 8, 16));
    }
    
    private static void addSpawn(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> resourceName, HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        context.register(resourceName, new AddSpawnsBiomeModifier(biomes, List.of(spawns)));
    }
}
