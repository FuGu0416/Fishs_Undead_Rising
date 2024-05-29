package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;

import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemGoldenHeart extends ItemRareLoot implements IBauble {

	public ItemGoldenHeart(String registryName, CreativeTabs tab, EnumRarity rarity) {
		super(registryName, tab, rarity, false);
		this.setMaxStackSize(1);
		this.setMaxDamage(Modconfig.GoldenHeart_dur);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == Items.GOLD_INGOT;
	}
    
    /**
     * Allow or forbid the specific book/item combination as an anvil enchant
     *
     * @param stack The item
     * @param book The book
     * @return if the enchantment is allowed
     */
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return Modconfig.GoldenHeart_BookEnchantability;
    }
	
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, list, flag);
        
        if (Modconfig.GoldenHeart_GrantsRegeneration) list.add(TextFormatting.YELLOW + I18n.format("tootip." + mod_LavaCow.MODID + ".goldenheart.regeneration", Modconfig.GoldenHeart_Regeneration_Amount));
        if (Modconfig.GoldenHeart_RepairsEquipment) list.add(TextFormatting.YELLOW + I18n.format("tootip." + mod_LavaCow.MODID + ".goldenheart.repair", Modconfig.GoldenHeart_Repair_Amount));
    }
	
	private static boolean isBlackListed(ItemStack ItemStackIn) {
		boolean flag = ItemStackIn.getItem().equals(FishItems.GOLDENHEART);
		for(String S : Modconfig.GoldenHeart_bl)
			if(ItemStackIn.getItem().getRegistryName().toString().equals(S)) {
				flag = true;
				break;
			}
			
		return flag;
	}

    /**
     * Add Baubles support
     * @param arg0
     * @param arg1
     */
    //@Optional.Method(modid = "baubles")
    public static void onTick(ItemStack stack, EntityPlayer player) {
    	boolean flag = false;
    	
    	if (Modconfig.GoldenHeart_GrantsRegeneration && ((stack.getMaxDamage() > 0 && stack.getItemDamage() != stack.getMaxDamage()) || stack.getMaxDamage() == 0)) {
	    	if (player.getActivePotionEffect(MobEffects.REGENERATION) == null && player.getHealth() < player.getMaxHealth()) {
	    		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, Modconfig.GoldenHeart_Regeneration_Amount * 20, 0));
	    		flag = true;
	    	} else if (Modconfig.GoldenHeart_RepairsEquipment && player.getHealth() == player.getMaxHealth()) {
	    		for(ItemStack item : player.getEquipmentAndArmor()) {
		        	if (!isBlackListed(item) && item.getMaxDamage() != 0 && item.getItem().isDamageable() && (item.isItemEnchantable() || item.isItemEnchanted()) && item.getItemDamage() > 0 && item.getItem().isRepairable() && !item.getHasSubtypes()) {
		        		item.setItemDamage(Math.max(item.getItemDamage() - Modconfig.GoldenHeart_Repair_Amount, 0));
		        		flag = true;
		        	}
		        }
	    	}
    	}
    	
    	if (flag && !player.isCreative() && stack.getMaxDamage() != 0 && player.world.rand.nextInt(100) < Modconfig.GoldenHeart_dur_drop)
    		stack.damageItem(1, player);
	}
    
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
          return baubles.api.BaubleType.TRINKET;
    }
    
    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase plr) {
    	if(stack.getItemDamage() > stack.getMaxDamage())stack.setItemDamage(stack.getMaxDamage());
    	if (plr instanceof EntityPlayer && plr.ticksExisted % 20 == 0 && !stack.isEmpty() && stack.getCount() > 0) {
	        ItemGoldenHeart.onTick(stack, (EntityPlayer) plr);
	    }
    }
    
    @Override
	@Optional.Method(modid = "baubles")
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		player.playSound(SoundEvents.ENTITY_ENDEREYE_DEATH, 0.75F, 2.0F);
	}

	@Override
	@Optional.Method(modid = "baubles")
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.playSound(SoundEvents.ENTITY_ENDEREYE_DEATH, 0.75F, 2.0F);
	}
}
