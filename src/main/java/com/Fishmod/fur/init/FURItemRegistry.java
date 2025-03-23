package com.Fishmod.fur.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.item.MoltenMeatItem;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURItemRegistry {
	public static List<RegistryObject<Item>> creativeTabSpawnEggMap = new ArrayList<RegistryObject<Item>>();
	public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Item> SHARP_FANG = DEF_REG.register("sharp_fang", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CHITIN = DEF_REG.register("chitin", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM = DEF_REG.register("ectoplasm", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_MASS = DEF_REG.register("ectoplasm_mass", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ECTOPLASM_INGOT = DEF_REG.register("ectoplasm_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MOLTEN_MEAT = DEF_REG.register("molten_meat", () -> new MoltenMeatItem(new Item.Properties().durability(0).stacksTo(64).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_ALLOY = DEF_REG.register("molten_alloy", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> UNDYINGHEART = DEF_REG.register("undyingheart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	
    static {
        spawnEgg("lavacow", FUREntityRegistry.LAVACOW, 0x312C36, 0xFFDE00);
        spawnEgg("foglet", FUREntityRegistry.FOGLET, 0xCBD3B9, 0x41352F);
    }
    
    private static void spawnEgg(String entityName, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2) {
        RegistryObject<Item> item = DEF_REG.register("spawn_egg_" + entityName, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties()));
        creativeTabSpawnEggMap.add(item);
    }
}
