package com.Fishmod.fur.entities.aquatic;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityAIPickupMeat;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SwarmerEntity extends AbstractSchoolingFish implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SwarmerEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(SwarmerEntity.class, EntityDataSerializers.INT);
	private static final UUID SPEED_BOOST_UUID = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
	
    private static final RawAnimation SWIM = RawAnimation.begin().thenPlay("swarmer.model.swimming");
    private static final RawAnimation LAND = RawAnimation.begin().thenPlay("swarmer.model.onland");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("swarmer.model.attacking");
    
    public SwarmerEntity(EntityType<? extends SwarmerEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);   
    }
    
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(1));
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AIPiranhaLeapAtTarget(this, 0.6F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));      
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.applyEntityAI();
	}
    
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, (p_210136_0_) -> {
            return !this.requiresCustomPersistence();
    	}));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 10, true, false, (p_210136_0_) -> {
    		return !this.requiresCustomPersistence() && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(FURTagRegistry.SWARMER_TARGETS) && ((LivingEntity)p_210136_0_).getHealth() < ((LivingEntity)p_210136_0_).getMaxHealth();
    	}));
    	this.targetSelector.addGoal(5, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 8.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    @Override
    protected boolean shouldDespawnInPeaceful() {
        return !this.getIsAmmo();
	}
    
    @Override
    public int getMaxSpawnClusterSize() {
       return 2;
    }
    
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_223323_0_, BlockPos p_223323_1_, RandomSource p_223323_2_) {
        if (p_223323_0_.getBrightness(LightLayer.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
           return false;
        } else {
           int i = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(p_223323_1_, 10) : p_223323_0_.getMaxLocalRawBrightness(p_223323_1_);
           return i <= p_223323_2_.nextInt(8);
        }
	}
    
    public static boolean checkSwarmerSpawnRules(EntityType<? extends SwarmerEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return isDarkEnoughToSpawn(p_223316_1_, p_223316_3_, p_223316_4_) && WaterAnimal.checkSurfaceWaterAnimalSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.getDifficulty() != Difficulty.PEACEFUL;
    }
    
    public int getMaxSchoolSize() {
        return 12;
    }
    
    protected void handleAirSupply(int p_209207_1_) {   
    	if (!this.getType().equals(FUREntityRegistry.SWARMER.get())) {
    		super.handleAirSupply(p_209207_1_);
    	}
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
        AttributeInstance attr = this.getAttribute(Attributes.MOVEMENT_SPEED);

        if (this.getTarget() != null) {
            if (attr.getModifier(SPEED_BOOST_UUID) == null) {
                attr.addTransientModifier(new AttributeModifier(SPEED_BOOST_UUID, "Chasing speed", 1.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            attr.removeModifier(SPEED_BOOST_UUID);
        }
 
    	if (this.getIsAmmo() && this.tickCount >= 8 * 20) {
    		this.kill();
    	}
    }
    
    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = p_70652_1_.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
           this.doEnchantDamageEffects(this, p_70652_1_);
           this.level().broadcastEntityEvent(this, (byte)4);
           /*if (!this.getType().equals(FUREntityRegistry.LAMPREY)) {
        	   this.playSound(FURSoundRegistry.SWARMER_ATTACK.get(), 1.0F, 1.0F);
           }*/
        }

        return flag;
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
    	if (this.getType().equals(FUREntityRegistry.SWARMER.get())) {
	    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Swarmer_Health.get());
	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Swarmer_Attack.get());
	    	this.setHealth(this.getMaxHealth());
    	}   	
    	
    	if (!this.getIsAmmo()) {
    		if (this.getType().equals(FUREntityRegistry.PIRANHA.get())) {
    			this.setSkin(0);
    		} else if (p_213386_3_ == MobSpawnType.BUCKET && p_213386_5_ != null && p_213386_5_.contains("BucketVariantTag", 3)) {
    			this.setSkin(p_213386_5_.getInt("BucketVariantTag"));
    			return livingdata;
    	    } else if (p_213386_1_.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_SWAMP)) {
	    		this.setSkin(2);
	    	} else if (p_213386_1_.getBiome(this.blockPosition()).is(Biomes.DEEP_LUKEWARM_OCEAN)) {
	    		this.setSkin(3);
	    	} else if (p_213386_1_.getBiome(this.blockPosition()).is(Biomes.LUKEWARM_OCEAN)) {
	    		this.setSkin(4);
	    	} else if (p_213386_1_.getBiome(this.blockPosition()).is(Biomes.DEEP_DARK)) {
	    		this.setSkin(6);
	    	}
    	}
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
        	this.triggerAnim("trigger_controller", "attack");
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    static class AIPiranhaLeapAtTarget extends Goal {
    	   /** The entity that is leaping. */
    	   private final Mob leaper;
    	   /** The entity that the leaper is leaping towards. */
    	   private LivingEntity leapTarget;
    	   /** The entity's motionY after leaping. */
    	   private final float leapMotionY;

    	   public AIPiranhaLeapAtTarget(Mob leapingEntity, float leapMotionYIn) {
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
    	         if (!(d0 < 4.0D) && !(d0 > 24.0D) && this.leapTarget.getY() >= this.leaper.getY()) {
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
    	      return !this.leaper.onGround() && !this.leaper.isInWaterOrBubble();
    	   }

    	   /**
    	    * Execute a one shot task or start executing a continuous task
    	    */
    	   public void start() {
      	      double d0 = this.leapTarget.getX() - this.leaper.getX();
      	      double d1 = this.leapTarget.getZ() - this.leaper.getZ();
     	      double f = Math.sqrt(d0 * d0 + d1 * d1);
     	      if (f >= 1.0E-4D) {
     	    	  this.leaper.setDeltaMovement(d0 / f * 0.5D * 0.8D + this.leaper.getDeltaMovement().x * 0.2D, (double)this.leapMotionY, d1 / f * 0.5D * 0.8D + this.leaper.getDeltaMovement().z * 0.2D);
     	      }
    	   }  	   
    }
    
    @Override
    public void saveToBucketTag(ItemStack p_30049_) {
        super.saveToBucketTag(p_30049_);
        CompoundTag compoundtag = p_30049_.getOrCreateTag();
        compoundtag.putInt("BucketVariantTag", this.getSkin());
	}
    
    @Override
    public ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.SWARMER_BUCKET.get());
	}
 
    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
	}
    
	protected SoundEvent getAmbientSound() {
		return null;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.SWARMER_DEATH.get();
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
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
	
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
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
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
		this.setIsAmmo(compound.getBoolean("is_Ammo"));
		this.setIsInfinite(compound.getBoolean("is_Infinite"));
		this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("is_Ammo", this.getIsAmmo());
        compound.putBoolean("is_Infinite", this.getIsInfinite());
        compound.putInt("Variant", getSkin());
    }
	
	@Override
	protected void dropExperience() {
		if (!this.getIsAmmo()) {
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

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (!this.isInWaterOrBubble() && !this.isAggressive()) {
    		state.getController().setAnimation(LAND);
        } else {
            state.getController().setAnimation(SWIM);
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
