package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemDreamCatcher extends ItemRareLoot implements baubles.api.IBauble {

	public ItemDreamCatcher(String registryName, CreativeTabs tab, EnumRarity rarity, boolean hasTooltip) {
		super(registryName, tab, rarity, hasTooltip);
		this.setMaxStackSize(1);
		this.setMaxDamage(Modconfig.Dreamcatcher_dur);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(stack.getItemDamage() > stack.getMaxDamage())
			stack.setItemDamage(stack.getMaxDamage());
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.WEB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.HaloNecklace_Damage));
	}
    
    /**
     * Allow or forbid the specific book/item combination as an anvil enchant
     *
     * @param stack The item
     * @param book The book
     * @return if the enchantment is allowed
     */
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return Modconfig.Dreamcatcher_BookEnchantability;
    }
	
    /**
     * Add Baubles support
     * @param arg0
     * @param arg1
     */  
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack arg0) {
          return baubles.api.BaubleType.CHARM;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase plr) {

    }
    
	@Override
	@Optional.Method(modid = "baubles")
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, .75F, 1.9f);
	}

	@Override
	@Optional.Method(modid = "baubles")
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, .75F, 2f);
	}
}
