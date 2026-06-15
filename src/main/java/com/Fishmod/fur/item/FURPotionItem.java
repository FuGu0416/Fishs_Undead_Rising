package com.Fishmod.fur.item;

import java.util.Random;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.LavaCowEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class FURPotionItem extends FURItem {
	
	private SoundEvent using_sound = null;
	private SimpleParticleType using_particle = ParticleTypes.SMOKE;
	
	public FURPotionItem(Properties properties, SoundEvent sound, SimpleParticleType particle) {
    	super(properties, 32, UseAnim.DRINK, 1);
    	this.using_sound = sound;
		this.using_particle = particle;
    }
 
    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA.get());
	}
	   
    private boolean isVanilla(ResourceLocation resourceLocation) {
    	return resourceLocation.toString().contains("minecraft:") || resourceLocation.toString().contains("fur:");
    }
	
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand InteractionHand) {
    	if(((!FURConfig.Fission_ModEntity.get() && isVanilla(ForgeRegistries.ENTITY_TYPES.getKey(target.getType()))) || (FURConfig.Fission_ModEntity.get() && target instanceof AgeableMob))) {
			
    		double dx = target.getX();
    		double dy = target.getY();
    		double dz = target.getZ();
    		boolean flag = false;
    		
    		if(!player.level().isClientSide) {
	    		if (stack.getItem().equals(FURItemRegistry.POTION_OF_FISSION.get()) && !target.isBaby()) {	    			
			    	AgeableMob parent = (AgeableMob)target;
			    	AgeableMob ageable = (AgeableMob)parent.getType().create(player.level());			    	    	
			    	CompoundTag CompoundTag = new CompoundTag();
			    	
			    	parent.addAdditionalSaveData(CompoundTag);			    			    	
			    	
			    	if (ageable instanceof Saddleable) {			    		
			    		CompoundTag.putBoolean("Saddled", false);
			    	}
			    
			    	ageable.readAdditionalSaveData(CompoundTag);				    	
			        ageable.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.getYRot(), target.getXRot());
			        parent.level().addFreshEntity(ageable);			        			       
			        
			        ageable.setBaby(true);
			        
			        if (ageable.getClass() == parent.getClass()) {
			        	parent.setBaby(true);
			        }
			        
			        if(parent instanceof TamableAnimal && ((TamableAnimal)parent).isTame() && ageable instanceof TamableAnimal tamable && !tamable.isTame()) {
			        	tamable.tame(player);
			        }
			        
			        ageable.playAmbientSound();
			        
			        flag = true;
	    		} else if (stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA.get()) && target instanceof Cow && !(target instanceof LavaCowEntity) && !target.isBaby()) {
	    			if (!target.hasEffect(MobEffects.FIRE_RESISTANCE)) {
	    				target.setSecondsOnFire(12);
	    			} else {	    				
	    				for (int i = 0 ; i < 2 ; i++) {
		    				AgeableMob ageable = FUREntityRegistry.LAVACOW.get().create(player.level());
		    				ageable.setBaby(true);
		    				ageable.moveTo(target.getX(), target.getY() + 0.2F, target.getZ(), target.getYRot(), target.getXRot());
		    				player.level().addFreshEntity(ageable);
	    				}	    				
	    				target.discard();
	    			}
	    			
			        flag = true;
	    		}/* else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST) && target.getType().equals(FUREntityRegistry.PARASITE)) {
	    			VespaCocoonEntity pupa = null;
	    			
	    			switch(((ParasiteEntity)target).getSkin()) {
	    				case 3:
	    					pupa = FUREntityRegistry.BEELZEBUBPUPA.create(player.level);
	    					break;
	    				case 2:
	    					pupa = FUREntityRegistry.VESPACOCOON.create(player.level);
	    					pupa.setSkin(0);
	    					break;
	    				default:
	    					break;
	    			}
	    			
	    			if (pupa != null) {
		    			target.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
	        			
			    		pupa.moveTo(target.getX(), target.getY(), target.getZ(), target.yRot, target.xRot);
			    		pupa.tame(player);
			    		player.level.addFreshEntity(pupa);
			    		target.remove();
			    		
		    			flag = true;
	    			}
	    		} else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST) && target.getType().equals(FUREntityRegistry.ENIGMOTH) && target.isBaby()) {
	    			VespaCocoonEntity pupa = FUREntityRegistry.VESPACOCOON.create(player.level);
	    			
	    			if (pupa != null) {
		    			target.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
	        			
			    		pupa.moveTo(target.getX(), target.getY(), target.getZ(), target.yRot, target.xRot);
			    		pupa.tame(player);
			    		pupa.setSkin(1);
			    		player.level.addFreshEntity(pupa);
			    		target.remove();
			    		
		    			flag = true;
	    			}
	    		}*/            		
    		}
    		  		
    		if (flag) {		
    			this.returnItem(stack, player.level(), player);
    			player.addItem(new ItemStack(Items.GLASS_BOTTLE));
    		}   		
	    	
    		if (player.level() instanceof ServerLevel server && flag) {	 
    			player.level().playSound(null, player.blockPosition(), this.using_sound, SoundSource.PLAYERS, 1.0F, 1.0F);
	    		for (int i = 0; i < 5; ++i) {
	    			double d0 = new Random().nextGaussian() * 0.02D;
	    			double d1 = new Random().nextGaussian() * 0.02D;
	    			double d2 = new Random().nextGaussian() * 0.02D;
	    			server.sendParticles(this.using_particle, dx + (double)(new Random().nextFloat() * player.getBbWidth() * 2.0F) - (double)player.getBbWidth(), dy + 1.0D + (double)(new Random().nextFloat() * player.getBbHeight()), dz + (double)(new Random().nextFloat() * player.getBbWidth() * 2.0F) - (double)player.getBbWidth(), 15, 0.0D, d0, d1, d2);
	    		}
    		}
    		
    		return flag ? InteractionResult.sidedSuccess(player.level().isClientSide) : InteractionResult.PASS;
        }  
    	
        return InteractionResult.PASS;
    }
    
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
	}
    
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
	}
    
    private ItemStack returnItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player Player = entityLiving instanceof Player ? (Player)entityLiving : null;
        if (Player instanceof ServerPlayer) {
           CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)Player, stack);
        }
        
        if (Player != null) {
        	Player.awardStat(Stats.ITEM_USED.get(this));
            if (!Player.getAbilities().instabuild) {
               stack.shrink(1);
               if (stack.isEmpty()) {
                   return new ItemStack(Items.GLASS_BOTTLE);
               }
               
               Player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
       
    	return stack;
    }
    
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override  
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) {
        	if (stack.getItem().equals(FURItemRegistry.POTION_OF_FISSION.get())) {	 
        		entityLiving.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 225, 2));
        	} else if (stack.getItem().equals(FURItemRegistry.POTION_OF_MOOTEN_LAVA.get())) {
        		entityLiving.setSecondsOnFire(12);
        	} else if (stack.getItem().equals(FURItemRegistry.CHARMING_CATALYST.get())) {
        		entityLiving.addEffect(new MobEffectInstance(FUREffectRegistry.CHARMING_PHEROMONE.get(), 30 * 20, 0));
        	}
        }

        return this.returnItem(stack, worldIn, entityLiving); 
    }

}
