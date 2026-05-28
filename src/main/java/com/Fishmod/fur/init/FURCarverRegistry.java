package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.worldgen.carver.FURLuminousGrottoCarver;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURCarverRegistry {

    public static final DeferredRegister<WorldCarver<?>> DEF_REG =
        DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, mod_LavaCow.MODID);

    public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> LUMINOUS_GROTTO =
        DEF_REG.register("luminous_grotto",
            () -> new FURLuminousGrottoCarver(CaveCarverConfiguration.CODEC));
}
