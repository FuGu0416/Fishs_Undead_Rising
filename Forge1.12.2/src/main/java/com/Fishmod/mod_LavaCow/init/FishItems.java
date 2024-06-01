package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
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
import com.Fishmod.mod_LavaCow.item.ItemBaubleTrinket;
import com.Fishmod.mod_LavaCow.item.ItemCactusFruit;
import com.Fishmod.mod_LavaCow.item.ItemChitinArmor;
import com.Fishmod.mod_LavaCow.item.ItemCrown;
import com.Fishmod.mod_LavaCow.item.ItemHolyGrenade;
import com.Fishmod.mod_LavaCow.item.ItemIntestine;
import com.Fishmod.mod_LavaCow.item.ItemMoltenAxe;
import com.Fishmod.mod_LavaCow.item.ItemMoltenBeef;
import com.Fishmod.mod_LavaCow.item.ItemNetherStew;
import com.Fishmod.mod_LavaCow.item.ItemParasite;
import com.Fishmod.mod_LavaCow.item.ItemPiranhaLauncher;
import com.Fishmod.mod_LavaCow.item.ItemRareLoot;
import com.Fishmod.mod_LavaCow.item.ItemPteraWing;
import com.Fishmod.mod_LavaCow.item.ItemRavenWhistle;
import com.Fishmod.mod_LavaCow.item.ItemSinisterWhetstone;
import com.Fishmod.mod_LavaCow.item.ItemSkeletonKingCrown;
import com.Fishmod.mod_LavaCow.item.ItemSwineArmor;
import com.Fishmod.mod_LavaCow.item.ItemVespaShield;
import com.Fishmod.mod_LavaCow.item.ItemWetaHoe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
 
