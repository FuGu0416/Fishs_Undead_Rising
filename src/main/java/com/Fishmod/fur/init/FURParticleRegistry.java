package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.particle.MothScaleOptions;
import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURParticleRegistry {
	public static final DeferredRegister<ParticleType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, mod_LavaCow.MODID);
	
	public static final RegistryObject<SimpleParticleType> GASTRO_ACID = DEF_REG.register("gastro_acid", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> LOCUST_SWARM = DEF_REG.register("locust_swarm", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> SLUDGE_JET = DEF_REG.register("sludge_jet", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> GHOST_FLAME = DEF_REG.register("ghost_flame", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> WITHER_FLAME = DEF_REG.register("wither_flame", () -> new SimpleParticleType(false));
public static final RegistryObject<SimpleParticleType> FEAR = DEF_REG.register("fear", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> BANSHEE_SHRIEK = DEF_REG.register("banshee_shriek", () -> new SimpleParticleType(false));

	// END_ROD-styled particle that is tinted at spawn time via RGB + scale, like redstone dust.
	public static final RegistryObject<ParticleType<MothScaleOptions>> MOTH_SCALE = DEF_REG.register("moth_scale",
		() -> new ParticleType<MothScaleOptions>(false, MothScaleOptions.DESERIALIZER) {
			public Codec<MothScaleOptions> codec() {
				return MothScaleOptions.CODEC;
			}
		});
}
