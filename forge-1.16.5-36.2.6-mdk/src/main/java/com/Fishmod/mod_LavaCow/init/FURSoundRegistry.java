package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURSoundRegistry {
	public static final SoundEvent PARASITE_AMBIENT = addSoundEvent("parasite_ambient");
	public static final SoundEvent PARASITE_HURT = addSoundEvent("parasite_hurt");
	public static final SoundEvent PARASITE_DEATH = addSoundEvent("parasite_death");
	public static final SoundEvent PARASITE_WEAVE = addSoundEvent("parasite_weave");
	
	public static final SoundEvent FOGLET_AMBIENT = addSoundEvent("foglet_ambient");
	public static final SoundEvent FOGLET_HURT = addSoundEvent("foglet_hurt");
	public static final SoundEvent FOGLET_DEATH = addSoundEvent("foglet_death");
	
	public static final SoundEvent UNDEADSWINE_ATTACK = addSoundEvent("undeadswine_attack");
	public static final SoundEvent UNDEADSWINE_CHARGE = addSoundEvent("undeadswine_charge");
	public static final SoundEvent UNDEADSWINE_HURT = addSoundEvent("undeadswine_hurt");
	public static final SoundEvent UNDEADSWINE_DEATH = addSoundEvent("undeadswine_death");
	
	public static final SoundEvent SALAMANDER_AMBIENT = addSoundEvent("salamander_ambient");
	public static final SoundEvent SALAMANDER_HURT = addSoundEvent("salamander_hurt");
	public static final SoundEvent SALAMANDER_DEATH = addSoundEvent("salamander_death");
	
	public static final SoundEvent SWARMER_AMBIENT = addSoundEvent("swarmer_ambient");
	public static final SoundEvent SWARMER_ATTACK = addSoundEvent("swarmer_attack");
	public static final SoundEvent SWARMER_HURT = addSoundEvent("swarmer_hurt");
	public static final SoundEvent SWARMER_DEATH = addSoundEvent("swarmer_death");
	
	public static final SoundEvent WENDIGO_AMBIENT = addSoundEvent("wendigo_ambient");
	public static final SoundEvent WENDIGO_ATTACK = addSoundEvent("wendigo_attack");
	public static final SoundEvent WENDIGO_HURT = addSoundEvent("wendigo_hurt");
	public static final SoundEvent WENDIGO_DEATH = addSoundEvent("wendigo_death");
	
	public static final SoundEvent MIMIC_AMBIENT = addSoundEvent("mimic_ambient");
	public static final SoundEvent MIMIC_HURT = addSoundEvent("mimic_hurt");
	public static final SoundEvent MIMIC_DEATH = addSoundEvent("mimic_death");
	
	public static final SoundEvent SLUDGELORD_AMBIENT = addSoundEvent("sludgelord_ambient");
	public static final SoundEvent SLUDGELORD_ATTACK = addSoundEvent("sludgelord_attack");
	public static final SoundEvent SLUDGELORD_HURT = addSoundEvent("sludgelord_hurt");
	public static final SoundEvent SLUDGELORD_DEATH = addSoundEvent("sludgelord_death");
	
	public static final SoundEvent LILSLUDGE_AMBIENT = addSoundEvent("lilsludge_ambient");
	public static final SoundEvent LILSLUDGE_HURT = addSoundEvent("lilsludge_hurt");
	public static final SoundEvent LILSLUDGE_DEATH = addSoundEvent("lilsludge_death");
	
	public static final SoundEvent RAVEN_AMBIENT = addSoundEvent("raven_ambient");
	public static final SoundEvent RAVEN_CALL = addSoundEvent("raven_call");
	public static final SoundEvent RAVEN_HURT = addSoundEvent("raven_hurt");
	public static final SoundEvent RAVEN_DEATH = addSoundEvent("raven_death");
	
	public static final SoundEvent PTERA_AMBIENT = addSoundEvent("ptera_ambient");
	public static final SoundEvent PTERA_HURT = addSoundEvent("ptera_hurt");
	public static final SoundEvent PTERA_DEATH = addSoundEvent("ptera_death");
	
	public static final SoundEvent VESPA_AMBIENT = addSoundEvent("vespa_ambient");
	public static final SoundEvent VESPA_HURT = addSoundEvent("vespa_hurt");
	public static final SoundEvent VESPA_DEATH = addSoundEvent("vespa_death");
	public static final SoundEvent VESPA_FLYING = addSoundEvent("vespa_flying");
	
	public static final SoundEvent SCARECROW_AMBIENT = addSoundEvent("scarecrow_ambient");
	public static final SoundEvent SCARECROW_HURT = addSoundEvent("scarecrow_hurt");
	public static final SoundEvent SCARECROW_DEATH = addSoundEvent("scarecrow_death");
	
	public static final SoundEvent UNBURIED_AMBIENT = addSoundEvent("unburied_ambient");
	public static final SoundEvent UNBURIED_HURT = addSoundEvent("unburied_hurt");
	public static final SoundEvent UNBURIED_DEATH = addSoundEvent("unburied_death");
	
	public static final SoundEvent BONEWORM_AMBIENT = addSoundEvent("boneworm_ambient");
	public static final SoundEvent BONEWORM_ATTACK = addSoundEvent("boneworm_attack");
	public static final SoundEvent BONEWORM_BURROW = addSoundEvent("boneworm_burrow");
	public static final SoundEvent BONEWORM_HURT = addSoundEvent("boneworm_hurt");
	public static final SoundEvent BONEWORM_DEATH = addSoundEvent("boneworm_death");
	
	public static final SoundEvent SEAGULL_AMBIENT = addSoundEvent("seagull_ambient");
	public static final SoundEvent SEAGULL_HURT = addSoundEvent("seagull_hurt");
	public static final SoundEvent SEAGULL_DEATH = addSoundEvent("seagull_death");
	
	public static final SoundEvent PINGU_AMBIENT = addSoundEvent("pingu_ambient");
	public static final SoundEvent PINGU_HURT = addSoundEvent("pingu_hurt");
	public static final SoundEvent PINGU_DEATH = addSoundEvent("pingu_death");
	
	public static final SoundEvent UNDERTAKER_AMBIENT = addSoundEvent("undertaker_ambient");
	public static final SoundEvent UNDERTAKER_HURT = addSoundEvent("undertaker_hurt");
	public static final SoundEvent UNDERTAKER_DEATH = addSoundEvent("undertaker_death");
	
	public static final SoundEvent GHOSTRAY_AMBIENT = addSoundEvent("ghostray_ambient");
	public static final SoundEvent GHOSTRAY_HURT = addSoundEvent("ghostray_hurt");
	public static final SoundEvent GHOSTRAY_DEATH = addSoundEvent("ghostray_death");
	
	public static final SoundEvent BANSHEE_AMBIENT = addSoundEvent("banshee_ambient");
	public static final SoundEvent BANSHEE_ATTACK = addSoundEvent("banshee_attack");
	public static final SoundEvent BANSHEE_HURT = addSoundEvent("banshee_hurt");
	public static final SoundEvent BANSHEE_DEATH = addSoundEvent("banshee_death");
	
	public static final SoundEvent WETA_AMBIENT = addSoundEvent("weta_ambient");
	public static final SoundEvent WETA_HURT = addSoundEvent("weta_hurt");
	public static final SoundEvent WETA_DEATH = addSoundEvent("weta_death");
	
	public static final SoundEvent AVATON_AMBIENT = addSoundEvent("avaton_ambient");
	public static final SoundEvent AVATON_SPELL = addSoundEvent("avaton_spell");
	public static final SoundEvent AVATON_DEATH = addSoundEvent("avaton_death");
	
	public static final SoundEvent SKELETONKING_ATTACK = addSoundEvent("skeletonking_attack");
	public static final SoundEvent SKELETONKING_HURT = addSoundEvent("skeletonking_hurt");
	public static final SoundEvent SKELETONKING_DEATH = addSoundEvent("skeletonking_death");
	public static final SoundEvent SKELETONKING_SPELL_SUMMON = addSoundEvent("skeletonking_spell_summon");
	public static final SoundEvent SKELETONKING_SPELL_TELEPORT = addSoundEvent("skeletonking_spell_teleport");
	public static final SoundEvent SKELETONKING_SPELL_TOSS = addSoundEvent("skeletonking_spell_toss");
	public static final SoundEvent SKELETONKING_SPAWN = addSoundEvent("skeletonking_spawn");
	
	public static final SoundEvent CACTYRANT_AMBIENT = addSoundEvent("cactyrant_ambient");
	public static final SoundEvent CACTYRANT_DEATH = addSoundEvent("cactyrant_death");

	public static final SoundEvent SEAHAG_AMBIENT = addSoundEvent("seahag_ambient");
	public static final SoundEvent SEAHAG_DEATH = addSoundEvent("seahag_death");

	public static final SoundEvent WRAITH_AMBIENT = addSoundEvent("wraith_ambient");
	public static final SoundEvent WRAITH_DEATH = addSoundEvent("wraith_death");
	
	public static final SoundEvent SCARAB_AMBIENT = addSoundEvent("scarab_ambient");
	public static final SoundEvent SCARAB_HURT = addSoundEvent("scarab_hurt");
	public static final SoundEvent SCARAB_DEATH = addSoundEvent("scarab_death");

	public static final SoundEvent BEELZEBUB_AMBIENT = addSoundEvent("beelzebub_ambient");
	public static final SoundEvent BEELZEBUB_SPELL = addSoundEvent("beelzebub_spell");
	public static final SoundEvent BEELZEBUB_HURT = addSoundEvent("beelzebub_hurt");
	public static final SoundEvent BEELZEBUB_DEATH = addSoundEvent("beelzebub_death");

	public static final SoundEvent ENIGMOTH_AMBIENT = addSoundEvent("enigmoth_ambient");
	public static final SoundEvent ENIGMOTH_FLAP = addSoundEvent("enigmoth_flap");
	public static final SoundEvent ENIGMOTH_HURT = addSoundEvent("enigmoth_hurt");
	public static final SoundEvent ENIGMOTH_DEATH = addSoundEvent("enigmoth_death");

	public static final SoundEvent RANDOM_FRUIT_PLANT = addSoundEvent("random_fruit_plant");
	public static final SoundEvent RANDOM_PIRANHA_SHOOT = addSoundEvent("random_piranha_shoot");
	public static final SoundEvent RANDOM_THORN_SHOOT = addSoundEvent("random_thorn_shoot");
	
    private static SoundEvent addSoundEvent(final String source) {
        final ResourceLocation sound_ID = new ResourceLocation(mod_LavaCow.MODID, source);
        return new SoundEvent(sound_ID).setRegistryName(sound_ID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : FURSoundRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }	
}

