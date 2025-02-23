package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FURItemRegistry {
	public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, mod_LavaCow.MODID);
	
	public static final RegistryObject<Item> UNDYINGHEART = DEF_REG.register("undyingheart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
}
