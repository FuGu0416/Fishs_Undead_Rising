package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURSoundRegistry {
	public static final DeferredRegister<SoundEvent> DEF_REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, mod_LavaCow.MODID);
	
	public static final RegistryObject<SoundEvent> PARASITE_AMBIENT = addSoundEvent("parasite_ambient");
	public static final RegistryObject<SoundEvent> PARASITE_HURT = addSoundEvent("parasite_hurt");
	public static final RegistryObject<SoundEvent> PARASITE_DEATH = addSoundEvent("parasite_death");
	public static final RegistryObject<SoundEvent> PARASITE_WEAVE = addSoundEvent("parasite_weave");
	
	public static final RegistryObject<SoundEvent> FOGLET_AMBIENT = addSoundEvent("foglet_ambient");
	public static final RegistryObject<SoundEvent> FOGLET_HURT = addSoundEvent("foglet_hurt");
	public static final RegistryObject<SoundEvent> FOGLET_DEATH = addSoundEvent("foglet_death");
	
	public static final RegistryObject<SoundEvent> UNDEADSWINE_ATTACK = addSoundEvent("undeadswine_attack");
	public static final RegistryObject<SoundEvent> UNDEADSWINE_CHARGE = addSoundEvent("undeadswine_charge");
	public static final RegistryObject<SoundEvent> UNDEADSWINE_HURT = addSoundEvent("undeadswine_hurt");
	public static final RegistryObject<SoundEvent> UNDEADSWINE_DEATH = addSoundEvent("undeadswine_death");
	
	public static final RegistryObject<SoundEvent> SALAMANDER_AMBIENT = addSoundEvent("salamander_ambient");
	public static final RegistryObject<SoundEvent> SALAMANDER_ATTACK = addSoundEvent("salamander_attack");
	public static final RegistryObject<SoundEvent> SALAMANDER_ATTACK_RANGE = addSoundEvent("salamander_attack_range");
	public static final RegistryObject<SoundEvent> SALAMANDER_HURT = addSoundEvent("salamander_hurt");
	public static final RegistryObject<SoundEvent> SALAMANDER_DEATH = addSoundEvent("salamander_death");
	
	public static final RegistryObject<SoundEvent> SWARMER_AMBIENT = addSoundEvent("swarmer_ambient");
	public static final RegistryObject<SoundEvent> SWARMER_ATTACK = addSoundEvent("swarmer_attack");
	public static final RegistryObject<SoundEvent> SWARMER_HURT = addSoundEvent("swarmer_hurt");
	public static final RegistryObject<SoundEvent> SWARMER_DEATH = addSoundEvent("swarmer_death");
	
	public static final RegistryObject<SoundEvent> WENDIGO_AMBIENT = addSoundEvent("wendigo_ambient");
	public static final RegistryObject<SoundEvent> WENDIGO_ATTACK = addSoundEvent("wendigo_attack");
	public static final RegistryObject<SoundEvent> WENDIGO_HURT = addSoundEvent("wendigo_hurt");
	public static final RegistryObject<SoundEvent> WENDIGO_DEATH = addSoundEvent("wendigo_death");
	
	public static final RegistryObject<SoundEvent> MIMIC_AMBIENT = addSoundEvent("mimic_ambient");
	public static final RegistryObject<SoundEvent> MIMIC_HURT = addSoundEvent("mimic_hurt");
	public static final RegistryObject<SoundEvent> MIMIC_DEATH = addSoundEvent("mimic_death");
	
	public static final RegistryObject<SoundEvent> SHROOMLORD_AMBIENT = addSoundEvent("shroomlord_ambient");
	public static final RegistryObject<SoundEvent> SHROOMLORD_ATTACK = addSoundEvent("shroomlord_attack");
	public static final RegistryObject<SoundEvent> SHROOMLORD_HURT = addSoundEvent("shroomlord_hurt");
	public static final RegistryObject<SoundEvent> SHROOMLORD_DEATH = addSoundEvent("shroomlord_death");
	
	public static final RegistryObject<SoundEvent> SHROOMLING_AMBIENT = addSoundEvent("shroomling_ambient");
	public static final RegistryObject<SoundEvent> SHROOMLING_HURT = addSoundEvent("shroomling_hurt");
	public static final RegistryObject<SoundEvent> SHROOMLING_DEATH = addSoundEvent("shroomling_death");
	
	public static final RegistryObject<SoundEvent> RAVEN_AMBIENT = addSoundEvent("raven_ambient");
	public static final RegistryObject<SoundEvent> RAVEN_CALL = addSoundEvent("raven_call");
	public static final RegistryObject<SoundEvent> RAVEN_HURT = addSoundEvent("raven_hurt");
	public static final RegistryObject<SoundEvent> RAVEN_DEATH = addSoundEvent("raven_death");
	
	public static final RegistryObject<SoundEvent> PTERA_AMBIENT = addSoundEvent("ptera_ambient");
	public static final RegistryObject<SoundEvent> PTERA_HURT = addSoundEvent("ptera_hurt");
	public static final RegistryObject<SoundEvent> PTERA_DEATH = addSoundEvent("ptera_death");
	
	public static final RegistryObject<SoundEvent> VESPA_AMBIENT = addSoundEvent("vespa_ambient");
	public static final RegistryObject<SoundEvent> VESPA_HURT = addSoundEvent("vespa_hurt");
	public static final RegistryObject<SoundEvent> VESPA_DEATH = addSoundEvent("vespa_death");
	public static final RegistryObject<SoundEvent> VESPA_FLYING = addSoundEvent("vespa_flying");
	
	public static final RegistryObject<SoundEvent> SCARECROW_AMBIENT = addSoundEvent("scarecrow_ambient");
	public static final RegistryObject<SoundEvent> SCARECROW_HURT = addSoundEvent("scarecrow_hurt");
	public static final RegistryObject<SoundEvent> SCARECROW_DEATH = addSoundEvent("scarecrow_death");
	
	public static final RegistryObject<SoundEvent> UNBURIED_AMBIENT = addSoundEvent("unburied_ambient");
	public static final RegistryObject<SoundEvent> UNBURIED_HURT = addSoundEvent("unburied_hurt");
	public static final RegistryObject<SoundEvent> UNBURIED_DEATH = addSoundEvent("unburied_death");
	
	public static final RegistryObject<SoundEvent> BONEWORM_AMBIENT = addSoundEvent("boneworm_ambient");
	public static final RegistryObject<SoundEvent> BONEWORM_ATTACK = addSoundEvent("boneworm_attack");
	public static final RegistryObject<SoundEvent> BONEWORM_BURROW = addSoundEvent("boneworm_burrow");
	public static final RegistryObject<SoundEvent> BONEWORM_HURT = addSoundEvent("boneworm_hurt");
	public static final RegistryObject<SoundEvent> BONEWORM_DEATH = addSoundEvent("boneworm_death");
	
	public static final RegistryObject<SoundEvent> PINGU_AMBIENT = addSoundEvent("pingu_ambient");
	public static final RegistryObject<SoundEvent> PINGU_HURT = addSoundEvent("pingu_hurt");
	public static final RegistryObject<SoundEvent> PINGU_DEATH = addSoundEvent("pingu_death");
	
	public static final RegistryObject<SoundEvent> UNDERTAKER_AMBIENT = addSoundEvent("undertaker_ambient");
	public static final RegistryObject<SoundEvent> UNDERTAKER_HURT = addSoundEvent("undertaker_hurt");
	public static final RegistryObject<SoundEvent> UNDERTAKER_DEATH = addSoundEvent("undertaker_death");
	
	public static final RegistryObject<SoundEvent> VOID_GLIDER_AMBIENT = addSoundEvent("void_glider_ambient");
	// No VOID_GLIDER_HURT - matches 1.16.5 Ghost Ray, which used BANSHEE_HURT for its own hurt sound.
	public static final RegistryObject<SoundEvent> VOID_GLIDER_DEATH = addSoundEvent("void_glider_death");
	
	public static final RegistryObject<SoundEvent> BANSHEE_AMBIENT = addSoundEvent("banshee_ambient");
	public static final RegistryObject<SoundEvent> BANSHEE_ATTACK = addSoundEvent("banshee_attack");
	public static final RegistryObject<SoundEvent> BANSHEE_HURT = addSoundEvent("banshee_hurt");
	public static final RegistryObject<SoundEvent> BANSHEE_DEATH = addSoundEvent("banshee_death");
	
	public static final RegistryObject<SoundEvent> WETA_AMBIENT = addSoundEvent("weta_ambient");
	public static final RegistryObject<SoundEvent> WETA_HURT = addSoundEvent("weta_hurt");
	public static final RegistryObject<SoundEvent> WETA_DEATH = addSoundEvent("weta_death");
	
	public static final RegistryObject<SoundEvent> AVATON_AMBIENT = addSoundEvent("avaton_ambient");
	public static final RegistryObject<SoundEvent> AVATON_SPELL = addSoundEvent("avaton_spell");
	public static final RegistryObject<SoundEvent> AVATON_DEATH = addSoundEvent("avaton_death");
	
	public static final RegistryObject<SoundEvent> SKELETONKING_ATTACK = addSoundEvent("skeletonking_attack");
	public static final RegistryObject<SoundEvent> SKELETONKING_HURT = addSoundEvent("skeletonking_hurt");
	public static final RegistryObject<SoundEvent> SKELETONKING_DEATH = addSoundEvent("skeletonking_death");
	public static final RegistryObject<SoundEvent> SKELETONKING_SPELL_SUMMON = addSoundEvent("skeletonking_spell_summon");
	public static final RegistryObject<SoundEvent> SKELETONKING_SPELL_TELEPORT = addSoundEvent("skeletonking_spell_teleport");
	public static final RegistryObject<SoundEvent> SKELETONKING_SPELL_TOSS = addSoundEvent("skeletonking_spell_toss");
	public static final RegistryObject<SoundEvent> SKELETONKING_SPAWN = addSoundEvent("skeletonking_spawn");
	
	public static final RegistryObject<SoundEvent> CACTYRANT_AMBIENT = addSoundEvent("cactyrant_ambient");
	public static final RegistryObject<SoundEvent> CACTYRANT_DEATH = addSoundEvent("cactyrant_death");

	public static final RegistryObject<SoundEvent> SEAHAG_AMBIENT = addSoundEvent("seahag_ambient");
	public static final RegistryObject<SoundEvent> SEAHAG_DEATH = addSoundEvent("seahag_death");

	public static final RegistryObject<SoundEvent> WRAITH_AMBIENT = addSoundEvent("wraith_ambient");
	public static final RegistryObject<SoundEvent> WRAITH_ATTACK = addSoundEvent("wraith_attack");
	public static final RegistryObject<SoundEvent> WRAITH_DEATH = addSoundEvent("wraith_death");
	/** wraith1 variant voice: reuses vanilla Evoker/Pillager audio (via sounds.json "event" aliasing)
	 *  under our own subtitle keys, so it doesn't misleadingly show "Evoker mumbles" in subtitles. */
	public static final RegistryObject<SoundEvent> WRAITH_AMBIENT_VARIANT1 = addSoundEvent("wraith_ambient_variant1");
	public static final RegistryObject<SoundEvent> WRAITH_ATTACK_VARIANT1 = addSoundEvent("wraith_attack_variant1");
	
	public static final RegistryObject<SoundEvent> SCARAB_AMBIENT = addSoundEvent("scarab_ambient");
	public static final RegistryObject<SoundEvent> SCARAB_HURT = addSoundEvent("scarab_hurt");
	public static final RegistryObject<SoundEvent> SCARAB_DEATH = addSoundEvent("scarab_death");

	public static final RegistryObject<SoundEvent> BEELZEBUB_AMBIENT = addSoundEvent("beelzebub_ambient");
	public static final RegistryObject<SoundEvent> BEELZEBUB_SPELL = addSoundEvent("beelzebub_spell");
	public static final RegistryObject<SoundEvent> BEELZEBUB_HURT = addSoundEvent("beelzebub_hurt");
	public static final RegistryObject<SoundEvent> BEELZEBUB_DEATH = addSoundEvent("beelzebub_death");

	public static final RegistryObject<SoundEvent> ENIGMOTH_AMBIENT = addSoundEvent("enigmoth_ambient");
	public static final RegistryObject<SoundEvent> ENIGMOTH_FLAP = addSoundEvent("enigmoth_flap");
	public static final RegistryObject<SoundEvent> ENIGMOTH_HURT = addSoundEvent("enigmoth_hurt");
	public static final RegistryObject<SoundEvent> ENIGMOTH_DEATH = addSoundEvent("enigmoth_death");

	public static final RegistryObject<SoundEvent> RANDOM_FRUIT_PLANT = addSoundEvent("random_fruit_plant");
	public static final RegistryObject<SoundEvent> RANDOM_PIRANHA_SHOOT = addSoundEvent("random_piranha_shoot");
	public static final RegistryObject<SoundEvent> RANDOM_THORN_SHOOT = addSoundEvent("random_thorn_shoot");
	public static final RegistryObject<SoundEvent> RANDOM_FANG_DAGGER_HIT = addSoundEvent("random_fang_dagger_hit");
	public static final RegistryObject<SoundEvent> RANDOM_FANG_DAGGER_THROW = addSoundEvent("random_fang_dagger_throw");
	public static final RegistryObject<SoundEvent> RANDOM_MOLTEN_GLOB_IMPACT = addSoundEvent("random_molten_glob_impact");
	public static final RegistryObject<SoundEvent> RANDOM_BEASTCALL_HORN_BLOW = addSoundEvent("random_beastcall_horn_blow");
	public static final RegistryObject<SoundEvent> SPIRIT_FORM_TRIGGERED = addSoundEvent("spirit_form_triggered");
	
	public static final RegistryObject<SoundEvent> LAMPREY_AMBIENT = addSoundEvent("lamprey_ambient");
	public static final RegistryObject<SoundEvent> LAMPREY_HURT = addSoundEvent("lamprey_hurt");
	public static final RegistryObject<SoundEvent> LAMPREY_DEATH = addSoundEvent("lamprey_death");

	public static final RegistryObject<SoundEvent> GHOUL_AMBIENT = addSoundEvent("ghoul_ambient");
	public static final RegistryObject<SoundEvent> GHOUL_HURT = addSoundEvent("ghoul_hurt");
	public static final RegistryObject<SoundEvent> GHOUL_DEATH = addSoundEvent("ghoul_death");

	public static final RegistryObject<SoundEvent> MUMMY_LORD_AMBIENT = addSoundEvent("mummy_lord_ambient");
	public static final RegistryObject<SoundEvent> MUMMY_LORD_HURT = addSoundEvent("mummy_lord_hurt");
	public static final RegistryObject<SoundEvent> MUMMY_LORD_DEATH = addSoundEvent("mummy_lord_death");

    private static RegistryObject<SoundEvent> addSoundEvent(final String soundName) {
        return DEF_REG.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(mod_LavaCow.MODID, soundName)));
    }	
}

