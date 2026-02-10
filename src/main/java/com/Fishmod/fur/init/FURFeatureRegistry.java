package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.worldgen.feature.LargeGlowShroomFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURFeatureRegistry {
	public static final DeferredRegister<Feature<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.FEATURES, mod_LavaCow.MODID);
	
	public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_GLOWSHROOM = DEF_REG.register("huge_glowshroom", 
			() -> new LargeGlowShroomFeature(HugeMushroomFeatureConfiguration.CODEC));
}
