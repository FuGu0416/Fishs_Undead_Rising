package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.FogletEntity;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDropRider;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class PteraEntity extends FlyingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(PteraEntity.class, DataSerializers.INT);
	
	public PteraEntity(EntityType<? extends PteraEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		//this.moveControl = new FlyingMovementController(this, 30, true);
	}

    @Override
    public boolean canBeControlledByRider() {
        return false;
    }
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this));
		if(this.level.getDifficulty() == Difficulty.HARD)
			this.goalSelector.addGoal(1, new EntityAIDropRider(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true).setUnseenMemoryTicks(160));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractFishEntity.class, 120, true, true, null).setUnseenMemoryTicks(160));
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.1D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Ptera_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Ptera_Attack.get())
        		.add(Attributes.FLYING_SPEED, 0.1D);
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
	
	@Override
	public boolean shouldRiderSit() {
		return false;
	}
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
	@Override
    public double getPassengersRidingOffset()
    {
        if(!this.getPassengers().isEmpty())
        	return -(double)this.getPassengers().get(0).getBbHeight() * 0.75D;
        
        return super.getPassengersRidingOffset();
    }
    
	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.35F;
    }
  
   /**
    * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
    * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
    */
   @Nullable
   @Override
   public Entity getControllingPassenger() {
       return null;
   }

   @Override
   public boolean doHurtTarget(Entity par1Entity) {
	   if(par1Entity instanceof AbstractFishEntity)
		   this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000.0D);
	   else
		   this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ptera_Attack.get());
	   
	   return super.doHurtTarget(par1Entity);
   }
   /**
    * Called when the entity is attacked.
    */
   public boolean hurt(DamageSource source, float amount)
   {
       if(!this.getPassengers().isEmpty())
    	   this.ejectPassengers();
    	   
	   return super.hurt(source, amount);
   }
   
   public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {
	   this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Ptera_Health.get());
       this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ptera_Attack.get());
       this.setHealth(this.getMaxHealth());

	   if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.SAVANNA))
		   this.setSkin(2);
	   else if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.SWAMP))
		   this.setSkin(3);
	   else if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.DRY))
		   this.setSkin(1);
	   
	   if(this.level.getDifficulty() == Difficulty.HARD) {
	        if (this.getRandom().nextInt(100) < 2) {
	        	CreeperEntity entityRider = EntityType.CREEPER.create(this.level);
	            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
	            entityRider.finalizeSpawn(worldIn, difficulty, p_213386_3_, (ILivingEntityData)null, (CompoundNBT)null);
	            entityRider.startRiding(this);	   
	        } else if (this.getRandom().nextInt(100) < 5) {
	            if(this.getSkin() == 0) {
	            	ZombieEntity entityRider = EntityType.ZOMBIE.create(this.level);
		            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
		            entityRider.finalizeSpawn(worldIn, difficulty, p_213386_3_, (ILivingEntityData)null, (CompoundNBT)null);
		            entityRider.startRiding(this);
	            }
	            else if(this.getSkin() == 1) {
	            	HuskEntity entityRider = EntityType.HUSK.create(this.level);
		            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
		            entityRider.finalizeSpawn(worldIn, difficulty, p_213386_3_, (ILivingEntityData)null, (CompoundNBT)null);
		            entityRider.startRiding(this);
	            }
	        } else if (this.getRandom().nextInt(100) < 10 && this.getSkin() == 0) {
	        	FogletEntity entityRider = FUREntityRegistry.FOGLET.create(this.level);
	            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
	            entityRider.finalizeSpawn(worldIn, difficulty, p_213386_3_, (ILivingEntityData)null, (CompoundNBT)null);
	            entityRider.setIsHanging(true);
	            entityRider.startRiding(this);	   
	        }
		}

	   
	   return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
	}  
   
	public int getSkin() {
   		return this.getEntityData().get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
   		this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
	}
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.PTERA_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.PTERA_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.PTERA_DEATH;
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}
}
