package com.Fishmod.mod_LavaCow.misc;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURItemGroup extends ItemGroup {

	public FURItemGroup() {
		super(mod_LavaCow.MODID);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(FURItemRegistry.UNDYINGHEART);
	}

}
