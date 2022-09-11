package com.Fishmod.mod_LavaCow.item.crafting;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURRecipeRegistry;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SinisterWhetstoneRecipe extends SpecialRecipe {
	public SinisterWhetstoneRecipe(ResourceLocation p_i50020_1_) {
		super(p_i50020_1_);
	}

	public boolean matches(CraftingInventory p_77569_1_, World p_77569_2_) {
		boolean flag = false;
		boolean flag1 = false;
		int level = 0;
		
		for(int i = 0; i < p_77569_1_.getContainerSize(); ++i) {
			ItemStack itemstack = p_77569_1_.getItem(i);
			CompoundNBT compoundnbt = itemstack.getTag();
			
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() == FURItemRegistry.ECTOPLASM_INGOT && !flag) {
					flag = true;
				} else if (itemstack.getItem() == FURItemRegistry.SINISTER_WHETSTONE && compoundnbt == null && !flag) {
					flag = true;
					level ++;
	            } else if (itemstack.getItem() == FURItemRegistry.SINISTER_WHETSTONE && compoundnbt != null && compoundnbt.contains("level", 3) && !flag) {
					flag = true;
					level += compoundnbt.getInt("level");
	            } else if (itemstack.getItem() == FURItemRegistry.SCYTHE_CLAW) {
	            	flag1 = true;
	            	level++;
	            } else {
	            	return false;
	            }
	         }
		}

		return flag && flag1 && level > 0 && level <= 5;
	}

	public ItemStack assemble(CraftingInventory p_77572_1_) {
		int level = 0;
		
		for(int i = 0; i < p_77572_1_.getContainerSize(); ++i) {
			ItemStack itemstack1 = p_77572_1_.getItem(i);
			CompoundNBT compoundnbt = itemstack1.getTag();
			if (!itemstack1.isEmpty() && itemstack1.getItem() == FURItemRegistry.SINISTER_WHETSTONE && compoundnbt != null && compoundnbt.contains("level", 3)) {
				level += compoundnbt.getInt("level");
			} else if (itemstack1.getItem() == FURItemRegistry.SCYTHE_CLAW) {
            	level++;
            }
		}

		ItemStack itemstack2 = new ItemStack(FURItemRegistry.SINISTER_WHETSTONE, 1);
		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putInt("level", level);
		itemstack2.setTag(compoundnbt);

		return itemstack2;
	}

	public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
		return p_194133_1_ >= 2 && p_194133_2_ >= 2;
	}

	public IRecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.SINISTER_WHETSTONE;
	}
}