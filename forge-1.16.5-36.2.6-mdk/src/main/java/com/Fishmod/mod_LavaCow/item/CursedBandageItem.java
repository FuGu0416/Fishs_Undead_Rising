package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CursedBandageItem extends FURItem {
	private final int type;
	
	public CursedBandageItem(Properties PropertiesIn, int typeIn) {
		super(PropertiesIn, 0, UseAction.NONE, 1);
		this.type = typeIn;	
    }
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:cursed_bandage." + this.type + ".desc0"));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:cursed_bandage." + this.type + ".desc1").withStyle(TextFormatting.YELLOW));
	}
	
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    	if (target instanceof TameableEntity && ((TameableEntity)target).isTame() && ((TameableEntity)target).getOwner() != null && ((TameableEntity)target).getOwner().equals(playerIn) && target.getHealth() < target.getMaxHealth())
        {
    		switch(this.type) { 
	            case 0:
	            	break;
	            case 1: 
	            	target.heal(2.0F);
	            	target.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 3 * 60 * 20, 0));
	                break; 
	            case 2: 
	            	target.heal(6.0F);
	                break; 
	            case 3: 
	            	target.heal(2.0F);
	            	target.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 3 * 60 * 20, 0));
	                break; 
	            default: 
	            	target.heal(2.0F);
	                break;
	        }
    		
    		if(!playerIn.isCreative())
    			stack.shrink(1);
    		
    		playerIn.playSound(SoundEvents.GRASS_STEP, 1.0F, 1.0F);
        	for (int i = 0; i < 5; ++i)
            {
                double d0 = new Random().nextGaussian() * 0.02D;
                double d1 = new Random().nextGaussian() * 0.02D;
                double d2 = new Random().nextGaussian() * 0.02D;
                target.level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, target.getX() + (double)(new Random().nextFloat() * target.getBbWidth() * 2.0F) - (double)target.getBbWidth(), target.getY() + 1.0D + (double)(new Random().nextFloat() * target.getBbHeight()), target.getZ() + (double)(new Random().nextFloat() * target.getBbWidth() * 2.0F) - (double)target.getBbWidth(), d0, d1, d2);
            }
        	
    		return ActionResultType.SUCCESS;
        }
        else
        	return super.interactLivingEntity(stack, playerIn, target, hand);
    }
}
