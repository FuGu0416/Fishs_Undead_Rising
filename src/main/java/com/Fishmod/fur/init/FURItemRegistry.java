package com.Fishmod.fur.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.item.CactusFruitItem;
import com.Fishmod.fur.item.ChitinArmorItem;
import com.Fishmod.fur.item.DiseasedBreadItem;
import com.Fishmod.fur.item.EntityBucketItem;
import com.Fishmod.fur.item.FURArrowItem;
import com.Fishmod.fur.item.FURItem;
import com.Fishmod.fur.item.FURPotionItem;
import com.Fishmod.fur.item.FURRangedItem;
import com.Fishmod.fur.item.FURStewItem;
import com.Fishmod.fur.item.FURThrowableItem;
import com.Fishmod.fur.item.FURWeaponItem;
import com.Fishmod.fur.item.FamineArmorItem;
import com.Fishmod.fur.item.FangDaggerItem;
import com.Fishmod.fur.item.FrozenThighItem;
import com.Fishmod.fur.item.GhostlyArmorItem;
import com.Fishmod.fur.item.InfusedBandageItem;
import com.Fishmod.fur.item.BloatedIntestineItem;
import com.Fishmod.fur.item.MoltenArmorItem;
import com.Fishmod.fur.item.MoltenAxeItem;
import com.Fishmod.fur.item.MoltenHammerItem;
import com.Fishmod.fur.item.MoltenMeatItem;
import com.Fishmod.fur.item.ParasiteRawItem;
import com.Fishmod.fur.item.SalamanderBucketItem;
import com.Fishmod.fur.item.UndyingHeartItem;
import com.Fishmod.fur.item.WetaHoeItem;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.effect.MobEffectInstance;

