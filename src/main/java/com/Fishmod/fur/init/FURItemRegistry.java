package com.Fishmod.fur.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.item.CactusFruitItem;
import com.Fishmod.fur.item.ChitinArmorItem;
import com.Fishmod.fur.item.DiseasedBreadItem;
import com.Fishmod.fur.item.EntityBucketItem;
import com.Fishmod.fur.item.FURItem;
import com.Fishmod.fur.item.FURStewItem;
import com.Fishmod.fur.item.FURThrowableItem;
import com.Fishmod.fur.item.FURWeaponItem;
import com.Fishmod.fur.item.FamineArmorItem;
import com.Fishmod.fur.item.FrozenThighItem;
import com.Fishmod.fur.item.GhostlyArmorItem;
import com.Fishmod.fur.item.MoltenArmorItem;
import com.Fishmod.fur.item.MoltenAxeItem;
import com.Fishmod.fur.item.MoltenMeatItem;
import com.Fishmod.fur.item.UndyingHeartItem;
import com.Fishmod.fur.item.WetaHoeItem;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.effect.MobEffectInstance;

public class FURItemRegistry {
	public static List<RegistryObject<Item>> creativeTabSpawnEggMap = new ArrayList<RegistryObject<Item>>();
	public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Item> SHARP_FANG = DEF_REG.register("sharp_fang", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BONE_SWORD = DEF_REG.register("bone_sword", () -> new FURWeaponItem(new Item.Properties(), Tiers.IRON, 2, -2.4F, 0.0D, Items.BONE_BLOCK, true));
	public static final RegistryObject<Item> CHITIN = DEF_REG.register("chitin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_HELMET = DEF_REG.register("chitin_armor_helmet", () -> new ChitinArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_CHESTPLATE = DEF_REG.register("chitin_armor_chestplate", () -> new ChitinArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_LEGGINGS = DEF_REG.register("chitin_armor_leggings", () -> new ChitinArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> CHITIN_ARMOR_BOOTS = DEF_REG.register("chitin_armor_boots", () -> new ChitinArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));
	public static final RegistryObject<Item> BASIC_BOMB = DEF_REG.register("basic_bomb", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> GHOSTBOMB = DEF_REG.register("ghostbomb", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> HOLY_GRENADE = DEF_REG.register("holy_grenade", () -> new FURThrowableItem(new Item.Properties()));
	public static final RegistryObject<Item> SONICBOMB = DEF_REG.register("sonicbomb", () -> new FURThrowableItem(new Item.Properties()));
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
	public static final RegistryObject<Item> MOLTEN_HAMMER = DEF_REG.register("molten_hammer", () -> new FURWeaponItem(new Item.Properties().fireResistant(), Tiers.DIAMOND, 3, -2.4F, 0.0D, Items.GUNPOWDER, true));
	public static final RegistryObject<Item> MOLTEN_ARMOR_HELMET = DEF_REG.register("molten_armor_helmet", () -> new MoltenArmorItem(ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.2F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_CHESTPLATE = DEF_REG.register("molten_armor_chestplate", () -> new MoltenArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.5F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_LEGGINGS = DEF_REG.register("molten_armor_leggings", () -> new MoltenArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.2F));
	public static final RegistryObject<Item> MOLTEN_ARMOR_BOOTS = DEF_REG.register("molten_armor_boots", () -> new MoltenArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE).fireResistant(), 0.1F));
	public static final RegistryObject<Item> UNDYINGHEART = DEF_REG.register("undyingheart", () -> new UndyingHeartItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MOOTENHEART = DEF_REG.register("mooten_heart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 1));
	public static final RegistryObject<Item> SOULFIREHEART = DEF_REG.register("soulfireheart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 1));
	public static final RegistryObject<Item> ACIDICHEART = DEF_REG.register("acidicheart", () -> new FURItem(new Item.Properties().rarity(Rarity.RARE), 0, UseAnim.NONE, 0));
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
	public static final RegistryObject<Item> PIRANHA_RAW = DEF_REG.register("piranha_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> PIRANHA_COOKED = DEF_REG.register("piranha_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> SWARMER_BUCKET = DEF_REG.register("swarmer_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.SWARMER.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));
	public static final RegistryObject<Item> SWARMER_RAW = DEF_REG.register("swarmer_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> SWARMER_COOKED = DEF_REG.register("swarmer_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> CACTUS_THORN = DEF_REG.register("cactus_thorn", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CACTUS_FRUIT = DEF_REG.register("cactus_fruit", () -> new CactusFruitItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.2F).effect(() -> new MobEffectInstance(FUREffectRegistry.THORNED.get(), 60 * 20, 0), 1.0F).build()), 1));
	public static final RegistryObject<Item> CACTOID_POT = DEF_REG.register("cactoid_pot", () -> new EntityBucketItem(() -> FUREntityRegistry.CACTOID.get(), Items.FLOWER_POT, (new Item.Properties()).stacksTo(1)));
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
	public static final RegistryObject<Item> FROZENTHIGH = DEF_REG.register("frozenthigh", () -> new FrozenThighItem(new Item.Properties().stacksTo(1).durability(64).food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4*20, 4), 0.6F).build())));
	public static final RegistryObject<Item> POISONSPORE = DEF_REG.register("poisonspore", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> MIMIC_CLAW = DEF_REG.register("mimic_claw", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60*20, 0), 0.3F).build()), 64, UseAnim.EAT, 0));
	public static final RegistryObject<Item> MIMIC_CLAW_COOKED = DEF_REG.register("mimic_claw_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build())));
	public static final RegistryObject<Item> KUNG_PAO_CHICKEN = DEF_REG.register("kung_pao_chicken", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).alwaysEat().meat().effect(() -> new MobEffectInstance(FUREffectRegistry.IMMOLATION.get(), 60*20, 1), 1.0F).build()), UseAnim.EAT, 1));
	public static final RegistryObject<Item> BOABING = DEF_REG.register("baobing", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(FUREffectRegistry.THORNED.get(), 60*20, 1), 1.0F).build()), UseAnim.EAT, 1));	
	public static final RegistryObject<Item> GHOSTJELLY = DEF_REG.register("ghostjelly", () -> new FURStewItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().meat().effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 6*20, 2), 1.0F).build()), UseAnim.EAT, 1));
	public static final RegistryObject<Item> UNDERTAKER_SHOVEL = DEF_REG.register("undertaker_shovel", () -> new FURWeaponItem(new Item.Properties().rarity(Rarity.RARE), Tiers.IRON, 2, -3.0F, 0.0D, FURItemRegistry.HATRED_SHARD.get(), true));
	public static final RegistryObject<Item> SHRIEK_CORD = DEF_REG.register("shriek_cord", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PTERA_WING = DEF_REG.register("ptera_wing", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1F).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 30*20, 2), 0.8F).build()), 64, UseAnim.EAT, 0));
	public static final RegistryObject<Item> PTERA_WING_COOKED = DEF_REG.register("ptera_wing_cooked", () -> new FURItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build()), 64, UseAnim.EAT, 0));
	
	/*
	public static final RegistryObject<Item> FISSIONPOTION = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).tab(mod_LavaCow.TAB).stacksTo(1).rarity(Rarity.COMMON), SoundEvents.SLIME_SQUISH, ParticleTypes.HAPPY_VILLAGER).setRegistryName("mod_lavacow:fissionpotion");	
	public static final RegistryObject<Item> PARASITE_COMMON = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_common");
	public static final RegistryObject<Item> PARASITE_DESERT = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_desert");
	public static final RegistryObject<Item> PARASITE_JUNGLE = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.POISON, 4*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_jungle");
	public static final RegistryObject<Item> PARASITE_MAGGOT = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_maggot");
	public static final RegistryObject<Item> PARASITE_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).build())).setRegistryName("mod_lavacow:parasite_item_cooked");
	public static final RegistryObject<Item> INTESTINE = new IntestineItem().setRegistryName("mod_lavacow:intestine");
	public static final RegistryObject<Item> POTION_OF_MOOTEN_LAVA = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).tab(mod_LavaCow.TAB).stacksTo(1).rarity(Rarity.EPIC), SoundEvents.FIREWORK_ROCKET_BLAST, ParticleTypes.LAVA).setRegistryName("mod_lavacow:potion_of_mooten_lava");
	public static final RegistryObject<Item> PLAGUED_PORKCHOP = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new EffectInstance(Effects.DIG_SLOWDOWN, 30*20, 0), 0.8F).build())).setRegistryName("mod_lavacow:plagued_porkchop");
	public static final RegistryObject<Item> GREEN_BACON_AND_EGGS = new NetherStewItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(10).saturationMod(1.2F).meat().alwaysEat().effect(() -> new EffectInstance(Effects.DIG_SPEED, 60*20, 0), 1F).build()), UseAction.EAT, 1).setRegistryName("mod_lavacow:green_bacon_and_eggs");
	public static final RegistryObject<Item> PIGBOARHIDE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:pigboarhide");
	public static final RegistryObject<Item> SILKY_SLUDGE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:silky_sludge");
	public static final RegistryObject<Item> SLUDGE_WAND = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:sludge_wand", ItemTier.GOLD, -2, -3.3F, FURItemRegistry.SILKY_SLUDGE);
	public static final RegistryObject<Item> BURNTOVIPOSITOR = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant()).setRegistryName("mod_lavacow:burntovipositor");
	public static final RegistryObject<Item> WAR = new FURRangedItem("mod_lavacow:war", Items.FIRE_CHARGE, FUREntityRegistry.WAR_SMALL_FIREBALL, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(384));
	public static final RegistryObject<Item> POISONSTINGER = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:poisonstinger");
	public static final RegistryObject<Item> VESPA_CARAPACE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:vespa_carapace");
	public static final RegistryObject<Item> TOOTH_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:tooth_dagger", ItemTier.STONE, 1, -2.0F, FURItemRegistry.SHARPTOOTH).setNoDescription();
	public static final RegistryObject<Item> VESPA_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:vespa_dagger", ItemTier.IRON, 2, -2.4F, FURItemRegistry.POISONSTINGER);
	public static final RegistryObject<Item> SAUSAGE_ROLL = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(8).saturationMod(0.8F).meat().build())).setRegistryName("mod_lavacow:sausage_roll");
	public static final RegistryObject<Item> SWINEMASK = new SwineArmorItem(EquipmentSlotType.HEAD, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_helmet");
	public static final RegistryObject<Item> SWINEARMOR_CHESTPLATE = new SwineArmorItem(EquipmentSlotType.CHEST, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_chestplate");
	public static final RegistryObject<Item> SWINEARMOR_LEGGINGS = new SwineArmorItem(EquipmentSlotType.LEGS, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_leggings");
	public static final RegistryObject<Item> SWINEARMOR_BOOTS = new SwineArmorItem(EquipmentSlotType.FEET, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_boots");
	public static final RegistryObject<Item> PIRANHALAUNCHER = new FURRangedItem("mod_lavacow:piranhalauncher", SWARMER, FUREntityRegistry.PIRANHA_LAUNCHER, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(384));
	public static final RegistryObject<Item> CURSED_BANDAGE = new CursedBandageItem(new Item.Properties().tab(mod_LavaCow.TAB), 0).setRegistryName("cursed_bandage");
	public static final RegistryObject<Item> HOLY_SLUDGE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:holy_sludge");
	public static final RegistryObject<Item> DREAMCATCHER = new DreamCatcherItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).stacksTo(1).durability(120)).setRegistryName("mod_lavacow:dreamcatcher");	
	public static final RegistryObject<Item> RAVEN_WHISTLE = new RavenWhistleItem(new Item.Properties().tab(mod_LavaCow.TAB).stacksTo(1)).setRegistryName("mod_lavacow:raven_whistle");
	public static final RegistryObject<Item> BONE_STEW = new NetherStewItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new EffectInstance(Effects.ABSORPTION, 20*20, 1), 1.0F).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 20*20, 0), 1.0F).build()), 1).setRegistryName("mod_lavacow:bonestew");
	public static final RegistryObject<Item> VESPA_SHIELD = new VespaShieldItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties()).durability(504).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:vespa_shield");
	public static final RegistryObject<Item> FROZEN_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:frozen_dagger", ItemTier.WOOD, 2, -2.4F, FURItemRegistry.SHATTERED_ICE);
	public static final RegistryObject<Item> SPECTRAL_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:spectral_dagger", FURItemTier.SPECTRAL, -1, -2.4F, FURItemRegistry.ECTOPLASM);
	public static final RegistryObject<Item> STAINED_KINGS_CROWN = new CrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON).stacksTo(1), 0).setRegistryName("mod_lavacow:stained_kings_crown");
	public static final RegistryObject<Item> CURSED_KINGS_CROWN = new CrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON).stacksTo(1), 1).setRegistryName("mod_lavacow:cursed_kings_crown");
	public static final RegistryObject<Item> SKELETONKING_CROWN = new SkeletonKingCrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant()).setRegistryName("mod_lavacow:skeletonking_crown");
	public static final RegistryObject<Item> EMBLEM_OF_KING = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON)).setRegistryName("mod_lavacow:emblem_of_king");
	public static final RegistryObject<Item> BEAST_CLAW = new FURWeaponItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC)), "mod_lavacow:beast_claw", ItemTier.DIAMOND, 3, -2.4F, FURItemRegistry.SCYTHE_CLAW);
	public static final RegistryObject<Item> SKELETONKING_MACE = new FURWeaponItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant()), "mod_lavacow:skeletonking_mace", ItemTier.DIAMOND, 12, -3.2F, FURItemRegistry.HATRED_SHARD);
	public static final RegistryObject<Item> THORN_SHOOTER = new FURRangedItem("mod_lavacow:thorn_shooter", CACTUS_THORN, FUREntityRegistry.CACTUS_THORN, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(768));
	public static final RegistryObject<Item> SALAMANDER_BUCKET = new FURFishBucketItem(FUREntityRegistry.SALAMANDER, () -> Fluids.LAVA, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:salamander_bucket");
	public static final RegistryObject<Item> SOULFIREHAMMER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant(), "mod_lavacow:soulfirehammer", ItemTier.NETHERITE, 4, -2.4F, FURItemRegistry.ECTOPLASM_INGOT);
	public static final RegistryObject<Item> SOULFIREAXE = new MoltenAxeItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant(), "mod_lavacow:soulfireaxe", ItemTier.NETHERITE, 5.0F, -3.0F, FURItemRegistry.ECTOPLASM_INGOT, ParticleTypes.SOUL_FIRE_FLAME);
	public static final RegistryObject<Item> SOULFIREPAN = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant(), "mod_lavacow:soulfirepan", ItemTier.NETHERITE, 2, -3.0F, FURItemRegistry.ECTOPLASM_INGOT);
	public static final RegistryObject<Item> FORSAKEN_STAFF = new FURRangedItem("mod_lavacow:forsaken_staff", null, FUREntityRegistry.DEATHCOIL, new Item.Properties().tab(mod_LavaCow.TAB).durability(32));
	public static final RegistryObject<Item> SINISTER_WHETSTONE = new SinisterWhetstoneItem(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:sinister_whetstone");
	public static final RegistryObject<Item> ILLAGER_NOSE = new IllagerNoseItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().tab(mod_LavaCow.TAB))).setRegistryName("mod_lavacow:illager_nose");
	public static final RegistryObject<Item> ANCIENT_AMBER = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:ancient_amber");
	public static final RegistryObject<Item> SCARAB_SCEPTER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:scarab_scepter", ItemTier.GOLD, -2, -3.3F, FURItemRegistry.ANCIENT_AMBER);	
	public static final RegistryObject<Item> PHEROMONE_GLAND = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(1).saturationMod(0.1F).effect(() -> new EffectInstance(FUREffectRegistry.CHARMING_PHEROMONE, 60 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.CONFUSION, 10 * 20, 1), 1.0F).build()), 1).setRegistryName("mod_lavacow:pheromone_gland");
	public static final RegistryObject<Item> CHARMING_CATALYST = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).tab(mod_LavaCow.TAB).stacksTo(1).rarity(Rarity.COMMON), SoundEvents.HONEY_DRINK, ParticleTypes.HEART).setRegistryName("mod_lavacow:charming_catalyst");
	public static final RegistryObject<Item> ENIGMOTH_LARVA = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(FUREffectRegistry.VOID_DUST, 20*20, 3), 0.3F).build())).setRegistryName("mod_lavacow:enigmoth_larva");
	public static final RegistryObject<Item> ENIGMOTH_LARVA_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).build())).setRegistryName("mod_lavacow:enigmoth_larva_cooked");
	public static final RegistryObject<Item> HOLY_WATER = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:holy_water");
	public static final RegistryObject<Item> MUMMIFIED_COD = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(FUREffectRegistry.CORRODED, 16*20, 0), 0.3F).build())).setRegistryName("mod_lavacow:mummified_cod");
	public static final RegistryObject<Item> MUMMIFIED_COD_BUCKET = new FishBucketItem(() -> FUREntityRegistry.MUMMIFIEDCOD, () -> Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:mummified_cod_bucket");
	public static final RegistryObject<Item> BONE_TROUT = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:bone_trout");
	public static final RegistryObject<Item> BONE_TROUT_BUCKET = new FishBucketItem(() -> FUREntityRegistry.BONETROUT, () -> Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:bone_trout_bucket");
	public static final RegistryObject<Item> LAMPREY = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(Effects.CONFUSION, 60*20, 0), 0.3F).build())).setRegistryName("mod_lavacow:lamprey");
	public static final RegistryObject<Item> LAMPREY_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.8F).build())).setRegistryName("mod_lavacow:lamprey_cooked");
	public static final RegistryObject<Item> LAMPREY_KABAYAKI = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(8).saturationMod(0.8F).build())).setRegistryName("mod_lavacow:lamprey_kabayaki");
	public static final RegistryObject<Item> LAMPREY_BUCKET = new FishBucketItem(() -> FUREntityRegistry.LAMPREY, () -> Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:lamprey_bucket");
	public static final RegistryObject<Item> PARASITE_OVUM = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(1).saturationMod(0.1F).effect(() -> new EffectInstance(FUREffectRegistry.INFESTED, 12 * 20, 0), 0.8F).build())).setRegistryName("mod_lavacow:parasite_ovum");
	public static final RegistryObject<Item> USHABTI = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:ushabti");
	public static final RegistryObject<Item> ANKH_SCEPTER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC), "mod_lavacow:ankh_scepter", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);
	public static final RegistryObject<Item> FUNGAL_STAFF = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC), "mod_lavacow:fungal_staff", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);
	public static final RegistryObject<Item> FROZEN_GRIP = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC), "mod_lavacow:frozen_grip", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);	
	public static final RegistryObject<Item> GHOUL_CLAW = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:ghoul_claw");
	public static final RegistryObject<Item> GHOUL_ARROW = new FURArrowItem(new Item.Properties().tab(mod_LavaCow.TAB), 1).setRegistryName("mod_lavacow:ghoul_arrow");
	public static final RegistryObject<Item> FANG_ARROW = new FURArrowItem(new Item.Properties().tab(mod_LavaCow.TAB), 1).setRegistryName("mod_lavacow:fang_arrow");
	*/
	
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
        
        /*
    	spawnEgg(FUREntityRegistry.PARASITE, 0xAAFFEE, 0xBBFFEE, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_parasite"));
    	spawnEgg(FUREntityRegistry.UNDEADSWINE, 0x8A9B8A, 0x3E5C5A, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_undeadswine"));
    	spawnEgg(FUREntityRegistry.SALAMANDER, 0x260606, 0xF4F142, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_salamander"));
    	spawnEgg(FUREntityRegistry.SLUDGELORD, 0x282119, 0x81DDFF, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_sludgelord"));
    	spawnEgg(FUREntityRegistry.RAVEN, 0x130D19, 0x192B3E, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_raven"));
    	spawnEgg(FUREntityRegistry.SEAGULL, 0xEEEEEE, 0x121212, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_seagull"));
    	spawnEgg(FUREntityRegistry.VESPA, 0x85E214, 0xDA3119, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_vespa"));
    	spawnEgg(FUREntityRegistry.BONEWORM, 0x989898, 0x410E0E, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_boneworm"));
    	spawnEgg(FUREntityRegistry.PINGU, 0x77A9FF, 0x797979, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_pingu"));
    	spawnEgg(FUREntityRegistry.GHOSTRAY, 0x233A41, 0x7AFDFD, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_ghostray"));
    	spawnEgg(FUREntityRegistry.FORSAKEN, 12698049, 4802889, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_forsaken"));
    	spawnEgg(FUREntityRegistry.SKELETONKING, 0x2F2A2A, 0xA2A1A1, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_skeletonking"));
    	spawnEgg(FUREntityRegistry.WARPEDFIREFLY, 0x0F9373, 0xFE8738, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_warpedfirefly"));
    	spawnEgg(FUREntityRegistry.GRAVEROBBER, 0x40433E, 0x959B9B, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_graverobber"));
    	spawnEgg(FUREntityRegistry.GRAVEROBBERGHOST, 0x7AF2FF, 0x40433E, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_graverobberghost"));
    	spawnEgg(FUREntityRegistry.SCARAB, 0x282219, 0xFFCD55, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_scarab"));
    	spawnEgg(FUREntityRegistry.BEELZEBUB, 0x1D1B1C, 0xF4EBDE, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_beelzebub"));
    	spawnEgg(FUREntityRegistry.ENIGMOTH, 0x0D0B11, 0xA675E9, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_enigmoth"));
    	spawnEgg(FUREntityRegistry.MUMMIFIEDCOD, 0xDDC88D, 0xAF905B, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_mummified_cod"));
    	spawnEgg(FUREntityRegistry.BONETROUT, 0xDFDDCB, 0xBBB8A0, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_bone_trout"));
    	spawnEgg(FUREntityRegistry.LAMPREY, 0x70ACAE, 0xDCF2F3, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_lamprey"));
    	spawnEgg(FUREntityRegistry.GHOUL, 0xA69087, 0xF7EDD9, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_ghoul"));
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
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.CACTOID_SPROUT.get(), 0.85F);
    }
}
