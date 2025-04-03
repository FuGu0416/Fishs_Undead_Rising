package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityFishCustomArrow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFishCustomArrow extends ItemArrow {
    protected String Tooltip = null;
    
    public ItemFishCustomArrow(String registryName) {
		setTranslationKey(mod_LavaCow.MODID + "." + registryName);
		setRegistryName(registryName);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
    }
    
    @Override
    public EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter) {
        if (stack.getItem().equals(FishItems.GHOUL_ARROW)) {
            EntityFishCustomArrow arrow = new EntityFishCustomArrow(world, shooter, FishItems.GHOUL_ARROW);
            arrow.setArrowType(0);
            return arrow;
        } else if (stack.getItem().equals(FishItems.FANG_ARROW)) {
        	EntityFishCustomArrow arrow = new EntityFishCustomArrow(world, shooter, FishItems.FANG_ARROW);
        	arrow.setArrowType(1);
            return arrow;
        }

        return new EntitySpectralArrow(world, shooter);
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
        int enchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
        return enchant <= 0 ? false : this.getClass() == ItemFishCustomArrow.class;
    }
}
