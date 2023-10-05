package com.Fishmod.mod_LavaCow.entities.aquatic;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

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
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
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
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class SwarmerEntity extends AbstractGroupFishEntity {
	protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(SwarmerEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(SwarmerEntity.class, DataSerializers.INT);
	
    public SwarmerEntity(EntityType<? extends SwarmerEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);   
    }
    
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
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
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.SWARMER_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag) && ((LivingEntity)p_210136_0_).getHealth() < ((LivingEntity)p_210136_0_).getMaxHealth();
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
    
    public static boolean isDarkEnoughToSpawn(IServerWorld p_223323_0_, BlockPos p_223323_1_, Random p_223323_2_) {
        if (p_223323_0_.getBrightness(LightType.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
           return false;
        } else {
           int i = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(p_223323_1_, 10) : p_223323_0_.getMaxLocalRawBrightness(p_223323_1_);
           return i <= p_223323_2_.nextInt(8);
        }
	}
    
    public static boolean checkSwarmerSpawnRules(EntityType<? extends SwarmerEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return p_223316_4_.nextInt(15) == 0 && isDarkEnoughToSpawn((IServerWorld) p_223316_1_, p_223316_3_, p_223316_4_) && AbstractFishEntity.checkFishSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.getDifficulty() != Difficulty.PEACEFUL;
    }
    
    public int getMaxSchoolSize() {
        return 12;
    }
    
    protected void handleAirSupply(int p_209207_1_) {    	
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
    	if (this.tickCount >= 8 * 20 && this.getIsAmmo()) {
    		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor(), this.getMaxHealth());
    	}
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
    	
    	if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition()))).contains(Type.SWAMP) && !this.getIsAmmo()) {
    		this.setSkin(2);
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
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    public boolean getIsAmmo() {
    	return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setIsAmmo(boolean t) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (t) {
           this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
        } else {
           this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
        }
    }
    
    public boolean getIsInfinite() {
    	return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }

    public void setIsInfinite(boolean t) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (t) {
           this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 4));
        } else {
           this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -5));
        }
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
		this.setIsAmmo(compound.getBoolean("is_Ammo"));
		this.setIsInfinite(compound.getBoolean("is_Infinite"));
		this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("is_Ammo", this.getIsAmmo());
        compound.putBoolean("is_Infinite", this.getIsInfinite());
        compound.putInt("Variant", getSkin());
    }
	
	@Override
	protected void dropExperience() {
		if(!this.getIsAmmo()) {
			super.dropExperience();
		}
	}
       
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {   	
    	return !this.getIsInfinite();
    }
}
