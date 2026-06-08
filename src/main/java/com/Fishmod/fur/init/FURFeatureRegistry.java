package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.worldgen.feature.CaveFloorSmootherFeature;
import com.Fishmod.fur.worldgen.feature.GiantGlimmercapFeature;
import com.Fishmod.fur.worldgen.feature.GrottoStreamFeature;
import com.Fishmod.fur.worldgen.feature.LargeGlowShroomFeature;
import com.Fishmod.fur.worldgen.feature.MycelialMatPatchFeature;
import com.Fishmod.fur.worldgen.feature.SmallPoolFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURFeatureRegistry {
	public static final DeferredRegister<Feature<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.FEATURES, mod_LavaCow.MODID);

	public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_GLOWSHROOM = DEF_REG.register("huge_glowshroom",
			() -> new LargeGlowShroomFeature(HugeMushroomFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_GLIMMERCAP = DEF_REG.register("huge_glimmercap",
			() -> new GiantGlimmercapFeature(HugeMushroomFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SMALL_POOL = DEF_REG.register("small_pool",
			() -> new SmallPoolFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> GROTTO_STREAM = DEF_REG.register("grotto_stream",
			() -> new GrottoStreamFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYCELIAL_MAT_PATCH = DEF_REG.register("mycelial_mat_patch",
			() -> new MycelialMatPatchFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAVE_FLOOR_SMOOTHER = DEF_REG.register("cave_floor_smoother",
			() -> new CaveFloorSmootherFeature(NoneFeatureConfiguration.CODEC));
}
