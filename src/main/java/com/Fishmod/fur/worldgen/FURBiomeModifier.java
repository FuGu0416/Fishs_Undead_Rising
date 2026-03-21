package com.Fishmod.fur.worldgen;

import java.util.List;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class FURBiomeModifier {
	public static final ResourceKey<BiomeModifier> ADD_FOGLET = registerKey("add_foglet");
	public static final ResourceKey<BiomeModifier> ADD_ISNACHI = registerKey("add_isnachi");
	public static final ResourceKey<BiomeModifier> ADD_IMP = registerKey("add_imp");
	public static final ResourceKey<BiomeModifier> ADD_SEAHAG = registerKey("add_seahag");
	public static final ResourceKey<BiomeModifier> ADD_PIRANHA = registerKey("add_piranha");
	public static final ResourceKey<BiomeModifier> ADD_SWARMER = registerKey("add_swarmer");
	public static final ResourceKey<BiomeModifier> ADD_CACTYRANT = registerKey("add_cactyrant");
	public static final ResourceKey<BiomeModifier> ADD_WENDIGO = registerKey("add_wendigo");
	public static final ResourceKey<BiomeModifier> ADD_SCARECROW = registerKey("add_scarecrow");
	public static final ResourceKey<BiomeModifier> ADD_WETA = registerKey("add_weta");
	public static final ResourceKey<BiomeModifier> ADD_AVATON = registerKey("add_avaton");
	public static final ResourceKey<BiomeModifier> ADD_WRAITH = registerKey("add_wraith");
	public static final ResourceKey<BiomeModifier> ADD_WISP = registerKey("add_wisp");
	public static final ResourceKey<BiomeModifier> ADD_MYCOSIS = registerKey("add_mycosis");
	public static final ResourceKey<BiomeModifier> ADD_FRIGID = registerKey("add_frigid");
	public static final ResourceKey<BiomeModifier> ADD_UNDERTAKER = registerKey("add_undertaker");
	public static final ResourceKey<BiomeModifier> ADD_BANSHEE = registerKey("add_banshee");
	public static final ResourceKey<BiomeModifier> ADD_CACTOID = registerKey("add_cactoid");
	public static final ResourceKey<BiomeModifier> ADD_PTERA = registerKey("add_ptera");
	public static final ResourceKey<BiomeModifier> ADD_SALAMANDER = registerKey("add_salamander");
	public static final ResourceKey<BiomeModifier> ADD_ENIGMOTH = registerKey("add_enigmoth");
	public static final ResourceKey<BiomeModifier> ADD_BONE_TROUT = registerKey("add_bone_trout");
	public static final ResourceKey<BiomeModifier> ADD_MUMMIFIED_COD = registerKey("add_mummified_cod");
	
    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(mod_LavaCow.MODID, name));
    }
    
    public static void bootstrap (BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        
        addSpawn(context, ADD_FOGLET, biomes.getOrThrow(FURTagRegistry.HAS_FOGLET),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.FOGLET.get(), 20, 8, 16));
        addSpawn(context, ADD_ISNACHI, biomes.getOrThrow(FURTagRegistry.HAS_ISNACHI),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.ISNACHI.get(), 20, 8, 16));
        addSpawn(context, ADD_IMP, HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.IMP.get(), 3, 8, 16));
        addSpawn(context, ADD_SEAHAG, biomes.getOrThrow(BiomeTags.IS_BEACH),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SEAHAG.get(), 20, 1, 2));
        addSpawn(context, ADD_PIRANHA, biomes.getOrThrow(FURTagRegistry.HAS_PIRANHA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.PIRANHA.get(), 15, 4, 8));        
        addSpawn(context, ADD_SWARMER, biomes.getOrThrow(FURTagRegistry.HAS_SWARMER),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SWARMER.get(), 15, 4, 8));
        addSpawn(context, ADD_CACTYRANT, biomes.getOrThrow(FURTagRegistry.HAS_CACTYRANT),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.CACTYRANT.get(), 8, 1, 2));    
        addSpawn(context, ADD_WENDIGO, biomes.getOrThrow(BiomeTags.IS_TAIGA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WENDIGO.get(), 15, 1, 1));     
        addSpawn(context, ADD_SCARECROW, biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SCARECROW.get(), 15, 1, 1));   
        addSpawn(context, ADD_WETA, biomes.getOrThrow(FURTagRegistry.HAS_WETA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WETA.get(), 30, 4, 8)); 
        addSpawn(context, ADD_AVATON, biomes.getOrThrow(BiomeTags.IS_SAVANNA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.AVATON.get(), 20, 1, 2));   
        addSpawn(context, ADD_WRAITH, biomes.getOrThrow(FURTagRegistry.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WRAITH.get(), 20, 2, 4));
        addSpawn(context, ADD_WISP, HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY), biomes.getOrThrow(Biomes.BASALT_DELTAS)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WISP.get(), 10, 4, 8));
        addSpawn(context, ADD_MYCOSIS, biomes.getOrThrow(FURTagRegistry.HAS_MYCOSIS),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.MYCOSIS.get(), 40, 8, 16)); 
        addSpawn(context, ADD_FRIGID, biomes.getOrThrow(BiomeTags.SPAWNS_SNOW_FOXES),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.FRIGID.get(), 20, 8, 16)); 
        addSpawn(context, ADD_UNDERTAKER, biomes.getOrThrow(FURTagRegistry.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.UNDERTAKER.get(), 8, 1, 1)); 
        addSpawn(context, ADD_BANSHEE, biomes.getOrThrow(FURTagRegistry.HAS_BANSHEE), 
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.BANSHEE.get(), 20, 1, 2)); 
        addSpawn(context, ADD_CACTOID, biomes.getOrThrow(FURTagRegistry.HAS_CACTOID),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.CACTOID.get(), 10, 4, 8));  
        addSpawn(context, ADD_PTERA, biomes.getOrThrow(FURTagRegistry.HAS_PTERA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.PTERA.get(), 20, 2, 4)); 
        addSpawn(context, ADD_SALAMANDER, HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SALAMANDER.get(), 10, 4, 8));
        addSpawn(context, ADD_ENIGMOTH, HolderSet.direct(biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.WARPED_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.ENIGMOTH.get(), 1, 1, 2));
        addSpawn(context, ADD_BONE_TROUT, biomes.getOrThrow(FURTagRegistry.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.BONE_TROUT.get(), 1, 3, 6));
        addSpawn(context, ADD_MUMMIFIED_COD, biomes.getOrThrow(Tags.Biomes.IS_DESERT),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.MUMMIFIED_COD.get(), 1, 3, 6));
    }
    
    private static void addSpawn(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> resourceName, HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        context.register(resourceName, new AddSpawnsBiomeModifier(biomes, List.of(spawns)));
    }
}
