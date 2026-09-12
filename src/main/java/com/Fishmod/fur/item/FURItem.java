package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURItem extends Item {
	private UseAnim UseAnim;
	private int UseDuration;
	private int Tooltip = 0;
	
	public FURItem(Properties properties, int useDuration, UseAnim useAnim, int tooltip) {
		super(properties);
		this.UseDuration = useDuration;
		this.UseAnim = useAnim;
		this.Tooltip = tooltip;
	}
	
    /**
     * 0: no tooltips
     * 1: tooltips w/ white text 
     * 2: tooltips w/ yellow text 
     */	
	public FURItem(Properties properties, int tooltip) {
		this(properties, 32, net.minecraft.world.item.UseAnim.EAT, tooltip);
	}
	
	public FURItem(Properties properties) {
		this(properties, 0);
	}

	@Override
    public int getUseDuration(ItemStack stack) {
        return this.UseDuration;
    }

	@Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return this.UseAnim;
    }
	
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
    	
		if (stack.getItem().equals(FURItemRegistry.BOABING.get()) && living.isOnFire()) {
			living.clearFire();
		}
		
		
        if (!level.isClientSide && stack.getItem().equals(FURItemRegistry.LAMPREY_KABAYAKI.get()) && living instanceof Player player && !player.isCreative()) {
        	living.removeEffect(MobEffects.POISON);
        	living.removeEffect(FUREffectRegistry.INFESTED.get());

        	if (!player.getInventory().add(new ItemStack(Items.STICK))) {
        		player.spawnAtLocation(new ItemStack(Items.STICK));
            }
        }

        if (!level.isClientSide && stack.getItem().equals(FURItemRegistry.SPORE_GEL.get())) {
        	living.removeEffect(FUREffectRegistry.SPOREROT.get());
        }

        // Same "cures everything" role Milk Bucket has (vanilla special-cases MILK_BUCKET the same
        // way in Player#eat), just themed as a blessed tonic - Holy Water is rare enough (Cleric/Grave
        // Robber trades only) that a blanket cure is fine, no need to scope it to a curated list.
        if (!level.isClientSide && stack.getItem().equals(FURItemRegistry.HOLY_WATER.get())) {
        	living.removeAllEffects();
        }

    	return super.finishUsingItem(stack, level, living);
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
    	return super.isFoil(stack) || stack.getItem().equals(FURItemRegistry.HOLY_WATER.get());
    }
    
    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
    	if (stack.getItem().equals(FURItemRegistry.COMBUSTIVE_GLAND.get())) {
    		return 6400;
    	} else if (stack.getItem().equals(FURItemRegistry.IMP_HORN.get())) {
    		return 3200;
    	} else {
    		return super.getBurnTime(stack, recipeType);
    	}
    }
 
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		FoodProperties foodStats = stack.getItem().getFoodProperties(stack, null);
		
		if (foodStats != null) {
			SpawnUtil.addFoodEffectTooltip(stack, tooltip, 1.0F);
		}

		if (stack.getItem().equals(FURItemRegistry.MIMIC_EGG.get()) && stack.hasTag()) {
			// HatchTime can keep counting past MIMIC_EGG_HATCH_TIME while the egg is fully
			// incubated but waiting on a flower_pot to actually hatch (see
			// MimicEntity#tickEggIncubation) - clamp the displayed percentage so a long wait
			// doesn't show e.g. 140%.
			int percent = Math.min(100, stack.getOrCreateTag().getInt("HatchTime") * 100 / MimicEntity.MIMIC_EGG_HATCH_TIME);
			tooltip.add(Component.translatable(percent + "%").withStyle(ChatFormatting.DARK_GRAY));
		} else if (this.Tooltip == 1) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
		}
	}	
}
