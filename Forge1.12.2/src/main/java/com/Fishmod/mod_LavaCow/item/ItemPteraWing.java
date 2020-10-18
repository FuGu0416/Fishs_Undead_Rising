package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPteraWing extends ItemFishCustomFood{

	public ItemPteraWing(String registryName, int amount, float saturation, boolean isWolfFood, int duration,
			boolean hasTooltip) {
		super(registryName, amount, saturation, isWolfFood, duration, hasTooltip);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		int i = stack.getMetadata();
		list.add(I18n.format("tootip.mod_lavacow.parasite." + (2 - i)));
	}
	
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 2; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
