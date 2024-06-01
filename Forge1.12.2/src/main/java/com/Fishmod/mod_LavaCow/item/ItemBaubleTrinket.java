package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemBaubleTrinket extends ItemRareLoot implements baubles.api.IBauble{

	public ItemBaubleTrinket(String registryName, CreativeTabs tab, EnumRarity rarity, boolean hasTooltip) {
		super(registryName, tab, rarity, hasTooltip);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(stack.getItemDamage() > stack.getMaxDamage())stack.setItemDamage(stack.getMaxDamage());
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}
    
    /**
     * Allow or forbid the specific book/item combination as an anvil enchant
     *
     * @param stack The item
     * @param book The book
     * @return if the enchantment is allowed
     */
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
    	return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		if(stack.getItem().equals(FishItems.HALO_NECKLACE))
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.HaloNecklace_Damage));
		else if(stack.getItem().equals(FishItems.MOOTENHEART))
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.MootenHeart_Damage));
		else if(stack.getItem().equals(FishItems.SOULFORGED_HEART)) {
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.MootenHeart_Damage));
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip + ".l2", Modconfig.SoulforgedHeart_Healing));
		}
	}
	
    /**
     * Add Baubles support
     * @param arg0
     * @param arg1
     */  
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack stack, EntityLivingBase entity) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack stack, EntityLivingBase entity) {
      return true;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack stack) {
    	if(stack.getItem().equals(FishItems.HALO_NECKLACE))
    		return baubles.api.BaubleType.AMULET;
    	else
    		return baubles.api.BaubleType.TRINKET;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase plr) {

    }
    
	@Override
	@Optional.Method(modid = "baubles")
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		if(stack.getItem().equals(FishItems.MOOTENHEART))
			player.playSound(SoundEvents.BLOCK_LAVA_POP, 0.75F, 2.0F);
		else if(stack.getItem().equals(FishItems.SOULFORGED_HEART))
			player.playSound(FishItems.ENTITY_AVATON_HURT, 0.75F, 1.25F);
		else
			player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75F, 2.0F);
	}

	@Override
	@Optional.Method(modid = "baubles")
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		if(stack.getItem().equals(FishItems.MOOTENHEART))
			player.playSound(SoundEvents.BLOCK_LAVA_POP, 0.75F, 2.0F);
		else if(stack.getItem().equals(FishItems.SOULFORGED_HEART))
			player.playSound(FishItems.ENTITY_AVATON_HURT, 0.75F, 1.25F);
		else
			player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75F, 2.0F);
	}
}
