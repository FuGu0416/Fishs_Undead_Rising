package com.Fishmod.mod_LavaCow.entities.aquatic;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SwarmerEntity extends AbstractGroupFishEntity {
	private boolean isAmmo = false;

    public SwarmerEntity(EntityType<? extends SwarmerEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);   
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AIPiranhaLeapAtTarget(this, 0.6F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));      
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.applyEntityAI();
	}
    
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_210136_0_) -> {
            return !this.requiresCustomPersistence();
    	}));
    	if(FURConfig.Piranha_AnimalAttack.get()) {
    		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SquidEntity.class, true));
    		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AgeableEntity.class, 10, true, false, (p_210136_0_) -> {
                    return !(p_210136_0_ instanceof TameableEntity) && ((AgeableEntity)p_210136_0_).getHealth() < ((AgeableEntity)p_210136_0_).getMaxHealth();
            }));
    	}
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 0, true, true, (p_210136_0_) -> {
                return !(p_210136_0_ instanceof SwarmerEntity || p_210136_0_ instanceof CreeperEntity) && ((MonsterEntity)p_210136_0_).getHealth() < ((MonsterEntity)p_210136_0_).getMaxHealth();
        }));
    	this.targetSelector.addGoal(5, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Swarmer_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Swarmer_Attack.get());
    }
    
    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
	}
    
    @Override
    public int getMaxSpawnClusterSize() {
       return 2;
    }
    
    public static boolean checkSwarmerSpawnRules(EntityType<? extends SwarmerEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return p_223316_4_.nextInt(15) == 0 && AbstractFishEntity.checkFishSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.getDifficulty() != Difficulty.PEACEFUL;
    }
    
    public int getMaxSchoolSize() {
        return 12;
    }
    
    protected void handleAirSupply(int p_209207_1_) {    	
    }
    
    @Override
    public void tick() {
    	super.tick();
    }
    
    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
           this.doEnchantDamageEffects(this, p_70652_1_);
           this.playSound(FURSoundRegistry.SWARMER_ATTACK, 1.0F, 1.0F);
        }

        return flag;
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
    	if(this.getType().equals(FUREntityRegistry.SWARMER)) {
	    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Swarmer_Health.get());
	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Swarmer_Attack.get());
	    	this.setHealth(this.getMaxHealth());
    	}
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    static class AIPiranhaLeapAtTarget extends Goal {
    	   /** The entity that is leaping. */
    	   private final MobEntity leaper;
    	   /** The entity that the leaper is leaping towards. */
    	   private LivingEntity leapTarget;
    	   /** The entity's motionY after leaping. */
    	   private final float leapMotionY;

    	   public AIPiranhaLeapAtTarget(MobEntity leapingEntity, float leapMotionYIn) {
    	      this.leaper = leapingEntity;
    	      this.leapMotionY = leapMotionYIn;
    	      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    	   }
    	   
    	   /**
    	    * Returns whether the EntityAIBase should begin execution.
    	    */
    	   public boolean canUse() {
    	      this.leapTarget = this.leaper.getTarget();
    	      if (this.leapTarget == null) {
    	         return false;
    	      } else {
    	         double d0 = this.leaper.distanceToSqr(this.leapTarget);
    	         if (!(d0 < 4.0D) && !(d0 > 24.0D) && this.leaper.isInWaterOrBubble() && this.leapTarget.getY() >= this.leaper.getY()) {
    	               return true;
    	         } else {
    	            return false;
    	         }
    	      }
    	   }
    	   
    	   /**
    	    * Returns whether an in-progress EntityAIBase should continue executing
    	    */
    	   public boolean canContinueToUse() {
    	      return !this.leaper.isOnGround() && !this.leaper.isInWaterOrBubble();
    	   }

    	   /**
    	    * Execute a one shot task or start executing a continuous task
    	    */
    	   public void start() {
      	      double d0 = this.leapTarget.getX() - this.leaper.getX();
      	      double d1 = this.leapTarget.getZ() - this.leaper.getZ();
     	      float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
     	      if ((double)f >= 1.0E-4D) {
     	    	  this.leaper.setDeltaMovement(d0 / (double)f * 0.5D * (double)0.8F + this.leaper.getDeltaMovement().x * (double)0.2F, (double)this.leapMotionY, d1 / (double)f * 0.5D * (double)0.8F + this.leaper.getDeltaMovement().z * (double)0.2F);
     	      }
    	   }  	   
    }
    
    protected ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.SWARMER_BUCKET);
	}
 
    @Override
    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }
    
	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.SWARMER_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.SWARMER_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SALMON_HURT;
	}
     
	protected SoundEvent getFlopSound() {
		return SoundEvents.GUARDIAN_FLOP;
	}
     
    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
	
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return this.getBbHeight() * 0.5F;
	}
	
	public void setIsAmmo(boolean t) {
		this.isAmmo = t;
	}
       
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isAmmo;
    }
}
