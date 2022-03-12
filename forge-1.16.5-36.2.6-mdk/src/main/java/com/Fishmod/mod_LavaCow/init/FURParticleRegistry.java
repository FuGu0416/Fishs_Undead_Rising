package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURParticleRegistry {
	public static final BasicParticleType GASTRO_ACID = (BasicParticleType) new BasicParticleType(false).setRegistryName("mod_lavacow:gastro_acid");
	public static final BasicParticleType LOCUST_SWARM = (BasicParticleType) new BasicParticleType(false).setRegistryName("mod_lavacow:locust_swarm");
	public static final BasicParticleType SLUDGE_JET = (BasicParticleType) new BasicParticleType(false).setRegistryName("mod_lavacow:sludge_jet");
	
    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event) {
        try {
            for (Field f : FURParticleRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof ParticleType) {
                    event.getRegistry().register((ParticleType<?>) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
