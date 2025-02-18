package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.FogletEntity;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDropRider;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class PteraEntity extends FlyingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(PteraEntity.class, DataSerializers.INT);
	
	public PteraEntity(EntityType<? extends PteraEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
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
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 120, true, true, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.PTERA_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag);
    	}).setUnseenMemoryTicks(160));
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
	   ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.PTERA_TARGETS);

	   if (tag != null && par1Entity instanceof LivingEntity && par1Entity.getType().is(tag)) {
		   this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ptera_Attack.get() * 2.0D);
	   } else {
		   this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ptera_Attack.get());
	   }
	   
	   return super.doHurtTarget(par1Entity);
   }
   
   /**
    * Called when the entity is attacked.
    */
   public boolean hurt(DamageSource source, float amount) {
       if (!this.getPassengers().isEmpty()) {
    	   this.ejectPassengers();
       }
    	   
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
	   
	   if (!this.isPassenger() && this.random.nextInt(100) < FURConfig.Ptera_Ability_Chance.get()) {
		   MobSpawnInfo.Spawners Result = ((MobSpawnInfo.Spawners)WeightedRandom.getRandomItem(this.random, LootTableHandler.PTERA_LIST));
		   Entity entityRider = Result.type.create(this.level);
			
		   if(entityRider instanceof MobEntity) {					
				entityRider.moveTo(this.blockPosition(), this.yRot, 0.0F);
				this.level.addFreshEntity(entityRider);
				entityRider.startRiding(this);
				((MobEntity) entityRider).finalizeSpawn(worldIn, difficulty, SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);								
				
				if(entityRider instanceof FogletEntity) {
					((FogletEntity) entityRider).setIsHanging(true);
				}
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
