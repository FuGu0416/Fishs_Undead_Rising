package com.Fishmod.fur.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FURStewItem extends FURItem {
	public FURStewItem(Properties PropertiesIn, int TooltipIn) {
    	super(PropertiesIn, 32, UseAnim.DRINK, TooltipIn);
    }

	public FURStewItem(Properties PropertiesIn, UseAnim UseActionIn, int TooltipIn) {
    	super(PropertiesIn, 32, UseActionIn, TooltipIn);
    }
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
    	/*if (this.equals(FURItemRegistry.NETHERSTEW) && target instanceof PigEntity && !target.isBaby()) {
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
        } else {  */ 	
        	return super.interactLivingEntity(stack, playerIn, target, hand);
        //}
    }
      
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
    	ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
    	
    	/*if (this.equals(FURItemRegistry.GHOSTJELLY)) {
    		entityLiving.setDeltaMovement(0.0D, 2.0D, 0.0D);
    		entityLiving.addEffect(new EffectInstance(Effects.SLOW_FALLING, 6 * 20, 2));
    		entityLiving.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.0F);
    	}*/
    	
        if (!worldIn.isClientSide && entityLiving instanceof Player player && !player.isCreative() && !itemstack.isEmpty()) {
        	if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
        		player.spawnAtLocation(new ItemStack(Items.BOWL));
            }
        }
        
    	return itemstack.isEmpty() ? new ItemStack(Items.BOWL) : itemstack;
    }
}
