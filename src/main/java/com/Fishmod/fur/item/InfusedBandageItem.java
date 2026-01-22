package com.Fishmod.fur.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class InfusedBandageItem extends FURItem {

	public InfusedBandageItem(Properties properties, int useDuration, UseAnim useAnim, int tooltip) {
		super(properties, useDuration, useAnim, tooltip);
	}	
	
	private void applyBandageEffects(ItemStack stack, @Nullable Player player, LivingEntity target, ServerLevel server) {
    	server.playSound(null, target.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 0.8F, 1.2F);
    	server.playSound(null, target.blockPosition(), SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
    	
    	for (int i = 0; i < 5; ++i) {
            double d0 = new Random().nextGaussian() * 0.02D;
            double d1 = new Random().nextGaussian() * 0.02D;
            double d2 = new Random().nextGaussian() * 0.02D;
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, target.getX() + (double)(new Random().nextFloat() * target.getBbWidth() * 2.0F) - (double)target.getBbWidth(), target.getY() + 1.0D + (double)(new Random().nextFloat() * target.getBbHeight()), target.getZ() + (double)(new Random().nextFloat() * target.getBbWidth() * 2.0F) - (double)target.getBbWidth(), 1, d0, d1, d2, 0.0D);
        }

    	for (MobEffectInstance inst :  PotionUtils.getMobEffects(stack)) {
            MobEffect effect = inst.getEffect();

            if (effect.isInstantenous()) {
                effect.applyInstantenousEffect(player, player, target, inst.getAmplifier(), 1.0D);
            } else {
                target.removeEffect(effect);
                target.addEffect(new MobEffectInstance(effect, inst.getDuration(), inst.getAmplifier()), player);
            }
        }
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
	    return InteractionResultHolder.consume(player.getItemInHand(hand));
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (entity instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem())) {
			if (level instanceof ServerLevel server) {
		        applyBandageEffects(stack, null, entity, server);
		    }
			
			player.getCooldowns().addCooldown(this, 20);
	
		    if (!player.getAbilities().instabuild) {
		        stack.shrink(1);
		    }
		}

	    return stack;
	}
	
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
    	Level level = player.level();
    	
	    if (level instanceof ServerLevel server && !player.getCooldowns().isOnCooldown(stack.getItem())) {
	    	applyBandageEffects(stack, player, target, server);
	    }
	    
	    player.getCooldowns().addCooldown(this, 20);
	    
	    if (!player.getAbilities().instabuild) {
	        stack.shrink(1);
	    }
	    
		return InteractionResult.sidedSuccess(level.isClientSide);
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        List<MobEffectInstance> effects = PotionUtils.getMobEffects(stack);

        if (effects.isEmpty()) return;

        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

}
