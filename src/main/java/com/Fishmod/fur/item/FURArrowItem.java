package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.projectiles.FURArrowEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURArrowItem extends ArrowItem {
	private int Tooltip = 0;

    /**
     * 0: no tooltips
     * 1: tooltips w/ white text 
     * 2: tooltips w/ yellow text 
     */	
	public FURArrowItem(Properties properties, int tooltip) {
		super(properties);
		this.Tooltip = tooltip;
	}
	
	public FURArrowItem(Properties p_i48487_1_) {
		this(p_i48487_1_, 0);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity living) {
		EntityType<? extends FURArrowEntity> type = FUREntityRegistry.GHOUL_ARROW.get();
		
		if (stack.getItem().equals(FURItemRegistry.GHOUL_ARROW.get())) {
			type = FUREntityRegistry.GHOUL_ARROW.get();
		} else if (stack.getItem().equals(FURItemRegistry.FANG_ARROW.get())) {
			type = FUREntityRegistry.FANG_ARROW.get();
		}
		
		FURArrowEntity arrowentity = new FURArrowEntity(type, level, living);
		return arrowentity;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this.getClass() == FURArrowItem.class;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if (stack.getItem().equals(FURItemRegistry.GHOUL_ARROW.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", FURConfig.Ghoul_targetHPThreshold.get()).withStyle(ChatFormatting.YELLOW));
		} else if (stack.getItem().equals(FURItemRegistry.FANG_ARROW.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", FURConfig.BoneSword_Damage.get(), FURConfig.BoneSword_DamageCap.get()).withStyle(ChatFormatting.YELLOW));
		} else if (this.Tooltip == 2) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc"));			
		} else if (this.Tooltip == 1) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
		}
	}	
}
