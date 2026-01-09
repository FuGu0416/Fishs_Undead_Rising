package com.Fishmod.fur.item;

import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FURStewItem extends FURItem {
	public FURStewItem(Properties properties, int tooltip) {
    	super(properties, 32, UseAnim.DRINK, tooltip);
    }

	public FURStewItem(Properties properties, UseAnim useAnim, int tooltip) {
    	super(properties.craftRemainder(Items.BOWL), 32, useAnim, tooltip);
    }
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity living, InteractionHand hand) {
    	/*if (this.equals(FURItemRegistry.NETHERSTEW) && living instanceof PigEntity && !living.isBaby()) {
    		if(!player.isCreative()) 
    		{
    			stack.shrink(1);
    		}
    		
    		player.playSound(SoundEvents.GENERIC_BURN, 1.0F, 1.0F);
        	for (int i = 0; i < 5; ++i)
            {
                double d0 = living.getRandom().nextGaussian() * 0.02D;
                double d1 = living.getRandom().nextGaussian() * 0.02D;
                double d2 = living.getRandom().nextGaussian() * 0.02D;
                living.level.addParticle(ParticleTypes.HEART, living.getRandomX(1.0D), living.getRandomY() + 0.5D, living.getRandomZ(1.0D), d0, d1, d2);
            }
        	
        	if (!player.level.isClientSide) 
        	{
                ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(player.level);
                zombifiedpiglinentity.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
                zombifiedpiglinentity.moveTo(living.getX(), living.getY(), living.getZ(), living.yRot, living.xRot);
                zombifiedpiglinentity.setBaby(true);
                player.level.addFreshEntity(zombifiedpiglinentity);
        	}
            return ActionResultType.PASS;
        } else {  */ 	
        	return super.interactLivingEntity(stack, player, living, hand);
        //}
    }
      
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
    	ItemStack itemstack = super.finishUsingItem(stack, level, living);
    	
    	if (this.equals(FURItemRegistry.GHOSTJELLY.get())) {
    		living.setDeltaMovement(0.0D, 2.0D, 0.0D);
    		living.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
    	}
    	
        if (!level.isClientSide && living instanceof Player player && !player.isCreative() && !itemstack.isEmpty()) {
        	if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
        		player.spawnAtLocation(new ItemStack(Items.BOWL));
            }
        }
        
    	return itemstack.isEmpty() ? new ItemStack(Items.BOWL) : itemstack;
    }
}
