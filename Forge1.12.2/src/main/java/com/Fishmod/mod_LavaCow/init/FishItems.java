package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityWarSmallFireball;
import com.Fishmod.mod_LavaCow.item.ItemFishCustomWeapon;
import com.Fishmod.mod_LavaCow.item.ItemCursedBandage;
import com.Fishmod.mod_LavaCow.item.ItemDreamCatcher;
import com.Fishmod.mod_LavaCow.item.ItemFamineArmor;
import com.Fishmod.mod_LavaCow.item.ItemFelArmor;
import com.Fishmod.mod_LavaCow.item.ItemFishCustom;
import com.Fishmod.mod_LavaCow.item.ItemFishCustomFood;
import com.Fishmod.mod_LavaCow.item.ItemFissionPotion;
import com.Fishmod.mod_LavaCow.item.ItemFrozenThigh;
import com.Fishmod.mod_LavaCow.item.ItemGoldenHeart;
import com.Fishmod.mod_LavaCow.item.ItemHaloNecklace;
import com.Fishmod.mod_LavaCow.item.ItemHolyGrenade;
import com.Fishmod.mod_LavaCow.item.ItemIntestine;
import com.Fishmod.mod_LavaCow.item.ItemMoltenAxe;
import com.Fishmod.mod_LavaCow.item.ItemMoltenBeef;
import com.Fishmod.mod_LavaCow.item.ItemNetherStew;
import com.Fishmod.mod_LavaCow.item.ItemPiranhaLauncher;
import com.Fishmod.mod_LavaCow.item.ItemPoisonSpore;
import com.Fishmod.mod_LavaCow.item.ItemRavenWhistle;
import com.Fishmod.mod_LavaCow.item.ItemSwineArmor;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
 
