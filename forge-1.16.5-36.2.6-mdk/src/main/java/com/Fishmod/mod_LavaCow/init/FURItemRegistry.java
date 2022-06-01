package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.block.ScarecrowHeadBlock;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.item.CactoidPotItem;
import com.Fishmod.mod_LavaCow.item.CactusFruitItem;
import com.Fishmod.mod_LavaCow.item.ChitinArmorItem;
import com.Fishmod.mod_LavaCow.item.CrownItem;
import com.Fishmod.mod_LavaCow.item.CursedBandageItem;
import com.Fishmod.mod_LavaCow.item.DreamCatcherItem;
import com.Fishmod.mod_LavaCow.item.FURFishBucketItem;
import com.Fishmod.mod_LavaCow.item.FURItem;
import com.Fishmod.mod_LavaCow.item.FURItemTier;
import com.Fishmod.mod_LavaCow.item.FURRangedItem;
import com.Fishmod.mod_LavaCow.item.FURThrowableItem;
import com.Fishmod.mod_LavaCow.item.FURWeaponItem;
import com.Fishmod.mod_LavaCow.item.FamineArmorItem;
import com.Fishmod.mod_LavaCow.item.FelArmorItem;
import com.Fishmod.mod_LavaCow.item.FissionPotionItem;
import com.Fishmod.mod_LavaCow.item.FrozenThighItem;
import com.Fishmod.mod_LavaCow.item.GoldenHeartItem;
import com.Fishmod.mod_LavaCow.item.IntestineItem;
import com.Fishmod.mod_LavaCow.item.MoltenAxeItem;
import com.Fishmod.mod_LavaCow.item.MoltenBeefItem;
import com.Fishmod.mod_LavaCow.item.NetherStewItem;
import com.Fishmod.mod_LavaCow.item.RavenWhistleItem;
import com.Fishmod.mod_LavaCow.item.ScarecrowHeadItem;
import com.Fishmod.mod_LavaCow.item.SkeletonKingCrownItem;
import com.Fishmod.mod_LavaCow.item.SwineArmorItem;
import com.Fishmod.mod_LavaCow.item.UndyingHeartItem;
import com.Fishmod.mod_LavaCow.item.VespaShieldItem;
import com.google.common.collect.Lists;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURItemRegistry {
	public static List<EffectInstance> Effect_netherstew = Lists.newArrayList(
		new EffectInstance(Effects.DAMAGE_BOOST, 40*20, 1),
		new EffectInstance(Effects.DAMAGE_RESISTANCE, 40*20, 1),
		new EffectInstance(Effects.MOVEMENT_SPEED, 40*20, 1),
		new EffectInstance(Effects.ABSORPTION, 40*20, 1)
	);
	
	public static final Item NETHERSTEW = new NetherStewItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> Effect_netherstew.get(new Random().nextInt(Effect_netherstew.size())), 1F).build()), 1).setRegistryName("mod_lavacow:netherstew");
	public static final Item FISSIONPOTION = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).tab(mod_LavaCow.TAB).stacksTo(1).rarity(Rarity.COMMON), SoundEvents.SLIME_SQUISH, ParticleTypes.HAPPY_VILLAGER).setRegistryName("mod_lavacow:fissionpotion");
	public static final Item HYPHAE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:hyphae");
	public static final Item PARASITE_COMMON = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_common");
	public static final Item PARASITE_DESERT = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_desert");
	public static final Item PARASITE_JUNGLE = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.POISON, 4*20, 0), 0.3F).build()), 2).setRegistryName("mod_lavacow:parasite_item_jungle");
	public static final Item PARASITE_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).build())).setRegistryName("mod_lavacow:parasite_item_cooked");
	public static final Item PIRANHA = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.1F).build())).setRegistryName("mod_lavacow:piranha_item");
	public static final Item PIRANHA_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.8F).build())).setRegistryName("mod_lavacow:piranha_item_cooked");
	public static final Item MOUSSE = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(3).saturationMod(0.1F).build()), 32, UseAction.DRINK, 0).setRegistryName("mod_lavacow:mousse");
	public static final Item MEATBALL = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.6F).build()), 8, UseAction.EAT, 0).setRegistryName("mod_lavacow:meatball");
	public static final Item SHARPTOOTH = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:sharptooth");
	public static final Item BONESWORD = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:bonesword", ItemTier.IRON, 2, -2.4F, Items.BONE_BLOCK);
	public static final Item INTESTINE = new IntestineItem().setRegistryName("mod_lavacow:intestine");
	public static final Item FROZENTHIGH = new FrozenThighItem(new Item.Properties().stacksTo(1).durability(64).tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(10).saturationMod(1.2F).alwaysEat().effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 4*20, 4), 0.6F).build())).setRegistryName("mod_lavacow:frozenthigh");
	public static final Item POISONSPORE = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:poisonspore");
	public static final Item UNDYINGHEART = new UndyingHeartItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:undyingheart");
	public static final Item GOLDENHEART = new GoldenHeartItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).stacksTo(1).durability(FURConfig.GoldenHeart_dur.get())).setRegistryName("mod_lavacow:goldenheart");
	public static final Item MOLTENBEEF = new MoltenBeefItem("moltenbeef").setRegistryName("mod_lavacow:moltenbeef");
	public static final Item MOLTENAXE = new MoltenAxeItem().setRegistryName("mod_lavacow:moltenaxe");
	public static final Item MOLTENHAMMER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant(), "mod_lavacow:moltenhammer", ItemTier.DIAMOND, 3, -2.4F, Items.GUNPOWDER);
	public static final Item MOOTENHEART = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), 0, UseAction.NONE, 1).setRegistryName("mod_lavacow:mootenheart");//new ItemBaubleTrinket("mootenheart", mod_LavaCow.TAB_ITEMS, EnumRarity.RARE, true);
	public static final Item POTION_OF_MOOTEN_LAVA = new FissionPotionItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).tab(mod_LavaCow.TAB).stacksTo(1).rarity(Rarity.EPIC), SoundEvents.FIREWORK_ROCKET_BLAST, ParticleTypes.LAVA).setRegistryName("mod_lavacow:potion_of_mooten_lava");
	public static final Item PLAGUED_PORKCHOP = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new EffectInstance(Effects.DIG_SLOWDOWN, 30*20, 0), 0.8F).build())).setRegistryName("mod_lavacow:plagued_porkchop");
	public static final Item GREEN_BACON_AND_EGGS = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(10).saturationMod(1.2F).meat().alwaysEat().effect(() -> new EffectInstance(Effects.DIG_SPEED, 60*20, 0), 1F).build()), 1).setRegistryName("mod_lavacow:green_bacon_and_eggs");
	public static final Item PIGBOARHIDE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:pigboarhide");
	public static final Item SCYTHE_CLAW = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:scythe_claw");
	public static final Item REAPERS_SCYTHE = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:reapers_scythe", ItemTier.DIAMOND, 8, -3.1F, FURItemRegistry.SCYTHE_CLAW);
	public static final Item FAMINE = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:famine", ItemTier.DIAMOND, 0, -1.2F, FURItemRegistry.SCYTHE_CLAW);
	public static final Item MIMIC_CLAW = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(3).saturationMod(0.3F).meat().effect(() -> new EffectInstance(Effects.CONFUSION, 60*20, 0), 0.3F).build()), 64, UseAction.EAT, 0).setRegistryName("mod_lavacow:mimic_claw");
	public static final Item MIMIC_CLAW_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(8).saturationMod(0.8F).meat().build())).setRegistryName("mod_lavacow:mimic_claw_cooked");
	public static final Item SILKY_SLUDGE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:silky_sludge");
	public static final Item SLUDGE_WAND = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:sludge_wand", ItemTier.GOLD, -2, -3.3F, FURItemRegistry.SILKY_SLUDGE);
	public static final Item BURNTOVIPOSITOR = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant()).setRegistryName("mod_lavacow:burntovipositor");
	public static final Item WAR = new FURRangedItem("mod_lavacow:war", Items.FIRE_CHARGE, FUREntityRegistry.WAR_SMALL_FIREBALL, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(384));
	public static final Item MOLTENPAN = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).setNoRepair().fireResistant(), "mod_lavacow:moltenpan", ItemTier.DIAMOND, 1, -3.0F, Items.GUNPOWDER);
	public static final Item FELARMOR_HELMET = new FelArmorItem(EquipmentSlotType.HEAD, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant(), 0.2F).setRegistryName("mod_lavacow:felarmor_helmet");
	public static final Item FELARMOR_CHESTPLATE = new FelArmorItem(EquipmentSlotType.CHEST, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant(), 0.5F).setRegistryName("mod_lavacow:felarmor_chestplate");
	public static final Item FELARMOR_LEGGINGS = new FelArmorItem(EquipmentSlotType.LEGS, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant(), 0.2F).setRegistryName("mod_lavacow:felarmor_leggings");
	public static final Item FELARMOR_BOOTS = new FelArmorItem(EquipmentSlotType.FEET, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE).fireResistant(), 0.1F).setRegistryName("mod_lavacow:felarmor_boots");
	public static final Item PTERA_WING_JUNGLE = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(4).saturationMod(0.1F).meat().alwaysEat().effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 2), 0.8F).build()), 2).setRegistryName("mod_lavacow:ptera_wing_jungle");
	public static final Item PTERA_WING_DESERT = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(4).saturationMod(0.1F).meat().alwaysEat().effect(() -> new EffectInstance(Effects.HUNGER, 30*20, 2), 0.8F).build()), 2).setRegistryName("mod_lavacow:ptera_wing_desert");
	public static final Item PTERA_WING_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(8).saturationMod(0.8F).meat().build())).setRegistryName("mod_lavacow:ptera_wing_cooked");
	public static final Item POISONSTINGER = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:poisonstinger");
	public static final Item VESPA_CARAPACE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:vespa_carapace");
	public static final Item TOOTH_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:tooth_dagger", ItemTier.STONE, 1, -2.0F, FURItemRegistry.SHARPTOOTH).setNoDescription();
	public static final Item VESPA_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:vespa_dagger", ItemTier.IRON, 2, -2.4F, FURItemRegistry.POISONSTINGER).setNoDescription();
	public static final Item SAUSAGE_ROLL = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(8).saturationMod(0.8F).meat().build())).setRegistryName("mod_lavacow:sausage_roll");
	public static final Item SWINEMASK = new SwineArmorItem(EquipmentSlotType.HEAD, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_helmet");
	public static final Item SWINEARMOR_CHESTPLATE = new SwineArmorItem(EquipmentSlotType.CHEST, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_chestplate");
	public static final Item SWINEARMOR_LEGGINGS = new SwineArmorItem(EquipmentSlotType.LEGS, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_leggings");
	public static final Item SWINEARMOR_BOOTS = new SwineArmorItem(EquipmentSlotType.FEET, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swinearmor_boots");
	public static final Item SWARMER = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(2).saturationMod(0.1F).build())).setRegistryName("mod_lavacow:swarmer_item");
	public static final Item SWARMER_COOKED = new Item(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.8F).build())).setRegistryName("mod_lavacow:swarmer_item_cooked");
	public static final Item PIRANHALAUNCHER = new FURRangedItem("mod_lavacow:piranhalauncher", SWARMER, FUREntityRegistry.PIRANHA_LAUNCHER, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(384));
	public static final Item CURSED_FABRIC = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:cursed_fabric");
	public static final Item CURSED_BANDAGE = new CursedBandageItem(new Item.Properties().tab(mod_LavaCow.TAB), 0).setRegistryName("cursed_bandage");
	public static final Item CURSED_BANDAGE_DMG = new CursedBandageItem(new Item.Properties().tab(mod_LavaCow.TAB), 1).setRegistryName("cursed_bandage_dmg");
	public static final Item CURSED_BANDAGE_HEAL = new CursedBandageItem(new Item.Properties().tab(mod_LavaCow.TAB), 2).setRegistryName("cursed_bandage_heal");
	public static final Item CURSED_BANDAGE_RES = new CursedBandageItem(new Item.Properties().tab(mod_LavaCow.TAB), 3).setRegistryName("cursed_bandage_res");
	public static final Item HOLY_SLUDGE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:holy_sludge");
	public static final Item HOLY_GRENADE = new FURThrowableItem("mod_lavacow:holy_grenade");
	public static final Item FEATHER_BLACK = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:feather_black");
	public static final Item SHATTERED_ICE = new FURItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(1).saturationMod(1.8F).build()), 32, UseAction.DRINK, 0).setRegistryName("mod_lavacow:shattered_ice");
	public static final Item DREAMCATCHER = new DreamCatcherItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).stacksTo(1).durability(0)).setRegistryName("mod_lavacow:dreamcatcher");	
	public static final Item RAVEN_WHISTLE = new RavenWhistleItem(new Item.Properties().tab(mod_LavaCow.TAB).stacksTo(1)).setRegistryName("mod_lavacow:raven_whistle");
	public static final Item ACIDICHEART = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:acidicheart");
	public static final Item FAMINEARMOR_HELMET = new FamineArmorItem(EquipmentSlotType.HEAD, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:faminearmor_helmet");
	public static final Item FAMINEARMOR_CHESTPLATE = new FamineArmorItem(EquipmentSlotType.CHEST, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:faminearmor_chestplate");
	public static final Item FAMINEARMOR_LEGGINGS = new FamineArmorItem(EquipmentSlotType.LEGS, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:faminearmor_leggings");
	public static final Item FAMINEARMOR_BOOTS = new FamineArmorItem(EquipmentSlotType.FEET, (new Item.Properties()).tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:faminearmor_boots");
	public static final Item FOUL_BRISTLE = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:foul_bristle");
	public static final Item ECTOPLASM = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:ectoplasm");
	public static final Item GHOSTJELLY = new NetherStewItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().build()), 1).setRegistryName("mod_lavacow:ghostjelly");
	public static final Item SHRIEK_CORD = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE)).setRegistryName("mod_lavacow:shriek_cord");
	public static final Item CHITIN = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:chitin");
	public static final Item GHOSTBOMB = new FURThrowableItem("mod_lavacow:ghostbomb");
	public static final Item SONICBOMB = new FURThrowableItem("mod_lavacow:sonicbomb");
	public static final Item BONE_STEW = new NetherStewItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().effect(() -> new EffectInstance(Effects.ABSORPTION, 20*20, 1), 1.0F).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 20*20, 0), 1.0F).build()), 1).setRegistryName("mod_lavacow:bonestew");
	public static final Item VESPA_SHIELD = new VespaShieldItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties()).durability(504).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:vespa_shield");
	public static final Item FROZEN_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:frozen_dagger", ItemTier.WOOD, 2, -2.4F, FURItemRegistry.SHATTERED_ICE);
	public static final Item SPECTRAL_DAGGER = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.COMMON), "mod_lavacow:spectral_dagger", FURItemTier.SPECTRAL, -1, -2.4F, FURItemRegistry.ECTOPLASM);
	public static final Item HATRED_SHARD = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:hatred_shard");
	public static final Item STAINED_KINGS_CROWN = new CrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON).stacksTo(1), 0).setRegistryName("mod_lavacow:stained_kings_crown");
	public static final Item CURSED_KINGS_CROWN = new CrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON).stacksTo(1), 1).setRegistryName("mod_lavacow:cursed_kings_crown");
	public static final Item SKELETONKING_CROWN = new SkeletonKingCrownItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant()).setRegistryName("mod_lavacow:skeletonking_crown");
	public static final Item EMBLEM_OF_KING = new Item(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.UNCOMMON)).setRegistryName("mod_lavacow:emblem_of_king");
	public static final Item BEAST_CLAW = new FURWeaponItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC)), "mod_lavacow:beast_claw", ItemTier.DIAMOND, 3, -2.4F, FURItemRegistry.SCYTHE_CLAW);
	public static final Item CURSEWEAVE_CLOTH = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:curseweave_cloth");
	public static final Item UNDERTAKER_SHOVEL = new FURWeaponItem(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE), "mod_lavacow:undertaker_shovel", ItemTier.IRON, 2, -3.0F, FURItemRegistry.HATRED_SHARD);
	public static final Item SKELETONKING_MACE = new FURWeaponItem(mod_LavaCow.PROXY.setupISTER(new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.EPIC).fireResistant()), "mod_lavacow:skeletonking_mace", ItemTier.DIAMOND, 12, -3.2F, FURItemRegistry.HATRED_SHARD);
	public static final Item PIRANHA_BUCKET = new FishBucketItem(() -> FUREntityRegistry.PIRANHA, () -> Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:piranha_bucket");
	public static final Item SWARMER_BUCKET = new FishBucketItem(() -> FUREntityRegistry.SWARMER, () -> Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:swarmer_bucket");
	public static final Item IMP_HORN = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:imp_horn");
	public static final Item CHITINARMOR_HELMET = new ChitinArmorItem(EquipmentSlotType.HEAD, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:chitinarmor_helmet");
	public static final Item CHITINARMOR_CHESTPLATE = new ChitinArmorItem(EquipmentSlotType.CHEST, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:chitinarmor_chestplate");
	public static final Item CHITINARMOR_LEGGINGS = new ChitinArmorItem(EquipmentSlotType.LEGS, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:chitinarmor_leggings");
	public static final Item CHITINARMOR_BOOTS = new ChitinArmorItem(EquipmentSlotType.FEET, (new Item.Properties()).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:chitinarmor_boots");
	public static final Item CACTUS_THORN = new Item(new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:cactus_thorn");
	public static final Item CACTUS_FRUIT = new CactusFruitItem(new Item.Properties().tab(mod_LavaCow.TAB).food(new Food.Builder().nutrition(4).saturationMod(0.2F).effect(() -> new EffectInstance(FUREffectRegistry.THORNED, 60*20, 0), 1.0F).build()), 1).setRegistryName("mod_lavacow:cactus_fruit");
	public static final Item THORN_SHOOTER = new FURRangedItem("mod_lavacow:thorn_shooter", CACTUS_THORN, FUREntityRegistry.CACTUS_THORN, new Item.Properties().tab(mod_LavaCow.TAB).rarity(Rarity.RARE).durability(768));
	public static final Item SALAMANDER_BUCKET = new FURFishBucketItem(FUREntityRegistry.SALAMANDER, () -> Fluids.LAVA, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:salamander_bucket");
	public static final Item CACTOID_POT = new CactoidPotItem(FUREntityRegistry.CACTOID, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:cactoid_pot");
	
    public static final BannerPattern PATTERN_SKELETONKING = addBanner("skeletonking");
    public static final BannerPattern PATTERN_WENDIGO = addBanner("wendigo");
    
    private static BannerPattern addBanner(String name) {
        return BannerPattern.create(name.toUpperCase(), name, "mod_lavacow." + name, true);
    }
    
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {  	
        try {
            for (Field f : FURItemRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    event.getRegistry().register((Item) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        
        try {
            for (Field f : FURBlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                	BlockItem blockItem;
                    Item.Properties props = new Item.Properties();
                    props.tab(mod_LavaCow.TAB);
                    
                    if(obj instanceof AirBlock)
                    	continue;
                    
                    if(obj instanceof ScarecrowHeadBlock)
                    	blockItem = new ScarecrowHeadItem((Block) obj, mod_LavaCow.PROXY.setupISTER(props));
                    else
                    	blockItem = new BlockItem((Block) obj, props);
                    blockItem.setRegistryName(((Block) obj).getRegistryName());
                    event.getRegistry().register(blockItem);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.LAVACOW, 0xFF2724, 0xFFDA24, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_lavacow"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.MYCOSIS, 0xBCE0AC, 0x83631D, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_mycosis"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.PARASITE, 0xAAFFEE, 0xBBFFEE, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_parasite"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.FOGLET, 0xCBD3B9, 0x41352F, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_foglet"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.IMP, 0xD03336, 0xFFD6A0, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_imp"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.FRIGID, 0xAFE0E2, 0x59484F, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_frigid"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.UNDEADSWINE, 0x8A9B8A, 0x3E5C5A, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_undeadswine"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SALAMANDER, 0x260606, 0xF4F142, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_salamander"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.WENDIGO, 0x30180C, 0xFFFAEC, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_wendigo"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.MIMIC, 0xE168FF, 0x070000, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_mimic"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SLUDGELORD, 0x282119, 0x81DDFF, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_sludgelord"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.RAVEN, 0x130D19, 0x192B3E, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_raven"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SEAGULL, 0xEEEEEE, 0x121212, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_seagull"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.PTERA, 0x208938, 0xD61717, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_ptera"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.VESPA, 0x85E214, 0xDA3119, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_vespa"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SCARECROW, 0x5A4F3B, 0xE9CD84, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_scarecrow"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.PIRANHA, 0x3E3E3E, 0xE34600, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_piranha"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SWARMER, 0x5D5D5D, 0x880909, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_swarmer"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.BONEWORM, 0x989898, 0x410E0E, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_boneworm"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.PINGU, 0x77A9FF, 0x797979, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_pingu"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.UNDERTAKER, 0x3c424b, 0xA3AC93, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_undertaker"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.UNBURIED, 0xD4D9BA, 0x292C32, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_unburied"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.GHOSTRAY, 0x233A41, 0x7AFDFD, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_ghostray"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.BANSHEE, 0xA2A78D, 0x34363A, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_banshee"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.WETA, 0x845336, 0xEACAA7, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_weta"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.AVATON, 0xAAA48E, 0x222829, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_avaton"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.FORSAKEN, 12698049, 4802889, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_forsaken"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.SKELETONKING, 0x2F2A2A, 0xA2A1A1, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_skeletonking"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.MUMMY, 0xE9DAAE, 0x9A8157, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_mummy"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.CACTYRANT, 0x9FE664, 0xE0D6B1, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_cactyrant"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.CACTOID, 0xABE97D, 0xE5DECD, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_cactoid"));
    	event.getRegistry().register(new ForgeSpawnEggItem(() -> FUREntityRegistry.WARPEDFIREFLY, 0x0F9373, 0xFE8738, new Item.Properties().tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:spawn_egg_warpedfirefly"));
    	
    	event.getRegistry().register(new BannerPatternItem(PATTERN_SKELETONKING, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:banner_pattern_skeletonking"));
    	event.getRegistry().register(new BannerPatternItem(PATTERN_WENDIGO, (new Item.Properties()).stacksTo(1).tab(mod_LavaCow.TAB)).setRegistryName("mod_lavacow:banner_pattern_wendigo"));
    	
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.GLOWSHROOM.asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.BLOODTOOTH_SHROOM.asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.CORDY_SHROOM.asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURBlockRegistry.VEIL_SHROOM.asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.HYPHAE.asItem(), 0.50F);
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.CACTUS_FRUIT.asItem(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FURItemRegistry.CACTUS_THORN.asItem(), 0.30F);
    }
}
