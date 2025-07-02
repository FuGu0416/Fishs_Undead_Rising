package com.Fishmod.fur.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.item.FURItem;
import com.Fishmod.fur.item.FURWeaponItem;
import com.Fishmod.fur.item.MoltenAxeItem;
import com.Fishmod.fur.item.MoltenMeatItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURItemRegistry {
	public static List<RegistryObject<Item>> creativeTabSpawnEggMap = new ArrayList<RegistryObject<Item>>();
	public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Item> SHARP_FANG = DEF_REG.register("sharp_fang", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BONE_SWORD = DEF_REG.register("bone_sword", () -> new FURWeaponItem(new Item.Properties(), Tiers.IRON, 2, -2.4F, Items.BONE_BLOCK, true));
	public static final RegistryObject<Item> CHITIN = DEF_REG.register("chitin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM = DEF_REG.register("ectoplasm", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_MASS = DEF_REG.register("ectoplasm_mass", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_INGOT = DEF_REG.register("ectoplasm_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MOLTEN_MEAT = DEF_REG.register("molten_meat", () -> new MoltenMeatItem(new Item.Properties().durability(0).stacksTo(64).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_ALLOY = DEF_REG.register("molten_alloy", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> MOLTEN_AXE = DEF_REG.register("molten_axe", () -> new MoltenAxeItem(new Item.Properties().fireResistant(), Tiers.DIAMOND, 5.0F, -3.0F, MOLTEN_ALLOY.get(), ParticleTypes.FLAME));
	public static final RegistryObject<Item> UNDYINGHEART = DEF_REG.register("undyingheart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> FOUL_BRISTLE = DEF_REG.register("foul_bristle", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> IMP_HORN = DEF_REG.register("imp_horn", () -> new FURItem(new Item.Properties()));
	public static final RegistryObject<Item> CURSED_FABRIC = DEF_REG.register("cursed_fabric", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CURSEWEAVE_CLOTH = DEF_REG.register("curseweave_cloth", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PIRANHA_BUCKET = DEF_REG.register("piranha_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.PIRANHA.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> PIRANHA_RAW = DEF_REG.register("piranha_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> PIRANHA_COOKED = DEF_REG.register("piranha_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	public static final RegistryObject<Item> SWARMER_BUCKET = DEF_REG.register("swarmer_bucket", () -> new MobBucketItem(() -> FUREntityRegistry.SWARMER.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> SWARMER_RAW = DEF_REG.register("swarmer_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
	public static final RegistryObject<Item> SWARMER_COOKED = DEF_REG.register("swarmer_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build())));
	
    static {
        spawnEgg("lavacow", FUREntityRegistry.LAVACOW, 0x312C36, 0xFFDE00);
        spawnEgg("foglet", FUREntityRegistry.FOGLET, 0xCBD3B9, 0x41352F);
        spawnEgg("isnachi", FUREntityRegistry.ISNACHI, 0xB4A27E, 0x190508);
        spawnEgg("imp", FUREntityRegistry.IMP, 0xD03336, 0xFFD6A0);
        spawnEgg("seahag", FUREntityRegistry.SEAHAG, 0x44AD9A, 0x4ADC00);
        spawnEgg("piranha", FUREntityRegistry.PIRANHA, 0x3E3E3E, 0xE34600);
        spawnEgg("swarmer", FUREntityRegistry.SWARMER, 0x5D5D5D, 0x880909);
        spawnEgg("cactyrant", FUREntityRegistry.CACTYRANT, 0x649832, 0x426520);
    }
    
    private static void spawnEgg(String entityName, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2) {
        RegistryObject<Item> item = DEF_REG.register("spawn_egg_" + entityName, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties()));
        creativeTabSpawnEggMap.add(item);
    }
}
