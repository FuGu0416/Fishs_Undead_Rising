package com.Fishmod.mod_LavaCow.item;

import java.util.Random;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.LavaCowEntity;
import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


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
    	if(((!FURConfig.Fission_ModEntity.get() && isVanilla(target.getType().getRegistryName())) || (FURConfig.Fission_ModEntity.get() && target instanceof AgeableEntity))) {
			
    		double dx = target.getX();
    		double dy = target.getY();
    		double dz = target.getZ();
    		boolean flag = false;
    		
    		if(!playerIn.level.isClientSide) {
	    		if (stack.getItem().equals(FURItemRegistry.FISSIONPOTION) && !target.isBaby()) {	    			
			    	AgeableEntity parent = (AgeableEntity)target;
			    	AgeableEntity AgeableEntity = (AgeableEntity) parent.getType().create(playerIn.level);			    	    	
			    	CompoundNBT compoundnbt = new CompoundNBT();
			    	
			    	parent.addAdditionalSaveData(compoundnbt);			    			    	
			    	
			    	if (AgeableEntity instanceof IEquipable) {			    		
			    		compoundnbt.putBoolean("Saddled", false);
			    	}
			    
			    	AgeableEntity.readAdditionalSaveData(compoundnbt);				    	
			        AgeableEntity.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.yRot, target.xRot);
			        parent.level.addFreshEntity(AgeableEntity);			        			       
			        
			        AgeableEntity.setBaby(true);
			        
			        if (AgeableEntity.getClass() == parent.getClass()) {
			        	parent.setBaby(true);
			        }
			        
			        if(parent instanceof TameableEntity && ((TameableEntity)parent).isTame() && AgeableEntity instanceof TameableEntity && !((TameableEntity)AgeableEntity).isTame()) {
			        	((TameableEntity)AgeableEntity).tame(playerIn);
			        }
			        
			        AgeableEntity.playAmbientSound();
			        
			        flag = true;
	    		} else if (stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA) && target instanceof CowEntity && !(target instanceof LavaCowEntity) && !target.isBaby()) {
	    			
	    			if (!target.hasEffect(Effects.FIRE_RESISTANCE)) {
	    				target.setSecondsOnFire(12);
	    			} else {	    				
	    				for (int i = 0 ; i < 2 ; i++) {
		    				AgeableEntity AgeableEntity = FUREntityRegistry.LAVACOW.create(playerIn.level);
		    				AgeableEntity.setBaby(true);
		    				AgeableEntity.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.yRot, target.xRot);
		    				playerIn.level.addFreshEntity(AgeableEntity);
	    				}	    				
	    				target.remove();
	    			}
	    			
			        flag = true;
	    		} else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST) && target.getType().equals(FUREntityRegistry.PARASITE)) {
	    			VespaCocoonEntity pupa = null;
	    			
	    			switch(((ParasiteEntity)target).getSkin()) {
	    				case 3:
	    					pupa = FUREntityRegistry.BEELZEBUBPUPA.create(playerIn.level);
	    					break;
	    				case 2:
	    					pupa = FUREntityRegistry.VESPACOCOON.create(playerIn.level);
	    					pupa.setSkin(0);
	    					break;
	    				default:
	    					break;
	    			}
	    			
	    			if (pupa != null) {
		    			target.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
	        			
			    		pupa.moveTo(target.getX(), target.getY(), target.getZ(), target.yRot, target.xRot);
			    		pupa.tame(playerIn);
			    		playerIn.level.addFreshEntity(pupa);
			    		target.remove();
			    		
		    			flag = true;
	    			}
	    		} else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST) && target.getType().equals(FUREntityRegistry.ENIGMOTH) && target.isBaby()) {
	    			VespaCocoonEntity pupa = FUREntityRegistry.VESPACOCOON.create(playerIn.level);
	    			
	    			if (pupa != null) {
		    			target.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
	        			
			    		pupa.moveTo(target.getX(), target.getY(), target.getZ(), target.yRot, target.xRot);
			    		pupa.tame(playerIn);
			    		pupa.setSkin(1);
			    		playerIn.level.addFreshEntity(pupa);
			    		target.remove();
			    		
		    			flag = true;
	    			}
	    		}            		
    		}
    		  		
    		if (flag) {		
    			this.returnItem(stack, playerIn.level, playerIn);
    			playerIn.addItem(new ItemStack(Items.GLASS_BOTTLE));
    		}   		
	    	
    		if (playerIn.level instanceof ServerWorld && flag) {	 
    			playerIn.level.playSound(null, playerIn.blockPosition(), this.using_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
	    		for (int i = 0; i < 5; ++i) {
	    			double d0 = new Random().nextGaussian() * 0.02D;
	    			double d1 = new Random().nextGaussian() * 0.02D;
	    			double d2 = new Random().nextGaussian() * 0.02D;
	    			((ServerWorld) playerIn.level).sendParticles(this.using_particle, dx + (double)(new Random().nextFloat() * playerIn.getBbWidth() * 2.0F) - (double)playerIn.getBbWidth(), dy + 1.0D + (double)(new Random().nextFloat() * playerIn.getBbHeight()), dz + (double)(new Random().nextFloat() * playerIn.getBbWidth() * 2.0F) - (double)playerIn.getBbWidth(), 15, 0.0D, d0, d1, d2);
	    		}
    		}
    		
    		return flag ? ActionResultType.sidedSuccess(playerIn.level.isClientSide) : ActionResultType.PASS;
        }  
    	
        return ActionResultType.PASS;
    }
    
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
	}
    
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return DrinkHelper.useDrink(p_77659_1_, p_77659_2_, p_77659_3_);
	}
    
    private ItemStack returnItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        if (playerentity instanceof ServerPlayerEntity) {
           CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }
        
        if (playerentity != null) {
        	playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.instabuild) {
               stack.shrink(1);
               if (stack.isEmpty()) {
                   return new ItemStack(Items.GLASS_BOTTLE);
               }
               
               playerentity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
       
    	return stack;
    }
    
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override  
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) {
        	if (stack.getItem().equals(FURItemRegistry.FISSIONPOTION)) {	 
        		entityLiving.addEffect(new EffectInstance(Effects.REGENERATION, 225, 2));
        	} else if (stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA)) {
        		entityLiving.setSecondsOnFire(12);
        	} else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST)) {      
        		entityLiving.addEffect(new EffectInstance(FUREffectRegistry.CHARMING_PHEROMONE, 30 * 20, 0));
        	}
        }

        return this.returnItem(stack, worldIn, entityLiving); 
    }

}
