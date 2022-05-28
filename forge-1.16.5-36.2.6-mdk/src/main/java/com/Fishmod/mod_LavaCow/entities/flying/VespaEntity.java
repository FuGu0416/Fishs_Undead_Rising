package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class VespaEntity extends FlyingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(VespaEntity.class, DataSerializers.INT);
	
	public VespaEntity(EntityType<? extends VespaEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		//this.moveControl = new FlyingMovementController(this, 30, true);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true).setUnseenMemoryTicks(160));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true).setUnseenMemoryTicks(160));
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.1D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Vespa_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Vespa_Attack.get())
        		.add(Attributes.FLYING_SPEED, 0.1D);
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.35F;
    }
   
    @Override
	public boolean doHurtTarget(Entity par1Entity) {
		if (super.doHurtTarget(par1Entity))
		{
			if (par1Entity instanceof LivingEntity)
			{
				float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

				((LivingEntity) par1Entity).addEffect(new EffectInstance(Effects.POISON, 6 * 20 * (int)local_difficulty, 0));
           		if(this.getRandom().nextInt(5) == 0)
           			((LivingEntity) par1Entity).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 6 * 20 * (int)local_difficulty, 0));
			}

			return true;
       }
       else
       {
           return false;
       }
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
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
		return FURSoundRegistry.VESPA_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.VESPA_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.VESPA_DEATH;
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}
}
