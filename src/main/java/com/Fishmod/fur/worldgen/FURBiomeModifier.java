package com.Fishmod.fur.worldgen;

import java.util.List;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.data.providers.FURBiomeTagsProvider;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURBiomesRegistry;
import com.Fishmod.fur.worldgen.feature.FURPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
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
	public static final ResourceKey<BiomeModifier> ADD_RAVEN = registerKey("add_raven");
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
	public static final ResourceKey<BiomeModifier> ADD_GHOUL = registerKey("add_ghoul");
	public static final ResourceKey<BiomeModifier> ADD_VESPA = registerKey("add_vespa");
	public static final ResourceKey<BiomeModifier> ADD_SHROOMLING = registerKey("add_shroomling");
	public static final ResourceKey<BiomeModifier> ADD_VOID_GLIDER = registerKey("add_void_glider");
	public static final ResourceKey<BiomeModifier> ADD_WARPEDFIREFLY = registerKey("add_warpedfirefly");
	public static final ResourceKey<BiomeModifier> ADD_GRAVEROBBER = registerKey("add_graverobber");
	public static final ResourceKey<BiomeModifier> ADD_BEELZEBUB = registerKey("add_beelzebub");
	public static final ResourceKey<BiomeModifier> ADD_BONE_PILE = registerKey("add_bone_pile");
	public static final ResourceKey<BiomeModifier> ADD_LUMINOUS_BAT = registerKey("add_luminous_bat");
	public static final ResourceKey<BiomeModifier> ADD_LUMINOUS_GLOW_SQUID = registerKey("add_luminous_glow_squid");

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(mod_LavaCow.MODID, name));
    }
    
    public static void bootstrap (BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        addSpawn(context, ADD_FOGLET, biomes.getOrThrow(FURBiomeTagsProvider.HAS_FOGLET),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.FOGLET.get(), 20, 8, 16));
        addSpawn(context, ADD_ISNACHI, biomes.getOrThrow(FURBiomeTagsProvider.HAS_ISNACHI),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.ISNACHI.get(), 20, 8, 16));
        addSpawn(context, ADD_IMP, HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.IMP.get(), 3, 8, 16));
        addSpawn(context, ADD_SEAHAG, biomes.getOrThrow(BiomeTags.IS_BEACH),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SEAHAG.get(), 20, 1, 2));
        addSpawn(context, ADD_PIRANHA, biomes.getOrThrow(FURBiomeTagsProvider.HAS_PIRANHA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.PIRANHA.get(), 15, 4, 8));        
        addSpawn(context, ADD_SWARMER, biomes.getOrThrow(FURBiomeTagsProvider.HAS_SWARMER),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SWARMER.get(), 15, 4, 8));
        addSpawn(context, ADD_CACTYRANT, biomes.getOrThrow(FURBiomeTagsProvider.HAS_CACTYRANT),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.CACTYRANT.get(), 8, 1, 2));    
        addSpawn(context, ADD_WENDIGO, biomes.getOrThrow(BiomeTags.IS_TAIGA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WENDIGO.get(), 15, 1, 1));     
        addSpawn(context, ADD_SCARECROW, biomes.getOrThrow(FURBiomeTagsProvider.HAS_SCARECROW),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SCARECROW.get(), 15, 1, 1));
        addSpawn(context, ADD_RAVEN, biomes.getOrThrow(FURBiomeTagsProvider.HAS_RAVEN),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.RAVEN.get(), 8, 2, 4));
        addSpawn(context, ADD_WETA, biomes.getOrThrow(FURBiomeTagsProvider.HAS_WETA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WETA.get(), 30, 4, 8)); 
        addSpawn(context, ADD_AVATON, biomes.getOrThrow(BiomeTags.IS_SAVANNA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.AVATON.get(), 20, 1, 2));   
        addSpawn(context, ADD_WRAITH, biomes.getOrThrow(FURBiomeTagsProvider.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WRAITH.get(), 20, 2, 4));
        addSpawn(context, ADD_WISP, HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY), biomes.getOrThrow(Biomes.BASALT_DELTAS)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WISP.get(), 10, 4, 8));
        addSpawn(context, ADD_MYCOSIS, biomes.getOrThrow(FURBiomeTagsProvider.HAS_MYCOSIS),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.MYCOSIS.get(), 40, 8, 16)); 
        addSpawn(context, ADD_FRIGID, biomes.getOrThrow(BiomeTags.SPAWNS_SNOW_FOXES),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.FRIGID.get(), 20, 8, 16)); 
        addSpawn(context, ADD_UNDERTAKER, biomes.getOrThrow(FURBiomeTagsProvider.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.UNDERTAKER.get(), 8, 1, 1)); 
        addSpawn(context, ADD_BANSHEE, biomes.getOrThrow(FURBiomeTagsProvider.HAS_BANSHEE), 
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.BANSHEE.get(), 20, 1, 2)); 
        addSpawn(context, ADD_CACTOID, biomes.getOrThrow(FURBiomeTagsProvider.HAS_CACTOID),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.CACTOID.get(), 10, 4, 8));  
        addSpawn(context, ADD_PTERA, biomes.getOrThrow(FURBiomeTagsProvider.HAS_PTERA),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.PTERA.get(), 20, 2, 4)); 
        addSpawn(context, ADD_SALAMANDER, HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SALAMANDER.get(), 10, 4, 8));
        addSpawn(context, ADD_ENIGMOTH, HolderSet.direct(biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.WARPED_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.ENIGMOTH.get(), 1, 1, 2));
        addSpawn(context, ADD_BONE_TROUT, biomes.getOrThrow(FURBiomeTagsProvider.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.BONE_TROUT.get(), 1, 3, 6));
        addSpawn(context, ADD_MUMMIFIED_COD, biomes.getOrThrow(Tags.Biomes.IS_DESERT),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.MUMMIFIED_COD.get(), 1, 3, 6));
        addSpawn(context, ADD_GHOUL, biomes.getOrThrow(FURBiomeTagsProvider.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.GHOUL.get(), 40, 4, 8));
        addSpawn(context, ADD_VESPA, biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.VESPA.get(), 20, 2, 4));
        addSpawn(context, ADD_SHROOMLING, HolderSet.direct(biomes.getOrThrow(FURBiomesRegistry.LUMINOUS_UNDERGROVE)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.SHROOMLING.get(), 20, 4, 8));
        // Vanilla cave ambient spawns also occur in the Luminous Undergrove, using the same
        // data as other cave biomes (Lush/Dripstone Caves): Bat + Glow Squid.
        addSpawn(context, ADD_LUMINOUS_BAT, HolderSet.direct(biomes.getOrThrow(FURBiomesRegistry.LUMINOUS_UNDERGROVE)),
                new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        addSpawn(context, ADD_LUMINOUS_GLOW_SQUID, HolderSet.direct(biomes.getOrThrow(FURBiomesRegistry.LUMINOUS_UNDERGROVE)),
                new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        addSpawn(context, ADD_VOID_GLIDER, HolderSet.direct(biomes.getOrThrow(Biomes.THE_END), biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.END_BARRENS), biomes.getOrThrow(Biomes.SMALL_END_ISLANDS)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.VOID_GLIDER.get(), 10, 1, 1));
        addSpawn(context, ADD_WARPEDFIREFLY, HolderSet.direct(biomes.getOrThrow(Biomes.WARPED_FOREST)),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.WARPEDFIREFLY.get(), 2, 4, 8));
        // 1.16.5 spawned the Grave Robber only at desert structures (pyramid / desert tomb) via
        // StructureSpawnListGatherEvent. Those structures aren't ported, so this approximates it with a
        // sparse ambient desert spawn (the Grave Robber also appears in raids as a Raider).
        addSpawn(context, ADD_GRAVEROBBER, biomes.getOrThrow(Tags.Biomes.IS_DESERT),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.GRAVEROBBER.get(), 8, 1, 1));
        // 1.16.5 spawned the Beelzebub across all non-Mushroom Overworld biomes (rate 2). Approximated
        // here by the hostile-overworld biome tag at a low weight.
        addSpawn(context, ADD_BEELZEBUB, biomes.getOrThrow(FURBiomeTagsProvider.IS_OVERWORLD_HOSTILE),
                new MobSpawnSettings.SpawnerData(FUREntityRegistry.BEELZEBUB.get(), 2, 1, 2));

        // Scatter Bone Piles across desert surfaces.
        context.register(ADD_BONE_PILE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_DESERT),
                HolderSet.direct(placedFeatures.getOrThrow(FURPlacedFeatures.BONE_PILE_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static void addSpawn(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> resourceName, HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        context.register(resourceName, new AddSpawnsBiomeModifier(biomes, List.of(spawns)));
    }
}
