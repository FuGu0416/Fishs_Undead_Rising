package com.Fishmod.mod_LavaCow.integration.curios;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioIntegration {

	@SuppressWarnings("deprecation")
	public static ItemStack findItem(Item item, LivingEntity living) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosApi.getCuriosHelper().findEquippedCurio(item, living)
					.map(ImmutableTriple::getRight)
					.orElse(ItemStack.EMPTY);
		}
		
		return ItemStack.EMPTY;
	}


	@SuppressWarnings("deprecation")
	public static ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
		if (ModList.get().isLoaded("curios")) {
			return CuriosApi.getCuriosHelper().findEquippedCurio(pred, living)
					.map(ImmutableTriple::getRight)
					.orElse(ItemStack.EMPTY);
		}
		
		return ItemStack.EMPTY;
	}
}
