package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

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
	public FURArrowItem(Properties p_i48487_1_, int TooltipIn) {
		super(p_i48487_1_);
		this.Tooltip = TooltipIn;
	}
	
	public FURArrowItem(Properties p_i48487_1_) {
		this(p_i48487_1_, 0);
	}

	@Override
	public AbstractArrow createArrow(Level p_200887_1_, ItemStack stack, LivingEntity p_200887_3_) {
		EntityType<? extends FURArrowEntity> Type = FUREntityRegistry.GHOUL_ARROW.get();
		
		if (stack.getItem().equals(FURItemRegistry.GHOUL_ARROW.get())) {
			Type = FUREntityRegistry.GHOUL_ARROW.get();
		} else if (stack.getItem().equals(FURItemRegistry.FANG_ARROW.get())) {
			Type = FUREntityRegistry.FANG_ARROW.get();
		}
		
		FURArrowEntity arrowentity = new FURArrowEntity(Type, p_200887_1_, p_200887_3_);
		return arrowentity;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this.getClass() == FURArrowItem.class;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (stack.getItem().equals(FURItemRegistry.GHOUL_ARROW.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", 40/*FURConfig.Ghoul_targetHPThreshold.get()*/).withStyle(ChatFormatting.YELLOW));
		} else if (stack.getItem().equals(FURItemRegistry.FANG_ARROW.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", 5/*FURConfig.BoneSword_Damage.get()*/, 10000/*FURConfig.BoneSword_DamageCap.get()*/).withStyle(ChatFormatting.YELLOW));
		} else if (this.Tooltip == 2) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc"));			
		} else if (this.Tooltip == 1) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
		}
	}	
}
