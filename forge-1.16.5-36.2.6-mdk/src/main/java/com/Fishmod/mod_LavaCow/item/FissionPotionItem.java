package com.Fishmod.mod_LavaCow.item;

import java.util.Random;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.LavaCowEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;


public class FissionPotionItem extends FURItem {
	
	private SoundEvent using_sound = null;
	private BasicParticleType using_particle = ParticleTypes.SMOKE;
	
	public FissionPotionItem(Properties PropertiesIn, SoundEvent soundIn, BasicParticleType particleIn) {
    	super(PropertiesIn, 32, UseAction.DRINK, 1);
    	this.using_sound = soundIn;
		this.using_particle = particleIn;
    }
 
    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA);
     }
	   
    private boolean isVanilla(ResourceLocation resourceLocation) {
    	return resourceLocation.toString().contains("minecraft:") || resourceLocation.toString().contains("mod_lavacow:");
    }
	
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    	if(((!FURConfig.Fission_ModEntity.get() && isVanilla(target.getType().getRegistryName())) || (FURConfig.Fission_ModEntity.get() && target instanceof AgeableEntity)) && !target.isBaby()) {
			
    		double dx = target.getX();
    		double dy = target.getY();
    		double dz = target.getZ();
    		boolean flag = false;
    		
    		if(!playerIn.level.isClientSide) {
	    		if(stack.getItem().equals(FURItemRegistry.FISSIONPOTION)) {	    			
			    	AgeableEntity parent = (AgeableEntity)target;
			    	AgeableEntity AgeableEntity = (AgeableEntity) parent.getType().create(playerIn.level);
			    	CompoundNBT compoundnbt = new CompoundNBT();
			    	parent.addAdditionalSaveData(compoundnbt);
			    	AgeableEntity.readAdditionalSaveData(compoundnbt);
			    	AgeableEntity.setBaby(true);
			        AgeableEntity.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.yRot, target.xRot);
			        parent.level.addFreshEntity(AgeableEntity);
			        if(AgeableEntity.getClass() == parent.getClass())parent.setBaby(true);
			        if(parent instanceof TameableEntity 
			        		&& ((TameableEntity)parent).isTame()
			        		&& AgeableEntity instanceof TameableEntity 
			        		&& !((TameableEntity)AgeableEntity).isTame())
			        	((TameableEntity)AgeableEntity).tame(playerIn);
			        AgeableEntity.playAmbientSound();
			        
			        flag = true;
	    		}
	    		else if(stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA) && target instanceof CowEntity && !(target instanceof LavaCowEntity)) {
	    			
	    			if(!target.hasEffect(Effects.FIRE_RESISTANCE))target.setSecondsOnFire(12);
	    			else {	    				
	    				for(int i = 0 ; i < 2 ; i++) {
		    				AgeableEntity AgeableEntity = FUREntityRegistry.LAVACOW.create(playerIn.level);
		    				AgeableEntity.setBaby(true);
		    				AgeableEntity.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.yRot, target.xRot);
		    				playerIn.level.addFreshEntity(AgeableEntity);
	    				}	    				
	    				target.remove();
	    			}
	    			
			        flag = true;
	    		}
    		}
    		
    		if(!playerIn.isCreative() && flag) {		
    			this.finishUsingItem(stack, playerIn.level, playerIn);
    			stack.shrink(1);
    		}
	    	
    		playerIn.playSound(this.using_sound, 1.0F, 1.0F);
    		for (int i = 0; i < 5; ++i) {
    			double d0 = new Random().nextGaussian() * 0.02D;
    			double d1 = new Random().nextGaussian() * 0.02D;
    			double d2 = new Random().nextGaussian() * 0.02D;
    			playerIn.level.addParticle(this.using_particle, dx + (double)(new Random().nextFloat() * playerIn.getBbWidth() * 2.0F) - (double)playerIn.getBbWidth(), dy + 1.0D + (double)(new Random().nextFloat() * playerIn.getBbHeight()), dz + (double)(new Random().nextFloat() * playerIn.getBbWidth() * 2.0F) - (double)playerIn.getBbWidth(), d0, d1, d2);
    		}
    		
    		return flag ? ActionResultType.sidedSuccess(playerIn.level.isClientSide) : ActionResultType.PASS;
        }  
    	
        return ActionResultType.PASS;
    }
    
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override  
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
        return entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.instabuild ? itemstack : new ItemStack(Items.BONE);
    }

}
