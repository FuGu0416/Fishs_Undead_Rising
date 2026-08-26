package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.worldgen.structure.FURGraveyardStructure;
import com.Fishmod.fur.worldgen.structure.FURRoyalTombStructure;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FURStructureTypeRegistry {
	public static final DeferredRegister<StructureType<?>> DEF_REG = DeferredRegister.create(Registries.STRUCTURE_TYPE, mod_LavaCow.MODID);

	public static final RegistryObject<StructureType<FURRoyalTombStructure>> ROYAL_TOMB = DEF_REG.register("royal_tomb", FURStructureTypeRegistry::royalTombType);
	public static final RegistryObject<StructureType<FURGraveyardStructure>> GRAVEYARD = DEF_REG.register("graveyard", FURStructureTypeRegistry::graveyardType);

	private static StructureType<FURRoyalTombStructure> royalTombType() {
		return () -> FURRoyalTombStructure.CODEC;
	}

	private static StructureType<FURGraveyardStructure> graveyardType() {
		return () -> FURGraveyardStructure.CODEC;
	}
}