@ObjectHolder(mod_LavaCow.MODID)
public class FishItems {
    public static ArmorMaterial ARMOR_FEL = EnumHelper.addArmorMaterial("armor_fel", "fel", 35, new int[]{3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.5F);
    public static ArmorMaterial ARMOR_SWINE = EnumHelper.addArmorMaterial("armor_swine", "swine", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static ArmorMaterial ARMOR_FAMINE = EnumHelper.addArmorMaterial("armor_famine", "famine", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.0F);
    public static ArmorMaterial ARMOR_CHITIN = EnumHelper.addArmorMaterial("armor_chitin", "chitin", 12, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0F);
    
    public static ToolMaterial TOOL_MOLTEN = EnumHelper.addToolMaterial("tool_molten", 4, 1872, 8.5F, 3.0F, 14);
    public static ToolMaterial TOOL_SCEPTER = EnumHelper.addToolMaterial("tool_scepter", 0, 400, 12.0F, 0.0F, 18);
    
	public static final Item NETHERSTEW = new ItemNetherStew("netherstew", 6, 0.6F).setRandPotionEffect(ItemNetherStew.Effect_nethersoup);
	public static final Item CANEBEEF = new ItemFishCustomFood("canebeef", 5, 0.5F, true, 16, true);
	public static final Item CANEPORK = new ItemFishCustomFood("canepork", 5, 0.5F, true, 16, true);
	public static final Item CANEROTTENMEAT = new ItemFishCustomFood("canerottenmeat", 3, 0.3F, true, 16, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 0), 0.8F);
	public static final Item FISSIONPOTION = new ItemFissionPotion("fissionpotion", SoundEvents.ENTITY_SLIME_ATTACK, EnumParticleTypes.VILLAGER_HAPPY, EnumRarity.RARE, false);
	public static final Item HYPHAE = new ItemFishCustom("hyphae", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item PARASITE_ITEM = new ItemParasite("parasite_item", 2, 0.3F, false, 32, false);
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
	public static final Item POISONSPORE = new ItemRareLoot("poisonspore", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item UNDYINGHEART = new ItemRareLoot("undyingheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item GOLDENHEART = new ItemGoldenHeart("goldenheart", mod_LavaCow.TAB_ITEMS, EnumRarity.EPIC);
	public static final Item MOLTENBEEF = new ItemMoltenBeef("moltenbeef");
	public static final Item MOLTEN_ALLOY = new ItemFishCustom("molten_alloy", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item MOLTENAXE = new ItemMoltenAxe("moltenaxe");
	public static final Item MOLTENHAMMER = new ItemFishCustomWeapon("moltenhammer", TOOL_MOLTEN, 4.5F, -2.6F, FishItems.MOLTENBEEF, EnumRarity.RARE);
	public static final Item MOOTENHEART = new ItemBaubleTrinket("mootenheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, true);
	public static final Item POTION_OF_MOOTEN_LAVA = new ItemFissionPotion("potion_of_mooten_lava", SoundEvents.ENTITY_FIREWORK_BLAST, EnumParticleTypes.LAVA, EnumRarity.EPIC, true);
	public static final Item SWINEMASK = new ItemSwineArmor("swinemask", 2, EntityEquipmentSlot.HEAD, 0.2F);
	public static final Item PLAGUED_PORKCHOP = new ItemFishCustomFood("plagued_porkchop", 3, 0.3F, true, 32, false).setPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * 20, 0), 0.8F);
	public static final Item GREEN_BACON_AND_EGGS = new ItemNetherStew("green_bacon_and_eggs", 10, 1.2F).setPotionEffect(new PotionEffect(MobEffects.HASTE, 60 * 20, 1), 1.0F).setAlwaysEdible();
	public static final Item PIGBOARHIDE = new ItemFishCustom("pigboarhide", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item SCYTHE_CLAW = new ItemFishCustom("scythe_claw", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item REAPERS_SCYTHE = new ItemFishCustomWeapon("reapers_scythe", ToolMaterial.DIAMOND, 8.0F, -3.1F, FishItems.SCYTHE_CLAW, EnumRarity.RARE);
	public static final Item FAMINE = new ItemFishCustomWeapon("famine", ToolMaterial.DIAMOND, 0.0F, -1.2F, FishItems.SCYTHE_CLAW, EnumRarity.RARE);
	public static final Item MIMIC_CLAW = new ItemFishCustomFood("mimic_claw", 3, 0.3F, true, 64, false).setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 3 * 20, 0), 0.3F);
	public static final Item MIMIC_CLAW_COOKED = new ItemFishCustomFood("mimic_claw_cooked", 8, 0.8F, true, 32, false);
	public static final Item SILKY_SLUDGE = new ItemFishCustom("silky_sludge", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item MOSSY_STICK = new ItemFishCustom("mossy_stick", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item SLUDGE_WAND = new ItemFishCustomWeapon("sludge_wand", TOOL_SCEPTER, -2.0F, -3.3F, FishItems.SILKY_SLUDGE, EnumRarity.RARE);
	public static final Item BURNTOVIPOSITOR = new ItemRareLoot("burntovipositor", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item WAR = new ItemPiranhaLauncher("war", Items.FIRE_CHARGE, EntityWarSmallFireball.class, FishItems.MOLTENBEEF, EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item MOLTENPAN = new ItemFishCustomWeapon("moltenpan", TOOL_MOLTEN, 2.5F, -2.8F, FishItems.MOLTENBEEF, EnumRarity.RARE);
	public static final Item FELARMOR_HELMET = new ItemFelArmor("felarmor_helmet", 2, EntityEquipmentSlot.HEAD, 0.2F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_CHESTPLATE = new ItemFelArmor("felarmor_chestplate", 2, EntityEquipmentSlot.CHEST, 0.5F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_LEGGINGS = new ItemFelArmor("felarmor_leggings", 2, EntityEquipmentSlot.LEGS, 0.2F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item FELARMOR_BOOTS = new ItemFelArmor("felarmor_boots", 2, EntityEquipmentSlot.FEET, 0.1F).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item PTERA_WING = new ItemPteraWing("ptera_wing", 4, 0.1F, true, 32, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30 * 20, 2), 0.8F);
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
	public static final Item PIRANHALAUNCHER = new ItemPiranhaLauncher("piranhalauncher", ZOMBIEPIRANHA_ITEM, EntityPiranhaLauncher.class, FishItems.ZOMBIEPIRANHA_ITEM, EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item CURSED_FABRIC = new ItemFishCustom("cursed_fabric", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CURSED_BANDAGE = new ItemCursedBandage("cursed_bandage");
	public static final Item HOLY_SLUDGE = new ItemFishCustom("holy_sludge", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item HOLY_GRENADE = new ItemHolyGrenade("holy_grenade");
	public static final Item FEATHER_BLACK = new ItemFishCustom("feather_black", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item SHATTERED_ICE = new ItemFishCustomFood("shattered_ice", 1, 1.8F, false, 32, false).setItemUseAction(EnumAction.DRINK);
	public static final Item HALO_NECKLACE = new ItemBaubleTrinket("halo_necklace", mod_LavaCow.TAB_ITEMS, EnumRarity.EPIC, true);
	public static final Item DREAMCATCHER = new ItemDreamCatcher("dreamcatcher", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, true);	
	public static final Item RAVEN_WHISTLE = new ItemRavenWhistle("raven_whistle");
	public static final Item ACIDICHEART = new ItemRareLoot("acidicheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item FAMINEARMOR_HELMET = new ItemFamineArmor("faminearmor_helmet", 2, EntityEquipmentSlot.HEAD);
	public static final Item FAMINEARMOR_CHESTPLATE = new ItemFamineArmor("faminearmor_chestplate", 2, EntityEquipmentSlot.CHEST);
	public static final Item FAMINEARMOR_LEGGINGS = new ItemFamineArmor("faminearmor_leggings", 2, EntityEquipmentSlot.LEGS);
	public static final Item FAMINEARMOR_BOOTS = new ItemFamineArmor("faminearmor_boots", 2, EntityEquipmentSlot.FEET);
	public static final Item FOUL_BRISTLE = new ItemFishCustom("foul_bristle", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CRABCAKE = new ItemFishCustomFood("crabcake", 5, 0.8F, true, 24, false);
	public static final Item ECTOPLASM = new ItemFishCustom("ectoplasm", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item GHOSTJELLY = new ItemNetherStew("ghostjelly", 6, 0.6F);
	public static final Item BANSHEE_VOCAL_CORD = new ItemRareLoot("banshee_vocal_cord", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, false);
	public static final Item WETA_JAW = new ItemFishCustom("weta_jaw", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CHITIN = new ItemFishCustom("chitin", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item GHOSTBOMB = new ItemHolyGrenade("ghostbomb");
	public static final Item SONICBOMB = new ItemHolyGrenade("sonicbomb");
	public static final Item WETA_HOE = new ItemWetaHoe("weta_hoe");
	public static final Item BONE_STEW = new ItemNetherStew("bonestew", 6, 0.6F).setMultiPotionEffect(ItemNetherStew.Effect_bonestew).setAlwaysEdible();
	public static final Item VESPA_SHIELD = new ItemVespaShield("vespa_shield");
	public static final Item FROZEN_DAGGER = new ItemFishCustomWeapon("frozen_dagger", ToolMaterial.WOOD, 1.0F, -2.4F, FishItems.SHATTERED_ICE, EnumRarity.COMMON);
	public static final Item SPECTRAL_DAGGER = new ItemFishCustomWeapon("spectral_dagger", ToolMaterial.IRON, -1.0F, -2.4F, FishItems.ECTOPLASM_INGOT, EnumRarity.COMMON).setMaxDamage(0);
	public static final Item HATRED_SHARD = new ItemFishCustom("hatred_shard", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item KINGS_CROWN = new ItemCrown("kings_crown", mod_LavaCow.TAB_ITEMS, false);
	public static final Item SKELETONKING_CROWN = new ItemSkeletonKingCrown("skeletonking_crown", 4, EntityEquipmentSlot.HEAD).setCreativeTab(mod_LavaCow.TAB_ITEMS);
	public static final Item EMBLEM_OF_KING = new ItemRareLoot("emblem_of_king", mod_LavaCow.TAB_ITEMS, EnumRarity.UNCOMMON, false);
	public static final Item BEAST_CLAW = new ItemFishCustomWeapon("beast_claw", ToolMaterial.DIAMOND, 3.0F, -2.4F, FishItems.SCYTHE_CLAW, EnumRarity.EPIC);	
	public static final Item CURSEWEAVE_CLOTH = new ItemFishCustom("curseweave_cloth", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item UNDERTAKER_SHOVEL = new ItemFishCustomWeapon("undertaker_shovel", TOOL_SCEPTER, 2.0F, -3.0F, FishItems.HATRED_SHARD, EnumRarity.RARE).setMaxDamage(200);
	public static final Item SKELETONKING_MACE = new ItemFishCustomWeapon("skeletonking_mace", ToolMaterial.DIAMOND, 12.0F, -3.2F, FishItems.HATRED_SHARD, EnumRarity.EPIC);
	public static final Item IMP_HORN = new ItemFishCustom("imp_horn", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CHITIN_HELMET = new ItemChitinArmor("chitinarmor_helmet", 2, EntityEquipmentSlot.HEAD);
	public static final Item CHITIN_CHESTPLATE = new ItemChitinArmor("chitinarmor_chestplate", 2, EntityEquipmentSlot.CHEST);
	public static final Item CHITIN_LEGGINGS = new ItemChitinArmor("chitinarmor_leggings", 2, EntityEquipmentSlot.LEGS);
	public static final Item CHITIN_BOOTS = new ItemChitinArmor("chitinarmor_boots", 2, EntityEquipmentSlot.FEET);
	public static final Item CACTUS_THORN = new ItemFishCustom("cactus_thorn", null, mod_LavaCow.TAB_ITEMS, false);
	public static final Item CACTUS_FRUIT = new ItemCactusFruit("cactus_fruit", 4, 0.2F, true, 32, true).setPotionEffect(new PotionEffect(ModMobEffects.THORNED, 30 * 20, 0), 1.0F);
	public static final Item THORN_SHOOTER = new ItemPiranhaLauncher("thorn_shooter", CACTUS_THORN, EntityCactusThorn.class, new ItemStack(Blocks.CACTUS).getItem(), EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS).setMaxDamage(768);
	public static final Item BAOBING = new ItemNetherStew("baobing", 6, 0.6F).setPotionEffect(new PotionEffect(ModMobEffects.THORNED, 60 * 20, 1), 1.0F);
	public static final Item KUNG_PAO_CHICKEN = new ItemNetherStew("kung_pao_chicken", 8, 0.8F).setPotionEffect(new PotionEffect(ModMobEffects.IMMOLATION, 60 * 20, 1), 1.0F);
	public static final Item ECTOPLASM_MASS = new ItemFishCustom("ectoplasm_mass", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item ECTOPLASM_INGOT = new ItemFishCustom("ectoplasm_ingot", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item SOULFORGED_HEART = new ItemBaubleTrinket("soulforged_heart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, true);
	public static final Item GHOST_SWARMER_ITEM = new ItemFishCustomFood("ghost_swarmer_item", 2, 0.1F, true, 64, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 3 * 20, 0), 0.3F);
	public static final Item FORSAKEN_STAFF = new ItemPiranhaLauncher("forsaken_staff", EntityDeathCoil.class, FishItems.HATRED_SHARD, EnumRarity.RARE).setCreativeTab(mod_LavaCow.TAB_ITEMS).setMaxDamage(333);
	public static final Item SINISTER_WHETSTONE = new ItemSinisterWhetstone("sinister_whetstone");
	public static final Item ANCIENT_AMBER = new ItemFishCustom("ancient_amber", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item SCARAB_WAND = new ItemFishCustomWeapon("scarab_wand", TOOL_SCEPTER, -2.0F, -3.3F, FishItems.ANCIENT_AMBER, EnumRarity.RARE).setMaxDamage(500);
	public static final Item DISEASED_WHEAT = new ItemFishCustom("diseased_wheat", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item DISEASED_BREAD = new ItemFishCustomFood("diseased_bread", 7, 0.6F, false, 32, true).setRandPotionEffect(ItemFishCustomFood.Effect_diseasedbread).setAlwaysEdible();
	public static final Item ENIGMOTH_LARVA_ITEM = new ItemFishCustomFood("enigmoth_larva_item", 2, 0.3F, false, 32, false);
	public static final Item ENIGMOTH_LARVA_ITEM_COOKED = new ItemFishCustomFood("enigmoth_larva_item_cooked", 6, 0.6F, false, 32, false);
	public static final Item HOLY_WATER = new ItemFishCustom("holy_water", null,  mod_LavaCow.TAB_ITEMS, false);
	public static final Item BASICBOMB = new ItemHolyGrenade("basicbomb");
	public static final Item FOUL_HIDE = new ItemFishCustom("foul_hide", null, mod_LavaCow.TAB_ITEMS, false);
	
	public static final SoundEvent ENTITY_PARASITE_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.ambient")).setRegistryName("entity_parasite_ambient");
	public static final SoundEvent ENTITY_PARASITE_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.hurt")).setRegistryName("entity_parasite_hurt");
	public static final SoundEvent ENTITY_PARASITE_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.death")).setRegistryName("entity_parasite_death");
	public static final SoundEvent ENTITY_PARASITE_WEAVE = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.parasite.weave")).setRegistryName("entity_parasite_weave");
	
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
	public static final SoundEvent ENTITY_SALAMANDER_SHOOT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.salamander.shoot")).setRegistryName("entity_salamander_shoot");
	
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
	public static final SoundEvent ENTITY_VESPA_FLYING = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.vespa.flying")).setRegistryName("entity_vespa_flying");
	
	public static final SoundEvent ENTITY_SCARECROW_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.ambient")).setRegistryName("entity_scarecrow_ambient");
	public static final SoundEvent ENTITY_SCARECROW_AMBIENT_OLD = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.ambient_old")).setRegistryName("entity_scarecrow_ambient_old");
	public static final SoundEvent ENTITY_SCARECROW_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.hurt")).setRegistryName("entity_scarecrow_hurt");
	public static final SoundEvent ENTITY_SCARECROW_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.death")).setRegistryName("entity_scarecrow_death");
	public static final SoundEvent ENTITY_SCARECROW_DEATH_OLD = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.death_old")).setRegistryName("entity_scarecrow_death_old");
	public static final SoundEvent ENTITY_SCARECROW_SCYTHE = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.scythe")).setRegistryName("entity_scarecrow_scythe");
	public static final SoundEvent ENTITY_SCARECROW_SPIN = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarecrow.spin")).setRegistryName("entity_scarecrow_spin");
	
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
	
	public static final SoundEvent ENTITY_AVATON_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avaton.ambient")).setRegistryName("entity_avaton_ambient");
	public static final SoundEvent ENTITY_AVATON_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avaton.hurt")).setRegistryName("entity_avaton_hurt");
	public static final SoundEvent ENTITY_AVATON_SPELL = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avaton.spell")).setRegistryName("entity_avaton_spell");
	public static final SoundEvent ENTITY_AVATON_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.avaton.death")).setRegistryName("entity_avaton_death");
	
	public static final SoundEvent ENTITY_SKELETONKING_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.ambient")).setRegistryName("entity_skeletonking_ambient");
	public static final SoundEvent ENTITY_SKELETONKING_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.attack")).setRegistryName("entity_skeletonking_attack");
	public static final SoundEvent ENTITY_SKELETONKING_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.death")).setRegistryName("entity_skeletonking_death");
	public static final SoundEvent ENTITY_SKELETONKING_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.hurt")).setRegistryName("entity_skeletonking_hurt");
	public static final SoundEvent ENTITY_SKELETONKING_KNOCKBACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.knockback")).setRegistryName("entity_skeletonking_knockback");
	public static final SoundEvent ENTITY_SKELETONKING_SPELL_SKELETON_ARMY = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.spell_skeleton_army")).setRegistryName("entity_skeletonking_spell_skeleton_army");
	public static final SoundEvent ENTITY_SKELETONKING_SPELL_SUMMON = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.spell_summon")).setRegistryName("entity_skeletonking_spell_summon");
	public static final SoundEvent ENTITY_SKELETONKING_SPELL_TELEPORT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.spell_teleport")).setRegistryName("entity_skeletonking_spell_teleport");
	public static final SoundEvent ENTITY_SKELETONKING_SPELL_TOSS = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.spell_toss")).setRegistryName("entity_skeletonking_spell_toss");
	public static final SoundEvent ENTITY_SKELETONKING_SPAWN = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.spawn")).setRegistryName("entity_skeletonking_spawn");
	public static final SoundEvent ENTITY_SKELETONKING_STEP = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.skeletonking.step")).setRegistryName("entity_skeletonking_step");
	
	public static final SoundEvent ENTITY_CACTYRANT_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.cactyrant.ambient")).setRegistryName("entity_cactyrant_ambient");
	public static final SoundEvent ENTITY_CACTYRANT_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.cactyrant.death")).setRegistryName("entity_cactyrant_death");
	public static final SoundEvent ENTITY_CACTYRANT_GROW = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.cactyrant.grow")).setRegistryName("entity_cactyrant_grow");
	
	public static final SoundEvent ENTITY_SEA_HAG_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sea_hag.ambient")).setRegistryName("entity_sea_hag_ambient");
	public static final SoundEvent ENTITY_SEA_HAG_SCREECH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.sea_hag.screech")).setRegistryName("entity_sea_hag_screech");
	
	public static final SoundEvent ENTITY_WRAITH_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wraith.ambient")).setRegistryName("entity_wraith_ambient");
	public static final SoundEvent ENTITY_WRAITH_ATTACK = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wraith.attack")).setRegistryName("entity_wraith_attack");
	public static final SoundEvent ENTITY_WRAITH_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.wraith.death")).setRegistryName("entity_wraith_death");
	
	public static final SoundEvent ENTITY_SCARAB_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarab.ambient")).setRegistryName("entity_scarab_ambient");
	public static final SoundEvent ENTITY_SCARAB_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarab.hurt")).setRegistryName("entity_scarab_hurt");
	public static final SoundEvent ENTITY_SCARAB_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.scarab.death")).setRegistryName("entity_scarab_death");
	
	public static final SoundEvent ENTITY_ENIGMOTH_AMBIENT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.enigmoth.ambient")).setRegistryName("entity_enigmoth_ambient");
	public static final SoundEvent ENTITY_ENIGHMOTH_HURT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.enigmoth.hurt")).setRegistryName("entity_enigmoth_hurt");
	public static final SoundEvent ENTITY_ENIGMOTH_DEATH = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.enigmoth.death")).setRegistryName("entity_enigmoth_death");
	public static final SoundEvent ENTITY_ENIGMOTH_FLYING = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.enigmoth.flying")).setRegistryName("entity_enigmoth_flying");
	public static final SoundEvent ENTITY_ENIGMOTH_SCALES = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "entity.enigmoth.scales")).setRegistryName("entity_enigmoth_scales");
	
	public static final SoundEvent RANDOM_DEATH_COIL_SHOOT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.death_coil_shoot")).setRegistryName("random_death_coil_shoot");
	public static final SoundEvent RANDOM_FRUIT_PLANT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.fruit_plant")).setRegistryName("random_fruit_plant");
	public static final SoundEvent RANDOM_HOLY_GRENADE = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.holy_grenade")).setRegistryName("random_holy_grenade");
	public static final SoundEvent RANDOM_PIRANHA_SHOOT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.piranha_shoot")).setRegistryName("random_piranha_shoot");
	public static final SoundEvent RANDOM_THORN_SHOOT = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.thorn_shoot")).setRegistryName("random_thorn_shoot");
	public static final SoundEvent RANDOM_SPIN = new SoundEvent(new ResourceLocation(mod_LavaCow.MODID, "random.spin")).setRegistryName("random_spin");
}
