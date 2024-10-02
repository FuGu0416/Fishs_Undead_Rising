package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.GhoulArrowEntity;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURArrowItem extends ArrowItem {
	private int Tooltip = 0;

    /**
     * 0: no tooltips
     * 1: tooltips w/ white text 
     * 2: tooltips w/ yellow text 
     */	
	public FURArrowItem(Properties p_i48487_1_, int TooltipIn) {
		super(p_i48487_1_);
		this.Tooltip = TooltipIn;
	}
	
	public FURArrowItem(Properties p_i48487_1_) {
		this(p_i48487_1_, 0);
	}

	@Override
	public AbstractArrowEntity createArrow(World p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
		GhoulArrowEntity arrowentity = new GhoulArrowEntity(p_200887_1_, p_200887_3_);
		return arrowentity;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this.getClass() == FURArrowItem.class;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(stack.getItem().equals(FURItemRegistry.GHOUL_ARROW)) {
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName(), FURConfig.Ghoul_targetHPThreshold.get()).withStyle(TextFormatting.YELLOW));
		} else if (this.Tooltip == 2) {
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()));			
		} else if (this.Tooltip == 1) {
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
		}
	}	
}
