package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParasiteRawItem extends FURItem {
	private static final FoodProperties COMMON = new FoodProperties.Builder()
	        .nutrition(2)
	        .saturationMod(0.3f)
	        .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 30*20, 0), 0.3F)
	        .build();

	private static final FoodProperties VESPA = new FoodProperties.Builder()
	        .nutrition(2)
	        .saturationMod(0.3f)
	        .effect(() -> new MobEffectInstance(MobEffects.POISON, 4*20, 0), 0.3F)
	        .build();
	
	public ParasiteRawItem(Properties properties) {
		super(properties.food(COMMON));
	}
	
    @Override
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (!stack.hasTag()) return COMMON;

        int v = stack.getTag().getInt("variant");

        return switch (v) {
            case 2 -> VESPA;
            default -> COMMON;
        };
    }
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc" + ((stack.hasTag()) ? stack.getTag().getInt("variant") : 0)).withStyle(ChatFormatting.BLUE));
		super.appendHoverText(stack, level, tooltip, flag);
	}

}
