package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class NetherStewItem extends FURItem {
	public NetherStewItem(Properties PropertiesIn, int TooltipIn) {
    	super(PropertiesIn, 32, UseAction.DRINK, TooltipIn);
    }

	public NetherStewItem(Properties PropertiesIn, UseAction UseActionIn, int TooltipIn) {
    	super(PropertiesIn, 32, UseActionIn, TooltipIn);
    }
	
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    	if (this.equals(FURItemRegistry.NETHERSTEW) && target instanceof PigEntity && !target.isBaby()) {
    		if(!playerIn.isCreative()) 
    		{
    			stack.shrink(1);
    		}
    		
    		playerIn.playSound(SoundEvents.GENERIC_BURN, 1.0F, 1.0F);
        	for (int i = 0; i < 5; ++i)
            {
                double d0 = target.getRandom().nextGaussian() * 0.02D;
                double d1 = target.getRandom().nextGaussian() * 0.02D;
                double d2 = target.getRandom().nextGaussian() * 0.02D;
                target.level.addParticle(ParticleTypes.HEART, target.getRandomX(1.0D), target.getRandomY() + 0.5D, target.getRandomZ(1.0D), d0, d1, d2);
            }
        	
        	if (!playerIn.level.isClientSide) 
        	{
                ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(playerIn.level);
                zombifiedpiglinentity.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
                zombifiedpiglinentity.moveTo(target.getX(), target.getY(), target.getZ(), target.yRot, target.xRot);
                zombifiedpiglinentity.setBaby(true);
                playerIn.level.addFreshEntity(zombifiedpiglinentity);
        	}
            return ActionResultType.PASS;
        } else {   	
        	return super.interactLivingEntity(stack, playerIn, target, hand);
        }
    }
      
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
    	ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
    	
    	if (this.equals(FURItemRegistry.GHOSTJELLY)) {
    		entityLiving.setDeltaMovement(0.0D, 2.0D, 0.0D);
    		entityLiving.addEffect(new EffectInstance(Effects.SLOW_FALLING, 6 * 20, 2));
    		entityLiving.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
    	}
    	
        if (!worldIn.isClientSide && entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).isCreative() && !itemstack.isEmpty()) {
        	if (!((PlayerEntity)entityLiving).inventory.add(new ItemStack(Items.BOWL))) {
        		((PlayerEntity)entityLiving).spawnAtLocation(new ItemStack(Items.BOWL));
            }
        }
        
    	return itemstack.isEmpty() ? new ItemStack(Items.BOWL) : itemstack;
    }
}
