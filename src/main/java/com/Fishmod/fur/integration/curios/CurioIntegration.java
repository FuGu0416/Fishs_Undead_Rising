package com.Fishmod.fur.integration.curios;

import java.util.function.Predicate;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CurioIntegration {

	public static ItemStack findItem(Item item, LivingEntity living) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosApi.getCuriosInventory(living)
					.resolve()
					.flatMap(handler -> handler.findFirstCurio(item))
					.map(SlotResult::stack)
					.orElse(ItemStack.EMPTY);
		}

		return ItemStack.EMPTY;
	}

	public static ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosApi.getCuriosInventory(living)
					.resolve()
					.flatMap(handler -> handler.findFirstCurio(pred))
					.map(SlotResult::stack)
					.orElse(ItemStack.EMPTY);
		}

		return ItemStack.EMPTY;
	}
}