@ObjectHolder(mod_LavaCow.MODID)
public class FishItems {
	public static final Item NETHERSTEW = new ItemNetherStew("netherstew");
	public static final Item CANEBEEF = new ItemFishCustomFood("canebeef", 5, 0.5F, true, 16, true).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8*20, 0), 1.0F);
	public static final Item CANEPORK = new ItemFishCustomFood("canepork", 5, 0.5F, true, 16, true).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8*20, 0), 1.0F);
	public static final Item CANEROTTENMEAT = new ItemFishCustomFood("canerottenmeat", 5, 0.5F, true, 16, true).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8*20, 0), 1.0F);
	public static final Item FISSIONPOTION = new ItemFissionPotion("fissionpotion", SoundEvents.ENTITY_SLIME_SQUISH, EnumParticleTypes.VILLAGER_HAPPY, EnumRarity.COMMON, false);
	public static final Item HYPHAE = new ItemFishCustom("hyphae", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item PARASITE_ITEM = new ItemFishCustomFood("parasite_item", 2, 0.3F, false, 32, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30*20, 0), 0.3F);
	public static final Item PARASITE_ITEM_COOKED = new ItemFishCustomFood("parasite_item_cooked", 6, 0.6F, false, 32, false);
	public static final Item PIRANHA = new ItemFishCustomFood("piranha", 2, 0.1F, false, 32, false);
	public static final Item PIRANHA_COOKED = new ItemFishCustomFood("piranha_cooked", 6, 0.8F, false, 32, false);
	public static final Item CHEIROLEPIS = new ItemFishCustomFood("cheirolepis", 6, 0.8F, false, 32, false);
	public static final Item CHEIROLEPIS_COOKED = new ItemFishCustomFood("cheirolepis_cooked", 2, 0.1F, false, 32, false);
	public static final Item MOUSSE = new ItemFishCustomFood("mousse", 3, 0.1F, true, 32, false).setItemUseAction(EnumAction.DRINK);
	public static final Item MEATBALL = new ItemFishCustomFood("meatball", 2, 0.6F, true, 8, false);
	public static final Item SHARPTOOTH = new ItemFishCustom("sharptooth", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item BONESWORD = new ItemFishCustomWeapon("bonesword", ToolMaterial.IRON, 2.0F, -2.4F, Item.getItemFromBlock(Blocks.BONE_BLOCK), EnumRarity.COMMON);
	public static final Item INTESTINE = new ItemIntestine("intestine");
	public static final Item FROZENTHIGH = new ItemFrozenThigh("frozenthigh", 10, 1.2F, true, 128);
	public static final Item POISONSPORE = new ItemPoisonSpore("poisonspore", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item UNDYINGHEART = new ItemPoisonSpore("undyingheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item GOLDENHEART = new ItemGoldenHeart("goldenheart", mod_LavaCow.TAB_ITEMS, EnumRarity.EPIC, true);
	public static final Item MOLTENBEEF = new ItemMoltenBeef("moltenbeef");
	public static final Item MOLTENAXE = new ItemMoltenAxe("moltenaxe");
	public static final Item MOLTENHAMMER = new ItemFishCustomWeapon("moltenhammer", ToolMaterial.DIAMOND, 3.0F, -2.4F, Items.GUNPOWDER, EnumRarity.RARE);
	public static final Item MOOTENHEART = new ItemPoisonSpore("mootenheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item POTION_OF_MOOTEN_LAVA = new ItemFissionPotion("potion_of_mooten_lava", SoundEvents.ENTITY_FIREWORK_BLAST, EnumParticleTypes.LAVA, EnumRarity.EPIC, true);
	public static final Item SWINEMASK = new ItemSwineArmor("swinemask", 2, EntityEquipmentSlot.HEAD, 0.2F);
	public static final Item PLAGUED_PORKCHOP = new ItemFishCustomFood("plagued_porkchop", 3, 0.3F, true, 32, false).setPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30*20, 0), 0.8F);
	public static final Item GREEN_BACON_AND_EGGS = new ItemFishCustomFood("green_bacon_and_eggs", 10, 1.2F, true, 32, true).setPotionEffect(new PotionEffect(MobEffects.HASTE, 60*20, 1), 1.0F).setAlwaysEdible();
	public static final Item PIGBOARHIDE = new ItemFishCustom("pigboarhide", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item SCYTHE_CLAW = new ItemFishCustom("scythe_claw", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item REAPERS_SCYTHE = new ItemFishCustomWeapon("reapers_scythe", ToolMaterial.DIAMOND, 8.0F, -3.1F, FishItems.SCYTHE_CLAW, EnumRarity.RARE);
	public static final Item FAMINE = new ItemFishCustomWeapon("famine", ToolMaterial.DIAMOND, 0.0F, -1.2F, FishItems.SCYTHE_CLAW, EnumRarity.RARE);
	public static final Item MIMIC_CLAW = new ItemFishCustomFood("mimic_claw", 3, 0.3F, true, 64, false).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3*20, 0), 0.3F);
	public static final Item MIMIC_CLAW_COOKED = new ItemFishCustomFood("mimic_claw_cooked", 8, 0.8F, true, 32, false);
	public static final Item SILKY_SLUDGE = new ItemFishCustom("silky_sludge", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item MOSSY_STICK = new ItemFishCustom("mossy_stick", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item SLUDGE_WAND = new ItemFishCustomWeapon("sludge_wand", ToolMaterial.GOLD, -2.0F, -3.3F, FishItems.SILKY_SLUDGE, EnumRarity.RARE);
	public static final Item BURNTOVIPOSITOR = new ItemPoisonSpore("burntovipositor", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item WAR = new ItemPiranhaLauncher("war", Items.FIRE_CHARGE.getUnlocalizedName(), EntityWarSmallFireball.class, EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item MOLTENPAN = new ItemFishCustomWeapon("moltenpan", ToolMaterial.DIAMOND, 1.0F, -3.0F, Items.GUNPOWDER, EnumRarity.RARE).setNoRepair();
	public static final Item FELARMOR_HELMET = new ItemFelArmor("felarmor_helmet", 2, EntityEquipmentSlot.HEAD, 0.2F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_CHESTPLATE = new ItemFelArmor("felarmor_chestplate", 2, EntityEquipmentSlot.CHEST, 0.5F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_LEGGINGS = new ItemFelArmor("felarmor_leggings", 2, EntityEquipmentSlot.LEGS, 0.2F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_BOOTS = new ItemFelArmor("felarmor_boots", 2, EntityEquipmentSlot.FEET, 0.1F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item PTERA_WING = new ItemFishCustomFood("ptera_wing", 4, 0.1F, true, 32, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30*20, 2), 0.8F).setAlwaysEdible();
	public static final Item PTERA_WING_COOKED = new ItemFishCustomFood("ptera_wing_cooked", 8, 0.8F, true, 32, false);
	public static final Item POISONSTINGER = new ItemFishCustom("poisonstinger", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item VESPA_CARAPACE = new ItemFishCustom("vespa_carapace", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item TOOTH_DAGGER = new ItemFishCustomWeapon("tooth_dagger", ToolMaterial.STONE, 1.0F, -2.0F, FishItems.SHARPTOOTH, EnumRarity.COMMON).setNoDescription();
	public static final Item VESPA_DAGGER = new ItemFishCustomWeapon("vespa_dagger", ToolMaterial.IRON, 2.0F, -2.4F, FishItems.POISONSTINGER, EnumRarity.COMMON).setNoDescription();
	public static final Item SAUSAGE_ROLL = new ItemFishCustomFood("sausage_roll", 8, 0.8F, true, 32, false);
	public static final Item SWINEARMOR_CHESTPLATE = new ItemSwineArmor("swinearmor_chestplate", 2, EntityEquipmentSlot.CHEST, 0.5F);
	public static final Item SWINEARMOR_LEGGINGS = new ItemSwineArmor("swinearmor_leggings", 2, EntityEquipmentSlot.LEGS, 0.2F);
	public static final Item SWINEARMOR_BOOTS = new ItemSwineArmor("swinearmor_boots", 2, EntityEquipmentSlot.FEET, 0.1F);
	public static final Item ZOMBIEPIRANHA_ITEM = new ItemFishCustomFood("zombiepiranha_item", 2, 0.1F, false, 32, false);
	public static final Item ZOMBIEPIRANHA_ITEM_COOKED = new ItemFishCustomFood("zombiepiranha_item_cooked", 6, 0.8F, false, 32, false);
	public static final Item PIRANHALAUNCHER = new ItemPiranhaLauncher("piranhalauncher", "item." + mod_LavaCow.MODID + ".zombiepiranha_item", EntityPiranhaLauncher.class, EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item CURSED_FABRIC = new ItemFishCustom("cursed_fabric", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CURSED_BANDAGE = new ItemCursedBandage("cursed_bandage");
	public static final Item HOLY_SLUDGE = new ItemFishCustom("holy_sludge", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item HOLY_GRENADE = new ItemHolyGrenade("holy_grenade");
	public static final Item FEATHER_BLACK = new ItemFishCustom("feather_black", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item SHATTERED_ICE = new ItemFishCustomFood("shattered_ice", 1, 1.8F, false, 32, false).setItemUseAction(EnumAction.DRINK);
	public static final Item HALO_NECKLACE = new ItemHaloNecklace("halo_necklace", mod_LavaCow.TAB_ITEMS, EnumRarity.EPIC, true);
	public static final Item DREAMCATCHER = new ItemDreamCatcher("dreamcatcher", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, true);
	public static final Item UNDERTAKER_SHOVEL = new ItemFishCustomWeapon("undertaker_shovel", ToolMaterial.IRON, 2.0F, -3.0F, FishItems.UNDYINGHEART, EnumRarity.RARE);
	public static final Item RAVEN_WHISTLE = new ItemRavenWhistle("raven_whistle");
	public static final Item ACIDICHEART = new ItemPoisonSpore("acidicheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item FAMINEARMOR_HELMET = new ItemFamineArmor("faminearmor_helmet", 2, EntityEquipmentSlot.HEAD);
	public static final Item FAMINEARMOR_CHESTPLATE = new ItemFamineArmor("faminearmor_chestplate", 2, EntityEquipmentSlot.CHEST);
	public static final Item FAMINEARMOR_LEGGINGS = new ItemFamineArmor("faminearmor_leggings", 2, EntityEquipmentSlot.LEGS);
	public static final Item FAMINEARMOR_BOOTS = new ItemFamineArmor("faminearmor_boots", 2, EntityEquipmentSlot.FEET);
	public static final Item FOUL_BRISTLE = new ItemFishCustom("foul_bristle", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CRABCAKE = new ItemFishCustomFood("crabcake", 5, 0.8F, true, 32, false);
	public static final Item ECTOPLASM = new ItemFishCustom("ectoplasm", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item GHOSTJELLY = new ItemNetherStew("ghostjelly").setRandPotionEffect(null);
	public static final Item BANSHEE_VOCAL_CORD = new ItemPoisonSpore("banshee_vocal_cord", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item WETA_JAW = new ItemFishCustom("weta_jaw", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CHITIN = new ItemFishCustom("chitin", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item GHOSTBOMB = new ItemHolyGrenade("ghostbomb");
	public static final Item SONICBOMB = new ItemHolyGrenade("sonicbomb");
	
	public static final SoundEvent ENTITY_PARASITE_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.ambient")).setRegistryName("entity_parasite_ambient");
	public static final SoundEvent ENTITY_PARASITE_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.hurt")).setRegistryName("entity_parasite_hurt");
	public static final SoundEvent ENTITY_PARASITE_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.death")).setRegistryName("entity_parasite_death");
	
	public static final SoundEvent ENTITY_FOGLET_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.foglet.ambient")).setRegistryName("entity_foglet_ambient");
	public static final SoundEvent ENTITY_FOGLET_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.foglet.hurt")).setRegistryName("entity_foglet_hurt");
	public static final SoundEvent ENTITY_FOGLET_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.foglet.death")).setRegistryName("entity_foglet_death");
	
	public static final SoundEvent ENTITY_UNDEADSWINE_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undeadswine.attack")).setRegistryName("entity_undeadswine_attack");
	public static final SoundEvent ENTITY_UNDEADSWINE_CHARGE = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undeadswine.charge")).setRegistryName("entity_undeadswine_charge");
	public static final SoundEvent ENTITY_UNDEADSWINE_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undeadswine.hurt")).setRegistryName("entity_undeadswine_hurt");
	public static final SoundEvent ENTITY_UNDEADSWINE_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undeadswine.death")).setRegistryName("entity_undeadswine_death");
	
	public static final SoundEvent ENTITY_SALAMANDER_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.salamander.ambient")).setRegistryName("entity_salamander_ambient");
	public static final SoundEvent ENTITY_SALAMANDER_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.salamander.hurt")).setRegistryName("entity_salamander_hurt");
	public static final SoundEvent ENTITY_SALAMANDER_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.salamander.death")).setRegistryName("entity_salamander_death");
	
	public static final SoundEvent ENTITY_ZOMBIEPIRANHA_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiepiranha.ambient")).setRegistryName("entity_zombiepiranha_ambient");
	public static final SoundEvent ENTITY_ZOMBIEPIRANHA_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiepiranha.attack")).setRegistryName("entity_zombiepiranha_attack");
	public static final SoundEvent ENTITY_ZOMBIEPIRANHA_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiepiranha.hurt")).setRegistryName("entity_zombiepiranha_hurt");
	public static final SoundEvent ENTITY_ZOMBIEPIRANHA_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiepiranha.death")).setRegistryName("entity_zombiepiranha_death");
	
	public static final SoundEvent ENTITY_WENDIGO_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wendigo.ambient")).setRegistryName("entity_wendigo_ambient");
	public static final SoundEvent ENTITY_WENDIGO_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wendigo.attack")).setRegistryName("entity_wendigo_attack");
	public static final SoundEvent ENTITY_WENDIGO_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wendigo.hurt")).setRegistryName("entity_wendigo_hurt");
	public static final SoundEvent ENTITY_WENDIGO_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wendigo.death")).setRegistryName("entity_wendigo_death");
	
	public static final SoundEvent ENTITY_MIMIC_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.mimic.ambient")).setRegistryName("entity_mimic_ambient");
	public static final SoundEvent ENTITY_MIMIC_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.mimic.hurt")).setRegistryName("entity_mimic_hurt");
	public static final SoundEvent ENTITY_MIMIC_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.mimic.death")).setRegistryName("entity_mimic_death");
	
	public static final SoundEvent ENTITY_SLUDGELORD_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sludgelord.ambient")).setRegistryName("entity_sludgelord_ambient");
	public static final SoundEvent ENTITY_SLUDGELORD_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sludgelord.attack")).setRegistryName("entity_sludgelord_attack");
	public static final SoundEvent ENTITY_SLUDGELORD_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sludgelord.hurt")).setRegistryName("entity_sludgelord_hurt");
	public static final SoundEvent ENTITY_SLUDGELORD_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sludgelord.death")).setRegistryName("entity_sludgelord_death");
	
	public static final SoundEvent ENTITY_LILSLUDGE_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.lilsludge.ambient")).setRegistryName("entity_lilsludge_ambient");
	public static final SoundEvent ENTITY_LILSLUDGE_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.lilsludge.hurt")).setRegistryName("entity_lilsludge_hurt");
	public static final SoundEvent ENTITY_LILSLUDGE_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.lilsludge.death")).setRegistryName("entity_lilsludge_death");
	
	public static final SoundEvent ENTITY_RAVEN_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.raven.ambient")).setRegistryName("entity_raven_ambient");
	public static final SoundEvent ENTITY_RAVEN_CALL = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.raven.call")).setRegistryName("entity_raven_call");
	public static final SoundEvent ENTITY_RAVEN_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.raven.hurt")).setRegistryName("entity_raven_hurt");
	public static final SoundEvent ENTITY_RAVEN_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.raven.death")).setRegistryName("entity_raven_death");
	
	public static final SoundEvent ENTITY_PTERA_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ptera.ambient")).setRegistryName("entity_ptera_ambient");
	public static final SoundEvent ENTITY_PTERA_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ptera.hurt")).setRegistryName("entity_ptera_hurt");
	public static final SoundEvent ENTITY_PTERA_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ptera.death")).setRegistryName("entity_ptera_death");
	
	public static final SoundEvent ENTITY_VESPA_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.vespa.ambient")).setRegistryName("entity_vespa_ambient");
	public static final SoundEvent ENTITY_VESPA_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.vespa.hurt")).setRegistryName("entity_vespa_hurt");
	public static final SoundEvent ENTITY_VESPA_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.vespa.death")).setRegistryName("entity_vespa_death");
	
	public static final SoundEvent ENTITY_SCARECROW_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.ambient")).setRegistryName("entity_scarecrow_ambient");
	public static final SoundEvent ENTITY_SCARECROW_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.hurt")).setRegistryName("entity_scarecrow_hurt");
	public static final SoundEvent ENTITY_SCARECROW_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.death")).setRegistryName("entity_scarecrow_death");
	
	public static final SoundEvent ENTITY_ZOMBIEFROZEN_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiefrozen.ambient")).setRegistryName("entity_zombiefrozen_ambient");
	public static final SoundEvent ENTITY_ZOMBIEFROZEN_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiefrozen.hurt")).setRegistryName("entity_zombiefrozen_hurt");
	public static final SoundEvent ENTITY_ZOMBIEFROZEN_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.zombiefrozen.death")).setRegistryName("entity_zombiefrozen_death");
	
	public static final SoundEvent ENTITY_BONEWORM_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.boneworm.ambient")).setRegistryName("entity_boneworm_ambient");
	public static final SoundEvent ENTITY_BONEWORM_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.boneworm.attack")).setRegistryName("entity_boneworm_attack");
	public static final SoundEvent ENTITY_BONEWORM_BURROW = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.boneworm.burrow")).setRegistryName("entity_boneworm_burrow");
	public static final SoundEvent ENTITY_BONEWORM_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.boneworm.hurt")).setRegistryName("entity_boneworm_hurt");
	public static final SoundEvent ENTITY_BONEWORM_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.boneworm.death")).setRegistryName("entity_boneworm_death");
	
	public static final SoundEvent ENTITY_SEAGULL_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.seagull.ambient")).setRegistryName("entity_seagull_ambient");
	public static final SoundEvent ENTITY_SEAGULL_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.seagull.hurt")).setRegistryName("entity_seagull_hurt");
	public static final SoundEvent ENTITY_SEAGULL_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.seagull.death")).setRegistryName("entity_seagull_death");
	
	public static final SoundEvent ENTITY_PINGU_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.pingu.ambient")).setRegistryName("entity_pingu_ambient");
	public static final SoundEvent ENTITY_PINGU_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.pingu.hurt")).setRegistryName("entity_pingu_hurt");
	public static final SoundEvent ENTITY_PINGU_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.pingu.death")).setRegistryName("entity_pingu_death");
	
	public static final SoundEvent ENTITY_UNDERTAKER_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undertaker.ambient")).setRegistryName("entity_undertaker_ambient");
	public static final SoundEvent ENTITY_UNDERTAKER_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undertaker.hurt")).setRegistryName("entity_undertaker_hurt");
	public static final SoundEvent ENTITY_UNDERTAKER_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.undertaker.death")).setRegistryName("entity_undertaker_death");
	
	public static final SoundEvent ENTITY_GHOSTRAY_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ghostray.ambient")).setRegistryName("entity_ghostray_ambient");
	public static final SoundEvent ENTITY_GHOSTRAY_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ghostray.hurt")).setRegistryName("entity_ghostray_hurt");
	public static final SoundEvent ENTITY_GHOSTRAY_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.ghostray.death")).setRegistryName("entity_ghostray_death");
	
	public static final SoundEvent ENTITY_BANSHEE_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.banshee.ambient")).setRegistryName("entity_banshee_ambient");
	public static final SoundEvent ENTITY_BANSHEE_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.banshee.attack")).setRegistryName("entity_banshee_attack");
	public static final SoundEvent ENTITY_BANSHEE_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.banshee.hurt")).setRegistryName("entity_banshee_hurt");
	public static final SoundEvent ENTITY_BANSHEE_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.banshee.death")).setRegistryName("entity_banshee_death");
	
	public static final SoundEvent ENTITY_WETA_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.weta.ambient")).setRegistryName("entity_weta_ambient");
	public static final SoundEvent ENTITY_WETA_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.weta.hurt")).setRegistryName("entity_weta_hurt");
	public static final SoundEvent ENTITY_WETA_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.weta.death")).setRegistryName("entity_weta_death");
	
	public static final SoundEvent ENTITY_AVADON_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avadon.ambient")).setRegistryName("entity_avadon_ambient");
	public static final SoundEvent ENTITY_AVADON_SPELL = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avadon.spell")).setRegistryName("entity_avadon_spell");
	public static final SoundEvent ENTITY_AVADON_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avadon.death")).setRegistryName("entity_avadon_death");
}
