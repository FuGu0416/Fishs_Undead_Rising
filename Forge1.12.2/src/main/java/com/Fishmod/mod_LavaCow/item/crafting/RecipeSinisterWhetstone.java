package com.Fishmod.mod_LavaCow.item.crafting;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeSinisterWhetstone extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
	    boolean flag = false;
	    boolean flag1 = false;
	    int level = 0;
	    
	    for (int i = 0; i < inv.getSizeInventory(); i++) {
	      ItemStack itemstack = inv.getStackInSlot(i);
	      NBTTagCompound compoundnbt = itemstack.getTagCompound();
	      
	      if (!itemstack.isEmpty())
	        if (itemstack.getItem() == FishItems.ECTOPLASM_INGOT && !flag) {
	          flag = true;
	        } else if (itemstack.getItem() == FishItems.SINISTER_WHETSTONE && compoundnbt == null && !flag) {
	          flag = true;
	          level++;
	        } else if (itemstack.getItem() == FishItems.SINISTER_WHETSTONE && compoundnbt != null && compoundnbt.hasKey("level", 3) && !flag) {
	          flag = true;
	          level += compoundnbt.getInteger("level");
	        } else if (itemstack.getItem() == FishItems.SCYTHE_CLAW) {
	          flag1 = true;
	          level++;
	        } else {
	          return false;
	        }  
	    } 
	    return (flag && flag1 && level > 0 && level <= 5);
	}
	
	@Override
	  public ItemStack getCraftingResult(InventoryCrafting inv) {
		    int level = 0;
		    for (int i = 0; i < inv.getSizeInventory(); i++) {
		      ItemStack itemstack1 = inv.getStackInSlot(i);
		      NBTTagCompound compoundNBT = itemstack1.getTagCompound();
		      if (!itemstack1.isEmpty() && itemstack1.getItem() == FishItems.SINISTER_WHETSTONE && compoundNBT != null && compoundNBT.hasKey("level", 3)) {
		        level += compoundNBT.getInteger("level");
		      } else if (itemstack1.getItem() == FishItems.SCYTHE_CLAW) {
		        level++;
		      } 
		    } 
		    ItemStack itemstack2 = new ItemStack(FishItems.SINISTER_WHETSTONE, 1);
		    NBTTagCompound compoundnbt = new NBTTagCompound();
		    compoundnbt.setInteger("level", level);
		    itemstack2.setTagCompound(compoundnbt);
		    return itemstack2;
	  }
	
	@Override
	public boolean canFit(int width, int height) {
        return width > 2 && height > 2;
	}
	
    public boolean isDynamic() {
        return true;
    }

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}
