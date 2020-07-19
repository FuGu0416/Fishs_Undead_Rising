package com.Fishmod.mod_LavaCow.util;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(FishItems.UNDYINGHEART);
	}

}