public class FURItemRegistry {
	public static List<RegistryObject<Item>> creativeTabSpawnEggMap = new ArrayList<RegistryObject<Item>>();
	public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, mod_LavaCow.MODID);
	public static final DeferredRegister<BannerPattern> BANNER_DEF_REG = DeferredRegister.create(Registries.BANNER_PATTERN, mod_LavaCow.MODID);
	
	public static final RegistryObject<Item> SHARP_FANG = DEF_REG.register("sharp_fang", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FANG_DAGGER = DEF_REG.register("fang_dagger", () -> new FangDaggerItem(new Item.Properties(), Tiers.STONE, 1, -2.0F, -1.0D, FURItemRegistry.SHARP_FANG.get(), true));
	public static final RegistryObject<Item> BONE_SWORD = DEF_REG.register("bone_sword", () -> new FURWeaponItem(new Item.Properties(), Tiers.IRON, 2, -2.4F, 0.0D, Items.BONE_BLOCK, true));
	public static final RegistryObject<Item> CHITIN = DEF_REG.register("chitin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_HELMET = DEF_REG.register("chitin_armor_helmet", () -> new ChitinArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_CHESTPLATE = DEF_REG.register("chitin_armor_chestplate", () -> new ChitinArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_LEGGINGS = DEF_REG.register("chitin_armor_leggings", () -> new ChitinArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_BOOTS = DEF_REG.register("chitin_armor_boots", () -> new ChitinArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));
	public static final RegistryObject<Item> BASIC_BOMB = DEF_REG.register("basic_bomb", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> GHOST_BOMB = DEF_REG.register("ghost_bomb", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> HOLY_GRENADE = DEF_REG.register("holy_grenade", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> SONIC_BOMB = DEF_REG.register("sonic_bomb", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> WETA_JAW = DEF_REG.register("weta_jaw", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> WETA_HOE = DEF_REG.register("weta_hoe", () -> new WetaHoeItem(new Item.Properties(), Tiers.IRON, -2, -1.0F, CHITIN.get()));
	public static final RegistryObject<Item> ECTOPLASM = DEF_REG.register("ectoplasm", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_MASS = DEF_REG.register("ectoplasm_mass", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_INGOT = DEF_REG.register("ectoplasm_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GHOSTLY_ARMOR_HELMET = DEF_REG.register("ghostly_armor_helmet", () -> new GhostlyArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GHOSTLY_ARMOR_CHESTPLATE = DEF_REG.register("ghostly_armor_chestplate", () -> new GhostlyArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GHOSTLY_ARMOR_LEGGINGS = DEF_REG.register("ghostly_armor_leggings", () -> new GhostlyArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GHOSTLY_ARMOR_BOOTS = DEF_REG.register("ghostly_armor_boots", () -> new GhostlyArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> WISP_ASHES = DEF_REG.register("wisp_ashes", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> WISP_IN_A_BOTTLE = DEF_REG.register("wisp_in_a_bottle", () -> new EntityBucketItem(FUREntityRegistry.WISP::get, Items.GLASS_BOTTLE, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> MOLTEN_MEAT = DEF_REG.register("molten_meat", () -> new MoltenMeatItem(new Item.Properties().durability(0).stacksTo(64).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_ALLOY = DEF_REG.register("molten_alloy", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> MOLTEN_AXE = DEF_REG.register("molten_axe", () -> new MoltenAxeItem(new Item.Properties().fireResistant(), Tiers.DIAMOND, 5.0F, -3.0F, MOLTEN_ALLOY.get(), ParticleTypes.FLAME));
	public static final RegistryObject<Item> MOLTEN_HAMMER = DEF_REG.register("molten_hammer", () -> new MoltenHammerItem(new Item.Properties().fireResistant(), Tiers.DIAMOND, 3, -2.4F, 0.0D, Items.GUNPOWDER, true));
	public static final RegistryObject<Item> MOLTEN_ARMOR_HELMET = DEF_REG.register("molten_armor_helmet", () -> new MoltenArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.2F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_CHESTPLATE = DEF_REG.register("molten_armor_chestplate", () -> new MoltenArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.5F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_LEGGINGS = DEF_REG.register("molten_armor_leggings", () -> new MoltenArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.2F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_BOOTS = DEF_REG.register("molten_armor_boots", () -> new MoltenArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.1F));
	public static final RegistryObject<Item> UNDYING_HEART = DEF_REG.register("undying_heart", () -> new UndyingHeartItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MOOTEN_HEART = DEF_REG.register("mooten_heart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 1));
	public static final RegistryObject<Item> SOULFORGED_HEART = DEF_REG.register("soulforged_heart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 1));
	public static final RegistryObject<Item> ACIDIC_HEART = DEF_REG.register("acidic_heart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 0));
	public static final RegistryObject<Item> FOUL_BRISTLE = DEF_REG.register("foul_bristle", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FOUL_HIDE = DEF_REG.register("foul_hide", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FAMINE_ARMOR_HELMET = DEF_REG.register("famine_armor_helmet", () -> new FamineArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> FAMINE_ARMOR_CHESTPLATE = DEF_REG.register("famine_armor_chestplate", () -> new FamineArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> FAMINE_ARMOR_LEGGINGS = DEF_REG.register("famine_armor_leggings", () -> new FamineArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> FAMINE_ARMOR_BOOTS = DEF_REG.register("famine_armor_boots", () -> new FamineArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SCYTHE_CLAW = DEF_REG.register("scythe_claw", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> REAPERS_SCYTHE = DEF_REG.register("reapers_scythe", () -> new FURWeaponItem(new Item.Properties(), Tiers.DIAMOND, 8, -3.1F, 1.0D, FURItemRegistry.SCYTHE_CLAW.get(), true));
	public static final RegistryObject<Item> FAMINE = DEF_REG.register("famine", () -> new FURWeaponItem(new Item.Properties(), Tiers.DIAMOND, 0, -1.2F, -1.0D, FURItemRegistry.SCYTHE_CLAW.get(), true));
	public static final RegistryObject<Item> IMP_HORN = DEF_REG.register("imp_horn", () -> new FURItem(new Item.Properties()));
	public static final RegistryObject<Item> CURSED_FABRIC = DEF_REG.register("cursed_fabric", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CURSEWEAVE_CLOTH = DEF_REG.register("curseweave_cloth", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PIRANHA_BUCKET = DEF_REG.register("piranha_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.PIRANHA.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> PIRANHA_RAW = DEF_REG.register("piranha_raw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> PIRANHA_COOKED = DEF_REG.register("piranha_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> SWARMER_BUCKET = DEF_REG.register("swarmer_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.SWARMER.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> SWARMER_RAW = DEF_REG.register("swarmer_raw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> SWARMER_COOKED = DEF_REG.register("swarmer_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> CACTUS_THORN = DEF_REG.register("cactus_thorn", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CACTUS_FRUIT = DEF_REG.register("cactus_fruit", () -> new CactusFruitItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.2F).effect(() -> new MobEffectInstance(FUREffectRegistry.THORNED.get(), 60 * 20, 0), 1.0F).build()), 1));
	public static final RegistryObject<Item> CACTOID_POT = DEF_REG.register("cactoid_pot", () -> new EntityBucketItem(() -> FUREntityRegistry.CACTOID.get(), Items.FLOWER_POT, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> THORN_SHOOTER = DEF_REG.register("thorn_shooter", () -> new FURRangedItem(CACTUS_THORN.get(), () -> FUREntityRegistry.CACTUS_THORN.get(), new Item.Properties().durability(768)));
	public static final RegistryObject<Item> HATRED_SHARD = DEF_REG.register("hatred_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISEASED_WHEAT = DEF_REG.register("diseased_wheat", () -> new Item(new Item.Properties())); 
	public static final RegistryObject<Item> DISEASED_BREAD = DEF_REG.register("diseased_bread", () -> new DiseasedBreadItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).
			effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 8*20, 0), 1.0F)
			.effect(() -> new MobEffectInstance(MobEffects.POISON, 8*20, 1), 0.3F)
			.effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 30*20, 1), 0.3F)
			.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 16*20, 1), 0.3F)
			.effect(() -> new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 16*20, 2), 0.3F).build()), 1)); 
	public static final RegistryObject<Item> FEATHER_BLACK = DEF_REG.register("feather_black", () -> new Item(new Item.Properties())); 
	public static final RegistryObject<Item> HYPHAE = DEF_REG.register("hyphae", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FROZEN_THIGH = DEF_REG.register("frozen_thigh", () -> new FrozenThighItem(new Item.Properties().stacksTo(1).durability(64).food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4*20, 4), 0.6F).build())));
	public static final RegistryObject<Item> POISON_SPORE = DEF_REG.register("poison_spore", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MIMIC_CLAW_RAW = DEF_REG.register("mimic_claw_raw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60*20, 0), 0.3F).build()), 64, UseAnim.EAT, 0));
	public static final RegistryObject<Item> MIMIC_CLAW_COOKED = DEF_REG.register("mimic_claw_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build())));
	public static final RegistryObject<Item> KUNG_PAO_CHICKEN = DEF_REG.register("kung_pao_chicken", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).alwaysEat().meat().effect(() -> new MobEffectInstance(FUREffectRegistry.IMMOLATION.get(), 60*20, 1), 1.0F).build()), UseAnim.EAT, 1));
	public static final RegistryObject<Item> BOABING = DEF_REG.register("baobing", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(FUREffectRegistry.THORNED.get(), 60*20, 1), 1.0F).build()), UseAnim.EAT, 1));	
	public static final RegistryObject<Item> GHOST_JELLY = DEF_REG.register("ghost_jelly", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 6*20, 2), 1.0F).build()), UseAnim.EAT, 1));
	public static final RegistryObject<Item> MAGMACHO = DEF_REG.register("magmacho", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(1.2F).alwaysEat().effect(() -> new MobEffectInstance(FUREffectRegistry.IMMOLATION.get(), 30*20, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10*20, 1), 1.0F).build()), UseAnim.EAT, 0));
	public static final RegistryObject<Item> UNDERTAKER_SHOVEL = DEF_REG.register("undertaker_shovel", () -> new FURWeaponItem(new Item.Properties().rarity(Rarity.RARE), Tiers.IRON, 2, -3.0F, 0.0D, FURItemRegistry.HATRED_SHARD.get(), true));
	public static final RegistryObject<Item> SHRIEK_CORD = DEF_REG.register("shriek_cord", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PTERA_WING_RAW = DEF_REG.register("ptera_wing_raw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1F).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 30*20, 2), 0.8F).build()), 64, UseAnim.EAT, 0));
	public static final RegistryObject<Item> PTERA_WING_COOKED = DEF_REG.register("ptera_wing_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build()), 64, UseAnim.EAT, 0));
	public static final RegistryObject<Item> GHOUL_CLAW = DEF_REG.register("ghoul_claw", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GHOUL_ARROW = DEF_REG.register("ghoul_arrow", () -> new FURArrowItem(new Item.Properties()));
	public static final RegistryObject<Item> FANG_ARROW = DEF_REG.register("fang_arrow", () -> new FURArrowItem(new Item.Properties()));
	public static final RegistryObject<Item> PARASITE_RAW = DEF_REG.register("parasite_raw", () -> new ParasiteRawItem(new Item.Properties()));
	public static final RegistryObject<Item> PARASITE_COOKED = DEF_REG.register("parasite_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build())));
	public static final RegistryObject<Item> HOLY_WATER = DEF_REG.register("holy_water", () -> new FURItem(new Item.Properties()));
	public static final RegistryObject<Item> VESPA_CARAPACE = DEF_REG.register("vespa_carapace", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BONE_STEW = DEF_REG.register("bone_stew", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 20*20, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20*20, 0), 1.0F).build()), 1));
	public static final RegistryObject<Item> EMBLEM_OF_KING = DEF_REG.register("emblem_of_king", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> SKELETONKING_MACE = DEF_REG.register("skeletonking_mace", () -> new FURWeaponItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), Tiers.DIAMOND, 12, -3.2F, 0.0D, FURItemRegistry.HATRED_SHARD.get(), false));
	public static final RegistryObject<Item> ANCIENT_AMBER = DEF_REG.register("ancient_amber", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ENIGMOTH_LARVA_RAW = DEF_REG.register("enigmoth_larva_raw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(FUREffectRegistry.VOID_DUST.get(), 20*20, 3), 0.3F).build())));
	public static final RegistryObject<Item> ENIGMOTH_LARVA_COOKED = DEF_REG.register("enigmoth_larva_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build())));
	public static final RegistryObject<Item> USHABTI = DEF_REG.register("ushabti", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> POISON_STINGER = DEF_REG.register("poison_stinger", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADVANCEMENT_ICON = DEF_REG.register("advancement_icon", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> INFUSED_BANDAGE = DEF_REG.register("infused_bandage", () -> new InfusedBandageItem(new Item.Properties(), 32, UseAnim.BOW, 0));
	public static final RegistryObject<Item> MIMIC_EGG = DEF_REG.register("mimic_egg", () -> new FURItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> POTION_OF_FISSION = DEF_REG.register("potion_of_fission", () -> new FURPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(1).rarity(Rarity.COMMON), SoundEvents.SLIME_SQUISH, ParticleTypes.HAPPY_VILLAGER));	
	public static final RegistryObject<Item> POTION_OF_MOOTEN_LAVA = DEF_REG.register("potion_of_mooten_lava", () -> new FURPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(1).rarity(Rarity.EPIC), SoundEvents.FIREWORK_ROCKET_BLAST, ParticleTypes.LAVA));
	public static final RegistryObject<Item> FISSION_REAGENT = DEF_REG.register("fission_reagent", () -> new Item(new Item.Properties().craftRemainder(CURSEWEAVE_CLOTH.get())));
	public static final RegistryObject<Item> MOOTEN_REAGENT = DEF_REG.register("mooten_reagent", () -> new Item(new Item.Properties().craftRemainder(CURSEWEAVE_CLOTH.get())));
	public static final RegistryObject<Item> SALAMANDER_BUCKET = DEF_REG.register("salamander_bucket", () -> new SalamanderBucketItem(() -> FUREntityRegistry.SALAMANDER.get(), () -> Fluids.LAVA, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> COMBUSTIVE_GLAND = DEF_REG.register("combustive_gland", () -> new FURItem(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> WAR = DEF_REG.register("war", () -> new FURRangedItem(Items.FIRE_CHARGE, () -> FUREntityRegistry.WAR_SMALL_FIREBALL.get(), new Item.Properties().durability(384)));
	public static final RegistryObject<Item> ENIGMOTH_DUST = DEF_REG.register("enigmoth_dust", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MUMMIFIED_COD = DEF_REG.register("mummified_cod", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 16*20, 0), 0.3F).build())));
	public static final RegistryObject<Item> BONE_TROUT = DEF_REG.register("bone_trout", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MUMMIFIED_COD_BUCKET = DEF_REG.register("mummified_cod_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.MUMMIFIED_COD.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> BONE_TROUT_BUCKET = DEF_REG.register("bone_trout_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.BONE_TROUT.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> LAMPREY_RAW = DEF_REG.register("lamprey_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60*20, 0), 0.3F).build())));
	public static final RegistryObject<Item> LAMPREY_COOKED = DEF_REG.register("lamprey_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> LAMPREY_KABAYAKI = DEF_REG.register("lamprey_kabayaki", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 0), 1.0F).build())));
	public static final RegistryObject<Item> LAMPREY_BUCKET = DEF_REG.register("lamprey_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.LAMPREY.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> BLOATED_INTESTINE = DEF_REG.register("bloated_intestine", () -> new BloatedIntestineItem());
	public static final RegistryObject<Item> SAUSAGE_ROLL = DEF_REG.register("sausage_roll", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build())));
	
	/*
	public static final RegistryObject<Item> PLAGUED_PORKCHOP = new Item(new Item.Properties().food(new Food.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new EffectInstance(Effects.DIG_SLOWDOWN, 30*20, 0), 0.8F).build())).setRegistryName("fur:plagued_porkchop");
	public static final RegistryObject<Item> GREEN_BACON_AND_EGGS = new NetherStewItem(new Item.Properties().food(new Food.Builder().nutrition(10).saturationMod(1.2F).meat().alwaysEat().effect(() -> new EffectInstance(Effects.DIG_SPEED, 60*20, 0), 1F).build()), UseAction.EAT, 1).setRegistryName("fur:green_bacon_and_eggs");
	public static final RegistryObject<Item> PIGBOARHIDE = new Item(new Item.Properties()).setRegistryName("fur:pigboarhide");
	public static final RegistryObject<Item> SILKY_SLUDGE = new Item(new Item.Properties()).setRegistryName("fur:silky_sludge");
	public static final RegistryObject<Item> SLUDGE_WAND = new FURWeaponItem(new Item.Properties().rarity(Rarity.RARE), "fur:sludge_wand", ItemTier.GOLD, -2, -3.3F, FURItemRegistry.SILKY_SLUDGE);
	public static final RegistryObject<Item> VESPA_DAGGER = new FURWeaponItem(new Item.Properties().rarity(Rarity.COMMON), "fur:vespa_dagger", ItemTier.IRON, 2, -2.4F, FURItemRegistry.POISONSTINGER);
	public static final RegistryObject<Item> SWINEMASK = new SwineArmorItem(EquipmentSlotType.HEAD, (new Item.Properties())).setRegistryName("fur:swinearmor_helmet");
	public static final RegistryObject<Item> SWINEARMOR_CHESTPLATE = new SwineArmorItem(EquipmentSlotType.CHEST, (new Item.Properties())).setRegistryName("fur:swinearmor_chestplate");
	public static final RegistryObject<Item> SWINEARMOR_LEGGINGS = new SwineArmorItem(EquipmentSlotType.LEGS, (new Item.Properties())).setRegistryName("fur:swinearmor_leggings");
	public static final RegistryObject<Item> SWINEARMOR_BOOTS = new SwineArmorItem(EquipmentSlotType.FEET, (new Item.Properties())).setRegistryName("fur:swinearmor_boots");
	public static final RegistryObject<Item> PIRANHALAUNCHER = new FURRangedItem("fur:piranhalauncher", SWARMER, FUREntityRegistry.PIRANHA_LAUNCHER, new Item.Properties().rarity(Rarity.RARE).durability(384));
	public static final RegistryObject<Item> DREAMCATCHER = new DreamCatcherItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).durability(120)).setRegistryName("fur:dreamcatcher");	
	public static final RegistryObject<Item> RAVEN_WHISTLE = new RavenWhistleItem(new Item.Properties().stacksTo(1)).setRegistryName("fur:raven_whistle");
	public static final RegistryObject<Item> VESPA_SHIELD = new VespaShieldItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties()).durability(504)).setRegistryName("fur:vespa_shield");
	public static final RegistryObject<Item> FROZEN_DAGGER = new FURWeaponItem(new Item.Properties().rarity(Rarity.COMMON), "fur:frozen_dagger", ItemTier.WOOD, 2, -2.4F, FURItemRegistry.SHATTERED_ICE);
	public static final RegistryObject<Item> SPECTRAL_DAGGER = new FURWeaponItem(new Item.Properties().rarity(Rarity.COMMON), "fur:spectral_dagger", FURItemTier.SPECTRAL, -1, -2.4F, FURItemRegistry.ECTOPLASM);
	public static final RegistryObject<Item> STAINED_KINGS_CROWN = new CrownItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), 0).setRegistryName("fur:stained_kings_crown");
	public static final RegistryObject<Item> CURSED_KINGS_CROWN = new CrownItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), 1).setRegistryName("fur:cursed_kings_crown");
	public static final RegistryObject<Item> SKELETONKING_CROWN = new SkeletonKingCrownItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant()).setRegistryName("fur:skeletonking_crown");
	public static final RegistryObject<Item> BEAST_CLAW = new FURWeaponItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().rarity(Rarity.EPIC)), "fur:beast_claw", ItemTier.DIAMOND, 3, -2.4F, FURItemRegistry.SCYTHE_CLAW);
	public static final RegistryObject<Item> SOULFIREHAMMER = new FURWeaponItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), "fur:soulfirehammer", ItemTier.NETHERITE, 4, -2.4F, FURItemRegistry.ECTOPLASM_INGOT);
	public static final RegistryObject<Item> SOULFIREAXE = new MoltenAxeItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), "fur:soulfireaxe", ItemTier.NETHERITE, 5.0F, -3.0F, FURItemRegistry.ECTOPLASM_INGOT, ParticleTypes.SOUL_FIRE_FLAME);
	public static final RegistryObject<Item> FORSAKEN_STAFF = new FURRangedItem("fur:forsaken_staff", null, FUREntityRegistry.DEATHCOIL, new Item.Properties().durability(32));
	public static final RegistryObject<Item> SINISTER_WHETSTONE = new SinisterWhetstoneItem(new Item.Properties()).setRegistryName("fur:sinister_whetstone");
	public static final RegistryObject<Item> ILLAGER_NOSE = new IllagerNoseItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties())).setRegistryName("fur:illager_nose");
	public static final RegistryObject<Item> SCARAB_SCEPTER = new FURWeaponItem(new Item.Properties().rarity(Rarity.RARE), "fur:scarab_scepter", ItemTier.GOLD, -2, -3.3F, FURItemRegistry.ANCIENT_AMBER);	
	public static final RegistryObject<Item> PHEROMONE_GLAND = new FURItem(new Item.Properties().food(new Food.Builder().nutrition(1).saturationMod(0.1F).effect(() -> new EffectInstance(FUREffectRegistry.CHARMING_PHEROMONE, 60 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.CONFUSION, 10 * 20, 1), 1.0F).build()), 1).setRegistryName("fur:pheromone_gland");
	public static final RegistryObject<Item> CHARMING_CATALYST = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(1).rarity(Rarity.COMMON), SoundEvents.HONEY_DRINK, ParticleTypes.HEART).setRegistryName("fur:charming_catalyst");
	public static final RegistryObject<Item> PARASITE_OVUM = new FURItem(new Item.Properties().food(new Food.Builder().nutrition(1).saturationMod(0.1F).effect(() -> new EffectInstance(FUREffectRegistry.INFESTED, 12 * 20, 0), 0.8F).build())).setRegistryName("fur:parasite_ovum");
	public static final RegistryObject<Item> ANKH_SCEPTER = new FURWeaponItem(new Item.Properties().rarity(Rarity.EPIC), "fur:ankh_scepter", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);
	public static final RegistryObject<Item> FUNGAL_STAFF = new FURWeaponItem(new Item.Properties().rarity(Rarity.EPIC), "fur:fungal_staff", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);
	public static final RegistryObject<Item> FROZEN_GRIP = new FURWeaponItem(new Item.Properties().rarity(Rarity.EPIC), "fur:frozen_grip", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);	
	*/
	
    public static final RegistryObject<BannerPattern> PATTERN_SKELETONKING = BANNER_DEF_REG.register("skeletonking", () -> new BannerPattern("skeletonking"));
    public static final RegistryObject<BannerPattern> PATTERN_WENDIGO = BANNER_DEF_REG.register("wendigo", () -> new BannerPattern("wendigo"));
		
    public static final RegistryObject<Item> SKELETONKING_PATTERN = DEF_REG.register("banner_pattern_skeletonking", () -> new BannerPatternItem(FURTagRegistry.PATTERN_SKELETONKING, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WENDIGO_PATTERN = DEF_REG.register("banner_pattern_wendigo", () -> new BannerPatternItem(FURTagRegistry.PATTERN_WENDIGO, new Item.Properties().stacksTo(1)));
    
    static {
        spawnEgg("lavacow", FUREntityRegistry.LAVACOW, 0x312C36, 0xFFDE00);
        spawnEgg("foglet", FUREntityRegistry.FOGLET, 0xCBD3B9, 0x41352F);
        spawnEgg("isnachi", FUREntityRegistry.ISNACHI, 0xB4A27E, 0x190508);
        spawnEgg("imp", FUREntityRegistry.IMP, 0xD03336, 0xFFD6A0);
        spawnEgg("seahag", FUREntityRegistry.SEAHAG, 0x44AD9A, 0x4ADC00);
        spawnEgg("piranha", FUREntityRegistry.PIRANHA, 0x3E3E3E, 0xE34600);
        spawnEgg("swarmer", FUREntityRegistry.SWARMER, 0x5D5D5D, 0x880909);
        spawnEgg("cactyrant", FUREntityRegistry.CACTYRANT, 0x649832, 0x426520);
        spawnEgg("wendigo", FUREntityRegistry.WENDIGO, 0x30180C, 0xFFFAEC);
        spawnEgg("scarecrow", FUREntityRegistry.SCARECROW, 0x5A4F3B, 0xE9CD84);
        spawnEgg("weta", FUREntityRegistry.WETA, 0x845336, 0xEACAA7);
        spawnEgg("avaton", FUREntityRegistry.AVATON, 0xAAA48E, 0x222829);
        spawnEgg("wraith", FUREntityRegistry.WRAITH, 0x2DE6FD, 0x00353B);
        spawnEgg("wisp", FUREntityRegistry.WISP, 0xD4D3D2, 0x46FEF1);
        spawnEgg("unburied", FUREntityRegistry.UNBURIED, 0xD4D9BA, 0x292C32);
        spawnEgg("mycosis", FUREntityRegistry.MYCOSIS, 0xBCE0AC, 0x83631D);
        spawnEgg("frigid", FUREntityRegistry.FRIGID, 0xAFE0E2, 0x59484F);
        spawnEgg("mummy", FUREntityRegistry.MUMMY, 0xE9DAAE, 0x9A8157);
        spawnEgg("undertaker", FUREntityRegistry.UNDERTAKER, 0x3c424b, 0xA3AC93);
        spawnEgg("banshee", FUREntityRegistry.BANSHEE, 0xA2A78D, 0x34363A);
        spawnEgg("cactoid", FUREntityRegistry.CACTOID, 0x649832, 0xFFF25F);
        spawnEgg("mimic", FUREntityRegistry.MIMIC, 0xE168FF, 0x070000);
        spawnEgg("ptera", FUREntityRegistry.PTERA, 0x208938, 0xD61717);
        spawnEgg("salamander", FUREntityRegistry.SALAMANDER, 0x260606, 0xF4F142);
        spawnEgg("enigmoth", FUREntityRegistry.ENIGMOTH, 0x0D0B11, 0xA675E9);
        spawnEgg("scarab", FUREntityRegistry.SCARAB, 0x282219, 0xFFCD55);
        spawnEgg("parasite", FUREntityRegistry.PARASITE, 0xAAFFEE, 0xBBFFEE);
    	spawnEgg("mummified_cod", FUREntityRegistry.MUMMIFIED_COD, 0xDDC88D, 0xAF905B);
    	spawnEgg("bone_trout", FUREntityRegistry.BONE_TROUT, 0xDFDDCB, 0xBBB8A0);
    	spawnEgg("ghoul", FUREntityRegistry.GHOUL, 0xA69087, 0xF7EDD9);
    	spawnEgg("lamprey", FUREntityRegistry.LAMPREY, 0x0A1822, 0xA0C3CF);
    	
        /*
    	spawnEgg(FUREntityRegistry.UNDEADSWINE, 0x8A9B8A, 0x3E5C5A, new Item.Properties()).setRegistryName("fur:spawn_egg_undeadswine"));
    	spawnEgg(FUREntityRegistry.SLUDGELORD, 0x282119, 0x81DDFF, new Item.Properties()).setRegistryName("fur:spawn_egg_sludgelord"));
    	spawnEgg(FUREntityRegistry.RAVEN, 0x130D19, 0x192B3E, new Item.Properties()).setRegistryName("fur:spawn_egg_raven"));
    	spawnEgg(FUREntityRegistry.SEAGULL, 0xEEEEEE, 0x121212, new Item.Properties()).setRegistryName("fur:spawn_egg_seagull"));
    	spawnEgg(FUREntityRegistry.VESPA, 0x85E214, 0xDA3119, new Item.Properties()).setRegistryName("fur:spawn_egg_vespa"));
    	spawnEgg(FUREntityRegistry.BONEWORM, 0x989898, 0x410E0E, new Item.Properties()).setRegistryName("fur:spawn_egg_boneworm"));
    	spawnEgg(FUREntityRegistry.PINGU, 0x77A9FF, 0x797979, new Item.Properties()).setRegistryName("fur:spawn_egg_pingu"));
    	spawnEgg(FUREntityRegistry.GHOSTRAY, 0x233A41, 0x7AFDFD, new Item.Properties()).setRegistryName("fur:spawn_egg_ghostray"));
    	spawnEgg(FUREntityRegistry.FORSAKEN, 12698049, 4802889, new Item.Properties()).setRegistryName("fur:spawn_egg_forsaken"));
    	spawnEgg(FUREntityRegistry.SKELETONKING, 0x2F2A2A, 0xA2A1A1, new Item.Properties()).setRegistryName("fur:spawn_egg_skeletonking"));
    	spawnEgg(FUREntityRegistry.WARPEDFIREFLY, 0x0F9373, 0xFE8738, new Item.Properties()).setRegistryName("fur:spawn_egg_warpedfirefly"));
    	spawnEgg(FUREntityRegistry.GRAVEROBBER, 0x40433E, 0x959B9B, new Item.Properties()).setRegistryName("fur:spawn_egg_graverobber"));
    	spawnEgg(FUREntityRegistry.GRAVEROBBERGHOST, 0x7AF2FF, 0x40433E, new Item.Properties()).setRegistryName("fur:spawn_egg_graverobberghost"));
    	spawnEgg(FUREntityRegistry.BEELZEBUB, 0x1D1B1C, 0xF4EBDE, new Item.Properties()).setRegistryName("fur:spawn_egg_beelzebub"));    	
    	*/
    }
    
    private static void spawnEgg(String entityName, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2) {
        RegistryObject<Item> item = DEF_REG.register("spawn_egg_" + entityName, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties()));
        creativeTabSpawnEggMap.add(item);
    }
    
    public static void SetCompostables() {
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.HYPHAE.get(), 0.50F);
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.CACTUS_FRUIT.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.CACTUS_THORN.get(), 0.30F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.CACTOID_SPROUT.get().asItem(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.GLOWSHROOM.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.BLOODTOOTH_SHROOM.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.CORDY_SHROOM.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.VEIL_SHROOM.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.GLOWSHROOM_BLOCK_STEM.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.GLOWSHROOM_BLOCK_CAP.get().asItem(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.MYCELIAL_MAT.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.MYCELIAL_VEIL.get().asItem(), 0.30F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.MYCELIAL_TENDRILS.get().asItem(), 0.30F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.LUMINOUS_FILAMENT.get().asItem(), 0.50F);
    }
}
