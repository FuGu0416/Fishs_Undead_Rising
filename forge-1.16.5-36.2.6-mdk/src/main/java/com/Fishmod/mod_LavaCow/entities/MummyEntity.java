package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIBreakDoor;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MummyEntity extends ZombieEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MummyEntity.class, DataSerializers.INT);
	private int attackTimer;
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {
		return p_213697_0_ == Difficulty.HARD;
	};
	
    public MummyEntity(EntityType<? extends MummyEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
        this.setCanBreakDoors(false);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
    
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new EntityFishAIBreakDoor(this, DOOR_BREAKING_PREDICATE));
    }
    
   
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ZombieEntity.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.21F)
        		.add(Attributes.MAX_HEALTH, FURConfig.Mummy_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Mummy_Attack.get())
        		.add(Attributes.ARMOR, 4.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep()
    {
        super.aiStep();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
    }
	
	@Override
    protected boolean isSunSensitive()
    {
        return false;
    }
    
    public static boolean checkMummySpawnRules(EntityType<? extends MummyEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    protected boolean convertsInWater() {
        return false;
    }
    
    protected boolean supportsBreakDoorGoal() {
        return true;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
    	super.tick();

    	if(this.tickCount % 2 == 0)
    		this.level.addParticle(FURParticleRegistry.LOCUST_SWARM, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), this.getY() + (double)((0.4F + new Random().nextFloat() * 0.6F) * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
    
    public boolean doHurtTarget(Entity par1Entity)
    {
        if (super.doHurtTarget(par1Entity))
        {
        	this.attackTimer = 5;
        	this.level.broadcastEntityEvent(this, (byte)4);
        	
        	if (par1Entity instanceof LivingEntity) {
        		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            	((LivingEntity)par1Entity).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 2 * 20 * (int)local_difficulty, 1));
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
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mummy_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mummy_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }

    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) 
    	{
            this.attackTimer = 5;
        }
        else
        {
            super.handleEntityEvent(id);
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.UNBURIED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.UNBURIED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.UNBURIED_DEATH;
    }
       
    public int getSkin()
    {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
    	this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setSkin(nbt.getInt("Variant"));
    }
}
